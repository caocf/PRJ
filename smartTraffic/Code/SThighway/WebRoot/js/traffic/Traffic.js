var searchInput="";
var search_xzqhbm="";//行政区划编码
var bmnum=0;//编码编号
var fwlx="";//服务设施类型
$(document).ready(function(){
	setHeight();
	$("#top_text").text("交通量");//top上的显示
	showTrafficInfo('jxdczxx/dczxxlist',1);
	findxzqh();
	finddczlx('jxdczxx/optionlist','dczlx');//调查站类型
	finddcff('jxdczxx/optionlist','dcff');//调查方法
	findgdfs('jxdczxx/optionlist','gdfs');//供电方式
	findtxfs('jxdczxx/optionlist','txfs');//通讯方式
	findglgn('jxdczxx/optionlist','glgn');//公路功能
});
function setHeight(){
	var height=document.documentElement.clientHeight-50;
	if(height<720){
		$(".left_I1").css({"height":"720px"});
		$(".common_c1").css({"height":"720px"});
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".common_c1").css({"height":""+height+"px"});
	}
}
window.onresize=function(){
	var height=document.documentElement.clientHeight-50;
	if(height<720){
		$(".left_I1").css({"height":"720px"});
		$(".common_c1").css({"height":"720px"});
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".common_c1").css({"height":""+height+"px"});
	}
}
function findxzqh(){
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
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='CRselectBoxItem' ><a rel='"+list[i].xzqhdm+"'>"+list[i].xzqhmc+"</a></li>");
					$("#xzqh_ul").append(newLi);
				}
				SelectOption("xzqh",180);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function finddczlx(actionName,optionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'optionName':optionName
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='CRselectBoxItem' ><a rel='"+list[i].id_int+"'>"+list[i].name+"</a></li>");
					$("#dczlx_ul").append(newLi);
				}
				SelectOption("dczlx",180);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function finddcff(actionName,optionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'optionName':optionName
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='CRselectBoxItem' ><a rel='"+list[i].id_int+"'>"+list[i].name+"</a></li>");
					$("#dcff_ul").append(newLi);
				}
				SelectOption("dcff",180);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function findgdfs(actionName,optionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'optionName':optionName
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='CRselectBoxItem' ><a rel='"+list[i].id_int+"'>"+list[i].name+"</a></li>");
					$("#gdfs_ul").append(newLi);
				}
				SelectOption("gdfs",180);	
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function findtxfs(actionName,optionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'optionName':optionName
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='CRselectBoxItem' ><a rel='"+list[i].id_int+"'>"+list[i].name+"</a></li>");
					$("#txfs_ul").append(newLi);
				}
				SelectOption("txfs",180);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function findglgn(actionName,optionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'optionName':optionName
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='CRselectBoxItem' ><a rel='"+list[i].id_int+"'>"+list[i].name+"</a></li>");
					$("#glgn_ul").append(newLi);
				}
				SelectOption("glgn",180);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function showTrafficInfo(actionName,selectedPage){
	//searchInput=$("#service_info").val();
	/*var selectvalue="";
	if(searchInput=="设施名称"){
		searchInput="";
	}*/
	selectvalue=searchInput;
	var xzqhdm=$("#xzqh .abc").val();
	var dczlxbh=$("#dczlx .abc").val();
	var dcffbh=$("#dcff .abc").val();
	var gdfsbh=$("#gdfs .abc").val();
	var txfsbh=$("#txfs .abc").val();
	var glgnbh=$("#glgn .abc").val();
	if(xzqhdm==0||xzqhdm==330400){
		xzqhdm="";
	}
	if(dczlxbh==0){
		dczlxbh="";
	}
	if(dcffbh==0){
		dcffbh="";
	}
	if(gdfsbh==0){
		gdfsbh="";
	}
	if(txfsbh==0){
		txfsbh="";
	}
	if(glgnbh==0){
		glgnbh="";
	}
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'rows':10,
			'token':token,
			'selectvalue':selectvalue,
			'dczlxbh':dczlxbh,
			'xzqhdm':xzqhdm,
			'dcffbh':dcffbh,
			'gdfsbh':gdfsbh,
			'txfsbh':txfsbh,
			'glgnbh':glgnbh
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showTrafficInfo',
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
			var newTr = $("<tr class='addTr'></tr>");
			//调查站名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].dczmc)+"&nbsp;</td>"));
			//行政区划
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].xzqhmc)+"&nbsp;</td>"));
			//调查站类型
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].dczlxmc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].dcffmc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].gdfsmc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].txfsmc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].glgnmc)+"&nbsp;</td>"));
			//操作
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeTrafficInfoDetail('"+list[i].dczbh+"')>查看</a>" +
										"<a class='Operate' onclick=showInMap('"+list[i].dczbh+"','"+(i+1)+"') >地图</a></td>"));
			$(".listTable").append(newTr);
		}else{
			var newTr = $("<tr class='addTr'></tr>");
			//调查站名称
			newTr.append($("<td>"+judgeIsNull(list[i].dczmc)+"&nbsp;</td>"));
			//行政区划
			newTr.append($("<td>"+judgeIsNull(list[i].xzqhmc)+"&nbsp;</td>"));
			//调查站类型
			newTr.append($("<td>"+judgeIsNull(list[i].dczlxmc)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].dcffmc)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].gdfsmc)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].txfsmc)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].glgnmc)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td><a class='Operate' onclick=seeTrafficInfoDetail('"+list[i].dczbh+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].dczbh+"','"+(i+1)+"') >地图</a></td>"));
			$(".listTable").append(newTr);	
		}
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
		showTrafficInfo(actionName, pageNo);
	}
}
/**
 * 查询路段信息
 */
