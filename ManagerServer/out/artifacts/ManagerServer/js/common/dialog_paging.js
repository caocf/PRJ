
var dialog_totalDisplayedPageNo = 5;//数字格式的页码并非显示全部，而是仅显示maxPageNumber个数字

/**
 * 下面的方法将被各模块页面中的JS调用，以在页面上ID号为pageDiv的DIV中显示页码及跳转信息
 * totalPage:总页数
 * selectedPage:当前选中页码
 * actionName:被请求的action名称
 * gotoPageMehtodName:跳转页面的js方法名称
 */
function dialog_printPage(totalPage,selectedPage,getDataMethodname,actionName,gotoPageMethodName,allTotal) {
	$("#dialog_allTotal").text(allTotal);
	//根据总页数决定是否需要显示页码信息，并处理存放首页、末页按钮的面板
	dialog_showPageDiv(totalPage,selectedPage,allTotal);
	//分页处理
	dialog_paging(selectedPage,totalPage,getDataMethodname,actionName,gotoPageMethodName);
}

/**
 * 判断总页数值是否等于<=1，如果是隐藏分页；
 * 否则先加载全部加载页面的面板，
 * 再判断是否=1，如果是 隐藏末页和下一页；
 * 否则，隐藏首页和上一页
 */
function dialog_showPageDiv(totalPage,selectedPage) {	
	//if (parseInt(totalPage) <= 1) {
//		$("#pageDiv").hide();
	//} else {
		$("#dialog_pageDiv").show();
		dialog_showLastAndNextBtn();
		dialog_showFirstAndPrevBtn();
		if (parseInt(selectedPage) == 1) {
			dialog_hideFirstAndPrevBtn();
		}
		if (parseInt(selectedPage) == parseInt(totalPage)) {
			dialog_hideLastAndNextBtn();
		}
	//}
}

/**
 * 在页面上显示按钮、数字和跳转三种形式的页码，并为表格中的行添加鼠标经过的效果
 */

function dialog_paging(selectedPage,totalPage,getDataMethodname,actionName,gotoPageMethodName) {
	
	dialog_cleanPageSpan();	
	//按钮页码
	/*$(".firstBtnSpan").append("<input type='button' class='firstPage' value='首页' onclick="+getDataMethodname+"('"+actionName+"',1)>");
	$(".prevBtnSpan").append("<input type='button' class='backPage'  value='上一页' onclick="+getDataMethodname+"('"+actionName+"',"+(parseInt(selectedPage)-1)+")>");										
	$(".nextBtnSpan").append("<input type='button' class='nextPage'  value='下一页' onclick="+getDataMethodname+"('"+actionName+"',"+(parseInt(selectedPage)+1)+")>");
	$(".lastBtnSpan").append("<input type='button' class='lastPage'  value='尾页' onclick="+getDataMethodname+"('"+actionName+"',"+parseInt(totalPage)+")>");	
	*/
	$(".dialog_firstBtnSpan").append("<a  href=javascript:"+getDataMethodname+"('"+actionName+"',1)> 首页&nbsp;</a>");
	$(".dialog_prevBtnSpan").append("<a href=javascript:"+getDataMethodname+"('"+actionName+"',"+(parseInt(selectedPage)-1)+")>上一页 </a>");										
	$(".dialog_nextBtnSpan").append("<a href=javascript:"+getDataMethodname+"('"+actionName+"',"+(parseInt(selectedPage)+1)+")>下一页 </a>");
	$(".dialog_lastBtnSpan").append("<a href=javascript:"+getDataMethodname+"('"+actionName+"',"+parseInt(totalPage)+")>尾页&nbsp;</a>");	
	
	//数字页码
	//从startPageNo开始，依次显示totalDispalyedPageNo个数字页码。显示时，当前页加粗，非当前页加"[]"
	var startPageNo = calculateStartPageNo(selectedPage,totalPage);
  
	if (parseInt(totalPage) <= parseInt(dialog_totalDisplayedPageNo)) {
    	var endPageNo=totalPage;
    }else{
    	var endPageNo= parseInt(startPageNo)+parseInt(dialog_totalDisplayedPageNo)-1;
    }
	for (var i = startPageNo; i <= endPageNo; i++) {
		if (selectedPage == i) {
			$(".dialog_pageNoSpan")
					.append(
							$("<a class='dCommonA' onclick="+getDataMethodname+"('"+actionName+"','"+parseInt(i)+"') style='color:#000000;font-weight:bold'>" + i + "</a>"));
		} else {
			$(".dialog_pageNoSpan").append(
					$("<a class='dCommonA' onclick="+getDataMethodname+"('"+actionName+"','"+parseInt(i)+"')>[" + i + "]</a>"));
		}
	}
	//鼠标移到自动加载页码 出现下滑线和手型
	$(".dialog_pageNoSpan a").mousemove(function() {
		 $(this).addClass('underlined');
	});
	//鼠标移开自动加载页码消失
	$(".dialog_pageNoSpan a").mouseout(function() {
		$(this).removeClass('underlined');// 鼠标离开移除hover样式 
	});
	
	//为每行添加一个hover事件,使表格有颜色变化		   
	 $('.dialog_listTable tr').mouseover(function() {
        $(this).addClass('mouseover');// 鼠标经过添加hover样式 
      }); 
     $('.dialog_listTable tr').mouseout( function() {		
        $(this).removeClass('mouseover');// 鼠标离开移除hover样式 
     });
     
    //跳转页码
     dialog_showGotoPageSpan(totalPage,selectedPage,gotoPageMethodName,actionName);
}

