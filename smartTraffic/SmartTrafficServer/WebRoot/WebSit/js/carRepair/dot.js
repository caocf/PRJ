var map;
$$(document).ready(function() {
	map=new XMap("simpleMap",1);
	map.addPanZoomBar(); 
	InitializeTianMap();
	InitializeFontColor("searchContent");
});
function GetCarRepairByName(actionName){
	if(!SearchContentCheck("searchContent")){
		return;
	}
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$(".layout1_left_datalist_tishi").remove();
	$$(".addTr_pressed").remove();
	$$(".layout1_left_datalist_title").css("visibility","visible");
	map.delWindow(); 
	$$.ajax( {
		url : actionName,
		type : 'post',
		dataType : 'json',
		data : {
			'name' :$$("#searchContent").val()
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			$$("#searchReasultCount").text(data.carRepairs.length);
			if (data.carRepairs.length== 0) {
				TableIsNull();
				$$("#pageDiv").hide();
			} else {
				var list = data.carRepairs;
				appendToTable(list,list.length);
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
	$$(".layout1_left_datalist").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
}
var datalist;
function appendToTable(list, listLength){ 
	datalist=list;
	for ( var i = 0; i < listLength; i++) {
		var lonlat=list[i].longtitude+","+list[i].lantitude;
		var content="<div id='winInfo'>"
			+"<div id='winTitle' ><label class='addTr_name2'>"+list[i].name+"</label></div>"
			+"<div id='winContent'>"
				+"<table>"
					+"<tr><td>联系方式：</td><td>"+DateIsNull(list[i].phone,"无")+"</td></tr>"
					+"<tr><td>汽修等级：</td><td>"+list[i].level+"</td></tr>"
					+"<tr><td colspan='2'>地址："+list[i].address+"</td>"
					+"</tr>"
				+"</table>"
			+"</div>"	
		+"</div>";
		
		newTr = $$("<div class='addTr' id='addTr"+i+"' onclick='SelectPoint(this,"+i+")'></div>");
		newTr.append($$("<div class='addTr_img'><img src='WebPage/image/newmap_icon/1.png'/></div>"));
		newTr.append($$("<div class='addTr_right'><label class='addTr_name'>"+list[i].name+"</label>" +
				"<label class='addTr_freeCount'>联系方式:</label><label class='addTr_item'>"+DateIsNull(list[i].phone,"无")+"</label>" +
				"<label class='addTr_totalCount'>汽修等级:</label><label  class='addTr_item'>"+list[i].level+"</label>" +
						"<br/><label class='addTr_address'>地点:</label><label class='addTr_item_address'>"+list[i].address+"</label></div>"));
		$$(".layout1_left_datalist").append(newTr);
		
		var image="WebSit/image/graphical/map_point_normal.png";
		if(i==0) setCenter(lonlat,16);
		map.addPopupForFeature(lonlat,image,(i+1)+"",26,"white","Times New Roman",content,200,120);
	}
}
function SelectPoint(thisop,num){
	$$(".addTr_pressed").attr("class","addTr");
	thisop.className = "addTr_pressed";
	var lonlat=datalist[num].longtitude+","+datalist[num].lantitude;
	 setCenter(lonlat,16);
	var content="<div id='winInfo'>"
		+"<div id='winTitle'>"+datalist[num].name+"</div>"
		+"<div id='winContent'>"
			+"<table>"
				+"<tr><td>联系方式：</td><td>"+DateIsNull(datalist[num].phone,"无")+"</td></tr>"
				+"<tr><td>汽修等级：</td><td>"+datalist[num].level+"</td></tr>"
				+"<tr><td colspan='2'>地址："+datalist[num].address+"</td>"
				+"</tr>"
			+"</table>"
		+"</div>"	
	+"</div>";
	map.createPopup(lonlat,200,120,content);
}
function LeftCssBySelf(num){
	$$(".addTr_pressed").attr("class","addTr");
	var nm=document.getElementById("addTr"+num);
	nm.className = "addTr_pressed";
}
