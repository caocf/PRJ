$(document).ready(function() {
	showLeaveAndOvertime();

});

function showLeaveAndOvertime() {
	$("#layer4_left a").css("color", "#77797e");
	$("#layer4_left a:last").css("color", "#0260a6");
	$.ajax( {
		url : 'http://120.55.100.184:90/HuZhouPort/showLeaveAndOvertime',
		type : "post",
		dataType : "json",
		success : function(data) {
			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}
	});

}

function LeaveAndOvertime(pagemodel, current, actionname) {
	var id = $("#userId").val();
	var basePath = $("#basePath").val();
	$(".logclear").empty();
	$(".logclear").remove();
	$("#fenye").hide();

	var listLength = pagemodel.recordsDate.length;
	if (listLength == 0) {
		newTr = $("<tr class='logclear'></tr>");
		newTr.append($("<td  colspan=6>暂无相关数据</td>"));
		$("#LeaveAndOvertimeTable").append(newTr);
	} else {
		$("#fenye").show();
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

		$("#fenye")
				.html(
						"<p>总记录:"
								+ pagemodel.total
								+ " &nbsp; 当前页: "
								+ current
								+ " / "
								+ pagemodel.totalPage
								+ "  <input type='hidden' value='"
								+ pagemodel.totalPage
								+ "' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye('"
								+ actionname
								+ "')>首页</a>&nbsp;<a onclick=shangyiye("
								+ current
								+ ",'"
								+ actionname
								+ "')>上一页</a>&nbsp; <a onclick=xiayiye("
								+ pagemodel.totalPage
								+ ","
								+ current
								+ ",'"
								+ actionname
								+ "')>下一页</a>&nbsp;  <a onclick=weiye("
								+ pagemodel.totalPage
								+ ",'"
								+ actionname
								+ "')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp; <a onclick=page1('"
								+ actionname + "')>跳转</a>  </p>");

	}
}

function shouye(actionname) {

	$.ajax( {
		url : actionname,
		type : "post",
		dataType : "json",
		data : {
			'page' : 1
		},
		success : function(data) {

			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}

	});
}

function shangyiye(current, actionname) {
	if (current > 1) {
		current = current - 1;

	}

	$.ajax( {
		url : actionname,
		type : "post",
		dataType : "json",
		data : {
			'page' : current
		},
		success : function(data) {
			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}
	});
}

function xiayiye(totalPage, current, actionname) {
	if (current < totalPage) {
		current = current + 1;

	}

	$.ajax( {
		url : actionname,
		type : "post",
		dataType : "json",
		data : {
			'page' : current
		},
		success : function(data) {
			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}
	});
}

function weiye(totalPage, actionname) {
	$.ajax( {
		url : actionname,
		type : "post",
		dataType : "json",
		data : {
			'page' : totalPage
		},
		success : function(data) {
			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}
	});
}

function page1(actionname) {
	var a = document.getElementById('page').value;
	var totalPage1 = document.getElementById('LogtotalPage').value;
	if (!isNaN(a)) {
		if (a - totalPage1 > 0 || a < 1) {
			alert("请输入1到" + $("#LogtotalPage").val() + "之间的数字")
		} else {

			if (parseInt(a) != a) {
				alert("请输入整数");
			} else {
				$.ajax( {
					url : actionname,
					type : "post",
					dataType : "json",
					data : {
						'page' : a
					},
					success : function(data) {
						LeaveAndOvertime(data.pagemodel, data.current,
								data.actionname);
					}
				});

			}
		}
	} else {
		alert("请输入数字")
	}

}

function AwaitingApproval(type) {
	$("#layer4_left a").css("color", "#77797e");
	type.style.color = "#0260a6";
	var userid = $("#userId").val()
	$.ajax( {
		url : 'AwaitingApproval',
		type : "post",
		dataType : "json",
		data : {
			'userid' : userid
		},
		success : function(data) {
			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}
	});

}

function Overtime(type) {
	$("#layer4_left a").css("color", "#77797e");
	type.style.color = "#0260a6";
	$.ajax( {
		url : 'Overtime',
		type : "post",
		dataType : "json",
		success : function(data) {
			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}
	});

}

function Leave(type) {
	$("#layer4_left a").css("color", "#77797e");
	type.style.color = "#0260a6";
	$.ajax( {
		url : 'Leave',
		type : "post",
		dataType : "json",
		success : function(data) {
			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}
	});

}

function Travel(type) {
	$("#layer4_left a").css("color", "#77797e");
	type.style.color = "#0260a6";
	$.ajax( {
		url : 'Travel',
		type : "post",
		dataType : "json",
		success : function(data) {
			LeaveAndOvertime(data.pagemodel, data.current, data.actionname);
		}
	});

}

function select123() {

	var content = document.getElementById('Content').value;
	if (content == "") {
		alert("请先输入内容");
	} else {

		$.ajax( {
			url : 'selectLeaveAndOvertime',
			type : "post",
			dataType : "json",
			data : {
				'content' : content
			},
			success : function(data) {
				selectLeaveAndOvertime(data.pagemodel, data.current,
						data.actionname, content);
			}
		});

	}
}

