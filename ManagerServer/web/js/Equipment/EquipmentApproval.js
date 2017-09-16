$(document).ready(function() {
	showEquipmentApproval();
});

function showEquipmentApproval(){
	var ID=$("#ID").val()
	var kind=$("#kind").val()
	$.ajax({
		url:'showEquipmentApproval',
		type:"post",
		dataType :"json",
		data:{"equipmentID" : ID},
		success: function(data){
		//alert("success");
		if(kind=="1"){  //1 查看   2是 审批
		$("#EquipmentApprovalDiv").hide();
		$("#approvaldiv").hide();
		EquipmentApproval(data.equipmentbean);
		}else{
			
		EquipmentApproval1(data.equipmentbean);
	    }
	}
	});
}

function EquipmentApproval(equipmentbean){
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>申请人：</td><td>" +equipmentbean.equipmentUserName+"</td>");
	$("#EquipmentApproval").append(newTr);
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>申请物品：</td><td>" +equipmentbean.equipmentKindName+"</td>");
	$("#EquipmentApproval").append(newTr);
	
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>申请时间：</td><td>" +GetShortTime1(equipmentbean.equipmentDate)+"</td>");
	$("#EquipmentApproval").append(newTr);
	
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>申请原因：</td><td>" +equipmentbean.equipmentReason+"</td>");
	$("#EquipmentApproval").append(newTr);
	
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>审批人员：</td><td>" +equipmentbean.approvalName+"</td>");
	$("#EquipmentApproval").append(newTr);

	if(equipmentbean.approvalResult=="0"){
		newTr=$("<tr class='equipmentbean'></tr>");
		newTr.append("<td class='lefttd'>审批结果：</td><td>待审批</td>");
		$("#EquipmentApproval").append(newTr);
	}else{

		newTr=$("<tr class='equipmentbean'></tr>");
		if(equipmentbean.approvalResult=="3"){
		newTr.append("<td class='lefttd'>审批结果：</td><td>驳回</td>");
		}else{
		newTr.append("<td class='lefttd'>审批结果：</td><td>已通过</td>");
		}
		$("#EquipmentApproval").append(newTr);
		
		newTr=$("<tr class='equipmentbean'></tr>");
		if(equipmentbean.approvalOpinion=="0"||equipmentbean.approvalOpinion==null){
			newTr.append("<td class='lefttd'>审核意见：</td><td>无</td>");
		}else{
		newTr.append("<td class='lefttd'>审核意见：</td><td>"+equipmentbean.approvalOpinion+"</td>");
		}
		$("#EquipmentApproval").append(newTr);
		
		newTr=$("<tr class='equipmentbean'></tr>");
		newTr.append("<td class='lefttd'>审批时间：</td><td>" +GetShortTime1(equipmentbean.approvalTime)+"</td>");
		$("#EquipmentApproval").append(newTr);
		
	}	
}

function EquipmentApproval1(equipmentbean){
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>申请人：</td><td>" +equipmentbean.equipmentUserName+"</td>");
	$("#EquipmentApproval").append(newTr);
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>申请物品：</td><td>" +equipmentbean.equipmentKindName+"</td>");
	$("#EquipmentApproval").append(newTr);
	
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>申请时间：</td><td>" +GetShortTime1(equipmentbean.equipmentDate)+"</td>");
	$("#EquipmentApproval").append(newTr);
	
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>申请原因：</td><td>" +equipmentbean.equipmentReason+"</td>");
	$("#EquipmentApproval").append(newTr);
	
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>审批人员：</td><td>" +equipmentbean.approvalName+"</td>");
	$("#EquipmentApproval").append(newTr);
	
	newTr=$("<tr class='equipmentbean'></tr>");
	newTr.append("<td class='lefttd'>审批结果：</td><td>待审批</td>");
	$("#EquipmentApproval").append(newTr);
	
}




function agree(){
	var ID=$("#ID").val() 
	var reviewResult = $('input:radio[name="reviewResult"]:checked').val();
	var approvalOpinion1 = document.getElementById('reviewOpinion').innerHTML;
	
	if(reviewResult=="3"){
	if (approvalOpinion1 == "请输入审批意见") {
		alert("请输入审批意见");
	} else {
	$.ajax({
		url: 'EquipmentApproval',
		type : "post",
		dataType : "json",
		data : { 'equipmentbean.equipmentID' : ID, 
			'equipmentbean.approvalResult' : reviewResult,
			'equipmentbean.approvalOpinion' : approvalOpinion1
		},
		success : function(data) {
			alert("提交成功");
			var basePath=$("#basePath").val()
			str=basePath+"page/Equipment/Equipment.jsp";
			 window.location.href=str;
		}
	});
}
}else{
	if (approvalOpinion1 == "请输入审批意见") {
		approvalOpinion1="0";
	}
	
	$.ajax({
		url: 'EquipmentApproval',
		type : "post",
		dataType : "json",
		data : { 'equipmentbean.equipmentID' : ID, 
			'equipmentbean.approvalResult' : reviewResult,
			'equipmentbean.approvalOpinion' : approvalOpinion1
		},
		success : function(data) {
			alert("提交成功");
			var basePath=$("#basePath").val()
			str=basePath+"page/Equipment/Equipment.jsp";
			 window.location.href=str;
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

