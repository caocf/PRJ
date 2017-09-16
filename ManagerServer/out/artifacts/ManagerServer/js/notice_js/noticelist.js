$(document).ready(function()
{
		$("#nav li").hover
		(
			function() {
				$(this).find("ul").show(100);
			},
			function() {
				$(this).find("ul").hide(300);
			}
		);

	getnoticelist(1);

});

function onOptionClick(obj,id)
{
	//alert();
	var v=$(obj).text();
	$("#"+id).text(v);
	//alert(v);
	getnoticelist(1);

}
//默认条件通知列表
function getnoticelist(page)
{
	$.ajax({
		url:'NoticeListByItems',
		type:'post',
		dataType:'json',
		data:{
			'region':$("#region").text(),
			'type':$("#type").text(),
			'page':page,
			'like':$("#tip").val(),
		},
		success:function(data)
		{
			var list=data.s1;
			//alert(l[1].title);
			$(".datalist").remove();
			pagingmake("", 'getnoticelist', page, data.resultcode);
			$(list).each(function (index, item)
			{
				//alert(item.title);
				$('#list').append
				(
					"<tr class='datalist'>"+
					"<td align='center'><input type='checkbox' class='checkInput' value='"+item.id+"'></td>"+
					"<td align='left'><a class='uniquenews' href='page/noticeforsee/NoticeDetail.jsp?id="+item.id+"'>"+isnull(item.title)+"</a></td>"+
					"<td align='center'>"+isnull(item.updatetime)+"</td>"+
					"<td align='center'><span class='caozuoword' style='color: green;' onclick=\'edit("+item.id+")\'>编辑" +
							"</span>&nbsp;&nbsp;&nbsp;<span style='color:#fa5252;' class='caozuoword' onclick='deleteone("+item.id+","+page+")'>删除</span></td>" +
					"</tr>"
				)
			});
		}
	});
}

function isnull(str) {
	var isnull = '';
	if (str == null || str == '' || str == 'null'||str == undefined) {
		return isnull;
	} else {
		return str;
	}
}
//删除单个
function deleteone(id,page)
{
	if(!confirm('确定删除？')){
		return
	}

	$.ajax(
		{
			url:'DeleteNoticeByID',
			type:'post',
			dataType:'json',
			data:{
				'id':id,
			},
			success:function(data)
			{
				var result=data.resultcode;
				if(result==1)
				{
					alert("删除成功");
					getnoticelist(page);

				}
				else
				{
					alert("删除失败");
				}
			}
		});
	
}
//全部选中
function checkAll(obj)
{
	var checkRes = obj.checked;
	$(".checkInput").each(function(){
		this.checked = checkRes;
	});
}

//删除全部
function deleteAll()
{
	var ids=new Array();
	$('input:checkbox[class=checkInput]:checked').each(function(i){
		ids.push($(this).val());
	});
	if(ids[0]==null){
		alert("请先选择删除的资讯");
		return;
	}
	deleteh(ids);

}
//批量删除通知
function deleteh(ids)
{
	if(!confirm('确定删除？')){
		return;
	}
	$.ajax({
		url : 'DeleteNoticeByIDs',
		type : 'post',
		dataType : 'json',
		data: {
			'ids':ids,
		},
		success : function (data)
		{
			alert('删除成功');
			getnoticelist(1);
		}
	})
}