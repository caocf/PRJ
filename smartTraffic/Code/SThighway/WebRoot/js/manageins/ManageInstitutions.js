var supergljgdm="";
var supergljgname="";
var GLJGDM="";//管理机构代码
var department_SJ="";//管理机构栏新建部门时的上级管理机构
var departmentdm="";
var selectedGLJGDM="";
var mark=new Array();
var clickdm=new Array();
var departmentbz="";//中间导航栏上面图标的标志
var search_mark=0;//0是在管理机构下搜索，1是在部门下搜索
var select_rybh="";//选中的人员编号
var addselectbm="";//新增和编辑时选的部门
$(document).ready(function(){
	$("#top_text").text("管理机构");
	//SelectOption("ssjs",212);
	//setmanagewidth();
	showleftGljg();//显示管理机构信息
	//showGljgdepartment();//显示管理机构部门信息
	//showGljgDepartmentPerson('gljglist/glrylistbybm',1);//显示部门人员
	//showGljgInfo();//显示管理机构信息
	//findRoleInfo();//添加角色信息
	
});
function findRoleInfo(){
	$.ajax({
		url:'jsqxmanager/jslist',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			var list=data.result.list;
			if(list!=""){
				for(var i=0;i<list.length;i++){
					document.getElementById("ssjs").options.add(new Option(list[i].jsmc,list[i].jsbh));//角色添加进下拉框
					document.getElementById("ssjs1").options.add(new Option(list[i].jsmc,list[i].jsbh));//角色添加进下拉框	
					/*var newLi=$("<li  class='CRselectBoxItem' ></li>");
					newLi.append($("<a  rel='"+list[i].jsbh+"'>"+list[i].jsmc+"</a>"));
					$("#addjs").append(newLi);*/
				}
			}
			
		}
	});
}
//显示管理机构信息
function showleftGljg(){
	$.ajax({
		url:'gljglist/gljglist',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$("#left_select_child1").empty();
			var list=data.result.obj;
			/*if(list==null){
				UlIsNull();
			}else{*/
			var newLb=$("<label class='left_name' id='gljg_bossname' onclick=goTogljg('"+list.nodeId+"',event,this,-1)>"+list.nodeDesc+"</label>");
				//$("#gljg_bossname").text(list.nodeDesc);
				//var newDiv=$("<div class=''>"+编辑+"</div>")
				/*var newDiv=$("<div class='gljg_edit'><div class='gljg_edit_link' id='"+list.nodeDesc+"' onclick=showGljgEditDiv('"+list.nodeId+"',this.id)><img src='image/main/edit.png' style='margin-top:17px;'class='edit_img_position'/></div>" +
						"<div class='gljg_edit_link' onclick=showGljgAddDiv('"+list.nodeId+"')><img src='image/main/add.png' class='edit_img_position' style='margin-top:17px;' /></div>" +
								"<div class='gljg_edit_link' onclick=showGljgDeleteDiv('"+list.nodeId+"')><img src='image/main/delete.png' class='edit_img_position' style='margin-top:17px;' /></div></div>");*/
				$("#left_no_select1").append(newLb);
			/*	$("#left_no_select1").append(newDiv);*/
				supergljgdm=list.nodeId;
				supergljgname=list.nodeDesc;
				$("#departmentry_title").text(list.nodeDesc);
				appendToLeftgljg(list,list.childrenNodes);
			//}
		}
	});
}
function UlIsNull(){
	var newLi=$("<li><label style='margin-left:40px;'>暂无数据<label></li>");
	$("#left_select_child1").append(newLi);
}
function appendToLeftgljg(sjgljg,list){
	document.getElementById("gljg").options.add(new Option(sjgljg.nodeDesc,sjgljg.nodeId));//管理机构添加进下拉框
	document.getElementById("gljg1").options.add(new Option(sjgljg.nodeDesc,sjgljg.nodeId));//管理机构添加进下拉框
	for(var i=0;i<list.length;i++){
		var newLi=$("<li class='left_no_select_li'  onmouseover='showgljgEditDiv("+i+")' id='left_select_li"+i+"' " +
				" onmouseout='hidegljgEditDiv("+i+")'><label onclick=goTogljg('"+list[i].nodeId+"',event,this,'"+i+"') " +
						"style='float:left;margin-left:40px;width:150px;overflow:hidden;height:42px;cursor:pointer;'>"+list[i].nodeDesc+"</label></li>");
		var newDiv=$("<div class='gljg_edit42' id='gljg_gljg"+i+"' style='left:110px;'><div class='gljg_edit_link42' id='"+list[i].nodeDesc+"' onclick=showGljgEditDiv('"+list[i].nodeId+"',this.id)><img src='image/main/edit.png' class='edit_img_position'/></div>" +
				"<div class='gljg_edit_link42' onclick=showAddgljgchildDiv('"+list[i].nodeId+"')><img src='image/main/add.png' class='edit_img_position'/></div>" +
						"<div class='gljg_edit_link42' onclick=showGljgDeleteDiv('"+list[i].nodeId+"')><img src='image/main/delete.png' class='edit_img_position'/></div></div>");
		newLi.append(newDiv)
		$("#left_select_child1").append(newLi);
		//$("#left_select_child1").append(newDiv);
		document.getElementById("gljg").options.add(new Option(list[i].nodeDesc,list[i].nodeId));//管理机构添加进下拉框
		document.getElementById("gljg1").options.add(new Option(list[i].nodeDesc,list[i].nodeId));//管理机构添加进下拉框
	}
	selectedGLJGDM=supergljgdm;
	departmentdm=supergljgdm;
	showGljgdepartment(supergljgdm);//显示管理机构部门信息
	showGljgInfo(supergljgdm);//显示管理机构信息
	showGljgPerson('gljglist/glrylistbybm', 1);//显示机构人员
}
/**
 * 根据管理机构显示人员和部门
 * @param gljgdm
 * @param event
 * @param gljgmc
 */
