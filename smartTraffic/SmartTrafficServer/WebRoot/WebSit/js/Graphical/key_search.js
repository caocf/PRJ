var map;
var poiCat;
var old_divname="msg_1";
$$(document).ready(function() {
	map = new XMap("simpleMap", 1);
	map.addPanZoomBar();
	InitializeTianMap();
	InitializeFontColor("searchContent");
	OnLoadPage();
});
//跳转处理
function OnLoadPage(){
	if($$("#sc_Point").val()!="null"){
		$$("#searchContent").val($$("#sc_Point").val());
		searchpoibykey('searchpoibykey',1);
	}
}



//搜索热点类型名字
function searchpoibykey(actionName, selectedPage) {
	$$(".layout1_left_datalist .addTr").remove();
	$$(".layout1_left_datalist .addTr_pressed").remove();
	$$(".layout1_left_datalist .layout1_left_datalist_tishi").remove();
	$$(".layout1_left_datalist_title").css("visibility","visible");
	if(!SearchContentCheck("searchContent")){
		return;
	}
	var tj_tags=$$("#searchContent").val();
	$$.ajax( {
		url : actionName,
		type : 'post',
		dataType : 'json',
		data : {
			 'sp.key' : encodeURI(tj_tags),
             'sp.pageNumber':PAGESIZE,
             'sp.startPosition':selectedPage,
             'sp.json':0
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			var list = $$.parseJSON(data.recodeString);
			list=list.FeatureCollection.featureMember;
			if (list==null) {
				TableIsNull();$$("#searchReasultCount").text("0");
				$$("#pageDiv").hide();
			} else {
				appendToTable(list, list.length);// 跳转页码的方法名称
				gotoPageMethodName = "gotoPageNo";
				var number=CountItemTrByPageNum(list.length,PAGESIZE);
				printPage(number, selectedPage, 'searchpoibykey', actionName,
						gotoPageMethodName);
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
	$$(".layout1_left_datalist ").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
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
		searchpoibykey(actionName, pageNo);
	}
}
var datalist;
function appendToTable(list, listLength){
	datalist=list;
$$("#searchReasultCount").text(listLength);
	for ( var i = 0; i < listLength; i++) {
		var JXPOI = list[i].JXPOI;
		var lonlat=JXPOI[0].GEOMETRY[0].Point[0].coordinates[0];
		var content="<div id='winInfo'>"
			+"<div id='winTitle' ><label class='addTr_name2'>"+JXPOI[0].NAME+"</label></div>"
			+"<div id='winContent'>"
				+"<table>"
					+"<tr><td colspan='4'>地址："+JXPOI[0].ADDRESS+"</td>"
					+"</tr>"
				+"</table>"
			+"</div>"	
		+"</div>";
	
		newTr = $$("<div class='addTr' id='addTr"+i+"' onclick='SelectPoint(this,"+i+")'></div>");
		newTr.append($$("<div class='addTr_img'><img src='WebSit/image/graphical/map_point_normal.png'/><label>"+(i+1)+"</label></div>"));
		newTr.append($$("<div class='addTr_right'><label class='addTr_name'>"+JXPOI[0].NAME+"</label>" +
						"<br/><label class='addTr_address'>地点:</label><label class='addTr_item_address'>"+JXPOI[0].ADDRESS+"</label></div>"));
		$$(".layout1_left_datalist").append(newTr);
		
		var image="WebSit/image/graphical/map_point_normal.png";
		if(i==0) setCenter(lonlat,16);
		map.addPopupForFeature(lonlat,image,(i+1)+"",26,"white","Times New Roman",content,200,100);
	}
}
function SelectPoint(thisop,num){
	$$(".addTr_pressed").attr("class","addTr");
	thisop.className = "addTr_pressed";
	var JXPOI = datalist[num].JXPOI;
	var lonlat=JXPOI[0].GEOMETRY[0].Point[0].coordinates[0];
	 setCenter(lonlat,16);
	 var content="<div id='winInfo'>"
			+"<div id='winTitle' ><label class='addTr_name2'>"+JXPOI[0].NAME+"</label></div>"
			+"<div id='winContent'>"
				+"<table>"
					+"<tr><td colspan='4'>地址："+JXPOI[0].ADDRESS+"</td>"
					+"</tr>"
				+"</table>"
			+"</div>"	
		+"</div>";
	map.createPopup(lonlat,200,100,content);
}