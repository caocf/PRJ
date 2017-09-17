/**
 * Created by 25019 on 2015/10/21.
 */
$(document).ready(function () {
    $("#tabmap").click(
        function () {
            window.location.href = $("#basePath").val()
                + "page/hdaoinfomap/hdaoinfo.jsp";
        });
    resize();
    $(window).resize(function (event) {
        resize();
    });

});

function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientHeight = window.innerHeight;
    $("#divleft").css('height', (clientHeight - 53) + 'px');
    $("#lefthdaohduantree").css('height', ($("#divleft").height()) + 'px');
    $("#leftfswtree").css('height', ($("#divleft").height()) + 'px');
    $("#divright").css('height', ($("#divleft").height() - 20) + 'px');
}

function searchApp() {
    $("tr[name='temptr']").remove();
    var xzqh = $("#selxzqh").attr("selitem");
    var data = {
        'xzqh': xzqh
    };
    $.ajax({
        url: "appurtenance/zhcxapp",
        data: data,
        type: "post",
        dataType: "json",
        success: function (result) {
            if (ifResultOK(result)) {
                var data = getResultRecords(result).data;
                var dept = getResultMap(result).dept;
                $("#bbtitle").text(dept + "附属物列表");
                var len = data.length;
                if (len > 0) {
                    $.each(data, function (n, hd) {
                        var tr = $("<tr name='temptr'><td>" + hd[1] + "</td><td><a onclick='gotoappinfolist(9," + hd[0] + ")'> " + setDeciNull(hd[2]) + "</a></td><td><a onclick='gotoappinfolist(10," + hd[0] + ")'>" + setDeciNull(hd[3]) + "</a></td><td><a onclick='gotoappinfolist(12," + hd[0] + ")'>" + setDeciNull(hd[4]) + "</a></td><td><a onclick='gotoappinfolist(15," + hd[0] + ")'>" + setDeciNull(hd[5]) + "</a></td><td><a onclick='gotoappinfolist(23," + hd[0] + ")'>" + setDeciNull(hd[6]) + "</a></td><td><a onclick='gotoappinfolist(29," + hd[0] + ")'>" + setDeciNull(hd[7]) + "</a></td><td>" + setDeciNull(hd[8]) + "</td></tr>");
                        $("#apptable").append(tr);
                    });
                } else {
                    var tr = $("<tr name='temptr'><td colspan='7'style='text-align: center;vertical-align: middle'>暂无数据</td></tr>");
                    $("#apptable").append(tr);
                }
            }
        }
    });
}

function gotohduan() {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduanlist.jsp';
}

function gotoappinfolist(fswlx, xzqh) {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/appinfolist.jsp?fswlx=' + fswlx + '&xzqh=' + xzqh;
}

function exporttable() {
    var xzqh = $("#selxzqh").attr("selitem");
    window.location.href = $("#basePath").val() + "appurtenance/exportapp?xzqh=" + xzqh;
}