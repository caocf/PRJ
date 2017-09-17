function comminit(){
	// 未登录返回登录页面
	var username = $("#username").val();
	if (username == null || username == "" || username == "null") {
		window.location.href = $("#basePath").val() + "page/login/login.jsp";
	}

	/*// 返回按钮
	$('#back').click(function() {
		window.location.href = $("#basePath").val() + "page/home/home.jsp";
	});
*/
	// 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
	$('#tablixingyanghu_zhixian').bind(
			'click',
			function() {
				window.location.href = $("#basePath").val()
						+ "page/hdaoyanghu/lixingyanghu_zhixian.jsp";
			});	
	$('#tablixingyanghu_gugan').bind(
			'click',
			function() {
				window.location.href = $("#basePath").val()
						+ "page/hdaoyanghu/lixingyanghu_gugan.jsp";
			});
	$('#tabzhuanxianggongcheng').bind(
			'click',
			function() {
				window.location.href = $("#basePath").val()
						+ "page/hdaoyanghu/zhuanxianggongcheng.jsp";
			});
	$('#tabyingjiqiangtonggongcheng').bind(
		'click',
		function() {
			window.location.href = $("#basePath").val()
				+ "page/hdaoyanghu/yingjiqiangtonggongcheng.jsp";
		});
}