$(document).ready(function() {
	wenhouDateInfo();
	findHomePageInfo();
	findHomePageInfoPrompt();
});

function wenhouDateInfo(){
	var myDate = new Date();
	var ti=myDate.getHours();
	var a="早上好";
	if(ti<=18&&ti>=12){
		a="下午好";
	}else if(ti>18){
		a="晚上好";
	}
	$("#wenhouDate").text(a);
}

function findHomePageInfo() {
	$.ajax( {
		url : 'findHomePageInfo',
		type : "post",
		dataType : "json",
		success : function(data) {
			showHomePageInfo(data.list);
		}
	});
}

function showHomePageInfo(list) {
	$("#report").text(list[0].report);
	$("#leaveorot").text(list[0].leaveorot);
	$("#illega").text(list[0].illega);
	$("#dangerousarrivaldeclare").text(list[0].dangerousarrivaldeclare);
	$("#dangerousworkdeclare").text(list[0].dangerousworkdeclare);
}

function findHomePageInfoPrompt() {
	$.ajax( {
		url : 'findHomePageInfoPrompt',
		type : "post",
		dataType : "json",
		success : function(data) {
			showHomePageInfoPrompt(data.list);
		}
	});
}

function showHomePageInfoPrompt(list) {
	var url;
	for ( var i = 0; i < list.length; i++) {
		if(list[i].state==0){
			url='page/officoa/schedule_show.jsp';
		}else if(list[i].state==1){
			url='page/LeaveAndOvertime/LeaveAndOvertime.jsp';
		}
		newTr = $("<tr class='logclear'></tr>");
		newTr.append($("<td><a href="+url+">[" + list[i].eventName + "]</a></td>"));
		newTr.append($("<td width='450px'>" + list[i].eventLocation + "</td>"));
		newTr.append($("<td>" + list[i].EventTime + "</td>"));
		$("#content_three_table").append(newTr);
	}
}

function toBusiness()
{
	window.location.href="page/ais/ais.jsp";
}

