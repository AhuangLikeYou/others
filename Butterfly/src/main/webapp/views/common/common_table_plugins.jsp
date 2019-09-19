<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

<!-- using bootstrap table -->
<!-- CSS START  -->
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-table/bootstrap-table.min.css" >
<!-- 	CSS END -->

<!-- JS START -->
<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="js/common/common_table.js"></script>
<!-- JS END -->

</head>

<body>
</body>

</head>

</html>