$(document).ready(function() {
	if($("#sc").val()=="1")
	{
		alert("保存成功！");
		window.parent.location.href=$("#basePath").val()+"page/business/permit_see.jsp?inspectionId="+$("#inspectionId").val();
	}
	if($("#sc").val()=="2")
	{	
		alert("保存失败！请重新保存。");
	}
	showInspectionInfoByInspectionId();// 显示信息
	
	$("#inspectionContent").focus(function() {
		var che = confirmContentCheck();

		$("#inspectionContentErr").text("");
	});
	$("#inspectionContent").blur(function() {
		confirmContentCheck();
	});
		
});
function confirmContentCheck() {
	var ret = true;
	ret = valueLength("#inspectionContent", "#inspectionContentErr", "0", "2000","内容不能超过2000个字。");
	
	return ret;
}

function showInspectionInfoByInspectionId(){
	$.ajax( {
		url : 'showInspectionInfoByInspectionId',
		type : "post",
		dataType : "json",
		data:{'inspection.inspectionId':$("#inspectionId").val()},
		success : function(data) {
			$("#inspectionObject").text(data.list[0][0].inspectionObject);
			$("#inspectionContent").text(DateIsNull2(data.list[0][0].inspectionContent));
			
		}
	});
}

function update_submit(){
	if (!confirmContentCheck()) {

		return false;
	}
}
function gotoPermit(){
	window.location.href=$("#basePath").val()+"page/business/permit_show.jsp";
}