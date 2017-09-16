var shipOrbitmap;//轨迹地图
var cmbdivfocus=0;//船名列表判断
var gjlayer;//轨迹图层
var markerlayer;//标注图层
$(document).ready(function() {
    $("#smart").addClass("active");
    $("#shipOrbit_li").addClass("active");
    $("#cmhinput").bind({
        'input propertychange': function() {
            if($('#cmhinput').val()!=''&&$('#cmhinput').val()!=null){
                getcbmclist();
            }
        },
        'blur':function(){
            if(cmbdivfocus==0){
                $("#cmhdiv").hide();
            }
        }
    });
    $("#cmhdiv").bind({
        'mouseover':function(){
            cmbdivfocus=1;
        },
        'mouseout':function(){
            cmbdivfocus=0;
        }
    })
    shipOrbitmapmake();
})
/**
 * 船舶名实时监听
 */
function getcbmclist(){
    $.ajax({
        url:'../supervise/queryCms',
        type:'post',
        dataType:'json',
        data:{
            'cbmc':$('#cmhinput').val()
        },
        success:function(data){
            $("#cmhdiv").empty();
            var list=data.records.data;
            if(list.length==0){
                $("#cmhdiv").hide();
                return
            }
            for(var i=0;i<list.length;i++){
                $("#cmhdiv").append(
                    "<label onclick=cmhlabelclick('"+list[i]+"')>"+list[i]+"</label>"
                );
            }
            $("#cmhdiv").show();
        }
    })
}
/**
 * 实时监听点击方法
 * @param text
 */
function cmhlabelclick(text){
        $('#cmhinput').val(text);
    $("#cmhdiv").hide();
}
/**
 * 船舶轨迹生成
 */
function guijimake(){
    $.ajax({
        url:'../supervise/trajectory',
        type:'post',
        dataType:'json',
        data:{
            'cbmc':$('#cmhinput').val(),
            'startTime':$('#beginTime').val(),
            'endTime':$('#endTime').val()
        },
        success:function(data){
            gjlayer.removeAllFeatures();
            markerlayer.clearMarkers();
            var list=data.obj;
            if(list==null){
                alert('该时间段该船无轨迹');
                return
            }
            zbchange(list);
        }
    })
}
/**
 * 坐标转换
 * @param list：数据
 */
function zbchange(list){
    var data='';
    for(var i=0;i<list.length;i++){
        if(i==(list.length-1)){
            data+=list[i].jd+' '+list[i].wd;
        }else{
            data+=list[i].jd+' '+list[i].wd+',';
        }
    }
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': data
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = "";
            var points=[];
            jwd = data.response.result.point;
            var jwdsp = jwd.split(',');
            if (jwdsp != null && jwdsp.length > 0) {
                for (var i = 0; i < jwdsp.length; i++) {
                    var lonlat=new WebtAPI.LonLat(jwdsp[i].split(' ')[0], jwdsp[i].split(' ')[1]);
                    if(i==0||i==(jwdsp.length-1)){
                        var imgurl="../image/smart/map_ic_markers_start.png";
                        if(i!=0){
                            imgurl="../image/smart/map_ic_markers_end.png";
                        }
                        var icon = new WebtAPI.WIcon(imgurl, new WebtAPI.Size(25,40));//生成船图片
                        var marker= new WebtAPI.WMarker(lonlat, icon);//生成标注
                        markerlayer.addMarker(marker);//添加标注到船标图层
                    }
                    if(i==0){
                        shipOrbitmap.panToLonLat(lonlat);
                    }
                    points[i]=lonlat;
                }
                var style = {
                    //fillColor: "#E8F2FE",
                    //fillOpacity: 0.4,
                    //hoverFillColor: "white",
                    //hoverFillOpacity: 0.8,
                    strokeColor: "#00d55f",//轨迹颜色
                    //strokeOpacity: 0.7,
                    strokeWidth: 10,//轨迹宽度
                    //strokeLinecap: "round",
                    strokeDashstyle: "solid",
                    //hoverStrokeColor: "green",
                    //hoverStrokeOpacity: 5,
                    //hoverStrokeWidth: 0.2,
                    //pointRadius: 6,
                    //hoverPointRadius: 1,
                    //hoverPointUnit: "%",
                    //pointerEvents: "visiblePainted",
                    //cursor: "inherit"
                };
            var feature = new WebtAPI.LineFeature(points,style);
            gjlayer.addFeatures(feature);
            } else {
                alert("坐标转化失败");
            }
        }
    });
}
// 船舶轨迹地图生成
function shipOrbitmapmake() {
    shipOrbitmap = new WebtAPI.WMap($$("shipOrbitmap_div"));
    // var lonlat = new WebtAPI.LonLat(120.125122, 30.916621);
    var lonlat = new WebtAPI.LonLat(120.12142, 30.753567);
    // var lonlat = new WebtAPI.LonLat(120.270266,30.920194);
    shipOrbitmap.setCenterByLonLat(lonlat, 12);
    gjlayer = new WebtAPI.Layer.Vector("");
    markerlayer = new WebtAPI.MarkerLayer("");
    shipOrbitmap.addLayer(gjlayer);
    shipOrbitmap.addLayer(markerlayer);
    if(GetQueryString('hasship')==1){
        $('#cmhinput').val($('#shipname').val());
        guijimake();
    }
}
//href传参获取
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)
        return  unescape(r[2]);
}