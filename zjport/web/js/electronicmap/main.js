/**
 * Created by Will on 2016/11/4.
 */
$(document).ready(function(){
    $("#viewcheck").addClass("active");
    $("#viewmap_li").addClass("active");
    showTree();
    mapmake();
    //queryCameras(106);
});

var showTree = function(){
    $.ajax({
        async: false,
        url: "../eleMap/queryRegionList",
        dataType: "json",
        type: "post",
        success: function (data) {
            $(data.records.data).each(function(index,item){
                var node={
                    "id":item.regionId,
                    "describe":item.name,
                    "nodename":item.name,
                    "name":"<div class='turnStructurediv' style='margin-right: 75px;' ><span ><i class='fa fa-folder'></i>&nbsp;"+item.name+"</span></div>",
                    "pId":item.parentId,
                    "isfirstlvl":true
                };
                listtreeNodes.push(node);
            });
            treemake();
        },
        error: function (xOption, status) {
        }
    });
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
    },
    callback: {
        onClick: zTreeOnClick
    }
};
function zTreeOnClick(event, treeId, treeNode) {
   if(treeNode.isfirstlvl){
       if(!treeNode.isParent){
           var rid = treeNode.id || 0;
           $.ajax({
               async: false,
               url: "../eleMap/queryCameras",
               dataType: "json",
               data:{
                   regionId:rid
               },
               type: "post",
               success: function (data) {
                   var treeObj = $.fn.zTree.getZTreeObj("konwledgeTree");
                   $(data.records.data).each(function(index,item) {
                       var node = {
                           "id": item.cameraId,
                           "describe": item.name,
                           "nodename": item.name,
                           "name": "<div class='turnStructurediv' style='margin-right: 75px;' ><span data-toggle='modal' data-target='#videomodal' onclick=\"showvideo('"+item.name+"',"+item.cameraId+")\"><i class='fa fa-camera'></i>&nbsp;" + item.name + "</span></div>",
                           "isfirstlvl": false
                       };
                       treeObj.addNodes(treeNode, node);
                   })
               },
               error: function (xOption, status) {
               }
           });
       }
   }
}
//左侧树
function treemake(){
    var treeObj = $("#konwledgeTree");
    $.fn.zTree.init(treeObj, konwledgesetting, listtreeNodes);
}
//地图制作
function mapmake() {
    shipOrbitmap = new WebtAPI.WMap($$("mapdiv"));
    var lonlat = new WebtAPI.LonLat(120.12142, 30.753567);
    shipOrbitmap.setCenterByLonLat(lonlat, 6);
}

/**
 * 调取视频
 * @param videoname：视频站点名称
 * @param videoid：视频id
 */
var showvideo = function (videoname, videoid) {
    var cids = [];
    cids.push(videoid);
    $.ajax({
        url: '../smart/queryCameraParament?cid=' + cids,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.obj != null) {
                $("#videotopname").text("视频站点"+videoname);
                vidoePlay(data.obj);
            }
            else
                alert('暂无搜索结果');
        }
    })
}

var vidoePlay = function (param) {
    console.log(param.length);
    try {
        var PlayOCXObj = document.getElementById("RealTimePlayOcx");
        PlayOCXObj.SetCapturParam('D:\\temp', "0");
        setTimeout(function () {
            PlayOCXObj.SetWndNum(1);//设置播放窗口数量
            for (var i = 0; i < param.length && i < 25; i++) {
                var ret = PlayOCXObj.StartTask_Preview(param[i]);
                console.log(ret);
            }
        }, 2000);
    } catch (e) {
        console.log(e);
        $("#download-control").show();
    }
}

var stopView = function () {
    try{
        var PlayOCXObj = document.getElementById("RealTimePlayOcx");
        PlayOCXObj.StopAllPreview();
    }catch(e){
        console.error(e);
    }
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