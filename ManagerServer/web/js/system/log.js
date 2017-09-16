$(document).ready(function() {
//alert("进入js");
	showLog();
	
});


function showLog(){
//	alert("ajax方法");
	$.ajax({
		url:'pageMedel1',
		type : "post",
		dataType : "json",
		success : function(data) {
	//alert("success");
	//alert(data);
	      Log(data.pagemodel,data.current);
	},
	error : function(XMLHttpRequest) {
	//	alert("error");
		alert($(".error", XMLHttpRequest.responseText).text());
	}
		});
	
	
}

function Log(pagemodel,current){
	$(".logclear").empty();
	$(".logclear").remove();
	//alert(pagemodel);
	//alert("current="+current)
//	alert("1234"+pagemodel.totalPage);
	//$("#logUser").attr("value",pagemodel.totalPage);
	
	
	var listLength = pagemodel.recordsDate.length;
	//alert(listLength)
	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='logclear'></tr>");
		//<input type="checkbox" name="checkbox" value="${s.logId}">
		newTr.append($("<td><input type='checkbox' name='checkbox' value='"+pagemodel.recordsDate[i].logId+"' /></td>"));
		newTr.append($("<td>"+pagemodel.recordsDate[i].logId+"</td>"));
		newTr.append($("<td>" + pagemodel.recordsDate[i].logUser + "</td>"));
		//newTr.append("<td>" + pagemodel.recordsDate[i].logUser + "</td>");
		newTr.append($("<td>" + pagemodel.recordsDate[i].logContent + "</td>"));
		newTr.append($("<td>" + pagemodel.recordsDate[i].styleName + "</td>"));
		if(pagemodel.recordsDate[i].isApp==1){
			newTr.append($("<td>手机端</td>"));	
		}else{
			newTr.append($("<td>电脑端</td>"));	
		}
		newTr.append($("<td>" + GetShortTime1(pagemodel.recordsDate[i].logTime) + "</td>"));
		$("#logTable").append(newTr);
	}
	//alert(pagemodel.total+"abc");
	//alert("555");
	
	//$("#fenye").html("123");


    $("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick='shouye()'>首页</a>&nbsp;<a onclick='shangyiye("+current+")'>上一页</a>&nbsp; <a onclick='xiayiye("+pagemodel.totalPage+","+current+")'>下一页</a>&nbsp;  <a onclick='weiye("+pagemodel.totalPage+")'>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;<a onclick=page1()>跳转</a> </p>");
}

function shouye(){
	$.ajax({
		url:'pageMedel1',
		type : "post",
		dataType : "json",
		data:{'page' : 1 },
		success : function(data) {
	//alert("success");
	//alert(data);
	      Log(data.pagemodel,data.current);
	},
	error : function(XMLHttpRequest) {
	//	alert("error");
		alert($(".error", XMLHttpRequest.responseText).text());
	}
		});
}



	function shangyiye(current){
		//alert(current+"=current123")
		
		if(current>1){
		 current=current-1;
		 
		 }
		
		$.ajax({
			url:'pageMedel1',
			type : "post",
			dataType : "json",
			data:{'page' : current },
			success : function(data) {
	//	alert("success");
		//alert(data);
		      Log(data.pagemodel,data.current);
		},
		error : function(XMLHttpRequest) {
	//		alert("error");
			alert($(".error", XMLHttpRequest.responseText).text());
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
			url:'pageMedel1',
			type : "post",
			dataType : "json",
			data:{'page' : current },
			success : function(data) {
	//	alert("success");
		//alert(data);
		      Log(data.pagemodel,data.current);
		},
		error : function(XMLHttpRequest) {
		//	alert("error");
			alert($(".error", XMLHttpRequest.responseText).text());
		}
			});
}	
	

	function weiye(totalPage){
		//alert(totalPage+"=totalPage");
		$.ajax({
			url:'pageMedel1',
			type : "post",
			dataType : "json",
			data:{'page' : totalPage },
			success : function(data) {
	//	alert("success");
		//alert(data);
		      Log(data.pagemodel,data.current);
		},
		error : function(XMLHttpRequest) {
	//		alert("error");
			alert($(".error", XMLHttpRequest.responseText).text());
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
					url:'pageMedel1',
					type : "post",
					dataType : "json",
					data:{'page' : a },
					success : function(data) {
			//	alert("success");
				//alert(data);
				      Log(data.pagemodel,data.current);
				},
				error : function(XMLHttpRequest) {
			//		alert("error");
					alert($(".error", XMLHttpRequest.responseText).text());
				}
					});
			   
			   
			   }}
		}else{
		alert("请输入数字")
		}


		}

	
	function delete213(){

		var sUserText="";


				var UserItems = document.getElementsByName("checkbox");
				if(UserItems!=null)
				{
					for(i=0;i<UserItems.length;i++)
					{
						if(UserItems[i].checked==true)
						{
							if(sUserText!="")
								sUserText += ",";
							sUserText += UserItems[i].value;
						}
					}
					
					if(sUserText==""){
					alert("请先选中要删除的日志");
					}else{
					if(confirm('是否确定删除日志')){
				
						   $.ajax({
								url:'delete1',
								type : "post",
								dataType : "json",
								data:{'deleteLogid' : sUserText },
								success : function(data) {
						//	alert("success");
							//alert(data);
							      Log(data.pagemodel,data.current);
							},
							error : function(XMLHttpRequest) {
						//		alert("error");
								alert($(".error", XMLHttpRequest.responseText).text());
							}
								});
						
						
						
						
						
						
						
					
					//str=c+"delete1.action?id="+sUserText
					//alert(str);
					//window.location.href=str;
						   }
					}
					
					//alert(sUserText);
					
				}


		}
	
				function chAll(){

                    
					var AllSelect=document.getElementById("checkbox1");

						var aUsers=document.getElementsByName("checkbox");
						
						var i;
						for(i=0;i<aUsers.length;i++)
							aUsers[i].checked=AllSelect.checked;


					}
	
	
				
