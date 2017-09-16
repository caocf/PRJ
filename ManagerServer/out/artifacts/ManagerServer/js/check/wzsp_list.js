$(document).ready(function () {
    $("#check_li").addClass("active");
    $("#wzsp_li").addClass("active");
    Search(1);
})

function option(data, id, obj) {
    var tx = $(obj).text();
    $("#" + id).text(tx);
    $("#" + id).attr('title', data);
}

function Search(page) {
    page = parseInt(page);
    var status = $('#spanstatus').attr('title');
    var type = $('#spantype').attr('title');
    var tip = $('#listkey').val();
    $.ajax({
        url: 'evidencelist',
        type: "post",
        dataType: "json",
        data: {
            "status": status,
            "type": type,
            "tip": tip,
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
                    var app = isnull(item.status);
                    var tdspan = '';
                    if (app == '待审核') {
                        tdspan = "<td class='td' style='color:#f89513 '>" + app + "</td><td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + ")'>查看</span><span style='cursor: pointer;padding-left: 10px'  onclick='GoAppro(" + item.id + ")'>审核</span><span style='cursor: pointer;padding-left: 10px'  onclick='GoUpdate(" + item.id + ")'>补充</span><span style='cursor: pointer;padding-left: 10px'  onclick='ToAppro(" + item.id + ")'><select style='border: 0;color: black;background-color: transparent' onchange='ChangeLawType(this," + item.id + ")'><option style='border: none' value='0'>重新分类</option><option style='border: none' value='1'>海事</option><option style='border: none' value='2'>港政</option></select></span></td>";
                    } else {
                        var r = item.isllegal;
                        if (r == '构成违章') {
                            tdspan = "<td class='td' style='color:#008000'>通过</td><td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + ")'>查看</span></td>";
                        } else {
                            tdspan = "<td class='td' style='color:red'>驳回</td><td class='td'><span style='cursor: pointer;'  onclick='GoShow(" + item.id + ")'>查看</span></td>";
                        }
                    }
                    $('#kqtable').append
                    (
                        "<tr class='tr'>" +
                        "<td class='td'>" + (index + 1) + "</td>" +
                        "<td class='td'>" + isnull(item.target) + "</td>" +
                        "<td class='td'>" + isnull(item.reason) + "</td>" +
                        "<td class='td'>" + isnull(item.firstman) + "," + isnull(item.secman) + "</td>" +
                        "<td class='td'>" + isnull(typeen.status) + "</td>" +
                        "<td class='td'>" + isnull(item.sumbdate) + "</td>" + tdspan +
                        "</tr>"
                    )
                });
            } else {
                TableIsNull();
            }
        }
    });
}

function ChangeLawType(obj, id) {
    var selval = $(obj).val();
    if (selval > 0) {
        $.ajax({
            url: 'ChangeLawType',
            type: "post",
            dataType: "json",
            data: {
                "id": id,
                "typeid": selval
            },
            success: function (data) {
                Search(1);
            }
        });
    }
}

function GoShow(id) {
    window.location.href = $('#basePath').val() + 'wzsp_detal?id=' + id;
}

function GoUpdate(id) {
    window.location.href = $('#basePath').val() + 'wzsp_update?id=' + id;
}

function GoAppro(id) {
    window.location.href = $('#basePath').val() + 'wzsp_check?id=' + id;
}

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