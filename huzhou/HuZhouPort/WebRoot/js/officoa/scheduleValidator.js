// 焦点
$(document).ready(function() {
	// 得到焦点
		$("#sName").focus(function() {
			var che = userNameCheck();
			$("#eventNameerr").text("");
		});
		// 失去焦点
		$("#sName").blur(function() {
			userNameCheck();
		});

		/*$("#sTime").focus(function() {
			var che = passwordCheck();

			$("#eventTimeerr").text("");
		});
		// 失去焦点
		$("#sTime").blur(function() {
			passwordCheck();
		});
*/
		$("#sContent").focus(function() {
			var che = confirmPasswordCheck();

			$("#eventContenterr").text("");
		});
		$("#sContent").blur(function() {
			confirmPasswordCheck();
		});

	});
function sTimechange(){
	if(passwordCheck()){
		$("#eventTimeerr").text("");
	}	
}

function userNameCheck() {
	var ret = true;
	ret = emptyChk("#sName", "#eventNameerr", "日程名称不能为空");
	if (ret == true) {
		ret = valueLength("#sName", "#eventNameerr", "1", "12",
				"日程名称不能大于12位");
	}
	if (ret == true) {
		ret = specialcharacter("#sName", "#eventNameerr", "不能出现特殊字符");
	}
	return ret;
}


function passwordCheck() {
	var ret = true;
	ret = emptyChk("#sTime", "#eventTimeerr", "时间不能为空.");
	
	return ret;
}


function confirmPasswordCheck() {
	var ret = true;
	ret = valueLength("#sContent", "#eventContenterr", "0", "100","内容不能超过100个字。");
	
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
	return true;
}

//选择人
function  ChooseUserOver(id){
	id.src="image/operation/user_add_hover.png";
}
function  ChooseUserOut(id){
	id.src="image/operation/user_add_normal.png";
}
