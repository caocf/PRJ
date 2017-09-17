(function($,owner){
	owner.gaodeKey = "3a1316dfb76f09e882251604b588fdb7"
	owner.webServiceKey = "c1cf9e618a313260f0f5b42eec2c6907"
	owner.servicePath = "http://120.55.193.1:8080/SmartTrafficConvert/convert"
	
	owner.action = {
		QUERY_LINE_BY_NAME:"QueryOriginBusLine",
		QUERY_LINE_BY_ID:"QueryOriginBusLineByID",
		QUERY_ORIGIN_BUS_ARRIVE:"QueryOriginBusArrive",
		QUERY_ORIGIN_NEARBY_BUS_STATION:"QueryOriginNearByBusStation",
		QUERY_ORIGIN_BUS_STATION:"QueryOriginBusStation",
		QUERY_ORIGIN_BUS_STATION_BY_ID:"QueryOriginBusStationByID",
		QUERY_ORIGIN_TRAFFIC_TRASFER:"QueryOriginTrafficTrasfer",
		QUERY_ORIGIN_NEARBY_BIKE_STATION:"QueryOriginNearByBikeStation",
		QUERY_ORIGN_BIKE_PARKING_COUNT:"QueryOriginBikeParkingCount",
		QUERY_ORIGN_BIKE_STATION:"QueryOriginBikeStation"
	}
	
	owner.queryLineByName = function(data,callback){
		query(owner.action.QUERY_LINE_BY_NAME,data,callback)
	}
	
	owner.queryLineById = function(data,callback){
		query(owner.action.QUERY_LINE_BY_ID,data,callback)
	}
	owner.queryOriginBusArrive = function(data){
		var result = [];
		var a = query(owner.action.QUERY_ORIGIN_BUS_ARRIVE, data, null, false);
		if(a == "") {
			return [];
		}
		var data = JSON.parse(a)
		if(data != null && data.BusLocationList != null) {
			for(var i = 0; i < data.BusLocationList.length; i++) {
				result.push(data.BusLocationList[i].StationId)
			}
		}
		return result;
	}
	owner.queryOriginNearByBusStation = function(data,callback){
		query(owner.action.QUERY_ORIGIN_NEARBY_BUS_STATION,data,callback);
	}
	owner.queryOriginBusStation = function(data,callback){
		query(owner.action.QUERY_ORIGIN_BUS_STATION,data,callback);
	}
	
	owner.queryOriginBusStationByID = function(data,callback,isSy){
		return query(owner.action.QUERY_ORIGIN_BUS_STATION_BY_ID,data,callback,isSy);
	}
	
	owner.queryOriginTrafficTrasfer = function(data,callback){
		query(owner.action.QUERY_ORIGIN_TRAFFIC_TRASFER,data,callback);
	}
	owner.queryOriginNearByBikeStation = function(data,callback){
		query(owner.action.QUERY_ORIGIN_NEARBY_BIKE_STATION,data,callback);
	}
	owner.queryOriginBikeParkingCount = function(data,callback){
		query(owner.action.QUERY_ORIGN_BIKE_PARKING_COUNT,data,callback);
	}
	owner.queryOriginBikeStation = function(data,callback){
		query(owner.action.QUERY_ORIGN_BIKE_STATION,data,callback);
	}
	
	owner.convert = function(list,callback){
		var str = ""
		var result = []
		for(var i = 1;i<=list.length;i++){
			str = str + list[i-1][0] +","+list[i-1][1]+"|"
			if(i % 40 == 0){
				str = str.substr(0,str.length-1);
				var a = _convert(str,callback);
				for(var j = 0;j< a.length;j++ ){
					result.push(a[j]);
				}
				str = ""
				
			}
		}
		str = str.substr(0,str.length-1);
		var a = _convert(str,callback);
		for(var i = 0;i< a.length;i++ ){
			result.push(a[i]);
		}
		return result;
	}
	_convert = function(local,callback){
		var asy = false;
		if(callback != null){
			asy = true;
		}
		var result=[]
		$.ajax({
			type:"get",
			url:"http://restapi.amap.com/v3/assistant/coordinate/convert?key="+owner.webServiceKey+"&locations="+local+"&coordsys=gps",
			async:asy,
			success:function(data){
				if(asy){
					callback(data)
				}else{
					if(data.status == 1){
						var locations = data.locations;
						var lcList = locations.split(";");
						var point;
						for(var i = 0;i<lcList.length;i++){
							point = lcList[i].split(",");
							result.push(point);
						}
					}
				}
			}
		});
		return result;
	}
	
	query = function(action,data,callback,isAsync){
		if(isAsync == null){
			isAsync = true
		}
		callback = callback || $.noop
		console.log(action+":"+JSON.stringify(data));
		var queryData = {action:action,param:JSON.stringify(data)}
		var result;
		jQuery.ajax({
			type:"post",
			url:owner.servicePath,
			async:isAsync,
			data:queryData,
			timeout:30000,
			success:function(data){
				console.log("查询结果:"+data);
				if(isAsync){
					if(data == null || data == ""){
						callback({})
					}else{
						var r = JSON.parse(data);
						callback(r);
					}
				}else{
					result = JSON.parse(data)
				}
			},
			error:function(xhr,type,errorThrown){
				if(type == "timeout"){
					callback(false,"网络连接超时，请检查网络链接!");
					$.toast("网络连接超时，请检查网络链接!");
				}
				console.log("query error:"+JSON.stringify(xhr));	
				console.log("query type:"+JSON.stringify(type));	
				console.log("query errorThrown:"+JSON.stringify(errorThrown));	
			}
		});
		return result;
	}
}(mui, window.app = {}))
