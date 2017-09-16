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
                $("#szh").text(obj.zh);
                $("#sxm").text(obj.xm);
                $("#szw").text("");
                $("#sdw").text(mapinfo.zzjg==null?'':mapinfo.zzjg.zzjgmc);
                $("#sbm").text(obj.bmmc);
                $("#sjs").text(obj.userRoleEN.role);
                $("#syx").text(obj.yx);
                $("#sbgdh").text(obj.bgdh);
                $("#ssjhm").text(obj.sjhm);
                $("#sxnwh").text(obj.xnwh);
                $("#szt").text(obj.zt == '1' ? '启用' : '禁用');
            }
        }
    });
}
