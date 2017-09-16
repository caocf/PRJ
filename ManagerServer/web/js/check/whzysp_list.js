$(document).ready(function () {
    $("#check_li").addClass("active");
    $("#whzysp_li").addClass("active");
    Search(1);
})

function Search(page) {
    page = parseInt(page);
    var shipname = $('#listkey').val();
    $.ajax({
        url: 'workport',
        type: "post",
        dataType: "json",
        data: {
            "shipname": shipname,
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
                    var app = isnull(item.status) == '' ? '' : item.status.status;
                    var tdspan = '';
                    if (app == '待审核') {
                        tdspan = "<td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + ")'>查看</span><span style='cursor: pointer;padding-left: 10px'  onclick='GoAppro(" + item.id + ")'>审核</span></td>";
                    } else {
                        tdspan = "<td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + ")'>查看</span></td>";
                    }
                    $('#kqtable').append
                    (
                        "<tr class='tr'>" +
                        "<td class='td'>" + isnull(item.ship) + "</td>" +
                        "<td class='td'>" + isnull(item.startport.portname) + "</td>" +
                        "<td class='td'>" + isnull(item.targetport.portname) + "</td>" +
                        "<td class='td'>" + isnull(item.wharfEN)  + "</td>" +
                        "<td class='td'>" + isnull(item.goodsname) + "</td>" +
                        "<td class='td'>" + isnull(item.rank.rankname) + "</td>" +
                        "<td class='td'>" + isnull(item.portime) + "</td>" +
                        "<td class='td'>" + app + "</td>" + tdspan +
                        "</tr>"
                    )
                });
            } else {
                TableIsNull();
            }
        }
    });
}

function GoShow(id) {
    window.location.href = $('#basePath').val() + 'whzysp_detal?id=' + id;
}

function GoAppro(id) {
    window.location.href = $('#basePath').val() + 'whzysp_check?id=' + id;
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