$(document).ready(function(){
    $("#calendar_li").addClass("active");
    getspdetal();
})
/**
 * 获取假单详情
 */
function getspdetal(){
    $.ajax({
        url : 'showLeaveAndOvertimeApproval',
        type : "post",
        dataType : "json",
        data : {
            'leaveOrOtID1':$("#kqid").val()
        },
        success : function(data) {
            $("#spbh").text(data.num);
            $("#scr").text(data.user.xm);
            $("#scbm").text(data.user.bmmc);
            $("#scsj").text(data.leaveOrOtDate);
            $("#lx").text(data.leaveOrOtKind.kindName);
            $("#sj").text(data.beginDate);
            $("#sc").text(data.lastDate+'小时');
            $("#sy").text(data.leaveOrOtReason);
            $("#spr").text(data.approvalID1.xm);
            $("#yysm").text(data.approvalOpinion1);
            var ztstr='';
            switch (data.approvalResult){
                case '待审批' :
                    ztstr="<span style='color: rgb(241,92,12)'>待审批</span>";
                    break;
                case '准许' :
                    ztstr="<span style='color: green'>批准</span>";
                    break;
                case '驳回' :
                    ztstr="<span style='color: red'>驳回</span>";
                    break;
                default :
                    break;
            }
            $("#spzt").append(
                ztstr
            );
        }
    })
}