/**
 * 显示跳转页码面板
 */
function dialog_showGotoPageSpan(totalPage,selectedPage,gotoPageMethodName,actionName){	
	$(".dialog_gotoPageSpan").append("共"+totalPage+"页&nbsp;到第&nbsp;<input class='indexCommon' value="+selectedPage+">&nbsp;页"
		+"&nbsp;<a type='button' class='freeGo' onclick="+gotoPageMethodName+"('"+actionName+"',"+totalPage+")>跳转</a>");
}

/**
 * 根据用户当前选择的页码决定显示到页面上的数字页码的起始号
 */
function dialog_calculateStartPageNo(selectedPage,totalPage) {
	var startPageNo = 1;
	
	//求出有可能的末页页码
	var endPageNo = parseInt(selectedPage)+parseInt(dialog_totalDisplayedPageNo)-1 ;	
	
	//当总页数大于或等于末页页码时，起始页号即为当前选择的页号。否则，继续判断
	//当页码不足totalDisplayedPageNo个，起始页号为1。
	//当用户单击了totalPage-dialog_totalDisplayedPageNo+2至totalPage之间的任一页码时，起始页号均为totalPage - dialog_totalDisplayedPageNo+1。
	if (totalPage >= endPageNo) {
		startPageNo = selectedPage;		
	} else {
		if (parseInt(totalPage) > parseInt(dialog_totalDisplayedPageNo)) {			
			startPageNo = parseInt(totalPage) - parseInt(dialog_totalDisplayedPageNo)+1;			
		} 
	}
	return startPageNo;
}

//清空页码区
function dialog_cleanPageSpan(){
	$(".dialog_pageNoSpan").empty();
	$(".dialog_firstBtnSpan").empty();
	$(".dialog_prevBtnSpan").empty();
	$(".dialog_nextBtnSpan").empty();
	$(".dialog_lastBtnSpan").empty();
	$(".dialog_gotoPageSpan").empty();	
}

/**
 * 隐藏上一页和首页
 */
function dialog_hideFirstAndPrevBtn() {
	$(".dialog_firstBtnSpan").hide();
	$(".dialog_prevBtnSpan").hide();
}

/**
 * 显示上一页和首页
 */
function dialog_showFirstAndPrevBtn() {
	$(".dialog_firstBtnSpan").show();
	$(".dialog_prevBtnSpan").show();
}

/**
 * 隐藏下一页和末页
 */
function dialog_hideLastAndNextBtn() {
	$(".dialog_nextBtnSpan").hide();
	$(".dialog_lastBtnSpan").hide();
}

/**
 * 显示下一页和末页
 */
function dialog_showLastAndNextBtn() {
	$(".dialog_nextBtnSpan").show();
	$(".dialog_lastBtnSpan").show();
}


