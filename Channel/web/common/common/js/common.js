$(document).ready(function () {
    if (!$.support.leadingWhitespace) {
        showie();
    }

    if ($("#leftpopmenu").length > 0) {
        $("#leftpopmenu").click(function (event) {
            event.stopPropagation();
        });
        $("#leftpopmenu").mouseleave(function (event) {
            hideleftpopmenu();
        })
        $(window).click(function (event) {
            hideleftpopmenu();
        });
    }
});

function hideleftpopmenu() {
    $("#leftpopmenu").animate({
        left: "-170px"
    }, 200); // 使元素在3s内向右移动500像素
}

function showleftpopmenu() {
    $("#leftpopmenu").animate({
        left: "0px"
    }, 200); // 使元素在3s内向右移动500像素
}


function showie() {
    var div = document.createElement("div");
    div.setAttribute("id", "newDiv");
    div.style.cssText = 'background-color:rgb(12,12,12);width:100%;height:50px;line-height:50px;color:#f2a64c;font-family:微软雅黑;font-size:12px;';
    div.innerHTML = "<div style='height:50px;margin-left:-335px;width:670px;float:left;position:relative;left:50%;'>" +
        "<div style='background-color:rgb(12,12,12);background-image:url(img/ic_tishi.png);background-repeat: no-repeat;width:16px;height:16px;"
        + "float:left;margin-top:17px;'></div><span style='line-height:50px;float:left;'>" +
        "&nbsp;&nbsp;检测到您当前使用的浏览器版本过低，将影响系统功能的正常使用。推荐使用谷歌浏览器（" +
        "<a href='common/common/js/ChromeStandaloneSetup.exe' style='color:white;'>点击下载</a>）或IE9以上版本。</span></div>"
        + "<img style='width:16px;height:16px;float:right;margin-top:17px;"
        + "margin-right:10px;cursor:pointer;' onclick='iehide()' src='img/ic_close.png'></img>";
    var first = document.body.firstChild; // 得到第一个元素
    document.body.insertBefore(div, first); // 在第原来的第一个元素之前插入
}

function iehide() {
    document.getElementById('newDiv').style.display = "none";
    document.getElementById('newDiv1').style.display = "none";
}

//输入框提示
function TextFocus(id) {
    if (id.value == id.defaultValue) {
        id.value = '';
        id.style.color = '#333333';
    }
}
// 输入框提示
function TextBlur(id) {
    if (!id.value) {
        id.value = id.defaultValue;
        id.style.color = '#a3a3a3';
    }
}

function rand(n, m) {
    var c = m - n + 1;
    return Math.floor(Math.random() * c + n);
}

/** ****************************时间相关函数*********************************** */
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

/** year : /yyyy/ */
var y4 = "([0-9]{4})";
/** year : /yy/ */
var y2 = "([0-9]{2})";
/** index year */
var yi = -1;
/** month : /MM/ */
var M2 = "(0[1-9]|1[0-2])";
/** month : /M/ */
var M1 = "([1-9]|1[0-2])";
/** index month */
var Mi = -1;
/** day : /dd/ */
var d2 = "(0[1-9]|[1-2][0-9]|30|31)";
/** day : /d/ */
var d1 = "([1-9]|[1-2][0-9]|30|31)";
/** index day */
var di = -1;
/** hour : /HH/ */
var H2 = "([0-1][0-9]|20|21|22|23)";
/** hour : /H/ */
var H1 = "([0-9]|1[0-9]|20|21|22|23)";
/** index hour */
var Hi = -1;
/** minute : /mm/ */
var m2 = "([0-5][0-9])";
/** minute : /m/ */
var m1 = "([0-9]|[1-5][0-9])";
/** index minute */
var mi = -1;
/** second : /ss/ */
var s2 = "([0-5][0-9])";
/** second : /s/ */
var s1 = "([0-9]|[1-5][0-9])";
/** index month */
var si = -1;

var regexp;

// 通过字符串获得时间
function getTimeFromStr(dateString, formatString) {
    if (validateDate(dateString, formatString)) {
        var now = new Date();
        var vals = regexp.exec(dateString);
        var index = validateIndex(formatString);
        var year = index[0] >= 0 ? vals[index[0] + 1] : now.getFullYear();
        var month = index[1] >= 0 ? (vals[index[1] + 1] - 1) : now.getMonth();
        var day = index[2] >= 0 ? vals[index[2] + 1] : 1;
        var hour = index[3] >= 0 ? vals[index[3] + 1] : "";
        var minute = index[4] >= 0 ? vals[index[4] + 1] : "";
        var second = index[5] >= 0 ? vals[index[5] + 1] : "";

        var validate;

        if (hour == "")
            validate = new Date(year, month, day);
        else
            validate = new Date(year, month, day, hour, minute, second);

        return validate;
    }
}

function validateDate(dateString, formatString) {
    var dateString = trim(dateString);
    if (dateString == "")
        return;
    var reg = formatString;
    reg = reg.replace(/yyyy/, y4);
    reg = reg.replace(/yy/, y2);
    reg = reg.replace(/MM/, M2);
    reg = reg.replace(/M/, M1);
    reg = reg.replace(/dd/, d2);
    reg = reg.replace(/d/, d1);
    reg = reg.replace(/HH/, H2);
    reg = reg.replace(/H/, H1);
    reg = reg.replace(/mm/, m2);
    reg = reg.replace(/m/, m1);
    reg = reg.replace(/ss/, s2);
    reg = reg.replace(/s/, s1);
    reg = new RegExp("^" + reg + "$");
    regexp = reg;
    var ret = reg.test(dateString);
    return ret;
}

function validateIndex(formatString) {
    var ia = new Array();
    var i = 0;
    yi = formatString.search(/yyyy/);
    if (yi < 0)
        yi = formatString.search(/yy/);
    if (yi >= 0) {
        ia[i] = yi;
        i++;
    }

    Mi = formatString.search(/MM/);
    if (Mi < 0)
        Mi = formatString.search(/M/);
    if (Mi >= 0) {
        ia[i] = Mi;
        i++;
    }

    di = formatString.search(/dd/);
    if (di < 0)
        di = formatString.search(/d/);
    if (di >= 0) {
        ia[i] = di;
        i++;
    }

    Hi = formatString.search(/HH/);
    if (Hi < 0)
        Hi = formatString.search(/H/);
    if (Hi >= 0) {
        ia[i] = Hi;
        i++;
    }

    mi = formatString.search(/mm/);
    if (mi < 0)
        mi = formatString.search(/m/);
    if (mi >= 0) {
        ia[i] = mi;
        i++;
    }

    si = formatString.search(/ss/);
    if (si < 0)
        si = formatString.search(/s/);
    if (si >= 0) {
        ia[i] = si;
        i++;
    }

    var ia2 = new Array(yi, Mi, di, Hi, mi, si);

    for (i = 0; i < ia.length - 1; i++)
        for (j = 0; j < ia.length - 1 - i; j++)
            if (ia[j] > ia[j + 1]) {
                temp = ia[j];
                ia[j] = ia[j + 1];
                ia[j + 1] = temp;
            }

    for (i = 0; i < ia.length; i++)
        for (j = 0; j < ia2.length; j++)
            if (ia[i] == ia2[j]) {
                ia2[j] = i;
            }

    return ia2;
}

function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

// 将Date转换成字符串
function getTimeFmt(date, datefmt) {
    if (datefmt == null || datefmt == '') {
        datefmt = 'yyyy-MM-dd HH:mm:ss';
    }
    return date.format(datefmt);
}

// 将Date转换成字符串
function getTimeFmtCn(date) {
    var dtime=new Date(date);
    var year = dtime.getFullYear();  //获取年
    var month = dtime.getMonth() + 1;    //获取月
    var day = dtime.getDate(); //获取日
    return  year + "年" + month + "月" + day + "日";
}

// 获得最根级窗口，常用于iframe超时
function getRootWin() {
    var win = window;
    while (win != win.parent) {
        win = win.parent;
    }
    return win;
}

function reinitIframeHeight(iframeid) {
    var iframe = document.getElementById(iframeid);//id改为你的iframe的id
    try {
        var bHeight = iframe.contentWindow.document.body.scrollHeight;
        var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        var height = Math.max(bHeight, dHeight);
        iframe.height = height;
    } catch (ex) {
    }
}

