var basePath;
$$(document).ready(function() {
	basePath = $$("#basePath").val();
	if ($$("#menu_number").val() == "null") {
		Status(1);
	} else {
		var menu_number=$$("#menu_number").val();
		if(menu_number!="0"){
			Status(menu_number);
		}
		
	}
});
function CreatePage1() {
	window.location.href = basePath + "WebSit/page/publicTraffic/wisdom/road_search.jsp";
	Status(1);
}

function CreatePage2() {
	window.location.href =  basePath+"WebSit/page/publicTraffic/bicycle/ConstructionSurvey.jsp";
	Status(2);
}
function CreatePage3() {
	window.location.href =  basePath+"WebSit/page/Passenger/TicketOutlet.jsp";
	Status(3);
}
function CreatePage4() {
	window.location.href =  basePath+"WebSit/page/publicTraffic/airline/ticket.jsp";
	Status(4);
}
function CreatePage5() {
	window.location.href =  basePath+"WebSit/page/publicTraffic/railway/importport.jsp";
	Status(5);
}
function CreatePage6() {
	window.location.href =  basePath+"WebSit/page/Graphical/self_driving.jsp";
	Status(6);
}
function CreatePage7() {
	window.location.href =  basePath+"WebSit/page/main/LiveTraffic.jsp";
	Status(7);
}
function CreatePage8() {
	window.location.href =  basePath+"WebSit/page/taxiInformation/TaxiInfor.jsp";
	Status(8);
}
function CreatePage9() {
	window.location.href =  basePath+"WebSit/page/knowledge/knowledge.jsp";
	Status(9);
}
/*
function CreatePage7() {
	window.location.href =  basePath+"WebSit/page/Graphical/parking.jsp";
	Status(7);
}*/

function Status(n1) {

	$$("#top_menu .m_"+n1).css("background", "url('"+basePath+"WebSit/image/main/menu_select_line.png') no-repeat");
	$$("#top_menu .m_"+n1).css("font-weight", "bold");
	$$("#top_menu .m_"+n1).css("background-position", "bottom");


}
