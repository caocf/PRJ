var supergljgdm="";
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
var showryway=0;//1显示机构人员；2显示部门人员
var xmpyszm="";
var newselectbm=1;
var jspInnerHtml="";
$(document).ready(function(){
	jspInnerHtml=$("#addnewMemberDiv").html();
	setHeight();
	$("#top_text").text("通讯录");
	findRoleInfo();//添加角色信息
	showleftGljg();//显示管理机构信息
	$(".letter").click(function(){
		$(".letter_selected").attr("class","letter");
		$(this).attr("class","letter_selected");
		xmpyszm=$(this).text();
		searchMemberList('gljglist/glrylistbybm',1);
	});
});
function setHeight(){
	var height=document.documentElement.clientHeight-50;
	if(height<680){
		$(".left_I1").css({"height":"680px"});
		$(".department_I1").css({"height":"680px"});
		$("#manage_c2").css({"height":"680px"});
		$("#department_left_select_child2").css({"max-height":"580px"});
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".department_I1").css({"height":""+height+"px"});
		$("#manage_c2").css({"height":""+height+"px"});
		$("#department_left_select_child2").css({"max-height":""+(height-100)+"px"});
	}
}
window.onresize=function(){
	var height=document.documentElement.clientHeight-50;
	if(height<680){
		$(".left_I1").css({"height":"680px"});
		$(".department_I1").css({"height":"680px"});
		$("#manage_c2").css({"height":"680px"});
		$("#department_left_select_child2").css({"max-height":"580px"});
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".department_I1").css({"height":""+height+"px"});
		$("#manage_c2").css({"height":""+height+"px"});
		$("#department_left_select_child2").css({"max-height":""+(height-100)+"px"});
	}
}
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
			}else if(data.result.resultcode==1){
				$("#ssjs").empty();
				var list=data.result.list;
				if(list!=""){
					for(var i=0;i<list.length;i++){
						document.getElementById("ssjs").options.add(new Option(list[i].jsmc,list[i].jsbh));//角色添加进下拉框
					}
				}
			}else{
				alert(data.result.resultdesc);
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
			}else if(data.result.resultcode==1){
				$("#left_select_child1").empty();
				var list=data.result.obj;
				/*if(list==null){
					UlIsNull();
				}else{*/
				var newLb=$("<label class='left_name' id='gljg_bossname' onclick=goTogljg('"+list.nodeId+"',event,this,-1)>"+list.nodeDesc+"</label>");
				supergljgdm=list.nodeId;
				if($("#gljgdm").val()!=""&&$("#gljgdm").val()!="null"){
					if($("#gljgdm").val()==list.nodeId){
						$("#left_no_select1").append(newLb);
						//$("#departmentry_title").text(list.nodeDesc);
						appendToLeftgljg(list,list.childrenNodes,1);
					}else{
						$("#left_no_select1").append(newLb);
						$("#left_no_select1").attr("class","left_no_select");
						appendToLeftgljg(list,list.childrenNodes,2);
					}
				}else{
					$("#left_no_select1").append(newLb);
					$("#departmentry_title").text(list.nodeDesc);
					appendToLeftgljg(list,list.childrenNodes,1);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
				
			//}
		}
	});
}
function UlIsNull(){
	var newLi=$("<li><label style='margin-left:40px;'>暂无数据<label></li>");
	$("#left_select_child1").append(newLi);
}
function appendToLeftgljg(sjgljg,list,num){//num是1就是嘉兴市公路管理局，num是2就是下级管理机构
	//document.getElementById("gljg").options.add(new Option(sjgljg.nodeDesc,sjgljg.nodeId));//管理机构添加进下拉框
	//document.getElementById("gljg1").options.add(new Option(sjgljg.nodeDesc,sjgljg.nodeId));//管理机构添加进下拉框
	for(var i=0;i<list.length;i++){
		var newLi=$("<li class='left_no_select_li'   id='left_select_li"+list[i].nodeId+"'><label onclick=goTogljg('"+list[i].nodeId+"',event,this,1) " +
						"style='float:left;margin-left:40px;width:150px;overflow:hidden;height:42px;cursor:pointer;'>"+list[i].nodeDesc+"</label></li>");
		$("#left_select_child1").append(newLi);
		//$("#left_select_child1").append(newDiv);
		//document.getElementById("gljg").options.add(new Option(list[i].nodeDesc,list[i].nodeId));//管理机构添加进下拉框
		//document.getElementById("gljg1").options.add(new Option(list[i].nodeDesc,list[i].nodeId));//管理机构添加进下拉框
	}
	if(num==2){
		$("#left_select_li"+$("#gljgdm").val()).attr("class","left_select_li");
		selectedGLJGDM=$("#gljgdm").val();
		departmentdm=$("#gljgdm").val();
		showGljgdepartment($("#gljgdm").val());//显示管理机构部门信息
		//showGljgPerson('gljglist/glrylistbybm', 1);//显示机构人员
	}else{
		selectedGLJGDM=supergljgdm;
		departmentdm=supergljgdm;
		showGljgdepartment(supergljgdm);//显示管理机构部门信息
		//showGljgPerson('gljglist/glrylistbybm', 1);//显示机构人员
	}
}
/**
 * 根据管理机构显示人员和部门
 * @param gljgdm
 * @param event
 * @param gljgmc
 */
