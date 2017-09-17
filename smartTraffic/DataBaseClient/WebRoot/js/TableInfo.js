var sTable="";
var tableNameText="";
var thnum=0;
var sPage=0;//选择页面
var columnTypeArray=new Array();
var pri_insert=false;//新增权限
var pri_update=false;//修改权限
var pri_delete=false;//删除权限
$(document).ready(function() {
	queryAllTable();
});
function queryAllTable() {

	$.ajax( {
		url : "queryAllTable",
		type : "post",
		dataType : "json",
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			$("#SelectTable").empty();

			if (data.tables == null) {
				TableIsNull();
			} else {
				var tables = data.tables;
				var SelectTable = document.getElementById("SelectTable");
				for ( var i = 0; i < tables.length; i++) {
					if (tables[i] != null){
						if (tables[i].comment != "null"&&tables[i].comment != null){
							SelectTable.options.add(new Option(tables[i].comment,tables[i].name));
						}	
					}
				}
				
				//初始化客户端表格
				if($("#tNum").val()!="null"){
					$("#SelectTable").val($("#tNum").val());
				}else if(getCookie(Cookie_tNum)!=undefined){
					var tNum=getCookie(Cookie_tNum);
					if(tNum!=""&&tNum!=null){
						$("#SelectTable").val(tNum);
					}	
				}
				SelectOneTable(true);
			}
		},
		error : function(a, b, c) {
			alert("抱歉，无法获取数据");
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});

}
function SelectOneTable(isInit){
	sTable=$("#SelectTable").val();
	tableNameText=$("#SelectTable").find("option:selected").text();
	queryColumnByTableName(isInit);
	
}
function queryColumnByTableName(isInit){
	pri_insert=false;//新增权限
	pri_update=false;//修改权限
	pri_delete=false;//删除权限
	$.ajax( {
		url : "queryColumnByTableName",
		type : "post",
		dataType : "json",
		data : {
			'tableName' : sTable
		},
		
		success : function(data) {
			
			if (data.content != null) {
				for ( var i = 0; i < data.content.length; i++) {
					if (data.content[i].privailege == "UPDATE") {
						pri_update = true;
					} else if (data.content[i].privailege == "DELETE") {
						pri_delete = true;
					} else if (data.content[i].privailege == "INSERT") {
						pri_insert = true;
					}
				}	
			}
			//新增按钮
			if(pri_insert){
				$("#AddButton").css("display","");
			}else{
				$("#AddButton").css("display","none");
			}
			if (data.columns == null) {
				TableIsNull();
			} else {
				var columns = data.columns;
				var html ="<tr>";
				thnum=columns.length+2;
				html+="<th>操作</th><th>序号</th>";
				for ( var i = 0; i < columns.length; i++) {
					columnTypeArray[i]=columns[i][0].columnType;
					if (columns[i] != null){
						if (columns[i][1].columnComment != null)
							html+="<th>" + columns[i][1].columnComment+ "</th>";
						else
							html+="<th>" + columns[i][0].columnName+ "</th>";
					}
						
				}
				$("#table_data").html(html+"</tr>");
				if(isInit==true){
					//初始化客户端表格
					if($("#pNum").val()!="null"){
						queryContetnByTableName("queryContetnByTableName", $("#pNum").val());
					}else if(getCookie(Cookie_pNum)!=undefined){
						var pNum=getCookie(Cookie_pNum);
						if(pNum!=""&&pNum!=null){
							queryContetnByTableName("queryContetnByTableName", pNum);
						}else{
							queryContetnByTableName("queryContetnByTableName", 1);
						}	
					}else{
						queryContetnByTableName("queryContetnByTableName", 1);
					}
				}else{
					queryContetnByTableName("queryContetnByTableName", 1);
				}
			}

		},
		error : function(a, b, c) {
			alert("抱歉，无法获取数据");
		}
	});
}
function selectAll(type){
	if(type.checked==true){
		$(":checkbox").attr("checked","checked");
	}
	else{
		$(":checkbox").attr("checked","");
	}
}
// http://localhost:8080/EditDataBase/queryColumnByTableName?tableName=Z_CL_BXKYCJBXX

function queryContetnByTableName(actionName, selectedPage) {
	sPage=selectedPage;
$("#table_data .addTr").empty();
$("#table_data .addTr").remove();
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'tableName' : $("#SelectTable").val(),
			'page':selectedPage,
			'num':15
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {

			if (data.content == null) {
				TableIsNull();
			} else {
				var content = data.content;
				for ( var i = 0; i < content.length; i++) {
					var newTr=$("<tr class='addTr'></tr>");
					var prihtml="";
					//新增按钮
					if(pri_update){
						prihtml="&nbsp;<a onclick=UpdateDate('"+content[i][content[i].length-2]+"') class='operation' >修改</a>";
					}
					if(pri_delete){
						prihtml+="<a onclick=DeleteDate('"+content[i][content[i].length-2]+"') class='operation' >删除</a>";
					}
					newTr.append($("<td>" +prihtml+"</td>"));
					newTr.append($("<td>"+(i+1)+"</td>"));
					for ( var j = 0; j < content[i].length-2; j++) {
						if(columnTypeArray[j]=="DATE"){
							newTr.append($("<td>"+DateIsNull(content[i][j]).replace("T"," ")+"</td>"));
						}else{
						newTr.append($("<td>"+DateIsNull(content[i][j])+"</td>"));
						}
					}
					$("#table_data").append(newTr);
				}
				if (content.length == 0) {
					TableIsNull();
				} 
			}
			var count= Math.ceil(data.count/15);
			gotoPageMethodName = "gotoPageNo";
			printPage(count, selectedPage, 'queryContetnByTableName',
					actionName, gotoPageMethodName, data.count);
		},
		error : function(a, b, c) {
			alert("抱歉，无法获取数据");
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}
// 数据为空
function TableIsNull() {
	$("#table_data .addTr").empty();
	$("#table_data .addTr").remove();
	newTr = $("<tr class='addTr'><td colspan="+thnum+" align='center'>暂无相关数据</td></tr>");
	$(".listTable").append(newTr);
	$("#pageDiv").hide();

}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").val();
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").val("");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		queryContetnByTableName(actionName, pageNo);
	}
}
function UpdateDate(rowid){
	addCookie(Cookie_pNum,sPage);
	addCookie(Cookie_tNum,sTable);
	window.location.href=$('#basePath').val()+"page/TableUpdate.jsp?rowid="+rowid+"&tableNameText="+tableNameText+"&pNum="+sPage+"&tNum="+sTable;
}
function AddDate(){
	addCookie(Cookie_pNum,sPage);
	addCookie(Cookie_tNum,sTable);
	window.location.href=$('#basePath').val()+"page/TableAdd.jsp?tableNameText="+tableNameText+"&pNum="+sPage+"&tNum="+sTable;;
}
function DeleteDate(rowid){
if(confirm("确定删除该条数据吗？")){
	$.ajax( {
		url : "deleteByTableAndID",
		type : "post",
		dataType : "json",
		data : {
			'tableName' : sTable,
			'rowid':rowid
		},
		
		success : function(data) {
			alert("删除成功！");
			queryContetnByTableName("queryContetnByTableName",sPage);
		},
		error : function(a, b, c) {
			alert("抱歉，删除失败");
		}
	});
}
}