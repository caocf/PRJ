$(document).ready(function() {
	if($("#parentId").val()=="-1"){
		$("#knowledgekind_kindindexname").val("通知公告");
	}
	showDepartmentTree();// 显示部门树
	
});

//显示部门树
function showDepartmentTree() {
	
	$.ajax( {
		url : 'showKnowLedge',
		type : "post",
		dataType : "json",
		success : function(data) {
		CreatDepartment(data.pagemodel.recordsDate,-1);
		}
	});
}

function CreatDepartment(list,parentId){
	newTree = $("#department_tree");
	for ( var i = 0; i < list.length; i++) {
		if(parentId==list[i].kindIndex){
			if($("#parentId").val()==list[i].kindID){
				$("#knowledgekind_kindindexname").val(list[i].kindName);
			}
		if($("#kindID").val()==list[i].kindID){
			$("#knowledgekind_kindname").val(list[i].kindName);
			continue;
		}
		
		if(checkChile(list,list[i].kindID)==0)
		{
			newTr = $("<li class='li" + list[i].kindID + "'>" +
					"<img src='image/common/minus.png' style='cursor:default'/>"
					+ "<a onclick=showUserListByDepartmentId(this,'" + list[i].kindID+ "','"+ list[i].kindName+ "')>"
					+ list[i].kindName + "</a></li>");
			if (list[i].kindIndex == -1) {
				newTree.append(newTr);
			} else {
				$('#opt' + list[i].kindIndex).append(newTr);
			}
		}
		else{
		newTr = $("<li class='li" + list[i].kindID + "'>" +
				"<img src='image/common/plus.png' onclick='openNextDepartment("+list[i].kindID+")' " +
						"id='img"+list[i].kindID+"' />"
				+ "<a onclick=showUserListByDepartmentId(this,'" + list[i].kindID+ "','"+ list[i].kindName+ "')>"
				+ list[i].kindName + "</a></li>");
		newTu = $("<ul id='opt" + list[i].kindID + "' style='display:none'></ul>");

		$(newTr).append(newTu);
		if (list[i].kindIndex == -1) {
			newTree.append(newTr);
		} else {
			$('#opt' + list[i].kindIndex).append(newTr);

		}
		CreatDepartment(list,list[i].kindID);
		}
		}
	
	}
}
//检查子部门
function checkChile(list,did){
	var cd=0;
	for ( var i = 0; i < list.length; i++) {
		if(list[i].kindIndex==did)
			cd+=1;		
	}	
	if(cd==0) return cd;
	else return 1;
	
}
//打开或关闭下级树
function openNextDepartment(id){
    eval("var   ul=opt"+id);   
    eval("var   img=img"+id);    
    ul.style.display=ul.style.display!="none"?"none":"block";
    img.src=ul.style.display!="none"?"image/common/minus.png":"image/common/plus.png";
}


//按部门显示用户列表
function showUserListByDepartmentId(s,kindID,kindName) {
	//alert(kindID);
	$("#kindID1").attr("value" ,kindID);
	$("#knowledgekind_kindindexname").attr("value" ,kindName);

	$("#bigtree").css("color","black");
	$('#department_tree li a').css("color","black");
	s.style.color="#3f9cd7";
	$("#user_right").attr("src",$("#basePath").val()+"page/officoa/NoticeEdit.jsp?kindID="+kindID);
}



function DateIsNull(value) {
	returnValue = "";
	if (value == null || value == "null") {

		return returnValue;
	} else {
		return value;
	}
}

// 关闭、展开击知识库
var ico=1;
function close_tree(){
	if(ico==1){
	$("#department_tree").css("display","none");
	$(".treeNode").attr("src","image/common/plus.png");  
	ico=0;
	}
	
	
	else{
		$('#department_tree').css('display','block');
		$(".treeNode").attr("src","image/common/minus.png");  
		ico=1;
	}
	}
//点击知识库
function firstDepartment(s){

	$("#parentId").attr("value" ,"-1");
	$("#knowledgekind_kindindexname").attr("value" ,"通知公告");
	s.style.color="#3f9cd7";
	$('#department_tree a').css("color","black");
}


function agree(){
	if($("#kindID").val()=="null"){
		addknowledge();
	}else{
		updateknowledge();
	}
}

function addknowledge(){
	var kindName=$('#knowledgekind_kindname').val();
	var kindIndex=$('#parentId').val();
	if(kindName==""){
		alert("请填写分类名称");
	}else{
	  if(kindName.length>100){
		 alert("分类名称不能超过100个字"); 
	  }else{
		$.ajax({
			url:'addknowledge',
			type : "post",
			dataType : "json",
			data: { "knowledgekind.kindName": kindName,
				"knowledgekind.kindIndex": kindIndex
			},
			success : function(data) {
			if(!data.hasSameName){
				 alert("提交成功");
			     parent.location.reload();  
			}else{
				 alert("类名不能重复！");
			}
	        
	}
		});
    }
	}
}
function updateknowledge(){
	var kindID=$("#kindID").val();
	var kindName=document.getElementById('knowledgekind_kindname').value;
	var kindIndex=document.getElementById('parentId').value;
	if(kindID==kindIndex){
		alert("所属分类不能是自己");
	}else if(kindName==""){
		alert("请填写分类名称");
	}else if(kindName.length>100){
		alert("分类名称不能超过100个字"); 
	}else{
		$.ajax({
			url:'updateknowledge',
			type : "post",
			dataType : "json",
			data: { 
				"knowledgekind.kindID": kindID,
				"knowledgekind.kindName": kindName,
				"knowledgekind.kindIndex": kindIndex
			},
			success : function(data) {
				if(!data.hasSameName){
	        alert("提交成功");
	        parent.location.reload();  
				}else{
					 alert("类名不能重复！");
				}
	}
		});
	}
}

