var searchtime=[];
searchtime[0]="xjnd";
var searchselect=[];
searchselect[0]="jsdj";
searchselect[1]="mclx";
searchselect[2]="cdfl";
searchselect[3]="sfdtlld";
searchselect[4]="sfsfld";
searchselect[5]="sfyfgs";
/*桥梁*/
searchselect[6]="jszkpd";
searchselect[7]="qdlx";
searchselect[8]="qdfzsslx";
searchselect[9]="sjhzdj";
searchselect[10]="thdj";
searchselect[11]="gldwmc";
searchselect[16]="kzdj";
searchselect[17]="xzdj";
/*标志标线*/
searchselect[12]="gldw";
searchselect[13]="bmcc";
searchselect[14]="zcxsjgg";
searchselect[15]="bzbxlxbh";

var Type="";
var selectValue="";
function getType(type){
	Type=type;
}
var selectleftmark=1;
//高级搜索
function showSearchDiv(type){
	Type=type;
	showfullbg();
	if(selectleftmark==1){
		showSearchInfo();
		selectleftmark=2;
	}
	$("#SearchDiv").show();
}
function closeSearchDiv(){
	/*$("#search1,#search2,#search3,#search_select1,#search_select2,#search_select3").empty();
	$("#search_input1,#search_input2,#search_input3").val("");
	$("#search_input1,#search_input2,#search_input3").show();
	$("#search_year1,#search_year2,#search_year3").val("");
	$("#search_year1,#search_year2,#search_year3").hide();
	$("#search1,#search2,#search3").append("<option value='null'>请选择搜索条件</option>");
	$("#search_select1,#search_select2,#search_select3").hide();
	$("#search_tr2,#search_tr3").hide();
	$("#search_checkbox1,#search_checkbox2,#search_checkbox3").attr("checked",false);*/
	$("#fullbg,#SearchDiv").hide();
}
function showSearchInfo(){
	$.ajax({
		url:'advancedsearch/advancedsearch',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'type':Type
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.map;
				searchList=list;
				for(var key in list){
					 if (list.hasOwnProperty(key)) {
						  // or if (Object.prototype.hasOwnProperty.call(obj,prop)) for safety...  
						   // alert("key: " + key + " value: " + list[key])  
						    document.getElementById("search1").options.add(new Option(list[key], key));
						    document.getElementById("search2").options.add(new Option(list[key], key));
						    document.getElementById("search3").options.add(new Option(list[key], key));
						  }  
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function selectSearch(num){
	/*$("#SearchDiv").css({"width":"800px"});
	if($("#search1 option:selected").val()=="")*/
	$("#search_tr"+(num+1)).show();
	if(num==1){
		var text=$("#search1 option:selected").text();
		var value=$("#search1 option:selected").val();
		/*$("#search1").empty();
		document.getElementById("search1").options.add(new Option(text, value));*/
		for(var i=0;i<searchselect.length;i++){
			if(searchselect[i]==value){
				$("#search_input"+num).css({"display":"none"});
				$("#search_year"+num).css({"display":"none"});
				insertInSelect(num,value);
				return;
			}
		}
		if(value=="xjnd"){
			$("#search_select"+num).css({"display":"none"});
			$("#search_input"+num).css({"display":"none"});
			$("#search_year"+num).css({"display":"block"});
			return;
		}else{
			$("#search_select"+num).css({"display":"none"});
			$("#search_input"+num).css({"display":"block"});
			$("#search_year"+num).css({"display":"none"});
		}
		/*if(value=="jsdj"){
			$("#search1_input").css({"display":"none"});
			var html="<option>一级</option><option>二级</option><option>准四级</option><option>四级</option><option>等外</option><option>高速</option>";
			$("#search_select1").append(html);
			$("#search_select1").css({"display":"block"});
		}*/
		/*for(var key in searchList){
			if (searchList.hasOwnProperty(key)) {
				if($("#search1 option:selected").val()==key){
					continue;
				}else{
					document.getElementById("search2").options.add(new Option(searchList[key], key));
				}
			}  
		}*/
	}else if(num==2){
		var text=$("#search2 option:selected").text();
		var value=$("#search2 option:selected").val();
		/*$("#search2").empty();
		document.getElementById("search2").options.add(new Option(text, value));*/
		for(var i=0;i<searchselect.length;i++){
			if(searchselect[i]==value){
				$("#search_year"+num).css({"display":"none"});
				$("#search_input"+num).css({"display":"none"});
				insertInSelect(num,value);
				return;
			}
		}
		if(value=="xjnd"){
			$("#search_select"+num).css({"display":"none"});
			$("#search_input"+num).css({"display":"none"});
			$("#search_year"+num).css({"display":"block"});
			return;
		}else{
			$("#search_select"+num).css({"display":"none"});
			$("#search_input"+num).css({"display":"block"});
			$("#search_year"+num).css({"display":"none"});
		}
		/*for(var key in searchList){
			if (searchList.hasOwnProperty(key)) {
				if($("#search1 option:selected").val()==key||$("#search2 option:selected").val()==key){
					continue;
				}else{
					document.getElementById("search3").options.add(new Option(searchList[key], key));
				}
				 
			}  
		}*/
	}else{
		var text=$("#search3 option:selected").text();
		var value=$("#search3 option:selected").val();
		/*$("#search3").empty();
		document.getElementById("search3").options.add(new Option(text, value));*/
		for(var i=0;i<searchselect.length;i++){
			if(searchselect[i]==value){
				$("#search_year"+num).css({"display":"none"});
				$("#search_input"+num).css({"display":"none"});
				insertInSelect(num,value);
				return;
			}
		}
		if(value=="xjnd"){
			$("#search_select"+num).css({"display":"none"});
			$("#search_input"+num).css({"display":"none"});
			$("#search_year"+num).css({"display":"block"});
			return;
		}else{
			$("#search_select"+num).css({"display":"none"});
			$("#search_input"+num).css({"display":"block"});
			$("#search_year"+num).css({"display":"none"});
		}
	}
}
function insertInSelect(num,value){
	$.ajax({
		url:'advancedsearch/searchselect',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'keyname':value,
			'type':Type
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$("#search_select"+num).empty();
				var list=data.result.list;
				for(var i=0;i<list.length;i++){
					document.getElementById("search_select"+num).options.add(new Option(list[i].name,list[i].idObj));
				}
				$("#search_select"+num).css({"display":"block"});
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function searchListInfo(){//alert($('#search1_checkbox').is(':checked'))
	var search1,search2,search3,mark1,mark2,mark3;
	if($("#search_select1").css("display")=="block"){
		if($("#search1 option:selected").val()=="bzbxlxbh"){
			search1=$("#search_select1 option:selected").val();
		}else if($("#search1 option:selected").val()=="xzdj"){
			search1=$("#search_select1 option:selected").val();
		}else{
			search1=$("#search_select1 option:selected").text();
		}
	}else if($("#search_year1").css("display")=="block"){
		search1=$("#search_year1").val();
	}else{
		search1=$("#search_input1").val();
	}
	if(document.getElementById("search_checkbox1").checked==true){
		mark1=1;
	}else{
		mark1=0;
	}
	selectValue=$("#search1 option:selected").val()+","+search1+","+mark1;
	if($("#search_select2").css("display")=="block"){
		if($("#search2 option:selected").val()=="bzbxlxbh"){
			search2=$("#search_select2 option:selected").val();
		}else if($("#search2 option:selected").val()=="xzdj"){
			search2=$("#search_select2 option:selected").val();
		}else{
			search2=$("#search_select2 option:selected").text();
		}
	}else if($("#search_year2").css("display")=="block"){
		search2=$("#search_year2").val();
	}else{
		search2=$("#search_input2").val();
	}
	if(document.getElementById("search_checkbox2").checked==true){
		mark2=1;
	}else{
		mark2=0;
	}
	selectValue+=";"+$("#search2 option:selected").val()+","+search2+","+mark2;
	if($("#search_select3").css("display")=="block"){
		if($("#search3 option:selected").val()=="bzbxlxbh"){
			search3=$("#search_select3 option:selected").val();
		}else if($("#search3 option:selected").val()=="xzdj"){
			search3=$("#search_select3 option:selected").val();
		}else{
			search3=$("#search_select3 option:selected").text();
		}
	}else if($("#search_year3").css("display")=="block"){
		search3=$("#search_year3").val();
	}else{
		search3=$("#search_input3").val();
	}
	if(document.getElementById("search_checkbox3").checked==true){
		mark3=1;
	}else{
		mark3=0;
	}
	selectValue+=";"+$("#search3 option:selected").val()+","+search3+","+mark3;
	//alert(selectValue)
	showSearList('advancedsearch/selectbycondition',1);
}

function showSearList(actionName,selectedPage){
	closeSearchDiv();
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'rows':10,
			'page':selectedPage,
			'type':Type,
			'condition':selectValue
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
					gotoPageMethodName = "gotoPageNoSearch";
					printPage(data.result.obj.pages, selectedPage, 'showSearList',
							actionName, gotoPageMethodName, data.result.obj.total);
				}
			}else{
				alert(data.result.resultdesc);
			}
		},
		complete : function(){
			CloseLoadingDiv();
		}
	});
}

function gotoPageNoSearch(actionName, totalPage) {
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
		showSearList(actionName, pageNo);
	}
}