var fwqOrsfz=0;//1是服务区2是收费站
var fwssList="";
var selectnum="";
var leftmark=1;
$(document).ready(function(){
	$("#top_text").text("公路地图");
	loadmap();
	if($("#fwqbh").val()!=""&&$("#fwqbh").val()!="null"){
	 	$("#service_li").attr("class","service_li_select");
	 	fwqOrsfz=1;
	 	var page=$("#amount").val()/7;
		if(isNum(page)){
			page=Math.floor(page)+1;
		}
		showServiceInfoByAmount('fwssmanager/fwqlist',1);
	}else if($("#sfzbh").val()!=""&&$("#sfzbh").val()!="null"){
		$("#fee_li").attr("class","service_li_select");
		fwqOrsfz=2;
		var page=$("#amount").val()/7;
		if(isNum(page)){
			page=Math.floor(page)+1;
		}
		showServiceInfoByAmount('fwssmanager/sfzlist',1);
	}else{
		$("#service_li").attr("class","service_li_select");
		fwqOrsfz=1;
		showServiceInfo('fwssmanager/fwqlist',1);
	}
});
function loadmap(){
	maplet = new XMap("highwaymap",3);
	maplet.setMapCenter("120.76042769896,30.773992239582",8);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}
function showServiceInfoByAmount(actionName,selectedPage){
	var selectId="";
	if(fwqOrsfz==1){
		selectId=$("#fwqbh").val();
	}else{
		selectId=$("#sfzbh").val();
	}
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':7,
			'page':selectedPage,
			'selectId':selectId
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
					fwssList=list;
					appendToTableByAmount(list);	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showServiceInfo',
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
 * 显示服务设施
 * @param actionName
 * @param selectedPage
 */
function showServiceInfo(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':7,
			'page':selectedPage
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
					fwssList=list;
					appendToTable(list);	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showServiceInfo',
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
	if(fwqOrsfz==1){
		for(var i=0;i<list.length;i++){
			if(list[i].fwqbh==$("#fwqbh").val()){
				var newDiv=$("<div class='highway_left_middle_div_select' id='"+i+"'></div>");
				newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].fwqmc)+"</div>");
				newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;线路名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
				showMarkInMap(i);
				$(".highway_left_middle").append(newDiv);
			}else{
				var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
				newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].fwqmc)+"</div>");
				newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;线路名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
				$(".highway_left_middle").append(newDiv);
			}
			
			
		}
	}else{
		for(var i=0;i<list.length;i++){
			if(list[i].sfzbh==$("#sfzbh").val()){
				var newDiv=$("<div class='highway_left_middle_div_select' id='"+i+"'></div>");
				newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].sfzmc)+"</div>");
				newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;线路名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
				showMarkInMap(i);
				$(".highway_left_middle").append(newDiv);
			}else{
				var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
				newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].sfzmc)+"</div>");
				newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;线路名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
				$(".highway_left_middle").append(newDiv);
			}
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
	if(fwqOrsfz==1){
		for(var i=0;i<list.length;i++){
			var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
			newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].fwqmc)+"</div>");
			newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;线路名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
			$(".highway_left_middle").append(newDiv);
		}
	}else{
		for(var i=0;i<list.length;i++){
			var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
			newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].sfzmc)+"</div>");
			newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;线路名称:&nbsp;"+judgeIsNull(list[i].xlmc)+"</div>");
			$(".highway_left_middle").append(newDiv);
		}
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
		showServiceInfo(actionName, pageNo);
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
	selectnum=num;
	var stakenames="";
	if(fwssList[num].fwqbh!=undefined){
		stakenames=String(fwssList[num].sxjkzh+","+fwssList[num].sxckzh+","+fwssList[num].xxjkzh+","+fwssList[num].xxckzh);
	}else{
		stakenames=String(fwssList[num].zxsxjkzh+","+fwssList[num].zxsxckzh+","+fwssList[num].zxxxjkzh+","+fwssList[num].zxxxckzh);
	}
	var stakes=stakenames.split(",");
	var a=0;
	for(var i=0;i<stakes.length;i++){
		if(stakes[i].indexOf("K")!=-1&&stakes[i].indexOf("+")!=-1){
			$.ajax({
				url:'transferPre/transferPre',
				type:'post',
				dataType:'json',
				data:{
					'stakenames':fwssList[num].lxjc+"-"+stakes[i]
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
								a=0;
								if(fwssList[num].fwqbh!=undefined){
									markerS=maplet.drawMarker(lonlat,"image/map/service.png",30);
									createFwqPopup(lonlat,fwssList[num].fwqbh);
								}else{
									markerS=maplet.drawMarker(lonlat,"image/map/sfz.png",30);
									createSfzPopup(lonlat,fwssList[num].sfzbh);
								}
								maplet.setMapCenter(lonlat,13);
							}
						}
					}
				}
			});
		}else{
			a=1;
		}
		
	}
	if(a==1){
		alert("桩号格式不对");
	}
}
/**
 * 创建服务区浮云
 * @param lonlat
 * @param fwqbh
 */
