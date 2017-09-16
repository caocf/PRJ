$(document).ready(function() {
	QJInfor();
	var t = new Date();
	var maxDate = [ t.getFullYear(), t.getMonth()+1, t.getDate()].join('-');
	$("#today").text(maxDate);
});
function preview(){
	$("#btnPrint").css("display","none");
	window.print();
	
}
function QJInfor(){
	var realendTime= $("#endTime").text()+" 23:59:59";
	$.ajax( {
		url : "printPersonData",
		type : "post",
		dataType : "json",
		data : {
		'reportModel.beginTime' : $("#beginTime").text(),
		'reportModel.endTime' : realendTime,
		'reportModel.amount' :$("#userId").val()
		},
		success : function(data) {
			var qjhtml=$("#QJ_div").html();
			var jbhtml=$("#JB_div").html();
			var czhtml=$("#CZ_div").html();
			var qjcount=0;
			var jbcount=0;
			var czcount=0;
				for(var i=0;i<data.list.length;i++){
				if(data.list[i][3]==1){
					qjhtml+="<p class=MsoNormal2><span lang=EN-US class='texttime'>"+getListDate(data.list[i][0])+"到"+getListDate(data.list[i][1])+
							"&nbsp;&nbsp;"+GetDayByHover(data.list[i][2])+"</span><span class='textcont'>天</span></p>";
					qjcount+=data.list[i][2];
				}else if(data.list[i][3]==2){
					jbhtml+="<p class=MsoNormal2><span lang=EN-US class='texttime'>"+getListDate(data.list[i][0])+"到"+getListDate(data.list[i][1])+
							"&nbsp;&nbsp;"+GetDayByHover(data.list[i][2])+"</span><span class='textcont'>天</span></p>";
					jbcount+=data.list[i][2];
				}else if(data.list[i][3]==3){
					czhtml+="<p class=MsoNormal2><span lang=EN-US class='texttime'>"+getListDate(data.list[i][0])+"到"+getListDate(data.list[i][1])+
							"&nbsp;&nbsp;"+GetDayByHover(data.list[i][2])+"</span><span class='textcont'>天</span><span class='texttime'> </span>" +
							"<span class='textcont'>"+data.list[i][5]+"</span><span lang=EN-US class='texttime'><o:p></o:p></span></p>";
					czcount+=data.list[i][2];
				}
			}
				$("#qjcount").text(GetDayByHover(qjcount)+"天");
				$("#jbcount").text(GetDayByHover(jbcount)+"天");
				$("#czcount").text(GetDayByHover(czcount)+"天");
				$("#sumcount").text(GetDayByHover(qjcount+jbcount+czcount)+"天");
				$("#sumtimecount").text(data.list.length);
			$("#QJ_div").html(qjhtml);
			$("#JB_div").html(jbhtml);
			$("#CZ_div").html(czhtml);
		}
		});
}
function getListDate(value){
	returnValue="无";
	if(value==null|value==""){
		return returnValue;
	}else{
		returnValue=value.substring(0,10);
		return returnValue;
	}
	return returnValue;
}
function GetDayByHover(lastDate){
	/*var dlong=0;
	if (lastDate >= 8) {
		var d = lastDate / 8; // 去商
		var l = lastDate % 8; // 去余
		if (l == 0) {
			dlong = d;
		} else {
			dlong = d+0.5;
		}
	} else if (lastDate >= 4){
		dlong = 0.5;
	}else{
		dlong = 0;
	}*/
	return lastDate / 8;
	
}