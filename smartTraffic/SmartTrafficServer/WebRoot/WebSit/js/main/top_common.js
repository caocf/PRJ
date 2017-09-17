var basePath;
$$(document).ready(function() {
	basePath = $$("#basePath").val();
	IsLogin();
	ShowWeather();
});
function IsLogin(){
	if(getCookie(Cookie_Name)){
		$$.ajax( {
			url : 'login',
			type : 'post',
			dataType : 'json',
			data : {
				'phone':getCookie(Cookie_Name),
				'password':getCookie(Cookie_Pwd)
			},
			success : function(data) {
				
				if(data.success){
					$$("#LoginUser").val(getCookie(Cookie_Name));
				}
			}

		});
	}
	if ($$("#LoginUser").val() == "null") {
		$$("#top_layout1_right_login").css("display", "none");
		$$("#top_layout1_right_nologin").css("display", "block");
	} else {
		$$("#top_layout1_right_login").css("display", "block");
		$$("#top_layout1_right_nologin").css("display", "none");
	}
}
//天气
function ShowWeather(){
	$$.ajax( {
		url : 'TodayWeather',
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if(data.results!=null){
				var weather=data.results[0];
				if(data.status=="success"){
					var weather_data=weather.weather_data;
					if(weather_data.length>0){
						$$("#weather").html(weather.currentCity+"&nbsp;"+weather_data[0].weather+"&nbsp;"+weather_data[0].temperature+"&nbsp;"+weather_data[0].wind);
					}else{
						$$("#weather").html("<label onclick='ShowWeather()'>出现错误，请刷新</label>");
					}
				}else{
					$$("#weather").html("<label onclick='ShowWeather()'>出现错误，请刷新</label>");
				}
			}else{
				$$("#weather").html("<label onclick='ShowWeather()'>出现错误，请刷新</label>");
			}
		},
		error : function(a, b, c) {
			$$("#weather").html("<label onclick='ShowWeather()'>出现错误，请刷新</label>");
		}

	});
	//setTimeout('ShowWeather()',10*60*1000); //指定10分钟刷新一次 
}
function TrafficeWebOver(id) {
	id.src = $$("#basePath").val()
			+ "WebSit/image/main/traffice_web_pressed.png";
}
function TrafficeWebOut(id) {
	id.src = $$("#basePath").val()
			+ "WebSit/image/main/traffice_web_normal.png";
}
//设为首页
function SetHome() {
	if (document.all) {
		try {
			document.body.style.behavior = 'url(#default#homepage)';
			document.body.setHomePage(window.location);
			alert("设置成功！");
		} catch (e) {
			alert("您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!");
		}
	} else {
		alert("您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!");
	}

}
//加入收藏
function AddFavorite() {
	var sURL = encodeURI(window.location);
	try {
		window.external.addFavorite(sURL, "智慧出行");
	} catch (e) {
		try {
			window.sidebar.addPanel("智慧出行", sURL, "");
		} catch (e) {

			alert("加入收藏失败，请使用Ctrl+D进行添加,或手动在浏览器里进行设置.");

		}
	}
}

function OpenTrafficMain(){
	//window.open("http://www.jxjtj.gov.cn/",'_blank');
	window.location.href="http://www.jxjtj.gov.cn/";
}
//----------------------登录--start-------------------


var isIe = (document.all) ? true : false;
// 设置select的可见状态
function setSelectState(state) {
	var objl = document.getElementsByTagName('select');
	for ( var i = 0; i < objl.length; i++) {
		objl[i].style.visibility = state;
	}
}

