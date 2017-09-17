var count=0;
var currentpage=1;
var edit = 0;//0是新增;1是编辑
/*编辑的编号*/
var sxxId = -1;
$(document).ready(function(){
	$("#top_text").text("系统设置");//top上的显示
	showVideoMantainInfo('videolist/spsxxlist',1);
	$("#lxdm").change(function(){ 
		var select = $(this).children('option:selected').val();
		var fxm=-1;
		fxmchangefunc(select,fxm);
	});
		
		
});
/* 显示视频维护信息列表*/
function showVideoMantainInfo(actionName,selectedPage){
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
			    $(".addTr").empty();
		    	$(".addTr").remove();
			var list=data.result.obj.data;
			if (list == "") {
				TableIsNull();
			} else {
				
					count=(selectedPage-1)*10+1;
				
				appendToTable(list);// 跳转页码的方法名称	
				gotoPageMethodName = "gotoPageNo";
				printPage(data.result.obj.pages, selectedPage, 'showVideoMantainInfo',
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
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].lxdm)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].fxm)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].sxxname)+"&nbsp;</td>"));
			//操作
			
				newTr.append($("<td style='background:#f9f9f9'><a class='Operate' onclick=showEditSpsxx("+list[i].sxxId+")>编辑</a>" +
						"<a class='Operate1' onclick='deleteSpsxx("+list[i].sxxId+")'>删除</a></td>"));
				newTr.append($("<td style='background:#f9f9f9;'>&nbsp;</td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			newTr.append($("<td >&nbsp;</td>"));
			newTr.append($("<td >"+count+"&nbsp;</td>"));
			newTr.append($("<td >"+judgeIsNull(list[i].lxdm)+"&nbsp;</td>"));
			newTr.append($("<td >"+judgeIsNull(list[i].fxm)+"&nbsp;</td>"));
			newTr.append($("<td >"+judgeIsNull(list[i].sxxname)+"&nbsp;</td>"));
			//操作
			
				newTr.append($("<td><a class='Operate' onclick=showEditSpsxx("+list[i].sxxId+")>编辑</a>" +
						"<a class='Operate1' onclick='deleteSpsxx("+list[i].sxxId+")'>删除</a></td>"));
				newTr.append($("<td >&nbsp;</td>"));
		}
		
		count++;
		$("#videoinfo").append(newTr);
	}
}
/*显示编辑的数据*/
function showEditSpsxx(sxxbh){
	$.ajax({
		url:"videolist/spsxxoption",
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'type':1
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode==-2){
				alert(data.result.resultdesc);
			}else{
				var list=data.result.list;
				appendToLxdmselect(list);
			}
		}
	});
	if(sxxbh!=-1){//是编辑框
		editContent(sxxbh);
		edit=1;
		sxxId = sxxbh;
	}
	else{
		edit=0;
	}
	showfullbg();
	$("#addnewSpsxx").show(); 
}

function appendToLxdmselect(list){
	//清空select内容
	$("#lxdm").empty();
	$("#fxm").empty();
	newOp = $("<option value='-1' selected='true' disabled='true'>请选择</option>");
	$("#lxdm").append(newOp);
	for(var i=0;i<list.length;i++){
		newOp = $("<option id='"+list[i]+"'>"+list[i]+"</option>");
		$("#lxdm").append(newOp);
	}
}

function fxmchangefunc(select,fxm){
	$.ajax({
		url:"videolist/spsxxoption",
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'type':4,
			'selectvalue':select
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode==-2){
				alert(data.result.resultdesc);
			}else{
				var list=data.result.list;
				appendToFxmselect(list,fxm);
			}
		}
	});
	
}
function appendToFxmselect(list,fxm){
	//清空select内容
	$("#fxm").empty();
	newOp = $("<option value='-1' selected='true' disabled='true'>请选择</option>");
	$("#fxm").append(newOp);
	for(var i=0;i<list.length;i++){
		newOp = $("<option id='"+list[i]+"'>"+list[i]+"</option>");
		$("#fxm").append(newOp);
		
	}
	if(fxm!=-1){
	$("#fxm").val(fxm);
	}
	
}

function addsfz(){
	//判断是否为空
	var selectlxdm = $("#lxdm").children('option:selected').val();
	if(selectlxdm==-1){
		alert("请选择路线代码");
		return;
	}
	var selectfxm= $("#fxm").children('option:selected').val();
	if(selectfxm==-1){
		alert("请选择方向名");
		return;
	}
	var selectsxx= $("#sxx").children('option:selected').val();
	if(selectsxx==-1){
		alert("请选择上下行");
		return;
	}
	
	
	if(edit==0){
		actionname="videolist/savespsxx";
		$.ajax({
			url:actionname,
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'spsxx.lxdm':selectlxdm,
				'spsxx.fxm':selectfxm,
				'spsxx.sxx':selectsxx
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				if(data.result.resultcode!=1){
					alert(data.result.resultdesc);
				}else{
					if(edit==0){
						alert("新增成功！");
					}else{
						alert("编辑成功！");
					}
					
					closewhsxxDiv();
					showVideoMantainInfo('videolist/spsxxlist',currentpage);
				}
			}
			});
	}else{
		actionname="videolist/updatespsxx";
	$.ajax({
		url:actionname,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'spsxx.lxdm':selectlxdm,
			'spsxx.fxm':selectfxm,
			'spsxx.sxx':selectsxx,
			'spsxx.sxxId':sxxId
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode!=1){
				alert(data.result.resultdesc);
			}else{
				if(edit==0){
					alert("新增成功！");
				}else{
					alert("编辑成功！");
				}
				
				closewhsxxDiv();
				showVideoMantainInfo('videolist/spsxxlist',currentpage);
			}
		}
		});
	}
}

/**
 * 加载编辑内容
 */
function editContent(sxxbh){
	$.ajax({
		url:"videolist/spsxxunique",
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'sxxId':sxxbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode!=1){
				alert(data.result.resultdesc);
			}else{
				edit=1;
				var sxx = data.result.obj;
				var lxdm = sxx.lxdm;
				var fxm = sxx.fxm;
				var sxxdm = sxx.sxx;
				$("#lxdm").val(lxdm);
				$("#lxdm").change(fxm);
				$("#sxx").val(sxxdm);
				var select = $("#lxdm option:selected").val();
				fxmchangefunc(select,fxm);
				
			}
		}
	
   });
}



function closewhsxxDiv(){
	$("#lxdm").empty();
	$("#fxm").empty();
	$("#addnewSpsxx,#fullbg").hide();
}

function deleteSpsxx(sxxId){
	if(confirm("你确定要删除该维护信息吗？")){
	$.ajax({
		url:"videolist/deletespsxx",
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'sxxId':sxxId
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode!=1){
				alert(data.result.resultdesc);
			}else{
					alert("删除成功！");
				showVideoMantainInfo('videolist/spsxxlist',currentpage);
			}
		}
		});
	}
	
}
/*更新数据库信息*/
function updatesqldate(){
	$.ajax({
		url:"videolist/updatesqldate",
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		beforeSend:function(){
			ShowLoadingDiv();
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}
			if(data.result.resultcode!=1){
				alert(data.result.resultdesc);
			}else{
				alert("更新成功！");
			}
       },
       complete:function(){
    	   CloseLoadingDiv();
       }
	});	
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
		showVideoMantainInfo(actionName, pageNo);
	}
}

