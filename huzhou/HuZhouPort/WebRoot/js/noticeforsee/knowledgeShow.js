$(document).ready(function() {
	KnowLedgeShow();
});
function KnowLedgeShow() {
	var knowledgeID = $("#knowledgeID").val()
	$.ajax( {
		url : 'KnowLedgeView',
		type : "post",
		dataType : "json",
		data : {
			" knowledgeID" : knowledgeID
		},
		success : function(data) {

			KnowLedgeView(data.knowledge);
		}
	});
}

function KnowLedgeView(knowledge) {
	if(knowledge.date!=null&&knowledge.date!="null"&&knowledge.date!=""){
		$("#date").val(knowledge.date.replace("T"," "));
	}else{
		$("#date").val("无");
	}
	if (knowledge.isLinkInfo == 1) {
		if(knowledge.usernames!=null&&knowledge.usernames!="null"&&knowledge.usernames!="")
		$("#usernames").text(knowledge.usernames);
		$("#usernames").text("内部用户");
	} else if (knowledge.isLinkInfo == 2) {
		$("#usernames").text("公众用户");
	} else {
		$("#usernames").text("全部用户");
	}
	$("#knowledgeName").val(knowledge.knowledgeName);
	$("#knowledgeIndex").val(knowledge.knowledgeIndex);
	$("#knowledgeContent").val(knowledge.knowledgeContent);

	if (knowledge.isLink == "1") {
		$("#isLink1").attr("checked", "checked");
	} else {
		$("#isLink2").attr("checked", "checked");
	}

}