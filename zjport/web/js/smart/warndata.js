var shipdata;//船舶信息
var areaname={};//区域map
$(document).ready(function () {
    $("#smart").addClass("active");
    $("#warn").addClass("active");
    $("#warndata_li").addClass("active");
    $("#area"+GetQueryString("area")).attr('selected','selected');
    getallarea();
})
//获取地区下拉框
function getallarea() {
    $.ajax({
        url: '../supervise/areas',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if ($("#userareaNo").val() == 330000) {
                $("#areaselect").show();
                $("#qyname").hide();
                $("#areaselect").empty();
                $(data.records.data).each(function (index, item) {
                    areaname[item.stAreaCode]=item.stAreaName;
                    $("#areaselect").append(
                        "<option value='" + item.stAreaCode + "'>" + item.stAreaName + "</option>"
                    );
                })
            } else {
                $("#areaselect").hide();
                $("#qyname").show();
                $(data.records.data).each(function (index, item) {
                    areaname[item.stAreaCode]=item.stAreaName;
                    if(item.stAreaCode==$("#userareaNo").val()){
                        $("#qyname").text(item.stAreaName);
                    }
                })
            }
            console.log(areaname)
            showInfoInTable('../supervise/warnlist',1);
        }
    })
}
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
            'page':selectedPage,
            'bjlx':$("#bjlxselect").val(),
            'end':$("#endTime").val(),
            'start':$("#beginTime").val(),
            'name':$("#rolenameselectinput").val(),
            'area':$("#userareaNo").val() != 330000 ? $("#userareaNo").val() : $("#areaselect").val()
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
        "<th>报警区域</th>"+
        "<th>报警类型</th>"+
        "<th>报警对象名称</th>"+
        "<th>报警时间</th>"+
        "<th>操作</th>"+
        "</tr>"
    );
    $(list).each(function(index,item){
        var area=areaname[item.area];
        var warntype='--';
        var gzyj='--';
        switch (parseInt(item.type)){
            case 1:
                warntype="危险品船舶驶入";
                gzyj="<span class='clickword' onclick=\"cbgz('"+item.zwcm+"')\">跟踪预警</span>"
                break;
            case 2:
                warntype="船舶状态异常";
                break;
            case 3:
                warntype="水位超警戒";
                break;
            case 4:
                warntype="流量异常";
                break;
            default :
                break;
        }
        $("#loglist-info").append(
            "<tr>" +
            "<td>"+((index+1)+(parseInt(selectedPage)-1)*10)+"</td>"+
            "<td>"+area+"</td>" +
            "<td>"+warntype+"</td>" +
            "<td>"+isnull(item.zwcm,'--',0)+"</td>" +
            "<td>"+isnull(item.createtime,'--',0)+"</td>" +
            "<td>"+gzyj+"</td>" +
            "</tr>"
        );
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
//船舶跟踪
function cbgz(shipname){
    $.ajax({
        url: '../supervise/cbmc',
        type: 'post',
        dataType: 'json',
        data : {
            'cbmc' : shipname
        },
        success: function (data) {
            window.open ("../cbgz.jsp", "船舶跟踪", "height=360, width=500, top=30, left=30, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
        }
    })
}
//href传参获取
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)
        return  unescape(r[2]);
}