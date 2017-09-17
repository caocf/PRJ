var searchInput="";
var search_xzqhbm="";//行政区划编码
var total="";
var type=4;//高级搜索类型
$(document).ready(function(){
	//$("#top_text").text("桥梁信息");//top上的显示
	$("#bridge_select").attr("class","mtop_select");
	showPartInfo();
});
//显示行政区划
function showPartInfo(){
	$.ajax({
		url:'qljbxxlist/xzqhlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				appendToPart(list);
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function appendToPart(list){
	for(var i=0;i<list.length;i++){
		if(list[i].sjxzqhdm!=null){
			var newLi=$("<li class='left_no_select_li' id='left_no_select_li"+(i+1)+"' onclick=showThePartBridge('"+list[i].xzqhdm+"','"+list[i].xzqhmc+"',this)>"+list[i].xzqhmc+"</li>");
			$("#left_select_child1").append(newLi);
		}
	}
	$(".left_no_select").attr("class","left_select");
	showBridgeInfo('qljbxxlist/qlgklist',1);
}
function showBridgeInfo(actionName,selectedPage){
	searchInput=$("#bridge_info").val();
	var selectvalue="";
	if(searchInput=="桥梁名称"){
		searchInput="";
	}
	selectvalue=searchInput;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'rows':10,
			'token':token,
			'xzqhdm':search_xzqhbm,
			'selectvalue':selectvalue
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==1){
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showBridgeInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
	
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		if(i%2==0){
			newTr = $("<tr class='addTr'></tr>");
			/*//桥梁代码
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qldm)+"&nbsp;</td>"));*/
			//桥梁名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qlmc)+"&nbsp;</td>"));
			//所在路段
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].lxjc)+"&nbsp;</td>"));	
			//桥梁中心桩号
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(conversionType(list[i].qlzxzh))+"&nbsp;</td>"));	
			//桥梁全长
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qlqc)+"&nbsp;</td>"));	
			//管养单位名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].gydwmc)+"&nbsp;</td>"));	
			//监管单位名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].jgdwmc)+"&nbsp;</td>"));	
			//修建年度
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].xjnd)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeBridgeInfoDetail('"+list[i].bzbm+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].bzbm+"','"+(i+1)+"') >地图</a></td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			/*//桥梁代码
			newTr.append($("<td>"+judgeIsNull(list[i].qldm)+"&nbsp;</td>"));*/
			//桥梁名称
			newTr.append($("<td>"+judgeIsNull(list[i].qlmc)+"&nbsp;</td>"));
			//所在路段
			newTr.append($("<td>"+judgeIsNull(list[i].lxjc)+"&nbsp;</td>"));	
			//桥梁中心桩号
			newTr.append($("<td>"+judgeIsNull(conversionType(list[i].qlzxzh))+"&nbsp;</td>"));	
			//桥梁全长
			newTr.append($("<td>"+judgeIsNull(list[i].qlqc)+"&nbsp;</td>"));	
			//管养单位名称
			newTr.append($("<td>"+judgeIsNull(list[i].gydwmc)+"&nbsp;</td>"));	
			//监管单位名称
			newTr.append($("<td>"+judgeIsNull(list[i].jgdwmc)+"&nbsp;</td>"));	
			//修建年度
			newTr.append($("<td>"+judgeIsNull(list[i].xjnd)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td><a class='Operate' onclick=seeBridgeInfoDetail('"+list[i].bzbm+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].bzbm+"','"+(i+1)+"') >地图</a></td>"));
			
		}
		$(".listTable").append(newTr);
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='8' align='center'>暂无相关数据</td></tr>");
	$(".listTable").append(newTr);
	$("#pageDiv").hide();
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").val();
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").prop("value", "1");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)||parseInt(pageNo)==0) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showBridgeInfo(actionName, pageNo);
	}
}
/**
 * 根据行政区划查看桥梁
 */
function showThePartBridge(xzqhdm,xzqhmc,thisop){
	$(".left_select_li").attr("class","left_no_select_li");
	$(".left_select").attr("class","left_no_select");
	if(xzqhdm==0){
		thisop.className="left_select";
		search_xzqhbm="";
	}else{
		thisop.className="left_select_li";
		search_xzqhbm=xzqhdm;
	}
	showBridgeInfo('qljbxxlist/qlgklist',1);
}
/**
 * 查询桥梁信息
 */
