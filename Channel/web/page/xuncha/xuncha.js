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
    $("#modalnew").on('show.bs.modal', function () {
        $("#hatbody").empty();
        $("#hatbody").newtr('01', '压顶破损');
        $("#hatbody").newtr('02', '墙身破损');
        $("#hatbody").newtr('03', '勾缝脱落');
        $("#hatbody").newtr('04', '踏步破损');
        $("#hatbody").newtr('05', '沉降位移');
        $("#hatbody").newtr('06', '其他问题');

        $("#mttbody").empty();
        $("#mttbody").newtr('01', '防撞轮胎<br>附属设施<br>损　　坏');
        $("#mttbody").newtr('02', '沉降位移');
        $("#mttbody").newtr('03', '码头破损');
        $("#mttbody").newtr('04', '其他问题');

        $("#hbtbody").empty();
        $("#hbtbody").newtr('01', '标志标牌<br>缺失损毁');
        $("#hbtbody").newtr('02', '标志标牌<br>倾　　斜');
        $("#hbtbody").newtr('03', '标志标牌<br>被遮挡');
        $("#hbtbody").newtr('04', '反光膜<br>老化脱落');
        $("#hbtbody").newtr('05', '浮标位移');
        $("#hbtbody").newtr('06', '其他问题');

        $("#lhtbody").empty();
        $("#lhtbody").newtr('01', '树木枯死<br>缺　　失');
        $("#lhtbody").newtr('02', '树木倒伏');
        $("#lhtbody").newtr('03', '树木虫病');
        $("#lhtbody").newtr('04', '绿化带内有<br>杂草、垃圾');
        $("#lhtbody").newtr('05', '其他问题');

        $("#ahtbody").empty();
        $("#ahtbody").newtr('01', '浅滩浅点');
        $("#ahtbody").newtr('02', '沉船沉物');
        $("#ahtbody").newtr('03', '其他问题');

        $("#wztbody").empty();
        $("#wztbody").newtr('01', '临时码头');
        $("#wztbody").newtr('02', '过河管线<br>管　　道');
        $("#wztbody").newtr('03', '施工遗留物');
        $("#wztbody").newtr('04', '其他问题');

        $("#qttbody").empty();
    });

    initui();
});
function initui() {
    $("#cbselall").bind('click', function () {
        selallgc();
    });
    $('#btndelxc').click(function () {
        var delids = $("#modaldel").attr('delids').split(',');
        delxc(delids);
    });
    $("#btneditxc").bind('click', function () {
        editxc();
    });
    loadselhdao();
    loadxc();
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
    ajax('hangdao/queryhdmanxzqh', {
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
                        loadxc();
                    },
                    id: "multidivselhdao"
                });

                $("#divnewselhdao").empty();
                $("#divnewselhdao").addselect({
                    data: data,
                    validators: {
                        notempty: {
                            msg: '请选择航道'
                        }
                    },
                    autoajax: {
                        name: 'gk.hdid'
                    },
                    id: "newselhdao",
                    selectstyle: 'width:100%;padding-right:0px;'
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
                        name: 'updategk.hdid'
                    },
                    id: "updateselhdao",
                    selectstyle: 'width:100%;padding-right:0px;'
                });

            }
        }
    });
}

