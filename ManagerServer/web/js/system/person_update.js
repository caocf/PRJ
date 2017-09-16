$(document).ready(function() {	
    updateUserInfor($("#UserId").val());
});

function updateUserInfor(userId){
	$.ajax({
		url : 'showUserById',
		type : "post",
		dataType : "json",
		data : {'userId':userId},
		success : function(data) {
		$("#userId").attr("value",data.list[0][0].userId);
		$(".userName").attr("value",data.list[0][0].userName);
		$(".userName_text").text(data.list[0][0].userName);
		$(".deparment_text").text(data.list[0][2].departmentName);
		$(".post_text").text(data.list[0][1].postName);
		$(".lawId_text").text(data.list[0][0].lawId);
		$(".userStatus_text").text(data.list[0][4].statusName);
		$(".role_text").text(data.list[0][3].roleName);
		$("#password").attr("value", data.list[0][0].password);
		$("#confirmPassword").attr("value", data.list[0][0].password);
		$("#partOfDepartmentId").attr("value", data.list[0][0].partOfDepartment); 
		$("#realname").attr("value", data.list[0][0].name);
		$("#partOfRole").attr("value", data.list[0][0].partOfRole);	
		$("#lawId").attr("value", data.list[0][0].lawId);
		$("#tel").attr("value", data.list[0][0].tel);
		$("#email").attr("value", data.list[0][0].email);
		$("#partOfPost").attr("value", data.list[0][0].partOfPost);
		$("#userStatus").attr("value",data.list[0][0].userStatus);
	}
	});
	
	
}

//修改
function updateUserInfo() {
	if(!validSubmit()){
		return;
	}else{
		$.ajax( {
		url : 'updateUserInfo',
		type : "post",
		dataType : "json",
		data : $('#actionName :input').serialize(),
		success : function(data) {
			alert("编辑成功！");	
			window.location.href=$('#basePath').val()+"page/system/person_center.jsp";
	},
	error : function(XMLHttpRequest) {
		var errorMsg=$(".error", XMLHttpRequest.responseText).text();
		errorMsg = errorMsg.replace(/^\s*/, "").replace(/\s*$/, "");
		if(errorMsg!="user")		
			alert("信息发生错误，请刷新页面");
		else
		    alert("此用户已存在！");
			
		}
	});
	}
	
}