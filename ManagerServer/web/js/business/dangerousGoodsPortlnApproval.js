$(document).ready(function() {
	showDangerousGoodsPortlnApproval();
});

function showDangerousGoodsPortlnApproval(){
	var ID=$("#ID").val()
	var kind=$("#kind").val()
	$.ajax({
		url:'showDangerousGoodsPortlnApproval',
		type:"post",
		dataType :"json",
		data:{"declareID" : ID},
		success: function(data){
		//alert("success");
		if(kind=="1"){  //1 查看   2是 审批
		$("#DangerousGoodsPortlnApprovalDiv").hide();
		$("#approvaldiv").hide();
		DangerousGoodsPortlnApproval(data.dangerousArrivalDeclareBean);
		}else{
		DangerousGoodsPortlnApproval1(data.dangerousArrivalDeclareBean);
	    }
	}
	})
}

function DangerousGoodsPortlnApproval(dangerousArrivalDeclareBean){
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>船舶名称：</td><td>" +dangerousArrivalDeclareBean.shipName+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>申报时间：</td><td>" +GetShortTime1(dangerousArrivalDeclareBean.declareTime)+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>进港时间：</td><td>" +GetShortTime1(dangerousArrivalDeclareBean.portTime)+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>起运港：</td><td>" +dangerousArrivalDeclareBean.startingPortName+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>目的港：</td><td>" +dangerousArrivalDeclareBean.arrivalPortName+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>货物名称：</td><td>" +dangerousArrivalDeclareBean.cargoTypes+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>危险品等级：</td><td>" +dangerousArrivalDeclareBean.dangerousLevel+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>载重：</td><td>" +dangerousArrivalDeclareBean.carrying+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	if(dangerousArrivalDeclareBean.reviewResult=="0"){
		newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
		newTr.append("<td class='lefttd'>审核结果：</td><td>待审批</td>");
		$("#DangerousGoodsPortlnApproval").append(newTr);
	}else{
		newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
		newTr.append("<td class='lefttd'>审批人员：</td><td>" +dangerousArrivalDeclareBean.reviewUserName+"</td>");
		$("#DangerousGoodsPortlnApproval").append(newTr);
		newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
		if(dangerousArrivalDeclareBean.reviewResult=="3"){
		newTr.append("<td class='lefttd'>审核结果：</td><td>驳回</td>");
		}else{
		newTr.append("<td class='lefttd'>审核结果：</td><td>已通过</td>");
		}
		$("#DangerousGoodsPortlnApproval").append(newTr);
		
		newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
		if(dangerousArrivalDeclareBean.reviewOpinion=="0"||dangerousArrivalDeclareBean.reviewOpinion==null||dangerousArrivalDeclareBean.reviewOpinion==""){
			newTr.append("<td class='lefttd'>审核意见：</td><td>无</td>");
		}else{
		newTr.append("<td class='lefttd'>审核意见：</td><td>"+dangerousArrivalDeclareBean.reviewOpinion+"</td>");
		}
		$("#DangerousGoodsPortlnApproval").append(newTr);
		
		newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
		newTr.append("<td class='lefttd'>审核时间：</td><td>"+GetShortTime1(dangerousArrivalDeclareBean.reviewTime)+"</td>");
		$("#DangerousGoodsPortlnApproval").append(newTr);
	}	
}

function DangerousGoodsPortlnApproval1(dangerousArrivalDeclareBean){
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>船舶名称：</td><td>" +dangerousArrivalDeclareBean.shipName+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>申报时间：</td><td>" +GetShortTime1(dangerousArrivalDeclareBean.declareTime)+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>进港时间：</td><td>" +GetShortTime1(dangerousArrivalDeclareBean.portTime)+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>起运港：</td><td>" +dangerousArrivalDeclareBean.startingPortName+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>目的港：</td><td>" +dangerousArrivalDeclareBean.arrivalPortName+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>货物名称：</td><td>" +dangerousArrivalDeclareBean.cargoTypes+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>危险品等级：</td><td>" +dangerousArrivalDeclareBean.dangerousLevel+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
	newTr=$("<tr class='dangerousArrivalDeclareBean'></tr>");
	newTr.append("<td class='lefttd'>载重：</td><td>" +dangerousArrivalDeclareBean.carrying+"</td>");
	$("#DangerousGoodsPortlnApproval").append(newTr);
}

function fanhui(){
	var basePath=$("#basePath").val()
	 str=basePath+"page/business/dangerousGoodsPortln.jsp";
	  window.location.href=str;
	
	
}


function agree(){
	var ID=$("#ID").val() 
	var userid=$("#userId").val()
	var reviewResult = $('input:radio[name="reviewResult"]:checked').val();
	//var approvalOpinion1 = document.getElementById('reviewOpinion').innerHTML;
	var approvalOpinion1 =$("#reviewOpinion").val();
	if(reviewResult=="3"){
	if (approvalOpinion1 == "请输入审批意见") {
		alert("请输入审批意见");
	} else {
	$.ajax({
		url: 'DangerousGoodsPortlnApproval',
		type : "post",
		dataType : "json",
		data : { 'declareID' : ID, 
			'userid' :userid,
			'reviewResult' : reviewResult,
			'reviewOpinion' : approvalOpinion1
		},
		success : function(data) {
			alert("提交成功");
			var basePath=$("#basePath").val()
			str=basePath+"page/business/dangerousGoodsPortln.jsp";
			 window.location.href=str;
		}
	})
}
}else{
	if (approvalOpinion1 == "请输入审批意见") {
		approvalOpinion1="0";
	}
	
	$.ajax({
		url: 'DangerousGoodsPortlnApproval',
		type : "post",
		dataType : "json",
		data : { 'declareID' : ID, 
			'userid' :userid,
			'reviewResult' : reviewResult,
			'reviewOpinion' : approvalOpinion1
		},
		success : function(data) {
			alert("提交成功");
			var basePath=$("#basePath").val()
			str=basePath+"page/business/dangerousGoodsPortln.jsp";
			 window.location.href=str;
		}
	})
	
}



}
function textareabeage(t){
	if(t.value=='请输入审批意见'){t.value='' ; t.style.color="#323944";}
	
	
	
}
function textareaend(t){
	if (t.value ==''){t.value='请输入审批意见'; t.style.color="#ccc"}
}

