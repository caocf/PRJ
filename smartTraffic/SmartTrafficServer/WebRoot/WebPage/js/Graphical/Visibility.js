var map;
var lineFeature;
var Gloval_areadata=new Array();
$$(document).ready(function() {
	setMapDivHeigth("#main_content");
	map = new XMap("simpleMap",2);//天地图
	setCenter(getCenter(),10);
	map.addPanZoomBar();
	wmap.setVisibility(true);
	GetRealInformationList();
	SelectOption_live("TrafficValue_select",90);
	queryMergedConRticByTime();
});
function GetRealInformationList(){
	Gloval_areadata[0]=new Array();
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
				Gloval_areadata[0][0]="全路网";
				Gloval_areadata[0][1]=color;
				Gloval_areadata[0][2]=toDecimal2(data.tpi);
				$$("#TrafficValue_value").css("color",color);
				$$("#TrafficValue_value").text(toDecimal2(data.tpi));
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
			var arealist=["南湖区","秀洲区","内环内","中环内","三环内"];
			for(var i=0;i<arealist.length;i++){
				Gloval_areadata[i+1]=new Array();
				var areanum=0;
				Gloval_areadata[i+1][0]="南湖区";
				if(list.length>0){
					for(var j=0;j<list.length;j++){
						if(list[j].adminName==arealist[i]){
							if(list[j].tpiLevel.indexOf("畅通")!=-1){
								Gloval_areadata[i+1][1]="#5c9f07";
							}else if(list[j].tpiLevel.indexOf("缓行")!=-1){
								Gloval_areadata[i+1][1]="#ff810b";
							}else{
								Gloval_areadata[i+1][1]="#red";
							}
							Gloval_areadata[i+1][2]=toDecimal2(list[j].tpi);
							areanum=1;
							break;
						}
					}
				}
				if(areanum==0&&list2.length>0){
						for(var j=0;j<list2.length;j++){
							if(list2[j].areaName==arealist[i]){
								if(list2[j].tpiLevel.indexOf("畅通")!=-1){
									Gloval_areadata[i+1][1]="#5c9f07";
								}else if(list2[j].tpiLevel.indexOf("拥堵")!=-1){
									Gloval_areadata[i+1][1]="#ff810b";
								}else{
									Gloval_areadata[i+1][1]="#red";
								}
								Gloval_areadata[i+1][2]=toDecimal2(list2[j].tpi);
								areanum=1;
								break;
							}
						}
					
				}
				if(areanum==0){
					Gloval_areadata[i+1][1]="#5c9f07";
					Gloval_areadata[i+1][2]="无";
				}
			}
			
		}
	});
	
}
function ChangeValue(id){
	$$("#TrafficValue_value").css("color",Gloval_areadata[id][1]);
	$$("#TrafficValue_value").text(Gloval_areadata[id][2]);
}


//下拉框
function SelectOption_live(div_id,width){
	$$("#"+div_id).css("width",width+"px");
	$$("#"+div_id+" .CRselectValue").css("width",(width-30)+"px");
	$$("#"+div_id+" .CRselectBoxOptions").css("width",(width-42)+"px");
	$$("#"+div_id+" .CRselectValue").click(function(){
		var cbshow=$$("#"+div_id+" .CRselectBoxOptions").css("display");
		$$(".CRselectBoxOptions").hide();
		$$(this).blur();
		if(cbshow=="none")
			$$("#"+div_id+" .CRselectBoxOptions").show();
		else
			$$("#"+div_id+" .CRselectBoxOptions").hide();
		return false;
	});
	$$("#"+div_id+" .CRselectBoxItem a").click(function(){
		$$(this).blur();
		var value = $$(this).attr("rel");
		var txt = $$(this).text();
		$$("#"+div_id+" .abc").val(value);
		
		//ChangeValue(value);
		
		$$("#"+div_id+" .abc_CRtext").val(txt);
		$$("#"+div_id+" .CRselectValue").text(txt);
		$$("#"+div_id+" .CRselectBoxItem a").removeClass("selected");
		$$(this).addClass("selected");
		$$("#"+div_id+" .CRselectBoxOptions").hide();
		return false;
	});
	/*点击任何地方关闭层*/
	$$(document).click(function(event){
		if( $$(event.target).attr("class") != "CRselectBox" ){
			$$(".CRselectBoxOptions").hide();
		}
	});

}
var speed=150;//数值越大速度越慢 
var RticByTimeScroll=setInterval(LiveTrafficRun,speed); 
//获取实时信息
function queryMergedConRticByTime(){
	$$.ajax( {
		url : 'queryMergedConRticByTime',
		type : "post",
		dataType : "json",
		success : function(data) {
			if(JSON.parse(data.result).msg!="succ"){
				alert(JSON.parse(data.result).msg);
				return;
			}
			var list = JSON.parse(data.result).mergedConRtics;
			var time=JSON.parse(data.result).recordTime;
			var html="";
			var Length=list.length;
			for(var i=0;i<Length;i++){
				html+="<p style='padding: 5px 10px;font-size: 14px;width:450px;float:left;'><label style='font-weight: bold;color:#2746a1'>"+list[i].roadName+"</label>&nbsp;从<label style='color:#0ca202;'>"+
						list[i].roadSections[0].rsStart+"</label>到<label style='color: #f51b29;'>"+list[i].roadSections[0].rsEnd+":</label><label style='font-weight: bold;color:#444444'>"+list[i].roadSections[0].desc+"</label>";
				for(var j=1;j<list[i].roadSections.length;j++){
					html+=";从<label style='color:#0ca202;'>"+list[i].roadSections[j].rsStart+
					"</label>到<label style='color: #f51b29;'>"+list[i].roadSections[j].rsEnd+list[i].roadSections[j].desc+"</label>";
				}
				
				html+="<label>("+time+")</label>";
				html+="</p>";
			}
			
			$$("#RticByTime").append(html);
		}
	});
} 
//滚动实时信息
function LiveTrafficRun() {
	if(document.getElementById("RticByTime").scrollTop>=50) document.getElementById("RticByTime").scrollTop=0;
	document.getElementById("RticByTime").scrollTop += 1;
	//setTimeout("LiveTrafficRun()", 200);
};
function MyMarOut(){
	RticByTimeScroll=setInterval(LiveTrafficRun,speed);	
}
