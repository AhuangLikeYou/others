function refresh() {
    $("#table").bootstrapTable('refresh');
}

function resetSearch() {
    $("#searchDiv input").val("");
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
        key: $("#key").val(),
        type: $("#type").val(),
    };
    return params;
}

//状态格式化
function statusFormatter(value, row, index) {
    if (value == "Y") {
        return "是";
    }
    if (value == "N") {
        return "否";
    }
    return value;
}

function deleteConfig() {
    layer.confirm('系统配置非常重要，确定删除？', {
        btn: ['确定', '取消']
    }, function () {

        var record = $("#table").bootstrapTable('getSelections');
        var configId = getArrByProperties(record, 'configId');
        if (record.length > 0) {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "system/sysConfig/deleteConfig?configId=" + configId,
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

function addConfig() {
    window.location.href = "system/sysConfig/sysconfigAddPage";
}

function configDetails() {
    var record = $("#table").bootstrapTable('getSelections');
    if (record.length == 1) {
        var configId = record[0].configId;
        window.location.href = "system/sysConfig/sysconfigDetailPage?configId=" + configId;
    } else {
        alert("请选中一行");
    }
}




