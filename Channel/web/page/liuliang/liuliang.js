$(document).ready(function () {
    //添加新增或更新记录时的输入验证器
    for (var i = 0; i < 30; i++) {
        $("#add" + i).validateTargetBind({double: {}});
        $("#update" + i).validateTargetBind({double: {}});
    }
    loadselhdao();
    loadliuliang();

    $("#cbselall").bind('click', function () {
        selallll();
    });

    $('#btndelll').click(function () {
        var delids = $("#modaldelete").attr('delids').split(',');
        delll(delids);
    });
});

function selallll() {
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
    var xzqh = 1;
    xzqh = $("#deptxzqh").val();
    ajax('hangdao/queryallhangdao', {
        'loginid': $("#userid").val(),
        'sfgg': '1',
        'xzqh': xzqh
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
                loadmultiselgcd();
                $("#divselhdao").addmultiselect({
                    data: data,
                    onchange: function (selitems) {
                        var selitemlist = new Array();
                        try {
                            if (selitems != null) {
                                for (var i = 0; i < selitems.length; i++) {
                                    var item = selitems[i].trim();
                                    if (item != null && item.length > 0 && item != '')
                                        selitemlist.push(item);
                                }
                            }
                        } catch (e) {
                        }
                        loadmultiselgcd(selitemlist);
                    },
                    id: "multidivselhdao"
                });


                $("#divnewselhdao").empty();
                loadsingleselgcd(null, "divnewselgcz");
                $("#divnewselhdao").addselect({
                    data: data,
                    onchange: function () {
                        var selitems = $("#multidivnewselhdao").val();
                        loadsingleselgcd(selitems, "divnewselgcz");
                    },
                    id: "multidivnewselhdao",
                    selectstyle: 'width:100%;padding-right:0px;'
                });


                $("#divupdateselhdao").empty();
                loadsingleselgcd(null, "divupdateselgcz");
                $("#divupdateselhdao").addselect({
                    data: data,
                    onchange: function () {
                        var selitems = $("#multidivupdateselhdao").val();
                        loadsingleselgcd(selitems, "divupdateselgcz");
                    },
                    id: "multidivupdateselhdao",
                    selectstyle: 'width:100%;padding-right:0px;'
                });
            }
        }
    });
}

function loadmultiselgcd(hdaoidlist) {
    var data = {
        'loginid': $("#userid").val()
    };
    if (hdaoidlist != null && hdaoidlist.length > 0) {
        for (var i = 0; i < hdaoidlist.length; i++) {
            data['ids[' + i + ']'] = hdaoidlist[i];
        }
    }
    ajax('traffic/querygcdbyids', data, function (result) {
        if (ifResultOK(result)) {
            var data = {};
            try {
                var jggcds = getResultMap(result).jggcds.data;
                for (var index in jggcds) {
                    var gcdid = jggcds[index].id;
                    var gcdmc = jggcds[index].mc;
                    data[GCZ_JG + '_' + gcdid] = gcdmc;
                }
            }
            catch (e) {
            }
            try {
                var rggcds = getResultMap(result).rggcds.data;
                for (var index in rggcds) {
                    var gcdid = rggcds[index].id;
                    var gcdmc = rggcds[index].mc;
                    data[GCZ_RG + '_' + gcdid] = gcdmc;
                }
            }
            catch (e) {
            }
            $("#divselgcz").empty();
            $("#divselgcz").addmultiselect({
                data: data,
                id: "multidivselgcz",
                onchange: function (val) {
                    loadliuliang();
                }
            });
        }
    });
}

function loadsingleselgcd(hdaoid, seldiv) {
    var data = {
        'loginid': $("#userid").val()
    };
    if (hdaoid != null && hdaoid != -1) {
        data['hdaoid'] = hdaoid;
    }
    ajax('traffic/querygcdbyhdao', data, function (result) {
        if (ifResultOK(result)) {
            var data = {};
            try {
                var jggcds = getResultMap(result).jggcds.data;
                for (var index in jggcds) {
                    var gcdid = jggcds[index].id;
                    var gcdmc = jggcds[index].mc;
                    data[GCZ_JG + ',' + gcdid] = gcdmc;
                }
            }
            catch (e) {
            }
            try {
                var rggcds = getResultMap(result).rggcds.data;
                for (var index in rggcds) {
                    var gcdid = rggcds[index].id;
                    var gcdmc = rggcds[index].mc;
                    data[GCZ_RG + ',' + gcdid] = gcdmc;
                }
            }
            catch (e) {
            }
            $("#" + seldiv).empty();
            $("#" + seldiv).addselect({
                data: data,
                id: "multi" + seldiv,
                validators: {notempty: {msg: "请选择观测点"}},
            });

        }
    });
}

