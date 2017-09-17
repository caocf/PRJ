//NULL显示为 returnValue
function DateIsNull(value,returnValue){	
	if(value==null||value=="null"||value==""){
		return returnValue;
	}else{	
		return value;
	}
}

//年-月
function YearAndMonth(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(0,7);
		return returnValue;
	}
}
//年
function Year(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(0,4);
		return returnValue;
	}
}
//月
function Month(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(5,7);
		return returnValue;
	}
}
//日
function DateTime(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(8,10);
		return returnValue;
	}
}
//年-月-日
function DateTimeFormat(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(0,10);
		returnValue=returnValue.replace("T", " ");
		return returnValue;
	}
}
//xx年xx月xx日  xx：xx
function HourTimeFormat(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(0,16);
		returnValue=returnValue.replace("T", " ");
		return returnValue;
	}
}
//xx年xx月xx日  xx：xx:xx
function SecondTimeFormat(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(0,19);
		returnValue=returnValue.replace("T", " ");
		return returnValue;
	}
}
//返回 xx小时xx分钟
function GetCusTime(value,returnValue){	
	if(value==null||value=="null"||value==""){
		return returnValue;
	}else{	
		returnValue=Math.floor(value.split(":")[0])+"小时"+Math.floor(value.split(":")[1])+"分钟";
		return returnValue;
	}
}
//时间格式化
function formatDate(date, format) {   
    if (!date) return; 
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
            date = new Date(date.replace(/-/, "/"));   
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {   
        "yyyy": date.getFullYear(),   
        "M": date.getMonth() + 1,   
        "d": date.getDate(),   
        "H": date.getHours(),   
        "m": date.getMinutes(),   
        "s": date.getSeconds(),   
        "MM": ("" + (date.getMonth() + 101)).substr(1),   
        "dd": ("" + (date.getDate() + 100)).substr(1),   
        "HH": ("" + (date.getHours() + 100)).substr(1),   
        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    };       
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
}  
//获得星期
function formDatetoweek(date) {   
    if (!date) return;   
   var week=date.getDay();
   var weekList=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    return weekList[week];              
} 
function TWOXIAOSHU(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else if(parseInt(value)==value/1){
		return value;
	}else{
		return parseFloat(value).toFixed(2);
	}
}

function SUBSTRINGDOUBLE(value,Integer){
	returnValue="";
	if(value==null){
		return returnValue;
	}else if(parseInt(value)==value/1){
		return value;
	}else{
		return parseFloat(value).toFixed(Integer);
	}
}

/**
 * 四舍五入
 * @param {Object} num
 * @param {Object} pos
 * @return {TypeName} 
 */
function RoundNumber(num, pos)  
{  
    return Math.round(num * Math.pow(10, pos)) / Math.pow(10, pos);  
}  


function ZeroToNull(value){
	if(value==0){
		return "";
	}else{
		return value;
	}
}