function goTogljg(gljgdm,event,thisop,num){
	select_rybh="";
	search_mark=0;
	$(".letter_selected").attr("class","letter");//还原字母检索
	$(".left_select_li").attr("class","left_no_select_li");
	$(".letter_selected").attr("class","letter");
	if(num!=-1){
		$(".left_select").attr("class","left_no_select");
		$("#left_select_li"+gljgdm).attr("class","left_select_li");
	}else{
		$("#left_no_select1").attr("class","left_select");
	}
	
	//if(event.srcElement.tagName!="DIV"&&event.srcElement.tagName!="IMG"){
		$("#bmdm,#gljgdm").val("");
		showGljgdepartment(gljgdm);
		selectedGLJGDM=gljgdm;
		var departmentry_title=thisop.innerText||thisop.textContent;
		$("#departmentry_title").text(departmentry_title);
		showGljgPerson('gljglist/glrylistbybm',1);//显示机构下所有人员
		//showGljgDepartmentPerson('gljglist/glrylistbybm',1);//显示部门人员
	//}
}
function showGljgWorker(gljgdm,thisop){
	select_rybh="";
	search_mark=0;
	$(".select_department").attr("class","no_select_department");
	$(".letter_selected").attr("class","letter");//还原字母检索
	selectedGLJGDM=gljgdm;
	var departmentry_title=thisop.innerText||thisop.textContent;
	$("#departmentry_title").text(departmentry_title);
	showGljgPerson('gljglist/glrylistbybm',1);//显示机构下所有人员
}
/**
 * 显示管理机构部门信息
 */
