$(document).ready(function(){
    $("#system").addClass("active");
    $("#rolemanage_li").addClass("active");
    showInfoInTable('../manage/rolelist',1);
})
/**
 * 显示角色列表信息
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
            'row':10,
            'jsmc':$("#rolenameselectinput").val()
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
function TableIsNull(){
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}
function appendToTable(list){
    $("#rolelist-info").empty();
    $("#rolelist-info").append(
        "<tr>" +
        "<th>角色名称</th>" +
        "<th>权限</th>" +
        "<th>操作</th>" +
        "</tr>"
    );
    $(list).each(function(index,item){
        var czwordstr="<span onclick=checkrolemodelmake('"+item.jsmc+"',"+item.jsbh+") style='color: #0063dc;cursor: pointer;'>查看</span>&nbsp;&nbsp;<span onclick=editrolemodelmake('"+item.jsmc+"',"+item.jsbh+") style='color: #0063dc;cursor: pointer;'>编辑</span>&nbsp;&nbsp;<span onclick='showdelmodel("+item.jsbh+")' style='color: #0063dc;cursor: pointer;'>删除</span>";
        if(item.jsbh==1){
            czwordstr="<span onclick=checkrolemodelmake('"+item.jsmc+"',"+item.jsbh+") style='color: #0063dc;cursor: pointer;'>查看</span>&nbsp;&nbsp;<span style='color: #ccc;'>编辑</span>&nbsp;&nbsp;<span style='color: #ccc;'>删除</span>";
        }
        $("#rolelist-info").append(
            "<tr>" +
            "<td>"+isnull(item.jsmc,'--',1)+"</td>" +
            "<td>"+isnull(item.qx,'--',1)+"</td>" +
            "<td>"+czwordstr+"</td>" +
            "</tr>"
        );
    })

}
var rolezTreeObj;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var rolesetting = {
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
/**
 * 新增角色弹出
 */
function addrolemodelmake(){
    $('#addroleModal').modal('show');
        $("#addroletitle").text('新增角色');
        $("#addrolebtn").attr('onclick','addrole()');
    $.ajax({
        url:'../manage/qxlist',
        type:'post',
        dataType:'json',
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var addnodes=[];
                $(data.records.data).each(function(index,item){
                    var node={'id':item.qxbh,'pId':item.fbh,'name':item.qxmc};
                    addnodes.push(node);
                })
                rolezTreeObj = $.fn.zTree.init($("#addroleTree"), rolesetting, addnodes);
            }else{
                alert(data.resultdesc);
                return
            }
        }
    });
}
/**
 * 编辑角色弹出
 * @param id:角色id
 * @param name:角色名称
 */
function editrolemodelmake(name,id){
    $('#addroleModal').modal('show');
                $("#role_name").val(name);
        $("#addroletitle").text('编辑角色');
        $("#addrolebtn").attr('onclick','editrole('+id+')');
    $.ajax({
        url:'../manage/viewrole',
        type:'post',
        dataType:'json',
        data:{
          'jsbh':id
        },
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var addnodes=[];
                $(data.records.data).each(function(index,item){
                    var ischeck=false;
                    if(item.check==1){
                        ischeck=true;
                    }
                    addnodes[index]={"id": item.qxbh,"pId": item.fbh,"name": item.qxmc,checked:ischeck,open:ischeck};
                })
                rolezTreeObj = $.fn.zTree.init($("#addroleTree"), rolesetting, addnodes);
            }else{
                alert(data.resultdesc);
                return
            }
        }
    });
}
/**
 * 查看角色弹出
 * @param id:角色id
 * @param name:角色名称
 */
