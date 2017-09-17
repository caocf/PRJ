function clear() {
	return "";
}
function checkForm() {
	if ($("#username").val() == "") {
		alert("请输入用户名！");
		$("#username").focus();
		return false;
	}
	if ($("#username").val().length < 2 || $("#username").val().length > 20) {
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
		$("#password").focus();
		return false;
	}

	return true;
}

function Login() {
	if (!checkForm()) {
		return;
	}
	$.ajax( {
		url : 'initDB',
		type : 'post',
		dataType : 'json',
		data : {
			'username' : $("#username").val(),
			'password' : $("#password").val()
		},
		success : function(data) {
		if(data.connect)
			window.location.href = $("#basePath").val() + "page/TableInfo.jsp";
		else alert("用户名或密码错误");
		},
		error : function(XMLHttpRequest) {
			alert("登录错误，请重新登录");

		}
	});
}

document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) { // enter 键
		Login();
	}
};
