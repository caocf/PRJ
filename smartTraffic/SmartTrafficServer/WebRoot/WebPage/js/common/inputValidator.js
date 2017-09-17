/**
 * valueId是为获取文本框的值准备的,errorId是为错误信息提示准备的， msg是信息提示，minimum最小值，biggest是最大值
 * 
 * @param {Object}
 *            valueId
 * @param {Object}
 *            errorId
 * @param {Object}
 *            msg
 * @return {TypeName}
 */

// 判断输入是否为空
function emptyChk(valueId, errorId, msg) {
	var check = $$.trim($$(valueId).val());
	if (check == "") {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;
}
// 检验是否为大小写英文字母、数字、下划线
function regularChk(valueId, errorId, msg) {
	var check = $$.trim($$(valueId).val());
	var regu = /^\w+$/;
	if (check == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (!regu.test(check)) {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;
}
// 第二次验证
function chkConfirmPwd(valueId, confirmId, errorId, msg) {
	var check = $$.trim($$(valueId).val());
	var confirmcheck = $$.trim($$(confirmId).val());
	if (check == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (check != confirmcheck) {
		errorStye(confirmId, errorId, msg);
		return false;
	}
	rightStye(confirmId, errorId);
	return true;
}
// 检验是否为汉字
function isChinese(valueId, errorId, msg) {
	var check = $$.trim($$(valueId).val());
	var reg = /^[\u4e00-\u9fa5]{1,10}$/;
	if (check == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (!reg.test(check)) {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;
}
// 检验是否为大小写英文字母、汉字、数字、下划线
function isChinOrEngOrNum(valueId, errorId, msg) {
	var check = $$.trim($$(valueId).val());
	var reg = /^([\u0391-\uFFE5]|[a-za-z0-9A-Z_])*$/;
	if (check == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (!reg.test(check)) {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;
}
// 判断长度
function valueLength(valueId, errorId, minimum, biggest, msg) {
	var check = $$.trim($$(valueId).val());
	if (check.length < minimum || check.length > biggest) {
		errorStye(valueId, errorId, msg);
		return false;
	} else {

		rightStye(valueId, errorId);
		return true;
	}

}

/**
 * 邮箱验证
 * 
 * @param {Object}
 *            idOfInput
 * @param {Object}
 *            msg
 * @return {TypeName}
 */
function isEmail(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (!reg.test(str)) {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;

}
/**
 * 检查是否选择
 * 
 * @param {Object}
 *            valueId
 * @param {Object}
 *            errorId
 * @param {Object}
 *            msg
 * @return {TypeName}
 */
function isSelect(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	if (str == 0 || str == "") {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;
}
// 验证汉字和英文
function isChineseOrEnglish(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /^[a-zA-Z\u4e00-\u9fa5]+$/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (!reg.test(str)) {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;
}
// 验证网址
function isWebsite(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /^((https|http|ftp|rtsp|mms)?:\/\/)+[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]':+!]*([^<>\"\"])*$/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (!reg.test(str)) {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;

}
// 只能输数字
function isNumber(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /^\d+$/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (!reg.test(str)) {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;
}
/**
 * 固定电话
 * 
 * @param valueId
 * @param errorId
 * @param msg
 * @returns {Boolean}
 */
function isPhone(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (!reg.test(str)) {
		errorStye(valueId, errorId, msg);
		return false;
	}
	rightStye(valueId, errorId);
	return true;
}
/**
 * 验证手机号
 * 
 * @param valueId
 * @param errorId
 * @param msg
 * @returns {Boolean}
 */
function isCellphone(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	} else {
		if (!reg.test(str)) {
			errorStye(valueId, errorId, msg);
			
			return false;
		} else {
			rightStye(valueId, errorId);
			return true;
		}
	}
}



/**
 * 手机验证码
 * 
 * @param valueId
 * @param errorId
 * @param msg
 * @returns {Boolean}
 */
function isCode(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /^[0-9a-zA-Z]{6}$/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	} else {
		if (!reg.test(str)) {
			errorStye(valueId, errorId, msg);
			
			return false;
		} else {
			rightStye(valueId, errorId);
			return true;
		}
	}
}

/**
 * 验证电话号码和手机号码
 * @param valueId
 * @param errorId
 * @param msg
 * @returns {Boolean}
 */
function isPhoneOrCellphone(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	var reg1 = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$$/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	} else {
		if (!reg.test(str) && !reg1.test(str)) {
			errorStye(valueId, errorId, msg);
			return false;
		} else {
			rightStye(valueId, errorId);
			return true;
		}
	}
}
/**
 * 验证特殊字符
 * 
 * @param valueId
 * @param errorId
 * @param msg
 * @returns {Boolean}
 */
function isUnusedCode(valueId, errorId, msg) {
	var str = $$.trim($$(valueId).val());
	var reg = /<|>|'|;|&|#|"|\/| |'/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	} else {
		if (reg.test(str)) {
			errorStye(valueId, errorId, msg);
			return false;
		} else {
			rightStye(valueId, errorId);
			return true;
		}
	}
}

// 匹配2位小数点
function CheckDoubleTow(valueId, errorId, msg) {
	var str = $$(valueId).val();
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (str != $$.trim($$(valueId).val())) {
		errorStye(valueId, errorId, "数据的开头和结尾不能输入空格");
		return false;
	}
	if (str.length >= 16) {
		errorStye(valueId, errorId, msg + "长度应小于16位");
		return false;
	}
	var reg = /^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
	if (reg.test(str)) {
		rightStye(valueId, errorId);
		return true;
	} else {
		errorStye(valueId, errorId, msg + "格式不正确，格式如：10.99或10.0或10");
		return false;
	}
}

//匹配4位小数点
function CheckDoubleFour(valueId, errorId, msg) {
	var str = $$(valueId).val();
	if (str != $$.trim($$(valueId).val())) {
		errorStye(valueId, errorId, "数据的开头和结尾不能录入空格");
		return false;
	}
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	}
	if (str.length >= 16) {
		errorStye(valueId, errorId, msg + "长度应小于16位");
		return false;
	}
	var reg = /^(([1-9]\d*)|\d)(\.\d{1,4})?$/;
	if (reg.test(str)) {
		rightStye(valueId, errorId);
		return true;
	} else {
		errorStye(valueId, errorId, msg + "格式不正确，格式如：10.9999");
		return false;
	}
}

// 匹配0-1
function CheckRate(valueId, errorId, msg) {
	var str = $$(valueId).val();
	if (str == "") {
		return true;
	}
	var reg = /^0$|^1$|(^(0\.([0-9]{0,4}))$)|(^(1\.0{0,4})$)/;
	if (reg.test(str) && str != "1." && str != "0.") {
		rightStye(valueId, errorId);
		return true;
	} else {
		errorStye(valueId, errorId, msg);
		return false;
	}

}
/**
 * 验证整数
 * @param {Object} valueId
 * @param {Object} errorId
 * @param {Object} msg
 * @return {TypeName} 
 */
function checkInt(valueId, errorId, msg) {
	var str = $$(valueId).val();
	if (str == "") {
		return true;
	}
	var reg = /^[\+\-]?\d*$/;
	if (reg.test(str) && str != "1." && str != "0.") {
		rightStye(valueId, errorId);
		return true;
	} else {
		errorStye(valueId, errorId, msg + "格式不对：请输入整数：如，100");
		return false;
	}
}
/**
 * 错误时界面样式
 * 
 * @param {Object}
 *            valueId
 * @param {Object}
 *            errorId
 * @param {Object}
 *            msg
 * @return {TypeName}
 */
function errorStye(valueId, errorId, msg) {
	//$$(valueId).css("border", '#eee 2px solid');
	//$$(valueId).css("background",'#fff');
	$$(errorId).text(msg);
}
function rightStye(valueId, errorId) {
	$$(errorId).text('');
	//$$(valueId).css("border", '#000 1px solid');
	//$$(valueId).css("background",'#fff');
}
function focusInfoEmpty(valueId, errorId) {
	$$(errorId).text('');

}

/**
 * 长度，中文，英文
 * 
 * @param value
 *            输入的值
 * @param errInfo
 *            错误提示信息显示的地方
 * @param minLength
 *            输入值最小长度
 * @param maxLength
 *            输入值最大长度
 * @param fiedName
 *            提示的语句
 * @returns {Boolean}
 */
function IsChineseEngLength(value, errInfo, minLength, maxLength, fiedName) {
	var ret = true;
	ret = valueLength(value, errInfo, minLength, maxLength, "* " + fiedName
			+ "长度为" + minLength + "-" + maxLength + "位");
	if (ret == false) {
		return false;
	}
	ret = isChineseOrEnglish(value, errInfo, "* " + fiedName + "只能输汉字或者大小写字母");
	if (ret == false) {
		return false;
	}

	return ret;
}
/**
 * 长度，中文，英文,数字，下划线
 * 
 * @param value
 * @param errInfo
 * @param minLength
 * @param maxLength
 * @param fiedName
 * @returns {Boolean}
 */
function isChinEngNumUnderLineLength(value,errInfo,minLength,maxLength,fiedName){
	var ret = true;
	ret = isChinOrEngOrNum(value,errInfo, "* "+fiedName+"只能是大小写英文字母、汉字、数字、下划线");
	if (ret == false) {
		return false;
	}
	ret = valueLength(value, errInfo, minLength, maxLength,
			"* "+fiedName+"长度为"+minLength+"-"+maxLength+"位");
	if (ret == false) {
		return false;
		}
	return ret;	
}
/**
 * 验证长度和非法字符
 * 
 * @param value
 * @param errInfo
 * @param minLength
 * @param maxLength
 * @param fiedName
 * @returns {Boolean}
 */
function isLengthUnusedCode(value,errInfo,minLength,maxLength,fiedName){
	var ret = true;
	ret = valueLength(value, errInfo, minLength, maxLength,
			"* "+fiedName+"长度为"+minLength+"-"+maxLength+"字之间");
	if (!ret) {
		return false;
		}
	ret = isUnusedCode(value,errInfo, "* "+fiedName+"不能输特殊字符，如：&,|;“等");
	if (!ret) {
		return false;
	}
	
	return ret;	
}
/**
 * 验证数字小写转大写
 * @memberOf {TypeName} 
 */
function isDaXiao(n) { 
	if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n)) 
		return "数据非法"; 
	var unit = "千百拾亿千百拾万千百拾元角分", str = ""; 
	n += "00"; 
	var p = n.indexOf("."); 
	if (p >= 0)
		n = n.substring(0, p) + n.substr(p+1, 2);
	    unit = unit.substr(unit.length - n.length);
	for (var i=0; i < n.length; i++)
		str += "零壹贰叁肆伍陆柒捌玖".charAt(n.charAt(i)) + unit.charAt(i);
	alert(str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整"))
	return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
	}/*
    alert("1526365.56 "+isXiaoDa(1526365.56))*/


/***
 * 操作按钮
 * @memberOf {TypeName} 
 */
$$(document).ready(function(){
  
  $$('li.mainlevel').mousemove(function(){
  $$(this).find('ul').slideDown();//you can give it a speed
  });
  $$('li.mainlevel').mouseleave(function(){
  $$(this).find('ul').slideUp("fast");
  });
  
});

/**
 * 简单的四则运算（包括括号、加法、减法、乘法、除法）
 * @param {Object} value
 */
function arithmetic(value,valueTo,errorTitile){
	try{
		$$(valueTo).val(RoundNumber(eval(value),0));
		return true;
		}catch(exception) {
			$$(valueTo).val("");
			$$(errorTitile).text("公式出错，请填写正确的公式")
		}
		return false;
}
/**
 * 特殊字符
 * @param {Object} value
 */
function specialcharacter(valueId, errorId, msg) {
	var str = $$(valueId).val();
	var reg =/[-~#^$@%&!*\s*]/;
	if (str == "") {
		rightStye(valueId, errorId);
		return true;
	} else {
		if (reg.test(str)) {
			errorStye(valueId, errorId, msg);
			return false;
		} else {
			rightStye(valueId, errorId);
			return true;
		}
	}
}

