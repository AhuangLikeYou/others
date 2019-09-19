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
    <jsp:include page="../common/common.jsp"/>
    <jsp:include page="../common/topMenus.jsp"/>

    <base href="<%=basePath%>">

    <title>添加用户</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <style type="text/css">
        .mainForm {
            margin-left: 200px;
            max-width: 50%;
        }

    </style>
</head>

<body>
<form action="" method="post" id="form" class="form-horizontal mainForm">
    　
    <div class="form-group">
        <label for="userName" class="col-sm-2 control-label">登录名</label>
        <div class="col-sm-10">
            <input id="userName" name="userName" type="text" class="form-control" placeholder=""/>
        </div>
    </div>
    <div class="form-group">
        <label for="realName" class="col-sm-2 control-label">中文名</label>
        <div class="col-sm-10">
            <input id="realName" name="realName" type="text" class="form-control" placeholder=""/>
        </div>
    </div>

    <div class="form-group">
        <label for="userPwd" class="col-sm-2 control-label">密码</label>
        <div class="col-sm-10">
            <input id="userPwd" name="userPwd" type="text" class="form-control" placeholder=""/>
        </div>
    </div>

    <div class="form-group">
        <label for="userPhone" class="col-sm-2 control-label">电话</label>
        <div class="col-sm-10">
            <input id="userPhone" name="Name" type="text" class="form-control" placeholder="">
        </div>
    </div>

    <div class="form-group">
        <label for="status" class="col-sm-2 control-label">状态</label>
        <div class="col-sm-10">
            <select id="status" name="status" class="form-control" onchange="disable()">
                <option value="0" selected>启用</option>
                <option value="1">锁定</option>
            </select>
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="btn-group">
                <button type="button" class="btn btn-success" onclick="save();">确认</button>
            </div>
            <div class="btn-group">
                <button type="button" class="btn btn-warning" onclick="cancel();">取消</button>
            </div>
        </div>

    </div>


</form>

</body>

<!-- JS START -->
<script type="text/javascript" src="<%=basePath%>js/system/userInfoAdd.js"></script>
<!-- JS END -->

</html>