function selectLeaveAndOvertime(pagemodel, current, actionname, content) {

	// var name="测试员";
	var id = $("#userId").val()
	var basePath = $("#basePath").val();
	$(".logclear").empty();
	$(".logclear").remove();
	$("#fenye").hide();

	var listLength = pagemodel.recordsDate.length;
	// alert(listLength);
	if (listLength == 0) {
		// alert("adajj");
		newTr = $("<tr class='logclear'></tr>");
		newTr.append($("<td colspan=6>暂无相关数据</td>"));
		$("#LeaveAndOvertimeTable").append(newTr);
	} else {
		$("#fenye").show();
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

			newTr.append($("<td>"
					+ GetShortTime1(pagemodel.recordsDate[i].leaveOrOtDate)
					+ "</td>"));
			newTr.append($("<td>" + pagemodel.recordsDate[i].approvalName
					+ "</td>"));
			newTr.append($("<td>" + pagemodel.recordsDate[i].approvalResult
					+ "</td>"));

			if (pagemodel.recordsDate[i].approvalID == id
					&& pagemodel.recordsDate[i].approvalResult != "通过审批"
					&& pagemodel.recordsDate[i].approvalResult != "驳回") {

				newTr
						.append($("<td><a class='operation' href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeShow.jsp?leaveOrOtID="
								+ pagemodel.recordsDate[i].leaveOrOtID
								+ "'>查看</a> &nbsp;&nbsp; <a class='operation' href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeApproval.jsp?leaveOrOtID="
								+ pagemodel.recordsDate[i].leaveOrOtID
								+ "'>审批</a></td>"));
			} else {
				newTr
						.append($("<td ><a  class='operation'  href='"
								+ basePath
								+ "page/LeaveAndOvertime/LeaveAndOvertimeShow.jsp?leaveOrOtID="
								+ pagemodel.recordsDate[i].leaveOrOtID
								+ "'>查看</a>&nbsp;&nbsp;"
								+ "<label style='color:#ccc'>审批</label></td>"));
			}
			$("#LeaveAndOvertimeTable").append(newTr);
		}

		$("#fenye")
				.html(
						"<p>总记录:"
								+ pagemodel.total
								+ " &nbsp; 当前页: "
								+ current
								+ " / "
								+ pagemodel.totalPage
								+ "  <input type='hidden' value='"
								+ pagemodel.totalPage
								+ "' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye1('"
								+ actionname
								+ "','"
								+ content
								+ "')>首页</a>&nbsp;<a onclick=shangyiye1("
								+ current
								+ ",'"
								+ actionname
								+ "','"
								+ content
								+ "')>上一页</a>&nbsp; <a onclick=xiayiye1("
								+ pagemodel.totalPage
								+ ","
								+ current
								+ ",'"
								+ actionname
								+ "','"
								+ content
								+ "')>下一页</a>&nbsp;  <a onclick=weiye1("
								+ pagemodel.totalPage
								+ ",'"
								+ actionname
								+ "','"
								+ content
								+ "')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;<a   onclick=page2('"
								+ actionname + "','" + content
								+ "')>跳转</a> </p>");

	}

}
function shouye1(actionname, content) {

	$.ajax( {
		url : actionname,
		type : "post",
		dataType : "json",
		data : {
			'page' : 1,
			'content' : content
		},

		success : function(data) {

			selectLeaveAndOvertime(data.pagemodel, data.current, actionname,
					content);
		}

	});
}

function shangyiye1(current, actionname, content) {
	if (current > 1) {
		current = current - 1;

	}

	$.ajax( {
		url : actionname,
		type : "post",
		dataType : "json",
		data : {
			'page' : current,
			'content' : content
		},
		success : function(data) {
			selectLeaveAndOvertime(data.pagemodel, data.current, actionname,
					content);
		}
	});
}

function xiayiye1(totalPage, current, actionname, content) {
	if (current < totalPage) {
		current = current + 1;

	}

	$.ajax( {
		url : actionname,
		type : "post",
		dataType : "json",
		data : {
			'page' : current,
			'content' : content
		},
		success : function(data) {
			selectLeaveAndOvertime(data.pagemodel, data.current, actionname,
					content);
		}
	});
}

function weiye1(totalPage, actionname, content) {
	$.ajax( {
		url : actionname,
		type : "post",
		dataType : "json",
		data : {
			'page' : totalPage,
			'content' : content
		},
		success : function(data) {
			selectLeaveAndOvertime(data.pagemodel, data.current, actionname,
					content);
		}
	});
}

function page2(actionname, content) {
	var a = document.getElementById('page').value;
	var totalPage1 = document.getElementById('LogtotalPage').value;
	if (!isNaN(a)) {
		if (a - totalPage1 > 0 || a < 1) {
			alert("请输入1到" + $("#LogtotalPage").val() + "之间的数字")
		} else {

			if (parseInt(a) != a) {
				alert("请输入整数");
			} else {
				$.ajax( {
					url : actionname,
					type : "post",
					dataType : "json",
					data : {
						'page' : a,
						'content' : content
					},
					success : function(data) {
						selectLeaveAndOvertime(data.pagemodel, data.current,
								actionname, content);
					}
				});

			}
		}
	} else {
		alert("请输入数字")
	}

}

function newLeaveAndOvertime() {
	var kindName = "事假";
	var leaveOrOtUser = "2"
	var leaveOrOtReason = "家里有点事";
	var beginDate = "2014-1-1";
	var endDate = "2014-1-11";
	var approvalID1 = "1";
	var approvalID2 = "3";
	var approvalID3 = "4";
	$.ajax( {
		url : 'newLeaveAndOvertime',
		type : "post",
		dataType : "json",
		data : {
			"leaveOrOtKindbean.kindName" : kindName,
			"leaveOrOtKindbean.leaveOrOtUser" : leaveOrOtUser,
			"leaveOrOtKindbean.leaveOrOtReason" : leaveOrOtReason,
			"leaveOrOtKindbean.beginDate" : beginDate,
			"leaveOrOtKindbean.endDate" : endDate,
			"leaveOrOtKindbean.approvalID1" : approvalID1,
			"leaveOrOtKindbean.approvalID2" : approvalID2,
			"leaveOrOtKindbean.approvalID3" : approvalID3

		},
		success : function(data) {
			alert("success");
		}
	});

}
