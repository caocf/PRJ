/**
 * Created by 25019 on 2015/10/28.
 */
var haarr = new Array("压顶破损", "墙身破损", "勾缝脱落", "踏步破损", "沉降位移", "其他问题");
var mtarr = new Array("防撞轮胎<br>附属设施<br>损　　坏", "沉降位移", "码头破损", "其他问题");
var hbarr = new Array("标志标牌<br>缺失损毁", "标志标牌<br>倾　　斜", "标志标牌<br>被遮挡", "反光膜<br>老化脱落", "浮标位移", "其他问题");
var lharr = new Array("树木枯死<br>缺　　失", "树木倒伏", "树木虫病", "绿化带内有<br>杂草、垃圾", "其他问题");
var aharr = new Array("浅滩浅点", "沉船沉物", "其他问题");
var wzarr = new Array("临时码头", "过河管线<br>管　　道", "施工遗留物", "其他问题");
var deletefileids = new Array();
$(document).ready(function () {
    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabxuncha').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/xuncha/xuncha.jsp";
    });
    $('#tabyanghulianxi').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/xuncha/yanghulianxi.jsp";
    });
    initui();
});

function initui() {
    $("#cbselall").bind('click', function () {
        selallgc();
    });
    $('#btndelyh').click(function () {
        var delids = $("#modaldel").attr('delids').split(',');
        delyh(delids);
    });
    $("#btnedityh").bind('click', function () {
        edityh();
    });
    loadselhdao();
    loadyh();
}

function selallgc() {
    if ($("#cbselall").prop('checked') == true) {
        for (var i = start; i < start + 30; i++) {
            $("#cb" + i).prop('checked', true);
        }
    } else {
        for (var i = start; i < start + 30; i++) {
            $("#cb" + i).prop('checked', false);
        }
    }
}

function loadselhdao() {
    // 加载航道选择
    ajax('hangdao/queryallhangdao', {
        'loginid': $("#userid").val(),
        'sfgg': '-1',
        'xzqh': $("#deptxzqh").val()
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            if (records) {
                var data = {};
                for (var i = 0; i < records.data.length; i++) {
                    var hdao = records.data[i];

                    var id = hdao.id;
                    var no = hdao.hdbh;
                    var name = hdao.hdmc;
                    data[id] = name;
                }
                $("#divselhdao").empty();
                $("#divselhdao").addmultiselect({
                    data: data,
                    onchange: function (selitems) {
                        loadyh();
                    },
                    id: "multidivselhdao"
                });
                $("#divupdateselhdao").empty();
                $("#divupdateselhdao").addselect({
                    data: data,
                    validators: {
                        notempty: {
                            msg: '请选择航道'
                        }
                    },
                    autoajax: {
                        name: 'updateyhgk.hdid'
                    },
                    id: "updateselhdao",
                    selectstyle: 'width:100%;padding-right:0px;'
                });
            }
        }
    });
}

function loadyh() {
    var starttime = null, endtime = null, hdids = null, content = null;
    hdids = $("#multidivselhdao").attr('selitem');
    starttime = $("#seldaystart").attr('realvalue');
    endtime = $("#seldayend").attr('realvalue');
    content = $("#searchContent").val();

    var data = {};
    if (hdids != null) {
        hdids = hdids.split(',');
        var index = 0;
        for (var i = 0; i < hdids.length; i++) {
            if (hdids[i] != null && hdids[i] != '') {
                data['ids[' + index + ']'] = hdids[i];
                index++;
            }
        }
    }
    if (starttime != null && starttime.length != 0)
        data.starttime = starttime;
    if (endtime != null && endtime.length != 0)
        data.endtime = endtime;
    if (content != null && content.length != 0)
        data.content = content;

    $("#yhtable").adddatatable(
        {
            url: 'queryyhlxd',
            data: data,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var i in records.data) {
                        var xc = records.data[i][0];
                        var hd = records.data[i][1];
                        var dt = {};
                        dt.id = xc.id;
                        dt.hdmc = hd.hdmc;
                        dt.qqd = xc.qd + "---" + xc.zd;
                        dt.xctime = xc.starttime + "至" + xc.endtime;
                        dt.scsj = xc.createtime;
                        dt.bgbm = xc.bgbm;
                        list.push(dt);
                    }
                }
                ret.data = list;
                ret.total = records.total;
                ret.secho = data.map.sEcho;
                return ret;
            },
            columns: [{
                mDataProp: null
            }, {
                mDataProp: 'hdmc'
            }, {
                mDataProp: 'qqd'
            }, {
                mDataProp: 'xctime'
            }, {
                mDataProp: 'bgbm'
            }, {
                mDataProp: 'scsj'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 6,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="viewyh(\''
                        + row.id
                        + '\')">查看</a>';
                    if (hasPerm('MAN_YHLX')) {
                        ret += '<a class="btn btn-link btnoper" onclick="editload('
                            + row.id
                            + ')">修改</a>'
                            + '<a class="btn btn-link btnoper" onclick="delsingle('
                            + row.id
                            + ',\''
                            + row.qqd
                            + '\')">删除</a>';
                    }
                    return ret;
                }
            }],
            fncreatedrow: function (nRow, aData, iDataIndex) {
                var start = parseInt($('#yhtable').attr('start'));
                $("td:eq(0)", nRow).html(
                    '<input type="checkbox" name="yhcheckbox" yhid='
                    + aData.id + ' qqd="' + aData.qqd + '" id="cb' + (start + iDataIndex)
                    + '" value="'
                    + (start + iDataIndex) + '">&nbsp;'
                    + (start + iDataIndex))
            }
        },
        {
            'sScrollX': "100%"
        });
}