function reinitIframeWidth(iframeid) {
    var iframe = document.getElementById(iframeid);//id改为你的iframe的id
    try {
        var bWidth = iframe.contentWindow.document.body.scrollWidth;
        var dWidth = iframe.contentWindow.document.documentElement.scrollWidth;
        var width = Math.max(bWidth, dWidth);
        iframe.width = width;
    } catch (ex) {
    }
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

// 获得datatable服务器分页时的对应name的值
function getDateTableParamBeginWith(aoData, name) {
    var list = new Array();
    for (var i = 0; i < aoData.length; i++) {
        var item = aoData[i];
        if (item.name.indexOf(name) == 0)
            list.push(item);
    }
    return list;
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

/** *****************************Cookie相关函数************************************* */

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

// 不设置时间存储cookie
function setTempCookie(name, value, path) {
    var name = escape(name);
    var value = escape(value);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "=" + value + path;
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
function getCookie(name) {
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

/** ************************* 判断后台返回结果是否成功等通用方法 ****************************** */
// 判断结果集是否为操作成功
function ifResultOK(jsondata) {
    if (jsondata.resultcode == 0) {
        return true;
    } else {
        return false;
    }
}

// 获得结果集描述，常用于当返回码为操作不成功
function getResultDesc(jsondata) {
    return jsondata.resultdesc;
}

// 获得结果集码
function getResultCode(jsondata) {
    return jsondata.resultcode;
}

// 获得结果集map
function getResultMap(jsondata) {
    return jsondata.map;
}

// 获得结果集记录
function getResultRecords(jsondata) {
    return jsondata.records;
}

// 获得结果集obj对象
function getResultObj(jsondata) {
    return jsondata.obj;
}

/** ************************显示正在加载等字样******************************* */
function showloading(comment) {
    if (comment == null || comment == '') {
        comment = '正在加载 ...';
    }
    if ($('#loadingdiv').length <= 0) {
        var loadingdiv = '<div id="loadingdiv" class="text-center" style="position: fixed;width: 100%;height: 100%;top:0px;left:0px;background: #000;opacity:0.7;z-index: 100000">'
            + '<div style="position:absolute;color:white;height:100px;top:50%;width:200px;left:50%;margin:-50px 0 0 -100px;">'
            + '<i class="icon-spinner icon-spin icon-4x" style="color:white;"></i><br>'
            + '<label style="color:white;" id="loadinglabel">'
            + comment
            + '</label>' + '</div>' + '</div>';

        $('body').append(loadingdiv);
    } else {
        $('#loadinglabel').text(comment);
        $('#loadingdiv').removeClass('hide');
    }
}

function hideloading() {
    $('#loadingdiv').addClass('hide');
}
/** ***********************自动验证表单工具******************************** */

function showvalidate(blockid) {
    var targetid = $("#" + blockid).attr('data-validate-fortarget');
    // 清空该target对应的所有
    $("*").find('[data-validate-fortarget=' + targetid + ']').addClass('hide');

    var part = $("#" + blockid).parent();
    if (!part.hasClass('form-group')) {
        part = $("#" + blockid).parent().parent();
        if (part.hasClass('form-group')) {
            part.removeClass('has-error');
        }
    }
}

function showinvalidate(blockid) {
    var targetid = $("#" + blockid).attr('data-validate-fortarget');
    // 清空该target对应的所有
    $("*").find('[data-validate-fortarget=' + targetid + ']').addClass('hide');
    // 显示当前错误信息
    $("#" + blockid).removeClass('hide');
    $("#" + targetid).focus();

    var part = $("#" + blockid).parent();
    if (!part.hasClass('form-group')) {
        part = $("#" + blockid).parent().parent();
        if (part.hasClass('form-group')) {
            part.addClass('has-error');
        }
    }
}

// 验证验证器列表中某一验证器, sync如果设置为true，则对于用户自定义的与后台交互验证的接口，都需要在调用成功以后才会继续执行，或最终返回true
// blockids待验证的所有验证器
// blockindex，当前验证哪个验证器
// success，验证成功回调函数
// failed， 验证失败回调函数
// sync， 对服务器验证是否进行同步验证
function verifyblock(blockids, blockindex, success, failed, sync) {
    // 如果验证器列表为空，或者验证超过了最后一个验证器，说明全部验证成功
    if (blockids.length == 0 || blockindex > (blockids.length - 1)) {
        if (success != null)
            success();
        return true;
    }

    var blockid = blockids[blockindex];
    var node = $("#" + blockid);
    var filter = node.attr('data-validate-filter');
    var msg = node.attr('data-validate-msg');
    var attr = node.attr('data-validate-attr');
    var min = node.attr('data-validate-min');
    var max = node.attr('data-validate-max');
    var target_id = node.attr('data-validate-target-id');
    var target_attr = node.attr('data-validate-target-attr');
    var exp = node.attr('data-validate-exp');
    var fn = node.attr('data-validate-fn');

    // 待验证的控件ID
    var targetid = node.attr('data-validate-fortarget');

    // 过滤验证
    var passed = true;
    var verifyremotesync = false;
    if (filter != null) { // 如果有过滤器，若这个block上没有过滤器，不进行验证
        // 获取值,如果指定了attr,则从attr中获取值
        var val = "";
        if (attr != null && attr != "") { // 可验证某自定义属性值
            val = $("#" + targetid).attr(attr);
        } else { // 默认验证属性是否为空
            val = $("#" + targetid).val();
        }
        // 如果无法取到值，则将值设置为''
        if (val == null)
            val = '';

        if (filter.toUpperCase() == "NOTEMPTY") { // 空值检查
            if (val == '') {
                passed = false;
                if (msg == null || msg == "") {
                    $("#" + blockid).html("输入不能为空!");
                }
            }
        } else { // 其它所有的验证器都在非空的情况下进行验证
            if (val == '') {
                passed = true; // 下面所有的验证检查，如果当前值为默认值，则不进行检查。
            } else {
                if (filter.toUpperCase() == "MAXSELECT") {
                    var items = val.split(',');
                    var itemscount = 0;
                    for (var i = 0; i < items.length; i++) {
                        if (items[i] != null && items[i] != '')
                            itemscount++;
                    }
                    if (max != null && itemscount > parseInt(max)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("最多可以选择" + max + "项!");
                        }
                    }
                } else if (filter.toUpperCase() == "MINSELECT") {
                    var items = val.split(',');
                    var itemscount = 0;
                    for (var i = 0; i < items.length; i++) {
                        if (items[i] != null && items[i] != '')
                            itemscount++;
                    }
                    if (min != null && itemscount < parseInt(min)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("最少需要选择" + min + "项!");
                        }
                    }
                } else if (filter.toUpperCase() == "SELECTRANGE") {
                    var items = val.split(',');
                    var itemscount = 0;
                    for (var i = 0; i < items.length; i++) {
                        if (items[i] != null && items[i] != '')
                            itemscount++;
                    }
                    if (max != null && itemscount > parseInt(max)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("最多可以选择" + max + "项!");
                        }
                    } else if (min != null && itemscount < parseInt(min)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("最少需要选择" + min + "项!");
                        }
                    }
                } else if (filter.toUpperCase() == "LENGTH") { // 长度范围检查
                    var cArr = val.match(/[^\x00-\xff]/ig);
                    var valLength = val.length
                        + (cArr == null ? 0 : cArr.length);

                    if (max != null && valLength > parseInt(max)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入最大长度为" + max + "!");
                        }
                    } else if (min != null && valLength < parseInt(min)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入最小长度为" + min + "!");
                        }
                    }
                } else if (filter.toUpperCase() == "MAXLENGTH") { // 最大长度检查
                    var cArr = val.match(/[^\x00-\xff]/ig);
                    var valLength = val.length
                        + (cArr == null ? 0 : cArr.length);
                    if (max != null && valLength > parseInt(max)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入最大长度为" + max + "!");
                        }
                    }
                } else if (filter.toUpperCase() == "MINLENGTH") { // 最小长度检查
                    var cArr = val.match(/[^\x00-\xff]/ig);
                    var valLength = val.length
                        + (cArr == null ? 0 : cArr.length);
                    if (min != null && valLength < parseInt(min)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入最小长度为" + min + "!");
                        }
                    }
                } else if (filter.toUpperCase() == "VALRANGE") { // 值范围检查
                    if (max != null && parseInt(val) > parseInt(max)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入值最大为" + max + "!");
                        }
                    } else if (min != null && parseInt(val) < parseInt(min)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入值最小为" + min + "!");
                        }
                    }
                } else if (filter.toUpperCase() == "MAXVAL") { // 最大值范围检查
                    if (max != null && parseInt(val) > parseInt(max)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入值最大为" + max + "!");
                        }
                    }
                } else if (filter.toUpperCase() == "MINVAL") { // 最小值范围检查
                    if (min != null && parseInt(val) < parseInt(min)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入值最小为" + min + "!");
                        }
                    }
                } else if (filter.toUpperCase() == "REGEXP") { // 自定义正则检查
                    if (exp != null && !exp.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("您的输入有误!");
                        }
                    }
                } else if (filter.toUpperCase() == "EQUAL") { // 其它页面元素比较检查
                    if (target_id != null && target_id != "") {
                        var targetval = "";
                        if (target_attr != null && target_attr != "") { // 可验证某自定义属性值
                            targetval = $("#" + target_id).attr(target_attr);
                        } else { // 默认验证属性是否为空
                            targetval = $("#" + target_id).val();
                        }
                        if (targetval == null)
                            targetval = '';
                        if (val != targetval) {
                            passed = false;
                            if (msg == null || msg == "") {
                                $("#" + blockid).html("您的输入与不匹配!");
                            }
                        }
                    }
                } else if (filter.toUpperCase() == "EMAIL") { // 邮箱格式检查
                    var regx = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                    if (regx != null && !regx.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("邮箱格式不正确!");
                        }
                    }
                } else if (filter.toUpperCase() == "PHONE") { // 手机号格式检查
                    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
                    var isMob = /^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
                    if (!isMob.exec(val) && !isPhone.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("手机格式不正确!");
                        }
                    }
                } else if (filter.toUpperCase() == "PASSWORD") { // 密码检查，
                    // 长度不能少于6位，且必须为字母数字或下划线，必须以数字开头
                    var regx = /^[a-zA-Z]\w{5,15}$/;
                    if (regx != null && !regx.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html(
                                "密码须为字母数字或下划线，且以数字开头，长度在6~15之间");
                        }
                    }
                } else if (filter.toUpperCase() == "NUMBER") { // 纯数字检查
                    var regx = /^[0-9\-]*$/;
                    if (regx != null && !regx.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入须为数字");
                        }
                    }
                } else if (filter.toUpperCase() == "CHARSETEN") { // 英文字符检查
                    var regx = /^[a-zA-Z]$/;
                    if (regx != null && !regx.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入须为字母");
                        }
                    }
                } else if (filter.toUpperCase() == "IDCARD") { // 身份证号检查
                    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
                    var regx = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                    if (regx != null && !regx.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("身份证格式不正确!");
                        }
                    }
                } else if (filter.toUpperCase() == "CHARNUMBER") { // 字母数字检查
                    var regx = /^[a-zA-Z0-9]$/;
                    if (regx != null && !regx.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入须为字母或数字");
                        }
                    }
                } else if (filter.toUpperCase() == "DOUBLE") { // 小数检查
                    var regx = /^[0-9.\-]*$/;
                    if (regx != null && !regx.exec(val)) {
                        passed = false;
                        if (msg == null || msg == "") {
                            $("#" + blockid).html("输入须为小数");
                        }
                    }
                } else if (filter.toUpperCase() == "USER") { // 自定义
                    if (fn != null && fn != "") {
                        var result = eval(fn + '(\"' + val + '\",\"' + blockid
                            + '\",\"' + targetid + '\")');
                        if ((typeof result).toLowerCase() == 'boolean') { // 说明是直接返回结果，调用者并未想用后台验证
                            passed = result;
                        } else if ((typeof result).toLowerCase() == 'object') {
                            // 调用者返回的是包含{url:,data：, success:,
                            // }的结果，希望验证器自动进行后台验证
                            // 如果无法获取url，则该自定义无效，如果没有success函数，默认以ifResultOK来判断验证是否成功。
                            var url = result.url;
                            var data = result.data;

                            if (url != null && url != "") {
                                if (sync != null && sync == true) {
                                    // 如果要求同步验证，且当前为远程验证，则异步调用ajax去验证,该函数不再传递验证
                                    // 进一步验证在ajax中进行
                                    verifyremotesync = true;
                                }// else 否则默认为pass通过

                                ajax(url, data, function (result, data) {
                                    var asyncpassed = true;
                                    if (data.validatefn != null) { // 如果设定了判断验证成功的函数，则调用
                                        asyncpassed = data.validatefn(result,
                                            data);
                                    } else {
                                        if (ifResultOK(result)) { // 否则调用默认的IfResultOK来判断
                                            asyncpassed = true;
                                        } else {
                                            asyncpassed = false;
                                        }
                                    }

                                    if (sync != null && sync == true) { // 如果定义了同步，则需要进行后续的调用
                                        if (asyncpassed == false) {
                                            showinvalidate(blockid);
                                            if (failed != null)
                                                failed(blockid, targetid);
                                        } else {
                                            showvalidate(blockid);
                                            verifyblock(blockids,
                                                blockindex + 1, success,
                                                failed, sync);
                                        }
                                    } else {
                                        // 非同步验证只显示出错结果，否则如果后续其它验证器有错误，这边验证通过，有可能会显示为验证通过
                                        if (asyncpassed == false) {
                                            showinvalidate(blockid);
                                        }
                                    }

                                });
                            }
                        }
                    }

                }
            }
        }
    }

    // 如果非异步远程
    if (verifyremotesync == false) {
        if (!passed) { // 验证失败
            showinvalidate(blockid);
            if (failed != null)
                failed(blockid, targetid);
        } else {
            showvalidate(blockid);

            // 继续下一个验证
            verifyblock(blockids, blockindex + 1, success, failed, sync);
        }
    }
}

