var searchInput="";
var search_xzqhbm="";//行政区划编码
var bmnum=0;//编码编号
var fwlx="";//服务设施类型
$(document).ready(function(){
	//$("#top_text").text("服务设施");//top上的显示
	$("#service_select").attr("class","mtop_select");
	showServicesInfo('fwssmanager/fwqlist',1);
});
function showServicesInfo(actionName,selectedPage){
	searchInput=$("#service_info").val();
	var selectvalue="";
	if(searchInput==$("#service_info").prop("defaultValue")){
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
			'selectvalue':selectvalue
		},
		beforeSend : function() {
			setHeight();
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
					printPage(data.result.obj.pages, selectedPage, 'showServicesInfo',
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
			//服务设施名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].fwqmc)+"&nbsp;</td>"));
			//所属公司
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].ssgs)+"&nbsp;</td>"));
			//线路名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].xlmc)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeServiceInfoDetail('"+list[i].fwqbh+"')>查看</a>" +
		
										"<a class='Operate' onclick=showInMap('"+list[i].fwqbh+"','"+(i+1)+"') >地图</a></td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			//服务设施名称
			newTr.append($("<td>"+judgeIsNull(list[i].fwqmc)+"&nbsp;</td>"));
			//所属公司
			newTr.append($("<td>"+judgeIsNull(list[i].ssgs)+"&nbsp;</td>"));
			//线路名称
			newTr.append($("<td>"+judgeIsNull(list[i].xlmc)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td><a class='Operate' onclick=seeServiceInfoDetail('"+list[i].fwqbh+"')>查看</a>" +
			
						"<a class='Operate' onclick=showInMap('"+list[i].fwqbh+"','"+(i+1)+"') >地图</a></td>"));
			
		}
		$(".listTable").append(newTr);
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='5' align='center'>暂无相关数据</td></tr>");
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
		showServicesInfo(actionName, pageNo);
	}
}
function deleteService(fwqbh){
	if(confirm("你确定要删除该服务区吗？")){
		$.ajax({
			url:'fwssmanager/deletefwq',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'fwssbh':fwqbh
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("删除成功");
					showServicesInfo('fwssmanager/fwqlist',select_page);
					//window.location.reload();
				}else{
					alert(text);
				}
			}
		});
	}
}
function addfwqService(){
	var jyss=document.getElementsByName("jyss");
	for(var i=0;i<jyss.length;i++){
		if(jyss[i].checked==true)jyss[i].checked=false;
	}
	var cyss=document.getElementsByName("cyss");
	for(var i=0;i<cyss.length;i++){
		if(cyss[i].checked==true)cyss[i].checked=false;
	}
	var zsss=document.getElementsByName("zsss");
	for(var i=0;i<zsss.length;i++){
		if(zsss[i].checked==true)zsss[i].checked=false;
	}
	var gwss=document.getElementsByName("gwss");
	for(var i=0;i<gwss.length;i++){
		if(gwss[i].checked==true)gwss[i].checked=false;
	}
	var clwxss=document.getElementsByName("clwxss");
	for(var i=0;i<clwxss.length;i++){
		if(clwxss[i].checked==true)clwxss[i].checked=false;
	}
	$("#fwqmc,#xlmc,#ssgs,#sxjkzh,#xxjkzh,#sxckzh,#xxckzh,#glzdzh,#zczzh,#gljjzh").val("");
	showfullbg();
	$("#fwq_title").text("新增服务区");
	$("#addfwqDiv").show();
}
function closeaddfwqDiv(){
	$("#fullbg,#addfwqDiv").hide();
}
var editbh="";
function editService(fwssbh){
	$("#fwq_title").text("编辑服务区");
	editbh=fwssbh;
	$.ajax({
		url:'fwssmanager/fwqxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'fwssbh':fwssbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var fwssinfo=data.result.obj;
				$("#fwqmc").val(judgeIsNull(fwssinfo.fwqmc));
				$("#ssgs").val(judgeIsNull(fwssinfo.ssgs));
				$("#xlmc").val(judgeIsNull(fwssinfo.xlmc));
				$("#sxjkzh").val(judgeIsNull(fwssinfo.sxjkzh));
				$("#xxjkzh").val(judgeIsNull(fwssinfo.xxjkzh));
				$("#sxckzh").val(judgeIsNull(fwssinfo.sxckzh));
				$("#xxckzh").val(judgeIsNull(fwssinfo.xxckzh));
				$("#glzdzh").val(judgeIsNull(fwssinfo.glzdzh));
				$("#zczzh").val(judgeIsNull(fwssinfo.zczzh));
				$("#gljjzh").val(judgeIsNull(fwssinfo.gljjzh));
				var jyss=document.getElementsByName("jyss");
				var cyss=document.getElementsByName("cyss");
				var zsss=document.getElementsByName("zsss");
				var gwss=document.getElementsByName("gwss");
				var clwxss=document.getElementsByName("clwxss");
				if(fwssinfo.jyss==0){
					jyss[0].checked=true;
				}else{
					jyss[1].checked=true;
				}
				if(fwssinfo.cyss==0){
					cyss[0].checked=true;
				}else{
					cyss[1].checked=true;
				}
				if(fwssinfo.zsss==0){
					zsss[0].checked=true;
				}else{
					zsss[1].checked=true;
				}
				if(fwssinfo.gwss==0){
					gwss[0].checked=true;
				}else{
					gwss[1].checked=true;
				}
				if(fwssinfo.clwxss==0){
					clwxss[0].checked=true;
				}else{
					clwxss[1].checked=true;
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
	showfullbg();
	$("#addfwqDiv").show();
}
function addfwq(){
	if(!checkInfo()){
		return;
	}
	var jyss=$("input[name='jyss']:checked").val();
	var cyss=$("input[name='cyss']:checked").val();
	var zsss=$("input[name='zsss']:checked").val();
	var gwss=$("input[name='gwss']:checked").val();
	var clwxss=$("input[name='clwxss']:checked").val();
	if(jyss==undefined){
		jyss=0;
	}
	if(cyss==undefined){
		cyss=0;
	}
	if(zsss==undefined){
		zsss=0;
	}
	if(gwss==undefined){
		gwss=0;
	}
	if(clwxss==undefined){
		clwxss=0;
	}
	if($("#fwq_title").text().indexOf("新增")>=0){//新增服务设施
		$.ajax({
			url:'fwssmanager/savefwq',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'fwssbh':18,
				'fwq.fwqmc':$("#fwqmc").val(),
				'fwq.ssgs':$("#ssgs").val(),
				'fwq.xlmc':$("#xlmc").val(),
				'fwq.sxjkzh':$("#sxjkzh").val(),
				'fwq.xxjkzh':$("#xxjkzh").val(),
				'fwq.sxckzh':$("#sxckzh").val(),
				'fwq.xxckzh':$("#xxckzh").val(),
				'fwq.jyss':jyss,
				'fwq.cyss':cyss,
				'fwq.zsss':zsss,
				'fwq.gwss':gwss,
				'fwq.clwxss':clwxss,
				'fwq.glzdzh':$("#glzdzh").val(),
				'fwq.zczzh':$("#zczzh").val(),
				'fwq.gljjzh':$("#gljjzh").val()
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("新增成功");
					window.location.reload();
				}else{
					alert(text);
				}
			}
		});
	}else{
		$.ajax({
			url:'fwssmanager/updatefwq',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'fwssbh':editbh,
				'fwq.fwqbh':editbh,
				'fwq.fwqmc':$("#fwqmc").val(),
				'fwq.ssgs':$("#ssgs").val(),
				'fwq.xlmc':$("#xlmc").val(),
				'fwq.sxjkzh':$("#sxjkzh").val(),
				'fwq.xxjkzh':$("#xxjkzh").val(),
				'fwq.sxckzh':$("#sxckzh").val(),
				'fwq.xxckzh':$("#xxckzh").val(),
				'fwq.jyss':jyss,
				'fwq.cyss':cyss,
				'fwq.zsss':zsss,
				'fwq.gwss':gwss,
				'fwq.clwxss':clwxss,
				'fwq.glzdzh':$("#glzdzh").val(),
				'fwq.zczzh':$("#zczzh").val(),
				'fwq.gljjzh':$("#gljjzh").val()
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("编辑成功");
					closeaddfwqDiv();
					showServicesInfo('fwssmanager/fwqlist',select_page);
				}else{
					alert(text);
				}
			}
		});
	}
}
function checkInfo(){
	if($("#fwqmc").val()==""){
		alert("请输入服务区名称");
		return false;
	}
	if($("#fwqmc").val().length>25){
		alert("服务区名称不能大于25位");
		return false;
	}
	if($("#ssgs").val().length>100){
		alert("所属公司名称不能大于100位");
		return false;
	}
	if($("#xlmc").val().length>50){
		alert("线路名称不能大于50位");
		return false;
	}
	if($("#sxjkzh").val().length>25){
		alert("上行进口桩号不能大于25位");
		return false;
	}
	if($("#xxjkzh").val().length>25){
		alert("下行进口桩号不能大于25位");
		return false;
	}
	if($("#sxckzh").val().length>25){
		alert("上行出口桩号不能大于25位");
		return false;
	}
	if($("#xxckzh").val().length>25){
		alert("下行出口桩号不能大于25位");
		return false;
	}
	if($("#glzdzh").val().length>25){
		alert("管理中队桩号不能大于25位");
		return false;
	}
	if($("#zczzh").val().length>25){
		alert("治超站桩号不能大于25位");
		return false;
	}
	if($("#gljjzh").val().length>25){
		alert("管理交警桩号不能大于25位");
		return false;
	}
	return true;
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
 * 查看服务设施详情页面
 * @param bzbm
 */
function seeServiceInfoDetail(fwqbh){
	$.ajax({
		url:'fwssmanager/fwqxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'fwssbh':fwqbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				var have="有";
				var nothave="无";
				$("#fwqmc_detail").text(judgeIsNull(list.fwqmc));
				$("#xlmc_detail").text(judgeIsNull(list.lxjc));
				$("#ssgs_detail").text(judgeIsNull(list.ssgs));
				$("#glzdzh_detail").text(judgeIsNull(list.glzdzh));
				$("#zczzh_detail").text(judgeIsNull(list.zczzh));
				$("#gljjzh_detail").text(judgeIsNull(list.gljjzh));
				$("#sxjkzh_detail").text(judgeIsNull(list.sxjkzh));
				$("#xxjkzh_detail").text(judgeIsNull(list.xxjkzh));
				$("#sxckzh_detail").text(judgeIsNull(list.sxckzh));
				$("#xxckzh_detail").text(judgeIsNull(list.xxckzh));
				if(list.jyss==1){
					$("#jyss_detail").text(have);
				}else{
					$("#jyss_detail").text(nothave);
				}
				if(list.cyss==1){
					$("#cyss_detail").text(have);
				}else{
					$("#cyss_detail").text(nothave);
				}
				if(list.zsss==1){
					$("#zsss_detail").text(have);
				}else{
					$("#zsss_detail").text(nothave);
				}
				if(list.gwss==1){
					$("#gwss_detail").text(have);
				}else{
					$("#gwss_detail").text(nothave);
				}
				if(list.clwxss==1){
					$("#clwxss_detail").text(have);
				}else{
					$("#clwxss_detail").text(nothave);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
	showfullbg();
	$("#fwqdetailDiv").show();
}
function closefwqdetailDiv(){
	$("#fullbg,#fwqdetailDiv").hide();
}
/**
 * 进入服务设施地图
 * @param bzbm
 */
function showInMap(fwqbh,num){
	var amount=Number((select_page-1)*10)+Number(num);
	window.open($("#basePath").val()+"page/highway/HighWayServiceMap.jsp?fwqbh="+fwqbh+"&amount="+amount,"_blank");
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}