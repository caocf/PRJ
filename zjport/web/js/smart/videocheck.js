$(document).ready(function () {
    $("#viewcheck").addClass("active");
    getdata();
})
var nbnodes = [];
function nbtreemake() {
    var treeObj = $("#messageTree");
    zTreeObj = $.fn.zTree.init(treeObj, nbsetting, nbnodes);
    shipOrbitmapmake();
    $('#videomodal').on('hidden.bs.modal', function () {
        stopView();
    })
    $("#keyinput").bind({
        'input propertychange': function() {
            if($('#keyinput').val()!=''&&$('#keyinput').val()!=null){
                getname();
            }
        },
        'blur':function(){
            setTimeout("hidenamelistdiv()",100);
        }
    })
}
//隐藏搜索结果
function hidenamelistdiv(){
    $("#namelistdiv").hide();
}
//获取树结构数据
function getdata() {
    $.ajax({
        type: "POST",
        url: "../video/hdaotree",
        datatype: "json",
        data:{
            'xzqh':$("#userareaNo").val()
        },
        success: function (data) {
            //console.log(JSON.stringify(data))
            $(data.obj).each(function (index, item) {
                var isfirstlvl = true;
                if (item.pid == 0) {
                    isfirstlvl = false;
                }
                var isfirstlvl = true;
                var namestr = "<div><span><i class='fa fa-folder'></i>&nbsp;" + item.name + "</span></div>"
                if (item.type == 7) {
                    namestr = "<div class='qzidiv qxz qx15' onclick=\"dwvideo("+item.id.split('c')[(item.id.split('c').length-1)]+")\" style='margin-right: 10px;'> <abbr title='定位'><i  class='fa fa-map-pin'></i></abbr> " +
                                "</div><div class='qzidiv' onclick=\"showvideo("+item.id.split('c')[(item.id.split('c').length-1)]+")\" > <abbr title='播放'><i  class='fa fa-play-circle'></i></abbr>" +
                                "</div><div onclick=\"videoclick("+item.id.split('c')[(item.id.split('c').length-1)]+")\" class='turnStructurediv' style='margin-right: 75px;' ><span ><i class='fa fa-camera'></i>&nbsp;" + item.name + "</span></div>";
                }
                var node = {
                    "id": item.id,
                    "name": namestr,
                    "pId": item.pid,
                    "titleword": item.name,
                    "type": item.type,
                    "isfirstlvl": isfirstlvl
                };
                nbnodes.push(node);
            });
            setqx();
            nbtreemake();
        }
    })
}

//获取模糊查询名字
function getname() {
    $.ajax({
        type: "POST",
        url: "../video/queryhit",
        datatype: "json",
        data: {
            'ctype': $('#treetype').val(),
            'name': $('#keyinput').val(),
            'xzqh': $("#userareaNo").val()
        },
        success: function (data) {
            $("#namelistdiv").empty();
            if (data.records.data == null || data.records.data == '') {
                return
            }
            $(data.records.data).each(function (index, item) {
                $("#namelistdiv").append(
                    "<label onclick=\"getinputdata(" + item.cid + ",'" + item.cname + "')\">" + item.cname + "</label>"
                );
            })
            $("#namelistdiv").show();
        }
    })
}

//获取搜索结果
function getinputdata(id, name) {
    $('#keyinput').val(name);
    $.ajax({
        type: "POST",
        url: "../video/locationhit",
        datatype: "json",
        data: {
            'ctype': $('#treetype').val(),
            'id': id
        },
        success: function (data) {
            var treeObj = $.fn.zTree.getZTreeObj("messageTree");
            for (var i = 0; i < data.obj.length; i++) {
                var node = treeObj.getNodeByParam("id", data.obj[i].id, null);
                if (node == null) {
                    var pnode = treeObj.getNodeByParam("id", data.obj[i].pid, null);
                    var isfirstlvl = true;
                    var namestr = "<div><span><i class='fa fa-folder'></i>&nbsp;" + data.obj[i].name + "</span></div>"
                    if (data.obj[i].type == 7) {
                        namestr = "<div class='qzidiv qxz qx15' onclick=\"dwvideo("+data.obj[i].id.split('c')[(data.obj[i].id.split('c').length-1)]+")\" style='margin-right: 10px;'> <abbr title='定位'><i  class='fa fa-map-pin'></i></abbr> " +
                                  "</div> <div class='qzidiv' onclick=\"showvideo("+data.obj[i].id.split('c')[(data.obj[i].id.split('c').length-1)]+")\" > <abbr title='播放'><i class='fa fa-play-circle'></i></abbr>" +
                                  "</div><div class='turnStructurediv' onclick=\"videoclick("+data.obj[i].id.split('c')[(data.obj[i].id.split('c').length-1)]+")\" style='margin-right: 75px;' ><span ><i class='fa fa-camera'></i>&nbsp;" + data.obj[i].name + "</span></div>";
                    }
                    node = {
                        "id": data.obj[i].id,
                        "name": namestr,
                        "pId": data.obj[i].pid,
                        'titleword': data.obj[i].name,
                        'type': data.obj[i].type,
                        'isfirstlvl': isfirstlvl
                    };
                    treeObj.addNodes(pnode, node);
                    node = treeObj.getNodeByParam("id", data.obj[i].id, null);
                    treeObj.selectNode(node);
                }
                treeObj.selectNode(node);
            }
            setqx();
        }
    })
}

