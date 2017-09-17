var mark=new Array();
var qxzID="";//权限组编号
$(document).ready(function(){
	$("#top_text").text("系统设置");//top上的显示
	
	showPermissionGroup('qxlistmanager/qxzlist',1);
});
/**
 * 显示权限列表
 * @param actionName
 * @param selectedPage
 */
function showPermissionGroup(actionName,selectedPage){
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
				printPage(data.result.obj.pages, selectedPage, 'showPermissionGroup',
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
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].qxzmc)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].jbqxdesc)+"&nbsp;</td>"));
			//操作
			
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=showEditRoleGroupDiv('"+list[i].qxzbh+"','"+list[i].qxzmc+"')>编辑</a>" +
						"<a class='Operate' onclick='deletePermissionGroup("+list[i].qxzbh+")'>删除</a></td>"));
		
		}else{
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td>&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].qxzmc)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].jbqxdesc)+"&nbsp;</td>"));
			//操作
			
				newTr.append($("<td><a class='Operate' onclick=showEditRoleGroupDiv('"+list[i].qxzbh+"','"+list[i].qxzmc+"')>编辑</a>" +
						"<a class='Operate' onclick='deletePermissionGroup("+list[i].qxzbh+")'>删除</a></td>"));
		}
		$(".listTable").append(newTr);
	}
}
function PerGroupUlIsNull(){
	var newLi=$("<li style='text-align:center;'><label >暂无数据<label></li>");
	$("#department_left_select_child2").append(newLi);
}

//显示新建权限组框
function ShowAddRoleGroup(){
	showfullbg();
	$("#rolegroup_name").val("");
	$("#add_rolegroup_title").text("新建权限组");
	showallPermissionInfo();
	$("#addnewRoleGrouDiv").show();
}
function closeaddnewRoleGroupDiv(){
	$("#perssiongroup_main").empty();
	$("#qxallcheck").prop("checked", false);
	$("#addnewRoleGrouDiv,#fullbg").hide();
}
var editqxzbh="";

//显示编辑权限组框
function showEditRoleGroupDiv(qxzbh,qxzmc){
	editqxzbh=qxzbh;
	showfullbg();
	
	$("#rolegroup_name").val(qxzmc);
	$("#add_rolegroup_title").text("编辑权限组");
	showOwnPermissionInfo(qxzbh);
	$("#addnewRoleGrouDiv").show();
}


/**
 * 显示所有权限
 */
function showallPermissionInfo(){
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
			if (list == "") {
				perssionIsNull();
			} else {
			$("#perssiongroup_main").empty();
			var list=data.result.list;	
			appendToTableAll(list);
			}
		}
	});
}
function appendToTableAll(list){
	for(var i=0;i<list.length;i++){
		var newLb=$("<label class='permission_label'><input type='checkbox'  name='permissionbox' id='rolegroup_"+list[i].qxbh+"' value='"+list[i].qxbh+"'/>&nbsp;"+list[i].qxmc+"</label>");
		$("#perssiongroup_main").append(newLb);
	}

}

function TableIsNull(){
	newTr = $("<div class='addLb' style='width:100%;height:40px;line-height:40px;vertical-align:middle;text-align:center;'>暂无相关数据</div>");
	$("#allPermission").append(newTr);
}

/**
 * 显示此权限组的权限
 */
function showOwnPermissionInfo(qxzbh){
	
	$.ajax({
		url:'qxlistmanager/jbqxlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'qxzbh':qxzbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if (list == "") {
				perssionIsNull();
			} else {
			$("#perssiongroup_main").empty();
			var list=data.result.list;	
			appendToSelectedGroup(list,qxzbh);
			}
		}
	});
}

function appendToSelectedGroup(list,qxzbh){
		for(var i=0;i<list.length;i++){
			var checkdesc = "";
			if(list[i].isQxzChoose==1){
				checkdesc = "checked='checked' ";
			}
			var newLb=$("<label class='permission_label'><input type='checkbox'  "+checkdesc+" name='permissionbox' id='rolegroup_"+qxzbh+"_"+list[i].qxbh+"' value='"+list[i].qxbh+"'/>&nbsp;"+list[i].qxmc+"</label>");
			$("#perssiongroup_main").append(newLb);
		}
	
}

/**
 * 全选框
 */
function qxallcheckRoleGroup(prename,thisop){
//	var divId = "rolegroup_";
	if(thisop.checked==true){
		$("input[id^='"+prename+"']").prop("checked", true);
	}
	if(thisop.checked==false){
		$("input[id^='"+prename+"']").prop("checked", false);
	}
}


function appendToEditTable(list){
	for(var i=0;i<list.length;i++){
		if(list[i].choose==true){
			var newLb=$("<label class='permission_label'><input type='checkbox'  checked='checked' name='permissionbox' value='"+list[i].qxbh+"'/>&nbsp;"+list[i].qxmc+"</label>");
			$("#allPermission").append(newLb);
		}else{
			var newLb=$("<label class='permission_label'><input type='checkbox'  name='permissionbox' value='"+list[i].qxbh+"'/>&nbsp;"+list[i].qxmc+"</label>");
			$("#allPermission").append(newLb);
		}
	}
}
//保存新增或者编辑
function savePermission(qxzbh){
	var Linkqx="";
	var opts=document.getElementsByName("permissionbox");
	for(var i=0;i<opts.length;i++){
		if(opts[i].checked==true){
			if(Linkqx==""){
				Linkqx=opts[i].value;
			}else{
				Linkqx+=","+opts[i].value;
			}
		}
	}
	if($("#rolegroup_name").val()==""){
		alert("请输入权限组名称");
		return;
	}
	if($("#rolegroup_name").val().length>100){
		alert("权限组名称长度不能大于100位");
		return;
	}
	if($("#add_rolegroup_title").text().indexOf("编辑")>=0){//编辑
		$.ajax({
			url:'qxzmanager/qxtoqxz',
			type:'post',
			dataType:'json',
			data:{
				'qxz.qxzmc':$("#rolegroup_name").val(),
				'token':token,
				'qxz.qxzbh':editqxzbh,
				'qxlist':Linkqx
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
	}else{//新增
		$.ajax({
			url:'qxzmanager/qxzsave',
			type:'post',
			dataType:'json',
			data:{
				'qxz.qxzmc':$("#rolegroup_name").val(),
				'token':token,
				'qxlist':Linkqx
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
/**
 * 删除权限组
 */
function deletePermissionGroup(qxzbh){
	if(confirm("你确定要删除该权限组吗？")){
		$.ajax({
			url:'qxzmanager/qxzdelete',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'qxz.qxzbh':qxzbh
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
		showPermissionGroup(actionName, pageNo);
	}
}

function   perssionIsNull(){
	newTr = $("<div class='addLb' style='width:100%;height:40px;line-height:40px;vertical-align:middle;text-align:center;'>暂无可选权限</div>");
	$("#perssiongroup_main").append(newTr);
}