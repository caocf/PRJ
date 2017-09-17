var markerS="";
var infolist="";
var selectLonlat="";
var selectnum="";
var leftmark=1;
$(document).ready(function(){
	$("#top_text").text("公路地图");
	loadmap();
	if($("#bridgebm").val()!=""&&$("#bridgebm").val()!="null"){
		var page=$("#amount").val()/7;
		if(isNum(page)){
			page=Math.floor(page)+1;
		}
		showBridgeInfoByAmount('qljbxxlist/qlgklist',1);
	}else{
		showBridgeInfo('qljbxxlist/qlgklist',1);
	}
});
function loadmap(){
	maplet = new XMap("highwaymap",3);
	maplet.setMapCenter("120.76042769896,30.773992239582",8);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}
function showBridgeInfoByAmount(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':7,
			'page':selectedPage,
			'selectId':$("#bridgebm").val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".highway_left_middle").empty();
				var list=data.result.obj.data;
				if(list==""){
					NullInfo();
				}else{
					infolist=list;
					appendToTableByAmount(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showBridgeInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
					$("#number").text(data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
/**
 * 显示桥梁信息
 * @param actionName
 * @param selectedPage
 */
function showBridgeInfo(actionName,selectedPage){
	searchInput=$("#search_info").val();
	var selectvalue="";
	if(searchInput=="桥梁名称"){
		searchInput="";
	}
	selectvalue=searchInput;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':7,
			'page':selectedPage,
			'selectvalue':selectvalue
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".highway_left_middle").empty();
				var list=data.result.obj.data;
				if(list==""){
					NullInfo();
				}else{
					infolist=list;
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showBridgeInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
					$("#number").text(data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function appendToTableByAmount(list){
	for(var i=0;i<list.length;i++){
		if(list[i].bzbm==$("#bridgebm").val()){
			var newDiv=$("<div class='highway_left_middle_div_select' id='"+i+"'></div>");
			newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].qlmc)+"</div>");
			newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所属地区:&nbsp;"+judgeIsNull(list[i].xzqh)+"</div>");
			showMarkInMap(i);
			$(".highway_left_middle").append(newDiv);
		}else{
			var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
			newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].qlmc)+"</div>");
			newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所属地区:&nbsp;"+judgeIsNull(list[i].xzqh)+"</div>");
			$(".highway_left_middle").append(newDiv);
		}
	}
	$(".highway_left_bottom").show();
	$(".highway_left_middle_div,.highway_left_middle_div_select").click(function(){
		$(".highway_left_middle_div_select").attr("class","highway_left_middle_div");
		$(this).attr("class","highway_left_middle_div_select");
		showMarkInMap($(this).attr("id"));
	});
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
		newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].qlmc)+"</div>");
		newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所属地区:&nbsp;"+judgeIsNull(list[i].xzqh)+"</div>");
		$(".highway_left_middle").append(newDiv);
	}
	$(".highway_left_bottom").show();
	$(".highway_left_middle_div").click(function(){
		$(".highway_left_middle_div_select").attr("class","highway_left_middle_div");
		$(this).attr("class","highway_left_middle_div_select");
		showMarkInMap($(this).attr("id"));
	});
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
		showBridgeInfo(actionName, pageNo);
	}
}
function showOrhideleft(){
	var width=$(".top_u1").width();
	if(leftmark==1){
		$(".highway_image").attr("src","image/map/arrowright_normal.png");
		$(".highway_image").attr("id","arrowright_");
		$(".image_size").css({"left":"0"});
		$(".highway_left").hide();
		$(".highway_right").css({"margin":"0"});
		leftmark=2;
		$(".highway_right_map").css({"width":""+width+"px"});
		//$(".highway_right_map").css({"width":"100%"});
		loadmap();
		if(selectnum!=""){
			showMarkInMap(selectnum);
		}
	}else{
		$(".highway_image").attr("src","image/map/arrowleft_normal.png");
		$(".highway_image").attr("id","arrowleft_");
		$(".image_size").css({"left":"350px"});
		$(".highway_left").show();
		$(".highway_right").css({"margin":"0 0 0 351px"});
		leftmark=1;
		$(".highway_right_map").css({"width":""+(width-351)+"px"});
		loadmap();
		if(selectnum!=""){
			showMarkInMap(selectnum);
		}
	}
}
function showMarkInMap(num){
	/*if(selectLonlat!=""){
		maplet.removeAllOverlay();
	}*/
	selectnum=num;
	if(conversionType(infolist[num].qlzxzh).indexOf("K")!=-1&&conversionType(infolist[num].qlzxzh).indexOf("+")!=-1){
		$.ajax({
			url:'transferPre/transferPre',
			type:'post',
			dataType:'json',
			data:{
				'stakenames':infolist[num].lxdm+"-"+conversionType(infolist[num].qlzxzh)
			},
			success:function(data){
				var code=data.result.resultcode;
				if(code==1){
					if(data.result.obj!=""){
						var obj=JSON.parse(data.result.obj);
						if(obj.stakelist!=""){
							var lon=obj.stakelist[0].offsetlongitude;
							var lat=obj.stakelist[0].offsetlatitude;
							var lonlat = lon+","+lat;
							selectLonlat=lonlat;
							markerS=maplet.drawMarker(lonlat,"image/map/bridge.png",30);
							maplet.setMapCenter(lonlat,13);
							createPopup(lonlat,infolist[num].bzbm);
						}else{
							alert("暂无数据");
						}
					}
					
				}else if(code==-1){
					BackToLoginPage();
				}else{
					alert(data.result.resultdesc);
				}
			}
		});
	}else{
		alert("桩号格式不对！");
	}
}
function createPopup(lonlat,bzbm){
	$.ajax({
		url:'qljbxxlist/qlqx',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'bzbm':bzbm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				var html="<table class='map_pop_tabel' cellpadding='0' cellspacing='0'><tbody>" +
						"<tr><td class='pop_td_left1'>桥梁名称：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.qlmc)+"</td><td class='pop_td_left2'>桥梁全长：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.qlqc)+"米</td></tr>" +
						"<tr><td class='pop_td_left1'>所在路段：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.lxjc)+"</td><td class='pop_td_left2'>路线代码：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.lxdm)+"米</td></tr>" +
							"<tr><td class='pop_td_left1'>桥梁中心桩号：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(conversionType(list.qlzxzh))+"</td><td class='pop_td_left2'>多跨径总长：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.dkjzc)+"米</td></tr>" +
								"<tr><td class='pop_td_left1'>桥面净宽：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.qmjk)+"米</td><td class='pop_td_left2'>设计荷载等级：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.sjhzdj)+"</td></tr>" +
									"<tr><td class='pop_td_left1'>跨越地物名称：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.kydwmc)+"</td><td class='pop_td_left2'>通航等级：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.thdj)+"</td></tr>" +
											"<tr><td class='pop_td_left1'>建设单位名称：</td><td  class='pop_td_right' colspan='3'>&nbsp;"+judgeIsNull(list.jsdwmc)+"</td></tr>" +
													"<tr><td class='pop_td_left1'>设计单位名称：</td><td class='pop_td_right' colspan='3'>&nbsp;"+judgeIsNull(list.sjdwmc)+"</td></tr>" +
						"<tr><td class='pop_td_left1'>施工单位名称：</td><td class='pop_td_right' colspan='3'>&nbsp;"+judgeIsNull(list.sgdwmc)+"</td></tr>" +
								"<tr><td class='pop_td_left1'>监理单位名称：</td><td class='pop_td_right' colspan='3'>&nbsp;"+judgeIsNull(list.jldwmc)+"</td></tr>" +
								"<tr><td  class='pop_td_left1'>修建年度：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.xjnd)+"</td><td class='pop_td_left2'>监管单位名称：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.jgdwmc)+"</td></tr>" +
										"<tr><td class='pop_td_left1'>管养单位名称：</td><td class='pop_td_right' colspan='3'>&nbsp;"+judgeIsNull(list.gydwmc)+"</td></tr></tbody></table>";
				/*$("#dtfzsslx").text(judgeIsNull(list.dtfzsslx));
				$("#sfhtlj").text(judgeIsNull(list.sfgtlj));*///是否互通立交
				/*$("#gydwmc").text(judgeIsNull(list.gydwmc));
				$("#pddj").text(judgeIsNull(list.pddj));
				$("#pdrq").text(judgeIsNull(list.pdrq));
				$("#gznd").text(judgeIsNull(list.gznd));*/
				maplet.createPopupForMarker(lonlat,html,"image/map/bridge.png",30,650,400);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
/**
 * 返回
 */
function gobackToSelectPage(){
	window.location.href=$("#basePath").val()+"page/highway/HighWayMap.jsp";
}

function NullInfo(){
	var newDiv=$("<div class='highway_left_middle_div'>暂无相关数据</div>");
	$(".highway_left_middle").append(newDiv);
	$(".highway_left_bottom").hide();
}
function fullOver(id){
	id.src="image/main/full_pressed.png";
}
function fullOut(id){
	id.src="image/main/full_normal.png";
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
