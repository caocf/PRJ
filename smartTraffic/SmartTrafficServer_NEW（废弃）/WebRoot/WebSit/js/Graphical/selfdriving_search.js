var map;
var passarr;
var oldautocomplete1="";
var oldautocomplete2="";
var oldpassarr;//途径点中文
var ordernum=0;
$$(document).ready(function() {
	map=new XMap("simpleMap",1);
	map.addPanZoomBar();
	map.addMousePosition();
	InitializeTianMap();
	passarr=new Array();
	oldpassarr=new Array();
	OnLoadPage();
});
//首页跳转处理
function OnLoadPage(){
	if($$("#sc_startPoint").val()!="null"){
		$$("#startPoint").val($$("#sc_startPoint").val());
		$$("#endPoint").val($$("#sc_endPoint").val());
		//startLocation=$$("#sc_startPoint").val();//起点地址
		if($$("#sc_startPointLonLat").val()!="null")
			{
			$$("#startPointLonLat").val($$("#sc_startPointLonLat").val());
			markerS = createMarker($$("#sc_startPointLonLat").val(),"WebSit/image/common/map_point_start.png");
			}
		if($$("#sc_endPointLonLat").val()!="null")
		{
			$$("#endPointLonLat").val($$("#sc_endPointLonLat").val());
			markerE = createMarker($$("#sc_endPointLonLat").val(),"WebSit/image/common/map_point_end.png");
		}
		searchCar(null,'1')
	}
}
function getLonlat() {
	var lonlat = $$(".mousePositionCtrl").text();
	lonlat=lonlat.replace(" ","");
	var type = $$(".type").val();
	addSEMarker(type, lonlat);
}

