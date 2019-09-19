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
</head>

<script type="text/javascript">

	window.onload = function() {
	//解决兼容性
		var pathName = window.document.location.pathname;
		var projectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 1);
		location.href = projectName + "/login.jsp";
	}
</script>

<body>
	
</body>
<script type="text/javascript">
</script>
</html>