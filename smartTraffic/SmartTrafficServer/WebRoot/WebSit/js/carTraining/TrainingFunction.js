var typeID;
$$(document).ready(function() {
	GetRegionForSchool();
});
function GetRegionForSchool() {
	$$.ajax( {
			url : 'GetRegionForSchool',
			type : "post",
			dataType : "json",
			success : function(data) {
				$$(".main_left").empty();
				var list = data.regions;
				for ( var i = 0; i < list.length; i++) {
					if (i == 0) {
						$$(".main_left").append($$("<div class='main_left_one'><a  class='main_left_select' "
												+ "onclick=SearchDrivingListNewsByid(this,'"+list[i].regionID+"')>"+ list[i].regionName+ "</a></div>"));
						SearchDrivingListNewsByid(null,list[i].regionID);
					} else {
						$$(".main_left").append($$("<div class='main_left_2'><a  class='main_left_select_no' "
								+ "onclick=SearchDrivingListNewsByid(this,'"+list[i].regionID+"')>"+ list[i].regionName+ "</a></div>"));
					}
				}

			}
		});

}
function SearchDrivingListNewsByid(thisop, id) {
	if (thisop != null) {
		$$(".main_left_select").attr("class", "main_left_select_no");
		thisop.className = "main_left_select";
	}
	typeID = id;
	SearchDrivingListNews('SearchDrivingListNews', 1);
}
function SearchDrivingListNews(actionName, selectedPage) {
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
			if (data.total == 0) {
				TableIsNull();
				$$("#pageDiv").hide();
			} else {
				var list = data.drirvingSchooleNews;
				appendToTable(list, list.length);
				gotoPageMethodName = "gotoPageNo";
				var number=CountItemTrByPageNum(data.total,PAGESIZE2);
				printPage(number, selectedPage, 'SearchDrivingListNews', actionName,
						gotoPageMethodName);
			}

		},
		complete : function() {
			CloseLoadingDiv();
	}
	});

}
function TableIsNull(){
	$$("#NewsList").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
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
		SearchDrivingListNews(actionName, pageNo);
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
	window.location.href=$$("#basePath").val()+"WebSit/page/carTraining/trainingDetail.jsp?id="+id;
}
function SelectItem_top(thisop,urlPath){
	$$(".main_content_title_divselect").attr("class","main_content_title_divnoselect");
	thisop.className="main_content_title_divselect";
	window.location.href=$$("#basePath").val()+urlPath;
}
