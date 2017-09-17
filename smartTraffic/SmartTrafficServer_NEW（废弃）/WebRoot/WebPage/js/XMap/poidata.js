
/**
 * 1关键字查询
 * 
 * @param key
 *           关键字
 */
function searchPoiByKeyData(key,startPosition,pageNumber) {
	map.delWindow();
	$$("#searchresult").empty();
	var url;
	if((typeof(startPosition)=='undefined')&&(typeof(pageNumber)=='undefined')){
		url="ajax/searchpoibykey?key="+key;
	}
	else if((typeof(startPosition)=='undefined')&&(typeof(pageNumber)!='undefined')){
		url="ajax/searchpoibykey?key="+key+"&pageNumber="+pageNumber;
	}
	else if((typeof(pageNumber)=='undefined')&&(typeof(startPosition)!='undefined')){
		url="ajax/searchpoibykey?key="+key+"&startPosition="+startPosition;
	}
	else{
		url="ajax/searchpoibykey?key="+key+"&pageNumber="+pageNumber+"&startPosition="+startPosition;
	}
	$$.get(url,function(data){
		parsePoiDataMap(data);
		parsePoiDataInfo(data);
	},'json');
}
/**
 * 分类查询
 * @param poiCat 类别编号
 * @param imageIcon
 * @param imageSize
 * @param startPostion  起始位置
 * @param pageNumber    每页显示的条数
 */
function searchPoiByCatData(poiCat,imageIcon,imageSize,startPostion,pageNumber) {
	$$("#searchresult").empty();
	$$("#pagebutton").css("display","block");
	map.delWindow();
	var url = "ajax/searchpoibycat?poiCat="+poiCat+"&startPosition="+startPosition+"&pageNumber="+pageNumber;
	$$.get(url, function(data) {
		parsePoiDataMap(data);
		parsePoiDataInfo(data);
	}, 'json');
}
/**
 * 区块查询
 * @param loc  中心点经纬度
 * @param m    查询区域
 * @param key  关键字
 */
function searchBySurroundData(loc,m,key,startPosition,pageNumber){
	map.delWindow();
	var url;
	$$("#searchresult").empty();
	$$("#leftInfo").append("<div class='infoMenu'><div id='searchresult'></div></div>");
	if(key.indexOf("停车场")!=-1){
		url="ajax/queryparkingplacebysurround?lonlat="+loc+"&m="+m;
		$$.get(url,function(data){
			parseParkingPlaceDataMap(data);
			parseParkingPlaceDataInfo(data);
		},'json');
	}else{
		url="ajax/searchsurround?loc="+loc+"&m="+m+"&key="+key+"&startPosition="+startPosition+"&pageNumber="+pageNumber;
		$$.get(url,function(data){
			parsePoiDataMap(data);
			parsePoiDataInfo(data);
		},'json');
	}
	//添加矩形框
	var url1="ajax/searchlinestring?loc="+loc+"&m="+m;
	$$.get(url1,function(data){
		var lineString=data.lineString;
		var str=lineString.split(" ");
		lineString=str[0]+";"+str[1]+";"+str[2]+";"+str[3]+";"+str[4];
		//map.drawLine(lineString, "#000000", "solid");
		//转化率为0.00001
		map.drawRadio(loc,0.00001*m);
	},'json');
}
/**
 * 停车场查询
 * @param regionCode   行政区划名
 * @param parkType     停车场类别
 * @param chargeWay    收费方式
 */
//定义全局的停车场停息,以便调用setTimeout()
var regionCode;
var parkType;
var chargeWay;
var startPosition;
var pageNumber;
function searchParkingPlaceData(region,type,way,start,number){
	if(typeof(region)!='undefined'){
		regionCode=region;
		parkType=type;
		chargeWay=way;
		startPosition=start;
		pageNumber=number;
	}
	map.delWindow();
	$$(".infoMenu").remove();
	$$("#leftInfo").append("<div class='infoMenu'><div id='searchresult'></div></div>");
	//var url="ajax/queryparkingplace?parkType="+parkType+"&regionCode="+regionCode+"&chargeWay="+chargeWay+"&startPosition="+startPosition+"&pageNumber="+pageNumber;
	var url="ajax/queryparkingplacecoordinate?parkType="+parkType+"&regionCode="+regionCode+"&chargeWay="+chargeWay+"&startPosition="+startPosition+"&pageNumber="+pageNumber;
	$$.get(url,function(data){
		/*setTimeout(searchParkingPlaceData,50000);*/
		parseParkingPlaceDataMap(data);
		parseParkingPlaceDataInfo(data);
	},'json');
}
/**
 * 按行政区划、码头等级、码头类型查询码头
 * @param regionCode  行政区划代码
 * @param wharfLevel  码头等级
 * @param wharfType   码头类型
 */
