<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>uploadFile</title>
</head>
<body>
    <h1>this is file upload test!</h1>
    <form action="uploadFileToMDFS" method="post" enctype="multipart/form-data">
        <input type="text" name="username"/>
        <input type="file" name="upfile"/>
        <input type="submit" value="submit"/>
    </form>
</body>
</html>