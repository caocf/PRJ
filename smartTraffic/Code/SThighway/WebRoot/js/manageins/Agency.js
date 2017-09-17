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
var editbm="";//点开部门下拉时放进去的一串代码，以","隔开
var bmdmlist=new Array();//部门代码字符串
$(document).ready(function(){
	//$("#top_text").text("管理机构");
	//SelectOption("ssjs",212);
	$("#manage_select").attr("class","mtop_select");
	showleftGljg();//显示管理机构信息
	
});
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
		
				$("#left_select_child1").empty();
				var list=data.result.obj;
				/*if(list==null){
					UlIsNull();
				}else{*/
				var newLb=$("<label class='left_name' id='gljg_bossname' onclick=goTogljg('"+list.nodeId+"',event,this,-1)>"+list.nodeDesc+"</label>");
					$("#gljg_bossname").text(list.nodeDesc);
					//var newDiv=$("<div class=''>"+编辑+"</div>")
					
					$("#left_no_select1").append(newLb);
					//$("#left_no_select1").append(newDiv);
					supergljgdm=list.nodeId;
					supergljgname=list.nodeDesc;
					$("#departmentry_title").text(list.nodeDesc);
					appendToLeftgljg(list,list.childrenNodes);
					shezhituo();//拖动
				//}

		}
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
function UlIsNull(){
	var newLi=$("<li><label style='margin-left:40px;'>暂无数据<label></li>");
	$("#left_select_child1").append(newLi);
}
function appendToLeftgljg(sjgljg,list){
	for(var i=0;i<list.length;i++){
		var newLi=$("<label onclick=goTogljg('"+list[i].nodeId+"',event,this,'"+i+"') " +
						"style='float:left;margin-left:40px;width:150px;overflow:hidden;height:42px;cursor:pointer;'>"+list[i].nodeDesc+"</label></li>");
		/*var newDiv=$("<div class='gljg_edit42' id='gljg_gljg"+i+"' style='left:110px;'><div class='gljg_edit_link42' id='"+list[i].nodeDesc+"' onclick=showGljgEditDiv('"+list[i].nodeId+"',this.id,'"+list[i].orgcode+"')><img src='image/main/edit.png' class='edit_img_position'/></div>" +
				"<div class='gljg_edit_link42' onclick=showAddgljgchildDiv('"+list[i].nodeId+"')><img src='image/main/add.png' class='edit_img_position'/></div>" +
						"<div class='gljg_edit_link42' onclick=showGljgDeleteDiv('"+list[i].nodeId+"')><img src='image/main/delete.png' class='edit_img_position'/></div></div>");
		newLi.append(newDiv)*/
		$("#left_select_child1").append(newLi);
		//$("#left_select_child1").append(newDiv);
	}
	selectedGLJGDM=supergljgdm;
	departmentdm=supergljgdm;
	showGljgdepartment(supergljgdm,0);//显示管理机构部门信息
	showGljgInfo(supergljgdm);//显示管理机构信息
}
/**
 * 根据管理机构显示人员和部门
 * @param gljgdm
 * @param event
 * @param gljgmc
 */
