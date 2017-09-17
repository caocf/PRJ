$$(document).ready(function() {
	GetAbout();
});
function GetAbout() {
	$$.ajax( {
		url : 'GetAbout',
		type : 'post',
		dataType : 'json',
		success : function(data) {
			$$("#about_title").text(data.about.title);
			$$("#about_time").append("<p>" + data.about.date.replace("T", " ") + "</p>");
			$$("#help_content").append("<p>" + data.about.content + "</p>");
			
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}

	});
}
