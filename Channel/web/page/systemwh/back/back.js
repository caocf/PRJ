/**
 * Created by 25019 on 2015/10/21.
 */
$(document).ready(function () {
    commoninit("备份还原");

    $("#modalnew").bind('show.bs.modal', function () {
        loadtable();
        showaddmodal();
        showscheduletype();
    });

    loadschedulelist();
});

function loadtable() {
    var data = new Data();
    $("#seltables").empty();
    $("#seltableall").prop('checked', false);
    ajax("backrestore/querytables", data, function (result) {
        if (ifResultOK(result)) {
            var tablelist = getResultRecords(result).data;
            for (var i = 0; i < tablelist.length; i++) {
                var table = tablelist[i];
                var tablename = table.tablename;
                var tablecomment = table.comment;
                var tablecnt = table.datacnt;

                var tr = $('<tr></tr>');
                tr.append('<td><input type="checkbox" tablename="' + tablename + '" name="seltablecb">' + (i + 1) + '</td>');
                tr.append('<td>' + tablename + '</td>');
                tr.append('<td>' + tablecomment + '</td>');
                tr.append('<td class="text-center">' + tablecnt + '</td>');
                $("#seltables").append(tr);
            }
        } else {
            alert(getResultDesc(result));
        }
    });
}

function selalltable() {
    if ($("#seltableall").prop('checked') == true) {
        $("#seltable").find("[name*='seltablecb']").prop('checked', true);
    } else {
        $("#seltable").find('[name*=seltablecb]').prop('checked', false);
    }
}

function showaddmodal() {
    showaddstep1();
}

function showaddstep1() {
    $("#bodystep1").show();
    $("#bodystep2").hide();

    $("#footstep1").show();
    $("#footstep2").hide();
}

function showaddstep2() {
    var backname = $("#backname").val();
    if (backname == null || backname == '') {
        $("#backname").focus();
        alert('请输入备份名');
        return;
    }
    var selectlist = new Array();
    $("#seltable").find("[name*='seltablecb']").each(function () {
        if ($(this).prop('checked') == true) {
            selectlist.push($(this).attr('tablename'));
        }
    });
    if (selectlist.length <= 0) {
        alert('请选择要备份的数据表');
        return;
    }

    $("#bodystep2").show();
    $("#bodystep1").hide();

    $("#footstep2").show();
    $("#footstep1").hide();
}

function showscheduletype() {
    var type = $("#backtype").val();
    switch (parseInt(type)) {
        case 1:
            $("#rowmonth").show();
            $("#rowweek").hide();
            $("#rowtime").show();
            $("#rowdaytime").hide();
            $("#rowtimeregion").hide();
            break;
        case 2:
            $("#rowmonth").hide();
            $("#rowweek").show();
            $("#rowtime").show();
            $("#rowdaytime").hide();
            $("#rowtimeregion").hide();
            break;
        case 3:
            $("#rowmonth").hide();
            $("#rowweek").hide();
            $("#rowtime").show();
            $("#rowdaytime").hide();
            $("#rowtimeregion").hide();
            break;
        case 4:
            $("#rowmonth").hide();
            $("#rowweek").hide();
            $("#rowtime").hide();
            $("#rowdaytime").show();
            $("#rowtimeregion").show();
            break;
    }
}


function addbackschedule(enable) {
    var data = new Data();
    var backname = $("#backname").val();
    var backcontent = backname;

    if ($("#seltableall").prop('checked') == true) {
        data.addDataObj('backall', true);
    } else {
        var selectlist = new Array();
        data.addDataObj('backall', false);
        $("#seltable").find("[name*='seltablecb']").each(function () {
            if ($(this).prop('checked') == true) {
                selectlist.push($(this).attr('tablename'));
            }
        });
        data.addDataList('tablenames', selectlist);
    }

    if (enable != null && enable == true) {
        data.addDataObj("ifenable", true);
    } else {
        data.addDataObj("ifenable", false);
    }

    var cronexpression = '';
    var second = "*", minutes = "*", hours = "*", dayofmonth = "*", month = "*", dayofweek = "?";

    //根据类型生成相关的cron表达式
    var type = $("#backtype").val();
    data.addDataObj('type', type);
    switch (parseInt(type)) {
        case 1:
            var selarray = new Array();
            $('#1212asdf').find("[type='checkbox']").each(function () {
                if ($(this).prop('checked') == true) {
                    selarray.push($(this).val());
                }
            });
            if (selarray.length <= 0) {
                alert('请选择每月哪几天进行备份');
                return;
            }
            dayofmonth = selarray.join(',');
            var seltime = $('#seltime').attr('realvalue');
            if (seltime == null || seltime == '') {
                alert('请选择备份时间');
                return;
            }
            seltime = seltime.split(':');
            second = seltime[2];
            minutes = seltime[1];
            hours = seltime[0];
            break;
        case 2:
            dayofmonth = "?";
            var selarray = new Array();
            $('#xxxxxx').find("[type='checkbox']").each(function () {
                if ($(this).prop('checked') == true) {
                    selarray.push($(this).val());
                }
            });
            if (selarray.length <= 0) {
                alert('请选择每周哪几天进行备份');
                return;
            }
            dayofweek = selarray.join(',');
            var seltime = $('#seltime').attr('realvalue');
            if (seltime == null || seltime == '') {
                alert('请选择备份时间');
                return;
            }
            seltime = seltime.split(':');
            second = seltime[2];
            minutes = seltime[1];
            hours = seltime[0];
            break;
        case 3:
            var seltime = $('#seltime').attr('realvalue');
            seltime = seltime.split(':');
            second = seltime[2];
            minutes = seltime[1];
            hours = seltime[0];
            if (seltime == null || seltime == '') {
                alert('请选择备份时间');
                return;
            }
            break;
        case 4:
            if ($("#timeregion").val() == null || $("#timeregion").val() == '') {
                alert('请输入备份间隔时间');
                return;
            }
            data.addDataObj('secondsinterval', $("#timeregion").val());

            var seltime = $('#seldaytime').attr('realvalue');
            if (seltime == null || seltime == '') {
                alert('请选择备份开始时间');
                return;
            }
            data.addDataObj('starttime', seltime);

            break;
    }

    cronexpression = second + " " + minutes + " " + hours + " " + dayofmonth + " " + month + " " + dayofweek;

    data.addDataObj('cronexpression', cronexpression);
    data.addDataObj('backname', backname);
    data.addDataObj('backcontent', backcontent);
    data.addDataObj('cronexpression', cronexpression);

    ajax('backrestore/addbackschedule', data, function (result) {
        if (ifResultOK(result)) {
            loadschedulelist();
        } else {
            alert(getResultDesc(result));
        }
        $('#modalnew').modal('hide');
    });
}

