/**
 * 此文件主要用来写通用的js
 * 例如重写整个网站的alter函数等
 */


//重写Window的alter方法,使用layer
window.alert = function (msg, _time, _icon) {
    if (!msg) {
        msg = "<br/>";
    } else {
        msg = " <p align='left' style='font-weight: bold'>提示</p>" + "<p align='center'>" + msg + "</p>";
    }

    if (!_time) {
        _time = 1000;
    }
    if (!_icon) {
        //0感叹号，1对勾，2叉，3问号.....
        _icon = 0;
    }

    layer.msg(msg,
        {
            icon: _icon, time: _time,
            offset: ["30%", '']
            , area: ['20%', '']
        });
}