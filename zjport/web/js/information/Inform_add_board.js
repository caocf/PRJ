/**
 * Created by TWQ on 2016/8/4.
 */
$(document).ready(function(){
    //取页面上addOrEdit数据，若为add则为新增，那么清空内容框中内容
    var addOrEdit = $("#addOrEdit").val();
    if("add" == addOrEdit) {$("#editor1").val('');}
    $("#information").addClass("active");
    $("#inform-send").addClass("active");
    showBoard();
    showApprovalUser();
})

function showApprovalUser() {
    $.ajax({
        url:"showApprovalUser",
        type:'post',
        dataType:'json',
        /*data:{
         'page':selectedPage,
         'rows':10,
         'type':type
         },*/
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                    //WebIsNull();
                }else{
                    appendApprovalUser(list);
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

function appendApprovalUser(list) {
    var newOption = "";
    for(var i=0;i<list.length;i++){
        newOption+="<option name='choose' value='"+list[i][0]+"'>"+list[i][1]+"</option>";
    }
    $("#disabledSelect").append(newOption);
}

function showBoard() {
    $.ajax({
        url:"showBoard",
        type:'post',
        dataType:'json',
        /*data:{
         'page':selectedPage,
         'rows':10,
         'type':type
         },*/
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                    //WebIsNull();
                }else{
                    appendBoardTree(list);
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

var nodes =[];

function appendBoardTree(list) {
    for(var i=0;i<list.length;i++){
        var node = {"id":list[i].stBoardId,"name":list[i].stBoardName,"pId":0};
        nodes.push(node);
    }
}


//调色板颜色切换
function colorchange(event,color){
    $('.dropdown-toggle').dropdown('toggle')
    $("#btn_colordiv").css('background-color',$(event).css('background-color'));
    $("#color").val(color)
}


var zTreeObj;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var setting = {
    check: {
        enable: true
    },
    view: {
        showLine: false
    }

};

//选择情报板树生成
function qbmtreemake(){
    zTreeObj = $.fn.zTree.init($("#leftTree"), setting, nodes);
    $.fn.zTree.destroy("rightTree");
}

//添加情报板
function addqbb() {
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    var checkednodes = treeObj.getCheckedNodes(true);
    var rnodes=[];
    for(var int=0;int<checkednodes.length;int++){
        rnodes[int]={"id":checkednodes[int].id, "name":checkednodes[int].name ,"pId":checkednodes[int].pId,'open':true};
    }
    zTreeObj1 = $.fn.zTree.init($("#rightTree"), setting, rnodes);
}
//删除情报板
function delqbb(){
    var treeObj = $.fn.zTree.getZTreeObj("rightTree");
    var nodes =treeObj.getCheckedNodes(true);
    for (var i=0, l=nodes.length; i < l; i++) {
        treeObj.removeNode(nodes[i]);
    }
}
//清空情报板
function clearqbb(){
    $.fn.zTree.destroy("rightTree");
}

//查找情报板
function findqbb(type){
    var treename;
    var value;
    if(type==1){
        treename='leftTree';
        value=$("#leftqbbinput").val();
    }
    else{
        treename='rightTree';
        value=$("#rightqbbnput").val();
    }
    var treeObj = $.fn.zTree.getZTreeObj(treename);
    var node = treeObj.getNodeByParam("name", value, null);
    if(node==null){
        return
    }
    treeObj.selectNode(node);
}

var boards=''
//生成情报板标签
function qbbbqmake(){
    var treeObj = $.fn.zTree.getZTreeObj("rightTree");
    var checkednodes = treeObj.getNodes(true);
    $('#myModal').modal('hide');
    if(checkednodes.length==0){
        return
    }
    boards='';
    $("#qbbdiv").empty();
    for(var i=0;i<checkednodes.length;i++){
        var width = checkednodes[i].name.length * 15 + 50;
        $("#qbbdiv").append(
            "<div style='margin-bottom:10px;height:27px;float: left;margin-left:10px;width:" + width + "px;color: #333;line-height: 27px;padding:0 5px;background-color: rgb(250,250,250);border: solid 1px #ccc;'>" +
            checkednodes[i].name + "<span  style='font-size: 20px;font-weight:700;line-height: 27px;float: right;cursor: pointer;'onclick='removeqbbbqdiv(this)'>×</span>" +
            "</div>"
        );
        if(i==(checkednodes.length-1)) {
            boards += checkednodes[i].name;
        } else {
            boards += checkednodes[i].name + ',';
        }
    }
    $("#boards").val(boards);
}

//删除情报板标签
function removeqbbbqdiv(event){
    $(event).parent().remove();
}

//点击发布判断是否为空并提交
function send(){
    if($("#informObject").val() == "") {
        alert("请选择需要发送的情报板！");
    } else if($("#disabledSelect").val() == "0") {
        alert("请选择审批人！");
    } else {
        $("#boardForm").submit();
    }
}