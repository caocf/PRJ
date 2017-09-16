$(document).ready(function() {
	//showLeaveAndOvertime('FindAttedanceByPermission', 1);
	//ListKind(null,1);
});

function CheckList(page)
{
	$.ajax({
		url:'LeaveAndOvertimefinish',
		type:'post',
		dataType:'json',
		data:{
			'region':$("#region").text(),
			'p1':$("#type").text(),
			'p2':page,
			'like':$("#tip").val(),
		},
		success:function(data)
		{
			var list=data.s1;
			//alert(l[1].title);
			$(".datalist").remove();
			pagingmake("", 'getnoticelist', page, data.resultcode);
			$(list).each(function (index, item)
			{
				//alert(item.title);
				$('#list').append
				(
					"<tr class='datalist'>"+
					"<td align='center'><input type='checkbox' class='checkInput' value='"+item.id+"'></td>"+
					"<td align='left'><a class='uniquenews' href='page/noticeforsee/NoticeDetail.jsp?id="+item.id+"'>"+isnull(item.title)+"</a></td>"+
					"<td align='center'>"+isnull(item.updatetime)+"</td>"+
					"<td align='center'><span class='caozuoword' style='color: green;' onclick=\'edit("+item.id+")\'>编辑" +
					"</span>&nbsp;&nbsp;&nbsp;<span style='color:#fa5252;' class='caozuoword' onclick='deleteone("+item.id+","+page+")'>删除</span></td>" +
					"</tr>"
				)
			});
		}
	});
}
// 全部
function showLeaveAndOvertime(actionName, selectedPage) {
	$("#layer4_left a").css("color", "#77797e");
	$("#layer4_left a:last").css("color", "#0260a6");
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'cpage' : selectedPage
		},
		success : function(data) {
			if(data.list!=null){
			gotoPageMethodName = "gotoPageNo";
			printPage(data.totalPage, selectedPage,
					'showLeaveAndOvertime', actionName, gotoPageMethodName,
					data.allTotal);
			LeaveAndOvertimeByAll(data.list);
			}else{
				TableIsNull();
			}
		}
	});

}
function TableIsNull(){
	$(".logclear").empty();
	$(".logclear").remove();

		newTr = $("<tr class='logclear'></tr>");
		newTr.append($("<td  colspan=6>暂无相关数据</td>"));
		$("#LeaveAndOvertimeTable").append(newTr);
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showLeaveAndOvertime(actionName, pageNo);
	}
}
function LeaveAndOvertimeByAll(list) {
	var id = $("#userId").val()
	var basePath = $("#basePath").val()
	$(".logclear").empty();
	$(".logclear").remove();

	var listLength = list.length;
	if (listLength == 0) {
		newTr = $("<tr class='logclear'></tr>");
		newTr.append($("<td  colspan=6>暂无相关数据</td>"));
		$("#LeaveAndOvertimeTable").append(newTr);
	} else {

		for ( var i = 0; i < listLength; i++) {
			newTr = $("<tr class='logclear'></tr>");
			newTr.append($("<td>" + list[i][2].name
					+ "</td>"));
			if (list[i][1].kindType == "1") {
				newTr.append("<td >请假</td>");
			} else if (list[i][1].kindType == "2") {
				newTr.append("<td >加班</td>");
			} else {
				newTr.append("<td >出差</td>");
			}
			newTr.append($("<td >"
					+ GetShortTime1(list[i][0].leaveOrOtDate)
					+ "</td>"));
			newTr.append($("<td >" + list[i][3].name
					+ "</td>"));
			var approvalResult=list[i][0].approvalResult;
			if(approvalResult==1){
				newTr.append($("<td >待审批</td>"));
			}else if(approvalResult==2){
				newTr.append($("<td >正在审批</td>"));
			}else if(approvalResult==3){
				newTr.append($("<td >驳回</td>"));
			}else if(approvalResult==4){
				newTr.append($("<td >通过审批</td>"));
			}
			
			if (list[i][0].approvalID1 == id
					&& list[i][0].approvalResult != 3
					&& list[i][0].approvalResult != 4) {
				newTr
						.append($("<td ><a class='operation' href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeShow.jsp?leaveOrOtID="
								+ list[i][0].leaveOrOtID
								+ "'>查看</a>&nbsp;&nbsp;<a class='operation'  href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeApproval.jsp?leaveOrOtID="
								+ list[i][0].leaveOrOtID
								+ "'>审批</a></td>"));
			} else {
				newTr
						.append($("<td ><a class='operation'  href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeShow.jsp?leaveOrOtID="
								+ list[i][0].leaveOrOtID
								+ "'>查看</a>&nbsp;&nbsp;"
								+ "<label style='color:#ccc'>审批</label></td>"));
			}
			$("#LeaveAndOvertimeTable").append(newTr);
		}
	}
}
function LeaveAndOvertime(pagemodel) {
	var id = $("#userId").val()
	var basePath = $("#basePath").val()
	$(".logclear").empty();
	$(".logclear").remove();

	var listLength = pagemodel.recordsDate.length;
	if (listLength == 0) {
		newTr = $("<tr class='logclear'></tr>");
		newTr.append($("<td  colspan=6>暂无相关数据</td>"));
		$("#LeaveAndOvertimeTable").append(newTr);
	} else {

		for ( var i = 0; i < listLength; i++) {
			newTr = $("<tr class='logclear'></tr>");
			newTr.append($("<td>" + pagemodel.recordsDate[i].leaveOrOtUser
					+ "</td>"));
			if (pagemodel.recordsDate[i].kindType == "1") {
				newTr.append("<td >请假</td>");
			} else if (pagemodel.recordsDate[i].kindType == "2") {
				newTr.append("<td >加班</td>");
			} else {
				newTr.append("<td >出差</td>");
			}
			newTr.append($("<td >"
					+ GetShortTime1(pagemodel.recordsDate[i].leaveOrOtDate)
					+ "</td>"));
			newTr.append($("<td >" + pagemodel.recordsDate[i].approvalName
					+ "</td>"));
			newTr.append($("<td >" + pagemodel.recordsDate[i].approvalResult
					+ "</td>"));
			if (pagemodel.recordsDate[i].approvalID == id
					&& pagemodel.recordsDate[i].approvalResult != "通过审批"
					&& pagemodel.recordsDate[i].approvalResult != "驳回") {
				newTr
						.append($("<td ><a class='operation' href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeShow.jsp?leaveOrOtID="
								+ pagemodel.recordsDate[i].leaveOrOtID
								+ "'>查看</a>&nbsp;&nbsp;<a class='operation'  href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeApproval.jsp?leaveOrOtID="
								+ pagemodel.recordsDate[i].leaveOrOtID
								+ "'>审批</a></td>"));
			} else {
				newTr
						.append($("<td ><a class='operation'  href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeShow.jsp?leaveOrOtID="
								+ pagemodel.recordsDate[i].leaveOrOtID
								+ "'>查看</a>&nbsp;&nbsp;"
								+ "<label style='color:#ccc'>审批</label></td>"));
			}
			$("#LeaveAndOvertimeTable").append(newTr);
		}
	}
}
function ListKind(type, kind) {
	if(type!=null){
	$("#layer4_left a").css("color", "#77797e");
	type.style.color = "#0260a6";
	}
	if (kind == 1) {// 由我审批
		AwaitingApproval('AwaitingApproval', 1);
	} else if (kind == 2) {//我的申请
		FindAttedanceByMy('FindAttedanceByMy', 1);
	}

}
// 由我审批
function AwaitingApproval(actionName, selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'userid' : $("#userId").val(),
			'page' : selectedPage
		},
		success : function(data) {
			gotoPageMethodName = "gotoPageNo1";
			printPage(data.pagemodel.totalPage, selectedPage,
					'AwaitingApproval', actionName, gotoPageMethodName,
					data.pagemodel.total);
			LeaveAndOvertime(data.pagemodel);
		}
	});

}
function gotoPageNo1(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		AwaitingApproval(actionName, pageNo);
	}
}
//我的申请
function FindAttedanceByMy(actionName, selectedPage) {

	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'cpage' : selectedPage
		},
		success : function(data) {
			gotoPageMethodName = "gotoPageNo2";
			printPage(data.totalPage, selectedPage,
					'FindAttedanceByMy', actionName, gotoPageMethodName,
					data.allTotal);
			LeaveAndOvertimeByAll(data.list);
		}
	});

}
function gotoPageNo2(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		FindAttedanceByMy(actionName, pageNo);
	}
}

function SearchContent(actionName, selectedPage) {
	$("#layer4_left a").css("color", "#77797e");
	$("#layer4_left a:last").css("color", "#0260a6");
	var content = $('#Content').val();
	if (/[~#^$@%&!*\s*]/.test(content)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'leaveOrOt.leaveOrOtReason' : content,
			'cpage':selectedPage
		},
		success : function(data) {
			if(data.list!=null){
			gotoPageMethodName = "gotoPageNo3";
			LeaveAndOvertimeByAll(data.list);
			printPage(data.totalPage, selectedPage,
					'LeaveAndOverTimeAllByMy', actionName, gotoPageMethodName,
					data.allTotal);
			}else{
				TableIsNull();
			}

		}
	});

}
function gotoPageNo3(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		SearchContent(actionName, pageNo);
	}
}
