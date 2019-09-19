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
    <jsp:include page="common.jsp"/>
    <jsp:include page="topMenus.jsp"/>

    <base href="<%=basePath%>">

    <title>修改密码</title>
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
    　 <h3>修改密码</h3>
    <div class="form-group">
        <label for="pwd" class="col-sm-2 control-label">当前密码</label>
        <div class="col-sm-10">
            <input id="pwd" name="pwd" class="form-control" placeholder="" type="password"/>
        </div>
    </div>
    <div class="form-group">
        <label for="newPwd" class="col-sm-2 control-label">新密码</label>
        <div class="col-sm-10">
            <input id="newPwd" name="newPwd" class="form-control" placeholder="" type="password"/>
        </div>
    </div>

    <div class="form-group">
        <label for="newPwdConfirm" class="col-sm-2 control-label">确认</label>
        <div class="col-sm-10">
            <input id="newPwdConfirm" name="newPwdConfirm" class="form-control" placeholder="" type="password"/>
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="btn-group">
                <button type="button" class="btn btn-success" onclick="resetPwd();">确认</button>
            </div>
            <div class="btn-group">
                <button type="button" class="btn btn-warning" onclick="cancel();">取消</button>
            </div>
        </div>

    </div>


</form>

</body>

<!-- JS START -->
<
<script>
    function resetPwd() {
        var pwd = $("#pwd").val();
        var newPwd = $("#newPwd").val();
        var newPwdConfirm = $("#newPwdConfirm").val();
        if (!pwd || pwd == "" || pwd.trim() == "") {
            alert("密码必填!");
            return;
        }
        if (!newPwd || newPwd == "" || newPwd.trim() == "") {
            alert("新密码必填!");
            return;
        }
        if (!newPwdConfirm || newPwdConfirm == "" || newPwdConfirm.trim() == "") {
            alert("请确认新密码!");
            return;
        }
        if (newPwd != newPwdConfirm) {
            alert("两次输入密码必须一致!");
            return;
        }

        $.ajax({
            type: "POST",
            dataType: "json",
            url: "system/userLogin/resetPwd",
            data: $('#form').serialize(),
            success: function (data) {
                alert(data.msg);
                if (data.success == true) {
                    layer.alert('修改成功,请退出重新登录', {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0
                    }, function () {
                        location.href = "system/userLogin/logout";
                    });

                }
            },
            error: function (data) {
                alert("error:" + data.responseText);
            }
        });
    }

    function cancel() {
        location.href = document.referrer;
    }
</script>
<!-- JS END -->

</html>
