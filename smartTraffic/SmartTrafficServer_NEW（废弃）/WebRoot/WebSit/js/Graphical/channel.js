var map;

$$(document).ready(function() {
	map = new XMap("simpleMap", 1);
	map.addPanZoomBar();
	InitializeTianMap();
});