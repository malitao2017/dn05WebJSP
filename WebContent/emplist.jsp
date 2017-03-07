<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@page import="java.util.*,util.*,entity.*,dao.*"%>
<html>
	<head>
		<title>empList</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/style.css" />
	</head>
	<body>
		<div id="wrap">
			<div id="top_content"> 
				<%@include file="head.jsp" %>
				<div id="content">
					<p id="whereami">
					</p>
					<h1>
						Welcome!
					</h1>
					<table class="table">
						<tr class="table_header">
							<td>
								ID
							</td>
							<td>
								Name
							</td>
							<td>
								Salary
							</td>
							<td>
								Age
							</td>
							<td>
								Operation
							</td>
						</tr>
						<%
						List<Employee> list =  (List<Employee>)request.getAttribute("employees");
						for(int i=0;i<list.size();i++){
							Employee emp = list.get(i);
						%>
							<tr class="row<%=i%2+1 %>">
								<td><%=emp.getId() %></td>
								<td><%=emp.getName() %></td>
								<td><%=emp.getSalary() %></td>
								<td><%=emp.getAge() %></td>
								<td><a href="del.do?id=<%=emp.getId() %>">删除</a>|
									<a href="load.do?id=<%=emp.getId() %>" >修改</a></td>
							</tr>
						<%
						}
						%>
					</table>
					<p>
						<input type="button" class="button" value="Add Employee" onclick="location='addEmp.jsp'"/>
					</p>
				</div>
			</div>
			<div id="footer">
				<div id="footer_bg">
				ABC@126.com
				</div>
			</div>
		</div>
	</body>
</html>
