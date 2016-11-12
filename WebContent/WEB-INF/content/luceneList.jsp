<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
.highlighter {
	color: red;
	background-color: yellow;
}
</style>

</head>
<body>
	<a href="commitRamIndex.action">提交内存中的索引</a>
	<a href="commitDBIndex.action">提交数据库中的索引</a>
	<a href="reCreCommitIndex.action">重构所有索引</a>
	<a href="deleteIndex.action">删除所有索引</a>
	<table border="1" width="80%">
		<tr>
			<th>ID</th>
			<th>标题</th>
			<th>简介</th>
		</tr>
		<c:forEach items="${page.resultList}" var="m">
			<tr>
				<td>${m.id}</td>
				<td>${m.title}</td>
				<td>${m.summary}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>