// 通过待验证的元素进行验证,sync若为true，则对于用户自定义的验证，如果有后台请求，会同步验证
function verifytarget(targetid, success, failed, sync) {
    var blockids = new Array();
    // 查找同级中的由validate jquery添加的所有help-block验证元素
    $("*").find('[data-validate-fortarget=' + targetid + ']').each(function () {
        blockids.push($(this).attr('id')); // 收集该验证目标所有的验证器
    });
    verifyblock(blockids, 0, success, failed, sync);
}

// 用于验证自己
function verifytargetthis() {
    return verifytarget($(this).attr('id'), null, null, false);
}

$.fn.validateForm = function (success, failed) {
    var blockids = new Array();
    // 遍历当前下面的所有验证信息
    $(this).find('[id*=data-validate-block-]').each(function () {
        blockids.push($(this).attr('id')); // 收集该表单中的所有验证器
    });
    verifyblock(blockids, 0, success, failed, true);
}

$.fn.validateTarget = function (success, failed) {
    var blockids = new Array();
    var targetid = $(this).attr('id');
    // 遍历当前下面的所有验证信息
    $(this).parent().find("[data-validate-fortarget=" + targetid + "]").each(
        function () {
            blockids.push($(this).attr('id')); // 收集该表单中的所有验证器
        });
    verifyblock(blockids, 0, success, failed, true);
}

/**
 * 绑定验证器，options格式为{待验证元素ID：{验证器1：{...},验证器2：{...},},待验证元素ID：{...},待验证元素ID：{...}}
 */
// 目前支持的验证器如下：
// NOTEMPTY: 非空验证器
// LENGTH： 长度验证器，需要指定MAX与MIN，中文长度*2
// MAXLENGTH： 最大长度验证器，需要指定MAX，中文长度*2
// MINLENGTH： 最小长度验证器，需要指定MIN，中文长度*2
// MAXSELECT: 最大选择数目，需要指定MAX，注： 已选择的项目以逗号分隔
// MINSELECT： 最小选择数目，需要指定MIN，注： 已选择的项目以逗号分隔
// SELECTRANGE： 选择数目范围，需要指定MIN，注： 已选择的项目以逗号分隔
// VALRANGE：值范围验证器，需要指定MAX与MIN
// MAXVAL：最大值验证器，需要指定MAX
// MINVAL：最小值验证器，需要指定MIN
// REGEXP：自定义正则验证器
// EQUAL：与另一元素值是否相等验证器，需要指定target
// EMAIL：邮件格式验证
// PHONE：手机号格式验证，支持手机与座机
// PASSWORD：密码验证，密码为字母数字或下划线，且以数字开头，长度在6~15之间
// NUMBER：输入全为数字验证器
// CHARSETEN：英文字母验证器
// IDCARD：身份证验证器
// CHARNUMBER：字母或数字验证器
// DOUBLE：小数验证器
// USER：自定义验证器，自定义验证器需要指定自定义js函数名fn，该js函数不能进行ajax异步请求，
// -----如果自定义验证不需要进行后台服务器验证，则fn根据验证结果直接返回TRUE/FALSE即可
// -----如果自定义验证需要进行后台服务器验证，则fn返回对象{url:***,data:{...},validatefn},
// -----其中validatefn(jsonresult)为判断验证成功与否函数，成功返回TRUE，失败返回FALSE，如果不指定validatefn，则默认通过ifResultOK来判断
// -----function check(text,blockid,targetid)
$.fn.validateFormBind = function (options) {
    for (var targetid in options) {
        var validators = options[targetid];
        $("#" + targetid).validateTargetBind(validators);
    }
    return true;
}

$.fn.validateTargetBind = function (validators) {
    var targetid = $(this).attr('id');
    var afterid = targetid;
    var posin = null;
    if (validators != null) {
        $("#" + targetid).addClass('data-validate-target');

        // 查找设置
        if (validators.settings != null) {
            if (validators.settings.posafter != null
                && validators.settings.posafter != '') {
                afterid = validators.settings.posafter;
            }
            if (validators.settings.posin != null && validators.settings.posin != '') {
                posin = validators.settings.posin;
            }
            delete validators.settings;
        }

        for (var filter in validators) {
            var validator = validators[filter];

            var randid = rand(0, 9999999999);
            var verify_block = $('<small id="data-validate-block-' + randid
                + '" class="help-block hide" data-validate-fortarget="'
                + targetid + '">' + validator.msg + '</small>');

            if (posin != null) {
                $("#" + posin).append(verify_block);
                verify_block.css("display", "inline");
            } else {
                $("#" + afterid).after(verify_block);
                afterid = "data-validate-block-" + randid;
            }

            verify_block.attr('data-validate-filter', filter.toLowerCase());

            if (validator.msg != null && validator.msg != "") {
                verify_block.attr('data-validate-msg', validator.msg);
            }

            if (validator.attr != null && validator.attr != "") {
                verify_block.attr('data-validate-attr', validator.attr);
            }

            if (validator.min != null) {
                verify_block.attr('data-validate-min', validator.min);
            }
            if (validator.max != null) {
                verify_block.attr('data-validate-max', validator.max);
            }
            if (validator.target != null) {
                verify_block.attr('data-validate-target-id',
                    validator.target.id);
                verify_block.attr('data-validate-target-attr',
                    validator.target.attr);
            }
            if (validator.exp != null && validator.exp != "") {
                verify_block.attr('data-validate-exp', validator.exp);
            }
            // 可自定义验证方法，也可以后台验证
            if (validator.fn != null && validator.fn != "") {
                verify_block.attr('data-validate-fn', validator.fn);
            }

            // 支持边输入边验证
            if ($("#" + targetid).is('input'))
                $("#" + targetid).bind('keyup', verifytargetthis);

            if ($("#" + targetid).is('select'))
                $("#" + targetid).bind('change', verifytargetthis);

            if ($("#" + targetid).is('textarea'))
                $("#" + targetid).bind('keyup', verifytargetthis);
        }
    }
}

$.fn.validateFormReset = function () {
    $(this).find("[id*=data-validate-block-]").each(function () {
        var attr = $(this).attr('data-validate-attr');
        var targetid = $(this).attr('data-validate-fortarget');
        if (!$("#" + targetid).is('button')) {
            if (attr != null && attr != "") {
                $("#" + targetid).attr(attr, '');
                try {
                    $("#" + targetid).val('');
                } catch (e) {
                }
            } else {
                $("#" + targetid).val('');
            }
        }
    });
    $(this).find("[id*=data-validate-block-]").addClass('hide');
    $(this).find(".form-group").removeClass('has-error');
}


$.fn.validateTargetReset = function (resetval) {
    var targetid = $(this).attr('id');
    $(this).parent().find("[data-validate-fortarget=" + targetid + "]").each(
        function () {
            if (resetval == null || resetval == true) {
                var attr = $(this).attr('data-validate-attr');
                var targetid = $(this).attr('data-validate-fortarget');
                if (attr != null && attr != "") {
                    $("#" + targetid).attr(attr, '');
                } else {
                    $("#" + targetid).val('');
                }
            }
            $(this).addClass('hide');
        });

    var part = $(this).parent();
    if (!part.hasClass('form-group')) {
        part = $(this).parent().parent();
        if (part.hasClass('form-group')) {
            part.removeClass('has-error');
        }
    }
}

$.fn.validateFormClear = function () {
    $(this).find("[id*=data-validate-block-]").remove();
    $(this).find(".data-validate-target").removeClass('data-validate-target');
    $(this).find(".form-group").removeClass('has-error');
}

$.fn.validateTargetClear = function () {
    var targetid = $(this).attr('id');

    $(this).parent().find("[data-validate-fortarget=" + targetid + "]")
        .remove();
    $(this).removeClass('data-validate-target');

    var part = $(this).parent();
    if (!part.hasClass('form-group')) {
        part = $(this).parent().parent();
        if (part.hasClass('form-group')) {
            part.removeClass('has-error');
        }
    }
}

/** ****************************封装过的ajax提交工具,************************************* */

// 带显示正在加载提示，success返回true，则自动关闭正在加载对话框，可用于多接口调用
function ajaxloading(url, data, success, loadingtext) {
    showloading(loadingtext);
    $.ajax({
        url: url,
        type: 'post',
        //timeout : 15000,
        dataType: 'json',
        data: data,
        success: function (result) {
            var ret = success(result, data);
            if (ret == null || ret == true)
                hideloading();
        },
        error: function (XMLHttpRequest, status) {
            hideloading();
            if (status == 'timeout') {
                alert('请求超时');
            }
        }
    });
}

function ajax(url, data, success, failed) {
    $.ajax({
        url: url,
        type: 'post',
        //timeout : 15000,// 请求超时
        dataType: 'json',
        data: data,
        success: function (result) {
            success(result, data);
        },
        error: function (XMLHttpRequest, status) {
            if (status == 'timeout') {
                alert('请求超时');
            }
            if (failed != null)
                failed();
        }
    });
}

// 文件上传
function ajaxfileupload(url, uploadname, uploadfileids, data, success) {
    for (uploadfileid in uploadfileids) {
        $("#" + uploadfileids[uploadfileid]).prop('name', uploadname);
    }

    $.ajaxFileUpload({
        url: url, // 需要链接到服务器地址
        secureuri: false,
        fileElementId: uploadfileids, // 文件选择框的id属性
        data: data,
        dataType: 'json', // 服务器返回的格式，可以是json
        success: function (result) // 相当于java中try语句块的用法
        {
            success(result, data);
        },
        error: function (XMLHttpRequest, status) {
            if (status == 'timeout') {
                alert('请求超时');
            }
        }
    });
}

// 带正在上传等待窗口的文件上传接口
function ajaxfileuploading(url, uploadname, uploadfileids, data, success) {
    for (uploadfileid in uploadfileids) {
        $("#" + uploadfileids[uploadfileid]).prop('name', uploadname);
    }

    showloading("正在上传,请稍候...");
    $.ajaxFileUpload({
        url: url, // 需要链接到服务器地址
        secureuri: false,
        fileElementId: uploadfileids, // 文件选择框的id属性
        data: data,
        dataType: 'json', // 服务器返回的格式，可以是json
        success: function (result) // 相当于java中try语句块的用法
        {
            var ret = success(result, data);
            if (ret == null || ret == true)
                hideloading();
        },
        error: function (XMLHttpRequest, status) {
            hideloading();
            if (status == 'timeout') {
                alert('请求超时');
            }
        }
    });
}

function autoajaxBind(options) {
    for (var id in options) {
        var autoajax = options[id];

        $("#" + id).attr('autoajax', '1');

        if (autoajax.name != null && autoajax.name != '') {
            $("#" + id).attr('autoajax-name', autoajax.name);
        }
        if (autoajax.attr != null && autoajax.attr != '') {
            $("#" + id).attr('autoajax-attr', autoajax.attr);
        }
        if (autoajax.defaultval != null) {
            $("#" + id).attr('autoajax-defaultval', autoajax.defaultval);
        } else {
            $("#" + id).attr('autoajax-defaultval', '');
        }
    }
}

