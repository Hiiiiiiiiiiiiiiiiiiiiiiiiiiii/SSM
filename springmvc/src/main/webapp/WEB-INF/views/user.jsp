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
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="/enter" method="post">
            <label>账户名:</label>
            <input class="form-control" name="userName" type="text">
            <label>密码:</label>
            <input class="form-control" name="password" type="password">
            <input type="file" name="files" class="form-control">
            <button class="btn btn-info" style="margin-top: 20px">确认</button>
        </form>
    </div>

    <script src="/static/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>