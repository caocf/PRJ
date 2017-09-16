$(document).ready(function()
{
	//alert($("#knowledgeID").val());
	KnowLedgeShow();
});
//获取单条通知
function KnowLedgeShow()
{
	$.ajax( {
		url : 'NewsByID',
		type : "post",
		dataType : "json",
		data : {
			"ID" : $("#knowledgeID").val()
		},
		success : function(data)
		{
			$("#title").text(data.obj.title);
			$("#time").text("时间:"+data.obj.updatetime);
			document.getElementById("content").innerHTML=data.obj.content;
		}
	});
}
//删除通知
function Delete(id)
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
					window.history.go(-1);

				}
				else
				{
					alert("删除失败");
				}
			}
		});
}
//跳转到编辑页
function  Edit(id)
{
	window.location.href = $('#basePath').val() + "NoticeEdit?ID="+$("#knowledgeID").val();
}

