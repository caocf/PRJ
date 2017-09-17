$(document).ready(function() {
	if ($("#menu_number").val() != "null") {
		var selectnum=$("#menu_number").val().split(",");
		$("#left_select_child"+selectnum[0]+" .li_select"+selectnum[1]).css({"background-color":"#d3e2e8","color":"#333333"});
		if(selectnum[0]==1){
			$("#left_no_select1,#left_select_child1").show();
			$("#left_no_select2,#left_select_child2").hide();
		}else{
			$("#left_no_select2,#left_select_child2").show();
			$("#left_no_select1,#left_select_child1").hide();
		}
	}
});
/*function ShowChildDIV(thisop,num){
	$(".left_select").attr("class","left_no_select");
	thisop.className="left_select";
	$("#left_select_child1,#left_select_child4").hide();
	$("#left_select_child"+num).show();
}*/
function RoleGroupManage(){
	window.location.href=$("#basePath").val()+"page/system/RoleGroupManage.jsp";
		
}
function RoleManage(){
	window.location.href=$("#basePath").val()+"page/system/RoleManage.jsp";
		
}
function LogManage(){
	window.location.href=$("#basePath").val()+"page/system/LogManage.jsp";
		
}
function PersonalData(){
	window.location.href=$("#basePath").val()+"page/personal/PersonalData.jsp";
		
}
/*function HeadPicture(){
	window.location.href=$("#basePath").val()+"page/personal/HeadPicture.jsp";
		
}*/
function ModifyPassword(){
	window.location.href=$("#basePath").val()+"page/personal/ModifyPassword.jsp";
}
function VideomantainManager(){
	window.location.href=$("#basePath").val()+"page/system/videomaintain.jsp";

}
function PostmantainManager(){
	window.location.href=$("#basePath").val()+"page/system/postmaintain.jsp";

}