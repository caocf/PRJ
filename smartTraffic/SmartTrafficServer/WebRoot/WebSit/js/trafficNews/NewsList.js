$$(document).ready(function() {
	if($$("#id").val()!="null")
	{
		SearchDetailNewsForWeb();
	}
	
});
function SearchDetailNewsForWeb() {
	$$.ajax( {
		url : 'SearchDetailNewsForWeb',
		type : "post",
		dataType : "json",
		data : {
			'newsID' : $$("#id").val()
		},
		success : function(data) {
			$$("#title").text(data.newsDetailForWeb.title);
			$$("#time").text("时间："+DateTimeFormat(data.newsDetailForWeb.date)+
					" 来源："+DateIsNull(data.newsDetailForWeb.source,"未知")+" 点击数："+ DateIsNull(data.newsDetailForWeb.clickNum,"未知")
					+" 作者："+ DateIsNull(data.newsDetailForWeb.author,"未知"));
			
			if(data.newsDetailForWeb.imageSrc!=null){
				$$("#imageSrc").html("<img src='"+data.newsDetailForWeb.imageSrc+"'/>");
			}
			$$("#news_content").html(data.newsDetailForWeb.content);
		}
	});

}


