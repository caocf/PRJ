$(document).ready(function(){
    $("#officeAssistant").addClass("active");
    $("#addressList_li").addClass("active");
    //nbtreemake();
    showDept();
})
var nbnodes=[];

function showOrg(type) {
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
                        node={
                            "id":list[int].stOrgId,
                            "name":"<div onclick=showUser('"+list[int].stOrgId+"',1,'"+list[int].stOrgName+"')><span><i class='fa fa-home'></i>&nbsp;"+list[int].stOrgName+"</span></div>",
                            "pId":pid,
                            "phone":0,
                            'titleword':list[int].stOrgName
                        };
                        nbnodes.push(node);
                    }
                    if(type!=""&&type!=null) {
                        showAllUser();
                    } else {
                        nbtreemake();
                        showUser('B4EB412FAFF44223A53456711CA3D5EA',1,'浙江省港航管理局');
                    }
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

function showDept(type) {
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

        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
}

//显示所有用户
function showAllUser() {
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
                        node={"id":list[int].stUserId, "name":"<i class='fa fa-user'></i>&nbsp;"+list[int].stUserName,"pId":pid,'titleword':list[int].stOrgName};
                        nbnodes.push(node);
                    }
                    checkboxtreemake();
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

//显示用户
function showUser(id,type,name) {
    //alert("id="+id+"  type="+type+"  name="+name);
    //清除搜索框中内容
    $("#nbuserselectinput").val("");
    $("#type").val(type);
    $("#structureId").val(id);
    $("#orgName").text(name);
    showUserList("showUserList",1);
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

//拼用户table
function appendToTable(array){
    var num = 0;
    for(var i=0;i<array.length;i++){
        var newTr;
        //var isShowMap = "style='color:#aaa;' ";
        num = i+1;
        newTr = $("<tr class='addTr'></tr>");
        //多选框  公众用户有删除功能
        /* newTr.append($("<td class='center'><input type='checkbox' name='file' value='"+list[i].stUserId+"' id='"+list[i].stUserId+"'/></td>"));*/
        //序号
        newTr.append($("<td class='center'>"+num+"&nbsp;</td>"));
        //用户姓名
        newTr.append($("<td class='center'>"+isNull(array[i][0])+"&nbsp;</td>"));
        //所属部门
        newTr.append($("<td class='center'>"+isNull(array[i][2])+"&nbsp;</td>"));
        //所属职务
        newTr.append($("<td class='center'>"+isNull(array[i][3])+"&nbsp;</td>"));
        //手机号码
        newTr.append($("<td class='center'>"+isNull(array[i][1])+"&nbsp;</td>"));
        //操作
        /*newTr.append($("<td class='center'>&nbsp;<span class='clickword' data-toggle='modal' data-target='#checkbguserModal' onclick='showDetail("+array[i][4]+",1)'>详情</span>&nbsp;&nbsp;" +
            "<span class='clickword' data-toggle='modal' data-target='#editbguserModal' onclick='showDetail("+array[i][4]+",2)'>编辑</span>&nbsp;&nbsp;</td>"));*/
        newTr.append($("<td class='center'>&nbsp;<span class='clickword' data-toggle='modal' data-target='#checkbguserModal' onclick='showDetail("+array[i][4]+",1)'>详情</span>&nbsp;&nbsp;</td>"));

        $("#txltable").append(newTr);
    }
}

function isNull(content) {
    if(content == null) {
        return "";
    } else {
        return content;
    }
}

function TableIsNull(){
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}

function showDetail(userId,type) {
    //根据type判断是查询还是编辑  1为查询，2为编辑
    $.ajax({
        type : "POST",
        url : "showUserDetail",
        datatype : "json",
        data : {'userId':userId},
        success : function(data) {

            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var array = new Array();
                array=data.records.data;
                if(array==""){
                    TableIsNull();
                }else{
                    showUserInfo(array,type);
                }
            }else{
                alert(data.result.resultdesc);
            }
        },
        error : function() {
            alert("显示人员出错");
        }
    });
}

function showUserInfo(array,type) {
    $("#userId").val(array[0][0]);
    if(type == 1) {
        $("#name_info").html(array[0][1]);
        $("#dept_info").html(array[0][2]);
        $("#position_info").html(array[0][3]);
        $("#lawcode_info").html(array[0][4]);
        $("#order_info").html(array[0][5]);
        $("#phone_info").html(array[0][6]);
        $("#officephone_info").html(array[0][7]);
        $("#fu_info").html(array[0][8]);
        $("#email_info").html(array[0][9]);
    } else {
        $("#name_edit").html(array[0][1]);
        $("#dept_edit").html(array[0][2]);
        $("#position_edit").html(array[0][3]);
        $("#lawcode_edit").val(array[0][4]);
        $("#order_edit").val(array[0][5]);
        $("#phone_edit").html(array[0][6]);
        $("#officephone_edit").html(array[0][7]);
        $("#fu_edit").html(array[0][8]);
        $("#email_edit").html(array[0][9]);
    }

}

function editDetail() {
    var userId = $("#userId").val();
    showDetail(userId,2);
}

