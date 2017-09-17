var typeId="";
var stationId="";
var select_date="";
$(document).ready(function(){
	typeId=$("#typeId").val();
	$("#left_no_select_li"+typeId).attr("class","left_select_li");
	stationId=$("#stationId").val();
	$("#top_text").text("公路站");
	if(typeId==2){
		showOtherInfoByTypeId('glzxxlist/dwrylist',1);
	}else if(typeId==3){
		showOtherInfoByTypeId('glzxxlist/gyldgklist',1);
	}else if(typeId==4){
		showOtherInfoByTypeId('glzxxlist/glzqlgklist',1);
	}else if(typeId==5){
		showOtherInfoByTypeId('glzxxlist/glzhdgklist',1);
	}else if(typeId==6){
		haveYearInfo('glzxxlist/workdatelist');
		showOtherInfoByTypeId('glzxxlist/yscqktjxxlist',1);
	}else if(typeId==7){
		haveYearInfo('glzxxlist/glxcdatelist');
		showOtherInfoByTypeId('glzxxlist/glxclist',1);
	}
});
function haveYearInfo(actionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'stationId':stationId
		},
		success:function(data){
			var time=data.result.list;
			for(var i=time.length-1;i>=0;i--){
				var newLi=$("<li onclick=findYearStatistic('"+time[i].id_string+"')>"+time[i].name+"</li>");
				$(".year_down_ul").append(newLi);
			}
		}
	});
	
}
/**
 * 显示其他信息
 * @param actionName
 * @param selectedPage
 */
