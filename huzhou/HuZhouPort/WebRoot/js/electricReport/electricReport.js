$(document).ready(function() {

	electricReportList('ElectricReportListByInfo', 1);
});
function electricReportList(actionName, selectedPage) {
	$.ajax( {
		url : 'ElectricReportListByInfo',
		type : "post",
		dataType : "json",
		data : {
			'cpage' : selectedPage
		},
		success : function(data) {

			if (data.list == null) {
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					electricReport(list, listLength);// 跳转页码的方法名称
		gotoPageMethodName = "gotoPageNo";
		printPage(data.totalPage, selectedPage, 'electricReportList',
				actionName, gotoPageMethodName, data.allTotal);
	}
}

}
	});

}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		electricReportList(actionName, pageNo);
	}
}
function electricReport(list, listLength) {
	var basePath = $("#basePath").val();
	$("#electricReportTable .clear").empty();
	$("#electricReportTable .clear").remove();

	if (listLength == 0) {
		newTr = $("<tr class='clear'></tr>");
		newTr.append($("<td colspan=6>暂无相关数据</td>"));
		$("#electricReportTable").append(newTr);
	} else {
		for ( var i = 0; i < listLength; i++) {
			newTr = $("<tr class='clear'></tr>");
			newTr.append("<td>" + list[i].shipName + "</td>");
			if (list[i].reportKind == 1) {
				newTr.append("<td>重船航行</td>");
			} else {
				newTr.append("<td>空船航行</td>");
			}
			newTr.append("<td>" + list[i].toBeDockedAtThePier + "</td>");
			newTr.append("<td>" + GetShortTime1(list[i].estimatedTimeOfArrival)
					+ "</td>");
			newTr.append("<td>" + GetShortTime1(list[i].reportTime) + "</td>");
			newTr.append($("<td><a onclick=findReportById('" + list[i].reportID
					+ "') class='operation'>查看</a></td>"));
			$("#electricReportTable").append(newTr);
		}

	}

}
function TableIsNull(){
	$("#electricReportTable .clear").empty();
	$("#electricReportTable .clear").remove();
	var newTr = $("<tr class='clear'></tr>");
	newTr.append($("<td colspan=6>暂无相关数据</td>"));
	$("#electricReportTable").append(newTr);
}
function findReportById(reportID) {
	window.location.href = $("#basePath").val()
			+ "page/electric/electricReportListShow.jsp?reportID=" + reportID;
}
function selectContent(actionName, selectedPage) {

	var search = $('#Content').val();
	if (/[~#^$@%&!*\s*]/.test(search)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'electricReportNew.shipInfo' : search,
			'cpage' : selectedPage
		},
		success : function(data) {

			if (data.list == null) {
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					electricReport(list, listLength);// 跳转页码的方法名称
		gotoPageMethodName = "gotoPageNo1";
		printPage(data.totalPage, selectedPage, 'selectContent', actionName,
				gotoPageMethodName, data.allTotal);
	}
}

}
	});

}
function gotoPageNo1(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		selectContent(actionName, pageNo);
	}
}
function selectBoatmanInfo(){
	/*var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		});*/
	if($("#boatmanInfoUl").text().length!=0)
	{
		ShowPublicUserListByShip("PublicUserListByShip",1);// 船户列表
	}
	$("#selectBoatManInfoDiv").show();
}
function closesboatInfoDiv(){
	$("#selectBoatManInfoDiv,#fullbg").hide();
}
//船户列表
function ShowPublicUserListByShip(actionName,selectedPage){
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data:{
			'cpage' : selectedPage
		},
		success : function(data) {
			$(".addLi").empty();
			$(".addLi").remove();
			if (data.list == null) {
				newLi = $("<li class='addLi'>暂无相关数据</li>");
				$("#boatmanInfoUl").append(newTr);
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					for ( var i = 0; i < listLength; i++) {
						newTr = $("<li class='addLi'><a onclick=SelctBoatMan('"
										+ list[i][0]+ "','"+list[i][1]+"') >"+list[i][1]+"("+ list[i][2] +")</a></li>");
						$("#boatmanInfoUl").append(newTr);
					}
					gotoPageMethodName = "gotoPageNo2";
					dialog_printPage(data.totalPage, selectedPage, 'ShowPublicUserListByShip',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		}
	});
}
function gotoPageNo2(actionName, totalPage) {
	var pageNo = $(".dialog_indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".dialog_indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		ShowPublicUserListByShip(actionName, pageNo);
	}
}
function SelctBoatMan(boatmanId,userName){

	$.ajax( {
		url : 'SelctBoatManForReportByUser',
		type : "post",
		dataType : "json",
		data:{
			'publicUser.userId' : boatmanId,
			'publicUser.userName' : userName
		},
		success : function(data) {
			closesboatInfoDiv();
         window.open($("#basePath").val()+"page/sailing/sailreport.jsp");
		}
	});

}

function SearchBoatMan(actionName, selectedPage) {

	var search = $('#SearchBoatman').val();
	if (/[~#^$@%&!*\s*]/.test(search)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
		'queryCondition.listKeyValuePair[0].key' : 's.shipNum',
		'queryCondition.listKeyValuePair[0].pair' : '%%',
		'queryCondition.listKeyValuePair[0].value' : search,
		'queryCondition.listKeyValuePair[0].connector' : 'or',
		'queryCondition.listKeyValuePair[1].key' : 'p.userName',
		'queryCondition.listKeyValuePair[1].pair' : '%%',
		'queryCondition.listKeyValuePair[1].value' : search,
			'cpage' : selectedPage
		},
		success : function(data) {
			$(".addLi").empty();
			$(".addLi").remove();
			if (data.list == null) {
				newLi = $("<li class='addLi'>暂无相关数据</li>");
				$("#boatmanInfoUl").append(newTr);
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					for ( var i = 0; i < listLength; i++) {
						newTr = $("<li class='addLi'><a onclick=SelctBoatMan('"
										+ list[i][0]+ "','"+list[i][1]+"') >"+list[i][1]+"("+ list[i][2] +")</a></li>");
						$("#boatmanInfoUl").append(newTr);
					}
					gotoPageMethodName = "gotoPageNo3";
					dialog_printPage(data.totalPage, selectedPage, 'SearchBoatMan',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		}
	});

}
function gotoPageNo3(actionName, totalPage) {
	var pageNo = $(".dialog_indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".dialog_indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		SearchBoatMan(actionName, pageNo);
	}
}