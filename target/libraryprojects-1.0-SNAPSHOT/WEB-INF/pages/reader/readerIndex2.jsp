<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>读者管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/public.css" media="all">
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">


        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="update">修改密码</a>
        </script>

    </div>
</div>

<script src="<%=basePath%>lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        // 初始化表格，设定只显示一页一条数据
        var tableIns = table.render({
            elem: '#currentTableId',
            url: '<%=basePath%>queryReaderAll2',
            toolbar: '#toolbarDemo',
            cols: [
                [
                    { type: "checkbox", width: 50 },
                    { field: 'cardnumber', width: 80, title: '卡号' },
                    { field: 'username', width: 80, title: '用户名' },
                    { field: 'name', width: 80, title: '真实姓名' },
                    { field: 'sex', width: 80, title: '性别' },
                    { field: 'tel', width: 80, title: '电话' },
                    { field: 'number', width: 80, title: '可借数量' },
                    { templet: "<div>{{layui.util.toDateString(d.creatDate,'yyyy-MM-dd HH:mm:ss')}}</div>", width: 150, title: '办卡时间' },
                    { title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center" }
                ]
            ],
            limits: [1],
            limit: 1,
            page: {
                curr: 1  // 默认显示第一页
            },
            skin: 'line',
            id: 'testReload'
        });

        // 重新加载数据
        function reloadTableData() {
            tableIns.reload({
                url: '<%=basePath%>queryReaderAll2',
                where: {},  // 可以添加额外的参数
                page: {
                    curr: 1  // 默认显示第一页
                }
            });
        }

        /**
         * tool操作栏监听事件
         */
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {  // 监听添加操作
                var index = layer.open({
                    title: '修改密码',
                    type: 2,
                    shade: 0.2,
                    maxmin: true,
                    shadeClose: true,
                    area: ['100%', '100%'],
                    content: '<%=basePath%>queryReaderInfoById?id=' + data.id,
                });
                $(window).on("resize", function () {
                    layer.full(index);
                });
            } else if (obj.event === 'update') {  // 监听添加操作
                var index = layer.open({
                    title: '修改密码',
                    type: 2,
                    shade: 0.2,
                    maxmin: true,
                    shadeClose: true,
                    area: ['60%', '60%'],
                    content: '<%=basePath%>queryReaderById?id=' + data.id,
                });
                $(window).on("resize", function () {
                    layer.full(index);
                });
            }
        });

        // 页面加载时自动加载数据
        reloadTableData();
    });
</script>


</body>
</html>
