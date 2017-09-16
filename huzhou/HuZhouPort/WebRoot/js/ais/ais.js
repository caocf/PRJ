$(document).ready(function() {
	showLeaveAndOvertime('queryEditAisList', 1);
});
function showLeaveAndOvertime(actionName, page) {
	var basePath = $("#basePath").val()
	var datastr = {};
	datastr['page'] = page;
	datastr['num'] = 10;
	$.ajax({
		url : actionName,
		type : "post",
		dataType : "json",
		data : datastr,
		success : function(data) {
			if (data.total != 0) {
				var gotoPageMethodName = "gotoPageNo";
				var totalPage = Math.ceil(data.total / 10);
				printPage(totalPage, page, 'showLeaveAndOvertime', actionName,
						gotoPageMethodName, data.total);
				$(".logclear").empty();
				$(".logclear").remove();
				$(data.aiss).each(
						function(index, item) {
							/*
							 * var caozuo='';
							 * if(item.picpath==''||item.picpath==null){
							 * caozuo="<span style='color:#ccc;' >查看</span>";
							 * }else{ caozuo="<span style='cursor:pointer;'
							 * onclick='caozuo(this.id)'
							 * id='"+item.picpath+"'>查看</span>" }
							 */
							var fnum = "待审核";
							var st="<td><span style='color:orange'>" + fnum + "</span></td>";
							var tempf = isnull(item.appflag);
							var check="审核";
							if (tempf == 1) {
								fnum = "通过";
								st="<td><span style='color:green'>" + fnum + "</span></td>";
								check="查看";
							}
							if (tempf == 2) {
								fnum = "驳回";
								st="<td><span style='color:red'>" + fnum + "</span></td>";
								check="查看";
							}
							//var check="<a class='operation' href='" + basePath + "page/ais/aisshow.jsp?ID=" + isnull(item.num) + "'>审核</a>";
							
							$("#LeaveAndOvertimeTable").append(
									"<tr class='logclear'>" + "<td>"
											+ isnull(item.name) + "</td>"
											+ "<td>" + isnull(item.num) + "</td>" 
											+ "<td>" + isnull(item.man) + "</td>" 
											+ "<td>" + isnull(item.adddate) + "</td>"
											+ st
											
											+ "<td>" 
											+ "<a class='operation' href='" + basePath + "page/ais/aisshow.jsp?ID=" + isnull(item.num) + "'>"+check+"</a>"
											//+"&nbsp;&nbsp;&nbsp;&nbsp;"
											//+check
											+ "</td>" 
											+ "</tr>");
						})
			} else {
				TableIsNull();
			}
		}
	});

}
function isyichang(ii) {
	if (ii == 1) {
		return '<span style="color:red;">异常</span>';
	} else if (ii == 0) {
		return '不异常';
	} else {
		return '未知';
	}
}
function caozuo(path) {
	window.parent.parent.showpic(path);
}
function isnull(str) {
	var isnull = '--';
	if (str != null && str != '') {
		return str;
	} else {
		return isnull;
	}
}
function TableIsNull() {
	$(".logclear").empty();
	$(".logclear").remove();

	newTr = $("<tr class='logclear'></tr>");
	newTr.append($("<td  colspan=7>暂无相关数据</td>"));
	$("#LeaveAndOvertimeTable").append(newTr);
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
		showLeaveAndOvertime(actionName, pageNo);
	}
}
function CompareDate(d1, d2) {
	return ((new Date(d1.replace(/-/g, "\/"))) > (new Date(d2.replace(/-/g,
			"\/"))));
}
