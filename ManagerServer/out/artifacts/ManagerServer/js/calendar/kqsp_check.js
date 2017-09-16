var addtype;
var spr=-1;//审批人id
$(document).ready(function(){
    $("#calendar_li").addClass("active");
    $("#kqsp_li").addClass("active");
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
            $("#spzt").append(
                "<span style='color: rgb(241,92,12)'>"+data.approvalResult+"</span>"
            );
        }
    })
}
function checksp(){
    $.ajax({
        url : 'CheckLeave',
        type : "post",
        dataType : "json",
        data : {
            'id':$("#kqid").val(),
            'opinon':$("#shenheword").val(),
            'result':$('input:radio[name="radiobutton"]:checked').val()
        },
        success : function(data) {
            alert('审批成功');
            window.location.href=$("#basePath").val()+'HomePage';
        }
        })

}