function viewyh(id) {
    $("#tableview").empty();
    ajax('queryyhlxdbyid', {
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            $("#modalview").modal('show');
            var records = getResultObj(result);
            if (records != null && records.length > 0) {
                $("#tableview").append('<tr><td width="7%"></td><td width="18%"></td><td width="20%"></td><td width="15%"></td><td width="20%"></td><td width="20%"></td></tr>');
                var strtime = "____年__月__日";
                strtime = getTimeFromStr(records[0].starttime, "yyyy-MM-dd HH:mm:ss").format("yyyy年MM月dd日");
                var xcinfo = records[0];
                $("#yhlxdid").val(xcinfo.id);
                $("#tableview").append("<tr><td colspan='6' style='text-align: center;font-size: xx-large'>航道养护联系单</td></tr>");
                var dept = getResultMap(result).dept;
                $("#tableview").append("<tr><td colspan='2' style='text-decoration: underline '>" + dept + ":</td></tr>");
                $("#tableview").append("<tr><td></td><td colspan='5'>我部门于" + strtime + ",在" + xcinfo.hdmc + "航道巡查时,发现下列航道缺陷,请安排养护</td></tr>");
                $("#tableview").append("<tr><td colspan='4'></td><td  style='text-align: right'>巡查报告部门：</td><td style='text-decoration: underline '>" + xcinfo.bgbm + "</td></tr>");
                $("#tableview").append("<tr><td colspan='6'></td></tr>");
                for (var j = 1; j <= 6; j++) {
                    //添加情况
                    switch (j) {
                        case 1:
                            $("#tableview").append("<tr><td colspan='2'>1.航道淤积缺陷</td></tr>");
                            break;
                        case 2:
                            $("#tableview").append("<tr><td colspan='2'>2.护岸、管理码头缺陷</td></tr>");
                            break;
                        case 3:
                            $("#tableview").append("<tr><td colspan='2'>3.航标缺陷</td></tr>");
                            break;
                        case 4:
                            $("#tableview").append("<tr><td colspan='2'>4.绿化缺陷</td></tr>");
                            break;
                        case 5:
                            $("#tableview").append("<tr><td colspan='2'>5.碍航物缺陷</td></tr>");
                            break;
                        case 6:
                            $("#tableview").append("<tr><td colspan='2'>6.其他情况</td></tr>");
                            break;
                    }
                    //添加具体信息
                    var qkisnull = true;
                    for (var i = 0; i < records.length; i++) {
                        var pojo = records[i];
                        var ab = pojo.ab == 1 ? "左岸" : "右岸";
                        var ms = pojo.ms;
                        if (pojo.qk == j) {
                            $("#tableview").append("<tr><td>地点:</td><td colspan='5' style='border-bottom: 1px solid black ;'>" + pojo.jtwz + " " + ab + "</td></tr>");
                            $("#tableview").append("<tr><td>概况:</td><td colspan='5' style='border-bottom: 1px solid black ;'>" + pojo.wtsm + ":" + ms + "</td></tr>");
                            $("#tableview").append("<tr><td colspan='6'></td></tr>");
                            qkisnull = false;
                        }
                    }
                    if (qkisnull) {
                        $("#tableview").append("<tr><td>地点:</td><td colspan='5' style='border-bottom: 1px solid black ;'></td></tr>");
                        $("#tableview").append("<tr><td>概况:</td><td colspan='5' style='border-bottom: 1px solid black ;'></td></tr>");
                        $("#tableview").append("<tr><td colspan='6'></td></tr>");
                    }
                }
                //附件处理
                var fjlist = getResultMap(result).fjlist;
                $("div[name='fjrow']").empty();
                for (var index in fjlist) {
                    var fjid = fjlist[index].id;
                    var fjname = fjlist[index].fname;
                    var fjtype = fjlist[index].ftype;
                    var fjsize = fjlist[index].fsize;
                    var fjtype = fjlist[index].ftype;
                    var rantoken = rand(1, 99999999);
                    var row = $('<div  name="fjrow"></div>');
                    var col = $('<div class="col-xs-12 viewfjdiv" style="background:#e4f0ff;margin-top:2px;"></div>');
                    row.append(col);
                    col.append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
                        + '<label style="font-weight:normal;" id="label' + rantoken + '">' + fjname
                        + "("
                        + fjsize
                        + "kb)"
                        + '</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                        + '<a class="btn btn-link" href="downloadcxcyhfj?id=' + fjid + '">下载</a>'
                    );
                    if (fjtype == 1) {
                        col.append('<a class="btn btn-link" onclick="viewattachment(' + fjid + ')">预览</a>');
                    }
                    $("#yhfjdivrow").append(row);
                }

                for (var index in fjlist) {
                    var fjid = fjlist[index].id;
                    var fjtype = fjlist[index].ftype;

                    if (fjtype == 1 || fjtype == "1") {
                        var $imgdiv = $("<div class='col-xs-3 imgfjdiv' style='display: none;'></div>");
                        var $img = $("<img class='img-responsive'/>");
                        $imgdiv.append($img);
                        $img.attr('src', "downloadcxcyhfj?id=" + fjid);

                        $("#yhfjdivrow").append($imgdiv);
                    }
                }

            }
        } else {
            alert(getResultDesc(result));
        }
    });
}