function goTogljg(gljgdm,event,thisop,num){
	search_mark=0;
	$(".left_select_li").attr("class","left_no_select_li");
	$(".letter_selected").attr("class","letter");
	if(num!=-1){
		$(".left_select").attr("class","left_no_select");
		$("#left_select_li"+num).attr("class","left_select_li");
	}else{
		$("#left_no_select1").attr("class","left_select");
	}
	
	//if(event.srcElement.tagName!="DIV"&&event.srcElement.tagName!="IMG"){
		showGljgdepartment(gljgdm);
		selectedGLJGDM=gljgdm;
		showGljgInfo(gljgdm);
		//departmentdm=gljgdm;
		var departmentry_title=thisop.innerText||thisop.textContent;
		$("#departmentry_title").text(departmentry_title);
		showGljgPerson('gljglist/glrylistbybm',1);//显示机构下所有人员
		//showGljgDepartmentPerson('gljglist/glrylistbybm',1);//显示部门人员
	//}
}
function showGljgWorker(gljgdm,thisop){
	search_mark=0;
	$(".letter_selected").attr("class","letter");
	selectedGLJGDM=gljgdm;
	var departmentry_title=thisop.innerText||thisop.textContent;
	$("#departmentry_title").text(departmentry_title);
	showGljgPerson('gljglist/glrylistbybm',1);//显示机构下所有人员
}
//删除机构

function DepartmentUlIsNull(){
	var newLi=$("<li style='text-align:center;'><label >暂无数据<label></li>");
	$("#department_left_select_child2").append(newLi);
	$(".addTr").remove();
	TableIsNull();
}
function appendToLeftgljgDepartment(list,secondgljgdm){
	for(var i=0;i<list.length;i++){
		var newLi=$("<li class='department_left_no_select_li' id='department_left_select_li"+i+"'  onmouseover='showEditOver("+i+")'  onmouseout='hideEditOut("+i+")'>" +
				"<div class='img_div' onclick='showchildDepartment("+i+")'><img src='image/main/arrow.png' id='img_style"+i+"' class='department_arrow_style'/></div>" +
						"<label style='float:left;margin-left:10px;width:180px;overflow:hidden;height:42px;cursor:pointer;' onclick=goTogljgDepartment('"+list[i].nodeId+"',this,'"+i+"') >"+list[i].nodeDesc+"</label></li>");
		/*var newDiv=$("<div class='gljg_edit42' id='gljg_department"+i+"'><div class='gljg_edit_link42' id='"+list[i].nodeDesc+"' onclick=showEditgljgchildDiv('"+list[i].nodeId+"',this.id,'"+secondgljgdm+"')><img src='image/main/edit.png' class='edit_img_position'/></div>" +
				"<div class='gljg_edit_link42' onclick=showAddDepartmentchildDiv('"+list[i].nodeId+"','"+list[i].nodeDesc+"','"+secondgljgdm+"')><img src='image/main/add.png' class='edit_img_position'/></div>" +
						"<div class='gljg_edit_link42' onclick=showDeletegljgchildDiv('"+list[i].nodeId+"')><img src='image/main/delete.png' class='edit_img_position'/></div></div>");
		*//*newLi.append(newDiv);*/
		mark[i]=0;
		$("#department_left_select_child2").append(newLi);
		var dpt_name="  "+list[i].nodeDesc;
		//document.getElementById("ssbm").options.add(new Option(dpt_name,list[i].nodeId));//部门添加进下拉框
		/*var selectLi=$("<li class='dptli' onclick=selectThisDepartment('"+list[i].nodeId+"','"+list[i].nodeDesc+"') >"+list[i].nodeDesc+"</li>");
		$("#addselect,#editselect").append(selectLi);*/
		//$("#editselect").append(selectLi);
		//document.getElementById("ssbm1").options.add(new Option(dpt_name,list[i].nodeId));//部门添加进下拉框
		if(list[i].childrenNodes!=""){
			for(var j=0;j<list[i].childrenNodes.length;j++){
				var newchildUl=$("<ul style='height:42px;width:249px;background-color:#f6f6f6;display: none;' class='department_child_ul"+i+"'></ul>");
				var newchildLi=$("<li id='department_li_select"+((j+1)*(i+1000))+"' class='department_child_no_select_li'  onmouseover='showEditOver("+((j+1)*(i+1000))+")'  onmouseout='hideEditOut("+((j+1)*(i+1000))+")'>" +
						"<label style='float:left;margin-left:60px;width:180px;overflow:hidden;height:42px;cursor:pointer;' onclick=goTogljgDepartment('"+list[i].childrenNodes[j].nodeId+"',this,-1) >"+list[i].childrenNodes[j].nodeDesc+"</label></li>");
				/*var newchildDiv=$("<div class='gljg_edit42' id='gljg_department"+((j+1)*(i+1000))+"' style='left:390px;'><div class='gljg_edit_link42' id='"+list[i].childrenNodes[j].nodeDesc+"' onclick=showEditgljgchildDiv('"+list[i].childrenNodes[j].nodeId+"',this.id,'"+secondgljgdm+"')><img src='image/main/edit.png' class='edit_img_position'/></div>" +
						//"<div class='gljg_edit_link42' onclick=showAddDepartmentchildDiv111('"+list[i].childrenNodes[j].nodeId+"')>"+"新增"+"</div>" +
								"<div class='gljg_edit_link42' onclick=showDeletegljgchildDiv('"+list[i].childrenNodes[j].nodeId+"')><img src='image/main/delete.png' class='edit_img_position'/></div></div>");
				newchildLi.append(newchildDiv);*/
				newchildUl.append(newchildLi);
				$("#department_left_select_child2").append(newchildUl);
		//document.getElementById("ssbm").options.add(new Option(list[i].childrenNodes[j].nodeDesc,list[i].childrenNodes[j].nodeId));//部门添加进下拉框
		//document.getElementById("ssbm1").options.add(new Option(list[i].childrenNodes[j].nodeDesc,list[i].childrenNodes[j].nodeId));//部门添加进下拉框
				/*var selectsecondli=$("<li class='dpt_secondli' onclick=selectThisDepartment('"+list[i].childrenNodes[j].nodeId+"','"+list[i].childrenNodes[j].nodeDesc+"') >"+list[i].childrenNodes[j].nodeDesc+"</li>")
				$("#addselect,#editselect").append(selectsecondli);*/
				//$("#editselect").append(selectsecondli);
			}
		}else{
			$("#img_style"+i).hide();
		}
		
	}
	$(".department_child_no_select_li,.department_child_select_li").bind("click",function(){
		$(".department_left_select_li").attr("class","department_left_no_select_li");
		$(".department_child_select_li").attr("class","department_child_no_select_li");
		$(this).attr("class","department_child_select_li");
	});
	$(".department_left_no_select_li,.department_left_select_li").bind("click",function(){
		$(".department_child_select_li").attr("class","department_child_no_select_li");
		$(".department_left_select_li").attr("class","department_left_no_select_li");
		$(this).attr("class","department_left_select_li");
	});
}
function showDepartmentDiv(){
	if($(".type_down_ul").css("display")=="block"){
		$(".type_down_ul").hide();
	}else{
		$(".type_down_ul").show();
	}
	$(document).click(function(event){
		if($(event.target).attr("class") != "type_select_mark"&&$(event.target).attr("id") != "ssbm"
			&&$(event.target).attr("id") != "selectbm_image"){
			$(".type_down_ul").hide();
		}
	});
	
}
function selectThisDepartment(bmdm,thisop){
	addselectbm=bmdm;
	$(".type_down_ul").hide();
	if($("#addnewMemberDiv").css("display")=="block"){
		$("#ssbm").text(thisop.innerText);
	}else{
		$("#ssbm1").text(thisop.innerText);
	}
	
}
function goTogljgDepartment(bmdm,thisop,num){
	search_mark=1;
	$(".letter_selected").attr("class","letter");
	/*var e=event||window.event||arguments.callee.caller.arguments[0];
	 * if(e.srcElement.tagName!="DIV"&&e.srcElement.tagName!="IMG"){*/
		departmentdm=bmdm;
		showGljgDepartmentPerson('gljglist/glrylistbybm',1);//显示人员
		var departmentry_title=thisop.innerText||thisop.textContent;
		$("#departmentry_title").text(departmentry_title);
		//document.getElementById("departmentry_title").innerText=bmmc;
		
	//}
}
/**
 * 显示子部门
 */
