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
    <link rel="stylesheet" href="/static/plugins/tree/css/metroStyle/metroStyle.css">
    <link rel="stylesheet" href="/static/plugins/datatables/jquery.dataTables.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <jsp:include page="../include/top.jsp"/>
    <!-- 左侧菜单栏 -->

    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="employeeList"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-2">
                    <div class="box">
                        <div class="box-body">
                            <button class="btn btn-default" id="addDept">添加部门</button>
                            <input type="hidden" id="deptId">
                            <ul id="ztree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-10">
                    <!-- Default box -->
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">员工管理</h3>
                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool"  id="addEmployee" title="Collapse">
                                    <i class="fa fa-plus"></i> 添加员工</button>
                            </div>
                        </div>
                        <div class="box-body">
                            <table class="table" id="dataTable">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>姓名</th>
                                    <th>部门</th>
                                    <th>手机</th>
                                    <th>#</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- /.box -->
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <div class="modal fade" id="addEmployeeModel" >
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">添加账号</h4>
                </div>
                <div class="modal-body">
                    <form id="addEmployeeForm">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" class="form-control" name="userName">
                        </div>
                        <div class="form-group">
                            <label>手机号码</label>
                            <input type="text" class="form-control" name="mobile">
                        </div>
                        <div class="form-group">
                            <label>密码(默认000000)</label>
                            <input type="password" class="form-control" name="password" value="000000">
                        </div>
                        <div class="form-group">
                            <label>所属部门</label>
                            <div id="checkboxList"></div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="addEmployeeFormBtn">保存</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部 -->
    <jsp:include page="../include/footer.jsp"/>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<jsp:include page="../include/script.jsp"/>
<script src="/static/plugins/tree/js/jquery.ztree.all.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/datatables/jquery.dataTables.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>
    $(function(){

        //删除账号(事件委托)
        $(document).delegate(".delEmployeeLink","click",function () {
            var accountId = $(this).attr("rel");
            layer.confirm("确定要删除吗",function (index) {
                layer.close(index);
                $.post("/employee/"+accountId+"/delete").done(function (data) {
                    if(data.state == 'success') {
                        dataTable.ajax.reload();
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.msg("系统异常");
                });
            });
        });
//        添加部门
        $(document).delegate("#addDept","click",function(){
            var pid = $("#deptId").val();
            //解决当不点击zTree的时候获取不到pid的值
            if(!pid){
                pid = 1;
            }
            layer.prompt({title:"请输入部门名称"},function(text,index){
                layer.close(index);

                $.post("/employee/dept/new/"+pid,{"deptName":text}).done(function(data) {
                    if(data.state == "success") {
                        layer.msg("添加部门成功");
                        //刷新树
                        var treeObj = $.fn.zTree.getZTreeObj("ztree");
                        treeObj.reAsyncChildNodes(null, "refresh");
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.msg("服务器忙，请稍后");
                });
            });
        });



        $("#addEmployee").click(function(){
            $("#checkboxList").html("");
            $.get("/employee/dept.json").done(function (data) {
                for(var i =0;i<data.length;i++) {
                    var obj = data[i];
                    if(obj.deptId != 1) {
                        var html = '<label class="checkbox-inline"><input type="checkbox" name="deptId" value="'+obj.deptId+'">'+obj.deptName+'</label>';
                        $("#checkboxList").append(html);
                    }
                }
                $("#addEmployeeModel").modal({
                    show:true,
                    backdrop:'static'
                });
            }).error(function () {
                layer.msg("获取部门数据失败");
            });
        });
        //验证添加账号表单
        $("#addEmployeeFormBtn").click(function () {
            $("#addEmployeeForm").submit();
        });
        $("#addEmployeeForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                userName:{
                    required:true
                },
                mobile:{
                    required:true
                },
                password:{
                    required:true
                },
                deptId:{
                    required:true
                }
            },
            messages:{
                userName:{
                    required:"请输入账号"
                },
                mobile:{
                    required:"请输入手机号码"
                },
                password:{
                    required:"请输入密码"
                },
                deptId:{
                    required:"请选择部门"
                }
            },
            submitHandler:function(){
                $.get("/employee/new",$("#addEmployeeForm").serialize()).done(function (data) {
                    if(data.state == 'success') {
                        $("#addEmployeeModel").modal('hide');
                        dataTable.ajax.reload();
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.msg("服务器忙，请稍后");
                });
            }
        });

        var dataTable = $("#dataTable").DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url" : "/employee/load.json",
                "data" : function(data){
                    data.deptId = $("#deptId").val();
                }
            },

            "lengthChange": false,
            "pageLength": 25,
            "columns":[
                {"data":"userId"},
                {"data":"userName"},
                {"data":function(row){
                    var deptArray = row.deptList;
                    var str = "";
                    for(var i = 0;i < deptArray.length;i++) {
                        str += deptArray[i].deptName + " ";
                    }
                    return str;
                }},
                {"data":"userMobile"},
                {"data":function(row){
                    return "<a href='javascript:;' rel='"+row.userId+"' class='delEmployeeLink'>删除</a>";
                }}
            ],
            "columnDefs": [
                {
                    "targets": [2,3,4],
                    "orderable": false
                },
                {
                    "targets": [0],
                    "visible": false
                }
            ],
            "language":{
                "search":"账号:",
                "info": "显示从 _START_ 到 _END_ 条数据，共 _TOTAL_ 条",
                "infoEmpty":"没有任何数据",
                "emptyTable":"暂无数据",
                "processing":"加载中...",
                "paginate": {
                    "first":      "首页",
                    "last":       "末页",
                    "next":       "上一页",
                    "previous":   "下一页"
                }
            }
        });


//************ zTree  ***************************
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey:"deptId",
                    pIdKey:"deptPid"
                },
                key:{
                    name:"deptName"
                }
            },
            async:{
                enable:true,
                url:"/employee/dept.json",
                type:"get",
                dataFilter:ajaxDataFilter
            },
            callback:{
                onClick:function(event,treeId,treeNode,clickFlag){
//                    alert(treeNode.deptId + treeNode.deptName + treeNode.deptPid);
                    $("#deptId").val(treeNode.deptId);
                    dataTable.ajax.reload();
                }
            }
        };
        function ajaxDataFilter(treeId, parentNode, responseData) {
            if (responseData) {
                for(var i =0; i < responseData.length; i++) {
                    if(responseData[i].deptId == 1) {
                        responseData[i].open = true;
                        break;
                    }
                }
            }
            return responseData;
        }
        $.fn.zTree.init($("#ztree"), setting);

    });
</script>
</body>
</html>
