<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Title</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.css">
</head>
<body>
<div style=" width: 800px; margin-left: 400px;margin-top: 100px">
    <form method="post" action="http://upload.qiniu.com/" enctype="multipart/form-data">
        <input name="token" type="text" hidden value="${upToken}">
        选择文件:<input name="file" type="file">
        <button>上传文件</button>
    </form>
</div>


</body>
</html>