function showchildDepartment(num){
	if(mark[num]==0){
		$(".department_child_ul"+num).show();
		$("#img_style"+num).attr("src","image/main/arrow_down.png");
		mark[num]=1;
	}else if(mark[num]==1){
		$(".department_child_ul"+num).hide();
		$("#img_style"+num).attr("src","image/main/arrow.png");
		mark[num]=0;
	}
}
/**
 * 显示部门
 */
function showLowerDepartment(){
	if(departmentbz==0){
		$(".department_left_select_child").hide();
		$("#dpt_img").attr("src","image/main/arrow.png");
		departmentbz=1;
	}else if(departmentbz==1){
		$(".department_left_select_child").show();
		$("#dpt_img").attr("src","image/main/arrow_down.png");
		departmentbz=0;
	}
}

/**
 * 显示部门人员
 */
function showGljgDepartmentPerson(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'glbm.bmdm':/*'402881fb4c2ae681014c2ae6a47d0001'*/departmentdm,
			'rows':10,
			'page':selectedPage
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$(".addTr").empty();
			$(".addTr").remove();
			var list=data.result.obj.data;
			if(list==""){
				TableIsNull();
			}else{
				appendToTable(list);
				gotoPageMethodName = "gotoPageNo";
				printPage(data.result.obj.pages, selectedPage, 'showGljgDepartmentPerson',
						actionName, gotoPageMethodName, data.result.obj.total);
			}
		}
	});
}
/**
 * 显示刚加载时的所有人员
 */
function showFirstGljgPerson(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':selectedGLJGDM,
			'rows':10,
			'page':selectedPage
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$(".addTr").empty();
			$(".addTr").remove();
			var list=data.result.obj.data;
			if(list==""){
				TableIsNull();
			}else{
				appendToTable(list);
				gotoPageMethodName = "gotoPageNo1";
				printPage(data.result.obj.pages, selectedPage, 'showGljgPerson',
						actionName, gotoPageMethodName, data.result.obj.total);
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}
/**
 * 显示机构下所有人员
 */
function showGljgPerson(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':selectedGLJGDM,
			'rows':10,
			'page':selectedPage
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$(".addTr").empty();
			$(".addTr").remove();
			var list=data.result.obj.data;
			if(list==""){
				TableIsNull();
			}else{
				appendToTable(list);
				gotoPageMethodName = "gotoPageNo1";
				printPage(data.result.obj.pages, selectedPage, 'showGljgPerson',
						actionName, gotoPageMethodName, data.result.obj.total);
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		var newTr=$("<tr class='addTr'></tr>");
		/*if(i%2==0){
			//姓名
			newTr.append($("<td style='background:#f9f9f9' class='selected_info' onclick=selectedIt('"+list[i].rybh+"')>"+judgeIsNull(list[i].ryxm)+"&nbsp;</td>"));
			//部门
			//newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].bmmc)+"&nbsp;</td>"));
			//角色
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].jsmc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].zw)+"&nbsp;</td>"));
			//手机长号
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].sjch)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].sjdh)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].bgsdh)+"&nbsp;</td>"));
			//操作
			newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick='seeDetailInfo("+list[i].rybh+")'>查看</a></td>"));
					"<a class='Operate' onclick=editRyInfo('"+list[i].rybh+"','"+list[i].ryxm+"','"+list[i].zw+"','"+list[i].lxdh+"','"+list[i].bmdm+"','"+list[i].bmmc+"')>编辑</a>" +
					"<a class='Operate' onclick='deleteMember("+list[i].rybh+")'>离职</a><a class='Operate' onclick='resetPassword("+list[i].rybh+")'>重置密码</a></td>"));
		}else{*/
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].ryxm)+"<input type='hidden' value='"+list[i].rybh+"' />&nbsp;</td>"));
			//newTr.append($("<td>"+judgeIsNull(list[i].bmmc)+"&nbsp;</td>"));
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].jsmc)+"&nbsp;</td>"));
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].zw)+"&nbsp;</td>"));
			//手机长号
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].sjch)+"&nbsp;</td>"));
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].sjdh)+"&nbsp;</td>"));
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].bgsdh)+"&nbsp;</td>"));
			//操作
			newTr.append($("<td class='selected_info'><a class='Operate' onclick='seeDetailInfo("+list[i].rybh+")'>查看</a></td>"));
					/*"<a class='Operate' onclick=editRyInfo('"+list[i].rybh+"','"+list[i].ryxm+"','"+list[i].zw+"','"+list[i].lxdh+"','"+list[i].bmdm+"','"+list[i].bmmc+"')>编辑</a>" +
					"<a class='Operate' onclick='deleteMember("+list[i].rybh+")'>离职</a><a class='Operate' onclick='resetPassword("+list[i].rybh+")'>重置密码</a></td>"));*/
		//}
		$(".listTable").append(newTr);
	}
	$(".selected_info").on("click",function(){
		$(".selected_info").each(function(){
			if($(this).css("background-color")=="rgb(235, 243, 247)"||$(this).css("background-color")=="#ebf3f7"){
				//$(this).css("background-color",$(this).siblings().css('background-color'));
				$(this).css({"background-color":"#ffffff"});
			}
		});
		select_rybh=$(this).parent().children("td").first().children("input").val(); 
		$(this).css({"background-color":"#ebf3f7"});
		$(this).siblings().css({"background-color":"#ebf3f7"});
	});
}
/**
 * 判断是否为null
 */
