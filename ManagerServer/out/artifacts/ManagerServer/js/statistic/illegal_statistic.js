var beginTime="";
var endTime="";
var ChangeIcon=0;
function getMaxDate() {
	var t = new Date();
	var maxDate = [ t.getFullYear(), t.getMonth() + 1, t.getDate() ].join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate;
}
// getMinDate生成客户端本地时间
function getMinDate() {
	var t = new Date();
	t.setMonth(t.getMonth());// 最小时间少2个月
	var maxDate = [ t.getFullYear() - 1, t.getMonth() + 1, t.getDate() ]
			.join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate;
}
function CreateReport() {
	$("#report_imgdiv").empty();
	$(".addTr").empty();
	$(".addTr").remove();
	if ($("#beginTime").val() == "") {
		alert("请选择起始时间");
		return;
	}
	if ($("#endTime").val() == "") {
		alert("请选择结束时间");
		return;
	}
	beginTime=$("#beginTime").val();
		endTime=$("#endTime").val();
		var endTime1=$("#endTime").val()+" 23:59:59";
	$("#report_imgdiv").append($("<img src='"+$("#basePath").val()+"ShowIllegalImage?reportModel.beginTime="+$("#beginTime").val()+"&" +
			"reportModel.endTime="+endTime1+"&reportModel.condition="+$("#condition_select").val()+"' />"));
	$(".parttime").text(beginTime+"至"+endTime);
	if($("#condition_select").val()==1)
		$(".contentRange").text("全部");
	else
	$(".contentRange").text("已审核通过");
	$.ajax( {
		url : 'IllegalReport',
		type : "post",
		dataType : "json",
		data : {
			'reportModel.beginTime' : $("#beginTime").val(),
			'reportModel.endTime' : endTime1,
			'reportModel.condition' : $("#condition_select").val()
		},
		success : function(data) {
			var newTr=$("#dataTable");
			$(".sum").text(data.list[0][0].Sum);
			for(var i=1;i<data.list[0].length;i++){
				if(i==data.list[0].length-1){
					newTr.append($("<tr class='addTr'><td></td><td class='td3'>"+data.list[0][i].Name+"</td><td class='td3'>"+data.list[0][i].Count+"</td><td class='td4'>"+data.list[0][i].Percentage+"</td></tr>"));
				}else{
					newTr.append($("<tr class='addTr'><td></td><td class='td1'>"+data.list[0][i].Name+"</td><td class='td1'>"+data.list[0][i].Count+"</td><td class='td2'>"+data.list[0][i].Percentage+"</td></tr>"));
				}
				
			}
			newTr.append($("<tr class='addTr'><td>违章缘由统计：</td><td colspan='3'></td></tr>"));
			newTr.append($("<tr class='addTr'><td></td><td class='td1'>名称</td><td class='td1'>数量</td><td class='td2'>百分比</td></tr>"));
			for(var i=0;i<data.list[1].length;i++){
				if(i!=data.list[1].length-1){
				newTr.append($("<tr class='addTr'><td></td><td class='td1'>"+data.list[1][i].Name+"</td><td class='td1'>"+data.list[1][i].Count+"</td><td class='td2'>"+data.list[1][i].Percentage+"</td></tr>"));
				}
				else{
					newTr.append($("<tr class='addTr'><td></td><td class='td3'>"+data.list[1][i].Name+"</td><td class='td3'>"+data.list[1][i].Count+"</td><td class='td4'>"+data.list[1][i].Percentage+"</td></tr>"));
				}
			}
		},
		error : function(XMLHttpRequest) {
			alert($(".error", XMLHttpRequest.responseText).text());
		}
	});

}
function GetExcel(){
	if(beginTime==""&&$("#beginTime").val() == ""){
		alert("请选择起始时间");
		return;
	}
	if ($("#endTime").val() == ""&&beginTime == "") {
		alert("请选择结束时间");
		return;
	}
	if(beginTime=="")
		beginTime=$("#beginTime").val();
	if(endTime=="")
		endTime=$("#endTime").val();
	var endTime1=$("#endTime").val()+" 23:59:59";
	window.location.href=$("#basePath").val()+"downloadIllegalExcel?reportModel.beginTime="+$("#beginTime").val()+"&" +
			"reportModel.endTime="+endTime1+"&reportModel.condition="+$("#condition_select").val();
}
function ChangePage(id){
	if(beginTime==""){
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