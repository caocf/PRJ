var cal;
$(document).ready(function() {
	cal = new Calendar('cal');
	getRoomList();
	
});
function clearDateInfor(){
	var d=new Date();
	var dtoday=d.getDate(); 
	$(".today").html(dtoday);
	for(var j=1;j<10;j++)
		$(".day0"+j).html(j);
	for(var j=10;j<32;j++)
		$(".day"+j).html(j);
}

function getRoomList()
{
	$.ajax({
		url:"allMeetingRooms",
		type : "post",
		dataType : "json",
		data:{},
		success:function(data)
		{
			$(data.list).each(function(index,item)
			{				
				var content="<option value='"+item[0].id+"'>"+item[0].typename+"会议室"+"</option>";
				$("#rooms").append(content);
				
			});
			roomRecordInMonth();
		}
	});	
}
//初始化记录标志
function roomRecordInMonth()
{
	$('.sign').remove();
	s=$("#rooms").val();
	console.log(s);
	$.ajax( {
		url : 'meetingRoomByMonth',
		type : "post",
		dataType : "json",
		data : {
			'year' : cal.year,
			'month':cal.month+1,
			'meetingBasic.meetingroom':$("#rooms").val()
		},
		success : function(data) 
		{
			for(var i=0;i<data.list.length;i++)
			{
				var meetingdate=data.list[i].meetingdate.split("T");
				var day=meetingdate[0].split("-")[2];
				
				$("#"+day).append("<img class='sign' src='image/schedule/point_blue.png'>");   
				
				if(day==cal.day)
				{
					$(".Today").append("<img class='sign' src='image/schedule/point_white.png'>"); 
				}

			}
		}
	});

}

function onSelectChange()
{
	//s=$("#rooms").val();
	//console.log(s);
	roomRecordInMonth();
}

function showEventListByTime(type,y,m,d)
{
	$("#calendar table td").css("border-color","#dce8f2");
	$("#calendar table td").css("border-style","solid");
	$("#calendar table td").css("border-width"," 1px 1px 1px 1px");
	type.style.border="2px solid #3f9cd7";
		
	$.ajax( {
		url : 'queryApplicationRecordByDay',
		type : "post",
		dataType : "json",
		data : {
			'meetingBasic.meetingdate':y+"-"+(m+1)+"-"+d,
			'meetingBasic.meetingroom':$("#rooms").val()
		},
		success : function(data) 
		{
			console.log(data);
			$("#data").empty(); 
					
			$(data.list).each(function(index,item)
			{
				var num=index+1;
				var user=item[1].name;
				var date=item[0].meetingdate.split("T")[0];
				var status1=item[0].status;
				var status2=item[0].depstatus;
				var status="待审批";
				
				if(status1==2&&status2==2)//同意
				{
					status="同意";
				}
				if(status1==3||status2==3)
				{
					status="驳回";
				}
				var row="<tr><td>"+num+"</td>" +
						"<td>"+user+"</td>" +
						"<td>"+date+"</td>" +
								"<td>"+status+"</td></tr>";
				$("#data").append(row);
				
			});
			
		}		
		
	});
}
function commit()
{
	var t=$('#topic').val();
	if(t==""||t==null)
	{
	//alert("请输入会议主题");
		$('#tipbox').text("请输入会议主题");
		$('#myModal').modal('show');
		
	return;
	}
	
	//$('#appform').resetForm(true);
	var d=$('#date').val();
	var t1=$('#time1').val();	
	var t2=$('#time2').val();
	if(d==""||d==null)
	{
		//alert("请输入日期");
		$('#tipbox').text("请输入日期");
		$('#myModal').modal('show');
		return false;
	}
	if(t1==""||t1==null)
	{
		//alert("请输入开始时间");
		$('#tipbox').text("请输入开始时间");
		$('#myModal').modal('show');
		return;
	}
	if(t2==""||t2==null)
	{
		//alert("请输入结束时间");
		$('#tipbox').text("请输入结束时间");
		$('#myModal').modal('show');
		return;
	}
	
	time1=new Date(d.split("-")[0],d.split("-")[1],d.split("-")[2],t1.split(":")[0],t1.split(":")[1]);
	time2=new Date(d.split("-")[0],d.split("-")[1],d.split("-")[2],t2.split(":")[0],t2.split(":")[1]);
	if(time1>=time2)
	{
		alert("开始时间不能晚于结束时间");
		return;
	}
	
	$.ajax( {
		url : 'applyroom',
		type : "post",
		dataType : "json",
		data : 
		{
			'meetingBasic.applicaionor':$('#userId').val(),
			'meetingBasic.meetingroom':$("#rooms").val(),
			'meetingBasic.topic':t,
			'meetingBasic.meetingdate':d,
			'meetingBasic.meetingtime':+"-",
			'meetingBasic.tel':$('#tel').val(),
			'user.partOfDepartment':$('#depid').val(),
		},
		success : function(data) 
		{
			window.location.reload();	
		}		
		
	});
}


