$(document).ready(function(){
    $("#cog").addClass("active");
    $("#rolemanager_li").addClass("active");
    showTabledata('UserRoles',1);
})

function showTabledata(actionname,page) {
    $.ajax({
        url: actionname,
        type: "post",
        dataType: "json",
        data: {
            'page': page,
            'name': $("#listkey").val()
        },
        success: function (data) {
            pagingmake(actionname, 'showTabledata', page, data.pages);
            $("#rolelist-info").empty();
            $("#rolelist-info").append(
                "<tr style='background-color: rgb(240,245,248);'>" +
                "<th style='width:50px;'>编号</th>"+
                "<th>角色名称</th>"+
                "<th>拥有权限</th>"+
                "<th style='width:50px;'>操作</th>"+
                "</tr>"
            );
            $(data.list).each(function (index, item) {
                var rolelist=item.permissionENs;
                var rolenamestr='';
                for(var i=0;i<rolelist.length;i++){
                    if(i==(rolelist.length-1))
                        rolenamestr+=rolelist[i].name;
                    else
                        rolenamestr+=rolelist[i].name+'，';
                }
                $("#rolelist-info").append(
                    "<tr>" +
                    "<td>" + ((index + 1) + parseInt(page-1)*10)+"</td>" +
                    "<td>" + item.role + "</td>" +
                    "<td>" + rolenamestr + "</td>" +
                    "<td>" +
                    "<span class='clickword'data-toggle='modal' data-target='#addroleModal' onclick=\"openmodel(2,'"+item.id+"')\">编辑</span>" +
                    "</td>" +
                    "</tr>"
                );
            })
            getallroles();
        }
    })
}
//打开新增、编辑模态框:1：新增，2：编辑
function openmodel(type,editid){
    if(type==1){
        isadd=true;
        $("#addroletitle").text('新建角色');
        $("#addrolebtn").attr('onclick','sendroles()');
        //$("#role_name").removeAttr('readonly');
        $("#role_name").val('');
        getallroles(-1);
    }else{
        isadd=false;
        $("#addrolebtn").attr('onclick','sendroles("'+editid+'")');
        $("#addroletitle").text('编辑角色');
        //$("#role_name").attr('readonly','readonly');
        $.ajax({
            url: 'UserRoleByID',
            type: "post",
            dataType: "json",
            data : {
                'id' : editid
            },
            success: function (data) {
                //console.log(JSON.stringify(data));
                $("#role_name").val(data.role);
                getallroles(data.permissionENs);
            }
        })
    }

}


//获取全部权限
function getallroles(editroles){
    $.ajax({
        url: 'PermissionList',
        type: "post",
        dataType: "json",
        success: function (data) {
            var rolenodes=[];
            $(data).each(function(index,item){
                var node={
                    'id':item.id,
                    'pId':item.group,
                    'name':item.name
                };
                rolenodes.push(node);
            })
            $.fn.zTree.init($("#addroleTree"), rolesetting, rolenodes);
            if(editroles!=-1){
                var treeObj = $.fn.zTree.getZTreeObj("addroleTree");
                $(editroles).each(function(index,item){
                    var node = treeObj.getNodeByParam("id", item.id);
                    treeObj.checkNode(node, true, true);
                })
            }
        }
    })
}

var isadd;
/**
 * 编辑，新增权限:
 * @param isadd :true 新增 ，false 编辑
 */
function sendroles(editid){
    var datastr={};
    datastr['rolename']=$.trim($("#role_name").val());
    var actionname;
    var okstr;
    if(isadd){
        if($.trim($("#role_name").val())==''||$.trim($("#role_name").val())==null){
            alert('请输入角色名称');
            return
        }
        //datastr['rolename']=$.trim($("#role_name").val());
        okstr='新增成功';
        actionname='NewRole';
    }else{
        okstr='编辑成功';
        actionname='updateUserRoleByID';
        datastr['id']=editid;
    }
    var treeObj = $.fn.zTree.getZTreeObj("addroleTree");
    var nodes = treeObj.getCheckedNodes(true);
    var ids=[];
    for(i=0;i<nodes.length;i++){
        ids.push(nodes[i].id);
    }
    datastr['Rids']=ids;
    $.ajax({
        url: actionname,
        type: "post",
        dataType: "json",
        data : datastr,
        success: function (data) {
            alert(okstr);
            $('#addroleModal').modal('hide');
            showTabledata('UserRoles',1);
        }
    })
}
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