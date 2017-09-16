/**
 * Created by TWQ on 2016/7/29.
 */
$(document).ready(function(){

    $("#system").addClass("active");
    $("#portletLib").addClass("active");
    showPortletTable("portletList",1);
});


function showPortletTable(actionName,selectedPage) {
    $(".bootpagediv").show();
    $("#nulltablediv").hide();

    var search = $("#nbuserselectinput").val();

    $.ajax({
        url:actionName,
        type:'post',
        dataType:'json',
        data:{
            'page':selectedPage,
            'rows':10,
            'search':search
        },
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            $(".addTr").empty();
            $(".addTr").remove();
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                $("#pagedetal").empty();
                $("#pagedetal").text(
                    "当前页"+list.length+"条记录 共"+data.records.total+"条记录，每页"+data.records.rows+"条"
                );
                pagingmake(actionName,'showPortletTable',selectedPage,data.records.pages);
                if(list==""){
                    TableIsNull();
                }else{
                    appendToTable(list);
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

function appendToTable(list){
    var num = 0;
    for(var i=0;i<list.length;i++){

        var newTr;
        //var isShowMap = "style='color:#aaa;' ";
        num = i+1;

        newTr = $("<tr class='addTr'></tr>");
        /*//多选框
         newTr.append($("<td ></td>"));*/  //暂时去掉批量删
        //序号
        newTr.append($("<td class='center'>"+num+"&nbsp;</td>"));
        //标题
        newTr.append($("<td>"+list[i].stModuleName+"&nbsp;</td>"));
        //创建时间
        newTr.append($("<td>"+list[i].stUserName+"&nbsp;</td>"));
        //状态
        newTr.append($("<td>"+list[i].dtCreate+"&nbsp;</td>"));
        //操作
        newTr.append($("<td class='center'>"+getOperate(list[i].stModuleId,list[i].stPortletLibId)+"</td>"));

        $("#portletInfo").append(newTr);
    }
}

function TableIsNull(){
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}

function getOperate(moduleId,libId) {
    var operate_state = "";
    if(moduleId=="function"||moduleId=="serviceSystem"||moduleId=="backlog") {
        operate_state = "&nbsp;<a href='#' onclick='getLibInfo("+libId+")' class='Operate' data-toggle='modal' data-target='#viewModal'>查看</a>&nbsp;&nbsp;";
    } else {
        operate_state = "&nbsp;<a href='#' onclick='getLibInfo("+libId+")' class='Operate' data-toggle='modal' data-target='#viewModal'>查看</a>&nbsp;&nbsp;" +
            "<a href='#' class='Operate' onclick='giveId("+libId+")'  data-toggle='modal' data-target='#myModal'>删除</a>";
    }
    return operate_state;
}

function getLibInfo(libId) {
    $.ajax({
        url:'getLibInfo',
        type:'post',
        dataType:'json',
        data:{
            'libId':libId
        },
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                var lib = list[0];

                $("#viewName").text(lib.stModuleName);
                $("#viewId").text(lib.stModuleId);
                //$("#viewName").text(lib.stModuleName);
                $("#viewUrl").text(lib.stUrl);
                $("#viewDescribe").text(lib.stDescribe);
            }else{
                alert(data.result.resultdesc);
            }

        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
}

function giveId(id) {
    $("#libId").val(id);
}

function deleteIt() {
    var id = $("#libId").val();
    window.location.href = "deleteLib?id="+id;
}

function addLib() {
    $("#libForm").submit();
}