function addnew() {
    $("#formnew").validateForm(function () {
        var url = 'addtraffic';
        var date = $("#seldaynew").attr('realvalue');
        var starttime = date + " 00:00:00";
        var endtime = date + " 23:59:59";
        var gcztypeid = $("#multidivnewselgcz").val().split(',');
        var data = {
            'loginid': $("#userid").val(),
            'hdllgc.type': gcztypeid[0],
            'hdllgc.gczid': gcztypeid[1],
            'hdllgc.starttime': starttime,
            'hdllgc.endtime': endtime,
            'hdllgc.timeflag': 1
        };
        $("#formnew").autoajax(url, data, function (result, data) {
            if (ifResultOK(result)) {
                $("#modalnew").modal('hide');
                loadliuliang();
            }
            else {
                alert(getResultDesc(result));
            }

        })
    });
}

function loadliuliang() {
    var starttime = $("#seldaystart").attr('realvalue');
    var endtime = $("#seldayend").attr('realvalue');
    var selgcdstr = $("#multidivselgcz").attr('selitem');
    var data = new Data();
    data.addDataObj('loginid', $("#userid").val());
    data.addDataObj('flag', 1);
    if (starttime != null && starttime.trim() != '') {
        data.addDataObj('starttime', starttime + " 00:00:00");
    }

    if (endtime != null && endtime.trim() != '') {
        data.addDataObj('endtime', endtime + " 23:59:59");
    }

    if (selgcdstr != null && selgcdstr != '') {
        data.addDataListStr('strids', selgcdstr);
    }

    $("#tableRecords").adddatatable({
        url: "traffic/querytraffic",
        data: data,
        datafn: function (data) {
            var records = getResultRecords(data);
            var ret = {};
            var list = new Array();
            if (records != null) {
                for (var i in records.data) {
                    var dt = {};
                    var sj = records.data[i];
                    dt.id = sj.id;
                    dt.gcd = sj.mc;
                    dt.type = sj.type;
                    dt.date = sj.starttime.replace("T", " ");

                    dt.upcbnum = sj.upcbnum;
                    dt.upcbton = sj.upcbton;
                    dt.upgoodston = sj.upgoodston;

                    dt.downcbnum = sj.downcbnum;
                    dt.downcbton = sj.downcbton;
                    dt.downgoodston = sj.downgoodston;

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
            mDataProp: 'gcd'
        }, {
            mDataProp: 'type'
        }, {
            mDataProp: 'date'
        }, {
            mDataProp: 'upcbnum'
        }, {
            mDataProp: 'upcbton'
        }, {
            mDataProp: 'upgoodston'
        }, {
            mDataProp: 'downcbnum'
        }, {
            mDataProp: 'downcbton'
        }, {
            mDataProp: 'downgoodston'
        }, {
            mDataProp: null
        }],
        columndefs: [
            {
                "render": function (data, type, row) {
                    if (data == 1)
                        return "激光流量观测站";
                    if (data == 2)
                        return "人工流量观测站";
                },
                "targets": 2
            }, {
                targets: 10,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="view(\''
                        + row.id
                        + '\')">查看</a>'
                        + '<a class="btn btn-link btnoper" onclick="updateload('
                        + row.id
                        + ')">修改</a>'
                        + '<a class="btn btn-link btnoper" onclick="delsingle('
                        + row.id + ',\'' + row.gcd
                        + '\')">删除</a>';
                    return ret;
                }
            }
        ],
        fncreatedrow: function (nRow, aData, iDataIndex) {
            var start = parseInt($('#tableRecords')
                .attr('start'));
            $("td:eq(0)", nRow).html(
                '<input type="checkbox" name="branchcheckbox" gcd="' + aData.gcd + '" tzid=' + aData.id + ' id="cb' + (start + iDataIndex)
                + '" value="'
                + (start + iDataIndex) + '">&nbsp;'
                + (start + iDataIndex))
        }
    });
}

function delsingle(id, gcd) {
    var delids = new Array();
    //var delnames = new Array();

    delids.push(id);
    //delnames.push(gcd);

    if (delids.length <= 0) {
        alert('请先选择要删除的流量观测！');
        return false;
    }
    //$("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldelete").attr('delids', delids.join(","));
    $("#modaldelete").modal('show');

}

