$(document).ready(function() {
	if($("#sc").val()=="1")
		{
		alert("保存成功！");
		window.location.href=$("#basePath").val()+"page/business/patrol_see.jsp?patrolId="+$("#patrolId").val();
		}
	if($("#sc").val()=="2")
		alert("保存失败！请重新保存。");
	showPatrolInfoByPatrolId();// 显示信息
	
	$("#patrolContent").focus(function() {
		var che = confirmContentCheck();

		$("#patrolContentErr").text("");
	});
	$("#patrolContent").blur(function() {
		confirmContentCheck();
	});
		
});
function confirmContentCheck() {
	var ret = true;
	ret = valueLength("#patrolContent", "#patrolContentErr", "0", "2000","内容不能超过2000个字。");
	
	return ret;
}
var uploadNum=0;
function showPatrolInfoByPatrolId(){
	$.ajax( {
		url : 'showPatrolInfoByPatrolId',
		type : "post",
		dataType : "json",
		data:{'patrol.patrolId':$("#patrolId").val()},
		success : function(data) {
			$("#patrolObject").text(data.list[0][0].patrolObject);
			$("#patrolContent").text(DateIsNull2(data.list[0][0].patrolContent));
		
		}
	});
}

function update_submit(){
	if (!confirmContentCheck()) {

		return false;
	}
}
function gotoPatrol(){
	window.location.href=$("#basePath").val()+"page/business/patrol_show.jsp";
}