/**
 * {id:{name:,attr:,defaultval:},id{...}}
 */
$.fn.autoajaxBind = function (option) {
    var data = {};
    var id = $(this).attr('id');
    data[id] = option;
    autoajaxBind(data);
}

$.fn.autoajaxloading = function (url, moredata, success) {
    $(this).autoajax(url, moredata, success, true);
}

// 自动提交，如果提交参数相同，自动以list方式提交
$.fn.autoajax = function (url, moredata, success, ifloading) {
    var map = {};
    // 查找所有带有autoajax class的元素， 对这些元素进行ajax提交
    $(this).find('[autoajax]').each(function () {
        var autoajax_name = $(this).attr('autoajax-name')// 如果有，则取该值为ajax提交数据名，否则取id
        var autoajax_attr = $(this).attr('autoajax-attr')// 如果没有，取val，有则从属性attr取
        var autoajax_defaultval = $(this).attr('autoajax-defaultval'); // 属性的默认值，如果用户没有输入，则取此值进行提交表单

        var name = '';
        var val = '';

        if (autoajax_defaultval == null) {
            autoajax_defaultval = '';
        }

        if (autoajax_name != null && autoajax_name != "") {
            name = autoajax_name;
        } else {
            name = $(this).attr('id');
        }
        if (autoajax_attr != null && autoajax_attr != "") {
            val = $(this).attr(autoajax_attr);
        } else {
            val = $(this).val();
        }

        if (val == null || val == '') {
            val = autoajax_defaultval;
        }

        // 如果名字不为null，则进行提交
        if (name != null) {
            if (map[name] == null) {
                var array = new Array();
                array.push(val);
                map[name] = array;
            } else {
                var array = map[name];
                array.push(val);
            }
        }
    });

    // 添加更多属性
    if (moredata != null) {
        for (name in moredata) {
            var val = moredata[name];
            if (map[name] == null) {
                var array = new Array();
                array.push(val);
                map[name] = array;
            } else {
                var array = map[name];
                array.push(val);
            }
        }
    }
    var data = {};
    for (var name in map) {
        var array = map[name];

        if (array.length == 1) {
            data[name] = array[0];
        } else {
            for (var i = 0; i < array.length; i++) {
                data[name + "[" + i + "]"] = array[i];
            }
        }
    }
    if (ifloading != null && ifloading == true) {
        ajaxloading(url, data, success);
    } else {
        ajax(url, data, success);
    }
}

$.fn.autoajaxfileuploading = function (url, moredata, success, ifloading) {
    $(this).autoajaxfileupload(url, moredata, success, true);
}

$.fn.autoajaxfileupload = function (url, moredata, success, ifloading) {
    var map = {};
    var uploadids = new Array();
    var uploadname = null;
    // 查找所有带有autoajax class的元素， 对这些元素进行ajax提交
    $(this).find('[autoajax]').each(function () {
        if ($(this).is('input') && $(this).prop('type') == 'file') {
            if (uploadname == null) {
                var autoajax_name = $(this).attr('autoajax-name');// 如果有，获取提交的文件接收变量参数名
                if (autoajax_name != null && autoajax_name != "") {
                    uploadname = autoajax_name;
                }
            }

            var id = $(this).attr('id');
            if (id != null && id != '')
                uploadids[uploadids.length] = id;
        } else {
            var autoajax_name = $(this).attr('autoajax-name')// 如果有，则取该值为ajax提交数据名，否则取id
            var autoajax_attr = $(this).attr('autoajax-attr')// 如果没有，取val，有则从属性attr取
            var autoajax_defaultval = $(this).attr('autoajax-defaultval'); // 属性的默认值，如果用户没有输入，则取此值进行提交表单

            var name = '';
            var val = '';
            if (autoajax_name != null && autoajax_name != "") {
                name = autoajax_name;
            } else {
                name = $(this).attr('id');
            }
            if (autoajax_attr != null && autoajax_attr != "") {
                val = $(this).attr(autoajax_attr);
            } else {
                val = $(this).val();
            }

            if (val == null || val == '') {
                val = autoajax_defaultval;
            }

            // 如果名字不为null，则进行提交
            if (name != null) {
                if (map[name] == null) {
                    var array = new Array();
                    array.push(val);
                    map[name] = array;
                } else {
                    var array = map[name];
                    array.push(val);
                }
            }
        }
    });

    // 添加更多属性
    if (moredata != null) {
        for (name in moredata) {
            var val = moredata[name];
            if (map[name] == null) {
                var array = new Array();
                array.push(val);
                map[name] = array;
            } else {
                var array = map[name];
                array.push(val);
            }
        }
    }

    var data = {};
    for (var name in map) {
        var array = map[name];

        if (array.length == 1) {
            data[name] = array[0];
        } else {
            for (var i = 0; i < array.length; i++) {
                data[name + "[" + i + "]"] = array[i];
            }
        }
    }
    if (ifloading != null && ifloading == true) {
        ajaxfileuploading(url, uploadname, uploadids, data, success);
    } else {
        ajaxfileupload(url, uploadname, uploadids, data, success);
    }
}

/** ***************************JQuery插件用于生成基于bootstrap的基础界面UI************************************** */
$.fn.isElementExist = function (id) {
    var cnt = 0;
    $(this).find("#" + id).each(function () {
        cnt++;
    });
    if (cnt > 0)
        return true;
    else
        return false;
}
// {
// --hint: 提示内容
// --disabled: true/false，是否禁止输入
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --defaultval: 默认值
// --moreclass: 类, 以逗号分隔
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// --}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --.....
// --}
// }
$.fn.addinputtext = function (options) {
    var hint, disabled, id, defaultval, autoajax, validators, moreclass;
    if (options != null) {
        moreclass = options.moreclass;
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
    }
    if (id == null || id == '') {
        id = 'input' + rand(0, 999999999);
    }

    // 如果元素不存在，则创建元素
    if (!$(this).isElementExist(id)) {
        var input = $('<input id=\"' + id
            + '\" class="form-control" type="text">');
        $(this).append(input);

        if (moreclass != null && moreclass != '') {
            moreclass = moreclass.split(',');
            for (var i in moreclass) {
                if (moreclass[i] != '') {
                    input.addClass(moreclass[i]);
                }
            }
        }
    }

    // 如果设置了默认值，则设置
    if (defaultval != null) {
        $("#" + id).val(defaultval);
    }

    // 如果设置了disabled，则
    if (disabled != null) {
        if (disabled == true) {
            $("#" + id).attr('disabled', '');
        } else if (disabled == false) {
            $("#" + id).removeAttr('disabled');
        }
    }

    // 如果设置了hint，则
    if (hint != null) {
        if (hint != '') {
            $("#" + id).attr('placeholder', hint);
        } else {
            $("#" + id).removeAttr('placeholder');
        }
    }

    // 如果设置了autoajax，则
    if (autoajax != null) {
        $("#" + id).autoajaxBind(autoajax);
    }

    // 如果设置了validators，则
    if (validators != null) {
        $("#" + id).validateTargetClear();
        $("#" + id).validateTargetBind(validators);
    }

    return id;
}

// {
// --hint: 提示内容
// --disabled: true/false，是否禁止输入
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --defaultval: 默认值
// --moreclass: 类, 以逗号分隔
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// --}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --.....
// --}
// }
$.fn.addinputpassword = function (options) {
    var hint, disabled, id, defaultval, autoajax, validators, moreclass;
    if (options != null) {
        moreclass = options.moreclass;
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
    }
    if (id == null || id == '') {
        id = 'input' + rand(0, 999999999);
    }

    // 如果元素不存在，则创建元素
    if (!$(this).isElementExist(id)) {
        var input = $('<input id=\"' + id
            + '\" class="form-control" type="password">');
        $(this).append(input);
        if (moreclass != null && moreclass != '') {
            moreclass = moreclass.split(',');
            for (var i in moreclass) {
                if (moreclass[i] != '') {
                    input.addClass(moreclass[i]);
                }
            }
        }
    }

    // 如果设置了默认值，则设置
    if (defaultval != null) {
        $("#" + id).val(defaultval);
    }

    // 如果设置了disabled，则
    if (disabled != null) {
        if (disabled == true) {
            $("#" + id).attr('disabled', '');
        } else if (disabled == false) {
            $("#" + id).removeAttr('disabled');
        }
    }

    // 如果设置了hint，则
    if (hint != null) {
        if (hint != '') {
            $("#" + id).attr('placeholder', hint);
        } else {
            $("#" + id).removeAttr('placeholder');
        }
    }

    // 如果设置了autoajax，则
    if (autoajax != null) {
        $("#" + id).autoajaxBind(autoajax);
    }

    // 如果设置了validators，则
    if (validators != null) {
        $("#" + id).validateTargetClear();
        $("#" + id).validateTargetBind(validators);
    }

    return id;
}

// {
// --hint: 提示内容
// --disabled: true/false，是否禁止输入
// --rows: 行数，默认为6行
// --moreclass: 类，以逗号分隔
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --defaultval: 默认值
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// --}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --.....
// --}
// }
$.fn.addtextarea = function (options) {
    var hint, disabled, id, defaultval, autoajax, validators, rows, moreclass;
    if (options != null) {
        moreclass = options.moreclass;
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
        rows = options.rows;
    }
    if (id == null || id == '') {
        id = 'textarea' + rand(0, 999999999);
    }

    // 如果元素不存在，则创建元素
    if (!$(this).isElementExist(id)) {
        var textarea = $('<textarea id=\"' + id
            + '\" class="form-control" rows=6></textarea>');
        $(this).append(textarea);

        if (moreclass != null && moreclass != '') {
            moreclass = moreclass.split(',');
            for (var i in moreclass) {
                if (moreclass[i] != '') {
                    textarea.addClass(moreclass[i]);
                }
            }
        }
    }

    if (defaultval != null) {
        $("#" + id).text(defaultval);
    }

    if (rows != null) {
        $("#" + id).attr('rows', rows);
    }

    // 如果设置了hint，则
    if (hint != null) {
        if (hint != '') {
            $("#" + id).attr('placeholder', hint);
        } else {
            $("#" + id).removeAttr('placeholder');
        }
    }

    // 如果设置了disabled，则
    if (disabled != null) {
        if (disabled == true) {
            $("#" + id).attr('disabled', '');
        } else if (disabled == false) {
            $("#" + id).removeAttr('disabled');
        }
    }

    if (autoajax != null)
        $("#" + id).autoajaxBind(autoajax);

    if (validators != null) {
        $("#" + id).validateTargetClear();
        $("#" + id).validateTargetBind(validators);
    }
    return id;
}

// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --hint: 提示内容
// --disabled: true/false，是否禁止输入
// --defaultval: 默认值，以逗号分隔
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --data:{
// ----key:val,
// ----key:val,
// ----key:val,
// ----}
// --onchange:function(val)
// --}
// }
$.fn.addmultiselect = function (options) {
    var hint, disabled, id, defaultval, autoajax, validators, data;
    var onchange = null;
    if (options != null) {
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
        data = options.data;
        onchange = options.onchange;
    }
    if (id == null || id == '') {
        id = 'multiselect' + rand(0, 999999999);
    }

    if (!$(this).isElementExist(id)) {
        if (defaultval == null) {
            defaultval = '';
        }
        defaultval = ("" + defaultval).split(',');

        if (hint == null) {
            hint = '暂无选择';
        }
        if (disabled != null && disabled == true) {
            disabled = 'disabled';
        } else {
            disabled = '';
        }
        var multiselect = $('<select id=\"multiselect' + id
            + '\" multiple="multiple" ' + disabled + '></select>');
        var selectlength = 0;
        if (data != null) {
            for (key in data) {
                var value = data[key];
                var selected = '';
                for (var index in defaultval) {
                    if (defaultval[index] != null && defaultval[index] != ''
                        && key == defaultval[index]) {
                        selected = 'selected';
                    }
                }
                multiselect.append('<option id=\"multioption' + id + "_" + key
                    + '\" ' + selected + ' value=\"' + key + '\">' + value
                    + '</option>');
                selectlength++;
            }
        }
        $(this).append(multiselect);

        // 为multiselect插件添加容器，并指定id
        $("#multiselect" + id).attr('selectlength', selectlength);
        var container = '<div class="btn-group" id=\"div' + id + '\"/>'
        // 使用multiselect插件添加多选功能
        $("#multiselect" + id).multiselect(
            {
                disableIfEmpty: true,
                buttonWidth: '100%',
                maxHeight: 300,
                nonSelectedText: hint,
                buttonContainer: container,
                onChange: function (element, checked) { // 当点击时，触发选择
                    var selitemstr = $('#' + id).attr('selitem');
                    var selitems = new Array();
                    if (selitemstr != null && selitemstr != '')
                        selitems = selitemstr.split(',');

                    var val = element.val();
                    if (checked == true) {
                        selitems[selitems.length] = val;
                    } else {
                        for (var index in selitems) {
                            if (selitems[index] == val) {
                                selitems.splice(index, 1);
                                break;
                            }
                        }
                    }
                    $('#' + id).attr('selitem', selitems.join(','));

                    if (onchange != null)
                        onchange(selitems);
                },
                onDropdownHide: function () {
                    // 如果有验证器，则进行验证
                    if (validators) {
                        verifytarget(id, null, null, false);
                    }
                },
                onDropdownShow: function () {
                    if (validators) {
                        $('#' + id).validateTargetReset(false);
                    }
                },
                buttonText: function (options) {
                    var selectlength = $("#" + id).attr('selectlength');
                    if (options.length == 0) {
                        return hint;
                    } else if (options.length == selectlength) {
                        return ' 已选择全部 ';
                    } else if (options.length > 3) {
                        return ' 已选择' + options.length + ' 项 ';
                    } else {
                        var selected = [];
                        options.each(function () {
                            selected.push([$(this).text(),
                                $(this).data('order')]);
                        });
                        selected.sort(function (a, b) {
                            return a[1] - b[1];
                        })
                        var text = '';
                        for (var i = 0; i < selected.length; i++) {
                            text += selected[i][0] + ', ';
                        }
                        return text.substr(0, text.length - 2) + ' ';
                    }
                }
            });

        // 设置该插件的ID
        $("#div" + id).find('.multiselect').attr('id', id);

        // 设置默认值
        $("#" + id).attr('selitem', defaultval.join(','));
        // 绑定验证器
        if (validators != null) {
            // 为每一个validator添加attr:selitem字段
            for (var validator in validators) {
                validators[validator].attr = 'selitem';
            }
            $("#" + id).validateTargetBind(validators);
        }
        if (autoajax != null) {
            // 为自动提交插件添加selitem属性
            autoajax.attr = 'selitem';
            $("#" + id).autoajaxBind(autoajax);
        }
    } else {
        // 如果元素已存在，如果设置这些值
        if (defaultval != null && defaultval != '')
            var defaultvalsplits = defaultval.split(",");
        $("#" + id).attr('selitem', defaultval);
        for (var index in defaultvalsplits) {
            var val = defaultvalsplits[index];
            $("#multiselect" + id).multiselect('select', val);
        }
    }

    return id;
}

// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --nohint: true/false
// --onchange: 更改时的事件
// --hint: 提示内容
// --disabled: true/false，是否禁止输入
// --selectclass: 类，以逗号分隔
// --optionclass: 类，以逗号分隔
// --defaultval: 默认值
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --data:{
// ----key:val,
// ----key:val,
// ----key:val,
// ----}
// --.....
// --}
// }
$.fn.addselect = function (options) {
    var hint, disabled, id, defaultval, autoajax, validators, data, selectclass, optionclass;
    var nohint;
    if (options != null) {
        selectstyle = options.selectstyle;
        selectclass = options.selectclass;
        optionclass = options.optionclass;
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
        data = options.data;
        nohint = options.nohint;
    }
    if (id == null || id == '') {
        id = 'select' + rand(0, 999999999);
    }
    if (selectstyle == null)
        selectstyle = "";

    if (!$(this).isElementExist(id)) {

        if (defaultval == null) {
            defaultval = '';
        }

        // 单选默认值只可能为一个
        defaultval = ("" + defaultval).split(',');
        if (defaultval.length > 0) {
            defaultval = defaultval[0];
        }

        if (hint == null) {
            hint = '--请选择--';
        }

        if (disabled != null && disabled == true) {
            disabled = 'disabled';
        } else {
            disabled = '';
        }
        var select = $('<select id=\"' + id + '\" ' + disabled
            + ' class="form-control" style="width:98%;padding-left:5px;padding-right:5px;' + selectstyle + '"></select>');

        if (nohint == null || (nohint == false || nohint == '0')) {
            select.append($('<option value="">' + hint + '</option>'));
        }
        if (data != null) {
            for (key in data) {
                var value = data[key];
                var selected = '';
                if (defaultval == key)
                    selected = 'selected';
                var option = '<option id=\"' + id + "_" + key + '\" '
                    + selected + ' value=\"' + key + '\">' + value
                    + '</option>';
                select.append(option);

                if (optionclass != null && optionclass != '') {
                    optionclass = optionclass.split(',');
                    for (var i in optionclass) {
                        if (optionclass[i] != '') {
                            option.addClass(optionclass[i]);
                        }
                    }
                }
            }
        }
        $(this).append(select);

        if (selectclass != null && selectclass != '') {
            selectclass = selectclass.split(',');
            for (var i in selectclass) {
                if (selectclass[i] != '') {
                    select.addClass(selectclass[i]);
                }
            }
        }

        // 绑定验证器
        if (validators != null) {
            $("#" + id).validateTargetBind(validators);
        }
        if (autoajax != null) {
            $("#" + id).autoajaxBind(autoajax);
        }

        if (options.onchange != null) {
            select.change(options.onchange);
        }
    } else {
        if (defaultval != null) {
            $("#" + id).find('option').each(function () {
                if (("" + defaultval) == $(this).val()) {
                    $(this).attr('selected', '');
                } else {
                    $(this).removeAttr('selected');
                }
            });
        }
    }
    return id;
}

// {
// --idstart: 如果不指定id，默认会自动生成，并由接口返回
// --idend: 如果不指定id，默认会自动生成，并由接口返回
// --hintstart: 开始时间选择提示
// --hintend: 结束时间选择提示
// --format: yyyy-MM-dd 时间的显示格式，时间的标准格式为yyyy-MM-dd HH:mm:ss
// --disabledstart: true/false,禁止选择开始日期
// --disabledend: true/false，禁止选择结束日期
// --defaultvalstart: 默认值，开始日期
// --defaultvalend: 默认值，结束日期
// --onpickedstart: 
// --onpickedend:
// --min: 允许选择的最小值
// --max: 允许选择的最大值
// --autoajaxstart: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --autoajaxend: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validatorsstart:{
// ----notempty:{
// ----},
// --validatorsend:{
// ----notempty:{
// ----},
// --.....
// --}
// }
$.fn.addmonthregion = function (options) {
    var idstart, idend, hintstart, hintend, format, disabledstart, disabledend, defaultvalstart, defaultvalend, max, min, autoajaxstart, autoajaxend, validatorsstart, validatorsend;
    var hint, disabled, id, defaultval, autoajax, validators, format, min, max;
    var onpickedstart, onpickedend;
    if (options != null) {
        idstart = options.idstart;
        idend = options.idend;
        hintstart = options.hintstart;
        hintend = options.hintend;
        format = options.format;
        disabledstart = options.disabledstart;
        disabledend = options.disabledend;
        defaultvalstart = options.defaultvalstart;
        defaultvalend = options.defaultvalend;
        max = options.max;
        min = options.min;
        autoajaxstart = options.autoajaxstart;
        autoajaxend = options.autoajaxend;
        validatorsstart = options.validatorsstart
        validatorsend = options.validatorsend;
        onpickedstart = options.onpickedstart;
        onpickedend = options.onpickedend;
    }
    if (idstart == null || idstart == '') {
        idstart = 'selmonthstart' + rand(0, 999999999);
    }
    if (idend == null || idend == '') {
        idend = 'selmonthend' + rand(0, 999999999);
    }
    var divid = "divcontainer" + rand(0, 99999999);
    var div = $('<div id=\"' + divid + '\"></div>');
    $(this).append(div);

    div.addmonth({
        id: idstart,
        hint: hintstart,
        disabled: disabledstart,
        format: format,
        defaultval: defaultvalstart,
        onpicked: onpickedstart,
        min: min,
        max: '#F{$dp.$D(\\\'' + idend + '\\\')}',
        autoajax: autoajaxstart,
        container: '<div class="input-group" style="width:46%;float:left;">'
    });
    div.append('<hr style="width:2%;margin:20px 0 0 5px;float:left;border:none; border-top:2px solid #888888;"/>');
    div.addmonth({
        id: idend,
        hint: hintend,
        disabled: disabledend,
        format: format,
        onpicked: onpickedend,
        defaultval: defaultvalend,
        min: '#F{$dp.$D(\\\'' + idstart + '\\\')}',
        max: max,
        autoajax: autoajaxend,
        container: '<div class="input-group" style="width:46%;float:left;margin-left:5px;">'
    });
    if (validatorsend != null) {
        // 为每一个validator添加attr:selitem字段
        for (var validator in validatorsend) {
            validatorsend[validator].attr = 'realvalue';
        }
        validatorsend.settings = {
            posafter: divid
        };
        $("#" + idend).validateTargetBind(validatorsend);
    }

    if (validatorsstart != null) {
        // 为每一个validator添加attr:selitem字段
        for (var validator in validatorsstart) {
            validatorsstart[validator].attr = 'realvalue';
        }
        validatorsstart.settings = {
            posafter: divid
        };
        $("#" + idstart).validateTargetBind(validatorsstart);
    }
}

$.fn.adddayregion = function (options) {
    var idstart, idend, hintstart, hintend, format, disabledstart, disabledend, defaultvalstart, defaultvalend, max, min, autoajaxstart, autoajaxend, validatorsstart, validatorsend;
    var hint, disabled, id, defaultval, autoajax, validators, format, min, max;
    var onpickedstart, onpickedend;
    if (options != null) {
        idstart = options.idstart;
        idend = options.idend;
        hintstart = options.hintstart;
        hintend = options.hintend;
        format = options.format;
        disabledstart = options.disabledstart;
        disabledend = options.disabledend;
        defaultvalstart = options.defaultvalstart;
        defaultvalend = options.defaultvalend;
        max = options.max;
        min = options.min;
        autoajaxstart = options.autoajaxstart;
        autoajaxend = options.autoajaxend;
        validatorsstart = options.validatorsstart
        validatorsend = options.validatorsend;
        onpickedstart = options.onpickedstart;
        onpickedend = options.onpickedend;
    }
    if (idstart == null || idstart == '') {
        idstart = 'seldaystart' + rand(0, 999999999);
    }
    if (idend == null || idend == '') {
        idend = 'seldayend' + rand(0, 999999999);
    }
    var divid = "divcontainer" + rand(0, 99999999);
    var div = $('<div id=\"' + divid + '\"></div>');
    $(this).append(div);

    div.addday({
        id: idstart,
        hint: hintstart,
        disabled: disabledstart,
        format: format,
        defaultval: defaultvalstart,
        onpicked: onpickedstart,
        min: min,
        max: '#F{$dp.$D(\\\'' + idend + '\\\')}',
        autoajax: autoajaxstart,
        container: '<div class="input-group" style="width:46%;float:left;">'
    });
    div
        .append('<hr style="width:2%;margin-left:5px;float:left;border:none; border-top:2px solid #888888;"/>');
    div
        .addday({
            id: idend,
            hint: hintend,
            disabled: disabledend,
            format: format,
            onpicked: onpickedend,
            defaultval: defaultvalend,
            min: '#F{$dp.$D(\\\'' + idstart + '\\\')}',
            max: max,
            autoajax: autoajaxend,
            container: '<div class="input-group" style="width:46%;float:left;margin-left:5px;">'
        });
    if (validatorsend != null) {
        // 为每一个validator添加attr:selitem字段
        for (var validator in validatorsend) {
            validatorsend[validator].attr = 'realvalue';
        }
        validatorsend.settings = {
            posafter: divid
        };
        $("#" + idend).validateTargetBind(validatorsend);
    }

    if (validatorsstart != null) {
        // 为每一个validator添加attr:selitem字段
        for (var validator in validatorsstart) {
            validatorsstart[validator].attr = 'realvalue';
        }
        validatorsstart.settings = {
            posafter: divid
        };
        $("#" + idstart).validateTargetBind(validatorsstart);
    }
}


$.fn.addtimeregion = function (options) {
    var idstart, idend, hintstart, hintend, format, disabledstart, disabledend, defaultvalstart, defaultvalend, max, min, autoajaxstart, autoajaxend, validatorsstart, validatorsend;
    var hint, disabled, id, defaultval, autoajax, validators, format, min, max;
    var onpickedstart, onpickedend;
    if (options != null) {
        idstart = options.idstart;
        idend = options.idend;
        hintstart = options.hintstart;
        hintend = options.hintend;
        format = options.format;
        disabledstart = options.disabledstart;
        disabledend = options.disabledend;
        defaultvalstart = options.defaultvalstart;
        defaultvalend = options.defaultvalend;
        max = options.max;
        min = options.min;
        autoajaxstart = options.autoajaxstart;
        autoajaxend = options.autoajaxend;
        validatorsstart = options.validatorsstart
        validatorsend = options.validatorsend;
        onpickedstart = options.onpickedstart;
        onpickedend = options.onpickedend;
    }
    if (idstart == null || idstart == '') {
        idstart = 'seltimestart' + rand(0, 999999999);
    }
    if (idend == null || idend == '') {
        idend = 'seltimeend' + rand(0, 999999999);
    }
    var divid = "divcontainer" + rand(0, 99999999);
    var div = $('<div id=\"' + divid + '\"></div>');
    $(this).append(div);

    div.addtime({
        id: idstart,
        hint: hintstart,
        disabled: disabledstart,
        format: format,
        defaultval: defaultvalstart,
        onpicked: onpickedstart,
        min: min,
        max: '#F{$dp.$D(\\\'' + idend + '\\\')}',
        autoajax: autoajaxstart,
        container: '<div class="input-group" style="width:30%;float:left;">'
    });
    div
        .append('<label style="width:2%;margin-left:5px;float:left;margin-top:5px;">至</label>');
    div
        .addtime({
            id: idend,
            hint: hintend,
            disabled: disabledend,
            format: format,
            onpicked: onpickedend,
            defaultval: defaultvalend,
            min: '#F{$dp.$D(\\\'' + idstart + '\\\')}',
            max: max,
            autoajax: autoajaxend,
            container: '<div class="input-group" style="width:30%;float:left;margin-left:5px;margin-right:10px;">'
        });
    if (validatorsend != null) {
        // 为每一个validator添加attr:selitem字段
        for (var validator in validatorsend) {
            validatorsend[validator].attr = 'realvalue';
        }
        validatorsend.settings = {
            posafter: divid
        };
        $("#" + idend).validateTargetBind(validatorsend);
    }

    if (validatorsstart != null) {
        // 为每一个validator添加attr:selitem字段
        for (var validator in validatorsstart) {
            validatorsstart[validator].attr = 'realvalue';
        }
        validatorsstart.settings = {
            posafter: divid
        };
        $("#" + idstart).validateTargetBind(validatorsstart);
    }
}

// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --disabled: true/false,禁止选择日期
// --format: 以format格式化显示，默认为yyyy
// --defaultval: 默认值，日期，以yyyy格式化
// --min: 可选的最小日期,以yyyy格式化
// --max：可选的最大日期,以yyyy格式化
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --moresetting: //用于设置更多wdate参数
// --container: //容器，默认为<div class="input-group">
// --validatorposafter: //验证信息存入位置
// --.....
// --}
// }
$.fn.addyear = function (options) {
    if (options == null)
        options = {};

    options.type = 'year';
    return $(this).addday(options);
}

// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --disabled: true/false,禁止选择日期
// --format: 以format格式化显示，默认为yyyy-MM
// --defaultval: 默认值，日期，以yyyy-MM格式化
// --min: 可选的最小日期,以yyyy-MM格式化
// --max：可选的最大日期,以yyyy-MM格式化
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --moresetting: //用于设置更多wdate参数
// --container: //容器，默认为<div class="input-group">
// --validatorposafter: //验证信息存入位置
// --.....
// --}
// }
$.fn.addmonth = function (options) {
    if (options == null)
        options = {};

    options.type = "month";
    return $(this).addday(options);
}

$.fn.addonlytime = function (options) {
    if (options == null)
        options = {};

    options.type = "onlytime";
    return $(this).addday(options);
}

// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --type: year month day time
// --disabled: true/false,禁止选择日期
// --format: 以format格式化显示，默认为yyyy-MM-dd
// --defaultval: 默认值，日期，以yyyy-MM-dd格式化
// --min: 可选的最小日期,以yyyy-MM-dd格式化
// --max：可选的最大日期,以yyyy-MM-dd格式化
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --onpicked: 
// --moresetting: //用于设置更多wdate参数
// --container: //容器，默认为<div class="input-group">
// --validatorposafter: //验证信息存入位置
// --.....
// --}
// }
$.fn.addday = function (options) {
    var hint, type, disabled, id, defaultval, autoajax, validators, format, min, max, moresetting, container, validatorposafter;
    if (options != null) {
        id = options.id;
        type = options.type;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
        format = options.format;
        min = options.min;
        max = options.max;
        moresetting = options.moresetting;
        container = options.container;
        validatorposafter = options.validatorposafter;
    }

    if (id == null || id == '') {
        id = 'selday' + rand(0, 999999999);
    }

    if (!$(this).isElementExist(id)) {
        if (type == null || type == '') {
            type = 'day';
        }

        if (format == null || format == '') {
            if (type == 'year') {
                format = 'yyyy年';
                if (defaultval != null && defaultval != '') {
                    defaultval = getTimeFromStr(defaultval, 'yyyy')
                } else {
                    defaultval = getTimeFromStr('1970-01-01 00:00:00',
                        'yyyy-MM-dd HH:mm:ss');
                }
            } else if (type == 'month') {
                format = 'yyyy年MM月';
                if (defaultval != null && defaultval != '') {
                    defaultval = getTimeFromStr(defaultval, 'yyyy-MM')
                } else {
                    defaultval = getTimeFromStr('1970-01-01 00:00:00',
                        'yyyy-MM-dd HH:mm:ss');
                }
            } else if (type == 'time') {
                format = 'yyyy年MM月dd日 HH:mm:ss';
                if (defaultval != null && defaultval != '') {
                    defaultval = getTimeFromStr(defaultval, 'yyyy-MM-dd HH:mm:ss')
                } else {
                    defaultval = getTimeFromStr('1970-01-01 00:00:00',
                        'yyyy-MM-dd HH:mm:ss');
                }
            } else if (type == "onlytime") {
                format = 'HH:mm:ss';
                if (defaultval != null && defaultval != '') {
                    defaultval = getTimeFromStr(defaultval, 'HH:mm:ss')
                } else {
                    defaultval = getTimeFromStr('1970-01-01 00:00:00',
                        'yyyy-MM-dd HH:mm:ss');
                }
            } else {
                format = 'yyyy年MM月dd日';
                if (defaultval != null && defaultval != '') {
                    defaultval = getTimeFromStr(defaultval, 'yyyy-MM-dd')
                } else {
                    defaultval = getTimeFromStr('1970-01-01 00:00:00',
                        'yyyy-MM-dd HH:mm:ss');
                }
            }
        }

        if (hint != null && hint != '') {
            hint = 'placeholder=\"' + hint + '\"';
        } else {
            hint = '';
        }

        var div = null;
        if (container == null || container == '')
            div = $('<div class="input-group"></div>');
        else
            div = $(container);
        if (max != null && max != '') {
            max = ' ,maxDate:\'' + max + '\'';
        } else {
            max = '';
        }

        if (moresetting == null || moresetting == '') {
            moresetting = '';
        } else {
            moresetting = moresetting + ',';
        }

        if (min != null && min != '') {
            min = ' ,minDate:\'' + min + '\'';
        } else {
            min = '';
        }

        if (disabled == null || disabled == false) {
            if (defaultval.getTime() == getTimeFromStr('1970-01-01 00:00:00',
                    'yyyy-MM-dd HH:mm:ss').getTime()) {
                div
                    .append($('<input class="col-xs-6 form-control" id=\"'
                        + id
                        + '\" type="text" readonly style="background:white;" realvalue=\"\" '
                        + hint + '>'));

                div.append($('<span class="input-group-addon" onclick="'
                    + 'var x= document.getElementById(\'' + id
                    + '\').getBoundingClientRect().left;'
                    + 'var y =document.getElementById(\'' + id
                    + '\').getBoundingClientRect().top + $(\'#' + id
                    + '\').outerHeight();'
                    + 'WdatePicker({position:{left:x,top:y},' + moresetting
                    + 'onpicked:function(){verifytarget(\'' + id
                    + '\', null, null, false);'
                    + ((options.onpicked == null) ? '' : options.onpicked + "();") + '},oncleared:function(){verifytarget(\'' + id
                    + '\', null, null, false);'
                    + ((options.onpicked == null) ? '' : options.onpicked + "();") + '},el:\'' + id
                    + '\',dateFmt:\'' + format + '\'' + max + min
                    + '})"><i class="icon-time"></i></span>'));
            } else {
                var realval = "";
                if (type == 'year') {
                    realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                } else if (type == 'month') {
                    realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                } else if (type == 'time') {
                    realval = getTimeFmt(defaultval, 'yyyy-MM-dd HH:mm:ss');
                } else if (type == 'onlytime') {
                    realval = getTimeFmt(defaultval, 'HH:mm:ss');
                } else {
                    realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                }
                div
                    .append($('<input class="form-control" id=\"'
                        + id
                        + '\" type="text" readonly style="background:white;" realvalue=\"'
                        + realval + '\" ' + hint + ' value=\''
                        + getTimeFmt(defaultval, format) + '\'>'));
                div.append($('<span class="input-group-addon" onclick="'
                    + 'var x= document.getElementById(\'' + id
                    + '\').getBoundingClientRect().left;'
                    + 'var y =document.getElementById(\'' + id
                    + '\').getBoundingClientRect().top + $(\'#' + id
                    + '\').outerHeight();'
                    + 'WdatePicker({position:{left:x,top:y},' + moresetting
                    + 'onpicked:function(){verifytarget(\'' + id
                    + '\', null, null, false);'
                    + ((options.onpicked == null) ? '' : options.onpicked + "();") + '},startDate:\''
                    + getTimeFmt(defaultval, 'yyyy-MM-dd HH:mm:ss')
                    + '\',el:\'' + id + '\',dateFmt:\'' + format + '\''
                    + max + min + '});"><i class="icon-time"></i></span>'));
            }
        } else {
            if (defaultval.getTime() == getTimeFromStr('1970-01-01 00:00:00',
                    'yyyy-MM-dd HH:mm:ss').getTime()) {
                div.append($('<input class="form-control" id=\"' + id
                    + '\" type="text" readonly realvalue=\"\" ' + hint
                    + '>'));
                div
                    .append($('<span class="input-group-addon"><i class="icon-time"></i></span>'));
            } else {
                var realval = "";
                if (type == 'year') {
                    realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                } else if (type == 'month') {
                    realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                } else if (type == 'time') {
                    realval = getTimeFmt(defaultval, 'yyyy-MM-dd HH:mm:ss');
                } else if (type == 'onlytime') {
                    realval = getTimeFmt(defaultval, 'HH:mm:ss');
                } else {
                    realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                }
                div.append($('<input class="form-control" id=\"' + id
                    + '\" type="text" readonly realvalue=\"' + realval
                    + '\" ' + hint + ' value=\''
                    + getTimeFmt(defaultval, format) + '\'>'));
                div
                    .append($('<span class="input-group-addon"><i class="icon-time"></i></span>'));
            }
        }
        $(this).append(div);
        $("#" + id).attr('format', format);
        $("#" + id).attr('timetype', type);
        div.attr('id', 'div' + id);
        // 绑定验证器
        if (validators != null) {
            // 为每一个validator添加attr:selitem字段
            for (var validator in validators) {
                validators[validator].attr = 'realvalue';
            }
            if (validatorposafter != null && validatorposafter != '')
                validators.settings = {
                    posafter: validatorposafter
                };
            else
                validators.settings = {
                    posafter: 'div' + id
                };
            $("#" + id).validateTargetBind(validators);
        }
        if (autoajax != null) {
            // 为自动提交插件添加selitem属性
            autoajax.attr = 'realvalue';
            if (autoajax.defaultval == null || autoajax.defaultval == '') {
                if (type == 'year') {
                    autoajax.defaultval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                } else if (type == 'month') {
                    autoajax.defaultval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                } else if (type == 'time') {
                    autoajax.defaultval = getTimeFmt(defaultval,
                        'yyyy-MM-dd HH:mm:ss');
                } else if (type == 'onlytime') {
                    autoajax.defaultval = getTimeFmt(defaultval,
                        'HH:mm:ss');
                } else {
                    autoajax.defaultval = getTimeFmt(defaultval, 'yyyy-MM-dd');
                }
            }
            $("#" + id).autoajaxBind(autoajax);
        }
    } else {
        var format = $("#" + id).attr('format');
        var type = $("#" + id).attr('timetype');

        if (type == 'year') {
            if (defaultval != null && defaultval != '') {
                defaultval = getTimeFromStr(defaultval, 'yyyy')
            } else {
                defaultval = getTimeFromStr('1970-01-01 00:00:00',
                    'yyyy-MM-dd HH:mm:ss');
            }
        } else if (type == 'month') {
            if (defaultval != null && defaultval != '') {
                defaultval = getTimeFromStr(defaultval, 'yyyy-MM')
            } else {
                defaultval = getTimeFromStr('1970-01-01 00:00:00',
                    'yyyy-MM-dd HH:mm:ss');
            }
        } else if (type == 'time') {
            if (defaultval != null && defaultval != '') {
                defaultval = getTimeFromStr(defaultval, 'yyyy-MM-dd HH:mm:ss')
            } else {
                defaultval = getTimeFromStr('1970-01-01 00:00:00',
                    'yyyy-MM-dd HH:mm:ss');
            }
        } else if (type == "onlytime") {
            if (defaultval != null && defaultval != '') {
                defaultval = getTimeFromStr(defaultval, 'HH:mm:ss')
            } else {
                defaultval = getTimeFromStr('1970-01-01 00:00:00',
                    'yyyy-MM-dd HH:mm:ss');
            }
        } else {
            if (defaultval != null && defaultval != '') {
                defaultval = getTimeFromStr(defaultval, 'yyyy-MM-dd')
            } else {
                defaultval = getTimeFromStr('1970-01-01 00:00:00',
                    'yyyy-MM-dd HH:mm:ss');
            }
        }

        if (defaultval.getTime() != getTimeFromStr('1970-01-01 00:00:00',
                'yyyy-MM-dd HH:mm:ss').getTime()) {
            var realval = "";
            if (type == 'year') {
                realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
            } else if (type == 'month') {
                realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
            } else if (type == 'time') {
                realval = getTimeFmt(defaultval, 'yyyy-MM-dd HH:mm:ss');
            } else if (type == 'onlytime') {
                realval = getTimeFmt(defaultval, 'HH:mm:ss');
            } else {
                realval = getTimeFmt(defaultval, 'yyyy-MM-dd');
            }

            $("#" + id).attr('realvalue', realval);
            $("#" + id).val(getTimeFmt(defaultval, format));
        } else {
            $("#" + id).attr('realvalue', '');
            $("#" + id).val('');
        }
    }
    return id;
}

// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --disabled: true/false,禁止选择日期
// --format: 以format格式化显示，默认为yyyy-MM-dd HH:mm:ss
// --defaultval: 默认值，日期，以yyyy-MM-dd HH:mm:ss格式化
// --min: 可选的最小日期,以yyyy-MM-dd HH:mm:ss格式化
// --max：可选的最大日期,以yyyy-MM-dd HH:mm:ss格式化
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --moresetting: //用于设置更多wdate参数
// --container: //容器，默认为<div class="input-group">
// --validatorposafter: //验证信息存入位置
// --.....
// --}
// }
$.fn.addtime = function (options) {
    if (options == null)
        options = {};
    options.type = "time";
    return $(this).addday(options);
}

$.fn.addcheckbox = function (options) {

}
$.fn.addcheckboxgroup = function (options) {

}
$.fn.addradiogroup = function (options) {

}

// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --defaultval: 默认值
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --}
// }
$.fn.addhidden = function (options) {
    var id, defaultval, autoajax;
    if (options != null) {
        id = options.id;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
    }
    if (id == null || id == '') {
        id = 'hidden' + rand(0, 999999999);
    }
    if (!$(this).isElementExist(id)) {
        if (defaultval == null) {
            defaultval = '';
        }

        var input = $('<input id=\"' + id
            + '\" class="form-control" type="hidden" value=\"' + defaultval
            + '\">');
        $(this).append(input);

        if (autoajax != null)
            $("#" + id).autoajaxBind(autoajax);
    } else {
        if (defaultval != '') {
            $('#' + id).val(defaultval);
        }
        if (autoajax != null)
            $("#" + id).autoajaxBind(autoajax);
    }
    return id;
}

function retrieveData(sSource, aoData, fnCallback) {
    start = getDateTableParam(aoData, "iDisplayStart") + 1;
    var tableid = getDateTableParam(aoData, 'datatables_tableid');

    $("#" + tableid).attr('start', start);
    $("#" + tableid).attr('page', getDateTablePage(aoData));
    $("#" + tableid).attr('rows', getDateTableRows(aoData));

    var nodataforward = getDateTableParam(aoData, 'datatables_nodataforward');
    var datafn = getDateTableParam(aoData, 'datatables_datafn');
    var userpusheditem = getDateTableParamBeginWith(aoData, 'pushed_');
    var data = {};

    for (var userpushed in userpusheditem) {
        var item = userpusheditem[userpushed];
        var key = item.name;
        var value = item.value;
        key = key.substr(7);
        data[key] = value;
    }

    data['sEcho'] = getDateTableParam(aoData, "sEcho");
    data['rows'] = getDateTableRows(aoData);
    data['page'] = getDateTablePage(aoData);

    ajax(sSource, data, function (result, data) {
        if (ifResultOK(result)) {
            var list = new Array();
            var total = 0;
            var sEcho = '';
            if (datafn != null && datafn != '') {
                ret = datafn(result);
                list = ret.data;
                total = ret.total;
                sEcho = ret.secho;
            } else {
                list = getResultRecords(result).data;
                total = getResultRecords(result).total;
                sEcho = getResultMap(result).sEcho;
            }
            $("#" + tableid).attr('count', list.length);
            if (nodataforward == true && list.length <= 0
                && getDateTablePage(aoData) > 1) {
                var columndefs = getDateTableParam(aoData,
                    "datatables_columndefs");
                var columns = getDateTableParam(aoData, "datatables_columns");
                var fncreatedrow = getDateTableParam(aoData,
                    "datatables_fncreatedrow");
                var options = {};
                options.url = sSource;
                options.data = data;
                options.page = getDateTablePage(aoData) - 1;
                options.rows = getDateTableRows(aoData);
                options.nodataforward = true;
                options.columndefs = columndefs;
                options.columns = columns;
                options.datafn = datafn;
                options.fncreatedrow = fncreatedrow;
                $("#" + tableid).adddatatable(options);
            } else {
                var data = toDateTableJsonResult(list, total, total, sEcho);
                fnCallback(data); // 服务器端返回的对象的returnObject部分是要求的格式
            }
        }
    });
}

// {
// url : 后台接口
// data: 接口传入参数
// page: 页码 ,默认为1
// rows: 每页数量， 默认为10
// nodataforward: 无数据时是否自动page递减以保证有数据显示，常在数据删除时,默认为true
// datafn: 数据处理回调， 返回{data:,total,secho:}
// 以下参数可参数datatable详细参数说明
// --------------------http://www.360doc.com/content/15/0108/14/8790037_439143305.shtml
// columndefs: [{targets: , render: function()},{},{}]
// columns: [{mDataProp: },{},{}]
// fncreatedrow: function(nRow, aData, iDataIndex) {}
// }
// 注： 不同页面的第一个数据的索引可从$(dataid).attr('start')中获取
// 当前的page,rows可从$(dataid).attr('page')和$(dataid).attr('rows')中获取
// 当前页记录总数可从$(dataid).attr('count')中获取
$.fn.adddatatable = function (options, moredefs) {
    if (options == null)
        return;
    if (options.page == null)
        options.page = 1;
    if (options.page < 0)
        options.page = 1;
    if (options.rows == null) {
        options.rows = 10;
    }

    if (options.rows < 0)
        options.rows = 1;
    if (options.nodataforward == null)
        options.nodataforward = true;

    var data = {
        "retrieve": false,
        "destroy": true,
        "bAutoWidth": false,
        "bSort": false,
        "bDeferRender": true,
        "bProcessing": false, // 加载数据时显示正在加载信息
        "bServerSide": true, // 指定从服务器端获取数据
        "bFilter": false, // 不使用过滤功能
        "bSort": false,
        "bLengthChange": false, // 用户是否可改变每页显示数量
        "iDisplayStart": options.rows * (options.page - 1),
        "iDisplayLength": options.rows, // 每页显示10条数据
        "sAjaxSource": options.url,// 获取数据的url
        "fnServerData": retrieveData, // 获取数据的处理函数
        "fnServerParams": function (aoData) {
            for (var dt in options.data) {
                var key = dt;
                var value = options.data[key];
                key = "pushed_" + key;

                var datapush = {};
                datapush.name = key;
                datapush.value = value;
                aoData.push(datapush);
            }
            aoData.push({
                name: 'datatables_datafn',
                value: options.datafn
            });
            aoData.push({
                name: 'datatables_tableid',
                value: $(this).attr('id')
            });
            aoData.push({
                name: 'datatables_nodataforward',
                value: options.nodataforward
            });
            if (options.columndefs) {
                aoData.push({
                    name: 'datatables_columndefs',
                    value: options.columndefs
                });
            }
            if (options.columns) {
                aoData.push({
                    name: 'datatables_columns',
                    value: options.columns
                });
            }
            if (options.fncreatedrow) {
                aoData.push({
                    name: 'datatables_fncreatedrow',
                    value: options.fncreatedrow
                });
            }
        },
        "sPaginationType": "full_numbers", // 翻页界面类型
        "oLanguage": { // 汉化
            "sProcessing": "",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "暂无数据",
            "sLoadingRecords": "",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        }
    }
    if (options.columndefs) {
        data.aoColumnDefs = options.columndefs;
    }
    if (options.columns) {
        data.aoColumns = options.columns;
    }
    if (options.fncreatedrow) {
        data.fnCreatedRow = options.fncreatedrow;
    }

    if (moredefs != null) {
        for (var defs in moredefs) {
            var key = defs;
            var val = moredefs[key];
            data[key] = val;
        }
    }

    $(this).dataTable(data);
}

/**
 * 将键值对对象设置到某一元素上
 */
$.fn.setobj = function (name, obj) {
    // 添加自定义属性
    if (obj != null && name != null && name != '') {
        for (var key in obj) {
            var val = obj[key];
            if (val instanceof Function)
                $(this).attr(name + "_" + key, val.getName());
            else
                $(this).attr(name + "_" + key, val);
        }
    }
}

/**
 * 从元素上获取某一对象，键值对对象
 */
$.fn.getobj = function (name) {
    var attrs = $(this).get(0).attributes;
    var obj = {};
    try {
        for (var index = 0; index < attrs.length; index++) {
            var key = attrs[index].name;
            if (key.indexOf(name + "_") == 0) {
                var key = key.replace(name + "_", '');
                var val = attrs[index].value;
                obj[key] = val;
            }
        }
    } catch (e) {
    }
    return obj;
}


/**
 *  将list以 key[0],key[1]的方式添加到object中，常用于后台提交list参数
 * @param key
 * @param list
 */
Object.addDataList = function (key, list) {
    if (key != null && key != '' && list != null && list.length > 0) {
        var ll = new Array();
        for (var i = 0; i < list.length; i++) {
            var obj = list[i].trim();
            if (obj != null && obj.length > 0 && obj != '')
                ll.push(obj);
        }
        for (var i = 0; i < ll.length; i++) {
            var obj = ll[i];
            this[key + '[' + i + ']'] = obj;
        }
    }
}

/**
 *  将liststr以 key[0],key[1]的方式添加到object中，常用于后台提交list参数
 *  liststr以逗号分隔
 * @param key
 * @param liststr
 */
Object.addDataListStr = function (key, liststr) {
    if (key != null && key != '' && liststr != null)
        this.addDataList(key, liststr.split(','));
}

/**
 * 为对象添加属性key，值为obj
 * @param key
 * @param obj
 */
Object.addDataObj = function (key, obj) {
    if (key != null && key != '' && obj != null)
        this[key] = obj;
}

function Data() {
    this.addDataObj = function (key, obj) {
        if (key != null && key != '' && obj != null)
            this[key] = obj;
    };
    this.addDataList = function (key, list) {
        if (key != null && key != '' && list != null && list.length > 0) {
            var ll = new Array();
            for (var i = 0; i < list.length; i++) {
                var obj = list[i].trim();
                if (obj != null && obj.length > 0 && obj != '')
                    ll.push(obj);
            }
            for (var i = 0; i < ll.length; i++) {
                var obj = ll[i];
                this[key + '[' + i + ']'] = obj;
            }
        }
    };
    this.addDataListStr = function (key, liststr) {
        if (key != null && key != '' && liststr != null)
            this.addDataList(key, liststr.split(','));
    };
}

// 列值去除null
function getColValue(colvalue) {
    if (colvalue == null) {
        return "";
    } else {
        return colvalue;
    }
}

// 列值去除null和0
function getColValueFromNum(colvalue) {
    if (colvalue == null || colvalue == 0 || colvalue == 0.0) {
        return "";
    } else {
        return colvalue;
    }
}

// 数字列值去除null为0
function getColZero(colvalue) {
    if (colvalue == null || colvalue == 0 || colvalue == 0.0) {
        return "0";
    } else {
        return colvalue;
    }
}

function hasPerm(permcode) {
    if ($("#" + permcode).length > 0) {
        var val = $("#" + permcode).val();
        if (val == "true")
            return true;
    }
    return false;
}

function groupHasPerm(groupname) {
    var found = false;
    var ret = $(document).find('[value*="' + groupname + '"]');
    if (ret.length > 0)
        return true;
    return false;
}

function tohdaoxinxi() {
    window.location.href = $('#basePath').val() + 'page/hdaoinfomap/hdaoinfo.jsp';
}

function toliuliang() {
    window.location.href = $('#basePath').val() + 'page/liuliang/liuliang.jsp';
}

function tohdaoxuncha() {
    window.location.href = $('#basePath').val() + 'page/xuncha/xuncha.jsp';
}

function tohangzheng() {
    window.location.href = $('#basePath').val() + 'page/hangzheng/xzxk.jsp';
}

function tohangdaotubj() {
    window.location.href = $('#basePath').val() + 'page/hdaoinfo/hdaoinfomanager.jsp';
}

function tohdaoyh() {
    if (groupHasPerm('骨干航道养护'))
        window.location.href = $('#basePath').val() + 'page/hdaoyanghu/lixingyanghu_gugan.jsp';
    else if (groupHasPerm('支线航道养护'))
        window.location.href = $('#basePath').val() + 'page/hdaoyanghu/lixingyanghu_zhixian.jsp';
    else if (groupHasPerm('专项工程'))
        window.location.href = $('#basePath').val() + 'page/hdaoyanghu/zhuanxianggongcheng.jsp';
    else
        window.location.href = $('#basePath').val() + "/page/nopermPage.jsp";
}

function totongji() {
    if (hasPerm('VIEW_SHENGGYHBB') || hasPerm('VIEW_SHIGGYHBB') || hasPerm('VIEW_DPTGGYHBB')
        || hasPerm('VIEW_SHENZXYHBB') || hasPerm('VIEW_SHIZXYHBB') || hasPerm('VIEW_DPTZXYHBB')
        || hasPerm('VIEW_SHENZXGCBB') || hasPerm('VIEW_SHIZXGCBB') || hasPerm('VIEW_DPTZXGCBB')
        || hasPerm('VIEW_JSYBB')
    )
        window.location.href = $('#basePath').val() + 'page/tongjibaobiao/fading_framework.jsp';
    else if (hasPerm('VIEW_SHENHDBB') || hasPerm('VIEW_SHIHDBB'))
        window.location.href = $('#basePath').val() + 'page/tongjibaobiao/sheding_framework.jsp';
    else
        window.location.href = $('#basePath').val() + "/page/nopermPage.jsp";
}

function toxitongwh() {
    if (groupHasPerm('用户管理'))
        window.location.href = $('#basePath').val() + 'page/systemwh/user/systemwh-user.jsp';
    else if (groupHasPerm('组织机构管理'))
        window.location.href = $('#basePath').val() + 'page/systemwh/dpt/systemwh-dpt.jsp';
    else if (groupHasPerm('角色权限'))
        window.location.href = $('#basePath').val() + 'page/systemwh/perm/perm.jsp';
    else if (groupHasPerm('数据字典'))
        window.location.href = $('#basePath').val() + 'page/systemwh/dict/systemwh-dict.jsp';
    else if (groupHasPerm('日志管理'))
        window.location.href = $('#basePath').val() + 'page/systemwh/log/systemwh-log.jsp';
    else if (groupHasPerm('备份管理'))
        window.location.href = $('#basePath').val() + 'page/systemwh/back/systemwh-back.jsp';
    else
        window.location.href = $('#basePath').val() + "/page/nopermPage.jsp";
}

function printtable(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;
    document.body.innerHTML = printContents;
    window.print();
    document.body.innerHTML = originalContents;
}

function setDeciNull(n) {
    if (n == 0 || n == 0.0) {
        return "";
    } else {
        return n;
    }
}
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function logout() {
    $.ajax({
        url: 'user/logout',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (ifResultOK(data)) {
                window.location.href = $("#basePath").val()
                    + "page/login/login.jsp";
                //window.location.href="http://172.26.24.160/casZjgh/logout?service=http://172.26.24.160/casZjgh/login?service=http%3A%2F%2F10.100.70.126%2FChannel%2F";
                //window.location.href = "http://220.189.207.21:8290/casEG/logout?service=http://220.189.207.21:8290/casEG/login?service=http%3A%2F%2Flocalhost%3A8080%2FChannel%2F";
            }
        },
        error: function (data) {
        }
    });
}
