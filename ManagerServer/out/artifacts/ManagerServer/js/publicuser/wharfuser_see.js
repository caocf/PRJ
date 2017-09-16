$(document).ready(function() {
	PublicInfoByWharf();
	publicUserById();
});


function PublicInfoByWharf(){
	$.ajax( {
		url : "PublicInfoByWharf",
		type : "post",
		dataType : "json",
		data:{
		'publicUser.userId':$("#publicId").val()
	},
		success : function(data) {
		$("#wharfNum").text(data.list[0][0].userName);
		$("#imei").text(data.list[0][0].imei);
		
	},
	error : function(XMLHttpRequest) {
		alert("获取信息失败，请刷新重试！");
	}
	});

}

function publicUserById(){
	$.ajax( {
		url : "publicUserByIdWharf",
		type : "post",
		dataType : "json",
		data:{
		'publicUser.userId':$("#publicId").val()
	},
		success : function(data) {
		append(data.list);
	},
	error : function(XMLHttpRequest) {
		alert("获取信息失败，请刷新重试！");
	}
	});

}

function append(list){
	for(var i=0;i<list.length;i++){
		var tr=$("<tr></tr>");
		tr.append("<td>"+list[i][0]+"</td>");
		
		var path="File/"+list[i][4];
		
		tr.append("<td>"+DateIsNull(list[i][2],path)+"</td>");
		$("#wharfTable").append(tr);
	}
}
function DateIsNull(value,path) {
	returnValue = "无";
	if (value == null || value == "null") {

		return returnValue;
	} else {
		return "<a href='"+path+"' download='"+path+"'>"+value+"</a>";
	}
}
