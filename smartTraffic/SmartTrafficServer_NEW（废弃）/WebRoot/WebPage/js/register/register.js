var PhoneCode="";
var startss = 60;
function OnLoad(){
	initComplexArea('seachprov', 'seachcity', 'seachdistrict',area_array, sub_array, '0', '0', '0');
}
$$(document).ready(function() {
		$$("#txttelephone").focus(function() {
			var che = checktelephone();
			$$("#txttelephoneerr").text("");
		});
	   $$("#txttelephone").blur(function() {
		   checktelephone();
		});
	   
	   $$("#codetext").focus(function() {
			var che = Getredo();
			$$("#codetexterr").text("");
		});
	   $$("#codetext").blur(function() {
		   Getredo();
		});
	   $$("#username").focus(function() {
			var che = checkusername();
			$$("#usernameerr").text("");
		});
	   $$("#username").blur(function() {
		   checkusername();
		});
	   
		$$("#txtuserpassword").focus(function() {
			var che = checkuserpassword();
			$$("#userpassword").text("");
		});		
		$$("#txtuserpassword").blur(function() {
			checkuserpassword();
		});
		
		$$("#txtpwdagin").focus(function() {
			var che = checkpwdagin();
			$$("#pwdagin").text("");
		});
		$$("#txtpwdagin").blur(function() {
			checkpwdagin();
		});
		$$("#txtemail").focus(function() {
			var che = checkemail();
			$$("#emailerr").text("");
		});
	   $$("#txtemail").blur(function() {
		   checkemail();
		});
}); 

// 检查手机号码
function checktelephone() {
	$$("#txttelephoneerr").css("color","red");
	var ret = true;
	ret = emptyChk("#txttelephone", "#txttelephoneerr", "手机号码不能为空！");
	if(ret == true){
		ret = isCellphone("#txttelephone", "#txttelephoneerr", "手机号码格式错误");
	}
	if(ret==true){
		$$.ajax( {
			url : 'PhoneIsRegisterForWeb',
			type : 'post',
			dataType : 'json',
			data : {
				'phone':$$("#txttelephone").val()
			},
			success : function(data) {
				// 处理后台获取的数据
				if(data.userIsExist){
					ret=false;
					$$("#txttelephoneerr").text("手机号码已被注册");
					
				}else{
				    ret=true;
				    $$("#txttelephoneerr").text("√");
				    $$("#txttelephoneerr").css("color","green");
				}
			},
			error : function(a, b, c) {
				ret=false;
				//alert("出现错误，请刷新");
			}
		});
	}
	return ret;
}

