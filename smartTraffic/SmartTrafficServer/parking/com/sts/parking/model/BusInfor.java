package com.sts.parking.model;

public class BusInfor implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String routeId;// 线路代码由4位线路标识码+6位行政区划代码组成
	private String routeName; // 线路名称或关键字
	private String busStationId; // 站点代码
	private String busStationName; // 站点名称或关键字
	private String longitude; // double poi点经度
	private String latitude;// double poi点纬度

	private String startpointlon;// double 起点经度
	private String startpointlat;// double 起点纬度
	private String endpointlon;// double 终点经度
	private String endpointlat; // double 终点纬度
	private String order;// Integer 排序方式。1：少换乘。2：时间短。3少步行。4.价格少 5.总距离短 返
							// 回方案按换乘次数返回，需要调用 方进行排序
	private String count;// Integer 返回方案条数
	
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getBusStationId() {
		return busStationId;
	}
	public void setBusStationId(String busStationId) {
		this.busStationId = busStationId;
	}
	public String getBusStationName() {
		return busStationName;
	}
	public void setBusStationName(String busStationName) {
		this.busStationName = busStationName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getStartpointlon() {
		return startpointlon;
	}
	public void setStartpointlon(String startpointlon) {
		this.startpointlon = startpointlon;
	}
	public String getStartpointlat() {
		return startpointlat;
	}
	public void setStartpointlat(String startpointlat) {
		this.startpointlat = startpointlat;
	}
	public String getEndpointlon() {
		return endpointlon;
	}
	public void setEndpointlon(String endpointlon) {
		this.endpointlon = endpointlon;
	}
	public String getEndpointlat() {
		return endpointlat;
	}
	public void setEndpointlat(String endpointlat) {
		this.endpointlat = endpointlat;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

}