function searchServiceInfo(){
	search_xzqhbm="";
	$(".left_select_li").attr("class","left_no_select_li");
	$("#left_no_select1").attr("class","left_select");
	showServicesInfo('fwssmanager/fwqlist',1);
}
function goToService(){
	window.location.href=$("#basePath").val()+"page/services/Services.jsp";
}
function goTofeeStation(){
	window.location.href=$("#basePath").val()+"page/services/FeeStation.jsp";
}
/**
 * 查看交调信息详情
 * @param bzbm
 */
function seeTrafficInfoDetail(dczbh){
	$.ajax({
		url:'jxdczxx/dczxx',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'dczbh':dczbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				$("#dczmc_detail").text(judgeIsNull(list.dczmc));
				$("#xlmc_detail").text(judgeIsNull(list.lxjc));
				$("#dcffmc_detail").text(judgeIsNull(list.dcffmc));
				$("#dczlxmc_detail").text(judgeIsNull(list.dczlxmc));
				$("#dczzt_detail").text(judgeIsNull(list.dczzt));
				$("#gdfsmc_detail").text(judgeIsNull(list.gdfsmc));
				$("#txfsmc_detail").text(judgeIsNull(list.txfsmc));
				$("#xzqhmc_detail").text(judgeIsNull(list.xzqhmc));
				$("#glgnmc_detail").text(judgeIsNull(list.glgnmc));
				$("#gljg_detail").text(judgeIsNull(list.gljg));
				$("#qdmc_detail").text(judgeIsNull(list.qdmc));
				$("#qdzh_detail").text(judgeIsNull(conversionType(list.qdzh)));
				$("#zdmc_detail").text(judgeIsNull(list.zdmc));
				$("#zdzh_detail").text(judgeIsNull(conversionType(list.zdzh)));
				$("#zh_detail").text(judgeIsNull(conversionType(list.zh)));
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
	showfullbg();
	$("#DetailDiv").show();
}
/**
 * 进入交调信息地图
 * @param bzbm
 */
function showInMap(dczbh,num){
	var amount=Number((select_page-1)*10)+Number(num);
	window.open($("#basePath").val()+"page/highway/HighWayTrafficMap.jsp?dczbh="+dczbh+"&amount="+amount,"_blank");
}
function goToTraffic(){
	window.location.href=$("#basePath").val()+"page/traffic/Traffic.jsp";
}
function goToRealTraffic(){
	window.location.href=$("#basePath").val()+"page/traffic/RealTraffic.jsp";
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}
function trabuttonOver(){
	$("#tra_button1_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#tra_button1_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#tra_button1_right").css({"background":"url('image/main/right_pressed.png')"});
}
function trabuttonOut(){
	$("#tra_button1_left").css({"background":"url('image/main/left_normal.png')"});
	$("#tra_button1_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#tra_button1_right").css({"background":"url('image/main/right_normal.png')"});
}