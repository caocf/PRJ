$(document).ready(function() {
	showRoleList("showRoleList");// 显示左侧角色导航
	});

// 显示左侧角色导航
function showRoleList(ationName) {
	$.ajax( {
		url : ationName,
		type : "post",
		dataType : "json",
		success : function(data) {
			RoleList(data.list);
		}
	});
}
function RoleList(list) {
	$(".addTrLi").remove();
	$(".addTrLi").empty();
	for ( var i = 0; i < list.length; i++) {
		newTr = $("<li class='addTrLi'>"
				+ "<a onclick=showRoleInfo(this,'" + list[i].roleId
				+ "','" + list[i].roleName + "')>" + list[i].roleName
				+ "</a></li>");
		newTrUl = $("<ul></ul>");
		$(newTr).append(newTrUl);
		$("#role_menu").append(newTr);
	}
	$("#role_menu li a:first").css("background-color", "#dce9f2");
}

// 显示角色权限
function showRoleInfo(id, roleId, roleName) {
	$("#role_menu li a").css("background-color", "#f6f7fb");
	if (id)
		id.style.backgroundColor = "#dce9f2";
	$("#roleId").attr("value",roleId);
	$("#roleName").attr("value",roleName);
	showRolePermission(roleId);
}

var ico = 1;
function close_menu() {
	if (ico == 1) {
		$("#role_menu").css("display", "none");
		$(".img1").attr("src", "image/common/plus.png");
		ico = 0;
	} else {
		$('#role_menu').css('display', 'block');
		$(".img1").attr("src", "image/common/minus.png");
		ico = 1;
	}
}
