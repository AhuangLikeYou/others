//获取窗口索引
var index = parent.layer.getFrameIndex(window.name);

$(document).ready(function () {
//自适应
    parent.layer.iframeAuto(index);
});

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

/**
 * 添加选择
 */
function addChoose() {
    var data = $("#table").bootstrapTable('getSelections');
    if (!data || data.length == 0) {
        return;
    }
    if (data.length > 10) {
        alert("一个用户最多只能添加10个角色");
        return;
    }
    var chosen = $("#table-choose").bootstrapTable('getData');
    if (!chosen || chosen.length == 0) {
        $("#table-choose").bootstrapTable('append', data);
    } else {
        //剔除重复选项
        var _chosen = new Array();
        for (var i = 0; i < data.length; i++) {
            var exits = 'N';
            var d = data[i];
            for (var j = 0; j < chosen.length; j++) {
                var c = chosen[j];
                if (d.resourceId == c.resourceId) {
                    exits = 'Y';
                    break;
                }
            }
            if ('N' == exits) {
                _chosen[_chosen.length] = d;
            }
        }

        if (!_chosen.length || _chosen.length == 0) {
            return;
        }
        if (_chosen.length + chosen.length > 10) {
            alert("一个用户最多只能添加10个角色");
            return;
        }
        $("#table-choose").bootstrapTable('append', _chosen);
    }

}

/**
 * 清空选择
 */
function resetChoose() {
    $("#table-choose").bootstrapTable('removeAll');
}

/**
 * 确认关联
 */
function connect() {
    var chosen = $("#table-choose").bootstrapTable('getData');
    if (!chosen || chosen.length == 0) {
        alert('请添加角色');
        return;
    }

    var resourceIds = getArrByProperties(chosen, 'resourceId');
    var roleId = $("#roleId").val();
    var requestUrl = "system/roleResources/roleResuorceConnect?resourceIds=" + resourceIds
        + "&roleId=" + roleId;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: requestUrl,
        data: $('#form').serialize(),
        success: function (data) {
            if (data.success == true) {
                // parent.layer.msg('');
                parent.alert("成功");
                cancelConnect();
            } else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });
}

function cancelConnect() {
    parent.layer.close(index);
}





 