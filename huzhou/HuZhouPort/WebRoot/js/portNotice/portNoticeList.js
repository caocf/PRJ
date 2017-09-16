$(document).ready(function() {
	getJsonPortNotice("captureHtmlPortNotice", 1);//进入界面调用此方法
	});

function getJsonPortNotice(actionName, selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'cpage' : selectedPage
		},

		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			$(".pdTr").remove();
			$(".pdTr").empty();
			if (data.list == null) {

			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.totalPage, selectedPage,
							'getJsonPortNotice', actionName,
							gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			$(".loadingData").hide();// 请求执行完后隐藏loading图标。
	}
	});
}

function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		getJsonPortNotice(actionName, pageNo);
	}
}

function appendToTable(list) {
	for ( var i = 0; i < list.length; i++) {
		newTr = $("<tr class='pdTr'></tr>");
		newTr.append($("<td onclick=showPortNoticeDetailInfo('"+list[i].url+"')>" + list[i].titile + "&nbsp;</td>"));
		newTr.append($("<td>" + list[i].date + "&nbsp;</td>"));
		$("#portNotice_table").append(newTr);
	}
}

function showPortNoticeDetailInfo(url){
	window.location.href=$("#basePath").val()+"page/portNotice/showPortNotice.jsp?url="+url;
}