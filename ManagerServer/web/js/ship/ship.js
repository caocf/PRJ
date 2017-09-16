$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#cbcx_li").addClass("active");
});

function hintli(obj) {
    var v = $(obj).text();
    if (v != '暂无相关船舶信息') {
        $("#iptshipname").val(v);
    }
    $("#search-sort-list").hide();
}

function showhintname() {
    $("#search-sort-list").empty();
    var tip = $("#iptshipname").val();
    $.ajax({
        url: 'ShipNamesByTip',
        type: 'post',
        dataType: 'json',
        data: {
            'tip': tip
        },
        success: function (data) {
            var arr = data.obj;
            if (arr != null && arr.length > 0) {
                for (var i in arr) {
                    var shipname = arr[i];
                    $("#search-sort-list").append("<li onclick='hintli(this)' value='" + shipname + "'>" + shipname + "</li>")
                }
            } else {
                $("#search-sort-list").append("<li onclick='hintli(this)' value='0'>暂无相关船舶信息</li>")
            }
            $("#search-sort-list").show();
        }
    });
}

function hidehintname() {
    $("#search-sort-list").hide();
}

function searchgoto() {
    var shipname = $("#iptshipname").val();
    if (shipname != null && shipname != undefined && shipname != "") {
        var basePath = $("#basePath").val();
        window.location.href = basePath + "shipinfo?shipname=" + shipname;
    } else {
        alert("请输入船名");
        return false;
    }
}

