$(document).ready(function(){
  $("#viewcheck").addClass("active");
  $("#shishiyulan_li").addClass("active");
  $("#clearbtn").bind("click",function(){
    $("#clearbtn").hide();
    $("#videoselectinput").val('');
  });
  $(".gjzkbtn").bind("click",function(){
    $(".gjzkbtn").toggle();
    $("#gjdiv").slideToggle();
  });
  $("#scjeditbtn").bind("click",function(){
    $("#qxbjlan,#tjsxlan").slideDown();
    var scltreeObj = $.fn.zTree.getZTreeObj("sclTree");
    var nodes = scltreeObj.getNodes();
    for (var i=0, l=nodes.length; i < l; i++) {
      scltreeObj.setChkDisabled(nodes[i], false);
    }
  });
  $("#qxbjbtn").bind("click",function(){
    $("#qxbjlan,#tjsxlan").slideUp();
    var scltreeObj = $.fn.zTree.getZTreeObj("sclTree");
    var nodes = scltreeObj.getNodes();
    for (var i=0, l=nodes.length; i < l; i++) {
      scltreeObj.checkNode(nodes[i], false);
      scltreeObj.setChkDisabled(nodes[i], true);
    }
  });
  $("#tsfhbtn").bind("click",function(){
    $("#selecttslan,#tjsxlan").slideUp();
  });
  $(".gjtjcheckbox").bind("click",function(){
    var ischecked=this.checked;
    var trid=$(this).attr('id')+'_tr';
    if(ischecked){
      $('#'+trid).show();
    }else{
      $('#'+trid).hide();
    }
  });
  $("#videoselectinput").bind('input propertychange', function() {
    if($('#videoselectinput').val()!=''&&$('#videoselectinput').val()!=null){
      $("#clearbtn").show();
    }
  });
})
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
    },
    key: {
      title: "t"
    }
  }
};

