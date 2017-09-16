var kqtype=0;//列表类别
$(document).ready(function(){
    $("#calendar_li").addClass("active");
    $("#kqsp_li").addClass("active");
    showTabledata("LeaveAndOvertimefinish",1);
})
/**
 * 考勤审批列表
 * @param actionName：接口名
 * @param page：页码
 */
function showTabledata(actionName,page)
{
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
            console.log(JSON.stringify(data));
            $("#kqtable").empty();
            $("#kqtable").append(
                "<tr style='background-color: rgb(240,245,248);'>"+
                    "<th>序号</th>"+
                    "<th>类别</th>"+
                    "<th>申请人</th>"+
                    "<th>申请时间</th>"+
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
                    switch (data.obj[i][3].approvalResult){
                        case '待审批' :
                            ztstr="<span style='color: rgb(241,92,12)'>待审批</span>";
                            clickwordstr="<span class='clickword' onclick=\"window.location.href='"+$("#basePath").val()+"kqsp_check?type="+data.obj[i][3].leaveOrOtKind.kindType+"&id="+data.obj[i][3].leaveOrOtID+"'\">审批</span>";
                            break;
                        case '准许' :
                            ztstr="<span style='color: rgb(81,191,104)'>批准</span>";
                            clickwordstr="<span class='clickword' onclick=\"window.location.href='"+$("#basePath").val()+"kqsp_detal?type="+data.obj[i][3].leaveOrOtKind.kindType+"&id="+data.obj[i][3].leaveOrOtID+"'\">查看</span>";
                            break;
                        case '驳回' :
                            ztstr="<span style='color: red'>驳回</span>";
                            clickwordstr="<span class='clickword' onclick=\"window.location.href='"+$("#basePath").val()+"kqsp_detal?type="+data.obj[i][3].leaveOrOtKind.kindType+"&id="+data.obj[i][3].leaveOrOtID+"'\">查看</span>";
                            break;
                        default :
                            break;
                    }
                    $("#kqtable").append(
                        "<tr>"+
                            "<td>"+(i+1)+"</td>"+
                            "<td>"+data.obj[i][3].leaveOrOtKind.kindName+"</td>"+
                           /* "<td>"+data.obj[i][3].lastDate+"</td>"+*/
                            "<td>"+data.obj[i][3].user.xm+"</td>"+
                            "<td>"+data.obj[i][3].leaveOrOtDate+"</td>"+
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
function TableIsNull(){
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}