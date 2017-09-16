$(document).ready(function() {
	showLeaveAndOvertimeApproval();

});

function showLeaveAndOvertimeApproval() {
	var leaveOrOtID = $("#leaveOrOtID").val()

	$.ajax({
		url : 'showLeaveAndOvertimeApproval',
		type : "post",
		dataType : "json",
		data : {
			" leaveOrOtID1" : leaveOrOtID
		},
		success : function(data) {
			LeaveAndOvertimeApproval(data.leaveOrOtKindbean);
		}
	});

}

function LeaveAndOvertimeApproval(leaveOrOtKindbean) {
	
	var appname=leaveOrOtKindbean.approvalID1 ;
	if(leaveOrOtKindbean.approvalID2!="0"){
		appname=appname+","+leaveOrOtKindbean.approvalID2
	}
	if(leaveOrOtKindbean.approvalID3!="0"){
		appname=appname+","+leaveOrOtKindbean.approvalID3
	}
	
	newTr = $("<tr class='leaveOrOtKindbean'></tr>");
	newTr.append($("<td class='lefttd'>申请人：</td><td>" + leaveOrOtKindbean.leaveOrOtUser
			+ "</td>"));
	$("#LeaveAndOvertimeApproval").append(newTr);
	newTr = $("<tr class='leaveOrOtKindbean'></tr>");
	newTr.append($("<td class='lefttd'>审批人：</td><td>" + appname
			+ "</td>"));
	$("#LeaveAndOvertimeApproval").append(newTr);
	
	newTr = $("<tr class='leaveOrOtKindbean'></tr>");
	if (leaveOrOtKindbean.kindType == "1") {
		newTr.append($("<td class='lefttd'>加班/请假/出差：</td><td>请假</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
		newTr = $("<tr class='leaveOrOtKindbean'></tr>");
		newTr.append($("<td class='lefttd'>请假时间：</td><td>" + leaveOrOtKindbean.beginDate
				+ "  — — " + leaveOrOtKindbean.endDate + "</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
		newTr = $("<tr class='leaveOrOtKindbean'></tr>");
		newTr.append($("<td class='lefttd'>请假类型：</td><td>" + leaveOrOtKindbean.kindName
						+ "</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
		newTr = $("<tr class='leaveOrOtKindbean'></tr>");
		newTr.append($("<td class='lefttd'>请假原因：</td><td>" + leaveOrOtKindbean.leaveOrOtReason
				+ "</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
		
	} else if(leaveOrOtKindbean.kindType == "2") {
		newTr.append($("<td class='lefttd'>加班/请假/出差：</td><td>加班</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
		newTr = $("<tr class='leaveOrOtKindbean'></tr>");
		newTr.append($("<td class='lefttd'>加班时间：</td><td>" + leaveOrOtKindbean.beginDate
				+ "  — — " + leaveOrOtKindbean.endDate + "</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
		newTr = $("<tr class='leaveOrOtKindbean'></tr>");
		newTr.append($("<td class='lefttd'>加班原因：</td><td>" + leaveOrOtKindbean.leaveOrOtReason
				+ "</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
	}else{
		newTr.append($("<td class='lefttd'>加班/请假/出差：</td><td>出差</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
		
		newTr = $("<tr class='leaveOrOtKindbean'></tr>");
		newTr.append($("<td class='lefttd'>出差地点：</td><td>" + leaveOrOtKindbean.address+"</td>"));
		
		$("#LeaveAndOvertimeApproval").append(newTr);
		newTr = $("<tr class='leaveOrOtKindbean'></tr>");
		newTr.append($("<td class='lefttd'>出差时间：</td><td>" + leaveOrOtKindbean.beginDate
				+ "  — — " + leaveOrOtKindbean.endDate + "</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);

		newTr = $("<tr class='leaveOrOtKindbean'></tr>");
		newTr.append($("<td class='lefttd'>出差原因：</td><td>" + leaveOrOtKindbean.leaveOrOtReason
				+ "</td>"));
		$("#LeaveAndOvertimeApproval").append(newTr);
	}
	
	
	newTr = $("<tr class='leaveOrOtKindbean'></tr>");
	newTr.append($("<td class='lefttd'>审批状态：</td><td>" + leaveOrOtKindbean.approvalResult
			+ "</td>"));
	$("#LeaveAndOvertimeApproval").append(newTr);
	newTr = $("<tr class='leaveOrOtKindbean'></tr>");
	newTr.append($("<td class='lefttd'>审批意见：</td><td>" + DateIsNull(leaveOrOtKindbean.approvalOpinion1)
			+ "</td>"));
	$("#LeaveAndOvertimeApproval").append(newTr);
	

}

function fanhui() {
	var basePath = $("#basePath").val()
	str = basePath + "page/LeaveAndOvertime/LeaveAndOvertime.jsp";
	window.location.href = str;

}

function agree() {
	var approvalResult1 = $('input:radio[name="approvalResult1"]:checked')
			.val();

	var id=$("#userId").val()
	var leaveOrOtID = $("#leaveOrOtID").val();
	var approvalOpinion1 =$("#approvalOpinion1").val();
	if (approvalOpinion1 == "请输入审批意见") {
		alert("请输入审批意见");
	} else {
		$.ajax({
			url : 'LeaveAndOvertimeApprovalAgree',
			type : "post",
			dataType : "json",
			data : {
				'leaveOrOt.leaveOrOtID' : leaveOrOtID,
				'leaveOrOt.approvalID1' : id,
				'leaveOrOt.approvalResult1' : approvalResult1,
				'leaveOrOt.approvalOpinion1' : approvalOpinion1
			},
			success : function(data) {
				alert("提交成功");
				var basePath = $("#basePath").val()
				str = basePath + "page/LeaveAndOvertime/LeaveAndOvertime.jsp";
				window.location.href = str;

			}
		});

	}
}


function textareabeage(t){
	if(t.value=='请输入审批意见'){t.value='' ; t.style.color="#323944";}
	
	
	
}
function textareaend(t){
	if (t.value ==''){t.value='请输入审批意见'; t.style.color="#ccc"}
}
