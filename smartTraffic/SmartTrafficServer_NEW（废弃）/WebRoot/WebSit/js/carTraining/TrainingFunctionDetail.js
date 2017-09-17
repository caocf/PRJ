$$(document).ready(function() {
	if($$("#id").val()!="null")
	{
		SearchDrivingDetailNews();
	}
	
});
function SearchDrivingDetailNews() {
	$$.ajax( {
		url : 'SearchDrivingDetailNews',
		type : "post",
		dataType : "json",
		data : {
			'id' : $$("#id").val()
		},
		success : function(data) {
			$$("#title").text(data.drirvingSchooleDetailNews.title);
			//$$("id").text();
			$$("#time").text("时间："+DateTimeFormat(data.drirvingSchooleDetailNews.date)+" id："+ DateIsNull(data.drirvingSchooleDetailNews.id,"未知"));
			
			if(data.drirvingSchooleDetailNews.imageSrc!=null){
				$$("#imageSrc").html("<img src='"+data.drirvingSchooleDetailNews.imageSrc+"'/>");
			}
			$$("#news_content").text(data.drirvingSchooleDetailNews.content);
		}
	});

}


