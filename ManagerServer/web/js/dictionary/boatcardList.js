var General_List;
var General_page=1;
$(document).ready(function(){
	ShowBoatcardListByPage('ShowBoatcardListByPage',1);
});
function ShowBoatcardListByPage(actionName,selectedPage){
	General_page=selectedPage;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'cpage':selectedPage
		},
		success:function(data){
			$(".addTr").empty();
			$(".addTr").remove();
			General_List=data.list;
			if(General_List==null){
				TableIsNull();
			}else{
				appendToTable(data.list);
				gotoPageMethodName = "gotoPageNo";
				printPage(data.totalPage,selectedPage,'ShowBoatcardListByPage',
						actionName,gotoPageMethodName,data.allTotal);
			}
		}
	});
}
function SearchBoatcard(actionName,selectedPage){
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
			'cerficate.cardName':search
		},
		success:function(data){
			$(".addTr").empty();
			$(".addTr").remove();
			General_List=data.list;
			if(General_List==null){
				TableIsNull();
			}else{
				appendToTable(data.list);
				gotoPageMethodName = "gotoPageNo2";
				printPage(data.totalPage,selectedPage,'ShowBoatcardListByPage',
						actionName,gotoPageMethodName,data.allTotal);
			}
		}
	});
}
function TableIsNull(){
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
		ShowBoatcardListByPage(actionName, pageNo);
	}
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		var newTr=$("<tr class='addTr'></tr>");
		newTr.append($("<td>"+(i+1)+"</td>"));
		newTr.append($("<td>"+list[i].cardName+"</td>"));
		
		newTr.append($("<td><a class='Operate' onclick='OpenUpdateDiv("+i+")'>编辑</a>" +
						"<a class='Operate' onclick='DeleteBoatmanKind("+i+")'>删除</a></td>"));
		$(".listTable").append(newTr);
	}
}

//删除
function DeleteBoatmanKind(num){
	if(confirm("你确定要删除此证书吗？")){
		$.ajax({
			url:'DeleteBoatCard',
			type:'post',
			dataType:'json',
			data:{
				'certificate.cardID':General_List[num].cardID
			},
			success:function(data){
				alert("删除成功！");
				ShowBoatcardListByPage('ShowBoatcardListByPage',General_page);
			}
		});
	}
}

//编辑
function OpenUpdateDiv(num){
	$("#dialogtitle").text("修改证书名称");
	$("#boatmanKindID").val(General_List[num].cardID);
	$("#boatmanKindName").val(General_List[num].cardName);
	$("#BoatmanKindDiv").show();

}
function OpenAddDiv(){
	$("#dialogtitle").text("新增证书类型");
	$("#BoatmanKindDiv").show();
}
function UpdateOrSaveBoatmanKind(){
	if($.trim($("#boatmanKindName").val()).length==0||$.trim($("#boatmanKindName").val()).length>10){
		alert("证书名称输入1~10位！");
		return;
	}
	if($.trim($("#boatmanKindID").val()).length!=0){
		$.ajax({
			url:'UpdateBoatCard',
			type:'post',
			dataType:'json',
			data:{
				'certificate.cardID':$("#boatmanKindID").val(),
				'certificate.cardName':$("#boatmanKindName").val()
			},
			success:function(data){
				if(data.totalPage==0){
						alert("修改成功！");
						closeDialogDiv();
						$("#dialogtitle").text("");
						$("#boatmanKindID").val("");
						$("#boatmanKindName").val("");
						ShowBoatcardListByPage('ShowBoatcardListByPage',General_page);
				}else{
					alert("证书名称不能重复");
				}
				
			},
			error:function(e){
				alert("修改失败，请刷新重试！");
			}
		});
	}else{
		$.ajax({
			url:'AddBoatCard',
			type:'post',
			dataType:'json',
			data:{
				'certificate.cardName':$("#boatmanKindName").val(),
				'certificate.isDelete':'N'
			},
			success:function(data){
				if(data.totalPage==0){
					alert("新增成功！");
					closeDialogDiv();
					$("#dialogtitle").text("");
					$("#boatmanKindID").val("");
					$("#boatmanKindName").val("");
					ShowBoatcardListByPage('ShowBoatcardListByPage',General_page);
				}else{
					alert("证书名称不能重复");
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