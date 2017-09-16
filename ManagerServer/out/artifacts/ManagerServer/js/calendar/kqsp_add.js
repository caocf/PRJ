var addtype;
var spr=-1;//审批人id
$(document).ready(function(){
    $("#calendar_li").addClass("active");
    addtype=$("#addtype").val();
    var nowdate=new Date();
    var enddate=new Date();
    enddate.setHours(enddate.getHours()+1);
    var nowtime=nowdate.getFullYear()+'-'+nowdate.getMonth()+'-'+nowdate.getDay()+' '+nowdate.getHours()+':00';
    var endtime=enddate.getFullYear()+'-'+enddate.getMonth()+'-'+enddate.getDay()+' '+enddate.getHours()+':00';
    $("#beginTime").val(nowtime);
    $("#endTime").val(endtime);
    $("#qjfootspan").append(
        "申请人："+$("#username").val()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;部门："+$("#bmname").val()
    );
    getsprlist();
    getduringtime();
    if($("#qjid").val()!=-1){
        getjddetal();
    }
})
/**
 * 获取请假时长
 */
function getduringtime(){
    var datastr={};
    datastr['begin']=$("#beginTime").val();
    datastr['end']=$("#endTime").val();
    $.ajax({
        url : 'getRealTime',
        type : "post",
        dataType : "json",
        data : datastr,
        success : function(data) {
            $('#qjNo').text(data.resultcode+'小时');
        }
    })
}
/**
 * 修改时获取假单详情
 */
function getjddetal(){
    $.ajax({
        url : 'showLeaveAndOvertimeApproval',
        type : "post",
        dataType : "json",
        data : {
            'leaveOrOtID1':$("#qjid").val()
        },
        success : function(data) {
            if(addtype==5){
                $("#ccplace").val(data.address);
            }else if(addtype!=2){
                setaddtype(data.leaveOrOtKind.kindID);
            }
            spr=data.approvalID1.id;
            $("#sprword").text(data.approvalID1.xm);
            $("#beginTime").val(data.beginDate);
            $("#endTime").val(data.endDate);
            $("#qjNo").text(data.lastDate+'小时');
            $("#syword").val(data.leaveOrOtReason);
        }
    })
}
/**
 * 设置请假下拉列表类型
 * @param type
 */
function setaddtype(type){
    addtype=type;
    switch (parseInt(type)){
        case 1 :
            $("#qjtypeword").text('事假');
            break;
        case 3 :
            $("#qjtypeword").text('病假');
            break;
        case 4 :
            $("#qjtypeword").text('婚假');
            break;
        case 6 :
            $("#qjtypeword").text('产假');
            break;
        case 7 :
            $("#qjtypeword").text('丧假');
            break;
        default :
            break;
    }
}
/**
 * 根据用户id获取审批人列表
 */
function getsprlist(){
    var datastr={};
    datastr['id']=$("#sjzg").val();
    $.ajax({
        url : 'Approvers',
        type : "post",
        dataType : "json",
        data : datastr,
        success : function(data) {
            $("#sprul").empty();
            $(data.obj).each(function(index,item){
                $("#sprul").append(
                "<li><a onclick=setspr('"+item.id+"',this)>"+item.xm+"</a></li>"
                );
            })
        }
    })
}
/**
 * 审批人下拉设置
 * @param sprid：审批人id
 * @param aevent:对应a标签对象
 */
function setspr(sprid,aevent){
    spr=sprid;
    $("#sprword").text($(aevent).text());
}
/**
 * 提交假单
 */
function addkq(){
    var actionname;
    var datastr={};
    if($("#qjid").val()!=-1){
        actionname='LeaveReCommit';
        datastr['id']=$("#qjid").val();
    }else{
        actionname='newLeaveAndOvertime';
    }
    if($("#syword").val()==''||$("#syword").val()==null){
        alert('事由不能为空');
        return
    }
    if(spr==-1){
        alert('请选择审批人');
        return
    }
    datastr['applier']=$("#userid").val();
    datastr['Reason']=$("#syword").val();
    datastr['kindName']=addtype;
    if(addtype==5){
        if($("#ccplace").val()==''||$("#ccplace").val()==null){
            alert('出差地不能为空');
            return
        }
        datastr['address']=$("#ccplace").val();
    }
    datastr['beginDate']=$("#beginTime").val();
    datastr['endDate']=$("#endTime").val();
    datastr['approveid']=spr;
    $.ajax({
        url : actionname,
        type : "post",
        dataType : "json",
        data : datastr,
        success : function(data) {
            alert('提交成功');
            window.location.href=$("#basePath").val()+'HomePage';
        }
    })
}