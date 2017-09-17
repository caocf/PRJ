var map;
var bzbxlist="";
var selectValue="";
var selectnum="";
var leftmark=1;
$(document).ready(function(){
	$("#top_text").text("公路地图");
	loadmap();
	if($("#bzbxbh").val()!=""&&$("#bzbxbh").val()!="null"){
		var page=$("#amount").val()/6;
		if(isNum(page)){
			page=Math.floor(page)+1;
		}
		showMarkSignInfoByAmount('bzbxmanager/bzbxlist',1);
	}else{
		showMarkSignInfo('bzbxmanager/bzbxlist',1);
	}
});
function loadmap(){
	map = new XMap("highwaymap",3);
	map.setMapCenter("120.76042769896,30.773992239582",8);
	map.setScaleVisible(false);//设置比例尺不可见
	map.setOverviewVisible(false,false);//鹰眼不可见
}
function showMarkSignInfoByAmount(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':6,
			'page':selectedPage,
			'selectId':$("#bzbxbh").val()
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
					bzbxlist=list;
					appendToTableByAmount(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showMarkSignInfo',
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
 * 显示标志标线
 * @param actionName
 * @param selectedPage
 */
function showMarkSignInfo(actionName,selectedPage){
	searchInput=$("#mark_info").val();
	var selectvalue="";
	if(searchInput=="桩号"){
		searchInput="";
	}
	selectvalue=searchInput;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':6,
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
					bzbxlist=list;
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showMarkSignInfo',
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
		if(list[i].bzbxbh==$("#bzbxbh").val()){
			var newDiv=$("<div class='highway_left_middle_div_select' id='"+i+"'></div>");
			newDiv.append("<div style='width:100%;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style='height:50px;' src='"+list[i].bzbxpath+"'></div>");
			newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;路线名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
			showMarkInMap(i);
			$(".highway_left_middle").append(newDiv);
		}else{
			var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
			newDiv.append("<div style='width:100%;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style='height:50px;' src='"+list[i].bzbxpath+"'></div>");
			newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;路线名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
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
		newDiv.append("<div style='width:100%;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style='height:50px;' src='"+list[i].bzbxpath+"'></div>");
		newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;路线名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
		$(".highway_left_middle").append(newDiv);
	}
	$(".highway_left_bottom").show();
	$(".highway_left_middle_div").click(function(){
		$(".highway_left_middle_div_select").attr("class","highway_left_middle_div");
		$(this).attr("class","highway_left_middle_div_select");
		showMarkInMap($(this).attr("id"));
	});
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
	selectnum=num;
	var stakenames=String(bzbxlist[num].bzwzl+","+bzbxlist[num].bzwzm+","+bzbxlist[num].bzwzr);
	var stakes=stakenames.split(",");
	var a=0;
	for(var i=0;i<stakes.length;i++){
		if(stakes[i]!=""&&conversionType(stakes[i]).indexOf("K")!=-1&&conversionType(stakes[i]).indexOf("+")!=-1){
			if(stakes[i]!="null"){
				$.ajax({
					url:'transferPre/transferPre',
					type:'post',
					dataType:'json',
					data:{
						'stakenames':bzbxlist[num].lxjc+"-"+conversionType(stakes[i])
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
									markerS=map.drawMarker(lonlat,"image/map/bzbx"+bzbxlist[num].bzbxlxbh+".png",30);
									map.setMapCenter(lonlat,13);
									a=0;
									createPopup(lonlat,bzbxlist[num].bzbxbh,num);
								}else{
									a=1;
								}
							}
							
						}
					}
				});
			}
		}else{
			alert("桩号格式不对!");
			return;
		}
	}
	if(a=1){
		alert("暂无数据");
	}
	
}
/**
 * 创建标志标线浮云
 * @param lonlat
 * @param bzbxbh
 */
function createPopup(lonlat,bzbxbh,num){
	$.ajax({
		url:'bzbxmanager/bzbxxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'bzbx.bzbxbh':bzbxbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				var bzwz="";
				var bzwzposition="";
				if(list.bzwzl!=null&&list.bzwzm!=null&&list.bzwzr!=null){
					bzwz="左中右";
					bzwzposition=judgeIsNull(list.bzwzl);
				}else if(list.bzwzl!=null){
					bzwz="左";
					bzwzposition=judgeIsNull(list.bzwzl);
				}else if(list.bzwzm!=null){
					bzwz="中";
					bzwzposition=judgeIsNull(list.bzwzm);
				}else if(list.bzwzr!=null){
					bzwz="右";
					bzwzposition=judgeIsNull(list.bzwzr);
				}
				var html="<img style='height:50px;' src='"+list.bzbxpath+"'><br>" +
						"<table class='map_pop_tabel' cellpadding='0' cellspacing='0'>" +
						"<tr><td class='pop_td_left1'>线路名称：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.xlmc)+"</td><td class='pop_td_left2'>类型：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.bzbxlxmc)+"</td></tr>" +
								"<tr><td class='pop_td_left1'>标志位置：</td><td class='pop_td_right'>&nbsp;"+bzwz+"&nbsp;"+bzwzposition+"</td><td class='pop_td_left2'>管理单位：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.gldw)+"</td></tr>" +
										"<tr><td class='pop_td_left1'>制作安装单位：</td><td class='pop_td_right' colspan='3'>&nbsp;"+judgeIsNull(list.zzazdw)+"</td></tr></table>";
				maplet.createPopupForMarker(lonlat,html,"image/map/bzbx"+bzbxlist[num].bzbxlxbh+".png",30,650,230);
			}else{
				alert(data.result.resultdesc);
			}
		}
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
		showMarkSignInfo(actionName, pageNo);
	}
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
