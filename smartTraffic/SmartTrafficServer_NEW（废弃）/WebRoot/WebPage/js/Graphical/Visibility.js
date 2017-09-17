var map;
var lineFeature;
var Gloval_areadata=new Array();
$$(document).ready(function() {
	map = new XMap("simpleMap",2);//天地图
	setCenter(getCenter(),10);
	map.addPanZoomBar();
	wmap.setVisibility(true);
	GetRealInformationList();
	SelectOption_live("TrafficValue_select",90);
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
		
		ChangeValue(value);
		
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