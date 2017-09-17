var delId="";
var loadstartTime="";
var loadendTime="";
var loadsearchValue="";
$(document).ready(function(){
	$("#top_text").text("系统设置");//top上的显示
	showLogInfo('czrzmanager/czrzlist',1);
});
//显示用户列表
function showLogInfo(actionName,selectedPage){
	var selectvalue=$("#yhm_info").val();
	if(selectvalue=="用户名"){
		selectvalue="";
	}
	var startTime=$("#beginTime").val();
	var endTime=$("#endTime").val();
	loadstartTime=startTime;
	loadendTime=endTime;
	loadsearchValue=selectvalue;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'selectvalue':selectvalue,
			'endTime':endTime,
			'startTime':startTime,
			'token':token,
			'rows':10,
			'page':selectedPage
		},
		success:function(data){		
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode==-2){
				alert(data.result.resultdesc);
			}else{
			$(".addTr").empty();
			$(".addTr").remove();
			var list=data.result.obj.data;
			if (list == "") {
				TableIsNull();
			} else {
				appendToTable(list);// 跳转页码的方法名称	
				gotoPageMethodName = "gotoPageNo";
				printPage(data.result.obj.pages, selectedPage, 'showLogInfo',
						actionName, gotoPageMethodName, data.result.obj.total);
			}
		}
	 }
	});
}
function appendToTable(list){
	$(".addTr").empty();
	$(".addTr").remove();
	for(var i=0;i<list.length;i++){
		newTr = $("<tr class='addTr'></tr>");
		if(i%2==0){
			newTr.append($("<td style='background:#f9f9f9'><input type='checkbox' name='box' value='"+list[i].rzbh+"' class='selectbox' /></td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].ryxm)+"</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].rzmc)+"</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+HourTimeFormat(list[i].czsj)+"</td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td><input type='checkbox' name='box' value='"+list[i].rzbh+"' class='selectbox' /></td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].ryxm)+"</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].rzmc)+"</td>"));
			newTr.append($("<td>"+HourTimeFormat(list[i].czsj)+"</td>"));
		}
		$("#logTable").append(newTr);
		
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='4' align='center'>暂无相关数据</td></tr>");
	$("#logTable").append(newTr);
	$("#pageDiv").hide();
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").val();
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").prop("value", "1");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)||parseInt(pageNo)==0) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showLogInfo(actionName, pageNo);
	}
}

//删除其他部门用户
var t=0;
function deleteLogList(){
	var userID="";
	var opts=document.getElementsByName("box");
	// 删除其他部门用户

	for(var i=0;i<opts.length;i++){
		if(opts[i].checked==true){
			if(userID==""){
				userID=opts[i].value;
			}else{
				userID+=","+opts[i].value;
			}
	   }
	}
	if(userID.length==0){
		alert("请先选择要删除的记录!");
		return false;
	}
	DeleteLog(userID);
}

function DeleteLog(idlist){
	if(confirm("你确定要删除选中的日志吗？")){
		$.ajax({
			url:'czrzmanager/deleteczrz',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'rzbhlist':idlist
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("删除成功");
					window.location.reload();
				}else{
					alert(text);
				}
			}
		});
	}
}
//导出日志
function loadLogList(){
	window.location.href=$("#basePath").val()+"czrzmanager/exportexcel?token="+token+"&startTime="+loadstartTime+"" +
			"&endTime="+loadendTime+"&selectvalue="+loadsearchValue;
	/*$.ajax({
		url:'czrzmanager/exportexcel',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data!=null){
				 var result = eval('('+data+')');  //转为json对象   
	        	 var code=result.resultcode;
	 			 var message = result.resultdesc;
	 			 alert(message);
			}
		}
	});*/
}
function closeChangeUserDialog(){
	$("#ChangeUserDiv,#fullbg").hide();
}
function closeAddUserDialog(){
	$("#AddUserDiv,#fullbg").hide();
}
function closeResetDialog(){
	$("#ResetPwdDiv,#fullbg").hide();
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}

