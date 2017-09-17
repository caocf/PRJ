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
	ret = emptyChk("#newPwd", "#newPwderr", "新手机号码不能为空.");
	if (ret == true) {
		ret = isCellphone("#newPwd", "#newPwderr","密码是5-20位");
	}
	return ret;
}
function confirmPasswordCheck() {
	var ret = true;
	ret = emptyChk("#newPwd2", "#newPwd2err", "手机号码不能为空");
	if (ret == true) {
		ret = chkConfirmPwd("#newPwd", "#newPwd2",
				"#newPwd2err", "两次手机号码不一样");
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
function SavePhone(){
	if(!validSubmit()){
		return;
	}
		$$.ajax( {
			url : 'SavePhoneForWeb',
			type : "post",
			dataType : "json",
			data : {
			'newPassword' : $$("#newPwd").val()
			},
			success : function(data) {
				if(!data.success){
					alert("绑定手机失败！");
				}else {
					alert("绑定手机成功！");
					window.location.href = $$("#basePath").val()+ "WebSit/page/register/find4.jsp";
				}
			}
		});	


	}
