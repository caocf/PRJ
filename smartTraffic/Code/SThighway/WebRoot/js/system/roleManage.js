var delId="";
var permissionId='';
var isremind="";
var mark=new Array();
var fatherGroup=new Array();
$(document).ready(function(){
	$("#top_text").text("系统设置");//top上的显示
	showRoleInfo('jsqxmanager/jslistall',1);
	//全选键失效
	/*window.document.getElementById("selectall").onclick=null;
	$("#selectall").click(function(){
		this.checked=false;
		alert("你不能删除其他部门用户！");
	});*/
});
/**
 * 显示角色列表
 * @param actionName
 * @param selectedPage
 */
function showRoleInfo(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':10,
			'page':selectedPage
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode==-2){
				alert(data.result.resultdesc);
			}else{
			$(".addTr").empty();
			$(".addTr").remove();
			var list=data.result.obj.data;
			if (list == "") {
				TableIsNull();
			} else {
				appendToTable(list);// 跳转页码的方法名称	
				gotoPageMethodName = "gotoPageNo";
				printPage(data.result.obj.pages, selectedPage, 'showRoleInfo',
						actionName, gotoPageMethodName, data.result.obj.total);
			}
		}
	  }
	});
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		if(i%2==0){
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td style='background:#f9f9f9'>&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].jsmc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qxs)+"&nbsp;</td>"));
			//操作
			if(list[i].jsmc=="超级管理员"){
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=showEditRoleDiv('"+list[i].jsbh+"','"+list[i].jsmc+"')>编辑</a>" +
						"<a class='operate'>删除</a></td>"));
			}else{
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=showEditRoleDiv('"+list[i].jsbh+"','"+list[i].jsmc+"')>编辑</a>" +
						"<a class='Operate' onclick='deleteRoleInfo("+list[i].jsbh+")'>删除</a></td>"));
			}
		}else{
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td>&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].jsmc)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].qxs)+"&nbsp;</td>"));
			//操作
			if(list[i].jsmc=="超级管理员"){
				newTr.append($("<td><a class='Operate' onclick=showEditRoleDiv('"+list[i].jsbh+"','"+list[i].jsmc+"')>编辑</a>" +
						"<a class='operate'>删除</a></td>"));
			}else{
				newTr.append($("<td><a class='Operate' onclick=showEditRoleDiv('"+list[i].jsbh+"','"+list[i].jsmc+"')>编辑</a>" +
						"<a class='Operate' onclick='deleteRoleInfo("+list[i].jsbh+")'>删除</a></td>"));
			}
		}
		$(".listTable").append(newTr);
	}
}
function deleteRoleInfo(jsbh){
	if(confirm("你确定删除该角色？")){
		$.ajax({
			url:'jsqxmanager/jsdelete',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'js.jsbh':jsbh
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
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='4' align='center'>暂无相关数据</td></tr>");
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
		showRoleInfo(actionName, pageNo);
	}
}
//显示新建角色框
function ShowAddRole(){
	showfullbg();
	$("#role_name").val("");
	$("#add_role_title").text("新建角色");
	showPermissionGroup();
	//showallPermission();
	$("#addnewRoleDiv").show();
}
function closeaddnewRoleDiv(){
	$(".addDiv,.perssion_left,perssion_right").remove();
	$("#qxallcheck").prop("checked", false);
	$("#addnewRoleDiv,#fullbg").hide();
}
function addNewRole(){
	$.ajax({
		
	});
}
/**
 * 显示权限组
 */
function showPermissionGroup(){
	$.ajax({
		url:'qxlistmanager/qxzlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$(".addDiv,.perssion_left,perssion_right").remove();
			var list=data.result.list;
			appendToqxGroup(list);
		}
	});
};
function appendToqxGroup(list){
	var  count=0;
	for(var i=0;i<list.length;i++){
		var jbqxlist = list[i].jbqxlist;
		if(jbqxlist.length>0){
		var  newDiv=$("<div class='addDiv' ></div>");
		var leftDiv =$("<div class='perssion_left' id='perleft_"+list[i].qxzbh+"' ></div>");
		var rightDiv =$("<div class='perssion_right' id='perright_"+list[i].qxzbh+"'></div>");
		
		 if(count==0){
			 newDiv=$("<div class='addDiv' style='border:0;'></div>");
			/*  leftDiv =$("<div class='perssion_left' style='border:0;background:#f9f9f9;' id='perleft_"+list[i].qxzbh+"' ></div>");
			  rightDiv =$("<div class='perssion_right' style='border:0;background:#f9f9f9;' id='perright_"+list[i].qxzbh+"'></div>");*/
		 }
	   
		
		leftDiv.append($("<input type='checkbox' name='qxzbox' id='add_qxzc_"+list[i].qxzbh+"' " +
				" class='qxzbox_style' value='"+list[i].qxzbh+"' onclick=linkPermission('"+list[i].qxzbh+"',this) />"));
		leftDiv.append($("<label >&nbsp;"+list[i].qxzmc+"</label>"));
		for(var j=0;j<jbqxlist.length;j++){
			var newLb=$("<label class='role_permission_label' '><input type='checkbox' name='permissionbox'" +
					" id='add_qxc_"+list[i].qxzbh+"_"+jbqxlist[j].qxbh+"'" +
					" value='"+jbqxlist[j].qxbh+"' " +
					" onclick=linkPermissionGroup('"+list[i].qxzbh+"',this) />&nbsp;&nbsp;"+jbqxlist[j].qxmc+"</label>");
			rightDiv.append(newLb);
		}
		newDiv.append(leftDiv);
		newDiv.append(rightDiv);
		$(".perssion_main").append(newDiv);
		count++;
		}
	}
}


/**
 * 显示所有权限
 */
function showallPermission(){
	$.ajax({
		url:'qxlistmanager/jbqxlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$(".permission_info").empty();
			var list=data.result.list;
			appendToTableAll(list);	
		}
	});
}

