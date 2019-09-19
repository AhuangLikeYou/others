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
        var rolesId = getArrByProperties(record, 'roleId');
        if (record.length > 0) {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "system/roleInfo/deleteRole?rolesId=" + rolesId,
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
    window.location.href = "system/roleInfo/addRolePage";
}

function details() {
    var record = $("#table").bootstrapTable('getSelections');
    if (record.length == 1) {
        var roleId = record[0].roleId;
        window.location.href = "system/roleInfo/roleInfoDetail?roleId=" + roleId;
    } else {
        alert("请选中一行");
    }
}

function updateStatus() {
    var record = $("#table").bootstrapTable('getSelections');
    if (record.length == 1) {
        var roleId = record[0].roleId;
        var requestUrl = "system/roleInfo/updateRoleStatus?roleId=" + roleId;
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


/**
 * 分配用户
 */
function addRole() {
    var record = $("#table").bootstrapTable('getSelections');
    if (!record || record.length == 0 || record.length > 1) {
        alert('请选择一条数据');
        return;
    }
    var roleId = record[0].roleId;
    var openUrl = 'system/userRole/userRoleConnectPage?roleId=' + roleId;
    layer.open({
        type: 2,
        area: ['100%', '100%'],
        fixed: false, //不固定
        maxmin: true,
        content: openUrl,
        success: function (layero, index) {
            // layer.iframeAuto(index);
        }
    });
}


function addResource() {
    var record = $("#table").bootstrapTable('getSelections');
    if (!record || record.length == 0 || record.length > 1) {
        alert('请选择一条数据');
        return;
    }
    var roleId = record[0].roleId;
    var openUrl = 'system/roleResources/roleResourceConnectPage?roleId=' + roleId;
    layer.open({
        type: 2,
        area: ['100%', '100%'],
        fixed: false, //不固定
        maxmin: true,
        content: openUrl,
        success: function (layero, index) {
            // layer.iframeAuto(index);
        }
    });
}
