var shipid;//船舶id
$(document).ready(function(){
	shipid=GetQueryString('shipid');
	getxqByid();
})
/**
 * 获取船舶详情
 */
function getxqByid(){
	$.ajax({
		url:'ShipByID',
		type:'post',
		dataType:'json',
		data:{
			'id':shipid
		},
		success:function(data){
			$("#xqspan").html(
				"内河<br>"+
				data.publicUserEN.username+"<br>"+
				data.publicUserEN.tel+"<br>"+
				data.publicUserEN.usertype.typename+"<br>"+
				data.publicUserEN.region+"<br>"
			);
			$("#shipxqspan").html(
				data.regnumber+"<br>"+
				"待审批<br>"+
				data.shipname+"<br>"+
				data.regnumber+"<br>"
			);
			getimgname();
		}
	})
}
/**
 * 获取船舶证书名称
 */
function getimgname(){
	$.ajax({
		url:'ShipCeretByID',
		type:'post',
		dataType:'json',
		data:{
			'id':shipid
		},
		success:function(data){
			if(data==null){
				$("#shipxqspan").append(
					"暂无证书<br>"
				);
			}else{
				var  filename=data.con;
				var imgurl=$("#basePath").val()+data.con;
				filename=filename.substr(filename.lastIndexOf('/')+1);
				$("#shipxqspan").append(
					"<div style=\"width: 20px;height:20px;background-color: red;float: left;margin-top:5px;background-size: 100% 100%;background-repeat: no-repeat;background-image: url('"+imgurl+"');\" ></div>"+
					"<span style='float: left;line-height: 20px;margin-left: 10px;'>"+
					filename+"<br>"+
					"<span class='clickword'>预览</span>&nbsp;"+
					"<span class='clickword'>下载</span>"+
					"</span>"
				);
			}
		}
	})
}
/**
 * 提交审核结果
 */
function saveshenhe(){
	if($("input[name='radiobutton']:checked").val()==null){
		alert('请选择审批结果');
		return
	}
	$.ajax({
		url:'ShipCheckByID',
		type:'post',
		dataType:'json',
		data:{
			'id':shipid,
			'mark':$("#shenheword").val(),
			'status':$("input[name='radiobutton']:checked").val()
		},
		success:function(data){
			alert('审批成功');
			history.go(-1);
		}
	})
}
//href传参获取
function GetQueryString(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)
		return  unescape(r[2]);
}