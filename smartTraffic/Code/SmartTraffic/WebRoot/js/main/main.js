
var token="";
var isadmin="";
var isadminname="";
$(document).ready(function(){ 
   
	 findDimensions(); //设置footer
	// setFullBGShow();
	$("img[id^='module_']").bind({
	    mouseover:function(){
	    	 var _this = $(this);
	    	 var id = _this.attr('id');
	    	 var img_url = 'image/newmain/'+id+'_pressed.png';
	    	  _this.attr("src",img_url);

	    	

	    	 },
	    mouseout:function(){
	    	var _this = $(this);
	    	 var id = _this.attr('id');
	    	 var img_url = 'image/newmain/'+id+'_normal.png';
	    	 _this.attr("src",img_url);
	    	 
	    	 }
	});	
	
	
	
});






function findDimensions() //函数：获取尺寸 
{  
	var browserVersion= window.navigator.userAgent.toUpperCase();
  winHeight=0;
//获取窗口高度 
  if (window.innerHeight) {
     winHeight = window.innerHeight; 
   } else if ((document.body) && (document.body.clientHeight)) {
     winHeight = document.body.clientHeight; 
   }
//通过深入Document内部对body进行检测，获取窗口大小 
     if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) 
     { 
          winHeight = document.documentElement.clientHeight; 
          winWidth = document.documentElement.clientWidth; 
     } 
    
     if( winHeight >=700){
    	 //浏览器可以显示
    	 var topheight =winHeight-75; 
    	 if(browserVersion.indexOf("MSIE 7")>-1){
    		 document.getElementById("footer").style.paddingTop = topheight+"px";
    	 }
    	 document.getElementById("footer").style.marginTop = topheight+"px";
    	 
     }else{
    	 
    	 var topheight =700-75; 
    	 if(browserVersion.indexOf("MSIE 7")>-1){
    		 document.getElementById("footer").style.paddingTop = topheight+"px";
    	 }
    	 document.getElementById("footer").style.marginTop = topheight+"px";
     }
     if($("#popBack").is(":visible")){
    	 showFullBG();
     }
}

window.onresize=function(){
	 winHeight=0;
	 var browserVersion= window.navigator.userAgent.toUpperCase();
	//获取窗口高度 
	  if (window.innerHeight) {
	     winHeight = window.innerHeight; 
	   } else if ((document.body) && (document.body.clientHeight)) {
	     winHeight = document.body.clientHeight; 
	   }
	//通过深入Document内部对body进行检测，获取窗口大小 
	     if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) 
	     { 
	          winHeight = document.documentElement.clientHeight; 
	          winWidth = document.documentElement.clientWidth; 
	     } 
	  
	     if( winHeight >=700){
	    	 //浏览器可以显示
	    	 var topheight =winHeight-75; 
	    	 if(browserVersion.indexOf("MSIE 7")>-1){
	    		 document.getElementById("footer").style.paddingTop = topheight+"px";
	    	 }
	    	 
	    	 document.getElementById("footer").style.marginTop = topheight+"px";
	    	 
	     }else{

	    	 var topheight =700-75;
	    	 if(browserVersion.indexOf("MSIE 7")>-1){
	    		 document.getElementById("footer").style.paddingTop = topheight+"px";
	    	 }
	    	 document.getElementById("footer").style.marginTop = topheight+"px";
	     }
	     if($("#popBack").is(":visible")){
	    	 showFullBG();
	     }
};


function showFullBG(){
	  var bwidth = document.body.offsetWidth;
	     var bheight = document.body.offsetHeight;
	     $("#popBack").css({ 
	 		height:bheight, 
	 		width:bwidth

	 	}); 
}

function setFullBGShow(){
	showFullBG();
	
	$("#popBack").show();
	$(".popWin").show();
	
}
 function setFullBGHide(){
	 $("#popBack").hide();
		$(".popWin").hide();
 }
  function setFullBGShow1(){
		showFullBG();
		$("#popBack").show();
		$(".popWin1").show();	
	}
 function setFullBGHide1(){
		 $("#popBack").hide();
	 	$(".popWin1").hide();
	 }

 
 //链接
 function linkToVideo(){
		window.open($("#basePath").val()+"page/main/videomap.jsp");
 }
 function linkToRun(){
	 window.open($("#basePath").val()+"page/main/traffic.jsp");
 }
 function linkToAnalyse(){
	 window.open($("#basePath").val()+"page/statics/Statistic.jsp");
 }
 function linkToScience(){
	 window.open("http://172.20.45.68:7001/jxtpi/public_index"); 
 }
 function linkToWalk(){
		window.open("http://115.231.73.254/SmartTraffic/");
 }
 function linkToTransport(){
		window.open("http://115.231.73.254/SmartTraffic/");
}
  function linkToXcyd(){
	   window.open("http://192.168.170.17/jxydp/mainIndex.jsp");
 }
 