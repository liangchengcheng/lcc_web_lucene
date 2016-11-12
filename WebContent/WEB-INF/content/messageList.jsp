<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ tablib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="addMessage.action">添加新留言</a>
	<hr />
	<form action="lucene/search.action" method="post">
		<input type="text" name="keyword"> <select name="field">
			<option value="title">标题</option>
			<option value="content">全文</option>
			<option value="last_modified">日期</option>
		</select> <input type="submit" value="检索">
	</form>
	<table border="1" width="80%">
		<tr>
			<th>ID</th>
			<th>标题</th>
			<th>添加时间</th>
		</tr>
		<c:forEach items="${messageList }" var="m">
			<tr>
				<td>${m.id}</td>
				<td>${m.title}</td>
				<td>${m.addTime}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>