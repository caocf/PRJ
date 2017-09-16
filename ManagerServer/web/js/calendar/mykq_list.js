var kqtype=0;//列表类别
$(document).ready(function(){
    $("#calendar_li").addClass("active");
    $("#mykq_li").addClass("active");
    showTabledata('LeaveAndOvertimeApply',1);
})
/**
 * 我的考勤列表列表
 * @param actionName：接口名
 * @param page：页码
 */
function showTabledata(actionName,page){
    var datastr={};
    datastr['userid']=$("#userid").val();
    datastr['page']=parseInt(page)-1;
    if(kqtype!=0){
        datastr['type']=kqtype;
    }
    datastr['key']=$("#listkey").val();
    $.ajax({
        url : actionName,
        type : "post",
        dataType : "json",
        data : datastr,
        success : function(data) {
            $("#kqtable").empty();
            $("#kqtable").append(
                "<tr style='background-color: rgb(240,245,248);'>"+
                "<th>序号</th>"+
                "<th>类别</th>"+
                "<th>时长</th>"+
                "<th>申请时间</th>"+
                "<th>事由</th>"+
                "<th>状态</th>"+
                "<th>操作</th>"+
                "</tr>"
            );
            $("#pagedetal").empty();
            $("#pagedetal").text(
                "当前页"+data.obj.length+"条记录 共"+data.resultdesc+"条记录，每页10条"
            );
            pagingmake(actionName,'showTabledata',page,data.resultcode);
            if(data.obj.length==0){
                TableIsNull();
            }else{
                for(var i=0;i<data.obj.length;i++){
                    var ztstr='';
                    var clickwordstr='';
                                var href;
                                if(data.obj[i][2].leaveOrOtKind.kindType==1){
                                    href=$("#basePath").val()+"kqsp_add?id="+data.obj[i][2].leaveOrOtID;
                                }
                                if(data.obj[i][2].leaveOrOtKind.kindType==2){
                                    href=$("#basePath").val()+"jbsp_add?id="+data.obj[i][2].leaveOrOtID;
                                }
                                if(data.obj[i][2].leaveOrOtKind.kindType==3){
                                    href=$("#basePath").val()+"ccsp_add?id="+data.obj[i][2].leaveOrOtID;
                                }
                    if(data.obj[i][2].approvalResult3==1){
                        switch (data.obj[i][2].approvalResult){
                            case '待审批' :
                                ztstr="<span style='color: rgb(241,92,12)'>待审批</span>";
                                clickwordstr="<span class='clickword' onclick=\"window.location.href='"+$("#basePath").val()+"kqsp_detal?type="+data.obj[i][2].leaveOrOtKind.kindType+"&id="+data.obj[i][2].leaveOrOtID+"'\">查看</span>" ;
                                   /* "&nbsp;&nbsp;<span class='clickword' onclick=chehui('"+data.obj[i][2].leaveOrOtID+"')>撤回</span>";*/
                                break;
                            case '准许' :
                                ztstr="<span style='color: rgb(81,191,104)'>批准</span>";
                                clickwordstr="<span class='clickword' onclick=\"window.location.href='"+$("#basePath").val()+"kqsp_detal?type="+data.obj[i][2].leaveOrOtKind.kindType+"&id="+data.obj[i][2].leaveOrOtID+"'\">查看</span>";
                                break;
                            case '驳回' :
                                ztstr="<span style='color: red'>驳回</span>";
                                clickwordstr="<span class='clickword' onclick=\"window.location.href='"+$("#basePath").val()+"kqsp_detal?type="+data.obj[i][2].leaveOrOtKind.kindType+"&id="+data.obj[i][2].leaveOrOtID+"'\">查看</span>";
                                    /*"&nbsp;&nbsp;<span class='clickword' onclick=\"window.location.href='"+href+"'\">重新提交</span>";*/
                                break;
                            default :
                                break;
                        }
                    }else{
                                ztstr="<span>已撤回</span>";
                                clickwordstr="<span class='clickword' onclick=\"window.location.href='"+$("#basePath").val()+"kqsp_detal?type="+data.obj[i][2].leaveOrOtKind.kindType+"&id="+data.obj[i][2].leaveOrOtID+"'\">查看</span>" +
                                    "&nbsp;&nbsp;<span class='clickword' onclick=del('"+data.obj[i][2].leaveOrOtID+"')>删除</span>"+
                                    "&nbsp;&nbsp;<span class='clickword' onclick=\"window.location.href='"+href+"'\">修改</span>";
                    }
                    $("#kqtable").append(
                        "<tr>"+
                        "<td>"+(i+1)+(page-1)*10+"</td>"+
                        "<td>"+data.obj[i][2].leaveOrOtKind.kindName+"</td>"+
                        "<td>"+data.obj[i][2].lastDate+"</td>"+
                        "<td>"+data.obj[i][2].leaveOrOtDate+"</td>"+
                        "<td>"+data.obj[i][2].leaveOrOtReason+"</td>"+
                        "<td>"+ztstr+"</td>"+
                        "<td>"+clickwordstr+"</td>"+
                        "</tr>"
                    )
                }
            }
        }
    })

}
/**
 * 假单撤回
 * @param id：假单id
 */
function chehui(id){
    if(confirm('确定撤回？')){
        $.ajax({
            url : 'LeavePullBack',
            type : "post",
            dataType : "json",
            data : {
                'id':id
            },
            success : function(data) {
                alert('撤回成功');
                showTabledata('LeaveAndOvertimeApply',1);
            }
            })
    }else{
        return
    }

}
/**
 * 删除假单
 * @param id：假单id
 */
function del(id){
    if(confirm('确定删除？')){
        $.ajax({
            url : 'DeleteLeave',
            type : "post",
            dataType : "json",
            data : {
                'id':id
            },
            success : function(data) {
                alert('删除成功');
                showTabledata('LeaveAndOvertimeApply',1);
            }
        })
    }else{
        return
    }
}
/**
 *列表下拉列表选择
 * @param type:类别：0：全部，1：请假，2：加班，3：出差
 */
function setlbtype(type){
    kqtype=type;
    switch (parseInt(type)){
        case 0 :
            $("#lbnamespan").text('全部');
            break;
        case 1 :
            $("#lbnamespan").text('请假');
            break;
        case 2 :
            $("#lbnamespan").text('加班');
            break;
        case 3 :
            $("#lbnamespan").text('出差');
            break;
        default :
            break;
    }
}