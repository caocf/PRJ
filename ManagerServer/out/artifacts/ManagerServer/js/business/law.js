$(document).ready(function() {
	if($("#lawPart").val()==2){
		$("#layer1_menu li #law_right_part2").css("background-color","#fff");
		$("#layer1_menu li #law_right_part2").css("border-right","1px solid #c9d9e9");
		$("#layer1_menu li #law_right_part2").css("font-weight","bold");
		$("#law_right").attr("src",$("#basePath").val()+"page/business/permit_show.jsp");
	}
	else if($("#lawPart").val()==3){
		$("#layer1_menu li #law_right_part3").css("background-color","#fff");
		$("#layer1_menu li #law_right_part3").css("border-right","1px solid #c9d9e9");
		$("#layer1_menu li #law_right_part3").css("font-weight","bold");
		$("#law_right").attr("src",$("#basePath").val()+"page/business/patrol_show.jsp");
	}
	else if($("#lawPart").val()==4){
		$("#layer1_menu li #law_right_part4").css("background-color","#fff");
		$("#layer1_menu li #law_right_part4").css("border-right","1px solid #c9d9e9");
		$("#layer1_menu li #law_right_part4").css("font-weight","bold");
		$("#law_right").attr("src",$("#basePath").val()+"page/empty.jsp");
	}
	else{
	$("#layer1_menu li a:first").css("background-color","#fff");
	$("#layer1_menu li a:first").css("border-right","1px solid #c9d9e9");
	$("#layer1_menu li a:first").css("font-weight","bold");
	$("#law_right").attr("src",$("#basePath").val()+"page/business/illegal_show.jsp");
	}
});
function a_menu_onclick_first(id){
	$("#layer1_menu li a").css("background-color","#dce9f2");
	$("#layer1_menu li a").css("border","none");
	$("#layer1_menu li a").css("font-weight","");
		id.style.backgroundColor="#fff";
		id.style.fontWeight="bold";
		id.style.borderRight="1px solid #c9d9e9";
}
function a_menu_onclick(id){
	$("#layer1_menu li a").css("background-color","#dce9f2");
	$("#layer1_menu li a").css("border","none");
	$("#layer1_menu li a").css("font-weight","");
	id.style.backgroundColor="#fff";
	id.style.fontWeight="bold";
	id.style.borderRight="1px solid #c9d9e9";
	id.style.borderLeft="1px solid #c9d9e9";
}