function loadschedulelist() {

    $("#tableschedule").adddatatable({
        url: "backrestore/querybackschedules",
        data: {},
        page: $("#tableschedule").attr('page'),
        datafn: function (data) {
            var records = getResultRecords(data);
            var ret = {};
            var list = new Array();
            if (records != null) {
                for (var i in records.data) {
                    var sj = records.data[i];
                    var dt = {};
                    dt.id = sj.id;
                    dt.ifenable = sj.ifenable;
                    dt.backname = sj.backname;
                    dt.runtimestatus = sj.runtimestatus;
                    dt.backschedule = sj.backschedule;
                    list.push(dt);
                }
            }
            ret.data = list;
            ret.total = records.total;
            ret.secho = data.map.sEcho;


            if (ret.total >= 10) {
                $('#btnnewback').addClass('disabled');
            } else {
                $('#btnnewback').removeClass('disabled');
            }

            return ret;
        },
        columns: [{
            mDataProp: null
        }, {
            mDataProp: 'backname'
        }, {
            mDataProp: 'backschedule'
        }, {
            mDataProp: 'status'
        }, {
            mDataProp: null
        }],
        columndefs: [{
            targets: 4,
            render: function (data, type, row) {
                var ret = '<a class="btn btn-link btnoper" onclick="viewschedule(' + row.id + ')">查看计划</a>';

                if (hasPerm("MAN_BACK")) {
                    if (row.ifenable == 0) {
                        ret += '<a class="btn btn-link btnoper" onclick="disableenableschedule(' + row.id + ',' + row.ifenable + ')">启用计划</a>'
                    } else {
                        ret += '<a class="btn btn-link btnoper" onclick="disableenableschedule(' + row.id + ',' + row.ifenable + ')">禁用计划</a>'
                    }
                    ret += '<a class="btn btn-link btnoper" onclick="$(\'#modaldel\').attr(\'delid\',' + row.id + ');' +
                        '$(\'#delname\').text(\'' + row.backname + '\');$(\'#modaldel\').modal(\'show\')">删除计划</a>';
                }
                return ret;
            }
        }, {
            targets: 3,
            render: function (data, type, row) {
                if (row.ifenable == 0) {
                    var ret = '<label style="color:red;">' + row.runtimestatus + '</label>'
                    return ret;
                } else {
                    var ret = '<label style="color:black;">' + row.runtimestatus + '</label>'
                    return ret;
                }
            }
        }],
        fncreatedrow: function (nRow, aData, iDataIndex) {
            var start = parseInt($('#tableschedule')
                .attr('start'));
            $("td:eq(0)", nRow).html((start + iDataIndex));
        }
    });
}

function disableenableschedule(id, ifenable) {
    if (ifenable == 0) {
        ajax('backrestore/enablebackschedule', {id: id}, function (result) {
            if (!ifResultOK(result)) {
                alert(getResultDesc(result));
            }
            loadschedulelist();
        });
    } else {
        ajax('backrestore/disablebackschedule', {id: id}, function (result) {
            if (!ifResultOK(result)) {
                alert(getResultDesc(result));
            }
            loadschedulelist();
        });
    }

}

function deleteschedule() {
    var id = $('#modaldel').attr('delid');
    ajax('backrestore/deletebackschedule', {id: id}, function (result) {
        if (!ifResultOK(result)) {
            alert(getResultDesc(result));
        }
        $('#modaldel').modal('hide');
        loadschedulelist();
    });
}

function viewschedule(id) {
    window.location.href = "page/systemwh/back/systemwh-backdtl.jsp?id=" + id;
}