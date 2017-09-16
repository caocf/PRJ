var SearchName;
var Gloval_jf_warn;
var Gloval_jf_nowarn;
// 显示船舶信息
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
	showCBJFXXInfo(actionName,selectedPage);
	
	
}
//弹出框初始化
function showInfor(num){
	$(".XLH").text(Gloval_jf_warn[num].XLH);
	$(".KPRQ").text(ShortTimeByformateDay(Gloval_jf_warn[num].KPRQ));
	$(".JFXMDM").text(Gloval_jf_warn[num].JFXMDM);
	$(".KPDWMC").text(Gloval_jf_warn[num].KPDWMC);
	$(".JFXMMC").text(Gloval_jf_warn[num].JFXMMC);
	$(".SFFSDM").text(Gloval_jf_warn[num].SFFSDM);
	$(".SFFSMC").text(Gloval_jf_warn[num].SFFSMC);
	$(".KPXLH").text(Gloval_jf_warn[num].KPXLH);
	$(".YXQQ").text(ShortTimeByformateDay(Gloval_jf_warn[num].YXQQ));
	$(".YXQZ").text(ShortTimeByformateDay(Gloval_jf_warn[num].YXQZ));
	$(".YJZE").text(Gloval_jf_warn[num].YJZE);
	$(".SJZE").text(Gloval_jf_warn[num].SJZE);
	$(".TFBZ").text(Gloval_jf_warn[num].TFBZ);
	$(".ZFBZ").text(Gloval_jf_warn[num].ZFBZ);
	$(".XHBZ").text(Gloval_jf_warn[num].XHBZ);
	$(".KPRXM").text(Gloval_jf_warn[num].KPRXM);
	$(".KPDSDM").text(Gloval_jf_warn[num].KPDSDM);
	$(".KPDSMC").text(Gloval_jf_warn[num].KPDSMC);
	$("#dialog_div").show();
	$("#dialog_div").dialog( {
		title : null,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
//弹出框初始化
function showInfor(a,b){
	$(".XLH").text(Gloval_jf_nowarn[a][1][b].XLH);
	$(".KPRQ").text(ShortTimeByformateDay(Gloval_jf_nowarn[a][1][b].KPRQ));
	$(".JFXMDM").text(Gloval_jf_nowarn[a][1][b].JFXMDM);
	$(".KPDWMC").text(Gloval_jf_nowarn[a][1][b].KPDWMC);
	$(".JFXMMC").text(Gloval_jf_nowarn[a][1][b].JFXMMC);
	$(".SFFSDM").text(Gloval_jf_nowarn[a][1][b].SFFSDM);
	$(".SFFSMC").text(Gloval_jf_nowarn[a][1][b].SFFSMC);
	$(".KPXLH").text(Gloval_jf_nowarn[a][1][b].KPXLH);
	$(".YXQQ").text(ShortTimeByformateDay(Gloval_jf_nowarn[a][1][b].YXQQ));
	$(".YXQZ").text(ShortTimeByformateDay(Gloval_jf_nowarn[a][1][b].YXQZ));
	$(".YJZE").text(Gloval_jf_nowarn[a][1][b].YJZE);
	$(".SJZE").text(Gloval_jf_nowarn[a][1][b].SJZE);
	$(".TFBZ").text(Gloval_jf_nowarn[a][1][b].TFBZ);
	$(".ZFBZ").text(Gloval_jf_nowarn[a][1][b].ZFBZ);
	$(".XHBZ").text(Gloval_jf_nowarn[a][1][b].XHBZ);
	$(".KPRXM").text(Gloval_jf_nowarn[a][1][b].KPRXM);
	$(".KPDSDM").text(Gloval_jf_nowarn[a][1][b].KPDSDM);
	$(".KPDSMC").text(Gloval_jf_nowarn[a][1][b].KPDSMC);
	$("#dialog_div").show();
	$("#dialog_div").dialog( {
		title : null,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
function CloseDialog(){
	$("#dialog_div").dialog("close");
}
// 显示船舶信息
function showCBJFXXInfo(actionName,selectedPage) {
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	if(selectedPage==1){
		$("#WarnInfor tr").remove();
		$("#WarnInfor tr").empty();
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'cbname' : SearchName,
			'method' : 3,
			'scape':selectedPage
		},
		success : function(data) {
			CBJFInfor(data);
			gotoPageMethodName = "gotoPageNo";
			printPage(data.totalPage, selectedPage, 'showCBJFXXInfo',
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
		showCBJFXXInfo(actionName, pageNo);
	}
}
function ShowWarn(warnString){
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><th colspan='7'>船舶缴费信息</th></tr>");
	if(warnString=="")
		newTable.append("<tr><td colspan='7' class='warn'>船名不正确或没有缴费信息</td></tr>");
	else
		newTable.append("<tr><td colspan='7' class='warn'>"+warnString+"</td></tr>");
}
// 显示缴费信息
function CBJFInfor(data) {
	
	newTable = $("#Infor");
	Gloval_jf_warn=data.jf_warn;
	Gloval_jf_nowarn=data.jf_nowarn;
	if(data.list.length==0){
		newTable.append("<tr><th colspan='8'>船舶缴费信息</th></tr>");
		newTable.append("<tr><td colspan='8' class='warn'>没有缴费信息</td></tr>");
	}
	else{	
		if(data.jf_warn.length!=0){
			$("#WarnInfor").append("<tr><th colspan='8'>欠费缴费信息</th></tr>");
			$("#WarnInfor").append("<tr><td colspan='2'>序列号</td><td>缴费项目</td><td>应缴金额</td><td>实缴金额</td>"
					+ "<td>有效期起</td><td>有效期止</td><td>操作</td></tr>");
			for ( var i = 0; i < data.jf_warn.length; i++){
				$("#WarnInfor").append("<tr><td colspan='2'>" + data.jf_warn[i].XLH + "</td><td>"
						+ data.jf_warn[i].JFXMMC + "</td><td>" + data.jf_warn[i].YJZE
						+ "</td><td>" + data.jf_warn[i].SJZE + "</td><td>"
						+ ShortTimeByformateDay(data.jf_warn[i].YXQQ) + "</td><td>" + ShortTimeByformateDay(data.jf_warn[i].YXQZ)
						+ "</td><td> <a id='xlh'"+data.jf_warn[i].XLH+" class='operation' onclick=showInfor("+i+")>查看</a></td><td></tr>");
			}
		}
		if(data.jf_nowarn.length!=0){
			newTable.append("<tr><th colspan='8'>已缴费信息</th></tr>");
			newTable.append("<tr><td colspan='2'>序列号</td><td>缴费项目</td><td>应缴金额</td><td>实缴金额</td>"
					+ "<td>有效期起</td><td>有效期止</td><td>操作</td></tr>");
			for ( var i = 0; i < data.jf_nowarn.length; i++){
				var html="<tr><td rowspan="+data.jf_nowarn[i][1].length+" style='border-right: solid 1px #cccccc;' >" + data.jf_nowarn[i][0] + "月</td>";
				for ( var j = 0; j < data.jf_nowarn[i][1].length; j++){
					html+="<td>" + data.jf_nowarn[i][1][j].XLH + "</td><td>"
							+ data.jf_nowarn[i][1][j].JFXMMC + "</td><td>" + data.jf_nowarn[i][1][j].YJZE
							+ "</td><td>" + data.jf_nowarn[i][1][j].SJZE + "</td><td>"
							+ ShortTimeByformateDay(data.jf_nowarn[i][1][j].YXQQ) + "</td><td>" + ShortTimeByformateDay(data.jf_nowarn[i][1][j].YXQZ)
							+ "</td><td> <a id='xlh'"+data.jf_nowarn[i][1][j].XLH+" class='operation' onclick=showInfor("+i+","+j+")>查看</a></td><td></tr>";
				}
				newTable.append(html);	
			}
		}
		
	}
}