function showEventTable(list){
	$("#EventList .addTr").empty();
	$("#EventList .addTr").remove();
	var j=1;
	for ( var i = 0; i < list.length; i++) {
		newTr = $("<tr class='addTr'></tr>");
		newTr.append($("<td>"+j+"</td>"));
		newTr.append($("<td>"+list[i][0].eventName+"</td>"));
		newTr.append($("<td>"+list[i][3].scheduleKindName+"</td>"));
		newTr.append($("<td>"+GetShortTime(list[i][0].eventTime)+"</td>"));
		newTr.append($("<td>"+DateIsNull(list[i][0].eventLocation)+"</td>"));
		newTr.append($("<td class='fu"+list[i][0].eventId+"'></td>"));
		newTr.append($("<td class='eventId"+list[i][0].eventId+"'></td>"));
		if(list[i][1].userAgree=="0")
			newTr.append($("<td><a onclick='showSee("+list[i][0].eventId+")' class='operation'>查看</a>&nbsp;" +
							"<a onclick='showUpdate("+list[i][0].eventId+")' class='operation'>编辑</a>&nbsp;" +
							"<a onclick='deleteByEventId("+list[i][0].eventId+")' class='operation2'>删除</a></td>"));
			else
				newTr.append($("<td><a onclick='showSeeOthers("+list[i][0].eventId+")' class='operation'>查看</a></td>"));
		
		AttachmentByEventId(list[i][0].eventId);//附件
		$("#EventList").append(newTr);
		showFirstUser(list[i][0].eventId);//发起人
		j++;
	}
}














function getDayByEvent(eventTime){
	var strs= new Array(); //定义一数组
	strs=eventTime.split(" ");
	var strs2=strs[0];
	var strs3= new Array(); //定义一数组
	strs3=strs2.split("-");
	var strs4=strs3[2];
	return strs4;
}
function getDayBy(Time){
	var strs= new Array(); //定义一数组
	strs=Time.split("-");
	if(strs[1]<10)
		strs[1]="0"+strs[1];
	var strs4=strs[0]+"-"+strs[1];
	return strs4;
}

function addEventTable(list,num,linenum)
{
	newTr = $("<tr class='addTr'></tr>");
	newTr.append($("<td>"+linenum+"</td>"));
	newTr.append($("<td>"+list[num][0].eventName+"</td>"));
	newTr.append($("<td>"+list[num][3].scheduleKindName+"</td>"));
	newTr.append($("<td>"+GetShortTime(list[num][0].eventTime)+"</td>"));
	newTr.append($("<td>"+DateIsNull(list[num][0].eventLocation)+"</td>"));
	newTr.append($("<td class='fu"+list[num][0].eventId+"'></td>"));		
	newTr.append($("<td class='eventId"+list[num][0].eventId+"'>&nbsp;</td>"));
	if(list[num][1].userAgree=="0")
	newTr.append($("<td><a onclick='showSee("+list[num][0].eventId+")' class='operation'>查看</a>&nbsp;" +
					"<a onclick='showUpdate("+list[num][0].eventId+")' class='operation'>编辑</a>&nbsp;" +
					"<a onclick='deleteByEventId("+list[num][0].eventId+")'  class='operation2'>删除</a></td>"));
	else
		newTr.append($("<td><a onclick='showSeeOthers("+list[num][0].eventId+")'  class='operation'>查看</a></td>"));
	AttachmentByEventId(list[num][0].eventId);//附件
	$("#EventList").append(newTr);
	showFirstUser(list[num][0].eventId);//发起人
}

