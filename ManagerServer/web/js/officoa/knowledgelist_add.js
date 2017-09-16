$(document).ready(function() {
//alert("进入js");
	showKnowLedgeList();
	
	var kind=$("#kind").val();
	if(kind=="1"){
		addknowledgediv();
	}
	
	
});
function showKnowLedgeList(){
	//alert("ajax方法");
	var kindID=$("#kindID").val();
	$.ajax({
		url:'showKnowLedgedian',
		type : "post",
		dataType : "json",
		data: { "kindID": kindID,
			"userid" : $("#LoginUser").val()},
		success : function(data) {
	//alert("success");
	//alert(data);
	//LeaveAndOvertime(data.pagemodel,data.current,data.actionname);
	KnowLedge(data.pagemodel,data.current,data.actionname);
	}
		});
	
	
}

function KnowLedge(pagemodel,current,actionname){
	var kindID=$("#kindID").val()
	var basePath=$("#basePath").val()
	$(".knowledgeclear").empty();
	$(".knowledgeclear").remove();
	$("#fenye").hide();
	var listLength = pagemodel.recordsDate.length;
	
	if(listLength==0){
		//alert("adajj");
		newTr = $("<tr class='knowledgeclear'></tr>");
		newTr.append($("<td colspan=3>暂无相关数据</td>"));
		$("#Knowledge").append(newTr);
	}
	else{
		$("#fenye").show();
	for ( var i = 0; i < listLength; i++) {
		
		newTr = $("<tr class='knowledgeclear'></tr>");
		newTr.append($("<td>"+pagemodel.recordsDate[i].knowledgeName+"</td>"));
		newTr.append($("<td>"+pagemodel.recordsDate[i].knowledgeIndex+"</td>"));
		newTr.append($("<td><a class='operation'  onclick='showdeletekind("+pagemodel.recordsDate[i].knowledgeID+")' >查看</a>&nbsp;<a class='operation' href='"+basePath+"page/officoa/Knowledge_update.jsp?knowledgeID="+pagemodel.recordsDate[i].knowledgeID+"&kindID="+pagemodel.recordsDate[i].partOfKind+"&kindID1="+kindID+"' onclick='updatedeletekind()' >编辑</a>&nbsp;<a class='operation' onclick='knowledgedelete("+pagemodel.recordsDate[i].knowledgeID+")' >删除</a></td>"));
		$("#Knowledge").append(newTr);
		
		
		
		
	}
	
	$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye('"+actionname+"')>首页</a>&nbsp;<a onclick=shangyiye("+current+",'"+actionname+"')>上一页</a>&nbsp; <a onclick=xiayiye("+pagemodel.totalPage+","+current+",'"+actionname+"')>下一页</a>&nbsp;  <a onclick=weiye("+pagemodel.totalPage+",'"+actionname+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;  <a onclick=page1('"+actionname+"')>跳转</a> </p>");}
	
}


function shouye(actionname){

	var kindID=$("#kindID").val()
	$.ajax({
		url: actionname,
		type : "post",
		dataType : "json",
		data:{'page' : 1 ,'kindID' : kindID},
		success : function(data) {

			KnowLedge(data.pagemodel,data.current,data.actionname);
	}
	
		});
}


function shangyiye(current,actionname){
	//alert(current+"=current123")
	var kindID=$("#kindID").val()
	if(current>1){
	 current=current-1;
	 
	 }
	
	$.ajax({
		url:actionname,
		type : "post",
		dataType : "json",
		data:{'page' : current ,'kindID' : kindID},
		success : function(data) {
//	alert("success");
	//alert(data);
			KnowLedge(data.pagemodel,data.current,data.actionname);
	}
		});
}

function xiayiye(totalPage,current,actionname){
	//alert(totalPage+"=totalPage");
	//alert(current+"=current123")
	var kindID=$("#kindID").val()
	if(current<totalPage){
	 current=current+1;
	 
	 }
	
	$.ajax({
		url:actionname,
		type : "post",
		dataType : "json",
		data:{'page' : current ,'kindID' : kindID},
		success : function(data) {
//	alert("success");
	//alert(data);
			KnowLedge(data.pagemodel,data.current,data.actionname);
	}
		});
}	

function weiye(totalPage,actionname){
	var kindID=$("#kindID").val()
	//alert(totalPage+"=totalPage");
	$.ajax({
		url: actionname,
		type : "post",
		dataType : "json",
		data:{'page' : totalPage ,'kindID' : kindID},
		success : function(data) {
//	alert("success");
	//alert(data);
			KnowLedge(data.pagemodel,data.current,data.actionname);
	}
		});
}		


function page1(actionname){
	//alert(1);
	var kindID=$("#kindID").val()
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
				data:{'page' : a,'kindID' : kindID },
				success : function(data) {
		//	alert("success");
			//alert(data);
					KnowLedge(data.pagemodel,data.current,data.actionname);
			}
				});
		   
		   
		   }}
	}else{
	alert("请输入数字")
	}


	}




function selectKnowledgelist(){

	var kindID=$("#kindID").val()
	var content=document.getElementById('Content').value;
	if(content==""){
		alert("请先输入内容");
	}else{
	$.ajax({
		url:'selectKnowLedgedian',
		type : "post",
		dataType : "json",
		data:{
			'content' : content,
			'kindID' : kindID,
			"userid" : $("#LoginUser").val()
		  },
		success : function(data) {
		
	//alert(data);
	//alert(data.logbean)
		//alert(data.actionname);
			selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
	}
		});
	
	}
}



