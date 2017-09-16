$(document).ready(function() {
	//showAllRoleInfo();//角色列表
	//showDepartmentMemuInfo();//选择的部门树
	//showAllPostInfo();//职务
	//showAllStatusInfo();//状态
    updateUserInfor($("#UserId").val());
   // selectChange();
	
	});


function updateUserInfor(userId){
	$.ajax({
		url : 'showUserById',
		type : "post",
		dataType : "json",
		data : {'userId':userId},
		success : function(data) {

		AddressSee(data);	
			
/*			
		$("#userId").attr("value",data.list[0][0].userId);
		$("#userName").attr("value",data.list[0][0].userName);
		$("#password").attr("value", data.list[0][0].password);
		$("#confirmPassword").attr("value", data.list[0][0].password);
		$("#partOfDepartmentId").attr("value", data.list[0][0].partOfDepartment);
		$("#partOfDepartmentName").attr("value", data.list[0][2].departmentName);
		$("#realname").attr("value", data.list[0][0].name);
		$("#partOfRole").attr("value", data.list[0][0].partOfRole);	
		$("#lawId").attr("value", data.list[0][0].lawId);
		$("#tel").attr("value", data.list[0][0].tel);
		$("#email").attr("value", data.list[0][0].email);
		$("#partOfPost").attr("value", data.list[0][0].partOfPost);
		$("#StatusType").attr("value", data.list[0][4].statusType);
		if(data.list[0][4].statusType==1)
		{
		$("#Status1").css("display","");
		$("#Status2").css("display","none");
		}
	else {
		$("#Status1").css("display","none");
		$("#Status2").css("display","");
		}
		$(".userStatus"+data.list[0][0].userStatus).attr("checked","checked");*/
	}
	});
	
	
}
 function AddressSee(data){
	 newTr = $("<tr class='addresssee'></tr>");
		newTr.append($("<td>用户名：</td><td>" + data.list[0][0].userName
				+ "</td>"));
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		newTr = $("<tr class='addresssee'></tr>");
		newTr.append($("<td>用户部门：</td><td>" + data.list[0][2].departmentName
				+ "</td>"));
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		
		newTr = $("<tr class='addresssee'></tr>");
		newTr.append($("<td>用户角色：</td><td>" + data.list[0][3].roleName
				+ "</td>"));
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		
		newTr = $("<tr class='addresssee'></tr>");
		newTr.append($("<td>真实姓名：</td><td>" + data.list[0][0].name
				+ "</td>"));
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		newTr = $("<tr class='addresssee'></tr>");
		newTr.append($("<td>职务：</td><td>" + data.list[0][1].postName
				+ "</td>"));
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		newTr = $("<tr class='addresssee'></tr>");
		newTr.append($("<td>执法证编号：</td><td>" + data.list[0][0].lawId
				+ "</td>"));
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		
		
		newTr = $("<tr class='addresssee'></tr>");
		if(data.list[0][4].statusType=="1"){
			newTr.append($("<td>状态：</td><td>在职      " + data.list[0][4].statusName
					+ "</td>"));	
		}else{
		
		newTr.append($("<td>状态：</td><td>离职     " + data.list[0][4].statusName
				+ "</td>"));
		}
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		newTr = $("<tr class='addresssee'></tr>");
		newTr.append($("<td>联系电话：</td><td>" + data.list[0][0].tel
				+ "</td>"));
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		newTr = $("<tr class='addresssee'></tr>");
		newTr.append($("<td>电子邮件：</td><td>" + data.list[0][0].email
				+ "</td>"));
		// newTr.append($("<td>" +leaveOrOtKindbean.leaveOrOtUser+"</td>"));
		$("#addresssee").append(newTr);
		
		
 }









