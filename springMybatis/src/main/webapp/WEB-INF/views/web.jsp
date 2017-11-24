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
                <c:if test="${not empty message}">
                    <div class="bg-primary" style="margin-top: 20px"><h3>${message}</h3></div>
                </c:if>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>书籍名称</th>
                        <th>书籍ID</th>
                        <th>作者</th>
                        <th>类型</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${pageInfo.list}" var="book">
                        <tr>
                            <td><a href="javascript:;" class="book" rel="${book.bookId}">${book.bookName}</a></td>
                            <td>${book.bookId}</td>
                            <td>${book.bookAuthor}</td>
                            <td>${book.bookType}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <ul id="pagination-demo" class="pagination-lg"></ul>
            </div>
        </div>
    </div>
    <script src="/static/bootstrap/js/jquery-3.2.1.min.js"></script>
    <script src="/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="/static/dist/js/jquery.twbsPagination.min.js"></script>
    <script>
    $(function(){
        //分页
        $('#pagination-demo').twbsPagination({
            totalPages: ${pageInfo.pages},
            visiblePages: 3,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?p={{number}}"
        });
        $(".book").click(function(){
            var bookId = $(this).attr("rel");
            if(bookId!=null){
                window.location.href = "list/"+bookId+"/detail"
            }
        })
    });
</script>
</body>
</html>