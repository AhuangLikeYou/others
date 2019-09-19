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

    <title>系统配置</title>
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
    <h3>系统配置</h3>
    <div id="toolbar">
        <div class="form-inline" role="form">
            <div class="form-group" id="searchDiv">
                <span></span>
                <input id="type" class="form-control" style="width: 100px;" value="${type}" placeholder="类型"
                       onkeydown="searchKeyDown();">
                <input id="key" class="form-control" style="width: 100px;" value="${key}" placeholder="键"
                       onkeydown="searchKeyDown();">
                <input id="keyWords" class="form-control" style="" value="${keyWords}" placeholder="值"
                       onkeydown="searchKeyDown();">
            </div>
            <button id="resetSearch" class="btn btn-default" onclick="resetSearch();">重置</button>
            <button id="ok" class="btn btn-default" onclick="refresh();">查询</button>
            <button id="ok" class="btn btn-default" onclick="deleteConfig();">删除</button>
            <button id="ok" class="btn btn-default" onclick="configDetails();">编辑</button>
            <button id="ok" class="btn btn-success" onclick="addConfig();">添加</button>

        </div>
    </div>
    <table id="table"
           data-toggle="table"
           data-height="460"
           data-toolbar="#toolbar"
           data-show-refresh="true"
           data-show-toggle="true"
    <%--data-show-columns="true"--%>
           data-click-to-select="true"
           data-query-params="queryParams"
           data-side-pagination="server"
           data-pagination="true"
           data-page-list="[10, 20, 50, 100]"
           data-url="system/sysConfig/getConfigList">
        <thead>
        <tr>
            <th data-field="state" data-checkbox="true"></th>
            <th data-field="configId" width="100" data-visible="false">configId</th>
            <th data-field="configType">类型</th>
            <th data-field="configKey">键</th>
            <th data-field="configValue">值</th>
            <th data-field="iscache" data-formatter="statusFormatter">是否缓存</th>
            <th data-field="remarks" data-formatter="statusFormatter">备注</th>
            <th data-field="modifyDate" data-formatter="tableDateFormatter">更新日期</th>
            <th data-field="createDate" data-formatter="tableDateFormatter">录入日期</th>
        </tr>
        </thead>
    </table>
</div>
</body>

<!-- JS START -->
<script type="text/javascript" src="<%=basePath%>js/system/sysConfig.js"></script>
<!-- JS END -->

</html>