function appendToTableAll(list){
	for(var i=0;i<list.length;i++){
		var newLb=$("<label class='role_permission_label'><input type='checkbox' name='permissionbox' value='"+list[i].qxbh+"' " +
				" onclick=linkPermissionGroup('"+list[i].qxbh+"',this) />&nbsp;"+list[i].qxmc+"</label>");
		$(".permission_info").append(newLb);
	}
}
/**
 * 联动到权限
 * @param qxzbh
 * @param thisop
 */
function linkPermission(qxzbh,thisop){
	var divId = "add_qxc_"+qxzbh;
	if(thisop.checked==true){
		
		$("input[id^='"+divId+"_']").prop("checked", true);
	}
	if(thisop.checked==false){
		
		$("input[id^='"+divId+"_']").prop("checked", false);
	}
	
}
/**
 * 全选框
 */
function qxallcheckfunction(prename,thisop){
	var divId = "add_";
	if(thisop.checked==true){
		$("input[id^='"+divId+"']").prop("checked", true);
	}
	if(thisop.checked==false){
		$("input[id^='"+divId+"']").prop("checked", false);
	}
}

/**
 * 联动到权限组
 * @param qxbh
 * @param thisop
 */
function linkPermissionGroup(qxzbh,thisop){
	isQxzCheck(qxzbh);
}

/**
 * 初始化编辑checkbox
 */
function isQxzCheck(qxzbh){
	var ischeck = true;
	var divId = "add_qxc_"+qxzbh;
	$("input[id^='"+divId+"_']").each(function(index, element) {
		if(this.checked==false){
			ischeck=false;
		}
	});
	$("#add_qxzc_"+qxzbh).prop("checked", ischeck);
}

