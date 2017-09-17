var searchInput="";
var search_xzqhbm="";//行政区划编码
var select_bzbxlxbh="";//选中的标志标线类型
$(document).ready(function(){
	//$("#top_text").text("标志标线");//top上的显示
	$("#mark_select").attr("class","mtop_select");
	showPartInfo();
	findMarkTypeInfo();
});
//显示行政区划
function showPartInfo(){
	$.ajax({
		url:'qljbxxlist/xzqhlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				appendToPart(list);
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function appendToPart(list){
	for(var i=0;i<list.length;i++){
		if(list[i].sjxzqhdm!=null){
			var newLi=$("<li class='left_no_select_li' id='left_no_select_li"+(i+1)+"' onclick=showThePartMark('"+list[i].xzqhdm+"','"+list[i].xzqhmc+"',this)>"+list[i].xzqhmc+"</li>");
			$("#left_select_child1").append(newLi);
		}
		document.getElementById("xzqh").options.add(new Option(list[i].xzqhmc, list[i].xzqhdm));
		document.getElementById("xzqh1").options.add(new Option(list[i].xzqhmc, list[i].xzqhdm));
	}
	/*if($("#num").val()!=""&&$("#num").val()!="null"){
		if($("#num").val()==1){
			$("#area_name").text("嘉兴市标志标线信息");
			$(".left_no_select").attr("class","left_select");
			showMarkSignInfo('bzbxmanager/bzbxlist',1);
		}else{
			$("#left_no_select_li"+$("#num").val()).attr("class","left_select_li");
			search_xzqhbm=list[$("#num").val()-1].xzqhdm;
			$("#area_name").text(list[$("#num").val()-1].xzqhmc+"标志标线信息");
			showMarkSignInfo('bzbxmanager/bzbxlist',1);
		}
	}else{
		$("#area_name").text("嘉兴市标志标线信息");
		$(".left_no_select").attr("class","left_select");
		showMarkSignInfo('bzbxmanager/bzbxlist',1);
	}*/
	showMarkSignInfo('bzbxmanager/bzbxlist',1);
}
/**
 * 获取标志标线类型
 */
function findMarkTypeInfo(){
	$.ajax({
		url:'bzbxmanager/bzbxlxlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				if(list!=""){
					for(var i=0;i<list.length;i++){
						var newLi=$("<li onclick=findMarkInfo('"+list[i].bzbxlxbh+"','"+list[i].bzbxlxmc+"') >"+list[i].bzbxlxmc+"</li>");
						$(".type_down_ul").append(newLi);
						document.getElementById("bzbxlx").options.add(new Option(list[i].bzbxlxmc, list[i].bzbxlxbh));
						document.getElementById("bzbxlx1").options.add(new Option(list[i].bzbxlxmc, list[i].bzbxlxbh));
					}
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function findMarkInfo(bzbxlxbh,bzbxlxmc){
	select_bzbxlxbh=bzbxlxbh;
	$(".type_down_ul").hide();
	$("#bzbxlxmc").text(bzbxlxmc);
	showMarkSignInfo('bzbxmanager/bzbxlist',1);
}
function showMarkSignInfo(actionName,selectedPage){
	searchInput=$("#mark_info").val();
	var selectvalue="";
	if(searchInput=="桩号"){
		searchInput="";
	}
	selectvalue=searchInput;
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'page':selectedPage,
			'rows':8,
			'token':token,
			'lxbh':select_bzbxlxbh,
			'xzqhdm':search_xzqhbm,
			'selectvalue':selectvalue
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".addTr").empty();
				$(".addTr").remove();
				var list=data.result.obj.data;
				if(list==""){
					TableIsNull();
				}else{
					appendToTable(list);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.result.obj.pages, selectedPage, 'showMarkSignInfo',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
	
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		if(i%2==0){
			newTr = $("<tr class='addTr'></tr>");
			//内容图案
			//newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].bmnrta)+"&nbsp;</td>"));
			if(list[i].bzbxpath==""){
				newTr.append($("<td style='background:#f9f9f9'>&nbsp;</td>"));
			}else{
				newTr.append($("<td style='background:#f9f9f9'><img style='height:50px;' src='"+list[i].bzbxpath+"?temp="+ Math.random()+"' />&nbsp;</td>"));
			}
			//标志位置
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].bzwzl)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].bzwzm)+"&nbsp;</td>"));
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].bzwzr)+"&nbsp;</td>"));
			//类型
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].bzbxlxmc)+"&nbsp;</td>"));	
			//安装时间
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].azsj)+"&nbsp;</td>"));	
			//线路名称
			newTr.append($("<td style='background:#f9f9f9'>"+judgeIsNull(list[i].xlmc)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td style='background:#f9f9f9'>" +
					"<a class='Operate' onclick=seeMarkInfoDetail('"+list[i].bzbxbh+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].bzbxbh+"','"+(i+1)+"') >地图</a></td>"));
		}else{
			newTr = $("<tr class='addTr'></tr>");
			//内容图案
			if(list[i].bzbxpath==""){
				newTr.append($("<td>&nbsp;</td>"));
			}else{
				newTr.append($("<td><img style='height:50px;' src='"+list[i].bzbxpath+"?temp="+ Math.random()+"' />&nbsp;</td>"));
			}
			//标志位置
			newTr.append($("<td>"+judgeIsNull(list[i].bzwzl)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].bzwzm)+"&nbsp;</td>"));
			newTr.append($("<td>"+judgeIsNull(list[i].bzwzr)+"&nbsp;</td>"));
			//类型
			newTr.append($("<td>"+judgeIsNull(list[i].bzbxlxmc)+"&nbsp;</td>"));	
			//安装时间
			newTr.append($("<td>"+judgeIsNull(list[i].azsj)+"&nbsp;</td>"));	
			//线路名称
			newTr.append($("<td>"+judgeIsNull(list[i].xlmc)+"&nbsp;</td>"));	
			//操作
			newTr.append($("<td>" +
					"<a class='Operate' onclick=seeMarkInfoDetail('"+list[i].bzbxbh+"')>查看</a>" +
						"<a class='Operate' onclick=showInMap('"+list[i].bzbxbh+"','"+(i+1)+"') >地图</a></td>"));
			
		}
		$(".markTable").append(newTr);
	}
}
function TableIsNull(){
	newTr = $("<tr class='addTr'><td colspan='8' align='center'>暂无相关数据</td></tr>");
	$(".markTable").append(newTr);
	$("#pageDiv").hide();
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
		showMarkSignInfo(actionName, pageNo);
	}
}
function deleteMark(bzbxbh){
	if(confirm("你确定删除该标志标线吗？")){
		$.ajax({
			url:'bzbxmanager/deletebzbx',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'bzbx.bzbxbh':bzbxbh
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}
				var result=data.result.resultcode;
				var text=data.result.resultdesc;
				if(result==1){
					alert("删除成功");
					showMarkSignInfo('bzbxmanager/bzbxlist',select_page);
				}else{
					alert(text);
				}
			}
			
		});
	}
}
/**
 * 根据行政区划查看标志标线
 */
