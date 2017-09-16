function goBackSchedule(){
	window.location.href=$("#basePath").val()+"page/officoa/schedule_show.jsp";
}
function goScheduleUpdate(){
	window.location.href=$("#basePath").val()+"page/officoa/schedule_update.jsp?eventId="+$("#eventId").val();
}
//发布-button
function  PublishOver(id){
	id.style.background="url(image/operation/publish_click.png)";
}
function  PublishOut(id){
	id.style.background="url(image/operation/publish_normal.png)";
}
function openClockPage(){
	window.location.href=$("#basePath").val()+"page/officoa/schedule_clock.jsp?eventId="+$("#eventId").val();
}
//移除
function MoveToOver(id){
	id.src="image/schedule/moveto_hover.png";
}
function  MoveToOut(id){
	id.src="image/schedule/moveto.png";
}
//全部移除
function AllMoveToOver(id){
	id.src="image/schedule/allmoveto_hover.png";
}
function  AllMoveToOut(id){
	id.src="image/schedule/allmoveto.png";
}
//新增
function AddToOver(id){
	id.src="image/schedule/addto_hover.png";
}
function  AddToOut(id){
	id.src="image/schedule/addto.png";
}
//全部新增
function AllAddToOver(id){
	id.src="image/schedule/alladdto_hover.png";
}
function  AllAddToOut(id){
	id.src="image/schedule/alladdto.png";
}
//弹出框搜索
function DialogSearchOver(id){
	id.src="image/schedule/searchuser_hover.png";
}
function DialogSearchOut(id){
	id.src="image/schedule/searchuser_normal.png";
}