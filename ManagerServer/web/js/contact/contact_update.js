$(document).ready(function () {
    $(".txlli").css({'background-color': '#0186ed', 'color': 'white'});
    init();
})

function init() {
    var id = $("#kqid").val();
    $.ajax({
        url: 'queryuserbyid',
        type: "post",
        dataType: "json",
        data: {
            "id": id
        },
        success: function (data) {
            var obj = data.obj;
            var mapinfo = data.map;
            if (isnull(obj) != '') {
                $("#userid").val(obj.id);
                $("#szh").text(obj.zh);
                $("#sxm").text(obj.xm);
                $("#szw").text(obj.zw);
                $("#sdw").text(mapinfo.zzjg == null ? '' : mapinfo.zzjg.zzjgmc);
                $("#sbm").text(obj.bmmc);
                $("#sjs").text(obj.js);
                $("#syx").val(obj.yx);
                $("#sbgdh").val(obj.bgdh);
                $("#ssjhm").val(obj.sjhm);
                $("#sxnwh").val(obj.xnwh);
                $("#szt").text(obj.zt == '1' ? '启用' : '禁用');
            }
        }
    });
}


function updateuser() {
    var id = $("#userid").val();
    var yx = $("#syx").val();
    var bgdh = $("#sbgdh").val();
    var sjhm = $("#ssjhm").val();
    var xnwh = $("#sxnwh").val();
    $.ajax({
        url: 'updateuserbyid',
        type: "post",
        dataType: "json",
        data: {
            "id": id,
            "yx": yx,
            "bgdh": bgdh,
            "sjhm": sjhm,
            "xnwh": xnwh
        },
        success: function (data) {
            window.location.reload();
        }
    });
}
