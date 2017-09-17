var searchInput="";
var search_xzqhbm="";//行政区划编码
$(document).ready(function(){
	setHeight();
	$("#top_text").text("通阻信息");//top上的显示
	showTzInfo('tzxxlist/tzxxlist',1);
});
function setHeight(){
	var height=document.documentElement.clientHeight-50;
	if(height<680){
		$("#tzHeight").css({"height":"680px"});
	}else{
		$("#tzHeight").css({"height":""+height+"px"});
	}
}
window.onresize=function(){
	var height=document.documentElement.clientHeight-50;
	if(height<680){
		$("#tzHeight").css({"height":"680px"});
	}else{
		$("#tzHeight").css({"height":""+height+"px"});
	}
}
function showTzInfo(actionName,selectedPage){
	searchInput=$("#tz_info").val();
	var selectvalue="";
	if(searchInput=="标题、类别"){
		searchInput="";
	}
	selectvalue=searchInput;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'rows':10,
			'token':token,
			'selectvalue':selectvalue
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showTzInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
	
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		if(i%2==0){
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td style='background:#f9f9f9;text-align:center;'>&nbsp;</td>"));
			//标题
			newTr.append($("<td style='background:#f9f9f9;text-align:left;'>"+judgeIsNull(list[i].title)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9;text-align:center;'>&nbsp;</td>"));
			/*//创建时间
			newTr.append($("<td style='background:#f9f9f9'>"+DateTimeFormat(list[i].createDate)+"&nbsp;</td>"));
			//更新时间
			newTr.append($("<td style='background:#f9f9f9'>"+DateTimeFormat(list[i].updateDate)+"&nbsp;</td>"));*/	
			//发布时间
			newTr.append($("<td style='background:#f9f9f9;text-align:center;'>"+DateTimeFormat(list[i].publishDate)+"&nbsp;</td>"));	
			/*//发布人
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].publishUsername)+"&nbsp;</td>"));*/	
			//类别名称
			newTr.append($("<td style='background:#f9f9f9;text-align:center;'>"+judgeIsNull(list[i].columnName)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td style='background:#f9f9f9;text-align:center;'><a class='Operate' style='margin-right:0;' onclick=seeTzInfoDetail('"+list[i].mainId+"')>详细</a></td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			//标题
			newTr.append($("<td style='text-align:center;'>&nbsp;</td>"));
			newTr.append($("<td style='text-align:left;'>"+judgeIsNull(list[i].title)+"&nbsp;</td>"));
			newTr.append($("<td style='text-align:center;'>&nbsp;</td>"));
			/*//创建时间
			newTr.append($("<td>"+DateTimeFormat(list[i].createDate)+"&nbsp;</td>"));
			//更新时间
			newTr.append($("<td>"+DateTimeFormat(list[i].updateDate)+"&nbsp;</td>"));*/
			//发布时间
			newTr.append($("<td style='text-align:center;'>"+DateTimeFormat(list[i].publishDate)+"&nbsp;</td>"));	
			/*//发布人
			newTr.append($("<td>"+judgeIsNull(list[i].publishUsername)+"&nbsp;</td>"));	*/
			//类别名称
			newTr.append($("<td style='text-align:center;'>"+judgeIsNull(list[i].columnName)+"&nbsp;</td>"));		
			//操作
			newTr.append($("<td style='text-align:center;'><a class='Operate' style='margin-right:0;' onclick=seeTzInfoDetail('"+list[i].mainId+"')>详细</a></td>"));
			
		}
		$(".listTable").append(newTr);
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='7' align='center'>暂无相关数据</td></tr>");
	$(".listTable").append(newTr);
	$("#pageDiv").hide();
}
/**
 * 判断是否为null
 */
function judgeIsNull(value){
	returnValue="";
	if(value==null||value=="null"||value==""){
		return returnValue;
	}else{	
		return value;
	}
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
		showTzInfo(actionName, pageNo);
	}
}

/**
 * 查询通阻信息
 */
function searchTzInfo(){
	showTzInfo('tzxxlist/tzxxlist',1);
}
/**
 * 进入通阻信息详情页面
 * @param bzbm
 */
function seeTzInfoDetail(tzbh){
	//window.location.href=$("#basePath").val()+"page/tzinfo/TzInfoDetail.jsp?tzbh="+tzbh;
	showfullbg();
	showtzInfo(tzbh);
	$("#tzdetailDiv").show();
}
function closetzdetailDiv(){
	$("#fullbg,#tzdetailDiv").hide();
}
function showtzInfo(tzbh){
	$.ajax({
		url:'tzxxlist/tzxxxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'main_id':tzbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				$("#text_content").empty();
				//$("#tzinfo_title").text(list.title);
				$("#main_title").text(list.title);
				$("#push_time").text(DateTimeFormat(list.publishDate));
				$("#create_time").text(DateTimeFormat(list.createDate));
				$("#update_time").text(DateTimeFormat(list.updateDate));
				$("#push_person").text(list.publishUsername);
				$("#text_content").append(list.dataValue);
				$("#tzdetailDiv").css({"margin":"-"+$("#tzdetailDiv").height()/2+"px 0 0 -400px"});
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}
//输入框提示
function TextFocus(id) {
	if (id.value == id.defaultValue) {
		id.value = '';
		id.style.color = '#333333';
	}
}
//输入框提示
function TextBlur(id) {
	if (!id.value) {
		id.value = id.defaultValue;
		id.style.color = '#a3a3a3';
	}
}
