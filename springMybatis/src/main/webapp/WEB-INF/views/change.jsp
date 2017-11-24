<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="functions" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
</head>
<body>

    <div class="container">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <form action="/book/list/update" id="bookMessage">
                    <label>书籍编号</label>
                    <input class="form-control" type="text" name="bookId" value="${book.bookId}" disabled><br>
                    <label>书籍名称</label>
                    <input class="form-control" type="text" name="bookName" value="${book.bookName}"><br>
                    <label>书籍作者</label>
                    <input class="form-control" type="text" name="bookAuthor" value="${book.bookAuthor}"><br>
                    <label>书籍类型</label>
                    <input class="form-control" type="text" name="bookType" value="${book.bookType}"><br>
                </form>
                <button class="btn btn-info" id="change">修改</button>
                <button class="btn btn-group" id="giveUp">取消</button>
            </div>
        </div>
    </div>
    <script src="/static/bootstrap/js/jquery-3.2.1.min.js"></script>
    <script src="/static/bootstrap/js/bootstrap.min.js"></script>
    <script>
        $("#change").click(function(){
            $("#bookMessage").submit();
        })
    </script>
</body>
</html>