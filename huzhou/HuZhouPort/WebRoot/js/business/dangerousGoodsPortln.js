$(document).ready(function(){
	
	showDangerousGoodsPortln();
});
function showDangerousGoodsPortln(){
	$.ajax({
		url:'showDangerousGoodsPortln',
		type:"post",
		dataType: "json",
		success :function(data){
			
			DangerousGoodsPortln(data.pagemodel,data.current);
		}
		
	});
	
}

function DangerousGoodsPortln(pagemodel,current){
	var basePath=$("#basePath").val()
	$(".clear").empty();
	$(".clear").remove();
    $("#fenye").hide();
    var listLength=pagemodel.recordsDate.length;
    if(listLength==0){
    	newTr = $("<tr class='clear'></tr>");
		newTr.append($("<td colspan=7>暂无相关数据</td>"));
		$("#dangerousGoodsPortlnTable").append(newTr);
    }else{
    	$("#fenye").show();
    	for(var i=0;i<listLength; i++){
    		newTr=$("<tr class='clear'></tr>");
    	newTr.append("<td>"+pagemodel.recordsDate[i].shipName+"</td>");
    	newTr.append("<td>"+pagemodel.recordsDate[i].startingPortName+"</td>");
    	newTr.append("<td>"+pagemodel.recordsDate[i].arrivalPortName+"</td>");
    	newTr.append("<td>"+GetShortTime1(pagemodel.recordsDate[i].declareTime)+"</td>");
    	newTr.append("<td>"+pagemodel.recordsDate[i].cargoTypes+"</td>");
    	if(pagemodel.recordsDate[i].reviewResult=="0"){
    		newTr.append("<td>待审批</td>");
    	}
    	if(pagemodel.recordsDate[i].reviewResult=="3"){
    		newTr.append("<td>驳回</td>");
    	}
         if(pagemodel.recordsDate[i].reviewResult=="4"){
		    newTr.append("<td>已通过</td>");
	    }
        if(pagemodel.recordsDate[i].reviewResult=="0"){
        	newTr.append("<td><a class='operation' href='"+basePath+"page/business/dangerousGoodsPortlnApproval.jsp?ID="+pagemodel.recordsDate[i].declareID+"&kind=1'>查看</a>&nbsp; &nbsp;<a  class='operation' href='"+basePath+"page/business/dangerousGoodsPortlnApproval.jsp?ID="+pagemodel.recordsDate[i].declareID+"&kind=2'>审批</a></td>");
        }else{
        	newTr.append("<td><a class='operation' href='"+basePath+"page/business/dangerousGoodsPortlnApproval.jsp?ID="+pagemodel.recordsDate[i].declareID+"&kind=1'>查看</a>&nbsp; &nbsp;<label style='color:#ccc'>审批</label></td>");
        }
         
       $("#dangerousGoodsPortlnTable").append(newTr);
    }
    	$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye()>首页</a>&nbsp;<a onclick=shangyiye("+current+")>上一页</a>&nbsp; <a onclick=xiayiye("+pagemodel.totalPage+","+current+")>下一页</a>&nbsp;  <a onclick=weiye("+pagemodel.totalPage+")>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/> &nbsp;<a onclick=page1()>跳转</a></p>");
  }
    
}

function shouye(){


	$.ajax({
		url: 'showDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data:{'page' : 1 },
		success : function(data) {

			DangerousGoodsPortln(data.pagemodel,data.current);
	}
	
		});
}


function shangyiye(current){
	//alert(current+"=current123")
	
	if(current>1){
	 current=current-1;
	 
	 }
	
	$.ajax({
		url:'showDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data:{'page' : current },
		success : function(data) {
//	alert("success");
	//alert(data);
			DangerousGoodsPortln(data.pagemodel,data.current);
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
		url:'showDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data:{'page' : current },
		success : function(data) {
//	alert("success");
	//alert(data);
			DangerousGoodsPortln(data.pagemodel,data.current);
	}
		});
}	

function weiye(totalPage){
	//alert(totalPage+"=totalPage");
	$.ajax({
		url: 'showDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data:{'page' : totalPage },
		success : function(data) {
//	alert("success");
	//alert(data);
			DangerousGoodsPortln(data.pagemodel,data.current);
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
				url: 'showDangerousGoodsPortln',
				type : "post",
				dataType : "json",
				data:{'page' : a },
				success : function(data) {
		//	alert("success");
			//alert(data);
					DangerousGoodsPortln(data.pagemodel,data.current);
			}
				});
		   
		   
		   }}
	}else{
	alert("请输入数字")
	}


}


