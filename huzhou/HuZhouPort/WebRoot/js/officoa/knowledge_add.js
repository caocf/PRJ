var userList = new Array();// 选择的人名
var userIdList = new Array();// 选择的ID
$(document).ready(function() {
	SpecialPermisssion(35);
	creatTree();
	SelectedUser();
});
function DoSpecialPermisssion(status) {
	var objSelect = document.getElementById("isLinkInfo");
	if (status != 0) {
		if (status == 1) {
			objSelect.options.length = 0;
		} else if (status == 2) {
			objSelect.remove(2);
			objSelect.remove(0);
			$("#tr_user").css("display", "");
		}
	} else {
		objSelect.options.length = 0;
	}
}
// 点击知识库
function firstDepartment1(s){
	addknowledgeid="-1";
	addknowledgename="知识库";
	addknowledgeparentid="0";
	addknowledgeparentname="0";

	//alert(kindID);
	$("#kindID1").attr("value" ,"-1");

	s.style.color="#3f9cd7";
	$('#department_tree a').css("color","black");
	$("#user_right").attr("src",$("#basePath").val()+"page/officoa/Knowledgelist.jsp?kindID=-1");
}
function knowledgeAdd() {
	//alert("请选择通知公告分类");
	var knowledgeName = document.getElementById('knowledgeName').value;
	var knowledgeIndex = document.getElementById('knowledgeIndex').value;
	var partOfKind = document.getElementById('kindID1').value;


	var isLink = $('input:radio[name="isLink"]:checked').val();
	var isLinkInfo = $('#isLinkInfo option:selected').val();
	var usernames=$("#listOfUserName").text();
	if(usernames=="港航内部全部用户") usernames="";

	var knowledgeContent = document.getElementById('knowledgeContent').value;
	var lu = $("#listOfUser").val();
	if ($("#isLinkInfo").val() != 1) {
		lu = "";
	}

	if (partOfKind == ""||partOfKind==null) {
		alert("请选择通知公告分类");return;
	}else if (knowledgeName == "") {
		alert("请填写通知公告名称");return;
	}else if (knowledgeContent == "") {
		alert("请填写通知公告内容");return;
	} else if (knowledgeName.length > 1000) {
		alert("通知公告名称不能超过1000个字");return;
	} else if (knowledgeIndex.length > 100) {
		alert("通知公告摘要不能超过100个字");return;
	} else if (knowledgeContent.length > 10000) {
		alert("通知公告内容不能超过10000个字");return;
	}else if(isLink==1){
		knowledgeContent = knowledgeContent.match(/http:\/\/.+/); 
		if (knowledgeContent == null){
			alert('你输入的URL无效'); 
			return false;
		}else{
			knowledgeAdd2();	
		}
		return;
	}
	knowledgeAdd2();
}
function knowledgeAdd2(){
	var knowledgeName = document.getElementById('knowledgeName').value;
	var knowledgeIndex = document.getElementById('knowledgeIndex').value;
	var partOfKind = document.getElementById('kindID1').value;
	var isLink = $('input:radio[name="isLink"]:checked').val();
	var isLinkInfo = $('#isLinkInfo option:selected').val();
	var usernames=$("#listOfUserName").text();
	if(usernames=="港航内部全部用户") usernames="";

	var knowledgeContent = document.getElementById('knowledgeContent').value;
	var lu = $("#listOfUser").val();
	if ($("#isLinkInfo").val() != 1) {
		lu = "";
	}

	$.ajax( {
		url : 'knowledgeAdd',
		type : "post",
		dataType : "json",
		data : {
			"knowledge.knowledgeName" : knowledgeName,
			"knowledge.knowledgeIndex" : knowledgeIndex,
			"knowledge.partOfKind" : partOfKind,
			"knowledge.isLink" : isLink,
			"knowledge.isLinkInfo" : isLinkInfo,
			"knowledge.knowledgeContent" : knowledgeContent,
			"knowledge.userids" : $("#listOfUser").val(),
			'knowledge.usernames': usernames
		},
		success : function(data) {
			alert("提交成功");
			var basePath = $("#basePath").val()
			str = basePath + "page/officoa/Knowledge_tree.jsp";
			window.location.href = str;

		}
	});
}
function SelectObject() {
	if ($("#isLinkInfo").val() == 1) {
		$("#tr_user").css("display", "");
	} else {
		$("#tr_user").css("display", "none");
	}
}
