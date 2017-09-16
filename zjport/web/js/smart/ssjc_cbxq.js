var shipdata;//船舶信息
$(document).ready(function () {
    $("#smart").addClass("active");
    $("#ssjc_li").addClass("active");
    $(".tabdiv").click(function(){
        typedatacheck(this);
    });
    getallshipdata();
})

function typedatacheck(event){
    $("#shipdatatable").empty();
    $("#nulltablediv").hide();
    switch ($(event).text()){
        case '基本信息':
            $("#shipdatatable").addClass('table-bordered');
            if(shipdata.jbxx==''||shipdata.jbxx==null){
                TableIsNull();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<td class='colortd col-lg-2'>船舶名称：</td>" +
                "<td class='col-lg-4'>"+isnull(shipdata.jbxx.zwcm,'--',0)+"</td>" +
                "<td class='colortd col-lg-2'>船籍港（代码）：</td>" +
                "<td class='col-lg-4'>"+isnull(shipdata.jbxx.cjgdm,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>船检登记号：</td>" +
                "<td>"+isnull(shipdata.jbxx.cjdjh,'--',0)+"</td>" +
                "<td class='colortd'>船舶登记号：</td>" +
                "<td>"+isnull(shipdata.jbxx.cbdjh,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>船舶类型：</td>" +
                "<td>"+isnull(shipdata.jbxx.cblx,'--',0)+"</td>" +
                "<td class='colortd'>船舶类型代码：</td>" +
                "<td>"+isnull(shipdata.jbxx.cblxdm,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>主机总功率：</td>" +
                "<td>"+isnull(shipdata.jbxx.zjzgl,'--',0)+"</td>" +
                "<td class='colortd'>参考载货量：</td>" +
                "<td>"+isnull(shipdata.jbxx.ckzhl,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>总吨位：</td>" +
                "<td>"+isnull(shipdata.jbxx.zdw,'--',0)+"</td>" +
                "<td class='colortd'>净吨位：</td>" +
                "<td>"+isnull(shipdata.jbxx.jdw,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>吃水空载（m）：</td>" +
                "<td>"+isnull(shipdata.jbxx.cskz,'--',0)+"</td>" +
                "<td class='colortd'>吃水满载（m）：</td>" +
                "<td>"+isnull(shipdata.jbxx.csmz,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>总长（m）：</td>" +
                "<td>"+isnull(shipdata.jbxx.zc,'--',0)+"</td>" +
                "<td class='colortd'>船长（m）：</td>" +
                "<td>"+isnull(shipdata.jbxx.cc,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>型宽（m）：</td>" +
                "<td>"+isnull(shipdata.jbxx.xk,'--',0)+"</td>" +
                "<td class='colortd'>型深（m）：</td>" +
                "<td>"+isnull(shipdata.jbxx.xs,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>量吨甲板长（m）：</td>" +
                "<td>"+isnull(shipdata.jbxx.ldjbc,'--',0)+"</td>" +
                "<td class='colortd'>船舶经营人：</td>" +
                "<td>"+isnull(shipdata.jbxx.jyr,'--',0)+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td class='colortd'>船舶所有人：</td>" +
                "<td>"+isnull(shipdata.jbxx.syr,'--',0)+"</td>" +
                "<td class='colortd'>所有人电话：</td>" +
                "<td>"+isnull(shipdata.jbxx.syrdh,'--',0)+"</td>" +
                "</tr>"
            );
            break;
        case '证书信息':
            $("#shipdatatable").removeClass('table-bordered');
            if(shipdata.czxx==''||shipdata.czxx==null){
                TableIsNull();
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
            $(shipdata.czxx).each(function(index,item){
                var fzrq='--';
                if(isnull(item.fzrq,'--',0)!='--'){
                    fzrq=datecl(item.fzrq);
                }
                var yxrq='--';
                if(isnull(item.yxrq,'--',0)!='--'){
                    yxrq=datecl(item.yxrq);
                }
                $("#shipdatatable").append(
                    "<tr>" +
                    "<td>"+(index+1)+"</td>" +
                    "<td>"+isnull(item.zsmc,'--',0)+"</td>" +
                    "<td>"+isnull(item.zsbh,'--',0)+"</td>" +
                    "<td>"+fzrq+"</td>" +
                    "<td>"+yxrq+"</td>" +
                    //"<td><span class='clickword'>详情</span></td>"+
                    "</tr>"
                );
            })
            break;
        case '违章信息':
            $("#shipdatatable").removeClass('table-bordered');
            if(shipdata.wzxx==''||shipdata.wzxx==null){
                TableIsNull();
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
            $(shipdata.wzxx).each(function(index,item){
                var afsj='--';
                if(isnull(item.fasj,'--',0)!='--'){
                    var date=new Date(parseInt(item.fasj));
                    afsj=date.getFullYear()+'-'+date.getMonth()+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes();
                }
                $("#shipdatatable").append(
                    "<tr>" +
                    "<td>"+(index+1)+"</td>" +
                    "<td>"+afsj+"</td>" +
                    "<td>"+isnull(item.fadd,'--',0)+"</td>" +
                    "<td>"+isnull(item.ajlb,'--',0)+"</td>" +
                    "<td>"+isnull(item.ay,'--',0)+"</td>" +
                    "<td>"+isnull(item.cfyj,'--',0)+"</td>" +
                    //"<td><span class='clickword'>详情</span></td>" +
                    "</tr>"
                );
            })
            break;
        case '缴费信息':
            $("#shipdatatable").removeClass('table-bordered');
            if(shipdata.jfxx==''||shipdata.jfxx==null){
                TableIsNull();
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
            $(shipdata.jfxx).each(function(index,item){
                var redclass='';
                if(parseFloat(item.sjze)<parseFloat(item.yjze)){
                    redclass="class='redtr'";
                }
                var kprq='--';
                if(isnull(item.kprq,'--',0)!='--'){
                    kprq=datecl(item.kprq);
                }
                var yxqq='--';
                if(isnull(item.yxqq,'--',0)!='--'){
                    yxqq=datecl(item.yxqq);
                }
                var yxqz='--';
                if(isnull(item.yxqz,'--',0)!='--'){
                    yxqz=datecl(item.yxqz);
                }
                $("#shipdatatable").append(
                    "<tr "+redclass+">" +
                    "<td>"+(index+1)+"</td>" +
                    "<td>"+kprq+"</td>" +
                    "<td>"+isnull(item.kpdwmc,'--',0)+"</td>" +
                    "<td>"+isnull(item.jfxmmc,'--',0)+"</td>" +
                    "<td>"+isnull(item.sffsmc,'--',0)+"</td>" +
                    "<td>"+isnull(item.yjze,'--',0)+"</td>" +
                    "<td>"+isnull(item.sjze,'--',0)+"</td>" +
                    "<td>"+yxqq+"</td>" +
                    "<td>"+yxqz+"</td>" +
                    //"<td><span class='clickword'>详情</span></td>"+
                    "</tr>"
                );
            })
            break;
        case '船检信息':
            $("#shipdatatable").removeClass('table-bordered');
            if(shipdata.jyxx==''||shipdata.jyxx==null){
                TableIsNull();
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
            $(shipdata.jyxx).each(function(index,item){
                var redclass='';
                if(parseFloat(item.sjze)<parseFloat(item.yjze)){
                    redclass="class='redtr'";
                }
                var jyksrq='--';
                if(isnull(item.jyksrq,'--',0)!='--'){
                    jyksrq=datecl(item.jyksrq);
                }
                var jywcrq='--';
                if(isnull(item.jywcrq,'--',0)!='--'){
                    jywcrq=datecl(item.jywcrq);
                }
                $("#shipdatatable").append(
                    "<tr "+redclass+">" +
                    "<td>"+(index+1)+"</td>" +
                    "<td>"+isnull(item.cjdjh,'--',0)+"</td>" +
                    "<td>"+isnull(item.jydd,'--',0)+"</td>" +
                    "<td>"+isnull(item.jydwmc,'--',0)+"</td>" +
                    "<td>"+isnull(item.sqr,'--',0)+"</td>" +
                    "<td>"+isnull(item.jyzl,'--',0)+"</td>" +
                    "<td>"+jyksrq+"</td>" +
                    "<td>"+jywcrq+"</td>" +
                    //"<td><span class='clickword'>详情</span></td>"+
                "</tr>"
                );
            })
            break;
        default :
            break;
    }
}
function TableIsNull(){
    $("#nulltablediv").show();
}

function datecl(dateno){
    var date=new Date(parseInt(dateno));
    return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
}
function getallshipdata(){
    $.ajax({
        url: '../supervise/cbXq',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': $("#shipname").val()
        },
        success: function (data) {
            shipdata=data.map;
            $(".tabdiv:eq(0)").click();
        }
    });
}
//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度
function isnull(str,isnullstr,islong) {
    var isnull = isnullstr;
    if (str == null || str == '' || str == 'null'||str == undefined) {
        return isnull;
    } else {
        if(islong==1){
            if(str.length>=20){
                return "<abbr title='"+str+"'>"+str.substr(0,20)+"</abbr>";
            }
        }
        return str;
    }
}

//日期格式转换方法
Date.prototype.format = function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
        format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}