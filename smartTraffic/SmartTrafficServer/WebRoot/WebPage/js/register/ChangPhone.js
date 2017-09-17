function phoneCheck() {
	var ret = true;
	ret = emptyChk("#phone", "#phoneerr", "手机号码不能为空");
	if (ret == true) {
		ret = isCellphone("#phone", "#phoneerr", "手机号码格式不正确");
	}
	return ret;
}
function forgetPassword() {
	if (!phoneCheck()) {
		alert($$("#phoneerr").text());
		return;
	}
	$$.ajax( {
		url : 'forgetPasswordForWeb',
		type : "post",
		dataType : "json",
		data : {
			'phone' : $$("#phone").val()
		},
		success : function(data) {

			if(data.success){
				window.location.href=$$("#basePath").val()+"WebPage/page/register/ChangPhone2.jsp";
			}else {
				alert("发生错误请重试！");
			}
		
		}
	});

}