function searchWharfPlaceData(regionCode,wharfType,wharfLevel){
	map.delWindow();
	$$(".infoMenu").remove();
	$$("#leftInfo").append("<div class='infoMenu'><div id='searchresult'></div></div>");
	//var url="ajax/querywharf?regionCode="+regionCode+"&wharfType="+wharfType+"&wharfLevel="+wharfLevel;
	var url="ajax/querywharfcoordinate?regionCode="+regionCode+"&wharfType="+wharfType+"&wharfLevel="+wharfLevel;
	$$.get(url,function(data){
		parseWharfPlaceDataMap(data,"images/marker/marker2.png");
		parseWharfPlaceDataInfo(data,"images/marker/marker2.png");
	},'json');
}
/**
 * 解析码头数据(地图显示)
 * @param data
 */
function parseWharfPlaceDataMap(data,imageIcon){
	var length=data.length;
	for(var i=0;i<length;i++){
		var lon=data[i].lon;
		var lat=data[i].lat;
		var name=data[i].wharfName;
		var wharfId=data[i].wharfId;
		var wharfForm=data[i].wharfForm;
		var wharfUse=data[i].wharfUse;
		var managerOrg=data[i].managerOrg;
		var berthNum=data[i].berthNum;
		var maxCargoThroughput=data[i].maxCargoThroughput;
		var wharfLevel=data[i].wharfLevel;
		var wharfType=data[i].wharfType;
		var lineString=lon+","+lat;
		var content="<div id='winInfo'>"
			+"<div>name："+name+"</div>"
			+"<div>wharfId："+wharfId+"</div>"
			+"<div>wharfForm："+wharfForm+"</div>"
			+"<div>wharfUse："+wharfUse+"</div>"
			+"<div>managerOrg："+managerOrg+"</div>"
			+"<div>berthNum："+berthNum+"</div>"
			+"<div>maxCargoThroughput："+maxCargoThroughput+"</div>"
			+"<div>wharfLevel："+wharfLevel+"</div>"
			/*+"<div>typeMsg："+wharfType+"</div>"*/
		+"</div>";
		map.setCenter(lineString,13);
		map.createPopupForMarker(lineString,content,imageIcon,30,300,300);
	}
}
/**
 * 解析码头数据(信息显示)
 * @param data
 */
function parseWharfPlaceDataInfo(data,imageIcon){
	var length=data.length;
	for(var i=0;i<length;i++){
		var lon=data[i].lon;
		var lat=data[i].lat;
		var name=data[i].wharfName;
		var wharfId=data[i].wharfId;
		var wharfForm=data[i].wharfForm;
		var wharfUse=data[i].wharfUse;
		var managerOrg=data[i].managerOrg;
		var berthNum=data[i].berthNum;
		var maxCargoThroughput=data[i].maxCargoThroughput;
		var wharfLevel=data[i].wharfLevel;
		var wharfType=data[i].wharfType;
		var lineString=lon+","+lat;
		$$("#searchresult").append("<div id='divMarker"+i+"'></div>");
		var content="<div id='winInfo'>"
			+"<div>name："+name+"</div>"
			+"<div>wharfId："+wharfId+"</div>"
			+"<div>wharfForm："+wharfForm+"</div>"
			+"<div>wharfUse："+wharfUse+"</div>"
			+"<div>managerOrg："+managerOrg+"</div>"
			+"<div>berthNum："+berthNum+"</div>"
			+"<div>maxCargoThroughput："+maxCargoThroughput+"</div>"
			+"<div>wharfLevel："+wharfLevel+"</div>"
			/*+"<div>typeMsg："+wharfType+"</div>"*/
		+"</div>";
		map.setCenter(lineString,13);
		parseParkingDataHepler(lineString,content,name,managerOrg,i,imageIcon);
	}
}
/**
 * 解析停车场数据(对于地图)
 * @param data
 */
