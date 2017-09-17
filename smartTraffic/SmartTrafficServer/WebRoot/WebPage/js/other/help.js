$$(document).ready(function() {
	GetHelp();
});
function GetHelp() {
	$$.ajax( {
		url : 'GetHelp',
		type : 'post',
		dataType : 'json',
		success : function(data) {
			for ( var i = 0; i < data.helps.length; i++) {
				$$("#help_content").append("<p>" + data.helps[i] + "</p>");
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}

	});
}
