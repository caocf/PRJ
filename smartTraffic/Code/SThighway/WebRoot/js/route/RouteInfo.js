var searchInput="";
var search_xzqhbm="";//行政区划编码
var bmnum=0;//编码编号
var searchList="";
$(document).ready(function(){
	//$("#top_text").text("路网信息");//top上的显示
	$("#route_select").attr("class","mtop_select");
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
			var newLi=$("<li class='left_no_select_li' id='left_no_select_li"+(i+1)+"' onclick=showThePartRoute('"+list[i].xzqhdm+"','"+list[i].xzqhmc+"',this,'"+(i+1)+"')>"+list[i].xzqhmc+"</li>");
			$("#left_select_child1").append(newLi);
		}
	}
	
	$(".left_no_select").attr("class","left_select");
	showRouteInfo('lxjbxxlist/lxgklist',1);

}
function showRouteInfo(actionName,selectedPage){
	searchInput=$("#bridge_info").val();
	var selectvalue="";
	if(searchInput=="路线代码、路线简称"){
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
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showRouteInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			
		},
		complete : function(){
			CloseLoadingDiv();
		}
	});
	
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		if(i%2==0){
			newTr = $("<tr class='addTr'></tr>");
			//路线代码
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].lxdm)+"&nbsp;</td>"));
			//路段编号
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].ldbh)+"&nbsp;</td>"));
			//路线简称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].lxjc)+"&nbsp;</td>"));	
			/*//路线地方名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].lxdfmc)+"&nbsp;</td>"));	*/
			//技术等级
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].jsdj)+"&nbsp;</td>"));	
			//里程
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].lc)+"&nbsp;</td>"));	
			//管理单位
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].gldwmc)+"&nbsp;</td>"));	
			//路段起点桩号
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(conversionType(list[i].ldqdzh))+"&nbsp;</td>"));	
			//路段终点桩号
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(conversionType(list[i].ldzdzh))+"&nbsp;</td>"));
			//操作
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeRouteInfoDetail('"+list[i].bzbm+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].bzbm+"','"+(i+1)+"') >地图</a></td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			//路线代码
			newTr.append($("<td>"+judgeIsNull(list[i].lxdm)+"&nbsp;</td>"));
			//路段编号
			newTr.append($("<td>"+judgeIsNull(list[i].ldbh)+"&nbsp;</td>"));
			//路线简称
			newTr.append($("<td>"+judgeIsNull(list[i].lxjc)+"&nbsp;</td>"));	
			/*//路线地方名称
			newTr.append($("<td>"+judgeIsNull(list[i].lxdfmc)+"&nbsp;</td>"));	*/
			//技术等级
			newTr.append($("<td>"+judgeIsNull(list[i].jsdj)+"&nbsp;</td>"));	
			//里程
			newTr.append($("<td>"+judgeIsNull(list[i].lc)+"&nbsp;</td>"));	
			//管理单位
			newTr.append($("<td>"+judgeIsNull(list[i].gldwmc)+"&nbsp;</td>"));	
			//路段起点桩号
			newTr.append($("<td>"+judgeIsNull(conversionType(list[i].ldqdzh))+"&nbsp;</td>"));	
			//路段终点桩号
			newTr.append($("<td>"+judgeIsNull(conversionType(list[i].ldzdzh))+"&nbsp;</td>"));
			//操作
			newTr.append($("<td><a class='Operate' onclick=seeRouteInfoDetail('"+list[i].bzbm+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].bzbm+"','"+(i+1)+"') >地图</a></td>"));
			
		}
		$(".listTable").append(newTr);
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='9' align='center'>暂无相关数据</td></tr>");
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
		showRouteInfo(actionName, pageNo);
	}
}
/**
 * 根据行政区划查看路段
 */
function showThePartRoute(xzqhdm,xzqhmc,thisop,num){
	$(".left_select_li").attr("class","left_no_select_li");
	$(".left_select").attr("class","left_no_select");
	if(xzqhdm==0){
		thisop.className="left_select";
		search_xzqhbm="";
	}else{
		thisop.className="left_select_li";
		search_xzqhbm=xzqhdm;
	}
	
	showRouteInfo('lxjbxxlist/lxgklist',1);
}
/**
 * 查询路段信息
 */
function searchRouteInfo(){
	search_xzqhbm="";
	$(".left_select_li").attr("class","left_no_select_li");
	$("#left_no_select1").attr("class","left_select");
	showRouteInfo('lxjbxxlist/lxgklist',1);
}
/**
 * 查看路网详情
 * @param bzbm
 */
