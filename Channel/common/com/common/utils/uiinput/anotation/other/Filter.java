package com.common.utils.uiinput.anotation.other;

//目前支持的验证器如下：
//NOTEMPTY: 非空验证器
//LENGTH： 长度验证器，需要指定MAX与MIN，中文长度*2
//MAXLENGTH： 最大长度验证器，需要指定MAX，中文长度*2
//MINLENGTH： 最小长度验证器，需要指定MIN，中文长度*2
//VALRANGE：值范围验证器，需要指定MAX与MIN
//MAXVAL：最大值验证器，需要指定MAX
//MINVAL：最小值验证器，需要指定MIN
//REGEXP：自定义正则验证器
//EQUAL：与另一元素值是否相等验证器，需要指定target
//EMAIL：邮件格式验证
//PHONE：手机号格式验证，支持手机与座机
//PASSWORD：密码验证，密码为字母数字或下划线，且以数字开头，长度在6~15之间
//NUMBER：输入全为数字验证器
//CHARSETEN：英文字母验证器
//IDCARD：身份证验证器
//CHARNUMBER：字母或数字验证器
//DOUBLE：小数验证器
//USER：自定义验证器，自定义验证器需要指定自定义js函数名fn，该js函数不能进行ajax异步请求，
//-----如果自定义验证不需要进行后台服务器验证，则fn根据验证结果直接返回TRUE/FALSE即可
//-----如果自定义验证需要进行后台服务器验证，则fn返回对象{url:***,data:{...},validatefn},
//-----其中validatefn(jsonresult)为判断验证成功与否函数，成功返回TRUE，失败返回FALSE，如果不指定validatefn，则默认通过ifResultOK来判断
public enum Filter {
	NOTEMPTY, // 非空检查, 默认空值为''或null
	LENGTH, // 长度检查,必须指定min和max
	MAXLENGTH, // 最大长度验证器，需要指定MAX，中文长度*2
	MINLENGTH, // 最小长度验证器，需要指定MIN，中文长度*2
	VALRANGE, // 数值大小检查,必须指定min和max
	MAXVAL, // 最大值验证器，需要指定MAX
	MINVAL, // 最小值验证器，需要指定MIN
	REGEXP, // 自定义正则检查，必须指定exp
	EQUAL, // 与页面另一元素值是否相等，必须指定target
	EMAIL, // 邮件格式检查
	PHONE, // 手机号或座机号检查
	PASSWORD, // 密码验证，密码为字母数字或下划线，且以数字开头，长度在6~15之间
	NUMBER, // 纯数字检查
	CHARSETEN, // 英文字母验证器
	IDCARD, // 身份证验证器
	CHARNUMBER, // 字符或数字
	DOUBLE, // 小数检查
	USER;// 自定义验证js函数， 此时fn不能为空
}
