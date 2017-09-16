$(document).ready(function() {
	ShowWharfBookList();
});
function ShowWharfBookList(){
	$("#booklist").empty();
	$.ajax( {
		url : "WharfCheckList",
		type : "post",
		dataType : "json",
		success : function(data) {
		var newDiv=$("#booklist");
        for(var i=0;i<data.list.length;i++){
        	newDiv.append($("<div>"+data.list[i].wharfBook+"<a onclick=DeleteWharfBook('"+data.list[i].wharfBook+"') class='a_delete'>[删除]</a></div>"));
        }		
	}
	});

}
function DeleteWharfBook(bookname){
	$.ajax( {
		url : "DeleteWharfBook",
		type : "post",
		dataType : "json",
		data:{
		'wharfBook.wharfBook':bookname
	},
		success : function(data) {
		alert("删除成功！");
		ShowWharfBookList();
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
		url : "AddWharfBook",
		type : "post",
		dataType : "json",
		data:{
		'wharfBook.wharfBook':sname
	},
		success : function(data) {
		alert("新增成功！");
		hiden();
		ShowWharfBookList();
	},
	error : function(XMLHttpRequest) {
		alert($(".error", XMLHttpRequest.responseText).text());
	}
	});

}