/**
 * Created by TWQ on 2016/10/23.
 */

function showBacklogTable(actionName,selectedPage) {
    $.ajax({
        url:actionName,
        type:'post',
        dataType:'json',
        data:{
            'page':selectedPage,
            'rows':3
        },
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            $("#backlogt").remove();
            $(".bootpagediv").remove();
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                pagingmake(actionName,'showBacklogTable',selectedPage,data.records.pages);
                if(list==""){
                    TableIsNull();
                }else{
                    appendToTable(list,data.records.total,selectedPage);
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

function appendToTable(list, num, selectedPage){
    var newTable;
    newTable = $("<table class='table table-hover' style='border: 0px' id='backlogt'>" +
        "<tr>" +
        "<th class='center'>待办事项名称</th>" +
        "<th class='center'>待办事项类别</th>" +
        "<th class='center'>接受日期</th>" +
        "<th class='center'>操作</th>" +
        "</tr>" +
        "</table>");
    for(var i=0;i<list.length;i++){

        var newTr;
        //var isShowMap = "style='color:#aaa;' ";
        newTr = $("<tr class='addTr'></tr>");
        //待办名称
        newTr.append($("<td class='center'>"+list[i].stBacklogName+"&nbsp;</td>"));
        //代办类型
        newTr.append($("<td class='center'>"+turnType(list[i].stType)+"&nbsp;</td>"));
        //创建时间
        newTr.append($("<td class='center'>"+list[i].dtCreate+"&nbsp;</td>"));
        //操作
        newTr.append($("<td class='center'><span class='label label-success' style='cursor: pointer;' onclick=deal('"+list[i].stType+"','"+list[i].stRelationId+"')>处理</span></td>"));

        newTable.append(newTr);
    }

    $("#backlogTable").append(newTable);

    if(num > 3) {
        var turnLeft;
        var turnRight;
        if(selectedPage == 1) {
            turnLeft = 1;
        } else {
            turnLeft = parseInt(selectedPage)-1;
        }

        if(selectedPage*3 >= num) {
            turnRight = selectedPage;
        } else {
            turnRight = parseInt(selectedPage)+1
        }
        var pageTurn = $("<div class='bootpagediv' style='width:100%;margin-right:20px;'>" +
        "<button class='btn btn-xs btn-yellow bigger' onclick=showBacklogTable('../backlog/showBacklog',"+turnLeft+")>" +
        "<i class='fa fa-angle-left'></i>" +
        "</button>" +
        "<button class='btn btn-xs bigger btn-yellow dropdown-toggle' data-toggle='dropdown' onclick=showBacklogTable('../backlog/showBacklog',"+turnRight+")>" +
        "<i class='fa fa-angle-right'></i>" +
        "</button>" +
        "</div>");
    }
    $("#backlogTable").append(pageTurn);
}

function TableIsNull(){
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}

function turnType(type) {
    var type_name = "";
    if(type == "1") {
        type_name = "信息发布待审批";
    } else if(type == "2") {
        type_name = "日程安排待安排";
    }
    return type_name;
}