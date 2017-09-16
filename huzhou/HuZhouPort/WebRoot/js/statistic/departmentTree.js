//弹出框的显示
function showDialog(){ 
	$("#departmentDIv").show(); 	
}
//弹出框的隐藏
function claseDialog() { 
	$("#departmentDIv").hide(); 
} 
function creatTree(){
	$.ajax( {
		url : 'showDepartmentList',
		type : "post",
		dataType : "json",
		success : function(data) {
		CreatDepartment(data.list,-1);	
	}
	});	
}
function CreatDepartment(list,parentId){
	var newTree = $("#tree");
	for ( var i = 0; i < list.length; i++) {
	if(parentId==list[i].partOfDepartmentId){
		if(checkChile(list,list[i].departmentId)==0)
		{
			newTr = $("<li class='li" + list[i].departmentId + "'>" +
					"<img src='image/common/minus.png' style='cursor:default'/>"
					+ "<a onclick=SelectThisDepartment('" + list[i].departmentId+ "','"+ list[i].departmentName + "')>"
					+ list[i].departmentName + "</a></li>");
			if (list[i].partOfDepartmentId == -1) {
				newTree.append(newTr);
			} else {
				$('#opt' + list[i].partOfDepartmentId).append(newTr);
			}
		}
		else{
		newTr = $("<li class='li" + list[i].departmentId + "'>" +
				"<img src='image/common/plus.png' onclick='openNextDepartment("+list[i].departmentId+")' " +
						"id='img"+list[i].departmentId+"' />"
				+ "<a onclick=SelectThisDepartment('" + list[i].departmentId+ "','"+ list[i].departmentName + "')>"
				+ list[i].departmentName + "</a></li>");
		newTu = $("<ul id='opt" + list[i].departmentId + "' style='display:none'></ul>");

		$(newTr).append(newTu);
		if (list[i].partOfDepartmentId == -1) {
			newTree.append(newTr);
		} else {
			$('#opt' + list[i].partOfDepartmentId).append(newTr);

		}
		CreatDepartment(list,list[i].departmentId);
		}
		
	}
	}

}
//检查子部门
function checkChile(list,did){
	var cd=0;
	for ( var i = 0; i < list.length; i++) {
		if(list[i].partOfDepartmentId==did)
			cd+=1;		
	}	
	if(cd==0) return cd;
	else return 1;
	
}
//打开或关闭下级树
function openNextDepartment(id){
	eval("var   ul=opt"+id);   
    eval("var   img=img"+id);    
    ul.style.display=ul.style.display!="none"?"none":"block";   
    img.src=ul.style.display!="none"?"image/common/minus.png":"image/common/plus.png";   
}
function SelectThisDepartment(departmentId,departmentName){
	$("#selectli").removeAttr("id");
	$(".selectli").removeAttr("class");
	if(departmentId!=0){
		$(".li"+departmentId).attr("id","selectli");
	}else{
		$("#ganhangju").attr("class","selectli");
	}
	Gloval_D_Id=departmentId;
	Gloval_D_Name=departmentName;
	claseDialog();
}