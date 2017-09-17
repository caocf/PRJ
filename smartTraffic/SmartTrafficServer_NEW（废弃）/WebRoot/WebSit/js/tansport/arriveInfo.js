var startStation="上海";//起点站
var arriveStation="北京";//终点站
var startDate;//出发时间
$$(document).ready(function() {
	GetTimeList();
	$$(".layout3").css("visibility","hidden");
	$$(".layout4").css("visibility","hidden");
	$$(".layout5").css("visibility","hidden");
	$$(".layout6").css("visibility","hidden");
	var t = new Date();
	var maxDate = [t.getFullYear(), t.getMonth()+1, t.getDate()].join('-');
//	$$("#leaveTime").val(formatDate(maxDate));
	
});
function ShowDataBySearch(actionName, selectedPage) {
	if(!SearchContentCheck("stationName")){
		return;
}
	$$(".layout3").css("visibility","");
	$$(".layout4").css("visibility","");
	$$(".layout5").css("visibility","");
	$$(".layout6").css("visibility","");
	ShowDataByPage(actionName, selectedPage);

}
function ShowDataByPage(actionName, selectedPage) {
	SelectOneTimeByCSS2(startDate);
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'stationName':$$("#stationName").val(),
			'num':PAGESIZE,
			'page' : selectedPage
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			if (data.busStationRealInfos.length == 0) {
				$$("#alltotal").text("0");
				TableIsNull();
			} else {
				var list = data.busStationRealInfos;
				appendToTable(list,list.length);// 跳转页码的方法名称
				gotoPageMethodName = "gotoPageNo";
				var number=CountItemTrByPageNum(list.length,PAGESIZE);
				printPage(number, selectedPage, 'ShowDataByPage',
						actionName, gotoPageMethodName);
			}
		},
		complete : function() {
			CloseLoadingDiv();// 请求执行完后隐藏loading图标。
	}
	});

}
function TableIsNull(){
	newTr = $$("<tr class='addTr'></tr>");
	newTr.append($$("<td colspan='7'><label class='tishi'>"+NODATA+"</label></td>"));
	$$(".listTable2").append(newTr);
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
		ShowDataByPage(actionName, pageNo);
	}
}
function appendToTable(list, listLength){
	$$("#alltotal").text(listLength);
	for ( var i = 0; i < listLength; i++) {
		newTr = $$("<tr class='addTr'></tr>");
		newTr.append($$("<td>&nbsp;</td>"));
		newTr.append($$("<td class='td_first'>"+DateIsNull(list[i].carNumber,'--')+"</td>"));
		newTr.append($$("<td><img src='WebSit/image/publicTraffic/startcity.png'/>"+DateIsNull(list[i].startStation,'--')+"</td>"));
		newTr.append($$("<td><img src='WebSit/image/publicTraffic/endcity.png'/>"+DateIsNull(list[i].endStation,'--')+"</td>"));
		newTr.append($$("<td>"+DateIsNull(list[i].company,'--')+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].planStartTime,'--')+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].planEndTime,'--')+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].realStartTime,'--')+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].realEndTime,'--')+"</td>"));
		if(list[i].carKind=1){
			newTr.append($$("<td>"+DateIsNull("大客车",'--')+"</td>"));
		}else if(list[i].carKind=2){
			newTr.append($$("<td>"+DateIsNull("中客车",'--')+"</td>"));
		}else if(list[i].carKind=3){
			newTr.append($$("<td>"+DateIsNull("小客车",'--')+"</td>"));
		}
		if(list[i].status=1){
			newTr.append($$("<td>"+DateIsNull("待发",'--')+"</td>"));
		}else if(list[i].status=2){
			newTr.append($$("<td>"+DateIsNull("发车",'--')+"</td>"));
		}else if(list[i].status=3){
			newTr.append($$("<td>"+DateIsNull("到站",'--')+"</td>"));
		}else if(list[i].status=4){
			newTr.append($$("<td>"+DateIsNull("延误",'--')+"</td>"));
		}else{
			newTr.append($$("<td>"+DateIsNull("取消",'--')+"</td>"));
		}
		
		
		$$(".listTable2").append(newTr);
	}
}
function SelectOneTime(thisop,time){
	SelectOneTimeByCSS(thisop);//点击的样式
	startDate=time;
	ShowDataByPage('GetBusCodeByStationName', 1);
	
}
function TimeOfArrival(reachTime,leaveTime){
	var days=Math.floor(formatDate(HourTimeFormat(reachTime),"yyyyMMdd"))-Math.floor(formatDate(HourTimeFormat(leaveTime),"yyyyMMdd")); 
	days=Math.floor(days);
	var daysString="";
	if(days==0){
		daysString="当日到达";
	}else if(days==1){
		daysString="次日到达";
	}else {
		daysString=days+"日到达";
	}
	return daysString;
	
}
function ChangeText(){
	var startCity=$$("#startCity").val();
	$$("#startCity").val($$("#endCity").val());
	$$("#endCity").val(startCity);
}

//getMaxDate生成客户端本地时间
function getMaxDate(){
	var t = new Date();
	var maxDate = [t.getFullYear(), t.getMonth()+1, t.getDate()+TIMELIMIT-1].join('-');
	return maxDate; 
}
//getMinDate生成客户端本地时间
function getMinDate(){
	var t = new Date();
	var maxDate = [t.getFullYear(), t.getMonth()+1, t.getDate()].join('-');
	return maxDate; 
}