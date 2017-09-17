var searchInput="";
var search_gljgdm="";//行政区划编码
var bmnum=0;//编码编号
var searchList="";
var li_select="";
$(document).ready(function(){
	setHeight();
	$("#top_text").text("行政执法");//top上的显示
	showgljgInfo();
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
//显示行政区划
function showgljgInfo(){
	$.ajax({
		url:'gljglist/gljgjbxxlist',
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
	search_gljgdm=list[0].gljgdm;
	for(var i=0;i<list.length;i++){
		if(list[i].sfxzcfjg==1){
			if(i==0){
				var newLi=$("<li class='left_select_li' id='left_no_select_li"+(i+1)+"' onclick=showInfoList('"+list[i].gljgdm+"','"+list[i].gljgmc+"',this)>"+list[i].gljgmc+"</li>");
				$("#left_select_child1").append(newLi);
			}else{
				var newLi=$("<li class='left_no_select_li' id='left_no_select_li"+(i+1)+"' onclick=showInfoList('"+list[i].gljgdm+"','"+list[i].gljgmc+"',this)>"+list[i].gljgmc+"</li>");
				$("#left_select_child1").append(newLi);
			}
			
		}
	}
	showListInfo('xzcf/xzcflist',1);
}
function showListInfo(actionName,selectedPage){
	searchInput=$("#search_input").val();
	var selectvalue="";
	selectvalue=searchInput;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'rows':10,
			'token':token,
			'xzcfjgdm':search_gljgdm,
			'selectvalue':selectvalue,
			'selectkey':li_select
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
					printPage(data.result.obj.pages, selectedPage, 'showListInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
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
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].xzcfjdwh)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].ajmc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].cfrxm)+"&nbsp;</td>"));	
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].xzcfrq)+"&nbsp;</td>"));	
			//操作
			if(list[i].xzcfly==0){
		    
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeInfoDetail('"+list[i].xzcfbh+"')>查看</a>" +
						"<a class='Operate' onclick=goToeditpage('"+list[i].xzcfbh+"') >编辑</a>" +
								"<a class='Operate' onclick=deleteInfo('"+list[i].xzcfbh+"') >删除</a></td>"));
				
			}else{
				
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=seeInfoDetail('"+list[i].xzcfbh+"')>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看</a>" +
						"</td>"));
				
			}
		}else{
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td>"+judgeIsNull(list[i].xzcfjdwh)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].ajmc)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].cfrxm)+"&nbsp;</td>"));	
			newTr.append($("<td>"+judgeIsNull(list[i].xzcfrq)+"&nbsp;</td>"));	
			//操作
			if(list[i].xzcfly==0){
			
			newTr.append($("<td><a class='Operate' onclick=seeInfoDetail('"+list[i].xzcfbh+"')>查看</a>" +
						"<a class='Operate' onclick=goToeditpage('"+list[i].xzcfbh+"') >编辑</a>" +
								"<a class='Operate' onclick=deleteInfo('"+list[i].xzcfbh+"') >删除</a></td>"));
				
			}else{
					
				newTr.append($("<td><a class='Operate' onclick=seeInfoDetail('"+list[i].xzcfbh+"')>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看</a></td>"));
				
			}
			
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
		showListInfo(actionName, pageNo);
	}
}
function showSelectUl(){
	if($(".select_down_ul").css("display")=="block"){
		$(".select_down_ul").hide();
	}else{
		$(".select_down_ul").show();
	}
	$(document).click(function(event){
		if($(event.target).attr("class") != "select_label_div"&&$(event.target).attr("id")!="select_label"
			&&$(event.target).attr("id")!="select_image" ){
			$(".select_down_ul").hide();
		}
	});
	$(".select_down_li").click(function(){
		$("#select_label").text($(this).text());
		li_select=$(this).attr("id");
	});
}
function searchInfoList(){
	if($("#select_label").text()=="筛选"&&$("#search_input").val()!=""){
		alert("请选择搜索类别！");
		return;
	}else{
		showListInfo('xzcf/xzcflist',1);
	}
}
function deleteInfo(xzchbh){
	if(confirm("你确定要删除该文书吗？")){
		$.ajax({
			url:'xzcf/deletexzcf',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'punishment.xzcfbh':xzchbh
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}else if(data.result.resultcode==1){
					alert("删除成功");
					//window.location.reload();
					showListInfo('xzcf/xzcflist',select_page);
				}else{
					alert(data.result.resultdesc);
				}
			}
		});
	}
}
/**
 * 查看详情
 * @param xzcfbh
 */
function seeInfoDetail(xzcfbh){
	$.ajax({
		url:'xzcf/xzcfxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'xzcfbh':xzcfbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				$("#xzcfjdwh_detail").text(judgeIsNull(list.xzcfjdwh));
				$("#ajmc_detail").text(judgeIsNull(list.ajmc));
				$("#cfrxm").text(judgeIsNull(list.cfrxm));
				$("#wfqyzjjgdm").text(judgeIsNull(list.wfqyzjjgdm));
				if(list.xzcftype==2){
					$(".frdbTr").show();
					$("#fddbrxm").text(judgeIsNull(list.fddbrxm));
					$("#fddbrcard").text(judgeIsNull(list.legalmanIdcard));
				}else{
					$(".frdbTr").hide();
				}
				$("#zywfss").text(judgeIsNull(list.zywfss));
				$("#xzcf").text(judgeIsNull(list.xzcf));
				$("#xzcfzl").text(judgeIsNull(list.xzcfzl));
				$("#xzcfjg").text(judgeIsNull(list.xzcfjgmc));
				$("#xzcfrq").text(judgeIsNull(list.xzcfrq));
				$("#bz").text(judgeIsNull(list.bz));
				$("#bcfdxlb").text(judgeIsNull(list.xzcftypename));
				$("#xzcfcardname").text(judgeIsNull(list.xzcfcardtypename));
				$("#xzcfcardnumber").text(judgeIsNull(list.xzcfcardnumber));
				$("#taskname").text(judgeIsNull(list.xzcfname));
				if(list.xzcfly==0){
					$("#xzcfly").text("系统上传");
				}else if(list.xzcfly==1){
					$("#xzcfly").text("治超站");
				}else{
					$("#xzcfly").text("");
				}
				showfullbg();
				$("#DetailDiv").show();
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
/**
 * 根据行政区划显示
 */
function showInfoList(gljgdm,gljgmc,thisop){
	$(".left_select_li").attr("class","left_no_select_li");
	$(".left_select").attr("class","left_no_select");
	thisop.className="left_select_li";
	search_gljgdm=gljgdm;
	showListInfo('xzcf/xzcflist',1);
}
function goToaddpage(){
	window.location.href=$("#basePath").val()+"page/law/editEnforceLaw.jsp";
}
function goToeditpage(xzcfbh){
	window.location.href=$("#basePath").val()+"page/law/editEnforceLaw.jsp?lawbh="+xzcfbh;
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}