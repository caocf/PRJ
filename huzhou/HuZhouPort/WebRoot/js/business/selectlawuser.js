var LawUserId="";
var LawUserName="";
var kind=0;
function showDialog() {
	$("#cresteTree").show();
	$("#cresteTree").dialog( {
		title : '选择第二执法人',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});

}
function showDialog(k) {
	$("#cresteTree").show();
	kind=k;
	if(kind==1){
	$("#cresteTree").dialog( {
		title : '选择第二执法人',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
    }else{
    	$("#cresteTree").dialog( {
    		title : '选择审核人',
    		collapsible : false,
    		minimizable : false,
    		maximizable : false,
    		resizable : false
    	});
    }
	
	
}
function creatTree() {
	$.ajax( {
		url : 'showDepartmentList',
		type : "post",
		dataType : "json",
		success : function(data) {
			CreatDepartment(data.list, -1);
		}
	});

}
function CreatDepartment(list, parentId) {
	newTree = $("#tree");
	for ( var i = 0; i < list.length; i++) {
		if (parentId == list[i].partOfDepartmentId) {
			if (checkChile(list, list[i].departmentId) == 0) {
				newTr = $("<li class='li"
						+ list[i].departmentId
						+ "'>"
						+ "<img src='image/common/minus.png' style='cursor:default'/>"
						+ "<a onclick=showUserList(this,'"
						+ list[i].departmentId + "')>" + list[i].departmentName
						+ "</a></li>");
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
						+ "<a onclick=showUserList(this,'"
						+ list[i].departmentId + "')>" + list[i].departmentName
						+ "</a></li>");
				newTu = $("<ul id='opt" + list[i].departmentId
						+ "' style='display:none'></ul>");

				$(newTr).append(newTu);
				if (list[i].partOfDepartmentId == -1) {
					newTree.append(newTr);
				} else {
					$('#opt' + list[i].partOfDepartmentId).append(newTr);

				}
				CreatDepartment(list, list[i].departmentId);
			}

		}
	}

}
// 检查子部门
function checkChile(list, did) {
	var cd = 0;
	for ( var i = 0; i < list.length; i++) {
		if (list[i].partOfDepartmentId == did)
			cd += 1;
	}
	if (cd == 0)
		return cd;
	else
		return 1;

}
// 打开或关闭下级树
function openNextDepartment(id) {
	eval("var   ul=opt" + id);
	eval("var   img=img" + id);
	ul.style.display = ul.style.display != "none" ? "none" : "block";
	img.src = ul.style.display != "none" ? "image/common/minus.png"
			: "image/common/plus.png";
}
function hidenDepartmentTree() {
	$("#cresteTree").dialog("close");
}
function changeUserOfDepartment() {
	if (LawUserName == "") {
		if(kind==1){
		alert("请选择第二执法人！");
		}else{
			alert("请选择审核人！");	
		}
		
		return false;
	} else {
		if(kind==1){
		$("#seconduser").attr("value",LawUserName);
		$("#seconduserId").attr("value", LawUserId);
		$("#cresteTree").dialog("close");
		$("#secondusererr").text("");
	    }else{
	    	$("#threeuser").attr("value",LawUserName);
			$("#threeuserId").attr("value", LawUserId);
			$("#cresteTree").dialog("close");
			$("#threeusererr").text("");
	    }
	}
}

function FindByName() {
	$("label").css("color", "");
	$.ajax( {
				url : 'FindByName',
				type : "post",
				dataType : "json",
				data : {
					'user.name' : $("#searchName").val()
				},
				success : function(data) {
					$(".dialog_alluser").empty();
					if(data.list.length==0) alert("没有符合的数据");
					for ( var i = 0; i < data.list.length; i++) {
						if (data.list[i].userId != $("#userId").val()) {
							var newLi = $("<div><a onclick=chooseUser(this,'"
									+ data.list[i].userId+ "','"
									+ data.list[i].name+ "') >"
									+ "<img src='image/schedule/person_head.png' class='person_head'/><label  id='u"
									+ data.list[i].userId + "' >"
									+ data.list[i].name + "</label></a></div>");
							var newUl = $(".dialog_alluser");
							$(newUl).append(newLi);
						}
					}
				}
			});
}
function chooseUser(s, userId, name) {
	LawUserId = userId;
	LawUserName = name;
	$('.dialog_alluser div').css("background", "#fff");
	s.parentNode.style.background = "url(image/schedule/selecting_background.png)";
	
}

function showUserList(s, departmentID) {
	$('#tree li a').css("color", "black");
	s.style.color = "#3f9cd7";
	$
			.ajax( {
				url : 'UserListByDepartment',
				type : "post",
				dataType : "json",
				data : {
					'user.partOfDepartment' : departmentID
				},
				success : function(data) {
					$(".dialog_alluser").empty();
					for ( var i = 0; i < data.list.length; i++) {
						if (data.list[i].userId != $("#userId").val()) {
							var newLi = $("<div><a onclick=chooseUser(this,'"
									+ data.list[i].userId+ "','"
									+ data.list[i].name+ "') >"
									+ "<img src='image/schedule/person_head.png' class='person_head'/>"
									+ "<label  id='u" + data.list[i].userId
									+ "' >" + data.list[i].name
									+ "</label></a></div>");
							var newUl = $(".dialog_alluser");
							$(newUl).append(newLi);
						}
					}
				}
			});
}
