/**
 * Created by TWQ on 2016/7/29.
 */
$(document).ready(function(){

    $("#information").addClass("active");
    $("#inform-send").addClass("active");
    var type = $("#type").val();
    if(type == "2") {
        $("#board").children('a').click();
    } else if(type=="3"){
        $("#message").children('a').click();
    } else {
        $("#internet").children('a').click();
    }

    $("#clearbtn").bind("click",function(){
        $("#clearbtn").hide();
        $("#nbuserselectinput").val('');
    })
    $("#nbuserselectinput").bind('input propertychange', function() {
        if($('#nbuserselectinput').val()!=''&&$('#nbuserselectinput').val()!=null){
            $("#clearbtn").show();
        }
    });
});
/*   信息发布四种状态   */
var infoState_wait_approval = "1";  //待审批
var infoState_approvaling = "2";    //审批中
var infoState_rejected = "3";       //被驳回
var infoState_send = "4";           //已发布
var infoState_recall = "5";         //已撤回

//切换tab时调用，进行获取type值和搜索框值
function showInform(type) {
    $("#type").val(type);
    if(type==3) {
        $("#stateSelect").hide();
    } else {
        $("#stateSelect").show();
    }
    $("#state").val("");
    $("#nbuserselectinput").val("");
    showInfoInTable("sendlist",1);
}

function searchInform() {
    showInfoInTable("sendlist",1);
}

function showStateInform(state) {
    $("#state").val(state);
    if(infoState_wait_approval == state) {
        $("#ztbutton").html("待审批");
    } else if(infoState_approvaling == state) {
        $("#ztbutton").html("审批中");
    } else if(infoState_rejected == state) {
        $("#ztbutton").html("已驳回");
    } else if(infoState_send == state) {
        $("#ztbutton").html("已发布");
    } else if(infoState_recall == state){
        $("#ztbutton").html("已撤回");
    } else {
        $("#ztbutton").html("状态");
    }
    showInfoInTable("sendlist",1);
}


