$(document).ready(function() {
	PublicInfoByShip();
	publicUserById();
});


function PublicInfoByShip(){
	$.ajax( {
		url : "PublicInfoByShip",
		type : "post",
		dataType : "json",
		data:{
		'publicUser.userId':$("#publicId").val()
	},
		success : function(data) {
		$("#shipName").text(data.list[0][0].userName);
		$("#imei").text(data.list[0][0].imei);
	},
	error : function(XMLHttpRequest) {
		alert("获取信息失败，请刷新重试！");
	}
	});

}

function publicUserById(){
	$.ajax( {
		url : "publicUserById",
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
		
		var path="File/"+list[i][3];
		
		tr.append("<td>"+DateIsNull(list[i][1],path)+"</td>");
		$("#shipTable").append(tr);
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
