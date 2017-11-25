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
        .td_title {
            font-weight: bold;
        }
    </style>

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

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <button class="btn btn-success btn-sm" id="takeBtn"><i class="fa fa-recycle"></i> 转到我的客户</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.customerName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.customPhone}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td>${customer.level}</td>
                        </tr>
                        <tr>
                            <td class="td_title">地址</td>
                            <td colspan="5">${customer.address}</td>
                        </tr>
                        <tr>
                            <td class="td_title">备注</td>
                            <td colspan="5">${customer.mark}</td>
                        </tr>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">创建日期：<fmt:formatDate value="${customer.createTime}"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<fmt:formatDate value="${customer.updateTime}"/></span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录</h3>
                            <c:if test="${empty saleChanceList}">
                                <div class="bg-danger"style="padding-left: 380px;color: red">暂无跟进记录</div>
                            </c:if>
                            <c:forEach items="${saleChanceList}" var="saleChance">
                                <div class="bg-success"><a href="/chance/${saleChance.id}/info" style="padding-left: 380px">${saleChance.name}</a></div>
                            </c:forEach>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">

                            <div>
                                <h3 class="box-title">日程安排</h3>
                                <button id="addTodo" class="btn btn-success btn-xs" style="margin-top: -8px"><i class="fa fa-plus"></i></button>
                            </div>
                            <c:if test="${empty taskList}">
                                <div class="bg-danger"style="padding-left: 160px;color: red">暂无日程安排</div>
                            </c:if>
                            <c:forEach items="${taskList}" var="task">
                                <div class="bg-success"><a href="/todo/${accountList.get(0).userId}" style="margin-left: 160px">${task.title}</a></div>
                            </c:forEach>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
                <div class="modal fade" id="chooseUserModel">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">请选择转入账号</h4>
                            </div>
                            <div class="modal-body">
                                <select id="userSelect" class="form-control">
                                    <c:forEach items="${accountList}" var="account">
                                        <c:if test="${account.userId != customer.accountId}">
                                            <option value="${account.userId}">${account.userName} (${account.userPassword})</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                <button type="button" class="btn btn-primary" id="saveTranBtn">确定</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
            </div>

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
        var customerId = ${customer.id};
        $("#give_other").click(function(){
            $("#chooseUserModel").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#saveTranBtn").click(function () {
            var toAccountId = $("#userSelect").val();
            var toAccountName = $("#userSelect option:selected").text();
            layer.confirm("确定要将客户转交给"+toAccountName+"吗",function (index) {
                layer.close(index);
                window.location.href = "/customer/to/"+customerId+"/other/"+toAccountId;
            });
        });
        //将客户放入公海
        $("#publicBtn").click(function () {
            layer.confirm("确定要将客户放入公海吗?",function (index) {
                layer.close(index);
                window.location.href = "/customer/my/"+customerId+"/public";
            });
        });
        $("#addTodo").click(function(){
            var customerId = ${customer.id};
            window.location.href = "/todo/add?customerId="+customerId;
        });
        $("#takeBtn").click(function(){
            window.location.href = "/customer/take/${customer.id}";
        })
    })()

</script>
</body>
</html>
