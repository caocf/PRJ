$(document).ready(function () {
    loadgcd();
});

function loadbaobiao(n) {
    var gcdbh = $("#selgcd").val();
    var starttime = "";
    var endtime = "";
    starttime = $("#selstarttime").attr('realvalue');
    endtime = $("#selendtime").attr('realvalue');
    $('#baobiaoiframe').attr("src", "querycbll?gcdbh=" + gcdbh + "&flag=" + n + "&starttime=" + starttime + "&endtime=" + endtime);
}

function exportcblltable() {
    var n = $("#flagipt").val();
    var gcdbh = $("#selgcd").val();
    var starttime = "";
    var endtime = "";
    starttime = $("#selstarttime").attr('realvalue');
    endtime = $("#selendtime").attr('realvalue');
    window.location.href = $("#basePath").val() + "exportcbll?gcdbh=" + gcdbh + "&flag=" + n + "&starttime=" + starttime + "&endtime=" + endtime;
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
        url: 'querycbgcd',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            var res = getResultObj(data);
            var sel = $('<select id="selgcd" class="form-control"></select>');
            var gcd;
            for (var i in res) {
                var op = res[i];
                if (i == 0) {
                    gcd = $("<option selected value='" + op.id + "'>" + op.tag+"("+op.name+")" + "</option>");
                } else {
                    gcd = $("<option value='" + op.id + "'>" + op.tag+"("+op.name+")" + "</option>");
                }
                sel.append(gcd);
            }
            $("#selgcddiv").append(sel);
        }
    });
}
