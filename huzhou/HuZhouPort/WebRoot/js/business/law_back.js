function GotoIllegalList(){
	window.location.href=$("#basePath").val()+"page/business/law.jsp?lawPart=1";
}
function GotoPermitList(){
	window.location.href=$("#basePath").val()+"page/business/law.jsp?lawPart=2";
}
function GotoPatrolList(){
	window.location.href=$("#basePath").val()+"page/business/law.jsp?lawPart=3";
}
function GotoChannelList(){
	window.location.href=$("#basePath").val()+"page/business/law.jsp?lawPart=4";
}

function printCurrentPage()
{
	window.print();  
}

//添加附件
function  AddFileOver(id){
	$("#file_img").attr("src","image/fileImg/addFile_hover.png");
}
function  AddFileOut(id){
	$("#file_img").attr("src","image/fileImg/addFile.png");
}

var uploadNum=1;
function addComponent(file)
{ var url = file.value;	
	file.style.right="-100px";	
	  var pos = url.lastIndexOf("/");
	   if(pos == -1){
	     pos = url.lastIndexOf("\\")
	   }
	   var filename = url.substr(pos +1)
	$("#EvidenceUpload").append($("<tr id='upload"+uploadNum+"'><td>" +
			"<label>"+filename+"</label></td><td><a onclick='deleteUpload("+uploadNum+")' " +
					"class='operation2'>删除</a></td></tr>"));
	uploadNum++;
	$("#file_div").append($("<input type='file'  onchange='addComponent(this)' class='file_button'" +
			" id='file"+uploadNum+"' name='illegalEvidence.ef'/>"));
}
function deleteUpload(num){
	$("#upload"+num).empty();
	$("#upload"+num).remove();
	$("#file"+num).empty();
	$("#file"+num).remove();
	
}