function closeClick() {
	map.events.unregister("click", map, getLonlat);
}
function switchPoint(type) {
	$$(".type").val(type);
	map.events.register("click", map, getLonlat);
}
// 添加起终点标记
function addSEMarker(type, lonlat) {
	if (type.indexOf("start") != -1) {
		$$("#startPoint").val("地图上的点");
		$$("#startPointLonLat").val(lonlat);
		if (markerS != null) {
			markers.removeMarker(markerS);
		}
		markerS = createMarker(lonlat, aMarker[0]);
	}
	else if (type.indexOf("end") != -1) {
		$$("#endPoint").val("地图上的点");
		$$("#endPointLonLat").val(lonlat);
		if (markerE != null) {
			markers.removeMarker(markerE);
		}
		markerE = createMarker(lonlat, aMarker[4]);
	}
	else if (type.indexOf("pass") != -1) {
		var marker = createMarker(lonlat, aMarker[2]);
		//将途经点存入数组
		passarr.push(marker);
		var passarnum=oldpassarr.length;
		oldpassarr.push("");
		$$("#byPassInput")
				.append(
						"<div class='bypass_item'>"
								+ "<input type='text' class='passPoint' id='passPoint"+passarnum+"' value='地图上的点' onfocus='PassPointFocus("+passarnum+")' />"
								+ "<input type='hidden' class='passPointLonlat' id='passPointLonlat"+passarnum+"' value='"+ lonlat + "' readonly='readonly' />"
								+ "<span class='item_delete' onclick='deleteByPass(this.parentNode)'></span></div>");
	}
	closeClick();
}
function startPointFocus(){
	if($$("#startPoint").val()!=oldautocomplete1&&$$("#startPoint").val()!="地图上的点"){
		$$("#startPointLonLat").val("");
	}
}
function endPointFocus(){
	if($$("#endPoint").val()!=oldautocomplete2&&$$("#endPoint").val()!="地图上的点"){
		$$("#endPointLonLat").val("");
	}
}
function PassPointFocus(passarnum){
	if($$("#passPoint"+passarnum).val()!=oldautocomplete2&&$$("#passPoint"+passarnum).val()!="地图上的点"){
		$$("#passPointLonlat"+passarnum).val("");
	}
}
//删除途径点
function deleteByPass(obj) {
	var idx = $$("#byPassInput div.bypass_item").index(obj);
	//删除图标上的途经点
	var marker = passarr[idx];
	markers.removeMarker(marker);
	//删除数组中的Marker
	passarr.splice(idx,1);
	oldpassarr.splice(idx,1);
	$$("#byPassInput").get(0).removeChild(
			$$("#byPassInput div.bypass_item").eq(idx).get(0));
}
function createMarker(lonlat, imageIcon) {
	var lonlat = map.createLonlat(lonlat);
	var icon = map.createIcon(imageIcon);
	marker = map.createMarker(lonlat, icon);
	map.addMarkerObject(marker);
	return marker;
}
function searchCar(thisop,type){
	if(thisop!=null){
	$$(".main_left_select").attr("class","main_left_select_no");
	thisop.className="main_left_select";
	}
	if(type!=0){
		ordernum=type;
	}
	if(!ChangePoint()){
		return;
	}
	map.clearLines();
	
	//EmptyDate();
	var start=$$("#startPointLonLat").val();
	var end=$$("#endPointLonLat").val();
	var pass="";
	$$(".passPointLonlat").each(function() {
		var passPoint = $$(this).val();
		if(passPoint!="")
		pass += ";" + passPoint;
	});
	pass=pass.substr(1,pass.length);
	
	$$.ajax( {
		url : 'queryroad',
		type : 'post',
		dataType : 'json',
		data : {
			'busInfor.startpointlon' :start,
			'busInfor.endpointlon' :end,
			'busInfor.routeName':pass,
			'busInfor.order' : ordernum
		},
		success : function(data) {
			$$(".main_left_middle").empty();
			var recodeString=$$.parseJSON(data.recodeString);	
			map.drawLine(recodeString.routelatlon,DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
			if (recodeString.routes.item != undefined) {
				var length2 = recodeString.routes.item.length;
				for ( var i = 0; i < length2; i++) {
					$$(".main_left_middle").append("<div class='info'>"+ recodeString.routes.item[i].strguide+ "</div>");
				}
			} else {
				TableIsNull();
			}
	
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	});
}

//检查其余
function ChangePoint(){
	if($$("#startPoint").val()==""){
		alert("请输入起点");
		return false;
	}
	if($$("#endPoint").val()==""){
		alert("请输入终点");
		return false;
	}
	
	/*$$(".passPoint").each(function() {
		var passPointText = $$(this).val();
		if(passPointText!=oldpassarr[passNum]&&passPointText!="地图上的点"){
			ShowNameList("searchpoibykey",1,"startPoint",passNum+2);
			passNum++;
			return false;
		}
		
	});*/
	if($$("#startPointLonLat").val()==""){
		ShowNameList("searchpoibykey",1,"startPoint",1);
		return false;
	}
	if($$("#endPointLonLat").val()==""){
		ShowNameList("searchpoibykey",1,"endPoint",2);
		return false;
	}
	for(var i=0;i<oldpassarr.length;i++){
		var passPointText = $$("#passPoint"+i).val();
		if(passPointText!=oldpassarr[i]&&passPointText!="地图上的点"){
			ShowNameList("searchpoibykey",1,"passPoint"+i,i+3);
			return false;
		}
	}
	if($$("#endPointLonLat").val()==$$("#startPointLonLat").val()){
		alert("请起终点不能是同一个位置");
		return false;
	}
	return true;
}

 
function ShowNameList(actionName,selectedPage,id,type){
	EmptyDate();
	 $$.ajax({  
	        url: actionName,  
	        type: "post",  
	        data: {   
	           'sp.key' : encodeURI(trim($$("#"+id).val())),
	           'sp.pageNumber':KEY_PAGESIZE,
	           'sp.startPosition':selectedPage,
	           'sp.json':0
	        }, 
	        beforeSend : function() {
				ShowLoadingDiv();
			}, 
	        success:function(data){ 
	        	var recodeString=data.recodeString.replace(/^\n+|\n+$/g,"");
	        	if(trim(recodeString)!="null"){
	            var list = $$.parseJSON(data.recodeString);
	            var featureMember = list.FeatureCollection.featureMember;
	            
	            if(featureMember!=null){
	            	  var str ="<table cellpadding=0 cellspacing=0>";
					for ( var i = 0; i < featureMember.length; i++) {
						var JXPOI = featureMember[i].JXPOI;
						var lonlat=JXPOI[0].GEOMETRY[0].Point[0].coordinates+"";
						var name=JXPOI[0].NAME;
						var address=JXPOI[0].ADDRESS;
						var pointtishi;
						if(type==1){
							pointtishi="起点";
						}else if(type==2){
							pointtishi="终点";
						}else {
							pointtishi="途径";
						}
						str += "<tr><td id='addLi_"+ i +"'  class='addLi'>" +
						"<div class='addLi_img'><img  src='WebSit/image/map_icon/"+(i+1)+".png'/></div>"+
						"<div class='addLi_right'><label class='addLi_select_pointname' >"+ name+"</label>" +
								"<label class='addLi_select_point' onclick=SelectPoint("+type+",'"+ name+"','"+address+"','"+lonlat+"') >选为"+pointtishi+"</label></div></td></tr>";
					
					}
					  str+="</table>";
			            $$(".main_left_middle").html(str);
	            }else{
	            	TableIsNull();
	            }
	          
	        }  
	      	else{
	      	alert("无法获取数据");
	      	}
	      },
			complete : function() {
				CloseLoadingDiv();
		}
	    });  
}
function TableIsNull(){
	$$(".main_left_middle").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
}

function SelectPoint(type,name,address,lonlat){

	if(type==1){
		$$("#startPoint").val(name);
		$$("#startPointLonLat").val(lonlat);
		map.addMarker(lonlat,"WebSit/image/common/map_point_start.png",30);
		if($$("#endPointLonLat").val()==""){
			ShowNameList("searchpoibykey",1,"endPoint",2);
		}
	}else if(type==2){
		$$("#endPoint").val(name);
		$$("#endPointLonLat").val(lonlat);
		map.addMarker(lonlat,"WebSit/image/common/map_point_end.png",30);
	}else{
		type=type-3;
		$$("#passPoint"+type).val(name);
		$$("#passPointLonLat"+type).val(lonlat);
		oldpassarr[type]=name;
		for(var i=0;i<oldpassarr.length;i++){
			var passPointText = $$("#passPoint"+i).val();
			if(passPointText!=oldpassarr[i]&&passPointText!="地图上的点"){
				ShowNameList("searchpoibykey",1,"passPoint"+i,i+3);
			}
		}
	}
	var pnum=0;
	for(var i=0;i<oldpassarr.length;i++){
		var passPointText = $$("#passPoint"+i).val();
		if(passPointText!=oldpassarr[i]&&passPointText!="地图上的点"){
			ShowNameList("searchpoibykey",1,"passPoint"+i,i+3);
			pnum++;
		}
	}
	if(pnum==0) searchCar(null,0);
}
//清空页面
function EmptyDate(){
	//map.delWindow();
	$$(".main_left_middle").empty();
}