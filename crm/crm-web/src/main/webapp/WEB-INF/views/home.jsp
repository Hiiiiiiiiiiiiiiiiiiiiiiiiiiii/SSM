<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!--引入css-->
    <jsp:include page="include/css.jsp"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
   <jsp:include page="include/top.jsp"/>
    <!-- =============================================== -->
<%--class="active"--%>
    <!-- 左侧菜单栏 -->
    <jsp:include page="include/left.jsp">
        <jsp:param name="menu" value="home"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <jsp:include page="include/footer.jsp"/>

</div>
<!-- ./wrapper -->
<jsp:include page="include/script.jsp"/>
</body>
</html>
