var infoList=new Array();
var totalList="";
$(document).ready(function(){
	setHeight();
	$("#top_text").text("交通量");//top上的显示
	//showRealTrafficInfo('jxdczxx/transdatalist',1);
});
function setHeight(){
	var height=document.documentElement.clientHeight-50;
	if(height<680){
		$(".left_I1").css({"height":"680px"});
		$(".common_c1").css({"height":"680px"});
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".common_c1").css({"height":""+height+"px"});
	}
}
window.onresize=function(){
	var height=document.documentElement.clientHeight-50;
	if(height<680){
		$(".left_I1").css({"height":"680px"});
		$(".common_c1").css({"height":"680px"});
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".common_c1").css({"height":""+height+"px"});
	}
}
function showRealTrafficInfo(actionName,selectedPage){
	var year,month,day,quarter,halfyear="";
	if($("#selectway").val()==1){
		year=$("#year").val();
	}else if($("#selectway").val()==2){
		year=String($("#month").val()).split("-")[0];
		month=String($("#month").val()).split("-")[1];
	}else if($("#selectway").val()==3){
		year=String($("#day").val()).split("-")[0];
		month=String($("#day").val()).split("-")[1];
		day=String($("#day").val()).split("-")[2];
	}else if($("#selectway").val()==4){
		year=$("#quarter_year").val();
		quarter=$("#quarter_four").val();
	}else if($("#selectway").val()==5){
		year=$("#halfyear_year").val();
		halfyear=$("#halfyear_half").val();
	}
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'rows':10,
			'token':token,
			'sjYear':year,
			'sjMonth':month,
			'sjDay':day,
			'sjHyear':halfyear,
			'sjSeason':quarter
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==2){
				alert("查询年份不能为空");
				return;
			}else if(data.result.resultcode==1){
				$(".addTr").empty();
				$(".addTr").remove();
				var list1=data.result.obj.data;
				var list2=data.result.objs[0];
				if(list1==""){
					TableIsNull();
				}else{
					appendToTable(list1,list2);// 跳转页码的方法名称	
					infoList=list1;
					totalList=list2;
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showRealTrafficInfo',
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
function appendToTable(list,totallist){
	for(var i=0;i<list.length;i++){
		if(i%2==0){
			var newTr = $("<tr class='addTr'></tr>");
			//路线名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].lxmc)+"&nbsp;</td>"));
			//观测里程
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].gclc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].jdcdlshj)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].jdczrshj)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qcdlshj)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qczrshj)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].syjtl)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].jtyjd)+"&nbsp;</td>"));
			//操作
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeTrafficInfoDetail('"+i+"')>查看</a></td>"));
			$("#infoTable").append(newTr);
		}else{
			var newTr = $("<tr class='addTr'></tr>");
			//路线名称
			newTr.append($("<td>"+judgeIsNull(list[i].lxmc)+"&nbsp;</td>"));
			//观测里程
			newTr.append($("<td>"+judgeIsNull(list[i].gclc)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].jdcdlshj)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].jdczrshj)+"&nbsp;</td>"));
			newTr.append($("<td >"+judgeIsNull(list[i].qcdlshj)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].qczrshj)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].syjtl)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].jtyjd)+"&nbsp;</td>"));
			//操作
			newTr.append($("<td><a class='Operate' onclick=seeTrafficInfoDetail('"+i+"')>查看</a></td>"));
			$("#infoTable").append(newTr);
		}
	}
	var newTr=$("<tr class='addTr'></tr>");
	//路线名称
	newTr.append($("<td>"+judgeIsNull(totallist.lxmc)+"&nbsp;</td>"));
	//观测里程
	newTr.append($("<td>"+judgeIsNull(totallist.gclc)+"&nbsp;</td>"));
	newTr.append($("<td>"+judgeIsNull(totallist.jdcdlshj)+"&nbsp;</td>"));
	newTr.append($("<td>"+judgeIsNull(totallist.jdczrshj)+"&nbsp;</td>"));
	newTr.append($("<td >"+judgeIsNull(totallist.qcdlshj)+"&nbsp;</td>"));
	newTr.append($("<td>"+judgeIsNull(totallist.qczrshj)+"&nbsp;</td>"));
	newTr.append($("<td>"+judgeIsNull(totallist.syjtl)+"&nbsp;</td>"));
	newTr.append($("<td>"+judgeIsNull(totallist.jtyjd)+"&nbsp;</td>"));
	//操作
	newTr.append($("<td><a class='Operate' onclick=seeTrafficInfoDetail(-1)>查看</a></td>"));
	$("#totalTable").append(newTr);
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='9' align='center'>暂无相关数据</td></tr>");
	$("#infoTable").append(newTr);
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
		showRealTrafficInfo(actionName, pageNo);
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
 * 查看实时信息详情
 * @param bzbm
 */
