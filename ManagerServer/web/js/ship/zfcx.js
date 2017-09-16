$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#zfcx_li").addClass("active");
    $("#clearbtn").bind("click", function () {
        $("#clearbtn").hide();
        $("#listkey").val('');
    })
    $("#listkey").bind('input propertychange', function () {
        if ($('#listkey').val() != '' && $('#listkey').val() != null) {
            $("#clearbtn").show();
        }
    });
    Search(1);
})

function Search(page) {
    page = parseInt(page);
    var shipname = $('#listkey').val();
    $.ajax({
        url: 'zfcxdt',
        type: "post",
        dataType: "json",
        data: {
            "shipname": shipname,
            "flag": "",
            "page": page
        },
        success: function (data) {
            $("#bootpagediv").show();
            $("#nulltablediv").hide();
            var obj = data.obj;
            $(".tr").remove();
            if (obj != null && obj.length > 0) {
                pagingmake("", 'Search', page, data.pages);
                $(obj).each(function (index, item) {
                    var typeen = item.typeEN;
                    var app = isnull(item.status);
                    var tdspan = '';
                    if (app == '待审核') {
                        tdspan = "<td class='td' style='color:#f89513 '>" + app + "</td><td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + "," + item.issimple + ")'>查看</span></td>";
                    } else {
                        var r = item.isllegal;
                        if (r == '不构成违章') {
                            tdspan = "<td class='td' style='color:#008000'>通过</td><td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + "," + item.issimple + ")'>查看</span></td>";
                        } else {
                            tdspan = "<td class='td' style='color:red'>驳回</td><td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + "," + item.issimple + ")'>查看</span></td>";
                        }
                    }
                    $('#dttable').append
                    (
                        "<tr class='tr'>" +
                        "<td class='td'>" + (index + 1) + "</td>" +
                        "<td class='td'>" + isnull(item.target) + "</td>" +
                        "<td class='td'>" + isnull(item.reason) + "</td>" +
                        "<td class='td'>" + isnull(item.firstman) + "," + isnull(item.secman) + "</td>" +
                        "<td class='td'>" + isnull(typeen.status) + "</td>" +
                        "<td class='td'>" + isnull(item.sumbdate) + "</td>" + tdspan +
                        "</tr>"
                    )
                });
            } else {
                TableIsNull();
            }
        }
    });
}

function GoShow(id, flag) {
    window.location.href = $('#basePath').val() + 'zfcx_detail?id=' + id + '&flag=' + flag;
}

function isnull(str) {
    var isnull = '';
    if (str == null || str == '' || str == 'null' || str == undefined) {
        return isnull;
    } else {
        return str;
    }
}

function TableIsNull() {
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}