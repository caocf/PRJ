$(document).ready(function () {
    $("#calendar_li").addClass("active");
    $("#cq_li").addClass("active");
    $("#timetype").children('div').click(function () {
        $("#timetype").children('div').css('background-color', 'rgb(250,250,250)');
        $(this).css('background-color', 'rgb(231,231,231)');
    });
    getbm();
})
//获取天数统计
function showcqdata() {
    if ($("#beginTime").val() == '' || $("#beginTime").val() == null) {
        alert('请选择开始时间');
        return
    }
    if ($("#endTime").val() == '' || $("#endTime").val() == null) {
        alert('请选择结束时间');
        return
    }
    $.ajax({
        url: 'CountByID',
        type: "post",
        dataType: "json",
        data: {
            'id': bmid,
            'date1': $("#beginTime").val() + ' 00:00',
            'date2': $("#endTime").val() + ' 23:59',
            'type': 1
        },
        beforeSend: function(){
            showwaitdiv();
        },
        success: function (data) {
            setcqdata(data);
        }
    })
}

//添加出勤统计
function setcqdata(datalist) {
    $.ajax({
        url: 'getRealTime',
        type: "post",
        dataType: "json",
        data: {
            'begin': $("#beginTime").val() + ' 00:00',
            'end': $("#endTime").val() + ' 23:59'
        },
        success: function (data) {
            var leave = Math.ceil(datalist.obj.leave / 8);
            var work = Math.ceil(data.resultcode / 8);
            $("#zqjts").text(leave);
            $("#zccts").text(datalist.obj.cc);
            $("#zjbts").text(datalist.obj.jb);
            $("#zcqts").text((work - leave));
            getbmuserdata(work);
        }
    })
}
//获取部门相关人员信息
function getbmuserdata(work){
    $.ajax({
        url: 'CrewsByDepID',
        type: "post",
        dataType: "json",
        data: {
            'pid': bmid
        },
        success: function (data) {
            //console.log(JSON.stringify(data));
            $("#rolelist-info").empty();
            $("#rolelist-info").append(
                "<tr style='background-color: rgb(240,245,248);'>" +
                "<th>部门人员</th>" +
                "<th>请假天数</th>" +
                "<th>出差天数</th>" +
                "<th>加班天数</th>" +
                "<th>到岗天数</th>" +
                "<th>出勤率</th>" +
                "</tr>"
            );
            $(data.obj).each(function(index,item){
                var usertr=$("<tr></tr>");
                usertr.append(
                    "<td>"+item.xm+"</td>"
                )
                $("#rolelist-info").append(usertr);
                getusercqdata(item.id,usertr,work);
            })
            hidewaitdiv();
        }
    })
}
/**
 * 获取部门下人员出勤数据
 * @param id 人员id
 * @param usertr 人员tr
 * @param work 工作日天数
 */
function getusercqdata(id,usertr,work){
    $.ajax({
        url: 'CountByID',
        type: "post",
        dataType: "json",
        data: {
            'id': id,
            'date1': $("#beginTime").val() + ' 00:00',
            'date2': $("#endTime").val() + ' 23:59',
            'type': 2
        },
        success: function (data) {
            var leave = Math.ceil(data.obj.leave / 8);
            usertr.append(
                "<td>"+leave+"</td>"+
                "<td>"+data.obj.cc+"</td>"+
                "<td>"+data.obj.jb+"</td>"+
                "<td>"+(work - leave)+"</td>"+
                "<td>"+((work - leave)/work)*100+"%</td>"
            )
        }
    })
}
var addmltreeNodes = [];
var konwledgesetting = {
    view: {
        nameIsHTML: true,
        showLine: false,
        showIcon: false,
        selectedMulti: false,
        dblClickExpand: false,
        addDiyDom: addDiyDom
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: zTreeOnClick
    }
};
//创建部门树
function bmtreemake() {
    var treeObj = $("#bmtree");
    $.fn.zTree.init(treeObj, konwledgesetting, addmltreeNodes);
    $("#addmlul").find('a').attr('data-stopPropagation', true);
    $("#addmlul").on("click", "[data-stopPropagation]", function (e) {
        e.stopPropagation();
    });
}

//部门树样式方法
function addDiyDom(treeId, treeNode) {
    var spaceWidth = 7;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 1) {
        var spaceStr = "<span style='display: inline-block;'></span>";
        switchObj.before(spaceStr);
    }
    if (treeNode.level > 0) {
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
        switchObj.before(spaceStr);
    }
    $(".ztree").find('a').removeAttr('title');
}
var bmid;
//获取部门
function getbm() {
    $.ajax({
        url: 'DepByCrewID',
        type: "post",
        dataType: "json",
        data: {
            'crewid': $("#userid").val()
        },
        success: function (data) {
            //console.log(JSON.stringify(data));
            var node = {
                "id": data.id,
                "describe": data.zzjgmc,
                "nodename": data.zzjgmc,
                "name": "<div class='turnStructurediv' style='margin-right: 75px;' ><span ><i class='fa fa-home'></i>&nbsp;<span onclick=\"addsjml('" + data.zzjgmc + "','" + data.id + "',true)\">" + data.zzjgmc + "</span></span></div>",
                "pId": 0,
                "isfristclick": true,
                "isparent": true
            };
            $("#sjmlname").text(data.zzjgmc);
            bmid = data.id;
            $("#timetype").children('div:eq(1)').click();
            addmltreeNodes.push(node);
            bmtreemake();
        }
    })
}

