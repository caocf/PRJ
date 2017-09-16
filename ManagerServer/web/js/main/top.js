$(document).ready(function() {
	
	$("#ul1 li a:first").addClass("topHoverClick");
	$("#ul1 li a font:first").css("color","#fff");
	//alert("main");
	
});
function a_top_onclick(id) {
	$("#ul1 li a").removeClass("topHoverClick");
	$("#ul1 li a font").css("color", "#16347a");
//	$("#ul1 li a").css("background-color", "#fff");
//	id.style.backgroundColor = "#1188d3";
	
	//$("#"+id).css("background-image ", "url(../../image/top/topHoverClick.png)");
	
	$("#"+id).addClass("topHoverClick");
	$("#"+id+" font").css("color","#fff");
	
}

function exit() {
	var r = confirm("退出登陆?");
	if (r == true) {
		var basePath = $("#basePath").val();
		parent.location.href = basePath + "page/login.jsp";
	}
	/*
	 * $.ajax( { url : 'ExitSystem', type : "post", dataType : "json", success :
	 * function(data) {} });
	 */
}
function exitMain() {
		var basePath = $("#basePath").val();
		parent.location.href = basePath + "page/main.jsp";
	}

