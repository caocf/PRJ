$(document).ready(function(){
	SpecialPermisssion(34);
	if($("#InfoId").val()!="null"&&$("#InfoId").val()!=""){
		showprofessionInfo();
	}
});
function DoSpecialPermisssion(status) {
	var objSelect = document.getElementById("isLinkInfo");
	if (status != 0) {
		if (status == 1) {
			objSelect.options.length = 0;
		} else if (status == 2) {
			objSelect.remove(2);
			objSelect.remove(0);
		}
	} else {
		objSelect.options.length = 0;
	}
}
//显示编辑信息
function showprofessionInfo(){
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
			var obj2=document.getElementById("isLinkInfo");
			if(obj2){
				var options=obj2.options;
				if(options){
					for(var i=0;i<options.length;i++){
						if(options[i].value==list.oriobj){
							options[i].defaultSelected=true;
							options[i].selected=true;
						}
					}
				}
			}
		}
	});
}
function AddprofessionInfo(){
	var titlename=$("#professionInfoName").val();
	var textcontent=$("#professionInfoContent").val();
	var LinkInfo=$("#isLinkInfo option:selected").val();
	if(titlename==""){
		alert("资讯标题不能为空！");
		$("#professionInfoName").focus();
		return;
	}
	if($.trim($("#professionInfoContent").val())==""){
		alert("资讯内容不能为空！");
		$("#professionInfoContent").focus();
		return;
	}
	if($("#InfoId").val()!="null"&&$("#InfoId").val()!=""){
		$.ajax({
			url:'updateIndustryInfo',
			type:'post',
			dataType:'json',
			data:{
				'industryinfo.id':$("#InfoId").val(),
				'industryinfo.title':titlename,
				'industryinfo.content':textcontent,
				'industryinfo.oriobj':LinkInfo
			},
			success:function(data){
				if(data.result==0){
					alert("更新成功!");
					window.history.go("-1");
				}else{
					alert("更新错误!");
				}
			}
		});	
	}else{
		$.ajax({
			url:'addIndustryInfo',
			type:'post',
			dataType:'json',
			data:{
				'industryinfo.title':titlename,
				'industryinfo.content':textcontent,
				'industryinfo.oriobj':LinkInfo
			},
			success:function(data){
				if(data.result==0){
					alert("新增成功!");
					window.history.go("-1");
				}else{
					alert("新增错误!");
				}
			}
		});
	}
	
}