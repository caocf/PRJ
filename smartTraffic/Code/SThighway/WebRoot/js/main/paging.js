
var totalDisplayedPageNo = 5;//数字格式的页码并非显示全部，而是仅显示maxPageNumber个数字
var select_page="";
/**
 * 下面的方法将被各模块页面中的JS调用，以在页面上ID号为pageDiv的DIV中显示页码及跳转信息
 * totalPage:总页数
 * selectedPage:当前选中页码
 * actionName:被请求的action名称
 * gotoPageMehtodName:跳转页面的js方法名称
 */
function printPage(totalPage,selectedPage,getDataMethodname,actionName,gotoPageMethodName,allTotal) {
	select_page=selectedPage;
	$("#allTotal").text(allTotal);
	//根据总页数决定是否需要显示页码信息，并处理存放首页、末页按钮的面板
	showPageDiv(totalPage,selectedPage,allTotal);
	//分页处理
	paging(selectedPage,totalPage,getDataMethodname,actionName,gotoPageMethodName);
}

/**
 * 判断总页数值是否等于<=1，如果是隐藏分页；
 * 否则先加载全部加载页面的面板，
 * 再判断是否=1，如果是 隐藏末页和下一页；
 * 否则，隐藏首页和上一页
 */
function showPageDiv(totalPage,selectedPage) {	
	//if (parseInt(totalPage) <= 1) {
//		$("#pageDiv").hide();
	//} else {
	select_page=selectedPage;
		$("#pageDiv").show();
		showLastAndNextBtn();
		showFirstAndPrevBtn();
		if (parseInt(selectedPage) == 1) {
			hideFirstAndPrevBtn();
		}
		if (parseInt(selectedPage) == parseInt(totalPage)) {
			hideLastAndNextBtn();
		}
	//}
}

/**
 * 在页面上显示按钮、数字和跳转三种形式的页码，并为表格中的行添加鼠标经过的效果
 */

function paging(selectedPage,totalPage,getDataMethodname,actionName,gotoPageMethodName) {
	select_page=selectedPage;
	cleanPageSpan();	
	$(".firstBtnSpan").append("<a  onclick="+getDataMethodname+"('"+actionName+"',1)> 首页</a>");
	$(".prevBtnSpan").append("<a onclick="+getDataMethodname+"('"+actionName+"',"+(parseInt(selectedPage)-1)+")>上一页 </a>");										
	$(".nextBtnSpan").append("<a onclick="+getDataMethodname+"('"+actionName+"',"+(parseInt(selectedPage)+1)+") >下一页 </a>");
	$(".lastBtnSpan").append("<a  onclick="+getDataMethodname+"('"+actionName+"',"+parseInt(totalPage)+")>尾页</a>");	
	
	//数字页码
	//从startPageNo开始，依次显示totalDispalyedPageNo个数字页码。显示时，当前页加粗，非当前页加"[]"
	var startPageNo = calculateStartPageNo(selectedPage,totalPage);
  
	if (parseInt(totalPage) <= parseInt(totalDisplayedPageNo)) {
    	var endPageNo=totalPage;
    }else{
    	var endPageNo= parseInt(startPageNo)+parseInt(totalDisplayedPageNo)-1;
    }
	for (var i = startPageNo; i <= endPageNo; i++) {
		if (selectedPage == i) {
			$(".pageNoSpan")
					.append(
							$("<a class='dCommonA' onclick="+getDataMethodname+"('"+actionName+"','"+parseInt(i)+"') style='color:#000000;font-weight:bold'>" + i + "</a>&nbsp;"));
		} else {
			$(".pageNoSpan").append(
					$("<a class='dCommonA' onclick="+getDataMethodname+"('"+actionName+"','"+parseInt(i)+"')>[" + i + "]</a>&nbsp;"));
		}
	}
	//鼠标移到自动加载页码 出现下滑线和手型
	$(".pageNoSpan a").mousemove(function() {
		 $(this).addClass('underlined');
	});
	//鼠标移开自动加载页码消失
	$(".pageNoSpan a").mouseout(function() {
		$(this).removeClass('underlined');// 鼠标离开移除hover样式 
	});
	
	//为每行添加一个hover事件,使表格有颜色变化		   
	 $('.listTable tr').mouseover(function() {
        $(this).addClass('mouseover');// 鼠标经过添加hover样式 
      }); 
     $('.listTable tr').mouseout( function() {		
        $(this).removeClass('mouseover');// 鼠标离开移除hover样式 
     });
     
    //跳转页码
    showGotoPageSpan(totalPage,selectedPage,gotoPageMethodName,actionName);
}

/**
 * 显示跳转页码面板
 */
function showGotoPageSpan(totalPage,selectedPage,gotoPageMethodName,actionName){	
	select_page=selectedPage;
	$(".gotoPageSpan").append("共"+totalPage+"页&nbsp;到第<input class='indexCommon'style='border:1; width:15px' value="+selectedPage+" >页"
		+"&nbsp;<a class='freeGo' onclick="+gotoPageMethodName+"('"+actionName+"',"+totalPage+") >跳转</a>");
}

/**
 * 根据用户当前选择的页码决定显示到页面上的数字页码的起始号
 */
function calculateStartPageNo(selectedPage,totalPage) {
	select_page=selectedPage;
	var startPageNo = 1;
	
	//求出有可能的末页页码
	var endPageNo = parseInt(selectedPage)+parseInt(totalDisplayedPageNo)-1 ;	
	
	//当总页数大于或等于末页页码时，起始页号即为当前选择的页号。否则，继续判断
	//当页码不足totalDisplayedPageNo个，起始页号为1。
	//当用户单击了totalPage-totalDisplayedPageNo+2至totalPage之间的任一页码时，起始页号均为totalPage - totalDisplayedPageNo+1。
	if (totalPage >= endPageNo) {
		if(selectedPage<=3){//选中的页码显示在中间
			startPageNo=1;
		}else{
			startPageNo = selectedPage-2;
		}
	} else {
		if (parseInt(totalPage) > parseInt(totalDisplayedPageNo)) {			
			startPageNo = parseInt(totalPage) - parseInt(totalDisplayedPageNo)+1;			
		} 
	}
	return startPageNo;
}

//清空页码区
function cleanPageSpan(){
	$(".pageNoSpan").empty();
	$(".firstBtnSpan").empty();
	$(".prevBtnSpan").empty();
	$(".nextBtnSpan").empty();
	$(".lastBtnSpan").empty();
	$(".gotoPageSpan").empty();	
}

/**
 * 隐藏上一页和首页
 */
function hideFirstAndPrevBtn() {
	$(".firstBtnSpan").hide();
	$(".prevBtnSpan").hide();
}

/**
 * 显示上一页和首页
 */
function showFirstAndPrevBtn() {
	$(".firstBtnSpan").show();
	$(".prevBtnSpan").show();
}

/**
 * 隐藏下一页和末页
 */
function hideLastAndNextBtn() {
	$(".nextBtnSpan").hide();
	$(".lastBtnSpan").hide();
}

/**
 * 显示下一页和末页
 */
function showLastAndNextBtn() {
	$(".nextBtnSpan").show();
	$(".lastBtnSpan").show();
}

/**
 * 清除表格中的所有数据
 */
function deleteTr() {
	$(".addTr").remove();
	$(".addTr").empty();
	$(".tableRolling div").remove();
	$(".tableRolling div").empty();
	
}

/**
 * 删除表格中某一行
 * @param {Object} trName
 */
function deleteItemTr(trName) {
	$("#" + trName).remove();
	$("#" + trName).empty();
}
