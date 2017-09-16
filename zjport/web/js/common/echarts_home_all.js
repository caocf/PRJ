function rainbow_line(id,pid,xname,da) {
	var stuSum = echarts.init(document.getElementById(id));
	var res = new Array();
	for(var j= 0; j < xname.length; j++){
		res.push({xAxis:j,y: da[j],name:xname[j],symbolSize:20});
	}
	var stuSumOption = {
		    title: {
		        x: 'center',
		        text: '',
		        link: ''
		    },
		    tooltip: {
		        trigger: 'item'
		    },
		    toolbox: {
		        show: true,
		        feature: {
		            dataView: {show: false, readOnly: false},
		            restore: {show: true},
		            saveAsImage: {show: true}
		        }
		    },
		    calculable: true,
		    grid: {
		        borderWidth: 0,
		        y: 80,
		        y2: 60
		    },
		    xAxis: [
		        {
		            type: 'category',
		            axisLabel:{
			        	rotate: 45
		            },
		            data:xname
		        },
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            show: true
		        }
		    ],
		    series: [
		        {
		            name: '各年级学生数量',
		            type: 'bar',
		            itemStyle: {
		                normal: {
		                    color: function(params) {
		                        // build a color map as your need.
		                        var colorList = [
		                          '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
		                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
		                           '#D7504B','#C6E579'
		                        ];
		                        return colorList[params.dataIndex];
		                    },
		                    label: {
		                        show: true,
		                        position: 'top',
		                        formatter: '{b}\n{c}'
		                    }
		                }
		            },
		            data: da,
		            markPoint: {
		                tooltip: {
		                    trigger: 'item',
		                    backgroundColor: 'rgba(0,0,0,0)',
		                    formatter: function(params){
		                        return '<img src="' 
		                                + params.data.symbol.replace('image://', '')
		                                + '"/>';
		                    }
		                },
		                data: res
		            }
		        }
		    ]
		};
	$("#"+pid).resize(function() { 
		stuSum.resize(); //使第一个图表适应
	});
	stuSum.setOption(stuSumOption);
}

//学校汇总
function shcoolSummary_set(id,pid,yname,da0,da1){
	var shcoolSummary = echarts.init(document.getElementById(id)); 
	var shcoolSummaryOption = {
		tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        },
	        formatter:function (params,ticket,callback) {
                var res = '<strong>'+params[0][1]+'</strong><br/>';
                for(var i=0;i<params.length;i++){
                	 res+=params[i][0]+':'+Math.abs(parseInt(params[i][2]))+'<br/>';
                }
                return res;
           }
	    },
	    legend: {
	        data:['民办'/*, '民办:教师', '民办:学生',''*/,'公办'/*, '公办:教师', '公办:学生'*/]
	    },
	    toolbox: {
	        show : true,
	        orient: 'vertical',
			y: 'center',
	        feature : {
	            dataView : {show: false, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true,name: '学校分类汇总',}
	        }
	    },
	    grid: {
	        borderWidth: 0
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'value',
	            splitLine : {show : false},
	            axisLabel: {
	            	show: false,
					formatter : function(params) {
						return Math.abs(params);
					},
	            },
	        }
	    ],
	    yAxis : [
	        {
	            splitNumber: 5,
	            splitLine : {show : false},
	            type : 'category',
	            axisTick : {show: false},
	            data : yname,
	        }
	    ],
	    series : [
	              
	        {
	            name:'公办',
	            type:'bar',
	            stack: '学校',
	            itemStyle: {normal: {color:'rgba(181,195,52,1)', barBorderRadius:5,label:{show:true, position: 'right',textStyle:{color:'#27727B'}}}},
	            data:da0
	        },
	        /*{
	            name:'公办:教师',
	            type:'bar',
	            stack: '公办',
	            itemStyle: {normal: {color:'rgba(181,195,52,0.6)', label:{show:true, position: 'inside',textStyle:{color:'#27727B'}}}},
	            data:[852, 1338, 1259, 558, 282]
	        },
	        {
	            name:'公办:学生',
	            type:'bar',
	            stack: '公办',
	            itemStyle: {normal: {color:'rgba(181,195,52,0.3)', label:{show:true, position: 'inside',textStyle:{color:'#27727B'}}}},
	            data:[682,1601, 1982, 802,66]
	        },*/
	        {
	            name:'民办',
	            type:'bar',
	            stack: '学校',
	            itemStyle: {normal: {color:'rgba(252,206,16,1)', barBorderRadius:5, label:{show:true, position:'left',formatter:function(params){ return Math.abs(parseInt(params.data));},textStyle:{color:'#E87C25'}}}},
	            data:da1
	        },
	        /*{
	            name:'民办:教师',
	            type:'bar',
	            stack: '民办',
	            itemStyle: {normal: {color:'rgba(252,206,16,0.6)', label:{show:true, position: 'inside',textStyle:{color:'#E87C25'}}}},
	            data:[7, 96, 41, 43, 0]
	        },
	        {
	            name:'民办:学生',
	            type:'bar',
	            stack: '民办',
	            itemStyle: {normal: {color:'rgba(252,206,16,0.3)', label:{show:true, position: 'inside',textStyle:{color:'#E87C25'}}}},
	            data:[861,1683, 733, 790,0]
	        }*/
	    ]
	};
	$("#"+pid).resize(function() { 
		shcoolSummary.resize(); //使第一个图表适应
	});
	shcoolSummary.setOption(shcoolSummaryOption);
}


