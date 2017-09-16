// 焦点
$(document).ready(function() {
	// 得到焦点
		$("#department_name").focus(function() {
			var che = department_nameCheck();
			
			$("#department_nameerr").text("");
		});
		// 失去焦点
		$("#department_name").blur(function() {
			department_nameCheck();
		});
	});

/**
 * 检验科室名称
 * 
 * @return {loginname}
 */
function department_nameCheck() {
	var ret = true;
	ret = emptyChk("#department_name", "#department_nameerr", "部门名称不能为空");
	return ret;
}


function validSubmit() {
	if (!department_nameCheck()) {
		return false;
	}
	return true;
}