var sclsetting={
  view: {
    nameIsHTML: true,
    showLine: false,
    showIcon: false,
    selectedMulti: false,
    dblClickExpand: false
    //addDiyDom: addDiyDom
  },
  check: {
    enable: true
  },
  data: {
    simpleData: {
      enable: true
    },
    key: {
      title: "t"
    }
  }
}
var listtreeNodes =[
  { id:1, pId:0, name: "<span><i  class='fa fa-folder-o'></i>&nbsp;主控中心（</span><span class='blueword' style='color:#0072b1;'>34</span>/45）</div> " , open:true,t:"主控中心"},
  { id:2, pId:0, name: "<span><i  class='fa fa-folder-o'></i>&nbsp;各级文件</span> </div> " , open:true},
  { id:3, pId:0, name: "<span><i  class='fa fa-folder-o'></i>&nbsp;标准规范</span></div> " , open:true},
  { id:4, pId:1, name: "<span><i  class='fa fa-video-camera'></i>&nbsp;海事安全法规</span><div class='qzidiv' > <i id='listtreei_4' class='fa fa-star' onclick=\"addtoscl(this,4)\"></i> </div>" , open:true,"nameword":"海事安全法规"},
  { id:5, pId:1, name: "<span><i  class='fa fa-video-camera'></i>&nbsp;海事安全法规1</span><div class='qzidiv' > <i id='listtreei_5' class='fa fa-star' onclick=addtoscl(this,5)></i> </div>" , open:true,"nameword":"海事安全法规1"},
  { id:6, pId:1, name: "<span><i  class='fa fa-video-camera'></i>&nbsp;海事安全法规2</span><div class='qzidiv' > <i id='listtreei_6' class='fa fa-star' onclick=addtoscl(this,6)></i> </div>" , open:true,"nameword":"海事安全法规2"},
  { id:7, pId:1, name: "<span><i  class='fa fa-video-camera'></i>&nbsp;海事安全法规3</span><div class='qzidiv' > <i id='listtreei_7' class='fa fa-star' onclick=addtoscl(this,7)></i> </div>" , open:true,"nameword":"海事安全法规3"},
  { id:8, pId:1, name: "<span><i  class='fa fa-video-camera'></i>&nbsp;海事安全法规4</span><div class='qzidiv' > <i id='listtreei_8' class='fa fa-star' onclick=addtoscl(this,8)></i> </div>" , open:true,"nameword":"海事安全法规4"},
];
var xzqhtreeNodes =[
  { id:0, pId:0, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;浙江</span>" , open:true},
  { id:3, pId:0, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;杭州</span>" , open:true},
  { id:2, pId:0, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;宁波</span>" , open:true},
  { id:1, pId:0, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;嘉兴</span>" , open:true},
  { id:4, pId:1, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;南湖</span>" , open:true},
  { id:5, pId:1, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;和云桥</span>" , open:true},
  { id:6, pId:1, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;梅溪</span>" , open:true},
  { id:7, pId:1, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;海宁</span>" , open:true},
  { id:8, pId:1, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;海盐</span>" , open:true},
  { id:9, pId:1, name: "<span onclick='addxzqh(this)'><i class='fa fa-folder'></i>&nbsp;平湖</span>" , open:true}
];
var scltreeNodes=[];
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
}

/**
 * 实时预览左边栏分组切换
 * @param thistab：点击标签
 * @param type：点击标识
 */
function phonetab(thistab,type){
  $(".phonefz").css('border','0');
  $(thistab).css({'border':'solid 1px #ccc','border-top':'solid 2px rgb(0,103,172)','border-bottom':'solid 2px white'});
  var treeObj = $.fn.zTree.getZTreeObj("konwledgeTree");
  var scltreeObj = $.fn.zTree.getZTreeObj("sclTree");
  switch (parseInt(type)){
    case 1 :
        $("#scjeditbtn").hide();
        $("#sclTree").hide();
        $("#konwledgeTree").show();
      treeObj.cancelSelectedNode();
      break;
    case 2 :
        $("#sclTree").show();
        $("#konwledgeTree").hide();
        $("#scjeditbtn").show();
      scltreeObj.cancelSelectedNode();
      var nodes = scltreeObj.getNodes();
      for (var i=0, l=nodes.length; i < l; i++) {
        scltreeObj.checkNode(nodes[i], false);
        scltreeObj.setChkDisabled(nodes[i], true);
      }
      break;
    case 3 :
      break;
    default :
      break;
  }
}

//添加上级目录
function addxzqh(event){
  $("#xzqhname").text($.trim($(event).text().substr(1,$(event).text().length)));
  $("#xzqhul").dropdown('toggle');
}

/**
 * 添加到收藏栏
 * @param event:对应点击按钮
 * @param id：节点id
 */
function addtoscl(event,id){
  var treeObj = $.fn.zTree.getZTreeObj("konwledgeTree");
  var scltreeObj = $.fn.zTree.getZTreeObj("sclTree");
  var node = treeObj.getNodeByParam("id", id, null);
  var name="<span><i  class='fa fa-video-camera'></i>&nbsp;"+node.nameword+"</span>"
  var scltreeNode={"id":node.id,"pId":0,"name":name};
  scltreeObj.addNodes(null, scltreeNode);
  alert('收藏成功');
  $(event).attr('onclick','isaddtoscl()');
}

//已添加到收藏栏
function isaddtoscl(){
  alert('已添加到搜藏栏');
}

//删除收藏栏
function delsclnode(){
  var scltreeObj = $.fn.zTree.getZTreeObj("sclTree");
  var nodes = scltreeObj.getCheckedNodes(true);
  if(nodes.length==0){
    alert('请选择删除监控点');
    return
  }
  for (var i=0, l=nodes.length; i < l; i++) {
    $("#listtreei_"+nodes[i].id).attr("onclick","addtoscl(event,"+nodes[i].id+")");
    scltreeObj.removeNode(nodes[i]);
  }
  alert('删除成功！');
  $("#qxbjlan,#tjsxlan").slideUp();
}

//搜索按钮点击
function selectbtnclick(){
  $("#selecttslan,#tjsxlan").slideDown();
}
