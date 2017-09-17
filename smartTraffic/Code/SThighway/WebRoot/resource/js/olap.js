function initfiltermenu(){
	$('#filter_div').dialog({
		title:'维度筛选',
		closed: true,
		collapsible: false,
		width:250,
		height:260,
		modal:true,
		buttons:[{
		  text:'确认',
		  handler:function(){
			var dimId = $("#filter_div #dimId").val();
			var tp = $("#filter_div #dimType").val();
			var pid = $("#d" + dimId).parent().attr("id");
			var target = findDimById(pid, dimId);
			if(tp == 'frd'){
					var vals = "";
					var seles = $("input[name='dimval']:checkbox:checked");
					seles.each(function(a, b){
							vals = vals + $(this).val();
							if(a != seles.size() - 1){
								   vals = vals + ',';
								}
					});
					target.vals = vals;
			}
			if(tp == 'day'){
					var startdt = $("#filter_div #dft2").val();
					var enddt = $("#filter_div #dft1").val();
					target.startdt = startdt;
					target.enddt = enddt;
			}
			if(tp == 'month'){
					var startmt = $("#filter_div #dfm2").val();
					var endmt = $("#filter_div #dfm1").val();
					target.startmt =  startmt;
					target.endmt =  endmt;
			}
			$('#filter_div').dialog('close');
			}
	},{
		text:'关闭',
		  handler:function(){
				$('#filter_div').dialog('close');
			}
		}]});
}
function initbutton(){
	jQuery(".step .s").bind("mouseout", function(){
		if(curPage != Number(jQuery(this).attr("pos"))){
			jQuery(this).removeClass("step1");
			jQuery(this).addClass("step2");
		}
	}).bind("mouseover",function(){
		var obj = jQuery(this);
		if(obj.hasClass("step2")){
			obj.removeClass("step2");
			obj.addClass("step1");
		}
	}).click(function(){
		if(curPage == Number(jQuery(this).attr("pos"))){
			return;
		}
		
		if(Number(jQuery(this).attr("pos")) == 2){
			if(dimpage() == false){
				return;
			};
		}
		
		if(Number(jQuery(this).attr("pos")) == 3){
			if(viewpage() == false){
				return;
			};
		}
		
		if(Number(jQuery(this).attr("pos")) == 1){
			selectkpipage();
		}
		
		jQuery(".step .s[pos='"+curPage+"']").removeClass("step1");
		jQuery(".step .s[pos='"+curPage+"']").addClass("step2");
		curPage = Number(jQuery(this).attr("pos"));
		var obj = jQuery(this);
		obj.removeClass("step2");
		obj.addClass("step1");
	});
}
function selectkpipage(){
	jQuery("#olap_panel").load("OlapIndex!selectKpi.action", {"ktype":pageInfo.ktype,"kpidate":pageInfo.kpidate});
}
function selectkpitype(val){
	pageInfo.ktype = val;
	selectkpipage();
}
function selectkpidate(val){
	pageInfo.kpidate = val;
	//清除维
	tableJson.cols = [{type:"kpiOther", id:"kpi", "income":"dtf2"}];
	tableJson.rows = [];
	
	dimpage();	
}
function tabExist(tidList, tid){
	var ret = false;
	for(k=0; k<tidList.length; k++){
		if(tidList[k] == tid){
			ret = true;
			break;
		}
	}
	return ret;
}
function dimpage(){
	if(kpiJson.length == 0){
		jQuery.messager.alert('提示','您还未选择指标哦！');
		return false;
	};
	var kpis = "";
	var tidList = new Array();
	for(i=0; i<kpiJson.length; i++){
		kpis = kpis + kpiJson[i].kpi_id;
		if(!tabExist(tidList, kpiJson[i].tid)){
			tidList.push(kpiJson[i].tid);
		}
		if(i != kpiJson.length - 1){
			kpis = kpis + ",";
		}
	}
	jQuery("#olap_panel").load("Dims.action", {"kpidate":pageInfo.kpidate,"kpiIds":kpis, "tableCnt":tidList.length});
	
	return true;
}

