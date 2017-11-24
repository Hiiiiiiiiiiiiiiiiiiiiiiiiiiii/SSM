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
                <div class="col-md-2">

                </div>
                <div class="col-md-7">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr class="info">
                                    <td>
                                        <label>书籍名称</label>
                                    </td>
                                    <td>
                                        <label>书籍编号</label>
                                    </td>
                                    <td>
                                        <label>作者</label>
                                    </td>
                                    <td>
                                        <label>类型</label>
                                    </td>
                                    <td>
                                        <label>操作</label>
                                    </td>
                                </tr>
                            </thead>

                            <tbody>
                            <tr class="active">
                                <td>
                                    <label>${book.bookName}</label>
                                </td>
                                <td>
                                    <label>${book.bookId}</label>
                                </td>
                                <td>
                                    <label>${book.bookAuthor}</label>
                                </td>
                                <td>
                                    <label>${book.bookType}</label>
                                </td>
                                <td>
                                    <a href="javascript:;" id="delete" rel="${book.bookId}">删除</a>
                                    <a href="javascript:;" id="change" rel="${book.bookId}">修改</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <script src="/static/bootstrap/js/jquery-3.2.1.min.js"></script>
        <script>
            $(function(){
                $("#delete").click(function(){
                    window.location.href = "delete";
                });
                $("#change").click(function(){
                    window.location.href = "change";
                })

            })
        </script>

</body>
</html>