function isNull(content) {
    if (content == null) {
        return "";
    } else {
        return content;
    }
}

function TableIsNull() {
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
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
    },
    callback: {
        onClick: zTreeOnClick
    }

};
//点击方法
function zTreeOnClick(event, treeId, treeNode) {
    if (treeNode.isfirstlvl) {
        if (treeNode.type != 7) {

            treeNode.isfirstlvl = false;
            $.ajax({
                async: false,
                url: "../video/addhdaotree",
                dataType: "json",
                data: {
                    'id': treeNode.id,
                    'ctype': treeNode.type,
                    'xzqh': $("#userareaNo").val()
                },
                type: "post",
                success: function (data) {
                    var treeObj = $.fn.zTree.getZTreeObj("messageTree");
                    $(data.obj).each(function (index, item) {
                        var isfirstlvl = true;
                        var namestr = "<div><span><i class='fa fa-folder'></i>&nbsp;" + item.name + "</span></div>"
                        if (item.type == 7) {
                            namestr = "<div class='qzidiv qxz qx15' onclick=\"dwvideo("+item.id.split('c')[(item.id.split('c').length-1)]+")\" style='margin-right: 10px;'> <abbr title='定位'><i class='fa fa-map-pin' ></i></abbr> </div> " +
                                        "<div class='qzidiv' onclick=\"showvideo("+item.id.split('c')[(item.id.split('c').length-1)]+")\" > <abbr title='播放'><i class='fa fa-play-circle'></i></abbr> </div>" +
                                        "<div class='turnStructurediv' onclick=\"videoclick("+item.id.split('c')[(item.id.split('c').length-1)]+")\" style='margin-right: 75px;' ><span ><i class='fa fa-camera'></i>&nbsp;" + item.name + "</span></div>";
                        }
                        var node = treeObj.getNodeByParam("id", item.id, null);
                        if (node == null) {
                            node = {
                                "id": item.id,
                                "name": namestr,
                                "pId": item.pid,
                                'titleword': item.name,
                                'type': item.type,
                                'isfirstlvl': isfirstlvl
                            };
                            treeObj.addNodes(treeNode, node);
                        }
                    })
                    setqx();
                },
                error: function (xOption, status) {
                }
            });
        }
    }
}

//摄像头节点点击
function videoclick(id){
    $.ajax({
        async: false,
        url: "../video/querycamerabyid",
        dataType: "json",
        data: {
            'id': id
        },
        type: "post",
        success: function (data) {
            markerdatachange(data);
        }
    })
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
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
        switchObj.before(spaceStr);
    }
    $(".ztree").find('a').removeAttr('title');
}
var shipOrbitmap;//地图
var videolayer;//视频图层
//地图
function shipOrbitmapmake() {
    shipOrbitmap = new WebtAPI.WMap($$("shipOrbitmap_div"));
    // var lonlat = new WebtAPI.LonLat(120.125122, 30.916621);
    var lonlat = new WebtAPI.LonLat(120.12142, 30.753567);
    shipOrbitmap.setCenterByLonLat(lonlat, 5);
    videolayer = new WebtAPI.MarkerLayer("");
    shipOrbitmap.addLayer(videolayer);
    addvideomarker();
    queryHdaoXzqh();
}
//加载视频图标
function addvideomarker(){
    $.ajax({
        async: false,
        url: "../video/querycameras",
        dataType: "json",
        data: {
            'xzqh': $("#userareaNo").val()
        },
        type: "post",
        success: function (data) {
            $(data.records.data).each(function(index,item){
                if (item[1].lon != 0 && item[1].lat != 0) {
                    var lonlat=new WebtAPI.LonLat(item[1].lon, item[1].lat);
                    var imgurl = "../image/smart/map_ic_movie_normal.png";
                    var icon = new WebtAPI.WIcon(imgurl, new WebtAPI.Size(32, 32));//生成船图片
                    var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
                    marker.name=item[2].cameraName;
                    marker.hdmc=item[0].hdqdmc+'-'+item[0].hdzdmc;
                    marker.ipdz=item[2].deviceIp;
                    marker.cid=item[1].cid;
                    marker.hdaomc=item[3].hdmc;
                    marker.item=item;
                    videolayer.addMarker(marker);
                    marker.register(
                        'click',
                        function () {
                            shipdata=this.item
                            shipOrbitmap.clearPopups();
                            var html =  "<label class='poplabel' style='border-bottom: solid 1px #ccc;'>"+this.name+"</label>" +
                                        "<label class='poplabel'><span>视频名称：</span>"+this.name+"</label>"+
                                        "<label class='poplabel'><span>所属航道：</span>"+this.hdaomc+"</label>"+
                                        "<label class='poplabel'><span>所属航段：</span>"+this.hdmc+"</label>"+
                                        "<label class='poplabel' style='border-bottom: solid 1px #ccc;'><span>IP地址：</span>"+this.ipdz+"</label>"+
                                        "<div style='width: 100%;height:40px;'>" +
                                        "<button type='button' style='float:right' class='btn btn-default' onclick=\"editvideodata(2)\">编辑 </button>"+
                                        "<button type='button' style='float:right;margin-right:10px;' class='btn btn-primary' onclick=showvideo("+this.cid+")>播放视频 </button>"+
                                        "</div>"
                            var popup = new WebtAPI.WPopup('neirongpop', this.getLonLat(), 300,
                                html, true);
                            popup.setToolbarDisplay(false);
                            popup.setOffset(60, 60);
                            shipOrbitmap.addPopup(popup);
                        });
                }
            })
        }
    })
}
var addorupdataaction;//判断是否新增
var videoid;//视频id
/**
 * 视频数据处理
 * @param data：数据
 */
