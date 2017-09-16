$(document).ready(function(){
    $("#cog").addClass("active");
    $("#datadictionary_li").addClass("active");
    $(".bqlabel").click(function(){
        $(".bqlabel").removeClass('bqlabelactive');
        $(this).addClass('bqlabelactive');
    });

    showTabledata('PortListPage',1);
})

function showTabledata(actionname,page) {
    $.ajax({
        url: actionname,
        type: "post",
        dataType: "json",
        data: {
            'page': page
        },
        success: function (data) {
            console.log(JSON.stringify(data));
            actiontype=actionname;
            pagingmake(actionname, 'showTabledata', page, data.pages);
            $("#rolelist-info").empty();
            $("#rolelist-info").append(
                "<tr style='background-color: rgb(240,245,248);'>" +
                "<th style='width:50px;'>编号</th>"+
                "<th>名称</th>"+
                "<th>操作</th>"+
                "</tr>"
            );
            $(data.list).each(function (index, item) {
                var name='';
                switch (actionname){
                    case 'PortListPage':
                        name=item.portname;
                        break;
                    case 'LawTypeListPage':
                        name=item.status;
                        break;
                    case 'DangerListPage':
                        name=item.rankname;
                        break;
                    case 'UnitListPage':
                        name=item.unitname;
                        break;
                    default :
                        break;
                }
                $("#rolelist-info").append(
                    "<tr>" +
                    "<td>" + ((index + 1) + parseInt(page-1)*10)+"</td>" +
                    "<td>" + name +"</td>" +
                    "<td>" +
                    "<span class='clickword'data-toggle='modal' data-target='#addroleModal' onclick=\"openmodel(2,'"+item.id+"','"+name+"')\">编辑</span>" +
                    "</td>" +
                    "</tr>"
                );
            })
        }
    })
}
//打开新增、编辑模态框:1：新增，2：编辑
function openmodel(type,editid,name){
    if(type==1){
        isadd=true;
        $("#addroletitle").text('新增字典数据');
        $("#addrolebtn").attr('onclick','sendroles()');
        $("#role_name").val('');
    }else{
        isadd=false;
        $("#addrolebtn").attr('onclick','sendroles("'+editid+'")');
        $("#addroletitle").text('编辑字典数据');
        $("#role_name").val(name);
    }

}
var actiontype;
var isadd;
/**
 * 编辑，新增权限:
 * @param isadd :true 新增 ，false 编辑
 */
function sendroles(editid){
    var datastr={};
    var actionname;
    var okstr;
        if($.trim($("#role_name").val())==''||$.trim($("#role_name").val())==null){
            alert('请输入字典数据内容');
            return
        }
        datastr['word']=$.trim($("#role_name").val());
    if(isadd){
        okstr='新增成功';
        switch (actiontype){
            case 'PortListPage':
                actionname='PorAdd';
                break;
            case 'LawTypeListPage':
                actionname='LawTypeAdd';
                break;
            case 'DangerListPage':
                actionname='DangerAdd';
                break;
            case 'UnitListPage':
                actionname='UnitAdd';
                break;
            default :
                break;
        }
    }else{
        okstr='编辑成功';
        switch (actiontype){
            case 'PortListPage':
                actionname='PortUp';
                break;
            case 'LawTypeListPage':
                actionname='LawTypeUp';
                break;
            case 'DangerListPage':
                actionname='DangerUp';
                break;
            case 'UnitListPage':
                actionname='UnitUp';
                break;
            default :
                break;
        }
        datastr['id']=editid;
    }
    $.ajax({
        url: actionname,
        type: "post",
        dataType: "json",
        data : datastr,
        success: function (data) {
            alert(okstr);
            $('#addroleModal').modal('hide');
            showTabledata(actiontype,1);
        }
    })
}