function createFwqPopup(lonlat,fwqbh){
	$.ajax({
		url:'fwssmanager/fwqxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'fwssbh':fwqbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				var jyss=cyss=zsss=gwss=clwxss="没有";
				if(list.jyss==1){
					jyss="有";
				}
				if(list.cyss==1){
					cyss="有";
				}
				if(list.zsss==1){
					zsss="有";
				}
				if(list.gwss==1){
					gwss="有";
				}
				if(list.clwxss==1){
					clwxss="有";
				}
				var html="<table class='map_pop_tabel' cellpadding='0' cellspacing='0'>" +
						"<tr><td class='pop_td_left1'>服务区名称：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.fwqmc)+"</td><td class='pop_td_left2'>路线名称：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.lxjc)+"</td></tr>" +
						"<tr><td class='pop_td_left1'>所属公司：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.ssgs)+"</td><td class='pop_td_left2'>加油设施：</td><td class='pop_td_right'>&nbsp;"+jyss+"</td></tr>" +
						"<tr><td class='pop_td_left1'>餐饮设施：</td><td class='pop_td_right'>&nbsp;"+cyss+"</td><td class='pop_td_left2'>住宿设施：</td><td class='pop_td_right'>&nbsp;"+zsss+"</td></tr>" +
						"<tr><td class='pop_td_left1'>购物设施：</td><td class='pop_td_right'>&nbsp;"+gwss+"</td><td class='pop_td_left2'>车辆维修设施：</td><td class='pop_td_right'>&nbsp;"+clwxss+"</td></tr></table>";
				maplet.createPopupForMarker(lonlat,html,"image/map/service.png",30,650,180);
				/*$("#glzdzh_detail").text(judgeIsNull(list.glzdzh));
				$("#zczzh_detail").text(judgeIsNull(list.zczzh));
				$("#gljjzh_detail").text(judgeIsNull(list.gljjzh));
				$("#sxjkzh_detail").text(judgeIsNull(list.sxjkzh));
				$("#xxjkzh_detail").text(judgeIsNull(list.xxjkzh));
				$("#sxckzh_detail").text(judgeIsNull(list.sxckzh));
				$("#xxckzh_detail").text(judgeIsNull(list.xxckzh));*/
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
/**
 * 创建收费站浮云
 * @param lonlat
 * @param sfzbh
 */
function createSfzPopup(lonlat,sfzbh){
	$.ajax({
		url:'fwssmanager/sfzxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'fwssbh':sfzbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				var sfzlx="";
				if(list.sfzlxbh==2){
					sfzlx="匝道收费站";
				}else{
					sfzlx="主线收费站";
				}
				var html="<table class='map_pop_tabel' cellpadding='0' cellspacing='0'>" +
						"<tr><td class='pop_td_left1'>收费站名称：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.sfzmc)+"</td><td class='pop_td_left2'>路线名称：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.lxjc)+"</td></tr>" +
				"<tr><td class='pop_td_left1'>所属公司：</td><td class='pop_td_right' colspan='3'>&nbsp;"+judgeIsNull(list.ssgs)+"</td></tr>" +
						"<tr><td class='pop_td_left1'>收费站类型：</td><td class='pop_td_right' colspan='3'>&nbsp;"+sfzlx+"</td></tr>" +
				"<tr><td class='pop_td_left1'>进口ETC车道数量：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.jketccdsl)+"</td><td class='pop_td_left2'>出口ETC车道数量：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.cketccdsl)+"</td></tr>" +
				"<tr><td class='pop_td_left1'>进口人工车道数量：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.jkrgcdsl)+"</td><td class='pop_td_left2'>出口人工车道数量：</td><td class='pop_td_right'>&nbsp;"+judgeIsNull(list.ckrgcdsl)+"</td></tr></table>";
				maplet.createPopupForMarker(lonlat,html,"image/map/sfz.png",30,650,210);
				/*$("#sfzpjzm_detail").text(judgeIsNull(list.sfzpjzm));
				$("#ckzx_detail").text(judgeIsNull(list.ckzx));
				$("#zxsxjkzh_detail").text(judgeIsNull(list.zxsxjkzh));
				$("#zxxxjkzh_detail").text(judgeIsNull(list.zxxxjkzh));
				$("#zxsxckzh_detail").text(judgeIsNull(list.zxsxckzh));
				$("#zxxxckzh_detail").text(judgeIsNull(list.zxxxckzh));*/
				showfullbg();
				$("#DetailDiv").show();
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
function searchService(thisop){
	$(".service_li_select").attr("class","service_li");
	thisop.className="service_li_select";
	fwqOrsfz=1;
	showServiceInfo('fwssmanager/fwqlist',1);
}
function searchFeeStation(thisop){
	$(".service_li_select").attr("class","service_li");
	thisop.className="service_li_select";
	fwqOrsfz=2;
	showServiceInfo('fwssmanager/sfzlist',1);
}
function NullInfo(){
	$("#service_div").hide();
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