function editload(id) {
    $("#yhlxdid").val(id);
    ajax('queryyhlxdbyid', {
        'id': id
    }, function (data) {
        if (ifResultOK(data)) {
            $("#modalupdate").modal('show');
            var records = getResultObj(data);
            if (records) {
                var gk = records[0];
                $("#updateselhdao").val(gk.hdid);
                $('input[name="updateztqk"][value="' + gk.ztqk + '"]').attr('checked', true);
                /*if (gk.yhflag == 1) {
                 $("#updateyhlxd").attr("checked",true);
                 }*/
                $("#updateseltime").empty();
                $("#updateseltime").addtimeregion({
                    idstart: 'updateseltimestart',
                    idend: 'updateseltimeend',
                    defaultvalstart: gk.starttime,
                    defaultvalend: gk.endtime,
                    hintstart: '年-月-日-时-分-秒',
                    hintend: '年-月-日-时-分-秒',
                    validatorsstart: {
                        notempty: {
                            msg: '请输入开始时间'
                        }
                    },
                    validatorsend: {
                        notempty: {
                            msg: '请输入结束时间'
                        }
                    },
                    autoajaxstart: {name: 'updateyhgk.starttime'},
                    autoajaxend: {name: 'updateyhgk.endtime'}
                });
                $("#updateqd").val(gk.qd);
                $("#updatezd").val(gk.zd);
                $("#updateqdzh").val(gk.qdzh);
                $("#updatezdzh").val(gk.zdzh);
                $("#updatexhth").val(gk.xhth);
                $("#updatecyr").val(gk.cyr);
                $("#updatedeptdiv").addseldpt({
                    id: 'updatexcdept',
                    defaultval: gk.dept
                });
                $("#haubody").empty();
                $("#mtubody").empty();
                $("#hbubody").empty();
                $("#lhubody").empty();
                $("#ahubody").empty();
                $("#wzubody").empty();
                for (var j = 1; j <= 7; j++) {
                    switch (j) {
                        case 1:
                            setTrVal(1, 6, records);
                            break;
                        case 2:
                            setTrVal(2, 4, records);
                            break;
                        case 3:
                            setTrVal(3, 6, records);
                            break;
                        case 4:
                            setTrVal(4, 5, records);
                            break;
                        case 5:
                            setTrVal(5, 3, records);
                            break;
                        case 6:
                            setTrVal(6, 4, records);
                            break;
                        case 7:
                            setTrVal(7, 10, records);
                            break;
                    }
                }

                var fjlist = getResultMap(data).fjlist;
                $("div[name='fjrow']").empty();
                for (var index in fjlist) {
                    var fjid = fjlist[index].id;
                    var fjname = fjlist[index].fname;
                    var fjsize = fjlist[index].fsize;
                    var rantoken = rand(1, 99999999);
                    var row = $('<div  name="fjrow" ftype="originfile" id="trfileid' + fjid
                        + '"></div>');
                    var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
                    row.append(col);
                    col.append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
                        + '<label style="font-weight:normal;" id="label' + rantoken + '">' + fjname
                        + "("
                        + fjsize
                        + "kb)"
                        + '</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                        + '<input type="button" class="btn btn-link" value="删除" onclick="adddelids(' + fjid + ')"/>');
                    $("#divupdate").append(row);
                }
            }
        }
    });
}

