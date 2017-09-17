
$(document).ready(function(){
	$("#top_text").text("个人设置");//top上的显示
	showPersonalInfo();
	$("#change").click(function(){
		infoChange();
	});
	$("#cancle").click(function(){
		infoRecover();
	});
    $("#update").click(function(){
		updatePerson();  
	  });
   
});
function showPersonalInfo(){


	$.ajax({
		url:'rymanager/finduserInfo', 
		type:'post',
		dataType:'json',
		data:{
			'token':token
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
				}
			}else{
			var glry=data.result.obj;
			var rybmlist = glry.rybmlist;
		    $("#name").val(glry.ryxm);
		    $("#xmqp").val(glry.xmqp);
		    $("#sjch").val(glry.sjch);
		    $("#sjdh").val(glry.sjdh);
		    /*$("#department").val(glry.bmmc);*/
		    /*$("#post").val(glry.zw);
		    $("#phone").val(glry.bgsdh);*/	   
		    $("#xmpyszm").val(glry.xmpyszm);
		    $("#rybh").val(glry.rybh);
		/*    $("#headpath").val(glry.headPicture);*/
		   /* $("#imghead").attr("src",glry.headPicture+"?date="+new Date()+";");*/
		    appendToBmtable(rybmlist);
			}
		}
	});
}

function infoChange(){
	//class 属性修改
	$("#name").css("border","#CCCCCC 1px solid ");
	$("#name").attr("disabled",false); 
	$("#xmqp").css("border","#CCCCCC 1px solid");
	$("#xmqp").attr("disabled",false); 
	$("#sjch").css("border","#CCCCCC 1px solid");
	$("#sjch").attr("disabled",false); 
	$("#sjdh").css("border","#CCCCCC 1px solid");
	$("#sjdh").attr("disabled",false); 
/*	$("#phone").css("border","#CCCCCC 1px solid");
	$("#phone").attr("disabled",false);*/ 
	//按钮显示
	$("#updatediv").show();
	/*$("#changeHeadPc").show();*/
}

function infoRecover(){
/*	//class 属性修改
	$("#name").css("border","0px");
	$("#name").attr("disabled",true); 
	$("#xmqp").css("border","0px");
	$("#xmqp").attr("disabled",true); 
	$("#sjch").css("border","0px");
	$("#sjch").attr("disabled",true); 
	$("#sjdh").css("border","0px");
	$("#sjdh").attr("disabled",true); 
	$("#phone").css("border","0px");
	$("#phone").attr("disabled",true); 
	//按钮显示
	$("#updatediv").hide();*/
	location.reload();
}

function updatePerson(){
	var validateresult =validateUser();
	if(!validateresult){
		return;
	}

	 $.ajax({
	      url: 'rymanager/basicinfoupdate', 
	      type: 'post',
	      dataType:'json',
	      data: {
			'token':token,	
             'glry.rybh':$.trim($("#rybh").val()),
             'glry.ryxm':$.trim($("#name").val()),
             'glry.xmqp':$.trim($("#xmqp").val()),
             'glry.sjch':$.trim($("#sjch").val()),
             'glry.sjdh':$.trim($("#sjdh").val()),
/*             'glry.zw':$.trim($("#post").val()),
             'glry.bgsdh':$("#phone").val(),*/
             'glry.xmpyszm':$.trim($("#xmpyszm").val())
            
		},
		success:function(data){
			var code=data.result.resultcode;
			var message =data.result.resultdesc;
			
			if(code!=1){
				if(code==-1){
				window.location.href=$("#basePath").val();
				}else if(code==12){
					alert("手机号码已存在！修改失败，请重新输入");
					$("#sjch").val("");
					$("#sjch").focus();
				}else if(code==18){	
					alert("人员姓名已存在！修改失败，请重新输入");
					$("#name").val("");
					$("#name").focus();
			    }else{
					alert(message);
					window.location.reload();
				}
			}else{
			alert("修改成功");	
			window.location.reload();
			}
			
		}
	});
}

function validateUser(){
    var name = $("#name").val();
    var sjch =  $("#sjch").val();
    var sjdh = $("#sjdh").val();
    var bgsdh = $("#phone").val();
    if(name==null||name==undefined||$.trim(name)==""){
    	alert("请输入姓名");
    	$("#name").focus();
    	return false;
    }
    if(name.length>25){
		alert("姓名长度不能大于25位");
		$("#name").focus();
		return false;
	}
	if($("#xmqp").val()==""){
		alert("请输入姓名全拼");
		$("#xmqp").focus();
		return false;
	}
    if($("#xmqp").val().length>100){
		alert("姓名全拼长度不能大于100位");
		$("#xmqp").focus();
		return false;
	}
    if(sjch==null||sjch==undefined||$.trim(sjch)==""){
    	alert("长号不能为空！");
    	$("#sjch").focus();
    	return false;
    }

    if(!/^\d{11}$/.test(sjch)){
    	alert("11位手机号码格式错误！");
    	$("#sjch").focus();
    	return false;
    }
    if(sjdh!=null&&sjdh!=undefined&&$.trim(sjdh)!=""&&!/^\d{6}$/.test(sjdh)){
    	alert("6位手机短号格式错误！");
    	$("#sjdh").focus();
    	return false;
    }
/*    //验证办公室电话格式
    if(bgsdh!=null&&bgsdh!=undefined&&$.trim(bgsdh)!=""&&!/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.test(bgsdh)){
    	alert("办公室电话格式错误！");
    	$("#phone").focus();
    	return false;
    }*/
    return true;
}

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
				alert(code);
				if(code==-1){ 
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
//清空预览区
function clearPrew(){
/*	 

     //判断是否存在一个新的div
	 if(document.getElementById("previewNew")){
		 document.getElementById("imghead").src=document.getElementById("headpath").value;
	 }else{
		 newobj.parentNode.remove(newobj);
		 alert(typeof(newobj));
		 document.getElementById("preview").style.display = "";
		 
	 }*/
	document.getElementById("imghead").src=document.getElementById("headpath").value;
	 var newPreview =document.getElementById("previewNew");
	
     if(newPreview!=null){
		document.getElementById("previewNew").parentNode.removeChild(document.getElementById("previewNew"));
		document.getElementById("preview").style.display="";
	}
}


function changebuttonover(obj){
	$("#btn").css({"background":"#00435E"});
	
}
function changebuttonout(obj){
	$("#btn").css({"background":"#006993"});
	
}
function appendToBmtable(list){
	for(var i=0;i<list.length;i++){
			newTr = $("<tr ></tr>");
			newTr.append($("<td style='text-align: right;'>所属机构:</td>"));
			newTr.append($("<td>"+list[i].ssjgmc+"</td>"));
			newTr.append($("<td style='text-align: right;'>所属部门:</td>"));
			newTr.append($("<td>"+list[i].bmmc+"</td>"));
			
			newTr1 = $("<tr ></tr>");
			newTr1.append($("<td style='text-align: right;'>办公室电话:</td>"));
			newTr1.append($("<td>"+list[i].bgsdh+"</td>"));
			
			newTr1.append($("<td style='text-align: right;'>职务:</td>"));
			newTr1.append($("<td>"+list[i].zwmc+"</td>"));
	$("#userTable").append(newTr);
	$("#userTable").append(newTr1);
	}
}

