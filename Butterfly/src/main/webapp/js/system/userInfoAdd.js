function save() {
    var userName = $("#userName").val();
    var userPwd = $("#userPwd").val();
    if (!userName || userName == "" || userName.trim() == "") {
        alert("用户名必填!");
        return;
    }
    if (!userPwd || userPwd == "" || userPwd.trim() == "") {
        alert("密码必填!");
        return;
    }

    $.ajax({
        type: "POST",
        dataType: "json",
        url: "system/userInfo/addUser",
        data: $('#form').serialize(),
        success: function (data) {
            alert(data.msg);
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });
}

function cancel() {
    location.href=document.referrer;
}