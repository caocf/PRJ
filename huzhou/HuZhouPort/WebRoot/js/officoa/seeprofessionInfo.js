$(document).ready(function(){
	showProfessionInfo();
});
function showProfessionInfo(){
	$.ajax({
		url:'findIndustryInfoDetail',
		type:'post',
		dataType:'json',
		data:{
			'id':$("#InfoId").val()
		},
		success:function(data){
			var list=data.industryinfo;
			$("#professionInfoName").val(list.title);
			$("#professionInfoContent").val(list.content);
			$("#createtime").text(HourTimeFormat(list.updatetime));
			if(list.oriobj==0){
				$("#faceToObject").text("外部用户");
			}else if(list.oriobj==1){
				$("#faceToObject").text("内部用户");
			}else{
				$("#faceToObject").text("全部用户");
			}
		}
	});
}