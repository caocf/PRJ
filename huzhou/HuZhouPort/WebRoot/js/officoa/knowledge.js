$(document).ready(function() {
//alert("进入js");
	showKnowLedge();
	
});
function showKnowLedge(){
	//alert("ajax方法");
	$.ajax({
		url:'showKnowLedge',
		type : "post",
		dataType : "json",
		success : function(data) {
	
	//alert(data);
	//LeaveAndOvertime(data.pagemodel,data.current,data.actionname);
	KnowLedge(data.pagemodel,data.current,data.actionname);
	}
		});
}

function KnowLedge(pagemodel,current,actionname){
	var basePath=$("#basePath").val()
	$(".knowledgeclear").empty();
	$(".knowledgeclear").remove();
	$("#fenye").hide();
	var listLength = pagemodel.recordsDate.length;
	
	if(listLength==0){
		//alert("adajj");
		newTr = $("<tr class='knowledgeclear'></tr>");
		newTr.append($("<td colspan=2>暂无相关数据</td>"));
		$("#Knowledge").append(newTr);
	}
	else{
		$("#fenye").show();
	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='knowledgeclear'></tr>");
		//newTr.append($("<td><a href='"+basePath+"page/officoa/Knowledgelist.jsp?kindID="+pagemodel.recordsDate[i].kindID+"' >" + pagemodel.recordsDate[i].kindName + "</a></td>"));
		newTr.append($("<td>"+pagemodel.recordsDate[i].kindName+"</td> <td><a class='operation' href='"+basePath+"page/officoa/Knowledgelist.jsp?kindID="+pagemodel.recordsDate[i].kindID+"' > 查看</a></td>"));
		$("#Knowledge").append(newTr);
		
		
		
		
	}
	
	$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye('"+actionname+"')>首页</a>&nbsp;<a onclick=shangyiye("+current+",'"+actionname+"')>上一页</a>&nbsp; <a onclick=xiayiye("+pagemodel.totalPage+","+current+",'"+actionname+"')>下一页</a>&nbsp;  <a onclick=weiye("+pagemodel.totalPage+",'"+actionname+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/><input type='button' value='跳转' onclick=page1('"+actionname+"') class='freeGo' /> </p>");}
	
}


function shouye(actionname){


	$.ajax({
		url: actionname,
		type : "post",
		dataType : "json",
		data:{'page' : 1 },
		success : function(data) {

			KnowLedge(data.pagemodel,data.current,data.actionname);
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
//	alert("success");
	//alert(data);
			KnowLedge(data.pagemodel,data.current,data.actionname);
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
//	alert("success");
	//alert(data);
			KnowLedge(data.pagemodel,data.current,data.actionname);
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
//	alert("success");
	//alert(data);
			KnowLedge(data.pagemodel,data.current,data.actionname);
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
					KnowLedge(data.pagemodel,data.current,data.actionname);
			}
				});
		   
		   
		   }}
	}else{
	alert("请输入数字")
	}


	}




function select123(){


	var content=document.getElementById('Content').value;
	if(content==""){
		alert("请先输入内容");
	}else{
	
	$.ajax({
		url:'selectKnowLedge',
		type : "post",
		dataType : "json",
		data:{
			'content' : content
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
	var basePath=$("#basePath").val()
		$(".knowledgeclear").empty();
	$(".knowledgeclear").remove();
	$("#fenye").hide();
	var listLength = pagemodel.recordsDate.length;
	
	if(listLength==0){
		//alert("adajj");
		newTr = $("<tr class='knowledgeclear'></tr>");
		newTr.append($("<td colspan=2>暂无相关数据</td>"));
		$("#Knowledge").append(newTr);
	}
	else{
		$("#fenye").show();
	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='knowledgeclear'></tr>");
		newTr.append($("<td>"+pagemodel.recordsDate[i].kindName+"</td><td><a class='operation' href='"+basePath+"page/officoa/Knowledgelist.jsp?kindID="+pagemodel.recordsDate[i].kindID+"' > 查看</a></td>"));
		newTr.append($("<td><a href='"+basePath+"page/officoa/Knowledgelist.jsp?kindID="+pagemodel.recordsDate[i].kindID+"' >" + pagemodel.recordsDate[i].kindName + "</a></td>"));
		$("#Knowledge").append(newTr);
		
		
		
		
	}
	
	$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye1('"+actionname+"','"+content+"')>首页</a>&nbsp;<a onclick=shangyiye1("+current+",'"+actionname+"','"+content+"')>上一页</a>&nbsp; <a onclick=xiayiye1("+pagemodel.totalPage+","+current+",'"+actionname+"','"+content+"')>下一页</a>&nbsp;  <a onclick=weiye1("+pagemodel.totalPage+",'"+actionname+"','"+content+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/><input type='button' value='跳转' onclick=page2('"+actionname+"','"+content+"') class='freeGo' /> </p>");

}}
	function shouye1(actionname,content){

		$.ajax({
			url : actionname,
			type : "post",
			dataType : "json",
			data :{'page' : 1 ,
			      'content'  : content },
			
			success : function(data) {

				selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
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
					selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
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
					selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
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
					selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
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
							selectKnowLedge(data.pagemodel,data.current,data.actionname,content);
					}
						});
				   
				   
				   }}
			}else{
			alert("请输入数字")
			}


			}
		
		
		
function addknowledgediv(){
			
			$("#deletelogdiv").show();
			$("#deletelogdiv").dialog( {
				title : '添加知识库类别',
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


function addknowledge(){


	var content=document.getElementById('kindname').value;
	if(content==""){
		alert("请输入知识库类型");
	}else{
	
	$.ajax({
		url:'addknowledge',
		type : "post",
		dataType : "json",
		data:{
			'content' : content
		  },
		success : function(data) {
			var basePath = $("#basePath").val()
			str = basePath + "page/officoa/Knowledge.jsp";
			window.location.href = str;	
		//alert("success");
		

	}
		});
	
	}
}

