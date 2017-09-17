var typeID;
$$(document).ready(function() {
	//SearchNewsTypeForWeb();
	showTzInfoList('QueryTzxxList',1);
});
/*function SearchNewsTypeForWeb() {
	$$.ajax( {
			url : 'SearchNewsTypeForWeb',
			type : "post",
			dataType : "json",
			success : function(data) {
				$$(".main_left").empty();
				var list = data.newsTypeForWebs;
				for ( var i = 0; i < list.length; i++) {
					if (i == 0) {
						$$(".main_left").append($$("<div class='main_left_one'><a  class='main_left_select' "
												+ "onclick=SearchListNewsForWebByid(this,'"+list[i].typeID+"',0)>"+ list[i].typeName+ "</a></div>"));
						SearchListNewsForWebByid(null,list[i].typeID,0);
					} else {
						$$(".main_left").append($$("<div class='main_left_one'><a  class='main_left_select_no' "
								+ "onclick=SearchListNewsForWebByid(this,'"+list[i].typeID+"',1)>"+ list[i].typeName+ "</a></div>"));
					}
				}

			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		});

}*/
/**
 * 通阻信息
 */
var clickInfo="";
function SearchTzInfoList(thisop,actionName,selectedPage,type){
	$$("#main_right_detail").hide();//公交改道
	$$("#main_right_list").show();
	$$("#search_input,#beginTime,#endTime").val("");
	$$(".main_left_select").attr("class", "main_left_select_no");
	thisop.className = "main_left_select";
	if(type==0){
		showTzInfoList(actionName,selectedPage);
	}
	if(type==1){
		showGzInfoList(actionName,selectedPage);
	}
}
function showTzInfoList(actionName,selectedPage){
	/*var content=$$("#search_input").val();
	var startTime=$$("#beginTime").val();
	var endTime=$$("#endTime").val();*/
	$$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'num':11
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success:function(data){
			//$$(".addTr").remove();
			$$("#NewsList").empty();
			//$$("#search_div").show();
			var list=data.tzxxs;
			if(data.total==0){
				TableIsNull();
			}else{
				appendToTzTable(list);
				gotoPageMethodName = "gotoTzPageNo";
				printPage(data.total, selectedPage, 'showTzInfoList', actionName,
						gotoPageMethodName);
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}
function showGzInfoList(actionName,selectedPage){
	/*var content=$$("#search_input").val();
	var startTime=$$("#beginTime").val();
	var endTime=$$("#endTime").val();*/
	$$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'num':11
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success:function(data){
			//$$(".addTr").remove();
			$$("#NewsList").empty();
			//$$("#search_div").show();
			var list=data.jtgzs;
			if(data.total==0){
				TableIsNull();
			}else{
				appendToGzTable(list);
				gotoPageMethodName = "gotoTzPageNo";
				printPage(data.total, selectedPage, 'showGzInfoList', actionName,
						gotoPageMethodName);
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}
function appendToTzTable(list){
	for(var i=0;i<list.length;i++){
		var newTr = $$("<div class='right1'></div>");
		newTr.append($$("<div style='width:100%;float:left;font-weight: bold;font-size:14px;' ><label onclick='seeInfoDetail("+list[i].XXBH+",0)' style='cursor:pointer;'>"+list[i].BT+"</label></div>"));
		newTr.append($$("<div style='width:100%;float:right;color: #999;'><p class='date'>发布时间:"+DateTimeFormat(list[i].FBSJ)+"&nbsp;发布人："+DateIsNull(list[i].FBR,"未知")+"</p></div>"))
		$$("#NewsList").append(newTr);
	}
}
function appendToGzTable(list){
	for(var i=0;i<list.length;i++){
		var newTr = $$("<div class='right1'></div>");
		newTr.append($$("<div style='width:100%;float:left;font-weight: bold;font-size:14px;' ><label onclick='seeInfoDetail("+list[i].BH+",1)' style='cursor:pointer;'>"+list[i].BT+"</label></div>"));
		newTr.append($$("<div style='width:100%;float:right;color: #999;'><p class='date'>发布时间:"+DateTimeFormat(list[i].FBSJ)+"&nbsp;发布人："+DateIsNull(list[i].FBR,"未知")+"</p></div>"))
		$$("#NewsList").append(newTr);
	}
}
function seeInfoDetail(xxbh,type){
	if(type==0){
		window.location.href=$$("#basePath").val()+"WebPage/page/trafficNews/tzinfo.jsp?xxbh="+xxbh;
	}
	if(type==1){
		window.location.href=$$("#basePath").val()+"WebPage/page/trafficNews/gzinfo.jsp?xxbh="+xxbh;
	}
}
function gotoTzPageNo(actionName, totalPage) {
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
		showTzInfoList(actionName, pageNo);
	}
}
function TzTableIsNull(){
	$$("#tzInfo").append("<tr class='addTr'><td colspan='5'>暂无相关信息</td></tr>");
	$$("#pageDiv").hide();
}
function SearchTrafficInfoList(thisop,selectedPage){
	//$$("#search_div").hide();
	//$$("#NewsList").empty();
	$$("#main_right_list").hide();
	$$("#main_right_detail").show();//公交改道
	$$(".main_left_select").attr("class", "main_left_select_no");
	thisop.className = "main_left_select";
}
function SearchListNewsForWebByid(thisop, id,num) {
	if (thisop != null) {
		$$(".main_left_select").attr("class", "main_left_select_no");
		thisop.className = "main_left_select";
	}
	typeID = id;
	if(num==0)
		SearchListNewsForWeb1('GetRoadWorkNews', 1);
	else{
		SearchListNewsForWeb('SearchListNewsForWeb', 1);
	}
}
//路况
function SearchListNewsForWeb1(actionName, selectedPage) {
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'type' : typeID,
			'page' : selectedPage,
			'num' : PAGESIZE2
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			$$("#NewsList").empty();
			if(data!=undefined){
			if (data.total == 0) {
				TableIsNull();
				$$("#pageDiv").hide();
			} else {
				var list = data.roadWorks;
				appendToTable1(list, list.length);
				gotoPageMethodName = "gotoPageNo1";
				var number = CountItemTrByPageNum(data.total,PAGESIZE2);
				printPage(number, selectedPage, 'SearchListNewsForWeb1', actionName,
						gotoPageMethodName);
			}
			}else{
				TableIsNull();
				$$("#pageDiv").hide();
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
function appendToTable1(list, listLength){
	for ( var i = 0; i < listLength; i++) {
		var newTr = $$("<div class='right1'></div>");
		newTr.append($$("<a class='title' style='cursor: default;' >"+list[i].workRoadName+"</a><p class='date'>"+
				DateTimeFormat(list[i].workRoadStartTime)+"&nbsp;到&nbsp;"+DateTimeFormat(list[i].workRoadEndTime)+"</p>"));
		if(list[i].imageSrc!=null){
			newTr.append($$("<div class='img'><img src='"+list[i].imageSrc+"'/></div>"));
		}
		newTr.append($$(" <div class='rig_cont'><p>影响路段："+list[i].workRoadStart+"到"+list[i].workRoadEnd+"影响情况："+DateIsNull(list[i].degree,"未知")+"</p></div>"));
		$$("#NewsList").append(newTr);
	}
}
function gotoPageNo1(actionName, totalPage) {
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
		SearchListNewsForWeb1(actionName, pageNo);
	}
}
//路况end
function SearchListNewsForWeb(actionName, selectedPage) {
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'type' : typeID,
			'page' : selectedPage,
			'num' : PAGESIZE2
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			$$("#NewsList").empty();
			if(data!=undefined){
			if (data.total == 0) {
				TableIsNull();
				$$("#pageDiv").hide();
			} else {
				var list = data.newsList;
				appendToTable(list, list.length);
				gotoPageMethodName = "gotoPageNo";
				var number = CountItemTrByPageNum(data.total,PAGESIZE2);
				printPage(number, selectedPage, 'SearchListNewsForWeb', actionName,
						gotoPageMethodName);
			}
			}else{
				TableIsNull();
				$$("#pageDiv").hide();
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
	$$("#NewsList").append($$("<div class='layout1_left_datalist_tishi' style='font-size:14px;text-align:center;margin-top:15px' >暂无相关信息</div>"));
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
		SearchListNewsForWeb(actionName, pageNo);
	}
}

function appendToTable(list, listLength){
	for ( var i = 0; i < listLength; i++) {
		var newTr = $$("<div class='right1'></div>");
		newTr.append($$("<a class='title' onclick=NewsDetail('"+list[i].id+"')>"+list[i].title+"</a><p class='date'>"+DateTimeFormat(list[i].date)+"</p>"));
		if(list[i].imageSrc!=null){
			newTr.append($$("<div class='img'><img src='"+list[i].imageSrc+"'/></div>"));
		}
		newTr.append($$(" <div class='rig_cont'><p>"+list[i].content+"</p></div>"));
		$$("#NewsList").append(newTr);
	}
}
function NewsDetail(id){
	window.location.href=$$("#basePath").val()+"WebPage/page/trafficNews/trafficNews_detail.jsp?id="+id;
}