$$(document).ready(function() {
	$$("#oldPwd").focus(function() {
		var che = oldPwdCheck();

		$$("#oldPwderr").text("");
	});

	$$("#oldPwd").blur(function() {
		oldPwdCheck();
	});

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
function oldPwdCheck() {
	var ret = true;
	ret = emptyChk("#oldPwd", "#oldPwderr", "原密码不能为空.");
	if (ret == true) {
		ret = valueLength("#oldPwd", "#oldPwderr", "5", "20", "密码是5-20位");
	}
	if (ret == true) {
		ret = regularChk('#oldPwd', "#oldPwderr", "密码只能包含_ ,英文字母,数字.");
	}
	return ret;
}
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
	if (!oldPwdCheck()) {
		return false;
	}
	if (!newPwdCheck()) {

		return false;
	}
	if (!confirmPasswordCheck()) {

		return false;
	}
	return true;
}
function backToModify(){
	window.history.go("-1");
}
function SavePwd(){
	if(!validSubmit()){
		return;
	}
		$$.ajax( {
			url : 'EditPasswordForWeb',
			type : "post",
			dataType : "json",
			data : {
			'oldPassword' : $$("#oldPwd").val(),
			'newPassword' : $$("#newPwd").val()
			},
			success : function(data) {
				if(!data.success){
					alert("原密码错误！");
				}else {
					alert("密码修改成功！");
					window.history.go("-1");
				}
			}
		});	


	}