function addRole(){
	if($("#role_name").val()==""){
		alert("请输入角色名称");
		return;
	}
	if($("#role_name").val().length>100){
		alert("角色名称不能大于100位");
		return;
	}

	var permission=document.getElementsByName("permissionbox");
	var qxlist="";
	for(var j=0;j<permission.length;j++){
		if(permission[j].checked==true){
			if(qxlist==""){
				qxlist=permission[j].value;
			}else{
				qxlist+=","+permission[j].value;
			}
		}
	}
	if($("#add_role_title").text().indexOf("新建")>=0){
		$.ajax({
			url:'jsqxmanager/jssave',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'js.jsmc':$("#role_name").val(),
				'qxlist':qxlist
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
			url:'jsqxmanager/jsupdate',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'js.jsmc':$("#role_name").val(),
				'js.jsbh':editjsbh,
				'qxlist':qxlist
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





/*function textfocus(id){
	if($("#role option:selected").val()==null){
		$("#mm,#checkmm,#sjhm1").attr("readonly","readonly");
		//$("#determine,#cancel").onclick=null;
		//document.getElementById("determine").onclick=null;
	}else{
		$("#mm,#checkmm,#sjhm1").attr("readonly",false);
	}
}*/
var editjsbh="";
//显示编辑弹出框
function showEditRoleDiv(jsbh,jsmc){
	editjsbh=jsbh;
	showfullbg();
	$("#role_name").val(jsmc);
	showPermissionGroupSelected(jsbh);

	$("#add_role_title").text("编辑角色");
	$("#addnewRoleDiv").show();
}

//显示选中权限组
function showPermissionGroupSelected(jsbh){
	$.ajax({
		url:'qxlistmanager/qxzlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'jsbh':jsbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			$(".addDiv,.perssion_left,perssion_right").remove();
			var list=data.result.list;
			appendToSelectedGroup(list,jsbh);
		}
	});
}
function appendToSelectedGroup(list,jsbh){
	var  count=0;
	for(var i=0;i<list.length;i++){
		var jbqxlist = list[i].jbqxlist;
		if(jbqxlist.length>0){
		var  newDiv=$("<div class='addDiv' ></div>");
		var leftDiv =$("<div class='perssion_left' id='perleft_"+list[i].qxzbh+"' ></div>");
		var rightDiv =$("<div class='perssion_right' id='perright_"+list[i].qxzbh+"'></div>");
		
		 if(count==0){
			 newDiv=$("<div class='addDiv' style='border:0;'></div>");
		 }
	   
		
		leftDiv.append($("<input type='checkbox' name='qxzbox' id='add_qxzc_"+list[i].qxzbh+"' " +
				" class='qxzbox_style' value='"+list[i].qxzbh+"' onclick=linkPermission('"+list[i].qxzbh+"',this) />"));
		leftDiv.append($("<label >&nbsp;"+list[i].qxzmc+"</label>"));
		for(var j=0;j<jbqxlist.length;j++){
			var jsChoose = "";
			if(jbqxlist[j].isJsChoose=='1'){
				
				jsChoose = "checked='checked' ";
			}
			var newLb=$("<label class='role_permission_label' '><input type='checkbox' name='permissionbox' "+jsChoose+
					" id='add_qxc_"+list[i].qxzbh+"_"+jbqxlist[j].qxbh+"'" +
					" value='"+jbqxlist[j].qxbh+"' " +
					" onclick=linkPermissionGroup('"+list[i].qxzbh+"',this) />&nbsp;&nbsp;"+jbqxlist[j].qxmc+"</label>");
			rightDiv.append(newLb);
		}
		newDiv.append(leftDiv);
		newDiv.append(rightDiv);
		$(".perssion_main").append(newDiv);
		count++;
		//给权限组check初始化
		isQxzCheck(list[i].qxzbh);
		}
	}
}

//显示有选中的权限
function showallPermissionSelected(jsbh){
	
}


/**
 * 显示背景
 */
function showfullbg(){
	var bh = $(".common_c0").height(); 
	var bw = $(".top_u1").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
}
