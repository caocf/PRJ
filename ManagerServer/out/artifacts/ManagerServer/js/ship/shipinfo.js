$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#cbcx_li").addClass("active");
    $(".tablabel").click(function () {
        clearlabcss();
        $(this).addClass("activitylabel");
    });
    $("#jbxxlab").click();
});

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

function setDate(obj, strformat) {
    if (obj == null) {
        return '';
    } else {
        if (obj == -2209017600000) {
            return new Date().format(strformat);
        } else {
            return new Date(obj).format(strformat);
        }
    }

}

function setyesorno(obj) {
    if (obj == null) {
        return '';
    } else {
        if (obj == 1) {
            return '是';
        } else {
            return '否'
        }
    }

}

function isnull(str) {
    var isnull = '';
    if (str == null) {
        return isnull;
    } else if (str.length > 15) {
        return str.substr(0, 15) + '...';
    } else {
        return str;
    }
}

function changedate(str, datetype) {
    var nulldate = '';
    if (str != null & str != -2209017600000) {
        nulldate = new Date(parseInt(str)).format(datetype);
        return nulldate;
    }
    return nulldate;
}

function clearlabcss() {
    $(".tablabel").each(function () {
        $(this).removeClass("activitylabel");
    });
}

function TableIsNull() {
    $("#shipcontent").hide();
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}

function showcbxx(id) {
    var shipname = $("#hideshipname").val();
    var url = "baseinfo";
    switch (id) {
        case 1:
            url = "baseinfo";
            $.ajax({
                url: url,
                method: 'post',
                dataType: 'json',
                data: {
                    'shipname': shipname
                },
                success: function (ret) {
                    $("#dt").empty();
                    $("#shipcontent").show();
                    $("#nulltablediv").hide();
                    var obj = ret.obj;
                    if (obj.ZWCM != undefined) {
                        var temptable = $('#dt');
                        var temptr1 = $('<tr><td class="texttd">船舶登记号</td><td class="datatd">' + setUndefined(obj.CBDJH) + '</td><td class="texttd">船名</td><td class="datatd">' + setUndefined(obj.ZWCM) + '</td></tr>');
                        var temptr2 = $('<tr><td class="texttd">经营人</td><td class="datatd">' + setUndefined(obj.JYR) + '</td><td class="texttd">经营人电话</td><td class="datatd">' + setUndefined(obj.JYRDH) + '</td></tr>');
                        var temptr3 = $('<tr><td class="texttd">所有人</td><td class="datatd">' + setUndefined(obj.SYR) + '</td><td class="texttd">船长</td><td class="datatd">' + setUndefined(obj.CC) + '</td></tr>');
                        var temptr4 = $('<tr><td class="texttd">总长</td><td class="datatd">' + setUndefined(obj.ZC) + '</td><td class="texttd">总吨位</td><td class="datatd">' + setUndefined(obj.ZDW) + '</td></tr>');
                        var temptr5 = $('<tr><td class="texttd">船舶类型</td><td class="datatd">' + setUndefined(obj.CBLX) + '</td><td class="texttd">船舶类型代码</td><td class="datatd">' + setUndefined(obj.CBLXDM) + '</td></tr>');
                        var temptr6 = $('<tr><td class="texttd">船籍港名称</td><td class="datatd">' + setUndefined(obj.CJGMC) + '</td><td class="texttd">船籍港代码</td><td class="datatd">' + setUndefined(obj.CJGDM) + '</td></tr>');
                        var temptr7 = $('<tr><td class="texttd">船检登记号</td><td class="datatd">' + setUndefined(obj.CJDJH) + '</td><td class="texttd">主机总功率</td><td class="datatd">' + setUndefined(obj.ZJZGL) + '</td></tr>');
                        var temptr8 = $('<tr><td class="texttd">吃水空载</td><td class="datatd">' + setUndefined(obj.CSKZ) + '</td><td class="texttd">吃水满载</td><td class="datatd">' + setUndefined(obj.CSMZ) + '</td></tr>');
                        var temptr9 = $('<tr><td class="texttd">型深</td><td class="datatd">' + setUndefined(obj.XS) + '</td><td class="texttd">型宽</td><td class="datatd">' + setUndefined(obj.XK) + '</td></tr>');
                        var temptr10 = $('<tr><td class="texttd">净吨位</td><td class="datatd">' + setUndefined(obj.JDW) + '</td><td class="texttd">参考载货量</td><td class="datatd">' + setUndefined(obj.CKZHL) + '</td></tr>');
                        temptable.append(temptr1);
                        temptable.append(temptr2);
                        temptable.append(temptr3);
                        temptable.append(temptr4);
                        temptable.append(temptr5);
                        temptable.append(temptr6);
                        temptable.append(temptr7);
                        temptable.append(temptr8);
                        temptable.append(temptr9);
                        temptable.append(temptr10);
                    } else {
                        TableIsNull();
                    }
                }
            });
            break;
        case 2:
            url = "certinfo";
            loadzsxx(url, 1);
            break;
        case 3:
            url = "illegalinfo";
            loadwzxx(url, 1);
            break;
        case 4:
            url = "chargeinfo";
            loadjfxx(url, 1);
            break;
        case 5:
            url = "inspectinfo";
            loadjyxx(url, 1);
            break;
    }
}