function markerdatachange(data) {
    if (data.map.locainfo==null) {
        alert('该视频点暂无坐标，请点击采集按钮');
        return
    }
    shipdata=data;
    var lonlat=new WebtAPI.LonLat(data.map.locainfo.lon, data.map.locainfo.lat);
    shipOrbitmap.clearPopups();
    var html =  "<label class='poplabel' style='border-bottom: solid 1px #ccc;'>"+data.map.baseinfo.cameraName+"</label>" +
        "<label class='poplabel'><span>视频名称：</span>"+data.map.baseinfo.cameraName+"</label>"+
        "<label class='poplabel'><span>所属航道：</span>"+data.map.hdaoinfo.hdmc+"</label>"+
        "<label class='poplabel'><span>所属航段：</span>"+data.map.hduaninfo.hdqdmc+"-"+data.map.hduaninfo.hdzdmc+"</label>"+
        "<label class='poplabel' style='border-bottom: solid 1px #ccc;'><span>IP地址：</span>"+data.map.baseinfo.deviceIp+"</label>"+
        "<div style='width: 100%;height:40px;'>" +
        "<button type='button' style='float:right' class='btn btn-default' onclick=\"editvideodata(1)\">编辑 </button>"+
        "<button type='button' style='float:right;margin-right:10px;' class='btn btn-primary' onclick=showvideo("+data.map.locainfo.cid+")>播放视频 </button>"+
        "</div>"
    var popup = new WebtAPI.WPopup('neirongpop', lonlat, 300,
        html, true);
    popup.setToolbarDisplay(false);
    popup.setOffset(60, 60);
    shipOrbitmap.addPopup(popup);
    shipOrbitmap.panToLonLat(lonlat);
}

//根据行政区划查航道
function queryHdaoXzqh(){
    $.ajax({
        async: false,
        url: "../video/queryhdaoxzqh",
        dataType: "json",
        data: {
            'xzqh': $("#userareaNo").val()
        },
        type: "post",
        success: function (data) {
            $("#checkhd").empty();
            $("#checkhd").append(
                "<option selected='selected' value='-1'>请选择航道</option>"
            );
            $(data.records.data).each(function(index,item){
                $("#checkhd").append(
                    "<option  value="+item.id+">"+item.hdmc+"</option>"
                );
            })
            $("#checkhd").change(function(){
                queryhduansshdid(-1);
            })
        }
    })
}

//根据航道id和行政区划查航段
function queryhduansshdid(hduanid){
    if($("#checkhd").val()==-1){
        $("#checkhduan").empty();
        $("#checkhduan").append(
            "<option selected='selected' value='-1'>请选择航段</option>"
        );
        return
    }
    $.ajax({
        async: false,
        url: "../video/queryhduansshdid",
        dataType: "json",
        data: {
            'xzqh': $("#userareaNo").val(),
            'sshdid':$("#checkhd").val()
        },
        type: "post",
        success: function (data) {
            $("#checkhduan").empty();
            $("#checkhduan").append(
                "<option value='-1'>请选择航段</option>"
            );
            $(data.records.data).each(function(index,item){
                $("#checkhduan").append(
                    "<option  value="+item.id+">"+item.hdqdmc+"-"+item.hdzdmc+"</option>"
                );
            })
            $("#checkhduan").val(hduanid);
        }
    })
}

