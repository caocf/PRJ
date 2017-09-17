

function initGuildPic(){
	
	$("#link_Left").click(function(){
		$("#linklineid").toggle();
	});
	$("#link_Left2").click(function(){
		$("#linklineid").toggle();
	});
	//综合交通
	$("#mainguid_tvideo").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#265da6');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#2a68b9');
	    	 }
	});
	$("#mainguid_tscience").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#306db9');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#3679ce');
	    	 }
	});
	$("#mainguid_twalkservice").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#3375c7');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#3982de');
	    	 }
	});
	$("#mainguid_tlogistics").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#4688da');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#4e97f3');
	    	 }
	});
	$("#mainguid_tanalyse").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#5b98e5');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#65a9ff');
	    	 }
	});
	//智慧运管
	$("#mainguid_tpbus").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#26a854');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#2abb5d');
	    	 }
	});
	$("#mainguid_tpdanger").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#22994c');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#26aa55');
	    	 }
	});
	$("#mainguid_tptaxi").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#1f8b45');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#239b4d');
	    	 }
	});
	$("#mainguid_tpvideo").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#1d7e3f');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#208c46');
	    	 }
	});
	$("#mainguid_tpanalyse").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#1a7439');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#1d8140');
	    	 }
	});
	//智慧公路
	$("#mainguid_rroute").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#007474');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#008181');
	    	 }
	});
	$("#mainguid_rblock").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#008383');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#009292');
	    	 }
	});
	
	$("#mainguid_rvideo").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#009494');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#00a5a5');
	    	 }
	});
	$("#mainguid_ranalyse").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#00a3a3');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#00b6b6');
	    	 }
	});
	//智慧港航
	$("#mainguid_pboat").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#2680e5');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#2a8eff');
	    	 }
	});
	
	$("#mainguid_pvideo").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#2171cc');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#257ee3');
	    	 }
	});
	
	$("#mainguid_pchannel").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#1e65b4');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#2170c8');
	    	 }
	});
	
	$("#mainguid_panalyse").bind({
	    mouseover:function(){
	    		 $(this).css('background-color','#1b5ba3');
	    	 },
	    mouseout:function(){
	    	    $(this).css('background-color','#1e65b5');
	    	 }
	});

	   
	
	//返回按钮点击事件
	$("#portguid_back").click(backMainPage);
	$("#roadguid_back").click(backMainPage);
	$("#transportguid_back").click(backMainPage);
	$("#trafficguid_back").click(backMainPage);
	$("#trafficguid_back_zh").click(function(){//运行监测
		window.location.href=$("#basePath").val()+"page/main/traffic.jsp";
	});
	/**综合交通链接事件
	 * 
	 */
	$("#change_trun").click(function(){//运行监测
		 location.reload();
	});
	$("#mainguid_tvideo").click(function(){//video
		window.location.href = $("#basePath").val()+"page/main/videomap.jsp";
	});
	$("#tra_sciencelink").click(function(){//科技治堵
		window.open("http://172.20.45.68:7001/jxtpi/public_index");
	});
	$("#mainguid_twalkservice").click(function(){//出行服务
		window.open("http://115.231.73.254/SmartTraffic/");
	});
	
	/**智慧运管
      *智慧运管-公交监测：http://172.20.18.60
	
	 */
	$("#mainguid_tpbus").click(function(){//公交监测
		window.open("http://172.20.18.60/jtj.jsp");
	});
	$("#mainguid_tptaxi").click(function(){//出租车监测
		window.location.href = $("#basePath").val()+"page/transport/taxi.jsp";
	});
	
	/**
	 *智慧港航-船舶动态监测：http://172.20.24.48
	 */
	$("#mainguid_pboat").click(function(){//船舶动态监测
		window.location.href = $("#basePath").val()+"page/port/ship.jsp";              
	});

	
	//行业分析链接
	$("#mainguid_panalyse").click(function(){  //港航
		window.open("http://172.20.44.68/rsbi/Login!login.action?" +
				"userName=ganghang&password=123456&rurl=frame/NFrame.action");
	});
	$("#mainguid_ranalyse").click(function(){   //公路
		window.open("http://172.20.44.68/rsbi/Login!login.action?" +
				"userName=gonglu&password=123456&rurl=frame/NFrame.action");
	});
	$("#mainguid_tanalyse").click(function(){   //交通
		window.location.href = $("#basePath").val()+"page/statics/Statistic.jsp";
		/*window.open("http://172.20.44.68/rsbi/Login!login.action?" +
		"userName=jiaotong&password=123456&rurl=frame/NFrame.action");*/
	});
	$("#mainguid_tpanalyse").click(function(){   //运管
		window.open("http://172.20.44.68/rsbi/Login!login.action?" +
				"userName=yunguan&password=123456&rurl=frame/NFrame.action");
	});
	
	/**
	 * 公路
	 */
	$("#mainguid_rroute").click(function(){   //路网
		//window.open("http://172.20.8.45:8081/SThighway/page/main/firstpage.jsp");
		window.open("http://172.20.44.69/SThighway/page/main/firstpage.jsp");
	});
	$("#mainguid_rblock").click(function(){   //通阻
		window.open("http://172.20.44.69/SThighway/page/tzinfo/TzInfo.jsp");
	});
	$("#mainguid_rvideo").click(function(){   //视频
		window.open("http://172.20.44.69/SThighway/page/videomonitor/VideoMonitor.jsp");
	});
	
	/**
	 * 运行监测菜单链接
	 */                  
	/*$("#tra_roadlink").click(function(){  //
		 window.open($("#basePath").val()+"page/main/road.jsp");
	});
	$("#tra_buslink").click(function(){  //公交监测
		window.open("http://172.20.18.60");
	});
	$("#tra_taxilink").click(function(){  //出租车监测
		window.open($("#basePath").val()+"page/transport/taxi.jsp");
	});
	$("#tra_dangerlink").click(function(){  //两客一危监测 
		window.open($("#basePath").val()+"page/transport/taxi.jsp");
	});
	$("#tra_shiplink").click(function(){  //船舶监测
		window.open("http://172.20.24.48");
		window.open($("#basePath").val()+"page/port/ship.jsp");
	});*/
	
}

