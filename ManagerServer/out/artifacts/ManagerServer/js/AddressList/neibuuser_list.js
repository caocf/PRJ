var General_List;
var General_page=1;
$(document).ready(function(){
	ShowBoatmanKindListByPage('AllUsersByType',1);
});
/**
 * 初始化列表加载方法
 * @param actionName：接口名
 * @param selectedPage：页码
 */
function ShowBoatmanKindListByPage(actionName,selectedPage){
	General_page=selectedPage;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'type':1
		},
		success:function(data){
			$(".listTable .addTr").empty();
			$(".listTable .addTr").remove();
			General_List=data.s1;
			if(General_List==null){
				TableIsNull();
			}else{
				pagingmake(actionName,'ShowBoatmanKindListByPage',selectedPage,data.resultcode);
				appendToTable(General_List,selectedPage);
			}
		}
	});
}
/**
 * 搜索框查询方法
 * @param actionName：接口名
 * @param selectedPage：页码
 */
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
			'page':selectedPage,
			'type':1,
			'key':search
		},
		success:function(data){
			$(".listTable .addTr").empty();
			$(".listTable .addTr").remove();
			General_List=data.s1;
			if(General_List==null){
				TableIsNull();
			}else{
				pagingmake(actionName,'SearchBoatmanKind',selectedPage,data.resultcode);
				appendToTable(General_List,selectedPage);
			}
		}
	});
}
/**
 * 列表为空显示方法
 */
function TableIsNull(){
	$(".addTr").empty();
	$(".addTr").remove();
	var newTr=$("<tr class='addTr'></tr>");
	newTr.append($("<td colspan='7' style='text-align:center'>暂无数据</td>"));
	$(".listTable").append(newTr);
	$("#pageDiv").hide();
}
/**
 * 加载列表
 * @param list：列表数据
 * @param selectedPage：页码
 */
function appendToTable(list,selectedPage){
	for(var i=0;i<list.length;i++){
		var status="<span style='color: red;'>禁用</span>";
		var btnstr='启用';
		if(list[i].status==1){
			status="<span style='color: green;'>正常</span>";
			btnstr='禁用';
		}
		var newTr=$("<tr class='addTr'></tr>");
		newTr.append($("<td class='firsttdorth'>"+(i+1+(selectedPage-1)*10)+"</td>"));
		newTr.append($("<td>"+list[i].username+"</td>"));
		newTr.append($("<td>"+list[i].tel+"</td>"));
		newTr.append($("<td>"+list[i].usertype.typename+"</td>"));
		newTr.append($("<td>"+list[i].registertime+"</td>"));
		newTr.append($("<td>"+status+"</td>"));
		newTr.append($("<td><a class='Operate' onclick='checkbyid("+list[i].usertype.id+")'>查看</a>" +
						"<a class='Operate' onclick='changestatus("+list[i].usertype.id+","+list[i].status+")'>"+btnstr+"</a></td>"));
		$(".listTable").append(newTr);
	}
}
/**
 * 改变状态
 * @param id:用户id
 * @param status：当前用户状态
 */
function changestatus(id,status){
	var newstatus=0;
	if(status==0){
		newstatus=1;
	}
	$.ajax({
		url:'setUserStatusByID',
		type:'post',
		dataType:'json',
		data:{
			'id':id,
			'status':newstatus
		},
		success:function(data){
			alert('修改成功');
			ShowBoatmanKindListByPage('AllUsersByType',General_page);
		}
	})
}
/**
 * 查看详情
 * @param id
 */
function checkbyid(id){
	window.location.href=$("#basePath").val()+'page/AddressList/neihe_xiangqing.jsp?type=1&id='+id;
}