function Login(){
	$$.ajax( {
		url : 'login',
		type : 'post',
		dataType : 'json',
		data : {
			'phone':$$("#userName").val(),
			'password':$$("#psd").val()
		},
		beforeSend : function() {
			//开始提交数据
		},
		success : function(data) {
			//处理后台获取的数据
			if(data.success){
				alert("登录成功");
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		},
		complete : function() {
			//数据处理完成
	}

	});
}

function changeBack(th){
	var password = document.getElementById('txtuserpassword');
	var passText = document.getElementById('txtusertext');
	if(password.value==""){
	password.style.display='none';
	       passText.style.display='';
	passText.value="请输入密码";passText.style.color='#999';
	}
	}   

function changeTip(th){
	var password = document.getElementById('txtuserpassword');
	var passText = document.getElementById('txtusertext');
      passText.style.display='none';
        password.style.display='';
        password.focus();
        if(th.value==th.defaultValue){th.value='';th.style.color='#000'}
    

}
function changeBack1(th){
	var password = document.getElementById('txtpwdagin');
	var passText = document.getElementById('txtpwdtext');
	if(password.value==""){
	password.style.display='none';
	       passText.style.display='';
	passText.value="请输入密码";passText.style.color='#999';
	}
	}   

function changeTip1(th){
	var password = document.getElementById('txtpwdagin');
	var passText = document.getElementById('txtpwdtext');
      passText.style.display='none';
        password.style.display='';
        password.focus();
        if(th.value==th.defaultValue){th.value='';th.style.color='#000'}
    

}