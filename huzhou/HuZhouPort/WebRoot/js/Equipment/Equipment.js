$(document).ready(function() {
	showEquipment();
	
});

function showEquipment(){
	$("#layer4_left a").css("color","#77797e");
	$("#layer4_left a:last").css("color","#0260a6");
	$.ajax({
		url:'showEquipment',
		type : "post",
		dataType : "json",
		success : function(data) {
	//alert("success");

	Equipment(data.pagemodel,data.current,data.actionname);
	}
		});
	
}
	function Equipment(pagemodel,current,actionname){
		//var name="测试员";
		//var id="2"; //用户id
		var id=$("#userId").val()
		//alert(id);
		var basePath=$("#basePath").val()
		$(".logclear").empty();
		$(".logclear").remove();
	    $("#fenye").hide();
		
		var listLength = pagemodel.recordsDate.length;
		//alert(listLength);
		if(listLength==0){
			//alert("adajj");
			newTr = $("<tr class='logclear'></tr>");
			newTr.append($("<td  colspan=6>暂无相关数据</td>"));
			$("#EquipmentTable").append(newTr);
		}
		else{
			$("#fenye").show();
		for ( var i = 0; i < listLength; i++) {
			newTr = $("<tr class='logclear'></tr>");
			//<input type="checkbox" name="checkbox" value="${s.logId}">
			newTr.append($("<td>" +pagemodel.recordsDate[i].equipmentUserName+"</td>"));
			newTr.append($("<td>" +pagemodel.recordsDate[i].equipmentKindName+"</td>"));
			newTr.append($("<td >" + GetShortTime1(pagemodel.recordsDate[i].equipmentDate) + "</td>"));
			newTr.append($("<td >" + pagemodel.recordsDate[i].approvalName + "</td>"));
			if(pagemodel.recordsDate[i].approvalResult=="0"||pagemodel.recordsDate[i].approvalResult=="1"){
				newTr.append($("<td >待审批</td>"));
			}
			if(pagemodel.recordsDate[i].approvalResult=="3"){
				newTr.append($("<td >驳回</td>"));
			}
			if(pagemodel.recordsDate[i].approvalResult=="4"){
				newTr.append($("<td >已通过</td>"));
			}
			if(pagemodel.recordsDate[i].approvalID==id&&pagemodel.recordsDate[i].approvalResult!="4"&&pagemodel.recordsDate[i].approvalResult!="3"){
			newTr.append($("<td ><a class='operation' href='"+basePath+"page/Equipment/EquipmentApproval.jsp?equipmentID="+pagemodel.recordsDate[i].equipmentID+"&kind=1'>查看</a>&nbsp;&nbsp;<a class='operation'  href='"+basePath+"page/Equipment/EquipmentApproval.jsp?equipmentID="+pagemodel.recordsDate[i].equipmentID+"&kind=2'>审批</a></td>"));
			}else{
			newTr.append($("<td ><a class='operation'  href='"+basePath+"page/Equipment/EquipmentApproval.jsp?equipmentID="+pagemodel.recordsDate[i].equipmentID+"&kind=1'>查看</a>&nbsp;&nbsp;" +
					"<label style='color:#ccc'>审批</label></td>"));
			}
			$("#EquipmentTable").append(newTr);
		}
		$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye('"+actionname+"')>首页</a>&nbsp;<a onclick=shangyiye("+current+",'"+actionname+"')>上一页</a>&nbsp; <a onclick=xiayiye("+pagemodel.totalPage+","+current+",'"+actionname+"')>下一页</a>&nbsp;  <a onclick=weiye("+pagemodel.totalPage+",'"+actionname+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp; <a onclick=page1('"+actionname+"')>跳转</a>  </p>");
	   // $("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: <font>"+current+"</font> / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp; <a onclick=shouye('"+actionname+"')>首页</a> </p>");
		}
	}
	
	function shouye(actionname){


		$.ajax({
			url: actionname,
			type : "post",
			dataType : "json",
			data:{'page' : 1 },
			success : function(data) {

				Equipment(data.pagemodel,data.current,data.actionname);
		}
		
			});
	}


	function shangyiye(current,actionname){
		//alert(current+"=current123")
		
		if(current>1){
		 current=current-1;
		 }
		
		$.ajax({
			url:actionname,
			type : "post",
			dataType : "json",
			data:{'page' : current },
			success : function(data) {
//		alert("success");
		//alert(data);
				Equipment(data.pagemodel,data.current,data.actionname);
		}
			});
	}

	function xiayiye(totalPage,current,actionname){
		//alert(totalPage+"=totalPage");
		//alert(current+"=current123")
		
		if(current<totalPage){
		 current=current+1;
		 
		 }
		
		$.ajax({
			url:actionname,
			type : "post",
			dataType : "json",
			data:{'page' : current },
			success : function(data) {
//		alert("success");
		//alert(data);
				Equipment(data.pagemodel,data.current,data.actionname);
		}
			});
	}	

	function weiye(totalPage,actionname){
		//alert(totalPage+"=totalPage");
		$.ajax({
			url: actionname,
			type : "post",
			dataType : "json",
			data:{'page' : totalPage },
			success : function(data) {
//		alert("success");
		//alert(data);
				Equipment(data.pagemodel,data.current,data.actionname);
		}
			});
	}		


	function page1(actionname){
		//alert(1);
		var a=document.getElementById('page').value;
		var totalPage1=document.getElementById('LogtotalPage').value;
		if(!isNaN(a)){
		// if(a>totalPage||a<1){
			if(a-totalPage1>0||a<1){
		  alert("请输入1到"+$("#LogtotalPage").val()+"之间的数字")
		  }else{
		  
		     if(parseInt(a)!=a)
		   {
		  alert("请输入整数");
		   }else{
			   $.ajax({
					url: actionname,
					type : "post",
					dataType : "json",
					data:{'page' : a },
					success : function(data) {
			//	alert("success");
				//alert(data);
						Equipment(data.pagemodel,data.current,data.actionname);
				}
					});
			   }}
		}else{
		alert("请输入数字")
		}
}
	
	
	function AwaitingApproval(type){
		$("#layer4_left a").css("color","#77797e");
		type.style.color="#0260a6";
		//alert("123");
		$.ajax({
			url:'AwaitingApprovalEquipment',
			type : "post",
			dataType : "json",
			success : function(data) {
		//alert("success");
		//alert(data);
       Equipment(data.pagemodel,data.current,data.actionname);
		}
			});
	}
	
	
	function select123(){
		$("#layer4_left a").css("color","#77797e");
		$("#layer4_left a:last").css("color","#0260a6");

		var content=document.getElementById('Content').value;
		if(content==""){
			alert("请先输入内容");
		}else{
		
		$.ajax({
			url:'selectEquipment',
			type : "post",
			dataType : "json",
			data:{
				'content' : content
			  },
			success : function(data) {
			//alert("success");
				selectEquipment(data.pagemodel,data.current,data.actionname,content);
		}
			});
		
		}
	}
	
	function selectEquipment(pagemodel,current,actionname,content){
		
		var id=$("#userId").val()
		var basePath=$("#basePath").val()
		$(".logclear").empty();
		$(".logclear").remove();
	    $("#fenye").hide();
		
		var listLength = pagemodel.recordsDate.length;
		//alert(listLength);
		if(listLength==0){
			//alert("adajj");
			newTr = $("<tr class='logclear'></tr>");
			newTr.append($("<td  colspan=6>暂无相关数据</td>"));
			$("#EquipmentTable").append(newTr);
		}
		else{
			$("#fenye").show();
		for ( var i = 0; i < listLength; i++) {
			newTr = $("<tr class='logclear'></tr>");
			//<input type="checkbox" name="checkbox" value="${s.logId}">
			newTr.append($("<td>" +pagemodel.recordsDate[i].equipmentUserName+"</td>"));
			newTr.append($("<td>" +pagemodel.recordsDate[i].equipmentKindName+"</td>"));
			newTr.append($("<td >" + GetShortTime1(pagemodel.recordsDate[i].equipmentDate) + "</td>"));
			newTr.append($("<td >" + pagemodel.recordsDate[i].approvalName + "</td>"));
			if(pagemodel.recordsDate[i].approvalResult=="0"||pagemodel.recordsDate[i].approvalResult=="1"){
				newTr.append($("<td >待审批</td>"));
			}
			if(pagemodel.recordsDate[i].approvalResult=="3"){
				newTr.append($("<td >驳回</td>"));
			}
			if(pagemodel.recordsDate[i].approvalResult=="4"){
				newTr.append($("<td >已通过</td>"));
			}
			if(pagemodel.recordsDate[i].approvalID==id&&pagemodel.recordsDate[i].approvalResult!="4"&&pagemodel.recordsDate[i].approvalResult!="3"){
			newTr.append($("<td ><a class='operation' href='"+basePath+"page/LeaveAndOvertime/LeaveAndOvertimeShow.jsp?leaveOrOtID="+pagemodel.recordsDate[i].leaveOrOtID+"'>查看</a>&nbsp;&nbsp;<a class='operation'  href='"+basePath+"page/LeaveAndOvertime/LeaveAndOvertimeApproval.jsp?leaveOrOtID="+pagemodel.recordsDate[i].leaveOrOtID+"'>审批</a></td>"));
			}else{
			newTr.append($("<td ><a class='operation'  href='"+basePath+"page/LeaveAndOvertime/LeaveAndOvertimeShow.jsp?leaveOrOtID="+pagemodel.recordsDate[i].leaveOrOtID+"'>查看</a>&nbsp;&nbsp;" +
					"<label style='color:#ccc'>审批</label></td>"));
			}
			$("#EquipmentTable").append(newTr);
		}

		$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye1('"+actionname+"','"+content+"')>首页</a>&nbsp;<a onclick=shangyiye1("+current+",'"+actionname+"','"+content+"')>上一页</a>&nbsp; <a onclick=xiayiye1("+pagemodel.totalPage+","+current+",'"+actionname+"','"+content+"')>下一页</a>&nbsp;  <a onclick=weiye1("+pagemodel.totalPage+",'"+actionname+"','"+content+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;<a   onclick=page2('"+actionname+"','"+content+"')>跳转</a> </p>");

		}
	}
	
	
	function shouye1(actionname,content){

		$.ajax({
			url : actionname,
			type : "post",
			dataType : "json",
			data :{'page' : 1 ,
			      'content'  : content },
			
			success : function(data) {

				selectEquipment(data.pagemodel,data.current,actionname,content);
		}

			});
		}


		function shangyiye1(current,actionname,content){
			//alert(current+"=current123")
			
			if(current>1){
			 current=current-1;
			 
			 }
			
			$.ajax({
				url:actionname,
				type : "post",
				dataType : "json",
				data:{'page' : current,
				      'content'  : content },
				success : function(data) {
//			alert("success");
			//alert(data);
					selectEquipment(data.pagemodel,data.current,actionname,content);
			}
				});
		}

		function xiayiye1(totalPage,current,actionname,content){
			//alert(totalPage+"=totalPage");
			//alert(current+"=current123")
			
			if(current<totalPage){
			 current=current+1;
			 
			 }
			
			$.ajax({
				url:actionname,
				type : "post",
				dataType : "json",
				data:{'page' : current,
				      'content'  : content  },
				success : function(data) {
//			alert("success");
			//alert(data);
					selectEquipment(data.pagemodel,data.current,actionname,content);
			}
				});
		}	

		function weiye1(totalPage,actionname,content){
			//alert(totalPage+"=totalPage");
			$.ajax({
				url: actionname,
				type : "post",
				dataType : "json",
				data:{'page' : totalPage ,
				      'content'  : content },
				success : function(data) {
//			alert("success");
			//alert(data);
					selectEquipment(data.pagemodel,data.current,actionname,content);
			}
				});
		}		


		function page2(actionname,content){
			//alert(1);
			var a=document.getElementById('page').value;
			var totalPage1=document.getElementById('LogtotalPage').value;
			if(!isNaN(a)){
			// if(a>totalPage||a<1){
				if(a-totalPage1>0||a<1){
			  alert("请输入1到"+$("#LogtotalPage").val()+"之间的数字")
			  }else{
			  
			     if(parseInt(a)!=a)
			   {
			  alert("请输入整数");
			   }else{
				   $.ajax({
						url: actionname,
						type : "post",
						dataType : "json",
						data:{'page' : a ,
						      'content'  : content },
						success : function(data) {
				//	alert("success");
					//alert(data);
							selectEquipment(data.pagemodel,data.current,actionname,content);
					}
						});
				   
				   
				   }}
			}else{
			alert("请输入数字")
			}


			}

	