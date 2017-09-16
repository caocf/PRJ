/**
 * Created by TWQ on 2016/8/4.
 */
$(document).ready(function(){
    $("#information").addClass("active");
    $("#inform-send").addClass("active");
    $("#delfile").click(function(){
        //清空file组件的值
        var html=document.getElementById('uploadspan').innerHTML;
        document.getElementById('uploadspan').innerHTML=html;

        $("#filepath").val("");
    });

    $('#templet1,#templet2').children().bind({
        'mouseover':function(){
            $(this).parent().popover('show');
        },
        'mouseout':function(){
            $(this).parent().popover('hide');
        },
    })
})
//显示文件名
function selectFile()
{
    $("#filepath").val($("#uploadFile").val());
}
//初始化导入div
function daoru(){
    var html=document.getElementById('uploadspan').innerHTML;
    document.getElementById('uploadspan').innerHTML=html;

    $("#filepath").val("");
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
var nodeslvl2=[];
var nodeslvl3=[];
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
                        nodeslvl2.push(node);
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
                        nodeslvl3.push(node);
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

$("#modal-custom").iziModal({
    overlayClose: false,
    width: 600,
    autoOpen: false,
    overlayColor: 'rgba(0, 0, 0, 0.6)',
    onOpened: function() {
        console.log('onOpened');
    },
    onClosed: function() {
        console.log('onClosed');
    }
});
$(document).on('click', '.trigger-custom', function (event) {
    event.preventDefault();
    $('#modal-custom').iziModal('open');
});


$("#modal-custom").on('click', 'header a', function(event) {
    event.preventDefault();
    var index = $(this).index();
    $(this).addClass('active').siblings('a').removeClass('active');
    $(this).parents("div").find("section").eq(index).removeClass('hide').siblings('section').addClass('hide');

    if( $(this).index() === 0 ){
        $("#modal-custom .iziModal-content .icon-close").css('background', '#ddd');
    } else {
        $("#modal-custom .iziModal-content .icon-close").attr('style', '');
    }
});

$("#modal-custom").on('click', '.submit', function(event) {
    event.preventDefault();

    var fx = "wobble",  //wobble shake
        $modal = $(this).closest('.iziModal');

    if( !$modal.hasClass(fx) ){
        $modal.addClass(fx);
        setTimeout(function(){
            $modal.removeClass(fx);
        }, 1500);
    }
});
//短信添加联系人分组切换
    function phonetab(thistab){
        $(".phonefz").css('border','0');
        $(thistab).css({'border':'solid 1px #ccc','border-top':'solid 2px rgb(0,103,172)','border-bottom':'solid 2px white'});
    }
////联系人选中
//function lxrchecked(thischecked){
//    labeltoggle(thischecked);
//}
////添加选中联系人
//function addlxr(){
//    if($('#lxrdiv').children('.ischecked').attr('class').split(' ')[0]=='treelv_3'){
//        var html1=$('#lxrdiv').children('.ischecked').prop("outerHTML");
//        $('#addlxrdiv').append(html1);
//    }else{
//    getnextlv($('#lxrdiv').children('.ischecked')).filter(".treelv_3").each(function(){
//        var html2=$(this).prop("outerHTML");
//        $('#addlxrdiv').append(html2);
//    });
//    }
//    $('#addlxrdiv').children('.ischecked').removeClass('ischecked');
//}
////清空联系人
//function clearlxr(){
//    $('#addlxrdiv').children('label').remove();
//}
//
////删除联系人
//function dellxr(){
//    $('#addlxrdiv').children('.ischecked').remove();
//}

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
    //addlvl2lvl3();
}
function addlvl2lvl3(){
    var treeObj = $.fn.zTree.getZTreeObj("messageTree");
            treeObj.addNodes(null,nodeslvl2);
    //for(var i=0;i<nodeslvl2.length;i++){
    //    var funode = treeObj.getNodeByParam("id", nodeslvl2[i].pId, null);
    //    if(funode==null)
    //        treeObj.addNodes(null,-1,nodeslvl2[i]);
    //    else
    //        treeObj.addNodes(funode,0,nodeslvl2[i],true);
    //}
    //for(var i=0;i<nodeslvl3.length;i++){
    //    var funode = treeObj.getNodeByParam("id", nodeslvl3[i].pId, null);
    //    treeObj.addNodes(funode,-1,nodeslvl3[i],true);
    //}
}
//添加联系人
function addlxr() {
    var treeObj = $.fn.zTree.getZTreeObj("messageTree");
    var checkednodes = treeObj.getCheckedNodes(true);
    var rnodes=[];
    for(var int=0;int<checkednodes.length;int++){
        if(checkednodes[int].phone!=0){
            var node={"id":checkednodes[int].id, "name":checkednodes[int].name,'open':true,"phone":checkednodes[int].phone};
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
    for(var int=0;int<allnodes.length;int++){
        /*if(int==(allnodes.length-1)){
        showlxrstr+=allnodes[int].phone
        }else{
        showlxrstr+=allnodes[int].phone+';'
        }*/
        showlxrstr+=allnodes[int].phone+',';
    }
    $("#showlxrstr").val(showlxrstr);
}

//点击发布判断是否为空并提交
function send(){
    if($("#showlxrstr").val() == "") {
        alert("请填写号码或选择发送对象！");
    } else {
        $("#messageForm").submit();
    }
}


//上传Excel
function uploadFile() {
    var path = $("#uploadFile").val();
    if(path==''||path==null){
        alert('请选择文件');
        return
    }
    var extname = path.substring(path.lastIndexOf(".")+1,path.length);
    extname = extname.toLowerCase();//处理了大小写
    if(extname!= "xls"&&extname!= "xlsx"){
        alert("只能上传后缀为.xls或.xlsx的表格");
        return;
    }
    $.ajaxFileUpload({
        url : '../file/excalImport',
        secureuri : false,
        fileElementId: ['uploadFile'],
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if(data.resultcode==-1){
                alert(data.resultdesc);
                return
            }
            $("#uploadFile").val("");
            $('#uploadModal').modal('hide');
            $("#showlxrstr").val($("#showlxrstr").val()+data.obj);
            alert('导入成功');
            /*$(".fileupdiv").hide();
            $(".fugaidiv").hide();*/
            //清空file组件的值
            /*var html=document.getElementById('uploadspan').innerHTML;
            document.getElementById('uploadspan').innerHTML=html;*/
        }
    });
}

function fileDownload() {
    window.location.href="../file/fileDownload";
}