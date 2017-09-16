function echartsShow(id){
	var Echarts = echarts.init(document.getElementById(id),'macarons');
	Echarts.clear();
	Echarts.showLoading({text: '正在努力的读取数据中...'});
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		data : {
			type : id
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			Echarts.setOption(option);
			Echarts.hideLoading();
		},
		error : function() {
	
		}
	});
	$("#"+id+"-code").resize(function() { 
		Echarts.resize();
	});
}
/*
//各年级学生数量
function stuSum(){
	
	var stuSum = echarts.init(document.getElementById("stuSum"),'macarons');
	stuSum.clear();
	stuSum.showLoading({text: '正在努力的读取数据中...'});
	$.getJSON("indexReport.do", function(data) { //getJSON的方法
		var option = data.option;
		stuSum.setOption(option);
		stuSum.hideLoading();
	});
	
	$.ajax({ // ajax的方法
		type : "POST",
		url : "indexReport.do",
		dataType : "JSON",
		async : false, 
		data : {
			type : "gradeStudentNum"
		},
		success : function(data) {
			//alert(data[0].option);
			var studentSunNum=data[0].studentSunNum;
			$("#stuSunNum").html("学生总人数："+studentSunNum+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：人");
			eval("var option = "+data[0].option);
			stuSum.setOption(option);
			stuSum.hideLoading();
		},
		error : function(data) {

		}

	});
	$("#stuSum-code").resize(function() { 
		stuSum.resize(); //使第一个图表适应
	});
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		data : {
			type : "gradeStudentNum"
		},
		success : function(data) {
			var xname = new Array();
			var da = new Array();
			var legend=new Array();
			var studentSunNum=0;
			xname=data[0].x_axle;
			studentSunNum=data[0].studentSunNum;
			$("#stuSunNum").html("学生总人数："+studentSunNum+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：人");
			da =data[1].data;
			var barName=new Array();
			var barDa = new Array();
			//小学总数
			barName.push("小学");
			var xxDa=0;
			for(var i=0;i<6;i++){
				xxDa+=data[1].data[i];
			}
			barDa.push(xxDa);
				//初中总数
				barName.push("初中");
				var czDa=0;
				for(var i=6;i<9;i++){
					czDa+=data[1].data[i];
				}
				barDa.push(czDa);
				
				//高中总数
				barName.push("高中");
				var gzDa=0;
				for(var i=9;i<data[1].data.length;i++){
					gzDa+=data[1].data[i];
				}
				barDa.push(gzDa);
			var barCent= new Array();
		    for ( var i = 0; i < barName.length; i++) {
		    	barCent.push({value:barDa[i], name:barName[i]});
			}
			//legend=data[data.length-1].legend;
			for ( var i = 1; i < data.length; i++) {
				da.push(data[i].data);
			}
			
			var stuSum = echarts.init(document.getElementById("stuSum"),'macarons');
			var stuSumOption = {
				    
				    tooltip : {
				        trigger: 'axis',
				        borderRadius :5
				    },
				    legend: {
				        data:legend
				    },
				    toolbox: {
				    	orient: 'vertical',
						y: 'center',
				        show : true,
				        feature : {
				            mark : {show: false},
				            dataView : {show: false, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true,name: '学生数量按年级统计'}
				        }
				    },
				    calculable : false,
				    grid: {
				    	borderWidth: 0,
				    },
				    xAxis : [
				        {
				            type : 'category',
				            axisLabel : {
				                  rotate: 45
				              },
				            splitLine: {
					            show:false
					        },
				            data : xname
				        }
				    ],
				    yAxis : [
				        {
				        	show: false,
				            type : 'value',
				            splitLine: {
						        show:false
						    },
				        }
				    ],
				    series :  [
				    {
				    	name:legend[0],
				    	type:'bar',
				    	itemStyle: {
						    normal: {
						        color: function(params) {
						            var colorList = [
											'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
											'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
											'#26C0C0','#C6E579','#D7504B','#F4E001','#F0805A'
						            ];
						            return colorList[params.dataIndex];
						        },
						        barBorderRadius:5,
			        			label : {
			        				show: true, 
			        				formatter:'{c}',
			        				position: 'top'
			        			}
						    }
						},
				    	data:da
				    },
				    {
				        name:'人数',
				        type:'pie',
				        tooltip : {
				            trigger: 'item',
				            formatter: '{b} <br/> {a} : {c}({d}%)'
				        },
				        center: ["75%","20%"],
				        radius : [0, 30],
				        itemStyle :　{
				            normal : {
				            	label : {
			                        show : true,
			                        position : 'outer',
			                        formatter :  "{b} \n {c}"
			                    },
				                labelLine : {
				                    length : 20
				                }
				            }
				        },
				        data:barCent
				    }
				  ]
				};
			
			$("#stuSum-code").resize(function() { 
				stuSum.resize(); //使第一个图表适应
			});
			stuSum.setOption(stuSumOption);
		},
		error : function() {
	
		}
	});
}
//学生户籍
function stuHouse(){
	var stuHouse = echarts.init(document.getElementById("stuHouse"),'macarons');
	stuHouse.clear();
	stuHouse.showLoading({text: '正在努力的读取数据中...'});
	
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		data : {
			type : "studentHouse"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			stuHouse.setOption(option);
			stuHouse.hideLoading();
		},
		error : function(data) {
	
		}
	});
};

//学校汇总
function shcoolSummary(){
	var shcoolSummary = echarts.init(document.getElementById('shcoolSummary'),'macarons');
	shcoolSummary.clear();
	shcoolSummary.showLoading({text: '正在努力的读取数据中...'});
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		data : {
			type : "gradeShcoolSummary"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			shcoolSummary.setOption(option);
			shcoolSummary.hideLoading();
		},
		error : function() {
	
		}
	});
	$("#shcoolSummary-code").resize(function() { 
		shcoolSummary.resize(); //使第一个图表适应
	});
};

//管理数据详细
function echartsBar(){
	var echartsBar = echarts.init(document.getElementById('echartsBar'),'macarons');
	echartsBar.clear();
	echartsBar.showLoading({text: '正在努力的读取数据中...'});
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		data : {
			type : "gradeEchartsBar"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			echartsBar.setOption(option);
			echartsBar.hideLoading();
		},
		error : function() {
	
		}
	});
	$("#barwidth").resize(function() { 
		echartsBar.resize(); //使第一个图表适应
	}); 
}
//教师职称
function teacherTitle(){
	var teacherTitle = echarts.init(document.getElementById("teacherTitle"),'macarons');
	teacherTitle.clear();
	teacherTitle.showLoading({text: '正在努力的读取数据中...'});
	
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		dataType : "JSON",
		async : false, 
		data : {
			type : "teacherTitle"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			teacherTitle.setOption(option);
			teacherTitle.hideLoading();
		},
		error : function() {
	
		}
	});
}
//教研活动学科统计
function subject(){
	var subject = echarts.init(document.getElementById("subject"),'macarons');
	subject.clear();
	subject.showLoading({text: '正在努力的读取数据中...'});
	
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		dataType : "JSON",
		async : false, 
		data : {
			type : "activeSubject"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			subject.setOption(option);
			subject.hideLoading();
		},
		error : function() {
	
		}
	});
};
//教研活动次数统计
function active(){
	var active = echarts.init(document.getElementById("active"),'macarons');
	active.clear();
	active.showLoading({text: '正在努力的读取数据中...'});
	
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		dataType : "JSON",
		async : false, 
		data : {
			type : "activeNumber"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			active.setOption(option);
			active.hideLoading();
		},
		error : function() {
	
		}
	});
};
//教师变动统计
function teacherChangeDataTotal(){
	var teacherChangeDataTotal = echarts.init(document.getElementById("teacherChangeDataTotal"),'macarons');
	teacherChangeDataTotal.clear();
	teacherChangeDataTotal.showLoading({text: '正在努力的读取数据中...'});
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		data : {
			type : "teacherChangeDataNum"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			teacherChangeDataTotal.setOption(option);
			teacherChangeDataTotal.hideLoading();
		},
		error : function() {
	
		}
	});
	
	$("#teacherChange").resize(function() { 
		teacherChangeDataTotal.resize(); //使第一个图表适应
	});
};

//非编教师变动统计
function teacherUnFormationTotal(){
	var teacherUnFormationTotal = echarts.init(document.getElementById("teacherUnFormationTotal"),'macarons');
	teacherUnFormationTotal.clear();
	teacherUnFormationTotal.showLoading({text: '正在努力的读取数据中...'});
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		data : {
			type : "teacherUnFormationNum"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			teacherUnFormationTotal.setOption(option);
			teacherUnFormationTotal.hideLoading();
		},
		error : function() {
	
		}
	});
	$("#teacherUnFormation").resize(function() { 
		teacherUnFormationTotal.resize(); //使第一个图表适应
	});
};
//教师政治面貌
function teachLandscape(){
	var teachLandscape = echarts.init(document.getElementById("teachLandscape"),'macarons');
	teachLandscape.clear();
	teachLandscape.showLoading({text: '正在努力的读取数据中...'});
	
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		dataType : "JSON",
		async : false, 
		data : {
			type : "teacherPoliticsStatus"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			teachLandscape.setOption(option);
			teachLandscape.hideLoading();
		},
		error : function() {
	
		}
	});
}

//教师政治面貌
function code(){
	var code = echarts.init(document.getElementById("code"),'macarons');
	code.clear();
	code.showLoading({text: '正在努力的读取数据中...'});
	
	$.ajax({
		type : "POST",
		url : "indexReport.do",
		dataType : "JSON",
		async : false, 
		data : {
			type : "queryList"
		},
		success : function(data) {
			eval("var option = "+data[0].option);
			code.setOption(option);
			code.hideLoading();
		},
		error : function() {
	
		}
	});
}

*/