function showGljgdepartment(gljgdm){
	$.ajax({
		url:'gljglist/glbmlistbyfjg',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':gljgdm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#department_left_select_child2").empty();
				$("#dpt_left_no_select2").empty();
				//$("#ssbm").empty();
				var list=data.result.list;
				var secondgljgdm=data.gljg.gljgdm;
				departmentbz=0;
				var newImgDiv=$("<div style='height:42px;width:20px;float:left;' onclick='showLowerDepartment()' ><img src='image/main/arrow_down.png' id='dpt_img' style='margin:16px 0 0 10px;'/></div>");
				var newLb=$("<label class='left_name' style='margin-left:0;width:180px;' onclick=showGljgWorker('"+data.result.obj.gljgdm+"',this) >"+data.result.obj.gljgmc+"</label>");
				$("#dpt_left_no_select2").append(newImgDiv);
				$("#dpt_left_no_select2").append(newLb);
				if(list==""){
					DepartmentUlIsNull();
					$("#dpt_img").hide();
				}else{
					appendToLeftgljgDepartment(list,secondgljgdm);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function DepartmentUlIsNull(){
	var newLi=$("<li style='text-align:center;'><label >暂无数据<label></li>");
	$("#department_left_select_child2").append(newLi);
	$(".addTr").remove();
	TableIsNull();
}
var a=65;
function appendToLeftgljgDepartment(list,secondgljgdm){//alert(String.fromCharCode(65))
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='department_div' id='dpt"+list[i].nodeId+"'></div>");
		var selectDiv=$("<div class='no_select_department' ></div>");
		selectDiv.append($("<div class='img_div'><img src='image/main/arrow.png' id='"+list[i].nodeId+"' class='department_arrow_style'/></div>"));
		selectDiv.append($("<label class='department_label' onclick=goTogljgDepartment('"+list[i].nodeId+"',this,'"+i+"') >"+list[i].nodeDesc+"</label>"));
		newDiv.append(selectDiv);
		$("#department_left_select_child2").append(newDiv);
		if(list[i].childrenNodes!=""){
			var distance=0;
			appendToNext(list[i].childrenNodes,list[i].nodeId,distance);
		}else{
			$("#"+list[i].nodeId).hide();
		}
	}
	if($("#bmdm").val()!=""&&$("#bmdm").val()!="null"){
		departmentdm=$("#bmdm").val();
		showGljgDepartmentPerson('gljglist/glrylistbybm',1);//显示人员
		var info=$("#"+$("#bmdm").val()).parent(".img_div").siblings("label");
		//var departmentry_title=info.innerText||info.textContent;
		$("#departmentry_title").text(info.text());
	}else{
		showGljgPerson('gljglist/glrylistbybm', 1);
	}
	$(".no_select_department").bind({
		mouseover:function(){
			//$(".department_style").hide();
			/*$(".department_div").each(function(){
				if($(this).children(".department_style").css("display")=="block"){
					$(this).children(".department_style").hide();
				}
			});*/
			//$(this).parent().find(".department_div").children(".department_style").hide();
	    	$(this).children(".department_style").show();
	    },
	    mouseout:function(){
	    	//$(this).parent().children(".department_style").hide();
	    	//$(this).siblings().children(".department_style").hide();
	    	$(this).children(".department_style").hide();
	    }/*,
	    click:function(){
	    	$(".select_department").attr("class","no_select_department");
	    	$(this).attr("class","select_department");
	    }*/
	});
	$(".img_div").click(function(){
		var dm=$(this).children("img").attr("id");//alert($(this).parent().attr("class"))
		if($(this).parent().parent().children(".department_div").css("display")=="none"){
			$(this).parent().parent().children(".department_div").show();
			$("#"+dm).attr("src","image/main/arrow_down.png");
		}else{
			$(this).parent().parent().children(".department_div").hide();
			//$("#department"+dm).children().hide();
			$("#"+dm).attr("src","image/main/arrow.png");
		}
	});
}
function showInfo(thisop){
	//thisop.
}
function appendToNext(list,sjbmdm,distance){
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='department_div' id='dpt"+list[i].nodeId+"' style='display:none;'></div>");
		var selectDiv=$("<div class='no_select_department' ></div>");
		selectDiv.append($("<div class='img_div' style='margin-left:"+(distance+20)+"px;'><img src='image/main/arrow.png' id='"+list[i].nodeId+"' class='department_arrow_style'/></div>"));
		selectDiv.append($("<label class='department_label' onclick=goTogljgDepartment('"+list[i].nodeId+"',this,'"+i+"') >"+list[i].nodeDesc+"</label>"));
		newDiv.append(selectDiv);
		$("#dpt"+sjbmdm).append(newDiv);
	}
	for(var i=0;i<list.length;i++){
		if(list[i].childrenNodes!=""){
			distance=parseInt($("#"+list[i].nodeId).parent(".img_div").css("margin-left"));
			appendToNext(list[i].childrenNodes,list[i].nodeId,distance);
		}else{
			$("#"+list[i].nodeId).hide();
		}
	}
	/*if(num!=0){
		distance=20+20*(num-1);
		num=0;
	}*/
}
var dptdm="";
var showDptmc="";
/*function showDepartmentDiv(num){
	if($("#addselect"+num).css("display")=="block"){
		showselectedbm();//显示选中的部门
		$("#addselect"+num).hide();
	}else{
		$("#addselect"+num).show();
	}
	$(document).bind("click",function(e){ 
		var target = $(e.target); 
		if(target.closest("#addselect"+num).length == 0&&$(event.target).attr("class") != "type_select_mark"&&$(event.target).attr("id") != "ssbm"
			&&$(event.target).attr("id") != "selectbm_image"){ 
			$("#addselect"+num).hide(); 
			showselectedbm();
		} 
	});
	$(document).click(function(event){
		if($(event.target).attr("class") != "type_select_mark"&&$(event.target).attr("id") != "ssbm"
			&&$(event.target).attr("id") != "selectbm_image"){
			$(".type_down_ul").hide();
		}
	});
	
}*/
/*function showselectedbm(){
	dptdm="";
	showDptmc="";
	var dptselect=document.getElementsByName("dptbox");
	for(var i=0;i<dptselect.length;i++){
		if(dptselect[i].checked==true){
			if(dptdm==""){
				dptdm=dptselect[i].value;
				showDptmc=$("#dpt"+dptselect[i].value).text();
			}else{
				dptdm+=","+dptselect[i].value;
				showDptmc+="，"+$("#dpt"+dptselect[i].value).text();
			}
		}
	}
	if(showDptmc==""){
		$("#ssbm").text("选择部门");
	}else{
		$("#ssbm").text(showDptmc);
	}
}*/
function goTogljgDepartment(bmdm,thisop,num){
	select_rybh="";
	search_mark=1;
	//$(".letter_selected").attr("class","letter");
	/*var e=event||window.event||arguments.callee.caller.arguments[0];
	 * if(e.srcElement.tagName!="DIV"&&e.srcElement.tagName!="IMG"){*/
	$(".letter_selected").attr("class","letter");//还原字母检索
	$(".select_department").attr("class","no_select_department");
	$("#bmdm,#gljgdm").val("");
	//thisop.parentNode.attr("class","select_department");
	thisop.parentNode.className="select_department";
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
	showryway=2;//显示部门人员
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
		/*beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},*/
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
					appendToTable(list,2);
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showGljgDepartmentPerson',
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
/**
 * 显示机构下所有人员
 */
function showGljgPerson(actionName,selectedPage){
	showryway=1;//显示机构人员
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
			}else if(data.result.resultcode==1){
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list,1);
					gotoPageMethodName = "gotoPageNo1";
					printPage(data.result.obj.pages, selectedPage, 'showGljgPerson',
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
function appendToTable(list,num){
	if(num==2){
		$("#order_button").show();
		shezhituo();
	}else{
		$("#order_button").hide();
	}
	for(var i=0;i<list.length;i++){
		var rybmlist=list[i].rybmlist;
		var newTr=$("<tr class='addTr'></tr>");
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].ryxm)+"<input type='hidden' value='"+list[i].rybh+"' />&nbsp;</td>"));
			//newTr.append($("<td>"+judgeIsNull(list[i].bmmc)+"&nbsp;</td>"));
			var bmmclist="";
			var zwmclist="";
			var bgsdhlist=""
			for(var j=0;j<rybmlist.length;j++){
				if(bmmclist==""){
					bmmclist="<label>"+judgeIsNull(rybmlist[j].bmmc)+"</label>";
					zwmclist="<label>"+judgeIsNull(rybmlist[j].zwmc)+"</label>";
					bgsdhlist="<label>"+judgeIsNull(rybmlist[j].bgsdh)+"</label>";
				}else{
					bmmclist+="<br/><label>"+judgeIsNull(rybmlist[j].bmmc)+"</label>";
					zwmclist+="<br/><label>"+judgeIsNull(rybmlist[j].zwmc)+"</label>";
					bgsdhlist+="<br/><label>"+judgeIsNull(rybmlist[j].bgsdh)+"</label>";
				}
			}
			newTr.append($("<td class='selected_info'>"+bmmclist+"&nbsp;</td>"));
			newTr.append($("<td class='selected_info'>"+zwmclist+"&nbsp;</td>"));
			newTr.append($("<td class='selected_info'>"+bgsdhlist+"&nbsp;</td>"));
			//手机长号
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].sjch)+"&nbsp;</td>"));
			newTr.append($("<td class='selected_info'>"+judgeIsNull(list[i].sjdh)+"&nbsp;</td>"));
			//操作
			newTr.append($("<td class='selected_info'><a class='Operate' onclick='seeDetailInfo("+list[i].rybh+")'>详细</a></td>"));
					/*"<a class='Operate' onclick=editRyInfo('"+list[i].rybh+"','"+list[i].ryxm+"','"+list[i].zw+"','"+list[i].lxdh+"','"+list[i].bmdm+"','"+list[i].bmmc+"')>编辑</a>" +
					"<a class='Operate' onclick='deleteMember("+list[i].rybh+")'>离职</a><a class='Operate' onclick='resetPassword("+list[i].rybh+")'>重置密码</a></td>"));*/
		$(".listTable").append(newTr);
	}
	$(".selected_info").on("click",function(){
		$(".selected_info").each(function(){
			if($(this).css("background-color")=="rgb(211, 226, 232)"||$(this).css("background-color")=="#d3e2e8"){
				//$(this).css("background-color",$(this).siblings().css('background-color'));
				$(this).css({"background-color":"#ffffff"});
			}
		});
		select_rybh=$(this).parent().children("td").first().children("input").val(); 
		$(this).css({"background-color":"#d3e2e8"});
		$(this).siblings().css({"background-color":"#d3e2e8"});
	});
}
function getMousePos(e) {
	return {
		x : e.pageX || e.clientX + document.body.scrollLeft,
		y : e.pageY || e.clientY + document.body.scrollTop
	}
}
// 获取元素位置
function getElementPos(el) {
	return {
		x : el.offsetParent ? el.offsetLeft
				+ arguments.callee(el.offsetParent)['x'] : el.offsetLeft,
		y : el.offsetParent ? el.offsetTop
				+ arguments.callee(el.offsetParent)['y'] : el.offsetTop
	}
}
// 获取元素尺寸
function getElementSize(el) {
	return {
		width : el.offsetWidth,
		height : el.offsetHeight
	}
}
// 禁止选择
document.onselectstart = function() {
	return false;
}
// 判断是否有挪动
var MOVE = {};
MOVE.isMove = false;

