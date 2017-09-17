$$(document).ready(function(){
	showInfoDetail();
});
function showInfoDetail(){
	$$.ajax({
		url:'QueryJTGZDetail',
		type:'post',
		dataType:'json',
		data:{
			'xxbh':$$("#xxbh").val(),
		},
		success:function(data){
			var list=data;
			var newDiv=$$("<div class='info_body'></div>");
			newDiv.append($$("<div class='info_title'>"+list.BT+"</div>"));
			newDiv.append($$("<div style='width:100%;height:30px;' ><span class='date' >发布时间："+list.FBSJ+"&nbsp;发布人："+list.FBR+"</span></div>"));
			newDiv.append($$("<div class='info_nr'>&nbsp;&nbsp;&nbsp;&nbsp;"+list.NR+"</div>"));
			$$("#main_content").append(newDiv);
		}
	});
}
function goback(){
	window.history.go("-1");
}