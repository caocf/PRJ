var ChangePage=0;
var ChangeIcon=0;
$(document).ready(function() {	
	CreateYearSelect();
});
function CreateYearSelect() {
	$.ajax( {
		url : 'FixByYear',
		type : "post",
		dataType : "json",
		success : function(data) {	
			if (data.list.length > 0) {
				var minYear = data.list[0].minYear, maxYear = data.list[0].maxYear;
				for(var i=maxYear;i>=minYear;i--){
					document.getElementById("year_select").options.add(new Option(i,i));
				}			
			}
		}
	});

}

function CreateReport() {
	$("#report_imgdiv").empty();
	$(".addTr").empty();
	$(".addTr").remove();
	
	$("#report_imgdiv").append($("<img src='"+$("#basePath").val()+"ShowFixImage?reportModel.beginTime="+$("#year_select").val()+"&" +
			"reportModel.endTime="+$("#partoftime_select").val()+"' />"));
	$(".parttime").text($("#year_select").val());	
	$(".contentRange").text("年度定期签证航次报告统计");
	$.ajax( {
		url : 'DateOfFixReport',
		type : "post",
		dataType : "json",
		data : {
		'reportModel.beginTime' : $("#year_select").val(),
		'reportModel.endTime' : $("#partoftime_select").val()
		},
		success : function(data) {
			var newTr=$("#dataTable");
			$(".sum").text(data.list[0].Sum);
			for(var i=1;i<data.list.length;i++){
				if(i==data.list.length-1){
				newTr.append($("<tr class='addTr'><td></td><td class='td3'>"+data.list[i].Name+"</td><td class='td3'>"+data.list[i].Count+"</td><td class='td4'>"+data.list[i].Percentage+"</td></tr>"));
				}
				else{
					newTr.append($("<tr class='addTr'><td></td><td class='td1'>"+data.list[i].Name+"</td><td class='td1'>"+data.list[i].Count+"</td><td class='td2'>"+data.list[i].Percentage+"</td></tr>"));		
				}
			}
		},
		error : function(XMLHttpRequest) {
			alert($(".error", XMLHttpRequest.responseText).text());
		}
	});
	ChangePage=1;
}
function GetExcel(){
	window.location.href=$("#basePath").val()+"downloadFixExcel?reportModel.beginTime="+$("#year_select").val()+"&" +
			"reportModel.endTime="+$("#partoftime_select").val();
}
function ChangeDiv(id){
	if(ChangePage==0){
		alert("还未统计！");
		return;
	}
	if(ChangeIcon==0){
		$("#report_datediv").css("display","block");
		$("#report_imgdiv").css("display","none");
		id.src="image/statistic/no_report_choose.png";
		ChangeIcon=1;
	}
	else if(ChangeIcon==1){
		$("#report_datediv").css("display","none");
		$("#report_imgdiv").css("display","block");
		id.src="image/statistic/yes_report_choose.png";
		ChangeIcon=0;
	}
}