//更新摄像头经纬度信息
function updatechannelcamera(){
    if($("#checkhduan").val()==-1){
        alert('请选择航段');
        return
    }
    var datastr={};
    datastr.cid=dwvideoid;
    datastr.hdid=$("#checkhduan").val();
    datastr.ctype=$("#videotype").val();
    datastr.lon=$("#lonlatinput").val().split(',')[0];
    datastr.lat=$("#lonlatinput").val().split(',')[1];
    datastr.nickname=$("#addressinput").val();
    if(addorupdataaction=='updatechannelcamera'){
        datastr['id']=videoid;
    }
    $.ajax({
        url: "../video/"+addorupdataaction,
        contentType:"application/json",
        cache:false,
        dataType: "json",
        data:JSON.stringify(datastr),
        type: "post",
        success: function (data) {
            alert('修改成功');
            $('#addzbModal').modal('hide');
            $('#shipOrbitmap_div').css('pointer','auto');
            shipOrbitmap.unregisterEvents("click",mapclick);
            window.location.reload();
        }
    })
}
var dwvideoid;
var shipdata;//定位船舶信息
var ifdw=true;//是否定位
//定位摄像头
function dwvideo(id){
    if(ifdw){
        ifdw=false;
        $("#shipOrbitmap_div").css('cursor',"url('../image/smart/map_point_blue.ico'),auto");
    }else{
        ifdw=true;
        $("#shipOrbitmap_div").css('cursor',"auto");
        return
    }
    $.ajax({
        async: false,
        url: "../video/querycamerabyid",
        dataType: "json",
        data: {
            'id': id
        },
        type: "post",
        success: function (data) {
            addorupdataaction='addchannelcamera';
            shipdata='';
            if (data.map.locainfo!=null) {
                addorupdataaction='updatechannelcamera';
                shipdata=data.map;
                videoid=shipdata.locainfo.id;
            }
            dwvideoid=id;
            shipOrbitmap.events.register(
                "click", this,mapclick
            )
        }
    })
}
//地图点击方法
function mapclick(e){
    var lonlat=shipOrbitmap.getLonLatFromPixel(e.xy).transform(new WebtAPI.Projection("EPSG:900913"),new WebtAPI.Projection("EPSG:4326"));
    $('#addzbModal').modal('show');
    $('#lonlatinput').val(lonlat.lon.toFixed(8)+','+lonlat.lat.toFixed(8));
    if(shipdata==''){
        $('#checkhd>option:eq(0),#videotype>option:eq(0)').attr('selected','selected');
        $("#checkhduan").empty();
        $("#addressinput").val('');
        $("#checkhduan").append(
            "<option selected='selected' value='-1'>请选择航段</option>"
        );
    }else{
        $("#addressinput").val(shipdata.locainfo.nickname);
        $("#videotype").val(shipdata.locainfo.ctype);
        $("#checkhd").val(shipdata.hdaoinfo.id);
        queryhduansshdid(shipdata.hduaninfo.id);
        videoid=shipdata.locainfo.cid;
    }
}
//视频编辑:type 1 树点击 2 地图点击
function editvideodata(type){
    console.log(JSON.stringify(shipdata))
    $('#addzbModal').modal('show');
    addorupdataaction='updatechannelcamera';
    if(type==1){
        $('#lonlatinput').val(shipdata.map.locainfo.lon+','+shipdata.map.locainfo.lat);
        $("#addressinput").val(shipdata.map.locainfo.nickname);
        $("#videotype").val(shipdata.map.locainfo.ctype);
        $("#checkhd").val(shipdata.map.hdaoinfo.id);
        queryhduansshdid(shipdata.map.hduaninfo.id);
        videoid=shipdata.map.locainfo.cid;
    }else{
        $('#lonlatinput').val(shipdata[1].lon+','+shipdata[1].lat);
        $("#addressinput").val(shipdata[1].nickname);
        $("#videotype").val(shipdata[1].ctype);
        $("#checkhd").val(shipdata[3].id);
        queryhduansshdid(shipdata[0].id);
        videoid=shipdata[1].cid;
    }
}
/**
 * 调取视频
 * @param videoname：视频站点名称
 * @param videoid：视频id
 */
var showvideo = function (videoid) {
    $('#videomodal').modal('show');
    var cids = [];
    cids.push(videoid);
    $.ajax({
        url: '../smart/queryCameraParament?cid=' + cids,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.obj != null) {
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

//checkbox全选
function checkedOrNo(obj) {
    var isCheck = obj.checked;
    $('.systemcheck').each(function () {
        this.checked = isCheck;
    });
}

