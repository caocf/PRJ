$(document).ready(function() {
	UserInfor($("#userId").val());
});

function UserInfor(userId) {
	$.ajax( {
		url : 'showUserById',
		type : "post",
		dataType : "json",
		data : {
			'user.userId':userId
		},
		success : function(data) {
			$("#name").text(data.list[0][0].name);
			$("#department").text(data.list[0][2].departmentName);
			$("#post").text(data.list[0][1].postName);
			$("#email").text(data.list[0][0].email);
			$("#tel").text(data.list[0][0].tel);
			$("#lawId").text(data.list[0][0].lawId);
			$("#StatusType").text(data.list[0][4].statusName);
			$("#role").text(data.list[0][3].roleName);
			
			
		}
	});
}
function updateUser(){
	window.location.href=$('#basePath').val()+"page/system/person_update.jsp?UserId="+$("#userId").val();
}
function ChangePassword(){
	window.location.href=$('#basePath').val()+"page/system/ChangPassword.jsp";
}
