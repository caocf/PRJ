var defValPass="请输入密码";
var defValUser="请输入用户名";
$(document).ready(function(){ 
	 findDimensions();
	showVersion();
	
	//判断是否记住密码
	$("#username").css("color","#999999");
	$("#username").val(defValUser);
	$('#showpassword').show();  
    $('#showpassword').val(defValPass);   
    $('#password').hide(); 
    $("#errordiv").hide();
    loginAuto();
	//用户名自动提示
   var inputUser = $("#username");
   inputUser.bind({
    focus: function() {
    $("#errordiv").hide();
     var _this = $(this);
     if (_this.val() == defValUser) {
      _this.val('');
      _this.css("color","black") ;
     }
    },
    blur: function() {
     var _this = $(this);
     if (_this.val() == '') {
      _this.css("color","#999999");
      _this.val(defValUser);
     
     }
    }
   
   });
 
   //密码自动提示
   $("#password").bind({
	   blur:function(){ 
	   if($(this).val()=="")  
	   {  
	   $(this).val("");  
	     
	   if($(this).attr("type") == "password"){  
	   $(this).hide();  
	   $('#showpassword').show();  
	   $('#showpassword').val(defValPass);     
	   }else{  
	      var pass = $('#showpassword').val();  
	      if(pass.length<1){  
	         $(this).hide();  
	          $('#showpassword').show();  
	        }  
	      }  
	    } 
	  
	   },
   focus:function(){
	   $("#errordiv").hide();
   },
   click:function(){
   	$("#errordiv").hide();
   }
   });  
   $("#showpassword").bind({
	focus:function(){  	
	    if($(this).val()==defValPass)  
	    {  
	     $(this).val("");  
	      if($(this).attr("type")=="text"){  
	        $(this).hide();  
	        $('#password').show();  
	        $('#password').val("");  
	        $('#password').focus();//加上  
	      }else{  
	        var pass = $('#password').val();  
	        if(pass.length<1){  
	         $(this).hide();  
	          $('#showpassword').show(); 
	          $("#errordiv").hide();
	        }  
	      }  
	    }
	    $("#errordiv").hide();
	   }
	   });	   	   
});
//登陆按钮
function usersubmit(){
	var remember = "2";
	//判断用户名密码是否为空
	var isremember =$("#isremember").is(':checked');
/*	alert(isremember);*/
	var username = $("#username").val();
	var password = $("#password").val();
	if($.trim(username)==""||username==null||username==defValUser){
		$("#errormessage").val("用户名不能为空");
		$("#errordiv").show();
		return;
	}else if($.trim(password)==""||password==null){
    	$("#errormessage").val("密码不能为空");
    	$("#errordiv").show();
    	return; 
		
	}else{
		if(isremember){
			 remember="1";
		}else{
			 remember="2";
			}
		
		userlogin(username,password,remember);
	}

}


function userlogin(username,password,isremember){
	$.ajax({
		url:'login/userlogin',
		type:'post',
		dataType:'json',
		data:{
			'username':username,
             'glry.rymm':password,
             'isRemember':isremember
		},
		success:function(data){
			var code=data.result.resultcode;
			var message = data.result.resultdesc;
			if(code!=1){
				$("#errormessage").val(message);
		    	$("#errordiv").show();
			}else{
				//取出原本访问的页面
				var url = getCookieValue("goUrl");
				if(url!=null&&url!=undefined&&url!=""){
					//删除记住的url
					deleteCookie("goUrl","/");
					window.location.href=url;
				}else{
				
				window.location.href=$("#basePath").val()+"page/login/main.jsp";
				};
				
			};
		
		}
	});
}


 function EnterPress(evt){ //回车
	 var evt=evt?evt:(window.event?window.event:null);//兼容IE和FF
	  if (evt.keyCode==13){
	   document.getElementById("login").onclick();
	 } 
 }

 function loginAuto(){
	 var password = "";
     var username = "";
	/* var usernamevalue =DecodeCookie(getCookieValue("username"));
	 var passwordvalue = getCookieValue("password");*/
	 $.ajax({
			url:'login/rememberName',
			type:'post',
			dataType:'json',
			success:function(data){
				var list = data.result.list;
		
				if(list!=null&&list.length>0){
					username = list[0];
					password = list[1];
				}
				if (username!= null&&username!=""&&username!="undefined"
					&&password!=null&&password!=""&&password!= "undefined"){
					 //密码框
					
					$("#username").css("color","black") ;
					$("#username").val(username);
					$('#showpassword').hide();  
					$('#password').show(); 
					$("#password").val(password);	
					$("#isremember").prop("checked", true);
				}
			  }
			});
	
	
	 
 }

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

 function findDimensions() //函数：获取尺寸 
 {  
	 var topheight = 0;
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
      if( winHeight >=650){
    	 
     	 //浏览器可以显示
     	  topheight =winHeight-70; 
     	 if(browserVersion.indexOf("MSIE 7")>-1){
     		 document.getElementById("footer").style.paddingTop = (topheight)+"px";
     	 }
     	 document.getElementById("footer").style.marginTop = topheight+"px";
     	 
      }else{
     	 
     	  topheight =650-70; 
     	 if(browserVersion.indexOf("MSIE 7")>-1){
     		 document.getElementById("footer").style.paddingTop = (topheight)+"px";
     	 }
     	 document.getElementById("footer").style.marginTop = topheight+"px";
      }
    
      // 获取窗口宽度
      if (window.innerWidth)
          winWidth = window.innerWidth;
      else if ((document.body) && (document.body.clientWidth))
             winWidth = document.body.clientWidth;
     
       if((winWidth-20)/2<923-240){
        
        $("#loimg").css("width",(winWidth-20)/2+240);
        
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
 	  
 	     if( winHeight >=650){
 	    	 var topheight =winHeight-70; 
 	    	 if(browserVersion.indexOf("MSIE 7")>-1){
 	    		 document.getElementById("footer").style.paddingTop = (topheight)+"px";
 	    	 }
 	    	 
 	    	 document.getElementById("footer").style.marginTop = topheight+"px";
 	    	 
 	     }else{

 	    	 var topheight =650-70;
 	    	 if(browserVersion.indexOf("MSIE 7")>-1){
 	    		 document.getElementById("footer").style.paddingTop = (topheight)+"px";
 	    	 }
 	    	 document.getElementById("footer").style.marginTop = topheight+"px";
 	     }
 	     
 	     
 	    // 获取窗口宽度
 	      if (window.innerWidth)
 	          winWidth = window.innerWidth;
 	      else if ((document.body) && (document.body.clientWidth))
 	             winWidth = document.body.clientWidth;
 	     
 	       if((winWidth-20)/2<923-240){
 	        $("#loimg").css("width",(winWidth-20)/2+240);
 	       
 	       }
 };

 function frontdownLoad(){
		window.location.href=$("#basePath").val()+"login/frontdownload";
	}





