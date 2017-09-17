var actionname='company/findCompany';
$$(document).ready(function() {
	$$(".selectCompanyInput").focus(function(){
		if($$(".selectCompanyInput").val()=='请输入企业名称'){
			$$(".selectCompanyInput").val('');
			$$(".selectCompanyInput").css('color','#333');
		}
	});
	$$(".selectCompanyInput").blur(function(){
		if($$(".selectCompanyInput").val()==''){
			$$(".selectCompanyInput").val('请输入企业名称');
			$$(".selectCompanyInput").css('color','#ccc');
		}
	});
	$$(".selectCompanyButton").click(function(){
		showTzInfoList(actionname,1);
	});
	$$(".closeword").click(function(){
		$$(".fugaidiv").hide();
		$$(".companyinfodiv").hide();
	});
});
function showTzInfoList(actionname,page){
	var selectword='';
	if($$(".selectCompanyInput").val()!='请输入企业名称' && $$(".selectCompanyInput").val()!='' && $$(".selectCompanyInput").val()!=null){
		var word=$$(".selectCompanyInput").val();
		selectword=$$.trim(word);
	}else{
		alert('请输入企业名称');
		return;
	}
	$$.ajax({
		url:actionname,
		type:'post',
		dataType:'json',
		data:{
			'page':page,
			'selectword':selectword
		},
		success:function(data){
			if(data.companylist.length==0){
				alert("未找到包含'"+selectword+"'的企业名称");
				return;
			};
			$$(".companyTable").html('');
			$$(".companyTable").append(
					"<tr style='background-color: #eeedfc;'>"+
					"<th style='width:25%;'>企业名称</th>"+
					"<th style='width:35%;'>企业地址</th>"+
					"<th style='width:12%;'>法定代表人</th>"+
					"<th style='width:13%;'>经营许可证号</th>"+
					"<th style='width:15%;'>企业类型</th>"+
				"</tr>"
			);
			printPage(data.total, page, 'showTzInfoList', actionname,
					'gotoTzPageNo');
			$$(data.companylist).each(function(index,item){
				var companyType='';
				if(item.companyType==1){
					companyType='出租车经营企业';
				}
				if(item.companyType==2){
					companyType='公交经营企业';
				}
				if(item.companyType==3){
					companyType='货运经营企业';
				}
				if(item.companyType==4){
					companyType='驾培经营企业';
				}
				if(item.companyType==5){
					companyType='经营企业';
				}
				if(item.companyType==6){
					companyType='客运经营企业';
				}
				if(item.companyType==7){
					companyType='维修经营企业';
				}
				if(item.companyType==8){
					companyType='道路危险品经营企业';
				}
				$$(".companyTable").append(
					"<tr style='border-top:0;cursor:pointer;' id='"+item.id+"' onclick='onecompanyinfo(this.id)'>"+
						"<td style='width:25%;'>"+item.companyName+"</td>"+
						"<td style='width:35%;'>"+item.companyAddress+"</td>"+
						"<td style='width:12%;'>"+item.companyPerson+"</td>"+
						"<td style='width:13%;'>"+item.companyNo+"</td>"+
						"<td style='width:15%;'>"+companyType+"</td>"+
						"<input type='hidden' value='"+item.companyType+"' id='ct"+item.id+"' />"+
					"</tr>"
				);
			});
		}
	});
}
function onecompanyinfo(id){
	$$(".fugaidiv").show();
	$$(".companyinfodiv").show();
	var companytype=$$("#ct"+id).val();
	$$.ajax({
		url:'company/findOneCompany',
		type:'post',
		dataType:'json',
		data:{
			'id':id,
			'companytype':companytype
		},
		success:function(data){
			$$(".infotable").html('');
			$$(data.oneCompany).each(function(index,item){
				var dz='';
				var fw='';
				var fddbr='';
				var xzqhmc='';
				var lxdh='';
				var jyxkzh='';
				var hfrq='';
				var jjlxmc='';
				var jyxkzyxqq='';
				var zcrq='';
				var jyxkzyxqz='';
				if(item.JYFW!=null&&item.JYFW!=''){
					fw=item.JYFW;
				}
				if(item.FDDBR!=null&&item.FDDBR!=''){
					fddbr=item.FDDBR;
				}
				if(item.XZQHMC!=null&&item.XZQHMC!=''){
					xzqhmc=item.XZQHMC;
				}
				if(item.LXDH!=null&&item.LXDH!=''){
					lxdh=item.LXDH;
				}
				if(item.JYXKZH!=null&&item.JYXKZH!=''){
					jyxkzh=item.JYXKZH;
				}
				if(item.HFRQ!=null&&item.HFRQ!=''){
					hfrq=item.HFRQ.substr(0,10);
				}
				if(item.JJLXMC!=null&&item.JJLXMC!=''){
					jjlxmc=item.JJLXMC;
				}
				if(item.JYXKZYXQQ!=null&&item.JYXKZYXQQ!=''){
					jyxkzyxqq=item.JYXKZYXQQ.substr(0,10);
				}
				if(item.ZCRQ!=null&&item.ZCRQ!=''){
					zcrq=item.ZCRQ.substr(0,10);
				}
				if(item.JYXKZYXQZ!=null&&item.JYXKZYXQZ!=''){
					jyxkzyxqz=item.JYXKZYXQZ.substr(0,10);
				}
				if(item.YHDZ==null||item.YHDZ==''){
					dz=xzqhmc;
				}else{
					dz=item.YHDZ;
				}
				$$("#info_name").text(item.YHMC);
				$$("#info_address").text(dz);
				$$(".infotable").append(
					"<tr>"+
	  					"<td class='infolefttd'>法定代表人：</td>"+
	  					"<td class='inforighttd'>"+fddbr+"</td>"+
	  					"<td class='infolefttd'>行政区划：</td>"+
	  					"<td class='inforighttd'>"+xzqhmc+"</td>"+
	  				"</tr>"+  			
	  				"<tr>"+
	  				"<td class='infolefttd'>联系电话：</td>"+
	  				"<td class='inforighttd'>"+lxdh+"</td>"+
	  					"<td class='infolefttd'>经营许可证号：</td>"+
	  					"<td class='inforighttd'>"+jyxkzh+"</td>"+
	  				"</tr>"+ 			
	  				"<tr>"+
	  					"<td class='infolefttd'>核发日期：</td>"+
	  					"<td class='inforighttd'>"+hfrq+"</td>"+
	  					"<td class='infolefttd'>经济类型：</td>"+
	  					"<td class='inforighttd'>"+jjlxmc+"</td>"+
	  				"</tr>"+  			
	  				"<tr>"+
	  					"<td class='infolefttd'>经营许可证有效期起：</td>"+
	  					"<td class='inforighttd'>"+jyxkzyxqq+"</td>"+
	  					"<td class='infolefttd'>注册日期：</td>"+
	  					"<td class='inforighttd'>"+zcrq+"</td>"+
	  				"</tr>"+	
	  				"<tr>"+
	  					"<td class='infolefttd'>经营许可证有效期止：</td>"+
	  					"<td class='inforighttd'>"+jyxkzyxqz+"</td>"+
	  					"<td class='inforighttd'></td>"+
	  					"<td class='inforighttd'></td>"+
	  				"</tr>"+
	  				"<tr>"+
	  					"<td class='infolefttd'>经营范围：</td>"+
	  					"<td class='inforighttd' colspan='3'>"+fw+"</td>"+
	  				"</tr>"
				);
			});
		}
	});
}
function gotoTzPageNo(actionName, totalPage) {
	var pageNo = $$(".indexCommon").val();
	var str = $$.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$$(".indexCommon").val("");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showTzInfoList(actionName, pageNo);
	}
}