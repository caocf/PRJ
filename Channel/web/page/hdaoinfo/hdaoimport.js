$(document).ready(function () {
    resize();
    $(window).resize(function (event) {
        resize();
    });
    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabhangdao').bind(
        'click',
        function () {
            window.location.href = $("#basePath").val()
                + "page/hdaoinfo/hdaoinfomanager.jsp";
        });
    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabmapinfo').bind(
        'click',
        function () {
            window.location.href = $("#basePath").val()
                + "page/hdaoinfo/mapinfomanager.jsp";
        });
    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabimport').bind(
        'click',
        function () {
            window.location.href = $("#basePath").val()
                + "page/hdaoinfo/hdaoimport.jsp";
        });
    loadtablelist();
});

function resize() {
    var clientheight = window.innerHeight;
    $("#divleftrow").css('height', (clientheight - 52 - 80) + 'px');
    $("#divrightrow").css('height', (clientheight - 53 - 80 - 50) + 'px');
}

function loadtablelist() {
    $("#divtablelist").etree({
        id: -1,
        childrendatafn: loadtablelistfn
    });
}

function loadtablelistfn(pnode, fncallback) {
    var nodes = new Array();
    ajax('appurtenancetype/queryappbytype', {
        'type': 2
    }, function (result) {
        if (ifResultOK(result)) {
            var records = getResultRecords(result);
            if (records != null) {
                var hdaonode = {
                    id: 1,
                    name: '航道',
                    nametype: '7',
                    clicked: true,
                    clickfn: tableclickfn
                };
                var hduannode = {
                    id: 2,
                    name: '航段',
                    nametype: '8',
                    clicked: false,
                    clickfn: tableclickfn
                };
                nodes.push(hdaonode);
                nodes.push(hduannode);
                for (var i = 0; i < records.data.length; i++) {
                    var obj = records.data[i];
                    var node = {
                        id: i + 3,
                        name: obj.name,
                        nametype: obj.id,
                        clicked: false,
                        clickfn: tableclickfn
                    };
                    nodes.push(node);
                }
            }
        }
        fncallback(nodes);
    });
}

function tableclickfn(node, event) {
    $("#fswlx").val(node.nametype);
    $("div[name='tempfjdiv']").remove();
    $("#divimportdata").hide();
}
function downloadFile() {
    var fswlx = $("#fswlx").val();
    if (fswlx == 7 || fswlx == 8 || fswlx == 9 || fswlx == 10 || fswlx == 12 || fswlx == 15 || fswlx == 19 || fswlx == 30) {
        window.location.href = $("#basePath").val() + "appurtenance/downloadtemplate?fswlx=" + fswlx;
    } else {
        alert("模板未找到");
    }
}
function uploadFile() {
    $("div[name='tempfjdiv']").remove();
    var rantoken = rand(1, 99999999);
    var row = $('<div name="tempfjdiv" class="row hide" id="row' + rantoken + '"></div>');
    var col = $('<div class="col-xs-12" style="background:transparent;"></div>');
    row.append(col);
    col.append('<i class="icon-paper-clip"></i>&nbsp;'
        + '<label style="font-weight:normal;" id="label'
        + rantoken
        + '"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-primary" onclick="importData()">导入</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        + '<input id="file'
        + rantoken
        + '" type="file" class="hide" autoajax autoajax-name="filelist" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="selectfile(this,\'' + rantoken + '\')">');

    $("#divimportdata").append(row);
    $("#file" + rantoken).click();

    $("#divimportdata").show();
}
function selectfile(from, rantoken) {
    var file = $('#file' + rantoken).val();
    var size = '';
    try {
        var size = (from.files[0].size / 1024).toFixed(2);
    } catch (e) {
        size = '';
    }
    if (file != null && file != "") {
        var extindex = file.lastIndexOf(".");
        var ext = file.substring(extindex + 1);
        if (ext != 'xls' && ext != 'xlsx') {
            alert("请参考模板,上传Excel文件");
            return false;
        } else {
            var pos = file.lastIndexOf('\\');
            var filename = file.substring(pos + 1);
            $("#row" + rantoken).removeClass('hide');

            if (size == null || size == '')
                $("#label" + rantoken).html(filename);
            else
                $("#label" + rantoken).html(filename + "(" + size + "kb)");
        }
    }
}

function importData() {
    var data = {
        fswlx: $("#fswlx").val()
    };
    $("#divform").validateForm(function () {
        $("#divform").autoajaxfileuploading('appurtenance/batchimport', data, function (result, data) {
                if (ifResultOK(result)) {
                    var obj = getResultObj(result);
                    var len = obj.length;
                    var errormsg = "";
                    if (len > 0) {
                        for (var i = 0; i < len; i++) {
                            errormsg += obj[i];
                            if (i + 1 != len) {
                                errormsg += ',';
                            }
                        }
                        alert("导入成功,部分未导入失败,出错行数：" + errormsg);
                    } else {
                        alert("导入成功");
                    }
                } else {
                    alert(getResultDesc(result));
                }
            }
        );
    });
}
