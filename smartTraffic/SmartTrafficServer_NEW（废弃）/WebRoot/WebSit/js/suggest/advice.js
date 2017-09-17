$$(document).ready(function() {

	SearchAllSuggestionType();
});

function SearchAllSuggestionType() {
	$$
			.ajax( {
				url : "SearchAllSuggestionType",
				type : "post",
				success : function(data) {
					var list = data.suggestionTypes;
					for ( var i = 0; i < list.length; i++) {
						if (i == 0) {
							$$("#regionCode .CRselectValue").text(list[i].name);
							$$("#regionCode .abc_CRtext").val(
									list[i].name);
							$$("#regionCode .abc").val( list[i].id);
							$$("#regionCode .CRselectBoxOptions")
									.append(
											$$("<li class='CRselectBoxItem'><a class='selected' rel="
													+ list[i].id
													+ ">"
													+ list[i].name
													+ "</a></li>"));
						} else {
							$$("#regionCode .CRselectBoxOptions").append(
									$$("<li class='CRselectBoxItem'><a  rel="
											+ list[i].id + ">" + list[i].name
											+ "</a></li>"));
						}
						;
					}
					SelectOption("regionCode", 150);
				}
			});
}

function GetSuggestion(){
	$$.ajax( {
		url : 'GetSuggestion',
		type : 'post',
		dataType : 'json',
		data : {
		    'content':$$(".right_cont_kuang").text(),
		     'contact' : $$("#phone").val()
		},
		beforeSend : function() {
			//开始提交数据
		},
		success : function(data) {
			//处理后台获取的数据
			if(data.success){
				alert("登录成功");
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		},
		complete : function() {
			//数据处理完成
	}

	});
}
