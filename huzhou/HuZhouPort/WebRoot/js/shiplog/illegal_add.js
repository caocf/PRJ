$(document).ready(function() {
	if($("#sc").val()=="1")
		{
		alert("保存成功！");
		window.location.href=$("#basePath").val()+"page/business/law.jsp?lawPart=1";
		}
	if($("#sc").val()=="2")
		alert("保存失败！请重新保存。");
		showIllegalReasonList();// 缘由
		creatTree();
		
		$("#seconduser").focus(function() {
			var che = SeconduserCheck();
			$("#secondusererr").text("");
		});
		$("#seconduser").blur(function() {
			SeconduserCheck();
		});
		
		$("#threeuser").focus(function() {
			var che = ThreeuserCheck();
			$("#threeusererr").text("");
		});
		$("#threeuser").blur(function() {
			ThreeuserCheck();
		});
		
		$("#illegalObject").focus(function() {
			var che = ObjectCheck();
			$("#illegalObjecterr").text("");
		});
		$("#illegalObject").blur(function() {
			ObjectCheck();
		});
		$("#location").focus(function() {
			var che = LocationCheck();
			$("#locationerr").text("");
		});
		$("#location").blur(function() {
			LocationCheck();
		});
		$("#illegalContent").focus(function() {
			var che = confirmContentCheck();
			$("#illegalContenterr").text("");
		});
		$("#illegalContent").blur(function() {
			confirmContentCheck();
		});
					
	});
function SeconduserCheck(){
	var ret = true;
	ret =emptyChk("#seconduser", "#secondusererr", "请选择第二执法人");	
	return ret;
}
function ThreeuserCheck(){
	var ret = true;
	ret =emptyChk("#threeuser", "#threeusererr", "请选择审核人");
	if(ret==true){
		if($("#threeuserId").val()==$("#seconduserId").val()){
			$("#threeusererr").text("第二执法人和审核人不能相同");
			ret=false;
		}
	}
	return ret;
}
function ObjectCheck(){
	var ret = true;
	ret =emptyChk("#illegalObject", "#illegalObjecterr", "违章对象不能为空！");	
	if(ret == true)
	ret = valueLength("#illegalObject", "#illegalObjecterr", "1", "100","违章对象名称不能超过100个字。");	
	return ret;
}
function LocationCheck(){
	var ret = true;
	ret =emptyChk("#location", "#locationerr", "违章地点不能为空！");	
	if(ret == true)
	ret = valueLength("#location", "#locationerr", "1", "200","违章地点不能超过200个字。");	
	return ret;

}
function confirmContentCheck() {
	var ret = true;
	ret = valueLength("#illegalContent", "#illegalContenterr", "0", "2000","内容不能超过2000个字。");	
	return ret;
}

function showIllegalReasonList() {
	$.ajax( {
		url : 'showIllegalReasonList',
		type : "post",
		dataType : "json",
		success : function(data) {
			for ( var i = 0; i < data.list.length; i++)
				document.getElementById("illegalReason").options.add(new Option(
						data.list[i].reasonName,data.list[i].reasonId));
		}
	});

}
function update_submit(){
	if (!SeconduserCheck()) {
		return false;
	}
	if (!ThreeuserCheck()) {
		return false;
	}
	if (!ObjectCheck()) {
		return false;
	}
	if (!LocationCheck()) {
		return false;
	}
	if (!confirmContentCheck()) {
		return false;
	}
	
}
function GetQueryString(name)
{
	 var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	 var r = window.location.search.substr(1).match(reg);
	 if(r!=null)
	 return  unescape(r[2]);
}