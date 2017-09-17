var index=1;
$(document).ready(function(){
	$("#top_text").text("数据交换状态监测");//top上的显示
	$(".data_top_right a").click(function(){
		if($(this).attr("class")=="page_no_select"){
			return;
		}
		$(".page_no_select").attr("class","page_no");
		$(this).attr("class","page_no_select");
		//alert($(this).attr("rel"))
		$("div[index='"+index+"']").hide(1000,showOther($(this).attr("rel")));
		index=$(this).attr("rel");
	});
	$("#right_image").click(function(){
		if($(".data_middle").children(".slide_div").length==1){
			return;
		}else if(index==$(".data_middle").children(".slide_div").length){
			$("div[index='"+index+"']").hide(1000,showOther(1));
			index=1;
		}else{
			$("div[index='"+index+"']").hide(1000,showOther(index+1));
			index++;
		}
		$(".page_no_select").attr("class","page_no");
		$(".data_top_right a[rel='"+index+"']").attr("class","page_no_select");
	});
	$("#left_image").click(function(){
		//alert($(".data_middle").children(".slide_div").length)
		if($(".data_middle").children(".slide_div").length==1){
			return;
		}else if(index==1){
			$("div[index='"+index+"']").hide(1000,showOther($(".data_middle").children(".slide_div").length));
			index=$(".data_middle").children(".slide_div").length;
		}else{
			$("div[index='"+index+"']").hide(1000,showOther(index-1));
			index--;
		}
		$(".page_no_select").attr("class","page_no");
		$(".data_top_right a[rel='"+index+"']").attr("class","page_no_select");
	});
	$(".select_ul_item a").click(function(){
		if($(this).attr("class")=="choose_radio_select"){
			return;
		}else{
			$(".choose_radio_select").attr("class","choose_radio");
			$(this).attr("class","choose_radio_select");
			showlinecharts($(this).next().text(),$(this).attr("rel"));
			showpiecharts($(this).next().text(),$(this).attr("rel"));
		}
		
	});
	 
	showSystem();
		var t=new Date;
		var month=t.getMonth();
		var year=t.getFullYear();
		if(month==0){
			month=12;
			year=year-1;
		}
		var timeString = [year, month+1, t.getDate()].join('-');
		var time=formatDate(timeString,"yyyy-MM-dd");
		$("#search_time").val(time);
	// $("#myStat").circliful();
});
setInterval(refreshsystem,3000);
setInterval(refreshcharts,180000);
function refreshcharts(){
	showlinecharts($(".choose_radio_select").next().text(),$(".choose_radio_select").attr("rel"));
	showpiecharts($(".choose_radio_select").next().text(),$(".choose_radio_select").attr("rel"));
}
function refreshsystem(){
	$.ajax({
		url:'datastate/datasyncsystem',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".data_middle,.data_top_right").empty();
				var list=data.result.list;
				var length=Math.ceil(list.length/6);
				for(var j=0;j<length;j++){
					if(j==0){
						var newA=$("<a class='page_no_select' rel='"+(j+1)+"'>"+(j+1)+"</a>");
						$(".data_top_right").append(newA);
					}else{
						var newA=$("<a class='page_no' rel='"+(j+1)+"'>"+(j+1)+"</a>");
						$(".data_top_right").append(newA);
					}
				}
				for(var i=0;i<length;i++){
					var newOutDiv=$("<div index='"+(i+1)+"' class='slide_div'></div>");
					var divnumber;
					for(var m=i*6;m<(i+1)*6;m++){
						//if(list[i].moduletype==1){
						if(m>=list.length){
							continue;
						}
						var newDiv=$("<div class='data_middle_div'></div>");
						if(list[m].moduletype==6){
							newDiv.append($("<div class='middle_top"+list[m].modulename.length+"'><img src='image/datasync/ic_upload.png' class='download_image'><span class='data_span datapos'>"+list[m].modulename+"</span></div>"));
						}else{
							newDiv.append($("<div class='middle_top"+list[m].modulename.length+"'><img src='image/datasync/ic_download.png' class='download_image'><span class='data_span datapos'>"+list[m].modulename+"</span></div>"));
						}
							
						if(list[m].currentstate==1){
							newDiv.append($("<div class='data_middle_middle'><img alt='' class='sync_image move' src='image/datasync/ic_insync.gif'></div>"));
							newDiv.append($("<div class='data_middle_bottom'><label class='data_label_sync'>正在同步中</label><br/><br/><label class='data_label_time'>上一次同步："+list[m].lasttime+"</label></div>"));
						}else if(list[m].currentstate==0){
							newDiv.append($("<div class='data_middle_middle'><img alt='' class='sync_image move' src='image/datasync/ic_success.png'></div>"));
							newDiv.append($("<div class='data_middle_bottom'><label class='data_label_success'>同步成功</label><br/><br/><label class='data_label_time'>上一次同步："+list[m].lasttime+"</label></div>"));
						}else if(list[m].currentstate==-1){
							newDiv.append($("<div class='data_middle_middle'><img alt='' class='sync_image move' src='image/datasync/ic_fail.png'></div>"));
							newDiv.append($("<div class='data_middle_bottom'><label class='data_label_fail'>同步失败</label><br/><br/><label class='data_label_time'>上一次同步："+list[m].lasttime+"</label></div>"));
						}else if(list[m].currentstate==-2){
							newDiv.append($("<div class='data_middle_middle'><img alt='' class='sync_image move' src='image/datasync/ic_nomessage.png'></div>"));
							newDiv.append($("<div class='data_middle_bottom'><label class='data_label_no'>从未同步过</label></div>"));
						}
						newOutDiv.append(newDiv);
					}
					$(".data_middle").append(newOutDiv);
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function showOther(id){
	$("div[index='"+id+"']").show(1000);
}
function showSystem(){
	$.ajax({
		url:'datastate/datasyncsystem',
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".data_middle,.data_top_right").empty();
				var list=data.result.list;
				var length=Math.ceil(list.length/6);
				for(var j=0;j<length;j++){
					if(j==0){
						var newA=$("<a class='page_no_select' rel='"+(j+1)+"'>"+(j+1)+"</a>");
						$(".data_top_right").append(newA);
					}else{
						var newA=$("<a class='page_no' rel='"+(j+1)+"'>"+(j+1)+"</a>");
						$(".data_top_right").append(newA);
					}
				}
				for(var i=0;i<length;i++){
					var newOutDiv=$("<div index='"+(i+1)+"' class='slide_div'></div>");
					var divnumber;
					for(var m=i*6;m<(i+1)*6;m++){
						//if(list[i].moduletype==1){
						if(m>=list.length){
							continue;
						}
						var newDiv=$("<div class='data_middle_div'></div>");
						if(list[m].moduletype==6){
							newDiv.append($("<div class='middle_top"+list[m].modulename.length+"'><img src='image/datasync/ic_upload.png' class='download_image'><span class='data_span datapos'>"+list[m].modulename+"</span></div>"));
						}else{
							newDiv.append($("<div class='middle_top"+list[m].modulename.length+"'><img src='image/datasync/ic_download.png' class='download_image'><span class='data_span datapos'>"+list[m].modulename+"</span></div>"));
						}
							
						if(list[m].currentstate==1){
							newDiv.append($("<div class='data_middle_middle'><img alt='' class='sync_image move' src='image/datasync/ic_insync.gif'></div>"));
							newDiv.append($("<div class='data_middle_bottom'><label class='data_label_sync'>正在同步中</label><br/><br/><label class='data_label_time'>上一次同步："+list[m].lasttime+"</label></div>"));
						}else if(list[m].currentstate==0){
							newDiv.append($("<div class='data_middle_middle'><img alt='' class='sync_image move' src='image/datasync/ic_success.png'></div>"));
							newDiv.append($("<div class='data_middle_bottom'><label class='data_label_success'>同步成功</label><br/><br/><label class='data_label_time'>上一次同步："+list[m].lasttime+"</label></div>"));
						}else if(list[m].currentstate==-1){
							newDiv.append($("<div class='data_middle_middle'><img alt='' class='sync_image move' src='image/datasync/ic_fail.png'></div>"));
							newDiv.append($("<div class='data_middle_bottom'><label class='data_label_fail'>同步失败</label><br/><br/><label class='data_label_time'>上一次同步："+list[m].lasttime+"</label></div>"));
						}else if(list[m].currentstate==-2){
							newDiv.append($("<div class='data_middle_middle'><img alt='' class='sync_image move' src='image/datasync/ic_nomessage.png'></div>"));
							newDiv.append($("<div class='data_middle_bottom'><label class='data_label_no'>从未同步过</label></div>"));
						}
						newOutDiv.append(newDiv);
					}
					$(".data_middle").append(newOutDiv);
				}
				showlinecharts(list[0].modulename,1);
				showpiecharts(list[0].modulename,1);
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function showlinecharts(title,type){
	$.ajax({
		url:'datastate/datalinechartinfo',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'moduletype':type,
			'xnumber':5
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				new Highcharts.Chart({
					 chart: {
			             renderTo: 'linechart',
			             type: 'line',
			             marginRight: 130,
			             marginBottom: 80
			             /*events: {
			                 load:loadTime
			             }*/
			         },
				        title: {
				            text: title+'同步数量统计',
				            x: -20, //center
				            style:{
				            	fontFamily:'微软雅黑'
				            }
				        },
				        /*subtitle: {
				            text: 'Source: WorldClimate.com',
				            x: -20
				        },*/
				        xAxis: {
				            categories: [GetDateStr(-4), GetDateStr(-3), GetDateStr(-2), GetDateStr(-1), GetDateStr(0)]
				        },
				        yAxis: {
				            title: {
				                text: '数量'
				            },
				            plotLines: [{
				                value: 0,
				                width: 1,
				                color: '#808080'
				            }]
				        },
				        tooltip: {
				            valueSuffix: '行'
				        },
				        legend: {
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'middle',
				            borderWidth: 0
				        },
				        series: [{
				            name: '同步数量',
				            data: [list[0].totallinewritten, list[1].totallinewritten, list[2].totallinewritten, list[3].totallinewritten,list[4].totallinewritten]
				        }]
				        
				    });
				 $(".highcharts-tooltip").next().hide();
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
	
}

function GetDateStr(AddDayCount) { 
	var dd = new Date();
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
	var y = dd.getFullYear(); 
	var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
	var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate(); //获取当前几号，不足10补0
	return y+"-"+m+"-"+d; 
}
function showpiecharts(title,type){
	$.ajax({
		url:'datastate/datapiechartinfo',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'moduletype':type
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.objs;
				var success=Number(list[0]);
				var fail=Number(list[1]);
				var piechart=new Highcharts.Chart({
			        chart: {
			        	renderTo: 'piechart',
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        colors:['#51c219','#ff4a4e'],
			        title: {
			            text: title+'同步成功率统计',
			            style:{
			            	fontFamily:'微软雅黑'
			            }
			        },
			        tooltip: {
			    	    pointFormat: '<b>{point.percentage:.1f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    color: '#000000',
			                   // connectorColor: '#000000',
			                    formatter:function(){
			                    	if(this.point.name == '成功'){
			                    		return '<b style="color:#51c219">'+this.point.name+':'+Highcharts.numberFormat(this.percentage, 2) +'%</b>';
			                    	}else{
			                    		return '<b style="color:#ff4a4e">'+this.point.name+':'+Highcharts.numberFormat(this.percentage, 2) +'%</b>';
			                    	}
			                    }
			                    /*format: '<b>{point.name}</b>: {point.percentage:.1f} %'*/
			                }
			            }
			        },
			        series: [{
			            type: 'pie',
			            name: '成功',
			            data: [['成功',success],['失败', fail ]]
			        }]
			    });
				 $(".highcharts-tooltip").next().hide();
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function showMoreInfoDiv(){
	var bh = $("#out_div").height()+50;
	var bw = $(".common_c0").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	$(".Top_left").text($(".choose_radio_select").next().text()+"详细信息");
	//$("#DetailDiv").show();
	showMoreInfo();
	
	
}
function showMoreInfo(){
	$.ajax({
		url:'datastate/findmoreinfo',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'moduletype':$(".choose_radio_select").attr("rel"),
			'time':$("#search_time").val()
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				$(".addTr").remove();
				var list=data.result.list;
				
				for(var i=0;i<list.length;i++){
					var transloglist=list[i].transloglist;
					if(transloglist==null){
						continue;
					}
					for(var j=0;j<transloglist.length;j++){
						var steploglist=list[i].transloglist[j].steploglist;
						//for(var m=0;m<steploglist.length;m++){
							var newTr=$("<tr class='addTr'></tr>");
							newTr.append($("<td rowspan='"+steploglist.length+"'>"+transloglist[j].transname+"</td>"));
							newTr.append($("<td>"+steploglist[0].stepname+"</td>"));
							newTr.append($("<td>"+SecondTimeFormat(steploglist[0].logdate)+"</td>"));
							newTr.append($("<td>"+steploglist[0].writtenline+"</td>"));
							$("#detailTable").append(newTr);
						//}
						for(var m=1;m<steploglist.length;m++){
							var newTr1=$("<tr class='addTr'></tr>");
							newTr1.append($("<td>"+steploglist[m].stepname+"</td>"));
							newTr1.append($("<td>"+SecondTimeFormat(steploglist[m].logdate)+"</td>"));
							newTr1.append($("<td>"+steploglist[m].writtenline+"</td>"));
							$("#detailTable").append(newTr1);
						}
					}
				}
				$("#DetailDiv").show();
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function searchTimeInfo(){
	showMoreInfo();
}
//输入框提示
function TextFocus(id) {
	if (id.value == id.defaultValue) {
		id.value = '';
		id.style.color = '#333333';
	}
}
//输入框提示
function TextBlur(id) {
	if (!id.value) {
		id.value = id.defaultValue;
		id.style.color = '#a3a3a3';
	}
}
function leftOver(thisop,name){
	thisop.src="image/datasync/"+name+"hover.png";
}
function leftOut(thisop,name){
	thisop.src="image/datasync/"+name+"normal.png";
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}