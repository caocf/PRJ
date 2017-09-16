$(document).ready(function() {
	if($("#loginname").val()=="null")
		parent.location.href=$("#basePath").val()+"page/login.jsp";

	$("#oldPwd").focus(function() {
		var che = oldpasswordCheck();
		$("#oldPwderr").text("");
	});
	$("#oldPwd").blur(function() {
		oldpasswordCheck();
	});
	$("#newPwd").focus(function() {
		var che = passwordCheck();		
		$("#newPwderr").text("");
	});
	$("#newPwd").blur(function() {
		passwordCheck();
	});
	$("#repeatnewPwd").focus(function() {
		var che = confirmPasswordCheck();		
		$("#repeatnewPwderr").text("");
	});
	$("#repeatnewPwd").blur(function() {
		confirmPasswordCheck();
	});
});
function oldpasswordCheck() {
	var ret = true;
	ret = emptyChk("#oldPwd", "#oldPwderr", "原始密码不能为空.");
	if (ret == true) {
		ret = valueLength("#oldPwd", "#oldPwderr", "1", "20", "密码是1-20位");
	}
	if (ret == true) {
		ret = regularChk('#oldPwd', "#oldPwderr", "密码只能包含_ ,英文字母,数字.");
	}
	return ret;
}
function passwordCheck() {
	var ret = true;
	ret = emptyChk("#newPwd", "#newPwderr", "密码不能为空.");
	if (ret == true) {
		ret = valueLength("#newPwd", "#newPwderr", "1", "20", "密码是1-20位");
	}
	if (ret == true) {
		ret = regularChk('#newPwd', "#newPwderr", "密码只能包含_ ,字母,数字");
	}
	return ret;
}
function confirmPasswordCheck() {
	var ret = true;
	ret = emptyChk("#repeatnewPwd", "#repeatnewPwderr", "确认密码不能为空");
	if (ret == true) {
		ret = chkConfirmPwd("#newPwd", "#repeatnewPwd",
				"#repeatnewPwderr", "两次密码不一样");
	}
	return ret;
}
function validSubmit() {
	if (!oldpasswordCheck()) {
		return false;
	}
	if (!passwordCheck()) {
		return false;
	}
	if (!confirmPasswordCheck()) {

		return false;
	}
	return true;
}
function changeRepeatPWD(type){
	type.src="image/operation/submit_click.png";
	if(!validSubmit()){
		return;
	}
	$.ajax( {
		url : 'ChangePassword',
		type : "post",
		dataType : "json",
		data : {
			'user.password': $("#newPwd").val(),
			'user.userId': $("#userId").val(),
			'user.email':$("#oldPwd").val()
		},
		success : function(data) {		
				alert("密码修改成功！");
				 exitSystem();
		},
		error : function(XMLHttpRequest) {
			alert($(".error", XMLHttpRequest.responseText).text());
		}
	});	
}
function exitSystem(){

	var r=confirm("是否重新登陆系统?");
	if(r==true)
	{
		var basePath = $("#basePath").val(); 
		top.location.href= basePath+"page/login.jsp";
	}
	/*$.ajax( {
		url : 'ExitSystem',
		type : "post",
		dataType : "json",
		success : function(data) {}
	});	*/

}
function closeDialog(){
	$("#dialogTable input").attr("value","");
	$("#dialogTable label").text("");
}
