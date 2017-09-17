var count=0;
var currentpage=1;
var edit = 0;//0是新增;1是编辑
/*编辑的编号*/
var sxxId = -1;
$(document).ready(function(){
	$("#top_text").text("系统设置");//top上的显示
	showpostMantainInfo('rymanager/postlistpage',1);
});
/* 显示视频维护信息列表*/
function showpostMantainInfo(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':10,
			'page':selectedPage
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode==-2){
				alert(data.result.resultdesc);
			}else{
				currentpage = selectedPage;
			var list=data.result.obj.data;
			if (list == "") {
				TableIsNull();
			} else {
				$(".addTr").remove();
				count=(selectedPage-1)*10+1;
				appendToTable(list);// 跳转页码的方法名称	
				gotoPageMethodName = "gotoPageNo";
				printPage(data.result.obj.pages, selectedPage, 'showpostMantainInfo',
						actionName, gotoPageMethodName, data.result.obj.total);
			}
		}
	  }
	});
}

function appendToTable(list){
	for(var i=0;i<list.length;i++){

		if(i%2==0){
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td style='background:#f9f9f9;'>&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9;'>"+count+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].zwmc)+"&nbsp;</td>"));
			//操作
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=showEditSpsxx('"+list[i].zwbh+"','"+list[i].zwmc+"')>编辑</a>" +
						"<a class='Operate1' onclick='deleteSpsxx("+list[i].zwbh+")'>删除</a></td>"));
				newTr.append($("<td style='background:#f9f9f9;'>&nbsp;</td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td >&nbsp;</td>"));
			newTr.append($("<td >"+count+"&nbsp;</td>"));
			newTr.append($("<td >"+judgeIsNull(list[i].zwmc)+"&nbsp;</td>"));
			//操作
			
				newTr.append($("<td><a class='Operate' onclick=showEditSpsxx("+list[i].zwbh+")>编辑</a>" +
						"<a class='Operate1' onclick='deleteSpsxx("+list[i].zwbh+")'>删除</a></td>"));
				newTr.append($("<td >&nbsp;</td>"));
		}
		count++;
		$("#videoinfo").append(newTr);
	}
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").val();
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").prop("value", "1");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)||parseInt(pageNo)==0) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showpostMantainInfo(actionName, pageNo);
	}
}

/*显示编辑的数据*/
var editzwbh="";
function showEditSpsxx(zwbh,zwmc){
	editzwbh=zwbh;
	edit=1;
	$("#zwmc").val(zwmc);
	$("#sfz_title").text("编辑职位")
	showfullbg();
	$("#addnewSpsxx").show(); 
}
function showAddSpsxx(){
	edit=0;
	$("#zwmc").val("");
	$("#sfz_title").text("新增职位");
	showfullbg();
	$("#addnewSpsxx").show(); 
}
function Trim(str,is_global) { 
	var result;
	result = str.replace(/(^\s+)|(\s+$)/g,"");
	if(is_global.toLowerCase()=="g")result = result.replace(/\s/g,""); 
	return result;
} 
function addsfz(){
	if(edit==0){
		$.ajax({
			url:'rymanager/savepost',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'zw.zwmc':Trim($("#zwmc").val(),'g')
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				if(data.result.resultcode!=1){
					alert(data.result.resultdesc);
				}else{
					alert("新增成功！");
					closewhsxxDiv();
					showpostMantainInfo('rymanager/postlistpage',currentpage);
				}
			}
			});
	}else{
		$.ajax({
			url:'rymanager/updatepost',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'zw.zwbh':editzwbh,
				'zw.zwmc':Trim($("#zwmc").val(),'g')
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				if(data.result.resultcode!=1){
					alert(data.result.resultdesc);
				}else{
					alert("编辑成功！");
					closewhsxxDiv();
					showpostMantainInfo('rymanager/postlistpage',currentpage);
				}
			}
			});
	}
}

function closewhsxxDiv(){
	$("#addnewSpsxx,#fullbg").hide();
}

function deleteSpsxx(zwbh){
	if(confirm("你确定要删除该职务吗？")){
		$.ajax({
			url:"rymanager/deletepost",
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'zw.zwbh':zwbh
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				if(data.result.resultcode!=1){
					alert(data.result.resultdesc);
				}else{
					alert("删除成功！");
					showpostMantainInfo('rymanager/postlistpage',currentpage);
				}
			}
		});
	}
	
}