$(document).ready(function() {
	CheckLogin();
});
document.onkeydown = function() {
	var k = window.event.keyCode;
	if (k == 116) { // 屏蔽 F5 刷新键
		window.event.keyCode = 0;
		window.event.returnValue = false;
		window.location.reload();

	}
}

// 正在加载中的显示
function ShowLoadingDiv() {
	$("#img_ShowLoadingDiv").html("<img src='image/loading.gif'/>");
	//parent.popit();
	var bh = $("body").height();
	var bw = $("body").width();
	$("#fullbg").css( {
		height : bh,
		width : bw,
		display : "block"
	});
	$("#ShowLoadingDiv").show();
}
function CloseLoadingDiv() {
	//parent.closepopit();
	$("#fullbg,#ShowLoadingDiv").hide();
}
// 验证登陆
function CheckLogin() {
	if ($("#LoginUser").val() == "null")
		window.top.location.href = $("#basePath").val() + "page/login.jsp";
}

//数据为空
function TableIsNull() {
	$("#table_list .addTr").empty();
	$("#table_list .addTr").remove();
	newTr = $("<tr class='addTr'><td align='center'>暂无数据表</td></tr>");
	$("#table_list").append(newTr);
}
function exit(){
	if(confirm("确定退出？"))
	window.location.href=$("#basePath").val()+"page/login.jsp";
}
/**
* *********  操作实例  **************
*	var map = new HashMap();
*	map.put("key1","Value1");
*	map.put("key2","Value2");
*	map.put("key3","Value3");
*	map.put("key4","Value4");
*	map.put("key5","Value5");
*	alert("size："+map.size()+" key1："+map.get("key1"));
*	map.remove("key1");
*	map.put("key3","newValue");
*	var values = map.values();
*	for(var i in values){
*		document.write(i+"："+values[i]+"   ");
*	}
*	document.write("<br>");
*	var keySet = map.keySet();
*	for(var i in keySet){
*		document.write(i+"："+keySet[i]+"  ");
*	}
*	alert(map.isEmpty());
*/

/*function HashMap(){
	//定义长度
	var length = 0;
	//创建一个对象
	var obj = new Object();

	*//**
	* 判断Map是否为空
	*//*
	this.isEmpty = function(){
		return length == 0;
	};

	*//**
	* 判断对象中是否包含给定Key
	*//*
	this.containsKey=function(key){
		return (key in obj);
	};

	*//**
	* 判断对象中是否包含给定的Value
	*//*
	this.containsValue=function(value){
		for(var key in obj){
			if(obj[key] == value){
				return true;
			}
		}
		return false;
	};

	*//**
	*向map中添加数据
	*//*
	this.put=function(key,value){
		if(!this.containsKey(key)){
			length++;
		}
		obj[key] = value;
	};

	*//**
	* 根据给定的Key获得Value
	*//*
	this.get=function(key){
		return this.containsKey(key)?obj[key]:null;
	};

	*//**
	* 根据给定的Key删除一个值
	*//*
	this.remove=function(key){
		if(this.containsKey(key)&&(delete obj[key])){
			length--;
		}
	};

	*//**
	* 获得Map中的所有Value
	*//*
	this.values=function(){
		var _values= new Array();
		for(var key in obj){
			_values.push(obj[key]);
		}
		return _values;
	};

	*//**
	* 获得Map中的所有Key
	*//*
	this.keySet=function(){
		var _keys = new Array();
		for(var key in obj){
			_keys.push(key);
		}
		return _keys;
	};

	*//**
	* 获得Map的长度
	*//*
	this.size = function(){
		return length;
	};

	*//**
	* 清空Map
	*//*
	this.clear = function(){
		length = 0;
		obj = new Object();
	};
}*/