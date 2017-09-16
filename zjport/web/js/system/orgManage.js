$(document).ready(function(){
    $("#system").addClass("active");
    $("#orgManage_li").addClass("active");
    //nbtreemake();
    showOrg();
})
var nbnodes=[];

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
                    for(var int=0;int<list.length;int++){
                        var node;
                        var pid;
                        if(list[int].stParentOrgId == null || list[int].stParentOrgId == "") {
                            pid = 0;
                        } else {
                            pid = list[int].stParentOrgId;
                        }
                        node={"id":list[int].stOrgId, "name":"<div onclick=showDetail('"+list[int].stOrgId+"','"+list[int].stOrgName+"')><span><i class='fa fa-home'></i>&nbsp;"+list[int].stOrgName+"</span></div>","pId":pid,"phone":0,'titleword':list[int].stOrgName};
                        nbnodes.push(node);
                    }

                    nbtreemake();
                    showDetail('B4EB412FAFF44223A53456711CA3D5EA','浙江省港航管理局');
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

/*function showDept(type) {
    $.ajax({
        url:"../common/showDept",
        type:'post',
        dataType:'json',
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                }else{
                    nbnodes=[];
                    var node;
                    for(var int=0;int<list.length;int++){
                        node={"id":list[int].stDepartmentId, "name":"<div onclick=showUser('"+list[int].stDepartmentId+"',2,'"+list[int].stDepartmentName+"')><span><i class='fa fa-folder'></i>&nbsp;"+list[int].stDepartmentName+"</span></div>","pId":list[int].stOrgId,"phone":0,'titleword':list[int].stOrgName};
                        nbnodes.push(node);
                    }
                    showOrg(type);
                }
            }else{
                alert(data.result.resultdesc);
            }

        }/!*,
         complete : function() {
         CloseLoadingDiv();
         }*!/
    });
}*/

//显示用户
function showDetail(id,name) {
    //清除搜索框中内容
    $("#nbuserselectinput").val("");
    $("#orgId").val(id);
    $("#orgName").text(name);

    $.ajax({
        url:"showOrgDetail",
        type:'post',
        dataType:'json',
        data:{
            'id':id
        },
        success:function(data){
            var list=data.obj.records.data;
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                addDetail(list);
            }else{
                alert(data.result.resultdesc);
            }

        }
    });
    //showUserList("../officeAssistant/showUserList",1);
}

function addDetail(list) {
    var org = list[0];
    $("#org_name").text(org[0]);
    $("#org_code").text(org[1]);
    $("#org_short_name").text(org[2]);
    $("#org_domains").text(org[3]);
    $("#parent_org").text(org[4]);

    $("#org_address").text(org[5]);
    $("#org_phone").text(org[6]);
    $("#org_area").text(org[8]);
    $("#org_fox").text(org[9]);
    $("#org_post").text(org[10]);

    //添加修改栏信息
    $("#orgAddress").val(org[5]);
    $("#orgPhone").val(org[6]);
    $("#orgArea").val(org[7]);
    $("#orgFox").val(org[9]);
    $("#orgPost").val(org[10]);
}

function send() {
    var id= $("#orgId").val();
    var name = $("#org_name").text();
    $.ajax({
        type : "POST",
        url : "orgDetailSubmit",
        datatype : "json",
        data : $("#orgForm").serializeArray(),
        success : function(data) {

            $('#editModal').modal('hide');
            showDetail(id,name);
            alert("成功！");
        },
        error : function() {
            alert("保存出错！！");
        }
    });
}

function showUserList(actionName,selectedPage) {
    $(".bootpagediv").show();
    $("#nulltablediv").hide();
    var type = $("#type").val();
    var structureId = $("#structureId").val();
    var search = $("#nbuserselectinput").val();
    $.ajax({
        url:actionName,
        type:'post',
        dataType:'json',
        data:{
            'page':selectedPage,
            'rows':10,
            'type':type,
            'structureId':structureId,
            'search':search
        },
        success:function(data){
            $(".addTr").empty();
            $(".addTr").remove();
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var array = new Array();
                array=data.records.data;
                $("#pagedetal").empty();
                $("#pagedetal").text(
                    "当前页"+array.length+"条记录 共"+data.records.total+"条记录，每页"+data.records.rows+"条"
                );
                pagingmake(actionName,'showUserList',selectedPage,data.records.pages);
                if(array==""){
                    TableIsNull();
                }else{
                    appendToTable(array);
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
var addmlTreeObj;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var nbsetting = {
    view: {
        nameIsHTML: true,
        showLine: false,
        showIcon: false,
        selectedMulti: false,
        dblClickExpand: false,
        addDiyDom: addDiyDom
    },
    data: {
        simpleData: {
            enable: true
        }
    }

};
var checksetting = {
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
function addDiyDom(treeId, treeNode) {
    var spaceWidth = 7;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 1) {
        var spaceStr = "<span style='display: inline-block;'></span>";
        switchObj.before(spaceStr);
    }
    if (treeNode.level > 0) {
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
        switchObj.before(spaceStr);
    }
    $(".ztree").find('a').removeAttr('title');
}
function nbtreemake(){
    var treeObj = $("#messageTree");
    zTreeObj = $.fn.zTree.init(treeObj, nbsetting, nbnodes);
    //treeObj.hover(function () {
    //    if (!treeObj.hasClass("showIcon")) {
    //        treeObj.addClass("showIcon");
    //    }
    //}
    //, function() {
    //treeObj.removeClass("showIcon");
    //}
    //);
    var nbtreeObj = $.fn.zTree.getZTreeObj("messageTree");
    var firstnode=nbtreeObj.getNodeByParam("titleword",'浙江省港航管理局', null);
    nbtreeObj.selectNode(firstnode);
}


var qzleftsetting = {
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
    }

};
var nodes=[];
function selectArea() {
    $.ajax({
        url:"showAreaList",
        type:'post',
        dataType:'json',
        success:function(data){
            nodes=[];
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.obj.records.data;
                $("#sjmlname").text($("#org_area").text());
                if(list==""){
                }else{
                    var node;
                    for(var int=0;int<list.length;int++){
                        node={"open": true,"id":list[int].stAreaCode, "name":"<div><span onclick='addsjml(this,"+list[int].stAreaCode+")'><i class='fa fa-folder'></i>&nbsp;"+list[int].stAreaName+"</span></div>","pId":list[int].stPCode};
                        nodes.push(node);
                    }
                    addmlTreemake();
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
function addmlTreemake(){
    var treeObj = $("#addmlTree");
    addmlTreeObj = $.fn.zTree.init(treeObj, nbsetting, nodes);
    $("#addmlul").find('a').attr('data-stopPropagation',true);
    $("#addmlul").on("click", "[data-stopPropagation]", function(e) {
        e.stopPropagation();
    });
}

//添加上级目录
function addsjml(event,id){
    $("#sjmlname").text($.trim($(event).text().substr(1,$(event).text().length)));
    $("#addmlul").dropdown('toggle');
    $("#orgArea").val(id);
}