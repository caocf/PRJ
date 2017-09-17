var startStation="嘉兴";//起点站
var arriveStation="北京";//终点站
var leaveTime="";//出发时间
var railwayType="";//发车类型	

$$(document).ready(function() {
	GetTimeList();
	$$(".layout3").css("visibility","hidden");
	$$(".layout4").css("visibility","hidden");
	$$(".layout5").css("visibility","hidden");
	$$(".layout6").css("visibility","hidden");
	//ShowDataByPage("GetTrainSchedule",1);
	//SelectOption("layout1_select01",105);
	//SelectOption("layout1_select02",140);
});
function ShowDataBySearch(actionName, selectedPage) {
	$$(".layout3").css("visibility","");
	$$(".layout4").css("visibility","");
	$$(".layout5").css("visibility","");
	$$(".layout6").css("visibility","");
	var getid=document.getElementById("startCity");
	if(getid.defaultValue!=getid.value){
		startStation=getid.value;
	}
	getid=document.getElementById("endCity");
	if(getid.defaultValue!=getid.value){
		arriveStation=getid.value;
	}
	$$(".layout4_label1").html(startStation+"--&gt;"+arriveStation);
	var opts=document.getElementsByName("leaveTime");
	leaveTime="";
	for(i=0;i<opts.length;i++){
        if(opts[i].checked==true){      	
        	leaveTime+="1";        
        }else{
        	leaveTime+="0";        
        }
    }
	railwayType="";
	opts=document.getElementsByName("type");
	for(i=0;i<opts.length;i++){
        if(opts[i].checked==true){      	
        	railwayType+="1";        
        }else{
        	railwayType+="0";        
        }
    }
	ShowDataByPage(actionName, selectedPage);
	

}
function ShowDataByPage(actionName, selectedPage) {
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'startStation':startStation,
			'arriveStation':arriveStation,
			'page' : selectedPage,
			'num':PAGESIZE,
			'leaveTime':leaveTime,
			'type':railwayType
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			if (data.schedules.length == 0) {
				$$("#alltotal").text("0");
				TableIsNull();
			} else {
				var list = data.schedules;
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
		newTr.append($$("<td class='td_first'>"+DateIsNull(list[i].trainNumber,'--')+"</td>"));
		var src1,src2;
		if(list[i].startKind==0){
			src1="WebSit/image/publicTraffic/startcity.png";
		}else{
			src1="WebSit/image/publicTraffic/pass.png";
		}
		if(list[i].endKind==0){
			src2="WebSit/image/publicTraffic/endcity.png";
		}else{
			src2="WebSit/image/publicTraffic/pass.png";
		}
		newTr.append($$("<td><img src='"+src1+"'/>"+DateIsNull(list[i].startCity,'--')+"<br/>" +
				"<img src='"+src2+"'/>"+DateIsNull(list[i].endCity,'--')+"</td>"));
		newTr.append($$("<td>"+TimeToSubstring(list[i].leaveTime,11,16)+"<br/><label class='label_grey'>"+TimeToSubstring(list[i].reachTime,11,16)+"</label></td>"));
		newTr.append($$("<td>"+GetCusTime(list[i].costTime,'--')+"<br/><label class='label_grey'>"+TimeOfArrival(list[i].reachTime,list[i].leaveTime)+"</label></td>"));
		newTr.append($$("<td>--</td>"));
		newTr.append($$("<td>--</td>"));
		$$(".listTable2").append(newTr);
	}
}
function SelectOneTime(thisop,time){
	SelectOneTimeByCSS(thisop);//点击的样式
	ShowDataByPage('GetTrainSchedule', 1);
	
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
function ChangeText(tishitext){
	var startCity=$$("#startCity").val();
	var endCity=$$("#endCity").val();
	$$("#startCity").val(endCity);
	$$("#endCity").val(startCity);
	if(endCity!=tishitext){
		$$("#startCity").css("color","#000");
	}else{
		$$("#startCity").css("color","#999");
	}
	if(startCity!=tishitext){
		$$("#endCity").css("color","#000");
	}else{
		$$("#endCity").css("color","#999");
	}
}