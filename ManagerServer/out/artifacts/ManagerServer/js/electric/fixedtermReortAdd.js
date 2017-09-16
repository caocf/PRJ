$(document).ready(function() {
	if ($("#fixedTermID").val() != "" && $("#fixedTermID").val() != 'null') {
		findFixedReportInfoById($("#fixedTermID").val());
	}
});
function findFixedReportInfoById(fixedTermID) {
	$.ajax( {
		url : "findFixedReportInfoById",
		type : "post",
		dataType : "json",
		data : {
			'fixedTermID' : fixedTermID
		},
		success : function(data) {
			var li = data.list;
			$("#shipName").attr("value", li[0].shipName);
			$("#startingPort").attr("value", li[0].startingPort);
			$("#arrivalPort").attr("value", li[0].arrivalPort);
			$("#startTime").attr("value", TimeData(li[0].startTime));
			$("#endTime").attr("value", TimeData(li[0].endTime));
		}
	});
}

function TimeData(value){
	if(value==null){
		return "";
	}else{
		return value.replace("T"," ");
	}
}
function editFixedTermReport() {
	var actionName;
	if ($("#fixedTermID").val() == "" || $("#fixedTermID").val() == 'null') {
		actionName = 'addFixedTermReportInfo';
		$("#fixedTermID").attr("value", '0');
	} else {
		actionName = 'modifyFixedTermReportInfo';
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : $("#AddFixedTerm_Form").serialize(),
		success : function(data) {
			window.location.href = $("#basePath").val()
					+ "page/electric/fixedtermReportList.jsp";
		}
	});

}
//// getMaxDate生成客户端本地时间
//	function getMaxDate1() {
//		var t = new Date();
//		var maxDate = [ t.getFullYear(), t.getMonth() + 4, t.getDate() ]
//				.join('-');
//		maxDate += ' ' + t.toLocaleTimeString();
//		return maxDate;
//	}
//	// getMinDate生成客户端本地时间
//	function getMinDate1() {
//		var t = new Date();
//		t.setMonth(t.getMonth() + 1);// 最小时间少2个月
//		var maxDate = [ t.getFullYear(), t.getMonth(), t.getDate() - 1 ]
//				.join('-');
//		maxDate += ' ' + t.toLocaleTimeString();
//		return maxDate;
//	}