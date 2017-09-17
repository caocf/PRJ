
var token="";
var isadmin="";
var isadminname="";
$(document).ready(function(){ 
    //获取浏览器高度
	 findDimensions();
	 var roadtoken = $("#roadtoken").val();
	  token = roadtoken;
	//获取个人信息
	searchInformation();
    //图片mouseover  mouseout
	$("img[id^='module_']").bind({
	    mouseover:function(){
	    	 var _this = $(this);
	    	 var id = _this.attr('id');
	    	 var img_url = 'image/login/'+id+'pressed.png';
	    	  _this.attr("src",img_url);

	    	

	    	 },
	    mouseout:function(){
	    	var _this = $(this);
	    	 var id = _this.attr('id');
	    	 var img_url = 'image/login/'+id+'.png';
	    	 _this.attr("src",img_url);
	    	 
	    	 }
	});

	/*$("#map").bind({
	    mouseover:function(){
	    	 var img_url = 'url("../HighWayCenter/image/login/mappressed.png")';
	    	 $("#mapbottom").css("background",img_url);
	    },
	    mouseout:function(){
	    	 var img_url = 'url("../HighWayCenter/image/login/mapbottom.png")';
	    	 $("#mapbottom").css("background",img_url);
	    }		
	});*/
	
	$("#userdiv").bind({
	    mouseover:function(){	 
	    	 $("#userset").show();
	    	 if(isadmin==1||isadminname=="admin"){  		 
	    		 $("#setPerson").hide();
	    	 }
	    	 $("#userimage").attr("src","image/login/main_arrow_up.png");
	    },
	    mouseout:function(){
	    	
	    	 $("#userset").hide();
	    	 $("#userimage").attr("src","image/login/main_arrow_down.png");
	    }
	  /*  click:function(){
	    	var isHidden = $("#userset").is(":hidden");
	    	if(isHidden){
	    		 $("#userset").show();
		    	 $("#userimage").attr("src","image/login/main_arrow_up.png");
	    	}else{
	    		 $("#userset").hide();
		    	 $("#userimage").attr("src","image/login/main_arrow_down.png");
	    	}
	    }*/
	});
	$("#userset").bind({
	    mouseover:function(){	 
	    	 $("#userset").show();
	    	 $("#userimage").attr("src","image/login/main_arrow_up.png");
	    },
	    mouseout:function(event){
	    	
	    	 $("#userset").hide();
	    	 $("#userimage").attr("src","image/login/main_arrow_down.png");
	    }		
	});

	$("#setPerson").click(function(){
		if(isadmin!=1&&isadminname!="admin"){
			window.location.href=$("#basePath").val()+"page/personal/PersonalData.jsp";
		}
		
	});
	
	$("#logout").click(function(){
		  if(confirm("你确定要退出吗？"))
	    {
           logoutfunction();
	    } else {
		     return;
		    }
	
		
	});
	showVersion();
	
});
function showVersion(){
	$.ajax({
		url:'login/versionCode',
		type:'post',
		dataType:'json',
		success:function(data){
			var version=data.result.obj;
			$("#version").text(version);
		}
		
	});
}
function searchInformation(){
	$.ajax({
		url:'rymanager/finduserInfo', 
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode!=1){
				
				if(data.result.resultcode==-1){
				    window.location.href=$("#basePath").val();
				}else if(data.result.resultcode==-2){
					$("#username").css("padding-top","11px");
					$("#username").html("游客");
				    $("#depart").html("");
				}else{
					alert(data.result.resultdesc);
				}
			}else{
				var glry=data.result.obj;
				var username = glry.ryxm;
				var rybmlist = glry.rybmlist;
				var department = "";
				for(var i=0;i<rybmlist.length;i++){
				    department = department+rybmlist[i].bmmc;
				    if(i+1<rybmlist.length){
				    	department = department+",";
				    }
				   
				}
				var rybh = glry.rybh;
				isadmin = rybh;	
				iaadminname=glry.ryxm;
			    $("#username").html(username);
			    $("#depart").html(department);		    
			    if(department==null||department==""){
			    	$("#username").css("padding-top","11px");
			    }

			}
		}
	});
}


function goToGLJG(){
	window.location.href=$("#basePath").val()+"page/manageins/ManageInstitutions.jsp";
}
function goToQLXX(){
	window.location.href=$("#basePath").val()+"page/bridge/BridgeInfo.jsp";
}
function goToLDXX(){
	window.location.href=$("#basePath").val()+"page/main/firstpage.jsp";
}
function goToTZXX(){
	window.location.href=$("#basePath").val()+"page/tzinfo/TzInfo.jsp";
}
function goToGLDT(){
	window.location.href=$("#basePath").val()+"page/highway/HighWayMap.jsp";
}
function goToXZJF(){
	window.location.href=$("#basePath").val()+"page/law/EnforceLaw.jsp";
}
function goToGLZ(){
	window.location.href=$("#basePath").val()+"page/roadstation/RoadStation.jsp";
}
function goToSPJK(){
	window.location.href=$("#basePath").val()+"page/videomonitor/VideoMonitor.jsp"
}
function goToJTL(){
	window.location.href=$("#basePath").val()+"page/traffic/Traffic.jsp";
}
function goToFWSS(){
	
}
function goToBZBX(){
	window.location.href=$("#basePath").val()+"page/marksign/MarkSign.jsp";
}
function goToTJFX(){
	window.location.href=$("#basePath").val()+"page/statistic/Statistic.jsp";
}
function goToSJTB(){
	window.location.href=$("#basePath").val()+"page/datasync/Datasync.jsp";
}
function goToContacts(){
	window.location.href=$("#basePath").val()+"page/contacts/Contacts.jsp";
}

function gotosystem(){
	window.location.href=$("#basePath").val()+"page/system/RoleManage.jsp";
}

function logoutfunction(){
	
	$.ajax({
		url:'login/userlogout', 
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode!=1){
				alert("登出异常，操作失败！");	
				}else{
				
				 window.location.href=$("#basePath").val();
			}
			
		}
	
});
}

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
    
     if( winHeight >=800){
    	 //浏览器可以显示
    	 var topheight =winHeight-111; 
    	 if(browserVersion.indexOf("MSIE 7")>-1){
    		 document.getElementById("footer").style.paddingTop = topheight+"px";
    	 }
    	 document.getElementById("footer").style.marginTop = topheight+"px";
    	 
     }else{
    	 
    	 var topheight =850-111; 
    	 if(browserVersion.indexOf("MSIE 7")>-1){
    		 document.getElementById("footer").style.paddingTop = topheight+"px";
    	 }
    	 document.getElementById("footer").style.marginTop = topheight+"px";
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
	  
	     if( winHeight >=800){
	    	 //浏览器可以显示
	    	 var topheight =winHeight-111; 
	    	 if(browserVersion.indexOf("MSIE 7")>-1){
	    		 document.getElementById("footer").style.paddingTop = topheight+"px";
	    	 }
	    	 
	    	 document.getElementById("footer").style.marginTop = topheight+"px";
	    	 
	     }else{

	    	 var topheight =850-111;
	    	 if(browserVersion.indexOf("MSIE 7")>-1){
	    		 document.getElementById("footer").style.paddingTop = topheight+"px";
	    	 }
	    	 document.getElementById("footer").style.marginTop = topheight+"px";
	     }
};



