var tj_array=new Array();
function ChangeTextValue(thisop) {
	var tj_tags = thisop.value;
	if (tj_tags.length > 2) {
	
			$.ajax( {
				url : "SearchShipName",
				type : "post",
				data : {
					'shipName' : tj_tags,
					'cpage' : 1
				},
				success : function(data) {
					var list = data.list;

					if (list != null) {
						for ( var i = 0; i < list.length; i++) {
							tj_array[i] = list[i].shipName;
						}
					}
					$("#cbname").autocomplete( {
						source : tj_array,
						appendTo : "#autocomplete_div"
					});

				}
			});
		}

}