function judgeIsNull(value){
	returnValue="";
	if(value==null||value=="null"||value==""){
		return returnValue;
	}else{	
		return value;
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='7' align='center'>暂无相关数据</td></tr>");
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
		showGljgDepartmentPerson(actionName, pageNo);
	}
}
function gotoPageNo1(actionName, totalPage) {
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
		showGljgPerson(actionName, pageNo);
	}
}
function gotoPageNo2(actionName, totalPage) {
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
		searchMemberList(xmpyszm, pageNo, actionName);
	}
}
function gotoPageNo3(actionName, totalPage) {
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
		searchMemberInfo(actionName, pageNo);
	}
}
/**
 * 查看人员信息
 */
function seeDetailInfo(rybh){
	$(".member_info").show();
	$.ajax({
		url:'rymanager/findryInfo',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'glry.rybh':rybh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			var memberinfo=data.result.obj;
			$("#member_name").text(memberinfo.ryxm);
			/*$("#member_xmqp").text(judgeIsNull(memberinfo.xmqp));
			$("#member_xmszm").text(judgeIsNull(memberinfo.xmpyszm));*/
			$("#member_dpt").text(judgeIsNull(memberinfo.bmmc));
			$("#member_role").text(judgeIsNull(memberinfo.jsmc));
			$("#member_position").text(judgeIsNull(memberinfo.zw));
			var lxdh="";
			if(memberinfo.sjch!=""){
				lxdh=judgeIsNull(memberinfo.sjch);
			}
			if(memberinfo.sjdh!=null){
				if(lxdh==""){
					lxdh=judgeIsNull(memberinfo.sjdh);
				}else{
					lxdh+=" ; "+judgeIsNull(memberinfo.sjdh);
				}
			}
			$("#member_sjch").text(judgeIsNull(memberinfo.sjch));
			$("#member_sjdh").text(judgeIsNull(memberinfo.sjdh));
			$("#member_bgsdh").text(judgeIsNull(memberinfo.bgsdh));
		}
	});
}
function closememberInfoDiv(){
	$(".member_info").hide();
}
/**
 * 删除成员
 */
function deleteMember(){
	if(select_rybh==""){
		alert("请选择人员");
	}else{
		if(confirm("是否删除该人员？")){
			$.ajax({
				url:'rymanager/glrydelete',
				type:'post',
				dataType:'json',
				data:{
					'glry.rybh':select_rybh,
					'token':token
				},
				success:function(data){
					if(data.result.resultcode==-1){
						BackToLoginPage();
					}
					var result=data.result.resultcode;
					var text=data.result.resultdesc;
					if(result==1){
						alert("删除成功");
						window.location.reload();
					}else{
						alert(text);
					}
				}
			});
		}
	}
	
}

var gljg_info_name="";
var gljg_info_address="";
var gljg_info_phone="";
var gljg_info_department="";
var gljg_info_dm=""
/**
 * 显示管理机构信息
 * @param gljgdm
 */
