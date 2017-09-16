// 焦点
$(document).ready(function() {
	// 得到焦点
		$("#userName").focus(function() {
			var che = userNameCheck();
			$("#userNameerr").text("");
		});
		// 失去焦点
		$("#userName").blur(function() {
			userNameCheck();
		});

		// password得到焦点
		$("#password").focus(function() {
			var che = passwordCheck();

			$("#passworderr").text("");
		});
		// password失去焦点
		$("#password").blur(function() {
			passwordCheck();
		});

		// confirmPassword得到焦点
		$("#confirmPassword").focus(function() {
			var che = confirmPasswordCheck();

			$("#confirmPassworderr").text("");
		});
		// confirmPassword失去焦点
		$("#confirmPassword").blur(function() {
			confirmPasswordCheck();
		});

		// name得到焦点
		$("#realname").focus(function() {
			var che = nameCheck();

			$("#nameerr").text("");
		});
		// name失去焦点
		$("#realname").blur(function() {
			nameCheck();
		});
		// userStatus得到焦点
		$("#StatusType").focus(function() {
			var che;
			if ($("#StatusType").val() == 1)
				che = userStatusCheck();
			if ($("#StatusType").val() == 0)
				che = userStatusCheck2();

			$("#userStatuserr").text("");
		});
		// userStatus失去焦点
		$("#StatusType").blur(function() {
			if ($("#StatusType").val() == 1)
				userStatusCheck();
			if ($("#StatusType").val() == 0)
				userStatusCheck2();
		});
		/*// tel得到焦点
		$("#tel").focus(function() {
			var che = telCheck();

			$("#telerr").text("");
		});
		// tel失去焦点
		$("#tel").blur(function() {
			telCheck();
		});
		// email得到焦点
		$("#email").focus(function() {
			var che = emailCheck();

			$("#emailerr").text("");
		});
		// email失去焦点
		$("#email").blur(function() {
			emailCheck();
		});*/
		// partOfPost得到焦点
		$("#partOfPost").focus(function() {
			var che = partOfPostCheck();

			$("#partOfPosterr").text("");
		});
		// partOfPost失去焦点
		$("#partOfPost").blur(function() {
			partOfPostCheck();
		});

		/*// lawId得到焦点
		$("#lawId").focus(function() {
			var che = lawIdCheck();

			$("#lawIderr").text("");
		});
		// lawId失去焦点
		$("#lawId").blur(function() {
			lawIdCheck();
		});
*/
		// partOfRole得到焦点
		$("#partOfRole").focus(function() {
			var che = partOfRoleCheck();

			$("#partOfRoleerr").text("");
		});
		// partOfRole失去焦点
		$("#partOfRole").blur(function() {
			partOfRoleCheck();
		});
		// partOfDepartment得到焦点
		$("#partOfDepartment").focus(function() {
			var che = partOfDepartmentCheck();

			$("#partOfDepartmenterr").text("");
		});
		// partOfDepartment失去焦点
		$("#partOfDepartment").blur(function() {
			partOfDepartmentCheck();
		});
	});

/**
 * 检验登录名
 * 
 * @return {loginname}
 */
function userNameCheck() {
	var ret = true;
	ret = emptyChk("#userName", "#userNameerr", "用户名不能为空");
	if (ret == true) {
		ret = valueLength("#userName", "#userNameerr", "2", "12",
				"用户名不能小于2位大于12位");
	}
	if (ret == true) {
		ret = specialcharacter("#userName", "#userNameerr", "不能出现特殊字符");
	}
	return ret;
}

/**
 * 检验密码
 * 
 * @return {password}
 */
function passwordCheck() {
	var ret = true;
	ret = emptyChk("#password", "#passworderr", "密码不能为空.");
	if (ret == true) {
		ret = valueLength("#password", "#passworderr", "6", "20", "密码是6-20位");
	}
	if (ret == true) {
		ret = regularChk('#password', "#passworderr", "密码只能包含_ ,英文字母,数字.");
	}
	return ret;
}

