$(document).ready(function() {
	showDangerousGoodsJobApproval();
});

function showDangerousGoodsJobApproval(){
	var ID=$("#ID").val()
	var kind=$("#kind").val()
	$.ajax({
		url:'showDangerousGoodsJobApproval',
		type:"post",
		dataType :"json",
		data:{"declareID" : ID},
		success: function(data){
		
		if(kind=="1"){  //1 查看   2是 审批
		$("#DangerousGoodsJobApprovalDiv").hide();
		$("#approvaldiv").hide();
		DangerousGoodsJobApproval(data.dangerousWorkDeclareBean);
		}else{
		DangerousGoodsJobApproval1(data.dangerousWorkDeclareBean);
	    }
	}
	})
}

function DangerousGoodsJobApproval(dangerousWorkDeclareBean){
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>船舶名称：</td><td>" +dangerousWorkDeclareBean.shipName+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>申报时间：</td><td>" +GetShortTime1(dangerousWorkDeclareBean.declareTime)+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>起运港：</td><td>" +dangerousWorkDeclareBean.startingPortName+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>目的港：</td><td>" +dangerousWorkDeclareBean.arrivalPortName+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>货物名称：</td><td>" +dangerousWorkDeclareBean.cargoTypes+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>危险品等级：</td><td>" +dangerousWorkDeclareBean.dangerousLevel+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>作业码头名称：</td><td>" +dangerousWorkDeclareBean.wharfID+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>申请作业时间：</td><td>" +GetShortTime1(dangerousWorkDeclareBean.workTIme)+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>载重：</td><td>" +dangerousWorkDeclareBean.carrying+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	if(dangerousWorkDeclareBean.reviewResult=="0"){
		newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
		newTr.append("<td class='lefttd'>审核结果：</td><td>待审批</td>");
		$("#DangerousGoodsJobApproval").append(newTr);
	}else{
		newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
		newTr.append("<td class='lefttd'>审批人员：</td><td>" +dangerousWorkDeclareBean.reviewUserName+"</td>");
		$("#DangerousGoodsJobApproval").append(newTr);
		newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
		if(dangerousWorkDeclareBean.reviewResult=="3"){
		newTr.append("<td class='lefttd'>审核结果：</td><td>驳回</td>");
		}else{
		newTr.append("<td class='lefttd'>审核结果：</td><td>已通过</td>");
		}
		$("#DangerousGoodsJobApproval").append(newTr);
		
		newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
		if(dangerousWorkDeclareBean.reviewOpinion=="0"||dangerousWorkDeclareBean.reviewOpinion==null||dangerousWorkDeclareBean.reviewOpinion==""){
			newTr.append("<td class='lefttd'>审核意见：</td><td>无</td>");
		}else{
		newTr.append("<td class='lefttd'>审核意见：</td><td>"+dangerousWorkDeclareBean.reviewOpinion+"</td>");
		}
		$("#DangerousGoodsJobApproval").append(newTr);
		
		newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
		newTr.append("<td class='lefttd'>审核时间：</td><td>"+GetShortTime1(dangerousWorkDeclareBean.reviewTime)+"</td>");
		$("#DangerousGoodsJobApproval").append(newTr);
	}	
}

function DangerousGoodsJobApproval1(dangerousWorkDeclareBean){
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>船舶名称：</td><td>" +dangerousWorkDeclareBean.shipName+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>申报时间：</td><td>" +GetShortTime1(dangerousWorkDeclareBean.declareTime)+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>起运港：</td><td>" +dangerousWorkDeclareBean.startingPortName+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>目的港：</td><td>" +dangerousWorkDeclareBean.arrivalPortName+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>货物名称：</td><td>" +dangerousWorkDeclareBean.cargoTypes+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>危险品等级：</td><td>" +dangerousWorkDeclareBean.dangerousLevel+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>作业码头名称：</td><td>" +dangerousWorkDeclareBean.wharfID+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>申请作业时间：</td><td>" +GetShortTime1(dangerousWorkDeclareBean.workTIme)+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
	
	newTr=$("<tr class='dangerousGoodsJobApproval'></tr>");
	newTr.append("<td class='lefttd'>载重：</td><td>" +dangerousWorkDeclareBean.carrying+"</td>");
	$("#DangerousGoodsJobApproval").append(newTr);
}

function fanhui(){
	var basePath=$("#basePath").val()
	 str=basePath+"page/business/dangerousGoodsJob.jsp";
	  window.location.href=str;
	
	
}


function agree(){
	var ID=$("#ID").val() 
	//var userid="2";
	var userid=$("#userId").val()
	var reviewResult = $('input:radio[name="reviewResult"]:checked').val();
   // var approvalOpinion1 = document.getElementById('reviewOpinion').innerHTML;
     var approvalOpinion1 =$("#reviewOpinion").val();
	if(reviewResult=="3"){
	if (approvalOpinion1 == "请输入审批意见") {
		alert("请输入审批意见");
	} else {
	$.ajax({
		url: 'DangerousGoodsJobApproval',
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
			str=basePath+"page/business/dangerousGoodsJob.jsp";
			 window.location.href=str;
		}
	})
}
	}else{
		if (approvalOpinion1 == "请输入审批意见") {
			approvalOpinion1="0";
		}
		
		$.ajax({
			url: 'DangerousGoodsJobApproval',
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
				str=basePath+"page/business/dangerousGoodsJob.jsp";
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
