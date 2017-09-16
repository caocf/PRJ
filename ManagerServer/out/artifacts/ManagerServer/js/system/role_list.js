$(document).ready(function() {
	showKindList();
});

//删除角色
function del_role() {	
	if ($("#roleId").val() == 1)
		alert("超级用户不能删除！");
	else {
		if (confirm('确定要删除吗？')) {
		$.ajax( {
			url : 'deleteRole',
			type : "post",
			dataType : "json",
			data : {
				'roleId' : $("#roleId").val()
			},
			success : function(data) {
				if(data.list== null)
					{ alert("删除成功！");
					window.location.reload();
					}
				else 
				{ var sName="";
					for(var i=0;i<data.list.length;i++)
					{
						sName=sName+data.list[i].Name;
					}
					alert("不能删除该角色！\n拥有该角色的人员或职位:"+sName+"。");
				}
			}
		});
	}}
}


//显示右侧权限类型导航
function showKindList() {
	$.ajax( {
		url : "showPermissionKindList",
		type : "post",
		dataType : "json",
		success : function(data) {
			KindList(data.list);
		}
	});
}
function KindList(list){
	$("tr").empty(); 
	$("tr").remove(); 
	for ( var i = 0; i < list.length; i++) {
		newTh = $("<div class='content_kind'>" + list[i].permissionKindName+ "</div>");
		$("#permission_list").append(newTh);
		newTh = $("<div class='kindid" + list[i].permissionKindId + "'></div>");
		$("#permission_list").append(newTh);
		showPermissionList(list[i].permissionKindId);
	}
	 setTimeout("showRolePermission(1)",500);
}

//显示右侧权限类型导航
function showPermissionList(permissionKindId) {
	$.ajax( {
		url : "showPermissionList",
		type : "post",
		dataType : "json",
		data : {
			'permission.partOfKind' : permissionKindId
		},
		success : function(data) {
			PermissionList(data.list);
		}
	});
}

function PermissionList(list){
	for ( var i = 0; i < list.length; i++) {
		var a=0;
		for ( var j = 0; j < Golval_pid.length; j++){
			if(list[i].permissionId==Golval_pid[j]){
				a=1;
				break;
			}
		}
		if(a==0){
			newTd = $("<div class='permissionItem'><input type='checkbox' name='list["+i+"].status' " +
					"value='1' class='"+list[i].permissionId+"'/>" +list[i].permissionName+ "</div>");
		}else{
			newTd = $("<div class='permissionItem'>" +list[i].permissionName+ "<label class='"+list[i].permissionId+"'></label></div>");
			
		}
		newTr = $("div .kindid"+list[i].partOfKind);
		$(newTr).append(newTd);
		$(":checkbox").attr("disabled","disabled");

	}
}

//显示角色权限
function showRolePermission(roleId){
	$(":checkbox").attr("checked","");
	$.ajax( {
		url : 'searchRolePermissionByRoleId',
		type : "post",
		dataType : "json",
		data : {
			'roleId' : roleId
		},
		success : function(data) {
			RolePermission(data.list);
		}
	});
}
function RolePermission(list){
	$("#roleName").attr("value",list[0][1].roleName);
	for ( var i = 0; i < list.length; i++) {
		var a=-1;
		var psId=list[i][0].permissionId;
		for ( var j = 0; j < Golval_pid.length; j++){
			if(psId==Golval_pid[j]){
				a=j;
				break;
			}
		}
		if(a==-1){
			if(list[i][0].status==1){
				$("input."+psId).attr("checked","checked");
			}
			else{
				$("input."+psId).attr("checked","");
			}
		}else{
			if(list[i][0].status==0){
				$("label."+psId).text(Golval_pid_prompt[a][0]);
			}
			else if(list[i][0].status==1){
				$("label."+psId).text(Golval_pid_prompt[a][1]);
			}else if(list[i][0].status==2){
				$("label."+psId).text(Golval_pid_prompt[a][2]);
			}else if(list[i][0].status==3){
				$("label."+psId).text(Golval_pid_prompt[a][3]);
			}
		}
		
	}
}


function show_update(){
	if($("#roleId").val()==1) alert("超级用户不能编辑！");
	else
	window.location.href=$("#basePath").val()+"page/system/role_update.jsp?roleId="+$("#roleId").val();
}