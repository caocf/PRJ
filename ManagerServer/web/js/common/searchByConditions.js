//展开按条件查询
function showSelectByConditionsDiv() {
	$(".selectByConditions").show();
	showQuickSearch();
	/*$(".tableRolling").css("height","78%");*/
}
//隐藏按条件查询
function hideSelectByConditionsDiv() {
	$(".selectByConditions").hide();
/*	$(".tableRolling").css("height","96%");*/
}
function tip(){
	alert('请选择筛选条件');
}
//隐藏增加
function hideAddDiv() {
	$("#editInfo").hide();
}

// 展开模糊查询框
function showFuzzySearchTable() {
	$("#quickSearch").hide();
	$("#fuzzySearchTable").show();
}
function showQuickSearch() {
	$("#fuzzySearchTable").hide();
	$("#quickSearch").show();
}



/**
 * 选择全部
 */
function checkedAll(table){
	$("#"+table).find("tr").each(function(){
		$(this).find("td:first").each(function(){
//			if($("#IsCheckedAll").attr('checked')==true){
				$(".listDelete").attr("checked",true);
//			}else{
//				$(".listDelete").attr("checked",false);
//			}
			
		});
	});
}