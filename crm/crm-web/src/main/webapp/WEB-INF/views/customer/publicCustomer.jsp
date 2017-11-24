<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="../include/css.jsp"/>
    <style>
        .name-avatar {
            display: inline-block;
            width: 50px;
            height: 50px;
            background-color: #ccc;
            border-radius: 50%;
            text-align: center;
            line-height: 50px;
            font-size: 24px;
            color: #FFF;
        }
        .table>tbody>tr:hover {
            cursor: pointer;
        }
        .table>tbody>tr>td {
            vertical-align: middle;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>

    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <jsp:include page="../include/top.jsp"/>


    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="customer_public"/>
    </jsp:include>

    <!-- =============================================== -->
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">公海客户</h3>
                    <div class="box-tools pull-right">
                        <button class="btn btn-primary btn-sm"><i class="fa fa-file-excel-o"></i> 导出Excel</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>职位</th>
                            <th>跟进时间</th>
                            <th>级别</th>
                            <th>联系方式</th>
                        </tr>
                        <c:forEach items="${customerList}" var="customer">
                            <tr class="customer" val="${customer.id}">
                                <td><span class="name-avatar" style="<c:if test="${customer.sex eq '女士'}"> background-color: pink</c:if>">${fn:substring(customer.customerName, 0, 1)}</span></td>
                                <td>${customer.customerName}</td>
                                <td>${customer.sex}</td>
                                <td>${customer.jobTitle}</td>
                                <td><fmt:formatDate value="${customer.lastChatTime}"/></td>
                                <td class="star">${customer.level}</td>
                                <td><i class="fa fa-phone"></i> ${customer.customPhone} <br></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:if test="${empty customerList}">
                    <div class="alert alert-danger" role="alert">
                        <div style="padding-left: 500px">暂无客户数据</div>
                    </div>
                </c:if>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <jsp:include page="../include/footer.jsp"/>

</div>
<!-- ./wrapper -->

<jsp:include page="../include/script.jsp"/>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    (function(){
        <c:if test="${not empty message}">
        layer.msg("${message}");
        </c:if>
        $(".customer").click(function(){
            var customerId = $(this).attr("val")
            window.location.href = "/customer/"+customerId+"/customerMessage";
        })
    })()
</script>
</body>
</html>
