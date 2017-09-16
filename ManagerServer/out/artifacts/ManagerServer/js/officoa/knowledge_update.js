$(document).ready(function() {
	SpecialPermisssion(35);
	KnowLedgeShow();
});
function DoSpecialPermisssion(status) {
	var objSelect = document.getElementById("isLinkInfo");
	if (status != 0) {
		if (status == 1) {
			objSelect.options.length = 0;
		} else if (status == 2) {
			objSelect.remove(2);
			objSelect.remove(0);
		}
	} else {
		objSelect.options.length = 0;
	}
}
function KnowLedgeShow() {
	var knowledgeID = $("#knowledgeID").val();
	$.ajax( {
		url : 'KnowLedgeView',
		type : "post",
		dataType : "json",
		data : {
			"knowledgeID" : knowledgeID
		},
		success : function(data) {
			KnowLedgeView(data.knowledge);
		}
	});
}

function KnowLedgeView(knowledge) {

	document.getElementById('knowledgeName').value = knowledge.knowledgeName;
	document.getElementById('knowledgeIndex').value = knowledge.knowledgeIndex;
	document.getElementById('knowledgeContent').value = knowledge.knowledgeContent;

	if (knowledge.isLink == "1") {
		$("#isLink1").attr("checked", "checked");
	} else {
		$("#isLink2").attr("checked", "checked");
	}

}

function knowledgeUpdate() {

	var knowledgeID = document.getElementById('knowledgeID').value;
	var knowledgeName = document.getElementById('knowledgeName').value;
	var knowledgeIndex = document.getElementById('knowledgeIndex').value;
	var partOfKind = document.getElementById('kindID').value;
	var isLink = $('input:radio[name="isLink"]:checked').val();
	var knowledgeContent = document.getElementById('knowledgeContent').value;

	if (knowledgeName == "") {
		alert("请填写通知公告名称");
	} /*else if (knowledgeIndex == "") {
		alert("请填写通知公告摘要");
	}*/ else if(knowledgeContent==""){ 
		alert("请填写通知公告内容") 
	}else if (knowledgeName.length > 1000) {
		alert("通知公告名称不能超过1000个字");
	} else if (knowledgeIndex.length > 100) {
		alert("通知公告摘要不能超过100个字");
	} else if (knowledgeContent.length > 10000) {
		alert("通知公告内容不能超过10000个字");
	} else if(isLink==1) {
		knowledgeContent = knowledgeContent.match(/http:\/\/.+/); 
		if (knowledgeContent == null){
			alert('你输入的URL无效'); 
			return false;
		}else{
			 knowledgeUpdate2();
		}
		return;
	}
	 knowledgeUpdate2();
}
function knowledgeUpdate2(){
	var knowledgeID = document.getElementById('knowledgeID').value;
	var knowledgeName = document.getElementById('knowledgeName').value;
	var knowledgeIndex = document.getElementById('knowledgeIndex').value;
	var partOfKind = document.getElementById('kindID').value;
	var isLink = $('input:radio[name="isLink"]:checked').val();
	var knowledgeContent = document.getElementById('knowledgeContent').value;
		$.ajax( {
			url : 'knowledgeUpdate',
			type : "post",
			dataType : "json",
			data : {
				"knowledge.knowledgeID" : knowledgeID,
				"knowledge.knowledgeName" : knowledgeName,
				"knowledge.knowledgeIndex" : knowledgeIndex,
				"knowledge.partOfKind" : partOfKind,
				"knowledge.isLink" : isLink,
				"knowledge.knowledgeContent" : knowledgeContent
			},
			success : function(data) {

				alert("提交成功");
				var basePath = $("#basePath").val()
				str = basePath + "page/officoa/NoticeEdit.jsp?kindID="+ $("#kindID1").val();
				window.location.href = str;

			}
		});
}