function loadxc() {
    var starttime = null, endtime = null, hdids = null;
    hdids = $("#multidivselhdao").attr('selitem');
    starttime = $("#seldaystart").attr('realvalue');
    endtime = $("#seldayend").attr('realvalue');
    var data = {};
    if (starttime != null && starttime.length != 0)
        data.starttime = starttime;
    if (endtime != null && endtime.length != 0)
        data.endtime = endtime;
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

    $("#xctable").adddatatable(
        {
            url: 'querycruise',
            data: data,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var i in records.data) {
                        var xc = records.data[i];
                        var dt = {};
                        dt.id = xc.id;
                        dt.hdmc = xc.hdmc;
                        dt.qqd = xc.qd + "---" + xc.zd;
                        var dtxczh = '';
                        if (xc.qdzh != '' || xc.zdzh != '') {
                            dtxczh = xc.qdzh + "---" + xc.zdzh;
                        }
                        dt.xczh = dtxczh;
                        dt.ztqk = xc.ztqk;
                        dt.deptname = xc.deptname;
                        dt.xctime = xc.starttime + "至" + xc.endtime;
                        dt.yhgkid = xc.yhgkid;
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
                mDataProp: 'xczh'
            }, {
                mDataProp: 'ztqk'
            }, {
                mDataProp: 'deptname'
            }, {
                mDataProp: 'xctime'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 7,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="viewxc(\''
                        + row.id
                        + '\')">查看</a>';
                    //权限你懂的
                    if (hasPerm('MAN_XCJL')) {
                        if (row.yhgkid == 0) {
                            ret += '<a class="btn btn-link btnoper" onclick="editload('
                                + row.id
                                + ')">修改</a>';
                        }
                        ret += '<a class="btn btn-link btnoper" onclick="delsingle('
                            + row.id
                            + ',\''
                            + row.qqd
                            + '\')">删除</a>';
                    }
                    return ret;
                }
            }],
            fncreatedrow: function (nRow, aData, iDataIndex) {
                var start = parseInt($('#xctable').attr('start'));
                $("td:eq(0)", nRow).html(
                    '<input type="checkbox" name="xccheckbox" xcid='
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

function addnew() {
    var ztqk = $("input[name='ztqk']:checked").val();
    var flag = $("#createyhlxd").is(':checked') ? 1 : 0;
    var gk = {};
    gk.qks = new Array();

    //护岸
    var haqk = getXunChaTableData("ha");
    if (haqk.length > 0) {
        var ha = {};
        ha.qk = 1;
        ha.qksm = '护岸情况';
        ha.wts = haqk;
        gk.qks.push(ha);
    }

    //码头
    var mtqk = getXunChaTableData("mt");
    if (mtqk.length > 0) {
        var mt = {};
        mt.qk = 2;
        mt.qksm = '码头情况';
        mt.wts = mtqk;
        gk.qks.push(mt);
    }

    //航标
    var hbqk = getXunChaTableData("hb");
    if (hbqk.length > 0) {
        var hb = {};
        hb.qk = 3;
        hb.qksm = '航标情况';
        hb.wts = hbqk
        gk.qks.push(hb);
    }

    //绿化
    var lhqk = getXunChaTableData("lh");
    if (lhqk.length > 0) {
        var lh = {};
        lh.qk = 4;
        lh.qksm = '绿化情况';
        lh.wts = lhqk;
        gk.qks.push(lh);
    }

    //碍航
    var ahqk = getXunChaTableData("ah");
    if (ahqk.length > 0) {
        var ah = {};
        ah.qk = 5;
        ah.qksm = '碍航情况';
        ah.wts = ahqk;
        gk.qks.push(ah);
    }

    //违章
    var wzqk = getXunChaTableData("wz");
    if (wzqk.length > 0) {
        var wz = {};
        wz.qk = 6;
        wz.qksm = '违章情况';
        wz.wts = wzqk;
        gk.qks.push(wz);
    }

    //其他
    var qtqk = getXunChaTableData("qt");
    if (qtqk.length > 0) {
        var qt = {};
        qt.qk = 7;
        qt.qksm = '其它情况';
        qt.wts = qtqk;
        gk.qks.push(qt);
    }

    var strjson = JSON.stringify(gk);
    var data = {
        flag: flag,
        'gk.ztqk': ztqk,
        'gk.yhflag': flag,
        jsondata: strjson
    };
    $("#divaddnew").validateForm(function () {
        $("#divaddnew").autoajaxfileuploading('addcruise', data, function (result, data) {
                if (ifResultOK(result)) {
                    alert("新建巡查记录成功");
                    $("#modalnew").modal('hide');
                    loadxc();
                } else {
                    alert(getResultDesc(result));
                }
            }
        );
    });
}

function delsingle(id, qqd) {
    var delids = new Array();
    var delnames = new Array();

    delids.push(id);
    delnames.push(qqd);

    if (delids.length <= 0) {
        alert('请先选择要删除的巡查记录！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldel").attr('delids', delids.join(","));
    $("#modaldel").modal('show');
}

function delmulti() {
    var delids = new Array();
    var delnames = new Array();
    $("[name='xccheckbox']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('xcid');
            delids.push(id);
            var qqd = $(this).attr('qqd');
            delnames.push(qqd);
        }
    });
    if (delids.length <= 0) {
        alert('请先选择要删除的巡查记录！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldel").attr('delids', delids.join(","));
    $("#modaldel").modal('show');
}

function delxc(ids) {
    var data = {};
    for (var index in ids) {
        var id = ids[index];
        data["ids[" + index + "]"] = id;
    }
    ajax('delcruise', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldel").modal('hide');
            loadxc();
        } else {
            alert(getResultDesc(result));
        }
    });
}

function viewxc(id) {
    ajax('querycruisebyid', {
        'id': id
    }, function (data) {
        if (ifResultOK(data)) {
            $("#modalview").modal('show');
            var records = getResultObj(data);
            if (records) {
                var gk = records[0];
                var qkindex = gk.qk;
                var wtindex = gk.wt;
                var indexval = 1;
                var ztqk = "";
                switch (gk.ztqk) {
                    case 1:
                        ztqk = "好";
                        break;
                    case 2:
                        ztqk = "较好";
                        break;
                    case 3:
                        ztqk = "一般";
                        break;
                    case 4:
                        ztqk = "差";
                        break;
                    default :
                        ztqk = "好";
                        break;
                }
                $("#tdhdmc").text(gk.hdmc);
                $("#tdztqk").text(ztqk);
                $("#tdtime").text(getTimeFromStr(gk.starttime, "yyyy-MM-dd HH:mm:ss").format("yyyy年MM月dd日HH时mm分ss秒") + " 至 " + getTimeFromStr(gk.endtime, "yyyy-MM-dd HH:mm:ss").format("yyyy年MM月dd日HH时mm分ss秒"));
                $("#tdaddr").text(gk.qd + " 至 " + gk.zd);
                var trdtxczh = '';
                if (gk.qdzh != '' || gk.zdzh != '') {
                    trdtxczh = gk.qdzh + " 至 " + gk.zdzh;
                }
                $("#tdaddrzh").text(trdtxczh);
                $("#tdxhth").text(gk.xhth==null?'':gk.xhth);
                $("#tdcyr").text(gk.cyr);
                var dept = getResultMap(data).dept;
                $("#tddept").text(dept);
                $("#havbody").empty();
                $("#mtvbody").empty();
                $("#hbvbody").empty();
                $("#lhvbody").empty();
                $("#ahvbody").empty();
                $("#wzvbody").empty();
                $("#qtvbody").empty();
                for (var i = 0; i < records.length; i++) {
                    var obj = records[i];
                    if (qkindex != obj.qk) {
                        indexval = 1;
                        qkindex = obj.qk;
                    } else {
                        if (wtindex != obj.wt) {
                            wtindex = obj.wt;
                            indexval++;
                        }
                    }
                    switch (obj.qk) {
                        case 1:
                            $("#havbody").viewtr(obj, indexval);
                            break;
                        case 2:
                            $("#mtvbody").viewtr(obj, indexval);
                            break;
                        case 3:
                            $("#hbvbody").viewtr(obj, indexval);
                            break;
                        case 4:
                            $("#lhvbody").viewtr(obj, indexval);
                            break;
                        case 5:
                            $("#ahvbody").viewtr(obj, indexval);
                            break;
                        case 6:
                            $("#wzvbody").viewtr(obj, indexval);
                            break;
                        case 7:
                            $("#qtvbody").viewtr(obj, indexval);
                            break;
                    }
                }
                //附件处理
                var fjlist = getResultMap(data).fjlist;
                $("div[name='fjrow']").empty();
                for (var index in fjlist) {
                    var fjid = fjlist[index].id;
                    var fjname = fjlist[index].fname;
                    var fjsize = fjlist[index].fsize;
                    var fjtype = fjlist[index].ftype;
                    var rantoken = rand(1, 99999999);
                    var row = $('<div name="fjrow"></div>');
                    var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
                    row.append(col);
                    col.append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
                        + '<label style="font-weight:normal;" id="label' + rantoken + '">' + fjname
                        + "("
                        + fjsize
                        + "kb)"
                        + '</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                        + '<a class="btn btn-link" href="downloadcxcfj?id=' + fjid + '">下载</a>'
                    );
                    if (fjtype == 1) {
                        col.append('<a class="btn btn-link" onclick="viewattachment(' + fjid + ')">预览</a>');
                    }
                    $("#divform").append(row);
                }
            }
        }
    });
}

function editload(id) {
    $("#xcid").val(id);
    ajax('querycruisebyid', {
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
                    autoajaxstart: {name: 'updategk.starttime'},
                    autoajaxend: {name: 'updategk.endtime'}
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

function editxc() {
    var id = $("#xcid").val();
    var ztqk = $("input[name='updateztqk']:checked").val();
    var flag = $("#updateyhlxd").is(':checked') ? 1 : 0;
    var updategk = {};
    updategk.qks = new Array();

    //护岸
    var haqk = getXunChaTableData("hau");
    if (haqk.length > 0) {
        var ha = {};
        ha.qk = 1;
        ha.qksm = '护岸情况';
        ha.wts = haqk;
        updategk.qks.push(ha);
    }

    //码头
    var mtqk = getXunChaTableData("mtu");
    if (mtqk.length > 0) {
        var mt = {};
        mt.qk = 2;
        mt.qksm = '码头情况';
        mt.wts = mtqk;
        updategk.qks.push(mt);
    }

    //航标
    var hbqk = getXunChaTableData("hbu");
    if (hbqk.length > 0) {
        var hb = {};
        hb.qk = 3;
        hb.qksm = '航标情况';
        hb.wts = hbqk
        updategk.qks.push(hb);
    }

    //绿化
    var lhqk = getXunChaTableData("lhu");
    if (lhqk.length > 0) {
        var lh = {};
        lh.qk = 4;
        lh.qksm = '绿化情况';
        lh.wts = lhqk;
        updategk.qks.push(lh);
    }

    //碍航
    var ahqk = getXunChaTableData("ahu");
    if (ahqk.length > 0) {
        var ah = {};
        ah.qk = 5;
        ah.qksm = '碍航情况';
        ah.wts = ahqk;
        updategk.qks.push(ah);
    }

    //违章
    var wzqk = getXunChaTableData("wzu");
    if (wzqk.length > 0) {
        var wz = {};
        wz.qk = 6;
        wz.qksm = '违章情况';
        wz.wts = wzqk;
        updategk.qks.push(wz);
    }

    //其他
    var qtqk = getXunChaTableData("qtu");
    if (qtqk.length > 0) {
        var qt = {};
        qt.qk = 7;
        qt.qksm = '其它情况';
        qt.wts = qtqk;
        updategk.qks.push(qt);
    }

    var strjson = JSON.stringify(updategk);
    var data = {
        id: id,
        flag: flag,
        'updategk.ztqk': ztqk,
        jsondata: strjson
    };
    for (var index in deletefileids) {
        var fjid = deletefileids[index];
        data["delfileids[" + index + "]"] = fjid;
    }
    ;
    $("#divupdate").validateForm(function () {
        $("#divupdate").autoajaxfileuploading('updatecruise', data, function (result, data) {
                if (ifResultOK(result)) {
                    alert("更新巡查记录成功");
                    $("#modalupdate").modal('hide');
                    deletefileids.length = 0;
                    loadxc();
                } else {
                    alert(getResultDesc(result));
                }
            }
        );
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
        '<img style="width:100%;height:auto;" src="downloadcxcfj?id=' + id + '">');
    $("#modalpicview").modal('show');
}
/*********************end处理附件信息js*****************************/