// 就是创建的标杆
var div = document.createElement('div');
div.style.width = '250px';
div.style.height = '1px';
div.style.fontSize = '0';
div.style.background = 'red';
/**
 * 拖动
 */
function shezhituo(){
	var outer_wrap = document.getElementById("outer_wrap");
	outer_wrap.onmousedown = function(event) {
		// 获取列表顺序
		var lis = outer_wrap.getElementsByTagName('li');
		for (var i = 0; i < lis.length; i++) {
			lis[i]['pos'] = getElementPos(lis[i]);
			lis[i]['size'] = getElementSize(lis[i]);
		}
		event = event || window.event;
		var t = event.target || event.srcElement;
		if (t.className == "sort_li") {
			var p = getMousePos(event);
			var el = t.cloneNode(true);
			el.style.position = 'absolute';
			el.style.left = t.pos.x + 'px';
			el.style.top = t.pos.y + 'px';
			el.style.width = t.size.width + 'px';
			el.style.height = t.size.height + 'px';
			el.style.border = '1px solid #d4d4d4';
			el.style.background = '#d4d4d4';
			el.style.opacity = '0.7';
			document.body.appendChild(el);

			document.onmousemove = function(event) {
				event = event || window.event;
				var current = getMousePos(event);
				el.style.left = t.pos.x + current.x - p.x + 'px';
				el.style.top = t.pos.y + current.y - p.y + 'px';
				document.body.style.cursor = 'move';

				// 判断插入点
				for (var i = 0; i < lis.length; i++) {
					if (current.x > lis[i]['pos']['x']
							&& current.x < lis[i]['pos']['x']
									+ lis[i]['size']['width']
							&& current.y > lis[i]['pos']['y']
							&& current.y < lis[i]['pos']['y']
									+ lis[i]['size']['height'] / 2) {
						if (t != lis[i]) {
							MOVE.isMove = true;
							outer_wrap.insertBefore(div, lis[i]);
						}

					} else if (current.x > lis[i]['pos']['x']
							&& current.x < lis[i]['pos']['x']
									+ lis[i]['size']['width']
							&& current.y > lis[i]['pos']['y']
									+ lis[i]['size']['height'] / 2
							&& current.y < lis[i]['pos']['y']
									+ lis[i]['size']['height']) {
						if (t != lis[i]) {
							MOVE.isMove = true;
							outer_wrap.insertBefore(div, lis[i].nextSibling);
						}
					}
				}
			}
			// 移除事件
			document.onmouseup = function(event) {
				event = event || window.event;
				document.onmousemove = null;
				if (MOVE.isMove) {
					outer_wrap.replaceChild(t, div);
					MOVE.isMove = false;
				}
				document.body.removeChild(el);
				el = null;
				document.body.style.cursor = 'normal';
				document.onmouseup = null;
			}
		}
	}
}