function parseParkingPlaceDataMap(data){
	var length=data.length;
	var image;//设置停车场的图标
	for(var i=0;i<length;i++){
		var lon=data[i].lon;
		var lat=data[i].lat;
		var lineString=lon+","+lat;
		var lonlat="\""+lineString+"\"";
		var name=data[i].name;
		var address=data[i].address;
		var totalCount=data[i].totalCount;
		var freeCount=data[i].freeCount;
		var updateTime=data[i].updatetime;
		var parkId=data[i].parkId;
		var region=data[i].region;
		var code=data[i].code;
		var tollId=data[i].tollId;
		var typeId=data[i].typeId;
		//判断停车场的空满度
		var percent=(totalCount-freeCount)/totalCount;
		var img=freeCount+".png";
		if(percent<0.3){
			information="free";
			image="images/parking/free/"+img+"";
		}else if(percent<0.6){
			information="mid";
			image="images/parking/mid/"+img+"";
		}else if(percent<1){
			information="few";
			image="images/parking/few/"+img+"";
		}else{
			information="no";
			image="images/parking/no.png";
		}
		var photo="http://58.246.138.178:8040/jxsm/PoiThumbnailImageHandler.ashx?POI=8051630";
		var content="<div id='winInfo'>"
			+"<div id='winTitle'>"+name+"</div>"
			+"<div id='winContent'>"
				+"<table>"
					+"<tr>"
					+"<th class='content1' >编号</th>"
					+"<td class='content2'>"+parkId+"</td>"
					+"<td class='content3' rowspan='3'><img src='"+photo+"' width='100px' height='100px'></img></td>"
					+"</tr>"
					+"<tr>"
					+"<th class='content1'>总车位</th>"
					+"<td class='content2'>"+totalCount+"</td>"
					+"</tr>"
					+"<tr>"
					+"<th class='content1'>空余车位</th>"
					+"<td class='content2'>"+freeCount+"</td>"
					+"</tr>"
				+"</table>"
			+"</div>"	
			+"<div id='winSearch'>"
				+"<ul class='winul'>"
				+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='tohere()'>到这里去</li>"
				+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='fromhere()'>从这里出发</li>"
				+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='surround("+lonlat+")'>在附近找</li>"
				+"</ul>"
				+"<div class='winfoot'>"
					+"起点<input name='start'><button>公交</button><button>驾车</button>"
				+"</div>"
			+"</div>"
		+"</div>";
		map.setCenter(lineString,13);
		map.createPopupForMarker(lineString,content,image,30,300,300);
	}
}
/**
 * 解析停车场数据(用于显示)
 * @param data
 */