function seeTrafficInfoDetail(num){
	if(num!=-1){
		$("#lxmc").text(judgeIsNull(infoList[num].lxmc));
		$("#gclc").text(judgeIsNull(infoList[num].gclc));
		$("#jdcdlshj").text(judgeIsNull(infoList[num].jdcdlshj));
		$("#jdczrshj").text(judgeIsNull(infoList[num].jdczrshj));
		$("#qcdlshj").text(judgeIsNull(infoList[num].qcdlshj));
		$("#qczrshj").text(judgeIsNull(infoList[num].qczrshj));
		$("#xxhc").text(judgeIsNull(infoList[num].xxhc));
		$("#zxhc").text(judgeIsNull(infoList[num].zxhc));
		$("#dxhc").text(judgeIsNull(infoList[num].dxhc));
		$("#tdxhc").text(judgeIsNull(infoList[num].tdxhc));
		$("#jzxc").text(judgeIsNull(infoList[num].jzxc));
		$("#zxkc").text(judgeIsNull(infoList[num].zxkc));
		$("#dkc").text(judgeIsNull(infoList[num].dkc));
		$("#mtc").text(judgeIsNull(infoList[num].mtc));
		$("#tlj").text(judgeIsNull(infoList[num].tlj));
		$("#rlc").text(judgeIsNull(infoList[num].rlc));
		$("#clc").text(judgeIsNull(infoList[num].clc));
		$("#zxc").text(judgeIsNull(infoList[num].zxc));
		$("#xsl").text(judgeIsNull(infoList[num].xsl));
		$("#syjtl").text(judgeIsNull(infoList[num].syjtl));
		$("#jtyjd").text(judgeIsNull(infoList[num].jtyjd));
	}else{
		$("#lxmc").text(judgeIsNull(totalList.lxmc));
		$("#gclc").text(judgeIsNull(totalList.gclc));
		$("#jdcdlshj").text(judgeIsNull(totalList.jdcdlshj));
		$("#jdczrshj").text(judgeIsNull(totalList.jdczrshj));
		$("#qcdlshj").text(judgeIsNull(totalList.qcdlshj));
		$("#qczrshj").text(judgeIsNull(totalList.qczrshj));
		$("#xxhc").text(judgeIsNull(totalList.xxhc));
		$("#zxhc").text(judgeIsNull(totalList.zxhc));
		$("#dxhc").text(judgeIsNull(totalList.dxhc));
		$("#tdxhc").text(judgeIsNull(totalList.tdxhc));
		$("#jzxc").text(judgeIsNull(totalList.jzxc));
		$("#zxkc").text(judgeIsNull(totalList.zxkc));
		$("#dkc").text(judgeIsNull(totalList.dkc));
		$("#mtc").text(judgeIsNull(totalList.mtc));
		$("#tlj").text(judgeIsNull(totalList.tlj));
		$("#rlc").text(judgeIsNull(totalList.rlc));
		$("#clc").text(judgeIsNull(totalList.clc));
		$("#zxc").text(judgeIsNull(totalList.zxc));
		$("#xsl").text(judgeIsNull(totalList.xsl));
		$("#syjtl").text(judgeIsNull(totalList.syjtl));
		$("#jtyjd").text(judgeIsNull(totalList.jtyjd));
	}
	showfullbg();
	$("#DetailDiv").show();
}
function showInput(){
	if($("#selectway").val()==1){
		$("#month,#day,#quarter,#halfyear").hide();
		$("#year").show();
	}else if($("#selectway").val()==2){
		$("#year,#day,#quarter,#halfyear").hide();
		$("#month").show();
	}else if($("#selectway").val()==3){
		$("#year,#month,#quarter,#halfyear").hide();
		$("#day").show();
	}else if($("#selectway").val()==4){
		$("#year,#month,#day,#halfyear").hide();
		$("#quarter").show();
	}else if($("#selectway").val()==5){
		$("#year,#month,#day,#quarter").hide();
		$("#halfyear").show();
	}
}
/**
 * 进入服务设施地图
 * @param bzbm
 */
function showInMap(fwqbh){
	//window.open($("#basePath").val()+"page/highway/HighWayServiceMap.jsp?fwqbh="+fwqbh,"_blank");
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