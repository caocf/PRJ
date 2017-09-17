$$(document).ready(function() {
	LogAndExitRefresh=true;//登录退出时是否刷新页面
	if ($$("#LoginUser").val() == "null") {
		LoginWeb();
		
	} else {
	SearchAllComplainType();
	}
	

	$$("#titletext").focus(function() {
		var che = checktext();
		$$("#titletexterr").text("");
	});
   $$("#titletext").blur(function() {
	   checktext();
	});
   
   
   $$("#textareatext").focus(function() {
		var che = checktextarea2();
		$$("#textareacont").text("");
	});
   $$("#textareatext").blur(function() {
	   checktextarea2();
	});
   
   
   $$("#phonetext").focus(function() {
		var che = checkphone();
		$$("#phonetexterr").text("");
	});
   $$("#phonetext").blur(function() {
	   checkphone();
	});
   

});

function SearchAllComplainType() {
	$$
			    .ajax( {
				url : "SearchAllComplainType",
				type : "post",
				success : function(data) {
					var list = data.complainTypes;
					for ( var i = 0; i < list.length; i++) {
						if (i == 0) {
							$$("#regionCode .CRselectValue").text(list[i].name);

							$$("#regionCode .abc_CRtext").val(
									list[i].name);
							$$("#regionCode .abc").val( list[i].id);
							$$("#regionCode .CRselectBoxOptions")
									.append(
											$$("<li class='CRselectBoxItem'><a class='selected' rel="
													+ list[i].id
													+ ">"
													+ list[i].name
													+ "</a></li>"));
						} else {
							$$("#regionCode .CRselectBoxOptions").append(
									$$("<li class='CRselectBoxItem'><a  rel="
											+ list[i].id + ">" + list[i].name
											+ "</a></li>"));
						}
						;
					}
					SelectOption("regionCode", 150);
				}
			});
}





function checktext(){ //标题部分
	var reg = /<|>|'|;|&|#|"|\/| |'/;
	var mytitletext=document.getElementById("titletext").value;
	var mytitletexterr=document.getElementById("titletexterr");
	if(mytitletext!=""){
		if(reg.test(mytitletext)){		
			mytitletexterr.innerHTML="<font color='red'>不能输入此类字符！</font>";
			return false;
		}else{
			mytitletexterr.innerHTML="<font color='green'>√</font>";
			return true;
		}
	}else
	 {
		mytitletexterr.innerHTML="<font color='red'>标题不能为空！</font>";
		//mytitletexterr.innerHTML="";
	     return false;
	 }	
}

function checktextarea2(){
	var mytextareacont=document.getElementById("textareacont");
	if(document.getElementById("textareatext").value==""){
	
		mytextareacont.innerHTML="<font color='red'>内容不能为空！</font>";
	     return false;
	}else{
		mytextareacont.innerHTML="";
		 return true;
	}
} 
function checktextarea(textareaID,spanID,maxNum)   

{    
	var mytextareacont=document.getElementById("textareacont");
	// 得到输入的字符的长度
	var NowNum = $$.trim($$("#textareatext").val()).length;
	if(document.getElementById("textareatext").value!=""){
	// 判断输入的长度是否超过规定的长度
	if(NowNum>maxNum)
	{
	   // 如果超过就截取规定长度的内容
	document.getElementById("textareatext").value = document.getElementById("textareatext").value.substring(0,maxNum);
	return false;
	}
	else{
	    // 得到当前的输入长度并且显示在页面上
	document.getElementById("textareatexterr").innerHTML = NowNum;
	return true;
	}
	}else{
		document.getElementById("textareatexterr").innerHTML = NowNum;
		mytextareacont.innerHTML="<font color='red'>内容不能为空！</font>";
	     return false;
	}
}  
    
  
    

function checkphone(){ //手机验证
	var reg = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
	var myphonetext=document.getElementById("phonetext").value;
	var myphonetexterr=document.getElementById("phonetexterr");
	if(myphonetext!=""){
		if(!reg.test(myphonetext)){		
			myphonetexterr.innerHTML="<font color='red'>手机号码格式错误！</font>";
			return false;
		}else{
			myphonetexterr.innerHTML="<font color='green'>√</font>";
			return true;
		}
	}else
	 {
		myphonetexterr.innerHTML="";
	     return true;
	 }	
}


function validSubmit() {
	if (!checkphone()) {
		return false;
	}
	if (!checktext()) {
		return false;
	}
	if (!checktextarea('textareatext','textareatexterr',500)) {

		return false;
	}
	if(!checktextarea2()){
		return false;
	}
	return true;
}

function SaveComplain(){  //检验是否提交成功
	if(!validSubmit()){
		return false;
	}
	$$.ajax( {
		url : 'SaveSuggestion',
		type : 'post',
		dataType : 'json',
		data : {
		    'title':$$("#titletext").val(),
		    'type':$$("#inputtext").val(),		    
		    'content':$$("#textareatext").val(),
		    'contact' : $$("#phonetext").val()
		},
		beforeSend : function() {
			//开始提交数据
		},
		success : function(data) {
			//处理后台获取的数据
			if(data.saveResult){
				alert("提交失败");
			}else{				
				alert("提交成功");			
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
	
	

