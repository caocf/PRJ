<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CXtUser user = ((CXtUser) session.getAttribute("user"));
    String username = null;
    int userid = -1;
    if (user != null) {
        username = user.getUsername();
        userid = user.getId();
    }


    CXtDpt shiju = (CXtDpt) session.getAttribute("shiju");
    int szshiju = -2;
    if (shiju != null)
        szshiju = shiju.getId();

    CXtDpt chuju = (CXtDpt) session.getAttribute("chuju");
    int szchuju = -2;
    if (chuju != null)
        szchuju = chuju.getId();

    CXtDpt szjudpt = (CXtDpt) session.getAttribute("dpt");
    int szju = -2;
    if (szjudpt != null)
        szju = szjudpt.getId();

    CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.getAttribute("dptshixzqh");
    int szshixzqh = -2;
    if (dptshixzqh != null)
        szshixzqh = dptshixzqh.getId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>骨干航道-例行养护</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet" type="text/css"
          href="page/hdaoyanghu/lixingyanghu_gugan.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hdaoyanghu/hdaoyanghu.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hdaoyanghu/lixingyanghu_gugan.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>

</head>

<body>

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>


<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">


<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2" style="min-width: 200px;">
            <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
                 class="pull-left" style="margin-top:10px;"
                 onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
            <label class="pull-left">航道养护</label>
        </div>
        <div class="col-xs-7">
            <c:if test="${ui.groupHasPerms('骨干航道养护')||ui.groupHasPerms('支线航道养护')}">
                <div class="btn-group">
                    <button type="button"
                            class="btn btn-primary dropdown-toggle active"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        例行养护<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <c:if test="${ui.groupHasPerms('骨干航道养护')}">
                            <li style="height:35px;"><a id="tablixingyanghu_gugan"
                                                        style="height:35px;">骨干航道</a></li>
                        </c:if>
                        <c:if test="${ui.groupHasPerms('支线航道养护')}">
                            <li style="height:35px;"><a id="tablixingyanghu_zhixian"
                                                        style="height:35px;">支线航道</a></li>
                        </c:if>
                    </ul>
                </div>
            </c:if>
            <c:if test="${ui.groupHasPerms('专项工程')}">
                <a class="btn btn-primary" id="tabzhuanxianggongcheng">专项工程</a>
            </c:if>
            <c:if test="${ui.groupHasPerms('应急抢通工程')}">
                <a class="btn btn-primary" id="tabyingjiqiangtonggongcheng">应急抢通</a>
            </c:if>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>

    <div class="row navcontent shadow border">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">骨干航道</p>
                </div>
            </div>
            <div class="row">
                <c:choose>
                <c:when test="${ui.hasPerm('MAN_SHIGGYH')||ui.hasPerm('MAN_DPTGGYH')}">
                <div class="col-xs-2">
                    <a class="btn btn-primary" style="margin: 15px 0 15px 0"
                       data-toggle="modal" data-target="#modaladdtz">台账登记</a> <a
                        class="btn btn-default" style="margin: 15px 0 15px 0"
                        onclick="delmulti();">删除</a>
                </div>
                <div class="col-xs-3 col-xs-offset-2">
                    </c:when>
                    <c:otherwise>
                    <div class="col-xs-3 col-xs-offset-4">
                        </c:otherwise>
                        </c:choose>
                        <div class="row">
                            <div class="col-xs-4 text-right">
                                <h4 style="margin-top:22px;font-size:16px;">航道:</h4>
                            </div>
                            <div class="col-xs-8 text-right" style="margin: 15px 0 15px 0"
                                 id="divselhdao">
                                <script>
                                    $("#divselhdao").addmultiselect({
                                        id: "multiselhdao"
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-5">
                        <div class="row">
                            <div class="col-xs-2 text-right">
                                <h4 style="margin-top:22px;font-size:16px;">月份:</h4>
                            </div>
                            <div class="col-xs-10 text-left" id="divtimeregion"
                                 style="margin: 15px 0 0 0">
                                <script>
                                    $("#divtimeregion").addmonthregion({
                                        idstart: "selstarttime",
                                        idend: "selendtime",
                                        hintstart: '请选择开始月份',
                                        hintend: '请选择结束月份',
                                        onpickedstart: 'loadgugan',
                                        onpickedend: 'loadgugan'
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <table id="tablegugan" class="borderright" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th rowspan="2" class="bordertop borderleft"><input id="cbselall" type="checkbox">&nbsp;序号
                                </th>
                                <th rowspan="2" class="bordertop borderleft">航道名</th>
                                <th rowspan="2" class="bordertop borderleft">单位</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">测量</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">疏浚</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">整治建筑物修复</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">管理码头修复</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">系缆桩修复</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">航标维护</th>
                                <th colspan="3" class="bordertop borderleft borderbottom">无主碍航物清除</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">绿化养护</th>
                                <th rowspan="2" class="bordertop borderleft">月份</th>
                                <th rowspan="2" class="bordertop borderleft">操作</th>
                            </tr>
                            <tr>
                                <th class="borderleft">平方公里</th>
                                <th class="borderleft">元</th>

                                <th class="borderleft">立方米</th>
                                <th class="borderleft">元</th>

                                <th class="borderleft">平方米</th>
                                <th class="borderleft">元</th>

                                <th class="borderleft">座</th>
                                <th class="borderleft">元</th>


                                <th class="borderleft">个</th>
                                <th class="borderleft">元</th>


                                <th class="borderleft">座/座次</th>
                                <th class="borderleft">元</th>

                                <th class="borderleft">艘/吨位</th>
                                <th class="borderleft">处/吨</th>
                                <th class="borderleft">元</th>

                                <th class="borderleft">平方米</th>
                                <th class="borderleft">元</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 台账登记查看 -->
<div class="modal fade" id="modalviewtz" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">查看骨干航道养护台账登记</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid form-horizontal">
                    <div class="form-group" style="padding-left:20px;">
                        <label class="pull-left control-label" style="text-align: left;">单位:</label> <label
                            class="col-xs-4 control-label" id="viewtzdw"
                            style="text-align:left;"></label>
                    </div>
                    <div class="form-group" style="padding-left:20px;">
                        <label class="pull-left control-label" style="text-align: left;">航道:</label> <label
                            class="col-xs-2 control-label" id="viewtzselhdao"
                            style="text-align:left;"></label>
                    </div>
                    <div class="form-group" style="padding-left:20px;">
                        <label class="pull-left control-label" style="text-align: left;">月份:</label> <label
                            class="col-xs-2 control-label" id="viewtzselrq"
                            style="text-align:left;"></label>
                    </div>
                    <div>
                        <table class="table table-bordered tabletzdj">
                            <tr>
                                <th class="text-center"
                                    style="height:78px;line-height:78px;">养护类型
                                </th>
                                <th class="text-center"
                                    style="height:78px;line-height:78px;">单位
                                </th>
                                <th class="text-center"
                                    style="height:78px;line-height:78px;">数量
                                </th>
                                <th class="text-center"
                                    style="height:78px;line-height:78px;">备注
                                </th>
                            </tr>
                            <!-- ------------------------------------ -->
                            <tr>
                                <td class="text-center" rowspan="2">测量</td>
                                <td class="text-center">平方公里</td>
                                <td class="text-center" id="iview11"></td>
                                <td class="text-center" id="iview13" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview12"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">疏浚</td>
                                <td class="text-center">立方米</td>
                                <td class="text-center" id="iview21"></td>
                                <td class="text-center" id="iview23" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview22"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">整治建筑物修复</td>
                                <td class="text-center">平方米</td>
                                <td class="text-center" id="iview31"></td>
                                <td class="text-center" id="iview33" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview32"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">管理码头修复</td>
                                <td class="text-center">座</td>
                                <td class="text-center" id="iview41"></td>
                                <td class="text-center" id="iview43" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview42"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">系缆桩修复</td>
                                <td class="text-center">个</td>
                                <td class="text-center" id="iview51"></td>
                                <td class="text-center" id="iview53" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview52"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">航标维护</td>
                                <td class="text-center">座/座次</td>
                                <td class="text-center" id="iview61"></td>
                                <td class="text-center" id="iview63" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview62"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="3"
                                    style="height:78px;line-height:78px;">无主碍航物清除
                                </td>
                                <td class="text-center">艘/吨位</td>
                                <td class="text-center" id="iview71"></td>
                                <td class="text-center" id="iview73" rowspan="3"></td>
                            </tr>
                            <tr>
                                <td class="text-center">处/吨</td>
                                <td class="text-center" id="iview81"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview72"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">绿化养护</td>
                                <td class="text-center">平方米</td>
                                <td class="text-center" id="iview91"></td>
                                <td class="text-center" id="iview93" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview92"></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>

            </div>
        </div>
    </div>
</div>

<c:if test="${ui.hasPerm('MAN_SHIGGYH')||ui.hasPerm('MAN_DPTGGYH')}">
    <!-- 台账登记 -->
    <div class="modal fade" id="modaladdtz" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">骨干航道养护台账登记</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid form-horizontal" id="formaddtz">
                        <div class="form-group" style="padding-left:20px;">
                            <label class="pull-left control-label" style="text-align: left;">单位:</label>

                            <div class="col-xs-4" id="divaddtzdw">
                                <script>
                                    <c:if test="${ui.hasPerm('MAN_SHIGGYH')}">
                                    $("#divaddtzdw").addseldpt({
                                        id: 'addtzdw',
                                        autoajax: {
                                            name: 'jbxx.dw'
                                        },
                                        validators: {
                                            notempty: {
                                                msg: '请输入单位'
                                            }
                                        }, defaultval: <%=szshiju%>
                                    }, <%=szshiju%>);
                                    </c:if>
                                    <c:if test="${ui.hasPerm('MAN_DPTGGYH')}">
                                    $("#divaddtzdw").addseldpt({
                                        id: 'addtzdw',
                                        autoajax: {
                                            name: 'jbxx.dw'
                                        },
                                        validators: {
                                            notempty: {
                                                msg: '请输入单位'
                                            }
                                        }, defaultval: <%=szju%>
                                    }, <%=szju%>, true);
                                    </c:if>
                                </script>
                            </div>
                        </div>
                        <div class="form-group" style="padding-left:20px;">
                            <label class="pull-left control-label" style="text-align: left;">航道:</label>

                            <div class="col-xs-4" id="divaddtzselhdao" style="text-align: left;"></div>
                        </div>
                        <div class="form-group" style="padding-left:20px;">
                            <label class="pull-left control-label" style="text-align: left;">月份:</label>

                            <div class="col-xs-4" id="divaddtzselrq">
                                <script>
                                    var nowday = getTimeFmt(new Date(),
                                            'yyyy-MM');
                                    $("#divaddtzselrq").addmonth({
                                        id: 'addtzselrq',
                                        defaultval: nowday,
                                        autoajax: {
                                            name: 'jbxx.starttime'
                                        }
                                    });
                                </script>
                            </div>
                        </div>
                        <div>
                            <table class="table table-bordered tabletzdj" cellpadding="0" cellspacing="">
                                <tr>
                                    <th class="text-center"
                                        style="height:78px;line-height:78px;">养护类型
                                    </th>
                                    <th class="text-center"
                                        style="height:78px;line-height:78px;">单位
                                    </th>
                                    <th class="text-center"
                                        style="height:78px;line-height:78px;">数量
                                    </th>
                                    <th class="text-center"
                                        style="height:78px;line-height:78px;">备注
                                    </th>
                                </tr>
                                <!-- ------------------------------------ -->
                                <tr>
                                    <td class="text-center" rowspan="2">测量</td>
                                    <td class="text-center">平方公里</td>
                                    <td id="i11">
                                        <script>
                                            $("#i11").addinputtext({
                                                hint: "请输入数量",
                                                id: "input11",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.clsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="i13" rowspan="2">
                                        <script>
                                            $("#i13").addtextarea({
                                                hint: "请输入备注",
                                                id: "input13",
                                                rows: '4',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.clbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="i12">
                                        <script>
                                            $("#i12").addinputtext({
                                                hint: "请输入金额",
                                                id: "input12",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.cljg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">疏浚</td>
                                    <td class="text-center">立方米</td>
                                    <td id="i21">
                                        <script>
                                            $("#i21").addinputtext({
                                                hint: "请输入数量",
                                                id: "input21",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.sjsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="i23" rowspan="2">
                                        <script>
                                            $("#i23").addtextarea({
                                                hint: "请输入备注",
                                                id: "input23",
                                                rows: '4',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.sjbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="i22">
                                        <script>
                                            $("#i22").addinputtext({
                                                hint: "请输入金额",
                                                id: "input22",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.sjjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">整治建筑物修复</td>
                                    <td class="text-center">平方米</td>
                                    <td id="i31">
                                        <script>
                                            $("#i31").addinputtext({
                                                hint: "请输入数量",
                                                id: "input31",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.zzjzwxfsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="i33" rowspan="2">
                                        <script>
                                            $("#i33").addtextarea({
                                                hint: "请输入备注",
                                                id: "input33",
                                                rows: '4',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.zzjzwxfbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="i32">
                                        <script>
                                            $("#i32").addinputtext({
                                                hint: "请输入金额",
                                                id: "input32",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.zzjzwxfjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">管理码头修复</td>
                                    <td class="text-center">座</td>
                                    <td id="i41">
                                        <script>
                                            $("#i41").addinputtext({
                                                hint: "请输入数量",
                                                id: "input41",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.glmtxfsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="i43" rowspan="2">
                                        <script>
                                            $("#i43").addtextarea({
                                                hint: "请输入备注",
                                                id: "input43",
                                                rows: '4',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.glmtxfbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="i42">
                                        <script>
                                            $("#i42").addinputtext({
                                                hint: "请输入金额",
                                                id: "input42",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.glmtxfjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">系缆桩修复</td>
                                    <td class="text-center">个</td>
                                    <td id="i51">
                                        <script>
                                            $("#i51").addinputtext({
                                                hint: "请输入数量",
                                                id: "input51",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.xlzxfsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="i53" rowspan="2">
                                        <script>
                                            $("#i53").addtextarea({
                                                hint: "请输入备注",
                                                id: "input53",
                                                rows: '4',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.xlzxfbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="i52">
                                        <script>
                                            $("#i52").addinputtext({
                                                hint: "请输入金额",
                                                id: "input52",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.xlzxfjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">航标维护</td>
                                    <td class="text-center">座/座次</td>
                                    <td id="i61">
                                        <script>
                                            $("#i61").addinputtext({
                                                hint: "请输入数量",
                                                id: "input61",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.hbwhzsl"
                                                }
                                            });
                                            $('#input61').css('float', 'left');
                                            $('#input61').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#i61").addinputtext({
                                                hint: "请输入数量",
                                                id: "input611",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.hbwhzcsl"
                                                }
                                            });
                                            $('#input611').css('float', 'left');
                                            $('#input611').css('width', '47%');
                                        </script>
                                    </td>

                                    <td id="i63" rowspan="2">
                                        <script>
                                            $("#i63").addtextarea({
                                                hint: "请输入备注",
                                                id: "input63",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.hbwhbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="i62">
                                        <script>
                                            $("#i62").addinputtext({
                                                hint: "请输入金额",
                                                id: "input62",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.hbwhjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="3"
                                        style="height:78px;line-height:78px;">无主碍航物清除
                                    </td>
                                    <td class="text-center">艘/吨位</td>
                                    <td id="i71">
                                        <script>
                                            $("#i71").addinputtext({
                                                hint: "请输入数量",
                                                id: "input71",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcssl"
                                                }
                                            });

                                            $('#input71').css('float', 'left');
                                            $('#input71').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#i71").addinputtext({
                                                hint: "请输入数量",
                                                id: "input711",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcdwsl"
                                                }
                                            });

                                            $('#input711').css('float', 'left');
                                            $('#input711').css('width', '47%');
                                        </script>
                                    </td>
                                    <td id="i73" rowspan="3">
                                        <script>
                                            $("#i73").addtextarea({
                                                hint: "请输入备注",
                                                id: "input73",
                                                rows: '5',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">处/吨</td>
                                    <td id="i81">
                                        <script>
                                            $("#i81").addinputtext({
                                                hint: "请输入数量",
                                                id: "input81",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqccsl"
                                                }
                                            });
                                            $('#input81').css('float', 'left');
                                            $('#input81').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#i81").addinputtext({
                                                hint: "请输入数量",
                                                id: "input811",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcdsl"
                                                }
                                            });
                                            $('#input811').css('float', 'left');
                                            $('#input811').css('width', '47%');
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="i72">
                                        <script>
                                            $("#i72").addinputtext({
                                                hint: "请输入金额",
                                                id: "input72",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">绿化养护</td>
                                    <td class="text-center">平方米</td>
                                    <td id="i91">
                                        <script>
                                            $("#i91").addinputtext({
                                                hint: "请输入数量",
                                                id: "input91",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.lhyhsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="i93" rowspan="2">
                                        <script>
                                            $("#i93").addtextarea({
                                                hint: "请输入备注",
                                                id: "input93",
                                                rows: '4',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.lhyhbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="i92">
                                        <script>
                                            $("#i92").addinputtext({
                                                hint: "请输入金额",
                                                id: "input92",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.lhyhjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnaddtz" type="button" class="btn btn-primary"
                            onclick="addtz();">保存
                    </button>
                </div>
            </div>
        </div>
    </div>


    <!-- 删除台账登记 -->
    <div class="modal fade" id="modaldeltz" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除台账</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            你确定要删除该台账&nbsp;<label id="lbmodaldelnames"></label>&nbsp;吗？
                        </p>

                        <p id="pErrMsg" class="text-danger"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btndeltz" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 编辑台账登记 -->
    <div class="modal fade" id="modaledittz" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">修改养护台账登记</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid form-horizontal" id="formedittz">
                        <div class="row hide">
                            <div class="col-xs-12" id="divedittzid"></div>
                        </div>
                        <div class="form-group" style="padding-left:20px;">
                            <label class="pull-left control-label" style="text-align: left;">单位:</label>

                            <div class="col-xs-4" id="divedittzdw">
                                <script>
                                    $("#divedittzdw").addseldpt({
                                        id: 'edittzdw',
                                        autoajax: {
                                            name: 'jbxx.dw'
                                        },
                                        validators: {
                                            notempty: {
                                                msg: '请输入单位'
                                            }
                                        }
                                    });
                                </script>
                            </div>
                        </div>
                        <div class="form-group" style="padding-left:20px;">
                            <label class="pull-left control-label" style="text-align: left;">航道:</label>

                            <div class="col-xs-4" id="divedittzselhdao"></div>
                        </div>
                        <div class="form-group" style="padding-left:20px;">
                            <label class="pull-left control-label" style="text-align: left;">月份:</label>

                            <div class="col-xs-4" id="divedittzselrq">
                                <script>
                                    var nowday = getTimeFmt(new Date(),
                                            'yyyy-MM');
                                    $("#divedittzselrq").addmonth({
                                        id: 'edittzselrq',
                                        defaultval: nowday,
                                        autoajax: {
                                            name: 'jbxx.starttime'
                                        }
                                    });
                                </script>
                            </div>
                        </div>
                        <div>
                            <table class="table table-bordered tabletzdj" cellpadding="0" cellspacing="0">
                                <tr>
                                    <th class="text-center"
                                        style="">养护类型
                                    </th>
                                    <th class="text-center">单位</th>
                                    <th class="text-center"
                                        style="">数量
                                    </th>
                                    <th class="text-center"
                                        style="">备注
                                    </th>
                                </tr>
                                <!-- ------------------------------------ -->
                                <tr>
                                    <td class="text-center" rowspan="2">测量</td>
                                    <td class="text-center">平方公里</td>
                                    <td id="e11">
                                        <script>
                                            $("#e11").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput11",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.clsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="e13" rowspan="2">
                                        <script>
                                            $("#e13").addtextarea({
                                                hint: "请输入备注",
                                                id: "einput13",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.clbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="e12">
                                        <script>
                                            $("#e12").addinputtext({
                                                hint: "请输入金额",
                                                id: "einput12",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.cljg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">疏浚</td>
                                    <td class="text-center">立方米</td>
                                    <td id="e21">
                                        <script>
                                            $("#e21").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput21",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.sjsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="e23" rowspan="2">
                                        <script>
                                            $("#e23").addtextarea({
                                                hint: "请输入备注",
                                                id: "einput23",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.sjbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="e22">
                                        <script>
                                            $("#e22").addinputtext({
                                                hint: "请输入金额",
                                                id: "einput22",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.sjjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">整治建筑物修复</td>
                                    <td class="text-center">平方米</td>
                                    <td id="e31">
                                        <script>
                                            $("#e31").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput31",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.zzjzwxfsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="e33" rowspan="2">
                                        <script>
                                            $("#e33").addtextarea({
                                                hint: "请输入备注",
                                                id: "einput33",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.zzjzwxfbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="e32">
                                        <script>
                                            $("#e32").addinputtext({
                                                hint: "请输入金额",
                                                id: "einput32",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.zzjzwxfjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">管理码头修复</td>
                                    <td class="text-center">座</td>
                                    <td id="e41">
                                        <script>
                                            $("#e41").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput41",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.glmtxfsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="e43" rowspan="2">
                                        <script>
                                            $("#e43").addtextarea({
                                                hint: "请输入备注",
                                                id: "einput43",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.glmtxfbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="e42">
                                        <script>
                                            $("#e42").addinputtext({
                                                hint: "请输入金额",
                                                id: "einput42",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.glmtxfjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">系缆桩修复</td>
                                    <td class="text-center">个</td>
                                    <td id="e51">
                                        <script>
                                            $("#e51").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput51",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.xlzxfsl"
                                                }
                                            });
                                        </script>
                                    </td>
                                    <td id="e53" rowspan="2">
                                        <script>
                                            $("#e53").addtextarea({
                                                hint: "请输入备注",
                                                id: "einput53",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.xlzxfbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="e52">
                                        <script>
                                            $("#e52").addinputtext({
                                                hint: "请输入金额",
                                                id: "einput52",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.xlzxfjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">航标维护</td>
                                    <td class="text-center">座/座次</td>
                                    <td id="e61">
                                        <script>
                                            $("#e61").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput61",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.hbwhzsl"
                                                }
                                            });
                                            $('#einput61').css('float', 'left');
                                            $('#einput61').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#e61").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput611",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.hbwhzcsl"
                                                }
                                            });

                                            $('#einput611').css('float', 'left');
                                            $('#einput611').css('width', '47%');
                                        </script>
                                    </td>

                                    <td id="e63" rowspan="2">
                                        <script>
                                            $("#e63").addtextarea({
                                                hint: "请输入备注",
                                                id: "einput63",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.hbwhbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="e62">
                                        <script>
                                            $("#e62").addinputtext({
                                                hint: "请输入金额",
                                                id: "einput62",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.hbwhjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>


                                <tr>
                                    <td class="text-center" rowspan="3"
                                        style="height:78px;line-height:78px;">无主碍航物清除
                                    </td>
                                    <td class="text-center">艘/吨位</td>
                                    <td id="e71">
                                        <script>
                                            $("#e71").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput71",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcssl"
                                                }
                                            });

                                            $('#einput71').css('float', 'left');
                                            $('#einput71').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#e71").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput711",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcdwsl"
                                                }
                                            });

                                            $('#einput711').css('float', 'left');
                                            $('#einput711').css('width', '47%');
                                        </script>
                                    </td>

                                    <td id="e73" rowspan="3">
                                        <script>
                                            $("#e73").addtextarea({
                                                hint: "请输入备注",
                                                id: "einput73",
                                                rows: '7',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">处/吨</td>
                                    <td id="e81">
                                        <script>
                                            $("#e81").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput81",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqccsl"
                                                }
                                            });

                                            $('#einput81').css('float', 'left');
                                            $('#einput81').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#e81").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput811",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcdsl"
                                                }
                                            });

                                            $('#einput811').css('float', 'left');
                                            $('#einput811').css('width', '47%');
                                        </script>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="e72">
                                        <script>
                                            $("#e72").addinputtext({
                                                hint: "请输入金额",
                                                id: "einput72",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.wzahwqcjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-center" rowspan="2">绿化养护</td>
                                    <td class="text-center">平方米</td>
                                    <td id="e91">
                                        <script>
                                            $("#e91").addinputtext({
                                                hint: "请输入数量",
                                                id: "einput91",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.lhyhsl"
                                                }
                                            });
                                        </script>
                                    </td>
                                    <td id="e93" rowspan="2">
                                        <script>
                                            $("#e93").addtextarea({
                                                hint: "请输入备注",
                                                id: "einput93",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.lhyhbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="e92">
                                        <script>
                                            $("#e92").addinputtext({
                                                hint: "请输入金额",
                                                id: "einput92",
                                                validators: {
                                                    number: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "jbxx.lhyhjg"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnedittz" type="button" class="btn btn-primary"
                            onclick="edittz();">保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</c:if>
</body>
</html>