function viewpage(){
	if(kpiJson.length == 0){
		jQuery.messager.alert('提示','您还未选择指标哦！');
		return false;
	};
	
	//判断指标和维度是否配对，如果维度不配对，提示用户删除维度
	/**
	var kpis = "";
	var tidList = new Array();
	for(i=0; i<kpiJson.length; i++){
		kpis = kpis + kpiJson[i].kpi_id;
		if(!tabExist(tidList, kpiJson[i].tid)){
			tidList.push(kpiJson[i].tid);
		}
		if(i != kpiJson.length - 1){
			kpis = kpis + ",";
		}
	}
	jQuery.ajax({type:"post", data:{"kpidate":pageInfo.kpidate,"kpiIds":kpis, "tableCnt":tidList.length}, url:"Dims!queryDims.action", dataType:"json", success:function(dt){
		var hasNotallow = false;
		var cols = tableJson.cols;
		//判断是否需要删除维度
		for(i=0; i<cols.length; i++){
			if(cols[i].type == 'kpiOther'){
				continue;
			}
			if(!existDim(cols[i].id, dt)){
				cols[i].notallow = true;
				hasNotallow = true;
			}
		}
			
		alert(existDim(2, dt));
	}});
	**/
	
	var t1 = JSON.stringify(tableJson);
	var t2 = JSON.stringify(kpiJson);
	jQuery("#olap_panel").load("ReportView.action", {"kpidate":pageInfo.kpidate,"tableJson":t1, "kpiJson":t2});
	
	return true;
}
function existDim(dimId, dims){
	var ret = false;
	for(i=0; i<dims.length; i++){
		if(dims[i].dim_id == dimId){
			ret = true;
			break;
		}
	}
	return ret;
}
function clearKpis(){
	kpiJson = [];
	$(".hasselkpi .skctx").children().remove();
	clearAll();
}
//回写KPI
function rebackKpis(){
	for(i=0; i<kpiJson.length; i++){
		var t = kpiJson[i];
	var target = $("<div class='s_kpis' id='k"+t.kpi_id+"' ><span class='dimtext'>"+ t.kpi_name +"</span><span class='dimdel' title='删除'>&nbsp;</span></div>");
		target.appendTo(".hasselkpi .skctx");
		//添加删除事件
		target.find(".dimdel").bind("click", function(){
			//从JSON中删除
			var v = $(this).parent().attr("id").replace("k","");
			var idx = -1;
			for(var i=0; i<kpiJson.length; i++){
				if(kpiJson[i].kpi_id == v){
					idx = i;
					break;
				}
			}
			kpiJson.splice(idx, 1);
			//移除DIV
			$(this).parent().remove();
			
		});
	}
}
function chgtr(ts){
	if(ts.checked){
		var t = jQuery(ts);
		//判断是否已经存在
		var vv = t.val();
		var ext = false;
		for(var i=0; i<kpiJson.length; i++){
			if(kpiJson[i].kpi_id == vv){
				ext = true;
				break;
			}
		}
		if(ext){
			jQuery.messager.alert('提示','您已经选了该指标了！');
			return;
		}
		
		//添加指标
		var obj = {"aggre":t.attr("aggre"), "col_name":t.attr("col_name"), "fmt":t.attr("fmt"), "alias":t.attr("alias"), "kpi_name":t.attr("kpi_name"), "tid":t.attr("tid"), "unit":t.attr("unit"), "rate":Number(t.attr("rate")), "kpi_id":t.val(), "descKey":t.attr("descKey")};
		kpiJson.push(obj);
		
		//添加DIV
		var target = $("<div class='s_kpis' id='k"+t.val()+"' ><span class='dimtext'>"+ t.attr("kpi_name") +"</span><span class='dimdel' title='删除'>&nbsp;</span></div>");
		target.appendTo(".hasselkpi .skctx");
		//添加删除事件
		target.find(".dimdel").bind("click", function(){
			target.remove();
			//从JSON中删除
			var v = t.val();
			var idx = -1;
			for(var i=0; i<kpiJson.length; i++){
				if(kpiJson[i].kpi_id == v){
					idx = i;
					break;
				}
			}
			kpiJson.splice(idx, 1);
		});
		
	}else{
		//jQuery(ts).parent().parent().css("background-color", "#FFF");
		//移除指标
		var v = jQuery(ts).val();
		var idx = -1;
		for(var i=0; i<kpiJson.length; i++){
			if(kpiJson[i].kpi_id == v){
				idx = i;
				break;
			}
		}
		kpiJson.splice(idx, 1);
		
		//移除DIV
		$("#k" + v).remove();
	}	
}

function clearAll(){
	var ckd = jQuery("input[name=selall]").attr("checked") == 'checked';
	jQuery("input[name=kids]").each(function(a, b){
		this.checked = false;
	});
}

