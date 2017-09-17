/**
 * Created by Dj on 2015/7/16.
 */
$(document).ready(function () {
    var username = $("#username").val();
    if (username == null || username == "" || username == "null") {
        window.location.href = $("#basePath").val() + "page/login/login.jsp";
    }

    $('#back').click(function () {
        window.location.href = $("#basePath").val() + "page/home/home.jsp";
    });

    $('#menu a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    $('#myModal').on('show.bs.modal', function (e) {
        hideAllErr();
    });
    $('#myModal').on('shown.bs.modal', function (e) {
        $("#inputOriPwd").focus();
    });

    $("#btnSaveEdit").click(function () {
        var name = $("#tdName").val();
        var phone = $("#tdPhone").val();
        var email = $("#tdEmail").val();
        var lawid = $("#tdLawid").val();

        if (name == null || name == "") {
            $("#tdName").focus();
            $("#divName").addClass("has-error");
            $("#tdNameErr").removeClass("hide");
            $("#tdNameErr").addClass("show");
            $("#tdNameErr").html("请输入姓名");
            return;
        }
        if (phone != null && phone != "") {
            var patrnmobile = /^0?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
            if (!patrnmobile.exec($("#tdPhone").val())) {
                $("#tdPhone").focus();
                $("#divPhone").addClass("has-error");
                $("#tdPhoneErr").removeClass("hide");
                $("#tdPhoneErr").addClass("show");
                $("#tdPhoneErr").html("联系电话格式不正确");
                return
            }
        }
        if (email != null && email != "") {
            var patrnemail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            if (!patrnemail.exec(email)) {
                $("#tdEmail").focus();
                $("#divEmail").addClass("has-error");
                $("#tdEmailErr").removeClass("hide");
                $("#tdEmailErr").addClass("show");
                $("#tdEmailErr").html("邮箱格式不正确");
                return
            }
        }
        $.ajax({
            url: 'userinfo/updateuserinfo',
            type: 'post',
            dataType: 'json',
            data: {
                'id': $("#userid").val(),
                'name': name,
                'tel': phone,
                'email': email,
                'lawid': lawid
            },
            success: function (data) {
                if (ifResultOK(data)) {
                    window.location.reload();
                } else {

                }
            },
            error: function (data) {

            }
        });
    });

    $('#btnSavePwd').click(function () {
        hideAllErr();
        var oripwd = $("#inputOriPwd").val();
        var newpwd = $("#inputNewPwd").val();
        var anewpwd = $("#inputANewPwd").val();
        if (oripwd == null || oripwd == "") {
            $("#inputOriPwd").focus();
            $("#divOriPwd").addClass("has-error");
            $("#inputOriPwdErr").html("请输入原密码");
            $("#inputOriPwdErr").removeClass("hide");
            $("#inputOriPwdErr").addClass("show");
            return;
        }
        if (newpwd == null || newpwd == "") {
            $("#inputNewPwd").focus();
            $("#divNewPwd").addClass("has-error");
            $("#inputNewPwdErr").html("请输入密码");
            $("#inputNewPwdErr").addClass("hide");
            $("#inputNewPwdErr").addClass("show");
            return;
        }
        if (anewpwd == null || anewpwd == "") {
            $("#inputANewPwd").focus();
            $("#divANewPwd").addClass("has-error");
            $("#inputANewPwdErr").html("请再次输入密码");
            $("#inputANewPwdErr").addClass("hide");
            $("#inputANewPwdErr").addClass("show");
            return;
        }
        if (anewpwd != newpwd) {
            $("#inputANewPwd").focus();
            $("#divANewPwd").addClass("has-error");
            $("#inputANewPwdErr").html("两次输入密码不一致");
            $("#inputANewPwdErr").addClass("hide");
            $("#inputANewPwdErr").addClass("show");
            return;
        }

        $.ajax({
            url: 'userinfo/updateuserpassword',
            type: 'post',
            dataType: 'json',
            data: {
                'id': $("#userid").val(),
                'password': newpwd,
                'oldpassword': oripwd
            },
            success: function (data) {
                if (ifResultOK(data)) {
                    $('#myModal').modal('hide');
                } else {
                    if (getResultCode(data) == RESULT_USERINFO_OLDPASSERROR) {
                        $("#inputOriPwd").focus();
                        $("#divOriPwd").addClass("has-error");
                        $("#inputOriPwdErr").html("原密码不正确");
                        $("#inputOriPwdErr").removeClass("hide");
                        $("#inputOriPwdErr").addClass("show");
                    } else {
                        $("#inputOriPwd").focus();
                        $("#divOriPwd").addClass("has-error");
                        $("#inputOriPwdErr").html(getResultDesc(data));
                        $("#inputOriPwdErr").removeClass("hide");
                        $("#inputOriPwdErr").addClass("show");
                    }
                }
            },
            error: function (data) {
                $("#inputOriPwd").focus();
                $("#divOriPwd").addClass("has-error");
                $("#inputOriPwdErr").html("网络错误");
                $("#inputOriPwdErr").removeClass("hide");
                $("#inputOriPwdErr").addClass("show");
            }
        });

    });

    $("#btnEdit").click(function () {
        $("#tdName").focus();
        $("#tdName").css('border', '1px solid gray');
        $("#tdName").addClass('form-control');
        $("#tdName").removeAttr("readonly");

        $("#tdPhone").css('border', '1px solid gray');
        $("#tdPhone").addClass('form-control');
        $("#tdPhone").removeAttr("readonly");

        $("#tdEmail").css('border', '1px solid gray');
        $("#tdEmail").addClass('form-control');
        $("#tdEmail").removeAttr("readonly");

        $("#tdLawid").css('border', '1px solid gray');
        $("#tdLawid").addClass('form-control');
        $("#tdLawid").removeAttr("readonly");

        $("#divEditSave").addClass("show");
        $("#divEditSave").removeClass("hide");

        $("#divEditBtn").addClass("hide");
        $("#divEditBtn").removeClass("show");

        $("#divEditCancel").addClass("show");
        $("#divEditCancel").removeClass("hide");
    });

    initui();
});

