$(document).ready(function() {
	$("#organization").css("color","#3f9cd7");
	showDepartmentTree();// 显示部门菜单
	});

// 显示部门菜单
function showDepartmentTree() {
	$.ajax( {
		url : 'showDepartmentList',
		type : "post",
		dataType : "json",
		success : function(data) {
			$("#department_tree").empty();
			CreatDepartment(data.list, -1);
		}
	});
}
function CreatDepartment(list, parentId) {
	newTree = $("#department_tree");
	for ( var i = 0; i < list.length; i++) {
		if (parentId == list[i].partOfDepartmentId) {
			if (checkChile(list, list[i].departmentId) == 0) {
				newTr = $("<li class='li"
						+ list[i].departmentId
						+ "'>"
						+ "<img src='image/common/minus.png' style='cursor:default'/>"
						+ "<a onclick=showDepartmentById(this,'"
						+ list[i].departmentId + "','"
						+ list[i].departmentName + "','"
						+ list[i].partOfDepartmentId + "')>" + list[i].departmentName
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
						+ "<img src='image/common/plus.png' onclick='openDepartmentChild("
						+ list[i].departmentId + ")' " + "id='img"
						+ list[i].departmentId + "' />"
						+ "<a onclick=showDepartmentById(this,'"
						+ list[i].departmentId + "','"
						+ list[i].departmentName + "','"
						+ list[i].partOfDepartmentId + "')>" + list[i].departmentName
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
function openDepartmentChild(id) {
	eval("var   ul=opt" + id);
	eval("var   img=img" + id);
	ul.style.display = ul.style.display != "none" ? "none" : "block";
	img.src = ul.style.display != "none" ? "image/common/minus.png"
			: "image/common/plus.png";
}
// 点击部门菜单
function showDepartmentById(s, departmentID, departmentName, partOfDepartmentId) {
	hideDepartment();
	$(".show_first").css("display", "none");
	$(".show_second").css("display", "block");
	$("#organization").css("color", "black");
	$('#department_tree a').css("color", "black");
	s.style.color = "#3f9cd7";
	$("#show_departmentname").text(departmentName);
	$("#show_departmentId").attr("value", departmentID);

	if (partOfDepartmentId == -1) {

		$("#show_partOfDepartmentId").attr("value", "-1");
		$("#show_partofdepartmentName").text("湖州港航管理局");
	} else
		findDepartmentNameByChild(partOfDepartmentId);
}
// 查找上级部门
function findDepartmentNameByChild(partOfDepartmentId) {
	$.ajax( {
		url : 'searchParentDepartmentName',
		type : "post",
		dataType : "json",
		data : {
			'department.departmentId' : partOfDepartmentId
		},
		success : function(data) {
			$("#show_partOfDepartmentId").attr("value",
					data.list[0].departmentId);
			$("#show_partofdepartmentName").text(
					data.list[0].departmentName);
		}
	});
}

// 保存信息
function editionDepartmentInfo() {
	var actionName;
	var tishi;// 提示语句
	if ($("#departmentId").val() == "") {
		actionName = "addDepartmentInfo";
		tishi = 1;
	} else {
		actionName = "updateDepartmentInfo";
		tishi = 2;
	}
	if (!validSubmit()) {
		return;
	}
	var partOfDepartmentId = $("#partOfDepartmentId").val();
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			"departmentId" : $("#departmentId").val(),
			"departmentName" : $("#department_name").val(),
			"partOfDepartmentId" : partOfDepartmentId
		},
		success : function(data) {
			if (data.msg == null) {
				if (tishi == 1) {
					alert("新建成功！");
				}
				if (tishi == 2) {
					alert("编辑成功！");
				}
				hideDepartment();
				showDepartmentTree();// 刷新部门菜单
		$("#show_departmentId").attr("value", $("#departmentId").val());
		$("#show_departmentname").text($("#department_name").val());
		$("#show_partOfDepartmentId").attr("value",
				$("#partOfDepartmentId").val());
		$("#show_partofdepartmentName").text(
				$("#partOfDepartmentName").val());

		$("#department_name").attr("value", "");
		$("#departmentId").attr("value", "");
		$("#partOfDepartmentId").attr("value", "");
		$("#partOfDepartmentName").attr("value", "");

		$("#department_nameerr").html("");
	} else {
		alert(data.msg);
	}
}
	});
}

/**
 * 撤销
 */
function deleteDepartmentById() {

	if ($("#show_departmentId").val() == "") {
		alert("请选择要删除的部门");
		return;
	}
	if (confirm('确定要撤销该部门吗？')) {
		$.ajax( {
			url : "deleteDepartmentInfo",
			type : "post",
			dataType : "json",
			data : {
				"departmentId" : $("#show_departmentId").val()
			},
			success : function(data) {
				alert("撤销成功")
				window.location.reload();
			},
			error : function(XMLHttpRequest) {
				alert("不能删除，该部门有用户。");
			}
		});
	}

}

function updateDepartment() {
	if ($("#show_departmentId").val() == "-1") {
		alert("湖州港航管理局不能编辑");
		return;
	}
	showDepartmentBySelect();// 选择的部门树
	showEditDepartmentInfo();
	$("#departmentId").attr("value", $("#show_departmentId").val());
	$("#department_name").attr("value", $("#show_departmentname").text());
	$("#partOfDepartmentId").attr("value", $("#show_partOfDepartmentId").val());
	$("#partOfDepartmentName").attr("value",
			$("#show_partofdepartmentName").text());
}

function addDepartment() {
	showDepartmentBySelect();// 选择的部门树
	$("#partOfDepartmentId").attr("value", $("#show_departmentId").val());
	$("#partOfDepartmentName").attr("value", $("#show_departmentname").text());
	showEditDepartmentInfo();
}

function showEditDepartmentInfo() {
	$("#add_departmentDiv").show();
	$("#u2_right").hide();
}

/**
 * 隐藏编辑面板
 */
function hideDepartment() {
	$("#add_departmentDiv").hide();
	$("#u2_right").show();
}

// 关闭、展开
var ico = 1;
function close_tree() {
	$("#partOfDepartmentName").attr("value", "");
	$('#department_tree a').css("color", "black");
	if (ico == 1) {
		$("#department_tree").css("display", "none");
		$(".img1").attr("src", "image/common/plus.png");
		ico = 0;
	} else {
		$('#department_tree').css('display', 'block');
		$(".img1").attr("src", "image/common/minus.png");
		ico = 1;
	}
}
// 点击湖州港航管理局
function firstDepartment(s) {
	s.style.color = "#3f9cd7";
	hideDepartment();
	$('#department_tree a').css("color", "black");
	$(".show_second").css("display", "none");
	$(".show_first").css("display", "block");
	$("#show_departmentname").text("湖州港航管理局");
	$("#show_departmentId").attr("value", "-1");
	$('ul li').css("color", "black");
}

function showDepartmentBySelect() {

	$.ajax( {
		url : 'showDepartmentList',
		type : "post",
		dataType : "json",
		success : function(data) {
			$("#tree").empty();
			creatDepartmentBySelect(data.list,-1);
		}
	});
}
// 将数据显示到页面
function creatDepartmentBySelect(list, parentId) {
	
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
				$('#opti' + list[i].partOfDepartmentId).append(newTr);

			}
		} else {
			newTr = $("<li class='li"
					+ list[i].departmentId
					+ "'>"
					+ "<img src='image/common/plus.png' onclick='openNextDepartment("
					+ list[i].departmentId + ")' " + "id='imgi"
					+ list[i].departmentId + "' />"
					+ "<a onclick=chooseDepartment(this,'"
					+ list[i].departmentId + "','" + list[i].departmentName
					+ "')>" + list[i].departmentName + "</a></li>");
			newTu = $("<ul id='opti" + list[i].departmentId
					+ "' style='display:none'></ul>");

			$(newTr).append(newTu);
			if (list[i].partOfDepartmentId == -1) {
				newTree.append(newTr);
			} else {
				$('#opti' + list[i].partOfDepartmentId).append(newTr);

			}
			creatDepartmentBySelect(list, list[i].departmentId) ;
		}
		
		}
	}
}

// 关闭部门树
function chooseDepartment(s, id, name) {
	$("#tree li a").css("color", "black");
	s.style.color = "#3f9cd7";
	$("#partOfDepartmentId").attr("value", id);
	$("#partOfDepartmentName").attr("value", name);
}
//打开或关闭下级树
function openNextDepartment(id) {
	eval("var   ul=opti" + id);
	eval("var   img=imgi" + id);
	ul.style.display = ul.style.display != "none" ? "none" : "block";
	img.src = ul.style.display != "none" ? "image/common/minus.png"
			: "image/common/plus.png";
}