function showThePartMark(xzqhdm,xzqhmc,thisop){
	$(".left_select_li").attr("class","left_no_select_li");
	$(".left_select").attr("class","left_no_select");
	if(xzqhdm==0){
		thisop.className="left_select";
		search_xzqhbm="";
	}else{
		thisop.className="left_select_li";
		search_xzqhbm=xzqhdm;
	}
	$("#area_name").text(xzqhmc+"标志标线信息");
	
	showMarkSignInfo('bzbxmanager/bzbxlist',1);
}
/**
 * 查询标志标线
 */
function searchMarkSignInfo(){
	/*if($("#mark_info").val()=="桩号"){
		return;
	}*/
	search_xzqhbm="";
	$(".left_select_li").attr("class","left_no_select_li");
	$("#left_no_select1").attr("class","left_select");
	$("#area_name").text("嘉兴市标志标线信息");
	showMarkSignInfo('bzbxmanager/bzbxlist',1);
}
/**
 * 进入标志标线详情页面
 * @param bzbm
 */
function seeMarkInfoDetail(bzbxbh){
	$.ajax({
		url:'bzbxmanager/bzbxxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'bzbx.bzbxbh':bzbxbh
		},
		success:function(data){
			
				var list=data.result.obj;
				//var obj_bzwz_position=document.getElementById("bzwz_position1");
				if((list.bzwzl!=null&&list.bzwzl!="")&&(list.bzwzm!=null&&list.bzwzm!="")&&(list.bzwzr!=null&&list.bzwzr!="")){
					$("#position_detail").text("左中右");
					$("#bzwz_detail").text(judgeIsNull(list.bzwzl));
				}else if(list.bzwzl!=null&&list.bzwzl!=""){
					$("#position_detail").text("左");
					$("#bzwz_detail").text(judgeIsNull(list.bzwzl));
				}else if(list.bzwzm!=null&&list.bzwzm!=""){
					$("#position_detail").text("中");
					$("#bzwz_detail").text(judgeIsNull(list.bzwzm));
				}else if(list.bzwzr!=null&&list.bzwzr!=""){
					$("#position_detail").text("右");
					$("#bzwz_detail").text(judgeIsNull(list.bzwzr));
				}
				$("#xlmc_detail").text(judgeIsNull(list.xlmc));
				$("#bmcc_detail").text(judgeIsNull(list.bmcc));
				$("#zcxsjgg_detail").text(judgeIsNull(list.zcxsjgg));
				$("#azsj_detail").text(judgeIsNull(list.azsj));
				$("#zzazdw_detail").text(judgeIsNull(list.zzazdw));
				$("#gldw_detail").text(judgeIsNull(list.gldw));
				$("#bz_detail").text(judgeIsNull(list.bz));
				$("#bmnrsl_detail").text(judgeIsNull(list.bmnrsl));
				$("#gldj_detail").text(judgeIsNull(list.gldj));
				$("#bmnrsl_detail").text(judgeIsNull(list.bmnrsl));
				//if(list.xzqhdm=="330400"){
				$("#xzqh_detail").text(judgeIsNull(list.xzqhmc));
				$("#bmnrtn_detail").attr("src",""+judgeIsNull(list.bzbxpath)+"");
				$("#bzbxlx_detail").text(judgeIsNull(list.bzbxlxmc));
			
		}
	});
	showfullbg();
	$("#MarkDetailDiv").show();
}
function closeMarkDetailDiv(){
	$("#fullbg,#MarkDetailDiv").hide();
}
/**
 * 进入标志标线地图
 * @param bzbm
 */
