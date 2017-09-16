var ListOfCargoType = new Array();
var ListOfCargo = new Array();
var selectCargo="";
var selectWharf="";
var selectArrivePort="";
var selectStartPort="";
var boatmanInfo=new Array();
var selectboatmanInfo=new Array();
$(document).ready(function(){
	showModifyInfo('ElectricReportByReportId');
	$("#titlename").text("新增航行电子报告");
		//ShowBoatUserInfo();
	showPortInfo();//目的港
	showCargoInfo();
	showPortList();
});
function gotoBack(){
	window.history.go("-1");
}

function showModifyInfo(actionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'electricReportNew.reportID':$("#reportId").val()
		},
		success:function(data){
			$(".opti").empty();
			$(".opti").remove();
			$(".addTr").empty();
			$(".addTr").remove();
			var list=data.list[0];
			$("#boatName").text(list.shipName);
			if(list.reportKind==1){	
				document.getElementById("selectsail").options.add(new Option("重船航行",1));
				document.getElementById("selectsail").options.add(new Option("空船航行",2));
			}else{
				document.getElementById("selectsail").options.add(new Option("空船航行",2));
				document.getElementById("selectsail").options.add(new Option("重船航行",1));
			}
			if(list.outOrIn==1){
				document.getElementById("selectport").options.add(new Option("进港",1));
				document.getElementById("selectport").options.add(new Option("出港",2));
			}else{
				document.getElementById("selectport").options.add(new Option("出港",2));
				document.getElementById("selectport").options.add(new Option("进港",1));
			}
			
			searchStartPort();
			//$("#startport").text(list.startingPort);
			$("#arriveport").text(list.arrivalPort);
			if($("#arriveport").text().indexOf("湖州")>=0||$("#startport").text().indexOf("湖州")>=0){
				$("#workwharf,#inOrNotPort").show();
			}
			$("#cargoName").text(list.cargoType);
			$("#goodWeight").val(list.cargoNumber);
			$("#stopWharf").text(list.toBeDockedAtThePier);
			$("#entertime").val(HourTimeFormat(list.estimatedTimeOfArrival));
			$("#addOil").val(list.draft);
			$("#addOiltime").val(HourTimeFormat(list.draftTime));
			if(list.shipInfo!=""){
				var shipmanInfo=list.shipInfo.split(';');
				//alert(shipmanInfo.length);
				for(var i=0;i<shipmanInfo.length;i++){
					var newTr=$("<tr class='addTr'></tr>");
					newTr.append($("<td>"+shipmanInfo[i].split(",")[0]+"</td>"));
					newTr.append($("<td><label>"+shipmanInfo[i].split(",")[1]+"</label><label>&nbsp;(&nbsp;"+shipmanInfo[i].split(",")[2]+":"+shipmanInfo[i].split(",")[3]+"&nbsp;)</label></td>"));
					selectboatmanInfo[i]=new Array();
					selectboatmanInfo[i][0]=shipmanInfo[i].split(",")[0];
					selectboatmanInfo[i][1]=shipmanInfo[i].split(",")[1];
					selectboatmanInfo[i][2]=shipmanInfo[i].split(",")[2];
					selectboatmanInfo[i][3]=shipmanInfo[i].split(",")[3];
					selectboatmanInfo[i][4]=1;
					selectboatmanInfo[i][5]=i;
					$("#sailboatTable").append(newTr);
				}
			}
			
			if($("#selectsail option:selected").val()==2){
				$("#cargoStyle,#cargoNum").hide();
				$("#workwharf,#stopWharf").hide();
			}
		}
	});
}
function searchStartPort(){
	$.ajax({
		url:'showStartPort',
		type:'post',
		dataType:'json',
		data:{
			'shipName':$("#shipName").val()
		},
		success:function(data){
			if(data.electricReportNew.arrivalPort=="null"){
				$("#showselectportDiv").show();
			}else{
				$("#startport").text(data.electricReportNew.arrivalPort);
				if($("#startport").text().indexOf("湖州")>=0){
					$("#workwharf,#inOrNotPort").show();
				}
				
				selectSailStyle();
			}
		}
	});
}
function selectSailStyle(){
	if($("#selectsail option:selected").val()==2){
		$("#cargoStyle,#cargoNum").hide();
		$("#workwharf,#stopWharf").hide();
	}else{
		$("#cargoStyle,#cargoNum").show();
		$("#workwharf,#stopWharf").show();
	}
}
function selectBoatmanInfo(){
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		});
	ShowBoatUserInfo();//选择船员
	$("#selectBoatManInfoDiv").show();
}
function closesboatInfoDiv(){
	$("#selectBoatManInfoDiv,#fullbg").hide();
}
//显示船员信息
function ShowBoatUserInfo(){
	$.ajax({
		url:'ShowBoatUserInfo',
		type:'post',
		dataType:'json',
		data:{
			'boatman.boatmanShip':$("#shipName").val()
		},
		success:function(data){
			$("#boatmanInfoUl").empty();
			boatmanInfo.splice(0, boatmanInfo.length);
			var list=data.list;/*alert(JSON.stringify(list));*/
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addLi' id='selectBotman"+i+"' onclick=boatmanInfoSelected(this,'"+i+"')><span class='spanStyle' id='boatmanKindName"+list[i][0].boatmanID+"'>"+list[i][1].boatmanKindName+"</span>" +
						"<span class='spanStyle' id='boatmanName"+list[i][0].boatmanID+"'>"+list[i][0].boatmanName+"</span></br>" +
								"<span class='spanStyle' id='cardName"+list[i][0].boatmanID+"'>"+list[i][2].cardName+"</span>" +
										"<span class='spanStyle' id='boatmanCard"+list[i][0].boatmanID+"'>"+list[i][0].boatCardNum+"</span></li>");
				boatmanInfo[i]=new Array();
				boatmanInfo[i][0]=list[i][1].boatmanKindName;
				boatmanInfo[i][1]=list[i][0].boatmanName;
				boatmanInfo[i][2]=list[i][2].cardName;
				boatmanInfo[i][3]=list[i][0].boatCardNum;
				boatmanInfo[i][4]=0;
				boatmanInfo[i][5]=i;
				$("#boatmanInfoUl").append(newLi);
			}
			
		}
	});
}
//选中船员信息
function boatmanInfoSelected(thisop,id){
	for(var i=0;i<boatmanInfo.length;i++){
		if(boatmanInfo[i][5]==id&&boatmanInfo[i][4]==0){
			thisop.style.background="#7BC4FD";
			boatmanInfo[i][4]=1;
		}else if(boatmanInfo[i][5]==id&&boatmanInfo[i][4]==1){
			thisop.style.background="white";
			boatmanInfo[i][4]=0;
		}
	}
}
function determineBoatInfo(){
	$(".addTr").empty();
	$(".addTr").remove();
	selectboatmanInfo.splice(0, selectboatmanInfo.length);
	var j=0;
	for(var i=0;i<boatmanInfo.length;i++){
		if(boatmanInfo[i][4]==1){
			var newTr=$("<tr class='addTr'></tr>");
			newTr.append($("<td>"+boatmanInfo[i][0]+"</td>"));
			newTr.append($("<td><label>"+boatmanInfo[i][1]+"</label><label>&nbsp;(&nbsp;"+boatmanInfo[i][2]+":"+boatmanInfo[i][3]+"&nbsp;)</label></td>"));
			selectboatmanInfo[j]=new Array();
			selectboatmanInfo[j][0]=boatmanInfo[i][0];
			selectboatmanInfo[j][1]=boatmanInfo[i][1];
			selectboatmanInfo[j][2]=boatmanInfo[i][2];
			selectboatmanInfo[j][3]=boatmanInfo[i][3];
			selectboatmanInfo[j][4]=boatmanInfo[i][4];
			j++;
			$("#sailboatTable").append(newTr);
		}
	}
	closesboatInfoDiv();
}
//显示始发港
function showStartPortDiv(){
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		});

	$("#selectStartPortDiv").show();
}
//显示目的港
function showPortDiv(){
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		});
	
	$("#selectPortDiv").show();
	
}
function showCargoDiv(){
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	
	$("#selectCargoDiv").show();
	
}
//显示码头
function showWharfDiv(){
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	$("#searchwharf").val("");
	
	$("#selectWharfDiv").show();
}
function closePortDialog(){
	$("#fullbg,#selectPortDiv").hide();
}
function closeStartPortDialog(){
	$("#fullbg,#selectStartPortDiv").hide();
}
function closeCargoDialog(){
	$("#fullbg,#selectCargoDiv").hide();
}
function closeWharfDialog(){
	$("#fullbg,#selectWharfDiv").hide();
}
function selectPort(){
	
}
//选择始发港口
function addselectStartPort(s,portname,portID){
	$(".addTypeLiselected").attr("class","addLi");
	$("#addStartPortLi"+portID).attr("class","addTypeLiselected");
	selectStartPort=portname;
}
function selectStartPortToText(){
	$("#startport").text(selectStartPort);
	$("#fullbg,#selectStartPortDiv").hide();
	judgeWorkWharfshowOrhide();
}
//显示港口
function showPortInfo(){
	$.ajax({
		url:'showPortList',
		type:'post',
		dataType:'json',
		success:function(data){
			var list=data.list;
			for(var i=0;i<list.length;i++){
				var newLi_end=$("<li class='addLi' id='addPortLi"+list[i].portID+"' onclick=addselectMUDIPort(this,'"+list[i].portName+"','"+list[i].portID+"')>"+list[i].portName+"</li>");
				$("#portname").append(newLi_end);
				var newLi_start=$("<li class='addLi' id='addStartPortLi"+list[i].portID+"' onclick=addselectStartPort(this,'"+list[i].portName+"','"+list[i].portID+"')>"+list[i].portName+"</li>");
				$("#startportname").append(newLi_start);
			}
		}
	});
}
//选择目的港口
function addselectMUDIPort(s,portname,portID){
	$(".addTypeLiselected").attr("class","addLi");
	$("#addPortLi"+portID).attr("class","addTypeLiselected");
	selectArrivePort=portname;
}
function selectPortToText(){
	$("#arriveport").text(selectArrivePort);
	$("#fullbg,#selectPortDiv").hide();
	judgeWorkWharfshowOrhide();
}
function judgeWorkWharfshowOrhide(){
	var judgestartport=$("#startport").text();
	var judgearriveport=$("#arriveport").text();
	if(judgestartport!==""||judgearriveport!=""){
		if(judgestartport.indexOf("湖州")>=0||judgearriveport.indexOf("湖州")>=0){
			$("#workwharf,#inOrNotPort").show();
		}else{
			$("#workwharf,#inOrNotPort").hide();
		}
		
		selectSailStyle();
	}
}
//显示货物
function showCargoInfo(){
	$.ajax({
		url:'GoodsKindAll',
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){
			$("#cargoname .addLi,.addTypeLiselected").empty();
			$("#cargoname .addLi,.addTypeLiselected").remove();
			$(".addUl").empty();
			$(".addUl").remove();
			var list1=data.goodskindlist;
			for(var i=0;i<list1.length;i++){
				var newLi=$("<li class='addLi' onclick=showCargoType('"+list1[i][0]+"')>"+list1[i][1]+"</li>");
				var newUl=$("<ul class='addUl' id='cargoul"+list1[i][0]+"'></ul>")
				$("#cargoname").append(newLi);
				$("#cargoname").append(newUl);
			}
			var list2=data.goodskindlist1;
			for(var j=0;j<list2.length;j++){
				var newLi=$("<li class='addLi' id='addCargoLi"+list2[j][0]+"' onclick=addselectCargo(this,'"+list2[j][1]+"','"+list2[j][0]+"')>"+list2[j][1]+"</li>");
				$("#cargoname").append(newLi);
				ListOfCargo[j]=new Array();
				ListOfCargo[j][0]=list2[j][0]; 
				ListOfCargo[j][1]=list2[j][1];
				ListOfCargo[j][2]=0;
			}
		}
	});
}
//显示子货物
function showCargoType(num){
	$.ajax({
		url:'GoodsAll',
		type:'post',
		dataType:'json',
		data:{
			'goodsKindID':num
		},
		success:function(data){
			$(".addTypeLi,.addTypeLiselected").empty();
			$(".addTypeLi,.addTypeLiselected").remove();
			var list=data.goodslist;
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addTypeLi' style='padding-left:45px;' id='addTypeLi"+list[i].goodsID+"' onclick=addselectTypeCargo(this,'"+list[i].goodsName+"','"+list[i].goodsID+"')>"+list[i].goodsName+"</li>");
				$("#cargoul"+num).append(newLi);
				ListOfCargoType[i]=new Array();
				ListOfCargoType[i][0]=list[i].goodsID;
				ListOfCargoType[i][1]=list[i].goodsName;
				ListOfCargoType[i][2]=0;
			}
			
		}
	});
	$("#cargoul"+num).toggle();
}

