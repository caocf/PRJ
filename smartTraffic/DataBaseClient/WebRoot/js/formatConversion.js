
//NULL显示为无
function DateIsNull(value){	
	returnValue="&nbsp;";
	if(value==null||value=="null"||value==""){
		
		return returnValue;
	}else{	
		return value;
	}
}
//NULL显示 returnValue
function DateIsNullTOTISHI(value,returnValue){	
	if(value==null||value=="null"||value==""){
		
		return returnValue;
	}else{	
		return value;
	}
}
//NULL不显示
function DateIsNull2(value){	
	returnValue="";
	if(value==null||value=="null"||value==""){
		
		return returnValue;
	}else{	
		return value;
	}
}
//NULL不显示
function DateIsNull3(value){	
	returnValue="&nbsp;";
	if(value==null||value=="null"||value==""){
		
		return returnValue;
	}else{	
		return value;
	}
}
function DateIsNullToZero(value){
	var returnValue=0;
	if(value==null||value=="null"){
		
		return returnValue;
	}else{	
		return value;
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
	returnValue="&nbsp;";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(0,16);
		returnValue=returnValue.replace("T", " ");
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
