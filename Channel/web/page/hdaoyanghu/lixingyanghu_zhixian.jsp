<%@page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>支线航道-例行养护</title>

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
    <link rel="stylesheet" type="text/css" href="page/hdaoyanghu/lixingyanghu_zhixian.css">

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
            src="page/hdaoyanghu/lixingyanghu_zhixian.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
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
                    <p class="nomargin nopadding navcontenttitle">支线航道</p>
                </div>
            </div>
            <div class="row">
                <c:choose>
                <c:when test="${ui.hasPerm('MAN_SHIZXYH')||ui.hasPerm('MAN_DPTZXYH')}">
                <div class="col-xs-2">
                    <a class="btn btn-primary" style="margin: 15px 0 15px 0"
                       data-toggle="modal" data-target="#modaladdtz">台账登记</a> <a
                        class="btn btn-default" style="margin: 15px 0 15px 0"
                        onclick="delmulti();">删除</a>
                </div>
                <div class="col-xs-3 col-xs-offset-2">
                    </c:when>
                    <c:otherwise>
                    <div class="col-xs-3 col-xs-offset-3">
                        </c:otherwise>
                        </c:choose>

                        <div class="row">
                            <div class="col-xs-4 text-right">
                                <h4 style="margin-top:22px;font-size:16px;">单位:</h4>
                            </div>
                            <div class="col-xs-8 text-right" style="margin: 15px 0 15px 0"
                                 id="divseldw">
                                <script>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_SHENZXYH')}">
                                    $("#divseldw").addseldpt({
                                        id: "seldw",
                                        selectfn: function () {
                                            loadbranch();
                                        }, defaultval: 1
                                    });
                                    </c:when>
                                    <c:otherwise>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_SHIZXYH')||ui.hasPerm('MAN_SHIZXYH')}">
                                    $("#divseldw").addseldpt({
                                        id: "seldw",
                                        selectfn: function () {
                                            loadbranch();
                                        },
                                        defaultval: <%=szshiju%>
                                    }, <%=szshiju%>, false);
                                    </c:when>
                                    <c:otherwise>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_DPTZXYH')||ui.hasPerm('MAN_DPTZXYH')}">
                                    $("#divseldw").addseldpt({
                                        id: "seldw",
                                        selectfn: function () {
                                            loadbranch();
                                        },
                                        defaultval: <%=szju%>
                                    }, <%=szju%>, true);
                                    </c:when>
                                    </c:choose>
                                    </c:otherwise>
                                    </c:choose>
                                    </c:otherwise>
                                    </c:choose>
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
                                        onpickedstart: "loadbranch",
                                        onpickedend: "loadbranch"
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-xs-12">
                        <table id="tablebranch" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th rowspan="2" class="bordertop borderleft"><input id="cbselall" type="checkbox">&nbsp;序号
                                </th>
                                <th rowspan="2" class="bordertop borderleft">单位</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">测量</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">碍航疏浚</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">整治建筑物修复</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">维修管理码头(埠头)</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">航标维护</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">打捞沉船</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">其它清障</th>
                                <th colspan="2" class="bordertop borderleft borderbottom">其他维修工程</th>
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

                                <th class="borderleft">座/座次</th>
                                <th class="borderleft">元</th>

                                <th class="borderleft">艘/吨位</th>
                                <th class="borderleft">元</th>

                                <th class="borderleft">处/吨</th>
                                <th class="borderleft">元</th>

                                <th class="borderleft">处</th>
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

