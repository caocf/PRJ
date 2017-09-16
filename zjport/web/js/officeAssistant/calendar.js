
$(document).ready(function() {
    $("#officeAssistant").addClass("active");
    $("#calendar_li").addClass("active");

    $('#data').fullCalendar({
        header: {
            left: 'agendaDay,agendaWeek,month',
            center: 'title',
            right: 'prev,next'
        },
        //defaultDate: '2016-06-12',
        lang: 'zh-cn',
        firstDay:0,
        buttonIcons: false, // show the prev/next text
        eventLimit: true, // allow "more" link when too many events
        buttonText:{
            prev: '‹', // ‹
            next: '›' // ›
        },
        eventClick: function(event) {
            window.location.href=$("#basePath").val() + "officeAssistant/calendarView?id="+event.id;
        },
        dayClick: function(date, allDay, jsEvent, view) {
            window.location.href=$("#basePath").val() + "officeAssistant/calendar_add.jsp";
        },
        events: /*[
            {
                id:1,
                title: '蕾姆',
                start: '2016-09-07'
            },
            {
                title: 'Long Event',
                start: '2016-09-07',
                end: '2016-09-10'
            },
            {
                id: 999,
                title: 'Repeating Event',
                start: '2016-09-09T16:00:00'
            },
            {
                id: 999,
                title: 'Repeating Event',
                start: '2016-09-16T16:00:00'
            },
            {
                title: 'Conference',
                start: '2016-09-11',
                end: '2016-09-13'
            },
            {
                title: 'Meeting',
                start: '2016-09-12T10:30:00',
                end: '2016-09-12T12:30:00'
            },
            {
                title: 'Lunch',
                start: '2016-09-12T12:00:00'
            },
            {
                title: 'Meeting',
                start: '2016-09-12T14:30:00'
            },
            {
                title: 'Happy Hour',
                start: '2016-09-12T17:30:00'
            },
            {
                title: 'Dinner',
                start: '2016-09-12T20:00:00'
            },
            {
                title: 'Birthday Party',
                start: '2016-09-13T07:00:00'
            },
            {
                title: 'Click for Google',
                url: 'http://google.com/',
                start: '2016-09-28'
            }
        ]*/'showCalendar'
    });
    $('.fc-right').append(
        "<button class='btn btn-primary' onclick=\"window.location.href='"+$("#basePath").val()+"officeAssistant/calendarAdd'\">新建日程</button>"
    )

    /*$(".fc-sat").css('backgroundColor','#FFC0CB');//这个是周六的TD
    $(".fc-sun").css('backgroundColor','#FFC0CB');//这个是周日的TD*/
});
Date.prototype.format = function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
        format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}