function showGljgInfo(gljgdm){
	gljg_info_dm=gljgdm;
	$.ajax({
		url:'gljglist/findjginfo',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':gljgdm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			var info=data.result.obj;
			if(info==""){
				
			}else{
				gljg_info_name=info.gljgmc;
				gljg_info_address=info.lxdz;
				gljg_info_phone=info.lxdh;
				$("#gljg_name").text(info.gljgmc);
				$("#gljg_address").text("地址："+judgeIsNull(info.lxdz));
				$("#gljg_phone").text("电话："+judgeIsNull(info.lxdh));
				if(data.result.list!=""){
					/*var gljg_department="";
					var department=data.result.list.split(",");
					for(var i=0;i<department.length;i++){
						if(gljg_department==""){
							gljg_department=department[i];
						}else{
							gljg_department+="、"+department[i];
						}
					}*/
					$("#gljg_department").text(data.result.list);
					gljg_info_department=data.result.list;
				}else{
					$("#gljg_department").text("暂无下属部门");
				}
				/*if(info.HJcGlbms!=""){
					var gljg_department="";
					for(var i=0;i<info.HJcGlbms.length;i++){
						if(gljg_department==""){
							gljg_department=info.HJcGlbms[i].bmmc;
						}else{
							gljg_department+="、"+info.HJcGlbms[i].bmmc;
						}
					}
					$("#gljg_department").text(gljg_department);
				}else{
					$("#gljg_department").text("暂无下属部门");
				}*/
			}
		}
	});
}
//重置密码
var resetId="";
function resetPassword(){
	if(select_rybh==""){
		alert("请选择人员");
	}else{
		var bh = $(".common_c0").height(); 
		var bw = $(".top_u1").width();
		$("#fullbg").css({
			height:bh, 
			width:bw, 
			display:"block" 
		});
		resetId=select_rybh;
		$("#ResetPwdDiv").show();
	}
}
function closeResetPwdDiv(){
	$("#ResetPwdDiv,#fullbg").hide();
}
function resetPwd(){
	if($("#reset_newmm").val()==""){
		alert("请输入密码");
		return false;
	}
	var regu = /^\w+$/;
	if (!regu.test($("#reset_newmm").val())) {
		alert("密码只能包含_ ,英文字母,数字!");
		return false;
	}
	if($("#reset_newmm").val().length>20||$("#reset_newmm").val().length<5){
		alert("请输入5-20位的密码");
		return false;
	}
	if($("#reset_checkmm").val()!=$("#reset_newmm").val()){
		alert("两次密码不同");
		return false;
	}
	$.ajax({
		url:'rymanager/pwchongzhi',
		type:'post',
		dataType:'json',
		data:{
			'newpassword':$("#reset_newmm").val(),
			'token':token,
			'glry.rybh':resetId
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			var result=data.result.resultcode;
			var text=data.result.resultdesc;
			if(result==1){
				alert("重置成功");
				window.location.reload();
			}else{
				alert(text);
			}
		}
	});
}
//新成员入职
function showinductionDiv(){
	$("#username,#name,#mm,#checkmm,#zw,#lxdh").val("");
	var bh = $(".common_c0").height(); 
	var bw = $(".top_u1").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
	selectaddGljg();
	$("#addnewMemberDiv").show();
}
function closeaddnewMemberDiv(){
	$("#addnewMemberDiv,#fullbg").hide();
}
function selectaddGljg(){
	$("#ssbm").text("选择部门");
	addselectbm="";
	$.ajax({
		url:'gljglist/glbmlistbyfjg',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':$("#gljg option:selected").val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$("#addselect").empty();
			var list=data.result.list;
			for(var i=0;i<list.length;i++){
				var selectLi=$("<li class='dptli' onclick=selectThisDepartment('"+list[i].nodeId+"',this) >"+list[i].nodeDesc+"</li>");
				$("#addselect").append(selectLi);
				if(list[i].childrenNodes!=""){
					for(var j=0;j<list[i].childrenNodes.length;j++){
						var selectsecondli=$("<li class='dpt_secondli' onclick=selectThisDepartment('"+list[i].childrenNodes[j].nodeId+"',this) >"+list[i].childrenNodes[j].nodeDesc+"</li>")
						$("#addselect").append(selectsecondli);
					}
				}
			}
			
		}
	});
}
function addNewMember(){
	if(!checkinfo()){
		return;
	}
	if($("#ssbm").text()=="选择部门"){
		addselectbm="";
	}
	$.ajax({
		url:'rymanager/glrysave',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'bmdm':addselectbm,
			'jsbh':$("#ssjs option:selected").val(),
			'glry.ryxm':$("#name").val(),
			'glry.xmqp':$("#xmqp").val(),
			'glry.xmpyszm':$("#xmszm").val(),
			'glry.rymm':$("#mm").val(),
			'glry.sjch':$("#lxdh").val(),
			'glry.zw':$("#zw").val(),
			'glry.sjdh':$("#sjdh").val(),
			'glry.bgsdh':$("#bgsdh").val()
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
}
function checkinfo(){
	if($("#name").val()==""){
		alert("请输入姓名");
		return false;
	}
	if($("#name").val().length>25){
		alert("姓名长度不能大于25位");
		return false;
	}
	if($("#xmqp").val()==""){
		alert("请输入姓名全拼");
		return false;
	}
	if($("#xmqp").val().length>100){
		alert("姓名全拼长度不能大于100位");
		return false;
	}
	if($("#xmszm").val()==""){
		alert("请输入姓名首字母");
		return false;
	}
	if($("#xmszm").val().length>25){
		alert("姓名首字母长度不能大于25位");
		return false;
	}
	if($("#mm").val()==""){
		alert("请输入密码");
		return false;
	}
	var regu = /^\w+$/;
	if (!regu.test($("#mm").val())) {
		alert("密码只能包含_ ,英文字母,数字!");
		return false;
	}
	if($("#mm").val().length>20||$("#mm").val().length<5){
		alert("请输入5-20位的密码");
		return false;
	}
	if($("#checkmm").val()!=$("#mm").val()){
		alert("两次密码不同");
		return false;
	}
	if($("#zw").val().length>25){
		alert("职务长度不能大于25位");
		return false;
	}
	if($("#lxdh").val()==""){
		alert("请输入手机长号");
		return false;
	}
	if($("#lxdh").val().length>15){
		alert("手机长号长度不对");
		return false;
	}
	var regx=/\D/;
	if(regx.test($("#lxdh").val())){
		alert("手机长号只能是数字");
		return false;
	}
	if(regx.test($("#sjdh").val())){
		alert("手机短号只能是数字");
		return false;
	}
	if(regx.test($("#bgsdh").val())){
		alert("办公室电话只能是数字");
		return false;
	}
	if($("#ssbm").text()=="选择部门"){
		alert("请选择部门");
		return false;
	}
	return true;
}
function selecteditGljg(dm){
	var editgljgdm="";
	if(dm==0){
		$("#ssbm1").text("选择部门");
		addselectbm="";
		editgljgdm=$("#gljg1 option:selected").val()
	}else{
		editgljgdm=dm
	}
	
	$.ajax({
		url:'gljglist/glbmlistbyfjg',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':editgljgdm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$("#editselect").empty();
			var list=data.result.list;
			for(var i=0;i<list.length;i++){
				var selectLi=$("<li class='dptli' onclick=selectThisDepartment('"+list[i].nodeId+"',this) >"+list[i].nodeDesc+"</li>");
				$("#editselect").append(selectLi);
				if(list[i].childrenNodes!=""){
					for(var j=0;j<list[i].childrenNodes.length;j++){
						var selectsecondli=$("<li class='dpt_secondli' onclick=selectThisDepartment('"+list[i].childrenNodes[j].nodeId+"',this) >"+list[i].childrenNodes[j].nodeDesc+"</li>")
						$("#editselect").append(selectsecondli);
					}
				}
			}
			
		}
	});
}
//编辑成员
var editryId=""
function editRyInfo(){
	if(select_rybh==""){
		alert("请选择人员");
	}else{
		editryId=select_rybh;
		$.ajax({
			url:'rymanager/findryInfo',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'glry.rybh':editryId
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var memberinfo=data.result.obj;
				/*$("#member_dpt").text(memberinfo.bmmc);
				$("#member_role").text(memberinfo.jsmc);*/
				$("#name1").val(memberinfo.ryxm);
				$("#xmqp1").val(memberinfo.xmqp);
				$("#xmszm1").val(memberinfo.xmpyszm);
				$("#zw1").val(memberinfo.zw);
				$("#lxdh1").val(memberinfo.sjch);
				$("#sjdh1").val(memberinfo.sjdh);
				//bmmc
				/*var obj2=document.getElementById("ssbm1");
				if(obj2){
					var options=obj2.options;
					if(options){
						for(var i=0;i<options.length;i++){
							if(options[i].text==memberinfo.bmmc){
								options[i].defaultSelected=true;
								options[i].selected=true;
							}
						}
					}
				}*/
				//gljg
				var obj_jg=document.getElementById("gljg1");
				if(obj_jg){
					var options=obj_jg.options;
					if(options){
						for(var i=0;i<options.length;i++){
							if(options[i].value==memberinfo.fjgdm){
								options[i].defaultSelected=true;
								options[i].selected=true;
							}
						}
					}
				}
				$("#ssbm1").text(memberinfo.bmmc);
				addselectbm=memberinfo.bmdm;
				selecteditGljg(memberinfo.fjgdm);
				var obj_js=document.getElementById("ssjs1");
				if(obj_js){
					var options=obj_js.options;
					if(options){
						for(var i=0;i<options.length;i++){
							if(options[i].text==memberinfo.jsmc){
								options[i].defaultSelected=true;
								options[i].selected=true;
							}
						}
					}
				}
			}
		});
		showfullbg();
		$("#editMemberDiv").show();
	}
}
function closeeditMemberDiv(){
	$("#editMemberDiv,#fullbg").hide();
}
function editMember(){
	if($("#name1").val()==""){
		alert("请输入姓名");
		return ;
	}
	if($("#name1").val().length>25){
		alert("姓名长度不能大于25位");
		return ;
	}
	if($("#xmqp1").val()==""){
		alert("请输入姓名全拼");
		return;
	}
	if($("#xmqp1").val().length>100){
		alert("姓名全拼长度不能大于100位");
		return;
	}
	if($("#xmszm1").val()==""){
		alert("请输入姓名首字母");
		return;
	}
	if($("#xmszm1").val().length>25){
		alert("姓名首字母长度不能大于25位");
		return;
	}
	if($("#zw1").val().length>25){
		alert("职务长度不能大于25位");
		return ;
	}
	if($("#ssbm1").text()=="选择部门"){
		alert("请选择部门");
		return;
	}
	if($("#lxdh1").val()==""){
		alert("请输入手机长号");
		return ;
	}
	if($("#lxdh1").val().length>15){
		alert("手机长号长度不对");
		return ;
	}
	var regx=/\D/;
	if(regx.test($("#lxdh1").val())){
		alert("手机长号只能是数字");
		return;
	}
	if(regx.test($("#sjdh1").val())){
		alert("手机短号只能是数字");
		return;
	}
	if(regx.test($("#bgsdh1").val())){
		alert("办公室电话只能是数字");
		return;
	}
	if($("#sjdh1").val().length>8){
		alert("手机短号不能大于8位");
		return;
	}
	$.ajax({
		url:'rymanager/glryupdate',
		type:'post',
		dataType:'json',
		data:{
			'glry.rybh':editryId,
			'token':token,
			'bmdm':addselectbm,
			'jsbh':$("#ssjs1 option:selected").val(),
			'glry.ryxm':$("#name1").val(),
			'glry.rymm':$("#mm1").val(),
			'glry.xmpyszm':$("#xmszm1").val(),
			'glry.sjch':$("#lxdh1").val(),
			'glry.zw':$("#zw1").val(),
			'glry.sjdh':$("#sjdh1").val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			var result=data.result.resultcode;
			var text=data.result.resultdesc;
			if(result==1){
				alert("编辑成功");
				window.location.reload();
			}else{
				alert(text);
			}
		}
	});
}

