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
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <jsp:include page="../include/top.jsp"/>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="todo"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">计划任务</h3>

                    <div class="box-tools pull-right">
                        <button class="btn btn-success btn-sm" id="addBtn"><i class="fa fa-plus"></i> 新增任务</button>
                        <button class="btn btn-primary btn-sm" id="show"><i class="fa fa-eye"></i> 显示所有任务</button>
                    </div>
                </div>
                <div class="box-body">

                    <ul class="todo-list">
                        <c:forEach items="${taskList}" var="task">

                                <li ${task.done eq 1 ? 'hidden':''} class="${task.done eq 1 ? 'done':''}">
                                    <input type="checkbox" rel="${task.id}" class="checked" ${task.done eq 1 ? 'checked':''}>
                                    <span class="text">${task.title}</span>
                                    <c:if test="${not empty task.custId}">
                                        <a href=""><i class="fa fa-user-o"></i>${task.customer.customerName}</a>
                                    </c:if>
                                    <c:if test="${not empty task.saleId}">
                                        <a href=""><i class="fa fa-money"></i>${task.saleChance.name}</a>
                                    </c:if>

                                    <small class="label ${task.overTime ? 'label-danger' : 'label-success'}"><i class="fa fa-clock-o"></i><fmt:formatDate value="${task.finishTime}"/></small>
                                    <div class="tools">
                                        <i class="fa fa-edit ${task.done eq 1 ? 'hidden':''}"rel="${task.id}"></i>
                                        <a href="javascript:;" class="delete" rel="${task.id}"><i class="fa fa-trash-o"></i></a>
                                    </div>
                                </li>
                        </c:forEach>
                    </ul>
                </div>
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

<!-- jQuery 2.2.3 -->
<jsp:include page="../include/script.jsp"/>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    (function(){
        $("#addBtn").click(function(){
            var accountId = ${accountId};
            window.location.href = "/todo/"+accountId+"/add";
        });
        $(".checked").click(function(){
            var taskId = $(this).attr("rel");
            var state = $(this)[0].checked;
            window.location.href = "/todo/"+taskId+"/change/"+${accountId};
        });
        $("#show").click(function(){
            $(".done").show();
            $("#hidden").show();
        });
        $(".delete").click(function(){
            var taskId = $(this).attr("rel");
            layer.confirm("确定要删除吗",function () {
                window.location.href = "/todo/"+taskId+"/delete/"+${accountId};
            });
        });
    })()
</script>
</body>
</html>