//拖动排序
function showSortDiv(){
	var height="";
	$.ajax({
		url:'gljglist/selectryorderlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'glbmdm':departmentdm,
			'rows':-1,
			'page':-1
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#outer_wrap").empty();
				var list=data.result.list;
				height=Math.round(list.length/2)*25;
				$("#sortDiv").css("margin","-"+(height+46)+"px 0 0 -150px");
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='sort_li' id='"+list[i].rybh+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+list[i].ryxm+"</li>");
					$("#outer_wrap").append(newLi);
				}
				showfullbg();
				$("#sortDiv").show();
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function closesortDiv(){
	//document.body.style.overflow="auto";
	$("#fullbg,#sortDiv").hide();
}
function suresort(){
	var objs=document.getElementById("outer_wrap").childNodes;
	var bmdmsort="";
	for(var i=0;i<objs.length;i++){
		if(bmdmsort==""){
			bmdmsort=objs[i].id;
		}else{
			bmdmsort+=","+objs[i].id;
		}
	}
	$.ajax({
		url:'gljglist/updateryorderlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'orderlist':bmdmsort,
			'glbmdm':departmentdm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			var result=data.result.resultcode;
			var text=data.result.resultdesc;
			if(result==1){
				alert("排序成功");
				closesortDiv();
				//showGljgdepartment(selectedGLJGDM,1);
				showGljgDepartmentPerson('gljglist/glrylistbybm',1);
				//window.location.reload();
			}else{
				alert(text);
			}
		}
	});
}
/**
 * 判断是否为null
 */
function judgeIsNull(value){
	returnValue=" ";
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
		searchMemberList(actionName,pageNo);
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
			}else if(data.result.resultcode==1){
				$(".ryinfoTr").remove();
				var memberinfo=data.result.obj;
				var rybmlist=memberinfo.rybmlist;
				var html="<tr class='ryinfoTr'><td class='td_left1'>姓名&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<label>"+memberinfo.ryxm+"</label></td></tr>";
				for(var i=0;i<rybmlist.length;i++){
					html+="<tr class='ryinfoTr'><td class='td_left1'>所属机构&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<label>"+judgeIsNull(rybmlist[i].ssjgmc)+"</label></tr>" +
						"<tr class='ryinfoTr'><td class='td_left1'>部门&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<label>"+judgeIsNull(rybmlist[i].bmmc)+"</label></tr>" +
						"<tr class='ryinfoTr'><td class='td_left1'>职务&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<label>"+judgeIsNull(rybmlist[i].zwmc)+"</label></tr>" +
						"<tr class='ryinfoTr'><td class='td_left1'>办公室电话&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<label>"+judgeIsNull(rybmlist[i].bgsdh)+"</label></tr>" 
				}
					html+="<tr class='ryinfoTr'><td class='td_left1'>手机长号&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<label>"+judgeIsNull(memberinfo.sjch)+"</label></tr>" +
							"<tr class='ryinfoTr'><td class='td_left1'>手机短号&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<label>"+judgeIsNull(memberinfo.sjdh)+"</label></tr>" +
								"<tr class='ryinfoTr'><td class='td_left1'>角色&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<label>"+judgeIsNull(memberinfo.jsmc)+"</label></tr>";
				
				/*$("#member_xmqp").text(judgeIsNull(memberinfo.xmqp));
				$("#member_xmszm").text(judgeIsNull(memberinfo.xmpyszm));*/
				$("#ryinfolist").append(html);
				showfullbg();
				$("#member_info").show();
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function closememberInfoDiv(){
	$("#member_info,#fullbg").hide();
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
					}else if(data.result.resultcode==1){
						var result=data.result.resultcode;
						var text=data.result.resultdesc;
						if(result==1){
							alert("删除成功");
							//window.location.reload();
							if(showryway==1){
								showGljgPerson("gljglist/glrylistbybm", select_page);
							}else{
								showGljgDepartmentPerson("gljglist/glrylistbybm", select_page);
							}
						}else{
							alert(text);
						}
					}else{
						alert(data.result.result.desc);
					}
					
				}
			});
		}
	}
	
}
//重置密码
var resetId="";
function resetPassword(){
	if(select_rybh==""){
		alert("请选择人员");
	}else{
		showfullbg();
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
			}else if(data.result.resultcode==1){
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("重置成功");
					window.location.reload();
				}else{
					alert(text);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
//编辑成员
var editryId="";
var editBmDm="";
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
				}else if(data.result.resultcode==1){
					var memberinfo=data.result.obj;
					var rybmlist=memberinfo.rybmlist;
					/*$("#ssjg0").val(rybmlist[0].ssjgbh);
					$("#zw0").text(rybmlist[0].zwmc);
					$("#zwid0").text(rybmlist[0].zwbh);
					selectaddGljg(0);
					$("#bgsdh0").val(rybmlist[0].bgsdh);*/
					addTrnum=rybmlist.length;
					for(var i=0;i<rybmlist.length;i++){
						if(i>0){
							$(".adddpt"+i).remove();
							var html="";
							html="<tr class='adddpt"+i+"'><td colspan='4' style='height:10px;'>&nbsp;</td></tr><tr class='adddpt"+i+"' name='addTr'><td class='td_left1'>所属机构&nbsp;&nbsp;&nbsp;</td>" +
							"<td class='td_right'>&nbsp;&nbsp;&nbsp;<select id='ssjg"+i+"' style='width:207px;' onchange=selectaddDpt('"+i+"')></select></td>" +
							"<td class='td_left1'>所属部门&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<select id='ssbm"+i+"' name='ssbm' style='width:207px;'></select>" +
							"<img class='add_image' onmouseover='addImageOver(this)' onmouseout='addImageOut(this)' " +
								"src='image/main/ic_add_normal.png' onclick='addDepartment()' /></td></tr>";
							html+="<tr class='adddpt"+i+"'><td class='td_left1'>职务&nbsp;&nbsp;&nbsp;</td><td class='td_right'>" +
									"<div class='type_select_div' style='margin-left:12px;'>" +
										"<div class='type_select' ><div class='type_select_mark' id='divposition"+i+"' onclick=showZWDiv('"+i+"')>" +
											"<label class='zw_label' id='zw"+i+"'>选择职务</label><input type='hidden' name='zwbh' id='zwid"+i+"' value='' /><img src='image/main/arrow_down.png' " +
													"style='float:right;margin:13px 10px 0 0;' id='selectbm_image'></div>" +
										"<div class='zw_down_div' id='zw_down_div_add"+i+"'>" +
											"<ul class='zw_down_ul' id='zwselect"+i+"'></ul>" +
											"<button class='zw_button' onclick=selectZW('"+i+"')>确定</button><button class='zw_button' onclick=hideZWDiv('"+i+"')>取消</button></div></div></div></td>" +
												"<td class='td_left2'>办公室电话&nbsp;&nbsp;&nbsp;</td><td class='td_right'><label style='float:left;'>&nbsp;&nbsp;&nbsp;</label><input class='mark_input' style='float:left;' name='bgsdh' id='bgsdh"+i+"' type='text'/>" +
													"<img class='add_image' src='image/main/ic_delete_normal.png' onclick=delDepartment('"+i+"') onmouseover='delImageOver(this)' onmouseout='delImageOut(this)' /></td></tr>";
							$("#addnewMemberTable").append(html);
						}
						$("#ssjg"+i).val(rybmlist[i].ssjgbh);
						$("#bgsdh"+i).val(rybmlist[i].bgsdh);
						$("#zw"+i).text(judgeIsNull(rybmlist[i].zwmc));
						$("#zwid"+i).val(rybmlist[i].zwbh);
						selecteditaddGljg(i,rybmlist[i].ssjgbh,rybmlist[i].bmbh);
					}
					$("#name").val(memberinfo.ryxm);
					$("#xmqp").val(memberinfo.xmqp);
					$("#xmszm").val(memberinfo.xmpyszm);
					$("#lxdh").val(memberinfo.sjch);
					$("#sjdh").val(memberinfo.sjdh);
					$("#ssjs").val(memberinfo.jsbh);
					$("#member_title").text("编辑成员");
					showfullbg();
					$(".hideMmTr").hide();
					$("#addnewMemberDiv").show();
					//dptdm=memberinfo.bmdm;
					//editBmDm=String(memberinfo.bmdm);
					//showSelectedBm(editBmDm);
					//addselectbm=memberinfo.bmdm;
					/*var obj_js=document.getElementById("ssjs");
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
					}*/
				}else{
					alert(data.result.resultdesc);
				}
				
			}
		});
	}
}