/**
 * 编辑基本信息
 */
function showEditBasicInfo(){
	$("#Infoname").val(gljg_info_name);
	$("#Infoaddress").val(gljg_info_address);
	$("#Infophone").val(gljg_info_phone);
	$("#Infodpt").val(gljg_info_department);
	var bh = $(".common_c0").height(); 
	var bw = $(".top_u1").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
	$("#editBasicInfoDiv").show();
}
function closeeditBasicInfoDiv(){
	$("#editBasicInfoDiv,#fullbg").hide();
}
function editBasicInfo(){
	if($("#Infoaddress").val().length>100){
		alert("地址长度不能大于100位");
		return;
	}
	if($("#Infoname").val().length>50){
		alert("管理机构名称长度不能大于50位");
		return;
	}
	if($("#Infophone").val().length>25){
		alert("联系电话长度不能大于25位");
		return;
	}
	$.ajax({
		url:'jgmanager/gljgupdate',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':gljg_info_dm,
			'gljg.gljgmc':$("#Infoname").val(),
			'gljg.lxdh':$("#Infophone").val(),
			'gljg.lxdz':$("#Infoaddress").val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			var result=data.result.resultcode;
			var text=data.result.resultdesc;
			if(result==1){
				alert("编辑成功");
				closeeditBasicInfoDiv();
				showGljgInfo(gljg_info_dm);
			}else{
				alert(text);
			}
		}
	});
	
}
/*function setmanagewidth(){
	var manage_width=document.documentElement.clientWidth-672;
	if(manage_width<694){
		document.getElementById("manage_width").style.width=694+"px";
	}else{
		document.getElementById("manage_width").style.width=manage_width+"px";
	}
}
window.onresize=function(){
	var manage_width=document.documentElement.clientWidth-672;
	if(manage_width<694){
		document.getElementById("manage_width").style.width=694+"px";
	}else{
		document.getElementById("manage_width").style.width=manage_width+"px";
	}
}*/
//显示管理机构部门编辑div
function showEditOver(bmdm){
	$("#gljg_department"+bmdm).show();
}
function hideEditOut(bmdm){
	$("#gljg_department"+bmdm).hide();
}
function showgljgedit(){
	$(".gljg_edit").show();
}
//显示管理机构子机构编辑div
function showgljgEditDiv(id){
	$("#gljg_gljg"+id).show();
}
function hidegljgEditDiv(id){
	$("#gljg_gljg"+id).hide();
}
function hidegljgedit(){
	$(".gljg_edit").hide();
}
//显示左边第二栏管理机构
function showgljgeditDepartment(){
	$("#gljg_edit42").show();
}
function hidegljgeditDepartment(){
	$("#gljg_edit42").hide();
}
function showGljgEditDiv(gljgdm,thisop){
	$("#editGljgDiv_text").text("编辑");
	$("#gljg_edit_name").val(thisop);
	GLJGDM=gljgdm;
	var bh = $(".common_c0").height(); 
	var bw = $(".top_u1").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
	$("#editGljgDiv").show();
}
function closeEditGljgDiv(){
	$("#editGljgDiv,#fullbg").hide();
}
function showfullbg(){
	var bh = $(".common_c0").height(); 
	var bw = $(".top_u1").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
}
//新增机构
function showGljgAddDiv(){
	$("#editGljgDiv_text").text("新增子管理局");
	$("#gljg_edit_name").val("");
	var bh = $(".common_c0").height(); 
	var bw = $(".top_u1").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
	$("#editGljgDiv").show();
}
function editGljg(){
	 if($("#editGljgDiv_text").text().indexOf("编辑")>=0){
		 if($("#gljg_edit_name").val()==""){
			 alert("请输入管理机构名称");
			 return;
		 }
		 if($("#gljg_edit_name").val().length>50){
			 alert("管理机构名称长度不能大于50");
			 return;
		 }
		 if(supergljgdm==GLJGDM){//判断机构代码和自己的代码是否一致
			 $.ajax({
					url:'jgmanager/gljgupdate' ,
					type:'post',
					dataType:'json',
					data:{
						'gljg.gljgmc':$("#gljg_edit_name").val(),
						'gljg.gljgdm':GLJGDM,
						'nameOnlyFlag':1,
						'token':token
					},
					success:function(data){
						if(data.result.resultcode==-1){
							BackToLoginPage();
						}
						var result=data.result.resultcode;
						var text=data.result.resultdesc;
						if(result==1){
							alert("修改成功");
							window.location.reload();
						}else{
							alert(text);
						}
					}
				 });
		 }else{
			 $.ajax({
					url:'jgmanager/gljgupdate' ,
					type:'post',
					dataType:'json',
					data:{
						'gljg.sjgljgdm':supergljgdm,
						'gljg.gljgmc':$("#gljg_edit_name").val(),
						'gljg.gljgdm':GLJGDM,
						'nameOnlyFlag':1,
						'token':token
					},
					success:function(data){
						if(data.result.resultcode==-1){
							BackToLoginPage();
						}
						var result=data.result.resultcode;
						var text=data.result.resultdesc;
						if(result==1){
							alert("修改成功");
							window.location.reload();
						}else{
							alert(text);
						}
					}
				 });
		 }
		 
	 }else{
		 if($("#gljg_edit_name").val()==""){
			 alert("请输入管理机构名称");
			 return;
		 }
		 if($("#gljg_edit_name").val().length>50){
			 alert("管理机构名称长度不能大于50");
			 return;
		 }
		 $.ajax({
			url:'jgmanager/gljgsave' ,
			type:'post',
			dataType:'json',
			data:{
				'gljg.sjgljgdm':supergljgdm,
				'gljg.gljgmc':$("#gljg_edit_name").val(),
				'token':token
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
	 }
}
//编辑部门
function showEditgljgchildDiv(bmdm,thisop,secondgljgdm){
	departmentdm=bmdm;
	department_SJ=secondgljgdm;
	$("#department_title").text("编辑部门");
	$("#add_department_name").val(thisop);
	showfullbg();
	$("#addGljgDepartmentDiv").show();
}
//新增部门
function showAddgljgchildDiv(gljgbm){
	department_SJ=gljgbm;
	$("#department_title").text("新增部门");
	$("#add_department_name").val("");
	showfullbg();
	$("#addGljgDepartmentDiv").show();
}
function closeaddGljgDepartmentDiv(){
	$("#addGljgDepartmentDiv,#fullbg").hide();
}
function addFirstDepartment(){
	if($("#add_department_name").val()==""){
		alert("请输入部门名称");
		return;
	}
	if($("#add_department_name").val().length>100){
		alert("部门名称长度不能超过100");
		return;
	}
	if($("#department_title").text().indexOf("新增部门")>=0){
		$.ajax({
			url:'jgmanager/glbmsave',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'glbm.bmmc':$("#add_department_name").val(),
				'jgdm':department_SJ
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("新增成功");
					closeaddGljgDepartmentDiv();
					showGljgdepartment(department_SJ);
					//window.location.reload();
				}else{
					alert(text);
				}
			}
		});
	}else{
		$.ajax({
			url:'jgmanager/glbmupdate',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'glbm.bmmc':$("#add_department_name").val(),
				'jgdm':department_SJ,
				'glbm.bmdm':departmentdm
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("编辑成功");
					window.location.reload();
				}else{
					alert(text);
				}
			}
		});
	}
}
var childbm_sj="";
var childbm_sj_sj="";
/**
 * 新增子部门
 */
