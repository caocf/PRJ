var host1 = window.location.hostname;
var port = "80";
if (host1 == "www.trafficeye.com.cn") 
{
	host1 = "www.trafficeye.com.cn";
	//port = "80";
} else if (host1 == "182.254.143.172") {
	host1 = "182.254.143.172";
	//port = "7001";
}else{
	host1 = "182.254.143.172";
	//port = "80";
}

document.write("<script type='text/javascript' charset='utf-8' src='http://" + host1 + ":" + port + "/WebtAPI/wapi.js'></script>");
document.write("<script type='text/javascript' charset='utf-8' src='http://" + host1 + ":" + port + "/WebtAPI/data.js'></script>");