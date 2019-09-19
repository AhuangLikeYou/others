<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../common/common.jsp" />
	<jsp:include page="../common/topMenus.jsp" />

	<base href="<%=basePath%>">

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="css/system/main.css">

<style>
</style>

</head>
<body>

</br>
</br>
</br>
</br>
<p align="center">

		欢迎访问butterfly
</p>

</body>

<!-- JS START -->
<script type="text/javascript" src="js/system/main.js"></script>
<!-- JS END -->

</html>
