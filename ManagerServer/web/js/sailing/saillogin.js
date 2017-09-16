function changImage() {
	document.getElementById("randImage").src = "validateCode.action?"
			+ Math.random();
}

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
		return false;
	}
	var regu2 = /^[A-Za-z0-9]+$/;

	if ($("#email").val() == "") {
		alert("请输入验证码！");
		return false;
	}
	if ($("#email").val().length != 4) {
		alert("验证码是4位！");
		return false;
	}
	if (!regu2.test($("#email").val())) {
		alert("验证码只能包含英文字母,数字!");
		return false;
	}

	return true;
}
function sailLogin(){
	if(!checkForm()){
		return;
	}
	$.ajax({
		url:'LoginPcByPublic',
		type:'post',
		dataType:'json',
		data:{
			'publicUser.UserName':$("#username").val(),
			'publicUser.psd':$("#password").val(),
			'publicUser.imei':$("#email").val()
		},
		success:function(data){
			if(data.allTotal==2){
				alert("用户名不存在！");
				changImage();
			}else if(data.allTotal==3){
				alert("密码错误!");
				changImage();
			}else if(data.allTotal==4){
				alert("验证码不正确!");
				changImage();
			}else if(data.allTotal==5){
				alert("用户没有绑定的船舶!");
				changImage();
			}else{
				window.location.href=$("#basePath").val()+"page/sailing/sailreport.jsp";
			}
				
		}
		
	});
	
}
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];           
     if(e && e.keyCode==13){ // enter 键
    	 sailLogin();
    }
}; 