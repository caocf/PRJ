/**
 * Created by 25019 on 2015/10/21.
 */
$(document).ready(function () {
    commoninit("备份还原");
    viewbackschedule();
    loadbackhistorylist();
});


function viewbackschedule() {
    var id = getscheduleid();
    ajax("backrestore/viewbackschedule", {id: id}, function (result) {
        if (ifResultOK(result)) {
            var map = getResultMap(result);
            var status = map.job.runtimestatus;
            var backname = map.job.backname;
            var backcontent = map.job.backcontent;
            var backschedule = map.backschedule;
            var prebacktime = map.prebacktime;
            var nextbacktime = map.nextbacktime;

            if (map.job.ifenable == 1) {
                $('#btnenable').addClass('hide');
                $('#btndisable').removeClass('hide');
            } else {
                $('#btnenable').removeClass('hide');
                $('#btndisable').addClass('hide');
            }

            $("#currstatus").text(status);
            $("#backname").text(backname);
            $("#backcontent").text(backcontent);
            $("#backschedule").text(backschedule);
            $("#prebacktime").text(prebacktime);
            $("#nextbacktime").text(nextbacktime);
        } else {
            alert(getResultDesc(result));
        }
    });
}

function disableschedule() {
    var id = getscheduleid();
    ajax('backrestore/disablebackschedule', {id: id}, function (result) {
        if (!ifResultOK(result)) {
            alert(getResultDesc(result));
        }
        window.location.reload();
    });
}
function enableschedule() {
    var id = getscheduleid();
    ajax('backrestore/enablebackschedule', {id: id}, function (result) {
        if (!ifResultOK(result)) {
            alert(getResultDesc(result));
        }
        window.location.reload();
    });
}

function backup() {
    setInterval("backuptimer()", 200);//1000为1秒钟
    $('#backupprogressbar').addClass('active');

    $("#btnbackup").addClass('disabled');
    var id = getscheduleid();
    ajaxloading('backrestore/backup', {id: id}, function (result) {
        if (!ifResultOK(result)) {
            alert(getResultDesc(result));
        } else {
            alert('备份成功');
        }
        window.location.reload();
    }, "正在备份，请稍候");
}

function getscheduleid() {
    return $('#scheduleid').val();
}