var zTreeObj;
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
//短信添加联系人分组切换
function phonetab(thistab,type){
    $(".phonefz").css('border','0');
    $(thistab).css({'border':'solid 1px #ccc','border-top':'solid 2px rgb(0,103,172)','border-bottom':'solid 2px white'});
    switch (parseInt(type)){
        case 1 :
            nbtreemake();
            $("#messageTree").show();
            $("#nbryssl").show();
            $("#ggryssl").hide();
            $("#qzbq").hide();
            nbrytablemake();
            break;
        case 2 :
            $("#nbryssl").hide();
            $("#qzbq").hide();
            $("#ggryssl").show();
            ggrytablemake();
            break;
        case 3 :
            $("#qzbq").show();
            $("#nbryssl").show();
            $("#ggryssl").hide();
            $("#messageTree").hide();
            qzrytablemake();
            break;
        default :
            break;
    }
}
//内部人员列表展示
function nbrytablemake(){
    $("#txltable").empty();
    $("#txltable").append(
    "<tr>"+
    "<th class='center' width='10%'>序号</th>"+
    "<th class='center' width='15%'>员工姓名</th>"+
    "<th class='center' width='15%'>所属部门</th>"+
    "<th class='center' width='15%'>所在职位</th>"+
    /*"<th class='center' width='10%'>在职状态</th>"+*/
    "<th class='center' width='20%'>手机号码</th>"+
    "<th class='center' width='15%'>操作</th>"+
    "</tr>"
    );
    showDept();
    showUserList("showUserList",1);
}
//公共人员列表
function ggrytablemake(){
    $("#txltable").empty();
    $("#txltable").append(
    "<tr>"+
    "<th><input type='checkbox' onclick='checkedOrNo(this)'/></th>"+
    "<th>序号</th>"+
    "<th>员工姓名</th>"+
    "<th>所属部门</th>"+
    "<th>所在职位</th>"+
    "<th>在职状态</th>"+
    "<th>手机号码</th>"+
    "<th>操作</th>"+
    "</tr>"
    );
}

//群组人员列表显示
function qzrytablemake(){
    showGroup();
    $("#txltable").empty();
    $("#txltable").append(
        "<tr>"+
        "<th class='center' width='10%'>序号</th>"+
        "<th class='center' width='15%'>员工姓名</th>"+
        "<th class='center' width='15%'>所属部门</th>"+
        "<th class='center' width='15%'>所在职位</th>"+
            /*"<th class='center' width='10%'>在职状态</th>"+*/
        "<th class='center' width='20%'>手机号码</th>"+
        "<th class='center' width='15%'>操作</th>"+
        "</tr>"
    );

    showAddressGroupList("showAddressGroupList",1);
}

function showGroup() {
    $.ajax({
        type : "POST",
        url : "showAddressGroup",
        datatype : "json",
        success : function(data) {
            $(".qzdiv").empty();
            $(".qzdiv").remove();
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                    //DivIsNull();
                }else{
                    showAddressGroup(list);
                }
            }else{
                //alert(data.result.resultdesc);
            }
        },
        error : function() {
            alert("显示通讯录群组出错！");
        }
    });
}

function showAddressGroup(list) {
    for(var i=0;i<list.length;i++){
        var newTr;
        newTr = $("<div class='qzdiv' onclick=clickqzdiv(this,"+list[i].stAddressListGroupId+",'"+list[i].stGroupName+"') onmousemove='mouseoverqzdiv(this)' onmouseout='mouseoutqzdiv(this)'></div>");
        newTr.append($("<span>"+list[i].stGroupName+"</span><div class='qzidiv Operate' style='margin-right: 10px;' onclick='deleteGroup("+list[i].stAddressListGroupId+")'><i class='fa fa-trash'></i></div> <div class='qzidiv' onclick=addOreditqz('edit',"+list[i].stAddressListGroupId+")> <i class='fa fa-pencil-square-o'></i> </div>"))
        $("#qzbq").append(newTr);
    }
    $('.qzdiv:eq(0)').click();
}

//点击群组
function clickqzdiv(event,id,name){
    $('.qzdiv').css('color','#333');
    $('.qzdiv').css('background-color','white');
    $('.qzdiv').attr('onmouseout','mouseoutqzdiv(this)');
    $(event).css('color','white');
    $(event).css('background-color','rgb(0,103,172)');
    $(event).removeAttr('onmouseout');
    $("#groupId").val(id);
    $("#orgName").text(name);
    showAddressGroupList("showAddressGroupList",1);
}

function showAddressGroupList(actionName,selectedPage) {
    var groupId = $("#groupId").val();
    $.ajax({
        type : "POST",
        url : actionName,
        datatype : "json",
        data:{
            'groupId':groupId,
            'page':selectedPage,
            'rows':10
        },
        success : function(data) {
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
        },
        error : function() {
            alert("显示通讯录群组出错！");
        }
    });
}