function showOtherInfoByTypeId(actionName,selectedPage){
	$("#pageDiv").hide();
	$(".year_select_div").hide();
	$("#stationMember,#manageRoad,#bridge,#culvert,#monthStatistic,#roadInspect").hide();
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':10,
			'page':selectedPage,
			'stationId':stationId,
			'workDate':select_date
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				//$("#station_name").text()
				$("#station_name").text(data.result.objs);
				if(typeId==2){
					$("#stationMember").show();
					$("#station_title_label").text("公路站成员列表");
				}else if(typeId==3){
					$("#manageRoad").show();
					$("#station_title_label").text("管养路段列表");
				}else if(typeId==4){
					$("#bridge").show();
					$("#station_title_label").text("桥梁列表");
				}else if(typeId==5){
					$("#culvert").show();
					$("#station_title_label").text("涵洞列表");
				}else if(typeId==6){
					$(".year_select_div").show();
					$("#monthStatistic").show();
					$("#station_title_label").text("月生产统计信息列表");
				}else if(typeId==7){
					$(".year_select_div").show();
					$("#roadInspect").show();
					$("#station_title_label").text("公路巡查信息列表");
				}
				$(".addTr").empty();
				$(".addTr").empty();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showOtherInfoByTypeId',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
			}
		},
		complete : function(){
			CloseLoadingDiv();
		}
	});
}
function appendToTable(list){
	if(typeId==2){
		for(var i=0;i<list.length;i++){
			if(i%2==0){
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].workerName)+"&nbsp;</td>"));//员工名称
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].workType)+"&nbsp;</td>"));//员工类型
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].post)+"&nbsp;</td>"));//职务
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].mobilephone)+"&nbsp;</td>"));//手机号
				if(list[i].workStatus==0){
					newTr.append($("<td style='background:#f9f9f9'>已离职&nbsp;</td>"));//状态
				}else{
					newTr.append($("<td style='background:#f9f9f9'>在职&nbsp;</td>"));//状态
				}
				
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeworkerDetail('"+list[i].id+"')>查看</a></td>"));
				$("#stationMember").append(newTr);
			}else{
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td>"+judgeIsNull(list[i].workerName)+"&nbsp;</td>"));//员工名称
				newTr.append($("<td>"+judgeIsNull(list[i].workType)+"&nbsp;</td>"));//员工类型
				newTr.append($("<td>"+judgeIsNull(list[i].post)+"&nbsp;</td>"));//职务
				newTr.append($("<td>"+judgeIsNull(list[i].mobilephone)+"&nbsp;</td>"));//手机号
				if(list[i].workStatus==0){
					newTr.append($("<td>已离职&nbsp;</td>"));//状态
				}else{
					newTr.append($("<td>在职&nbsp;</td>"));//状态
				}
				
				newTr.append($("<td><a class='Operate' onclick=seeworkerDetail('"+list[i].id+"')>查看</a></td>"));
				$("#stationMember").append(newTr);
			}
		}
	}else if(typeId==3){
		for(var i=0;i<list.length;i++){
			if(i%2==0){
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].code)+"&nbsp;</td>"));//路线编号
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].sectionName)+"&nbsp;</td>"));//路线名称
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull("z")+"&nbsp;</td>"));//所属行政区域
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].manageLength)+"&nbsp;</td>"));//管养里程
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].startStake)+"&nbsp;</td>"));//路段起点桩号
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].endStake)+"&nbsp;</td>"));//路段终点桩号
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeDetail('"+list[i].id+"')>查看</a>" +
						"<a class='Operate' onclick=seeDetailInMap('"+list[i].id+"')>地图</a></td>"));
				$("#manageRoad").append(newTr);
			}else{
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td>"+judgeIsNull(list[i].code)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].sectionName)+"&nbsp;</td>"));
				newTr.append($("<td >"+judgeIsNull("z")+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].manageLength)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].startStake)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].endStake)+"&nbsp;</td>"));
				newTr.append($("<td><a class='Operate' onclick=seeDetail('"+list[i].id+"')>查看</a>" +
						"<a class='Operate' onclick=seeDetailInMap('"+list[i].id+"')>地图</a></td>"));
				$("#manageRoad").append(newTr);
			}
		}
	}else if(typeId==4){
		for(var i=0;i<list.length;i++){
			if(i%2==0){
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].code)+"&nbsp;</td>"));//桥梁代码
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].name)+"&nbsp;</td>"));//桥梁名称
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].gydwmc)+"&nbsp;</td>"));//管养路段
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].stake)+"&nbsp;</td>"));//桥梁中心桩号
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qlqc)+"&nbsp;</td>"));//桥梁全长
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeDetail('"+list[i].id+"')>查看</a>" +
						"<a class='Operate' onclick=seeDetailInMap('"+list[i].id+"')>地图</a></td>"));
				$("#bridge").append(newTr);
			}else{
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td>"+judgeIsNull(list[i].code)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].name)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].gydwmc)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].stake)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].qlqc)+"&nbsp;</td>"));
				newTr.append($("<td><a class='Operate' onclick=seeDetail('"+list[i].id+"')>查看</a>" +
						"<a class='Operate' onclick=seeDetailInMap('"+list[i].id+"')>地图</a></td>"));
				$("#bridge").append(newTr);
			}
		}
	}else if(typeId==5){
		for(var i=0;i<list.length;i++){
			if(i%2==0){
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].code)+"&nbsp;</td>"));//涵洞代码
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].stake)+"&nbsp;</td>"));//涵洞中心桩号
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].type)+"&nbsp;</td>"));//涵洞类型
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].height)+"&nbsp;</td>"));//涵洞净高
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].culvertLong)+"&nbsp;</td>"));//涵洞全长
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].width)+"&nbsp;</td>"));//涵洞全宽
				$("#culvert").append(newTr);
			}else{
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td>"+judgeIsNull(list[i].code)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].stake)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].type)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].height)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].culvertLong)+"&nbsp;</td>"));
				newTr.append($("<td>"+judgeIsNull(list[i].width)+"&nbsp;</td>"));
				$("#culvert").append(newTr);
			}
		}
	}else if(typeId==6){
		for(var i=0;i<list.length;i++){
			if(i%2==0){
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].workdateString)+"&nbsp;</td>"));//统计年月
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].dutyPercent)+"&nbsp;</td>"));//出勤率
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].producePercent)+"&nbsp;</td>"));//直接生产率
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].planFund)+"&nbsp;</td>"));//计划经费
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].actualFund)+"&nbsp;</td>"));//实际经费
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qualityNorm)+"&nbsp;</td>"));//质量指标
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].actualQuality)+"&nbsp;</td>"));//实际质量
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qualityPercent)+"&nbsp;</td>"));//质量完成率
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].planAmount)+"&nbsp;</td>"));//计划工程量
				$("#monthStatistic").append(newTr);
			}else{
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td>"+judgeIsNull(list[i].workdateString)+"&nbsp;</td>"));//统计年月
				newTr.append($("<td>"+judgeIsNull(list[i].dutyPercent)+"&nbsp;</td>"));//出勤率
				newTr.append($("<td>"+judgeIsNull(list[i].producePercent)+"&nbsp;</td>"));//直接生产率
				newTr.append($("<td>"+judgeIsNull(list[i].planFund)+"&nbsp;</td>"));//计划经费
				newTr.append($("<td>"+judgeIsNull(list[i].actualFund)+"&nbsp;</td>"));//实际经费
				newTr.append($("<td>"+judgeIsNull(list[i].qualityNorm)+"&nbsp;</td>"));//质量指标
				newTr.append($("<td>"+judgeIsNull(list[i].actualQuality)+"&nbsp;</td>"));//实际质量
				newTr.append($("<td>"+judgeIsNull(list[i].qualityPercent)+"&nbsp;</td>"));//质量完成率
				newTr.append($("<td>"+judgeIsNull(list[i].planAmount)+"&nbsp;</td>"));//计划工程量
				$("#monthStatistic").append(newTr);
			}
		}
	}else if(typeId==7){
		for(var i=0;i<list.length;i++){
			if(i%2==0){
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(DateTimeFormat(list[i].inspectDate))+"&nbsp;</td>"));//巡查日期
				if(list[i].weather=="sunny"){
					newTr.append($("<td style='background:#f9f9f9'>晴天&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="rainy"){
					newTr.append($("<td style='background:#f9f9f9'>雨&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="cloudy"){
					newTr.append($("<td style='background:#f9f9f9'>多云&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="clouded"){
					newTr.append($("<td style='background:#f9f9f9'>阴&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="snow"){
					newTr.append($("<td style='background:#f9f9f9'>雪&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="typhoon"){
					newTr.append($("<td style='background:#f9f9f9'>台风&nbsp;</td>"));//天气状况
				}else{
					newTr.append($("<td style='background:#f9f9f9'>&nbsp;</td>"));//天气状况
				}
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].content)+"&nbsp;</td>"));//巡查记录
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].inspector)+"&nbsp;</td>"));//巡查人
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].handlerSignature)+"&nbsp;</td>"));//处理人
				newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].handleDate)+"&nbsp;</td>"));//处理日期
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seePatrolDetail('"+list[i].id+"')>查看</a></td>"));
				$("#roadInspect").append(newTr);
			}else{
				var newTr=$("<tr class='addTr'></tr>");
				newTr.append($("<td>"+judgeIsNull(DateTimeFormat(list[i].inspectDate))+"&nbsp;</td>"));//巡查日期
				if(list[i].weather=="sunny"){
					newTr.append($("<td>晴天&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="rainy"){
					newTr.append($("<td>雨&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="cloudy"){
					newTr.append($("<td>多云&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="clouded"){
					newTr.append($("<td>阴&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="snow"){
					newTr.append($("<td>雪&nbsp;</td>"));//天气状况
				}else if(list[i].weather=="typhoon"){
					newTr.append($("<td>台风&nbsp;</td>"));//天气状况
				}else{
					newTr.append($("<td>&nbsp;</td>"));//天气状况
				}
				newTr.append($("<td>"+judgeIsNull(list[i].content)+"&nbsp;</td>"));//巡查记录
				newTr.append($("<td>"+judgeIsNull(list[i].inspector)+"&nbsp;</td>"));//巡查人
				newTr.append($("<td>"+judgeIsNull(list[i].handlerSignature)+"&nbsp;</td>"));//处理人
				newTr.append($("<td>"+judgeIsNull(list[i].handleDate)+"&nbsp;</td>"));//处理日期
				newTr.append($("<td><a class='Operate' onclick=seePatrolDetail('"+list[i].id+"')>查看</a></td>"));
				$("#roadInspect").append(newTr);
			}
		}
	}
}
function TableIsNull(){
	if(typeId==2){
		var newTr = $("<tr class='addTr'><td colspan='6' align='center'>暂无相关数据</td></tr>");
		$("#stationMember").append(newTr);
	}else if(typeId==3){
		var newTr = $("<tr class='addTr'><td colspan='7' align='center'>暂无相关数据</td></tr>");
		$("#manageRoad").append(newTr);
	}else if(typeId==4){
		var newTr = $("<tr class='addTr'><td colspan='6' align='center'>暂无相关数据</td></tr>");
		$("#bridge").append(newTr);
	}else if(typeId==5){
		var newTr = $("<tr class='addTr'><td colspan='6' align='center'>暂无相关数据</td></tr>");
		$("#culvert").append(newTr);
	}else if(typeId==6){
		var newTr = $("<tr class='addTr'><td colspan='9' align='center'>暂无相关数据</td></tr>");
		$("#monthStatistic").append(newTr);
	}else if(typeId==7){
		var newTr = $("<tr class='addTr'><td colspan='7' align='center'>暂无相关数据</td></tr>");
		$("#roadInspect").append(newTr);
	}
	$("#pageDiv").hide();
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
		showOtherInfoByTypeId(actionName, pageNo);
	}
}
function showRoadStationBy(thisop,num){
	$(".left_select_li").attr("class","left_no_select_li");
	thisop.className="left_select_li";
	if(num==1){
		window.location.href=$("#basePath").val()+"page/roadstation/RoadStationDetail.jsp?stationId="+$("#stationId").val()+"&xxxxId="+$("#xxxxId").val();
	}else{
		window.location.href=$("#basePath").val()+"page/roadstation/OtherStationInfo.jsp?stationId="+$("#stationId").val()+"&typeId="+num+"&xxxxId="+$("#xxxxId").val();
	}
}
/**
 * 看人员详情
 * @param id
 */
function seeworkerDetail(id){
	$.ajax({
		url:'glzxxlist/dwryxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'id':id
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				$("#workcode").text("");
				$("#workerName").text(judgeIsNull(list.workerName));
				$("#workType").text(judgeIsNull(list.workType));
				$("#post").text(judgeIsNull(list.post));
				if(list.sex==0){
					$("#sex").text("女");
				}else{
					$("#sex").text("男");
				}
				if(list.marriage==1){
					$("#marriage").text("是");
				}else{
					$("#marriage").text("否");
				}
				$("#people").text(judgeIsNull(list.people));
				$("#political").text(judgeIsNull(list.political));
				$("#birthplace").text(judgeIsNull(list.birthplace));
				$("#professional").text(judgeIsNull(list.professional));
				$("#telephone").text(judgeIsNull(list.telephone));
				$("#cardId").text(judgeIsNull(list.cardId));
				$("#education").text(judgeIsNull(list.education));
				$("#entryTime").text(DateTimeFormat(judgeIsNull(list.entryTime)));
				if(list.workStatus==1){
					$("#workStatus").text("在职");
				}else{
					$("#workStatus").text("离职");
				}
				$("#contractBegin").text(DateTimeFormat(judgeIsNull(list.contractBegin)));
				$("#contractEnd").text(DateTimeFormat(judgeIsNull(list.contractEnd)));
				$("#address").text(judgeIsNull(list.address));
				$("#resume").text(judgeIsNull(list.resume));
				if(list.attendanceMark==1){
					$("#attendanceMark").text("是");
				}else{
					$("#attendanceMark").text("否");
				}
				$("#remarks").text(judgeIsNull(list.remarks));
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
	$("#memberInfo_div").show();
}
function closememberDetailDiv(){
	$("#memberInfo_div").hide();
}
/**
 * 看巡查详情
 * @param id
 */
function seePatrolDetail(id){
	$.ajax({
		url:'glzxxlist/glxcxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'id':id
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".addTrxc").remove();
				var list=data.result.obj;
				$("#inspectDate").text(judgeIsNull(DateTimeFormat(list.inspectDate)));//巡查日期
				if(list.weather=="sunny"){
					$("#weather").text("晴天");
				}else if(list.weather=="rainy"){
					$("#weather").text("雨");
				}else if(list.weather=="cloudy"){
					$("#weather").text("多云");
				}else if(list.weather=="clouded"){
					$("#weather").text("阴");
				}else if(list.weather=="snow"){
					$("#weather").text("雪");
				}else if(list.weather=="typhoon"){
					$("#weather").text("台风");
				}else{
					$("#weather").text("");
				}
				$("#xcjl").text(judgeIsNull(list.content));
				$("#inspector").text(judgeIsNull(list.inspector));
				$("#opinion").text(judgeIsNull(list.opinion));
				$("#signDate").text(judgeIsNull(DateTimeFormat(list.signDate)));
				$("#signature").text(judgeIsNull(list.signature));
				$("#handleDate").text(judgeIsNull(DateTimeFormat(list.handleDate)));
				$("#handlerSignature").text(judgeIsNull(list.handlerSignature));
				$("#handleResult").text(judgeIsNull(list.handleResult));
				//$("#sectionName").text(judgeIsNull(list.roadName));
				var gyldlist=list.gyldlist;
				for(var i=0;i<gyldlist.length;i++){
					var newTr=$("<tr class='addTrxc'></tr>");
					newTr.append($("<td style='text-align: right;'>巡查路段：</td>"));
					newTr.append($("<td>"+judgeIsNull(gyldlist[i].sectionName)+"&nbsp;</td>"));
					newTr.append($("<td style='text-align: right;'>起止桩号：</td>"));
					newTr.append($("<td>"+judgeIsNull(gyldlist[i].stake)+"&nbsp;</td>"));
					$(".stationDetailTable").append(newTr);
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
	$("#patrol_div").show();
}
function closeXCDetailDiv(){
	$("#patrol_div").hide();
}
function showYearDiv(){
	$(".year_down_ul").show();
	$(document).bind("click",function(e){
		var target = $(e.target); 
		var classname=$(e.target).attr("class");
		if(target.closest(".year_down_ul").length == 0&&classname!="year_select"&&classname!="year_select_time"&&classname!="year_image"){
			$(".year_down_ul").hide(); 
		} 
	});
}
function findYearStatistic(year){
	$(".year_select_time").text(year+"年");
	$(".year_down_ul").hide();
	select_date=year;
	if(typeId==6){
		showOtherInfoByTypeId('glzxxlist/yscqktjxxlist',1);
	}else if(typeId==7){
		showOtherInfoByTypeId('glzxxlist/glxclist',1);
	}
}
function closeGoToStation(){
	window.location.href=$("#basePath").val()+"page/roadstation/RoadStation.jsp";
}