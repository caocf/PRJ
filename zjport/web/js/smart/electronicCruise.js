$(document).ready(function(){
    $("#smart").addClass("active");
    $("#electronicCruise_id").addClass("active");
    $("#rjjh_li").addClass("active");
    //$("#start").on("click",startCruise);
    var xzqh = $("#xzqh").val();

    $.ajax({
        //url: '../smart/queryChannel',
        url: '../video/queryhdaocamera',
        dataType: "json",
        data:{
            xzqh:xzqh
        },
        type: "post",
        success: function(data) {
            console.log(data);
            $("#checkchanneloption").append(
                "<option selected='selected' value=-1>请选择航道</option>"
            );
            $(data.records.data).each(function(index,item){
                $("#checkchanneloption").append(
                    "<option value="+item.id+">"+item.hdmc+"</option>"
                );
            })
        },
        error:function(options,status){
            console.error(options);
            console.error(status);
        }
    });
    $("#changebtn").click(function(){
        if($('#order').val()==0){
            $('#order').val(1);
        }else{
            $('#order').val(0);
        }
        var qdtext=$("#modalhdqd").text();
        $("#modalhdqd").text($("#modalhdzd").text());
        $("#modalhdzd").text(qdtext);
    })
    $("#export_b").on("click",exportXls);
    showInfoInTable('../smart/history',1);
});
/**
 * 添加二级下拉
 */
function addnextoption(){
    var xzqh = $("#xzqh").val();
    if($("#checkchanneloption").val()!=-1){
        $.ajax({
            //url: '../smart/queryMonitor',
            url: '../video/queryhduancamera',
            dataType: "json",
            type: "post",
            data : {
                sshdid:$("#checkchanneloption").val(),
                xzqh:xzqh
            },
            success: function(data) {
                console.log(data);
                $("#startoption,#endoption").empty();
                $("#startoption").append(
                    "<option value=-1 selected='selected'>请选择航段</option>"
                );
                //$("#endoption").append(
                //    "<option value=-1 selected='selected'>请选择巡航终点</option>"
                //);
                $(data.records.data).each(function(index,item){
                    $("#startoption,#endoption").append(
                        "<option value="+item.id+">"+item.hdqdmc+"-"+item.hdzdmc+"</option>"
                    );
                })
            }
        })
    }
}
var ids=[];//摄像头id组
/**
 * 添加巡航模态框数据
 */
function addmodaldata(){
    if($('#checkchanneloption').val()==-1){
        alert('请选择航道');
        return
    }
    if($('#startoption').val()==-1){
        alert('请选择航段');
        return
    }
    $.ajax({
        url: '../smart/queryCameras',
        dataType: "json",
        type: "post",
        data : {
            hdaoId:$("#startoption").val()
        },
        success: function(data) {
            console.log(data);
            var str = $('#startoption').find("option:selected").text();
            var strs = str.split("-");
            $("#modalsxhd").text($("#checkchanneloption").find("option:selected").text());
            $("#modalhdqd").text(strs[0]);
            $("#modalhdzd").text(strs[1]);
            $("#modaltable").empty();
            $("#modaltable").append(
            "<tr>"+
            "<th>摄像头名称</th>"+
            "<th>所在位置</th>"+
            "</tr>"
            );
            $(data.records.data).each(function(index,item){
                $("#modaltable").append(
                    "<tr>"+
                    "<td>"+item[0]+"</td>"+
                    //"<td>("+item[1]+","+item[2]+")</td>"+
                    "<td>"+item[3]+"</td>"+
                    "</tr>"
                );
            });

            $('#videoids').val(ids);
            $('#order').val(0);
            $('#xhdqform').val(strs[0]);
            $('#xhzdform').val(strs[1]);
            $('#channelName').val($("#modalsxhd").text());
            $("#hduanId").val($("#startoption").val());
            $('#addmlModal').modal('show');
        }
    });

}
/**
 * 开始巡航
 */
var startCruise = function(){
    //参数 cameraIds集合 值 monitor_id
    //stCruiseFrom 巡航起始点用户选择
    //stCruiseTo 巡航结束点用户选择
    //stChannel 航道
    //window.location.href = "../smart/start";
    $.ajax({
        url: '../smart/start',
        dataType: "json",
        type: "post",
        data : {
            cameraIds:ids,
            stCruiseFrom:$("#modalhdqd").text(),
            stCruiseTo:$("#modalhdzd").text()
        },
        success: function(data) {

        }
        })
}
/**
 * 列表加载
 * @param actionname：接口名
 * @param selectedPage：页码
 */