//管理数据详细 不包括当前月的6个月，后保、会议、请示报告、通知
function echartsBar_set(id,pid,xname,da0,da1,da2,da3,da4,da5,legend){
	var echartsBar = echarts.init(document.getElementById(id));
	var baroption = {
		    title : {
		        text: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		    	x:'left',
		        data:legend
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: false, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    grid: {
		        borderWidth: 0
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            splitLine : {show : false},
		            axisLabel:{
			        	rotate: 45
		            },
		            data : xname
		        }
		    ],
		    yAxis : [
		        {
		        	show: false,
		        	splitLine : {show : false},
		            type : 'value'
		        }
		    ],
	    series: [
	        {
	            name: legend[0],
	            type: 'bar',
	            itemStyle: {
				    normal: {
				        barBorderRadius:5,
	        			label : {
	        				show: true, 
	        				formatter:'{c}',
	        				position: 'top'
	        			}
				    }
				},
	            data: da0
	        },
	        {
	            name: legend[1],
	            type: 'bar',
	            itemStyle: {
				    normal: {
				        barBorderRadius:5,
	        			label : {
	        				show: true, 
	        				formatter:'{c}',
	        				position: 'top'
	        			}
				    }
				},
	            data: da1
	        },
	        {
	            name: legend[2],
	            type: 'bar',
	            itemStyle: {
				    normal: {
				        barBorderRadius:5,
	        			label : {
	        				show: true, 
	        				formatter:'{c}',
	        				position: 'top'
	        			}
				    }
				},
	            data: da2
	        },
	        {
	            name: legend[3],
	            type: 'bar',
	            itemStyle: {
				    normal: {
				        barBorderRadius:5,
	        			label : {
	        				show: true, 
	        				formatter:'{c}',
	        				position: 'top'
	        			}
				    }
				},
	            data: da3
	        },
	        {
	            name: legend[4],
	            type: 'bar',
	            itemStyle: {
				    normal: {
				        barBorderRadius:5,
	        			label : {
	        				show: true, 
	        				formatter:'{c}',
	        				position: 'top'
	        			}
				    }
				},
	            data: da4
	        },
	        {
	            name: legend[5],
	            type: 'bar',
	            itemStyle: {
				    normal: {
				        barBorderRadius:5,
	        			label : {
	        				show: true, 
	        				formatter:'{c}',
	        				position: 'top'
	        			}
				    }
				},
	            data: da5
	        }
	    ]
	};
		
		$("#"+pid).resize(function() { 
			echartsBar.resize(); //使第一个图表适应
		}); 
		echartsBar.setOption(baroption);
}
//学生户籍报表
function rainbow_stuHouse(id,pid,xname,da) {
		var stuHouse = echarts.init(document.getElementById(id));
		var res = new Array();
		for(var j= 0; j < 5; j++){
			res.push({value:da[j],name:xname[j]});
		}
		var optionStuHouse = {
		    title : {
		        text: '',
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c}  ({d}%)"
		    },
		    legend: {
		        x : 'center',
		        y : 'bottom',
		        data:xname
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: false, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel']
		            },
		            restore : {show: true},
		            saveAsImage : {show: true,name: '学生户籍',}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'半径模式',
		            type:'pie',
		            radius : [20, 110],
		            center : ['50%', 200],
		            roseType : 'radius',
		            width: '70%',       // for funnel
		            max: 50,            // for funnel
		            itemStyle : {
		                normal : {
		                    label : {
		                        show : true,
		                        position : 'outer',
		                        formatter :  "{b} \n {c}人"
		                    },
		                    labelLine : {
		                        show : true
		                    }
		                },
		                emphasis : {
		                    label : {
		                        show : false
		                    },
		                    labelLine : {
		                        show : false
		                    }
		                }
		            },
		            data:res
		        }
		    ]
		};
		$("#"+pid).resize(function() { 
			stuHouse.resize(); //使第一个图表适应
		});
		stuHouse.setOption(optionStuHouse);
	}
