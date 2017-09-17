$$(document).ready(function() {
	GetSuggestion();
});
function GetSuggestion() {
	$$.ajax( {
			url : 'GetSuggestion',
			type : "post",
			dataType : "json",
			success : function(data) { 
				$$("#NewsList").empty();
				var list = data.suggestions;
					if (list.length == 0) {
						TableIsNull();
					} else {
					  appendToTable(list, list.length);
					}				
			}
		});

}
function TableIsNull(){
	$$("#NewsList").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
}
function appendToTable(list, listLength){
	for ( var i = 0; i < listLength; i++) {
		var newTr = $$("<div class='right2'></div>");
		newTr.append($$("<p class='title11'>"+list[i].title+"</p>"));			
		newTr.append($$("<p class='type11'>类型："+list[i].typeName+"</p>"+"<br/>"));	
		newTr.append($$("<p class='phone11'>投诉人："+list[i].contact+"</p>"));
		newTr.append($$("<div class='rig_cont'><p>"+list[i].content+"</p></div>"));		
		$$("#NewsList").append(newTr);
	}
}