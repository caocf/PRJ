$(document).ready(function () {
    $("#check_li").addClass("active");
    $("#whzysp_li").addClass("active");
    init();
})

function init() {
    var id = $("#kqid").val();
    $.ajax({
        url: 'workportbyid',
        type: "post",
        dataType: "json",
        data: {
            "id": id
        },
        success: function (data) {
            var obj = data.obj;
            if (isnull(obj) != '') {
                $("#snumber").text(obj.number);
                $("#swharfEN").text(obj.wharfEN);
                $("#sship").text(obj.ship);
                var sp = isnull(obj.startport) == '' ? '' : obj.startport.portname;
                var ep = isnull(obj.targetport) == '' ? '' : obj.targetport.portname;
                $("#sstartport").text(sp);
                $("#stargetport").text(ep);
                $("#sgoodsname").text(obj.goodsname);
                $("#sranktype").text(isnull(obj.rank) == '' ? '' : obj.rank.rankname);
                $("#smount").text(obj.mount.concat(isnull(obj.unit) == '' ? '' : obj.unit.unitname));
                $("#sportime").text(obj.portime);
                $("#sendtime").text(obj.endtime);
                $("#sstatus").text(isnull(obj.status) == '' ? '' : obj.status.status);
                $("#schecker").text(obj.checker);
                $("#sreason").text(obj.reason);
            }
        }
    });
}