function addselectTypeCargo(s,cargoName,cargoId){
	$(".addTypeLiselected").attr("class","addTypeLi");
	$("#addTypeLi"+cargoId).attr("class","addTypeLiselected");
	selectCargo=cargoName;
}
function addselectCargo(s,cargoName,cargoId){
	$(".addTypeLiselected").attr("class","addLi");
	$("#addCargoLi"+cargoId).attr("class","addTypeLiselected");
	selectCargo=cargoName;
}
function selectCargoToText(){
	$("#cargoName").text(selectCargo);
	$("#fullbg,#selectCargoDiv").hide();
}
//显示港区
function showPortList(){
	selectWharfDiv(1);
	$.ajax({
		url:'findPortAreas',
		type:'post',
		dataType:'json',
		success:function(data){
			$("#portList .addLi").empty();
			$("#portList .addLi").remove();
			var list=data.portAreas;
			if(data.list==""){
				var newLi=$("<li class='addLi'>暂无数据</li>");
				$("#portList").append(newLi);
			}
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addLi'  onclick=showAreaList('"+list[i].id+"','"+list[i].name+"')>"+list[i].name+"</li>");
				$("#portList").append(newLi);
			}
		}
	});
}
//显示片区
function showAreaList(id,name){
	$("#PieceName").text(name);
	selectWharfDiv(2);
	$.ajax({
		url:'findPieceAreas',
		type:'post',
		dataType:'json',
		data:{
			'portareaid':id
		},
		success:function(data){
			$("#areaList .addLi").empty();
			$("#areaList .addLi").remove();
			var list=data.pieceAreas;
			if(data.list==""){
				var newLi=$("<li class='addLi'>没有这个片区</li>");
				$("#areaList").append(newLi);
			}
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addLi' onclick=showWharfInfo('"+list[i].id+"','"+list[i].name+"')>"+list[i].name+"</li>");
				$("#areaList").append(newLi);
			}
		}
	});
}
//显示码头
function showWharfInfo(id,name){
	$("#WharfName").text(name);
	selectWharfDiv(3);
	$.ajax({
		url:'findWharfItems',
		type:'post',
		dataType:'json',
		data:{
			'pieceareaid':id
		},
		success:function(data){
			$("#wharfname .addLi").empty();
			$("#wharfname .addLi").remove();
			var list=data.wharfItems;
			if(data.list==""){
				var newLi=$("<li class='addLi'>"+"没有这个码头"+"</li>");
				$("#wharfname").append(newLi);
			}
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addLi' id='addWharfLi"+list[i].id+"' onclick=addselectWharf('"+list[i].id+"','"+list[i].mc+"')>"+list[i].mc+"</li>");
				$("#wharfname").append(newLi);
			}
		}
	});
}
//搜索码头
function SearchWharfInfo(){
	$("#WharfName").text("搜索结果");
	selectWharfDiv(3);
	var content=$("#searchwharf").val();
	if (/[~#^$@%&!*\s*]/.test(content)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax({
		url:'searchWharfItems',
		type:'post',
		dataType:'json',
		data:{
			'portareaid':-1,
			'pieceareaid':-1,
			'wharfname':content
		},
		success:function(data){
			$("#wharfname .addLi").empty();
			$("#wharfname .addLi").remove();
			var list=data.wharfItems;
			if(data.list==""){
				var newLi=$("<li class='addLi'>"+"没有这个码头"+"</li>");
				$("#wharfname").append(newLi);
			}
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addLi' id='addWharfLi"+list[i].id+"' onclick=addselectWharf('"+list[i].id+"','"+list[i].mc+"')>"+list[i].mc+"</li>");
				$("#wharfname").append(newLi);
			}
		}
	});
}
function selectWharfDiv(type){
	$("#wharfname,#areaList,#portList,#PieceDiv,#WharfDiv").css("display","none");
	if(type==1){
		$("#portList").css("display","");
	}else if(type==2){
		$("#areaList,#PieceDiv").css("display","");
	}else if(type==3){
		$("#wharfname,#WharfDiv").css("display","");
	}
	
	
}
//选择码头
function addselectWharf(wharfId,name){
	$(".addTypeLiselected").attr("class","addLi");
	$("#addWharfLi"+wharfId).attr("class","addTypeLiselected");
	selectWharf=name;
}
function selectWharfToText(){
	$("#stopWharf").text(selectWharf);
	$("#fullbg,#selectWharfDiv").hide();
}
//新增航行报告
function addInfo(){
	var boatname=$("#boatName").text();
	var selectsail=$("#selectsail option:selected").val();
	var selectport=$("#selectport option:selected").val();
	var startport=$("#startport").text();
	var arriveport=$("#arriveport").text();
	var cargoName=$("#cargoName").text();
	var goodWeight=$("#goodWeight").val();
	var stopWharf=$("#stopWharf").text();
	var entertime=$("#entertime").val();
	var addOil=$("#addOil").val();
	var addOiltime=$("#addOiltime").val();
	var unit=$("#unit option:selected").text();
	var LinkshipInfo="";
	for(var j=0;j<selectboatmanInfo.length;j++){
		if(selectboatmanInfo[j][4]==1){
			if(LinkshipInfo==""){
				LinkshipInfo=selectboatmanInfo[j][0]+","+selectboatmanInfo[j][1]+","+selectboatmanInfo[j][2]+","+selectboatmanInfo[j][3];
			}else{
				LinkshipInfo+=";"+selectboatmanInfo[j][0]+","+selectboatmanInfo[j][1]+","+selectboatmanInfo[j][2]+","+selectboatmanInfo[j][3];
			}
		}
	}
	var regx=/\D/;
	if(regx.test(goodWeight)&&$("#selectsail option:selected").val()==1){
		alert("载货数量只能是数字！");
		return;
	}
	if(regx.test(addOil)){
		alert("加油量只能是数字！");
		return;
	}
	if(!checkform()){
		return;
	}
	if(addOil!=""&&addOil!=0){
		if(addOiltime==""){
			alert("最近加油时间不能为空");
			return;
		}
	}

	if(addOiltime!=""){
		if(addOil==""){
			alert("最近加油量不能为空");
			return;
		}
	}
	if(LinkshipInfo==""){
		alert("船员信息不能为空！");
		return;
	}
		$.ajax({
			url:'SaveElectricReport',
			type:'post',
			dataType:'json',
			data:{
				'electricReportNew.reportKind':selectsail,
				'electricReportNew.outOrIn':selectport,
				'electricReportNew.startingPort':startport,
				'electricReportNew.arrivalPort':arriveport,
				'electricReportNew.cargoType':cargoName,
				'electricReportNew.cargoNumber':goodWeight,
				'electricReportNew.toBeDockedAtThePier':stopWharf,
				'electricReportNew.estimatedTimeOfArrival':entertime,
				'electricReportNew.uniti':unit,
				'electricReportNew.draft':addOil,
				'electricReportNew.draftTime':addOiltime,
				'electricReportNew.shipInfo':LinkshipInfo
			},
			beforeSend : function() {
				var bh = $("body").height(); 
				var bw = $("body").width(); 
				$("#fullbg").css({
					height:bh,
					width:bw
					});
				$("#fullbg,#loadingDiv").show();// 获取数据之前显示loading图标。
			},
			success:function(data){
				$("#loadingDiv").hide();
				$("#alarmInfo").append(data.returnValue);
				$("#alarmInfoDiv").show();
			},
			error:function(XMLHttpRequest){
				alert("新增错误！");
			}
		});
}
function checkform(){
	var boatname=$("#boatName").text();
	var selectsail=$("#selectsail option:selected").val();
	var selectport=$("#selectport option:selected").val();
	var startport=$("#startport").text();
	var arriveport=$("#arriveport").text();
	var cargoName=$("#cargoName").text();
	var goodWeight=$("#goodWeight").val();
	var stopWharf=$("#stopWharf").text();
	var entertime=$("#entertime").val();
	var addOil=$("#addOil").val();
	var addOiltime=$("#addOiltime").val();
	if(boatname==""){
		alert("船名不能为空！");
		return false;
	}
	if(startport==""){
		alert("始发港不能为空");
		return false;
	}
	if(arriveport==""){
		alert("目的港不能为空");
		return false;
	}
	if(cargoName==""&&$("#selectsail option:selected").val()==1){
		alert("载货种类不能为空");
		return false;
	}
	if(goodWeight==""&&$("#selectsail option:selected").val()==1){
		alert("载货数量不能为空");
		return false;
	}
	if(goodWeight>650000){
		alert("载货数量不能大于65万吨");
		return false;
	}
	if($("#selectsail option:selected").val() == 1 && (startport.indexOf("湖州")>=0||arriveport.indexOf("湖州")>=0)&&stopWharf==""){
		alert("本次作业码头不能为空");
		return false;
	}
	if(entertime==""){
		alert("预计进出港时间不能为空");
		return false;
	}
	return true;
	
}
function determineReport(){
	window.history.go("-1");
}