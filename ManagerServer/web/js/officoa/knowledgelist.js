var StatusByPermisssion;
$(document).ready(function() {
	SpecialPermisssion(35);
	
});
function DoSpecialPermisssion(status){
	if(status!=0){
		if(status<=1){
			$("#addButton").remove();
		}
		StatusByPermisssion=status;
		showKnowLedgeList('showKnowLedgedian',1);
	}
}
function showKnowLedgeList(actionName,selectedPage) {
	var kindID = $("#kindID").val();
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			"kindID" : kindID,
			"userid" : -1,
			'page':selectedPage
		},
		success : function(data) {

			if (data.pagemodel == null) {
				TableIsNull();
			} else {
				var listLength = data.pagemodel.recordsDate.length;
				var list = data.pagemodel;
				if (listLength == 0) {
					TableIsNull();
				} else {
					KnowLedge(list);
					gotoPageMethodName = "gotoPageNo";
					printPage(data.pagemodel.totalPage, selectedPage, 'showKnowLedgeList',
							actionName, gotoPageMethodName, data.pagemodel.total);
				}
			}
		
		}
	});

}
//数据为空
function TableIsNull(){
	$(".knowledgeclear").empty();
	$(".knowledgeclear").remove();
	newTr = $("<tr class='knowledgeclear'></tr>");
	newTr.append($("<td colspan=4>暂无相关数据</td>"));
	$("#Knowledge").append(newTr);
	
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value","");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showKnowLedgeList(actionName, pageNo);
	}
}

function KnowLedge(pagemodel) {
	var basePath = $("#basePath").val();
	$(".knowledgeclear").empty();
	$(".knowledgeclear").remove();
	var listLength = pagemodel.recordsDate.length;

	if (listLength == 0) {
		newTr = $("<tr class='knowledgeclear'></tr>");
		newTr.append($("<td colspan=5>暂无相关数据</td>"));
		$("#Knowledge").append(newTr);
	} else {
		for ( var i = 0; i < listLength; i++) {
			newTr = $("<tr class='knowledgeclear'></tr>");
			newTr.append($("<td>" + pagemodel.recordsDate[i].knowledgeName
					+ "</td>"));

			if (pagemodel.recordsDate[i].date == null) {
				newTr.append($("<td>无</td>"));
			} else {
				newTr.append($("<td>" + pagemodel.recordsDate[i].date.replace("T"," ")
						+ "</td>"));
			}
			if (pagemodel.recordsDate[i].usernames == null||pagemodel.recordsDate[i].usernames == "") {
				if (pagemodel.recordsDate[i].isLinkInfo == 0) {
					newTr.append($("<td>全部用户</td>"));
				}else if (pagemodel.recordsDate[i].isLinkInfo == 1) {
					newTr.append($("<td>内部用户</td>"));
				}else if (pagemodel.recordsDate[i].isLinkInfo == 2) {
					newTr.append($("<td>公众用户</td>"));
				}else{newTr.append($("<td>无</td>"));}
			} else {
				newTr.append($("<td>" + pagemodel.recordsDate[i].usernames
						+ "</td>"));
			}
			if((StatusByPermisssion==2&&pagemodel.recordsDate[i].isLinkInfo==1)||StatusByPermisssion==3){
				newTr
				.append($("<td><a class='operation' href='"
						+ basePath
						+ "page/officoa/NoticeDetail.jsp?knowledgeID="
						+ pagemodel.recordsDate[i].knowledgeID
						+ "' >查看</a>&nbsp;<a class='operation' href='"
						+ basePath
						+ "page/officoa/Knowledge_update.jsp?knowledgeID="
						+ pagemodel.recordsDate[i].knowledgeID
						+ "&kindID="
						+ pagemodel.recordsDate[i].partOfKind
						+ "&kindID1="
						+ $("#kindID").val()
						+ "' >编辑</a>&nbsp;<a class='operation' onclick='knowledgedelete("
						+ pagemodel.recordsDate[i].knowledgeID
						+ ")' >删除</a></td>"));
			}
			else{
				newTr
				.append($("<td><a class='operation' href='"
						+ basePath
						+ "page/officoa/NoticeDetail.jsp?knowledgeID="
						+ pagemodel.recordsDate[i].knowledgeID
						+ "' >查看</a></td>"));	}
		
			$("#Knowledge").append(newTr);

		}


	}

}



function selectKnowledgelist(actionName,selectedPage) {

	var kindID = $("#kindID").val();
	var content = document.getElementById('Content').value;
	if (/[~#^$@%&!*\s*]/.test(content)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
		$.ajax( {
			url : actionName,
			type : "post",
			dataType : "json",
			data : {
				'content' : content,
				'kindID' : kindID,
				"userid" : -1,
				'page':selectedPage
			},
			success : function(data) {

				if (data.pagemodel == null) {
					TableIsNull();
				} else {
					var listLength = data.pagemodel.recordsDate.length;
					var list = data.pagemodel;
					if (listLength == 0) {
						TableIsNull();
					} else {
						KnowLedge(list);// 跳转页码的方法名称	
						gotoPageMethodName = "gotoPageNo2";
						printPage(data.pagemodel.totalPage, selectedPage, 'selectKnowledgelist',
								actionName, gotoPageMethodName, data.pagemodel.total);
					}
				}
			}
		});

	
}
function gotoPageNo2(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value","");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		selectKnowledgelist(actionName, pageNo);
	}
}



function addkonwledge() {
	var kindID = $("#kindID").val();
	/*if (kindID == "-1") {
		///alert("请选择通知公告分类");
	} else */{
		var basePath = $("#basePath").val();
		str = basePath + "page/officoa/Knowledge_add.jsp?kindID=-1" //+ kindID;
		parent.location.href = str;
	}
}

function knowledgedelete(knowledgeID) {
	var kindID = $("#kindID").val();

	if (confirm('是否确定删除')) {
		$.ajax( {
			url : 'knowledgeDelete',
			type : "post",
			dataType : "json",
			data : {
				'knowledgeID' : knowledgeID
			},
			success : function(data) {
				var basePath = $("#basePath").val();
				str = basePath + "page/officoa/NoticeEdit.jsp?kindID="
						+ kindID;
				window.location.href = str;
			}
		});

	}
}
