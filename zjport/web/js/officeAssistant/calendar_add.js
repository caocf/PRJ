/**
 * Created by TWQ on 2016/8/3.
 */

$(document).ready(function(){
    $("#officeAssistant").addClass("active");
    $("#calendar_li").addClass("active");
});

/*  富文本编辑器  */
$(function () {
    // Replace the <textarea id="editor1"> with a CKEditor
    // instance, using default configuration.
    CKEDITOR.replace('editor1');

});

function closexl(c){
    $("#"+c).dropdown('toggle');
}
$(document).ready(function(){
    $('#attachment').change(function(){
        $('#filenamespan').text($('#attachment').val());
    });
})
//清空文件
function cleanfile(){
    $('#uploadFile').val('');
    $('#imgname').empty();
}

//点击发布判断是否为空并提交
function send(){
    if($("#title").val() == "") {
        alert("日程标题不能为空！");
    } else if($("#useridstr").val() == "") {
        alert("收件人不能为空！");
    } else if($("#beginTime").val() == "") {
        alert("开始时间不能为空！");
    } else if($("#endTime").val() == "") {
        alert("结束时间不能为空！");
    }  else {
        $("#calendarForm").submit();
    }
}

function showOrg() {
    $.ajax({
        url:"../common/showOrg",
        type:'post',
        dataType:'json',
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                    //WebIsNull();
                }else{
                    //nodes=[];
                    //nodeslvl2=[];
                    //nodeslvl3=[];
                    for(var int=0;int<list.length;int++){
                        var node;
                        var pid;
                        if(list[int].stParentOrgId == null || list[int].stParentOrgId == "") {
                            pid = 0;
                        } else {
                            pid = list[int].stParentOrgId;
                        }
                        node={"id":list[int].stOrgId, "name":"<i class='fa fa-home'></i>&nbsp;"+list[int].stOrgName,"pId":pid,"phone":0};
                        nodes.push(node);
                    }
                    //showDept();
                    showUser();
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
function showDept() {
    $.ajax({
        url:"../common/showDept",
        type:'post',
        dataType:'json',
        success:function(data){
            nodes=[];
            nodeslvl2=[];
            nodeslvl3=[];
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                }else{
                    var node;
                    for(var int=0;int<list.length;int++){
                        node={"id":list[int].stDepartmentId, "name":"<i class='fa fa-folder'></i>&nbsp;"+list[int].stDepartmentName,"pId":list[int].stOrgId,"phone":0};
                        nodes.push(node);
                    }
                    //showUser();
                    showOrg();
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

function showUser() {
    $.ajax({
        url:"../common/showUser",
        type:'post',
        dataType:'json',
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                }else{
                    var node;
                    for(var int=0;int<list.length;int++){
                        var pid;
                        if(list[int].stDepartmentId == null || list[int].stDepartmentId == "") {
                            pid = list[int].stOrgId;
                        } else {
                            pid = list[int].stDepartmentId
                        }
                        //无手机号的人不显示在列表中
                        if(list[int].stPhone != 0 && list[int].stPhone != null) {
                            node={"id":list[int].stUserId, "name":"<i class='fa fa-user'></i>&nbsp;"+list[int].stUserName,"pId":pid,"phone":list[int].stPhone};

                        } else {
                            node={"id":list[int].stUserId, "name":"<i class='fa fa-user'></i>&nbsp;"+list[int].stUserName,"pId":pid,"phone":list[int].stPhone,"chkDisabled":true};
                        }
                        nodes.push(node);
                    }
                    treemake();
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

var zTreeObj;
var zTreeObj1;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var messagesetting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: 0
        }
    },
    view: {
        nameIsHTML: true,
        showIcon: false,
        showLine: false
    }

};

// zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
var zNodes = [
    {name:"test1", open:true, children:[
        {name:"test1_1"}, {name:"test1_2"}]},
    {name:"test2", open:true, children:[
        {name:"test2_1"}, {name:"test2_2"}]}
];
var nodes = [
    { "id":1, "name":"test1" ,"pId":0},
    { "id":2, "name":"test2","pId":1},
    { "id":3, "name":"test3","pId":1},
    { "id":4, "name":"test4","pId":3},
    { "id":5, "name":"test5","pId":3},
    { "id":6, "name":"test6","pId":2},
    { "id":7, "name":"test7","pId":2},
    { "id":8, "name":"test8","pId":2},
    { "id":9, "name":"test9" ,"pId":0}
];

function treemake(){
    zTreeObj = $.fn.zTree.init($("#messageTree"), messagesetting, nodes);
    $.fn.zTree.destroy("rightTree");
}

//添加联系人
function addlxr() {
    var treeObj = $.fn.zTree.getZTreeObj("messageTree");
    var checkednodes = treeObj.getCheckedNodes(true);
    var rnodes=[];
    for(var int=0;int<checkednodes.length;int++){
        if(checkednodes[int].phone!=0){
            var node;
            var node={"id":checkednodes[int].id, "name":checkednodes[int].name,'open':true,"phone":checkednodes[int].phone,'title':checkednodes[int].title};
            rnodes.push(node);
        }
    }
    zTreeObj1 = $.fn.zTree.init($("#rightTree"), messagesetting, rnodes);
}
//删除联系人
function dellxr(){
    var treeObj = $.fn.zTree.getZTreeObj("rightTree");
    var nodes =treeObj.getCheckedNodes(true);
    for (var i=0, l=nodes.length; i < l; i++) {
        treeObj.removeNode(nodes[i]);
    }
}
//清空联系人
function clearlxr(){
    $.fn.zTree.destroy("rightTree");
}

//查找联系人
function findlxr(type){
    var treename;
    var value;
    if(type==1){
        treename='messageTree';
        value=$("#leftlxrinput").val();
    }
    else{
        treename='rightTree';
        value=$("#rightlxrinput").val();
    }
    var lxrtreeObj = $.fn.zTree.getZTreeObj(treename);
    var nodes = lxrtreeObj.getSelectedNodes();//获取高亮节点
    if (nodes.length>0) {
        for(var i=0;i<nodes.length;i++) {
            lxrtreeObj.cancelSelectedNode(nodes[i]);//取消节点高亮
        }
    }
    var node = lxrtreeObj.getNodesByParamFuzzy("name", value, null);//获取模糊查询节点
    if(node==null){
        return
    }
    for(var i=0;i<node.length;i++){
        lxrtreeObj.selectNode(node[i],true);//添加高亮
    }
}

//生成联系人
function sclxr(){
    var treeObj = $.fn.zTree.getZTreeObj("rightTree");
    var allnodes = treeObj.getNodes();
    $('#myModal').modal('hide');
    var showlxrstr='';
    var useridstr='';
    for(var int=0;int<allnodes.length;int++){
        if(int==(allnodes.length-1)){
            useridstr+=allnodes[int].id
         }else{
            useridstr+=allnodes[int].id+','
         }
        showlxrstr+=allnodes[int].title+';';
    }
        $("#useridstr").val(useridstr);
    if(showlxrstr==''){
        $("#usernamespan").text('请选择联系人');
        $("#usernamespan").css('color','#ccc');
    }else{
        $("#usernamespan").text(showlxrstr);
        $("#usernamespan").css('color','#333');
    }
}