var map;
var mark=new Array();
var routelist="";
var markLine="";
var selectnum="";
var leftmark=1;
$(document).ready(function(){
	$("#top_text").text("公路地图");
	loadmap();
	if($("#routebm").val()!=""&&$("#routebm").val()!="null"){
		var page=$("#amount").val()/10;
		if(isNum(page)){
			page=Math.floor(page)+1;
		}
		showRouteInfoByAmount('lxjbxxlist/lxgklist',1);
		//showRouteInfoById('lxjbxxlist/lxqx');
	}else{
		showRouteInfo('lxjbxxlist/lxgklist',1);
	}
});
function loadmap(){
	map = new XMap("highwaymap",3);
	map.setMapCenter("120.76042769896,30.773992239582",8);
	map.setScaleVisible(false);//设置比例尺不可见
	map.setOverviewVisible(false,false);//鹰眼不可见
}
function showRouteInfoByAmount(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':10,
			'page':selectedPage,
			'selectId':$("#routebm").val()
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
					routelist=list;
					appendToTableByAmount(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showRouteInfo',
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
 * 显示路段信息
 * @param actionName
 * @param selectedPage
 */
function showRouteInfo(actionName,selectedPage){
	searchInput=$("#search_info").val();
	var selectvalue="";
	if(searchInput=="路线代码、路线简称"){
		searchInput="";
	}
	selectvalue=searchInput;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':10,
			'page':selectedPage,
			'selectvalue':selectvalue
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				//$(".highway_route_info,.highway_route_info_detail,.highway_route_info_selected,.highway_route_info_detail_selected").empty();
				//$(".highway_route_info,.highway_route_info_detail,.highway_route_info_selected,.highway_route_info_detail_selected").remove();
				$(".highway_left_middle").empty();
				var list=data.result.obj.data;
				if(list==""){
					NullInfo();
				}else{
					routelist=list;
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showRouteInfo',
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
		if(list[i].bzbm==$("#routebm").val()){
			var newDiv1=$("<div class='highway_route_info_selected' id='highway_route_info"+list[i].bzbm+"' onclick=showRouteDetail('"+list[i].bzbm+"','"+i+"')><label class='highway_route_name'>"+judgeIsNull(list[i].lxjc)+"&nbsp;&nbsp;("+list[i].lxdm+")"+"</label></div>");
			var newDiv2=$("<div class='highway_route_info_detail_selected'  id='highway_route_info_detail"+list[i].bzbm+"'></div>");
			newDiv2.append($("<div class='highway_route_info_detail_top'><label class='detail_top_left'>"+judgeIsNull(list[i].lxjc)+"&nbsp;&nbsp;("+list[i].lxdm+")"+"</label>" +
					"<label class='detail_top_right' onclick=goToRouteDetailPage('"+list[i].bzbm+"')>详情</label></div>"));
			newDiv2.append($("<div class='higgway_route_info_detail_body'><span class='detail_body_desc'>起点：&nbsp;"+judgeIsNull(list[i].ldqdmc)+"</span>" +
					"<span class='detail_body_desc'>终点：&nbsp;"+judgeIsNull(list[i].ldzdmc)+"</span><span class='detail_body_desc'>里程：&nbsp;"+judgeIsNull(list[i].lc)+"</span>" +
							"<span class='detail_body_desc'>设计时速：&nbsp;"+judgeIsNull(list[i].lc)+"</span></div>"));
			showRouteDetail(list[i].bzbm,i);
			$(".highway_left_middle").append(newDiv1);
			$(".highway_left_middle").append(newDiv2);
		}else{
			var newDiv1=$("<div class='highway_route_info' id='highway_route_info"+list[i].bzbm+"' onclick=showRouteDetail('"+list[i].bzbm+"','"+i+"')><label class='highway_route_name'>"+judgeIsNull(list[i].lxjc)+"&nbsp;&nbsp;("+list[i].lxdm+")"+"</label></div>");
			var newDiv2=$("<div class='highway_route_info_detail'  id='highway_route_info_detail"+list[i].bzbm+"'></div>");
			newDiv2.append($("<div class='highway_route_info_detail_top'><label class='detail_top_left'>"+judgeIsNull(list[i].lxjc)+"&nbsp;&nbsp;("+list[i].lxdm+")"+"</label>" +
					"<label class='detail_top_right' onclick=goToRouteDetailPage('"+list[i].bzbm+"')>详情</label></div>"));
			newDiv2.append($("<div class='higgway_route_info_detail_body'><span class='detail_body_desc'>起点：&nbsp;"+judgeIsNull(list[i].ldqdmc)+"</span>" +
					"<span class='detail_body_desc'>终点：&nbsp;"+judgeIsNull(list[i].ldzdmc)+"</span><span class='detail_body_desc'>里程：&nbsp;"+judgeIsNull(list[i].lc)+"</span>" +
							"<span class='detail_body_desc'>设计时速：&nbsp;"+judgeIsNull(list[i].lc)+"</span></div>"));
			$(".highway_left_middle").append(newDiv1);
			$(".highway_left_middle").append(newDiv2);
		}
		mark[list[i].bzbm]=0;
	}
	$(".highway_left_bottom").show();
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		var newDiv1=$("<div class='highway_route_info' id='highway_route_info"+list[i].bzbm+"' onclick=showRouteDetail('"+list[i].bzbm+"','"+i+"')><label class='highway_route_name'>"+judgeIsNull(list[i].lxjc)+"&nbsp;&nbsp;("+list[i].lxdm+")"+"</label></div>");
		var newDiv2=$("<div class='highway_route_info_detail'  id='highway_route_info_detail"+list[i].bzbm+"'></div>");
		newDiv2.append($("<div class='highway_route_info_detail_top'><label class='detail_top_left'>"+judgeIsNull(list[i].lxjc)+"&nbsp;&nbsp;("+list[i].lxdm+")"+"</label>" +
				"<label class='detail_top_right' onclick=goToRouteDetailPage('"+list[i].bzbm+"')>详情</label></div>"));
		newDiv2.append($("<div class='higgway_route_info_detail_body'><span class='detail_body_desc'>起点：&nbsp;"+judgeIsNull(list[i].ldqdmc)+"</span>" +
				"<span class='detail_body_desc'>终点：&nbsp;"+judgeIsNull(list[i].ldzdmc)+"</span><span class='detail_body_desc'>里程：&nbsp;"+judgeIsNull(list[i].lc)+"</span>" +
						"<span class='detail_body_desc'>设计时速：&nbsp;"+judgeIsNull(list[i].lc)+"</span></div>"));
		$(".highway_left_middle").append(newDiv1);
		$(".highway_left_middle").append(newDiv2);
		mark[list[i].bzbm]=0;
	}
	$(".highway_left_bottom").show();
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
		showRouteInfo(actionName, pageNo);
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
			showRouteDetail(routelist[selectnum].bzbm,selectnum);
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
			showRouteDetail(routelist[selectnum].bzbm,selectnum);
		}
	}
}
/**
 * 显示路段小详情
 * @param bzbm
 */
