$(document).ready(function() {
	//if($("#LoginUser").val()=="null")
	//	window.top.location.href=$("#basePath").val()+"page/login.jsp";
});

var Golval_pid=[10,33,34,35];
var Golval_pid_prompt=[["(无权限)","(个人管理)","(部门管理)","(港航局管理)"],["(无权限)","(个人管理)","(部门管理)","(港航局管理)"],["(无权限)","(查看)","(发布到内部)","(发布到全部)"],["(无权限)","(查看)","(发布到内部)","(发布到全部)"]];
function SpecialPermisssion(spid) {
	$.ajax( {
		url : 'searchRolePermissionByRoleId',
		type : "post",
		dataType : "json",
		success : function(data) {
		for ( var i = 0; i < data.list.length; i++) {
			if (spid==data.list[i][0].permissionId) {
				DoSpecialPermisssion(data.list[i][0].status);//获取权限状态后
				break;
			}
		}
		}
	});

}