var stationId="";
$(document).ready(function(data){
	$("#top_text").text("公路站");
	if($("#xxxxId").val()!=""&&$("#xxxxId").val()!="null"){
		showRoadStationDetail();
	}
});
/*function loadDetailMap(){
	var map=new XMap("station_map",3); 
	map.setMapCenter("120.76042769896,30.773992239582",8);
	map.setScaleVisible(false)//设置比例尺不可见
	map.setOverviewVisible(false,false);//鹰眼不可见
	
}*/
function showRoadStationDetail(){
	$.ajax({
		url:'glzxxlist/glzxxxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'id':$("#xxxxId").val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list0=data.result.list[0];
				var list1=data.result.list[1];
				stationId=list0.id;
				$("#station_title_label").text("基本信息");
				$("#station_name").text(list0.name);
				$("#glzdm").text(judgeIsNull(list0.code));
				$("#glzmc").text(judgeIsNull(list0.name));
				$("#gljg").text(judgeIsNull(list0.gljgmc));
				$("#year").text(judgeIsNull(list1.year));
				$("#greenArea").text(judgeIsNull(list1.greenArea));
				$("#workArea").text(judgeIsNull(list1.workArea));
				$("#liveArea").text(judgeIsNull(list1.liveArea));
				$("#totalArea").text(judgeIsNull(list1.totalArea));
				$("#issuedTarget").text(judgeIsNull(list1.issuedTarget));
				$("#issuedPercent").text(judgeIsNull(list1.issuedPercent));
				$("#fightTarget").text(judgeIsNull(list1.fightTarget));
				$("#fightPercent").text(judgeIsNull(list1.fightPercent))
				$("#provinceTarget").text(judgeIsNull(list1.provinceTarget));
				$("#countryTarget").text(judgeIsNull(list1.countryTarget));
				$("#firstSeasonTarget").text(judgeIsNull(list1.firstSeasonTarget));
				$("#secondSeasonTarget").text(judgeIsNull(list1.secondSeasonTarget));
				$("#thirdSeasonTarget").text(judgeIsNull(list1.thirdSeasonTarget));
				$("#fourthSeasonTarget").text(judgeIsNull(list1.fourthSeasonTarget));
				$("#maintainTotal").text(judgeIsNull(list1.maintainTotal));
				$("#bridgeCount").text(judgeIsNull(list1.bridgeCount));
				$("#bridgeLong").text(judgeIsNull(list1.bridgeLong));
				$("#tunnelCount").text(judgeIsNull(list1.tunnelCount));
				$("#tunnelLong").text(judgeIsNull(list1.tunnelLong));
				$("#greenLong").text(judgeIsNull(list1.greenLong));
				$("#greenPercent").text(judgeIsNull(list1.greenPercent));
				$("#countryRoad").text(judgeIsNull(list1.countryRoad));
				$("#provinceRoad").text(judgeIsNull(list1.provinceRoad));
				$("#cityRoad").text(judgeIsNull(list1.cityRoad));
				$("#mainGoodIndex").text(judgeIsNull(list1.mainGoodIndex));
				$("#mainGoodRate").text(judgeIsNull(list1.mainGoodRate));
				$("#subGoodIndex").text(judgeIsNull(list1.subGoodIndex));
				$("#subGoodRate").text(judgeIsNull(list1.subGoodRate));
				$("#machineTotal").text(judgeIsNull(list1.machineTotal));
				$("#machineMoney").text(judgeIsNull(list1.machineMoney));
				$("#tractorCar").text(judgeIsNull(list1.tractorCar));
				$("#rollerCar").text(judgeIsNull(list1.rollerCar));
				$("#smallTruckCar").text(judgeIsNull(list1.smallTruckCar));
				$("#asphaltCar").text(judgeIsNull(list1.asphaltCar));
				$("#loadCar").text(judgeIsNull(list1.loadCar));
				$("#waterCar").text(judgeIsNull(list1.waterCar));
				$("#sweepCar").text(judgeIsNull(list1.sweepCar));
				$("#weedCar").text(judgeIsNull(list1.weedCar));
				$("#mixerCar").text(judgeIsNull(list1.mixerCar));
				$("#fillSewCar").text(judgeIsNull(list1.fillSewCar));
				$("#inspectCar").text(judgeIsNull(list1.inspectCar));
				$("#totalWorker").text(judgeIsNull(list1.totalWorker));
				$("#fixedMoney").text(judgeIsNull(list1.fixedMoney));
				$("#fixedWorker").text(judgeIsNull(list1.fixedWorker));
				$("#tempWorker").text(judgeIsNull(list1.tempWorker));
				$("#manWorker").text(judgeIsNull(list1.manWorker));
				$("#womanWorker").text(judgeIsNull(list1.womanWorker));
				$("#thirtyFiveDown").text(judgeIsNull(list1.thirtyFiveDown));
				$("#thirtyFiveToFortyFive").text(judgeIsNull(list1.thirtyFiveToFortyFive));
				$("#fortyFiveUp").text(judgeIsNull(list1.fortyFiveUp));
				$("#college").text(judgeIsNull(list1.college));
				$("#highSchool").text(judgeIsNull(list1.highSchool));
				$("#middleSchool").text(judgeIsNull(list1.middleSchool));
				$("#partyCount").text(judgeIsNull(list1.partyCount));
				$("#memberCount").text(judgeIsNull(list1.memberCount));
				$("picture").text(judgeIsNull(list0.picId));
				$("#remark").text(judgeIsNull(list1.remark));
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
	//loadDetailMap();//添加地图
}
function showRoadStationBy(thisop,num){
	$(".left_select_li").attr("class","left_no_select_li");
	thisop.className="left_select_li";
	if(num==1){
		window.location.href=$("#basePath").val()+"page/roadstation/RoadStationDetail.jsp?xxxxId="+$("#xxxxId").val()+"&stationId="+$("#stationId").val();
	}else{
		window.location.href=$("#basePath").val()+"page/roadstation/OtherStationInfo.jsp?typeId="+num+"&stationId="+$("#stationId").val()+"&xxxxId="+$("#xxxxId").val();
	}
}
function closeGoToStation(){
	window.location.href=$("#basePath").val()+"page/roadstation/RoadStation.jsp";
}