$(document).ready(function(){
	$("#system").addClass("active");
	$("#LogManage_li").addClass("active");
	showInfoInTable('../log/queryLogs',1);
})
/**
 * 显示日志列表信息
 * @param actionName：接口名
 * @param selectedPage：页码
 */
function showInfoInTable(actionName,selectedPage) {
	$(".bootpagediv").show();
	$("#nulltablediv").hide();
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'endTime':$("#endTime").val(),
			'startTime':$("#beginTime").val(),
			'optUser':$("#rolenameselectinput").val()
		},
		/*beforeSend : function() {
		 ShowLoadingDiv();// 获取数据之前显示loading图标。
		 },*/
		success:function(data){
			if(data.resultcode==-1){
				BackToLoginPage();
			}else if(data.resultcode==0){
				var list=data.records.data;
				$("#pagedetal").empty();
				$("#pagedetal").text(
					"当前页"+list.length+"条记录 共"+data.records.total+"条记录，每页"+data.records.rows+"条"
				);
				pagingmake(actionName,'showInfoInTable',selectedPage,data.records.pages);
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);
				}
			}else{
				alert(data.result.resultdesc);
			}

		}/*,
		 complete : function() {
		 CloseLoadingDiv();
		 }*/
	});
}
/**
 * 创建表格
 * @param list：数据集合
 */
function appendToTable(list){
	$("#loglist-info").empty();
	$("#loglist-info").append(
		"<tr>"+
			"<th><input type='checkbox' onclick='checkedOrNo(this)'/></th>"+
			"<th>用户名</th>"+
			"<th>操作内容</th>"+
			"<th>操作时间</th>"+
		"</tr>"
	);
	$(list).each(function(index,item){
		$("#loglist-info").append(
			"<tr>" +
				"<td><input type='checkbox' class='systemcheck' value='"+item.id+"'/></td>"+
				"<td>"+isnull(item.name,'--',1)+"</td>" +
				"<td>"+isnull(item.content,'--',1)+"</td>" +
				"<td>"+isnull(item.jlsj,'--',0)+"</td>" +
			"</tr>"
		);
	})

}

//checkbox全选
function checkedOrNo(obj){
	var isCheck = obj.checked;
	$('.systemcheck').each(function(){
		this.checked=isCheck;
	});
}

//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度
function isnull(str,isnullstr,islong) {
	var isnull = isnullstr;
	if (str == null || str == '' || str == 'null'||str == undefined) {
		return isnull;
	} else {
		if(islong==1){
			if(str.length>=20){
				return "<abbr title='"+str+"'>"+str.substr(0,20)+"</abbr>";
			}
		}
		return str;
	}
}

//删除日志
function dellog(){
	var ids='';
	if($('input.systemcheck:checked').length==0){
		alert('请选择要删除的日志');
		return
	}
	for(var i=0;i<$('input.systemcheck:checked').length;i++){
		if(i==($('input.systemcheck:checked').length-1)){
			ids+=$('input.systemcheck:checked:eq('+i+')').val();
		}else{
			ids+=$('input.systemcheck:checked:eq('+i+')').val()+',';
		}
	}
	$.ajax({
		url:'../log/deleteLogs',
		type:'post',
		dataType:'json',
		data:{
			'ids':ids
		},
		success:function(data){
			if(data.resultcode==-1){
				BackToLoginPage();
			}else if(data.resultcode==0){
				alert('删除成功！');
				showInfoInTable('../log/queryLogs',1);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}

function loadLogList() {
	window.location.href = $("#basePath").val() + "log/exportExcel?startTime=" + $("#beginTime").val() + "" +
		"&endTime=" + $("#endTime").val() + "&optUser=" + $("#rolenameselectinput").val();
}