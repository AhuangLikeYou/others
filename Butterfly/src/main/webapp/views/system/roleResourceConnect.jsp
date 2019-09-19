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
    <jsp:include page="../common/common_table_plugins.jsp"/>

    <base href="<%=basePath%>">

    <title></title>
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
    <input id="roleId" name="roleId" type="text" value="${roleId}" hidden="hidden" readonly="readonly">
    <div class="row">
        <div class="col-xs-6">
            <div id="toolbar">
                <div class="form-inline" role="form">
                    <div class="form-group">
                        <input id="keyWords" class="form-control" type="text" placeholder="输入关键字查询..."
                               onkeydown="searchKeyDown();">
                    </div>
                    <button class="btn btn-default" onclick="refresh();">查询</button>
                    <button class="btn btn-success" onclick="addChoose();">添加</button>
                </div>
            </div>
            <table id="table"
                   data-toggle="table"
                   data-height="440"
                   data-toolbar="#toolbar"
            <%--data-show-refresh="true"--%>
            <%--data-show-toggle="true"--%>
                   data-click-to-select="true"
                   data-query-params="queryParams"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-page-list="[10, 20, 50, 100]"
                   data-url="system/resourceInfo/getResourceList">
                <thead>
                <tr>
                    <th data-field="state" data-checkbox="true"></th>
                    <th data-field="resourceId" data-visible="false">resourceId</th>
                    <th data-field="resourceName">资源名称</th>
                    <th data-field="resourceCode">资源编码</th>
                    <th data-field="resourcePath">访问路径</th>
                </tr>
                </thead>
            </table>
        </div>

        <div class="col-xs-6">
            <div id="toolbar-choose">
                <div class="form-inline" role="form">
                    <button class="btn btn-warning" onclick="resetChoose();">清空</button>
                </div>
            </div>
            <table id="table-choose"
                   data-toggle="table"
                   data-height="385"
                   data-toolbar="#toolbar-choose"
                   data-query-params="queryParams">
                <thead>
                <tr>
                    <th data-field="resourceId" data-visible="false">resourceId</th>
                    <th data-field="resourceName">资源名称</th>
                    <th data-field="resourceCode">资源编码</th>
                </tr>
                </thead>
            </table>
        </div>

    </div>

    <span></span>
    <span></span>

    <div class="form-group" align="center">
        <div class="btn-group">
            <button type="button" class="btn btn-success" onclick="connect();">确认</button>
        </div>
        <div class="btn-group">
            <button type="button" class="btn btn-warning" onclick="cancelConnect();">取消</button>
        </div>
    </div>

</div>
</body>

<!-- JS START -->
<script type="text/javascript" src="<%=basePath%>js/system/roleResourceConnect.js"></script>
<!-- JS END -->

</html>