function backMainPage(){
	window.location.href=$("#basePath").val()+"page/main/serviceFlat.jsp";
}

//遮罩层
function ShowLoadingDiv(){
	var bh = $(".common_c0").height();
	var bw = $(".common_c0").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	$(".loadingDiv").show();
	
}
function CloseLoadingDiv(){
	$("#fullbg,.loadingDiv").hide();
	
}
//遮罩层


/**
 * 定位底部导航栏
 */
function setLinkLeft(){
	var linkdivid = document.getElementById("linklineid");
	/*linkdivid.style.top =  getBottomPos("traffectedroad");*/
	var linkleft = document.getElementById("link_Left");
	linkleft.style.left = linkdivid.offsetLeft-30;
	linkleft.style.top = linkdivid.offsetTop;
}

/**
 * 定位底部导航栏
 */
function setLinkLeft2(){
	var linkdivid = document.getElementById("linklineid");
	/*linkdivid.style.top =  getBottomPos("traffectedroad");*/
	var linkleft = document.getElementById("link_Left2");
	var linktop = document.getElementById("runlist");
	 
	linktop.style.left = linkdivid.offsetLeft;
	linktop.style.top = linkdivid.offsetTop-278;
	linkleft.style.left = linkdivid.offsetLeft-30;
	linkleft.style.top = linkdivid.offsetTop;
	
	//var e2 =  document.getElementById("mainguid_tscience");
	var setTop2 = linkdivid.offsetTop-110;
	var setLeft2 = linkdivid.offsetLeft+360;
	var setE2 = document.getElementById("carrunlist");
	 setE2.style.top = setTop2+"px";
	 setE2.style.left = setLeft2+"px";
}