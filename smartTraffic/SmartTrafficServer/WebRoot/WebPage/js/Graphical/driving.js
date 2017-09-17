var markerS, markerE;
		var map, routeQuery, linkQuery, langQuery;
		var id_count = 0;
		var bypass_array = [], avoidarea_array = [], avoidlink_array = [];
		var GET_ROUTE_INS = {};
		var langData;
		//修改路况地址
		TRAFFIC_PIV_URL = "http://115.231.73.253:8098/ROOT1/",
    	$$(function(){
    		map=new XMap("simpleMap",2);
    		//创建途经点图层
			map.passMarkerLayer=map.createPassMarkerLayer();
			map.addLayers([map.passMarkerLayer]);
			//添加动态路况
			map.trafficLayer.setVisibility(true);
			//添加卫星地图
			map.addNavType();
			//添加右键功能
			addRight();
			//创建语言控制
			var langQuery=map.createLanguage();
			//执行语言搜索
			langQuery.executeSearch(1);
    	});
    	/**
    	*创建右键菜单
    	*/
    	function addRight(){
			var rightMenu = new WebtAPI.WControl.WContextMenu({id:"rightMenu"});
			map.addContextMenu(rightMenu);
			
			var item1 = new WebtAPI.WMenuItem({
				id: "fromHere",
				showPic: true,
				picUrl: "qidian2.png",
				width: 100,
				text: "从这里出发",
				callback: function(){
					var lonlat = rightMenu.getCurrentLonlat();
					rightRoutePoint("start",lonlat);
				}
			});
			
			var item2 = new WebtAPI.WMenuItem({
				id: "toHere",
				width: 100,
				text: "到这里去",
				callback: function(){
					var lonlat = rightMenu.getCurrentLonlat();
					//geoSearch(lonlat, "endPoint");
					rightRoutePoint("end",lonlat);
				}
			});
			
			var item3 = new WebtAPI.WMenuItem({
				id: "avoid",
				width: 100,
				text: "途经点",
				callback: function(){
					var lonlat = rightMenu.getCurrentLonlat();
					rightRoutePoint("pass",lonlat);
				}
			});
			rightMenu.addMenuItem(item1);
			rightMenu.addMenuItem(item2);
			rightMenu.addMenuItem(item3);
			rightMenu.activate();
    	}
    	
    	/**地理编码查询**/
		function geoSearch(lonlat, args) {
			var lonlat=map.createLonlat2Second128(lonlat);
			$$("#"+args+"").val(lonlat);
		}
		
    	function executeSearch(type,thisop) {
    		$$(".select_"+type+"").addClass("main_left_select");
    		if(type==1){
    			$$(".select_2").removeClass("main_left_select");
    			$$(".select_4").removeClass("main_left_select");
    		}
    		if(type==2){
    			$$(".select_1").removeClass("main_left_select");
    			$$(".select_4").removeClass("main_left_select");
    		}
    		if(type==4){
    			$$(".select_1").removeClass("main_left_select");
    			$$(".select_2").removeClass("main_left_select");
    		}
			$$("#infoMenu").empty();
			map.clearLines();
			//判断是否有途经点
			//创建routeSearch
			var point = "";
			//起点
			point += $$("#staPoint").val();
			//途经点数组
			//途经点
			$$(".passPoint").each(function() {
				var passPoint = $$(this).val();
				if (passPoint != null) {
					point += "," + passPoint;
				}
			});
			//终点
			point += "," + $$("#endPoint").val();
			var avoid = "";
			//规避点
			$$(".areaValue").each(function() {
				var avoidArea = $$(this).val();
				if (avoidArea != null) {
					avoid += avoidArea + ",";
				}
			});
			var length = avoid.length - 1;
			var str = avoid.substring(0, length);
			//自驾方式
			var costModel = $$("#costModel").val();
			//创建驾车查询
			routeQuery = map.createRouteQuery(point,type,str);
			routeQuery.setArgs({
				type : "route",
				callback : "routeSearchCallback"
			});
			routeQuery.executeSearch();
		}
    	
    	/**驾车规划回调函数**/
		function routeSearchCallback(data, args) {
			var html;
			if(data["routeInfo"].length > 0){
				html = "<div class='route_item'>" +
				"	<div class='route_distance'>" +
				"		<span><b class='color_3'>全程</b>&nbsp;约" + data["routeInfo"][0]["distsum"]["dist"] + data["routeInfo"][0]["distsum"]["unit"] + "</span>" +
				"	</div>" +
				"	<ul class='route_list2'>";
				var distsum = data["routeInfo"][0]["distsum"]["dist"]
						+ data["routeInfo"][0]["distsum"]["unit"];
				var segmt = data["routeInfo"][0]["segmt"].length;
				for ( var i = 0; i < segmt; i++) {
					var coor = data["routeInfo"][0]["segmt"][i].clist.geometry.coordinates;
					map.drawLine(coor+"","blue","solid");
				}
				var dataList = data["routeInfo"][0]["segmt"];
				for ( var i = 0, len = dataList.length; i < len; i++) {
					var segmt = dataList[i];
					html += "<li>"
							+ "	<table cellpading='0' cellspacing='0' border='0' width='100%'>"
							+ "		<tr>" + "			<th>"
							+ (i + 1)
							+ "</th>"
							+ "			<td>"
							+ (i == 0 ? "从" : "沿")
							+ "<b style='color:#3A90FF;'>"
							+ ((i == 0) ? "起点"
									: (segmt["roadName"] ? (segmt["roadName"]["1"] ? segmt["roadName"]["1"]
											: (segmt["roadName"]["2"] ? segmt["roadName"]["2"]
													: ""))
											: "")
											+ "</b>")
							+ "向<b style='color:#F27489;'>"
							+ langData["dir"][segmt["dir"]]
							+ "</b>"
							+ "行驶<b style='font-weight:bold;'>"
							+ segmt["dist"]["dist"]
							+ "</b>"
							+ segmt["dist"]["unit"]
							+ "，"
							+ "<b>"
							+ langData["action"][segmt["action"]]
							+ "</b>"
							+ ((i + 1) == len ? ""
									: "进入<b style='color:#3A90FF;'>"
											+ (dataList[i + 1]["roadName"] ? (dataList[i + 1]["roadName"]["1"] ? dataList[i + 1]["roadName"]["1"]
													: (dataList[i + 1]["roadName"]["2"] ? dataList[i + 1]["roadName"]["2"]
															: ""))
													: "") + "</b>")
							+ ((segmt["addinfo"] != 0 && segmt["addinfo"] != undefined) ? ","
									+ langData["addinfo"][segmt["addinfo"]]
									: "")
							+ ((segmt["passinfo"] != 0 && segmt["passinfo"] != undefined) ? ",然后"
									+ langData["passinfo"][segmt["passinfo"]]
									: "")
							+ "			</td>"
							+ "		</tr>"
							+ "	</table>"
							+ "</li>";
				}
				$$("#infoMenu").append(html);
			}
		}
	
    	function switchPoint(type) {
			var pointImg = document.getElementById("routeSearchPointImg");
			if (!map || !type)
				return;
				//当传入点为起终点时，要清空页面原有起终点，保持唯一起终点
				var markers = wmap.markersLayer.markers;
				if (type.indexOf("start") != -1) {
					if (markerS != null) {
						wmap.markersLayer.removeMarker(markerS);
					}
					pointImg.value = "WebPage/image/driving/" + type + ".png";
				}
				if (type.indexOf("end") != -1) {
					if (markerE != null) {
						wmap.markersLayer.removeMarker(markerE);
					}
					pointImg.value = "WebPage/image/driving/" + type + ".png";
				}
				if (type.indexOf("pass") != -1) {
					pointImg.value = "WebPage/image/driving/" + type + ".png";
				}
			//为地图注册标注事件
			wmap.registerEvents("click", markerRoutePoint);
		}
	//地图标注时间------鼠标右键
	function rightRoutePoint(type,lonlat){
		var pointImg = document.getElementById("routeSearchPointImg");
		if (!map || !type)
		return;
		//当传入点为起终点时，要清空页面原有起终点，保持唯一起终点
		var markers = wmap.markersLayer.markers;
		if (type.indexOf("start") != -1) {
			if (markerS != null) {
				wmap.markersLayer.removeMarker(markerS);
			}
			pointImg.value = "WebPage/image/driving/" + type + ".png";
		}
		if (type.indexOf("end") != -1) {
			if (markerE != null) {
				wmap.markersLayer.removeMarker(markerE);
			}
			pointImg.value = "WebPage/image/driving/" + type + ".png";
		}
		if (type.indexOf("pass") != -1) {
			pointImg.value = "WebPage/image/driving/" + type + ".png";
		}
			
		var type = document.getElementById("routeSearchPointImg").value;
		//创建图标对象
		var icon=map.createIcon(type,30);
		if (type.indexOf("start") != -1) {
			$$("[name=start]").val(lonlat.lon+","+lonlat.lat);
			$$("#startPointLonLat").val("地图上的点");
			//创建标注
			markerS=map.createMarker(lonlat,icon);
			//在map中添加标注
			map.addMarkerObject(markerS);
			//转为128秒
			var lonlatstart=map.createLonlat2Second128(lonlat);
			$$("#staPoint").val(lonlatstart);
		} else if (type.indexOf("end") != -1) {
			$$("[name=end]").val(lonlat.lon+","+lonlat.lat);
			$$("#endPointLonLat").val("地图上的点");
			markerE=map.createMarker(lonlat,icon);
			map.addMarkerObject(markerE);
			var lonlatend=map.createLonlat2Second128(lonlat);
			$$("#endPoint").val(lonlatend);
		} else if (type.indexOf("pass") != -1) {
			var lonlatpass=map.createLonlat2Second128(lonlat);
			$$("#byPassInput")
					.append(
							"<div class='bypass_item'>"
									+ "<input type='text' onblur='valueChange(this,1)' class='passPointShow' value='地图上的点'>"
									+ "<input type='hidden' onblur='valueChange(this,1)' class='passPoint' value='"+ lonlatpass + "'>"
								    + "<span class='item_delete' onclick='deleteByPass(this.parentNode)'></span>"
								    + "</div>");
			var marker=map.createMarker(lonlat,icon);
			map.addMarkerObject(marker);
			//添加一个标记管理器
			map.passMarkerLayer.addMarker(marker);
		}
	}
	//地图标注事件------鼠标点击
	function markerRoutePoint(e) {
		var position = this.events.getMousePosition(e);
		var lonlat=map.getWGS84(position);
		var type = document.getElementById("routeSearchPointImg").value;
		//创建图标对象
		var icon=map.createIcon(type,30);
		if (type.indexOf("start") != -1) {
			$$("[name=start]").val(lonlat.lon+","+lonlat.lat);
			//创建标注
			markerS=map.createMarker(lonlat,icon);
			//在map中添加标注
			map.addMarkerObject(markerS);
			//转为128秒
			var lonlatstart=map.createLonlat2Second128(lonlat);
			$$("#staPoint").val(lonlatstart);
			$$("#startPointLonLat").val("地图上的点");
		} else if (type.indexOf("end") != -1) {
			$$("[name=end]").val(lonlat.lon+","+lonlat.lat);
			markerE=map.createMarker(lonlat,icon);
			map.addMarkerObject(markerE);
			var lonlatend=map.createLonlat2Second128(lonlat);
			$$("#endPoint").val(lonlatend);
			$$("#endPointLonLat").val("地图上的点");
		} else if (type.indexOf("pass") != -1) {
			var lonlatpass=map.createLonlat2Second128(lonlat);
			$$("#byPassInput")
					.append(
							"<div class='bypass_item'>"
									+ "<input type='text' onblur='valueChange(this,1)' class='passPointShow' value='地图上的点'>"
									+ "<input type='hidden' onblur='valueChange(this,1)' class='passPoint' value='"+ lonlatpass + "'>"
								    + "<span class='item_delete' onclick='deleteByPass(this.parentNode)'></span>"
								    + "</div>");
			var marker=map.createMarker(lonlat,icon);
			map.addMarkerObject(marker);
			//添加一个标记管理器
			map.passMarkerLayer.addMarker(marker);
		}
		clearSwitchPoint();
	}
	
	//清空选点相关信息
	function clearSwitchPoint() {
		var pointDiv = document.getElementById("routeSearchPoint");
		pointDiv.style.cssText = "";
		document.body.onmousemove = null;
		document.body.onkeydown = null;
		map.unregisterEvents("click", markerRoutePoint);
	}
	
	//删除途径点
	function deleteByPass(obj) {
		var idx = $$("#byPassInput div.bypass_item").index(obj);
		//删除图标上的途经点
		var marker = map.passMarkerLayer.markers[idx];
		map.passMarkerLayer.removeMarker(marker);
		$$("#byPassInput").get(0).removeChild(
				$$("#byPassInput div.bypass_item").eq(idx).get(0));
	}