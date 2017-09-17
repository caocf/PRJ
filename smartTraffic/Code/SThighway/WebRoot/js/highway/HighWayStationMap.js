var selectnum="";
var leftmark=1;
$(document).ready(function(){
	$("#top_text").text("公路地图");
	loadmap();
	if($("#stationbh").val()!=""&&$("#stationbh").val()!="null"){
		var page=$("#amount").val()/8;
		if(isNum(page)){
			page=Math.floor(page)+1;
		}
		showRoadStationInfoByAmount('glzxxlist/glzgklist',1);
	}else{
		showRoadStationInfo('glzxxlist/glzgklist',1);
	}
});
function loadmap(){
	maplet = new XMap("highwaymap",3);
	maplet.setMapCenter("120.76042769896,30.773992239582",8);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}
function showRoadStationInfoByAmount(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':8,
			'page':selectedPage,
			'selectId':$("#stationbh").val()
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
					appendToTableByAoumt(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showRoadStationInfo',
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
 * 显示公路站信息
 * @param actionName
 * @param selectedPage
 */
function showRoadStationInfo(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':8,
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
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showRoadStationInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
					$("#number").text(data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function appendToTableByAoumt(list){
	for(var i=0;i<list.length;i++){
		if(list[i].xxxxId==$("#stationbh").val()){
			var newDiv=$("<div class='highway_left_middle_div_select' id='"+i+"'></div>");
			newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].name)+"</div>");
			newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].gljgmc)+"</div>");
			//showMarkInMap(i);
			$(".highway_left_middle").append(newDiv);
		}else{
			var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
			newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].name)+"</div>");
			newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].gljgmc)+"</div>");
			$(".highway_left_middle").append(newDiv);
		}
		
		
	}
	$(".highway_left_bottom").show();
	$(".highway_left_middle_div,.highway_left_middle_div_select").click(function(){
		$(".highway_left_middle_div_select").attr("class","highway_left_middle_div");
		$(this).attr("class","highway_left_middle_div_select");
		//showMarkInMap($(this).attr("id"));
	});
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='highway_left_middle_div' id='"+i+"'></div>");
		newDiv.append("<div class='bridge_name'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].name)+"</div>");
		newDiv.append("<div class='bridge_area'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+judgeIsNull(list[i].gljgmc)+"</div>");
		$(".highway_left_middle").append(newDiv);
	}
	$(".highway_left_bottom").show();
	$(".highway_left_middle_div").click(function(){
		$(".highway_left_middle_div_select").attr("class","highway_left_middle_div");
		$(this).attr("class","highway_left_middle_div_select");
		//showMarkInMap($(this).attr("id"));
	});
}
function showMarkInMap(num){
	
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
