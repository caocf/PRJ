$(document).ready(
    function () {
        loadselhdao();
    });

function loadselhdao() {
    // 加载航道选择
    var xzqh = 1;
    xzqh = $("#deptxzqh").val();
    ajax('hangdao/queryallhangdao', {
        'loginid': $("#userid").val(),
        'sfgg': '1',
        'xzqh': xzqh
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            if (records) {
                var data = {};
                for (var i = 0; i < records.data.length; i++) {
                    var hdao = records.data[i];

                    var id = hdao.id;
                    var no = hdao.hdbh;
                    var name = hdao.hdmc;
                    data[id] = name;
                }
                $("#selxzqhdiv").empty();
                $("#selxzqhdiv").addmultiselect({
                    data: data,
                    onchange: function () {
                        loadbaobiao();
                    },
                    id: "multiselxzqhdiv"
                });
                loadbaobiao();
            }
        }
    });
}

function loadbaobiao() {
    var sel = $('#selfsw').val();
    var xzqh = 1;
    xzqh = $("#deptxzqh").val();
    var ids = $("#multiselxzqhdiv").attr("selitem");
    switch (parseInt(sel)) {
        case 1:
            $('#label').html('桥梁按桥梁用途');
            $('#baobiaoiframe').attr("src",
                "statistics/queryhdmcql?ids=" + ids + "&xzqh=" + xzqh);
            break;
        case 2:
            $('#label').html('航标按标志类别');
            $('#baobiaoiframe').attr("src",
                "statistics/queryhdmchb?ids=" + ids + "&xzqh=" + xzqh);
            break;
        case 3:
            $('#label').html('码头按码头类型');
            $('#baobiaoiframe').attr("src",
                "statistics/queryhdmcmt?ids=" + ids + "&xzqh=" + xzqh);
            break;
        case 4:
            $('#label').html('管线按管线种类');
            $('#baobiaoiframe').attr("src",
                "statistics/queryhdmcgx?ids=" + ids + "&xzqh=" + xzqh);
            break;
    }
}

function printbaobiao() {
    var sel = $('#selfsw').val();
    var xzqh = 1;
    xzqh = $("#deptxzqh").val();
    var ids = $("#multiselxzqhdiv").attr("selitem");
    switch (parseInt(sel)) {
        case 1:
            window.location.href = $("#basePath").val()
                + "/statistics/exporthdmcql?ids="
                + ids + "&xzqh=" + xzqh;
            break;
        case 2:
            window.location.href = $("#basePath").val()
                + "/statistics/exporthdmchb?ids="
                + ids + "&xzqh=" + xzqh;
            break;
        case 3:
            window.location.href = $("#basePath").val()
                + "/statistics/exporthdmcmt?ids="
                + ids + "&xzqh=" + xzqh;
            break;
        case 4:
            window.location.href = $("#basePath").val()
                + "/statistics/exporthdmcgx?ids="
                + ids + "&xzqh=" + xzqh;
            break;
    }
}