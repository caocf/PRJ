$(document).ready(function () {
    $("#check_li").addClass("active");
    $("#whjgsp_li").addClass("active");
    Search(1);
})

function Search(page) {
    page = parseInt(page);
    var shipname = $('#listkey').val();
    $.ajax({
        url: 'dangrousport',
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
                        tdspan = "<td class='td'><span style='cursor: pointer;color: #00a7d0'  onclick='GoShow(" + item.id + ")'>查看</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style='cursor: pointer;color: #00a7d0;padding-left: 10px'  onclick='GoAppro(" + item.id + ")'>审核</span></td>";
                    } else {
                        tdspan = "<td class='td'><span style='cursor: pointer;color: #00a7d0'  onclick='GoShow(" + item.id + ")'>查看</span></td>";
                    }
                    $('#kqtable').append
                    (
                        "<tr class='tr'>" +
                        "<td class='td'>" + isnull(item.shipname) + "</td>" +
                        "<td class='td'>" + isnull(item.startportEN.portname) + "</td>" +
                        "<td class='td'>" + isnull(item.endportEN.portname) + "</td>" +
                        "<td class='td'>" + isnull(item.goods) + "</td>" +
                        "<td class='td'>" + isnull(item.dangerrankEN.rankname) + "</td>" +
                        "<td class='td'>" + isnull(item.committime) + "</td>" +
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
    window.location.href = $('#basePath').val() + 'whjgsp_detal?id=' + id;
}

function GoAppro(id) {
    window.location.href = $('#basePath').val() + 'whjgsp_check?id=' + id;
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