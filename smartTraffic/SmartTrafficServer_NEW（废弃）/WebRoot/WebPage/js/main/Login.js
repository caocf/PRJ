/*// 焦点
$$(document).ready(function() {
	// 得到焦点
	$$("#userName").focus(function() {
		var che = userNameCheck();
		$$("#loginerr").text("");
	});
	// 失去焦点
	$$("#userName").blur(function() {
		userNameCheck();
	});
	// 得到焦点
	$$("#password").focus(function() {
		var che = passwordCheck();
		$$("#loginerr").text("");
	});
	// 失去焦点
	$$("#password").blur(function() {
		passwordCheck();
	});
});*/ 

function userNameCheck() {
	var ret = true;
	ret = emptyChk("#userName", "#loginerr", "用户名不能为空.");
	if(ret == true){
		var name=$$("#userName").val();
		 name=name.replace(/(^s*)|(s*$)/g, "");
		if(name.substring(0,1)=="请"){
			$$("#loginerr").text("用户名不能为空.");
			ret =false;
		}else{
			ret =true;
		}
	}
	if (ret == true) {
		ret = valueLength("#userName", "#loginerr", "3", "20", "用户名是3-20位");
	}
	if (ret == true) {
		ret = regularChk('#userName', "#loginerr", "用户名只能包含_ ,英文字母,数字.");
	}
	return ret;
}
function passwordCheck() {
	var ret = true;
	ret = emptyChk("#password", "#loginerr", "密码不能为空.");
	if (ret == true) {
		ret = valueLength("#password", "#loginerr", "3", "20", "密码是3-20位");
	}
	if (ret == true) {
		ret = regularChk('#password', "#loginerr", "密码只能包含_ ,英文字母,数字.");
	}
	return ret;
}

function validSubmit() {
	
	if (!userNameCheck()) {
		return false;
	}
	if (!passwordCheck()) {
		return false;
	}
	return true;
}


// 登录
function Login(){
	if(!validSubmit()){
		return;
	}
	//保存或删除cookie
	CookieSaveOrDelete($$("#userName").val(),$$("#password").val());
	$$.ajax( {
		url : 'login',
		type : 'post',
		dataType : 'json',
		data : {
			'phone':$$("#userName").val(),
			'password':$$("#password").val()
		},
		success : function(data) {
			// 处理后台获取的数据
			if(data.success){
				alert("登录成功");
				// 显示登录信息框
				parent.ShowLoginPersonDIV();
				parent.closeWindow();
			}else{
				alert("登录失败");
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}

	});

}
function closeWindow(){
	if(!LogAndExitRefresh){
		window.history.go("-1");
	}else{
		parent.closeWindow();
	}
}
function changeTip(th){
    var passText = document.getElementById('passText');
    var password = document.getElementById('password');
    passText.style.display='none';
    password.style.display='';
    password.focus();
    if(th.value==th.defaultValue){th.value='';th.style.color='#000';}
    

}
function changeBack(th){
	var password = document.getElementById('password');
	var passText = document.getElementById('passText');
	if(password.value==""){
		password.style.display='none';
		passText.style.display='';
		passText.value="请输入密码";
		passText.style.color='#a3a3a3';
	}
}  
//保存或删除cookie
function CookieSaveOrDelete(userName,password){
	if($$("#CookieSaveOrDelete").prop("checked")==true){
		addCookie(Cookie_Name,userName,14);
		addCookie(Cookie_Pwd,password,14);
	}else{
		deleteMyCookie();
	}
}