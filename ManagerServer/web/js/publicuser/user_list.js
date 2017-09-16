var listtype = 1;
var usertype = -1;
$(document).ready(function () {
    $("#user_li").addClass("active");
    $("#userlist_li").addClass("active");
    $('.tablabel').click(function () {
        $('.tablabel').removeClass('activelabel');
        $(this).addClass('activelabel');
        if ($(this).text() == '企业用户') {
            listtype = 2;
        } else {
            listtype = 1;
        }
        showTabledata('UsersByKey', 1);
    })
    showTabledata('UsersByKey',1);
})

function showTabledata(actionname, page) {

    $.ajax({
        url: actionname,
        type: "post",
        dataType: "json",
        data: {
            'page': page,
            'key': $("#listkey").val(),
            'status': usertype,
            'type': listtype
        },
        success: function (data) {
            //console.log(JSON.stringify(data));
            pagingmake(actionname, 'showTabledata', page, data.pages);
            $("#kqtable").empty();
                $("#kqtable").append(
                    "<tr style='background-color: rgb(240,245,248);'>" +
                    "<th style='width:50px;'>编号</th>" +
                    "<th>用户名</th>" +
                    "<th>手机号</th>" +
                    "<th>用户类型</th>" +
                    "<th>更新时间</th>" +
                    "<th>状态</th>" +
                    "<th>操作</th>" +
                    "</tr>"
                );
                $(data.list).each(function (index, item) {
                    var statuscolor;
                    var switchstr;
                    var isallow;
                    if (item[1].status == 1) {
                        statuscolor = '#008000';
                        switchstr = '正常';
                        isallow="<span class='clickword' onclick=\"setuserstatus(0,'"+item[1].userid+"')\">禁用</span>";
                    } else {
                        statuscolor = 'red';
                        switchstr = '禁用';
                        isallow="<span class='clickword' onclick=\"setuserstatus(1,'"+item[1].userid+"')\">启用</span>";
                    }
                    $("#kqtable").append(
                        "<tr>" +
                        "<td>" + ((index + 1) + parseInt(page - 1) * 10) + "</td>" +
                        "<td>" + item[1].username + "</td>" +
                        "<td>" + item[1].tel + "</td>" +
                        "<td>" + item[1].usertype.typename + "</td>" +
                        "<td>" + item[1].registertime + "</td>" +
                        "<td><span style='color:" + statuscolor + "'>" + switchstr + "</span></td>" +
                        "<td>" +
                        "<span class='clickword' onclick=\"window.location.href='" + $("#basePath").val() + "user_check?type="+listtype+"&idoftype="+item[1].userid+"'\">查看</span>&nbsp;&nbsp;" +
                        "<span class='clickword'>删除</span>&nbsp;&nbsp;" +
                        isallow+
                        "</td>" +
                        "</tr>"
                    );
                })

        }
    })
}

//设置用户状态
function setuserstatus(status,id){
    $.ajax({
        url: 'setUserStatusByID',
        type: "post",
        dataType: "json",
        data: {
            'status': status,
            'id': id
        },
        success: function () {
            alert('修改成功');
            showTabledata('UsersByKey',1);
        }
    })
}
/**
 * 列表类型切换
 * @param type 类型
 */
function setlbtype(type) {
    usertype = type;
    showTabledata('UsersByKey',1);
    if (type == 0) {
        $("#typespan").text('禁用');
    } else if (type == 1) {
        $("#typespan").text('启用');
    } else {
        $("#typespan").text('全部状态');
    }
}