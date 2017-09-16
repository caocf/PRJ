$(document).ready(function() {
  WharfName();
	});
function WharfName(){
$.ajax( {
	url : 'wharfBinDingWharfName',
	type : "post",
	dataType : "json",
	success : function(data) {
	//alert("show");
		showWharfName(data.pagemodel,data.current);
	}
});
}

function showWharfName(pagemodel,current){
	$(".addDiv").remove();
	$(".addDiv").empty();
	$(".fixedTermDataItem_table").remove();
	$(".fixedTermDataItem_table").empty();
	var listLength=pagemodel.recordsDate.length;
	if(listLength==0){
		alert("暂无相关数据");	
    }else{
    	for ( var i = 0; i < listLength; i++) {
             var newDiv = $("<div class='addDiv' id='fixedTermData_div_" + i+ "'></div>");
             
             var newTable = $("<table id='fixedTermData_table_"
     				+ i
     				+ "' class='fixedTermData_table' onclick=showWharfWork('"
     				+ pagemodel.recordsDate[i].wharfNumber + "','fixedTermData_div_"
     				+ i + "','state_"+i+"')><col width='18px'/><col width='150px'/><col width='150px'/><col width='200px'/></table>")
    		
     	  var newTr = $("<tr class='addTr' id='fixedTermData_tr_" + i + "'></tr>");
         	newTr.append($("<td><input type='checkbox' id='fixedTermCheckBox_"
					+ i
					+ "' onclick='jogefixedTermCheckBox(this.id)' class='fixedTermCheckBox' value='"
					+ pagemodel.recordsDate[i].wharfNumber +","+pagemodel.recordsDate[i].wharfWorkNorm+ "'></td>"));
         	
         	newTr.append($("<td>码头名称："
    				+ pagemodel.recordsDate[i].wharfNum + "&nbsp;</td>"));
         	newTr.append($("<td>码头编号："
    				+ pagemodel.recordsDate[i].wharfNumber + "&nbsp;</td>"));
         	if(pagemodel.recordsDate[i].wharfWorkNorm==""||pagemodel.recordsDate[i].wharfWorkNorm==null){
         	newTr.append($("<td>码头作业定量：未设定"
    				 + "&nbsp;</td>"));
         	}else{
         	newTr.append($("<td >码头作业定量："
    				+ pagemodel.recordsDate[i].wharfWorkNorm + "&nbsp;(剩余量:"+pagemodel.recordsDate[i].wharfWorkSurplus+")</td>")); 	
         	}
         	/*newTr.append($("<td  width='50px' id='state_" + i
					+ "' class='ingState'>"
					+ "&nbsp;</td>"));*/
        	newTable.append(newTr);
    		newDiv.append(newTable);
    		$("#showFixedTermReport").append(newDiv);

    	}
    	
    	$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye()>首页</a>&nbsp;<a onclick=shangyiye("+current+")>上一页</a>&nbsp; <a onclick=xiayiye("+pagemodel.totalPage+","+current+")>下一页</a>&nbsp;  <a onclick=weiye("+pagemodel.totalPage+")>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/> &nbsp;<a onclick=page1()>跳转</a></p>");
    }
}

function jogefixedTermCheckBox(id) {
	var a = $("#" + id).attr("checked");
	$(".fixedTermCheckBox").attr("checked", false);
	$("#" + id).attr("checked", a);
}


function shouye(){


	$.ajax({
		url: 'wharfBinDingWharfName',
		type : "post",
		dataType : "json",
		data:{'page' : 1 },
		success : function(data) {

			showWharfName(data.pagemodel,data.current);
	}
	
		});
}


