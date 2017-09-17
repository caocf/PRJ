$(document).ready(function(){
	$("#top_text").text("个人设置");//top上的显示
    $("#updatePass").click(function(){
    	updatePassword();
    });
});

function validatePass(){
	var oldpassword = $.trim($("#mm").val());
	var newpassword= $.trim($("#newmm").val());
	var checkpassword = $.trim($("#checkmm").val());
	if(oldpassword==""||oldpassword==null){
		alert("旧密码不能为空");
		$("#mm").focus();
		return false;
	}
	if(newpassword==""||newpassword==null){
		alert("新密码不能为空");
		$("#newmm").focus();
		return false;
	}
	if(checkpassword==""||checkpassword==null){
		alert("确认密码不能为空");
		$("#checkmm").focus();
		return false;
	}
	if(newpassword==oldpassword){
		alert("新密码不能与旧密码相同");
		$("#newmm").focus();
		return false;
	}

	var regu = /^\w+$/;
	if (!regu.test(newpassword)) {
		alert("密码只能包含_ ,英文字母,数字!");
		$("#newmm").focus();
		return false;
	}
	if(newpassword.length>20||newpassword.length<5){
		alert("请输入5-20位的密码");
		$("#newmm").focus();
		return false;
	}
	if(newpassword!=checkpassword){
		alert("两次密码不同");
		$("#newmm").focus();
		return false;
	}
	
	return true;
	
}

function updatePassword(){
	var validateresult =validatePass();
	

	if(!validateresult){
		return;
	}
	$.ajax({
		url:'rymanager/passwordupdate', 
		type:'post',
		dataType:'json',
		data:{
			'token':token,	
             'oldpassword':$("#mm").val(),
             'newpassword':$("#newmm").val()     
		},
		success:function(data){
			var code=data.result.resultcode;
			var message = data.result.resultdesc;
			if(code!=1){
				if(code==-1){
					
				    alert(message);
				    
				    window.location.href=$("#basePath").val();
				}else{
					alert(data.result.resultdesc);
					location.reload();
				}
			}else{
			alert("修改成功");	
			location.reload();
			}
			
		}
	});
	
}
