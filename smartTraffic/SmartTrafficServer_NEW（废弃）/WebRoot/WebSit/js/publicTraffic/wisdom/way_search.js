//地图上浮云的搜索
function ChangeSpan_Way(thisop,type){
	$$(".map_bus_div7_span1").attr("class","map_bus_div7_span");
	thisop.className="map_bus_div7_span1";
	$$(".map_bus_div7_content1").attr("class","map_bus_div7_content");
	$$("#map_bus_div7_content"+type).attr("class","map_bus_div7_content1");
	
}
//1:换乘，@lon,lat起点经纬度2：换乘，@lon,lat终点经纬度，3自驾，@lon,lat起点经纬度，4自驾，@lon,lat终点经纬度
function transfer_Way(type,LonLat,name){

	if(type==1){
		if($$("#map_bus_div7_cnt1").val()==""){
			alert("请输入内容");
		}else{
			window.open($$("#basePath").val()+"WebSit/page/publicTraffic/wisdom/transfer_search.jsp?startPoint="+
			name+"&endPoint="+$$("#map_bus_div7_cnt1").val()+"&startPointLonLat="+LonLat);
		}
	}else if(type==2){
		if($$("#map_bus_div7_cnt2").val()==""){
			alert("请输入内容");
		}else{
			window.open($$("#basePath").val()+"WebSit/page/publicTraffic/wisdom/transfer_search.jsp?startPoint="+
			$$("#map_bus_div7_cnt2").val()+"&endPoint="+name+"&endPointLonLat="+LonLat);
		}
	}else if(type==3){
		if($$("#map_bus_div7_cnt1").val()==""){
			alert("请输入内容");
		}else{
			window.open($$("#basePath").val()+"WebSit/page/Graphical/selfdriving_search.jsp?startPoint="+
			name+"&endPoint="+$$("#map_bus_div7_cnt1").val()+"&startPointLonLat="+LonLat);
		}
	}else if(type==4){
		if($$("#map_bus_div7_cnt2").val()==""){
			alert("请输入内容");
		}else{
			window.open($$("#basePath").val()+"WebSit/page/Graphical/selfdriving_search.jsp?startPoint="+
			$$("#map_bus_div7_cnt2").val()+"&endPoint="+name+"&endPointLonLat="+LonLat);
		}
	}
}
function searchKey_Way(){
	if($$("#map_bus_div7_cnt3").value==""){
		alert("请输入内容");
	}else{
		window.open($$("#basePath").val()+"WebSit/page/Graphical/key_search.jsp?KeyPoint="+$$("#map_bus_div7_cnt3").val());
	}

}
