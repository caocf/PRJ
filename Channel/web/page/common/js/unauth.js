$(document).ready(function(){
	// 未登录返回登录页面
	var username = $("#username").val();
	if (username == null || username == "" || username == "null") {
		getRootWin().location.href = $("#basePath").val() + "page/login/login.jsp";
	}
});