var actionname;
var StatusByPermisssion;
$(document).ready(function(){
	SpecialPermisssion(34);
});
function DoSpecialPermisssion(status){
	if(status!=0){
		if(status<=1){
			$(".profession_addbutton").remove();
		}
		StatusByPermisssion=status;
		showProfessionInfomation('findIndustryInfoList',1);
	}
}
function showProfessionInfomation(actionName,selectedPage){
	actionname=actionName;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'rows':15,
			'searchstring':$("#profession_search").val()
		},
		success:function(data){
			$(".addTr").empty();
			$(".addTr").remove();
			var list=data.list;
			if(list==""){
				TableIsNull();
			}else{
				appendToTable(list,data.pages,data.total,selectedPage);
				gotoPageMethodName = "gotoPageNo";
				printPage(data.pages,selectedPage,'showProfessionInfomation',
						actionName,gotoPageMethodName,data.total);
			}
		}
	});
}
function TableIsNull(){
	$(".addTr").empty();
	$(".addTr").remove();
	var newTr=$("<tr class='addTr'></tr>");
	newTr.append($("<td colspan='3' style='text-align:center'>暂无资讯</td>"));
	$(".profession_table").append(newTr);
	$("#pageDiv").hide();
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").val();
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").prop("value", "1");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)||parseInt(pageNo)==0) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showProfessionInfomation(actionName, pageNo);
	}
}
function appendToTable(list,pages,total,selectedPage){
	for(var i=0;i<list.length;i++){
		var newTr=$("<tr class='addTr'></tr>");
		newTr.append($("<td>"+list[i].title+"</td>"));
		if(list[i].oriobj==0){
			newTr.append($("<td>"+"外部用户"+"</td>"));
		}else if(list[i].oriobj==1){
			newTr.append($("<td>"+"内部用户"+"</td>"));
		}else{
			newTr.append($("<td>"+"全部用户"+"</td>"));
		}
		
		if((StatusByPermisssion==2&&list[i].oriobj==1)||StatusByPermisssion==3){
			newTr.append($("<td><a class='Operate' onclick='seeprofessionInfo("+list[i].id+")'>查看</a>" +
					"<a class='Operate' onclick='editprofessionInfo("+list[i].id+")'>编辑</a>" +
							"<a class='Operate' onclick='deleteprofessionInfo("+list[i].id+")'>删除</a></td>"));
		}else{
			newTr.append($("<td><a class='Operate' onclick='seeprofessionInfo("+list[i].id+")'>查看</a></td>"));
		}
		
		$(".profession_table").append(newTr);
	}
}
//删除
function deleteprofessionInfo(num){
	if(confirm("你确定要删除此资讯吗？")){
		$.ajax({
			url:'deleteIndustryInfo',
			type:'post',
			dataType:'json',
			data:{
				'id':num
			},
			success:function(data){
				alert("删除成功！");
				window.location.href=$("#basePath").val()+"page/officoa/profession_information.jsp";
			}
			
		});
	}
}
//查看
function seeprofessionInfo(num){
	window.location.href=$("#basePath").val()+"page/officoa/seeprofessionInfo.jsp?InfoId="+num;
}
//编辑
function editprofessionInfo(num){
	window.location.href=$("#basePath").val()+"page/officoa/addprofessionInfo.jsp?InfoId="+num;
}
function addProfessionInfo(){
	window.location.href=$("#basePath").val()+"page/officoa/addprofessionInfo.jsp";
}