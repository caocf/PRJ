$(document).ready(function(){
	addLinkToGuid();
	loadmap();
	
});
window.onresize=function(){
	setLinkLeft();
};
function addLinkToGuid(){
	setLinkLeft();
	initGuildPic();
	initTag();
	
	
}
function loadmap(){
	maplet = new XMap("port_map",3);
	maplet.setMapCenter("120.76403,30.74283",8);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}

function initTag(){
/**
 * 比例尺图标初始化
 */	
$("#zoomLarge").click(function() {
	maplet.zoomIn();
	//initCenterZoom(currentJwd,currentZoom+1);
});	
$("#zoomSmall").click(function() {
	maplet.zoomOut();
	//initCenterZoom(currentJwd,currentZoom-1);
});

}