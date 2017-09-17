var searchInput="";
var search_gljgdm="";//管理机构代码
$(document).ready(function(){
	//$("#top_text").text("公路站");//top上的显示
	$("#station_select").attr("class","mtop_select");
	showleftGljg();
	showRoadStationInfo('glzxxlist/glzgklist',1);
});
//显示管理机构信息
function showleftGljg(){
	$.ajax({
		url:'gljglist/gljglist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'sfzxcfjg':1
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#left_select_child1").empty();
				var list=data.result.obj;
				var newLb=$("<label class='left_name' onclick=goToThegljgGlz('"+list.nodeId+"','"+list.nodeDesc+"',this,0)>"+list.nodeDesc+"</label>");
				$("#left_no_select1").append(newLb);
				appendToLeftgljg(list.childrenNodes);
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function appendToLeftgljg(list){
	for(var i=0;i<list.length;i++){
		var newLi=$("<li class='left_no_select_li' onclick=goToThegljgGlz('"+list[i].nodeId+"','"+list[i].nodeDesc+"',this,'"+(i+1)+"') ><label style='float:left;width:150px;height:42px;cursor:pointer;'>"+list[i].nodeDesc+"</label></li>");
		$("#left_select_child1").append(newLi);
	}
}
function showRoadStationInfo(actionName,selectedPage){
	searchInput=$("#bridge_info").val();
	var selectvalue="";
	if(searchInput=="公路站名称、代码"){
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
			'gljgdm':search_gljgdm,
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
					printPage(data.result.obj.pages, selectedPage, 'showRoadStationInfo',
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
			//公路站名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].name)+"&nbsp;</td>"));
			//所属管理机构
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].gljgmc)+"&nbsp;</td>"));
			//桥梁座数
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].bridgeCount)+"&nbsp;</td>"));	
			//隧道座数
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].tunnelCount)+"&nbsp;</td>"));	
			//国道（公里）
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].countryRoad)+"&nbsp;</td>"));	
			//省道
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].provinceRoad)+"&nbsp;</td>"));
			//县乡道
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].cityRoad)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeRoadStationDetail('"+list[i].xxxxId+"','"+list[i].stationId+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].xxxxId+"','"+list[i].stationId+"','"+(i+1)+"') >地图</a></td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			//公路站名称
			newTr.append($("<td>"+judgeIsNull(list[i].name)+"&nbsp;</td>"));
			//所属管理机构
			newTr.append($("<td>"+judgeIsNull(list[i].gljgmc)+"&nbsp;</td>"));
			//桥梁座数
			newTr.append($("<td>"+judgeIsNull(list[i].bridgeCount)+"&nbsp;</td>"));	
			//隧道座数
			newTr.append($("<td>"+judgeIsNull(list[i].tunnelCount)+"&nbsp;</td>"));	
			//国道（公里）
			newTr.append($("<td>"+judgeIsNull(list[i].countryRoad)+"&nbsp;</td>"));	
			//省道
			newTr.append($("<td>"+judgeIsNull(list[i].provinceRoad)+"&nbsp;</td>"));
			//县乡道
			newTr.append($("<td>"+judgeIsNull(list[i].cityRoad)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td><a class='Operate' onclick=seeRoadStationDetail('"+list[i].xxxxId+"','"+list[i].stationId+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].xxxxId+"','"+list[i].stationId+"','"+(i+1)+"') >地图</a></td>"));
			
		}
		$(".listTable").append(newTr);
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='8' align='center'>暂无相关数据</td></tr>");
	$(".listTable").append(newTr);
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
		showRoadStationInfo(actionName, pageNo);
	}
}
/**
 * 根据管理机构查看公路站
 */
function goToThegljgGlz(gljgdm,gljgmc,thisop,num){
	$(".left_select_li").attr("class","left_no_select_li");
	$(".left_select").attr("class","left_no_select");
	if(num==0){
		search_gljgdm="";
		$("#left_no_select1").attr("class","left_select");
	}else{
		search_gljgdm=gljgdm;
		thisop.className="left_select_li";
	}
	
	showRoadStationInfo('glzxxlist/glzgklist',1);
}
/**
 * 查询公路站信息
 */
function searchRoadStationInfo(){
	search_gljgdm="";
	$(".left_select_li").attr("class","left_no_select_li");
	$("#left_no_select1").attr("class","left_select");
	showRoadStationInfo('glzxxlist/glzgklist',1);
}
/**
 * 进入公路站详情页面
 * @param bzbm
 */
function seeRoadStationDetail(xxxxId,stationId){
	window.location.href=$("#basePath").val()+"page/roadstation/RoadStationDetail.jsp?xxxxId="+xxxxId+"&stationId="+stationId;
}
/**
 * 在地图显示
 * @param stationId
 */
function showInMap(stationId,num){
	var amount=Number((select_page-1)*10)+Number(num);
	window.open($("#basePath").val()+"page/highway/HighWayStationMap.jsp?stationbh="+stationId+"&amount="+amount,"_blank");
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}