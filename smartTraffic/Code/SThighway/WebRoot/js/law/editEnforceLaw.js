var searchInput="";
var search_xzqhbm="";//行政区划编码
var bmnum=0;//编码编号
var searchList="";
var addOredit=1;//1是新增
$(document).ready(function(){
	$("#top_text").text("行政执法");//top上的显示
	findgljg();
	$("input[name='cfselect']").click(function(){
		if($(this).val()==2){
			$(".legalmanTr").show();
		}else{
			$(".legalmanTr").hide();
			$("#fddbrxm,#legalmanCard").val("");
		}
	});
});
function findgljg(){
	$.ajax({
		url:'gljglist/gljgjbxxlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'sfzxcfjg':1
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					document.getElementById("xzcfjg").options.add(new Option(list[i].orgname,list[i].gljgdm));
				}
				if($("#lawbh").val()!=""&&$("#lawbh").val()!="null"){
					$("#title_name").text("编辑行政处罚信息");
					showcredentials();
					showpower();
					addOredit=2;//2是编辑
				}else{
					showcredentials();
					showpower();
					$("#xzcfjdwh").show();
					$("#title_name").text("新增行政处罚信息");
				}
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function showcredentials(){
	$.ajax({
		url:'xzcf/selectoptionlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'type':2
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					document.getElementById("credentials").options.add(new Option(list[i].name,list[i].idObj));
				}
			}
		}
	});
}
function showdownSelectDiv(){
	if($(".type_down_ul").css("display")=="block"){
		$(".type_down_ul").hide();
	}else{
		$(".type_down_ul").show();
	}
	$(document).click(function(event){
		if($(event.target).attr("class") != "type_select_mark"&&$(event.target).attr("id")!="showtext"
			&&$(event.target).attr("id")!="select_image" ){
			$(".type_down_ul").hide();
		}
	});
}
function showpower(){
	$.ajax({
		url:'xzcf/selectoptionlist',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'type':1
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					//document.getElementById("power").options.add(new Option(list[i].name,list[i].idObj));
					var newLi=$("<li class='addLi' onclick=selectThisPower('"+list[i].idObj+"',this) name='"+list[i].idObj+"'>"+list[i].name+"</li>")
					$(".type_down_ul").append(newLi);
				}
				//$('#power_div').text($("#power option:selected").text());
				showEnforceLawInfo($("#lawbh").val());
			}
		}
	});
}
var selectpowerid="";
function selectThisPower(id,thisop){
	selectpowerid=id;
	$(".type_down_ul").hide();
	var powername=thisop.textContent||thisop.innerText;
	$("#showtext").text(powername);
	$("#power_div").text(powername);
}
function showEnforceLawInfo(xzcfbh){
	$.ajax({
		url:'xzcf/xzcfxq',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'xzcfbh':xzcfbh
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj;
				$("#xzcfjg").val(list.xzcfjgdm);
				$("#xzcfjdwh_edit").text(judgeIsNull(list.xzcfjdwh));
				$("#ajmc").val(judgeIsNull(list.ajmc));
				$("#cfrxm").val(judgeIsNull(list.cfrxm));
				$("#wfqyzjjgdm").val(judgeIsNull(list.wfqyzjjgdm));
				$("#zywfss").val(judgeIsNull(list.zywfss));
				$("#xzcf").val(judgeIsNull(list.xzcf));
				$("#xzcfzl").val(judgeIsNull(list.xzcfzl));
				$("#xzcfrq").val(judgeIsNull(list.xzcfrq));
				$("#bz").val(judgeIsNull(list.bz));
				$("input[value='"+list.xzcftype+"']").attr("checked","checked");
				$("#credentials").val(list.xzcfcardtype);
				$("#credentialsnumber").val(list.xzcfcardnumber);
				if(list.xzcftype==2){
					$(".legalmanTr").show();
					$("#fddbrxm").val(judgeIsNull(list.fddbrxm));
					$("#legalmanCard").val(judgeIsNull(list.legalmanIdcard));
				}
				$("#showtext,#power_div").text(judgeIsNull(list.xzcfname));
				selectpowerid=$(".addLi:contains('"+judgeIsNull(list.xzcfname)+"')").attr("name");
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function saveInfo(){
	if(!checkInfo()){
		return;
	}
	if(addOredit==1){
		if($.trim($("#xzcfjdwh").val())==""){
			alert("请输入行政处罚文书号！");
			$("#xzcfjdwh").val("");
			$("#xzcfjdwh").focus();
			return;
		}
		$.ajax({
			url:'xzcf/savexzcf',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'punishment.xzcfjdwh':$("#xzcfjdwh").val(),
				'punishment.ajmc':$("#ajmc").val(),
				'punishment.cfrxm':$("#cfrxm").val(),
				'punishment.wfqyzjjgdm':$("#wfqyzjjgdm").val(),
				'punishment.fddbrxm':$("#fddbrxm").val(),
				'punishment.zywfss':$("#zywfss").val(),
				'punishment.xzcf':$("#xzcf").val(),
				'punishment.xzcfzl':$("#xzcfzl").val(),
				'punishment.xzcfrq':$("#xzcfrq").val(),
				'punishment.bz':$("#bz").val(),
				'punishment.xzcfjgdm':$("#xzcfjg option:selected").val(),
				'punishment.xzcftype':$("input[name=cfselect]:checked").val(),
				'punishment.xzcfcardtype':$("#credentials option:selected").val(),
				'punishment.xzcfcardnumber':$("#credentialsnumber").val(),
				'punishment.legalmanIdcard':$("#legalmanCard").val(),
				'punishment.xzcfitemid':selectpowerid
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}else if(data.result.resultcode==1){
					alert("新增成功");
					window.history.go("-1");
				}else{
					alert(data.result.resultdesc);
				}
			}
		});
	}else{
		$.ajax({
			url:'xzcf/updatexzcf',
			type:'post',
			dataType:'json',
			data:{
				'token':token,
				'punishment.xzcfbh':$("#lawbh").val(),
				'punishment.xzcfjdwh':$("#xzcfjdwh_edit").text(),
				'punishment.ajmc':$("#ajmc").val(),
				'punishment.cfrxm':$("#cfrxm").val(),
				'punishment.wfqyzjjgdm':$("#wfqyzjjgdm").val(),
				'punishment.fddbrxm':$("#fddbrxm").val(),
				'punishment.zywfss':$("#zywfss").val(),
				'punishment.xzcf':$("#xzcf").val(),
				'punishment.xzcfzl':$("#xzcfzl").val(),
				'punishment.xzcfrq':$("#xzcfrq").val(),
				'punishment.bz':$("#bz").val(),
				'punishment.xzcfjgdm':$("#xzcfjg option:selected").val(),
				'punishment.xzcftype':$("input[name=cfselect]:checked").val(),
				'punishment.xzcfcardtype':$("#credentials option:selected").val(),
				'punishment.xzcfcardnumber':$("#credentialsnumber").val(),
				'punishment.legalmanIdcard':$("#legalmanCard").val(),
				'punishment.xzcfitemid':selectpowerid
			},
			success:function(data){
				if(data.result.resultcode==-1){
					BackToLoginPage();
				}else if(data.result.resultcode==1){
					alert("编辑成功");
					//window.location.reload();
					window.history.go("-1");
				}else{
					alert(data.result.resultdesc);
				}
			}
		});
	}
	
}
function checkInfo(){
	if($("#ajmc").val()==""){
		alert("案件名称不能为空");
		$("#ajmc").focus();
		return false;
	}
	if($("#wfqyzjjgdm")==""){
		alert("违法企业组织机构代码不能为空");
		$("#wfqyzjjgdm").focus();
		return false;
	}
	if($("#cfrxm").val()==""){
		alert("被处罚对象姓名或者名称不能位空");
		$("#cfrxm").focus();
		return false;
	}
	if($(".legalmanTr").css("display")!="none"){
		if($("#fddbrxm").val()==""){
			alert("被处罚单位法人姓名不能位空");
			$("#fddbrxm").focus();
			return false;
		}
		if($("#legalmanCard").val()==""){
			alert("法人身份证号码不能位空");
			$("#legalmanCard").focus();
			return false;
		}
	}
	if($("#credentialsnumber").val()==""){
		alert("被处罚对象证件号码不能位空");
		$("#credentialsnumber").focus();
		return false;
	}
	if($("#showtext").text=="请选择权力事项"){
		alert("请选择权力事项");
		return false;
	}
	if($("#zywfss").val()==""){
		alert("主要违法事实不能为空");
		$("#zywfss").focus();
		return false;
	}
	if($("#xzcf").val()==""){
		alert("行政处罚的履行方式和期限不能为空");
		$("#xzcf").focus();
		return false;
	}
	if($("#xzcfzl").val()==""){
		alert("行政处罚的种类和依据不能为空");
		$("#xzcfzl").focus();
		return false;
	}
	if($("#xzcfrq").val()==""){
		alert("行政处罚日期不能为空");
		$("#xzcfrq").focus();
		return false;
	}
	if($("#xzcfjdwh").val().length>110){
		alert("行政处罚决定书文号不能大于110位");
		$("#xzcfjdwh").focus();
		return false;
	}
	if($("#ajmc").val().length>110){
		alert("案件名称不能大于110位");
		$("#ajmc").focus();
		return false;
	}
	if($("#cfrxm").val().length>110){
		alert("被处罚对象姓名或者名称不能大于110位");
		$("#cfrxm").focus();
		return false;
	}
	if($("#wfqyzjjgdm").val().length>110){
		alert("违法企业组织机构代码不能大于110位");
		$("#wfqyzjjgdm").focus();
		return false;
	}
	if($("#fddbrxm").val().length>110){
		alert("法定代表人名称不能大于100位！");
		$("#fddbrxm").focus();
		return false;
	}
	if($("#xzcfzl").val().length>110){
		alert("行政处罚的种类和依据不能大于110位！");
		$("#xzcfzl").focus();
		return false;
	}
	if($("#xzcfrq").val().length>110){
		alert("做出行政处罚机关名和日期不能大于110位！");
		$("#xzcfrq").focus();
		return false;
	}
	if($("#bz").val().length>110){
		alert("备注长度不能大于110位！");
		$("#bz").focus();
		return false;
	}
	return true;
}
/**
 * 进入路段地图
 * @param bzbm
 */
function showInMap(bzbm,num){
	var amount=Number((select_page-1)*10)+Number(num);
	window.open($("#basePath").val()+"page/highway/HighWayRouteMap.jsp?routebm="+bzbm+"&amount="+amount,"_blank");
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}