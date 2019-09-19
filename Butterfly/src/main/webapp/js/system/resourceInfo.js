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

function deleteResource() {
    layer.confirm('确定删除？', {
        btn: ['确定', '取消']
    }, function () {

        var record = $("#table").bootstrapTable('getSelections');
        var resourceIds = getArrByProperties(record, 'resourceId');
        if (record.length > 0) {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "system/resourceInfo/deleteResource?resourceIds=" + resourceIds,
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
    window.location.href = "system/resourceInfo/addResourcePage";
}

function details() {
    var record = $("#table").bootstrapTable('getSelections');
    if (record.length == 1) {
        var resourceId = record[0].resourceId;
        window.location.href = "system/resourceInfo/ResourceInfoDetail?resourceId=" + resourceId;
    } else {
        alert("请选中一行");
    }
}

function updateStatus() {
    var record = $("#table").bootstrapTable('getSelections');
    if (record.length == 1) {
        var resourceId = record[0].resourceId;
        var requestUrl = "system/resourceInfo/updateResourceStatus?resourceId=" + resourceId;
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