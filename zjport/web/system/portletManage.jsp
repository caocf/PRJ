<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/12/06
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.model.Portlet" pageEncoding="UTF-8" %>
<%@page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>portlet管理</title>

    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <script src="../js/common/jQuery-2.1.4.min.js"></script>

    <link rel="stylesheet" href="../css/common/animate.css" type="text/css"/>
    <style type="text/css">
        .table thead > tr > th {
            text-align: center !important;
        }

        .table tbody > tr > td {
            text-align: center !important;
        }

        .chart-legend li, .panel-body h4, .panel-body span {
            color: #333 !important;
        }

        .btn.btn-outline.blue {
            background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
            border-color: #3598dc;
            color: #3598dc;
        }

        .btn.btn-outline.blue.active, .btn.btn-outline.blue:active, .btn.btn-outline.blue:active:focus, .btn.btn-outline.blue:active:hover, .btn.btn-outline.blue:focus, .btn.btn-outline.blue:hover {
            background-color: #3598dc;
            border-color: #3598dc;
            color: #fff;
        }

        .emphasis * {
            color: #777;
        }

        .emphasis h3 {
            margin: 0;
            padding: 0;
        }

        .emphasis p {
            margin-bottom: 10px;
        }

        .emphasis {
            border-top: 4px solid transparent;
            padding-top: 15px;
        }

        .emphasis:hover {
            border-top-color: green;
            color: #000;
        }

        .emphasis:hover * {
            color: #000;
        }

        .user-action {
            padding: 0px 15px 15px;
        }

        .border-right {
            border-right: 1px solid #F4F4F4;
        }

        .topcolordiv {
            padding: 5px;
            width: 34px;
            height: 34px;
            float: left;
            margin-left: 10px;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
        }

        .topcolordiv > div {
            width: 100%;
            height: 100%;
        }

        .topcolordiv > i {
            font-size: 20px;
            line-height: 24px;
        }

        .topcolordivactived {
            background: #bae3be;
        }
    </style>
</head>
<%
    List<Portlet> portletLeftList = (List<Portlet>)session.getAttribute("portletLeftList");
    List<Portlet> portletRightList = (List<Portlet>)session.getAttribute("portletRightList");
%>
<body class="skin-blue-light sidebar-mini"
      style="font-family: Helvetica,Tahoma,'Microsoft YaHei','微软雅黑',Arial,STXihei,'华文细黑',SimSun,'宋体',Heiti,'黑体',sans-serif; ">

<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_released.png">
        个人工作台布局
        <!-- <small>advanced tables</small> -->
        <%--<i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>--%>

        <%--<button type="button" style="float: right" class="btn btn-primary" onclick="savePortlet()"><i class="fa fa-send-o"></i>
            保存
        </button>--%>
        <button data-toggle="modal" data-target="#mkModal" onclick="getPortletLib()" type="button" style="float: right;margin-right:10px;" class="btn btn-warning"><i
                class="fa fa-plus"></i> 新增
        </button>
    </h1>
</section>