function showRouteDetail(bzbm,num){
	selectnum=num;
	$(".highway_route_info_selected").attr("class","highway_route_info");
	$(".highway_route_info_detail_selected").attr("class","highway_route_info_detail");
	$("#highway_route_info"+bzbm).attr("class","highway_route_info_selected");
	$("#highway_route_info_detail"+bzbm).attr("class","highway_route_info_detail_selected");
	var lonlat1="";
	var lonlat2="";
	var LonLat="";
	if(markLine!=""){
		map.removeLine(markLine);
	}
	if(conversionType(routelist[num].ldqdzh).indexOf("K")!=-1&&conversionType(routelist[num].ldqdzh).indexOf("+")!=-1){
		$.ajax({
			url:'transferPre/transferPre',
			type:'post',
			dataType:'json',
			data:{
				'stakenames':routelist[num].lxdm+"-"+conversionType(routelist[num].ldqdzh)
			},
			success:function(data){
				var code=data.result.resultcode;
				if(code==1){
					if(data.result.obj!=""){
						var obj=JSON.parse(data.result.obj);
						if(obj.stakelist!=""){
							var lon=obj.stakelist[0].offsetlongitude;
							var lat=obj.stakelist[0].offsetlatitude;
							lonlat1 = lon+","+lat;
							$.ajax({
								url:'transferPre/transferPre',
								type:'post',
								dataType:'json',
								data:{
									'stakenames':routelist[num].lxdm+"-"+conversionType(routelist[num].ldzdzh)
								},
								success:function(data){
									var code=data.result.resultcode;
									if(code==1){
										if(data.result.obj!=""){
											var obj=JSON.parse(data.result.obj);
											if(obj.stakelist!=""){
												var lon=obj.stakelist[0].offsetlongitude;
												var lat=obj.stakelist[0].offsetlatitude;
												lonlat2 = lon+","+lat;
												map.setMapCenter(lonlat1,13);
												LonLat=lonlat1+";"+lonlat2;
												markLine=map.drawLine(LonLat,"#000000","solid",0);
											}
										}
										
									}else if(code==-1){
										BackToLoginPage();
									}else{
										alert(data.result.resultdesc);
									}
									if(LonLat==""){
										alert("暂无地图数据");
									}
								}
							});
						}
					}
					
				}
			}
		});
	}else{
		alert("桩号格式不对！");
		return;
	}
}
/**
 * 进入路段详情页面
 * @param bzbm
 */
function goToRouteDetailPage(bzbm){
	window.location.href=$("#basePath").val()+"page/route/RouteDetail.jsp?routebm="+bzbm;
}
/**
 * 返回
 */
function gobackToSelectPage(){
	window.location.href=$("#basePath").val()+"page/highway/HighWayMap.jsp";
}
/**
 * 自适应设置宽度
 */
function setWidth(){
	var s=document.documentElement.clientWidth-350;
	if(s<1016){
		document.getElementById("highway_right").style.width=1016+"px";
	}else{
		document.getElementById("highway_right").style.width=s+"px";
	}
}
window.onresize=function(){
	var s=document.documentElement.clientWidth-350;
	if(s<1016){
		document.getElementById("highway_right").style.width=1016+"px";
	}else{
		document.getElementById("highway_right").style.width=s+"px";
	}
};
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
