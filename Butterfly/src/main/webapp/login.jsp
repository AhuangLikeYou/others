.
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
    <jsp:include page="/views/common/common.jsp"/>

    <base href="<%=basePath%>">

    <title>用户登录</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <!-- CSS START -->
    <link rel="stylesheet" href="css/login/login.css">
    <!-- CSS END -->

    <style>

    </style>
</head>

<body background="">


<div class="container">

    <form class="form-signin">
        <h2 class="form-signin-heading">请登录</h2>
        <label for="form-username" class="sr-only">用户名</label>
        <input type="text" id="form-username" class="form-control" value="admin" placeholder="用户名" required autofocus>
        <label for="form-password" class="sr-only">密码</label>
        <input type="password" id="form-password" class="form-control" value="123456" placeholder="密码" required>
        <%--   <div class="checkbox">
               <label>
                   <input type="checkbox" value="remember-me"> Remember me
               </label>
           </div>--%>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="login();">登 录</button>
        <p style="color: red;text-align: center" hidden="hidden" id="chromeNotice"> 建议使用chrome浏览器访问本系统</p>
    </form>
</div>

</body>


<!-- JS START -->
<script type="text/javascript" src="js/system/login.js"></script>
<!-- JS END -->
<script type="text/javascript">
    window.onload = function () {
        var isChrome = window.navigator.userAgent.indexOf("Chrome") !== -1;
        if (!isChrome) {
            $("#chromeNotice").removeAttr("hidden");
        }
    }
</script>


</html>