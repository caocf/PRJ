var searchInput="";
var search_xzqhbm="";//行政区划编码
var bmnum=0;//编码编号
var fwlx="";//服务设施类型
$(document).ready(function(){
	//$("#top_text").text("服务设施");//top上的显示
	$("#service_select").attr("class","mtop_select");
	showFeeStationInfo('fwssmanager/sfzlist',1);
});
function showFeeStationInfo(actionName,selectedPage){
	searchInput=$("#service_info").val();
	var selectvalue="";
	if(searchInput=="收费站名称"){
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
					TableIsNull()
				}else{
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showFeeStationInfo',
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
			//收费站名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].sfzmc)+"&nbsp;</td>"));
			//收费站类型
			if(list[i].sfzlxbh==1){
				newTr.append($("<td style='background:#f9f9f9'>主线收费站&nbsp;</td>"));
			}else if(list[i].sfzlxbh==2){
				newTr.append($("<td style='background:#f9f9f9'>匝道收费站&nbsp;</td>"));
			}else{
				newTr.append($("<td style='background:#f9f9f9'>&nbsp;</td>"));
			}
			
			//出口指向
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].ckzx)+"&nbsp;</td>"));
			//线路名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].xlmc)+"&nbsp;</td>"));	
			//所属公司
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].ssgs)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeFeeStationDetail('"+list[i].sfzbh+"')>查看</a>" +
					
						"<a class='Operate' onclick=showInMap('"+list[i].sfzbh+"','"+(i+1)+"') >地图</a></td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			//收费站名称
			newTr.append($("<td>"+judgeIsNull(list[i].sfzmc)+"&nbsp;</td>"));
			//收费站类型
			if(list[i].sfzlxbh==1){
				newTr.append($("<td>主线收费站&nbsp;</td>"));
			}else if(list[i].sfzlxbh==2){
				newTr.append($("<td>匝道收费站&nbsp;</td>"));
			}else{
				newTr.append($("<td>&nbsp;</td>"));
			}
			//出口指向
			newTr.append($("<td>"+judgeIsNull(list[i].ckzx)+"&nbsp;</td>"));
			//线路名称
			newTr.append($("<td>"+judgeIsNull(list[i].xlmc)+"&nbsp;</td>"));	
			//所属公司
			newTr.append($("<td>"+judgeIsNull(list[i].ssgs)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td><a class='Operate' onclick=seeFeeStationDetail('"+list[i].sfzbh+"')>查看</a>" +
					
						"<a class='Operate' onclick=showInMap('"+list[i].sfzbh+"','"+(i+1)+"') >地图</a></td>"));
			
		}
		$(".listTable").append(newTr);
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='6' align='center'>暂无相关数据</td></tr>");
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
		showFeeStationInfo(actionName, pageNo);
	}
}
function deleteFeeStation(sfzbh){
	if(confirm("你确定要删除该收费站吗？")){
		$.ajax({
			url:'fwssmanager/deletesfz',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'fwssbh':sfzbh
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("删除成功");
					//showServicesInfo('fwssmanager/fwqlist',select_page);
					window.location.reload();
				}else{
					alert(text);
				}
			}
		});
	}
}
function addfeeStation(){
	var sfzlx=document.getElementById("sfzlx");
	sfzlx.options[0].defaultSelected=true;
	sfzlx.options[0].selected=true;
	$("#sfzmc,#sfzpjzm,#ssgs,#xlmc,#ckzx,#jketccdsl,#jkrgcdsl,#zxsxjkzh,#zxxxjkzh,#cketccdsl,#ckrgcdsl,#zxsxckzh,#zxxxckzh").val("");
	showfullbg();
	$("#sfz_title").text("新增收费站")
	$("#addsfzDiv").show();
}
function closeaddsfzDiv(){
	$("#fullbg,#addsfzDiv").hide();
}
var editsfzbh="";
function editFeeStation(sfzbh){
	editsfzbh=sfzbh;
	$.ajax({
		url:'fwssmanager/sfzxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'fwssbh':sfzbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				$("#sfzmc").val(judgeIsNull(list.sfzmc));
				$("#xlmc").val(judgeIsNull(list.xlmc));
				$("#ssgs").val(judgeIsNull(list.ssgs));
				$("#sfzpjzm").val(judgeIsNull(list.sfzpjzm));
				var sfzlx=document.getElementById("sfzlx");
				if(list.sfzlxbh==2){
					sfzlx.options[1].defaultSelected=true;
					sfzlx.options[1].selected=true;
				}else{
					sfzlx.options[0].defaultSelected=true;
					sfzlx.options[0].selected=true;
				}
				$("#ckzx").val(judgeIsNull(list.ckzx));
				$("#jketccdsl").val(judgeIsNull(list.jketccdsl));
				$("#jkrgcdsl").val(judgeIsNull(list.jkrgcdsl));
				$("#zxsxjkzh").val(judgeIsNull(list.zxsxjkzh));
				$("#zxxxjkzh").val(judgeIsNull(list.zxxxjkzh));
				$("#cketccdsl").val(judgeIsNull(list.cketccdsl));
				$("#ckrgcdsl").val(judgeIsNull(list.ckrgcdsl));
				$("#zxsxckzh").val(judgeIsNull(list.zxsxckzh));
				$("#zxxxckzh").val(judgeIsNull(list.zxxxckzh));
				showfullbg();
				$("#sfz_title").text("编辑收费站")
				$("#addsfzDiv").show();
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function addsfz(){
	if(!checkInfo()){
		return;
	}
	if($("#sfz_title").text().indexOf("新增")>=0){
		$.ajax({
			url:'fwssmanager/savesfz',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'sfz.sfzmc':$("#sfzmc").val(),
				'sfz.sfzpjzm':$("#sfzpjzm").val(),
				'sfz.ssgs':$("#ssgs").val(),
				'sfz.xlmc':$("#xlmc").val(),
				'sfz.sfzlxbh':$("#sfzlx option:selected").val(),
				'sfz.ckzx':$("#ckzx").val(),
				'sfz.jketccdsl':$.trim($("#jketccdsl").val()),
				'sfz.cketccdsl':$.trim($("#cketccdsl").val()),
				'sfz.zxsxjkzh':$("#zxsxjkzh").val(),
				'sfz.zxsxckzh':$("#zxsxckzh").val(),
				'sfz.jkrgcdsl':$.trim($("#jkrgcdsl").val()),
				'sfz.ckrgcdsl':$.trim($("#ckrgcdsl").val()),
				'sfz.zxxxjkzh':$("#zxxxjkzh").val(),
				'sfz.zxxxckzh':$("#zxxxckzh").val()
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
	}else{//编辑
		$.ajax({
			url:'fwssmanager/updatesfz',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'sfz.sfzbh':editsfzbh,
				'sfz.sfzmc':$("#sfzmc").val(),
				'sfz.sfzpjzm':$("#sfzpjzm").val(),
				'sfz.ssgs':$("#ssgs").val(),
				'sfz.xlmc':$("#xlmc").val(),
				'sfz.sfzlxbh':$("#sfzlx option:selected").val(),
				'sfz.ckzx':$("#ckzx").val(),
				'sfz.jketccdsl':$.trim($("#jketccdsl").val()),
				'sfz.cketccdsl':$.trim($("#cketccdsl").val()),
				'sfz.zxsxjkzh':$("#zxsxjkzh").val(),
				'sfz.zxsxckzh':$("#zxsxckzh").val(),
				'sfz.jkrgcdsl':$.trim($("#jkrgcdsl").val()),
				'sfz.ckrgcdsl':$.trim($("#ckrgcdsl").val()),
				'sfz.zxxxjkzh':$("#zxxxjkzh").val(),
				'sfz.zxxxckzh':$("#zxxxckzh").val()
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("编辑成功");
					//window.location.reload();
					closeaddsfzDiv();
					showFeeStationInfo('fwssmanager/sfzlist',select_page);
				}else{
					alert(text);
				}
			}
		});
	}
}
function checkInfo(){
	if($("#sfzmc").val()==""){
		alert("请输入收费站名称");
		return false;
	}
	if($("#sfzpjzm").val().length>25){
		alert("收费站票据站名不能大于25位");
		return false;
	}
	if($("#sfzmc").val().length>25){
		alert("收费站名称不能大于25位");
		return false;
	}
	if($("#ssgs").val().length>100){
		alert("所属公司名称不能大于100位");
		return false;
	}
	if($("#xlmc").val().length>25){
		alert("线路名称不能大于25位");
		return false;
	}
	if($("#zxsxjkzh").val().length>25){
		alert("主线上行进口桩号不能大于25位");
		return false;
	}
	if($("#zxxxjkzh").val().length>25){
		alert("主线下行进口桩号不能大于25位");
		return false;
	}
	if($("#zxsxckzh").val().length>25){
		alert("主线上行出口桩号不能大于25位");
		return false;
	}
	if($("#zxxxckzh").val().length>25){
		alert("主线下行出口桩号不能大于25位");
		return false;
	}
	var regx=/\D/;
	if(regx.test($.trim($("#jketccdsl").val()))){
		alert("进口ETC车道数量只能是数字！");
		return false;
	}
	if(regx.test($.trim($("#cketccdsl").val()))){
		alert("出口ETC车道数量只能是数字！");
		return false;
	}
	if(regx.test($.trim($("#jkrgcdsl").val()))){
		alert("进口人工车道数量只能是数字！");
		return false;
	}
	if(regx.test($.trim($("#ckrgcdsl").val()))){
		alert("出口人工车道数量只能是数字！");
		return false;
	}
	if($("#jketccdsl").val().length>11){
		alert("进口ETC车道数量不能大于11位");
		return false;
	}
	if($("#jkrgcdsl").val().length>11){
		alert("进口人工车道数量不能大于11位");
		return false;
	}
	if($("#cketccdsl").val().length>11){
		alert("出口ETC车道数量不能大于11位");
		return false;
	}
	if($("#ckrgcdsl").val().length>11){
		alert("出口人工车道数量不能大于11位");
		return false;
	}
	return true;
}
/*function findServiceInfo(thisop,num){
	fwlx=num;
	var fwsslx="";
	if(navigator.appName.indexOf("Explorer") > -1){
		fwsslx=thisop.innerText;
	}else{
		fwsslx=thisop.textContent;
	}
	//fwsslx=thisop.innerText||thisop.textContent;
	$("#fwsslx").text(fwsslx);
	showServicesInfo('lxjbxxlist/lxgklist',1);
}*/
/**
 * 查询路段信息
 */
function searchFeeStationInfo(){
	search_xzqhbm="";
	showFeeStationInfo('fwssmanager/sfzlist',1);
}
function goToService(){
	window.location.href=$("#basePath").val()+"page/services/Services.jsp";
}
function goTofeeStation(){
	window.location.href=$("#basePath").val()+"page/services/FeeStation.jsp";
}
/**
 * 查看收费站详情
 * @param bzbm
 */
function seeFeeStationDetail(sfzbh){
	$.ajax({
		url:'fwssmanager/sfzxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'fwssbh':sfzbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				$("#sfzmc_detail").text(judgeIsNull(list.sfzmc));
				$("#xlmc_detail").text(judgeIsNull(list.lxjc));
				$("#ssgs_detail").text(judgeIsNull(list.ssgs));
				$("#sfzpjzm_detail").text(judgeIsNull(list.sfzpjzm));
				if(list.sfzlxbh==2){
					$("#sfzlx_detail").text("匝道收费站");
				}else{
					$("#sfzlx_detail").text("主线收费站");
				}
				$("#ckzx_detail").text(judgeIsNull(list.ckzx));
				$("#jketccdsl_detail").text(judgeIsNull(list.jketccdsl));
				$("#jkrgcdsl_detail").text(judgeIsNull(list.jkrgcdsl));
				$("#zxsxjkzh_detail").text(judgeIsNull(list.zxsxjkzh));
				$("#zxxxjkzh_detail").text(judgeIsNull(list.zxxxjkzh));
				$("#cketccdsl_detail").text(judgeIsNull(list.cketccdsl));
				$("#ckrgcdsl_detail").text(judgeIsNull(list.ckrgcdsl));
				$("#zxsxckzh_detail").text(judgeIsNull(list.zxsxckzh));
				$("#zxxxckzh_detail").text(judgeIsNull(list.zxxxckzh));
				showfullbg();
				$("#DetailDiv").show();
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
	
}
/**
 * 进入收费站地图
 * @param bzbm
 */
function showInMap(sfzbh,num){
	var amount=Number((select_page-1)*10)+Number(num);
	window.open($("#basePath").val()+"page/highway/HighWayServiceMap.jsp?sfzbh="+sfzbh+"&amount="+amount,"_blank");
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}