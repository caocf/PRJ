$(document).ready(function() {
	showAisShow();
});

function showAisShow() {
	var ID = $("#ID").val();
	//alert(ID);
	$.ajax({
		url : 'showaisinfo',
		type : "post",
		dataType : "json",
		data : {
			"aisNo" : ID
		},
		success : function(data) {
			showaisdetail(data.editResult);
		}
	});
}

function showaisdetail(obj) {
	var basePath = $("#basePath").val()
	var newTr = "";
	newTr = $("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>船舶名称：</td><td>" + obj.name + "</td>");
	$("#aistable").append(newTr);

	newTr = $("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>九位码：</td><td>" + obj.num + "</td>");
	$("#aistable").append(newTr);

	newTr = $("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>补录时间：</td><td>" + obj.adddate + "</td>");
	$("#aistable").append(newTr);
	
	newTr = $("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>补录人：</td><td>" + obj.man + "</td>");
	$("#aistable").append(newTr);
	
	newTr = $("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>审核人：</td><td>" + obj.checker + "</td>");
	$("#aistable").append(newTr);

	var fj = obj.picpath;
	if (fj != null && fj != '' && fj != undefined) {
		var newimg = $('<img alt="" src="' + basePath + 'File/' + fj
				+ '" height="100%" width="50%">');
		$("#aisfj").append(newimg);
	}
	if (obj.appflag == 0 || obj.appflag == null || obj.appflag == undefined) {
		var appok = $('<img src="'
				+ basePath
				+ 'image/operation/btn_pass_normal.png" onclick="appok('
				+ obj.num
				+ ')"  title="审核通过" />');
		$("#layer1").append(appok);
		var appnok = $('<img src="'
				+ basePath
				+ 'image/operation/btn_reject_normal.png" onclick="appnok('
				+ obj.num
				+ ')"  title="审核驳回" />');
		$("#layer1").append(appnok);
	}
/*	var appedit = $('<img src="'
			+ basePath
			+ 'image/operation/btn_edit__normal.png" onclick="appedit('
			+ obj.num
			+ ')"  title="编辑" />');
	$("#layer1").append(appedit);*/

}

function fanhui() {
	var basePath = $("#basePath").val()
	str = basePath + "page/ais/ais.jsp";
	window.location.href = str;
}

function appok(num) {
	appais(num, 1);
}

function appnok(num) {
	appais(num, 2);
}

function appais(ais, fnum) {
	$.ajax({
		url : 'appaisinfo',
		type : "post",
		dataType : "json",
		data : {
			"aisNo" : ais,
			"fnum" : fnum,
			"ais.checker" : $("#LoginUser").val()
		},
		success : function(data) {
			if (fnum == 1) {
				alert("审核通过");
			} else {
				alert("审核驳回");
			}
			var basePath = $("#basePath").val()
			str = basePath + "page/ais/ais.jsp";
			window.location.href = str;
		}
	});
}