function loadzsxx(actionName, page) {
    var data = {};
    var shipname = $("#hideshipname").val();
    data['page'] = parseInt(page) - 1;
    data['shipname'] = shipname;
    $.ajax({
        url: actionName,
        type: "post",
        dataType: "json",
        data: data,
        success: function (data) {
            $("#dt").empty();
            $("#shipcontent").show();
            $("#nulltablediv").hide();
            var jsondata = JSON.parse(data);
            var record = jsondata.recordset;
            var total = jsondata.pager.total;
            var pageSize = jsondata.pager.pageSize;
            var sz = Math.floor(total / pageSize);// 商
            var ys = total % pageSize;// 余数
            var pages = 10;
            if (sz == 0) {
                pages = 1;
            } else if (ys == 0) {
                pages = sz;
            } else {
                pages = sz + 1;
            }
            /*$("#pagedetal").empty();
             $("#pagedetal").text(
             "当前页" + pageSize + "条记录 共" + total + "条记录，每页30条"
             );*/
            dtpaging(actionName, 'loadzsxx', page, pages);
            if (record == null || record.length == 0) {
                TableIsNull();
            } else {
                $("#dt").append(
                    "<tr>" +
                    "<th class='textth' style='width:10%'>序号</th>" +
                    "<th class='textth' style='width:30%;'>证书名称</th>" +
                    "<th class='textth' style='width:30%;'>证书编号</th>" +
                    "<th class='textth' style='width:15%;'>发证日期</th>" +
                    "<th class='textth' style='width:15%;'>有效日期</th>" +
                    "</tr>"
                );
                $(record).each(function (index, item) {
                    $("#dt").append(
                        "<tr>" +
                        "<td class='zstd'>" + (index + 1) + "</td>" +
                        "<td class='zstd'>" + isnull(item.ZSMC) + "</td>" +
                        "<td class='zstd'>" + item.ZSBH + "</td>" +
                        "<td class='zstd'>" + setDate(item.FZRQ, 'yyyy-MM-dd') + "</td>" +
                        "<td class='zstd'>" + setDate(item.YXRQ, 'yyyy-MM-dd') + "</td>" +
                        "</tr>"
                    )
                });
            }
        }
    })

}

