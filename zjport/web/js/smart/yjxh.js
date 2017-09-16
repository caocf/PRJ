$(document).ready(function () {
    $("#smart").addClass("active");
    $("#electronicCruise_id").addClass("active");
    $("#yjxh_li").addClass("active");
    $(".shiplist").click(function () {
        typedatacheck(this);
    });
    $(".shipxq").click(function () {
        qhshipdata(this);
    });
    $("#refreshbtn").click(function () {
        getxhdata(false);
        gettabledata('../supervise/yccb',1);
    });
    $("#areaselect").change(function(){
        getxhdata(true);
    });
    getallarea();
})
//获取地区下拉框
function getallarea() {
    $.ajax({
        url: '../supervise/areas',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if ($("#userareaNo").val() == 330000) {
                $("#areaselect").show();
                $("#qyname").hide();
                $("#areaselect").empty();
                $(data.records.data).each(function (index, item) {
                    $("#areaselect").append(
                        "<option value='" + item.stAreaCode + "'>" + item.stAreaName + "</option>"
                    );
                })
            } else {
                $("#areaselect").hide();
                $("#qyname").show();
                $(data.records.data).each(function (index, item) {
                    if(item.stAreaCode==$("#userareaNo").val()){
                        $("#qyname").text(item.stAreaName);
                    }
                })
            }
            getxhdata();
        }
    })
}
//获取地区相关巡航信息
function getxhdata() {
    $.ajax({
        url: '../supervise/yjxh',
        type: 'post',
        dataType: 'json',
        data: {
            area: $("#userareaNo").val() != 330000 ? $("#userareaNo").val() : $("#areaselect").val()
        },
        success: function (data) {
            $("#yjxhtopworddiv").empty();
            var wxpcolor="<span class='bluespanword' onclick=\"clicklisttab(0)\">"+data.map.wxcnt+"</span>";
            var hmdcolor="<span class='bluespanword' onclick=\"clicklisttab(1)\">"+data.map.hmdcnt+"</span>";
            var wzcolor="<span class='bluespanword' onclick=\"clicklisttab(2)\">"+data.map.wzcnt+"</span>";
            var zscolor="<span class='bluespanword' onclick=\"clicklisttab(3)\">"+data.map.zscnt+"</span>";
            var jfcolor="<span class='bluespanword' onclick=\"clicklisttab(4)\">"+data.map.jfcnt+"</span>";
            var czcolor="<span class='bluespanword' onclick=\"clicklisttab(5)\">"+data.map.czcnt+"</span>";
            var wtcolor="<span class='bluespanword' onclick=\"clicklisttab(6)\">"+data.map.wtcnt+"</span>";
            if(data.map.wxcnt==null){
                wxpcolor="<span class='cccspanword'>暂无数据</span>";
            }
            if(data.map.hmdcnt==null){
                hmdcolor="<span class='cccspanword'>暂无数据</span>";
            }
            if(data.map.wzcnt==null){
                wzcolor="<span class='cccspanword'>暂无数据</span>";
            }
            if(data.map.zscnt==null){
                zscolor="<span class='cccspanword'>暂无数据</span>";
            }
            if(data.map.jfcnt==null){
                jfcolor="<span class='cccspanword'>暂无数据</span>";
            }
            if(data.map.czcnt==null){
                czcolor="<span class='cccspanword'>暂无数据</span>";
            }
            if(data.map.wtcnt==null){
                wtcolor="<span class='cccspanword'>暂无数据</span>";
            }
            $("#yjxhtopworddiv").append(
                "<label class='toplabel'>" +
                "危险品船舶<br>" +
                wxpcolor +
                "</label>"+
                "<label class='toplabel'>" +
                "黑名单船舶<br>" +
                hmdcolor +
                "</label>"+
                "<label class='toplabel'>" +
                "违章船舶<br>" +
                wzcolor +
                "</label>"+
                "<label class='toplabel'>" +
                "证书异常<br>" +
                zscolor +
                "</label>"+
                "<label class='toplabel'>" +
                "规费未缴清<br>" +
                jfcolor +
                "</label>"+
                "<label class='toplabel'>" +
                "超载<br>" +
                czcolor +
                "</label>"+
                "<label class='toplabel' style='border-right: 0'>" +
                "违停<br>" +
                wtcolor +
                "</label>"
            );
                $(".shiplist:eq(0)").click();
        }
    })
}
//蓝色数字点击方法
function clicklisttab(no){
    $(".shiplist:eq("+no+")").click();
}
var tabletype;
//切换巡航相关信息
function typedatacheck(event) {
    $("#loglist-info").empty();
    $("#nulltablediv").hide();
    switch ($(event).text()) {
        case '危险品船舶':
            tabletype=1;
            break;
        case '黑名单船舶':
            tabletype=2;
            break;
        case '违章船舶':
            tabletype=3;
            break;
        case '证书异常船舶':
            tabletype=4;
            break;
        case '规费未缴清船舶':
            tabletype=5;
            break;
        case '超载船舶':
            tabletype=6;
            break;
        case '违停船舶':
            tabletype=7;
            break;
        default :
            break;
    }
    gettabledata('../supervise/yccb',1);
}

