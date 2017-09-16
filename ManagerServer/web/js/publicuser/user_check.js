$(document).ready(function () {
    $("#user_li").addClass("active");
    $("#userlist_li").addClass("active");
    getuserdata();
})
//调取用户详情
function getuserdata() {
    $.ajax({
        url: 'UserInfoByID',
        type: "post",
        dataType: "json",
        data: {
            'id': $("#kqid").val()
        },
        success: function (data) {
            console.log(JSON.stringify(data));
            $("#yhm").text(data.username);
            $("#yhlx").text(data.usertype.typename);
            $("#szcs").text(data.region.regionname);
            $("#sjh").html(data.tel + "&nbsp;&nbsp;&nbsp;&nbsp;<span class='clickword'>修改手机号</span>");
            var yhztstr = "<span style='color:red'>禁用</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='clickword'>启用</span>"
            if (data.status == 1) {
                yhztstr = "<span style='color:#008000'>正常</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='clickword'>禁用</span>"
            }
            $("#yhzt").html(yhztstr);
            password = data.password;
            username = data.username;
            $("#repassbtn").attr('onclick', "repassword()");
            if ($("#type").val() == 1) {
                getshipsdata();
            } else {
                getcompanydata();
            }
        }
    })
}

//按用户名获取船舶列表
function getshipsdata() {
    $.ajax({
        url: 'myshiplist',
        type: "post",
        dataType: "json",
        data: {
            'Username': username
        },
        success: function (data) {
            //console.log(JSON.stringify(data));
            $('#kqtable').empty();
            $('#kqtable').append(
                "<tr style='background-color: rgb(240,245,248);'>" +
                "<th style='width:50px;'>编号</th>" +
                "<th>船名号</th>" +
                "<th>船舶登记号</th>" +
                "<th>附件</th>" +
                "<th>创建时间</th>" +
                "<th>状态</th>" +
               // "<th>操作</th>" +
                "</tr>"
            );
            $(data.data).each(function (index, item) {
                var statuscolor;
                switch (parseInt(item.shipstatus.id)) {
                    case 1:
                        statuscolor = '#333';
                        break;
                    case 2:
                        statuscolor = '#008000';
                        break;
                    case 3:
                        statuscolor = 'red';
                        break;
                }
                var fjtd;
                if (item.shipcertENs == null || item.shipcertENs == '') {
                    fjtd = "<td>--</td>";
                } else {
                   // fjtd = "<td>船舶国籍证书&nbsp;<span class='clickword' onclick=\"downimg('" + item.shipcertENs[0].gppc + "')\">下载</span>" +
                        //"&nbsp;&nbsp;&nbsp;AIS证书&nbsp;<span class='clickword' onclick=\"downimg('" + item.shipcertENs[0].con + "')\">下载</span></td>"
                }
                $('#kqtable').append(
                    "<tr>" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + item.shipname + "</td>" +
                    "<td>" + item.regnumber + "</td>" +
                    fjtd +
                    "<td>" + item.binddate + "</td>" +
                    "<td><span style='color:" + statuscolor + "'>" + item.shipstatus.status + "</span></td>" +
                    /*"<td>" +
                    "<span class='clickword' onclick=\"\">解绑</span>&nbsp;&nbsp;&nbsp;" +
                    "<span class='clickword' onclick=\"\">禁用</span>" +
                    "</td>" +*/
                    "</tr>"
                );
            })
        }
    })
}

//获取企业详情
function getcompanydata() {
    $.ajax({
        url: 'MyCompany',
        type: "post",
        dataType: "json",
        data: {
            'Username': username
        },
        success: function (data) {
            console.log(JSON.stringify(data));
            $("#qymc").text(data.data[0].name);
            $("#gsyyzzh").text(data.data[0].regnumber);
            var imgname = data.data[0].companyCertEN[0].dir.split('/')[(data.data[0].companyCertEN[0].dir.split('/').length - 1)];
            $("#gsyyzz").html(
                imgname + "&nbsp;&nbsp;<span class='clickword' onclick=\"downimg('" + data.data[0].companyCertEN[0].dir + "')\">下载</span>"
            );
        }
    })
}
//下载图片
function downimg(imgsrc) {
    window.location.href = $("#basePath").val() + "CertDown?path=" + imgsrc;
}

var password;
var username;
//重置密码
function repassword() {
    if ($.trim($("#newpassword").val()) == '' || $.trim($("#newpassword").val()) == null) {
        alert('新密码不能为空');
        return
    }
    if ($.trim($("#newpassword").val()) != $.trim($("#renewpassword").val())) {
        alert('2次密码不一致，请检查');
        return
    }
    $.ajax({
        url: 'changepublicpsw',
        type: "post",
        dataType: "json",
        data: {
            'pswold': password,
            'username': username,
            'pswnew': $.trim($("#newpassword").val())
        },
        success: function (data) {
            alert('密码重置成功');
            $('#myModal').modal('hide')
            getuserdata();
        }
    })
}