function selectKnowLedge(pagemodel,current,actionname,content){
		$(".knowledgeclear").empty();
	$(".knowledgeclear").remove();
	$("#fenye").hide();
	var listLength = pagemodel.recordsDate.length;
	
	if(listLength==0){
		//alert("adajj");
		newTr = $("<tr class='knowledgeclear'></tr>");
		newTr.append($("<td colspan=3>暂无相关数据</td>"));
		$("#Knowledge").append(newTr);
	}
	else{
		$("#fenye").show();
	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='knowledgeclear'></tr>");
		newTr.append($("<td>"+pagemodel.recordsDate[i].knowledgeName+"</td>"));
		newTr.append($("<td>"+pagemodel.recordsDate[i].knowledgeIndex+"</td>"));
		newTr.append($("<td><a class='operation'  onclick='showdeletekind("+pagemodel.recordsDate[i].knowledgeID+")' >查看</a>&nbsp;<a class='operation' href='"+basePath+"page/officoa/Knowledge_update.jsp?knowledgeID="+pagemodel.recordsDate[i].knowledgeID+"&kindID="+pagemodel.recordsDate[i].partOfKind+"&kindID1="+kindID+"' onclick='updatedeletekind()' >编辑</a>&nbsp;<a class='operation' onclick='knowledgedelete("+pagemodel.recordsDate[i].knowledgeID+")' >删除</a></td>"));
		$("#Knowledge").append(newTr);
		
		
		
		
	}
	
	$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页:"+current+"/ "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye1('"+actionname+"','"+content+"')>首页</a>&nbsp;<a onclick=shangyiye1("+current+",'"+actionname+"','"+content+"')>上一页</a>&nbsp; <a onclick=xiayiye1("+pagemodel.totalPage+","+current+",'"+actionname+"','"+content+"')>下一页</a>&nbsp;  <a onclick=weiye1("+pagemodel.totalPage+",'"+actionname+"','"+content+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;  <a onclick=page2('"+actionname+"','"+content+"') >跳转</a> </p>");

}}
	function shouye1(actionname,content){
		var kindID=$("#kindID").val()
		$.ajax({
			url : actionname,
			type : "post",
			dataType : "json",
			data :{'page' : 1 ,
			      'content'  : content ,'kindID' : kindID},
			
			success : function(data) {

				selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
		}

			});
		}


		function shangyiye1(current,actionname,content){
			//alert(current+"=current123")
			var kindID=$("#kindID").val()
			if(current>1){
			 current=current-1;
			 
			 }
			
			$.ajax({
				url:actionname,
				type : "post",
				dataType : "json",
				data:{'page' : current,
				      'content'  : content,'kindID' : kindID },
				success : function(data) {
//			alert("success");
			//alert(data);
					selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
			}
				});
		}

		function xiayiye1(totalPage,current,actionname,content){
			//alert(totalPage+"=totalPage");
			//alert(current+"=current123")
			var kindID=$("#kindID").val()
			if(current<totalPage){
			 current=current+1;
			 
			 }
			
			$.ajax({
				url:actionname,
				type : "post",
				dataType : "json",
				data:{'page' : current,
				      'content'  : content,'kindID' : kindID  },
				success : function(data) {
//			alert("success");
			//alert(data);
					selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
			}
				});
		}	

		function weiye1(totalPage,actionname,content){
			var kindID=$("#kindID").val()
			$.ajax({
				url: actionname,
				type : "post",
				dataType : "json",
				data:{'page' : totalPage ,
				      'content'  : content ,'kindID' : kindID},
				success : function(data) {
//			alert("success");
			//alert(data);
					selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
			}
				});
		}		


		function page2(actionname,content){
			var kindID=$("#kindID").val()
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
						      'content'  : content ,'kindID' : kindID},
						success : function(data) {
				//	alert("success");
					//alert(data);
							selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
					}
						});
				   
				   
				   }}
			}else{
			alert("请输入数字")
			}
		}
	
function addkonwledge(){
	var kindID=$("#kindID").val();
	if( kindID=="-1"){
		alert("请选择通知公告类别");
	}else{
		$("#kind").attr("value","2");
		var basePath = $("#basePath").val()
		str = basePath + "page/officoa/Knowledge_add.jsp?kindID="+kindID;
		window.location.href = str;	
	}
}	

function knowledgedelete(knowledgeID){
	var kindID=$("#kindID").val();
	
	if(confirm('是否确定删除')){
	$.ajax({
		url:'knowledgeDelete',
		type : "post",
		dataType : "json",
		data:{'knowledgeID' : knowledgeID},
		success : function(data) {
			var basePath = $("#basePath").val()
			str = basePath + "page/officoa/NoticeEdit.jsp?kindID="+kindID;
			window.location.href = str;	
	}
		});
	
}
}

function addknowledgediv(){
	
	$("#deletelogdiv").show();
	$("#deletelogdiv").dialog( {
		title : '添加通知公告类别',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
function hiden(){
	$("#deletelogdiv").dialog("close");
	document.getElementById('kindname').value=="";
}

function showdeletekind(knowledgeID){
	$("#kind").attr("value","2");
	var basePath = $("#basePath").val()
	str = basePath + "page/officoa/NoticeDetail.jsp?knowledgeID="+knowledgeID;
	window.location.href = str;	
}
function updatedeletekind(){
	$("#kind").attr("value","2");
}

		