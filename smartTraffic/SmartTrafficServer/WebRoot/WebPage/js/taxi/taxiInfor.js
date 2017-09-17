function SelectItem_top(thisop,urlPath){
	$$(".top_select").attr("class","top_select_no");
	thisop.className="top_select";
	window.location.href=$$("#basePath").val()+urlPath;
}

function SelectItem_main_left(thisop,urlPath){
	$$(".main_left_select").attr("class","main_left_select_no");
	thisop.className="main_left_select";
	$$(".right").attr("src",urlPath);
}
function SelectItem_top1(thisop,urlPath){
	$$(".top_select").attr("class","top_select_no");
	thisop.className="top_select";
	//window.location.href=$$("#basePath").val()+urlPath;
	$$(".down").attr("src",urlPath);
}
function SelectItem_top2(thisop,urlPath){
	$$(".top_select_1").attr("class","top_select_1_no");
	thisop.className="top_select_1";
	window.location.href=$$("#basePath").val()+urlPath;
}