function select123(){

	var LogUser=document.getElementById('logUser').value;
	var LogContent=document.getElementById('LogContent').value;
	var StyleName=document.getElementById('StyleName').value;
	var LogTime=document.getElementById('LogTime').value;
	//true 是中文  
	if(isChinese(LogTime)){  
		alert("日志时间里请不要输入中文") 
	}else{
		$.ajax({
		url:'selectLog',
		type : "post",
		dataType : "json",
		data:{'logbean.LogUser' : LogUser,
			'logbean.LogContent' : LogContent,
		    'logbean.StyleName'	: StyleName,
		    'logbean.LogTime' : LogTime
		},
		success : function(data) {

	//alert(data);
	//alert(data.logbean)
	      selectLog(data.pagemodel,data.current,LogUser ,LogContent,StyleName,LogTime);
	      $("#deletelogdiv").dialog("close");
	}
		});
	}
}
	
	function  selectLog(pagemodel,current,LogUser ,LogContent,StyleName,LogTime ){
		$(".logclear").empty();
		$(".logclear").remove();
		$("#fenye").hide();
		

		
		
		var listLength = pagemodel.recordsDate.length;
		//alert(listLength);
		if(listLength==0){
			//alert("adajj");
			newTr = $("<tr class='logclear'></tr>");
			newTr.append($("<td colspan=7>暂无相关数据</td>"));
			$("#logTable").append(newTr);
		}
		else{
			$("#fenye").show();
		for ( var i = 0; i < listLength; i++) {
			newTr = $("<tr class='logclear'></tr>");
			newTr.append($("<td><input type='checkbox' name='checkbox' value='"+pagemodel.recordsDate[i].logId+"' /></td>"));
			newTr.append($("<td>"+pagemodel.recordsDate[i].logId+"</td>"));
			
			//newTr.append($("<td><input type='checkbox' name='checkbox' value='"+pagemodel.recordsDate[i].logId+"' />"+pagemodel.recordsDate[i].logId+"</td>"));
			newTr.append($("<td>" + pagemodel.recordsDate[i].logUser + "</td>"));
			//newTr.append("<td>" + pagemodel.recordsDate[i].logUser + "</td>");
			newTr.append($("<td>" + pagemodel.recordsDate[i].logContent + "</td>"));
			newTr.append($("<td>" + pagemodel.recordsDate[i].styleName + "</td>"));
			if(pagemodel.recordsDate[i].isApp==1){
				newTr.append($("<td>手机端</td>"));	
			}else{
				newTr.append($("<td>电脑端</td>"));	
			}
			newTr.append($("<td>" + GetShortTime1(pagemodel.recordsDate[i].logTime) + "</td>"));
			$("#logTable").append(newTr);
		}
		//alert(pagemodel.total+"abc");
		//alert("555");
		
		//$("#fenye").html("123");


	    $("#fenye").html("<p>总记录:"+pagemodel.total+" &nbsp; 当前页: "+current+" / "+pagemodel.totalPage+"  <input type='hidden' value='"+pagemodel.totalPage+"' id='LogtotalPage'/>    &nbsp;  <a onclick=shouye1('"+LogUser+"','"+LogContent+"','"+StyleName+"','"+LogTime+"')>首页</a>&nbsp;<a onclick=shangyiye1("+current+",'"+LogUser+"','"+LogContent+"','"+StyleName+"','"+LogTime+"')>上一页</a>&nbsp; <a onclick=xiayiye1("+pagemodel.totalPage+","+current+",'"+LogUser+"','"+LogContent+"','"+StyleName+"','"+LogTime+"')>下一页</a>&nbsp;  <a onclick=weiye1("+pagemodel.totalPage+",'"+LogUser+"','"+LogContent+"','"+StyleName+"','"+LogTime+"')>尾页</a>  <input type='text' id='page' value='1' style='width:26px'/>&nbsp;<a onclick=page2('"+LogUser+"','"+LogContent+"','"+StyleName+"','"+LogTime+"')>跳转</a> </p>");
	    //("+LogUser+","+LogContent+","+StyleName+","+LogTime+")
		}
		
	}
	

	function shouye1(LogUser,LogContent,StyleName,LogTime){
		//alert("123");
		$.ajax({
			url:'selectLog',
			type : "post",
			dataType : "json",
			data:{'page' : 1 ,
				'logbean.LogUser' : LogUser,
				'logbean.LogContent' : LogContent,
			    'logbean.StyleName'	: StyleName,
			    'logbean.LogTime' : LogTime
			},
			success : function(data) {
		//alert("success");
		//alert(data);
				 selectLog(data.pagemodel,data.current,LogUser ,LogContent,StyleName,LogTime);
		}
		
			});
	}
	
	
	function shangyiye1(current,LogUser,LogContent,StyleName,LogTime){
		//alert(current+"=current123")
		
		if(current>1){
		 current=current-1;
		 
		 }
		
		$.ajax({
			url:'selectLog',
			type : "post",
			dataType : "json",
			data:{'page' : current ,
				'logbean.LogUser' : LogUser,
				'logbean.LogContent' : LogContent,
			    'logbean.StyleName'	: StyleName,
			    'logbean.LogTime' : LogTime
			},
			success : function(data) {
	//	alert("success");
		//alert(data);
				 selectLog(data.pagemodel,data.current,LogUser ,LogContent,StyleName,LogTime);
		}

			});
}
	function xiayiye1(totalPage,current,LogUser,LogContent,StyleName,LogTime){
		//alert(totalPage+"=totalPage");
		//alert(current+"=current123")
		
		if(current<totalPage){
		 current=current+1;
		 
		 }
		
		
			$.ajax({
				url:'selectLog',
				type : "post",
				dataType : "json",
				data:{'page' : current,
					'logbean.LogUser' : LogUser,
					'logbean.LogContent' : LogContent,
				    'logbean.StyleName'	: StyleName,
				    'logbean.LogTime' : LogTime
				},
			success : function(data) {
	//	alert("success");
		//alert(data);
				 selectLog(data.pagemodel,data.current,LogUser ,LogContent,StyleName,LogTime);
		}
		
			});
}	
	

	function weiye1(totalPage,LogUser,LogContent,StyleName,LogTime){
		//alert(totalPage+"=totalPage");
		$.ajax({
			url:'selectLog',
			type : "post",
			dataType : "json",
			data:{'page' : totalPage ,
				'logbean.LogUser' : LogUser,
				'logbean.LogContent' : LogContent,
			    'logbean.StyleName'	: StyleName,
			    'logbean.LogTime' : LogTime
			},
			success : function(data) {
	//	alert("success");
		//alert(data);
				 selectLog(data.pagemodel,data.current,LogUser ,LogContent,StyleName,LogTime);
		}
		
			});
}		
	
	
	function page2(LogUser,LogContent,StyleName,LogTime){
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
			  // alert("123");
			 //  alert(a);
			   $.ajax({
					url:'selectLog',
					type : "post",
					dataType : "json",
					data:{'page' : a ,
						'logbean.LogUser' : LogUser,
						'logbean.LogContent' : LogContent,
					    'logbean.StyleName'	: StyleName,
					    'logbean.LogTime' : LogTime
					},
					success : function(data) {
				//alert("success");
				//alert(data);
						 selectLog(data.pagemodel,data.current,LogUser ,LogContent,StyleName,LogTime);
				}
				
					});
			   
			   
			   }
		     }
		}else{
		alert("请输入数字")
		}


		}		
		
function deletediv(){
	
	$("#deletelogdiv").show();
	$("#deletelogdiv").dialog( {
		title : '高级查询',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
function hiden(){
	$("#deletelogdiv").dialog("close");
}

// 检验是否为汉字
function isChinese(str) {
	var reg = /^[\u4e00-\u9fa5]{1,10}$/;
	if (!reg.test(str)) {
		return false;
	}else{
	return true;
	}
}
	
				
				