function showDepartmentMemuInfo() {

	$.ajax( {
		url : 'showDepartmentList',
		type : "post",
		dataType : "json",
		success : function(data) {
			showDepartmentInfoAppend(data.list, -1);
		}
	});
}
// 将数据显示到页面
function showDepartmentInfoAppend(list, parentId) {
	newTree = $("#tree");
	for ( var i = 0; i < list.length; i++) {
		if (parentId == list[i].partOfDepartmentId) {
			if (checkChile(list, list[i].departmentId) == 0) {
				newTr = $("<li class='li" + list[i].departmentId + "'>"
						+ "<img src='image/common/minus.png' />"
						+ "<a onclick=chooseDepartment(this,'"
						+ list[i].departmentId + "','" + list[i].departmentName
						+ "')>" + list[i].departmentName + "</a></li>");
				if (list[i].partOfDepartmentId == -1) {
					newTree.append(newTr);
				} else {
					$('#opt' + list[i].partOfDepartmentId).append(newTr);

				}
			} else {
				newTr = $("<li class='li"
						+ list[i].departmentId
						+ "'>"
						+ "<img src='image/common/plus.png' onclick='openNextDepartment("
						+ list[i].departmentId + ")' " + "id='img"
						+ list[i].departmentId + "' />"
						+ "<a onclick=chooseDepartment(this,'"
						+ list[i].departmentId + "','" + list[i].departmentName
						+ "')>" + list[i].departmentName + "</a></li>");
				newTu = $("<ul id='opt" + list[i].departmentId
						+ "' style='display:none'></ul>");

				$(newTr).append(newTu);
				if (list[i].partOfDepartmentId == -1) {
					newTree.append(newTr);
				} else {
					$('#opt' + list[i].partOfDepartmentId).append(newTr);

				}
				showDepartmentInfoAppend(list, list[i].departmentId);
			}

		}
	}
}
//检查子部门-侧边导航栏
function checkChile(list,did){
	var cd=0;
	for ( var i = 0; i < list.length; i++) {
		if(list[i].partOfDepartmentId==did)
			cd+=1;		
	}	
	if(cd==0) return cd;
	else return 1;
	
}
//关闭部门树
function chooseDepartment(s, id, name) {
	$("#tree li a").css("color", "black");
	s.style.color = "#3f9cd7";
	$("#hideDepartmentId").attr("value", id);
	$("#hideDepartmentName").attr("value", name);
}
//打开或关闭下级树
function openNextDepartment(id){
	eval("var   ul=opt"+id);   
    eval("var   img=img"+id);    
    ul.style.display=ul.style.display!="none"?"none":"block";   
    img.src=ul.style.display!="none"?"image/common/minus.png":"image/common/plus.png";   
}
//选择部门弹出框
function openDepartmentTree2(){
	$("#cresteTree").show();
	$("#cresteTree").dialog( {
		title : '选择部门',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
//确定选择部门
function changeUserOfDepartment(){
	if($("#hideDepartmentId").val()=="")
	{alert("请先选择部门！");}
	else{
		$("#tree li a").css("color","black");
		$("#partOfDepartmentId").attr("value",$("#hideDepartmentId").val());
		$("#partOfDepartmentName").attr("value",$("#hideDepartmentName").val());
		$("#cresteTree").dialog("close");
	}
}
//关闭部门树
function hidenDepartmentTree() {
	$("#tree li a").css("color","black");
	$("#cresteTree").dialog("close");
}
//角色列表
function showAllRoleInfo() {
	$.ajax( {
		url : 'showRoleList',
		type : "post",
		dataType : "json",
		data : {
			defaultRole : $("#defaultRole").val()
		},
		success : function(data) {
			for(var i=0;i<data.list.length;i++)
			document.getElementById("partOfRole").options.add(new Option(data.list[i].roleName, data.list[i].roleId));
		}
	});
}
//职务
function showAllPostInfo(partOfPostid) {
	$.ajax( {
		url : 'showPostInfo',
		type : "post",
		dataType : "json",
		success : function(data) {
			partOfPostName=data.list[partOfPostid-1].postName	
			alert("data.list[partOfPostid-1].postName="+data.list[partOfPostid-1].postName);
		for(var i=0;i<data.list.length;i++)
			document.getElementById("partOfPost").options.add(new Option(data.list[i].postName, data.list[i].postId));
		}
	});
}
//状态
function showAllStatusInfo() {
	$.ajax( {
		url : 'showStatusInfo',
		type : "post",
		dataType : "json",
		data : {
			statusId : $("#statusId").val()
		},
		success : function(data) {
		var Statue1="",Statue2="";
			for(var i=0;i<data.list.length;i++)
			{
				if(data.list[i].statusType==1)
					Statue1=Statue1+"<input type='radio' value='"+data.list[i].statusId+"' name='userStatus' class='userStatus"+data.list[i].statusId+"' onclick='onchecked()'/>"+data.list[i].statusName;
				else Statue2=Statue2+"<input type='radio' value='"+data.list[i].statusId+"' name='userStatus' class='userStatus"+data.list[i].statusId+"' onclick='onchecked()'/>"+data.list[i].statusName;
					
			}
			$("#Status1").html(Statue1);
			$("#Status2").html(Statue2);
		}
	});
}

function onchecked(){
 $("#StatusType").focus();	
}
function selectChange(){
	$("#StatusType").change(function(){
		if($("#StatusType").val()==1)
			{
			$("#Status1").css("display","");
			$("#Status2").css("display","none");
			}
		else {
			$("#Status1").css("display","none");
			$("#Status2").css("display","");	
			}
	});
}
