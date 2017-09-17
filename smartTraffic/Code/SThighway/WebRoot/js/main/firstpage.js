$(document).ready(function(){
	//$("#top_text").text("路网信息");//top上的显示
	$("#first_select").attr("class","mtop_select");
	showRouteInfo();
});
var List="";
function showRouteInfo(){
	$.ajax({
		url:'lxjbxxlist/lwintroduce',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
            if(data.result.resultcode==1){
				var list=data.result.list;
				List=list;
				appendToDiv(list,data.result.obj);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function appendToDiv(list,permission){
	var html0="";
	var html1="";

		html0=list[0];
		html1=list[1];

	$("#left_text_edit").val(list[0]);
	$("#right_text_edit").val(list[1]);
	$("#left_text").append(html0);
	$("#right_text").append(html1);
}