function parseParkingPlaceDataInfo(data){
	var length=data.length;
	var image;//设置停车场的图标
	for(var i=0;i<length;i++){
		var lon=data[i].lon;
		var lat=data[i].lat;
		var lineString=lon+","+lat;
		var lonlat="\""+lineString+"\"";
		var name=data[i].name;
		var address=data[i].address;
		var totalCount=data[i].totalCount;
		var freeCount=data[i].freeCount;
		var updateTime=data[i].updatetime;
		var parkId=data[i].parkId;
		var region=data[i].region;
		var code=data[i].code;
		var tollId=data[i].tollId;
		var typeId=data[i].typeId;
		//显示菜单所对应的div
		$$("#searchresult").append("<div id='divMarker"+i+"'></div>");
		
		//判断停车场的空满度
		var percent=(totalCount-freeCount)/totalCount;
		var img=freeCount+".png";
		if(percent<0.3){
			information="free";
			image="images/parking/free/"+img+"";
		}else if(percent<0.6){
			information="mid";
			image="images/parking/mid/"+img+"";
		}else if(percent<1){
			information="few";
			image="images/parking/few/"+img+"";
		}else{
			information="no";
			image="images/parking/no.png";
		}
		var photo="http://58.246.138.178:8040/jxsm/PoiThumbnailImageHandler.ashx?POI=8051630";
		var content="<div id='winInfo'>"
			+"<div id='winTitle'>"+name+"</div>"
			+"<div id='winContent'>"
				+"<table>"
					+"<tr>"
					+"<th class='content1' >编号</th>"
					+"<td class='content2'>"+parkId+"</td>"
					+"<td class='content3' rowspan='3'><img src='"+photo+"' width='100px' height='100px'></img></td>"
					+"</tr>"
					+"<tr>"
					+"<th class='content1'>总车位</th>"
					+"<td class='content2'>"+totalCount+"</td>"
					+"</tr>"
					+"<tr>"
					+"<th class='content1'>空余车位</th>"
					+"<td class='content2'>"+freeCount+"</td>"
					+"</tr>"
				+"</table>"
			+"</div>"	
			+"<div id='winSearch'>"
				+"<ul class='winul'>"
				+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='tohere()'>到这里去</li>"
				+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='fromhere()'>从这里出发</li>"
				+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='surround("+lonlat+")'>在附近找</li>"
				+"</ul>"
				+"<div class='winfoot'>"
					+"起点<input name='start'><button>公交</button><button>驾车</button>"
				+"</div>"
			+"</div>"
		+"</div>";
		map.setCenter(lineString,13);
		parseParkingDataHepler(lineString,content,name,address,i,image);
	}
}
//poi数据、用于地图显示
function parsePoiDataMap(data) {
	if(data.FeatureCollection.featureMember){
		var x = data.FeatureCollection.featureMember.length;
		for ( var i = 0; i < x; i++) {
				//显示菜单所对应的div
				var coordinates = data.FeatureCollection.featureMember[i].JXPOI[0].GEOMETRY[0].Point[0].coordinates;
				var lineString="\""+coordinates+""+"\"";
				var mpPhoto = data.FeatureCollection.featureMember[i].JXPOI[0].MPPHOTO;
				var name = data.FeatureCollection.featureMember[i].JXPOI[0].NAME;
				var address = data.FeatureCollection.featureMember[i].JXPOI[0].ADDRESS;
				var photo = "http://58.246.138.178:8040/jxsm/PoiThumbnailImageHandler.ashx?POI="+ mpPhoto;
				var content="<div id='winInfo'>"
							+"<div id='winTitle'>"+name+"</div>"
							+"<div id='winContent'>"
								+"<table>"
									+"<tr>"
									+"<th class='content1' >地址</th>"
									+"<td class='content2'>"+address+"</td>"
									+"<td class='content3' rowspan='2'><img src='"+photo+"' width='100px' height='100px'></img></td>"
									+"</tr>"
									+"<tr>"
									+"<th class='content1'>电话</th>"
									+"<td class='content2'>xxxxxxxxxxxxxxxxxxx</td>"
									+"</tr>"
								+"</table>"
							+"</div>"	
							+"<div id='winSearch'>"
								+"<ul class='winul'>"
								+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='tohere()'>到这里去</li>"
								+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='fromhere()'>从这里出发</li>"
								+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='surround("+lineString+")'>在附近找</li>"
								+"</ul>"
								+"<div class='winfoot'>"
									+"起点<input name='start'><button>公交</button><button>驾车</button>"
								+"</div>"
							+"</div>"
						+"</div>";
				map.setCenter(coordinates+"",13);
				map.createPopupForMarker(coordinates+"",content,"js/img/common/"+i+".png",30,300,300);
		}
	}
}
//poi数据（用于显示）
function parsePoiDataInfo(data) {
	if(data.FeatureCollection.featureMember){
		var x = data.FeatureCollection.featureMember.length;
		for ( var i = 0; i < x; i++) {
				//显示菜单所对应的div
				$$("#searchresult").append("<div id='divMarker"+i+"'></div>");
				var coordinates = data.FeatureCollection.featureMember[i].JXPOI[0].GEOMETRY[0].Point[0].coordinates;
				var lineString="\""+coordinates+""+"\"";
				var mpPhoto = data.FeatureCollection.featureMember[i].JXPOI[0].MPPHOTO;
				var name = data.FeatureCollection.featureMember[i].JXPOI[0].NAME;
				var address = data.FeatureCollection.featureMember[i].JXPOI[0].ADDRESS;
				var photo = "http://58.246.138.178:8040/jxsm/PoiThumbnailImageHandler.ashx?POI="+ mpPhoto;
				var content="<div id='winInfo'>"
							+"<div id='winTitle'>"+name+"</div>"
							+"<div id='winContent'>"
								+"<table>"
									+"<tr>"
									+"<th class='content1' >地址</th>"
									+"<td class='content2'>"+address+"</td>"
									+"<td class='content3' rowspan='2'><img src='"+photo+"' width='100px' height='100px'></img></td>"
									+"</tr>"
									+"<tr>"
									+"<th class='content1'>电话</th>"
									+"<td class='content2'>xxxxxxxxxxxxxxxxxxx</td>"
									+"</tr>"
								+"</table>"
							+"</div>"	
							+"<div id='winSearch'>"
								+"<ul class='winul'>"
								+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='tohere()'>到这里去</li>"
								+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='fromhere()'>从这里出发</li>"
								+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='surround("+lineString+")'>在附近找</li>"
								+"</ul>"
								+"<div class='winfoot'>"
									+"起点<input name='start'><button>公交</button><button>驾车</button>"
								+"</div>"
							+"</div>"
						+"</div>";
				map.setCenter(coordinates+"",13);
				parsePoiDataHepler(coordinates+"",content,name,address,i);
		}
	}
}
//相对于POI点
function parsePoiDataHepler(lonlat,content,name,address,index){
	//兴趣点数据
	$$("#divMarker"+index+"").append("<img src='js/img/common/"+index+".png'>" +
			"<a href='javascript://' id='a"+index+"'>"+name+"</a><br/>" +
					"<span>地址："+address+"</span>");
	var a=document.getElementById("a"+index+"");
	a.onclick =function createPopup(){
		map.createPopup(lonlat,300,300,content);
	}
	
}
//相对于停车场
function parseParkingDataHepler(lonlat,content,name,address,index,image){
	$$("#divMarker"+index+"").append("<img src="+image+">" +
			"<a href='javascript://' id='a"+index+"'>"+name+"</a><br/>" +
			"<span>地址："+address+"</span>");
	var a=document.getElementById("a"+index+"");
	a.onclick =function createPopup(){
		map.createPopup(lonlat,300,300,content);
	}
}
//相对于码头
function parseParkingDataHepler(lonlat,content,name,managerOrg,index,imageIcon){
	$$("#divMarker"+index+"").append("<img src="+imageIcon+">" +
			"<a href='javascript://' id='a"+index+"'>"+name+"</a><br/>" +
					"<span>地址："+managerOrg+"</span>");
	var a=document.getElementById("a"+index+"");
	a.onclick =function createPopup(){
		map.createPopup(lonlat,300,300,content);
	}
	
}
/**
 * 右键搜索周边
 * @param lonlat
 */