function showInMap(bzbxbh,num){
	var amount=Number((select_page-1)*10)+Number(num);
	window.open($("#basePath").val()+"page/highway/HighWayMarkSignMap.jsp?bzbxbh="+bzbxbh+"&amount="+amount,"_blank");
}
function showMarkTypeDiv(){
	if($(".type_down_ul").css("display")=="block"){
		$(".type_down_ul").hide();
	}else{
		$(".type_down_ul").show();
	}
	$(document).click(function(event){
		if($(event.target).attr("class") != "type_select_mark"&&$(event.target).attr("id")!="bzbxlxmc"
			&&$(event.target).attr("id")!="select_image" ){
			$(".type_down_ul").hide();
		}
	});
	
}
function showaddMarkDiv(){
	//$("#xlmc,#gldj,#bzwz,#gldw,#bmnrsl,#bmcc,#zcxsjgg,#azsj,#zzazdw,#bz,#pictureName").val("");
	$("input").val("");
	var obj_xzqh=document.getElementById("xzqh");
	obj_xzqh.options[0].defaultSelected=true;
	obj_xzqh.options[0].selected=true;
	var obj_bzbxlx=document.getElementById("bzbxlx");
	obj_bzbxlx.options[0].defaultSelected=true;
	obj_bzbxlx.options[0].selected=true;
	var obj_bzwz_position=document.getElementById("bzwz_position");
	obj_bzwz_position.options[0].defaultSelected=true;
	obj_bzwz_position.options[0].selected=true;
	showfullbg();
	$("#addMarkDiv").show();
}
function closeaddMarkDiv(){
	$("#fullbg,#addMarkDiv").hide();
}
function openfile(){
	try{   
        var obj=new ActiveXObject("wscript.shell");   
        if(obj){   
            obj.Run("\""+filename+"\"", 1, false );  
            //obj.run("osk");/*打开屏幕键盘*/  
            //obj.Run('"'+filename+'"');   
            obj=null;   
        }   
    }catch(e){   
        alert("请确定是否存在该盘符或文件");   
    }     
}
function addMarkSign(){
	if(!checkinfo()){
		return;
	}
	if(!checkimg()){
		return;
	}
	var bzwzl="";
	var bzwzm="";
	var bzwzr="";
	if($("#bzwz_position option:selected").val()==1){
		bzwzl=$("#bzwz").val();
	}else if($("#bzwz_position option:selected").val()==2){
		bzwzm=$("#bzwz").val();
	}else if($("#bzwz_position option:selected").val()==3){
		bzwzr=$("#bzwz").val();
	}else if($("#bzwz_position option:selected").val()==4){
		bzwzl=bzwzm=bzwzr=$("#bzwz").val();
	}
	$.ajaxFileUpload({
		url:'bzbxmanager/savebzbx',
		secureuri:false, //是否需要安全协议，一般设置为false 
		fileElementId:'pictureName', //文件上传域的ID 
		type:'post',
		dataType:'text',
		data:{
			'token':token,
			'bzbx.xzqhdm':$("#xzqh option:selected").val(),
			'bzbx.xlmc':$("#xlmc").val(),
			'bzbx.bzbxlxbh':$("#bzbxlx option:selected").val(),
			'bzbx.gldj':$("#gldj").val(),
			'bzbx.bzwzl':bzwzl,
			'bzbx.bzwzm':bzwzm,
			'bzbx.bzwzr':bzwzr,
			'bzbx.bmnrsl':$("#bmnrsl").val(),
			'bzbx.bmcc':$("#bmcc").val(),
			'bzbx.zcxsjgg':$("#zcxsjgg").val(),
			'bzbx.azsj':$("#azsj").val(),
			'bzbx.zzazdw':$("#zzazdw").val(),
			'bzbx.gldw':$("#gldw").val(),
			'bzbx.bz':$("#bz").val()
		},
		success:function(data){
			if(data.resultcode==-1){
				BackToLoginPage();
			}
			 var result = eval('('+data+')');  //转为json对象   
        	 //var code=result.resultcode;
 			 var message = result.resultdesc;
 			 alert(message);
 			 window.location.reload();
		},
		error:function(XMLHttpRequest){
			
		}
	});
}
function checkimg(){
	var filesName = "";
	var fileType; //图片类型
	var check = true; //一个条件变量
	/*$("input[name='files']").each(function(index){
		if($(this).val()!=""){
		  filesName.push($(this).val());
		}
	});*/
	filesName=$("#pictureName").val();
	/*for(var i=0;i<filesName.length;i++){
		//alert(filesName[i]);
		  fileType = filesName[i].substring(filesName[i].lastIndexOf('.') + 1);
		  if (check && fileType != 'jpg' && fileType != 'png' && fileType != 'gif') {
		      $.messager.alert("错误信息",'图片格式不正确');
		      check = false;
		      break;
		  }
	}*/
	fileType = filesName.substring(filesName.lastIndexOf('.') + 1);
	if (check  && fileType != 'png'&&filesName!="") {
	      alert('图片格式不正确,只能使用png格式的图片');
	      check = false;
	}
	return check;
}
function checkEditimg(){
	var filesName = "";
	var fileType; //图片类型
	var check = true; //一个条件变量
	filesName=$("#pictureName1").val();
	fileType = filesName.substring(filesName.lastIndexOf('.') + 1);
	if (check && fileType != 'png' && filesName!="") {
	      alert('图片格式不正确');
	      check = false;
	}
	return check;
}
function checkinfo(){
	if($("#xlmc").val()==""){
		alert("请输入线路名称");
		return false;
	}
	if($("#xlmc").val().length>10){
		alert("线路名称长度不能大于10位");
		return false;
	}
	if($("#gldj").val()==""){
		alert("请输入公路等级");
		return false;
	}
	if($("#gldj").val().length>100){
		alert("公路等级长度不能大于100位");
		return false;
	}
	if($("#bzwz").val()==""){
		alert("请输入标志位置");
		return false;
	}
	if($("#bzwz").val().length>100){
		alert("标志位置长度不能大于100位");
		return false;
	}
	if($("#gldw").val().length>250){
		alert("标志位置长度不能大于250位");
		return false;
	}
	if($("#bmnrsl").val().length>100){
		alert("版面内容数量长度不能大于100位");
		return false;
	}
	if($("#bmcc").val().length>100){
		alert("版面尺寸长度不能大于100位");
		return false;
	}
	if($("#zcxsjgg").val().length>100){
		alert("支撑形式及规格长度不能大于100位");
		return false;
	}
	if($("#zzazdw").val().length>250){
		alert("制作安装单位长度不能大于250位");
		return false;
	}
	if($("#bz").val().length>2000){
		alert("支撑形式及规格长度不能大于2000位");
		return false;
	}
	return true;
}
//编辑
var editMarkbh="";
function showeditMarkInfoDiv(bzbxbh){
	editMarkbh=bzbxbh;
	$.ajax({
		url:'bzbxmanager/bzbxxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'bzbx.bzbxbh':bzbxbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				var obj_bzwz_position=document.getElementById("bzwz_position1");
				if((list.bzwzl!=null&&list.bzwzl!="")&&(list.bzwzm!=null&&list.bzwzm!="")&&(list.bzwzr!=null&&list.bzwzr!="")){
					obj_bzwz_position.options[3].defaultSelected=true;
					obj_bzwz_position.options[3].selected=true;
					$("#bzwz1").val(judgeIsNull(list.bzwzl));
				}else if(list.bzwzl!=null&&list.bzwzl!=""){
					obj_bzwz_position.options[0].defaultSelected=true;
					obj_bzwz_position.options[0].selected=true;
					$("#bzwz1").val(judgeIsNull(list.bzwzl));
				}else if(list.bzwzm!=null&&list.bzwzm!=""){
					obj_bzwz_position.options[1].defaultSelected=true;
					obj_bzwz_position.options[1].selected=true;
					$("#bzwz1").val(judgeIsNull(list.bzwzm));
				}else if(list.bzwzr!=null&&list.bzwzr!=""){
					obj_bzwz_position.options[2].defaultSelected=true;
					obj_bzwz_position.options[2].selected=true;
					$("#bzwz1").val(judgeIsNull(list.bzwzr));
				}
				$("#xlmc1").val(judgeIsNull(list.xlmc));
				$("#bmcc1").val(judgeIsNull(list.bmcc));
				$("#zcxsjgg1").val(judgeIsNull(list.zcxsjgg));
				$("#azsj1").val(judgeIsNull(list.azsj));
				$("#zzazdw1").val(judgeIsNull(list.zzazdw));
				$("#gldw1").val(judgeIsNull(list.gldw));
				$("#bz1").val(judgeIsNull(list.bz));
				$("#bmnrsl1").val(judgeIsNull(list.bmnrsl));
				$("#gldj1").val(judgeIsNull(list.gldj));
				$("#bmnrsl1").val(judgeIsNull(list.bmnrsl));
				var obj_xzqh=document.getElementById("xzqh1");
				if(obj_xzqh){
					var options=obj_xzqh.options;
					if(options){
						for(var i=0;i<options.length;i++){
							if(options[i].value==list.xzqhdm){
								options[i].defaultSelected=true;
								options[i].selected=true;
							}
						}
					}
				}
				var obj_bzbx=document.getElementById("bzbxlx1");
				if(obj_bzbx){
					var options=obj_bzbx.options;
					if(options){
						for(var i=0;i<options.length;i++){
							if(options[i].value==list.bzbxlxbh){
								options[i].defaultSelected=true;
								options[i].selected=true;
							}
						}
					}
				}
				$("#pictureName1").val("");
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
	showfullbg();
	$("#editMarkDiv").show();
}
function editMarkSign(){
	if(!checkEditInfo()){
		return;
	}
	if(!checkEditimg()){
		return;
	}
	var bzwzl="";
	var bzwzm="";
	var bzwzr="";
	if($("#bzwz_position1 option:selected").val()==1){
		bzwzl=$("#bzwz1").val();
	}else if($("#bzwz_position1 option:selected").val()==2){
		bzwzm=$("#bzwz1").val();
	}else if($("#bzwz_position1 option:selected").val()==3){
		bzwzr=$("#bzwz1").val();
	}else if($("#bzwz_position1 option:selected").val()==4){
		bzwzl=bzwzm=bzwzr=$("#bzwz1").val();
	}
	$.ajaxFileUpload({
		url:'bzbxmanager/updatebzbx',
		secureuri:false, //是否需要安全协议，一般设置为false 
		fileElementId:'pictureName1', //文件上传域的ID 
		type:'post',
		dataType:'text',
		data:{
			'token':token,
			'bzbx.bzbxbh':editMarkbh,
			'bzbx.xzqhdm':$("#xzqh1 option:selected").val(),
			'bzbx.xlmc':$("#xlmc1").val(),
			'bzbx.bzbxlxbh':$("#bzbxlx1 option:selected").val(),
			'bzbx.gldj':$("#gldj1").val(),
			'bzbx.bzwzl':bzwzl,
			'bzbx.bzwzm':bzwzm,
			'bzbx.bzwzr':bzwzr,
			'bzbx.bmnrsl':$("#bmnrsl1").val(),
			'bzbx.bmcc':$("#bmcc1").val(),
			'bzbx.zcxsjgg':$("#zcxsjgg1").val(),
			'bzbx.azsj':$("#azsj1").val(),
			'bzbx.zzazdw':$("#zzazdw1").val(),
			'bzbx.gldw':$("#gldw1").val(),
			'bzbx.bz':$("#bz1").val()
		},
		success:function(data){
			if(data.resultcode==-1){
				BackToLoginPage();
			}
			 var result = eval('('+data+')');  //转为json对象   
        	 //var code=result.resultcode;
 			 var message = result.resultdesc;
 			 alert(message);
 			 //window.location.reload();
 			closeeditMarkDiv();
 			showMarkSignInfo('bzbxmanager/bzbxlist',select_page);
		},
		error:function(XMLHttpRequest){
			
		}
	});
}
function checkEditInfo(){
	if($("#xlmc1").val()==""){
		alert("请输入线路名称");
		return false;
	}
	if($("#xlmc1").val().length>10){
		alert("线路名称长度不能大于10位");
		return false;
	}
	if($("#gldj1").val()==""){
		alert("请输入公路等级");
		return false;
	}
	if($("#gldj1").val().length>100){
		alert("公路等级长度不能大于100位");
		return false;
	}
	if($("#bzwz1").val()==""){
		alert("请输入标志位置");
		return false;
	}
	if($("#bzwz1").val().length>100){
		alert("标志位置长度不能大于100位");
		return false;
	}
	if($("#gldw1").val().length>250){
		alert("标志位置长度不能大于250位");
		return false;
	}
	if($("#bmnrsl1").val().length>100){
		alert("版面内容数量长度不能大于100位");
		return false;
	}
	if($("#bmcc").val().length>100){
		alert("版面尺寸长度不能大于100位");
		return false;
	}
	if($("#zcxsjgg1").val().length>100){
		alert("支撑形式及规格长度不能大于100位");
		return false;
	}
	if($("#zzazdw1").val().length>250){
		alert("制作安装单位长度不能大于250位");
		return false;
	}
	if($("#bz1").val().length>2000){
		alert("支撑形式及规格长度不能大于2000位");
		return false;
	}
	return true;
}
function closeeditMarkDiv(){
	$("#fullbg,#editMarkDiv").hide();
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}
function addbuttonover(){
	$("#add_button_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#add_button_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#add_button_right").css({"background":"url('image/main/right_pressed.png')"});
}
function addbuttonout(){
	$("#add_button_left").css({"background":"url('image/main/left_normal.png')"});
	$("#add_button_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#add_button_right").css({"background":"url('image/main/right_normal.png')"});
}
/*function checkLeave(){
	showMarkSignInfo('bzbxmanager/bzbxlist',select_page);
}*/