function GetShortTime(time){
	return time.substr(0,19);
}
//添加事件
function addEvent(){
	window.location.href=$("#basePath").val()+"page/officoa/schedule_add.jsp";
}
//查看页面-发起人
function showSee(eventId){
	window.location.href=$("#basePath").val()+"page/officoa/schedule_see.jsp?eventId="+eventId;
}
//查看页面-参加人
function showSeeOthers(eventId){
	window.location.href=$("#basePath").val()+"page/officoa/schedule_see_others.jsp?eventId="+eventId;
}
//编辑页面
function showUpdate(eventId){
	window.location.href=$("#basePath").val()+"page/officoa/schedule_update.jsp?eventId="+eventId;
}
function showFirstUser(id){

	$.ajax( {
		url : 'FindFirstUser',
		type : "post",
		dataType : "json",
		data : {
			'scheduleEvent.eventId':id
		},
		success : function(data) {
			$(".fu"+id).text(data.list[0]);
		}
	});


}
//附件
function AttachmentByEventId(eventId)
{
	$.ajax( {
		url : 'FindAttachmentByEventId',
		type : "post",
		dataType : "json",
		data : {
			'scheduleEvent.eventId':eventId
		},
		success : function(data) {
			var tdhtml="";
			for(var i=0;i<data.list.length;i++)
			{
				tdhtml=tdhtml+"<a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>"+data.list[i].attachmentName+"</a> ";
			}
			if(tdhtml!="")
			$(".eventId"+eventId).html(tdhtml);
			else $(".eventId"+eventId).html("无");
		}
	});	
}

