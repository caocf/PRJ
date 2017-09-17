var infoList="";
var selectnum="";
var leftmark=1;
$(document).ready(function(){
	$("#top_text").text("公路地图");
	loadmap();
	if($("#gljgdm").val()!=""&&$("#gljgdm").val()!="null"){
		showGljgInfo('gljglist/gljgjbxxlist');
	}else{
		showGljgInfo('gljglist/gljgjbxxlist');
	}
});
function loadmap(){
	maplet = new XMap("highwaymap",3);
	maplet.setMapCenter("120.76042769896,30.773992239582",8);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}
/**
 * 显示管理机构
 * @param actionName
 * @param selectedPage
 */
function showGljgInfo(actionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".highway_left_middle").empty();
				var list=data.result.list;
				if(list==""){
					NullInfo();
				}else{
					infoList=list;
					appendToTable(list);	
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		if($("#gljgdm").val()!=""&&$("#gljgdm").val()!="null"){
			if(list[i].gljgdm==$("#gljgdm").val()){
				var newDiv=$("<div class='highway_left_middle_div_select' id='"+i+"' style='height:60px;'></div>");
				newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].gljgmc)+"</div>");
				$(".highway_left_middle").append(newDiv);
				showMarkInMap(i);
			}else{
				var newDiv=$("<div class='highway_left_middle_div' id='"+i+"' style='height:60px;'></div>");
				newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].gljgmc)+"</div>");
				$(".highway_left_middle").append(newDiv);
			}
		}else{
			var newDiv=$("<div class='highway_left_middle_div' id='"+i+"' style='height:60px;'></div>");
			newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].gljgmc)+"</div>");
			$(".highway_left_middle").append(newDiv);
		}
	}
	$(".highway_left_middle_div,.highway_left_middle_div_select").click(function(){
		$(".highway_left_middle_div_select").attr("class","highway_left_middle_div");
		$(this).attr("class","highway_left_middle_div_select");
		showMarkInMap($(this).attr("id"));
	});
	
}
/**
 * 地图左拉
 */
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
	var lon=infoList[num].jd;
	var lat=infoList[num].wd;
	if(lon==null||lat==null){
		return;
	}
	var lonlat = lon+","+lat;
	selectLonlat=maplet.createLonlat(lonlat);
	markerS=maplet.drawMarker(lonlat,"image/map/gljg.png",30);
	maplet.setMapCenter(lonlat,13);
	createPopup(lonlat,num);
}
function createPopup(lonlat,num){
	var html="<table class='map_pop_tabel' cellpadding='0' cellspacing='0'>" +
			"<tr><td class='pop_td_left1'>名称：</td><td class='pop_td_right'>&nbsp;"+infoList[num].gljgmc+"</td></tr>" +
			"<tr><td class='pop_td_left1'>地址：</td><td class='pop_td_right' style='word-break: break-all; word-wrap:break-word;'>&nbsp;"+infoList[num].lxdz+"</td></tr>" +
					"<tr><td class='pop_td_left1'>联系电话：</td><td class='pop_td_right'>&nbsp;"+infoList[num].lxdh+"</td></tr></table>";
	maplet.createPopupForMarker(lonlat,html,"image/map/gljg.png",30,450,140);
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
