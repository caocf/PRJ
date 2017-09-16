$(document).ready(function(){
	showCruiseLog();

});

function showCruiseLog(){

	$.ajax({
		url:'showCruiseLog',
		type:"post",
		dataType: "json",
		success :function(data){
			
			CruiseLog(data.pagemodel,data.current);
		} 
	});
}

function CruiseLog(pagemodel,current){
	var basePath=$("#basePath").val()
	$(".clear").empty();
	$(".clear").remove();
	$("#fenye").hide();
	var listLength=pagemodel.recordsDate.length;
	if(listLength==0){
		newTr = $("<tr class='clear'></tr>");
		newTr.append($("<td colspan=5>暂无相关数据</td>"));
		$("#cruiselogTable").append(newTr);
	}else{
		$("#fenye").show();
		for(var i=0;i<listLength;i++){
			newTr=$("<tr class='clear'></tr>");
			newTr.append("<td>"+pagemodel.recordsDate[i].cruiseLogLoaction+"</td>");
			newTr.append("<td>"+pagemodel.recordsDate[i].cruiseLogUserName+"</td>");
			newTr.append("<td>"+GetShortTime1(pagemodel.recordsDate[i].startTime)+"</td>");
			newTr.append("<td>"+GetShortTime1(pagemodel.recordsDate[i].endTime)+"</td>");
			newTr.append("<td><a class='operation' href='"+basePath+"page/cruiselog/cruiselogshow.jsp?ID="+pagemodel.recordsDate[i].cruiseLogID+"'>查看</a></td>");
			 $("#cruiselogTable").append(newTr);
		}
		$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye()>首页</a>&nbsp;<a onclick=shangyiye("+current+")>上一页</a>&nbsp; <a onclick=xiayiye("+pagemodel.totalPage+","+current+")>下一页</a>&nbsp;  <a onclick=weiye("+pagemodel.totalPage+")>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/> &nbsp;<a onclick=page1()>跳转</a></p>");
	}
}

function shouye(){


	$.ajax({
		url: 'showCruiseLog',
		type : "post",
		dataType : "json",
		data:{'page' : 1 },
		success : function(data) {

			CruiseLog(data.pagemodel,data.current);
	}
	
		});
}


function shangyiye(current){
	//alert(current+"=current123")
	
	if(current>1){
	 current=current-1;
	 
	 }
	
	$.ajax({
		url:'showCruiseLog',
		type : "post",
		dataType : "json",
		data:{'page' : current },
		success : function(data) {
//	alert("success");
	//alert(data);
			CruiseLog(data.pagemodel,data.current);
	}
		});
}

function xiayiye(totalPage,current){
	//alert(totalPage+"=totalPage");
	//alert(current+"=current123")
	
	if(current<totalPage){
	 current=current+1;
	 
	 }
	
	$.ajax({
		url:'showCruiseLog',
		type : "post",
		dataType : "json",
		data:{'page' : current },
		success : function(data) {
//	alert("success");
	//alert(data);
			CruiseLog(data.pagemodel,data.current);
	}
		});
}	

function weiye(totalPage){
	//alert(totalPage+"=totalPage");
	$.ajax({
		url: 'showCruiseLog',
		type : "post",
		dataType : "json",
		data:{'page' : totalPage },
		success : function(data) {
//	alert("success");
	//alert(data);
			CruiseLog(data.pagemodel,data.current);
	}
		});
}		


function page1(){
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
				url: 'showCruiseLog',
				type : "post",
				dataType : "json",
				data:{'page' : a },
				success : function(data) {
		//	alert("success");
			//alert(data);
					CruiseLog(data.pagemodel,data.current);
			}
				});
		   
		   
		   }}
	}else{
	alert("请输入数字")
	}
}

function selectContent(){
	
	var content=document.getElementById('Content').value;
	if (/[~#^$@%&!*\s*]/.test(content)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
		$.ajax({
			url:'selectCruiseLog',
			type:"post",
			dataType: "json",
			data:{'content' : content},
			success :function(data){
				//alert("success");
				selectCruiseLog(data.pagemodel,data.current,content)
			} 
		});
		
    
}

function selectCruiseLog(pagemodel,current,content){
	var basePath=$("#basePath").val()
	$(".clear").empty();
	$(".clear").remove();
	$("#fenye").hide();
	var listLength=pagemodel.recordsDate.length;
	if(listLength==0){
		newTr = $("<tr class='clear'></tr>");
		newTr.append($("<td  colspan=5>暂无相关数据</td>"));
		$("#cruiselogTable").append(newTr);
	}else{
		$("#fenye").show();
		for(var i=0;i<listLength;i++){
			newTr=$("<tr class='clear'></tr>");
			newTr.append("<td>"+pagemodel.recordsDate[i].cruiseLogLoaction+"</td>");
			newTr.append("<td>"+pagemodel.recordsDate[i].cruiseLogUserName+"</td>");
			newTr.append("<td>"+GetShortTime1(pagemodel.recordsDate[i].startTime)+"</td>");
			newTr.append("<td>"+GetShortTime1(pagemodel.recordsDate[i].endTime)+"</td>");
			newTr.append("<td><a class='operation' href='"+basePath+"page/cruiselog/cruiselogshow.jsp?ID="+pagemodel.recordsDate[i].cruiseLogID+"'>查看</a></td>");
			 $("#cruiselogTable").append(newTr);
		}
		$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye1('"+content+"')>首页</a>&nbsp;<a onclick=shangyiye1("+current+",'"+content+"')>上一页</a>&nbsp; <a onclick=xiayiye1("+pagemodel.totalPage+","+current+",'"+content+"')>下一页</a>&nbsp;  <a onclick=weiye1("+pagemodel.totalPage+",'"+content+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;<a onclick=page2('"+content+"')>跳转</a> </p>");
		}
}


function shouye1(content){

	$.ajax({
		url : 'selectCruiseLog',
		type : "post",
		dataType : "json",
		data :{'page' : 1 ,
		      'content'  : content },
		
		success : function(data) {

			selectCruiseLog(data.pagemodel,data.current,content)
	}

		});
	}
function shangyiye1(current,content){
	//alert(current+"=current123")
	
	if(current>1){
	 current=current-1;
	 
	 }
	
	$.ajax({
		url : 'selectCruiseLog',
		type : "post",
		dataType : "json",
		data:{'page' : current,
		      'content'  : content },
		success : function(data) {

			selectCruiseLog(data.pagemodel,data.current,content)
	}
		});
}

function xiayiye1(totalPage,current,content){

	if(current<totalPage){
	 current=current+1;
	 
	 }
	
	$.ajax({
		url:'selectCruiseLog',
		type : "post",
		dataType : "json",
		data:{'page' : current,
		      'content'  : content  },
		success : function(data) {
//	alert("success");
	//alert(data);
			selectCruiseLog(data.pagemodel,data.current,content)
	}
		});
}	

function weiye1(totalPage,content){
	//alert(totalPage+"=totalPage");
	$.ajax({
		url: 'selectCruiseLog',
		type : "post",
		dataType : "json",
		data:{'page' : totalPage ,
		      'content'  : content },
		success : function(data) {
//	alert("success");
	//alert(data);
			selectCruiseLog(data.pagemodel,data.current,content)
	}
		});
}	

function page2(content){
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
				url: 'selectCruiseLog',
				type : "post",
				dataType : "json",
				data:{'page' : a ,
				      'content'  : content },
				success : function(data) {
					selectCruiseLog(data.pagemodel,data.current,content)
			}
				});
		   
		   
		   }}
	}else{
	alert("请输入数字")
	}
}

