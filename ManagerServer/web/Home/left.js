$(document).ready(function()
{
	//var h=$(window).innerHeight;
	//$("#left").css("height",h);
	//alert("fdff");
	/*//power();
	$("#left ul li ul").hide(); 
	$('#left ul li:has(ul a)').each(function(i)
	{
		$(this).click(function()
		{
			$(this).children().slideUp(400);
			$(this).addClass("showLeftTopUl");
		});
	 
	});*/

});

function a_left_onclick(thiop)
{
	$(thiop).next().slideToggle("normal",function()
	{
		if($(thiop).next().is(':hidden'))
		{
			//alert(11);
			$(thiop).css("background","url('../../image/left/to_left.png') no-repeat 90% center");
		}
		else
		{
			$(thiop).css("background","url('../../image/left/down_button.png') no-repeat 90% center");
		}
		$(thiop).css("background-color","#337ab7");
	});
	$(thiop).next().find("a").css({"background-color":"rgb(222,236,247)","color":"black"});
	if($.trim($(thiop).text())=='意见箱'){
		$(".child").css({"background-color":"rgb(222,236,247)","color":"black"});
	}
	//thiop.parent().
	//$("#"+id).slideToggle();
	/*$("#item1").css("background-color","#7BC4FD");
	$("#item2").css("background-color","#7BC4FD");
	thiop.style.backgroundColor="#1188d3";

	//$("#left ul li").css("background-color","#dce9f2");
	//$("#left ul li a").css("color","#323944");

	//thiop.style.color="#dce9f2";
	//$("#left ul li a").css("list-style","none");
	if(id!=undefined)
	{
		$("#"+id).toggle();
	}*/
}
function a_left_onclick1(thiop)
{
	$(".child").css({"background-color":"rgb(222,236,247)","color":"black"});
	$(thiop).css({"background-color":"#1188d3","color":"white"});

	//$("#left ul li").css("background-color","#dce9f2");
	//$("#left ul li a").css("color","#323944");

	//thiop.style.color="#dce9f2";
	//$("#left ul li a").css("list-style","none");

}

function power() {
	var roleId = $("#roleId").val()
	$.ajax( {
		url : 'searchRolePermissionByRoleId',
		type : "post",
		dataType : "json",
		data : {
			'roleId' : roleId
		},
		success : function(data) {
			RolePermission(data.list);
		}
	});

}


function RolePermission(list){
	var minper=100;//默认权限从小到达的左侧导航
	$("#left ul li,#left ul .perssion_child div a").css("display", "none");
	$("#left ul #first,#left ul .perssion_child").css("display", "");
	for ( var i = 0; i < list.length; i++) {
		var pid=list[i][0].permissionId;
		if (list[i][0].status != 0&&$(".perssion" + pid).val()!=undefined) {
			if(minper>pid) minper=pid;
			$(".perssion" + pid).css("display", "");
		}
	}
	if(minper!=100){
	$(".rightpage").attr("src", $(".perssion"+minper+" a").attr("href"));
	$(".rightpage1").attr("src", $(".perssion"+minper+" a").attr("href"));
	$(".rightpage2").attr("src",$(".perssion"+minper+" a").attr("href"));
	$(".rightpage3").attr("src",$(".perssion"+minper+" a").attr("href"));
	}else{
		$(".rightpage").attr("src", "");
		$(".rightpage1").attr("src", "");
		$(".rightpage2").attr("src","");
		$(".rightpage3").attr("src","");
	}
}