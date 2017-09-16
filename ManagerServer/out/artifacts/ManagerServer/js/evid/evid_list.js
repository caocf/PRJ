$(document).ready(function () {
    $(".zjbqli").css({'background-color': '#0186ed', 'color': 'white'});
    search(1);
})

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

function option(data, id, obj) {
    var tx = $(obj).text();
    $("#" + id).text(tx);
    $("#" + id).attr('title', data);
}

function search(page) {
    page = parseInt(page);
    var typeen = $('#spantype').attr('title');
    var starttime = $("#beginTime").val();
    var endtime = $("#endTime").val();
    var shipname = $('#listkey').val();
    $.ajax({
        url: 'eviddt',
        type: "post",
        dataType: "json",
        data: {
            "shipname": shipname,
            "typeen": typeen,
            "starttime": starttime,
            "endtime": endtime,
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
                    $('#dttable').append
                    (
                        "<tr class='tr'>" +
                        "<td class='td'>" + (index + 1) + "</td>" +
                        "<td class='td'>" + isnull(item.target) + "</td>" +
                        "<td class='td'>" + isnull(item.reason) + "</td>" +
                        "<td class='td'>" + isnull(item.firstman) + "," + isnull(item.secman) + "</td>" +
                        "<td class='td'>" + isnull(typeen.status) + "</td>" +
                        "<td class='td'>" + isnull(item.sumbdate) + "</td>" +
                        "<td class='td'><span style='cursor:pointer;' onclick='GoShow(\"" + item.id + "\")'>详情</span></td>" +
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
    window.location.href = $('#basePath').val() + 'evid_detail?id=' + id;
}