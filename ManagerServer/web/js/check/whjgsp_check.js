$(document).ready(function () {
    $("#check_li").addClass("active");
    $("#whjgsp_li").addClass("active");
    init();
})

function init() {
    var id = $("#kqid").val();
    $.ajax({
        url: 'dangrousportbyid',
        type: "post",
        dataType: "json",
        data: {
            "id": id
        },
        success: function (data) {
            var obj = data.obj;
            if (isnull(obj) != '') {
                $("#snumber").text(obj.number);
                $("#sshipname").text(obj.shipname);
                var sp = isnull(obj.startportEN) == '' ? '' : obj.startportEN.portname;
                var ep = isnull(obj.endportEN) == '' ? '' : obj.endportEN.portname;
                $("#sport").text(sp.concat("-", ep));
                $("#sberthtime").text(obj.berthtime);
                $("#sgoods").text(obj.goods);
                $("#srank").text(isnull(obj.dangerrankEN) == '' ? '' : obj.dangerrankEN.rankname);
                $("#stons").text(obj.tons.concat(isnull(obj.goodsunitEN) == '' ? '' : obj.goodsunitEN.unitname));
                var sstatus = isnull(obj.status) == '' ? '' : obj.status.status;
                $("#sstatus").text(sstatus);
                $("#schecker").text(obj.checker);
                $("#sreason").text(obj.reason);
                if (sstatus == '待审核') {
                    $("#approdiv").show();
                    $("#approbtndiv").show();
                }
            }
        }
    });
}

function appro() {
    var id = $("#kqid").val();
    var checker = $("#username").val();
    var statusid = $("input[name='radiobutton']:checked").val();
    var reason = $("#shenheword").val();
    $.ajax({
        url: 'approvaldangrousport',
        type: "post",
        dataType: "json",
        data: {
            "id": id,
            "statusid": statusid,
            "reason": reason,
            "checker": checker
        },
        success: function (data) {
            window.location.reload();
        }
    });
}