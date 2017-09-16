$(document).ready(function() {
	showLeaveAndOvertime('queryShipQueryLogs', 1);
	$(".input_box").css('margin','6px 0');
	$(".timeinput").attr('readonly','readonly');
	$(".timeinput").css('cursor','pointer');
});
function showLeaveAndOvertime(actionName, page) {
	var datastr={};
	datastr['page']=page;
	datastr['num']=10;
	datastr['startTime']=$("#starttime").val();
	datastr['endTime']=$("#endtime").val();
	if(CompareDate($("#starttime").val(),$("#endtime").val())){
		alert('结束日期应大于起始日期！');
		return
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : datastr,
		success : function(data) {
			if(data.total!=0){
			var gotoPageMethodName = "gotoPageNo";
			var totalPage=Math.ceil(data.total/10);
			printPage(totalPage, page,
					'showLeaveAndOvertime', actionName, gotoPageMethodName,
					data.total);
			$(".logclear").empty();
			$(".logclear").remove();
			$(data.result).each(function(index,item){
				$("#LeaveAndOvertimeTable").append(
						"<tr class='logclear'>" +
						"<td>"+isnull(item.logUserName)+"</td>" +
						"<td>"+parseFloat(item.lon)+"-"+parseFloat(item.lan)+"</td>" +
						"<td>"+isnull(item.shipname)+"</td>" +
						"<td>"+isnull(item.shipnum)+"</td>" +
						"<td>"+isnull(item.querytime)+"</td>" +
						"<td>"+isyichang(item.qzyc)+"</td>" +
						"<td>"+isyichang(item.jfyc)+"</td>" +
						"<td>"+isyichang(item.zsyc)+"</td>" +
						"<td>"+isyichang(item.wzyc)+"</td>" +
						"<td><span style='cursor:pointer;' onclick='caozuo(this.id)' id='"+isnull(item.shipname)+"'>操作</span></td>" +
						"</tr>"
				);
			})
			}else{
				TableIsNull();
			}
		}
	});

}
function isyichang(ii){
	if(ii==1){
		return '<span style="color:red;">异常</span>';
	}else if(ii==0){
		return '不异常';
	}else{
		return '未知';
	}
}
function caozuo(name){
	window.location.href=encodeURI($('#basePath').val()+"page/shiplog/illegal_add.jsp?name="+name);
}
function isnull(str){
	var isnull='--';
	if(str!=null&&str!=''){
		return str;
	}else{
		return isnull;
	}
}
function TableIsNull(){
	$(".logclear").empty();
	$(".logclear").remove();

		newTr = $("<tr class='logclear'></tr>");
		newTr.append($("<td  colspan=6>暂无相关数据</td>"));
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
function CompareDate(d1,d2)
{
  return ((new Date(d1.replace(/-/g,"\/"))) > (new Date(d2.replace(/-/g,"\/"))));
}