function seeRouteInfoDetail(bzbm){
	$.ajax({
		url:'lxjbxxlist/lxqx',
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
				$("#route_name").text(judgeIsNull(list.lxjc));
				$("#lxdm").text(judgeIsNull(list.lxdm));
				$("#ldbm").text(judgeIsNull(list.ldbm));
				$("#lxjc").text(judgeIsNull(list.lxjc));
				$("#lxdfmc").text(judgeIsNull(list.lxdfmc));
				$("#jsdj").text(judgeIsNull(list.jsdj));
				$("#lc").text(judgeIsNull(list.lc)+"公里");//里程
				$("#gldw").text(judgeIsNull((list.gldwmc)));
				$("#xzqh").text(judgeIsNull(list.xzqh)+"（"+judgeIsNull(list.xzqhdm)+"）");
				$("#lxqdzh").text(judgeIsNull(conversionType(list.ldqdzh)));
				$("#qdsfwfjd").text(judgeIsNull(list.qdsfwfjd));//起点是否位分界点
				$("#lxzdzh").text(judgeIsNull(conversionType(list.ldzdzh)));
				$("#zdsfwfjd").text(judgeIsNull(list.zjsfwfjd));//////
				$("#dllx").text(judgeIsNull(list.dllx));
				$("#ljkd").text(judgeIsNull(list.ljkd)+"米");
				$("#cdfl").text(judgeIsNull(list.cdfl));
				$("#qdfjdlb").text(judgeIsNull(list.qdfjdlb));
				$("#zdfjdlb").text(judgeIsNull(list.zdfjdlb));
				$("#lmkd").text(judgeIsNull(list.lmkd));
				$("#mclx").text(judgeIsNull(list.mclx));
				$("#ldqdmc").text(judgeIsNull(list.ldqdmc));
				$("#ldzdmc").text(judgeIsNull(list.ldzdmc));
				$("#sjss").text(judgeIsNull(list.sjss));
				$("#sjxsjtl").text(judgeIsNull(list.sjxsjtl));
				$("#dm").text(judgeIsNull(list.dm));
				$("#hdmd").text(judgeIsNull(list.hdmd));////
				$("#sfdtl").text(judgeIsNull(list.sfdtl));
				$("#sfsfld").text(judgeIsNull(list.sfsfld));
				$("#sfqytc").text(judgeIsNull(list.sfqytc));
				
				
				$("#sfcgld").text(judgeIsNull(list.sfcgld));
				$("#sfyfgs").text(judgeIsNull(list.sfyfgs));
				$("#cfldgldwmc").text(judgeIsNull(list.cfldgldwmc));
				$("#cflxdm").text(judgeIsNull(list.cflxdm));
				$("#cfldqdzh").text(judgeIsNull(list.cfldqdzh));
				$("#cfldzdzh").text(judgeIsNull(list.cfldzdzh));
				$("#gydwmc").text(judgeIsNull(list.gydwmc));
				$("#gydwsshylb").text(judgeIsNull(list.gydwsshylb));
				$("#yhlxasjf").text(judgeIsNull(list.yhlxasjf));
				$("#yhlxazjlyf").text(judgeIsNull(list.yhlxazjlyf));
				$("#yhlx").text(judgeIsNull(list.yhlx));
				$("#yhlcgl").text(judgeIsNull(list.yhlcgl));
				$("#klvlcgl").text(judgeIsNull(list.klvlcgl));
				$("#ylvlcgl").text(judgeIsNull(list.ylvlcgl));
				$("#yssgbmlc").text(judgeIsNull(list.yssgbmlc));
				$("#ysswmybldlc").text(judgeIsNull(list.ysswmybldlc));
				$("#kgrq").text(judgeIsNull(list.kgrq));
				$("#jgrq").text(judgeIsNull(list.jgrq));
				$("#ysrq").text(judgeIsNull(list.ysrq));
				$("#tcrq").text(judgeIsNull(list.tcrq));
				$("#xjnd").text(judgeIsNull(list.xjnd));
				$("#gjbgrq").text(judgeIsNull(list.gjbgrq));
				$("#gjbgyy").text(judgeIsNull(list.gjbgyy));
				$("#bgyysm").text(judgeIsNull(list.bgyysm));
				
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
	showfullbg();
	$("#DetailDiv").show();
}
function seeAllInfo(){
	$(".hideTr").show();
	$(".aTr").hide();
}
/**
 * 进入路段地图
 * @param bzbm
 */
function showInMap(bzbm,num){
	var amount=Number((select_page-1)*10)+Number(num);
	window.open($("#basePath").val()+"page/highway/HighWayRouteMap.jsp?routebm="+bzbm+"&amount="+amount,"_blank");
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}