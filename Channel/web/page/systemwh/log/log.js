/**
 * Created by 25019 on 2015/10/21.
 */
$(document).ready(function () {
    commoninit("日志管理");

    $('#seldaystart').bind('change', loadlog);
    loadlog();
});

function loadlog() {
    var data = new Data();
    data.addDataObj('loginid', $("#userid").val());
    var startday = $('#seldaystart').attr('realvalue');
    var endday = $('#seldayend').attr('realvalue');
    if (startday != null && startday != '')
        data.addDataObj('starttime', startday + " 00:00:00");
    if (endday != null && endday != '')
        data.addDataObj('endtime', endday + " 23:59:59");

    $("#tableLog").adddatatable({
        url: "querylogs",
        data: data,
        page: $("#tableLog").attr('page'),
        datafn: function (data) {
            $("#cbselall").prop('checked', false);
            var records = getResultRecords(data);
            var ret = {};
            var list = new Array();
            if (records != null) {
                for (var i in records.data) {
                    var sj = records.data[i];
                    var dt = {};
                    dt.id = sj.id;
                    dt.opername = sj.opername;
                    dt.ipaddr = sj.ipaddr;
                    dt.modulename = sj.modulename;
                    dt.opname = sj.opname;
                    dt.remarks = sj.remarks;
                    dt.createtime = sj.createtime;

                    list.push(dt);
                }
            }
            ret.data = list;
            ret.total = records.total;
            ret.secho = data.obj.sEcho;
            return ret;
        },
        columns: [{
            mDataProp: null
        }, {
            mDataProp: 'opername'
        }, {
            mDataProp: 'ipaddr'
        }, {
            mDataProp: 'modulename'
        }, {
            mDataProp: 'opname'
        }, {
            mDataProp: 'remarks'
        }, {
            mDataProp: 'createtime'
        }, {
            mDataProp: null
        }],
        columndefs: [{
            targets: 7,
            render: function (data, type, row) {
                var ret = '<a class="btn btn-link btnoper" onclick="delsinglelogload('
                    + row.id + ')">删除</a>';
                return ret;
            },
            bVisible: $("#man_log_perm").val() == "true" ? true : false
        }],
        fncreatedrow: function (nRow, aData, iDataIndex) {
            var start = parseInt($('#tableLog')
                .attr('start'));
            $("td:eq(0)", nRow).html(
                '<input type="checkbox" name="Logcheckbox" logid='
                + aData.id + ' dw="' + aData.dw
                + '" id="cb' + (start + iDataIndex)
                + '" value="'
                + (start + iDataIndex) + '">&nbsp;'
                + (start + iDataIndex))
        }
    });
}

function selall() {
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

function delmultilogload() {
    var delidlist = new Array();
    $("[name='Logcheckbox']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('logid');
            delidlist.push(id);
        }
    });
    if (delidlist == null || delidlist.length == 0) {
        alert("请选择要删除的日志!");
        return;
    }
    $("#modaldel").attr("delidsstr", delidlist.join(","));
    $("#modaldel").modal('show');
}

function delsinglelogload(id) {
    $("#modaldel").attr("delidsstr", id);
    $("#modaldel").modal('show');
}

function dellogs(idliststr) {
    var data = new Data();
    data.addDataObj('loginid', $("#userid").val());
    data.addDataListStr('logids', idliststr);
    ajax('dellogs', data, function (result) {
        if (ifResultOK(result)) {
            loadlog();
        } else {
            alert(getResultDesc(result));
        }
        $("#modaldel").modal('hide');
    });
}