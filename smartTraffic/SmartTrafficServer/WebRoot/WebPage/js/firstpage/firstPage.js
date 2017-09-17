var Gloval_areadata=new Array();
$$(document).ready(function() {
	wmap=new XMap("simpleMap",2);
	wmap.addPanZoomBar(); 
	wmap.setCenter("120.737967,30.760247",8);
	wmap.setVisibility(true);
	document.getElementById("main_layout3_img2").innerHTML=document.getElementById("main_layout3_img1").innerHTML; 
	GetRealInformationList();
});
var speed=30;//数值越大速度越慢 
var MyMar=setInterval(Marquee,speed); 
function Marquee(){ 
	if(document.getElementById("main_layout3_img2").offsetWidth-document.getElementById("main_layout3_content").scrollLeft<=0) 
	{
		document.getElementById("main_layout3_content").scrollLeft-=document.getElementById("main_layout3_img1").offsetWidth; 
	}		
	else{ 
		document.getElementById("main_layout3_content").scrollLeft++ ;
	} 
} 
function MyMarOut(){
	MyMar=setInterval(Marquee,speed);	
}
/*function SearchPOI(code){
	window.location.href=$$("#basePath").val()+"WebPage/page/Graphical/pot.jsp?code="+code;
}
function SearchPOIkind(){
	if(!SearchContentCheck("poiKind")){
		return;
	}
	window.location.href=$$("#basePath").val()+"WebPage/page/Graphical/pot.jsp?poiKind="+$$("#poiKind").val();
}*/

function openToLargeImage(imgSrc){
	var bh = $$("body").height(); 
	var bw = $$("body").width(); 
	$$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		});
	screenResize();
	var leftMargin=Number(myWidth-1000)/2;
	$$("#ShowPopUpDiv").css("left",leftMargin+"px");
	$$("#img_ShowPopUpDiv").html("<img src="+basePath+"WebPage/image/firstpage/"+imgSrc+" />");
$$("#ShowPopUpDiv").show(); 	

}
//正在加载中的隐藏
function CloseLargeImage() { 
		$$("#fullbg,#ShowPopUpDiv").hide(); 
	} 

/*function ChangeDiv(id,thisop){
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
}*/
//搜索公交
function SearchBus(type){
	if(type==1){
		if(!SearchContentCheck("SearchBusContent1")){
			return;
		}
		window.location.href=$$("#basePath").val()+"WebPage/page/publicTraffic/wisdom/road_search.jsp?searchcontent="+$$("#SearchBusContent1").val();
	}if(type==2){
		if(!SearchContentCheck("SearchBusContent2")){
			return;
		}
		window.location.href=$$("#basePath").val()+"WebPage/page/publicTraffic/wisdom/bus_station.jsp?searchcontent="+$$("#SearchBusContent2").val();
	}if(type==3){
		if(GetLatLon_transfer()){
			window.location.href=$$("#basePath").val()+"WebPage/page/publicTraffic/wisdom/bus_transfer.jsp?startPoint="+$$("#startPoint").val()+"&endPoint="+$$("#endPoint").val();
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