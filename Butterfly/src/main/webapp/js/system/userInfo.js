function refresh() {
    $("#table").bootstrapTable('refresh');
}

function searchKeyDown() {
    if (window.event.keyCode == 13) {
        refresh();
    }
}

function queryParams(params) {
    var params = {
        limit: params.limit,   //页面大小
        offset: params.offset,  //页码
        keyWords: $("#keyWords").val(),
    };
    return params;
}

//状态格式化
function statusFormatter(value, row, index) {
    if (value == 0) {
        return "启用";
    }
    if (value == 1) {
        return "锁定";
    }
    return value;
}

function deleteUser() {

    layer.confirm('确定删除？', {
        btn: ['确定', '取消']
    }, function () {

        var record = $("#table").bootstrapTable('getSelections');
        var usersId = getArrByProperties(record, 'userId');
        if (record.length > 0) {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "system/userInfo/deleteUsers?usersId=" + usersId,
                data: $('#form').serialize(),
                success: function (data) {
                    if (data.success == true) {
                        alert(data.msg);
                        refresh();
                    } else {
                        alert(data.msg);
                    }
                },
                error: function (data) {
                    alert("error:" + data.responseText);
                }
            });
        }
        else {
            alert("请选中一行");
        }

        //取消按钮函数
    }, function () {
        return;
    });

}

function add() {
    window.location.href = "system/userInfo/addUserPage";
}

function details() {
    var record = $("#table").bootstrapTable('getSelections');
    if (record.length == 1) {
        var userId = record[0].userId;
        window.location.href = "system/userInfo/userInfoDetail?userId=" + userId;
    } else {
        alert("请选中一行");
    }
}

function updateStatus() {
    var record = $("#table").bootstrapTable('getSelections');
    if (record.length == 1) {
        var userId = record[0].userId;
        var requestUrl = "system/userInfo/updateStatus?userId=" + userId;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: requestUrl,
            data: $('#form').serialize(),
            success: function (data) {
                if (data.success == true) {
                    refresh();
                } else {
                    alert(data.msg);
                }
            },
            error: function (data) {
                alert("error:" + data.responseText);
            }
        });

    } else {
        alert("请选中一行");
    }
}

function resetPwd() {
    var record = $("#table").bootstrapTable('getSelections');
    if (record.length == 1) {
        var userId = record[0].userId;
        var requestUrl = "system/userInfo/resetPwd?userId=" + userId;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: requestUrl,
            data: $('#form').serialize(),
            success: function (data) {
                if (data.success == true) {
                    alert("成功");
                    refresh();
                } else {
                    alert(data.msg);
                }
            },
            error: function (data) {
                alert("error:" + data.responseText);
            }
        });
    } else {
        alert("请选中一行");
    }
}


