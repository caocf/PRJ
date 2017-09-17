var startss = 5;
$$(document).ready(function() {
	timer(startss);
});
// 倒计时
function timer(ss) {

	if (ss == 1) {
		window.location.href = $$("#basePath").val()
		+ "WebPage/page/firstpage/FirstPage.jsp";
	} else {
		var ss = Number(ss - 1);// 计算剩余的秒数
		$$("#seconds").text(ss);
		setTimeout("timer(" + ss + ")", 1000);
	}

}


