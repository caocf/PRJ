//年-月-日
function YearMonthDay(value){
	returnValue="";
	if(value==null){
		return returnValue;
	}else{
		returnValue=value.substring(0,10);
		return returnValue;
	}	
}
//NULL显示为无
function DateIsNull(value){	
	returnValue="无";
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
	returnValue="";
	if(value==null||value=="null"||value==""){
		
		return returnValue;
	}else{	
		return ":"+value;
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
//返回xxxx-xx-xx xx:xx
function ShortTimeByformate(value){
	returnValue="&nbsp;";
	if(value==null|value==""){
		return returnValue;
	}else{
		returnValue=value.substring(0,16);
		return returnValue;
	}
	return returnValue;
}
//返回xxxx-xx-xx
function ShortTimeByformateDay(value){
	returnValue="&nbsp;";
	if(value==null|value==""){
		return returnValue;
	}else{
		returnValue=value.substring(0,10);
		return returnValue;
	}
	return returnValue;
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

function SUBSTRINGDOUBLE(value,number){
	returnValue="";
	if(value==null){
		return returnValue;
	}else if(parseInt(value)==value/1){
		return value;
	}else{
		return parseFloat(value).toFixed(number);
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