//教师职称
function rainbow_title(id,pid,xname,da) {
	var teacherTitle = echarts.init(document.getElementById(id));
	var teacherTitleOption = {
		    title : {
		        text: '',
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis',
		        borderRadius :5
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: false, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true,name: '教师职称',}
		        }
		    },
		    grid: {
		        borderWidth: 0
		    },
		    calculable: true,
		    xAxis : [
		        {
		            type : 'value',
		            splitLine : {show : false},
		            boundaryGap : [0, 0.01]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            splitLine : {show : false},
		            axisLabel:{
			        	rotate: 45
		            },
		            data : xname
		        }
		    ],
		    series : [
		        {
		            name:'人数',
		            type:'bar',
		            itemStyle: {
					    normal: {
					        color: function(params) {
					            var colorList = [
										'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
										'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
										'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
					            ];
					            return colorList[params.dataIndex];
					        },
					        barBorderRadius:5,
		        			label : {
		        				show: true, 
		        				formatter:'{c}人',
		        				position: 'right'
		        			}
					    }
					},
		            data:da
		        }
		    ]
		};
	$("#"+pid).resize(function() { 
		teacherTitle.resize(); //使第一个图表适应
	});
	teacherTitle.setOption(teacherTitleOption);
}

//教师变动
function teacher_change(id,pid,xname,da,daFB,daTX){
		var teacherChangeDataTotal = echarts.init(document.getElementById(id));
		var teacherChangeDataTotalOption = {
				title: {
			        sublink: ''
			    },
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        },
			        formatter: function (params) {
			            var tar;
			            if (params[1].value != '-') {
			                tar = params[1];
			            }
			            else {
			                tar = params[0];
			            }
			            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
			        }
			    },
			    legend: {
			        data:[]
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: false},
			            dataView : {show: false, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    xAxis : [
			        {
			            type : 'category',
			            axisLabel:{
				        	rotate: 25
			            },
			            data : xname
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        
			        {
			            type:'bar',
			            stack: '总量',
			            itemStyle : { normal: {
			            	color: function(params) {
					            var colorList = [
										'#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
										'#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
										'#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700',
										'#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0' ,
										'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
										'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
										'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
					            ];
					            return colorList[params.dataIndex];
					        },
					        barBorderRadius:5,
		        			label : {
		        				show: true, 
		        				formatter:'{c}\n人',
		        				position: 'top'
		        			}
			        }},
			            data:da
			        }
			    ]
			};
		$("#"+pid).resize(function() { 
			 teacherChangeDataTotal.resize(); //使第一个图表适应
			});
		 teacherChangeDataTotal.setOption(teacherChangeDataTotalOption);
}
//教研活动学科统计
function rainbow_subject(id,pid,xname,da){
	  var subject = echarts.init(document.getElementById(id));
	  var subjectOption = {
			    title : {
			        text: '',
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    toolbox: {
			        show : true,
			        padding:[5,50,5,5],
			        feature : {
			            mark : {show: false},
			            dataView : {show: false, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    grid:{
			    	x: '10%',
			    	x2: '10%',
			    	borderWidth: 0
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            splitLine : {show : false},
			            axisLabel:{
				        	rotate: 45
			            },
			            data : xname
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            splitLine : {show : false},
			            max:50
			        }
			    ],
			    series : [
			        {
			            name:'科目',
			            type:'bar',
			            itemStyle: {
						    normal: {
						        color: function(params) {
						            var colorList = [
											'#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
											'#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
											'#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700',
											'#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0' ,
											'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
											'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
											'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
						            ];
						            return colorList[params.dataIndex];
						        },
						        barBorderRadius:5,
			        			label : {
			        				show: true, 
			        				formatter:'{c}\n次',
			        				position: 'top'
			        			}
						    }
						},
			            data:da
			        }
			    ]
			};
	  $("#"+pid).resize(function() { 
		  subject.resize(); //使第一个图表适应
		});
	  subject.setOption(subjectOption);
}


//教研活动次数统计
function rainbow_active(id,pid,xname,da){
	  var active = echarts.init(document.getElementById(id));
	  var activeOption = {
			    title : {
			        text: '',
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: false},
			            dataView : {show: false, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    grid:{borderWidth: 0},
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            splitLine : {show : false},
			            boundaryGap : false,
			            axisLabel:{
				        	rotate: 45
			            },
			            data : xname
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            splitLine : {show : false},
			            axisLabel : {
			                formatter: '{value}次'
			            },
			            max:150
			        }
			    ],
			    series : [
			        {
			            name:'教研活动次数',
			            type:'line',
			            itemStyle: {
						    normal: {
			        			label : {
			        				show: true, 
			        				formatter:'{c}次',
			        				position: 'top'
			        			},
			        			areaStyle: {
			        				type: 'default'
			        			}
						    }
						},
			            data:da
			        }
			    ]
			};
	  $("#"+pid).resize(function() { 
		  active.resize(); //使第一个图表适应
		});
	  active.setOption(activeOption);
}

function teacher_unformation(id,pid,xname,da){
	var teacherUnFormationTotal = echarts.init(document.getElementById(id));
	 var colorList = [
                      '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                       '#FE8463','#9BCA63','#FAD860','#F3A43B'
                    ];
	var teacherUnFormationOption = {
		    title: {
		        text: '',
		       
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        formatter: function (params) {
		            var tar = params[0];
		            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
		        }
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    xAxis : [
		        {
		            type : 'category',
		            splitLine: {show:false},
		            axisLabel:{
			        	rotate: 45
		            },
		            data : xname
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        
		        {
		            type:'bar',
		            stack: '总量',
		            itemStyle : { normal: {
		            	color: function(params) {
				            var colorList = [
									'#F0805A', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
									'#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
									'#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700',
									'#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0' ,
									'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
									'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
									'#D7504B','#C6E579','#F4E001','#ff7f50','#26C0C0'
				            ];
				            return colorList[params.dataIndex];
				        },
				        barBorderRadius:5,
	        			label : {
	        				show: true, 
	        				formatter:'{c}\n人',
	        				position: 'top'
	        			}
		            	
		        }},
		            data:da
		        }
		    ]
		};
	$("#"+pid).resize(function() { 
		teacherUnFormationTotal.resize(); //使第一个图表适应
		});
	teacherUnFormationTotal.setOption(teacherUnFormationOption);
}

//教师政治面貌
function teacher_teachLandscape(id,pid,xname,da){
	var teachLandscape = echarts.init(document.getElementById(id));
	var res = new Array();
	for(var j= 0; j < da.length; j++){
		res.push({value:da[j],name:xname[j]});
	}
	var teachLandscapeOption = {
		    title : {
		        text: '',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:xname
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: false, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: da[0]
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'政治面貌',
		            type:'pie',
		            itemStyle: {
					    normal: {
					        color: function(params) {
					            var colorList = [
					              'red'
					            ];
					            return colorList[params.dataIndex];
					        },
					        label:{
					        	show : true,
		                        position : 'outer',
		                        formatter :  "{b} \n {c}人"
		                    },
		                    labelLine : {
		                        show : true,
		                        length:5
		                    }
		                },
		                emphasis : {
		                    label : {
		                        show : false
		                    },
		                    labelLine : {
		                        show : false
		                    }
		                }
					},
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:res
		        }
		    ]
		};
	$("#"+pid).resize(function() { 
		teachLandscape.resize(); //使第一个图表适应
	});
	teachLandscape.setOption(teachLandscapeOption);
}