function showInfoInTable(actionName,selectedPage) {
    $(".bootpagediv").show();
    $("#nulltablediv").hide();

    var type = $("#type").val();
    var state = $("#state").val();
    var search = $("#nbuserselectinput").val();

    $.ajax({
        url:actionName,
        type:'post',
        dataType:'json',
        data:{
            'page':selectedPage,
            'rows':10,
            'type':type,
            'state':state,
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
                    pagingmake(actionName,'showInfoInTable',selectedPage,data.records.pages);
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

        if(list[i].stType == "1") {  //1状态为网站信息
            newTr = $("<tr class='addTr'></tr>");
            /*//多选框
             newTr.append($("<td ></td>"));*/  //暂时去掉批量删
            //序号
            newTr.append($("<td class='center'>"+num+"&nbsp;</td>"));
            //标题
            newTr.append($("<td>"+list[i].stInformTitle+"&nbsp;</td>"));
            //创建时间
            newTr.append($("<td>"+list[i].dtCreate+"&nbsp;</td>"));
            //状态
            newTr.append($("<td class='center'>"+turnStateImg(list[i].stState)+"&nbsp;</td>"));
            //操作
            newTr.append($("<td class='center'>"+turnStateOperate(list[i].stState,list[i].stInformId)+"</td>"));

            $("#internet-info").append(newTr);
        } else if(list[i].stType == "2") {   //2状态为情报板信息
            newTr = $("<tr class='addTr'></tr>");
            /*//多选框
             newTr.append($("<td ></td>"));*/  //暂时去掉批量删
            //序号
            newTr.append($("<td class='center'>"+num+"&nbsp;</td>"));
            //情报板内容
            newTr.append($("<td>"+list[i].stInformContent+"&nbsp;</td>"));
            //创建时间
            newTr.append($("<td>"+list[i].dtCreate+"&nbsp;</td>"));
            //状态
            newTr.append($("<td class='center'>"+turnStateImg(list[i].stState)+"&nbsp;</td>"));
            //操作
            newTr.append($("<td class='center'>"+turnStateOperate(list[i].stState,list[i].stInformId)+"</td>"));


            $("#board-info").append(newTr);
        } else if(list[i].stType == "3") {   //3状态为短信
            newTr = $("<tr class='addTr'></tr>");
            /*//多选框
             newTr.append($("<td ></td>"));*/  //暂时去掉批量删
            //序号
            newTr.append($("<td class='center'>"+num+"&nbsp;</td>"));
            //标题
            newTr.append($("<td>"+list[i].stInformContent+"&nbsp;</td>"));
            //创建时间
            newTr.append($("<td>"+list[i].dtCreate+"&nbsp;</td>"));
            //状态
            newTr.append($("<td class='center'>"+list[i].stResult+"&nbsp;</td>"));
            //操作
            newTr.append($("<td class='center'>&nbsp;<a href=sendDetail?id="+list[i].stInformId+" class='Operate'>查看</a></td>"));

            $("#message-info").append(newTr);
        }
    }
}

function TableIsNull(){
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}


function turnStateImg(state) {
    var state_img = "";
    if(state == infoState_wait_approval) {
        state_img = "<img src='../image/information/information_state_1.png'/>";
    } else if(state == infoState_approvaling) {
        state_img = "<img src='../image/information/information_state_2.png'/>";
    } else if(state == infoState_rejected) {
        state_img = "<img src='../image/information/information_state_3.png'/>";
    } else if(state == infoState_send){
        state_img = "<img src='../image/information/information_state_4.png'/>";
    } else if(state == infoState_recall) {
        state_img = "<img src='../image/information/information_state_5.png'/>";
    }
    return state_img;
}


function turnStateOperate(state,infoid) {
    var state_operate = "";

    if(state == infoState_wait_approval) {
        state_operate = "&nbsp;<a href=sendDetail?id="+infoid+" class='Operate'>查看</a>&nbsp;&nbsp;" +
                        "<a href='#' class='Operate' onclick=giveId("+infoid+") data-toggle='modal' data-target='#recallModal'>撤回</a>&nbsp;&nbsp;" +
                        "<a href='#' class='Operate' onclick=giveId("+infoid+") data-toggle='modal' data-target='#myModal'>删除</a>";
    } else if(state == infoState_approvaling) {
        state_operate = "&nbsp;<a href=sendDetail?id="+infoid+" class='Operate'>查看</a>";
    } else if(state == infoState_rejected) {
        state_operate = "&nbsp;<a href=sendDetail?id="+infoid+" class='Operate'>查看</a>&nbsp;&nbsp;" +
                        "<a href=sendAgain?id="+infoid+" class='Operate'>重发</a>&nbsp;&nbsp;" +
                        "<a href='#' class='Operate' onclick=giveId("+infoid+") data-toggle='modal' data-target='#myModal'>删除</a>";
    } else if(state == infoState_send) {
        state_operate = "&nbsp;<a href=sendDetail?id="+infoid+" class='Operate'>查看</a>";
    } else if(state == infoState_recall) {
        state_operate = "&nbsp;<a href=editInfo?id="+infoid+" class='Operate'>修改</a>&nbsp;&nbsp;" +
            "<a href='#' class='Operate' onclick=giveId("+infoid+") data-toggle='modal' data-target='#myModal'>删除</a>";
    }
    return state_operate;
}

function giveId(id) {
    $("#infoId").val(id);
}

function recall() {
    var id = $("#infoId").val();
    window.location.href = "recall?id="+id;
}

function deleteIt() {
    var id = $("#infoId").val();
    window.location.href = "deleteInform?id="+id;
}


/*
function test() {
    /!*$.ajax({
        url:"http://120.55.193.1:8080/GH/SendMSG",
        type:'post',
        dataType:'json',
        data:{
            'tel':'15888398873,15990305045',
            'mstemplate':'SMS_12740170',
            'rent':'112334'
        },
        success:function(data){
            alert("ok");
        }
    });*!/

    window.location.href="test";
}
*/
