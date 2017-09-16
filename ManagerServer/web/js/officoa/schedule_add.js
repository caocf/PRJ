var userList = new Array();//选择的人名
var userIdList = new Array();//选择的ID
$(document).ready(function() {
	if ($("#sc").val() == "1")
	{
		alert("保存成功！");
		window.location.href=$("#basePath").val()+"page/officoa/schedule_show.jsp";
	}
	if ($("#sc").val() == "2")
			alert("保存失败！请重新保存。");
	var t = new Date();
	var date = [ t.getFullYear(), t.getMonth()+1, t.getDate() ].join('-');
	date += ' ' + [ t.getHours(), t.getSeconds(), t.getMinutes() ].join('-');
	$("#sTime").val(date);
	ScheduleKind();// 日程类型
	creatTree();// 显示人员
	$("#listOfUser").attr("value", $("#userId").val());
	userList[0] = $("#listOfUserName").text();
	userIdList[0] = $("#userId").val();
	SelectedUser();
});
// getMaxDate生成客户端本地时间
function getMaxDate() {
	var t = new Date();
	var maxDate = [ t.getFullYear(), t.getMonth() + 4, t.getDate() ].join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate;
}
// getMinDate生成客户端本地时间
function getMinDate() {
	var t = new Date();
	t.setMonth(t.getMonth() + 1);// 最小时间少2个月
	var maxDate = [ t.getFullYear(), t.getMonth(), t.getDate() - 1 ].join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate;
}
// 日程类型
function ScheduleKind() {
	$.ajax( {
		url : 'EventKindList',
		type : "post",
		dataType : "json",
		success : function(data) {
			for ( var i = 0; i < data.list.length; i++)
				document.getElementById("scheduleKind").options.add(new Option(
						data.list[i].scheduleKindName,
						data.list[i].scheduleKindId));
		}
	});
}

function add_submit() {
	if (!validSubmit()) {
		return false;
	}

	if ($("#listOfUser").val() == "") {
		alert("请选择参加人！");
		return false;
	} else
		return true;
}

