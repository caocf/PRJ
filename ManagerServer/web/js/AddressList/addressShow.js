$(document).ready(function() {
	$("#organization").css("color","#3f9cd7");
	showDepartmentTree();//显示部门树		
	});
	//alert("addd");

//显示部门树
function showDepartmentTree() {
	//alert("aaaa");
	
	$.ajax( {
		url : 'showDepartmentList',
		type : "post",
		dataType : "json",
		success : function(data) {
			CreatDepartment(data.list,-1);
		}
	});
}

function CreatDepartment(list,parentId){
	newTree = $("#department_tree");
	for ( var i = 0; i < list.length; i++) {
	if(parentId==list[i].partOfDepartmentId){
		if(checkChile(list,list[i].departmentId)==0)
		{
			newTr = $("<li class='li" + list[i].departmentId + "'>" +
					"<img src='image/common/minus.png' style='cursor:default'/>"
					+ "<a onclick=showUserListByDepartmentId(this,'" + list[i].departmentId+ "')>"
					+ list[i].departmentName + "</a></li>");
			if (list[i].partOfDepartmentId == -1) {
				newTree.append(newTr);
			} else {
				$('#opt' + list[i].partOfDepartmentId).append(newTr);
			}
		}
		else{
		newTr = $("<li class='li" + list[i].departmentId + "'>" +
				"<img src='image/common/plus.png' onclick='openNextDepartment("+list[i].departmentId+")' " +
						"id='img"+list[i].departmentId+"' />"
				+ "<a onclick=showUserListByDepartmentId(this,'" + list[i].departmentId+ "')>"
				+ list[i].departmentName + "</a></li>");
		newTu = $("<ul id='opt" + list[i].departmentId + "' style='display:none'></ul>");

		$(newTr).append(newTu);
		if (list[i].partOfDepartmentId == -1) {
			newTree.append(newTr);
		} else {
			$('#opt' + list[i].partOfDepartmentId).append(newTr);

		}
		CreatDepartment(list,list[i].departmentId);
		}
		
	}
	}
}
//检查子部门
function checkChile(list,did){
	var cd=0;
	for ( var i = 0; i < list.length; i++) {
		if(list[i].partOfDepartmentId==did)
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
function showUserListByDepartmentId(s,departmentID) {
	$("#organization").css("color","black");
	$('#department_tree li a').css("color","black");
	s.style.color="#3f9cd7";
	$("#user_right").attr("src",$("#basePath").val()+"page/AddressList/addressList.jsp?departmentID="+departmentID);
}
function DateIsNull(value) {
	returnValue = "";
	if (value == null || value == "null") {

		return returnValue;
	} else {
		return value;
	}
}

// 关闭、展开湖州港航管理局
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
//点击湖州港航管理局
function firstDepartment(s){
	s.style.color="#3f9cd7";
	$('#department_tree a').css("color","black");
	$("#user_right").attr("src",$("#basePath").val()+"page/AddressList/addressList.jsp");
}