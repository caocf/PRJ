//设置当前频道
var def = 1;
var basePath="";
$$(document).ready(function() {
	if($$("#basePath").val()!=undefined)
	basePath = $$("#basePath").val();
	if ($$("#menu_number").val() != "null") {
		var menu_number=$$("#menu_number").val();
		if(menu_number!="0"){
			Status(menu_number);
		}else{
			def = 0;
		}
		
	}
});

function mover(object) {
	// 主菜单
	var mm = document.getElementById("m_" + object);
	mm.className = "m_li_a";

	// 初始主菜单先隐藏效果
	if (def != object) {
		var mdef = document.getElementById("m_" + def);
		mdef.className = "m_li";
	}
	$$(".s_li").css("display","none");
	// 子菜单
	var ss = document.getElementById("s_" + object);
	ss.style.display = "block";
	// 初始子菜单先隐藏效果
	if (def != object) {
		var sdef = document.getElementById("s_" + def);
		sdef.style.display = "none";
	}
}

function mout(object) {
	// 主菜单
	var mm = document.getElementById("m_" + object);
	mm.className = "m_li";
	var mdef = document.getElementById("m_" + def);
	mdef.className = "m_li_a";
	// 子菜单
/*	var ss = document.getElementById("s_" + object);
	ss.style.display = "none";
	var sdef = document.getElementById("s_" + def);
	sdef.style.display = "block";*/

}
//三级菜单
function mover3(m1,m2) {
	$$(".c_s_li").css("display","none");
	var ss = document.getElementById("c_s_" + m1+"_"+m2);
	if(ss!=undefined)
	ss.style.display = "block";
}
function mover4(m1,m2) {
	$$("#c_s_"+ m1+"_"+m2).css("display","block");
}

function mout4(m1,m2) {
	$$("#c_s_"+ m1+"_"+m2).css("display","none");
}

function Status(menu_number) {
	var n1=0, n2=0, n3=0;
	if(menu_number.split(",").length==2){
		n1=menu_number.split(",")[0];
		 n2=menu_number.split(",")[1];
	}else if(menu_number.split(",").length==3){
		n1=menu_number.split(",")[0];
		 n2=menu_number.split(",")[1];
		 n3=menu_number.split(",")[2];
	}
	$$("#top_menu .m_"+n1).css("background", "url('"+basePath+"WebPage/image/main/menu_select_line.png') no-repeat");
	$$("#top_menu .m_"+n1).css("font-weight", "bold");
	$$("#top_menu .m_"+n1).css("background-position", "bottom");
	$$("#top_menu #s_"+n1+"_"+n2).css("color","#5e79ec");
	$$("#top_menu #s_"+n1+"_"+n2+"_"+n3).css("color","#5e79ec");
}