function loadwzxx(actionName, page) {
    var data = {};
    var shipname = $("#hideshipname").val();
    data['page'] = parseInt(page) - 1;
    data['shipname'] = shipname;
    $.ajax({
        url: actionName,
        type: "post",
        dataType: "json",
        data: data,
        success: function (data) {
            $("#dt").empty();
            $("#shipcontent").show();
            $("#nulltablediv").hide();
            var jsondata = JSON.parse(data);
            var record = jsondata.recordset;
            var total = jsondata.pager.total;
            var pageSize = jsondata.pager.pageSize;
            var sz = Math.floor(total / pageSize);// 商
            var ys = total % pageSize;// 余数
            var pages = 10;
            if (sz == 0) {
                pages = 1;
            } else if (ys == 0) {
                pages = sz;
            } else {
                pages = sz + 1;
            }
            /*$("#pagedetal").empty();
             $("#pagedetal").text(
             "当前页" + record.length + "条记录 共" + total + "条记录，每页30条"
             );*/
            dtpaging(actionName, 'loadwzxx', page, pages);
            if (record == null || record.length == 0) {
                TableIsNull();
            } else {
                $("#dt").append(
                    "<tr>" +
                    "<th class='textth' style='width:5%'>序号</th>" +
                    "<th class='textth' style='width:15%;'>案发时间</th>" +
                    "<th class='textth' style='width:15%;'>案发地点</th>" +
                    "<th class='textth' style='width:10%;'>案件类别</th>" +
                    "<th class='textth' style='width:30%;'>案由</th>" +
                    "<th class='textth' style='width:20%;'>处罚意见</th>" +
                    "<th class='textth' style='width:5%;'>操作</th>" +
                    "</tr>"
                );
                $(record).each(function (index, item) {
                    var sfcf = item.SFCF == '1' ? '是' : '否';
                    var sfja = item.SFJA == '1' ? '是' : '否';
                    var str = item.SLH + "," + setDate(item.SLSJ, 'yyyy-MM-dd HH:mm:ss') + "," + item.ZWCM + "," + item.AY + "," + setDate(item.FASJ, 'yyyy-MM-dd HH:mm:ss') + "," + item.FADD + "," + item.AJLB + "," + item.ZYSS + "," + item.ZFSCBH + "," + item.WFNR + "," + item.WFTK + "," + item.CFTK + "," + item.CFYJ + "," + item.CFLB + "," + sfcf + "," + sfja + "," + setDate(item.JARQ, 'yyyy-MM-dd HH:mm:ss');
                    $("#dt").append(
                        "<tr>" +
                        "<td class='zstd'>" + (index + 1) + "</td>" +
                        "<td class='zstd'>" + setDate(item.FASJ, 'yyyy-MM-dd HH:mm:ss') + "</td>" +
                        "<td class='zstd'>" + isnull(item.FADD) + "</td>" +
                        "<td class='zstd'>" + isnull(item.AJLB) + "</td>" +
                        "<td class='zstd'>" + (item.AY) + "</td>" +
                        "<td class='zstd'>" + isnull(item.CFYJ) + "</td>" +
                        "<td class='zstd'><a onclick='wzxq(\"" + str + "\")' style='cursor: pointer'>详情</a></td>" +
                        "</tr>"
                    )
                });
            }
        }
    })
}

function wzxq(str) {
    var arr = str.split(',');
    for (var i in arr) {
        $("#wxdetail" + i).text("");
        var s = arr[i];
        $("#wxdetail" + i).text(s);
    }
    $("#wzmodal").modal("show");
}

