$(document).ready(function() {
	if($("#sc").val()=="1")
		{
		alert("保存成功！");
		window.location.href=$("#basePath").val()+"page/business/illegal_see.jsp?illegalId="+$("#illegalId").val();
		}
	if($("#sc").val()=="2")
		alert("保存失败！请重新保存。");
		showIllegalReasonList();//缘由
		
		$("#illegalContent").focus(function() {
			var che = confirmContentCheck();

			$("#illegalContenterr").text("");
		});
		$("#illegalContent").blur(function() {
			confirmContentCheck();
		});
			
	});
function confirmContentCheck() {
	var ret = true;
	ret = valueLength("#illegalContent", "#illegalContenterr", "0", "2000","内容不能超过2000个字。");
	
	return ret;
}

function showInfoByIllegalId(){
	$.ajax( {
		url : 'showInfoByIllegalId',
		type : "post",
		dataType : "json",
		data:{'illegal.illegalId':$("#illegalId").val()},
		success : function(data) {
			$("#illegalObject").text(data.list[0][0].illegalObject);
			$("#illegalReason").attr("value",data.list[0][0].illegalReason);
			$("#illegalContent").text(DateIsNull2(data.list[0][0].illegalContent));
		}
	});
}

function showIllegalReasonList() {
	$.ajax( {
		url : 'showIllegalReasonList',
		type : "post",
		dataType : "json",
		success : function(data) {
			for ( var i = 0; i < data.list.length; i++)
				document.getElementById("illegalReason").options.add(new Option(
						data.list[i].reasonName,data.list[i].reasonId));
		}
	});

	showInfoByIllegalId();// 显示信息
}
function update_submit(){
	if (!confirmContentCheck()) {

		return false;
	}
}
//返回列表
function gotoIllegal(){
	window.location.href=$("#basePath").val()+"page/business/illegal_show.jsp";
}