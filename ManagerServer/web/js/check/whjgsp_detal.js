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
                $("#sstatus").text(isnull(obj.status) == '' ? '' : obj.status.status);
                $("#schecker").text(obj.checker);
                $("#sreason").text(obj.reason);
            }
        }
    });
}