function delll(ids) {
    var data = {
        loginid: $("#userid").val()
    };
    for (var index in ids) {
        var id = ids[index];
        data["ids[" + index + "]"] = id;
    }
    ajax('deltraffic', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldelete").modal('hide');
            loadliuliang();
        } else {
            alert(getResultDesc(result));
        }
    });
}

function delmulti() {
    var delids = new Array();
    var delnames = new Array();
    $("[name='branchcheckbox']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('tzid');
            delids.push(id);
            //var gcd = $(this).attr('gcd');
            //delnames.push(gcd);
        }
    });
    if (delids.length <= 0) {
        alert('请先选择要删除的流量观测！');
        return false;
    }
    // $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldelete").attr('delids', delids.join(","));
    $("#modaldelete").modal('show');
}

function view(id) {
    ajax('querytrafficinfo', {
        loginid: $("#userid").val(),
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            $("#modalview").modal('show');
            var records = getResultRecords(result).data[0];
            $("#divviewselhdao").text(records[2].hdmc);
            $("#divviewselgcz").text(records[1].mc);
            //$("#divviewtimeregion").text(records[0].starttime+"至"+records[0].endtime);
            $("#divviewtimeregion").text(records[0].starttime);
            $("#view1").text(getColZero(records[0].uptlnum));
            $("#view2").text(getColZero(records[0].downtlnum));
            $("#view3").text(getColZero(records[0].upjdbnum));
            $("#view4").text(getColZero(records[0].downjdbnum));
            $("#view5").text(getColZero(records[0].upfjdbnum));
            $("#view6").text(getColZero(records[0].downfjdbnum));
            $("#view7").text(getColZero(records[0].upqtcnum));
            $("#view8").text(getColZero(records[0].downqtcnum));
            $("#view9").text(getColZero(records[0].upcbnum));
            $("#view10").text(getColZero(records[0].downcbnum));

            $("#view11").text(getColZero(records[0].uptlton));
            $("#view12").text(getColZero(records[0].downtlton));
            $("#view13").text(getColZero(records[0].upjdbton));
            $("#view14").text(getColZero(records[0].downjdbton));
            $("#view15").text(getColZero(records[0].upfjdbton));
            $("#view16").text(getColZero(records[0].downfjdbton));
            $("#view17").text(getColZero(records[0].upqtcton));
            $("#view18").text(getColZero(records[0].downqtcton));
            $("#view19").text(getColZero(records[0].upcbton));
            $("#view20").text(getColZero(records[0].downcbton));

            $("#view21").text(getColZero(records[0].upmtton));
            $("#view22").text(getColZero(records[0].downmtton));
            $("#view23").text(getColZero(records[0].upkjclton));
            $("#view24").text(getColZero(records[0].downkjclton));
            $("#view25").text(getColZero(records[0].upqtcton));
            $("#view26").text(getColZero(records[0].downqtcton));
            $("#view27").text(getColZero(records[0].upgoodston));
            $("#view28").text(getColZero(records[0].downgoodston));

        } else {
            alert(getResultDesc(result));
        }

    });
}

function updateload(id) {
    ajax('querytrafficinfo', {
        loginid: $("#userid").val(),
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            var records = getResultRecords(result).data[0];
            var ll = records[0];
            var gcz = records[1];
            var hdao = records[2];
            var starttime = ll.starttime;
            $("#divupdateid").empty();
            $("#divupdateid").addhidden({
                defaultval: id,
                id: "llid"
            });
            $("#divupdateselhdao").addselect({
                defaultval: hdao.id,
                id: "multidivupdateselhdao"
            });
            $("#divupdateselgcz").addselect({
                defaultval: ll.type + "," + ll.gczid,
                id: "multidivupdateselgcz"
            });
            $("#divupdatetimeregion").addday({
                id: 'seldayupdate',
                defaultval: starttime
            });
            $("#update1").val(getColZero(ll.uptlnum));
            $("#update2").val(getColZero(ll.downtlnum));
            $("#update3").val(getColZero(ll.upjdbnum));
            $("#update4").val(getColZero(ll.downjdbnum));
            $("#update5").val(getColZero(ll.upfjdbnum));
            $("#update6").val(getColZero(ll.downfjdbnum));
            $("#update7").val(getColZero(ll.upqtcnum));
            $("#update8").val(getColZero(ll.downqtcnum));
            $("#update9").val(getColZero(ll.upcbnum));
            $("#update10").val(getColZero(ll.downcbnum));

            $("#update11").val(getColZero(ll.uptlton));
            $("#update12").val(getColZero(ll.downtlton));
            $("#update13").val(getColZero(ll.upjdbton));
            $("#update14").val(getColZero(ll.downjdbton));
            $("#update15").val(getColZero(ll.upfjdbton));
            $("#update16").val(getColZero(ll.downfjdbton));
            $("#update17").val(getColZero(ll.upqtcton));
            $("#update18").val(getColZero(ll.downqtcton));
            $("#update19").val(getColZero(ll.upcbton));
            $("#update20").val(getColZero(ll.downcbton));

            $("#update21").val(getColZero(ll.upmtton));
            $("#update22").val(getColZero(ll.downmtton));
            $("#update23").val(getColZero(ll.upkjclton));
            $("#update24").val(getColZero(ll.downkjclton));
            $("#update25").val(getColZero(ll.upqtcton));
            $("#update26").val(getColZero(ll.downqtcton));
            $("#update27").val(getColZero(ll.upgoodston));
            $("#update28").val(getColZero(ll.downgoodston));

            $("#modalupdate").modal('show');

        } else {
            alert(getResultDesc(result));
        }

    });
}