function showAddDepartmentchildDiv(bmdm,bmmc,sjgljg){
	$("#add_child_department_title").text("新增"+bmmc+"子部门");
	childbm_sj=bmdm;
	childbm_sj_sj=sjgljg;
	$("#add_department_child_name").val("");
	showfullbg();
	$("#addDepartmentChildDiv").show();
}
function closeaddDepartmentChildDiv(){
	$("#addDepartmentChildDiv,#fullbg").hide();
}
function addChildDepartment(){
	if($("#add_department_child_name").val()==""){
		alert("请输入部门名称");
		return;
	}
	if($("#add_department_child_name").val().length>100){
		alert("部门名称长度不能超过100");
		return;
	}
	$.ajax({
		url:'jgmanager/glbmsave',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'glbm.bmmc':$("#add_department_child_name").val(),
			'sjbmdm':childbm_sj
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			var result=data.result.resultcode;
			var text=data.result.resultdesc;
			if(result==1){
				alert("新增成功");
				closeaddDepartmentChildDiv();
				showGljgdepartment(childbm_sj_sj);
				//window.location.reload();
			}else{
				alert(text);
			}
		}
	});
}
var xmpyszm="";
function searchMemberList(id,selectedPage,actionName){
	$(".letter_selected").attr("class","letter");
	id.className="letter_selected";
	xmpyszm=id;
	var index=id.innerText||id.textContent;
	if(index=="全部"){
		index="";
	}
	if(search_mark==0){//0是管理机构下搜索
		$.ajax({
			url:actionName,
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'xmpyszm':index,
				'page':selectedPage,
				'rows':10,
				'gljg.gljgdm':selectedGLJGDM
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);
					gotoPageMethodName = "gotoPageNo2";
					printPage(data.result.obj.pages, selectedPage, 'searchMemberList',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}
		});
	}else{
		$.ajax({
			url:actionName,
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'xmpyszm':index,
				'page':selectedPage,
				'rows':10,
				'glbm.bmdm':departmentdm
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);
					gotoPageMethodName = "gotoPageNo2";
					printPage(data.result.obj.pages, selectedPage, 'searchMemberList',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}
		});
	}
	
}
function searchMemberInfo(actionName,selectedPage){
	var selectValue=$("#search_info").val();
	if($("#search_info").val()=="姓名、手机长短号"){
		selectValue="";
	}
	if(search_mark==0){
		$.ajax({
			url:actionName,
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'selectvalue':selectValue,
				'page':selectedPage,
				'rows':10,
				'gljg.gljgdm':selectedGLJGDM
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);
					gotoPageMethodName = "gotoPageNo3";
					printPage(data.result.obj.pages, selectedPage, 'searchMemberInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}
		});
	}else if(search_mark==1){
		$.ajax({
			url:actionName,
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'selectvalue':selectValue,
				'page':selectedPage,
				'rows':10,
				'glbm.bmdm':departmentdm
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);
					gotoPageMethodName = "gotoPageNo3";
					printPage(data.result.obj.pages, selectedPage, 'searchMemberInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}
		});
	}
	
}
/**
 * 按钮效果
 */
