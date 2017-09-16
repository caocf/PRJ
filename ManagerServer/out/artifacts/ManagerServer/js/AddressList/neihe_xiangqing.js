var qu;//所在区域
var status;//用户状态
var userid;//用户id
$(document).ready(function(){
	switch (parseInt(GetQueryString('type'))){
		case 1:
			qu='内河';
			$("#xingqingtitle").html("用户管理&nbsp;&gt;&nbsp;内河&nbsp;&gt;&nbsp;详情");
			break;
		case 2:
			qu='码头';
			$("#xingqingtitle").html("用户管理&nbsp;&gt;&nbsp;码头&nbsp;&gt;&nbsp;详情");
			break;
		case 3:
			qu='沿海';
			$("#xingqingtitle").html("用户管理&nbsp;&gt;&nbsp;沿海企业&nbsp;&gt;&nbsp;详情");
			break;
		default :
			break;
	}
	userid=GetQueryString('id');
	getxqByid();
})
/**
 * 获取用户详情
 */
function getxqByid(){
	$.ajax({
		url:'UserInfoByID',
		type:'post',
		dataType:'json',
		data:{
			'id':userid
		},
		success:function(data){
			status=data.status;
			var statusstr='<span style="color: green">正常</span>';
			$("#statusbtn").text('禁用');
			if(status==0){
				$("#statusbtn").text('启用');
				statusstr='<span style="color: red">禁用</span>';
			}
			$("#xqspan").html(
				qu+"<br>"+
				data.username+"<br>"+
				data.tel+"<br>"+
				data.usertype.typename+"<br>"+
				data.region+"<br>"+
				statusstr+"<br>"
			)
			getshipByusername(data.username);
		}
	})
}
/**
 * 根据用户名获取用户绑定船舶
 * @param username：用户名
 */
function getshipByusername(username){
	$.ajax({
		url:'myshiplist',
		type:'post',
		dataType:'json',
		data:{
			'Username':username
		},
		success:function(data){
			$(".listTable .addTr").empty();
			$(".listTable .addTr").remove();
			General_List=data.data;
			if(General_List.length==0){
				TableIsNull();
			}else{
				appendToTable(General_List);
			}
		}
	})
}
/**
 * 加载列表
 * @param list：列表数据
 */
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		getimgname(list[i],i);
	}

}
/**
 * 获取船舶证书名称
 * @param shipid：船舶id
 */
function getimgname(shipdata,i){
	$.ajax({
		url:'ShipCeretByID',
		type:'post',
		dataType:'json',
		data:{
			'id':shipdata.shipid
		},
		success:function(data){
			var imgnamestr;
			var newTr=$("<tr class='addTr'></tr>");
			newTr.append($("<td class='tdborder firsttdorth'>"+(i+1)+"</td>"));
			newTr.append($("<td class='tdborder'>"+shipdata.shipname+"</td>"));
			newTr.append($("<td class='tdborder'>"+shipdata.regnumber+"</td>"));
			if(data==null){
				imgnamestr= "暂无证书";
			}else{
				var  filename=data.con;
				filename=filename.substr(filename.lastIndexOf('/')+1);
				imgnamestr= filename+"&nbsp;<span style='cursor: pointer;color:#0099CC;'>下载</span>";
			}
			newTr.append($("<td class='tdborder'>"+imgnamestr+"</td>"));
			$(".listTable").append(newTr);
		}
	})
}
/**
 * 列表为空显示方法
 */
function TableIsNull(){
	$(".addTr").empty();
	$(".addTr").remove();
	var newTr=$("<tr class='addTr'></tr>");
	newTr.append($("<td colspan='4' style='text-align:center'>暂无数据</td>"));
	$(".listTable").append(newTr);
}
/**
 * 改变状态
 */
function changestatus(){
	var newstatus=0;
	if(status==0){
		newstatus=1;
	}
	$.ajax({
		url:'setUserStatusByID',
		type:'post',
		dataType:'json',
		data:{
			'id':userid,
			'status':newstatus
		},
		success:function(data){
			alert('修改成功');
			getxqByid();
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