//弹出方法
function showMessageBox(wTitle, content, pos, wWidth) {
	closeWindow();
	var bWidth = document.body.clientWidth;// 阴影区域
	var bHeight = document.body.clientHeight;
	if (isIe) {
		setSelectState('hidden');
	}
	var back = document.createElement("div");
	back.id = "back";
	var styleStr = "top:0px;left:0px;position:absolute;background:#666;width:"
			+ bWidth + "px;height:" + bHeight + "px;";
	styleStr += (isIe) ? "filter:alpha(opacity=40);" : "opacity:0.40;";
	back.style.cssText = styleStr;
	document.body.appendChild(back);
	var mesW = document.createElement("div");
	mesW.id = "mesWindow";
	mesW.className = "mesWindow";
	mesW.innerHTML = "<div class='mesWindowContent' id='mesWindowContent'>"+ content + "</div>";
	styleStr = "left:" + (((pos.x - wWidth) > 0) ? (pos.x - wWidth) : pos.x)
			+ "px;top:" + (pos.y) + "px;position:absolute;width:" + wWidth + "px;";
	mesW.style.cssText = styleStr;
	document.body.appendChild(mesW);
}
function showBackground(obj, endInt) {
	obj.filters.alpha.opacity += 1;
	if (obj.filters.alpha.opacity < endInt) {
		setTimeout(function() {
			showBackground(obj, endInt)
		}, 8);
	}
}
// 关闭窗口
function closeWindow() {
	if (document.getElementById('back') != null) {
		document.getElementById('back').parentNode.removeChild(document
				.getElementById('back'));
	}
	if (document.getElementById('mesWindow') != null) {
		document.getElementById('mesWindow').parentNode.removeChild(document
				.getElementById('mesWindow'));
	}
	if (isIe) {
		setSelectState('');
	}
}
//测试弹出
function LoginWeb()
{  
	$$("#LoginWindow").attr("src","WebSit/page/register/text.jsp");
	var xh=parseInt(document.body.clientWidth/2+211);
	var yh=parseInt(window.screen.availHeight/2-200);
	var objPos = {x:xh, y:yh};
	messContent=$$("#openNew").html();
	showMessageBox('登录',messContent,objPos,422);
}
//-------------------------登录--end-------------------WebSit/page/main/selfInfo.jsp
//个人弹出
var OpenPersonDialog=0;
var OpenAPKDialog=0;
function ShowExitAndPwdDialog(){
	if($$("#APKDIV").attr("display")!="none"){
		$$("#top_layout1_right_login_img2").attr("src","WebSit/image/main/top_arrow_down_black.png");
		$$("#APKDIV").hide(); 
		OpenAPKDialog=0;
	}
	if(OpenPersonDialog==0){
		$$("#top_layout1_right_login_img1").attr("src","WebSit/image/main/top_arrow_up_blue.png");
		$$("#personDiv").show(); 
		OpenPersonDialog=1;
	}else{
		$$("#top_layout1_right_login_img1").attr("src","WebSit/image/main/top_arrow_down_blue.png");
		$$("#personDiv").hide(); 
		OpenPersonDialog=0;
	}
	
}
function PersonCenter(){
	window.location.href=basePath+"WebSit/page/main/selfInfo.jsp";
}
//apk下载
function ShowAPKDowloadDialog() {
	if($$("#personDiv").attr("display")!="none"){
		$$("#top_layout1_right_login_img1").attr("src","WebSit/image/main/top_arrow_down_blue.png");
		$$("#personDiv").hide(); 
		OpenPersonDialog=0;
	}
	if(OpenAPKDialog==0){
		$$("#top_layout1_right_login_img2").attr("src","WebSit/image/main/top_arrow_up_black.png");
		$$("#APKDIV").show(); 
		OpenAPKDialog=1;
	}else{
		$$("#top_layout1_right_login_img2").attr("src","WebSit/image/main/top_arrow_down_black.png");
		$$("#APKDIV").hide(); 
		OpenAPKDialog=0;
	}
	

}
function prExit() {
	if (confirm("是否确定退出？")) {
		$$.ajax( {
			url : 'UserExit',
			type : 'post',
			dataType : 'json',
			success : function(data) {
				//alert("已安全退出！");
				deleteMyCookie();
				ShowExitAndPwdDialog();
				$$(".top_logionname").text("null");
				$$("#LoginUser").val("null");
				if(LogAndExitRefresh==false){
					IsLogin();
				}else{
					window.location.reload();
				}
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}

		});

	}

}
function ShowLoginPersonDIV() {
	if (LogAndExitRefresh == false) {
		$$.ajax( {
			url : 'GetNameFromSession',
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.username != "") {
					$$("#LoginUser").val(data.code);
					$$(".top_logionname").text(data.username);
					IsLogin();
				} else {
					window.location.reload();
				}
			}
		});
	}else{
		window.location.reload();
		
	}
}
