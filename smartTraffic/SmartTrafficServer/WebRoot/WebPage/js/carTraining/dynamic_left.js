function SelectItem_top(thisop,urlPath){
	$$(".left1 ").attr("class","left2");
	thisop.className="left1";
	$$(".down").attr("src",urlPath);
	//window.location.href=$$("#basePath").val()+urlPath;
}
