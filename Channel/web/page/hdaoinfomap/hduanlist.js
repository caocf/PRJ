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

    initHdaolist();
});

function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientHeight = window.innerHeight;
    $("#divleft").css('height', (clientHeight - 53) + 'px');
    $("#lefthdaohduantree").css('height', ($("#divleft").height()) + 'px');
    $("#leftfswtree").css('height', ($("#divleft").height()) + 'px');
    $("#divright").css('height', ($("#divleft").height() - 20) + 'px');
}

function initHdaolist() {
    $("#selhdaolist").children().remove();
    ajax('hangdao/queryallhangdao', {'xzqh': 1, 'sfgg': -1}, function (data) {
        $("#selhdaolist").append(
            '<option value="-1">全部航道</option>');
        if (ifResultOK(data)) {
            var res = getResultRecords(data).data;
            if (res != null) {
                for (var i = 0; i < res.length; i++) {
                    var hdao = res[i];
                    $("#selhdaolist").append(
                        '<option value="' + hdao.id + '">'
                        + hdao.hdmc + '</option>');
                }
            }
        }
    });
}

function searchHduan() {
    $("tr[name='temptr']").remove();
    var xzqh = $("#selxzqh").attr("selitem");
    var sshdid = $("#selhdaolist").val();
    if (sshdid == null) {
        sshdid = -1;
    }
    var data = {
        'xzqh': xzqh,
        'sshdid': sshdid,
        'hddj': -1,
        'content': ""
    };
    $.ajax({
        url: "hangduan/zhcxhduan",
        data: data,
        type: "post",
        dataType: "json",
        success: function (result) {
            if (ifResultOK(result)) {
                var data = getResultRecords(result).data;
                var dept = getResultMap(result).dept;
                $("#bbtitle").text(dept + "航段列表");
                var len = data.length;
                if (len > 0) {
                    $.each(data, function (n, hd) {
                        if (n != len - 1) {
                            var tr = $("<tr name='temptr'><td>" + hd[1] + "</td><td>" + hd[2] + "</td><td>" + hd[3] + "</td><td>" + hd[4] + "</td><td>" + hd[5] + "</td><td>" + setDeciNull(hd[6]) + "</td><td><a onclick='gotoappinfolist(9," +xzqh+","+ hd[0] + ")'>" + setDeciNull(hd[7]) + "</a></td><td><a onclick='gotoappinfolist(10," +xzqh+","+ hd[0] + ")'>" + setDeciNull(hd[8]) + "</a></td><td><a onclick='gotoappinfolist(12," +xzqh+","+ hd[0] + ")'>" + setDeciNull(hd[9]) + "</a></td><td><a onclick='gotoappinfolist(15," +xzqh+","+ hd[0] + ")'>" + setDeciNull(hd[10]) + "</a></td><td><a onclick='gotoappinfolist(23," +xzqh+","+ hd[0] + ")'>" + setDeciNull(hd[11]) + "</a></td><td><a onclick='gotoappinfolist(29," +xzqh+","+ hd[0] + ")'>" + setDeciNull(hd[12]) + "</a></td></tr>");
                        } else {
                            var tr = $("<tr name='temptr'><td>合计</td><td></td><td></td><td></td><td></td><td></td><td><a onclick='gotoappinfolist(9," +xzqh+",-1)'>" + setDeciNull(hd[0]) + "</a></td><td><a onclick='gotoappinfolist(10," +xzqh+",-1)'>" + setDeciNull(hd[1]) + "</a></td><td><a onclick='gotoappinfolist(12," +xzqh+",-1)'>" + setDeciNull(hd[2]) + "</a></td><td><a onclick='gotoappinfolist(15," +xzqh+",-1)'>" + setDeciNull(hd[3]) + "</a></td><td><a onclick='gotoappinfolist(23," +xzqh+",-1)'>" + setDeciNull(hd[4]) + "</a></td><td><a onclick='gotoappinfolist(29," +xzqh+",-1)'>" + setDeciNull(hd[5]) + "</a></td></tr>");
                        }
                        $("#hduantable").append(tr);
                    });
                } else {
                    var tr = $("<tr name='temptr'><td colspan='12'style='text-align: center;vertical-align: middle'>暂无数据</td></tr>");
                    $("#hduantable").append(tr);
                }
            }
        }
    });
}

function gotohduan() {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduanlist.jsp';
}

function gotoappinfolist(fswlx, xzqh, sshdid) {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/appinfolist.jsp?fswlx=' + fswlx + '&xzqh=' + xzqh + '&sshdid=' + sshdid;
}

function gotohduaninfolist(xzqh, sshdid) {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduaninfolist.jsp?xzqh=' + xzqh + '&sshdid=' + sshdid;
}

function exporttable() {
    var xzqh = $("#selxzqh").attr("selitem");
    var sshdid = $("#selhdaolist").val();
    window.location.href = $("#basePath").val() + "hangduan/exporthduan?xzqh=" + xzqh + "&sshdid=" + sshdid;
}