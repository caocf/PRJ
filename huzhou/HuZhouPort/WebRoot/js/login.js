$(document).ready(function() {
});

function clear1() {
	return "";
}
function clear2() {
	return "";

}
function checkForm() {
	if ($("#username").val()== "") {
		alert("请输入用户名！");
		$("#username").focus();
		return false;
	}
	if ($("#username").val().length < 2 ||$("#username").val().length > 20) {
		alert("用户名不能小于2位大于20位！");
		$("#username").focus();
		return false;
	}
	if ($("#password").val() == "") {
		alert("请输入密码！");
		$("#password").focus();
		return false;
	}
	var regu = /^\w+$/;
	if (!regu.test($("#password").val())) {
		alert("密码只能包含_ ,英文字母,数字!");
		form.password.focus();
		return false;
	}
	var regu2 = /^[A-Za-z0-9]+$/;

	if ($("#email").val() == "") {
		alert("请输入验证码！");
		form.rand.focus();
		return false;
	}
	if ($("#email").val().length != 4) {
		alert("验证码是4位！");
		form.rand.focus();
		return false;
	}
	if (!regu2.test($("#email").val())) {
		alert("验证码只能包含英文字母,数字!");
		form.rand.focus();
		return false;
	}

	return true;
}
function changImage() {
	document.getElementById("randImage").src = "validateCode.action?"
			+ Math.random();
}
function Login(){
	$("#btnLogin").attr("src","image/login/bt_login_click.png");
	if(!checkForm()){
		return;
	}
	$.ajax( {
		url : 'login_pc',
		type : 'post',
		dataType : 'json',
		data : {
			'user.userName' : $("#username").val(),
			'user.password' : $("#password").val(),
			'user.email' : $("#email").val()
		},
		success : function(data) {
			window.location.href=$("#basePath").val()+"page/main.jsp";
		},
		error : function(XMLHttpRequest) {
			var errorMsg = $(".error", XMLHttpRequest.responseText).text();
			if(errorMsg.length!=0)
			alert(errorMsg);
			else alert("发生错误，请重新登陆");
			changImage();

		}
	});
}
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];           
     if(e && e.keyCode==13){ // enter 键
    	 Login();
    }
}; 
function Reset(type){
	type.src="image/login/bt_reset_click.png";
	$("#username").attr("value","");
	$("#password").attr("value","");
	$("#email").attr("value","");
	changImage();
}
function LoginOver(id){
	id.src="image/login/bt_login_hover.png";
}
function LoginOut(id){
	id.src="image/login/bt_login_normal.png";
}
function NoBorder(type){
	type.style.borderBottom="0px";
}