//手机验证码
function Getredo(){
	$$("#codetexterr").css("color","red");
	var ret = true;
	if(PhoneCode==""){
		if(!checktelephone()){
			$$("#txttelephoneerr").css("color","red");
			$$("#txttelephoneerr").text("请先输入正确的手机号码！");
		}else{
			$$("#txttelephoneerr").text("请点击获取验证码！");
				ret=false
		}
		
	}
	if(ret==true){
		ret = emptyChk("#codetext", "#codetexterr", "手机验证码不能为空！");
	}
	
	if(ret == true){
		ret = isCode("#codetext", "#codetexterr", "手机验证码格式错误");
	}
	if(ret == true){
		if($$("#codetext").val()==PhoneCode){
			$$("#codetexterr").css("color","green");
			$$("#codetexterr").text("√");
			ret = true;
		}else{
			$$("#codetexterr").text("验证码错误");
			ret = false;
		}
	}
	
	return ret;
}
function checkusername(){

	$$("#usernameerr").css("color","red");
	var ret = true;
	
	if(ret==true){
		ret = emptyChk("#username", "#usernameerr", "用户名不能为空！");
	}
	
	if(ret == true){
		ret = regularChk("#username", "#usernameerr", "用户名只能是大小写英文字母、数字、下划线");
	}
	if(ret == true){
		ret = valueLength("#username", "#usernameerr", 1,20,"用户名不能超过20个字符");
	}
	if(ret == true){
			$$("#usernameerr").css("color","green");
			$$("#usernameerr").text("√");
	}
	return ret;

}
//获取验证码
function GetVerifyCodeForWeb(){
	if(!checktelephone()){
		return;
	}
	$$.ajax( {
		url : 'GetVerifyCodeForWeb',
		type : 'post',
		dataType : 'json',
		data : {
			'phone':$$("#txttelephone").val()
		},
		success : function(data) {
			// 处理后台获取的数据
			if(data.success){
				PhoneCode=data.code;
				$$("#codetexterr").text("");
		
				$$("#bt_getcode").removeAttr("onclick");
				$$("#bt_getcode").attr("class","sendMsgBt1");
				timer(startss);
			}else{
				$$("#codetexterr").css("color","red");
				$$("#codetexterr").text("验证码获取失败！请重新获取");
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	});
  
}
//60秒倒计时
function timer(ss) {

	if (ss == 1) {
		$$("#bt_getcode").val("重新发送");
		$$("#bt_getcode").attr("class","sendMsgBt2");
		$$("#bt_getcode").click(function() {
			GetVerifyCodeForWeb();
		});
	} else {
		var ss = Number(ss - 1);// 计算剩余的秒数
		$$("#bt_getcode").val(ss + "秒后重新发送");
		setTimeout("timer(" + ss + ")", 1000);
	}

}
// 检查E-mail
function checkemail() {
	$$("#emailerr").css("color","red");
	var ret = true;
	if($$("#txtemail").val()!=""){
		if(ret == true){
			ret = isEmail("#txtemail", "#emailerr", "电子邮箱格式错误");
		}
		if(ret == true){
				$$("#emailerr").css("color","green");
				$$("#emailerr").text("√");
				
		}
	}
	return ret;
	
}

//检查密码
function checkuserpassword(){

	$$("#userpassword").css("color","red");
	var ret = true;
	if(ret==true){
		ret = emptyChk("#txtuserpassword", "#userpassword", "密码不能为空");
	}
	
	if(ret == true){
		ret = valueLength("#txtuserpassword", "#userpassword","5","20", "密码为5-20位");
	}
	if(ret == true){
		ret = regularChk("#txtuserpassword", "#userpassword", "密码只能是英文字母、数字、下划线");
	}
	if(ret == true){
		$$("#userpassword").css("color","green");
		$$("#userpassword").text("√");
	}
	return ret;
}
//检查确认密码
function checkpwdagin()  {

	$$("#pwdagin").css("color","red");
	var ret = true;
	ret = emptyChk("#txtpwdagin", "#pwdagin", "确认密码不能为空");
	if (ret == true) {
		ret = chkConfirmPwd("#txtuserpassword", "#txtpwdagin","#pwdagin", "两次密码不一样");
	}
	if(ret == true){
		$$("#pwdagin").css("color","green");
		$$("#pwdagin").text("√");
	}
	return ret;

}

function validSubmit() {
	if (!checktelephone()) {
		return false;
	}
	if (!Getredo()) {
		return false;
	}
	if (!checkusername()) {
		return false;
	}
	if (!checkuserpassword()) {

		return false;
	}
	if (!checkpwdagin()) {

		return false;
	}
	if (!checkemail()) {
		return false;
	}
	return true;
}
//提交验证
function ModifyUserInfoCheck() {
	var address = "";
	if ($$("#seachprov").val() != "0") {
		address = $$("#seachprov").find("option:selected").text() + "-";
		if ($$("#seachcity").val() == "0") {
			address += "-";
		} else {
			address += $$("#seachcity").find("option:selected").text() + "-";
		}
		if ($$("#seachdistrict").val() != "0") {
			address += $$("#seachdistrict").find("option:selected").text();
		}

	}
	return address;
}
// 注册
function Register(){
	if(!validSubmit()){
		return ;
	}
	var address=ModifyUserInfoCheck();
	var birthday=$$("#seachyear").val()+"-"+$$("#seachmonth").val()+"-"+$$("#seachday").val();
	$$.ajax( {
		url : 'RegisterUserForWeb',
		type : 'post',
		dataType : 'json',
		data : {
			'phone':$$("#txttelephone").val(),
			'code':$$("#codetext").val(),
			'email':$$("#txtemail").val(),
			'password':$$("#txtuserpassword").val(),
			'username':$$("#username").val(),
			'address':address,
			'birthday':birthday,
			'sex':$$('input[name="sex"]:checked').val()
			
		},
		success : function(data) {
			if(data.registerStatusCode!=null){
				if(data.registerStatusCode==1){
					alert("手机号与请求验证码时不一致");
				}else if(data.registerStatusCode==2){
					alert("验证码错误 ");
				}else if(data.registerStatusCode==3){
					alert("密码不能为空 ");
				}else if(data.registerStatusCode==4){
					alert("信息过期，请重新获取验证码 ");
				}else if(data.registerStatusCode==0){
					window.location.href = $$("#basePath").val() + "WebPage/page/register/reg_suc.jsp";
				}
				
			}else{
				alert("有错误请刷新！");
				
			}
		},
		error : function(XMLHttpRequest) {
			var errorMsg=$(".error", XMLHttpRequest.responseText).text();
				alert("信息发生错误，请刷新页面");
		}
			

	});
}