/**
 * 检验确认密码
 * 
 * @return {confirmPassword}
 */
function confirmPasswordCheck() {
	var ret = true;
	ret = emptyChk("#confirmPassword", "#confirmPassworderr", "确认密码不能为空");
	if (ret == true) {
		ret = chkConfirmPwd("#password", "#confirmPassword",
				"#confirmPassworderr", "两次密码不一样");
	}
	return ret;
}
/**
 * 检验真实姓名
 * 
 * @return {realname}
 */
function nameCheck() {
	var ret = true;
	ret = emptyChk("#realname", "#nameerr", "真实姓名不能为空");
	if (ret == true) {
		ret = valueLength("#realname", "#nameerr", "2", "12", "长度2-20位");
	}
	return ret;
}

/**
 * 
 * 
 * @return {phone}
 */
function userStatusCheck() {
	var ret = true;
	ret = emptyChk("#Status1 :checked", "#userStatuserr", "人员在职情况不能为空");
	return ret;
}
function userStatusCheck2() {
	var ret = true;
	ret = emptyChk("#Status2 :checked", "#userStatuserr", "人员离职情况不能为空");
	return ret;
}
/**
 * 检验固定电话
 * 
 * @return {phone}
 */
function telCheck() {
	var ret = true;
	ret = emptyChk("#tel", "#telerr", "电话不能为空");
	if (ret == true) {
		ret = valueLength("#tel", "#telerr", "2", "12", "长度2-12位");
	}
	if (ret == true) {
		ret = isNumber("#tel", "#telerr", "只能输入数字");
	}
	return ret;
}
/**
 * 检验邮箱
 * 
 * @return {email}
 */
function emailCheck() {
	var ret = true;
	ret = emptyChk("#email", "#emailerr", "邮箱不能为空");
	if (ret == true) {
		ret = isEmail("#email", "#emailerr", "邮箱格式不正确");
	}

	return ret;
}
/**
 * 检验职务
 * 
 * @return {email}
 */
function partOfPostCheck() {
	var ret = true;
	ret = emptyChk("#partOfPost", "#partOfPosterr", "职务不能为空");
	if (ret == true) {
	}
	return ret;
}

function lawIdCheck() {
	var ret = true;
	ret = emptyChk("#lawId", "#lawIderr", "执法证编号不能为空");
	if (ret == true) {
		ret = valueLength("#lawId", "#lawIderr", "2", "12", "长度2-12位");
	}
	return ret;
}
/**
 * 检验部门
 * 
 * @return {email}
 */
function partOfDepartmentCheck() {
	var ret = true;
	ret = emptyChk("#partOfDepartment", "#partOfDepartmenterr", "部门名称不能为空");
	if (ret == true) {
	}
	return ret;
}
/**
 * 检验角色
 * 
 * @return {email}
 */
function partOfRoleCheck() {
	var ret = true;
	ret = emptyChk("#partOfRole", "#partOfRoleerr", "职务不能为空");
	if (ret == true) {
	}
	return ret;
}

function validSubmit() {
	if (!userNameCheck()) {
		return false;
	}
	if (!passwordCheck()) {

		return false;
	}
	if (!confirmPasswordCheck()) {

		return false;
	}
	if ($("#StatusType").val() == 1)
		if (!userStatusCheck()) {

			return false;
		}
	if ($("#StatusType").val() == 0)
		if (!userStatusCheck2()) {

			return false;
		}
	if (!partOfRoleCheck()) {

		return false;
	}
	if (!nameCheck()) {
		return false;
	}
	if (!partOfPostCheck()) {

		return false;
	}
	/*if (!lawIdCheck()) {

		return false;
	}
	if (!telCheck()) {

		return false;
	}
	if (!emailCheck()) {

		return false;
	}
*/
	return true;
}