function loadbackhistorylist() {
    var id = getscheduleid();
    $("#tablehistory").adddatatable({
        url: "backrestore/querybackhistories",
        data: {id: id},
        page: $("#tablehistory").attr('page'),
        datafn: function (data) {
            var records = getResultRecords(data);
            var ret = {};
            var list = new Array();
            if (records != null) {
                for (var i in records.data) {
                    var sj = records.data[i];
                    var dt = {};
                    dt.id = sj.id;
                    dt.backtime = sj.backtime.replace('T', " ");
                    dt.backfilename = sj.backfilename;
                    dt.backfilesize = sj.backfilesize;
                    dt.backfilestatus = sj.backfilestatus;
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
            mDataProp: 'backtime'
        }, {
            mDataProp: 'backfilename'
        }, {
            mDataProp: 'backfilesize'
        }, {
            mDataProp: 'backfilestatus'
        }, {
            mDataProp: null
        }],
        columndefs: [{
            targets: 5,
            render: function (data, type, row) {
                var ret = '<a class="btn btn-link btnoper" onclick="restorehistoryload(' + row.id + ',\'' + row.backfilename + '\')">还原</a>' +
                    '<a class="btn btn-link btnoper" onclick="deletehistoryload(' + row.id + ',\'' + row.backfilename + '\')">删除</a>';
                return ret;
            },
            bVisible: hasPerm("MAN_BACK") ? true : false
        }, {
            targets: 4,
            render: function (data, type, row) {
                if (row.backfilestatus != '文件正常') {
                    var ret = '<label style="color:red;">' + row.backfilestatus + '</label>'
                    return ret;
                } else {
                    var ret = '<label style="color:black;">' + row.backfilestatus + '</label>'
                    return ret;
                }
            }
        }, {
            targets: 3,
            render: function (data, type, row) {
                var filesize = parseInt(row.backfilesize);
                if (filesize >= 1024) {
                    if (filesize < 1024 * 1024) {
                        filesize = Math.round((filesize / 1024 * 100) / 100) + "KB";
                    } else {
                        filesize = Math.round((filesize / (1024 * 1024) * 100) / 100) + "MB";
                    }
                } else {
                    filesize = filesize + "B";
                }
                return filesize;
            }
        }],
        fncreatedrow: function (nRow, aData, iDataIndex) {
            var start = parseInt($('#tablehistory')
                .attr('start'));
            $("td:eq(0)", nRow).html((start + iDataIndex));
        }
    });
}

function deletehistoryload(historyid, backfilename) {
    $("#modaldel").attr("historyid", historyid);
    $("#delname").text(backfilename);
    $("#modaldel").modal('show');
}

function deletehistory() {
    var historyid = $("#modaldel").attr("historyid");
    ajax('backrestore/deletebackhistory', {id: historyid}, function (result) {
        if (ifResultOK(result)) {
            loadbackhistorylist();
        } else {
            alert(getResultDesc(result));
        }
        $("#modaldel").modal('hide');
    });
}


function restorehistoryload(historyid, backfilename) {
    $("#modalrestore").attr("historyid", historyid);
    $("#restorename").text(backfilename);
    $("#modalrestore").modal('show');
}

function restorehistory() {
    var historyid = $("#modalrestore").attr("historyid");
    ajaxloading('backrestore/restore', {id: historyid}, function (result) {
        if (ifResultOK(result)) {
            $("#modalrestore").modal('hide');
            alert('还原成功');
        } else {
            alert(getResultDesc(result));
        }
    }, "正在恢复，请稍候");
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

function loadtable(callbk) {
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
            callbk();
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

function showupdateload() {
    var id = getscheduleid();
    loadtable(function () {
        ajax("backrestore/viewbackschedule", {id: id}, function (result) {
            if (ifResultOK(result)) {
                var map = getResultMap(result);
                var status = "当前状态：" + map.job.runtimestatus;
                var backname = map.job.backname;
                var type = map.job.type;
                var backcontent = map.job.backcontent;
                var backschedule = map.backschedule;
                var prebacktime = map.prebacktime;
                var nextbacktime = map.nextbacktime;
                var backtables = map.job.backtables;
                var cronexpression = map.job.cronexpression;
                var interval = map.job.interval;
                var starttime = map.job.starttime.replace('T', " ");

                if (backtables == null || backtables == '') {
                    $("#seltableall").prop('checked', true);
                    $("#seltable").find("[name*='seltablecb']").prop('checked', true);
                } else {
                    var backtables = backtables.split(',');
                    $("#seltable").find("[name*='seltablecb']").each(function () {
                        var tablename = $(this).attr('tablename');
                        for (var tbn in backtables) {
                            if (backtables[tbn] == tablename) {
                                $(this).prop("checked", true);
                            }
                        }
                    });
                }

                $("#updatebackname").val(backname);
                $("#backtype").find("option[value='" + type + "']").attr("selected", true);

                if (type == 1) {
                    var second, minutes, hours, dayofmonth, month, dayofweek;
                    var crons = cronexpression.split(" ");
                    second = crons[0];
                    minutes = crons[1];
                    hours = crons[2];
                    dayofmonth = crons[3];
                    month = crons[4];
                    dayofweek = crons[5];
                    $('#asfx').addonlytime({
                        id: 'seltime',
                        defaultval: hours + ":" + minutes + ":" + second
                    });

                    $("#1212asdf").find('input[type=\'checkbox\']').each(function () {
                        var val = $(this).val();
                        var days = dayofmonth.split(',');
                        for (var i in days) {
                            if (days[i] == val) {
                                $(this).prop("checked", true)
                            }
                        }
                    });
                }
                if (type == 2) {
                    var second, minutes, hours, dayofmonth, month, dayofweek;
                    var crons = cronexpression.split(" ");
                    second = crons[0];
                    minutes = crons[1];
                    hours = crons[2];
                    dayofmonth = crons[3];
                    month = crons[4];
                    dayofweek = crons[5];
                    $('#asfx').addonlytime({
                        id: 'seltime',
                        defaultval: hours + ":" + minutes + ":" + second
                    });

                    $("#xxxxxx").find('input[type=\'checkbox\']').each(function () {
                        var val = $(this).val();
                        var days = dayofweek.split(',');
                        for (var i in days) {
                            if (days[i] == val) {
                                $(this).prop("checked", true)
                            }
                        }
                    });
                }
                if (type == 3) {
                    var second, minutes, hours, dayofmonth, month, dayofweek;
                    var crons = cronexpression.split(" ");
                    second = crons[0];
                    minutes = crons[1];
                    hours = crons[2];
                    dayofmonth = crons[3];
                    month = crons[4];
                    dayofweek = crons[5];
                    $('#asfx').addonlytime({
                        id: 'seltime',
                        defaultval: hours + ":" + minutes + ":" + second
                    });
                }
                if (type == 4) {
                    $("#timeregion").val(interval);
                    $('#asfxxx').addtime({
                        id: 'seldaytime',
                        defaultval: starttime
                    })
                }
                showupdatestep1();
                showscheduletype();
                $("#modalupdate").modal('show');
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function showupdatestep1() {
    $("#bodystep1").show();
    $("#bodystep2").hide();

    $("#footstep1").show();
    $("#footstep2").hide();
}

function showupdatestep2() {
    var backname = $("#updatebackname").val();
    if (backname == null || backname == '') {
        $("#updatebackname").focus();
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


function updatebackschedule() {
    var data = new Data();
    var backname = $("#updatebackname").val();
    var backcontent = backname;
    var id = getscheduleid();

    data.addDataObj('id', id);

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

    ajax('backrestore/updatebackschedule', data, function (result) {
        if (ifResultOK(result)) {
            window.location.reload();
        } else {
            alert(getResultDesc(result));
        }
        $('#modalupdate').modal('hide');
    });
}