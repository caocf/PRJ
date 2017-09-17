var map;
var startpointlonlat="";//起点经纬度
var endpointlonlat="";//终点经纬度
var startLocation="";//起点地址
var endLocation="";//终点地址
var transferoder=1;//换乘方式序号
var datalist_coordinate=new Array();//经纬度 二位数组 第一参数：1.步行（#7fe77c）2.公交（7c9de7） 第一参数：经纬度
var oldautocomplete1="";
var oldautocomplete2="";
var tj_array1=new Array();
var tj_array2=new Array();
var tj_point1=new Array();
var tj_point2=new Array();
$$(document).ready(function() {
	map = new XMap("simpleMap",1);
	map.addPanZoomBar();
	InitializeTianMap();
	InitializeFontColor("startPoint");
	InitializeFontColor("endPoint");
	OnLoadPage();
});
//首页跳转处理
function OnLoadPage(){
	if($$("#sc_startPoint").val()!="null"){
		$$("#startPoint").val($$("#sc_startPoint").val());
		$$("#endPoint").val($$("#sc_endPoint").val());
		startLocation=$$("#sc_startPoint").val();//起点地址
		if($$("#sc_startPointLonLat").val()!="null")
			startpointlonlat=$$("#sc_startPointLonLat").val();
		if($$("#sc_endPointLonLat").val()!="null")
			endpointlonlat=$$("#sc_endPointLonLat").val();
		SearchBusTransfer();
	}
}
//搜索
function SearchBusTransfer() {
	
	//判断经纬度
	if(!GetLatLon_transfer()){
		return;
	}
	queryBusTransfer();
}

