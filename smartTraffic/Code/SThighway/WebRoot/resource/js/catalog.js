if($ == undefined){
	$ = jQuery;
}
function initTree(initsy){
	$('#ggcatatree').tree({
		url:'ReportCatalog!tree.action?type=-1&cataControl='+(initsy?true:false)+'&T='+Math.random(),
		dnd:false,
		data: [{id:'0', text:'报表目录', state:'closed'}],
		onBeforeLoad: function(node){
			if(!node || node == null){
				return false;
			}
		},
		onClick: function(node){
			$('#gytablelist').datagrid('load',{
				cataId: node.id, t:Math.random()
			});

		},
		onContextMenu: function(e, node){
			e.preventDefault();
			if(initsy){
				return;
			}
			$('#ggcatatree').tree('select', node.target);
			if(node.id == '0'){
				$('#ggcatatreeMenu').menu("disableItem", $("#ggcatatreeMenu #mod"));
				$('#ggcatatreeMenu').menu("disableItem", $("#ggcatatreeMenu #del"));
			}else{
				$('#ggcatatreeMenu').menu("enableItem", $("#ggcatatreeMenu #mod"));
				$('#ggcatatreeMenu').menu("enableItem", $("#ggcatatreeMenu #del"));
			}
			$('#ggcatatreeMenu').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		}
	});
	var node = $('#ggcatatree').tree("getRoot");
	$('#ggcatatree').tree("expand", node.target);
	if(initsy){
		initOtherTree();
	}
}
function showReportDiv(id, mvid, view, isprint, isexport){
	if($("#reportdailog").size() == 0){
		$("<div id=\"reportdailog\"></div>").appendTo("body");
	}
	
	var tb = [{
		iconCls:'icon-back',
		text:"返回",
		handler:function(){
			$("#reportdailog").dialog("close");
		}
	}];
	if(isexport){
		tb.push({
			iconCls:'icon-export',
			text:"导出",
			handler:function(){
				if(mvid == null || mvid == "null"){
					window.frames["olapinfo"].exportPage();
					return;
				}
				var expType = "html";
				var ctx = "<form action=\"ReportView!export.action\" method=\"post\"><input type='hidden' name='mvid' id='mvid' value='usave."+mvid+"'><input type='hidden' name='type' id='type'><div class='exportpanel'><span class='exptp select' tp='html'><img src='../resource/img/export-html.gif'><br>HTML</span>"+
			"<span class='exptp' tp='csv'><img src='../resource/img/export-csv.gif'><br>CSV</span>" +
			"<span class='exptp' tp='excel'><img src='../resource/img/export-excel.gif'><br>EXCEL</span>" + 
			"<span class='exptp' tp='pdf'><img src='../resource/img/export-pdf.gif'><br>PDF</span>" + 
			"</div></form>";
			$('#pdailog').dialog({
				title: '导出数据',
				width: 310,
				height: 200,
				closed: false,
				cache: false,
				modal: true,
				toolbar:null,
				content: ctx,
				onLoad:function(){},
				buttons:[{
							text:'确定',
							handler:function(){
								$("#pdailog #type").val(expType);
								$("#pdailog form").submit();
								$('#pdailog').dialog('close');
							}
						},{
							text:'取消',
							handler:function(){
								$('#pdailog').dialog('close');
							}
						}]
			});
			//注册事件
			$(".exportpanel span.exptp").click(function(e) {
				$(".exportpanel span.exptp").removeClass("select");
				$(this).addClass("select");
				expType = $(this).attr("tp");
			});
			}
		});
	}
	if(isprint == 1){
		tb.push({
			iconCls:'icon-print',
			text:"打印",
			handler:function(){
				if(mvid == null || mvid == "null"){
					window.frames["olapinfo"].printData();
				}else{
					var url2 = "ReportView!print.action?mvid=usave."+mvid+"&T="+Math.random();
					window.open(url2);
				}
			}
		});
	}
	var obj = {
		fit:true,
		border:false,
		closed: false,
		cache: false,
		modal: false,
		noheader:true,
		toolbar:tb,
		onClose:function(){
			$('#reportdailog').dialog('destroy');
		}
	};
	if(mvid == null || mvid == "null"){
		obj.content = "<iframe id=\"olapinfo\" name=\"olapinfo\" frameborder=\"0\" width=\"100%\" height=\"100%\"></iframe>";
	}else{
		obj.href = '../control/extView?mvid=usave.'+mvid+"&returnJsp=false";
	}
	$('#reportdailog').dialog(obj);
	if(mvid == null || mvid == "null"){
		src="../bireport/ReportDesign!viewOlapReport.action?pageId="+id+"&showtit=false&T="+Math.random();
		$("#olapinfo").attr("src", src);
	}
}
function fmtreport(value,row,index){
	return "<a href=\"javascript:;\" onclick=\"showReportDiv("+row.id+", '"+row.rfile+"', "+row.view+", "+row["print"]+", "+row["export"]+")\">" + value + "</a>";
}
function fmtAuthreport(value,row,index){
	return "<a href=\"javascript:;\" onclick=\"showReportDiv("+row.id+", '"+row.rfile+"')\">" + value + "</a>";
}
function fmtreport2(value,row,index){
	return "<a href=\"javascript:;\" onclick=\"editsyReport('"+row.income+"',"+row.id+")\">" + value + "</a>";
}
function fmturl(value,row,index){
	return "control/extView?mvid=usave." + row.rfile;
}
function fmtincome(value, row, index){
	if(row.incomeId == null){
		return "<font color='red'>未知</font>";
	}else{
		return value;
	}
}
function fmtrelease(value, row, index){
	if(value == null){
		return "否";
	}else{
		return "<font color='red'>是</font>";
	};
}
function initOtherTree(){
	$('#sycatatree').tree({
		url:'ReportCatalog!tree.action?type=1',
		dnd:false,
		data: [{id:'0', text:'报表目录', state:'closed'}],
		onBeforeLoad: function(node){
			if(!node || node == null){
				return false;
			}
		},
		onClick: function(node){
			$('#sytablelist').datagrid('load',{
				cataId: node.id, t:Math.random()
			});

		},
		onContextMenu: function(e, node){
			e.preventDefault();
			$('#sycatatree').tree('select', node.target);
			if(node.id == '0'){
				$('#sycatatreeMenu').menu("disableItem", $("#sycatatreeMenu #mod"));
				$('#sycatatreeMenu').menu("disableItem", $("#sycatatreeMenu #del"));
			}else{
				$('#sycatatreeMenu').menu("enableItem", $("#sycatatreeMenu #mod"));
				$('#sycatatreeMenu').menu("enableItem", $("#sycatatreeMenu #del"));
			}
			$('#sycatatreeMenu').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		}
	});
	var node = $('#sycatatree').tree("getRoot");
	$('#sycatatree').tree("expand", node.target);
}
function addCatalog(update, treeId){
	var node = $("#" + treeId).tree("getSelected");
	var obj;
	if(update){
		$.ajax({
			   type: "GET",
			   async: false,
			   url: "ReportCatalog!get.action",
			   dataType:"JSON",
			   data: {"id":node.id},
			   success: function(resp){
				  obj = resp;
			   }
		});
	}
	var ctx = "<div class=\"textpanel\"><span class=\"inputtext\">目录名称：</span><input type=\"text\" id=\"name\" class=\"inputform\" value=\""+(obj?obj.name:"")+"\"><br/><span class=\"inputtext\">目录说明：</span><input type=\"text\" id=\"note\" class=\"inputform\" value=\""+((obj?obj.note:""))+"\"><br/><span class=\"inputtext\">排序：</span><input type=\"text\" id=\"ord\" class=\"inputform\" value=\""+(obj?obj.ord:"1")+"\"><br/></div>";
	$('#pdailog').dialog({
		title: update?'修改目录':'新建目录',
		width: 330,
		height: 200,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content: ctx,
		buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var name = $("#pdailog #name").val();
					var note = $("#pdailog #note").val();
					var ord = $("#pdailog #ord").val();
					if(isNaN(ord)){
						alert("排序字段必须是数字类型。");
						return;
					}
					if(update==false){
						$.ajax({
						   type: "POST",
						   url: "ReportCatalog!save.action",
						   dataType:"text",
						   data: {"name":name,"note":note,"ord":ord, "type":(treeId=='ggcatatree' ? "-1" : "1"), "pid":node.id},
						   success: function(resp){
							   $("#" + treeId).tree("append", {parent:node.target, data:[{id:resp,text:name}]});
						   }
						});
					}else{
						$.ajax({
						   type: "POST",
						   url: "ReportCatalog!update.action",
						   dataType:"text",
						   data: {"name":name,"note":note,"ord":ord, "id":node.id},
						   success: function(resp){
							   $("#" + treeId).tree("update", {target:node.target, text:name});
						   }
						});
					}
					$('#pdailog').dialog('close');
				}
			},{
				text:'取消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#pdailog').dialog('close');
				}
			}]
	});
}
function delCatalog(treeId){
	var node = $("#" + treeId).tree("getSelected");
	//判断是否有子，有子不能删除
	if($("#" + treeId).tree("getChildren", node.target).length > 0){
		$.messager.alert("出错了。","该目录含有子目录，不能删除。", "error");
		return;
	}
	if(confirm('是否确认删除？')){
		$.ajax({
		   type: "GET",
		   url: "ReportCatalog!delete.action",
		   data: {"id":node.id, "type": ("ggcatatree"==treeId ? "-1":"1") },
		   success: function(resp){
			   $("#" + treeId).tree("remove", node.target);
		   },
		   error: function(){
			   $.messager.alert("出错了。","该目录下可能含有报表，不能删除。", "error");
		   }
		});
	}
}
function editsyReport(income, id){
	var url;
	if(income == 'olap'){ 
		url = "../bireport/ReportDesign.action?pageId="+id+"&showtit=false";
	}else{
		url = "../webreport/ReportMain.action?pageId="+id+"&showtit=false";
	}
	//location.href = url;
	if($("#reportdailog").size() == 0){
		$("<div id=\"reportdailog\"></div>").appendTo("body");
	}
	var tb = [{
		iconCls:'icon-back',
		text:"返回",
		handler:function(){
			$("#reportdailog").dialog("close");
		}
	}];
	var obj = {
		fit:true,
		border:false,
		closed: false,
		cache: false,
		modal: false,
		noheader:true,
		content:"<iframe id=\"olapinfo\" name=\"olapinfo\" src=\""+url+"\" frameborder=\"0\" width=\"100%\" height=\"100%\"></iframe>",
		toolbar:tb,
		onClose:function(){
			$('#reportdailog').dialog('destroy');
		}
	};
	$('#reportdailog').dialog(obj);
}
function initGrid(initsy){
	initGyGrid(initsy);
	if(initsy){
		
	}else{
		return;
	}
	$("#sytablelist").datagrid({
		singleSelect:false,
		collapsible:false,
		pagination:true,
		pageSize:20,
		url:'Report!list.action',
		method:'get',
		remoteSort:true,
		multiSort:false,
		sortName:"crtdate",
		sortOrder:"desc",
		queryParams:{t:Math.random()},
		toolbar:[{
		  text:'编辑',
		  iconCls:'icon-newpage',
		  handler:function(){
			var row = $("#sytablelist").datagrid("getChecked");
			if(row == null || row.length == 0){
				$.messager.alert("出错了。","您还未勾选数据。", "error");
				return;
			}
			if(row.length > 1){
				$.messager.alert("出错了。","只能选择一个报表进行编辑。", "error");
				return;
			}
			editsyReport(row[0].income, row[0].id);
		  }
		},{
		  text:'发布',
		  iconCls:'icon-release',
		  handler:function(){
			var row = $("#sytablelist").datagrid("getChecked");
			if(row == null || row.length == 0){
				$.messager.alert("出错了。","您还未勾选数据。", "error");
				return;
			}
			releaseReport(row);
		  }
		},{
		  text:'改名',
		  iconCls:'icon-edit',
		  handler:function(){
			var row = $("#sytablelist").datagrid("getChecked");
			if(row == null || row.length == 0){
				$.messager.alert("出错了。","您还未勾选数据。", "error");
				return;
			}
			if(row.length > 1){
				$.messager.alert("出错了。","只能选择一个报表进行改名。", "error");
				return;
			}
			chgreportname(row[0],'sy');
		  }
		},{
		  text:'删除',
		  iconCls:'icon-cancel',
		  handler:function(){
			var row = $("#sytablelist").datagrid("getChecked");
			if(row == null || row.length == 0){
				$.messager.alert("出错了。","您还未勾选数据。", "error");
				return;
			}
			deletemorereport(row);  
		  }
		}]
	});
}
function releaseReport(reports){
	var ids = "";
	var types = "";
	for(i=0; i<reports.length; i++){
		ids = ids + reports[i].id;
		types = types + reports[i].income;
		if(i != reports.length - 1){
			ids = ids + ",";
			types = types + ",";
		}
	}
	var param = {"ids": ids, "types":types};
	var ctx = "<div style=\"margin:10px;\">报表目录： <input id=\"cataSelect\" style=\"width:200px;\" value=\"\"><br><br><font color='red'>说明：</font>未发布的报表会自动发布到您选择的报表目录中，对于已发布的报表，系统自动覆盖。</div>";
	$('#pdailog').dialog({
		title: '批量发布报表',
		width: 350,
		height: 230,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content:ctx,
		onLoad:function(){},
		buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var cataId = $("#pdailog #cataSelect").combotree("getValue");
					if(cataId == null || cataId == ""){
						$.messager.alert("出错了。","您还未选择报表发布目录。", "error");
						return;
					}
					$('#pdailog').dialog('close');
					__showLoading();
					$.ajax({
						type:"POST",
						url: "ReportManager!releaseMore.action",
						dataType:"html",
						data: {"ids":ids, "types":types, "cataId":cataId},
						success: function(resp){
							__hideLoading();
							$.messager.alert("成功了。","报表发布成功。", "info");
						},
						error: function(){
							__hideLoading();
							$.messager.alert("出错了。","系统出错，请查看后台日志。", "error");
						}
					});
				}
			},{
				text:'取消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#pdailog').dialog('close');
				}
			}]
	});
	$("#cataSelect").combotree({
		url:"ReportCatalog!tree.action?type=-1",
		onClick: function(node){
		}
	});
}
function initGyGrid(initsy){
	if(initsy){
		$("#gytablelist").datagrid({
			singleSelect:true,
			collapsible:false,
			url:'Report!listAuthGy.action',
			pagination:true,
			pageSize:20,
			queryParams:{t:Math.random()},
			method:'get',
			onDblClickRow: function(index, data){
				showReportDiv(data.id, data.rfile, data.view, data["print"], data["export"]);
			}
		});
	}else{
		$("#gytablelist").datagrid({
			singleSelect:false,
			collapsible:false,
			url:'Report!listgy.action',
			pagination:true,
			pageSize:20,
			queryParams:{t:Math.random()},
			method:'get',
			onDblClickRow: function(index, data){
				showReportDiv(data.id, data.rfile);
			},
			toolbar:[{
			  text:'查看',
			  iconCls:'icon-newpage',
			  handler:function(){
				var row = $("#gytablelist").datagrid("getChecked");
				if(row == null || row.length == 0){
					$.messager.alert("出错了。","您还未勾选数据。", "error");
					return;
				}
				if(row.length > 1){
					$.messager.alert("出错了。","只能选择一个报表查看。", "error");
					return;
				}
				var data = row[0];
				showReportDiv(data.id, data.rfile);;
			  }
			},{
			  text:'改名',
			  iconCls:'icon-edit',
			  handler:function(){
				var row = $("#gytablelist").datagrid("getChecked");
				if(row == null || row.length == 0){
					$.messager.alert("出错了。","您还未勾选数据。", "error");
					return;
				}
				if(row.length > 1){
					$.messager.alert("出错了。","只能选择一个报表改名。", "error");
					return;
				}
				chgreportname(row[0],'gy');
			  }
			},{
			  text:'删除',
			  iconCls:'icon-cancel',
			  handler:function(){
				var row = $("#gytablelist").datagrid("getChecked");
				if(row == null || row.length == 0){
					$.messager.alert("出错了。","您还未勾选数据。", "error");
					return;
				}
				if(confirm("是否确认删除？")){
					var ids = "";
					var incomes = "";
					var mvids = "";
					for(i=0; i<row.length; i++){
						ids = ids + row[i].id;
						incomes = incomes + row[i].income;
						mvids = mvids + (row[i].rfile==null?"X":row[i].rfile);
						if(i != row.length - 1){
							ids = ids + ",";
							incomes = incomes + ",";
							mvids = mvids + ",";
						}
					}
					$.post("Report!delPubReport.action", {"pageId": ids, "income":incomes, "mvid":mvids}, function(resp){
						var node = $("#ggcatatree").tree("getSelected");
						$('#gytablelist').datagrid('load',{
							cataId: node==null?"0":node.id,
							t:Math.random()
						});
					});
				}
			  }
			}]
		});
	}
}
//删除多个，可以是报表或多维分析页面
function deletemorereport(reports){
	if(confirm("是否确认删除？")){
		var ids = "";
		var types = "";
		for(i=0; i<reports.length; i++){
			ids = ids + reports[i].id;
			types = types + reports[i].income;
			if(i != reports.length - 1){
				ids = ids + ",";
				types = types + ",";
			}
		}
		$.post("ReportManager!deleteMore.action", {"ids": ids, "types":types}, function(resp){
			var node = $("#sycatatree").tree("getSelected");
			$('#sytablelist').datagrid('load',{
				cataId: node==null?"0":node.id,
				t:Math.random()
			});
		});
	}
}
function deletemyreport(report){
	if(confirm("是否确认删除？")){
		$.post("../"+(report.income=='olap'?"bireport":"webreport")+"/MyReport!delete.action", {"reportId": report.id}, function(resp){
			var node = $("#sycatatree").tree("getSelected");
			$('#sytablelist').datagrid('load',{
				cataId: node==null?"0":node.id,
				t:Math.random()
			});
		});
	}
}
/**
tp 表示公有(gy)还是私有(sy)
**/
function chgreportname(report, tp){
	var ctx = "<div style=\"margin:5px;\"><div style=\"margin:5px;\">报表名称：&nbsp;<input type=\"text\" id=\"reportName\" style=\"width:195px;\" value=\""+(report.name)+"\"><br/><br/>报表目录： <input id=\"cataSelect\" style=\"width:200px;\" value=\""+(report.cataId)+"\"></div></div>";
	$('#pdailog').dialog({
		title: '编辑报表',
		width: 300,
		height: 200,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content:ctx,
		onLoad:function(){},
		buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var name = $("#pdailog #reportName").val();
					var cataId = $("#pdailog #cataSelect").combotree("getValue");
					if(tp == 'gy'){
						$.post("Report!updatePubReport.action", {"pageId": report.id, "pageName":name,"cataId":cataId}, function(resp){
							var node = $("#gycatatree").tree("getSelected");
							$('#gytablelist').datagrid('load',{
								cataId: node==null?"0":node.id,
								t:Math.random()
							});
						});

					}else{
						$.post("../"+(report.income=='olap'?"bireport":"webreport")+"/MyReport!rename.action", {"reportId": report.id, "reportName":name,"cataId":cataId}, function(resp){
							var node = $("#sycatatree").tree("getSelected");
							$('#sytablelist').datagrid('load',{
								cataId: node==null?"0":node.id,
								t:Math.random()
							});
						});
					}
					$('#pdailog').dialog('close');
				}
			},{
				text:'取消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#pdailog').dialog('close');
				}
			}]
	});
	$("#cataSelect").combotree({
		url:"ReportCatalog!tree.action?type=" + (tp == 'gy' ? "-1" :"1"),
		onClick: function(node){
		}
	});
}