function showInfoInTable(actionname,selectedPage){
    var startTime = $("#beginTime").val();
    var endTime = $("#endTime").val();
    var channel = "";//TODO 需要修改
    $.ajax({
        async: false,
        url: actionname,
        data: {
            page: selectedPage,
            rows: 10,
            'startTime': startTime,
            endTime:endTime,
            channel:channel
        },
        dataType: "json",
        type: "post",
        success: function(data) {
            if(data.resultcode==0){
                if(data.records != null){
                    var list=data.records.data;
                    console.log(list);
                    $("#pagedetal").empty();
                    $("#nulltablediv").hide();
                    $("#pagedetal").text(
                        "当前页"+list.length+"条记录 共"+data.records.total+"条记录，每页"+data.records.rows+"条"
                    );
                    pagingmake(actionname,'showInfoInTable',selectedPage,data.records.pages);
                    if(list==""){
                        TableIsNull();
                        return
                    }else{
                        appendToTable(list,selectedPage);
                    }
                }else{
                    TableIsNull();
                    return
                }
            }else{
                alert(data.resultdesc);
            }
        },
        error: function(xOption, status) {
            console.error(status);
            console.error(JSON.stringify(xOption));
            //console.error(error);
        }
    });
}
function TableIsNull() {
    $("#nulltablediv").show();
}
/**
 * 创建表格
 * @param list：数据集合
 * @param selectedPage：页码
 */
function appendToTable(list,selectedPage){
    $("#loglist-info").empty();
    $("#loglist-info").append(
        "<tr>"+
        "<th><input type='checkbox' onclick='checkedOrNo(this)'/></th>"+
        "<th>序号</th>"+
        "<th>巡查航道</th>"+
        "<th>巡查起点</th>"+
        "<th>巡查终点</th>"+
        "<th>巡查时间</th>"+
        "<th>操作</th>"+
        "</tr>"
    );
    $(list).each(function(index,item){
        $("#loglist-info").append(
            "<tr>" +
            "<td><input type='checkbox' class='systemcheck' value='"+item.stCruiseId+"'/></td>"+
            "<td>"+(index+1+parseInt(selectedPage-1)*10)+"</td>" +
            "<td>"+isnull(item.stChannel,'--',1)+"</td>" +
            "<td>"+isnull(item.stCruiseFrom,'--',1)+"</td>" +
            "<td>"+isnull(item.stCruiseTo,'--',1)+"</td>" +
            "<td>"+isnull(item.dtCuriseBegin,'--',0)+"&nbsp;至&nbsp;"+isnull(item.dtCuriseEnd,'--',0)+"</td>" +
            "<td><span class='clickword' onclick='queryDesc("+item.stCruiseId+")'>详情</span>&nbsp;&nbsp;<span class='clickword' onclick='deletetr("+item.stCruiseId+")'>删除</span></td>" +
            "</tr>"
        );
    })
}
function queryDesc(id){
    localStorage.cruiseId = id;
    window.location.href="../smart/cruiesDesc.jsp";
}
function deletetr(id){
    var stCruiseId = new Array();
    if(id==-1){
        if($('input.systemcheck:checked').length==0){
            alert('请选择要删除的巡查对象');
            return
        }
        for(var i=0;i<$('input.systemcheck:checked').length;i++){
            stCruiseId.push($('input.systemcheck:checked:eq('+i+')').val());
        }
    }else{
        stCruiseId.push(id);
    }
    $.ajax({
        async: false,
        url: "../smart/deletedHistory?stCruiseId="+stCruiseId,
        data: JSON.stringify(stCruiseId),
        dataType: "json",
        type: "post",
        success: function(data) {
            alert('删除成功');
            showInfoInTable('../smart/history',1);
        },
        error: function(xOption, status) {
            console.error(status);
            console.error(JSON.stringify(xOption));
            //console.error(error);
        }
    });
}

//checkbox全选
function checkedOrNo(obj){
    var isCheck = obj.checked;
    $('.systemcheck').each(function(){
        this.checked=isCheck;
    });
}
//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度:1为验证长度
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
var exportXls = function(){
    console.log("start export");
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var channel = "";//TODO 需要修改

    var form=$("<form>");//定义一个form表单
    form.attr("style","display:none");
    form.attr("target","");
    form.attr("method","get");
    form.attr("action","../smart/downloadHistory");
    var input1=$("<input>");
    input1.attr("type","hidden");
    input1.attr("name","startTime");
    input1.attr("value",startTime);

    var input2=$("<input>");
    input2.attr("type","hidden");
    input2.attr("name","endTime");
    input2.attr("value",endTime);
    form.append(input2);

    var input3=$("<input>");
    input3.attr("type","hidden");
    input3.attr("name","channel");
    input3.attr("value",channel);
    form.append(input3);
    $("body").append(form);//将表单放置在web中

    form.submit();//表单提交

}