function centerModals() {
    $('.modal').each(function (i) {
        var $clone = $(this).clone().css('display', 'block').appendTo('body');
        var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
        top = top > 0 ? top : 0;
        $clone.remove();
        $(this).find('.modal-content').css("margin-top", top);
    });
}

function hideAllErr() {
    $("#inputOriPwdErr").removeClass("show");
    $("#inputNewPwdErr").removeClass("show");
    $("#inputANewPwdErr").removeClass("show");
    $("#inputOriPwdErr").addClass("hide");
    $("#inputNewPwdErr").addClass("hide");
    $("#inputANewPwdErr").addClass("hide");
    $("#divOriPwd").removeClass("has-error");
    $("#divNewPwd").removeClass("has-error");
    $("#divANewPwd").removeClass("has-error");
    $("#inputOriPwdErr").val("");
    $("#inputNewPwdErr").val("");
    $("#inputANewPwdErr").val("");
}

function initui() {

    $.ajax({
        url: 'userinfo/queryuserinfo',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': $("#userid").val()
        },
        success: function (data) {
            if (ifResultOK(data)) {
                var record = getResultRecords(data);
                var pojo = record.data[0][0];
                if (record != null) {
                    $("#tdUsername").html(pojo.username);
                    $("#tdName").val(pojo.name);
                    $("#tdPhone").val(pojo.tel);
                    $("#tdEmail").val(pojo.email);
                    $("#tdLawid").val(pojo.lawid);
                    var roles = getResultMap(data).roles;
                    var roleslen = roles.length;
                    var rolename = "";
                    for (var i = 0; i < roleslen; i++) {
                        if (i == roleslen - 1) {
                            rolename += roles[i].name;
                        } else {
                            rolename += roles[i].name + " ";
                        }
                    }
                    $("#tdRoles").val(rolename);
                    if (record.data[0][1].id != 0) {
                        $("#tdTitle").html(record.data[0][1].attrdesc);
                    }
                    if (record.data[0][2].id != 0) {
                        $("#tdJstatus").html(record.data[0][2].type + "/" + record.data[0][2].typedesc);
                    }
                    $("#tdDpt").html(getResultMap(data).dpts);
                }
            }
        },
        error: function (data) {

        }
    });
}