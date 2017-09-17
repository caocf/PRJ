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


function searchHdao() {
    $("tr[name='temptr']").remove();
    var xzqh = $("#selxzqh").attr("selitem");
    $.ajax({
        url: "hangdao/zhcxhdao",
        data: {
            'xzqh': xzqh
        },
        type: "post",
        dataType: "json",
        success: function (result) {
            if (ifResultOK(result)) {
                var data = getResultRecords(result).data;
                var dept = getResultMap(result).dept;
                $("#bbtitle").text(dept + "航道列表");
                $.each(data, function (n, hd) {
                    var tr = $("<tr name='temptr'><td colspan='2'>" + hd.name + "</td><td><a onclick='gotohdaoinfolist(" + hd.xzqh + ",1)'> " + setDeciNull(hd.ggnum) + "</a></td><td>" + setDeciNull(hd.gglc) + "</td><td><a onclick='gotohdaoinfolist(" + hd.xzqh + ",0)'>" + setDeciNull(hd.zxnum) + "</a></td><td>" + setDeciNull(hd.zxlc) + "</td><td><a onclick='gotohdaoinfolist(" + hd.xzqh + ",-1)'>" + setDeciNull(hd.totalnum) + "</a></td><td>" + setDeciNull(hd.totallc) + "</td></tr>");
                    $("#hdaotable").append(tr);
                });
            }
        }
    });
}

function gotohduan() {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduanlist.jsp';
}

function gotohdaoinfolist(xzqh, sfgg) {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hdaoinfolist.jsp?xzqh=' + xzqh + '&sfgg=' + sfgg;
}

function exporttable() {
    var xzqh = $("#selxzqh").attr("selitem");
    window.location.href = $("#basePath").val() + "hangdao/exporthdao?xzqh=" + xzqh;
}