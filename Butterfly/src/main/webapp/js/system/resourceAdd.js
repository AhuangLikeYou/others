function save() {
    var resourceName = $("#resourceName").val();
    var resourceCode = $("#resourceCode").val();
    var resourcePath = $("#resourcePath").val();

    if (!resourceName || resourceName == "" || resourceName.trim() == "") {
        alert("资源名必填!");
        return;
    }
    if (!resourceCode || resourceCode == "" || resourceCode.trim() == "") {
        alert("代码必填!");
        return;
    }
    if (!resourcePath || resourcePath == "" || resourcePath.trim() == "") {
        alert("访问路径必填!");
        return;
    }

    $.ajax({
        type: "POST",
        dataType: "json",
        url: "system/resourceInfo/addResource",
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