function selectContent(){
	
	var content=document.getElementById('Content').value;
	if(content==""){
		alert("请先输入内容");
	}else{
		$.ajax({
			url:'selectDangerousGoodsPortln',
			type:"post",
			dataType: "json",
			data:{'content' : content},
			success :function(data){
				//alert("success");
				selectDangerousGoodsPortln(data.pagemodel,data.current,content)
			} 
		});
		
    }
}
function selectDangerousGoodsPortln(pagemodel,current,content){
	var basePath=$("#basePath").val()
	$(".clear").empty();
	$(".clear").remove();
    $("#fenye").hide();
    var listLength=pagemodel.recordsDate.length;
    if(listLength==0){
    	newTr = $("<tr class='clear'></tr>");
		newTr.append($("<td colspan=7>暂无相关数据</td>"));
		$("#dangerousGoodsPortlnTable").append(newTr);
    }else{
    	$("#fenye").show();
    	for(var i=0;i<listLength; i++){
    		newTr=$("<tr class='clear'></tr>");
    	newTr.append("<td>"+pagemodel.recordsDate[i].shipName+"</td>");
    	newTr.append("<td>"+pagemodel.recordsDate[i].startingPortName+"</td>");
    	newTr.append("<td>"+pagemodel.recordsDate[i].arrivalPortName+"</td>");
    	newTr.append("<td>"+GetShortTime1(pagemodel.recordsDate[i].declareTime)+"</td>");
    	newTr.append("<td>"+pagemodel.recordsDate[i].cargoTypes+"</td>");
    	if(pagemodel.recordsDate[i].reviewResult=="0"){
    		newTr.append("<td>待审批</td>");
    	}
    	if(pagemodel.recordsDate[i].reviewResult=="3"){
    		newTr.append("<td>驳回</td>");
    	}
         if(pagemodel.recordsDate[i].reviewResult=="4"){
		    newTr.append("<td>已通过</td>");
	    }
         if(pagemodel.recordsDate[i].reviewResult=="0"){
         	newTr.append("<td><a class='operation' href='"+basePath+"page/business/dangerousGoodsPortlnApproval.jsp?ID="+pagemodel.recordsDate[i].declareID+"&kind=1'>查看</a>&nbsp; &nbsp;<a class='operation' href='"+basePath+"page/business/dangerousGoodsPortlnApproval.jsp?ID="+pagemodel.recordsDate[i].declareID+"&kind=2'>审批</a></td>");
         }else{
         	newTr.append("<td><a class='operation' href='"+basePath+"page/business/dangerousGoodsPortlnApproval.jsp?ID="+pagemodel.recordsDate[i].declareID+"&kind=1'>查看</a>&nbsp; &nbsp;<label style='color:#ccc'>审批</label></td>");
         }
       $("#dangerousGoodsPortlnTable").append(newTr);
    }
    	$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye1('"+content+"')>首页</a>&nbsp;<a onclick=shangyiye1("+current+",'"+content+"')>上一页</a>&nbsp; <a onclick=xiayiye1("+pagemodel.totalPage+","+current+",'"+content+"')>下一页</a>&nbsp;  <a onclick=weiye1("+pagemodel.totalPage+",'"+content+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;<a onclick=page2('"+content+"')>跳转</a> </p>");
  }
    
	
}

function shouye1(content){

	$.ajax({
		url : 'selectDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data :{'page' : 1 ,
		      'content'  : content },
		
		success : function(data) {

			selectDangerousGoodsPortln(data.pagemodel,data.current,content)
	}

		});
	}
function shangyiye1(current,content){
	//alert(current+"=current123")
	
	if(current>1){
	 current=current-1;
	 
	 }
	
	$.ajax({
		url : 'selectDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data:{'page' : current,
		      'content'  : content },
		success : function(data) {

			selectDangerousGoodsPortln(data.pagemodel,data.current,content);
	}
		});
}

function xiayiye1(totalPage,current,content){

	if(current<totalPage){
	 current=current+1;
	 
	 }
	
	$.ajax({
		url:'selectDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data:{'page' : current,
		      'content'  : content  },
		success : function(data) {
//	alert("success");
	//alert(data);
			selectDangerousGoodsPortln(data.pagemodel,data.current,content);
	}
		});
}	

function weiye1(totalPage,content){
	//alert(totalPage+"=totalPage");
	$.ajax({
		url: 'selectDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data:{'page' : totalPage ,
		      'content'  : content },
		success : function(data) {
//	alert("success");
	//alert(data);
			selectDangerousGoodsPortln(data.pagemodel,data.current,content);
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
				url: 'selectDangerousGoodsPortln',
				type : "post",
				dataType : "json",
				data:{'page' : a ,
				      'content'  : content },
				success : function(data) {
					selectDangerousGoodsPortln(data.pagemodel,data.current,content);
			}
				});
		   
		   
		   }}
	}else{
	alert("请输入数字")
	}
}


function newDangerousGoodsPortln(){
	
	var shipName="浙F12323";
	var startingPort=2;
	var arrivalPort=1;
	var cargoTypes="泥土";
	var dangerousLevel="一般";
	var load=15;
	
	$.ajax({
		url:'newDangerousGoodsPortln',
		type : "post",
		dataType : "json",
		data: { "dangerousArrivalDeclareBean.shipName": shipName,
			 "dangerousArrivalDeclareBean.startingPort": startingPort,
			 "dangerousArrivalDeclareBean.arrivalPort": arrivalPort,
			 "dangerousArrivalDeclareBean.cargoTypes": cargoTypes,
			 "dangerousArrivalDeclareBean.dangerousLevel": dangerousLevel,
			 "dangerousArrivalDeclareBean.carrying": load
		},
		success : function(data) {
			alert("success");
		}
	});
	}