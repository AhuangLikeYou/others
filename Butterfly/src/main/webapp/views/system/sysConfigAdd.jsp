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
        <label for="configType" class="col-sm-2 control-label">类型</label>
        <div class="col-sm-10">
            <input id="configType" name="configType" type="text" class="form-control" placeholder=""/>
        </div>
    </div>
    <div class="form-group">
        <label for="configKey" class="col-sm-2 control-label">键</label>
        <div class="col-sm-10">
            <input id="configKey" name="configKey" type="text" class="form-control" placeholder=""/>
        </div>
    </div>

    <div class="form-group">
        <label for="configValue" class="col-sm-2 control-label">值</label>
        <div class="col-sm-10">
            <input id="configValue" name="configValue" type="text" class="form-control" placeholder=""/>
        </div>
    </div>

    <div class="form-group">
        <label for="remarks" class="col-sm-2 control-label">备注</label>
        <div class="col-sm-10">
            <textarea rows="5" name="remarks" id="remarks" cols="" class="form-control" p>

            </textarea>
        </div>
    </div>

    <div class="form-group">
        <label for="iscache" class="col-sm-2 control-label">是否缓存</label>
        <div class="col-sm-10">
            <select id="iscache" name="iscache" class="form-control" onchange="disable()">
                <option value="N" selected>
                    否
                </option>
                <option value="Y">是</option>
            </select>
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="btn-group">
                <button type="button" class="btn btn-success" onclick="saveConfig();">确认</button>
            </div>
            <div class="btn-group">
                <button type="button" class="btn btn-warning" onclick="cancel();">取消</button>
            </div>
        </div>

    </div>


</form>

</body>

<!-- JS START -->
<script type="text/javascript" src="<%=basePath%>js/system/sysConfigAdd.js"></script>
<!-- JS END -->

</html>
