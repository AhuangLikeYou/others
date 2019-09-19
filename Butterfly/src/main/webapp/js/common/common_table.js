/**
 * TODO: common bootstrap table method
 */

/*init bootstrap-table properties*/
function initTableAttributes(tableId) {
    var table = $("#" + tableId);
    table.attr("data-toggle", "table");
    table.attr("data-height", "400");
    table.attr("data-side-pagination", "server");
    table.attr("data-pagination", "true");
    table.attr("data-page-list", "[5, 10, 20, 50, 100, 200]");
    table.attr("data-search", "true");
}


//override date.format 
Date.prototype.format = function (format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

//date Formatter
function tableDateFormatter(value, row, index) {
    if (!value) {
        return "";
    }
    var date = new Date(value);
    return date.format('yyyy-MM-dd hh:mm:ss');
}

/**
 * 根据选中的数据，属性，返回集合
 * @param rows
 * @param proerties
 * @returns {*} 属性值，已逗号分隔
 */
function getArrByProperties(rows, proerties) {
    if (rows && rows.length > 0) {
        var arr = new Array();
        //如果列属性是关联属性，即带有.
        if (proerties.indexOf('.') != -1) {
            var index = 0;
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                try {
                    arr[index] = eval("row['" + proerties.replace(/\./g, "']['") + "']");
                    index = index + 1;
                } catch (err) {

                }
            }
        } else {
            for (var i = 0; i < rows.length; i++) {
                arr[i] = $(rows[i]).attr(proerties);
            }
        }
        return arr;
    } else {
        return null;
    }
}