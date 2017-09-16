$(document).ready(function(){
	$.ajax({
		url : 'module/appversioncheck/queryApp',
		type : 'post',
		dataType : 'json',
		data : {
			'appid': $("#appid").val()
		},
		success : function(data) {
			var appinfo = data.result.map.appinfo.appinfo;
			var appname = appinfo.appname;
			document.getElementById("apptitle").innerHTML = "查看<"+appname+"'";
			$("#appname").val(appname);
			
			$.ajax({
				url : 'module/appversioncheck/queryAppVersion',
				type : 'post',
				dataType : 'json',
				data : {
					'appvid': $("#appvid").val()
				},
				success : function(data) {
					var appvinfo = data.result.map.appVersionInfo;
					var versioncode = appvinfo.versioncode;
					var versionname = appvinfo.versionname;
					var updatelog = appvinfo.updatelog;
					var updatetype = appvinfo.updatetype;
					var downloadpath = appvinfo.downloadpath;
					var autoinstall = appvinfo.autoinstall;
					var respath = appvinfo.respath;
					

					document.getElementById("apptitle").innerHTML += versionname+"'>版本";
					
					$("#respath").val(getFileName(respath));
					$("#versioncode").val(versioncode);
					$("#versionname").val(versionname);
					$("#updatelog").val(updatelog);
					$("#updatetype")[0].selectedIndex = updatetype;
					$("#downloadpath").val(downloadpath);
					if (autoinstall == 0)
						$("#autoinstall")[0].selectedIndex = 1;
					if (autoinstall == 1)
						$("#autoinstall")[0].selectedIndex = 0;
				}
			});
		}
	});
});

function getFileName(url){
	var pos = url.lastIndexOf("/");
	if(pos == -1){
	   pos = url.lastIndexOf("\\");
	}
	var filename = url.substr(pos +1);
	return filename;
}

function closeview() {
	window.location.href="module/appversioncheck/appversioninfo.jsp?appid="+$("#appid").val();
}

function downloadfile() {
	window.location.href="module/appversioncheck/downloadAppVersionRes?appvid="+$("#appvid").val();
}