<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <link rel="stylesheet" href="css/common/topMenus.css">

    <base href="<%=basePath%>">

    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">


</head>
<body>

<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-static-top">
    <div class="navbar-header">
        <a class="navbar-brand" href="<%=basePath%>views/system/main.jsp">Butterfly </a>
    </div>

    <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav">


            <shiro:hasPermission name="TEST">
                <li class="dropdown"><a tabindex="0" data-toggle="dropdown" data-submenu=""> 测试接口<span
                        class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <shiro:hasPermission name="butterfly">
                            <li class="divider"></li>
                            <li><a tabindex="0" href="system/business/test/testPage">测试接口界面</a></li>
                        </shiro:hasPermission>

                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="resource_manage_add">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">系统管理 <span class="caret"></span></a>

                    <ul class="dropdown-menu">
                        <shiro:hasPermission name="druidControl">
                            <li class="divider"></li>
                            <li><a tabindex="0" href="system/druid/druidPage">数据库监控</a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="sys_config_page">
                            <li class="divider"></li>
                            <li><a tabindex="0" href="system/sysConfig/sysconfigPage">系统配置</a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="resource_manage_add">
                            <li class="divider"></li>
                            <li><a tabindex="0" href="system/userInfo/userInfoPage">用户管理</a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="resource_manage_add">
                            <li class="divider"></li>
                            <li><a tabindex="0" href="system/roleInfo/roleInfoPage">角色管理</a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="resource_manage_add">
                            <li class="divider"></li>
                            <li><a tabindex="0" href="system/resourceInfo/resourcePage">资源管理</a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="resource_manage_add">
                            <li class="divider"></li>
                            <li><a tabindex="0" href="system/userRoleResource/view">权限视图</a></li>
                        </shiro:hasPermission>

                    </ul>
                </li>
            </shiro:hasPermission>


        </ul>


        <ul class="nav navbar-nav navbar-right">
            <li><a href="<%=basePath%>system/userLogin/resetPwdPage">修改密码</a></li>
            <li><a href="<%=basePath%>system/userLogin/logout">退出</a></li>
            <li><a> </a></li>
        </ul>
    </div><!--/.nav-collapse -->
</nav>


</body>
</html>