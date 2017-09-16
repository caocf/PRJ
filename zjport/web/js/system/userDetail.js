/**
 * Created by TWQ on 2016/11/2.
 */
$(document).ready(function(){
    $("#system").addClass("active");
    $("#userManage_li").addClass("active");
})
function selectUser(id) {
    $.ajax({
        url:"showUserDetail",
        type:'post',
        dataType:'json',
        data : {
            'id':id
        },
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.obj.records.data;
                if(list==""){
                }else{
                    showUserDetail(list);
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

function showUserDetail(list) {
    var array = list[0];
    $("#user_name").text(array[0]);
    $("#user_position").text(array[1]);
    $("#user_org").text(array[2]);
    $("#user_dept").text(array[3]);
    $("#user_office_phone").text(array[4]);
    $("#user_phone").text(array[5]);
    $("#user_virtual").text(array[6]);
    $("#user_email").text(array[7]);
    $("#user_js").val(array[8]);
    $("#user_jsword").text(array[9]);
    $("#user_law").text(array[10]);
    $("#user_order").text(array[11]);

    $("#userLaw").val(array[10]);
    $("#userOrder").val(array[11]);
}

function showJs() {
    $.ajax({
        url:"showJsList",
        type:'post',
        dataType:'json',
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.obj.records.data;
                if(list==""){
                }else{
                    $("#userJs").empty();
                    if($("#user_js").val()==''||$("#user_js").val()==null){
                        $("#userJs").append(
                            "<option selected='selected' value='-1'>请选择角色</option>"
                        );
                        for(var int=0;int<list.length;int++){
                            $("#userJs").append(
                                "<option value='"+list[int].jsbh+"'>"+list[int].jsmc+"</option>"
                            );
                        }
                    }else{
                        $("#userJs").append(
                            "<option value='-1'>请选择角色</option>"
                        );
                        for(var int=0;int<list.length;int++){
                            var jsid=$("#user_js").val();
                            var seled='';
                            if(list[int].jsbh==jsid){
                                seled="selected='selected'";
                            }
                            $("#userJs").append(
                                "<option "+seled+" value='"+list[int].jsbh+"'>"+list[int].jsmc+"</option>"
                            );
                        }
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

function send() {
    var id= $("#userId").val();
    if($("#userJs").val() == "-1") {
        alert("请选择角色!");
    } else {
        $.ajax({
            type : "POST",
            url : "userDetailSubmit",
            datatype : "json",
            data : $("#userForm").serializeArray(),
            success : function(data) {

                $('#editModal').modal('hide');
                selectUser(id);
                alert("成功！");
            },
            error : function() {
                alert("保存出错！！");
            }
        });
    }
}
function treemake(){
    var treeObj = $("#addmlTree");
    $.fn.zTree.init(treeObj, konwledgesetting, listtreeNodes);

}
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
var listtreeNodes = [];
var konwledgesetting = {
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
function selectQx() {
    var js = $("#user_js").val();
    $.ajax({
        type : "POST",
        url : "selectQx",
        datatype : "json",
        data : {
            'js':js
        },
        success : function(data) {
            var list=data.obj.records.data;
            listtreeNodes=[];
            for(i=0;i<list.length;i++){
                var node={
                    "id":list[i][1],
                    "describe":list[i][0],
                    "nodename":list[i][0],
                    "name":"<div class='turnStructurediv' style='margin-right: 75px;' ><span ><i class='fa fa-key'></i>&nbsp;"+list[i][0]+"</span></div>",
                    "pId":list[i][2]
                };
                listtreeNodes.push(node);
            }
            treemake();
        },
        error : function() {
            alert("保存出错！！");
        }
    });
}