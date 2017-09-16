$(document).ready(function(){
  $("#officeAssistant").addClass("active");
  $("#knowledgeBase_li").addClass("active");

  showStructure();
  showStructureAdd();
  //turnStructure(1,"法律法规");
})

function treemake(){
  var treeObj = $("#konwledgeTree");
  $.fn.zTree.init(treeObj, konwledgesetting, listtreeNodes);
$(".turnStructurediv:eq(0)").click();
  //treeObj.find('a').hover(function () {
  //  if (!$(this).parent().hasClass("showIcon")) {
  //    if(!$(this).find('.button').hasClass("noline_docu")){
  //      $(this).parent().addClass("showIcon");
  //    }
  //  }
  //}
  //    , function() {
  //      $(this).parent().removeClass("showIcon");
  //}
  //);

}

var addmltreeNodes=[];
//显示新增中的组织架构
function showStructureAdd() {
  $.ajax({
    url: "showStructure",
    type: 'post',
    dataType: 'json',
    success: function (data) {
      if (data.resultcode == -1) {
        BackToLoginPage();
      } else if (data.resultcode == 0) {
        var list = data.records.data;
        if (list == "") {
          //WebIsNull();
        } else {
          for (var int = 0; int < list.length; int++) {
            var node;
            node = {
              "id": list[int].stStructureId,
              "name": "<div><span onclick='addsjml(this,"+list[int].stStructureId+")'><i class='fa fa-folder'></i>&nbsp;" + list[int].stStructureName + "</span></div>",
              "pId": list[int].stParentStructureId,
              "open": true,
              "nodename":list[int].stStructureName
            };
            addmltreeNodes.push(node);
          }
          node = {
            id: 0,
            pId: 0,
            name: "<div><span onclick='addsjml(this,0)'><i class='fa fa-folder'></i>&nbsp;知识库</span></div>",
            open: true,
            "nodename":"知识库"
          };
          addmltreeNodes.push(node);
        }
      } else {
        alert(data.result.resultdesc);
      }

    }/*,
     complete : function() {
     CloseLoadingDiv();
     }*/
  });
}
var listtreeNodes = [];
var movetreeNodes = [];
//显示知识库架构
function showStructure() {

  $.ajax({
    url:"showStructure",
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
          listtreeNodes = [];
          for(var int=0;int<list.length;int++){
            var node;
            var movenode;
            node={"id":list[int].stStructureId,
              "describe":list[int].stStructureDescribe,
              "nodename":list[int].stStructureName,
              "name":"<div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash' onclick=deleteNode("+list[int].stStructureId+")></i> </div> <div class='qzidiv' > <i onclick=addmltreemake("+list[int].stParentStructureId+","+list[int].stStructureId+") data-toggle='modal' data-target='#addmlModal' class='fa fa-pencil-square-o'></i> </div><div class='turnStructurediv' style='margin-right: 75px;' onclick=turnStructure("+list[int].stStructureId+",'"+list[int].stStructureName+"')><span ><i class='fa fa-folder'></i>&nbsp;"+list[int].stStructureName+"</span></div>",
              "pId":list[int].stParentStructureId
            };
            movenode={"id":list[int].stStructureId, "nodename":list[int].stStructureName, "name":"<div><span onclick=checkthismove("+list[int].stStructureId+",'"+list[int].stStructureName+"')><i class='fa fa-folder'></i>&nbsp;"+list[int].stStructureName+"</span></div>","pId":list[int].stParentStructureId,"open":true};
            listtreeNodes.push(node);
            movetreeNodes.push(movenode);
          }
          treemake();
          movetreemake();
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

//新增文件
function add() {
  var structureId = $("#structureId").val();
  window.location.href='addFile?structureId='+structureId;
}

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

//切换知识库文件夹
function turnStructure(structureId,structureName) {
  $("#structureId").val(structureId);
  $("#structureTitle").html(structureName)
  turnIn("showKnowledgeFile",1);
}

//查找相应文件夹下的文件
function turnIn(actionName,selectedPage) {
  $(".bootpagediv").show();
  $("#nulltablediv").hide();
  var search = $("#gguserselectinput").val();
  var structureId = $("#structureId").val();
  $.ajax({
    url:actionName,
    type:'post',
    dataType:'json',
    data:{
      'page':selectedPage,
      'rows':10,
      'structureId':structureId,
      'search':search
    },
    success:function(data){
      $(".addTr").empty();
      $(".addTr").remove();
      if(data.resultcode==-1){
        BackToLoginPage();
      }else if(data.resultcode==0){
        var list=data.records.data;
        $("#pagedetal").empty();
        $("#pagedetal").text(
            "当前页"+list.length+"条记录 共"+data.records.total+"条记录，每页"+data.records.rows+"条"
        );
        pagingmake(actionName,'turnIn',selectedPage,data.records.pages);
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

function appendToTable(list){
  var num = 0;
  for(var i=0;i<list.length;i++){
    var newTr;
    //var isShowMap = "style='color:#aaa;' ";
    num = i+1;

    newTr = $("<tr class='addTr'></tr>");
    //多选框
     newTr.append($("<td class='center'><input type='checkbox' name='file' value='"+list[i].stBaseId+"' id='"+list[i].stBaseId+"'/></td>"));
    //序号
    newTr.append($("<td class='center'>"+num+"&nbsp;</td>"));
    //标题
    newTr.append($("<td>"+list[i].stBaseTitle+"&nbsp;</td>"));
    //创建时间
    newTr.append($("<td>"+list[i].dtSend+"&nbsp;</td>"));
    //操作
    newTr.append($("<td class='center'>&nbsp;<a href=baseDetail?id="+list[i].stBaseId+" class='Operate'>查看</a>&nbsp;&nbsp;" +
        "<a href=baseEdit?id="+list[i].stBaseId+" class='Operate'>编辑</a>&nbsp;&nbsp;" +
        "<a href='#' class='Operate' onclick=batchDeleteOrOne("+list[i].stBaseId+") data-toggle='modal' data-target='#myModal'>删除</a></td>"));

    $("#txltable").append(newTr);
  }
}

function TableIsNull(){
  $(".bootpagediv").hide();
  $("#nulltablediv").show();
}

function searchIt() {
  turnIn("showKnowledgeFile",1);
}

function deleteIt() {
  var id = $("#baseId").val();
  window.location.href = "deleteBase?id="+id;
}
/*var listtreeNodes =[
  { id:1, pId:0, name: "<span><i class='fa fa-folder'></i>&nbsp;法律法规</span><div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash'></i> </div> <div class='qzidiv' > <i onclick='addmltreemake()' data-toggle='modal' data-target='#addmlModal'class='fa fa-pencil-square-o'></i> </div>" , open:true},
  { id:2, pId:0, name: "<span><i class='fa fa-folder'></i>&nbsp;各级文件</span><div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash'></i> </div> <div class='qzidiv' > <i onclick='addmltreemake()' data-toggle='modal' data-target='#addmlModal'class='fa fa-pencil-square-o'></i> </div>" , open:true},
  { id:3, pId:0, name: "<span><i class='fa fa-folder'></i>&nbsp;标准规范</span><div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash'></i> </div> <div class='qzidiv' > <i onclick='addmltreemake()' data-toggle='modal' data-target='#addmlModal'class='fa fa-pencil-square-o'></i> </div>" , open:true},
  { id:4, pId:1, name: "<span><i class='fa fa-folder'></i>&nbsp;海事安全法规</span><div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash'></i> </div> <div class='qzidiv' > <i onclick='addmltreemake()' data-toggle='modal' data-target='#addmlModal'class='fa fa-pencil-square-o'></i> </div>" , open:true},
];*/
/*var addmltreeNodes =[
  { id:0, pId:0, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;知识库</span>" , open:true},
  { id:1, pId:0, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;法律法规</span>" , open:true},
  { id:2, pId:0, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;各级文件</span>" , open:true},
  { id:3, pId:0, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;标准规范</span>" , open:true},
  { id:4, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
  { id:5, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
  { id:6, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
  { id:7, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
  { id:8, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
  { id:9, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true}
];*/
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

//新增目录树生成
function addmltreemake(pid,id){
  $("#parentStructure").val("");
  $("#structureName").val("");
  $("#structureDescribe").val("");

  $("#sjmlname").text('请选择上级目录');
  var treeObj = $("#addmlTree");
  $.fn.zTree.init(treeObj, konwledgesetting, addmltreeNodes);
  if(pid!=-100){
    getsjml(pid);
    $("#modalName").text("修改目录");

    var treeObj1 = $.fn.zTree.getZTreeObj("konwledgeTree");
    var node = treeObj1.getNodeByParam("id",id, null);
    $("#structure").val(id);
    $("#parentStructure").val(pid);
    $("#structureName").val(node.nodename);
    $("#structureDescribe").val(node.describe);
  } else {
    $("#modalName").text("添加目录");
  }
  //treeObj.hover(function () {
  //  if (!treeObj.hasClass("showIcon")) {
  //    treeObj.addClass("showIcon");
  //  }
  //}, function() {
  //  treeObj.removeClass("showIcon");
  //});
  $("#addmlul").find('a').attr('data-stopPropagation',true);
  $("#addmlul").on("click", "[data-stopPropagation]", function(e) {
    e.stopPropagation();
  });
}
//移动栏树生成
function movetreemake(){
  var treeObj = $("#moveTree");
  $.fn.zTree.init(treeObj, konwledgesetting, movetreeNodes);
  //treeObj.hover(function () {
  //  if (!treeObj.hasClass("showIcon")) {
  //    treeObj.addClass("showIcon");
  //  }
  //}, function() {
  //  treeObj.removeClass("showIcon");
  //});
  $("#moveul").find('a').attr('data-stopPropagation',true);
  $("#moveul").on("click", "[data-stopPropagation]", function(e) {
    e.stopPropagation();
  });
}
/**
 * 选中搜索栏目录
 * @param id：对应id
 * @param name：名称
 */
function checkthismove(id,name){
  $("#movename").text(name);
  $("#moveul").dropdown('toggle');

  var moveAll = "";

  $("input[name='file']").each(function() {
    if(this.checked) {
      if(moveAll=="") {
        moveAll +=$(this).val();
      } else {
        moveAll += ","+$(this).val();
      }
    }
  });
  if(moveAll == "") {
    alert("请选择需要移动的文件！");
    return;
  }
  $.ajax({
    type : "POST",
    url : "batchMove",
    datatype : "json",
    data : {'moveAll':moveAll,'parentId':id},
    success : function(data) {
      turnIn("showKnowledgeFile",1);
      alert("移动成功");
    },
    error : function() {
      alert("批量移动出错！");
    }
  });
}
//添加上级目录
function addsjml(event,id){
  $("#sjmlname").text($.trim($(event).text().substr(1,$(event).text().length)));
  $("#addmlul").dropdown('toggle');
  $("#parentStructure").val(id);
}

//获取上级目录
function getsjml(id){
  var treeObj = $.fn.zTree.getZTreeObj("addmlTree");
  var node = treeObj.getNodeByParam("id",id, null);
  $("#sjmlname").text(node.nodename);
}

//发送或更新
function send() {
  if($("#parentStructure").val() == "") {
    alert("上级目录不能为空！");
  } else if($("#structureName").val() == ""){
    alert("目录名称不能为空！");
  } else {
    $.ajax({
      type : "POST",
      url : "structureSubmit",
      datatype : "json",
      data : $("#structureForm").serializeArray(),
      success : function(data) {

        $('#addmlModal').modal('hide');
        showStructure();
        showStructureAdd();
        alert("成功！");
      },
      error : function() {
        alert("保存出错！！");
      }
    });
  }
}

//删除节点
function deleteNode(id) {
  var treeObj1 = $.fn.zTree.getZTreeObj("konwledgeTree");
  var node = treeObj1.getNodesByParam("pId",id, null);
  if(node.length > 0) {
    alert("该目录下有子目录无法删除，请先删除下面子目录！");
    return;
  } else {
    $.ajax({
      type : "POST",
      url : "structureDelete",
      datatype : "json",
      data : {'id':id},
      success : function(data) {
        showStructure();
        alert("删除成功！");
      },
      error : function() {
        alert("删除出错！！");
      }
    });
  }
}

//全选
function choose() {
  var objs = document.getElementsByName("file");

  if(document.getElementById('allSelect').checked) {
    for(var i=0;i<objs.length;i++) {
      if (objs[i].disabled==false){
        objs[i].checked = true;
      }
    }
  }
  else {
    for(var i=0;i<objs.length;i++) {
      if (objs[i].disabled==false){
        objs[i].checked = false;
      }
    }
  }
}

//单个删除或者批量删除
function batchDeleteOrOne(id) {
  var fileAll = "";
  if(id==null || id=="") {
    $("input[name='file']").each(function() {
      if(this.checked) {
        if(fileAll=="") {
          fileAll +=$(this).val();
        } else {
          fileAll += ","+$(this).val();
        }
      }
    });
  } else {
    fileAll = id;
  }

  if(fileAll == "") {
    alert("请选择需要删除的文件！");
    return;
  }
  $.ajax({
    type : "POST",
    url : "batchDelete",
    datatype : "json",
    data : {'fileAll':fileAll},
    success : function(data) {
      turnIn("showKnowledgeFile",1);
      alert("删除成功");
    },
    error : function() {
      alert("批量删除出错！");
    }
  });
}