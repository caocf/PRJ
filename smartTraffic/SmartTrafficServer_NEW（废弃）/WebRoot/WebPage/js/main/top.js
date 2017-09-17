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

function CreatePage1_1_1() {
	window.location.href = "http://www.jxjtj.gov.cn/";
}
function CreatePage2_1_1() {
	window.location.href =  basePath+"WebPage/page/publicTraffic/wisdom/road_search.jsp";
}
function CreatePage2_1_2() {
	window.location.href =  basePath+"WebPage/page/publicTraffic/bicycle/ConstructionSurvey.jsp";
}
function CreatePage2_1_3() {
	window.location.href =  basePath+"WebPage/page/Passenger/TicketOutlet.jsp";
}
function CreatePage2_1_4() {
	window.location.href =  basePath+"WebPage/page/taxiInformation/TaxiInfor.jsp";
}
function CreatePage2_1_5() {
	//window.location.href =  basePath+"WebPage/page/publicTraffic/railway/ticket.jsp";
	window.location.href = "http://www.12306.cn/mormhweb/";
}
function CreatePage2_1_6() {
	//window.location.href =  basePath+"WebPage/page/publicTraffic/airline/ticket.jsp";
	window.location.href = "http://flights.ctrip.com/";
}

function CreatePage2_2_1() {
	//window.location.href = "http://115.231.73.253/jxtpi/public_index";
	window.location.href = basePath+"WebPage/page/main/LiveTraffic.jsp";
}
function CreatePage2_2_2() {
	window.location.href = "http://61.130.7.232:7002/gis3/";
}
function CreatePage2_2_3() {
	window.location.href =  basePath+"WebPage/page/main/waterWay.jsp";
}
function CreatePage2_3_1() {
	window.location.href =   basePath + "WebPage/page/Graphical/self_driving.jsp";
}
function CreatePage2_3_2() {
	window.location.href =   basePath + "WebPage/page/Graphical/multiple.jsp";
}
function CreatePage2_3_3() {
	window.location.href =   basePath + "WebPage/page/Graphical/FreightTraffic.jsp";
}
function CreatePage2_4_1() {
	window.location.href =  basePath + "WebPage/page/Graphical/parking.jsp";
}
function CreatePage2_4_2() {
	window.location.href =  basePath + "WebPage/page/Graphical/driving.jsp";
}
function CreatePage3_1_1() {
	window.location.href = basePath + "WebPage/page/carRepair/dot.jsp";
}


function CreatePage4_1_1() {
	window.location.href = basePath+"WebPage/page/carTraining/dynamic.jsp";

}
function CreatePage4_2_1() {
	window.location.href = basePath+"WebPage/page/carTraining/drivingIntSec.jsp";

}
function CreatePage4_3_1() {
	window.location.href = basePath+"WebPage/page/carTraining/drivingApplication.jsp";
}
function CreatePage4_4_1() {
	window.location.href = basePath+"WebPage/page/carTraining/drivingSelect.jsp";

}
function CreatePage5_1_1() {
	window.location.href = basePath+"WebPage/page/trafficNews/trafficNews.jsp";
}

function CreatePage6_1_1() {
	window.location.href = basePath+"WebPage/page/illegal/illegal.jsp";
}
function CreatePage6_2_1() {
	window.location.href = basePath+"WebPage/page/illegal/illegal_OC.jsp";
}
function CreatePage7_1_1() {
	window.location.href = "http://www.jxjtj.gov.cn/page-446066119604.aspx";
}
function CreatePage7_2_1() {
	window.location.href ="http://www.jxjtj.gov.cn/page-446066119604.aspx";
}
function CreatePage8_1_1() {
	window.location.href =basePath+"WebPage/page/about/about.jsp";
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