function goTogljg(gljgdm,event,thisop,num){
	search_mark=0;
	$("#manage_info_middle_name,#dpt_desc,#dpt_intro").empty();
	$(".left_select_li").attr("class","left_no_select_li");
	$(".letter_selected").attr("class","letter");
	if(num!=-1){
		$(".left_select").attr("class","left_no_select");
		$("#left_select_li"+num).attr("class","left_select_li");
	}else{
		$("#left_no_select1").attr("class","left_select");
	}
	
	//if(event.srcElement.tagName!="DIV"&&event.srcElement.tagName!="IMG"){
		showGljgdepartment(gljgdm,0);
		selectedGLJGDM=gljgdm;
		showGljgInfo(gljgdm);
		//departmentdm=gljgdm;
		var departmentry_title=thisop.innerText||thisop.textContent;
		$("#departmentry_title").text(departmentry_title);
	//}
}
function showGljgWorker(gljgdm,thisop){
	search_mark=0;
	$(".letter_selected").attr("class","letter");
	selectedGLJGDM=gljgdm;
	var departmentry_title=thisop.innerText||thisop.textContent;
	$("#departmentry_title").text(departmentry_title);
}
//删除机构
function showGljgDeleteDiv(gljgdm){
	if(confirm("你确定要删除该管理机构吗？")){
		$.ajax({
			url:'jgmanager/gljgdelete',
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
					var result=data.result.resultcode;
					var text=data.result.resultdesc;
					if(result==1){
						alert("成功");
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
}
/**
 * 显示管理机构部门信息
 */
function showGljgdepartment(gljgdm,num){
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
				var newImgDiv=$("<div style='height:42px;width:20px;float:left;' onclick='showLowerDepartment()'><img src='image/main/arrow_down.png' id='dpt_img' style='margin:16px 0 0 10px;'/></div>");
				var newLb=$("<label class='left_name'>"+data.result.obj.gljgmc+"</label>");
				/*var newDiv=$("<div class='department_style' style='width:60px;' id='gljg_edit42'>" +
						"<div class='gljg_edit_link42' onclick=showAddgljgchildDiv('"+data.result.obj.gljgdm+"')><img src='image/main/add.png' class='edit_img_position' /></div>" +
						"<div class='gljg_edit_link42' onclick=showSortDiv('"+data.result.obj.gljgdm+"',1)><img src='image/main/order.png' class='edit_img_position' /></div></div>");*/
				$("#dpt_left_no_select2").append(newImgDiv);
				$("#dpt_left_no_select2").append(newLb);
			/*	$("#dpt_left_no_select2").append(newDiv);*/
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
		selectDiv.append($("<div class='img_div'  ><img src='image/main/arrow.png' id='"+list[i].nodeId+"' class='department_arrow_style'/></div>"));
		selectDiv.append($("<label class='department_label' >"+list[i].nodeDesc+"</label>"));
		/*selectDiv.append($("<div class='department_style' id='department"+a+""+i+"'><div class='gljg_edit_link42' id='"+list[i].nodeDesc+"' onclick=showEditgljgchildDiv('"+list[i].nodeId+"',this.id,'"+secondgljgdm+"')><img src='image/main/edit.png' class='edit_img_position'/></div>" +
							"<div class='gljg_edit_link42' id='"+list[i].nodeDesc+"' onclick=showAddDepartmentchildDiv('"+list[i].nodeId+"',this.id,'"+secondgljgdm+"')><img src='image/main/add.png' class='edit_img_position'/></div>" +
								"<div class='gljg_edit_link42' onclick=showDeletegljgchildDiv('"+list[i].nodeId+"')><img src='image/main/delete.png' class='edit_img_position'/></div>" +
										"<div class='gljg_edit_link42' id='a"+list[i].nodeId+"' onclick=showSortDiv('"+list[i].nodeId+"',2)><img src='image/main/order.png' class='edit_img_position' /></div></div>"));*/
		newDiv.append(selectDiv);
		$("#department_left_select_child2").append(newDiv);
		if(list[i].childrenNodes!=""){
			var distance=0;
			appendToNext(list[i].childrenNodes,list[i].nodeId,distance);
		}else{
			$("#"+list[i].nodeId).hide();
			$("#a"+list[i].nodeId).hide();
		}
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
	    },
	    click:function(){
	    	$(".select_department").attr("class","no_select_department");
	    	$(this).attr("class","select_department");
	    	showDepartmentInfo($(this).children(".img_div").children(".department_arrow_style").attr("id"));
	    }
	});
	$(".img_div").click(function(){
		var dm=$(this).children("img").attr("id");//alert($(this).parent().attr("class"))
		//bmdmlist.push(dm);
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
function showDepartmentInfo(glbmdm){
	$.ajax({
		url:'gljglist/selectbmxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'glbmdm':glbmdm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#manage_info_middle_name,#dpt_desc").empty();
				document.getElementById("dpt_desc").style.height=0;
    			var list=data.result.obj;
    			$("#manage_info_middle_name").append($("<a style='margin-left:20px;' title='点击进入通讯录' id='dpt_name' >"+judgeIsNull(list.bmmc)+"</a>"));
    			//$("#dpt_name").text(judgeIsNull(list.bmmc));
    			$("#dpt_intro").text("简介：")
    			$("#dpt_desc").val(judgeIsNull(list.bmdesc));
    			document.getElementById("dpt_desc").style.height = document.getElementById("dpt_desc").scrollHeight + 10 + "px";
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function goToContact(bmdm){
	window.location.href=$("#basePath").val()+"/page/contacts/Contacts.jsp?bmdm="+bmdm+"&gljgdm="+selectedGLJGDM;
}
function showInfo(thisop){
	//thisop.
}
//var distance=20;
function appendToNext(list,sjbmdm,distance){
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='department_div' id='dpt"+list[i].nodeId+"' style='display:none;'></div>");
		var selectDiv=$("<div class='no_select_department' ></div>");
		selectDiv.append($("<div class='img_div' style='margin-left:"+(distance+20)+"px;'><img src='image/main/arrow.png' id='"+list[i].nodeId+"' class='department_arrow_style'/></div>"));
		selectDiv.append($("<label class='department_label' >"+list[i].nodeDesc+"</label>"));
	/*	selectDiv.append($("<div class='department_style' id='department"+a+""+i+"'><div class='gljg_edit_link42' id='"+list[i].nodeDesc+"' onclick=showEditgljgchildDiv('"+list[i].nodeId+"',this.id,'"+sjbmdm+"')><img src='image/main/edit.png' class='edit_img_position'/></div>" +
				"<div class='gljg_edit_link42' id='"+list[i].nodeDesc+"' onclick=showAddDepartmentchildDiv('"+list[i].nodeId+"',this.id,'"+sjbmdm+"')><img src='image/main/add.png' class='edit_img_position'/></div>" +
						"<div class='gljg_edit_link42' onclick=showDeletegljgchildDiv('"+list[i].nodeId+"')><img src='image/main/delete.png' class='edit_img_position'/></div>" +
								"<div class='gljg_edit_link42' id='a"+list[i].nodeId+"' onclick=showSortDiv('"+list[i].nodeId+"',2)><img src='image/main/order.png' class='edit_img_position' /></div></div>"));*/
		newDiv.append(selectDiv);
		$("#dpt"+sjbmdm).append(newDiv);
	}
	for(var i=0;i<list.length;i++){
		if(list[i].childrenNodes!=""){
			distance=parseInt($("#"+list[i].nodeId).parent(".img_div").css("margin-left"));
			//distance=$("#"+list[i].nodeId).parent().
			appendToNext(list[i].childrenNodes,list[i].nodeId,distance);
		}else{
			$("#"+list[i].nodeId).hide();
			$("#a"+list[i].nodeId).hide();
		}
	}
}
function goTogljgDepartment(bmdm,thisop,num){
	search_mark=1;
	$(".letter_selected").attr("class","letter");
	/*var e=event||window.event||arguments.callee.caller.arguments[0];
	 * if(e.srcElement.tagName!="DIV"&&e.srcElement.tagName!="IMG"){*/
		departmentdm=bmdm;
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
//删除部门
function showDeletegljgchildDiv(bmdm){
	if(confirm("你确定要删除该部门吗？")){
		$.ajax({
			url:'jgmanager/glbmdelete',
			type:'post',
			dataType:'json',
			data:{
				'glbm.bmdm':bmdm,
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
						$("#dpt"+bmdm).remove();
						//showGljgdepartment(selectedGLJGDM);
						//window.location.reload();
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
var gljg_info_name="";
var gljg_info_address="";
var gljg_info_phone="";
var gljg_info_intro="";
var gljg_info_department="";
var gljg_info_dm="";
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
			}else if(data.result.resultcode==1){
				var info=data.result.obj;
				if(info==""){
					
				}else{
					gljg_info_name=info.gljgmc;
					gljg_info_address=info.lxdz;
					gljg_info_phone=info.lxdh;
					gljg_info_intro=info.jgdesc;
					$("#gljg_name").text(info.gljgmc);
					$("#gljg_address").text("地址："+judgeIsNull(info.lxdz));
					$("#gljg_phone").text("电话："+judgeIsNull(info.lxdh));
					$("#gljg_intro").text("简介：");
					document.getElementById("gljg_intro_text").style.height=0;
					$("#gljg_intro_text").val(judgeIsNull(info.jgdesc));
					//$("#gljg_intro_text").height($("#gljg_intro_text").scrollTop());
					document.getElementById("gljg_intro_text").style.height = document.getElementById("gljg_intro_text").scrollHeight + 10 + "px";
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
						//$("#gljg_department").text(data.result.list);
						gljg_info_department=data.result.list;
					}else{
						$("#gljg_department").text("暂无下属部门");
					}
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
/**
 * 编辑基本信息
 */
function showEditBasicInfo(){
	$.ajax({
		url:'gljglist/findjginfo',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'gljg.gljgdm':gljg_info_dm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var info=data.result.obj;
				if(info==""){
					$("#Infoname").val("");
					$("#Infoaddress").val("");
					$("#Infophone").val("");
					$("#Infointro").val("");
				}else{
					$("#Infoname").val(varIsNull(info.gljgmc));
					$("#Infoaddress").val(varIsNull(info.lxdz));
					$("#Infophone").val(varIsNull(info.lxdh));
					$("#Infointro").val(varIsNull(info.jgdesc));
					$("#orgcode").val(varIsNull(info.orgcode));
					$("#orgname").val(varIsNull(info.orgname));
					if(info.sfxzcfjg==1){
						$("#yes").attr("checked","checked");
					}else{
						$("#yes").attr("checked",false);
						$("#no").attr("checked","checked");
					}
					showfullbg();
					$("#editBasicInfoDiv").show();
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function varIsNull(value){
	returnValue="";
	if((value==null||value=="null"||value=="")&&value!=0){
		return returnValue;
	}else{	
		return value;
	}
}
function closeeditBasicInfoDiv(){
	$("#editBasicInfoDiv,#fullbg").hide();
}


//显示管理机构部门编辑div
function showEditOver(bmdm){
	$("#department"+bmdm).show();
}
function hideEditOut(bmdm){
	$("#department"+bmdm).hide();
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
function showGljgEditDiv(gljgdm,thisop,orgcode){
	$("#editGljgDiv_text").text("编辑");
	$("#gljg_edit_name").val(thisop);
	$("#gljg_orgcode").val(orgcode);
	GLJGDM=gljgdm;
	showfullbg();
	$("#editGljgDiv").show();
}
function closeEditGljgDiv(){
	$("#editGljgDiv,#fullbg").hide();
}
function showfullbg(){
	var bh = $(".common_c0").height(); 
	var bw = $(".common_c0").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
}

//编辑部门
function showEditgljgchildDiv(bmdm,thisop,secondgljgdm){
	departmentdm=bmdm;
	department_SJ=secondgljgdm;
	$("#department_title").text("编辑部门");
	$("#add_department_name").val(thisop);
	$.ajax({
		url:'gljglist/selectbmxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'glbmdm':bmdm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#manage_info_middle_name").empty();
    			var list=data.result.obj;
    			$("#add_department_intro").val(judgeIsNull(list.bmdesc));
    			showfullbg();
    			$("#addGljgDepartmentDiv").show();
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
//新增部门
function showAddgljgchildDiv(gljgbm){
	department_SJ=gljgbm;
	$("#department_title").text("新增部门");
	$("#add_department_name,#add_department_intro").val("");
	showfullbg();
	$("#addGljgDepartmentDiv").show();
}
function closeaddGljgDepartmentDiv(){
	$("#addGljgDepartmentDiv,#fullbg").hide();
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
	$("#add_department_child_name,#add_department_child_intro").val("");
	showfullbg();
	$("#addDepartmentChildDiv").show();
}
function closeaddDepartmentChildDiv(){
	$("#addDepartmentChildDiv,#fullbg").hide();
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
//拖动排序
function showSortDiv(dm,num){
	var sortgljgdm="";
	var sortglbmdm="";
	if(num==1){
		sortgljgdm=dm;
	}else{
		sortglbmdm=dm;
	}
	var height="";
	$.ajax({
		url:'gljglist/selectorderlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'glbmdm':sortglbmdm,
			'gljgdm':sortgljgdm
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#outer_wrap").empty();
				var list=data.result.list;
				height=Math.round(list.length/2)*20;
				$("#sortDiv").css("margin","-"+(height+46)+"px 0 0 -150px");
				for(var i=0;i<list.length;i++){
					var newLi=$("<li class='sort_li' id='"+list[i].bmdm+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+list[i].bmmc+"</li>");
					$("#outer_wrap").append(newLi);
				}
				showfullbg();
				$("#sortDiv").show();
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
	//document.body.style.overflow="hidden";
}
function closesortDiv(){
	//document.body.style.overflow="auto";
	$("#fullbg,#sortDiv").hide();
}