<c:if test="${ui.hasPerm('MAN_SHIZXYH')||ui.hasPerm('MAN_DPTZXYH')}">

    <!-- 台账登记 -->
    <div class="modal fade" id="modaladdtz" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width:80%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">支线航道养护台账登记</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid form-horizontal" id="formaddtz">
                        <center>
                            <h2>全航区其他航道例行养护工作统计表</h2>
                        </center>
                        <br>

                        <div class="form-group" style="padding-left:20px;">
                            <label class="pull-left control-label" style="text-align: left;">单位</label>

                            <div class="col-xs-3" id="divaddtzdw">
                                <script>
                                    <c:if test="${ui.hasPerm('MAN_SHIZXYH')}">
                                    $("#divaddtzdw").addseldpt({
                                        id: 'addtzdw',
                                        autoajax: {
                                            name: 'zxjbxx.dw'
                                        },
                                        validators: {
                                            notempty: {
                                                msg: '请输入单位'
                                            }
                                        }, defaultval: <%=szshiju%>
                                    }, <%=szshiju%>);
                                    </c:if>
                                    <c:if test="${ui.hasPerm('MAN_DPTZXYH')}">
                                    $("#divaddtzdw").addseldpt({
                                        id: 'addtzdw',
                                        autoajax: {
                                            name: 'zxjbxx.dw'
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
                            <label class="pull-left control-label" style="text-align: left;">日期</label>

                            <div class="col-xs-3" id="divaddtzselrq">
                                <script>
                                    var nowday = getTimeFmt(new Date(),
                                            'yyyy-MM');
                                    $("#divaddtzselrq").addmonth({
                                        id: 'addtzselrq',
                                        defaultval: nowday,
                                        autoajax: {
                                            name: 'zxjbxx.starttime'
                                        }
                                    });
                                </script>
                            </div>
                        </div>
                        <div>
                            <table class="table table-bordered tabletzdj">
                                <tr>
                                    <th class="text-center"
                                        style="height:35px;line-height:35px;">项目
                                    </th>
                                    <th class="text-center"
                                        style="height:35px;line-height:35px;">单位
                                    </th>
                                    <th class="text-center"
                                        style="height:35px;line-height:35px;">数量
                                    </th>
                                    <th class="text-center"
                                        style="height:35px;line-height:35px;">备注
                                    </th>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">测量</td>
                                    <td class="text-center">平方公里</td>
                                    <td id="iadd11">
                                        <script>
                                            $("#iadd11").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd11",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.clsl"
                                                }
                                            });
                                        </script>
                                    </td>
                                    <td id="iadd13" rowspan="2">
                                        <script>
                                            $("#iadd13").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputadd13",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.clbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iadd12">
                                        <script>
                                            $("#iadd12").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputadd12",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.clje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-center" rowspan="2">碍航物疏浚</td>
                                    <td class="text-center">立方米</td>
                                    <td id="iadd21">
                                        <script>
                                            $("#iadd21").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd21",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.ahsjsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="iadd23" rowspan="2">
                                        <script>
                                            $("#iadd23").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputadd23",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.ahsjbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iadd22">
                                        <script>
                                            $("#iadd22").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputadd22",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.ahsjje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-center" rowspan="2">整治建筑物修复</td>
                                    <td class="text-center">平方米</td>
                                    <td id="iadd31">
                                        <script>
                                            $("#iadd31").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd31",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.zzjzwxfsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="iadd33" rowspan="2">
                                        <script>
                                            $("#iadd33").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputadd33",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.zzjzwxfbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iadd32">
                                        <script>
                                            $("#iadd32").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputadd32",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.zzjzwxfje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">维修管理码头(埠头)</td>
                                    <td class="text-center">座</td>
                                    <td id="iadd41">
                                        <script>
                                            $("#iadd41").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd41",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.wxglmtsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="iadd43" rowspan="2">
                                        <script>
                                            $("#iadd43").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputadd43",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.wxglmtbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iadd42">
                                        <script>
                                            $("#iadd42").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputadd42",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.wxglmtje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">航标维护</td>
                                    <td class="text-center">座/座次</td>
                                    <td id="iadd51">
                                        <script>
                                            $("#iadd51").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd51",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.hbwhzsl"
                                                }
                                            });
                                            $('#inputadd51').css('float', 'left');
                                            $('#inputadd51').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#iadd51").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd511",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.hbwhzcsl"
                                                }
                                            });
                                            $('#inputadd511').css('float', 'left');
                                            $('#inputadd511').css('width', '47%');
                                        </script>
                                    </td>

                                    <td id="iadd53" rowspan="2">
                                        <script>
                                            $("#iadd53").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputadd53",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.hbwhbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iadd52">
                                        <script>
                                            $("#iadd52").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputadd52",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.hbwhje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">打捞沉船</td>
                                    <td class="text-center">艘/吨位</td>
                                    <td id="iadd61">
                                        <script>
                                            $("#iadd61").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd61",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.dlccssl"
                                                }
                                            });
                                            $('#inputadd61').css('float', 'left');
                                            $('#inputadd61').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#iadd61").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd612",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.dlccdwsl"
                                                }
                                            });
                                            $('#inputadd612').css('float', 'left');
                                            $('#inputadd612').css('width', '47%');
                                        </script>
                                    </td>

                                    <td id="iadd63" rowspan="2">
                                        <script>
                                            $("#iadd63").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputadd63",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.dlccbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iadd62">
                                        <script>
                                            $("#iadd62").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputadd62",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.dlccje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">其他清障</td>
                                    <td class="text-center">处/吨</td>
                                    <td id="iadd71">
                                        <script>
                                            $("#iadd71").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd71",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtqzcsl"
                                                }
                                            });
                                            $('#inputadd71').css('float', 'left');
                                            $('#inputadd71').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#iadd71").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd711",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtqzdsl"
                                                }
                                            });
                                            $('#inputadd711').css('float', 'left');
                                            $('#inputadd711').css('width', '47%');
                                        </script>
                                    </td>
                                    <td id="iadd73" rowspan="2">
                                        <script>
                                            $("#iadd73").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputadd73",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtqzbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iadd72">
                                        <script>
                                            $("#iadd72").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputadd72",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtqzje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">其他维修工程</td>
                                    <td class="text-center">处</td>
                                    <td id="iadd81">
                                        <script>
                                            $("#iadd81").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputadd81",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtwxgcsl"
                                                }
                                            });
                                        </script>
                                    </td>
                                    <td id="iadd83" rowspan="2">
                                        <script>
                                            $("#iadd83").addtextarea({
                                                hint: "请输入备注",
                                                rows: '3',
                                                id: "inputadd83",
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtwxgcbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iadd82">
                                        <script>
                                            $("#iadd82").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputadd82",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtwxgcje"
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

    <!-- 编辑台账登记 -->
    <div class="modal fade" id="modalupdatetz" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width:80%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">支线航道养护台账登记</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid form-horizontal" id="formupdatetz">
                        <div class="row hide">
                            <div class="col-xs-12" id="divupdatetzid"></div>
                        </div>
                        <center>
                            <h2>全航区其他航道例行养护工作统计表</h2>
                        </center>
                        <br>

                        <div class="form-group" style="padding-left:20px;">
                            <label class="pull-left control-label" style="text-align: left;">单位</label>

                            <div class="col-xs-4" id="divupdatetzdw">
                                <script>
                                    <c:if test="${ui.hasPerm('MAN_SHIGGYH')}">
                                    $("#divupdatetzdw").addseldpt({
                                        id: 'updatetzdw',
                                        autoajax: {
                                            name: 'zxjbxx.dw'
                                        },
                                        validators: {
                                            notempty: {
                                                msg: '请输入单位'
                                            }
                                        }, defaultval: <%=szshiju%>
                                    }, <%=szshiju%>);
                                    </c:if>
                                    <c:if test="${ui.hasPerm('MAN_DPTGGYH')}">
                                    $("#divupdatetzdw").addseldpt({
                                        id: 'updatetzdw',
                                        autoajax: {
                                            name: 'zxjbxx.dw'
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
                            <label class="pull-left control-label" style="text-align: left;">月份</label>

                            <div class="col-xs-4" id="divupdatetzselrq">
                                <script>
                                    var nowday = getTimeFmt(new Date(),
                                            'yyyy-MM');
                                    $("#divupdatetzselrq").addmonth({
                                        id: 'updatetzselrq',
                                        defaultval: nowday,
                                        autoajax: {
                                            name: 'zxjbxx.starttime'
                                        }
                                    });
                                </script>
                            </div>
                        </div>
                        <div>
                            <table class="table table-bordered tabletzdj">
                                <tr>
                                    <th class="text-center"
                                        style="height:35px;line-height:35px;">项目
                                    </th>
                                    <th class="text-center"
                                        style="height:35px;line-height:35px;">单位
                                    </th>
                                    <th class="text-center"
                                        style="height:35px;line-height:35px;">数量
                                    </th>
                                    <th class="text-center"
                                        style="height:35px;line-height:35px;">备注
                                    </th>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">测量</td>
                                    <td class="text-center">平方公里</td>
                                    <td id="iupdate11">
                                        <script>
                                            $("#iupdate11").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate11",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.clsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="iupdate13" rowspan="2">
                                        <script>
                                            $("#iupdate13").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputupdate13",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.clbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iupdate12">
                                        <script>
                                            $("#iupdate12").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputupdate12",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.clje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">碍航物疏浚</td>
                                    <td class="text-center">立方米</td>
                                    <td id="iupdate21">
                                        <script>
                                            $("#iupdate21").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate21",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.ahsjsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="iupdate23" rowspan="2">
                                        <script>
                                            $("#iupdate23").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputupdate23",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.ahsjbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iupdate22">
                                        <script>
                                            $("#iupdate22").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputupdate22",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.ahsjje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">整治建筑物修复</td>
                                    <td class="text-center">平方米</td>
                                    <td id="iupdate31">
                                        <script>
                                            $("#iupdate31").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate31",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.zzjzwxfsl"
                                                }
                                            });
                                        </script>
                                    </td>


                                    <td id="iupdate33" rowspan="2">
                                        <script>
                                            $("#iupdate33").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputupdate33",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.zzjzwxfbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iupdate32">
                                        <script>
                                            $("#iupdate32").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputupdate32",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.zzjzwxfje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">维修管理码头(埠头)</td>
                                    <td class="text-center">座</td>
                                    <td id="iupdate41">
                                        <script>
                                            $("#iupdate41").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate41",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.wxglmtsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="iupdate43" rowspan="2">
                                        <script>
                                            $("#iupdate43").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputupdate43",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.wxglmtbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iupdate42">
                                        <script>
                                            $("#iupdate42").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputupdate42",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.wxglmtje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">航标维护</td>
                                    <td class="text-center">座/座次</td>
                                    <td id="iupdate51">
                                        <script>
                                            $("#iupdate51").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate51",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.hbwhzsl"
                                                }
                                            });
                                            $('#inputupdate51').css('float', 'left');
                                            $('#inputupdate51').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#iupdate51").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate511",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.hbwhzcsl"
                                                }
                                            });
                                            $('#inputupdate511').css('float', 'left');
                                            $('#inputupdate511').css('width', '47%');
                                        </script>
                                    </td>

                                    <td id="iupdate53" rowspan="2">
                                        <script>
                                            $("#iupdate53").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputupdate53",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.hbwhbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iupdate52">
                                        <script>
                                            $("#iupdate52").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputupdate52",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.hbwhje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">打捞沉船</td>
                                    <td class="text-center">艘/吨位</td>
                                    <td id="iupdate61">
                                        <script>
                                            $("#iupdate61").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate61",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.dlccssl"
                                                }
                                            });
                                            $('#inputupdate61').css('float', 'left');
                                            $('#inputupdate61').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#iupdate61").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate611",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.dlccdwsl"
                                                }
                                            });
                                            $('#inputupdate611').css('float', 'left');
                                            $('#inputupdate611').css('width', '47%');
                                        </script>
                                    </td>

                                    <td id="iupdate63" rowspan="2">
                                        <script>
                                            $("#iupdate63").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputupdate63",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.dlccbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iupdate62">
                                        <script>
                                            $("#iupdate62").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputupdate62",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.dlccje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">其他清障</td>
                                    <td class="text-center">处/吨</td>
                                    <td id="iupdate71">
                                        <script>
                                            $("#iupdate71").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate71",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtqzcsl"
                                                }
                                            });
                                            $('#inputupdate71').css('float', 'left');
                                            $('#inputupdate71').css('width', '47%');
                                        </script>
                                        <label style="float:left;margin-top:8px;">&nbsp;&nbsp;/&nbsp;&nbsp;</label>
                                        <script>
                                            $("#iupdate71").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate711",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtqzdsl"
                                                }
                                            });
                                            $('#inputupdate711').css('float', 'left');
                                            $('#inputupdate711').css('width', '47%');
                                        </script>
                                    </td>

                                    <td id="iupdate73" rowspan="2">
                                        <script>
                                            $("#iupdate73").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputupdate73",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtqzbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iupdate72">
                                        <script>
                                            $("#iupdate72").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputupdate72",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtqzje"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" rowspan="2">其他维修工程</td>
                                    <td class="text-center">处</td>
                                    <td id="iupdate81">
                                        <script>
                                            $("#iupdate81").addinputtext({
                                                hint: "请输入数量",
                                                id: "inputupdate81",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtwxgcsl"
                                                }
                                            });
                                        </script>
                                    </td>

                                    <td id="iupdate83" rowspan="2">
                                        <script>
                                            $("#iupdate83").addtextarea({
                                                hint: "请输入备注",
                                                id: "inputupdate83",
                                                rows: '3',
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtwxgcbz"
                                                }
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center">元</td>
                                    <td id="iupdate82">
                                        <script>
                                            $("#iupdate82").addinputtext({
                                                hint: "请输入金额",
                                                id: "inputupdate82",
                                                validators: {
                                                    double: {}
                                                },
                                                moreclass: "noborder",
                                                autoajax: {
                                                    name: "zxjbxx.qtwxgcje"
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
                    <button id="btnupdatetz" type="button" class="btn btn-primary"
                            onclick="updatetz();">保存
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
</c:if>

<!-- 查看台账登记 -->
<div class="modal fade" id="modalviewtz" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width:80%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">支线航道养护台账登记</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid form-horizontal" id="formviewtz">
                    <center>
                        <h2>全航区其他航道例行养护工作统计表</h2>
                    </center>
                    <br>

                    <div class="form-group" style="padding-left:20px;">
                        <label class="pull-left control-label" style="text-align: left;">单位</label> <label
                            class="col-xs-2 control-label" id="viewtzdw"
                            style="text-align:left"></label>
                    </div>
                    <div class="form-group" style="padding-left:20px;">
                        <label class="pull-left control-label" style="text-align: left;">月份</label> <label
                            class="col-xs-2 control-label" id="viewtzselrq"
                            style="text-align:left"></label>
                    </div>
                    <div>
                        <table class="table table-bordered tabletzdj">
                            <tr>
                                <th class="text-center"
                                    style="height:35px;line-height:35px;">项目
                                </th>
                                <th class="text-center"
                                    style="height:35px;line-height:35px;">单位
                                </th>
                                <th class="text-center"
                                    style="height:35px;line-height:35px;">数量
                                </th>
                                <th class="text-center"
                                    style="height:35px;line-height:35px;">备注
                                </th>
                            </tr>
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
                                <td class="text-center" rowspan="2">碍航疏浚</td>
                                <td class="text-center">平方公里</td>
                                <td class="text-center" id="iview21"></td>
                                <td class="text-center" id="iview23" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview22"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">整治建筑物修复</td>
                                <td class="text-center">立方米</td>
                                <td class="text-center" id="iview31"></td>
                                <td class="text-center" id="iview33" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview32"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">维修管理码头(埠头)</td>
                                <td class="text-center">平方米</td>
                                <td class="text-center" id="iview41"></td>
                                <td class="text-center" id="iview43" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview42"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">航标维护</td>
                                <td class="text-center">座/座次</td>
                                <td class="text-center" id="iview51"></td>
                                <td class="text-center" id="iview53" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview52"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">打捞沉船</td>
                                <td class="text-center">艘/吨位</td>
                                <td class="text-center" id="iview61"></td>
                                <td class="text-center" id="iview63" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview62"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">其他清障</td>
                                <td class="text-center">处/吨</td>
                                <td class="text-center" id="iview71"></td>
                                <td class="text-center" id="iview73" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview72"></td>
                            </tr>
                            <tr>
                                <td class="text-center" rowspan="2">其他维修工程</td>
                                <td class="text-center"></td>
                                <td class="text-center" id="iview81"></td>
                                <td class="text-center" id="iview83" rowspan="2"></td>
                            </tr>
                            <tr>
                                <td class="text-center">元</td>
                                <td class="text-center" id="iview82"></td>
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

</body>
</html>
