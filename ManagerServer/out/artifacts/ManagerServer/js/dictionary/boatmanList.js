var General_List;
var General_page=1;
$(document).ready(function(){
	ShowBoatmanKindListByPage('ShowBoatmanKindListByPage',1);
});
function ShowBoatmanKindListByPage(actionName,selectedPage){
	General_page=selectedPage;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'cpage':selectedPage
		},
		success:function(data){
			$(".listTable .addTr").empty();
			$(".listTable .addTr").remove();
			General_List=data.list;
			if(General_List==null){
				TableIsNull();
			}else{
				appendToTable(data.list);
				gotoPageMethodName = "gotoPageNo";
				printPage(data.totalPage,selectedPage,'ShowBoatmanKindListByPage',
						actionName,gotoPageMethodName,data.allTotal);
			}
		}
	});
}
function SearchBoatmanKind(actionName,selectedPage){
	General_page=selectedPage;
	var search = $("#content_search").val();

	if (/[~#^$@%&!*\s*]/.test(search)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'cpage':selectedPage,
			'queryCondition.listKeyValuePair[0].key':'boatmanKindName',
			'queryCondition.listKeyValuePair[0].pair':'%%',
			'queryCondition.listKeyValuePair[0].value':search
		},
		success:function(data){
			$(".listTable .addTr").empty();
			$(".listTable .addTr").remove();
			General_List=data.list;
			if(General_List==null){
				TableIsNull();
			}else{
				appendToTable(data.list);
				gotoPageMethodName = "gotoPageNo2";
				printPage(data.totalPage,selectedPage,'ShowBoatmanKindListByPage',
						actionName,gotoPageMethodName,data.allTotal);
			}
		}
	});
}
function TableIsNull(){
	$(".addTr").empty();
	$(".addTr").remove();
	var newTr=$("<tr class='addTr'></tr>");
	newTr.append($("<td colspan='3' style='text-align:center'>暂无数据</td>"));
	$(".listTable").append(newTr);
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
		SearchBoatmanKind(actionName, pageNo);
	}
}
function gotoPageNo2(actionName, totalPage) {
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
		ShowBoatmanKindListByPage(actionName, pageNo);
	}
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		var newTr=$("<tr class='addTr'></tr>");
		newTr.append($("<td>"+(i+1)+"</td>"));
		newTr.append($("<td>"+list[i].boatmanKindName+"</td>"));
		
		newTr.append($("<td><a class='Operate' onclick='OpenUpdateDiv("+i+")'>编辑</a>" +
						"<a class='Operate' onclick='DeleteBoatmanKind("+i+")'>删除</a></td>"));
		$(".listTable").append(newTr);
	}
}

//删除
function DeleteBoatmanKind(num){
	if(confirm("你确定要删除此职位吗？")){
		$.ajax({
			url:'DeleteBoatmanKind',
			type:'post',
			dataType:'json',
			data:{
				'boatmanKind.boatmanKindID':General_List[num].boatmanKindID
			},
			success:function(data){
				alert("删除成功！");
				ShowBoatmanKindListByPage('ShowBoatmanKindListByPage',General_page);
			}
		});
	}
}

//编辑
function OpenUpdateDiv(num){
	$("#dialogtitle").text("修改船舶职位信息");
	$("#boatmanKindID").val(General_List[num].boatmanKindID);
	$("#boatmanKindName").val(General_List[num].boatmanKindName);
	$("#BoatmanKindDiv").show();

}
function OpenAddDiv(){
	$("#dialogtitle").text("新增船舶职位信息");
	$("#BoatmanKindDiv").show();
}
function UpdateOrSaveBoatmanKind(){
	if($.trim($("#boatmanKindName").val()).length==0||$.trim($("#boatmanKindName").val()).length>10){
		alert("职位名称输入1~10位！");
		return;
	}
	if($.trim($("#boatmanKindID").val()).length!=0){
		$.ajax({
			url:'UpdateBoatmanKind',
			type:'post',
			dataType:'json',
			data:{
				'boatmanKind.boatmanKindID':$("#boatmanKindID").val(),
				'boatmanKind.boatmanKindName':$("#boatmanKindName").val()
			},
			success:function(data){
				if(data.totalPage==0){
						alert("修改成功！");
						closeDialogDiv();
						$("#dialogtitle").text("");
						$("#boatmanKindID").val("");
						$("#boatmanKindName").val("");
						ShowBoatmanKindListByPage('ShowBoatmanKindListByPage',General_page);
				}else{
					alert("职位名称不能重复");
				}
				
			},
			error:function(e){
				alert("修改失败，请刷新重试！");
			}
		});
	}else{
		$.ajax({
			url:'AddBoatmanKind',
			type:'post',
			dataType:'json',
			data:{
				'boatmanKind.boatmanKindName':$("#boatmanKindName").val(),
				'boatmanKind.isDelete':'N'
			},
			success:function(data){
				if(data.totalPage==0){
					alert("新增成功！");
					closeDialogDiv();
					$("#dialogtitle").text("");
					$("#boatmanKindID").val("");
					$("#boatmanKindName").val("");
					ShowBoatmanKindListByPage('ShowBoatmanKindListByPage',General_page);
				}else{
					alert("职位名称不能重复");
				}
			},
			error:function(e){
				alert("新增失败，请刷新重试！");
			}
		});
	}
}
function closeDialogDiv(){
	$("#dialogtitle").text("");
	$("#boatmanKindID").val("");
	$("#boatmanKindName").val("");
	$("#BoatmanKindDiv").hide();
}