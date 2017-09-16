$(document).ready(function() {
	$("#bigtree").css("color","#3f9cd7");
	$("#bigtree").css("height",window.innerHeight+"px");
	showDepartmentTree();//显示部门树	
	addknowledgeid="-1"; //选择的id 
	addknowledgename="通知公告";//选择的name
	addknowledgeparentid="0"; //父节点id
	addknowledgeparentname="0"; //父节点name
	});
//var height=0;

//显示部门树
function showDepartmentTree() {
	
	$.ajax( {
		url : 'showKnowLedge',
		type : "post",
		dataType : "json",
		success : function(data) {
			CreatDepartment(data.pagemodel.recordsDate,-1,"通知公告");
		}
	});
}

function CreatDepartment(list,parentId,parentName){
	newTree = $("#department_tree");
	for ( var i = 0; i < list.length; i++) {
	if(parentId==list[i].kindIndex){
		if(checkChile(list,list[i].kindID)==0)
		{
			newTr = $("<li class='li" + list[i].kindID + "'>" +
					"<img src='image/common/minus.png' style='cursor:default'/>"
					+ "<a onclick=showUserListByDepartmentId(this,'" + list[i].kindID+ "','"+ list[i].kindName+ "','"+parentId+"','"+parentName+"')>"
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
				+ "<a onclick=showUserListByDepartmentId(this,'" + list[i].kindID+ "','"+ list[i].kindName+"','"+parentId+"','"+parentName+"')>"
				+ list[i].kindName + "</a></li>");
		newTu = $("<ul id='opt" + list[i].kindID + "' style='display:none'></ul>");

		$(newTr).append(newTu);
		if (list[i].kindIndex == -1) {
			newTree.append(newTr);
		} else {
			$('#opt' + list[i].kindIndex).append(newTr);

		}
		CreatDepartment(list,list[i].kindID,list[i].kindName);
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

var addknowledgeid;
var addknowledgename;
var addknowledgeparentid;
var addknowledgeparentname;
//按部门显示用户列表
function showUserListByDepartmentId(s,kindID,kindName,parentId,parentName){
	addknowledgeid=kindID;
	addknowledgename=kindName;
	addknowledgeparentid=parentId;
	addknowledgeparentname=parentName;
	$("#bigtree").css("color","black");
	$('#department_tree li a').css("color","black");
	s.style.color="#3f9cd7";
	$("#user_right").attr("src",$("#basePath").val()+"page/noticeforsee/NoticeEdit.jsp?kindID="+kindID);

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
	addknowledgeid="-1";
	addknowledgename="知识库";
	addknowledgeparentid="0";
	addknowledgeparentname="0";
	
	s.style.color="#3f9cd7";
	$('#department_tree a').css("color","black");
	$("#user_right").attr("src",$("#basePath").val()+"page/noticeforsee/NoticeEdit.jsp?kindID=-1");
}