function loadjfxx(actionName, page) {
    var data = {};
    var shipname = $("#hideshipname").val();
    data['page'] = parseInt(page) - 1;
    data['shipname'] = shipname;
    $.ajax({
        url: actionName,
        type: "post",
        dataType: "json",
        data: data,
        success: function (data) {
            $("#dt").empty();
            $("#shipcontent").show();
            $("#nulltablediv").hide();
            var jsondata = JSON.parse(data);
            var record = jsondata.recordset;
            var total = jsondata.pager.total;
            var pageSize = jsondata.pager.pageSize;
            var sz = Math.floor(total / pageSize);// 商
            var ys = total % pageSize;// 余数
            var pages = 10;
            if (sz == 0) {
                pages = 1;
            } else if (ys == 0) {
                pages = sz;
            } else {
                pages = sz + 1;
            }
            /*$("#pagedetal").empty();
             $("#pagedetal").text(
             "当前页" + pageSize + "条记录 共" + total + "条记录，每页30条"
             );*/
            dtpaging(actionName, 'loadjfxx', page, pages);
            if (record == null || record.length == 0) {
                TableIsNull();
            } else {
                $("#dt").append(
                    "<tr>" +
                    "<th class='textth' style='width:5%'>序号</th>" +
                    "<th class='textth' style='width:10%;'>开票日期</th>" +
                    "<th class='textth' style='width:15%;'>开票单位名称</th>" +
                    "<th class='textth' style='width:15%;'>缴费项目名称</th>" +
                    "<th class='textth' style='width:15%;'>收费方式名称</th>" +
                    "<th class='textth' style='width:10%;'>应缴金额</th>" +
                    "<th class='textth' style='width:10%;'>实缴金额</th>" +
                    "<th class='textth' style='width:10%;'>有效期起</th>" +
                    "<th class='textth' style='width:10%;'>有效期止</th>" +
                    "</tr>"
                );
                $(record).each(function (index, item) {
                    $("#dt").append(
                        "<tr>" +
                        "<td class='zstd'>" + (index + 1) + "</td>" +
                        "<td class='zstd'>" + setDate(item.KPRQ, 'yyyy-MM-dd') + "</td>" +
                        "<td class='zstd'>" + isnull(item.KPDWMC) + "</td>" +
                        "<td class='zstd'>" + isnull(item.JFXMMC) + "</td>" +
                        "<td class='zstd'>" + isnull(item.SFFSMC) + "</td>" +
                        "<td class='zstd'>" + isnull(item.YJZE) + "</td>" +
                        "<td class='zstd'>" + isnull(item.SJZE) + "</td>" +
                        "<td class='zstd'>" + setDate(item.YXQQ, 'yyyy-MM-dd') + "</td>" +
                        "<td class='zstd'>" + setDate(item.YXQZ, 'yyyy-MM-dd') + "</td>" +
                        "</tr>"
                    )
                });
            }
        }
    })
}

function loadjyxx(actionName, page) {
    var data = {};
    var shipname = $("#hideshipname").val();
    data['page'] = parseInt(page) - 1;
    data['shipname'] = shipname;
    $.ajax({
        url: actionName,
        type: "post",
        dataType: "json",
        data: data,
        success: function (data) {
            $("#dt").empty();
            $("#shipcontent").show();
            $("#nulltablediv").hide();
            var jsondata = JSON.parse(data);
            var record = jsondata.recordset;
            var total = jsondata.pager.total;
            var pageSize = jsondata.pager.pageSize;
            var sz = Math.floor(total / pageSize);// 商
            var ys = total % pageSize;// 余数
            var pages = 10;
            if (sz == 0) {
                pages = 1;
            } else if (ys == 0) {
                pages = sz;
            } else {
                pages = sz + 1;
            }
            /*$("#pagedetal").empty();
             $("#pagedetal").text(
             "当前页" + pageSize + "条记录 共" + total + "条记录，每页30条"
             );*/
            dtpaging(actionName, 'loadjyxx', page, pages);
            if (record == null || record.length == 0) {
                TableIsNull();
            } else {
                $("#dt").append(
                    "<tr>" +
                    "<th class='textth' style='width:5%'>序号</th>" +
                    "<th class='textth' style='width:15%;'>中文船名</th>" +
                    "<th class='textth' style='width:20%;'>检验部门</th>" +
                    "<th class='textth' style='width:15%;'>检验种类</th>" +
                    "<th class='textth' style='width:15%;'>检验开始日期</th>" +
                    "<th class='textth' style='width:15%;'>检验结束日期</th>" +
                    "<th class='textth' style='width:15%;'>下次检验日期</th>" +
                    "</tr>"
                );
                $(record).each(function (index, item) {
                    $("#dt").append(
                        "<tr>" +
                        "<td class='zstd'>" + (index + 1) + "</td>" +
                        "<td class='zstd'>" + isnull(item.ZWCM) + "</td>" +
                        "<td class='zstd'>" + isnull(item.JYDWMC) + "</td>" +
                        "<td class='zstd'>" + isnull(item.JYZL) + "</td>" +
                        "<td class='zstd'>" + setDate(item.JYKSRQ, 'yyyy-MM-dd') + "</td>" +
                        "<td class='zstd'>" + setDate(item.JYWCRQ, 'yyyy-MM-dd') + "</td>" +
                        "<td class='zstd'>" + setDate(item.XCJYRQ, 'yyyy-MM-dd') + "</td>" +
                        "</tr>"
                    )
                });
            }
        }
    })
}