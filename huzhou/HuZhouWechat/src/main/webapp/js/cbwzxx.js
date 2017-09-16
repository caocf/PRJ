var SearchName;
//显示船舶违章信息
function showBaseInfo(actionName,selectedPage) {
	var cbname = $("#cbname").val();
	if (cbname == "") {
		alert("船名不能为空！");
		return;
	}
	if (/[-~#^$@%&!*\s*]/.test(cbname)) {
		alert("船名不能包含特殊字符！");
		return;
	}
	SearchName=cbname;
	showCBWZXXInfo(actionName,selectedPage);
}
// 显示船舶信息
function showCBWZXXInfo(actionName,selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'cbname' : SearchName,
			'method' : 5,
			'scape':selectedPage
		},
		success : function(data) {
			CBWZInfor(data);
			gotoPageMethodName = "gotoPageNo";
			printPage(data.totalPage, selectedPage, 'showCBWZXXInfo',
					actionName, gotoPageMethodName, data.allTotal);
	},
	error : function(XMLHttpRequest) {
		ShowWarn($(".error", XMLHttpRequest.responseText).text());
	}
	});

}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value","");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showCBWZXXInfo(actionName, pageNo);
	}
}
function ShowWarn(warnString){
	$("#info tr").remove();
	$("#info tr").empty();
	newTable = $("#info");
	newTable.append("<tr><th colspan='2'>违章信息</th></tr>");
	if(warnString=="")
		newTable.append("<tr><td colspan='5' class='warn'>船名不正确或没有违章信息</td></tr>");
	else
		newTable.append("<tr><td colspan='5' class='warn'>"+warnString+"</td></tr>");
}
//显示违章处罚记录
function CBWZInfor(data) {
	$("#info tr").remove();
	$("#info tr").empty();
	newTable = $("#info");
	if(data.list.length==0){
		newTable.append("<tr><th colspan='2'>违章信息</th></tr>");
		newTable.append("<tr><td colspan='2' class='warn'>没有违章信息</td></tr>");
	}
	else{
	for ( var i = 0; i < data.list.length; i++){
		newTable.append("<tr><th colspan='2'>"+ data.list[i].FADD + "</th></tr>");
		newTable.append("<tr><td>受理号：</td><td>"+ data.list[i].SLH + "</td></tr>");
		newTable.append("<tr><td>受理时间：</td><td>"+ ShortTimeByformateDay(data.list[i].SLSJ) + "</td></tr>");
		newTable.append("<tr><td>中文船名：</td><td>"+ data.list[i].ZWCM + "</td></tr>");
		newTable.append("<tr><td>案由：</td><td>"+ data.list[i].AY + "</td></tr>");
		newTable.append("<tr><td>发案时间：</td><td>"+ ShortTimeByformateDay(data.list[i].FASJ) + "</td></tr>");
		newTable.append("<tr><td>发案地点：</td><td>"+ data.list[i].FADD + "</td></tr>");
		newTable.append("<tr><td>案件类别：</td><td>"+ data.list[i].AJLB + "</td></tr>");
		newTable.append("<tr><td>主要事实：</td><td>"+ data.list[i].ZYSS + "</td></tr>");
		newTable.append("<tr><td>执法手册编号：</td><td>"+ data.list[i].ZFSCBH + "</td></tr>");
		newTable.append("<tr><td>违法内容：</td><td>"+ data.list[i].WFNR + "</td></tr>");
		newTable.append("<tr><td>违反条款：</td><td>"+ data.list[i].WFTK + "</td></tr>");
		newTable.append("<tr><td>处罚条款：</td><td>"+ data.list[i].CFTK + "</td></tr>");
		newTable.append("<tr><td>处罚意见：</td><td>"+ data.list[i].CFYJ + "</td></tr>");
		
		var lb=data.list[i].CFLB.split("");
		var lbtext="";
		if(lb[0]=="1"&lbtext==""){
			lbtext="其他";
		}else if(lb[0]=="1"&lbtext!=""){
			lbtext+=",其他";
		}if(lb[1]=="1"&lbtext==""){
			lbtext="行政拘留";
		}else if(lb[1]=="1"&lbtext!=""){
			lbtext+=",行政拘留";
		}if(lb[2]=="1"&lbtext==""){
			lbtext="责令停产停业";
		}else if(lb[2]=="1"&lbtext!=""){
			lbtext+=",责令停产停业";
		}if(lb[3]=="1"&lbtext==""){
			lbtext="没收违法所得";
		}else if(lb[3]=="1"&lbtext!=""){
			lbtext+=",没收违法所得";
		}if(lb[4]=="1"&lbtext==""){
			lbtext="没收非法财务";
		}else if(lb[4]=="1"&lbtext!=""){
			lbtext+=",没收非法财务";
		}if(lb[5]=="1"&lbtext==""){
			lbtext="没收船舶";
		}else if(lb[5]=="1"&lbtext!=""){
			lbtext+=",没收船舶";
		}if(lb[6]=="1"&lbtext==""){
			lbtext="吊销证照";
		}else if(lb[6]=="1"&lbtext!=""){
			lbtext+=",吊销证照";
		}if(lb[7]=="1"&lbtext==""){
			lbtext="暂扣证照";
		}else if(lb[7]=="1"&lbtext!=""){
			lbtext+=",暂扣证照";
		}if(lb[8]=="1"&lbtext==""){
			lbtext="罚款";
		}else if(lb[8]=="1"&lbtext!=""){
			lbtext+=",罚款";
		}if(lb[9]=="1"&lbtext==""){
			lbtext="警告";
		}else if(lb[9]=="1"&lbtext!=""){
			lbtext+=",警告";
		}newTable.append("<tr><td>处罚类别：</td><td>"+ lbtext + "</td></tr>");
		
			if (data.list[i].SFCF == "1") {
				newTable.append("<tr><td>是否处罚：</td><td>是</td></tr>");
			} else {
				newTable.append("<tr><td>是否处罚：</td><td>否</td></tr>");
			}
			if (data.list[i].SFJA == "1") {
				newTable.append("<tr><td>是否结案：</td><td>是</td></tr>");
			} else {
				newTable.append("<tr><td>是否结案：</td><td>否</td></tr>");
			}
		newTable.append("<tr><td>结案日期：</td><td>"+ ShortTimeByformateDay(data.list[i].JARQ) + "</td></tr>");
	}
	}
}