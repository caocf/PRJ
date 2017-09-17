var map;
var Gloval_areadata=new Array();
$$(document).ready(function() {
	map=new XMap("simpleMap",1);
	map.addPanZoomBar(); 
	map.zoomOut();
	document.getElementById("main_layout3_img2").innerHTML=document.getElementById("main_layout3_img1").innerHTML; 
	GetRealInformationList();
});
var speed=30//数值越大速度越慢 
var MyMar=setInterval(Marquee,speed); 
function Marquee(){ 
	if(document.getElementById("main_layout3_img2").offsetWidth-document.getElementById("main_layout3_content").scrollLeft<=0) 
			document.getElementById("main_layout3_content").scrollLeft-=document.getElementById("main_layout3_img1").offsetWidth; 
	else{ 
		document.getElementById("main_layout3_content").scrollLeft++ ;
	} 
} 
function MyMarOut(){
	MyMar=setInterval(Marquee,speed);	
}
function SearchPOI(code){
	window.location.href=$$("#basePath").val()+"WebSit/page/Graphical/pot.jsp?code="+code;
}
function SearchPOIkind(){
	if(!SearchContentCheck("poiKind")){
		return;
	}
	window.location.href=$$("#basePath").val()+"WebSit/page/Graphical/pot.jsp?poiKind="+$$("#poiKind").val();
}
function ChangeDiv(id,thisop){
	$$("#mllli_01").css("display","none");
	$$("#mllli_02").css("display","none");
	$$("#mllli_03").css("display","none");
	$$("#mllli_"+id).css("display","block");
	$$(".main_layout1_left_layout1_item_yes").attr("class","main_layout1_left_layout1_item");
	$$("#mllli_"+id).attr("class","main_layout1_left_layout1_item_yes");
	$$(".main_layout1_left_layout1_li_yes").attr("class","main_layout1_left_layout1_li");
	thisop.className="main_layout1_left_layout1_li_yes";
}
function ChangeDiv1(id,thisop){
	$$("#mlrli_1,#mlrli_2,#mlrli_3,#mlrli_4,#mlrli_5").css("display","none");
	$$("#mlrli_"+id).css("display","block");
	$$(".main_layout2_right_item_yes").attr("class","main_layout2_right_item");
	$$("#mlrli_"+id).attr("class","main_layout2_right_item_yes");
	$$(".main_layout1_right_layout1_li_yes").attr("class","main_layout1_right_layout1_li");
	thisop.className="main_layout1_right_layout1_li_yes";
	$$(".main_layout1_right_layout1_li_data").html(Gloval_areadata[id][0]+"路网指数：<font color="+Gloval_areadata[id][1]+" >"+Gloval_areadata[id][2]+"</font>");
}
//搜索公交
function SearchBus(type){
	if(type==1){
		if(!SearchContentCheck("SearchBusContent1")){
			return;
		}
		window.location.href=$$("#basePath").val()+"WebSit/page/publicTraffic/wisdom/road_search.jsp?searchcontent="+$$("#SearchBusContent1").val();
	}if(type==2){
		if(!SearchContentCheck("SearchBusContent2")){
			return;
		}
		window.location.href=$$("#basePath").val()+"WebSit/page/publicTraffic/wisdom/bus_station.jsp?searchcontent="+$$("#SearchBusContent2").val();
	}if(type==3){
		if(GetLatLon_transfer()){
			window.location.href=$$("#basePath").val()+"WebSit/page/publicTraffic/wisdom/bus_transfer.jsp?startPoint="+$$("#startPoint").val()+"&endPoint="+$$("#endPoint").val();
		}
	}
}
function GetRealInformationList(){
	//全路网
	$$.ajax( {
		url : 'GetTrafficValue',
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.areaName!=undefined){
				var color="";
				if(data.tpiLevel.indexOf("畅通")!=-1){
					color="#5c9f07";
				}else if(data.tpiLevel.indexOf("缓行")!=-1){
					color="#ff810b";
				}else{
					color="#red";
				}
				$$(".main_layout1_left_layout1_label3").text(toDecimal2(data.tpi));
				$$(".main_layout1_left_layout1_label3").css("color",color);
			}
		}
		});
	$$.ajax( {
		url : 'queryArea',
		type : "post",
		dataType : "json",
		data:{
				"page":1,
				"num":6
		},
		success : function(data) {
			var list=JSON.parse(data.result).adminTpis;
			var list2=JSON.parse(data.result_area).areaTpis;
			var arealist=["全路网","南湖区","秀洲区","内环内","中环内","三环内"];
			for(var i=0;i<arealist.length;i++){
				Gloval_areadata[i]=new Array();
				var areanum=0;
				if(list.length>0){
					for(var j=0;j<list.length;j++){
						if(list[j].adminName==arealist[i]){
							Gloval_areadata[i][0]=list[j].adminName;
							if(list[j].tpiLevel.indexOf("畅通")!=-1){
								Gloval_areadata[i][1]="#5c9f07";
							}else if(list[j].tpiLevel.indexOf("缓行")!=-1){
								Gloval_areadata[i][1]="#ff810b";
							}else{
								Gloval_areadata[i][1]="#red";
							}
							Gloval_areadata[i][2]=toDecimal2(list[j].tpi);
							areanum=1;
							break;
						}
					}
				}
				if(areanum==0&&list2.length>0){
				
						for(var j=0;j<list2.length;j++){
							if(list2[j].areaName==arealist[i]){
								Gloval_areadata[i][0]=list2[j].areaName;
								if(list2[j].tpiLevel.indexOf("畅通")!=-1){
									Gloval_areadata[i][1]="#5c9f07";
								}else if(list2[j].tpiLevel.indexOf("拥堵")!=-1){
									Gloval_areadata[i][1]="#ff810b";
								}else{
									Gloval_areadata[i][1]="#red";
								}
								Gloval_areadata[i][2]=toDecimal2(list2[j].tpi);
								areanum=1;
								break;
							}
						}
					
				}
				if(areanum==0){
					Gloval_areadata[i][0]="";
					Gloval_areadata[i][1]="#5c9f07";
					Gloval_areadata[i][2]="无";
				}
			}
			
			$$(".main_layout1_right_layout1_li_data").html(Gloval_areadata[1][0]+"路网指数：<font color="+Gloval_areadata[1][1]+" >"+Gloval_areadata[1][2]+"</font>");
		}
	});
	// 0:南湖;1秀洲:;2二环:;3:内环
	for(var i=0;i<5;i++){
		GetRealInformation(i,"mlrli_"+(i+1));
	}
}
function GetRealInformation(type,id){
	$$("#"+id+" .main_layout2_right_item_content tr").remove();
	$$.ajax( {
		url : 'GetRealInformation',
		type : "post",
		dataType : "json",
		data:{"type":type},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			var list = data.realRoadInfos;
			var html="";
			var Length=list.length;
			if(Length>9){
				Length=9;
			}//最多显示9条
			for(var j=0;j<Length;j++){
				//0:正常；1：高峰；2：低峰
				if(list[j].status==0){
					html+="<tr><td><label class='real_road_text_normal' title='"+list[j].infoContent+"'>"+list[j].infoContent+"</label></td>" +
							"<td><label class='real_road_text_normal'>["+HourTimeFormat(list[j].infoDate)+"]</label></td></tr>";
				}else if(list[j].status==1){
					html+="<tr><td><label class='real_road_text_height' title='"+list[j].infoContent+"'>"+list[j].infoContent+"</label></td>" +
							"<td><label class='real_road_text_height'>["+HourTimeFormat(list[j].infoDate)+"]</label></td></tr>";
				}else if(list[j].status==2){
					html+="<tr><td><label class='real_road_text_slack' title='"+list[j].infoContent+"'>"+list[j].infoContent+"</label></td>" +
							"<td><label class='real_road_text_slack'>["+HourTimeFormat(list[j].infoDate)+"]</label></td></tr>";
				}
				
			}
			$$("#"+id+" .main_layout2_right_item_content").append(html);
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});


} 
/*//公交換乘自适应
var oldautocomplete1="";
var oldautocomplete2="";
var tj_array1=new Array();
var tj_array2=new Array();
var tj_point1=new Array();
var tj_point2=new Array();
var startpointlonlat="";
var endpointlonlat="";
function startPointSelect(){
	var tj_tags=$$("#startPoint").val().split(",")[0];
	 
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
						tj_array1[i]=JXPOI[0].NAME+","+JXPOI[0].ADDRESS;
						//tj_array1[i]=JXPOI[0].NAME;
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
           'sp.key' : encodeURI(tj_tags),
           'sp.pageNumber':10,
           'sp.startPosition':1,
           'sp.json':0
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
				tj_array2[i]=JXPOI[0].NAME+","+JXPOI[0].ADDRESS; 
				//tj_array2[i]=JXPOI[0].NAME;
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
function GetLatLon_transfer() {
	var trim_startPoint=trim($$("#startPoint").val());
	var trim_endPoint=trim($$("#endPoint").val());
	for ( var i = 0; i < tj_array1.length; i++) {
		if (tj_array1[i] ==trim_startPoint ) {
			startpointlonlat =tj_point1[i];
		}
	}
	for ( var i = 0; i < tj_array2.length; i++) {
		if (tj_array2[i] == trim_endPoint) {
			endpointlonlat = tj_point2[i];
		}
	}
	if(startpointlonlat==""){
		alert("请选择起点");
		return false;
	}
	if(endpointlonlat==""){
		alert("请终点");
		return false;
	}
	return true;
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
	 startPoint=$$("#startLonlat").val();
	$$("#startLonlat").val($$("#endLonlat").val());
	$$("#endLonlat").val(startPoint);
}*/

function GetLatLon_transfer() {
	var trim_startPoint=trim($$("#startPoint").val());
	var trim_endPoint=trim($$("#endPoint").val());
	if(trim_startPoint==""){
		alert("请输入起点");
		return false;
	}
	if(trim_startPoint==""){
		alert("请输入终点");
		return false;
	}
	return true;
}
//交换起终点
function ChangeTextOnClick(){
	
	var startPoint=$$("#startPoint").val();
	$$("#startPoint").val($$("#endPoint").val());
	$$("#endPoint").val(startPoint);
	
}