//节点点击方法
function zTreeOnClick(event, treeId, treeNode) {
    if (treeNode.isparent) {
        if (treeNode.isfristclick) {
            treeNode.isfristclick = false;
            $.ajax({
                url: "ItemsByPid",
                dataType: "json",
                data: {
                    'pid': treeNode.id
                },
                type: "post",
                success: function (data) {
                    var treeObj = $.fn.zTree.getZTreeObj("bmtree");
                    $(data.obj).each(function (index, item) {
                        var imgstr = "<i class='fa fa-group'></i>";
                        var isparent = false;
                        if (item.zzjglb == 1) {
                            imgstr = "<i class='fa fa-home'></i>";
                            isparent = true;
                        }
                        var node = {
                            "id": item.id,
                            "describe": item.zzjgmc,
                            "nodename": item.zzjgmc,
                            "name": "<div class='turnStructurediv' style='margin-right: 75px;' ><span>" + imgstr + "&nbsp;<span onclick=\"addsjml('" + item.zzjgmc + "','" + item.id + "'," + isparent + ")\">" + item.zzjgmc + "</span></span></div>",
                            "isfristclick": true,
                            "isparent": isparent
                        };
                        treeObj.addNodes(treeNode, node);
                    })
                    $("#addmlul").find('a').attr('data-stopPropagation', true);
                    $("#addmlul").on("click", "[data-stopPropagation]", function (e) {
                        e.stopPropagation();
                    });
                },
                error: function (xOption, status) {
                }
            });
        }
    }
}

//添加上级目录
function addsjml(name, id, isparent) {
    if (isparent) {
        return
    }
    $("#sjmlname").text(name);
    $("#addmlul").dropdown('toggle');
    bmid = id;
}

/**
 * 设置时间段
 * @param type：时间段类型1：本周 ，2：上周，3：本月 ，4：上月
 */
function settimetype(type) {
    switch (parseInt(type)) {
        case 1:
            $("#beginTime").val(getWeekStartDate);
            $("#endTime").val(getWeekEndDate);
            break;
        case 2:
            $("#beginTime").val(getUpWeekStartDate);
            $("#endTime").val(getUpWeekEndDate);
            break;
        case 3:
            $("#beginTime").val(getMonthStartDate);
            $("#endTime").val(getMonthEndDate);
            break;
        case 4:
            $("#beginTime").val(getLastMonthStartDate);
            $("#endTime").val(getLastMonthEndDate);
            break;
        default :
            break;
    }
}


//日期计算方法
var now = new Date();                    //当前日期
var nowDayOfWeek = now.getDay();         //今天本周的第几天
var Day = now.getDate() - now.getDay();
if (now.getDay() == 0)           //星期天表示 0 故当星期天的时候，获取上周开始的时候
{
    Day -= 6;
}           //当前日
var nowMonth = now.getMonth();           //当前月
var nowYear = now.getYear();             //当前年
nowYear += (nowYear < 2000) ? 1900 : 0;  //

var lastMonthDate = new Date();  //上月日期
lastMonthDate.setDate(1);
lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
var lastYear = lastMonthDate.getYear();
lastYear += (lastYear < 2000) ? 1900 : 0;  //
var lastMonth = lastMonthDate.getMonth();

//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth() + 1;
    var myweekday = date.getDate();

    if (mymonth < 10) {
        mymonth = "0" + mymonth;
    }
    if (myweekday < 10) {
        myweekday = "0" + myweekday;
    }
    return (myyear + "-" + mymonth + "-" + myweekday);
}

//获得某月的天数
function getMonthDays(myMonth) {
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
    return days;
}

//获得本季度的开始月份
function getQuarterStartMonth() {
    var quarterStartMonth = 0;
    if (nowMonth < 3) {
        quarterStartMonth = 0;
    }
    if (2 < 6) {
        quarterStartMonth = 3;
    }
    if (5 < 9) {
        quarterStartMonth = 6;
    }
    if (nowMonth > 8) {
        quarterStartMonth = 9;
    }
    return quarterStartMonth;
}
//获得本周的开始日期
var getWeekStartDate = new Date(nowYear, nowMonth, Day);
getWeekStartDate = formatDate(getWeekStartDate);
//获得本周的结束日期
var getWeekEndDate = new Date(nowYear, nowMonth, Day + 6);
getWeekEndDate = formatDate(getWeekEndDate);


//获得上周的开始日期
var getUpWeekStartDate = new Date(nowYear, nowMonth, Day - 7);
getUpWeekStartDate = formatDate(getUpWeekStartDate);

//获得上周的结束日期
var getUpWeekEndDate = new Date(nowYear, nowMonth, Day - 1);
getUpWeekEndDate = formatDate(getUpWeekEndDate);


//获得本月的开始日期
var getMonthStartDate = new Date(nowYear, nowMonth, 1);
getMonthStartDate = formatDate(getMonthStartDate);

//获得本月的结束日期
var getMonthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
getMonthEndDate = formatDate(getMonthEndDate);

//获得上月开始时间
var getLastMonthStartDate = new Date(lastYear, lastMonth, 1);
getLastMonthStartDate = formatDate(getLastMonthStartDate);

//获得上月结束时间
var getLastMonthEndDate = new Date(lastYear, lastMonth, getMonthDays(lastMonth));
getLastMonthEndDate = formatDate(getLastMonthEndDate);