//鼠标移动到群组
function mouseoverqzdiv(event){
    $(event).css('color','white');
    $(event).css('background-color','rgb(0,103,172)');
}
//鼠标移出群组
function mouseoutqzdiv(event){
    $(event).css('color','#333');
    $(event).css('background-color','white');
}
//checkbox全选
function checkedOrNo(obj){
    var isCheck = obj.checked;
    $('.systemcheck').each(function(){
        this.checked=isCheck;
    });
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
var qzrightsetting = {
    check: {
        enable: true,
        chkboxType: { "Y": "s", "N": "s" }
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

//添加联系人
function addlxr() {
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    var checkednodes = treeObj.getCheckedNodes(true);
    var rnodes=[];
    for(var int=0;int<checkednodes.length;int++){
        if(checkednodes[int].phone!=0){
            var node={"id":checkednodes[int].id, "name":checkednodes[int].name,'open':true,"phone":checkednodes[int].phone};
            rnodes.push(node);
        }
    }
    zTreeObj1 = $.fn.zTree.init($("#rightTree"), checksetting, rnodes);
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
        treename='leftTree';
        value=$("#leftlxrinput").val();
    }
    else{
        treename='rightTree';
        value=$("#rightlxrinput").val();
    }
    var treeObj = $.fn.zTree.getZTreeObj(treename);
    var node = treeObj.getNodesByParamFuzzy("name", value, null);
    if(node==null){
        return
    }
    for(var i=0;i<node.length;i++){
        lxrtreeObj.selectNode(node[i],true);
    }
}

//新增，编辑群组
function addOreditqz(type,id){
    clean();
    $('#addqzModal').modal('show');
    if(type=='add'){
        $('#qzmodaltotal').text('新增群组');
        showDept(1);
    }else{
        $.ajax({
            type : "POST",
            url : "editGroup",
            datatype : "json",
            data : {'id':id},
            success : function(data) {
                //$('#addqzModal').modal('hide');
                var array = new Array();
                array=data.records.data;
                $("#groupName").val(array[0][3]);
                $("#id").val(array[0][2]);
                var rnodes=[];
                var userIds;
                for(var int=0;int<array.length;int++){
                        var node={"id":array[int][0], "name":"<i class='fa fa-user'></i>&nbsp;"+array[int][1],'open':true};
                        rnodes.push(node);
                    userIds+=array[int][0]+";";
                }
                $("#userIds").val(userIds);
                zTreeObj1 = $.fn.zTree.init($("#rightTree"), checksetting, rnodes);
                showDept(1);
            },
            error : function() {
                alert("出错！");
            }
        });
        $('#qzmodaltotal').text('编辑群组');
    }

}

function checkboxtreemake(){
    zTreeObj = $.fn.zTree.init($("#leftTree"), checksetting, nbnodes);
    editchecked();
}
function editchecked(){
    var treeObj1 = $.fn.zTree.getZTreeObj("leftTree");
    var treeObj = $.fn.zTree.getZTreeObj("rightTree");
    var allnodes = treeObj.getNodes();
    for(var int=0;int<allnodes.length;int++){
        var node11=treeObj1.getNodeByParam("id", allnodes[int].id, null);
        treeObj1.checkNode(node11, true, true);
    }
}
function saveUser() {
    if($("#order_edit").val() == ""){
        alert("请输入用户序号");
    } else {
        $.ajax({
            type : "POST",
            url : "saveUser",
            datatype : "json",
            data : $("#userForm").serializeArray(),
            success : function(data) {
                $('#editbguserModal').modal('hide');
                alert("修改成功！");
            },
            error : function() {
                alert("批量移动出错！");
            }
        });
    }
}

//生成联系人
function sclxr(){
    var treeObj = $.fn.zTree.getZTreeObj("rightTree");
    var allnodes = treeObj.getNodes();
    //$('#addqzModal').modal('hide');
    var showlxrstr='';
    for(var int=0;int<allnodes.length;int++){
        /*if(int==(allnodes.length-1)){
         showlxrstr+=allnodes[int].phone
         }else{
         showlxrstr+=allnodes[int].phone+';'
         }*/
        showlxrstr+=allnodes[int].id+';';
    }
    $("#userIds").val(showlxrstr);

    send();
}

function send() {
    if($("#groupName").val() == "") {
        alert("群组名称不能为空！");
    } else if($("#userIds").val() == "") {
        alert("群组人员不能为空！");
    } else {
        //$("#groupForm").submit();
        $.ajax({
            type : "POST",
            url : "saveGroup",
            datatype : "json",
            data : $("#groupForm").serializeArray(),
            success : function(data) {
                $('#addqzModal').modal('hide');
                clean();
                showGroup();
                alert("保存成功！");
            },
            error : function() {
                alert("出错！");
            }
        });
    }
}

function deleteGroup(id) {
    $('#deleteModal').modal('show');
    $("#groupId").val(id);
}

function deleteIt() {
    var id = $("#groupId").val();
    $.ajax({
        type : "POST",
        url : "addressListDelete",
        datatype : "json",
        data : {'id':id},
        success : function(data) {
            $('#deleteModal').modal('hide');
            alert("删除成功");
        },
        error : function() {
            alert("删除出错！");
        }
    });
}

function clean() {

    $("#userIds").val("");
    $("#groupName").val("");
    $("#id").val("");
    clearlxr();
}