function setbtn(dimid){
	var obj = $("#d"+dimid)
	var pos = obj.find(".dimfilter").offset();
	$('#mm2').menu('show', {
	 left: pos.left,
	  top: pos.top + 18
	});
	$("#mm2").attr("cid", "d" + dimid);
	var pid = $("#d"+dimid).parent().attr("id");
	var target = findDimById(pid, dimid);
	
	//回写issum,casparent
   if(target.issum == 'y'){
	 $("#m-sum").find(".menu-icon").addClass("icon-ok");
   }else{
	 $("#m-sum").find(".menu-icon").removeClass("icon-ok");
   }
}

function delbtn(dimid){
	//从json中移除
	var pid = $("#d"+dimid).parent().attr("id");
	if(pid == 'hbq'){
		var idx= -1;
		for(var i=0; i<tableJson.rows.length; i++){
			if(tableJson.rows[i].id == dimid){
				idx = i;
			}
		}
		tableJson.rows.splice(idx, 1);
	}else if(pid == 'lbq'){
		var idx= -1;
		for(var i=0; i<tableJson.cols.length; i++){
			if(tableJson.cols[i].id == dimid){
				idx = i;
			}
		}
		tableJson.cols.splice(idx, 1);
	}
	
	
	$("#d"+dimid).remove();
	jQuery("#td" + dimid).show();
}

function findDimById(pid, id){
	var target = null;
	if(pid == 'hbq'){
		for(var i=0; i<tableJson.rows.length; i++){
			if(tableJson.rows[i].id == id){
				target = tableJson.rows[i];
			}
		}
	}else if(pid == 'lbq'){
		for(var i=0; i<tableJson.cols.length; i++){
			if(tableJson.cols[i].id == id){
				target = tableJson.cols[i];
			}
		}
	}
	return target;
}

function filterDim(){
	var cid = $("#mm2").attr("cid");
	var pid = $("#"+cid).parent().attr("id");
	var id = cid.replace("d", "");
	var target = findDimById(pid, id);

	var o = target;
	$("#filter_div").dialog("setTitle", o.dimdesc + " - 筛选");
	 $("#filter_div").dialog('open');
	 if(o.type == 'day'){
			$('#filter_div').dialog('refresh', "DimFilter.action?dimType="+o.type+"&dimId="+o.id+"&dft1="+ (o.enddt == undefined ? "" : o.enddt) +"&dft2=" + (o.startdt == undefined ? "" : o.startdt));
	 }else if(o.type == 'month'){
			$('#filter_div').dialog('refresh', "DimFilter.action?dimType="+o.type+"&dimId="+o.id+"&dfm1="+ (o.endmt == undefined ? "" : o.endmt) +"&dfm2=" + (o.startmt == undefined ? "" : o.startmt));
	 }else{
			$('#filter_div').dialog('refresh', "DimFilter.action?dimType="+o.type+"&dimId="+o.id+"&vals=" + o.vals);
	 }
}
   
function sumDim(){
   var cid = $("#mm2").attr("cid");
   var pid = $("#"+cid).parent().attr("id");
   var id = cid.replace("d", "");
  var target = findDimById(pid, id);
   if(target.issum == 'y'){
	 $("#m-sum").find(".menu-icon").removeClass("icon-ok");
	 target.issum = 'n';
   }else{
	 $("#m-sum").find(".menu-icon").addClass("icon-ok");
	 target.issum = 'y';
   }
}

function findCompById2(id, rows, cols){
	var target = null;
	for(var i=0; i<rows.length; i++){
		if('d' + rows[i].id == id){
			target = rows[i];
		}
	}
	//没找到继续找
	if(target == null){
		for(var i=0; i<cols.length; i++){
			if('d' + cols[i].id == id){
				target = cols[i];
			}
		}
	}
	return target;
}
function updateCompPos(){
	var cols = tableJson.cols;
	var rows = tableJson.rows;
	
	tableJson.cols = [];
	tableJson.rows = [];
	
	$("#hbq").children().each(function(){
		if($(this).css("position") != 'absolute'){
			var id = $(this).attr("id");
			tableJson.rows.push(findCompById2(id, rows, cols));
		}
	});
	
	$("#lbq").children().each(function(){
		if($(this).css("position") != 'absolute'){
			var id = $(this).attr("id");
			tableJson.cols.push(findCompById2(id, rows, cols));
		}
	});
}