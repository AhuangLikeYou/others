function updateConfigDetail() {
    var configType = $("#configType").val();
    var configKey = $("#configKey").val();
    if (!configType || configType == "" || configType.trim() == "") {
        alert("类型必填!");
        return;
    }
    if (!configKey || configKey == "" || configKey.trim() == "") {
        alert("键必填!");
        return;
    }


    $.ajax({
        type: "POST",
        dataType: "json",
        url: "system/sysConfig/updateConfig",
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