function shangyiye(current){
	//alert(current+"=current123")
	
	if(current>1){
	 current=current-1;
	 
	 }
	
	$.ajax({
		url:'wharfBinDingWharfName',
		type : "post",
		dataType : "json",
		data:{'page' : current },
		success : function(data) {
//	alert("success");
	//alert(data);
			showWharfName(data.pagemodel,data.current);
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
		url:'wharfBinDingWharfName',
		type : "post",
		dataType : "json",
		data:{'page' : current },
		success : function(data) {
//	alert("success");
	//alert(data);
			showWharfName(data.pagemodel,data.current);
	}
		});
}	

function weiye(totalPage){
	//alert(totalPage+"=totalPage");
	$.ajax({
		url: 'wharfBinDingWharfName',
		type : "post",
		dataType : "json",
		data:{'page' : totalPage },
		success : function(data) {
//	alert("success");
	//alert(data);
			showWharfName(data.pagemodel,data.current);
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
				url: 'wharfBinDingWharfName',
				type : "post",
				dataType : "json",
				data:{'page' : a },
				success : function(data) {
		//	alert("success");
			//alert(data);
					showWharfName(data.pagemodel,data.current);
			}
				});
		   
		   
		   }}
	}else{
	alert("请输入数字")
	}


}

function selectwharfname(){
	var content=document.getElementById('content').value;
	if(content==""){
		alert("请先输入内容");
	}else{
		$.ajax({
			url:'selectwharfBinDingWharfName',
			type:"post",
			dataType: "json",
			data:{'content' : content},
			success :function(data){
				//alert("success");
				selectWharfName(data.pagemodel,data.current,content)
			} 
		});
		
    }
}



function selectWharfName(pagemodel,current,content){
	$(".addDiv").remove();
	$(".addDiv").empty();
	$(".fixedTermDataItem_table").remove();
	$(".fixedTermDataItem_table").empty();
	var listLength=pagemodel.recordsDate.length;
	if(listLength==0){
		 $("#fenye").hide();
		 alert("暂无相关数据");	
    }else{$("#fenye").show();
    	for ( var i = 0; i < listLength; i++) {
             var newDiv = $("<div class='addDiv' id='fixedTermData_div_" + i+ "'></div>");
             
             var newTable = $("<table id='fixedTermData_table_"
     				+ i
     				+ "' class='fixedTermData_table' onclick=showWharfWork('"
     				+ pagemodel.recordsDate[i].wharfNumber + "','fixedTermData_div_"
     				+ i + "','state_"+i+"')></table>")
    		
     	  var newTr = $("<tr class='addTr' id='fixedTermData_tr_" + i + "'></tr>");
         	newTr.append($("<td width='18px'><input type='checkbox' id='fixedTermCheckBox_"
					+ i
					+ "' onclick='jogefixedTermCheckBox(this.id)' class='fixedTermCheckBox' value='"
					+ pagemodel.recordsDate[i].wharfNumber +","+pagemodel.recordsDate[i].wharfWorkNorm+ "'></td>"));
         	
         newTr.append($("<td width='250px'>码头名称："
    				+ pagemodel.recordsDate[i].wharfNum + "&nbsp;</td>"));
         	newTr.append($("<td width='150px'>码头编号："
    				+ pagemodel.recordsDate[i].wharfNumber + "&nbsp;</td>"));
        	if(pagemodel.recordsDate[i].wharfWorkNorm==""||pagemodel.recordsDate[i].wharfWorkNorm==null){
             	newTr.append($("<td width='150px'>码头作业定量：未设定"
        				 + "&nbsp;</td>"));
             	}else{
             	newTr.append($("<td width='150px'>码头作业定量："
        				+ pagemodel.recordsDate[i].wharfWorkNorm + "&nbsp;</td>")); 	
             	}
         	
         	newTr.append($("<td  width='30px' id='state_" + i
					+ "' class='ingState'>"
					+ "&nbsp;</td>"));
        	newTable.append(newTr);
    		newDiv.append(newTable);
    		$("#showFixedTermReport").append(newDiv);

    	}
    	
    	$("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye1('"+content+"')>首页</a>&nbsp;<a onclick=shangyiye1("+current+",'"+content+"')>上一页</a>&nbsp; <a onclick=xiayiye1("+pagemodel.totalPage+","+current+",'"+content+"')>下一页</a>&nbsp;  <a onclick=weiye1("+pagemodel.totalPage+",'"+content+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;<a onclick=page2('"+content+"')>跳转</a> </p>");
    	}
}


function shouye1(content){

	$.ajax({
		url : 'selectwharfBinDingWharfName',
		type : "post",
		dataType : "json",
		data :{'page' : 1 ,
		      'content'  : content },
		
		success : function(data) {

			selectWharfName(data.pagemodel,data.current,content)
	}

		});
	}
function shangyiye1(current,content){
	//alert(current+"=current123")
	
	if(current>1){
	 current=current-1;
	 
	 }
	
	$.ajax({
		url : 'selectwharfBinDingWharfName',
		type : "post",
		dataType : "json",
		data:{'page' : current,
		      'content'  : content },
		success : function(data) {

			selectWharfName(data.pagemodel,data.current,content);
	}
		});
}

function xiayiye1(totalPage,current,content){

	if(current<totalPage){
	 current=current+1;
	 
	 }
	
	$.ajax({
		url:'selectwharfBinDingWharfName',
		type : "post",
		dataType : "json",
		data:{'page' : current,
		      'content'  : content  },
		success : function(data) {
//	alert("success");
	//alert(data);
			selectWharfName(data.pagemodel,data.current,content);
	}
		});
}	

function weiye1(totalPage,content){
	//alert(totalPage+"=totalPage");
	$.ajax({
		url: 'selectwharfBinDingWharfName',
		type : "post",
		dataType : "json",
		data:{'page' : totalPage ,
		      'content'  : content },
		success : function(data) {
//	alert("success");
	//alert(data);
			selectWharfName(data.pagemodel,data.current,content);
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
				url: 'selectwharfBinDingWharfName',
				type : "post",
				dataType : "json",
				data:{'page' : a ,
				      'content'  : content },
				success : function(data) {
					selectWharfName(data.pagemodel,data.current,content);
			}
				});
		   
		   
		   }}
	}else{
	alert("请输入数字")
	}
}







