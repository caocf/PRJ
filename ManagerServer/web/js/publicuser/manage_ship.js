$(document).ready(function() {
	ShowShipBookList();
});
function ShowShipBookList(){
	$("#booklist").empty();
	$.ajax( {
		url : "ShipCheckList",
		type : "post",
		dataType : "json",
		success : function(data) {
		var newDiv=$("#booklist");
        for(var i=0;i<data.list.length;i++){
        	newDiv.append($("<div>"+data.list[i].shipBook+"<a onclick=DeleteShipBook('"+data.list[i].shipBook+"') class='a_delete'>[删除]</a></div>"));
        }		
	}
	});

}
function DeleteShipBook(bookname){
	$.ajax( {
		url : "DeleteShipBook",
		type : "post",
		dataType : "json",
		data:{
		'shipBook.shipBook':bookname
	},
		success : function(data) {
		alert("删除成功！");
		ShowShipBookList();
	},
	error : function(XMLHttpRequest) {
		alert($(".error", XMLHttpRequest.responseText).text());
	}
	});


}
function OpenAddDialog(){
	$("#AddBook").show();
	$("#AddBook").dialog( {
		title : '新增船舶资料',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
function hiden(){
	$("#AddBook").dialog("close");
}
function AddBookSubmit(){
	var sname=$("#name").val();
	if(sname==""){
		alert("资料名称不能为空！");
		return;
	}
	if (/[~#^$@%&!*\s*]/.test(sname)) {
		alert("资料名称不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : "AddShipBook",
		type : "post",
		dataType : "json",
		data:{
		'shipBook.shipBook':sname
	},
		success : function(data) {
		alert("新增成功！");
		hiden();
		ShowShipBookList();
	},
	error : function(XMLHttpRequest) {
		alert($(".error", XMLHttpRequest.responseText).text());
	}
	});

}