<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <jsp:include page="../../common/common.jsp"/>
    <jsp:include page="../../common/topMenus.jsp"/>

    <base href="<%=basePath%>">

    <title>测试接口</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">


</head>

<body>


<div class="container">
    <h3>系统测试接口</h3>
    <br>
    <br>

    <div class="row">
        <div class="col-md-3">
            <h4>测试多数据源</h4>
            <p><a href="system/business/test/testDynamicDataSource" target="_blank">测试多数据源</a></p>
            <p><a href="system/business/test/testDynamicDataSourceAddData" target="_blank">测试多数据源添加数据</a></p>
            <p><a href="XXX/undefined" target="_blank">测试404page</a></p>
        </div>
        <div class="col-md-3">
            <h4>测试二</h4>
            <p><a href="system/business/test/test" target="_blank">test</a></p>
            <br/>
            <br/>
        </div>
        <div class="col-md-3">
            <h4>测试三</h4>
            <p><a href="workOrder/todayTotal" target="_blank">test</a></p>
            <p><a href="workOrder/todaySources" target="_blank">test</a></p>
            <p><a href="workOrder/todayType" target="_blank">test</a></p>
            <p><a href="workOrder/monthTotal" target="_blank">test</a></p>
            <p><a href="workOrder/untreatedDetails" target="_blank">test</a></p>
            <br/>
        </div>
        <div class="col-md-3">
            <h4>测试四</h4>
            <p><a href="workOrder/test" target="_blank">test</a></p>
            <p><a href="workOrder/test" target="_blank">test</a></p>
            <p><a href="workOrder/test" target="_blank">test</a></p>
            <p><a href="workOrder/test" target="_blank">test</a></p>
            <p><a href="workOrder/test" target="_blank">test</a></p>
            <br/>
        </div>
    </div>

    <hr>
</body>

</html>
