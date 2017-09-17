var birthday_YYYY;
	var birthday_MM;
	var birthday_DD;

window.onload=function() {
	birthday_YYYY=document.getElementById("seachyear");
	birthday_MM=document.getElementById("seachmonth");
	birthday_DD=document.getElementById("seachday");
	strYYYY = birthday_YYYY.outerHTML;
	strMM = birthday_MM.outerHTML;
	strDD = birthday_DD.outerHTML;
	MonHead = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	// 年下拉框赋值
	var y = new Date().getFullYear();
	var str = strYYYY.substring(0, strYYYY.length - 9);
	// 以今年为准，前80年
	for (var i = (y); i  >(y-80); i--) {
		str += "<option value='" + i + "'> " + i + "</option>\r\n";
	}
	birthday_YYYY.outerHTML = str +"</select>";
	// 月份下拉框赋值
	var str = strMM.substring(0, strMM.length - 9);
	for (var i = 1; i < 13; i++){
		str += "<option value='" + i + "'> " + i + "</option>\r\n";
	}
	birthday_MM.outerHTML = str +"</select>";
	birthday_YYYY.value = y;
	birthday_MM.value = new Date().getMonth() + 1;
	var n = MonHead[new Date().getMonth()];
	if (new Date().getMonth() ==1 && IsPinYear(YYYYvalue)) n++;
	writeDay(n); // 日期下拉框赋值
	birthday_DD.value = new Date().getDate();
	//加载数据
	OnLoad();
}
//年发生变化时日期发生变化(主要是判断闰平年)
function YYYYMM(str) {
	var MMvalue = birthday_MM.options[birthday_MM.selectedIndex].value;
	if (MMvalue == ""){DD.outerHTML = strDD; return;}
	var n = MonHead[MMvalue - 1];
	if (MMvalue ==2 && IsPinYear(str)) n++;
	writeDay(n)
}
//月发生变化时日期联动
function MMDD(str) {
	var YYYYvalue = birthday_YYYY.options[birthday_YYYY.selectedIndex].value;
	if (str == ""){DD.outerHTML = strDD; return;}
	var n = MonHead[str - 1];
	if (str ==2 && IsPinYear(YYYYvalue)) n++;
	writeDay(n)
}
//据条件写日期的下拉框
function writeDay(n) {
	var s = strDD.substring(0, strDD.length - 9);
	for (var i=1; i<(n+1); i++)
	s += "<option value='" + i + "'> " + i + "</option>\r\n";
	birthday_DD.outerHTML = s +"</select>";
}
//判断是否闰平年
function IsPinYear(year)
{ return(0 == year%4 && (year%100 !=0 || year%400 == 0))}