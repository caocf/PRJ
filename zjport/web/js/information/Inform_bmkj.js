$(document).ready(function(){
    showDepartment();
    $("input[name=optionsRadiosinline]").click(function(){
        if($(this).attr('id')=='onlysee_radio'){
            $("#zdybm").hide();
        }else{
            $("#zdybm").show();
            $("#bmseldiv").empty();
            $("#bmseldiv").text('请选择部门，可多选');
        }
    })
})

function showDepartment() {
    $.ajax({
        url:"showOrgDepartment",
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
                    appendDeptTree(list);
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
var bmnodes =[];

function appendDeptTree(list) {
    for(var i=0;i<list.length;i++){
        var node = {"id":list[i].stDepartmentId,"name":list[i].stDepartmentName,"pId":0};
        bmnodes.push(node);
    }
}

var zTreeObj;

// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var setting = {
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
/*var bmnodes = [
    { "id":1, "name":"test1" ,"pId":0},
    { "id":2, "name":"test2","pId":1},
    { "id":3, "name":"test3","pId":1},
    { "id":4, "name":"test4","pId":3},
    { "id":5, "name":"test5","pId":3},
    { "id":6, "name":"test6","pId":2},
    { "id":7, "name":"test7","pId":2},
    { "id":8, "name":"test8","pId":2},
    { "id":9, "name":"test9" ,"pId":0}
];*/
//创建树
function bmtreemake(){
    zTreeObj = $.fn.zTree.init($("#bmTree"), setting, bmnodes);
}
var bmids='';
//生成部门
function bmmake(){
    var treeObj = $.fn.zTree.getZTreeObj("bmTree");
    var checkednodes = treeObj.getCheckedNodes(true);
    $('#bmModal').modal('hide');
    if(checkednodes.length==0){
        return
    }
    bmids='';
    $("#bmseldiv").empty();
    for(var i=0;i<checkednodes.length;i++){
        var width = checkednodes[i].name.length * 15 + 50;
        $("#bmseldiv").append(
            "<div style='margin-bottom:10px;height:27px;float: left;margin-left:10px;width:" + width + "px;color: #333;line-height: 27px;padding:0 5px;background-color: rgb(250,250,250);border: solid 1px #ccc;'>" +
            checkednodes[i].name + "<span class='bmclosebutton' style='font-size: 20px;font-weight:700;line-height: 27px;float: right;cursor: pointer;'onclick='removebmdiv(this)'>×</span>" +
            "</div>"
        );
        if(i==(checkednodes.length-1)) {
            bmids += checkednodes[i].id;
        } else {
            bmids += checkednodes[i].id + ',';
        }
    }
    $("#bmids").val(bmids);
}
//删除部门标签
function removebmdiv(event){
    $(event).parent().remove();
    if($(".bmclosebutton").size()==0){
        $("#bmseldiv").text('请选择部门，可多选');
    }
}