function queryBusTransfer(){
	//清空页面
	EmptyDate();
	if(startpointlonlat==endpointlonlat){
		alert("起终点不能相同！");
		return;
	}
	$$.ajax( {
		url : 'QueryOriginTrafficTrasfer',
		type : 'post',
		dataType : 'json',
		data : {
			'startLontitude' :startpointlonlat.split(",")[0],
			'startLantitude' :startpointlonlat.split(",")[1],
			'endLontitude' :endpointlonlat.split(",")[0],
			'endLantitude' :endpointlonlat.split(",")[1],
			'order':transferoder,
			'count':PAGESIZE

		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			if(data!=""){
			var list = JSON.parse(data);
			list=list.List;
			if (list.length == undefined||list.length ==0) {
				TableIsNull();
			} else {
				appendToTable(list,list.length);// 跳转页码的方法名称
				
			}
			}else{
				TableIsNull();
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		},
		complete : function() {
			CloseLoadingDiv();
	}

	});

}
function TableIsNull(){
	$$(".layout1_left_datalist3").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
}



function appendToTable(list, listLength){
	$$("#searchReasultCount").text(listLength);
	for ( var i = 0; i < listLength; i++) {
		var trname="";
		if(list[i].LineList!=null){
			for ( var j = 0; j<list[i].LineList.length; j++){
				if(j>0) trname+="&rarr;";
			trname+=list[i].LineList[j];
			}
		}else{
				trname="步行";
		}
			
		var coordinate=new Array();
		var all_length=list[i].NewList.length;//总次数
		var newDiv = $$("<div class='addDiv'></div>");
		var newTr_top = $$("<div class='addDiv_top' ></div>");
		newTr_top.append($$("<div class='addDiv_top_div_1' onclick=busRouteCoordinate('"+i+"')><label class='addDiv_top_div_num'>"+(i+1)+
							"</label><label class='addDiv_top_div_name'>"+trname+
								"</label>&nbsp;&nbsp;&nbsp;<label class='addDiv_top_div_time'>全程约"+list[i].Time+"分钟</label></div>"));
		newTr_top.append($$("<div class='addDiv_top_div_2'><label>全程距离约"+list[i].Distance+"米</label></div>"));
		newDiv.append(newTr_top);
		
		var newTr = $$("<div class='addDiv_content' id='addDiv_"+i+"' style='display:none'></div>");
		//起点标志
		newTr.append($$("<div class='addDiv_div_ends'><img src='WebPage/image/graphical/icon_origin.png'/><label>"+startLocation+"</label></div>"));
		for(var j=0;j<all_length;j++){
			var NewList=list[i].NewList[j];
			coordinate[j]=new Array();
			//0步行,1公交，2自行车,3地铁，4水上巴士
				if(NewList.Type==0){
					if(NewList.StationName==undefined){
						var stationName="目的地";
						if(j==all_length-1){
							stationName=endLocation;
						}
						newTr.append($$("<div class='addDiv_div_walk'>"+(j+1)+".步行往<label class='addDiv_label_notice'>"+NewList.Direction+
								"</label>至<label class='addDiv_label_notice'>"+stationName+
								"</label><label class='addDiv_label_right'>"+NewList.Distance+"米</label></div>"));
					}else{
					newTr.append($$("<div class='addDiv_div_walk'>"+(j+1)+".步行往<label class='addDiv_label_notice'>"+NewList.Direction+
							"</label>至<label class='addDiv_label_notice'>"+NewList.StationName+
							"</label><label class='addDiv_label_right'>"+NewList.Distance+"米</label></div>"));
					}
					
					coordinate[j][0]=0;
					coordinate[j][1]=NewList.Longitude+","+NewList.Latitude+";";
				
				}
				else if(NewList.Type==1){
					newTr.append($$("<div class='addDiv_div_bus_1'>"+(j+1)+".乘坐<label class='addDiv_label_notice'>"+NewList.LineDetails.Name+
							"</label>到下车<label class='addDiv_label_notice'>"+NewList.StationName+"</label><label  class='addDiv_label_right'>"+NewList.LineDetails.StationCount+"站</label></div>"));
					newTr.append($$("<div class='addDiv_div_bus_2'><label class='addDiv_label_businfor'>首末班时间："+NewList.LineDetails.StartTime+"-"+NewList.LineDetails.EndTime+"&nbsp;全程："+NewList.LineDetails.Price+"元</label></div>"));
					coordinate[j][0]=1;
					var coordinateString="";
					for(var n=0;n<NewList.LineTrailsList.length;n++){
						coordinateString+=NewList.LineTrailsList[n].Longitude+","+NewList.LineTrailsList[n].Latitude+";";
					}
					coordinate[j][1]=coordinateString;
				}
				else if(NewList.Type==2){
					alert("2自行车");
				}
				else if(NewList.Type==3){
					alert("3地铁");
				}
				else if(NewList.Type==4){
					alert("4水上巴士");
				}
			
		}
		//终点标志
		newTr.append($$("<div class='addDiv_div_ends'><img src='WebPage/image/graphical/icon_destenation.png'/><label>"+endLocation+"</label></div>"));
		newDiv.append(newTr);
		$$(".layout1_left_datalist3").append(newDiv);
		datalist_coordinate[i]=coordinate;//经纬度
		
	}
}
//地图显示整条线路
function busRouteCoordinate(num) {
	OpenDiv(num);//展开子菜单
	/*map.clearLines();
	map.clearMarkers();*/
	map.delWindow();
	var coordinate=datalist_coordinate[num];
	for (var k = 0; k < coordinate.length; k++) {
		if(coordinate[k][0]==0){
			var coordinate_line;
			if(k==0){
				coordinate_line=startpointlonlat+";"+coordinate[k][1];
				
			}else{
				var spstr = coordinate[k-1][1].split(";");
				coordinate_line=spstr[spstr.length-2]+";"+coordinate[k][1];
			}
			map.drawLine(coordinate_line,DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
		}else{
			var spstr = coordinate[k][1].split(";");
			addPointPoup(spstr[spstr.length-2],"WebPage/image/common/map_point_bus.png",21);
			addPointPoup(spstr[0],"WebPage/image/common/map_point_bus.png",21);
			map.drawLine(coordinate[k][1],DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
		}
		

	}
	
	addPointPoup(startpointlonlat,"WebPage/image/common/map_point_start.png",36);
	addPointPoup(endpointlonlat,"WebPage/image/common/map_point_end.png",36);
	//the most suitable size of the map
//	map.createLineSuitable();
}
//图上标点
function addPointPoup(lonlat,image,imageSize) {
	var lonlat = map.createLonlat(lonlat);
	var icon = map.createIcon(image,imageSize);
	imgMarker = map.createMarker(lonlat, icon);
	map.addMarkerObject(imgMarker);
}
function OpenDiv(num){
	$$(".addDiv_content").css("display","none");
	$$("#addDiv_"+num).css("display","block");
}

//交换起终点
function ChangeTextOnClick(){
	var tj_change=new Array();
	tj_change=tj_array1;
	tj_array1=tj_array2;
	tj_array2=tj_change;
	tj_change=tj_point1;
	tj_point1=tj_point2;
	tj_point2=tj_change;
	var startPoint=$$("#startPoint").val();
	$$("#startPoint").val($$("#endPoint").val());
	$$("#endPoint").val(startPoint);
	 startPoint=startpointlonlat;
	 startpointlonlat=endpointlonlat;
	 endpointlonlat=startPoint;
}

//换乘方式
function TransferOder(thisop,num){
	$$(".layout3_left_layout2_label_select").attr("class","layout3_left_layout2_label");
	thisop.className="layout3_left_layout2_label_select";
	transferoder=num;
	//搜索
	SearchBusTransfer();
}
//清空页面
function EmptyDate(){
	$$(".addDiv").empty();
	$$(".addDiv").remove();
	$$(".layout1_left_datalist_tishi").remove();
	$$(".layout1_left_datalist_title").css("visibility","visible");
	
	map.delWindow();
	$$("#searchReasultCount").text("0");
	  $$(".layout1_left_datalist3").empty();
}


function startPointSelect(){
	var tj_tags=$$("#startPoint").val();
	 
	if(tj_tags.length>1){
		if(tj_tags.indexOf(oldautocomplete1)==0||tj_tags.indexOf(oldautocomplete1)==-1){
	
    $$.ajax({  
        url: "searchpoibykey",  
        type: "post",  
        data: {   
        	 'sp.key' : encodeURI(tj_tags),
             'sp.pageNumber':10,
             'sp.startPosition':1,
             'sp.json':0
        },  
        success:function(data){  
        	oldautocomplete1=tj_tags;
        	var list = $$.parseJSON(data.recodeString);	
            var featureMember = list.FeatureCollection.featureMember;
    
            tj_array1.splice(0,tj_array1.length); 
            tj_point1.splice(0,tj_point1.length);
            if(featureMember!=null){
    			for ( var i = 0; i < featureMember.length; i++) {
    				var JXPOI = featureMember[i].JXPOI;
    				//tj_array1[i]=JXPOI[0].NAME+","+JXPOI[0].ADDRESS;
    				tj_array1[i]=JXPOI[0].NAME;
    				tj_point1[i]=JXPOI[0].GEOMETRY[0].Point[0].coordinates[0];
    			}
                }
			
			$$("#startPoint").autocomplete({
				source: tj_array1,  
				appendTo: "#autocomplete_div1"
			}); 
			
        }  
    });  
	}
	}

}
function endPointSelect(){
	
	var tj_tags=$$("#endPoint").val().split(",")[0];
	if(tj_tags.length>1){
		if(tj_tags.indexOf(oldautocomplete2)==0||tj_tags.indexOf(oldautocomplete2)==-1){
    $$.ajax({  
    	 url: "searchpoibykey",  
         type: "post",  
         data: {   
            'sp.key' :  encodeURI(tj_tags),
			'sp.pageNumber' : PAGESIZE,
			'sp.startPosition' : 1,
			'sp.Json' : 0
         },   
        success:function(data){  
        	oldautocomplete2=tj_tags;
        	  var list = $$.parseJSON(data.recodeString);	
              var featureMember = list.FeatureCollection.featureMember;
             
            tj_array2.splice(0,tj_array2.length); 
            tj_point2.splice(0,tj_point2.length);
            if(featureMember!=null){
    			for ( var i = 0; i < featureMember.length; i++) {
    				var JXPOI = featureMember[i].JXPOI;
    				//tj_array2[i]=JXPOI[0].NAME+","+JXPOI[0].ADDRESS; 
    				tj_array2[i]=JXPOI[0].NAME;
    				tj_point2[i]=JXPOI[0].GEOMETRY[0].Point[0].coordinates[0];
    			}
                }
			
			$$("#endPoint").autocomplete({
				source: tj_array2,  
				appendTo: "#autocomplete_div2"
			}); 
			
        }  
    });  
	}
	}
}
function startPointFocus(){
	if($$("#startPoint").val()!=oldautocomplete1){
		startpointlonlat="";
		startLocation="";
	}
}
function endPointFocus(){
	if($$("#endPoint").val()!=oldautocomplete2){
		endpointlonlat="";
		endLocation="";
	}
}
function GetLatLon_transfer() {

	var trim_startPoint=trim($$("#startPoint").val());
	var trim_endPoint=trim($$("#endPoint").val());
	for ( var i = 0; i < tj_array1.length; i++) {
		if (tj_array1[i] ==trim_startPoint ) {
			startpointlonlat=tj_point1[i];
			startLocation=tj_array1[i];
		}
	}
	for ( var i = 0; i < tj_array2.length; i++) {
		if (tj_array2[i] == trim_endPoint) {
			endpointlonlat=tj_point2[i];
			endLocation=tj_array2[i];
		}
	}
	if(trim_startPoint==""){
		alert("请输入起点");
		return false;
	}
	if(trim_endPoint==""){
		alert("请输入终点");
		return false;
	}
	if(startpointlonlat==""){
		ShowStartNameList("searchpoibykey",1);
		return false;
	}
	if(endpointlonlat==""){
		ShowEndNameList("searchpoibykey",1);
		return false;
	}
	return true;
}
function ShowStartNameList(actionName,selectedPage){
	EmptyDate();
	 $$.ajax({  
	        url: actionName,  
	        type: "post",  
	        data: {   
	           'sp.key' : encodeURI(trim($$("#startPoint").val())),
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
	            	$$("#searchReasultCount").text(featureMember.length);
					for ( var i = 0; i < featureMember.length; i++) {
						var JXPOI = featureMember[i].JXPOI;
						var lonlat=JXPOI[0].GEOMETRY[0].Point[0].coordinates+"";
						var name=JXPOI[0].NAME;
						var address=JXPOI[0].ADDRESS;
						str += "<tr><td id='addLi_"+ i +"' class='addLi'>" +
						"<div class='addLi_img'><img  src='WebPage/image/newmap_icon/1.png'/></div>"+
						"<div class='addLi_right'><label class='addLi_select_pointname'  onclick=CreatKeyPoint(this,'"+ name+"','"+address+"','"+lonlat+"',1) >"+ name+"</label>" +
								"<label class='addLi_select_point' onclick=SelectPoint(1,'"+ name+"','"+address+"','"+lonlat+"') >选为起点</label></div></td></tr>";
					
						var html="<label class='addLi_select_point2' onclick=SelectPoint(1,'"+name+"','"+address+"',"+lonlat+") >选为起点</label>";
						map.createPopupForMarker(lonlat,html,"WebPage/image/newmap_icon/1.png",36,80,60);
					
					}
					  str+="</table>";
			            $$(".layout1_left_datalist3").html(str);
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
function ShowEndNameList(actionName,selectedPage){
	EmptyDate();
	 $$.ajax({  
	        url: actionName,  
	        type: "post",  
	        data: {   
	           'sp.key' : encodeURI(trim($$("#endPoint").val())),
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
	            	$$("#searchReasultCount").text(featureMember.length);
					for ( var i = 0; i < featureMember.length; i++) {
						var JXPOI = featureMember[i].JXPOI;
						var lonlat=JXPOI[0].GEOMETRY[0].Point[0].coordinates+"";
						var name=JXPOI[0].NAME;
						var address=JXPOI[0].ADDRESS;
						str += "<tr><td id='addLi_"+ i +"'  class='addLi'>" +
						"<div class='addLi_img'><img  src='WebPage/image/newmap_icon/1.png'/></div>"+
						"<div class='addLi_right'><label class='addLi_select_pointname' onclick=CreatKeyPoint(this,'"+ name+"','"+address+"','"+lonlat+"',2) >"+ name+"</label>" +
								"<label class='addLi_select_point' onclick=SelectPoint(2,'"+ name+"','"+address+"','"+lonlat+"') >选为终点</label></td></tr>";
						
						var html="<label class='addLi_select_point2' onclick=SelectPoint(2,'"+ name+"','"+address+"',"+lonlat+") >选为终点</label>";
						map.createPopupForMarker(lonlat,html,"WebPage/image/newmap_icon/1.png",36,80,60);
					
					}
					 str+="</table>";
			            $$(".layout1_left_datalist3").html(str);
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
function CreatKeyPoint(thisop,name,address,lonlat,type){
	$$(".addLi_pressed").attr("class","addLi");
	thisop.className = "addLi_pressed";
	
	map.setCenter(lonlat,null);
	var html="";
	if(type==1)
	 html="<label class='addLi_select_point2' onclick=SelectPoint("+type+","+ name+","+address+","+lonlat+") >选为起点</label>";
	else
		 html="<label class='addLi_select_point2' onclick=SelectPoint("+type+","+ name+","+address+","+lonlat+") >选为终点</label>";
	map.createPopup(lonlat,80,60,html);
	/*var LonLat = map.createLonlat(lonlat);
	var icon = map.createIcon("WebPage/image/graphical/map_point_pressed.png",36);
	imgMarker = map.createMarker(LonLat, icon);
	map.addMarkerObject(imgMarker);
	imgMarker.events.register("click",null,function(){
		// 清除浮云窗口
		if (currentPopup) {
			map.removePopup(currentPopup);
		}
		
		var popup=map.createPopup(lonlat,100,100,html);
	});*/
}
function SelectPoint(type,name,address,lonlat){
	if(type==1){
		$$("#startPoint").val(name);
		startpointlonlat=lonlat;
		startLocation=address;
		if(endpointlonlat==""){
			ShowEndNameList("searchpoibykey",1);
		}else{
			queryBusTransfer();
		}
	}else{
		$$("#endPoint").val(name);
		endpointlonlat=lonlat;
		endLocation=address;
		queryBusTransfer();
	}
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $$(".indexCommon").attr("value");
	var str = $$.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$$(".indexCommon").val("");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		GetParking(actionName, pageNo);
	}
}

//收藏--换乘
function SaveTransferFavor(){
	if ($$("#LoginUser").val() == "null") {
		alert("请先登录");
	} else {
		$$.ajax( {
			url : 'SaveTransferFavor',
			type : 'post',
			dataType : 'json',
			data : {
				'startLongtitude':startpointlonlat.split(",")[0],
				'startLantitude':startpointlonlat.split(",")[1],
				'endLongtitude':endpointlonlat.split(",")[0],
				'endLantitude':endpointlonlat.split(",")[1],
				'startName':startLocation,
				'endName' : endLocation
			},
			success : function(data) {
				//-1 用户未登录、 0 已保存、 1保存成功
				if (data.status == -1) {
					alert("用户未登录");
				} else if (data.status == 0) {
					alert("已收藏");
					thisop.src="WebPage/image/common/favorates.png";
				}else if (data.status == 1) {
					alert("收藏成功！");
					thisop.src="WebPage/image/common/favorates.png";
				}
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		});
	}
}
function LeftCssBySelf(num){
	$$(".addLi_pressed").attr("class","addLi");
	var nm=document.getElementById("addLi_"+num);
	nm.className = "addLi_pressed";
}
