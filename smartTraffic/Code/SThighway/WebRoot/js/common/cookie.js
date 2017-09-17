/*var  Cookie_Name="userName";
var  Cookie_Pwd="userPwd";
//添加一个cookie：addCookie(name,value,expiresHours)
function addCookie(name,value,expiresHours) {
	var cookieString=name+"="+escape(value)+"; "; 
	// 获取当前时间
	var date = new Date();
	var expiresDays = 10; // 默认时间 将date设置为10天以后的时间
	if (expiresHours != null || expiresHours != undefined)
		expiresDays = expiresHours;

	date.setTime(date.getTime() + expiresDays * 24 * 3600 * 1000);
	// 判断是否设置过期时间
	if(expiresDays>0){
		document.cookie = cookieString+"expires="+ date.toGMTString();
	}
		
}
// 获取指定名称的cookie值：getCookie(name)
function getCookie(name){ 
	var strCookie=document.cookie; 
	var arrCookie=strCookie.split("; "); 
	for(var i=0;i<arrCookie.length;i++){ 
		var arr=arrCookie[i].split("="); 
		if(arr[0]==name)
			return arr[1]; 
	} 
	return ""; 
} 
// 删除指定名称的cookie：deleteCookie(name)
function deleteCookie(names) {
	var date=new Date(); 
	date.setTime(date.getTime()-10000); 
	for(var i=0;i<names.length;i++){
		var cookieString=names[i]+"v; ";
	}
	document.cookie=cookieString+"expires="+date.toGMTString(); 
}


//本系统清除cookie
function deleteMyCookie(){
	var Names=new Array();
	Names[0]=Cookie_Name;
	Names[1]=Cookie_Pwd;
	deleteCookie(Names);
}*/
function setCookie(name,value,hours,path){
    var name = escape(name);
    var value = escape(value);
    var expires = new Date();
     expires.setTime(expires.getTime() + hours*3600000);
     path = path == "" ? "" : ";path=" + path;
     _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
     document.cookie = name + "=" + value + _expires + path;
}



// 存入cookie时编码
function CodeCookie(str) {
	var strRtn="";

	for (var i=str.length-1;i>=0;i--) 
	{ 
		strRtn+=str.charCodeAt(i); 
		if (i) strRtn+="a"; // 用a作分隔符
	} 
	return strRtn; 
}  
// 取出来时解码
function DecodeCookie(str) {
	if(str==""){
		return;
	}else{
		var   strArr;
		var   strRtn="";
		strArr=str.split("a");
		for(var i=strArr.length-1;i>=0;i--)
			strRtn+=String.fromCharCode(eval(strArr[i]));
		return  strRtn;
	}
  }
// 获取cookie值
function getCookieValue(name){
    var name = escape(name);
    // 读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;       
    // 查找名为name的cookie的开始位置
     name += "=";
    var pos = allcookies.indexOf(name);    
    // 如果找到了具有该名字的cookie，那么提取并使用它的值
    if (pos != -1){                                             // 如果pos值为-1则说明搜索"version="失败
        var start = pos + name.length;                  // cookie值开始的位置
        var end = allcookies.indexOf(";",start);        // 从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
        if (end == -1) end = allcookies.length;        // 如果end值为-1说明cookie列表里只有一个cookie
        var value = allcookies.substring(start,end); // 提取cookie的值
        return (value);                           // 对它解码
         }   
    else 
    	return "";                               // 搜索失败，返回空字符串
}
// 删除cookie
function deleteCookie(name,path){
    var name = escape(name);
    var expires = new Date(0);
     path = path == "" ? "" : ";path=" + path;
     document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;
}


//不设置时间存储cookie
function setTemptCookie(name,value,path) {
	 var name = escape(name);
	    var value = escape(value);	    
	     path = path == "" ? "" : ";path=" + path;
	     document.cookie = name + "=" + value + path;
 		
 }