function setTrVal(qk, wt, records) {
    for (var i = 0; i < wt; i++) {
        var flag = true;
        var tempi = i + 1;
        for (var j = 0; j < records.length; j++) {
            var obj = records[j];
            if (obj.qk == qk && obj.wt == tempi) {
                flag = false;
                switch (qk) {
                    case 1:
                        $("#haubody").updatetr(obj);
                        break;
                    case 2:
                        $("#mtubody").updatetr(obj);
                        break;
                    case 3:
                        $("#hbubody").updatetr(obj);
                        break;
                    case 4:
                        $("#lhubody").updatetr(obj);
                        break;
                    case 5:
                        $("#ahubody").updatetr(obj);
                        break;
                    case 6:
                        $("#wzubody").updatetr(obj);
                        break;
                    case 7:
                        $("#qtubody").updatetr(obj);
                        break;
                }
            }
        }
        if (flag) {
            switch (qk) {
                case 1:
                    $("#haubody").newtr('0' + tempi, haarr[i]);
                    break;
                case 2:
                    $("#mtubody").newtr('0' + tempi, mtarr[i]);
                    break;
                case 3:
                    $("#hbubody").newtr('0' + tempi, hbarr[i]);
                    break;
                case 4:
                    $("#lhubody").newtr('0' + tempi, lharr[i]);
                    break;
                case 5:
                    $("#ahubody").newtr('0' + tempi, aharr[i]);
                    break;
                case 6:
                    $("#wzubody").newtr('0' + tempi, wzarr[i]);
                    break;
                case 7:
                    break;
            }
        }
    }
}

function edityh() {
    var id = $("#yhlxdid").val();
    var ztqk = $("input[name='updateztqk']:checked").val();
    var updateyhgk = {};
    updateyhgk.qks = new Array();

    //护岸
    var haqk = getXunChaTableData("hau");
    if (haqk.length > 0) {
        var ha = {};
        ha.qk = 1;
        ha.qksm = '护岸情况';
        ha.wts = haqk;
        updateyhgk.qks.push(ha);
    }

    //码头
    var mtqk = getXunChaTableData("mtu");
    if (mtqk.length > 0) {
        var mt = {};
        mt.qk = 2;
        mt.qksm = '码头情况';
        mt.wts = mtqk;
        updateyhgk.qks.push(mt);
    }

    //航标
    var hbqk = getXunChaTableData("hbu");
    if (hbqk.length > 0) {
        var hb = {};
        hb.qk = 3;
        hb.qksm = '航标情况';
        hb.wts = hbqk
        updateyhgk.qks.push(hb);
    }

    //绿化
    var lhqk = getXunChaTableData("lhu");
    if (lhqk.length > 0) {
        var lh = {};
        lh.qk = 4;
        lh.qksm = '绿化情况';
        lh.wts = lhqk;
        updateyhgk.qks.push(lh);
    }

    //碍航
    var ahqk = getXunChaTableData("ahu");
    if (ahqk.length > 0) {
        var ah = {};
        ah.qk = 5;
        ah.qksm = '碍航情况';
        ah.wts = ahqk;
        updateyhgk.qks.push(ah);
    }

    //违章
    var wzqk = getXunChaTableData("wzu");
    if (wzqk.length > 0) {
        var wz = {};
        wz.qk = 6;
        wz.qksm = '违章情况';
        wz.wts = wzqk;
        updateyhgk.qks.push(wz);
    }

    //其他
    var qtqk = getXunChaTableData("qtu");
    if (qtqk.length > 0) {
        var qt = {};
        qt.qk = 7;
        qt.qksm = '其它情况';
        qt.wts = qtqk;
        updateyhgk.qks.push(qt);
    }

    var strjson = JSON.stringify(updateyhgk);
    var data = {
        id: id,
        'updateyhgk.ztqk': ztqk,
        jsondata: strjson
    };
    for (var index in deletefileids) {
        var fjid = deletefileids[index];
        data["delfileids[" + index + "]"] = fjid;
    }
    ;
    $("#divupdate").validateForm(function () {
        $("#divupdate").autoajaxfileuploading('updateyhlxd', data, function (result, data) {
                if (ifResultOK(result)) {
                    alert("更新养护联系单成功");
                    $("#modalupdate").modal('hide');
                    deletefileids.length = 0;
                    loadyh();
                } else {
                    alert(getResultDesc(result));
                }
            }
        );
    });
}