function contextMenuToSurround(lonlat){
	var lineString="\""+lonlat+"\"";
	var content="<div id='winInfo'>"
		+"<div id='winSearch'>"
			+"<ul class='winul'>"
			+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='tohere()'>到这里去</li>"
			+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='fromhere()'>从这里出发</li>"
			+"<li onmouseover='this.style.backgroundColor=\"gray\"' onmouseout='this.style.backgroundColor=\"blue\"' onclick='surround("+lineString+")'>在附近找</li>"
			+"</ul>"
			+"<div class='winfoot'>"
				+"起点<input name='start'><button>公交</button><button>驾车</button>"
			+"</div>"
	+"</div>";
	map.createPopupForMarker(lonlat,content,"images/marker/marker2.png",30,300,100);
}
function tohere(){
	$$(".winfoot").empty().append("起点<input name='start'><button>公交</button><button>驾车</button>");
}
function fromhere(){
	$$(".winfoot").empty().append("终点<input name='end'><button>公交</button><button>驾车</button>");
}
function surround(lonlat){
	lonlat="\""+lonlat+"\"";
	$$(".winfoot").empty().append("<a onclick='searchbysurround("+lonlat+",1)'>酒店</a>&nbsp&nbsp<a onclick='searchbysurround("+lonlat+",2)'>餐馆</a>&nbsp&nbsp<a onclick='searchbysurround("+lonlat+",3)'>银行</a>&nbsp&nbsp<a onclick='searchbysurround("+lonlat+",4)'>医院</a>&nbsp&nbsp<input name='key' size='6'><button onclick='searchbysurround("+lonlat+")'>搜索</button>");
}
function searchbysurround(lonlat,name){
	$$(".infoMenu").empty().append("<div id='searchresult'></div>");
	var key;
	if(typeof(name)!='undefined'){
		if(name==1){
			key='酒店';
		}
		if(name==2){
			key='餐馆';
		}
		if(name==3){
			key='银行';
		}
		if(name==4){
			key='医院';
		}
	}else{
		key=$$("[name=key]").val();
	}
	searchBySurroundData(lonlat, 2000, key,1,10);
	//searchBySurround(lonlat, 2000, key, 0,"images/marker/marker2.png",25);
}