function update() {
    $("#formupdate").validateForm(function () {
        var url = 'updatetraffic';
        var id = $("#llid").val();
        var date = $("#seldayupdate").attr('realvalue');
        var starttime = date + " 00:00:00";
        var endtime = date + " 23:59:59";
        var gcztypeid = $("#multidivupdateselgcz").val().split(',');
        var data = {
            'loginid': $("#userid").val(),
            'id': id,
            'hdllgc.type': gcztypeid[0],
            'hdllgc.gczid': gcztypeid[1],
            'hdllgc.starttime': starttime,
            'hdllgc.endtime': endtime
        };
        $("#formupdate").autoajax(url, data, function (result, data) {
            if (ifResultOK(result)) {
                $("#modalupdate").modal('hide');
                loadliuliang();
            }
            else {
                alert(getResultDesc(result));
            }
        })
    });
}

function setFloatNum(n) {
    if (n == '') {
        return 0;
    } else {
        return parseFloat(n);
    }
}

function statCbUpNum() {
    $("#add9").val(setFloatNum($("#add1").val()) + setFloatNum($("#add3").val()) + setFloatNum($("#add5").val()) + setFloatNum($("#add7").val()));
}
function statCbBotNum() {
    $("#add10").val(setFloatNum($("#add2").val()) + setFloatNum($("#add4").val()) + setFloatNum($("#add6").val()) + setFloatNum($("#add8").val()));
}

function statCbUpTon() {
    $("#add19").val(setFloatNum($("#add11").val()) + setFloatNum($("#add13").val()) + setFloatNum($("#add15").val()) + setFloatNum($("#add17").val()));
}
function statCbBotTon() {
    $("#add20").val(setFloatNum($("#add12").val()) + setFloatNum($("#add14").val()) + setFloatNum($("#add16").val()) + setFloatNum($("#add18").val()));
}

function statGoodsUpTon() {
    $("#add27").val(setFloatNum($("#add21").val()) + setFloatNum($("#add23").val()) + setFloatNum($("#add25").val()));
}
function statGoodsBotTon() {
    $("#add28").val(setFloatNum($("#add22").val()) + setFloatNum($("#add24").val()) + setFloatNum($("#add26").val()));
}
function updateCbUpNum() {
    $("#update9").val(setFloatNum($("#update1").val()) + setFloatNum($("#update3").val()) + setFloatNum($("#update5").val()) + setFloatNum($("#update7").val()));
}
function updateCbBotNum() {
    $("#update10").val(setFloatNum($("#update2").val()) + setFloatNum($("#update4").val()) + setFloatNum($("#update6").val()) + setFloatNum($("#update8").val()));
}
function updateCbUpTon() {
    $("#update19").val(setFloatNum($("#update11").val()) + setFloatNum($("#update13").val()) + setFloatNum($("#update15").val()) + setFloatNum($("#update17").val()));
}
function updateCbBotTon() {
    $("#update20").val(setFloatNum($("#update12").val()) + setFloatNum($("#update14").val()) + setFloatNum($("#update16").val()) + setFloatNum($("#update18").val()));
}

function updateGoodsUpTon() {
    $("#update27").val(setFloatNum($("#update21").val()) + setFloatNum($("#update23").val()) + setFloatNum($("#update25").val()));
}
function updateGoodsBotTon() {
    $("#update28").val(setFloatNum($("#update22").val()) + setFloatNum($("#update24").val()) + setFloatNum($("#update26").val()));
}