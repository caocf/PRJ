$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#dzbg_li").addClass("active");
    init();
})

function init() {
    var id = $("#pkid").val();
    $.ajax({
        url: 'ereportbyid',
        type: "post",
        dataType: "json",
        data: {
            "id": id
        },
        success: function (data) {
            var obj = data.obj;
            if (isnull(obj) != '') {
                $("#sshipname").text(obj.shipname);
                $("#sclassname").text(isnull(obj.reportclassEN) == '' ? '' : obj.reportclassEN.name);
                var sp = isnull(obj.startport) == '' ? '' : obj.startport.portname;
                var ep = isnull(obj.endport) == '' ? '' : obj.endport.portname;
                $("#sregion").text(isnull(obj.portregionEN) == '' ? '' : obj.portregionEN.portregion);
                $("#sstartport").text(sp);
                $("#sendport").text(ep);
                $("#sporttype").text(isnull(obj.reporttypeEN) == '' ? '' : obj.reporttypeEN.reporttype);
                $("#sporttime").text(isnull(obj.porttime));
                $("#sgoodstype").text(isnull(obj.goodstypeEN) == '' ? '' : obj.goodstypeEN.typname);
                $("#snum").text(obj.goodscount.concat(isnull(obj.goodsunitEN) == '' ? '' : obj.goodsunitEN.unitname));
                $("#slastfueltime").text(obj.lastfueltime);
                $("#slastfuelcount").text(obj.lastfuelcount);
                $("#scommitdate").text(obj.commitdate);
            }
        }
    });
}
