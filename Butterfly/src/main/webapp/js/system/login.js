$("#form-username").keydown(function () {
    if (window.event.keyCode == 13) {
        $('#form-password').focus();
    }
});

$("#form-password").keydown(function () {
    if (window.event.keyCode == 13) {
        login();
    }
});

function login() {
    var uname = $('#form-username').val();
    var upwd = $('#form-password').val();
    if (!uname) {
        alert("请输入用户名");
        return;
    }
    if (!upwd) {
        alert("请输入密码");
        return;
    }

    $.ajax({
        type: 'post',
        dataType: "json",
        async: false,
        url: encodeURI('system/userLogin/loginCheck'), //请求地址
        data: {"uname": uname, "upwd": upwd},
        success: function (data) {
            if (data.success == true) {
                location.href = "system/userAccess/mainPage";
            } else {
                alert(data.msg);
            }
        }
    });
}

