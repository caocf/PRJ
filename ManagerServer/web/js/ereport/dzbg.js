$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#dzbg_li").addClass("active");
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
        url: 'dzbgdt',
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
                    $('#dttable').append
                    (
                        "<tr class='tr'>" +
                        "<td class='td'>" + (index + 1) + "</td>" +
                        "<td class='td'>" + isnull(item.shipname) + "</td>" +
                        "<td class='td'>" + isnull(item.reportclassEN.name) + "</td>" +
                        "<td class='td'>" + isnull(item.reporttypeEN.reporttype) + "</td>" +
                        "<td class='td'>" + isnull(item.porttime) + "</td>" +
                        "<td class='td'>" + isnull(item.commitdate) + "</td>" +
                        "<td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + ")'>查看</span></td>" +
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
    window.location.href = $('#basePath').val() + 'dzbg_detail?id=' + id;
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