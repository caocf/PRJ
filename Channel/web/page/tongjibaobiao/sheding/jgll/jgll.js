$(document).ready(function () {
    loadgcd();
});

function loadbaobiao(n) {
    var gcdbh = $("#selgcd").val();
    var starttime = "";
    var endtime = "";
    starttime = $("#selstarttime").attr('realvalue');
    endtime = $("#selendtime").attr('realvalue');
    $('#baobiaoiframe').attr("src", "queryjgll?gcdbh=" + gcdbh + "&flag=" + n + "&starttime=" + starttime + "&endtime=" + endtime);
}

function exportcblltable() {
    var n = $("#flagipt").val();
    var gcdbh = $("#selgcd").val();
    var starttime = "";
    var endtime = "";
    starttime = $("#selstarttime").attr('realvalue');
    endtime = $("#selendtime").attr('realvalue');
    window.location.href = $("#basePath").val() + "exportjgll?gcdbh=" + gcdbh + "&flag=" + n + "&starttime=" + starttime + "&endtime=" + endtime;
}

function loadcus() {
    $("#seldaydiv").show();
}

function searchCbll(n) {
    if (n != 10) {
        $("#seldaydiv").hide();
    }
    $("#flagipt").val(n);
    loadbaobiao(n);
}

function loadgcd() {
    $.ajax({
        url: 'queryjggcd',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            var res = getResultRecords(data).data;
            var sel = $('<select id="selgcd" class="form-control"><option value="-1">请选择观测点</option></select>');
            for (var i in res) {
                var op = res[i];
                var gcd = $("<option value='" + op.gcdid + "'>" + op.gcdmc + "</option>");
                sel.append(gcd);
            }
            $("#selgcddiv").append(sel);
        }
    });
}
