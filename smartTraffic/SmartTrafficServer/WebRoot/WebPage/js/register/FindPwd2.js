var startss = 60;
$$(document).ready(function() {
	timer(startss);
});
// 60秒倒计时
function timer(ss) {

	if (ss == 1) {
		$$("#SendMessageAgain").val("重新发送");
		$$("#SendMessageAgain").attr("class","sendMsgBt2");
		$$("#SendMessageAgain").click(function() {
			
				if ($$("#phone").val() == "null") {
					alert("操作超时");
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

						if (data.success) {
							$$("#SendMessageAgain").attr("class","sendMsgBt1");
							$$("#SendMessageAgain").removeAttr("onclick");
							timer(startss);
						} else {
							alert("发生错误请重试！");
						}

					}
				});


		});
	} else {
		var ss = Number(ss - 1);// 计算剩余的秒数
		$$("#SendMessageAgain").val(ss + "秒后重新发送");
		setTimeout("timer(" + ss + ")", 1000);
	}

}


function PhoneCodeCheck() {
	var ret = true;
	ret = emptyChk("#code", "#codeerr", "手机验证码不能为空");
	if (ret == true) {
		ret = isCode("#code", "#codeerr", "手机验证码格式不正确");
	}
	return ret;
}

function ChackViricodeForWeb() {
	if (!PhoneCodeCheck()) {
		alert($$("#codeerr").text());
		return;
	}
	$$.ajax( {
		url : 'ChackViricodeForWeb',
		type : "post",
		dataType : "json",
		data : {
			'phone' : $$("#phone").val(),
			'code' : $$("#code").val()
		},
		success : function(data) {

			if (data.registerStatusCode==0) {
				window.location.href = $$("#basePath").val()
						+ "WebPage/page/register/find3.jsp";
			} else if (data.registerStatusCode==1){
				alert("手机号发生更改！");
			}else if (data.registerStatusCode==2){
				alert("验证码错误！");
			}else if (data.registerStatusCode==3){
				alert("验证码有空值！");
			}else if (data.registerStatusCode==4){
				alert("验证码过期！");
			}else{
				alert("发生错误请重试！");
			}

		}
	});

}
