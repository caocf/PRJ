$(document).ready(function() {
		showLocation();//显示位置
		searchSignIdInfo();
});

function showLocation(){
	$.ajax( {
		url : 'showSignInfoById',
		type : "post",
		dataType : "json",
		data:{'signLocation':$("#signLocation").val()},
		success : function(data) {
			var map = new BMap.Map("allmap");
			var point = new BMap.Point(data.list[0].longitude,data.list[0].latitude);
			map.centerAndZoom(point, 20);
			var marker = new BMap.Marker(point);  // 创建标注
			map.addOverlay(marker);              // 将标注添加到地图中
			marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			var label = new BMap.Label("签退地点",{offset:new BMap.Size(20,-10)});
			marker.setLabel(label);		
			map.enableScrollWheelZoom(true);//地图缩放
		}
	});
	
}
function searchSignIdInfo() {
	
	$.ajax( {
		url : 'showSignInfo',
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 's.signID',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : $("#signID").val(),
			'signStatus':1,
			'queryCondition.orderByFielName' : 's.signID',
			'queryCondition.sequence' : 'asc',
			'cpage' : 1
		},
	success : function(data) {
	$("#signID").attr("value",data.list[0][0].signID);
	$("#signUser").attr("value", data.list[0][0].signUser);
	$("#signUserName").attr("value", data.list[0][1].name);
	$("#signTime").attr("value", dateT(data.list[0][0].signTime));
	$("#signLocation").attr("value", data.list[0][0].signLocation);
	$("#signLocationName").text(data.list[0][2].locationName);
	
}
	});
}
function dateT(value){
	if(value==null){
		return "";
	}
	return value.replace("T"," ");
}
function returnhistory(){
	$("#adduser").css("display","none");
	$("#usersTableDiv").css("display","block");
}