function resetbuttonover(){
	$("#reset_button_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#reset_button_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#reset_button_right").css({"background":"url('image/main/right_pressed.png')"});
}
function resetbuttonout(){
	$("#reset_button_left").css({"background":"url('image/main/left_normal.png')"});
	$("#reset_button_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#reset_button_right").css({"background":"url('image/main/right_normal.png')"});
}
function editbuttonover(){
	$("#edit_button_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#edit_button_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#edit_button_right").css({"background":"url('image/main/right_pressed.png')"});
}
function editbuttonout(){
	$("#edit_button_left").css({"background":"url('image/main/left_normal.png')"});
	$("#edit_button_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#edit_button_right").css({"background":"url('image/main/right_normal.png')"});
}
function delbuttonover(){
	$("#del_button_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#del_button_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#del_button_right").css({"background":"url('image/main/right_pressed.png')"});
}
function delbuttonout(){
	$("#del_button_left").css({"background":"url('image/main/left_normal.png')"});
	$("#del_button_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#del_button_right").css({"background":"url('image/main/right_normal.png')"});
}
function addbuttonover(){
	$("#add_button_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#add_button_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#add_button_right").css({"background":"url('image/main/right_pressed.png')"});
}
function addbuttonout(){
	$("#add_button_left").css({"background":"url('image/main/left_normal.png')"});
	$("#add_button_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#add_button_right").css({"background":"url('image/main/right_normal.png')"});
}
function buttonOver(){
	$(".c3_button1_left_pop").css({"background":"url('image/main/left_pressed.png')"});
	$(".c3_button1_center_pop").css({"background":"url('image/main/center_pressed.png') repeat"});
	$(".c3_button1_right_pop").css({"background":"url('image/main/right_pressed.png')"});
}
function buttonOut(){
	$(".c3_button1_left_pop").css({"background":"url('image/main/left_normal.png')"});
	$(".c3_button1_center_pop").css({"background":"url('image/main/center_normal.png') repeat"});
	$(".c3_button1_right_pop").css({"background":"url('image/main/right_normal.png')"});
}
function buttonDelOver(){
	$(".c3_button2_left_pop").css({"background":"url('image/main/left_white_pressed.png')"});
	$(".c3_button2_center_pop").css({"background":"url('image/main/center_white_pressed.png') repeat"});
	$(".c3_button2_right_pop").css({"background":"url('image/main/right_white_pressed.png')"});
}
function buttonDelOut(){
	$(".c3_button2_left_pop").css({"background":"url('image/main/left_white_normal.png')"});
	$(".c3_button2_center_pop").css({"background":"url('image/main/center_white_normal.png') repeat"});
	$(".c3_button2_right_pop").css({"background":"url('image/main/right_white_normal.png')"});
}
function buttonOverPop(){
	$(".c3_button1_left_pop").css({"background":"url('image/main/left_pressed.png')"});
	$(".c3_button1_center_pop").css({"background":"url('image/main/center_pressed.png') repeat"});
	$(".c3_button1_right_pop").css({"background":"url('image/main/right_pressed.png')"});
}
function buttonOutPop(){
	$(".c3_button1_left_pop").css({"background":"url('image/main/left_normal.png')"});
	$(".c3_button1_center_pop").css({"background":"url('image/main/center_normal.png') repeat"});
	$(".c3_button1_right_pop").css({"background":"url('image/main/right_normal.png')"});
}
function buttonDelOverPop(){
	$(".c3_button2_left_pop").css({"background":"url('image/main/left_white_pressed.png')"});
	$(".c3_button2_center_pop").css({"background":"url('image/main/center_white_pressed.png') repeat"});
	$(".c3_button2_right_pop").css({"background":"url('image/main/right_white_pressed.png')"});
}
function buttonDelOutPop(){
	$(".c3_button2_left_pop").css({"background":"url('image/main/left_white_normal.png')"});
	$(".c3_button2_center_pop").css({"background":"url('image/main/center_white_normal.png') repeat"});
	$(".c3_button2_right_pop").css({"background":"url('image/main/right_white_normal.png')"});
}
function CloseOver(id){
	id.src="image/main/close_pressed.png";
}
function CloseOut(id){
	id.src="image/main/close.png";
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}
//输入框提示
function TextFocus(id) {
	if (id.value == id.defaultValue) {
		id.value = '';
		id.style.color = '#333333';
	}
}
//输入框提示
function TextBlur(id) {
	if (!id.value) {
		id.value = id.defaultValue;
		id.style.color = '#a3a3a3';
	}
}
function seeInMap(){
	window.open($("#basePath").val()+"page/highway/HighWayGljgMap.jsp?gljgdm="+selectedGLJGDM,"_blank");
}
