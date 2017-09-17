$(document).ready(function () {
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


                $("#updatehdao").empty();
                loadsingleselgcd(null, "updategcd");
                $("#updatehdao").addselect({
                    data: data,
                    onchange: function () {
                        var selitems = $("#multiupdatehdao").val();
                        loadsingleselgcd(selitems, "updategcd");
                    },
                    id: "multiupdatehdao",
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
    $("#addshipname").validateTargetBind({notempty: {msg: "请输入船舶名称"}});
    $("#addshipton").validateTargetBind({double: {}, notempty: {msg: "请输入船舶吨位"}});
    $("#addgoodston").validateTargetBind({double: {}, notempty: {msg: "请输入货物运量"}});
    var date = $("#seldaynew").attr('realvalue');
    var starttime = date + " 00:00:00";
    var endtime = date + " 23:59:59";
    var gcdtypeid = $("#multidivnewselgcz").val().split(',');
    var shipdire = $("#addfx").val();
    var shipempty = $("#addshipempty").val();
    var shiptype = $("#selcblx").val();
    var goodstype = $("#selzhzl").val();
    $("#formnew").validateForm(function () {
        var url = 'addshiptraffic';
        var data = {
            'cbllgc.type': gcdtypeid[0],
            'cbllgc.gcdid': gcdtypeid[1],
            'cbllgc.starttime': starttime,
            'cbllgc.endtime': endtime,
            'cbllgc.shipdire': shipdire,
            'cbllgc.shipempty': shipempty,
            'cbllgc.shiptype': shiptype,
            'cbllgc.goodstype': goodstype
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
        url: "traffic/queryshiptraffic",
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
                    dt.shipname = sj.shipname;
                    dt.shipdire = sj.shipdire;
                    dt.shiptype = sj.shiptype;
                    dt.shipton = sj.shipton;
                    dt.shipempty = sj.shipempty;
                    dt.goodstype = sj.goodstype;
                    dt.goodston = sj.goodston;
                    dt.date = sj.starttime;
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
            mDataProp: 'shipname'
        }, {
            mDataProp: 'shipdire'
        }, {
            mDataProp: 'shiptype'
        }, {
            mDataProp: 'shipton'
        }, {
            mDataProp: 'shipempty'
        }, {
            mDataProp: 'goodstype'
        }, {
            mDataProp: 'goodston'
        }, {
            mDataProp: 'date'
        }, {
            mDataProp: null
        }],
        columndefs: [{
            "render": function (data, type, row) {
                if (data == 1)
                    return "上行";
                if (data == 2)
                    return "下行";
            },
            "targets": 3
        }, {
            "render": function (data, type, row) {
                if (data == 1)
                    return "是";
                if (data == 2)
                    return "否";
            },
            "targets": 6
        },
            {
                targets: 10,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="view(\''
                        + row.id
                        + '\')">查看</a>'
                        + '<a class="btn btn-link btnoper" onclick="updateload('
                        + row.id
                        + ')">修改</a>'
                        + '<a class="btn btn-link btnoper" onclick="delsingle('
                        + row.id + ',\'' + row.shipname
                        + '\')">删除</a>';
                    return ret;
                }
            }
        ],
        fncreatedrow: function (nRow, aData, iDataIndex) {
            var start = parseInt($('#tableRecords')
                .attr('start'));
            $("td:eq(0)", nRow).html(
                '<input type="checkbox" name="branchcheckbox" shipname="' + aData.shipname + '" llid=' + aData.id + ' id="cb' + (start + iDataIndex)
                + '" value="'
                + (start + iDataIndex) + '">&nbsp;'
                + (start + iDataIndex))
        }
    });
}
function delsingle(id, gcd) {
    var delids = new Array();
    delids.push(id);
    if (delids.length <= 0) {
        alert('请先选择要删除的流量观测！');
        return false;
    }
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
    ajax('delshiptraffic', data, function (result) {
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
            var id = $(this).attr('llid');
            delids.push(id);
        }
    });
    if (delids.length <= 0) {
        alert('请先选择要删除的流量观测！');
        return false;
    }
    $("#modaldelete").attr('delids', delids.join(","));
    $("#modaldelete").modal('show');
}

function view(id) {
    ajax('queryshiptrafficinfo', {
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            $("#modalview").modal('show');
            var records = getResultRecords(result).data[0];
            $("#viewgcd").text(records[2].hdmc + "---" + records[1].mc);
            $("#viewtimeregion").text(records[0].starttime.replace("T", " "));
            $("#viewshipname").text(records[0].shipname);
            $("#viewfx").text(records[0].shipdire == 1 ? "上行" : "下行");
            $("#viewshiptype").text(records[3].attrdesc);
            $("#viewshipton").text(records[0].shipton);
            $("#viewshipempty").text(records[0].shipempty == 1 ? "是" : "否");
            $("#viewgoodstype").text(records[4].attrdesc);
            $("#viewgoodston").text(records[0].goodston);
        } else {
            alert(getResultDesc(result));
        }

    });
}

function updateload(id) {
    ajax('queryshiptrafficinfo', {
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            var records = getResultRecords(result).data[0];
            var ll = records[0];
            var gcd = records[1];
            var hdao = records[2];
            var starttime = ll.starttime;
            $("#llid").val(ll.id);
            $("#updatehdao").addselect({
                defaultval: hdao.id,
                id: "multiupdatehdao"
            });
            $("#updategcd").addselect({
                defaultval: ll.type + "," + ll.gcdid,
                id: "multiupdategcd"
            });
            $("#updatetimeregion").addday({
                id: 'updatetime',
                hint: '请选择时间',
                validators: {notempty: {msg: "请选择观测日期"}},
                defaultval: starttime
            });
            $("#updateshipname").val(ll.shipname);
            $("#updateshipton").val(ll.shipton);
            $("#updategoodston").val(ll.goodston);
            $("#updatefx option[value='" + ll.shipdire + "']").attr("selected", true);
            $("#updateshipempty option[value='" + ll.shipempty + "']").attr("selected", true);
            $("#updateshiptype option[value='" + ll.shiptype + "']").attr("selected", true);
            $("#updategoodstype option[value='" + ll.goodstype + "']").attr("selected", true);
            $("#modalupdate").modal('show');
        } else {
            alert(getResultDesc(result));
        }

    });
}

function update() {
    $("#formupdate").validateForm(function () {
        var url = 'updateshiptraffic';
        var id = $("#llid").val();
        var date = $("#updatetime").attr('realvalue');
        var starttime = date + " 00:00:00";
        var endtime = date + " 23:59:59";
        var gcdtypeid = $("#multiupdategcd").val().split(',');
        var shipdire = $("#updatefx").val();
        var shipempty = $("#updateshipempty").val();
        var shiptype = $("#updateshiptype").val();
        var goodstype = $("#updategoodstype").val();
        var data = {
            'id': id,
            'cbllgc.type': gcdtypeid[0],
            'cbllgc.gcdid': gcdtypeid[1],
            'cbllgc.starttime': starttime,
            'cbllgc.endtime': endtime,
            'cbllgc.shipdire': shipdire,
            'cbllgc.shipempty': shipempty,
            'cbllgc.shiptype': shiptype,
            'cbllgc.goodstype': goodstype
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

