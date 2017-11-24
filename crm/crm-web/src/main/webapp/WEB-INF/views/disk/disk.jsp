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
    <link rel="stylesheet" href="/static/plugins/webuploader/webuploader.css">
    <link rel="stylesheet" href="/static/plugins/layer/mobile/need/layer.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <jsp:include page="../include/top.jsp"/>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="dropbox"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <section class="content" style="background-color: #ecf0f5;width: 1200px;">
            <a href="javascript:;" id="returnBtn" class="btn btn-danger"style="padding:8px;margin-left: 10px"><i class="fa fa-arrow-left">　</i>返回上一级</a>
            <div class="box-tools pull-right">

                <button id="createDir" class="btn btn-success" style="display: inline-block;margin-top: -34px;padding: 9px" ><i class="fa fa-folder">　</i>新建文件夹</button>
                <div id="picker"  style="display: inline-block"><i class="fa fa-file-excel-o"></i> 上传文件</div>
            </div>
            <section id="content" style="background-color: #ecf0f5;width: 1240px;margin-left: -20px;margin-top: 10px">
                <c:forEach items="${diskList}" var="disk">
                    <%--文件夹--%>
                    <c:if test="${disk.type == 'dir'}">
                        <a href="/disk/company/${disk.id}" style="display: inline-block">
                            <div style="margin-right: 30px">
                                <i class="fa fa-folder fa-3x" aria-hidden="true" style="color: #9e9e9e"></i>
                                <div style="margin-left: -8px;color: #0f0f0f">${disk.name}</div>
                            </div>
                        </a>
                    </c:if>
                    <%--文件--%>
                    <c:if test="${disk.type != 'dir'}">
                        <a href="javascript:;" class="file" id="${disk.id}" rel="${disk.newTime}" style="display: inline-block">
                            <div style="margin-right: 30px">
                                <td><i class="fa fa-file fa-3x" aria-hidden="true" style="color: #b2dba1"></i></td>
                                <div style="margin-left: -8px;color: #0f0f0f;">${disk.name}</div>
                            </div>
                        </a>
                    </c:if>
                </c:forEach>
            </section>
        </section>
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <jsp:include page="../include/footer.jsp"/>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<jsp:include page="../include/script.jsp"/>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/webuploader/webuploader.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/artTemplate/art-template.js"></script>
<script id="template" type="text/template">
    {{each diskList disk}}
        <%--文件夹--%>
        <?if(disk.type=="dir"){?>
            <a href="/disk/company/{{disk.id}}" style="display: inline-block">
                <div style="margin-right: 30px">
                    <i class="fa fa-folder fa-3x" aria-hidden="true" style="color: #9e9e9e"></i>
                    <div style="margin-left: -8px;color: #0f0f0f">{{disk.name}}</div>
                </div>
            </a>
        <?}else{?>

        <%--文件--%>
            <a href="javascript:;" class="file" id="{{disk.id}}" rel="{{disk.newTime}}" style="display: inline-block">
                <div style="margin-right: 30px">
                    <td><i class="fa fa-file fa-3x" aria-hidden="true" style="color: #b2dba1"></i></td>
                    <div style="margin-left: -8px;color: #0f0f0f">{{disk.name}}</div>
                </div>
            </a>
        <?}?>


     {{/each}}

</script>

<script>
    (function(){
        var uploader = WebUploader.create({
            pick: '#picker',
            swf: '/static/plugins/webuploader/Uploader.swf',
            server: 'http://upload.qiniup.com',
            auto: true,
            fileVal: 'file',
            formData:{
                'token':'${upToken}'
            },
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });




        $("#returnBtn").click(function(){
            history.back(-1);
        })
//        点击文件时间
        $(document).delegate(".file","click",function(){
            var id = $(this).attr("id");
            var createTime = $(this).attr("rel");
            layer.tips('创建时间:'+createTime+'</br><button class="btn btn-danger" id="delBtn" rel="'+id+'">删除</button><button class="btn btn-info" id="downBtn" rel="'+id+'">下载</button>', '#'+id);
        });
//        点击文件删除
        $(document).delegate("#delBtn","click",function(){
            var diskId = $(this).attr(rel);
            layer.confirm('您确定要删除？', {
                btn: ['确定','取消'] //按钮
            },function(){
                $.get("/disk/company/delete",{"diskId":diskId},function(data){
                    if(data.state=="success"){
                        layer.msg("删除文件成功!");
                    }
                })
            });
        });
//      新建文件夹
        $("#createDir").click(function(){
            var pid = ${pid};
            layer.prompt({title: '请输入新建文件夹名称', formType: 3}, function(pass, index){
                layer.close(index);
                $.get("/disk/company/create/${pid}/dir",{"folderName":pass},function(data){
                    $("#content").html("");
                    if(data.state=="success"){
                        layer.msg("新建文件夹成功!!");
//                      获取后端传过来的数据
                        var diskList = data.data;
//                        for(var i=0;i<diskList.length;i++){
//                            var time = diskList.updateTime;
//                            改变时间格式
//                            diskList.updateTime = moment(time).format("YYYY-MM-DD");
//                        }
//                      渲染模板
//                      根据模板要求将list放入对象数组中
                        var data = {"diskList":diskList};
//                      获取模板创建出的html
                        var html = template("template",data);
                        $(html).appendTo("#content");
                    }
                })

            })
        })
    })()
</script>
</body>
</html>
