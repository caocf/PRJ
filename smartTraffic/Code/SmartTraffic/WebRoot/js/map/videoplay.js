var xmlDoc = null;
$(document).ready(function(){
	findDimensions();
	$("#videoguid_back").click(function(){
		window.location.href=$("#basePath").val()+"page/main/videomap.jsp";
	});
	var cameraId = $("#cameraId").val();
	var cameraName = $("#cameraName").val();
	searchPlayInfo(cameraId,cameraName);
	
	
});


function searchPlayInfo(selectCameraId,cameraName){
    if (!window.ActiveXObject) {
      $("#loadDiv").show();
      return;
    }
    else{
    	 $("#loadDiv").hide();
    }
	
	$.ajax({
		url:'video/selectPlayInfo',
		type:'post',
		dataType:'json',
		data:{
			"cameraId":selectCameraId,
			"cameraName":cameraName
		},
		success:function(data){
			if(data.result.resultcode==0){
				PlayCamera(data.result.obj);
			}else{
				alert("查询播放参数失败");
			}
		}
	});
}

	function PlayCamera(obj)
	{
	    // var str = creatMyXml(obj);
         var r3= devread.StartTask_Preview(obj);
	}
	
	
	function findDimensions() //函数：获取尺寸 
	{ 
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
	   //  alert(winHeight-64);
	      $("#video_play").css("height",winHeight-64);
	    //  document.getElementById("DemoCtrl").style.height=winHeight-100;
	}

	 window.onresize=function(){
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
		  
		     $("#video_play").css("height",winHeight-64);
	 };





	