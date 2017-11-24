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
        <div id="funnel" style="width: 800px;height:500px;"></div>
        <div id="customer" style="width: 800px;height:500px;"></div>
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <jsp:include page="include/footer.jsp"/>

</div>
<!-- ./wrapper -->
<jsp:include page="include/script.jsp"/>
<script src="/static/plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
    (function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init($("#funnel")[0]);
        // 指定图表的配置项和数据
        option = {
            title: {
                text: '业务进程漏斗图',
                subtext: ''
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c}%"
            },
            toolbox: {
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            legend: {
                data: [
                    <c:forEach items="${chanceList}" var="map">
                        '${map.content}',
                    </c:forEach>
                ]
            },
            calculable: true,
            series: [
                {
                    name:'业务进程漏斗图',
                    type:'funnel',
                    left: '10%',
                    top: 60,
                    //x2: 80,
                    bottom: 60,
                    width: '80%',
                    // height: {totalHeight} - y - y2,
                    min: 0,
                    max: 12,
                    minSize: '0%',
                    maxSize: '100%',
                    sort: 'descending',
                    gap: 2,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        },
                        emphasis: {
                            textStyle: {
                                fontSize: 20
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            length: 10,
                            lineStyle: {
                                width: 1,
                                type: 'solid'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    },
                    data: [
                        <c:forEach items="${chanceList}" var="map">
                        {value:${map.count},name:'${map.content}'},
                        </c:forEach>
                    ]
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
//----------------------------------------------------------------------------------------
        var customer = echarts.init($("#customer")[0]);
        data = [
            <c:forEach items="${customerList}" var="map">
                ["${map.month}",${map.count}],
            </c:forEach>
        ];

        var dateList = data.map(function (item) {
            return item[0];
        });
        var valueList = data.map(function (item) {
            return item[1];
        });

        option = {

            // Make gradient line here
            visualMap: [{
                show: false,
                type: 'continuous',
                seriesIndex: 0,
                min: 0,
                max: 400
            }],


            title: [{
                left: 'center',
                text: '月新增客户折线图'
            }],
            tooltip: {
                trigger: 'axis'
            },
            xAxis: [{
                data: dateList
            }],
            yAxis: [{
                splitLine: {show: false}
            }],
            grid: [{
                bottom: '60%'
            }],
            series: [{
                type: 'line',
                showSymbol: false,
                data: valueList
            }]
        };
        customer.setOption(option);
    })()
</script>
</body>
</html>
