<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <jsp:include page="../include/top.jsp"/>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="chance_my"/>
    </jsp:include>

    <!-- =============================================== -->
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的销售机会</h3>

                    <div class="box-tools pull-right">
                        <a href="/chance/${account.userId}/my/add" type="button" class="btn btn-box-tool">
                            <i class="fa fa-plus"></i> 添加机会
                        </a>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <td>机会名称</td>
                            <td>关联客户</td>
                            <td>机会价值</td>
                            <td>当前进度</td>
                            <td>最后跟进时间</td>
                            <td>#</td>
                        </tr>
                        </thead>
                        <c:forEach items="${saleChanceList}" var="saleChance">
                            <tbody class="chance_info" val="${saleChance.id}">
                            <td>${saleChance.name}</td>
                            <td>${account.userName}</td>
                            <td>${saleChance.worth}</td>
                            <td>${saleChance.progress}</td>
                            <td><fmt:formatDate value="${saleChance.lastTime}"/></td>
                            <td>#</td>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
                <c:if test="${empty saleChanceList}">
                    <div class="alert alert-danger" role="alert">
                        <div style="padding-left: 500px">暂无数据　<a href="/chance/${account.userId}/my/add" class="alert-link" style="color: whitesmoke">点击添加机会!!</a></div>
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
        layer.msg("${message}")
        </c:if>

        $(".chance_info").click(function(){
            var saleChanceId = $(this).attr("val");
            window.location.href = "/chance/"+saleChanceId+"/info";
        })
    })()
</script>

</body>
</html>