//获取列表数据
function gettabledata(actionname,page){
    $.ajax({
        url: actionname,
        type: 'post',
        dataType: 'json',
        data: {
            area: $("#userareaNo").val() != 330000 ? $("#userareaNo").val() : $("#areaselect").val(),
            type: tabletype,
            page: page
        },
        success: function (data) {
            if(data.resultcode==-1){
                TableIsNull();
            }else if(data.resultcode==0){
                var list=data.records.data;
                $("#loglist-info").empty();
                $("#pagedetal").empty();
                $("#pagedetal").text(
                    "当前页"+list.length+"条记录 共"+data.records.total+"条记录，每页"+data.records.rows+"条"
                );
                pagingmake(actionname,'gettabledata',page,data.records.pages);
                if(list==""){
                    TableIsNull();
                }else{
                    $("#loglist-info").append(
                        "<tr>" +
                        "<th style='width: 50px;'>序号</th>" +
                        "<th>监测时间</th>" +
                        "<th>船名号</th>" +
                        "<th>MMSI</th>" +
                        "<th>异常状态</th>" +
                        "<th>操作</th>" +
                        "</tr>"
                    );
                    $(list).each(function (index, item) {
                        var yctype='';
                        if(tabletype==1){
                            yctype="<div class='ztcolordiv' style='background: red'>危</div>";
                        }
                        if(tabletype!=1&&tabletype!=2){
                            if (item.peccancy == 1) {
                                yctype+="<div class='ztcolordiv' style='background: #ae0f0f'>违章</div>";
                            }
                            if (item.overdue == 1) {
                                yctype+="<div class='ztcolordiv' style='background: #ef3636'>证书异常</div>";
                            }
                            if (item.arrearage == 1) {
                                yctype+="<div class='ztcolordiv' style='background: #f77d4d'>规费未缴清</div>";
                            }
                        }
                        $("#loglist-info").append(
                            "<tr>" +
                            "<td>" + ((index + 1)+10*(page-1)) +"</td>" +
                            "<td>" + isnull(item.shipdate, '--', 0) + "</td>" +
                            "<td>" + isnull(item.shipname, '--', 0) + "</td>" +
                            "<td>" + isnull(item.ais, '--', 0) + "</td>" +
                            "<td>" + yctype + "</td>" +
                            "<td>" +
                            "<span class='clickword' onclick=\"cbgz('"+item.shipname+"')\">跟踪</span>&nbsp;" +
                            "<span class='clickword' onclick=\"getshipxq('"+item.shipname+"')\">详情</span>" +
                            "</td>" +
                            "</tr>"
                        );
                    })
                }
            }else{
                alert(data.resultdesc);
            }
        }
    })
}
function TableIsNull() {
    $("#nulltablediv").show();
    $(".bootpagediv").hide();
}
//船舶跟踪
function cbgz(shipname){
    $.ajax({
        url: '../supervise/cbmc',
        type: 'post',
        dataType: 'json',
        data : {
            'cbmc' : shipname
        },
        success: function (data) {
            window.open ("../cbgz.jsp", "船舶跟踪", "height=360, width=500, top=30, left=30, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
        }
    })
}
var shipdata
//船舶详情
function getshipxq(shipname){
    $.ajax({
        url: '../supervise/cbXq',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': shipname
        },
        success: function (data) {
            shipdata = data.map;
            if(shipdata==null){
                alert('暂无该船信息');
                return
            }
            $('#cbxqModal').modal('show')
            $("#titleshipname").text(shipname);
            $(".shipxq:eq(0)").click();
        }
    });
}
//切换船舶相关信息
function qhshipdata(event) {
    $("#shipdatatable").empty();
    $("#nulltablediv").hide();
    switch ($(event).text()) {
        case '基本信息':
            $("#shipdatatable").addClass('table-bordered');
            if (shipdata.jbxx == '' || shipdata.jbxx == null) {
                TableIsNull1();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<td class='colortd col-lg-2'>船舶名称：</td>" +
                "<td class='col-lg-4'>" + isnull(shipdata.jbxx.zwcm, '--', 0) + "</td>" +
                "<td class='colortd col-lg-2'>船籍港（代码）：</td>" +
                "<td class='col-lg-4'>" + isnull(shipdata.jbxx.cjgdm, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>船检登记号：</td>" +
                "<td>" + isnull(shipdata.jbxx.cjdjh, '--', 0) + "</td>" +
                "<td class='colortd'>船舶登记号：</td>" +
                "<td>" + isnull(shipdata.jbxx.cbdjh, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>船舶类型：</td>" +
                "<td>" + isnull(shipdata.jbxx.cblx, '--', 0) + "</td>" +
                "<td class='colortd'>船舶类型代码：</td>" +
                "<td>" + isnull(shipdata.jbxx.cblxdm, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>主机总功率：</td>" +
                "<td>" + isnull(shipdata.jbxx.zjzgl, '--', 0) + "</td>" +
                "<td class='colortd'>参考载货量：</td>" +
                "<td>" + isnull(shipdata.jbxx.ckzhl, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>总吨位：</td>" +
                "<td>" + isnull(shipdata.jbxx.zdw, '--', 0) + "</td>" +
                "<td class='colortd'>净吨位：</td>" +
                "<td>" + isnull(shipdata.jbxx.jdw, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>吃水空载（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.cskz, '--', 0) + "</td>" +
                "<td class='colortd'>吃水满载（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.csmz, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>总长（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.zc, '--', 0) + "</td>" +
                "<td class='colortd'>船长（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.cc, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>型宽（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.xk, '--', 0) + "</td>" +
                "<td class='colortd'>型深（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.xs, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>量吨甲板长（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.ldjbc, '--', 0) + "</td>" +
                "<td class='colortd'>船舶经营人：</td>" +
                "<td>" + isnull(shipdata.jbxx.jyr, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>船舶所有人：</td>" +
                "<td>" + isnull(shipdata.jbxx.syr, '--', 0) + "</td>" +
                "<td class='colortd'>所有人电话：</td>" +
                "<td>" + isnull(shipdata.jbxx.syrdh, '--', 0) + "</td>" +
                "</tr>"
            );
            break;
        case '证书信息':
            $("#shipdatatable").removeClass('table-bordered');
            if (shipdata.czxx == '' || shipdata.czxx == null) {
                TableIsNull1();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<th style='width: 50px;'>序号</th>" +
                "<th>证书名称</th>" +
                "<th>证书编号</th>" +
                "<th>发证日期</th>" +
                "<th>有效日期</th>" +
                    //"<th>操作</th>" +
                "</tr>"
            );
            $(shipdata.czxx).each(function (index, item) {
                var fzrq = '--';
                if (isnull(item.fzrq, '--', 0) != '--') {
                    fzrq = datecl(item.fzrq);
                }
                var yxrq = '--';
                if (isnull(item.yxrq, '--', 0) != '--') {
                    yxrq = datecl(item.yxrq);
                }
                $("#shipdatatable").append(
                    "<tr>" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + isnull(item.zsmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.zsbh, '--', 0) + "</td>" +
                    "<td>" + fzrq + "</td>" +
                    "<td>" + yxrq + "</td>" +
                        //"<td><span class='clickword'>详情</span></td>"+
                    "</tr>"
                );
            })
            break;
        case '违章信息':
            $("#shipdatatable").removeClass('table-bordered');
            if (shipdata.wzxx == '' || shipdata.wzxx == null) {
                TableIsNull1();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<th style='width: 50px;'>序号</th>" +
                "<th>案发时间</th>" +
                "<th>案发地点</th>" +
                "<th>案件类别</th>" +
                "<th>案由</th>" +
                "<th>处罚意见</th>" +
                    //"<th>操作</th>" +
                "</tr>"
            );
            $(shipdata.wzxx).each(function (index, item) {
                var afsj = '--';
                if (isnull(item.fasj, '--', 0) != '--') {
                    var date = new Date(parseInt(item.fasj));
                    afsj = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
                }
                $("#shipdatatable").append(
                    "<tr>" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + afsj + "</td>" +
                    "<td>" + isnull(item.fadd, '--', 0) + "</td>" +
                    "<td>" + isnull(item.ajlb, '--', 0) + "</td>" +
                    "<td>" + isnull(item.ay, '--', 0) + "</td>" +
                    "<td>" + isnull(item.cfyj, '--', 0) + "</td>" +
                        //"<td><span class='clickword'>详情</span></td>" +
                    "</tr>"
                );
            })
            break;
        case '缴费信息':
            $("#shipdatatable").removeClass('table-bordered');
            if (shipdata.jfxx == '' || shipdata.jfxx == null) {
                TableIsNull1();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<th style='width: 50px;'>序号</th>" +
                "<th>开票日期</th>" +
                "<th>开票单位名称</th>" +
                "<th>缴费项目名称</th>" +
                "<th>收费项目名称</th>" +
                "<th>应缴金额</th>" +
                "<th>实缴金额</th>" +
                "<th>有效期起</th>" +
                "<th>有效期止</th>" +
                    //"<th>操作</th>" +
                "</tr>"
            );
            $(shipdata.jfxx).each(function (index, item) {
                var redclass = '';
                if (parseFloat(item.sjze) < parseFloat(item.yjze)) {
                    redclass = "class='redtr'";
                }
                var kprq = '--';
                if (isnull(item.kprq, '--', 0) != '--') {
                    kprq = datecl(item.kprq);
                }
                var yxqq = '--';
                if (isnull(item.yxqq, '--', 0) != '--') {
                    yxqq = datecl(item.yxqq);
                }
                var yxqz = '--';
                if (isnull(item.yxqz, '--', 0) != '--') {
                    yxqz = datecl(item.yxqz);
                }
                $("#shipdatatable").append(
                    "<tr " + redclass + ">" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + kprq + "</td>" +
                    "<td>" + isnull(item.kpdwmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.jfxmmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.sffsmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.yjze, '--', 0) + "</td>" +
                    "<td>" + isnull(item.sjze, '--', 0) + "</td>" +
                    "<td>" + yxqq + "</td>" +
                    "<td>" + yxqz + "</td>" +
                        //"<td><span class='clickword'>详情</span></td>"+
                    "</tr>"
                );
            })
            break;
        case '船检信息':
            $("#shipdatatable").removeClass('table-bordered');
            if (shipdata.jyxx == '' || shipdata.jyxx == null) {
                TableIsNull1();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<th style='width: 50px;'>序号</th>" +
                "<th>船检登记号</th>" +
                "<th>检验地点</th>" +
                "<th>检验单位名称</th>" +
                "<th>申请人</th>" +
                "<th>检验种类</th>" +
                "<th>检验开始日期</th>" +
                "<th>检验完成日期</th>" +
                    //"<th>操作</th>" +
                "</tr>"
            );
            $(shipdata.jyxx).each(function (index, item) {
                var redclass = '';
                if (parseFloat(item.sjze) < parseFloat(item.yjze)) {
                    redclass = "class='redtr'";
                }
                var jyksrq = '--';
                if (isnull(item.jyksrq, '--', 0) != '--') {
                    jyksrq = datecl(item.jyksrq);
                }
                var jywcrq = '--';
                if (isnull(item.jywcrq, '--', 0) != '--') {
                    jywcrq = datecl(item.jywcrq);
                }
                $("#shipdatatable").append(
                    "<tr " + redclass + ">" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + isnull(item.cjdjh, '--', 0) + "</td>" +
                    "<td>" + isnull(item.jydd, '--', 0) + "</td>" +
                    "<td>" + isnull(item.jydwmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.sqr, '--', 0) + "</td>" +
                    "<td>" + isnull(item.jyzl, '--', 0) + "</td>" +
                    "<td>" + jyksrq + "</td>" +
                    "<td>" + jywcrq + "</td>" +
                        //"<td><span class='clickword'>详情</span></td>"+
                    "</tr>"
                );
            })
            break;
        default :
            break;
    }
}
function datecl(dateno){
    var date=new Date(parseInt(dateno));
    return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
}
function TableIsNull1() {
    $("#nulltablediv1").show();
}
//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度
function isnull(str, isnullstr, islong) {
    var isnull = isnullstr;
    if (str == null || str == '' || str == 'null' || str == undefined) {
        return isnull;
    } else {
        if (islong) {
            if (str.length >= 20) {
                return "<abbr title='" + str + "'>" + str.substr(0, 20) + "</abbr>";
            }
        }
        return str;
    }
}