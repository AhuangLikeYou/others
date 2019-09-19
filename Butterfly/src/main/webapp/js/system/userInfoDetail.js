function updateDetail() {
    var userName = $("#userName").val();
    if (!userName || userName == "" || userName.trim() == "") {
        alert("用户名必填!");
        return;
    }

    $.ajax({
        type: "POST",
        dataType: "json",
        url: "system/userInfo/updateDetail",
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