function deleteByEventId(eventId){
	if(confirm("确定删除？")){
	$.ajax( {
		url : 'deleteByEventId',
		type : "post",
		dataType : "json",
		data : {
			'scheduleEvent.eventId':eventId
		},
		success : function(data) {
			alert("删除成功!");
			changEventListByTime();//刷新列表和日历中的摩天
			
		}
	});
	}
}
function changEventListByTime() 
{
	$.ajax({
				url : 'EventListByUserIdAndTime',
				type : "post",
				dataType : "json",
				data : {
					'user.userId' : $("#userId").val(),
					'scheduleEvent.eventTime' : $("#EntTime").val()
				},
				success : function(data) {
					var d = new Date();
					var dtoday = d.getDate();
					var tday = getDayByEvent($("#EntTime").val());
					var tdayhtml = tday;
					if (tday == dtoday) {
						if(data.list.length==0){
							$(".Today").html(tdayhtml);
							TableNoItem();
						}
						for ( var i = 0; i < data.list.length; i++) {
							if (i == 0)
								tdayhtml = tdayhtml
										+ "<br/><img src='image/schedule/point_white.png'>";
							else
								tdayhtml = tdayhtml
										+ "<img src='image/schedule/point_blue.png'>";
						}
						$(".Today").html(tdayhtml);
					} else {
						if(data.list.length==0){
							$("#" + tday).html(tdayhtml);
							TableNoItem();
						}
						for ( var i = 0; i < data.list.length; i++) {
							if (i == 0)
								tdayhtml = tdayhtml
										+ "<br/><img src='image/schedule/point_blue.png'>";
							else
								tdayhtml = tdayhtml
										+ "<img src='image/schedule/point_blue.png'>";
						}
						$("#" + tday).html(tdayhtml);
					}
					showEventTable(data.list);
				}
		});

}
function TableNoItem(){
	$("#EventList .addTr").empty();
	$("#EventList .addTr").remove();
}
function Calendar(name){
    this.name = name;
    this.now = new Date();
    this.year = this.now.getFullYear();
    this.month = this.now.getMonth();
    this.day = this.now.getDate();
    this.monthName = new Array('1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月');
    this.weekName = new Array('日','一','二','三','四','五','六');
    this.daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31,30, 31, 30, 31);
    this.element = document.getElementById('calendar');
    this.init();
    $("#select_day").text(this.day);
	$("#select_time").text(this.year+"年"+this.month+"月");
}
Calendar.prototype.init = function() {
    var strHtml = '';
    strHtml +='<table border="0" cellspacing="0" cellpadding="0">'+ this.createHead();
    strHtml += this.createWeek();
    strHtml += this.createDays();
    this.element.innerHTML = strHtml+'</table>';   
    //showEventList(this.year+"-"+(this.month+1));
}
Calendar.prototype.getDays = function() {
    if (1 == this.month) return ((0 == this.year % 4) && (0 != (this.year % 100))) ||(0 == this.year % 400) ? 29 : 28;
    else return this.daysInMonth[this.month];
}
Calendar.prototype.createHead = function() {
	var head='<tr><td   colspan="7"  class="head" valign="middle"><a href="javascript:' + this.name + '.changeYear(\'p\');" class="arrow">';
	head+='<img src="image/schedule/leftward_normal.png" class="u3_img" onMouseOver="LeftWardOver(this)" onMouseOut="LeftWardOut(this)"/></a>&nbsp;';
	head+= + this.year + ' 年&nbsp;<a href="javascript:' + this.name + '.changeYear(\'n\');" class="arrow">';
	head+='<img src="image/schedule/rightward_normal.png" class="u3_img" onMouseOver="RightWardOver(this)" onMouseOut="RightWardOut(this)"/></a>';
	head+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:' + this.name + '.changeMonth(\'p\');" class="arrow">';
	head+='<img src="image/schedule/leftward_normal.png" class="u3_img" onMouseOver="LeftWardOver(this)" onMouseOut="LeftWardOut(this)"/></a>&nbsp;';
	head+=this.monthName[this.month] + '&nbsp;<a href="javascript:' + this.name + '.changeMonth(\'n\');" class="arrow">';
	head+='<img src="image/schedule/rightward_normal.png" class="u3_img" onMouseOver="RightWardOver(this)" onMouseOut="RightWardOut(this)"/></a></td></tr>';
    return head;
}
Calendar.prototype.createWeek = function() {
    var strResult = '';
    for (var i = 0; i < 7; i++) {
        if (i == 0 || i == 6) strResult += '<td class="we">';
        else strResult += '<td class="ww">';
        strResult += this.weekName[i].substr(0,2) + '</td>';
    }
    strResult='<tr>'+strResult+'</tr>';
    return strResult;
}
Calendar.prototype.createDays = function() {
	var num=0;
    var strResult = '<tr>';
    var i = 0;
    var d = this.getDays();
    var n = Math.ceil(d / 7) * 7;
    var w = new Date(this.year, this.month, 1).getDay();
    for (var j = 0; j < w; j++) { //输出前空格
        i += 1;
        strResult += '<td>&nbsp; </td>';
        num++;
    }
    for (var j = 1; j <= d; j++) { //输出日期格
    	if(num%7==0&num>0)
    		strResult +='</tr><tr>';
    	num++;
        i += 1;
        if (j == this.day) {
        	if(j<10)
            strResult += '<td class="Today" id=0' + j + ' onclick=showEventListByTime(this,'+this.year+','+this.month+',"0'+j+'")>' + j + '<br /></td>';
        	else strResult += '<td class="Today" id=' + j + ' onclick=showEventListByTime(this,'+this.year+','+this.month+','+j+')>' + j + '<br /></td>';
        } else {
            var k = new Date(this.year, this.month, j).getDay();
            if (k == 0 || k == 6) {
            	if(j<10)
                strResult += '<td class="weekend" id=0' + j + ' onclick=showEventListByTime(this,'+this.year+','+this.month+',"0'+j+'")>' + j + '<br /></td>';
            	else  strResult += '<td class="weekend"  id=' + j + ' onclick=showEventListByTime(this,'+this.year+','+this.month+','+j+')>' + j + '<br /></td>';
            } else {
            	if(j<10)
                strResult += '<td id=0' + j + ' onclick=showEventListByTime(this,'+this.year+','+this.month+',"0'+j+'")>' + j + '<br /></td>';
            	else    strResult += '<td id=' + j + ' onclick=showEventListByTime(this,'+this.year+','+this.month+','+j+')>' + j + '<br /></td>';
            }
        }
    }
    for (var j = 0; j < (Math.ceil(i / 7) * 7 - i); j++) { //输出后空格
        strResult += '<td>&nbsp; </td>';
    }
    return strResult+'</tr>';
}

Calendar.prototype.changeYear = function(arg) {
    if (arg == 'p') this.year -= 1;
    if (arg == 'n') this.year += 1;
    this.init();showEventList();
}
Calendar.prototype.changeMonth = function(arg) {
    var m;
    if (arg == 'p') m = this.month - 1;
    if (arg == 'n') m = this.month + 1;
    if (arg == 'p' || arg == 'n') {
        if ( m > -1 && m < 12) {
            this.month = m;
        } else if (m < 0) {
            this.year -= 1;
            this.month = 11;
        } else if (m > 11) {
            this.year += 1;
            this.month = 0;
        }
    }
    this.init();showEventList();
}