function showWharfWork(wharfid,id,state){
	$(".ingState").css("background","url('image/electric/to_left.png') no-repeat 75 center");
	
    if($("#fixedTermDataItem_table_" + id).attr("class")){
		$("#fixedTermDataItem_table_" + id).remove();
	$("#fixedTermDataItem_table_" + id).empty();
		return;
	}
    
	$("#"+state).css("background","url('image/electric/to_bottom.png') no-repeat 75 center");
	$.ajax( {
		url : 'showWharfWork',
		type : "post",
		dataType : "json",
		data : {
			'wharfworkid' : wharfid
		},
		success : function(data) {
			appendFixedTermDataById(data.pagemodel.recordsDate, data.pagemodel.totalPage,id,wharfid)
		}
	});

}

var page=1;
function appendFixedTermDataById(list, totalPage,id,wharfid) {
	page=1
	var basePath=$("#basePath").val()
	
	$(".fixedTermDataItem_table").remove();
	$(".fixedTermDataItem_table").empty();
	var newTable = $("<table id='fixedTermDataItem_table_" + id
			+ "' class='fixedTermDataItem_table'></table>");
	var newTr = $("<tr class='addTrItem'></tr>");
	newTr.append($("<th width='100px'>船舶名称</th>"));
	newTr.append($("<th width='100px'>起运港</th>"));
	newTr.append($("<th width='100px'>目的港</th>"));
	newTr.append($("<th width='120px'>申报时间</th>"));
	newTr.append($("<th width='90px'>货物</th>"));
	newTr.append($("<th width='80px'>进出港标记</th>"));
	newTr.append($("<th width='50px'>操作</th>"));
	newTable.append(newTr);
	
	if(list.length==0){
    	newTr = $("<tr class='addTrItem'></tr>");
		newTr.append($("<td colspan=7>暂无相关数据</td>"));
		newTable.append(newTr);
    }else{
	
	for ( var i = 0; i < list.length; i++) {
		newTr = $("<tr class='addTrItem'></tr>");
		newTr.append("<td>"+list[i].shipName+"</td>");
		newTr.append("<td>"+list[i].startingPortName+"</td>");
		newTr.append("<td>"+list[i].arrivalPortName+"</td>");
		newTr.append("<td>"+GetShortTime1(list[i].declareTime)+"</td>");
		newTr.append("<td>"+list[i].cargoTypes+"</td>");
		if(list[i].portMart=="1"){
    		newTr.append("<td>进港</td>");
    	}else{
    		newTr.append("<td>出港</td>");
    	}	
		newTr.append("<td><a class='operation' href='"+basePath+"page/wharfwork/wharfworkshow.jsp?ID="+list[i].id+"'>查看</a></td>");
		newTable.append(newTr);
	}
	
	if(page<totalPage){
		newTr = $("<tr class='addTrItem'></tr>");
		newTr.append($("<td  bgcolor='#f6f6f6' class='load'  height='40px' colspan=7  style='text-align:center;' onclick=load('"+wharfid+"')><p class='operation'>加载更多</p></td>"));
		newTable.append(newTr);
	}
	
    }
	$("#" + id).append(newTable);

}


