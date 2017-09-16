var General_List;
var General_page=1;
$(document).ready(function(){
	ShowBoatmanKindListByPage('ALLShips',1);
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
			'page':selectedPage
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
			'type':3,
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
	newTr.append($("<td colspan='8' style='text-align:center'>暂无数据</td>"));
	$(".listTable").append(newTr);
}
/**
 * 加载列表
 * @param list：列表数据
 * @param selectedPage：页码
 */
function appendToTable(list,selectedPage){
	for(var i=0;i<list.length;i++){
		var status="<span style='color: orange;'>待审批</span>";
		var btnstr='审批';
		var onclick="onclick='choosebyid("+list[i].shipid+")'";
		if(list[i].englishname==1){
			status="<span style='color: red;'>驳回</span>";
			btnstr='查看';
			onclick="onclick='checkbyid("+list[i].shipid+")'";
		}
		if(list[i].englishname==2){
			status="<span style='color: green;'>通过</span>";
			btnstr='查看';
			onclick="onclick='checkbyid("+list[i].shipid+")'";
		}
		var newTr=$("<tr class='addTr'></tr>");
		newTr.append($("<td class='firsttdorth'>"+(i+1+(selectedPage-1)*10)+"</td>"));
		newTr.append($("<td>"+list[i].publicUserEN.username+"</td>"));
		newTr.append($("<td>"+list[i].publicUserEN.tel+"</td>"));
		newTr.append($("<td>"+list[i].publicUserEN.usertype.typename+"</td>"));
		newTr.append($("<td>"+list[i].shipname+"</td>"));
		newTr.append($("<td>"+list[i].publicUserEN.registertime+"</td>"));
		newTr.append($("<td>"+status+"</td>"));
		newTr.append($("<td><a class='Operate' "+onclick+">"+btnstr+"</a></td>"));
		$(".listTable").append(newTr);
	}
}
/**
 * 审核详情
 * @param shipid:船id
 */
function choosebyid(shipid){
	window.location.href=$("#basePath").val()+'page/AddressList/neibuuser_shenhe.jsp?shipid='+shipid;
}/**
 * 查看详情
 * @param shipid:船id
 */
function checkbyid(shipid){
	window.location.href=$("#basePath").val()+'page/AddressList/neibuuser_shenhe.jsp?shipid='+shipid;
}