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
			$("#table_list .addTr").empty();
			$("#table_list .addTr").remove();
			if (data.tables == null) {
				TableIsNull();
			} else {
				var tables = data.tables;
			
				for ( var i = 0; i < tables.length; i++) {
					var newTr = $("<tr class='addTr'></tr>");
				if(tables[i]!=null)
					newTr.append($("<td><label onclick=SelectTable('"+tables[i].name+"')>"+ tables[i].comment + "</label></td>"));

					$("#table_list").append(newTr);
				}
				$("#table_list tr:even").css("background-color", "#f6f6f6");
				$("#table_list tr:odd").css("background-color", "#fff");

			}

		},error : function(a, b, c) {
			alert("抱歉，无法获取数据");
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});

}
function SelectTable(tablename){
	window.location.href=$('#basePath').val()+"page/TableInfo.jsp?tablename="+tablename;
}