function checkrolemodelmake(name,id){
    $('#checkroleModal').modal('show');
                $("#checkinput").val(name);
    $.ajax({
        url:'../manage/viewrole',
        type:'post',
        dataType:'json',
        data:{
          'jsbh':id
        },
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var addnodes=[];
                $(data.records.data).each(function(index,item){
                    var ischeck=false;
                    if(item.check==1){
                        ischeck=true;
                    }
                    addnodes[index]={"id": item.qxbh,"pId": item.fbh,"name": item.qxmc,checked:ischeck,open:ischeck,"chkDisabled":true};
                })
                rolezTreeObj = $.fn.zTree.init($("#checkroleTree"), rolesetting, addnodes);
            }else{
                alert(data.resultdesc);
                return
            }
        }
    });
}
/**
 * 新增保存角色
 */
function addrole(){
    if ($("#role_name").val() == "") {
        alert("请输入角色名称");
        return;
    }
    if ($("#role_name").val().length > 100) {
        alert("角色名称不能大于100位");
        return;
    }
    var treeObj = $.fn.zTree.getZTreeObj("addroleTree");
    var nodes = treeObj.getCheckedNodes(true);
    if(nodes.length==0){
        alert('请选择权限');
        return
    }
    var idstr='';
    for (var int = 0; int < nodes.length; int++) {
        if(int==(nodes.length-1))
            idstr+=nodes[int].id;
        else
            idstr+=nodes[int].id+',';
    }
    $.ajax({
        url : '../manage/addrole',
        type : 'post',
        dataType : 'json',
        data : {
            'jsmc' : $.trim($("#role_name").val()),
            'qxstr' : idstr
        },
        success : function(data) {
            if (data.resultcode == 0) {
                alert('新增成功！');
                $('#addroleModal').modal('hide');
                showInfoInTable('../manage/rolelist',1);
            }else{
                alert(data.resultdesc);
                return
            }
        }
    })
}
/**
 * 编辑保存角色
 * @param id：角色id
 */
function editrole(id){
    if ($("#role_name").val() == "") {
        alert("请输入角色名称");
        return;
    }
    if ($("#role_name").val().length > 100) {
        alert("角色名称不能大于100位");
        return;
    }
    var treeObj = $.fn.zTree.getZTreeObj("addroleTree");
    var nodes = treeObj.getCheckedNodes(true);
    if(nodes.length==0){
        alert('请选择权限');
        return
    }
    var idstr='';
    for (var int = 0; int < nodes.length; int++) {
        if(int==(nodes.length-1))
            idstr+=nodes[int].id;
        else
            idstr+=nodes[int].id+',';
    }
    $.ajax({
        url : '../manage/modifyrole',
        type : 'post',
        dataType : 'json',
        data : {
            'jsmc' : $.trim($("#role_name").val()),
            'jsbh' : id,
            'qxstr' : idstr
        },
        success : function(data) {
            if (data.resultcode == 0) {
                alert('编辑成功！');
                $('#addroleModal').modal('hide');
                showInfoInTable('../manage/rolelist',1);
            }else{
                alert(data.resultdesc);
                return
            }
        }
    })
}
/**
 * 显示删除角色model
 * @param id：角色id
 */
function showdelmodel(id){
    $('#deleteModal').modal('show');
    $('#delbtn').attr('onclick','deleterole('+id+')');
}
/**
 * 删除角色
 * @param id；角色id
 */
function deleterole(id){
    $.ajax({
        url : '../manage/delrole',
        type : 'post',
        dataType : 'json',
        data : {
            'jsbh' : id
        },
        success : function(data) {
            if (data.resultcode == 0) {
                alert('删除成功！');
                $('#deleteModal').modal('hide');
                showInfoInTable('../manage/rolelist',1);
            }else{
                alert(data.resultdesc);
                return
            }
        }
    })
}
//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度
function isnull(str,isnullstr,islong) {
    var isnull = isnullstr;
    if (str == null || str == '' || str == 'null'||str == 'undefined') {
        return isnull;
    } else {
        if(islong==1){
            if(str.length>=40){
                return "<abbr title='"+str+"'>"+str.substr(0,40)+"</abbr>...";
            }
        }
        return str;
    }
}