function exportyh() {
    var id = $("#yhlxdid").val();
    window.location.href = $("#basePath").val()
        + "exportyhlxd?id=" + id;
}

function printyh() {
    var $body = $("body");
    var $invisiableDiv = $("<div style='display:none;'></div>");
    var $printdiv = $("<div></div>");

    //将printcontent移到printdiv中
    $printdiv.append($('#printcontent').clone());

    $body.children().each(function () {
        $invisiableDiv.append(this);
    });


    $body.append($invisiableDiv);
    $body.append($printdiv);

    //将printcontent中的图片附件转化为图片
    $("#yhfjdiv", $printdiv).text('图片信息');

    //清除原图片信息
    $(".viewfjdiv", $printdiv).remove();
    //显示图片
    $(".imgfjdiv", $printdiv).show();

    window.print();

    //恢复
    $invisiableDiv.children().each(function () {
        $body.append(this);
    });
    $invisiableDiv.remove();
    $printdiv.remove();
}

function delsingle(id, qqd) {
    var delids = new Array();
    var delnames = new Array();

    delids.push(id);
    delnames.push(qqd);

    if (delids.length <= 0) {
        alert('请先选择要删除的养护联系单！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldel").attr('delids', delids.join(","));
    $("#modaldel").modal('show');
}

function delmulti() {
    var delids = new Array();
    var delnames = new Array();
    $("[name='yhcheckbox']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('yhid');
            delids.push(id);
            var qqd = $(this).attr('qqd');
            delnames.push(qqd);
        }
    });
    if (delids.length <= 0) {
        alert('请先选择要删除的养护联系单！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldel").attr('delids', delids.join(","));
    $("#modaldel").modal('show');
}

function delyh(ids) {
    var data = {};
    for (var index in ids) {
        var id = ids[index];
        data["ids[" + index + "]"] = id;
    }
    ajax('delyhlxd', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldel").modal('hide');
            loadyh();
        } else {
            alert(getResultDesc(result));
        }
    });
}

/*********************处理附件信息js*****************************/
function addpicture(containerid) {
    var rantoken = rand(1, 99999999);

    var row = $('<div class="hide" id="row' + rantoken + '"></div>');
    var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
    row.append(col);
    col.append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
        + '<label style="font-weight:normal;" id="label'
        + rantoken
        + '"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        + '<input type="button" class="btn btn-link" value="删除" onclick="$(\'#row'
        + rantoken
        + '\').remove();"><input id="file'
        + rantoken
        + '" type="file" class="hide" autoajax autoajax-name="filelist" onchange="selectfile(this,\''
        + containerid + '\',\'' + rantoken + '\')">');

    $("#" + containerid).append(row);
    $("#file" + rantoken).click();
}

function selectfile(from, containerid, rantoken) {
    var file = $('#file' + rantoken).val();
    var size = '';
    try {
        var size = (from.files[0].size / 1024).toFixed(2);
    } catch (e) {
        size = '';
    }

    if (file != null && file != "") {
        $("#row" + rantoken).removeClass('hide');

        var pos = file.lastIndexOf('\\');
        var filename = file.substring(pos + 1);
        if (size == null || size == '')
            $("#label" + rantoken).html(filename);
        else
            $("#label" + rantoken).html(filename + "(" + size + "kb)");
    }
}

function adddelids(id) {
    deletefileids.push(id);
    $("#trfileid" + id).remove();
}

// 显示图片附件
function viewattachment(id) {
    $("#imgviewbody").empty();
    $("#imgviewbody").append(
        '<img style="width:100%;height:auto;" src="downloadcxcyhfj?id=' + id + '">');
    $("#modalpicview").modal('show');
}