/*
 * ActionServlet.java
 * Copyright: TsingSoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DAOFactory;
import dao.EmployeeDAO;
import entity.Employee;

/**
 * 业务处理的总逻辑
 * @author LT
 * @version 1.0, 2015年9月11日
 */
public class ActionServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String uri = request.getRequestURI();
		//注意不是indexOf
		String path = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
		
		if(path.equals("/list")){
			Connection con = null;
			try{
				EmployeeDAO dao = null;
				dao = (EmployeeDAO) DAOFactory.getInstance("EmployeeDAO");
				
				List<Employee> list= dao.findAll();
				request.setAttribute("employees", list);
//				PrintWriter out = response.getWriter();
//				out.println("转发之前不能有此操作");
//				out.close();//
				request.getRequestDispatcher("emplist.jsp").forward(request, response);;
			}catch(Exception e){
				e.printStackTrace();
				throw new ServletException(e);
			}finally{
				if(con!=null)
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}else if(path.equals("/add")){
			try {
				String name = request.getParameter("name");
				double salary = Double.valueOf(request.getParameter("salary"));
				int age = Integer.valueOf(request.getParameter("age"));
				System.out.println("name:"+name);
				System.out.println("salaty:"+salary);
				System.out.println("age:"+age);
			
				EmployeeDAO dao = null;
				dao = (EmployeeDAO) DAOFactory.getInstance("EmployeeDAO");
				
				Employee emp = new Employee();
				emp.setName(name);
				emp.setSalary(salary);
				emp.setAge(age);
				dao.save(emp);
				//更改为重定向模式
				//重定向之前不能有out.close();
				response.sendRedirect("list.do");
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
		}else if(path.equals("/load")){
			long id = Long.parseLong(request.getParameter("id"));
			try{
				EmployeeDAO dao = null;
				dao = (EmployeeDAO) DAOFactory.getInstance("EmployeeDAO");
				Employee emp = dao.findById(id);
				request.setAttribute("employee", emp);
				request.getRequestDispatcher("updateEmp.jsp").forward(request, response);;
			}catch(Exception e){
				e.printStackTrace();
				throw new ServletException(e);
			}
		}else if(path.equals("/modify")){
			long id = Long.parseLong(request.getParameter("id"));
			String name = request.getParameter("name");
			double salary = Double.parseDouble(request.getParameter("salary"));
			int age = Integer.parseInt(request.getParameter("age"));
			try{
				EmployeeDAO dao = null;
				dao = (EmployeeDAO) DAOFactory.getInstance("EmployeeDAO");
				
				Employee emp = new Employee();
				emp.setId(id);
				emp.setName(name);
				emp.setSalary(salary);
				emp.setAge(age);
				dao.update(emp);
				response.sendRedirect("list.do");
			}catch(Exception e){
				e.printStackTrace();
				throw new ServletException(e);
			}
		}else if(path.equals("/del")){
			long id = Long.parseLong(request.getParameter("id"));
			try{
				EmployeeDAO dao = null;
				dao = (EmployeeDAO) DAOFactory.getInstance("EmployeeDAO");
				dao.delete(id);
				response.sendRedirect("list.do");
			}catch(Exception e){
				e.printStackTrace();
				throw new ServletException(e);
			}
		}
	}
}