function showSelectedBm(bmdm){
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
				$("#addselect0").empty();
				var list=data.result.list;
				if(list==""){
					//NullInfo();
				}else{
					appendToEditBmDiv(list,bmdm);
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}

function appendToEditBmDiv(list,bmdm){
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='gljgAndDpt'></div>");
		newDiv.append($("<div class='jg_img_div'><img src='image/main/arrow.png' id='"+list[i].gljgdm+"' class='jg_arrow_style' style='cursor:pointer;' /></div>"));
		newDiv.append($("<label class='jg_name'>"+list[i].gljgmc+"</label>"));
		newDiv.append($("<ul class='jg' id='jg"+list[i].gljgdm+"'></ul>"));
		$("#addselect0").append(newDiv);
		appendToEditJG(list[i].gljgdm,bmdm);
	}
	$(".jg_img_div").click(function(){
		var id=$(this).children("img").attr("id");
		$(".jg_arrow_style").attr("src","image/main/arrow.png");
		if($(this).parent().children(".jg").css("display")=="none"){
			$(".jg").hide();
			$(this).parent().children(".jg").show();
			$("#"+id).attr("src","image/main/arrow_down.png");
		}else{
			$(this).parent().children(".jg").hide();
			$("#"+id).attr("src","image/main/arrow.png");
		}
	});
}
function appendToEditJG(gljgdm,bmdm){
	$.ajax({
		url:'gljglist/glbmlistbyfjg',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':gljgdm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				var selectedbmdm=bmdm.split(",");
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='jg_no_select_li'><input class='dpt_input' type='checkbox' name='dptbox' value='"+list[i].nodeId+"' />&nbsp;&nbsp;&nbsp;<label id='dpt"+list[i].nodeId+"'>"+list[i].nodeDesc+"</label></li>");
					for(var j=0;j<selectedbmdm.length;j++){
						if(selectedbmdm[j]==list[i].nodeId){
							newLi=$("<li class='jg_no_select_li'><input class='dpt_input' type='checkbox' checked='checked' name='dptbox' value='"+list[i].nodeId+"' />&nbsp;&nbsp;&nbsp;<label id='dpt"+list[i].nodeId+"'>"+list[i].nodeDesc+"</label></li>");
							$("#jg"+gljgdm).show();
						}else{
							continue;
						}
					}
					$("#jg"+gljgdm).append(newLi);
					
				}
				
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}

function closeeditMemberDiv(){
	$("#editMemberDiv,#fullbg").hide();
	$("#zw_down_div_edit").hide();
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
			'glry.zw':$("#zw1").text(),
			'glry.sjdh':$("#sjdh1").val(),
			'glry.xmqp':$("#xmqp1").val(),
			'glry.bgsdh':$("#bgsdh1").val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("编辑成功");
					//window.location.reload();
					closeeditMemberDiv();
					if(showryway==1){
						showGljgPerson("gljglist/glrylistbybm", select_page);
					}else{
						showGljgDepartmentPerson("gljglist/glrylistbybm", select_page);
					}
				}else{
					alert(text);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
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
function searchMemberList(actionName,selectedPage){
	select_rybh="";
	var index=xmpyszm;
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
				}else if(data.result.resultcode==1){
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
				}else{
					alert(data.result.resultdesc);
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
				}else if(data.result.resultcode==1){
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
				}else{
					alert(data.result.resultdesc);
				}
			}
		});
	}
	
}
function searchMemberInfo(actionName,selectedPage){
	select_rybh="";
	$(".letter_selected").attr("class","letter");//还原字母检索
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
				}else if(data.result.resultcode==1){
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
				}else{
					alert(data.result.resultdesc);
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
				}else if(data.result.resultcode==1){
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
				}else{
					alert(data.result.resultdesc);
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
//新成员入职
function showinductionDiv(){
	$("#addnewMemberDiv").empty();
	$("#addnewMemberDiv").append(jspInnerHtml);
	findRoleInfo();//添加角色信息
	addTrnum=1;
	zwId.splice(0,zwId.length); 
	/*$("#name,#mm,#checkmm,#lxdh,#xmqp,#xmszm,#bgsdh,#sjdh").val("");
	$("#zw").text("选择职务");*/
	$("#member_title").text("新成员入职");
	$(".hideMmTr").show();
	var bh = $(".common_c0").height();
	var bw = $(".top_u1").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
	selectaddGljg(0);
	$("#addnewMemberDiv").show();
	$("#addscroolDiv").scroll(function(){
		$(".zw_down_div").hide();
	});
	window.onscroll=function(){
		$(".zw_down_div").hide();
	}
}
function addImageOver(id){
	id.src="image/main/ic_add_pressed.png";
}
function addImageOut(id){
	id.src="image/main/ic_add_normal.png";
}
function delImageOver(id){
	id.src="image/main/ic_delete_pressed.png";
}
function delImageOut(id){
	id.src="image/main/ic_delete_normal.png";
}
function closeaddnewMemberDiv(){
	$("#addnewMemberDiv,#fullbg,#zw_down_div_add,.type_down_ul").hide();
	for(var i=addTrnum;i>0;i--){
		$(".adddpt"+i).remove();
	}
}
function selectaddGljg(num){
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
				$("#ssjg"+num).empty();
				var list=data.result.list;
				if(list==""){
					//NullInfo();
				}else{
					for(var i=0;i<list.length;i++){
						document.getElementById("ssjg"+num).options.add(new Option(list[i].gljgmc,list[i].gljgdm));//管理机构添加进下拉框
					}
					selectaddDpt(num);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function selecteditaddGljg(num,gljgdm,ssbmdm){
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
				$("#ssjg"+num).empty();
				var list=data.result.list;
				if(list==""){
					//NullInfo();
				}else{
					for(var i=0;i<list.length;i++){
						document.getElementById("ssjg"+num).options.add(new Option(list[i].gljgmc,list[i].gljgdm));//管理机构添加进下拉框
					}
					$("#ssjg"+num).val(gljgdm);
					selecteditaddDpt(num,ssbmdm);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function selecteditaddDpt(num,ssbmdm){
	$.ajax({
		url:'gljglist/glbmlistbyfjg',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':$("#ssjg"+num).val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#ssbm"+num).empty();
				var list=data.result.list;
				if(list==""){
					
				}else{
					for(var i=0;i<list.length;i++){
						document.getElementById("ssbm"+num).options.add(new Option(list[i].nodeDesc,list[i].nodeId));//角色添加进下拉框
					}
					$("#ssbm"+num).val(ssbmdm);
				}
				
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function selectaddDpt(num){
	$.ajax({
		url:'gljglist/glbmlistbyfjg',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':$("#ssjg"+num).val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#ssbm"+num).empty();
				var list=data.result.list;
				if(list==""){
					
				}else{
					for(var i=0;i<list.length;i++){
						document.getElementById("ssbm"+num).options.add(new Option(list[i].nodeDesc,list[i].nodeId));//角色添加进下拉框
					}
				}
				
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
var addTrnum=1;
function addDepartment(){
	var html="<tr class='adddpt"+addTrnum+"'><td colspan='4' style='height:10px;'>&nbsp;</td></tr><tr class='adddpt"+addTrnum+"' name='addTr'><td class='td_left1'>所属机构&nbsp;&nbsp;&nbsp;</td>" +
			"<td class='td_right'>&nbsp;&nbsp;&nbsp;<select id='ssjg"+addTrnum+"' style='width:207px;' onchange=selectaddDpt('"+addTrnum+"')></select></td>" +
			"<td class='td_left2'>所属部门&nbsp;&nbsp;&nbsp;</td><td class='td_right'>&nbsp;&nbsp;&nbsp;<select id='ssbm"+addTrnum+"' name='ssbm' style='width:207px;'></select>" +
			"<img class='add_image' onmouseover='addImageOver(this)' onmouseout='addImageOut(this)' " +
				"src='image/main/ic_add_normal.png' onclick='addDepartment()' /></td></tr>";
	html+="<tr class='adddpt"+addTrnum+"'><td class='td_left1'>职务&nbsp;&nbsp;&nbsp;</td><td class='td_right'>" +
			"<div class='type_select_div' style='margin-left:12px;'>" +
				"<div class='type_select' ><div class='type_select_mark' id='divposition"+addTrnum+"' onclick=showZWDiv('"+addTrnum+"')>" +
					"<label class='zw_label' id='zw"+addTrnum+"'>选择职务</label><input type='hidden' name='zwbh' id='zwid"+addTrnum+"' value='' /><img src='image/main/arrow_down.png' " +
							"style='float:right;margin:13px 10px 0 0;' id='selectbm_image'></div>" +
				"<div class='zw_down_div' id='zw_down_div_add"+addTrnum+"'>" +
					"<ul class='zw_down_ul' id='zwselect"+addTrnum+"'></ul>" +
					"<button class='zw_button' onclick=selectZW('"+addTrnum+"')>确定</button><button class='zw_button' onclick=hideZWDiv('"+addTrnum+"')>取消</button></div></div></div></td>" +
						"<td class='td_left2'>办公室电话&nbsp;&nbsp;&nbsp;</td><td class='td_right'><label style='float:left;'>&nbsp;&nbsp;&nbsp;</label><input style='float:left;' class='mark_input' name='bgsdh' id='bgsdh"+addTrnum+"' type='text'/>" +
							"<img class='add_image' src='image/main/ic_delete_normal.png' onclick=delDepartment('"+addTrnum+"') onmouseover='delImageOver(this)' onmouseout='delImageOut(this)' /></td></tr>";
	$("#addnewMemberTable").append(html);
	selectaddGljg(addTrnum);
	addTrnum=addTrnum+1;
}
function delDepartment(num){
	$(".adddpt"+num).remove();
}
var zwmcselect=new Array();
var zwbhlist=new Array();
function showZWDiv(num){
	zwmcselect.splice(0,zwmcselect.length);
	zwbhlist.splice(0,zwbhlist.length);
	if($("#zw_down_div_add"+num).css("display")=="none"){
		$.ajax({
			url:'rymanager/postlist',
			type:'post',
			dataType:'json',
			data:{
				'token':token
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}else if(data.result.resultcode==1){
					$("#zwselect,#zwselect_edit").empty();
					var list=data.result.list;
					/*var zwmc="";
					if($("#zw").text()!="选择职务"){
						zwmc=String($("#zw").text()).split("、");
					}*/
					$(".zw_down_ul").empty();
					for(var i=0;i<list.length;i++){
						var newLi=$("<li class='zwli' name='zwmc"+num+"' id='"+list[i].zwbh+"'>"+list[i].zwmc+"</li>");
						$("#zwselect"+num).append(newLi);
					}
					var div1=document.getElementById("divposition"+num);
				    var div2=document.getElementById("zw_down_div_add"+num);
				    div2.style.top=div1.getBoundingClientRect().top+"px";
				    div2.style.left=div1.getBoundingClientRect().left+"px";
					$("#zw_down_div_add"+num).show();	
					$(".zwli").click(function(){
						if($(this).attr("class")=="zwli_select"){
							$(this).attr("class","zwli");
							zwmcselect.splice($.inArray($(this).text(),zwmcselect),1);
							zwbhlist.splice($.inArray($(this).attr("id"),zwbhlist),1);
						}else{
							$(this).attr("class","zwli_select");
							zwmcselect.push($(this).text());
							zwbhlist.push($(this).attr("id"));
						}
					});
					$(document).bind("click",function(e){ 
						var target = $(e.target); 
						if(target.closest("#zw_down_div_add"+num).length == 0
							&&$(event.target).attr("id") != "selectbm_image"){ 
							$("#zw_down_div_add"+num).hide(); 
						} 
					});
				}else{
					alert(data.result.resultdesc);
				}
			}
		});
	}else{
		$("#zw_down_div_add"+num).hide();
	}
	
}
function hideZWDiv(num){
		$("#zw_down_div_add"+num).hide();	
}
var zwId=new Array();
function selectZW(num){
	/*var zw="";
	var zwid="";
	var zwmc=document.getElementsByName("zwmc"+num);
	for(var i=0;i<zwmc.length;i++){
		if(zwmc[i].className=="zwli_select"){
			if(zw==""){
				zw=zwmc[i].innerText||zwmc[i].textContent;
				zwid=zwmc[i].id;
			}else{
				zw+="、"+zwmc[i].innerText||zwmc[i].textContent;
				zwid+=","+zwmc[i].id;
			}
		}
	}*/
	//alert(zw);
	//zwId.push(zw);
	//alert(zwmcselect+"eeeee"+zwbhlist)
	$("#zwid"+num).val(zwbhlist);
	$("#zw"+num).text(zwmcselect);
	hideZWDiv(num);
}
function addNewMember(){
	if(!checkinfo()){
		return;
	}
	var zwmc=$("#zw").text();
	if($("#zw").text()=="选择职务"){
		zwmc="";
	}
	var bgsdhinfo="";
	var bgsdh=document.getElementsByName("bgsdh");
	for(var i=0;i<bgsdh.length;i++){
		if(i==0){
			bgsdhinfo=bgsdh[i].value;
		}else{
			bgsdhinfo+=";"+bgsdh[i].value;
		}
	}
	var ssbminfo="";
	var ssbm=document.getElementsByName("ssbm");
	for(var i=0;i<ssbm.length;i++){
		if(i==0){
			ssbminfo=ssbm[i].value;
		}else{
			ssbminfo+=";"+ssbm[i].value;
		}
	}
	var zwbhinfo="";
	var zwbh=document.getElementsByName("zwbh");
	for(var i=0;i<zwbh.length;i++){
		/*if(zwbh[i].value==""){
			alert("请选择职务");
			return;
		}else{*/
			if(i==0){
				zwbhinfo=zwbh[i].value;
			}else{
				zwbhinfo+=";"+zwbh[i].value;
			}
		//}
	}
	if($("#member_title").text().indexOf("新成员入职")>-1){
		if($("#mm").val()==""){
			alert("请输入密码");
			return;
		}
		var regu = /^\w+$/;
		if (!regu.test($("#mm").val())) {
			alert("密码只能包含_ ,英文字母,数字!");
			return;
		}
		if($("#mm").val().length>20||$("#mm").val().length<5){
			alert("请输入5-20位的密码");
			return;
		}
		if($("#checkmm").val()!=$("#mm").val()){
			alert("两次密码不同");
			return;
		}
		$.ajax({
			url:'rymanager/glrysave',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'bmdm':ssbminfo,
				'jsbh':$("#ssjs option:selected").val(),
				'glry.ryxm':$("#name").val(),
				'glry.xmqp':$("#xmqp").val(),
				'glry.xmpyszm':$("#xmszm").val(),
				'glry.rymm':$("#mm").val(),
				'glry.sjch':$("#lxdh").val(),
				'zwbh':zwbhinfo,
				'glry.sjdh':$("#sjdh").val(),
				'bgsdh':bgsdhinfo
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}else if(data.result.resultcode==1){
					var result=data.result.resultcode;
					var text=data.result.resultdesc;
					if(result==1){
						alert("新增成功");
						window.location.reload();
					}else{
						alert(text);
					}
				}else{
					alert(data.result.resultdesc);
				}
				
			}
		});
	}else{
		$.ajax({
			url:'rymanager/glryupdate',
			type:'post',
			dataType:'json',
			data:{
				'glry.rybh':editryId,
				'token':token,
				'bmdm':ssbminfo,
				'jsbh':$("#ssjs option:selected").val(),
				'glry.ryxm':$("#name").val(),
				'glry.xmpyszm':$("#xmszm").val(),
				'glry.sjch':$("#lxdh").val(),
				'zwbh':zwbhinfo,
				'glry.sjdh':$("#sjdh").val(),
				'glry.xmqp':$("#xmqp").val(),
				'bgsdh':bgsdhinfo
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}else if(data.result.resultcode==1){
					var result=data.result.resultcode;
					var text=data.result.resultdesc;
					if(result==1){
						editBmDm="";
						alert("编辑成功");
						//window.location.reload();
						closeaddnewMemberDiv();
						if(showryway==1){
							showGljgPerson("gljglist/glrylistbybm", select_page);
						}else{
							showGljgDepartmentPerson("gljglist/glrylistbybm", select_page);
						}
					}else{
						alert(text);
					}
				}else{
					alert(data.result.resultdesc);
				}
			}
		});
	}
	
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
	/*if($("#zw").val().length>25){
		alert("职务长度不能大于25位");
		return false;
	}*/
	if($("#lxdh").val()==""){
		alert("请输入手机长号");
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
	if(regx.test($("input[name='bgsdh']").val())){
		alert("办公室电话只能是数字");
		return false;
	}
	if($("#lxdh").val().length>15){
		alert("手机长号长度不能大于15位");
		return false;
	}
	if($("#sjdh").val().length>10){
		alert("手机短号长度不能大于10位");
		return false;
	}
	if($("input[name='bgsdh']").val().length>25){
		alert("办公室电话长度不能大于25位");
		return false;
	}
	/*if($("#ssbm").text()=="选择部门"){
		alert("请选择部门");
		return false;
	}*/
	return true;
}