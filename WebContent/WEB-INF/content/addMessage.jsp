<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="saveMessage.action" method="post"
		enctype="multipart/form-data">
		标题：<input type="text" name="message.title"><br /> 内容：
		<textarea rows="5" cols="20" name="message.content"></textarea>
		<br /> 附件:<input type="file" name="file">
		 <input type="submit" value="Save">
	</form>
</body>
</html>