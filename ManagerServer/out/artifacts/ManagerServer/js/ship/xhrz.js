$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#xhrz_li").addClass("active");
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
    var starttime = $("#beginTime").val();
    var endtime = $("#endTime").val();
    var member = $('#listkey').val();
    $.ajax({
        url: 'xhrzdt',
        type: "post",
        dataType: "json",
        data: {
            "member": member,
            'starttime': starttime,
            'endtime': endtime,
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
                        "<td class='td'>" + isnull(item.member) + "</td>" +
                        "<td class='td'>" + isnull(item.tools) + "</td>" +
                        "<td class='td'>" + isnull(item.area) + "</td>" +
                        "<td class='td'>" + item.miles + "</td>" +
                        "<td class='td'>" + item.eventnum + "</td>" +
                        "<td class='td'>" + isnull(item.starttime) + "</td>" +
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

function GoShow(id, flag) {
    window.location.href = $('#basePath').val() + 'xhrz_detail?id=' + id ;
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