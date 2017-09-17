var map;
var poiCat;
var old_divname="msg_1";
$$(document).ready(function() {
	map = new XMap("simpleMap", 1);
	map.addPanZoomBar();
	InitializeTianMap();
	InitializeFontColor("searchContent");
	if($$("#code").val()!="null"){
		SearchPOI($$("#code").val());
	}
	if($$("#poiKind").val()!="null"){
		$$("#searchContent").val($$("#poiKind").val());
		searchpoibycatByName();
	}
	
});

function showhidediv(id) {
	var msg_1 = document.getElementById("msg_1");
	var msg_2 = document.getElementById("msg_2");
	var msg_3 = document.getElementById("msg_3");
	if (id == 'msg_1') {
		if (msg_1.style.display == 'none') {
			msg_1.style.display = 'block';
			msg_2.style.display = 'none';
			msg_3.style.display = 'none';
			$$(".layout1_left_datalist3 ").css("display", "none");
			$$(".layout1_left2").css("display", "none");
			$$("#searchReasultTitle").css("display", "none");
			$$("#moreOrBack").html("更多&nbsp;&raquo;");
			$$("#moreOrBack").attr("onclick", "showhidediv('msg_2')");
		}
	} else {
		if (id == 'msg_2') {
			if (msg_2.style.display == 'none') {
				msg_1.style.display = 'none';
				msg_2.style.display = 'block';
				msg_3.style.display = 'none';
				$$(".layout1_left_datalist3 ").css("display", "none");
				$$(".layout1_left2").css("display", "none");
				$$("#searchReasultTitle").css("display", "none");
				$$("#moreOrBack").html("&lt;&lt;&nbsp;返回");
				$$("#moreOrBack").attr("onclick", "showhidediv('msg_1')");

			}
		} else if (id == 'msg_3') {
			if (msg_3.style.display == 'none') {
				msg_1.style.display = 'none';
				msg_2.style.display = 'none';
				msg_3.style.display = 'block';
				$$(".layout1_left_datalist3 ").css("display", "none");
				$$(".layout1_left2").css("display", "none");
				$$("#searchReasultTitle").css("display", "");
				$$("#moreOrBack").html("&lt;&lt;&nbsp;返回");
				$$("#moreOrBack").attr("onclick", "showhidediv('" + old_divname + "')");
			}
		} else if (id == 'datalist') {
			msg_1.style.display = 'none';
			msg_2.style.display = 'none';
			msg_3.style.display = 'none';
			$$(".layout1_left_datalist3 ").css("display", "");
			$$(".layout1_left2").css("display", "");
			$$("#searchReasultTitle").css("display", "");
			$$("#moreOrBack").html("&lt;&lt;&nbsp;返回");
			$$("#moreOrBack").attr("onclick", "showhidediv('" + old_divname + "')");

		}
		
	}
	old_divname = id;
}
function showhidediv2(id) {
	var msg_2 = document.getElementById("msg_2");
	var msg_1 = document.getElementById("msg_1");
	var msg_3 = document.getElementById("msg_3");
	if (id == 'msg_3') {
		msg_1.style.display = 'none';
		msg_2.style.display = 'none';
		msg_3.style.display = 'block';
		$$(".layout1_left_datalist3 ").css("display", "none");
		$$(".layout1_left2").css("display", "none");
		$$("#searchReasultTitle").css("display", "none");

	} else if (id == 'datalist') {
			msg_2.style.display = 'none';
			msg_1.style.display = 'none';
			msg_3.style.display = 'none';
			$$(".layout1_left_datalist3 ").css("display", "");
			$$(".layout1_left2").css("display", "");
			$$("#searchReasultTitle").css("display", "");
	}
	$$("#moreOrBack").html("&lt;&lt;&nbsp;返回");
	$$("#moreOrBack").attr("onclick", "showhidediv('" + old_divname + "')");
	old_divname = id;
}

function SelectItem_top(thisop, urlPath) {
	$$(".left_bottom_wb_nav1 ").attr("class", "left_bottom_wb_nav2");
	thisop.className = "left_bottom_wb_nav1";
	$$(".down").attr("src", urlPath);
}
//搜索热点类型名字
function searchpoibycatByName() {
	if(!SearchContentCheck("searchContent")){
		return;
	}
	showhidediv2("msg_3");
	$$.ajax( {
		url : 'searchpoibycatByName',
		type : 'post',
		dataType : 'json',
		data : {
			'sp.key' : $$("#searchContent").val()
		},
		success : function(data) {
			var list=data.recodelist;
			$$("#searchReasultCount").text(list.length);
			//$$("#msg_3 .lb1_text1").remove();
			$$("#msg_3 .lb11").remove();
			var newDiv = $$("<div class='lb11'></div>");
			newDiv.append($$("<p>热点分类</p>"));
			var newTr = $$("<div class='lb1_text1'></div>");
			for ( var i = 0; i < list.length; i++) {
			newTr.append($$("<label onclick=SearchPOI('"+ list[i].code + "')>" +
					list[i].name+ "</label>"));
			}
			newDiv.append(newTr);
			$$("#msg_3").append(newDiv);
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	});
}
//搜索热点详情
function searchpoibycat(actionName, selectedPage) {
	$$(".layout1_left_datalist3 .addTr").remove();
	$$(".layout1_left_datalist3 .addTr_pressed").remove();
	$$(".layout1_left_datalist3 .layout1_left_datalist_tishi").remove();
	showhidediv2("datalist");
	$$.ajax( {
		url : actionName,
		type : 'post',
		dataType : 'json',
		data : {
			'sp.poiCat' : poiCat,
			'sp.pageNumber' : PAGESIZE,
			'sp.startPosition' : selectedPage,
			'sp.Json' : 0
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
				printPage(number, selectedPage, 'searchpoibycat', actionName,
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
	$$(".layout1_left_datalist3 ").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
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
		searchpoibycat(actionName, pageNo);
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
		$$(".layout1_left_datalist3").append(newTr);
		
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
//点击热点，搜索热点详情
function SearchPOI(code){
	poiCat=code;
	searchpoibycat("searchpoibycat",1);
}