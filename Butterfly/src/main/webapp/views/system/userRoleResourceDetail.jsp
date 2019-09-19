<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <jsp:include page="../common/common.jsp"/>
    <jsp:include page="../common/topMenus.jsp"/>
    <jsp:include page="../common/common_table_plugins.jsp"/>

    <base href="<%=basePath%>">

    <title>权限概览</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <style type="text/css">

    </style>
</head>

<body>
<div class="container">
    <h3>角色信息</h3>
    <div id="toolbar">
        <div class="form-inline" role="form">
            <div class="form-group">
                <span></span>
                <input id="keyWords" class="form-control" type="text" placeholder="输入关键字查询..."
                       onkeydown="searchKeyDown();">
            </div>
            <button id="ok" class="btn btn-default" onclick="refresh();">查询</button>
            <button id="ok" class="btn btn-default" onclick="updateStatus();">禁/启用</button>
            <button id="ok" class="btn btn-default" onclick="deleteUser();">删除</button>
            <button id="ok" class="btn btn-default" onclick="details();">编辑</button>
            <button id="ok" class="btn btn-default" onclick="add();">添加</button>
            <button id="ok" class="btn btn-default" onclick="addRole();">分配用户</button>
            <button id="ok" class="btn btn-default" onclick="addResource();">分配资源</button>
        </div>
    </div>
    <table id="table"
           data-toggle="table"
           data-height="460"
           data-toolbar="#toolbar"
           data-show-refresh="true"
           data-show-toggle="true"
    <%--data-show-columns="true"--%>
           data-query-params="queryParams"
           data-side-pagination="server"
           data-pagination="true"
           data-page-list="[10, 20, 50, 100]"
           data-url="system/roleInfo/getRoleList">
        <thead>
        <tr>
            <th data-field="state" data-checkbox="true"></th>
            <th data-field="roleId" width="100" data-visible="false">roleId</th>
            <th data-field="roleName">角色名称</th>
            <th data-field="status" data-formatter="statusFormatter">状态</th>
            <th data-field="modifyDate" data-formatter="tableDateFormatter">更新日期</th>
            <th data-field="createDate" data-formatter="tableDateFormatter">录入日期</th>
        </tr>
        </thead>
    </table>
</div>
</body>

<!-- JS START -->
<script type="text/javascript" src="<%=basePath%>js/system/userRoleResourceDetail.js"></script>
<!-- JS END -->

</html>
