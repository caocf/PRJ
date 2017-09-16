var shipdata;//船舶信息
$(document).ready(function () {
    $("#smart").addClass("active");
    $("#warn").addClass("active");
    $("#warnset_li").addClass("active");
    showInfoInTable('../supervise/warnset',1);
})

/**
 * 显示日志列表信息
 * @param actionName：接口名
 * @param selectedPage：页码
 */
function showInfoInTable(actionName,selectedPage) {
    $(".bootpagediv").show();
    $("#nulltablediv").hide();
    $.ajax({
        url:actionName,
        type:'post',
        dataType:'json',
        data:{
            'page':selectedPage
        },
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                $("#pagedetal").empty();
                $("#pagedetal").text(
                    "当前页"+list.length+"条记录 共"+data.records.total+"条记录，每页"+data.records.rows+"条"
                );
                pagingmake(actionName,'showInfoInTable',selectedPage,data.records.pages);
                if(list==""){
                    TableIsNull();
                }else{
                    appendToTable(list,selectedPage);
                }
            }else{
                alert(data.result.resultdesc);
            }

        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
}
/**
 * 创建表格
 * @param list：数据集合
 * @param selectedPage：页码
 */
function appendToTable(list,selectedPage){
    $("#loglist-info").empty();
    $("#loglist-info").append(
        "<tr>"+
        "<th>序号</th>"+
        "<th>报警类型</th>"+
        "<th>报警名称</th>"+
        "<th>处理意见</th>"+
        "<th>状态</th>"+
        "<th>操作</th>"+
        "</tr>"
    );
    $(list).each(function(index,item){
        var warnstatus='开启';
        var czspanword='关闭';
        if(item.status==0){
            warnstatus='关闭';
            czspanword='开启';
        }
        var warntype='--';
        switch (parseInt(item.type)){
            case 1:
                warntype="船舶";
                break;
            case 2:
                warntype="水位";
                break;
            case 3:
                warntype="流量";
                break;
            default :
                break;
        }
        $("#loglist-info").append(
            "<tr>" +
            "<td>"+((index+1)+(parseInt(selectedPage)-1)*10)+"</td>"+
            "<td>"+warntype+"</td>" +
            "<td>"+isnull(item.name,'--',0)+"</td>" +
            "<td>"+isnull(item.advice,'--',0)+"</td>" +
            "<td>"+warnstatus+"</td>" +
            "<td><span class='clickword' onclick=changestatus("+item.id+")>"+czspanword+"</span></td>" +
            "</tr>"
        );
    })

}
/**
 * 关闭/开启报警设置
 * @param id:设置id
 */
function changestatus(id){
        $.ajax({
            url:'../supervise/switchwarn',
            type:'post',
            dataType:'json',
            data:{
                'id':id
            },
            success:function(data){
                if(data.resultcode==0) {
                    alert('操作成功！');
                    showInfoInTable('../supervise/warnset', 1);
                }
            }
        })
}
//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度
function isnull(str,isnullstr,islong) {
    var isnull = isnullstr;
    if (str == null || str == '' || str == 'null'||str == undefined) {
        return isnull;
    } else {
        if(islong==1){
            if(str.length>=20){
                return "<abbr title='"+str+"'>"+str.substr(0,20)+"</abbr>";
            }
        }
        return str;
    }
}
/**
 * 列表无内容时显示方法
 * @constructor
 */
function TableIsNull(){
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}
//href传参获取
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)
        return  unescape(r[2]);
}