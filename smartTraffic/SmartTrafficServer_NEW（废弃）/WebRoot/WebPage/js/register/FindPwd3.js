$$(document).ready(function() {
	$$("#newPwd").focus(function() {
		var che = newPwdCheck();

		$$("#newPwderr").text("");
	});

	$$("#newPwd").blur(function() {
		newPwdCheck();
	});

	$$("#newPwd2").focus(function() {
		var che = confirmPasswordCheck();

		$$("#newPwd2err").text("");
	});

	$$("#newPwd2").blur(function() {
		confirmPasswordCheck();
	});

});

function newPwdCheck() {
	var ret = true;
	ret = emptyChk("#newPwd", "#newPwderr", "新密码不能为空.");
	if (ret == true) {
		ret = valueLength("#newPwd", "#newPwderr", "5", "20", "密码是5-20位");
	}
	if (ret == true) {
		ret = regularChk('#newPwd', "#newPwderr", "密码只能包含_ ,英文字母,数字.");
	}
	return ret;
}
function confirmPasswordCheck() {
	var ret = true;
	ret = emptyChk("#newPwd2", "#newPwd2err", "确认密码不能为空");
	if (ret == true) {
		ret = chkConfirmPwd("#newPwd", "#newPwd2",
				"#newPwd2err", "两次密码不一样");
	}
	return ret;
}



function validSubmit() {
	if (!newPwdCheck()) {

		return false;
	}
	if (!confirmPasswordCheck()) {

		return false;
	}
	return true;
}
function SavePwd(){
	if(!validSubmit()){
		return;
	}
		$$.ajax( {
			url : 'ChangePasswordForWeb',
			type : "post",
			dataType : "json",
			data : {
			'newPassword' : $$("#newPwd").val()
			},
			success : function(data) {
				if(!data.success){
					alert("密码设置失败！");
				}else {
					alert("新密码设置成功！");
					window.location.href = $$("#basePath").val()+ "WebPage/page/register/find4.jsp";
				}
			}
		});	


	}