function searchBridgeInfo(){
	search_xzqhbm="";
	$(".left_select_li").attr("class","left_no_select_li");
	$("#left_no_select1").attr("class","left_select");
	showBridgeInfo('qljbxxlist/qlgklist',1);
}
/**
 * 进入桥梁详情页面
 * @param bzbm
 */
function seeBridgeInfoDetail(bzbm){
	$.ajax({
		url:'qljbxxlist/qlqx',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'bzbm':bzbm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				$("#bridge_name").text(judgeIsNull(list.qlmc));
				$("#qlmc").text(judgeIsNull(list.qlmc));
				$("#qlqc").text(judgeIsNull(list.qlqc)+"米");
				$("#lxjc").text(judgeIsNull(list.lxjc));
				$("#lxdm").text(judgeIsNull(list.lxdm));
				$("#qlzxzh").text(judgeIsNull(conversionType(list.qlzxzh)));
				$("#ljzc").text(judgeIsNull(list.dkjzc)+"米");//路径总长
				$("#qmjk").text((list.qmjk)+"米");
				$("#sjhzdj").text((list.sjhzdj));
				$("#kydwmc").text(judgeIsNull(list.kydwmc));
				$("#thdj").text(judgeIsNull(list.thdj));
				$("#dtfzsslx").text(judgeIsNull(list.dtfzsslx));
				$("#sfhtlj").text(judgeIsNull(list.sfgtlj));//是否互通立交
				$("#jsdwmc").text(judgeIsNull(list.jsdwmc));
				$("#sjdwmc").text(judgeIsNull(list.sjdwmc));
				$("#sgdwmc").text(judgeIsNull(list.sgdwmc));
				$("#jldwmc").text(judgeIsNull(list.jldwmc));
				$("#xjnd").text(judgeIsNull(list.xjnd));
				$("#jgdwmc").text(judgeIsNull(list.jgdwmc));
				$("#gydwmc").text(judgeIsNull(list.gydwmc));
				$("#gydwsshylb").text(judgeIsNull(list.gydwsshylb));
				$("#pddj").text(judgeIsNull(list.pddj));
				$("#pdrq").text(judgeIsNull(list.pdrq));
				$("#gznd").text(judgeIsNull(list.gznd));
				$("#gcxz").text(judgeIsNull(list.gcxz));
				$("#gldwmc").text(judgeIsNull(list.gldwmc));
				
				$("#xzqhdm").text(judgeIsNull(list.xzqhdm));
				$("#xzqh").text(judgeIsNull(list.xzqh));
				$("#qljd").text(judgeIsNull(list.qljd));
				$("#qlwd").text(judgeIsNull(list.qlwd));
				$("#qlxz").text(judgeIsNull(list.qlxz));
				$("#sfxz").text(judgeIsNull(list.sfxz));
				$("#qlszdm").text(judgeIsNull(list.qlszdm));
				$("#kydwlx").text(judgeIsNull(list.kydwlx));
				$("#qlytfl").text(judgeIsNull(list.qlytfl));
				$("#synx").text(judgeIsNull(list.synx));
				$("#qlkjfl").text(judgeIsNull(list.qlkjfl));
				$("#jszkpd").text(judgeIsNull(list.jszkpd));
				$("#zjdqjcrq").text(judgeIsNull(list.zjdqjcrq));
				$("#ycqjtgzcs").text(judgeIsNull(list.ycqjtgzcs));
				$("#ljqlb").text(judgeIsNull(list.ljqlb));
				$("#sfgtlj").text(judgeIsNull(list.sfgtlj));
				$("#ljqkyglx").text(judgeIsNull(list.ljqkyglx));
				$("#ljqltglx").text(judgeIsNull(list.ljqltglx));
				$("#zybhwz").text(judgeIsNull(list.zybhwz));
				$("#zybhms").text(judgeIsNull(list.zybhms));
				$("#dkjzc").text(judgeIsNull(list.dkjzc));
				$("#dkzdkj").text(judgeIsNull(list.dkzdkj));
				$("#qlkjzh").text(judgeIsNull(list.qlkjzh));
				$("#qlqk").text(judgeIsNull(list.qlqk));
				$("#qmjk").text(judgeIsNull(list.qmjk));
				$("#qlks").text(judgeIsNull(list.qlks));
				$("#zqks").text(judgeIsNull(list.zqks));
				$("#zqzk").text(judgeIsNull(list.zqzk));
				$("#zqbk").text(judgeIsNull(list.zqbk));
				$("#qyqc").text(judgeIsNull(list.qyqc));
				$("#hyqc").text(judgeIsNull(list.hyqc));
				$("#xcdk").text(judgeIsNull(list.xcdk));
				$("#rxdk").text(judgeIsNull(list.rxdk));
				$("#qlgd").text(judgeIsNull(list.qlgd));
				$("#qmbg").text(judgeIsNull(list.qmbg));
				$("#qxjk").text(judgeIsNull(list.qxjk));
				$("#qxjg").text(judgeIsNull(list.qxjg));
				$("#zdpm").text(judgeIsNull(list.zdpm));
				$("#zdgl").text(judgeIsNull(list.zdgl));
				$("#sbjgxs").text(judgeIsNull(list.sbjgxs));
				$("#sbjgcl").text(judgeIsNull(list.sbjgcl));
				$("#xbjgxs").text(judgeIsNull(list.xbjgxs));
				$("#xbcllx").text(judgeIsNull(list.xbcllx));
				$("#qdlx").text(judgeIsNull(list.qdlx));
				$("#qtlx").text(judgeIsNull(list.qtlx));
				$("#jcjgxs").text(judgeIsNull(list.jcjgxs));
				$("#yqjgxs").text(judgeIsNull(list.yqjgxs));
				$("#zzlx").text(judgeIsNull(list.zzlx));
				$("#ssflx").text(judgeIsNull(list.ssflx));
				$("#gqskb").text(judgeIsNull(list.gqskb));
				$("#qmzp").text(judgeIsNull(list.qmzp));
				$("#qmxx").text(judgeIsNull(list.qmxx));
				$("#qmqk").text(judgeIsNull(list.qmqk));
				$("#wpxtz").text(judgeIsNull(list.wpxtz));
				$("#qwdx").text(judgeIsNull(list.qwdx));
				$("#qmpzlx").text(judgeIsNull(list.qmpzlx));
				$("#zqjmxs").text(judgeIsNull(list.zqjmxs));
				$("#yqjmxs").text(judgeIsNull(list.yqjmxs));
				$("#qlyshz").text(judgeIsNull(list.qlyshz));
				$("#kzdj").text(judgeIsNull(list.kzdj));
				$("#hcdzjzp").text(judgeIsNull(list.hcdzjzp));
				$("#hczdbg").text(judgeIsNull(list.hczdbg));
				$("#sjhspl").text(judgeIsNull(list.sjhspl));
				$("#zzj").text(judgeIsNull(list.zzj));
				$("#sjhsw").text(judgeIsNull(list.sjhsw));
				$("#cnsw").text(judgeIsNull(list.cnsw));
				$("#lszghsw").text(judgeIsNull(list.lszghsw));
				$("#tcrq").text(judgeIsNull(list.tcrq));
				$("#kgrq").text(judgeIsNull(list.kgrq));
				$("#jgrq").text(judgeIsNull(list.jgrq));
				$("#gjbgrq").text(judgeIsNull(list.gjbgrq));
				$("#gzwgrq").text(judgeIsNull(list.gzwgrq));
				$("#gjbgyy").text(judgeIsNull(list.gjbgyy));
				$("#bgyysm").text(judgeIsNull(list.bgyysm));
				$("#bz").text(judgeIsNull(list.bz));
				
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
	showfullbg();
	$("#bridgeDetailDiv").show();
}
function seeAllInfo(){
	$(".hideTr").show();
	$(".aTr").hide();
}
function closebridgeDetailDiv(){
	$("#fullbg,#bridgeDetailDiv").hide();
	$(".hideTr").hide();
	$(".aTr").show();
}
/**
 * 进入桥梁地图
 * @param bzbm
 */
function showInMap(bzbm,num){
	var amount=Number((select_page-1)*10)+Number(num);
	window.open($("#basePath").val()+"page/highway/HighWayBridgeMap.jsp?bridgebm="+bzbm+"&amount="+amount,"_blank");
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}