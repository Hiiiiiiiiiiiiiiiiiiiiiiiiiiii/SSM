<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <jsp:param name="menu" value="customer"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">新增客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="customerForm" action="/customer/add" method="post" >
                        <input type="text" hidden name="accountId" value="${account.userId}">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" class="form-control" name="customerName">
                        </div>
                        <div class="form-group">
                            <label>性别</label>
                            <select class="form-control" name="sex">
                                <option value="先生">先生</option>
                                <option value="女士">女士</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>职位</label>
                            <input type="text" class="form-control" name="jobTitle">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" class="form-control" name="customPhone">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" class="form-control" name="address">
                        </div>
                        <div class="form-group">
                            <label>所属行业</label>
                            <select class="form-control" name="trade">
                                <c:forEach items="${tradeList}" var="trade">
                                    <option value="${trade}">${trade}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>客户来源</label>
                            <select name="source" class="form-control">
                                <c:forEach items="${sourceList}" var="source">
                                    <option value="${source}">${source}</option>
                                </c:forEach>
                                <option value="其他">其他</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>级别</label>
                            <select class="form-control" name="level">
                                <option value="★">★</option>
                                <option value="★★">★★</option>
                                <option value="★★★">★★★</option>
                                <option value="★★★★">★★★★</option>
                                <option value="★★★★★">★★★★★</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" class="form-control" name="mark">
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="save">保存</button>
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
<jsp:include page="../include/script.jsp"/>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>
    (function(){
        $("#save").click(function () {
            $("#customerForm").submit();
        });
        $("#customerForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                customerName:{
                    required:true
                },
                customPhone:{
                    required:true
                }
            },
            messages:{
                customerName:{
                    required:"请输入姓名"
                },
                customPhone:{
                    required:"请输入联系方式"
                }
            }
        });

    })()

</script>

</body>
</html>
