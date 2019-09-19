function updateDetail() {
    var roleName = $("#roleName").val();
    if (!roleName || roleName == "" || roleName.trim() == "") {
        alert("角色名必填!");
        return;
    }

    $.ajax({
        type: "POST",
        dataType: "json",
        url: "system/roleInfo/updateRoleDetail",
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
    location.href = document.referrer;
}