$(document).ready(function() {
	showKindList();
});


//显示右侧权限类型导航
function showKindList(){
	$.ajax({
		url:"showPermissionKindList",
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
		newTh = $("<div class='content_kind'>" +list[i].permissionKindName+ "</div>");
		$("#permission_list").append(newTh);
		newTh = $("<div class='kindid"+list[i].permissionKindId+"'></div>");
		$("#permission_list").append(newTh);
		showPermissionList(list[i].permissionKindId);
	}

	 setTimeout("showRolePermission()",500); 
}

//显示右侧权限类型导航
function showPermissionList(permissionKindId){
	$.ajax({
		url:"showPermissionList",
		type : "post",
		dataType : "json",
		data:{'permission.partOfKind':permissionKindId},
		success : function(data) {
		PermissionList(data.list);		
	}
	});
}

function PermissionList(list){
	for ( var i = 0; i < list.length; i++) {
		var a=-1;
		var psId=list[i].permissionId;
		for ( var j = 0; j < Golval_pid.length; j++){
			if(psId==Golval_pid[j]){
				a=j;
				break;
			}
		}
		if(a==-1){
			newTd = $("<div class='permissionItem'><input type='checkbox' name='permissionId'" +
					" value='"+psId+":1' class='"+psId+"'/>" 
					+list[i].permissionName+ "</div>");	
		}else{
			newTd = $("<div class='permissionItem'>" +list[i].permissionName+ "(<input type='radio' name='"+psId+"'" +
					" value='"+psId+":0' checked='checked'/>"+Golval_pid_prompt[a][0]+"<input type='radio' name='"+psId+"'" +
					" value='"+psId+":1' />"+Golval_pid_prompt[a][1]+"<input type='radio' name='"+psId+"'" +
					" value='"+psId+":2' />"+Golval_pid_prompt[a][2]+"<input type='radio' name='"+psId+"'" +
					" value='"+psId+":3' />"+Golval_pid_prompt[a][3]+")</div>");
		}
	
		newTr = $("div .kindid"+list[i].partOfKind);
		$(newTr).append(newTd);

		}
}

//显示角色权限
function showRolePermission(){
	$
	.ajax( {
		url : 'searchRolePermissionByRoleId',
		type : "post",
		dataType : "json",
		data : {
			'roleId' : $("#roleId").val()
		},
		success : function(data) {
			RolePermission(data.list);
		}
	});
}
function RolePermission(list){
	$("#roleName").attr("value",list[0][1].roleName);
	for ( var i = 0; i < list.length; i++) {
		var a=0;
		var psId=list[i][0].permissionId;
		for ( var j = 0; j < Golval_pid.length; j++){
			if(psId==Golval_pid[j]){
				var opts=document.getElementsByName(Golval_pid[j]);
				opts[list[i][0].status].checked=true;
				a=1;
				break;
			}
		}
		if(a==0){
			if(list[i][0].status==1){
				$("input."+psId).attr("checked","checked");
			}
			else{
				$("input."+psId).attr("checked","");
			}
		}
	}
}



//修改角色
function updata_role(){	
	if ($("#roleName").val() == "")
		alert("角色名不能为空！");
	else {
		var opts=document.getElementsByName("permissionId");
		 var linkStr="";
		for(var i=0;i<opts.length;i++){
	            if(opts[i].checked==true){
	            	if(linkStr==""){
	            		linkStr=opts[i].value;
	            	}else{
	            		linkStr+=";"+opts[i].value;
	            	}
	                
	            }
	       }
		for(var i=0;i<Golval_pid.length;i++){
        	if(linkStr==""){
        		linkStr=$('input[name="'+Golval_pid[i]+'"]:checked').val();
        	}else{
        		linkStr+=";"+$('input[name="'+Golval_pid[i]+'"]:checked').val();
        	}
            
    }
		$.ajax( {
			url : 'updateRole',
			type : "post",
			dataType : "json",
			data : {
			'roleId':$("#roleId").val(),
			'roleName':$("#roleName").val(),
			'listPermission':linkStr
		},
			success : function(data) {
				alert("修改成功！");
				window.history.go(-1);
			},
		error : function(XMLHttpRequest) {
			alert($(".error", XMLHttpRequest.responseText).text());
		}
		});

	}	
}
//全选
var flag = 0;
function chooseAll() {
	if (flag == 0) {
		$(":checkbox").attr("checked", "checked");
		flag = 1;
	} else {
		$(":checkbox").attr("checked", "");
		flag = 0;
	}

}