function load(wharfid){
	page=page+1;
	
	$.ajax( {
		url : 'showWharfWork',
		type : "post",
		dataType : "json",
		data : {
			'wharfworkid' : wharfid,
			'page':page
		},
		success : function(data) {
			appendFixedTermDataById1(data.pagemodel.recordsDate, data.pagemodel.totalPage,wharfid)
		}
	});
}


function appendFixedTermDataById1(list, totalPage,wharfid) {
	$(".load").css("display","none");
	
	var basePath=$("#basePath").val()
	for ( var i = 0; i < list.length; i++) {
		newTr = $("<tr class='addTrItem'></tr>");
		newTr.append("<td>"+list[i].shipName+"</td>");
		newTr.append("<td>"+list[i].startingPortName+"</td>");
		newTr.append("<td>"+list[i].arrivalPortName+"</td>");
		newTr.append("<td>"+GetShortTime1(list[i].declareTime)+"</td>");
		newTr.append("<td>"+list[i].cargoTypes+"</td>");
		if(list[i].portMart=="1"){
    		newTr.append("<td>进港</td>");
    	}else{
    		newTr.append("<td>出港</td>");
    	}	
		newTr.append("<td><a class='operation' href='"+basePath+"page/wharfwork/wharfworkshow.jsp?ID="+list[i].id+"'>查看</a></td>");
		 $(".fixedTermDataItem_table").append(newTr);
	}
	
	if(page<totalPage){
		newTr = $("<tr class='addTrItem'></tr>");
		newTr.append($("<td  bgcolor='#f6f6f6' class='load'   colspan=7  style='text-align:center;' onclick=load('"+wharfid+"')><p class='operation'>加载更多</p></td>"));
		 $(".fixedTermDataItem_table").append(newTr);
	}
}

function updatewharfname(){
	var basePath=$("#basePath").val()
	var fixedTemId="";
	var wharfWorkNorm="";
		$(".fixedTermCheckBox:checked").each(function(){
			var string=this.value;
			var strs= new Array(); //定义一数组 
			strs=string.split(","); //字符分割 
			fixedTemId=strs[0];
			wharfWorkNorm=strs[1];
			});
		if(fixedTemId==""){
			alert("请选择要修改的数据");
			return;
		}
		//alert(fixedTemId);
		
		location.href=basePath+"page/wharfwork/wharfNameUpdate.jsp?wharfnameID="+fixedTemId+"&wharfWorkNorm="+wharfWorkNorm;
}