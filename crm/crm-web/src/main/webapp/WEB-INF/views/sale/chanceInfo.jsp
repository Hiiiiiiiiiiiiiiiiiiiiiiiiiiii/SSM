<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-销售机会详情</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="../include/css.jsp"/>
    <![endif]-->
    <style>
        .td_title {
            font-weight: bold;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
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
        <jsp:param name="menu" value="chance"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">销售机会基本资料</h3>
                    <div class="box-tools">
                        <a href="/chance/${saleChance.accountId}/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/chance/my/${saleChance.id}/delete/${saleChance.accountId}" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</a>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">机会名称</td>
                            <td>${saleChance.name}</td>
                            <td class="td_title">价值</td>
                            <td><fmt:formatNumber value="${saleChance.worth}"/> </td>
                            <td class="td_title">当前进度</td>
                            <td>${saleChance.progress}<button class="btn btn-xs btn-success" id="showChangeProgressModalBtn"><i class="fa fa-pencil"></i></button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                        <fmt:formatDate value="${saleChance.createTime}"/>
                    </span>
                </div>
            </div>

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">关联客户资料</h3>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${saleChance.customer.customerName}</td>
                            <td class="td_title">职位</td>
                            <td>${saleChance.customer.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${saleChance.customer.customPhone}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${saleChance.customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${saleChance.customer.source}</td>
                            <td class="td_title">级别</td>
                            <td class="star">${saleChance.customer.level}</td>
                        </tr>

                        <tr>
                            <td class="td_title">地址</td>
                            <td colspan="5">${saleChance.customer.address}</td>
                        </tr>


                        <tr>
                            <td class="td_title">备注</td>
                            <td colspan="5">${saleChance.customer.mark}</td>
                        </tr>

                    </table>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <h4>跟进记录
                        <small><button id="showRecordModalBtn" class="btn btn-success btn-xs"><i class="fa fa-plus"></i></button></small>
                    </h4>
                    <ul class="timeline">
                        <c:choose>
                            <c:when test="${empty saleChanceRecordList}">
                                <li>
                                    <!-- timeline icon -->
                                    <i class="fa fa-circle-o bg-red"></i>
                                    <div class="timeline-item">
                                        <div class="timeline-body">
                                            暂无跟进记录
                                        </div>
                                    </div>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${saleChanceRecordList}" var="saleChanceRecord">
                                    <c:choose>
                                        <c:when test="${saleChanceRecord.content eq '将当前进度修改为 [成交]'}">
                                            <li>
                                                <!-- timeline icon -->
                                                <i class="fa fa-check bg-green"></i>
                                                <div class="timeline-item">
                                                    <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${saleChanceRecord.createTime}"/></span>
                                                    <div class="timeline-body">
                                                        ${saleChanceRecord.content}
                                                    </div>
                                                </div>
                                            </li>
                                        </c:when>
                                        <c:when test="${saleChanceRecord eq '将当前进度修改为 [暂时搁置]'}">
                                            <li>
                                                <!-- timeline icon -->
                                                <i class="fa fa-close bg-red"></i>
                                                <div class="timeline-item">
                                                    <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${saleChanceRecord.createTime}"/></span>
                                                    <div class="timeline-body">
                                                        ${saleChanceRecord.content}
                                                    </div>
                                                </div>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <!-- timeline icon -->
                                                <i class="fa fa-info bg-blue"></i>
                                                <div class="timeline-item">
                                                    <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${saleChanceRecord.createTime}"/></span>
                                                    <div class="timeline-body">
                                                        ${saleChanceRecord.content}
                                                    </div>
                                                </div>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="recordModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">添加跟进记录</h4>
                        </div>
                        <div class="modal-body">
                            <form action="/chance/my/${saleChance.id}/new/record" id="recordForm" method="post">
                                <textarea id="recordContent"  class="form-control" name="content"></textarea>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="saveRecordBtn">保存</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->


            <div class="modal fade" id="changeProgressModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">更新当前进度</h4>
                        </div>
                        <div class="modal-body">
                            <form method="post" action="/chance/my/${saleChance.id}/progress/update" id="updateProgressForm">
                                <input type="hidden" name="saleId" value="${saleChance.id}">
                                <select name="progress" class="form-control">
                                    <c:forEach items="${progressList}" var="progress">
                                        <option value="${progress}">${progress}</option>
                                    </c:forEach>
                                </select>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="saveProgress">更新</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->

        </section>

        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <jsp:include page="../include/footer.jsp"/>
</div>
    <jsp:include page="../include/script.jsp"/>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    (function(){
        $("#showRecordModalBtn").click(function(){
            layer.prompt({title: '添加新的跟进记录。', formType: 2}, function(pass, index){
                window.location.href = "/chance/my/${saleChance.id}/new/record/"+pass;
                layer.close(index);
            });
        });
        $("#showChangeProgressModalBtn").click(function () {
            $("#changeProgressModal").modal({
                show : true,
                backdrop:'static'
            });
        });
        $("#saveProgress").click(function () {
            $("#updateProgressForm").submit();
        });
    })()
</script>
<!-- ./wrapper -->
</body>
</html>

