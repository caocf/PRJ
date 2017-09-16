$(document).ready(function () {
    $(document).keyup(function (event) {
        switch (event.keyCode) {
            case 27:
                hidePop();
                break;
        }
    });
});

// 添加cookie
function setCookie(name, value, hours, path) {
    var name = escape(name);
    var value = escape(value);
    var expires = new Date();
    expires.setTime(expires.getTime() + hours * 3600000);
    path = path == "" ? "" : ";path=" + path;
    _expires = (typeof hours) == "string" ? "" : ";expires="
    + expires.toUTCString();
    document.cookie = name + "=" + value + _expires + path;
}

// 存入cookie时编码
function CodeCookie(str) {
    var strRtn = "";

    for (var i = str.length - 1; i >= 0; i--) {
        strRtn += str.charCodeAt(i);
        if (i)
            strRtn += "a"; // 用a作分隔符
    }
    return strRtn;
}

// 取出来时解码
function DecodeCookie(str) {
    if (str == "") {
        return;
    } else {
        var strArr;
        var strRtn = "";
        strArr = str.split("a");
        for (var i = strArr.length - 1; i >= 0; i--)
            strRtn += String.fromCharCode(eval(strArr[i]));
        return strRtn;
    }
}
// 获取cookie值
function getCookieValue(name) {
    var name = escape(name);
    // 读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;
    // 查找名为name的cookie的开始位置
    name += "=";
    var pos = allcookies.indexOf(name);
    // 如果找到了具有该名字的cookie，那么提取并使用它的值
    if (pos != -1) { // 如果pos值为-1则说明搜索"version="失败
        var start = pos + name.length; // cookie值开始的位置
        var end = allcookies.indexOf(";", start); // 从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
        if (end == -1)
            end = allcookies.length; // 如果end值为-1说明cookie列表里只有一个cookie
        var value = allcookies.substring(start, end); // 提取cookie的值
        return (value); // 对它解码
    } else
        return ""; // 搜索失败，返回空字符串
}
// 删除cookie
function deleteCookie(name, path) {
    var name = escape(name);
    var expires = new Date(0);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
}

// 不设置时间存储cookie
function setTemptCookie(name, value, path) {
    var name = escape(name);
    var value = escape(value);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "=" + value + path;
}

// 获得最根级窗口，常用于iframe超时
function getRootWin() {
    var win = window;
    while (win != win.parent) {
        win = win.parent;
    }
    return win;
}

// 获得datatable服务器分页时的对应name的值
function getDateTableParam(aoData, name) {
    for (var i = 0; i < aoData.length; i++) {
        var item = aoData[i];
        if (item.name == name)
            return item.value;
    }
    return null;
}

// 获得datatable服务器分页时的行数
function getDateTableRows(aoData) {
    var val = getDateTableParam(aoData, "iDisplayLength");
    if (val == null)
        return -1;
    else
        return val;
}

// 获得datatable服务器分页时的页码
function getDateTablePage(aoData) {
    var rows = getDateTableRows(aoData);
    var start = getDateTableParam(aoData, "iDisplayStart");
    if (start != null && rows != -1 && rows != 0) {
        return start / rows + 1;
    } else
        return -1;
}

// 将用户返回结果解析成DateTable可解析的json数据
function toDateTableJsonResult(dataList, iTotalRecords, iTotalDisplayRecords,
                               sEcho) {
    var data = {
        sEcho: sEcho,
        iTotalRecords: iTotalRecords,
        iTotalDisplayRecords: iTotalDisplayRecords,
        aaData: dataList
    };
    return data;
}

function showPop(src, width, height) {
    var rootwin = getRootWin();
    $("#popup", rootwin.document).attr('src', src);
    $("#popup", rootwin.document).css('width', width + 'px');
    $("#popup", rootwin.document).css('height', height + 'px');
    $("#popup", rootwin.document).css('margin', '-' + height / 2 + 'px' + " 0 0 -" + width / 2 + 'px');

    $("#fullbg", rootwin.document).fadeIn(200);
    $("#popup", rootwin.document).fadeIn(500);
}

function hidePop() {
    var rootwin = getRootWin();
    $("#fullbg", rootwin.document).css('display', 'none');
    $("#popup", rootwin.document).css('display', 'none');
    $("#popup", rootwin.document).attr('src', '');
}

function showloading(comment) {
    try {
        var rootwin = getRootWin();
        if (comment == null || comment == '') {
            comment = '正在加载 ...';
        }
        if ($('#loadingdiv', rootwin.document).length <= 0) {
            var loadingdiv = '<div id="loadingdiv" style="text-align: center;position: fixed;width: 100%;height: 100%;top:0px;left:0px;background: #000;opacity:0.7;z-index: 100000">'
                + '<div style="position:absolute;color:white;height:100px;top:50%;width:200px;left:50%;margin:-50px 0 0 -100px;">'
                + '<i class="icon-spinner icon-spin icon-4x" style="color:white;"></i><br>'
                + '<label style="color:white;" id="loadinglabel">'
                + comment
                + '</label>' + '</div>' + '</div>';

            var rootbody = $('body', rootwin.document);
            rootbody.append(loadingdiv);
        } else {
            $('#loadinglabel', rootwin.document).text(comment);
            $('#loadingdiv', rootwin.document).css('display', 'block');
        }
    } catch (e) {
    }
}

function hideloading() {
    try {
        var rootwin = getRootWin();
        $('#loadingdiv', rootwin.document).css('display', 'none');
    } catch (e) {
    }
}


function setAllParentIframeHeight(currwindow, paddingheight) {
    if (currwindow == null)
        currwindow = window;
    var height = $(currwindow.document).height();
    if (paddingheight != null)
        height += paddingheight;
    var parentwindow = getParentWindow(currwindow);
    if (parentwindow != null) {
        $("iframe", $(parentwindow.document)).css('height', height);
        setAllParentIframeHeight(parentwindow);
    }
}

function getParentWindow(currwindow) {
    var win = currwindow;
    var parentwin = win.parent;
    if (parentwin != null && parentwin != win)
        return parentwin;
    return null;
}

Date.prototype.addDays = function (d) {
    this.setDate(this.getDate() + d);
};


Date.prototype.addWeeks = function (w) {
    this.addDays(w * 7);
};


Date.prototype.addMonths = function (m) {
    var d = this.getDate();
    this.setMonth(this.getMonth() + m);

    if (this.getDate() < d)
        this.setDate(0);
};


Date.prototype.addYears = function (y) {
    var m = this.getMonth();
    this.setFullYear(this.getFullYear() + y);

    if (m < this.getMonth()) {
        this.setDate(0);
    }
};


Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "H+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

// 将Date转换成字符串
function getTimeFmt(date, datefmt) {
    if (datefmt == null || datefmt == '') {
        datefmt = 'yyyy-MM-dd HH:mm:ss';
    }
    return date.format(datefmt);
}

function daynow() {
    return getTimeFmt(new Date(), 'yyyy-MM-dd');
}

function daymonthago() {
    var date = new Date();
    date.addMonths(-1);
    return getTimeFmt(date, 'yyyy-MM-dd');
}