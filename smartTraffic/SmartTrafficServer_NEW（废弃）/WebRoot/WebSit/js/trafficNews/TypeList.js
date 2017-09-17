var typeID;
$$(document).ready(function() {
	SearchNewsTypeForWeb();
});
function SearchNewsTypeForWeb() {
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
	window.location.href=$$("#basePath").val()+"WebSit/page/trafficNews/trafficNews_detail.jsp?id="+id;
}