<section class="content">
    <div class="row">
        <section class="col-lg-7 leftSide connectedSortable">

            <% if(portletLeftList.size()==0 && portletRightList.size()==0) {%>
            <div class="box box-solid sortTable" id="1_primary_common">
                <div class="box box-primary" style="margin-bottom:0px">
                    <div class="box-header with-border">
                        <i class="fa fa-bars"></i>

                        <h3 class="box-title">常用功能</h3>

                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        用户的常用功能按钮，当前的功能按钮为：内网门户、办公OA、考勤管理三个。
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>
            <!-- /.box -->

            <div class="box box-solid sortTable" id="3_primary_common">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <i class="fa fa-exclamation-circle"></i>

                        <h3 class="box-title">待办事项</h3>

                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        显示当前用户待完成的一些事项，例如信息发布审批事项
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>

            <%
                }else if(portletLeftList.size()!=0) {
                    for(Portlet port:portletLeftList) {
            %>
            <div class="box box-solid sortTable" id="<%=port.getStPortletLibId()%>_<%=port.getStColor()%>_<%=port.getStDisplayForm()%>">
                <div class="box box-<%=port.getStColor()%>">
                    <div class="box-header with-border">
                        <i class="fa <%=port.getStIcon()%>"></i>

                        <h3 class="box-title"><%=port.getStModuleName()%>
                        </h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <%
                                if(!"function".equals(port.getStModuleId()) && !"serviceSystem".equals(port.getStModuleId()) && !"backlog".equals(port.getStModuleId())) {
                            %>
                            <button class='btn btn-box-tool' onclick="distorymkdiv('<%=port.getStModuleId()%>',this)"><i class='fa fa-times'></i></button>
                            <%
                                }
                            %>
                        </div>
                    </div>
                    <div class="box-body">
                        <%=port.getStDescribe()%>
                    </div>
                </div>
            </div>
            <%
                    }
                }
            %>
        </section>
        <!-- /.Left col -->
        <!-- right col (We are only adding the ID to make the widgets sortable)-->
        <section class="col-lg-5 rightSide connectedSortable">
            <% if(portletLeftList.size()==0 && portletRightList.size()==0) {%>
            <div class="box box-solid sortTable" id="2_primary_common">
                <div class="box box-primary" style="margin-bottom:0px">
                    <div class="box-header with-border">
                        <i class="fa fa-cubes"></i>

                        <h3 class="box-title">业务系统</h3>

                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <%--<button class="btn btn-box-tool" onclick="closeIt('supplier')"><i class="fa fa-times"></i></button>--%>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        显示当前用户有权限关联的其他业务系统
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>

            <%
            }else if(portletRightList.size()!=0) {
                for(Portlet port:portletRightList) {
            %>
            <div class="box box-solid sortTable" id="<%=port.getStPortletLibId()%>_<%=port.getStColor()%>_<%=port.getStDisplayForm()%>">
                <div class="box box-<%=port.getStColor()%>">
                    <div class="box-header with-border">
                        <i class="fa <%=port.getStIcon()%>"></i>

                        <h3 class="box-title"><%=port.getStModuleName()%>
                        </h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <%
                                if(!"function".equals(port.getStModuleId()) && !"serviceSystem".equals(port.getStModuleId()) && !"backlog".equals(port.getStModuleId())) {
                            %>
                            <button class='btn btn-box-tool' onclick="distorymkdiv('<%=port.getStModuleId()%>',this)"><i class='fa fa-times'></i></button>
                            <%
                                }
                            %>
                        </div>
                    </div>
                    <div class="box-body">
                        <%=port.getStDescribe()%>
                    </div>
                </div>
            </div>
            <%
                    }
                }
            %>
        </section>
        <!-- right col -->
    </div>
    <!-- /.row (main row) -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="mkModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:500px;height: 280px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    新增模块
                </h4>
            </div>
            <div class="modal-body" style="height:160px;padding:0;">
                <div style="float: left;height:100%;width:100%;overflow: auto;" class="treediv">
                    <table class="table" style="margin-bottom:0;">
                        <tr>
                            <td style="width:100px;line-height: 34px;">选择模块</td>
                            <td>
                                <select class="form-control" id="mknr">
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">头部颜色</td>
                            <td id="topcolortd">
                                <div class="topcolordiv topcolordivactived" style="margin-left:0;">
                                    <div style="background: #3c8dbc" id="primary"></div>
                                </div>
                                <div class="topcolordiv">
                                    <div style="background: #00c0ef" id="info"></div>
                                </div>
                                <div class="topcolordiv">
                                    <div style="background: #dd4b39" id="danger"></div>
                                </div>
                                <div class="topcolordiv">
                                    <div style="background: #f39c12" id="warning"></div>
                                </div>
                                <div class="topcolordiv">
                                    <div style="background: #00a65a" id="success"></div>
                                </div>
                                <div class="topcolordiv">
                                    <div style="background: #d2d6de" id="default"></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">选择图表</td>
                            <td id="tbtd">
                                <div class="topcolordiv topcolordivactived" style="margin-left:0;"><i class="fa fa-pie-chart" id="pie" title="玫瑰饼图"></i></div>
                                <div class="topcolordiv"><i class="fa fa-bar-chart" id="sbar" title="标准条形图"></i></div>
                                <div class="topcolordiv"><i class="fa fa-line-chart" id="line" title="标准折现图"></i></div>
                                <div class="topcolordiv"><i class="fa fa-align-left" id="hbar" title="横向条形图"></i></div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="addmk()">
                    新增
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#system").addClass("active");
        $("#portletManage_li").addClass("active");

        $(".connectedSortable").sortable({
            placeholder: "sort-highlight",
            connectWith: ".connectedSortable",
            handle: ".box-header, .nav-tabs",
            forcePlaceholderSize: true,
            zIndex: 999999,
            start: function (e, ui) {
                $(ui.helper).addClass('dragging');
            },
            stop: function (e, ui) {
                $(ui.item).css({width: ''}).removeClass('dragging');
                savePortlet();
            }
        });
        $(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom").css("cursor", "move");
        $("#topcolortd").children(".topcolordiv").click(function () {
            $("#topcolortd").children(".topcolordiv").removeClass('topcolordivactived')
            $(this).addClass('topcolordivactived');
        });
        $("#tbtd").children(".topcolordiv").click(function () {
            $("#tbtd").children(".topcolordiv").removeClass('topcolordivactived')
            $(this).addClass('topcolordivactived');
        });
        $('#mknr').change(function(){
            if($('#mknr>option:selected').attr('id')=='outIn'||$('#mknr>option:selected').attr('id')=='company'){
                $("#tbtd").children(".topcolordiv").removeClass('topcolordivactived');
                $("#tbtd").children(".topcolordiv").hide();
                $("#tbtd").children(".topcolordiv:eq(1),.topcolordiv:eq(3)").show();
            }else{
                $("#tbtd").children(".topcolordiv").removeClass('topcolordivactived');
                $("#tbtd").children(".topcolordiv").show();
            }
        })
        getPortletLib();


    })

    function getPortletLib() {
        var all = "";
        $(".leftSide").children('.sortTable').each(function(){
            console.log($(this).attr('id'));
            if(all == "") {
                all += $(this).attr('id');
            } else {
                all += ","+$(this).attr('id');
            }
        })

        $(".rightSide").children('.sortTable').each(function(){
            console.log($(this).attr('id'));
            if(all == "") {
                all += $(this).attr('id');
            } else {
                all += ","+$(this).attr('id');
            }
        })

        $.ajax({
            url:"getPortletLib",
            type:'post',
            dataType:'json',
            data:{
                'all':all
            },
            success:function(data){
                if(data.resultcode==-1){
                    BackToLoginPage();
                }else if(data.resultcode==0){
                    var list=data.records.data;
                    $("#mknr").empty();
                    $("#mknr").append(
                            "<option value='0' selected='selected'>请选择模块</option>"
                    );
                    for(var i=0;i<list.length;i++){
                        $("#mknr").append(
                                "<option id='"+list[i].stModuleId+"'  value='"+list[i].stModuleName+","+list[i].stIcon+","+list[i].stDescribe+","+list[i].stModuleId+","+list[i].stPortletLibId +"'>"+list[i].stModuleName+"</option>"
                        )
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

    function savePortlet() {
        var leftSide="";
        var rightSide="";
        $(".leftSide").children('.sortTable').each(function(){
            //console.log($(this).attr('id'));
            if(leftSide == "") {
                leftSide += $(this).attr('id');
            } else {
                leftSide += ","+$(this).attr('id');
            }
        })

        $(".rightSide").children('.sortTable').each(function(){
            //console.log($(this).attr('id'));
            if(rightSide == "") {
                rightSide += $(this).attr('id');
            } else {
                rightSide += ","+$(this).attr('id');
            }
        })

        $.ajax({
            url:"saveLayout",
            type:'post',
            dataType:'json',
            data:{
                'left':leftSide,
                'right':rightSide
            },
            success:function(data){
                //alert("保存成功！！");
            }/*,
             complete : function() {
             CloseLoadingDiv();
             }*/
        });
    }
    function resetmodel(){
        $('#mknr>option:eq(0)').attr('selected','selected');
    }
    function addmk() {
        if($('#mknr').val()==0){
            alert('请选择模块');
            return
        }
        $('#mkModal').modal('hide');
        var valsz=$("#mknr").val().split(',');
        //$('#mknr>option:selected').attr('disabled','');
        var mkdivid=valsz[4]+'_'+
                    $("#topcolortd").children(".topcolordivactived").children('div').attr('id')+'_'+
                    $("#tbtd").children(".topcolordivactived").children('i').attr('id');
        var topcolorclass="box box-"+$("#topcolortd").children(".topcolordivactived").children('div').attr('id');
        var iconclass="fa "+valsz[1];
        $('.connectedSortable:eq(0)').append(
                "<div class='box box-solid sortTable' id='"+mkdivid+"'>" +
                "<div class='"+topcolorclass+"'>" +
                "<div class='box-header with-border'>" +
                "<i class='"+iconclass+"'></i>" +
                "<h3 class='box-title'>"+valsz[0]+"</h3>" +
                "<div class='box-tools pull-right'>" +
                "<button class='btn btn-box-tool' data-widget='collapse'><i class='fa fa-minus'></i></button>" +
                "<button class='btn btn-box-tool' onclick=\"distorymkdiv('"+$('#mknr>option:selected').attr('id')+"',this)\"><i class='fa fa-times'></i></button>" +
                "</div>" +
                "</div>" +
                "<div class='box-body' >" +
                valsz[2] +
                "</div>" +
                "</div>" +
                "</div>"
        )
        $(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom").css("cursor", "move");
        $.getScript("../js/common/app.min.js");
        savePortlet();
    }
    function distorymkdiv(optionid,event){
        //$('#'+optionid).removeAttr('disabled');
        $(event).parents('.sortTable').remove();
    }
</script>
</body>
</html>
