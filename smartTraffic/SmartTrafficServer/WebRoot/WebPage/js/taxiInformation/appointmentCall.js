$$(document).ready(function() {
	SelectOption("tixing",100);
	LogAndExitRefresh=true;//登录退出时是否刷新页面
	if ($$("#LoginUser").val() == "null") {
		LoginWeb();
	} else {
		GetUserInfoForWeb();//返回个人信息
		queryOrder();
	}
});
function GetUserInfoForWeb() {
	$$.ajax( {
		url : 'GetUserInfoForWeb',
		type : 'post',
		dataType : 'json',
		success : function(data) {

			$$("#phone").val(DateIsNull(data.user.phone, ""));

		},
		error : function(a, b, c) {
			alert("获取电话号码失败");
		}
	});
}
//getMaxDate生成客户端本地时间
function getMaxDate(){
	var t = new Date();
	var maxDate = [t.getFullYear(), t.getMonth()+1, t.getDate()+TIMELIMIT-1].join('-');
	return maxDate; 
}
function queryOrder(){
	
	$$.ajax( {
			url : 'queryOrder',
			type : "post",
			dataType : "json",
			success : function(data) {
				$$(".listTable .addTr").empty();
				$$(".listTable .addTr").remove();
				if(data.taxiOrders!=null){
					$$(".loadingData").hide();
					var list = data.taxiOrders;
					for ( var i = 0; i < list.length; i++) {
						var newTr=$$("<tr class='addTr'></tr>");
						newTr.append($$("<td>&nbsp;</td>"));
						newTr.append($$("<td>"+list[i].start+"</td>"));
						newTr.append($$("<td>"+list[i].end+"</td>"));
						newTr.append($$("<td>"+HourTimeFormat(list[i].date)+"</td>"));
						newTr.append($$("<td>"+list[i].phone+"</td>"));
						if(list[i].alert==1)
							newTr.append($$("<td>是</td>"));
						else
							newTr.append($$("<td>否</td>"));
						newTr.append($$("<td>"+DateIsNull(list[i].status,"无")+"</td>"));
						$$(".listTable").append(newTr);
					}
					if(list.length==0) $$(".loadingData").show();
				}else{
					$$(".loadingData").show();
				}
			}
		});



}
function ChangeText(tishitext){
	var startCity=$$("#startCity").val();
	$$("#startCity").val($$("#endCity").val());
	$$("#endCity").val(startCity);
}
function orderTaxi(){

	if(!validSubmit()){
		return;
	}
	$$.ajax( {
			url : 'orderTaxi',
			type : "post",
			dataType : "json",
			data:{
				'start':$$("#startCity").val(),
				'end':$$("#endCity").val(),
				'date':$$("#Time").val(),
				'alert':$$("#tixing .abc").val(),
				'phone':$$("#phone").val()
			},
			success : function(data) {
				if(data.result){
					alert("提交成功");
					window.location.reload();
				}else{
					alert("提交失败");	
				}
			},error : function(a, b, c) {
				alert("提交失败");
			}
		});
}
function ValueCheck(valueId, msg) {
	var check = $$.trim($$(valueId).val());
	if (check == ""&&check == "") {
		alert(msg);
		return false;
	}
	return true;
}


function validSubmit() {
	if (!ValueCheck("#startCity","出发地不能为空")) {
		return false;
	}
	if (!ValueCheck("#endCity","到达地不能为空")) {

		return false;
	}
	if (!ValueCheck("#Time","时间不能为空")) {

		return false;
	}if (!ValueCheck("#phone","电话不能为空")) {

		return false;
	}
	return true;
}