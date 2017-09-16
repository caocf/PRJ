$(document).ready(function()
{
	//InitData();
});
//编辑框初始化
KindEditor.ready(function(K)
{
	var height1=300;
	if(document.documentElement.clientHeight>640){
		height1=document.documentElement.clientHeight-400;
	}
	var editor1 = K.create('textarea[name="content1"]', {
		cssPath : '../../kindeditor/plugins/code/prettify.css',
		uploadJson: $('#basePath').val()+'/upload.do',
		fileManagerJson: $('#basePath').val()+'/manager.do',
		filterMode: true,
		allowFileManager: true,
		resizeType : 0,
		width : '100%',
		height : height1,
		resizeType : 0,
		afterCreate : function() {
			var self = this;
			K.ctrl(document, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
			K.ctrl(self.edit.doc, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
		}
	});
	prettyPrint();
	editor=editor1;
});

//更新通知
function updateN()
{
	editor.sync();
	//alert($("#editor_id").val());
	$.ajax( {
		url : 'UpDateNotice',
		type : "post",
		dataType : "json",
		data : {
			"ID" : $("#newsid").text(),
			"title" : $("#newstitle").val(),
			'content': $("#editor_id").val()
		},
		success : function(data)
		{
			alert("更新成功");
		}
	});

}

//发送通知
function pushNotice()
{
	editor.sync();
	$.ajax(
		{
			url : 'SendMSAll',
			type : "post",
			dataType : "json",
			data : {
				"type" : $("#type").val(),
				"title" : $("#newstitle").val(),
				'content': $("#editor_id").val(),
				'region': $("#region").val()
			},
			success : function(data)
			{
				alert("更新成功");
			}
		}
	);
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
			}  else {
				newTr.append($("<td>" + pagemodel.recordsDate[i].usernames
						+ "</td>"));
			}

			
			if (pagemodel.recordsDate[i].isLink == "1") {
				newTr.append($("<td><a class='operation' href='"+ pagemodel.recordsDate[i].knowledgeContent+"' target='_blank' >查看</a></td>"));
				}else{
					newTr.append($("<td><a class='operation' href='"+ basePath
							+ "page/noticeforsee/NoticeDetail.jsp?knowledgeID="+ pagemodel.recordsDate[i].knowledgeID+ "' >查看</a></td>"));
				}
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
			"userid" : $("#LoginUser").val(),
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

