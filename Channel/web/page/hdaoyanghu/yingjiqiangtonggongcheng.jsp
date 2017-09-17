<%@ page import="com.channel.model.user.CXtUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
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

    String defaultpagestr = request.getParameter("defaultpage");
    int defaultpage = 1;
    if (defaultpagestr != null) {
        try {
            defaultpage = Integer.parseInt(defaultpagestr);
        } catch (Exception e) {

        }
    }

    CXtDpt shiju = (CXtDpt) session.getAttribute("shiju");
    int szshiju = 1;
    if (shiju != null)
        szshiju = shiju.getId();

    CXtDpt chuju = (CXtDpt) session.getAttribute("chuju");
    int szchuju = 1;
    if (chuju != null)
        szchuju = chuju.getId();

    CXtDpt szjudpt = (CXtDpt) session.getAttribute("dpt");
    int szju = 1;
    int deptxzqh = 1;
    if (szjudpt != null){
        szju = szjudpt.getId();
        deptxzqh=szjudpt.getXzqh();
    }


    CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.getAttribute("dptshixzqh");
    int szshixzqh = -2;
    if (dptshixzqh != null)
        szshixzqh = dptshixzqh.getId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>应急抢通</title>

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
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">
    <link rel="stylesheet" type="text/css"
          href="common/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/common/css/datatable.css">
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet" type="text/css"
          href="page/hdaoyanghu/yingjiqiangtonggongcheng.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
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
            src="page/hdaoyanghu/yingjiqiangtonggongcheng.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
</head>

<body style="min-width: 1400px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">
<input type="hidden" id="deptxzqh" value="<%=deptxzqh%>">

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
                    <button type="button" class="btn btn-primary dropdown-toggle"
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
                <a class="btn btn-primary active" id="tabyingjiqiangtonggongcheng">应急抢通</a>
            </c:if>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>
    <div class="row navcontent shadow border">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">应急抢通</p>
                </div>
            </div>
            <div class="row borderbottom">
                <c:choose>
                <c:when test="${ui.hasPerm('MAN_SHIYJQTGC') || ui.hasPerm('MAN_DPTYJQTGC')}">
                <div class="col-xs-2" style="width: 10%">
                    <a class="btn btn-primary" style="margin: 15px 0 15px 0"
                       data-toggle="modal" data-target="#modalnewproject">新建工程</a> <a
                        class="btn btn-default" style="margin: 15px 0 15px 0"
                        onclick="delmulti();">删除</a>
                </div>
                <div class="col-xs-2">
                    </c:when>
                    <c:otherwise>
                    <div class="col-xs-2">
                        </c:otherwise>
                        </c:choose>
                        <div class="row">
                            <div class="col-xs-4 text-right">
                                <h4 style="margin-top:22px;font-size:16px;">管理单位:</h4>
                            </div>
                            <div class="col-xs-8 text-left" style="margin: 15px 0 15px 0"
                                 id="divselgldw">
                                <script>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_SHENYJQTGC')}">
                                    $("#divselgldw").addseldpt({
                                        id: 'selgldw',
                                        hint: '请选择管理单位',
                                        selectfn: function () {
                                            loadyjqtgc();
                                        },
                                        defaultval: 1
                                    });
                                    </c:when>
                                    <c:otherwise>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_SHIYJQTGC')||ui.hasPerm('MAN_SHIYJQTGC')}">
                                    $("#divselgldw").addseldpt({
                                        id: 'selgldw',
                                        hint: '请选择管理单位',
                                        selectfn: function () {
                                            loadyjqtgc();
                                        },
                                        defaultval: <%=szshiju%>
                                    }, <%=szshiju%>, false);
                                    </c:when>
                                    <c:otherwise>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_DPTYJQTGC')||ui.hasPerm('MAN_DPTYJQTGC')}">
                                    $("#divselgldw").addseldpt({
                                        id: 'selgldw',
                                        hint: '请选择管理单位',
                                        selectfn: function () {
                                            loadyjqtgc();
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
                    <div class="col-xs-4" style="width: 27%">
                        <div class="row">
                            <div class="col-xs-3 text-right">
                                <h4 style="margin-top:22px;font-size:16px;">航道/航段:</h4>
                            </div>
                            <div class="col-xs-9 text-left">
                                <div id="divhdao" style="margin-top: 15px;">
                                    <select class="form-control leftradius"
                                            style="float:left;display:inline;width:auto;"
                                            id="selhdao"
                                            onchange="initsearchhangduan($(this).val())">
                                    </select>
                                </div>
                                <div id="divhduan" style="margin-top: 15px;">
                                    <select class="form-control rightradius"
                                            style="float:left;display:inline;width:auto;"
                                            id="selhduan" onchange="loadyjqtgc()">
                                        <option value="-1">全部航段</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="row">
                            <div class="col-xs-3 text-right">
                                <h4 style="margin-top:22px;font-size:16px;min-width:100px;">发生时间:</h4>
                            </div>
                            <div class="col-xs-9 text-left" id="divtimeregion"
                                 style="margin: 15px 0 0 0">
                                <script>
                                    $("#divtimeregion").adddayregion({
                                        idstart: 'seldaystart',
                                        hintstart: '请选择开始时间',
                                        hintend: '请选择结束时间',
                                        onpickedstart: "loadyjqtgc",
                                        onpickedend: "loadyjqtgc",
                                        idend: 'seldayend'
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-2" style="width:12%;">
                        <div class="input-group" style="margin: 15px 0 0 0">
                            <!-- /btn-group -->
                            <input type="text" class="form-control" aria-label="..." placeholder="请输入工程名称"
                                   id="searchContent">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="btnsearch" onclick="loadyjqtgc();">搜索
                            </button>
                        </span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12 nomargin nopadding">
                        <table id="tableRecords" cellspacing="0" width="100%"
                               page="<%=defaultpage%>">
                            <thead>
                            <tr>
                                <th width="10%"><input id="cbselall" type="checkbox">&nbsp;序号</th>
                                <th width="20%">工程名称</th>
                                <th width="20%">所在航道/航段</th>
                                <th width="10%">发生时间</th>
                                <th width="10%">管理单位</th>
                                <th width="10%">费用（万元）</th>
                                <th width="10%">最后修改时间</th>
                                <th width="10%">操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${ui.hasPerm('MAN_SHIYJQTGC') || ui.hasPerm('MAN_DPTYJQTGC')}">
    <!-- 新建工程 -->
    <div class="modal fade" id="modalnewproject" tabindex="-1"
         role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新建工程</h4>
                </div>
                <div class="modal-body" style="padding:20px 0 15px 0;">
                    <div id="addproject" class="container-fluid form-horizontal"
                         style="width:100%;">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">工程名称<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="divgcmc">
                                        <script>
                                            $("#divgcmc").addinputtext({
                                                id: 'gcmc',
                                                autoajax: {
                                                    name: 'yjqtgc.name'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入工程名称!'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">事件描述<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="diveventdesc">
                                        <script>
                                            $("#diveventdesc").addtextarea({
                                                autoajax: {
                                                    name: 'yjqtgc.eventdesc'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入事件描述!'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">发生时间<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divstartday">
                                        <script>
                                            var nowday = getTimeFmt(new Date(), 'yyyy-MM-dd');
                                            $("#divstartday").addday({
                                                id: 'starttime',
                                                autoajax: {
                                                    name: 'yjqtgc.starttime',
                                                },
                                                defaultval: nowday
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">管理单位<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divgldw">
                                        <script>
                                            <c:if test="${ui.hasPerm('MAN_SHIYJQTGC')}">
                                            $("#divgldw").addseldpt({
                                                id: 'addgldw',
                                                autoajax: {
                                                    name: 'yjqtgc.mandept'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入管理单位!'
                                                    }
                                                }, defaultval: <%=szshiju%>
                                            }, <%=szshiju%>);
                                            </c:if>
                                            <c:if test="${ui.hasPerm('MAN_DPTYJQTGC')}">
                                            $("#divgldw").addseldpt({
                                                id: 'addgldw',
                                                autoajax: {
                                                    name: 'yjqtgc.mandept'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入管理单位!'
                                                    }
                                                }, defaultval: <%=szju%>
                                            }, <%=szju%>, true);
                                            </c:if>
                                        </script>
                                    </div>
                                </div>


                            </div>
                        </div>


                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">所在航道<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divhdaoid">
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">所在航段<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divhduanid">
                                        <select id="tempselect" class="form-control"
                                                style="width:100%;padding-right:0px;">
                                            <option>--请选择--</option>
                                        </select>
                                    </div>
                                </div>


                            </div>
                        </div>


                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">具体位置<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="divaddress">
                                        <script>
                                            $("#divaddress").addinputtext({
                                                autoajax: {
                                                    name: 'yjqtgc.address'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入具体位置!'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">损失情况</label>

                                    <div class="col-xs-10" id="divlosecase">
                                        <script>
                                            $("#divlosecase").addtextarea({
                                                autoajax: {
                                                    name: 'yjqtgc.losecase'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">主要原因</label>

                                    <div class="col-xs-10" id="divmainreason">
                                        <script>
                                            $("#divmainreason").addtextarea({
                                                autoajax: {
                                                    name: 'yjqtgc.mainreason'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">处理情况<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="divrealcase">
                                        <script>
                                            $("#divrealcase").addtextarea({
                                                autoajax: {
                                                    name: 'yjqtgc.realcase'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入处理情况!'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">参加人员</label>

                                    <div class="col-xs-10" id="divparticipants">
                                        <script>
                                            $("#divparticipants").addinputtext({
                                                id: 'participants',
                                                autoajax: {
                                                    name: 'yjqtgc.participants'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">费用(万元)<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divcost">
                                        <script>
                                            $("#divcost").addinputtext({
                                                autoajax: {
                                                    name: 'yjqtgc.cost'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入费用!'
                                                    },
                                                    double: {
                                                        msg: '请输入数字'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">恢复时间</label>

                                    <div class="col-xs-8" id="divrestoretime">
                                        <script>
                                            $("#divrestoretime").addday({
                                                id: 'restoretime',
                                                autoajax: {
                                                    name: 'yjqtgc.restoretime'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">备注</label>

                                    <div class="col-xs-10" id="divremark">
                                        <script>
                                            $("#divremark").addtextarea({
                                                autoajax: {
                                                    name: 'yjqtgc.remark'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12"
                                 style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                                附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                <i class="icon-paper-clip" style="color:rgb(35, 82, 124);"></i>
                                <input type="button" class="btn btn-link" value="添加附件" style="padding-left:0px;"
                                       onclick="addpicture('addproject');">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnaddproject" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 删除弹窗 -->
    <div class="modal fade" id="modaldelyjqtgc" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除应急抢通</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            你确定要删除该应急抢通&nbsp;<label id="lbmodaldelnames"></label>&nbsp;吗？
                        </p>

                        <p id="pErrMsg" class="text-danger"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btndelyjqtgc" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 编辑工程 -->
    <div class="modal fade" id="modaleditproject" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">修改工程</h4>
                </div>
                <div class="modal-body" style="padding:20px 0 15px 0;">
                    <div id="editproject" class="container-fluid form-horizontal"
                         style="width:100%;">
                        <div class="row hide">
                            <div class="col-xs-12" id="diveditid"></div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">工程名称<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="diveditname">
                                        <script>
                                            $("#diveditname").addinputtext({
                                                id: 'editname',
                                                autoajax: {
                                                    name: 'yjqtgc.name'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入工程名称!'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">事件描述<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="divediteventdesc">
                                        <script>
                                            $("#divediteventdesc").addtextarea({
                                                id: 'editeventdesc',
                                                autoajax: {
                                                    name: 'yjqtgc.eventdesc'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">发生时间<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="diveditstarttime">
                                        <script>
                                            $("#diveditstarttime").addday({
                                                id: 'editstarttime',
                                                autoajax: {
                                                    name: 'yjqtgc.starttime'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">管理单位<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="diveditmandept">
                                        <script>
                                            <c:if test="${ui.hasPerm('MAN_SHIYJQTGC')}">
                                            $("#diveditmandept").addseldpt({
                                                id: 'editmandept',
                                                autoajax: {
                                                    name: 'yjqtgc.mandept'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入管理单位!'
                                                    }
                                                }, defaultval: <%=szshiju%>
                                            }, <%=szshiju%>);
                                            </c:if>
                                            <c:if test="${ui.hasPerm('MAN_DPTYJQTGC')}">
                                            $("#diveditmandept").addseldpt({
                                                id: 'editmandept',
                                                autoajax: {
                                                    name: 'yjqtgc.mandept'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入管理单位!'
                                                    }
                                                }, defaultval: <%=szju%>
                                            }, <%=szju%>, true);
                                            </c:if>
                                        </script>
                                    </div>
                                </div>


                            </div>
                        </div>


                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">所在航道<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divedithdaoid">
                                        <select class="form-control" id="seledithdao"
                                                onchange="initedithangduan($(this).val())">
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">所在航段<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divedithduanid">
                                        <select class="form-control"
                                                id="seledithduan">
                                            <option value="-1">全部航段</option>
                                        </select>
                                    </div>
                                </div>


                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">具体位置<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="diveditaddress">
                                        <script>
                                            $("#diveditaddress").addinputtext({
                                                id: 'editaddress',
                                                autoajax: {
                                                    name: 'yjqtgc.address'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入具体位置!'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">损失情况</label>

                                    <div class="col-xs-10" id="diveditlosecase">
                                        <script>
                                            $("#diveditlosecase").addtextarea({
                                                id: 'editlosecase',
                                                autoajax: {
                                                    name: 'yjqtgc.losecase'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">主要原因</label>

                                    <div class="col-xs-10" id="diveditmainreason">
                                        <script>
                                            $("#diveditmainreason").addtextarea({
                                                id: 'editmainreason',
                                                autoajax: {
                                                    name: 'yjqtgc.mainreason'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">处理情况<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="diveditrealcase">
                                        <script>
                                            $("#diveditrealcase").addtextarea({
                                                id: "editrealcase",
                                                autoajax: {
                                                    name: 'yjqtgc.realcase'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">参加人员</label>

                                    <div class="col-xs-10" id="diveditparticipants">
                                        <script>
                                            $("#diveditparticipants").addinputtext({
                                                id: "editparticipants",
                                                autoajax: {
                                                    name: 'yjqtgc.participants'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">费用(万元)<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="diveditcost">
                                        <script>
                                            $("#diveditcost").addinputtext({
                                                id: "editcost",
                                                autoajax: {
                                                    name: 'yjqtgc.cost'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入费用!'
                                                    },
                                                    double: {
                                                        msg: '请输入数字'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">恢复时间</label>

                                    <div class="col-xs-8" id="diveditrestoretime">
                                        <script>
                                            $("#diveditrestoretime").addday({
                                                id: 'editrestoretime',
                                                autoajax: {
                                                    name: 'yjqtgc.restoretime'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">备注</label>

                                    <div class="col-xs-10" id="diveditremark">
                                        <script>
                                            $("#diveditremark").addtextarea({
                                                id: "editremark",
                                                autoajax: {
                                                    name: 'yjqtgc.remark'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12"
                                 style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                                附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                <i class="icon-paper-clip" style="color:rgb(35, 82, 124);"></i>
                                <input type="button" class="btn btn-link" value="添加附件" style="padding-left:0px;"
                                       onclick="addpicture('editproject');">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btneditproject" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

    </c:if>
    <!-- 查看工程 -->
    <div class="modal fade" id="modalviewproject" tabindex="-1"
         role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">应急抢通详情</h4>
                </div>
                <div class="modal-body" style="padding:20px 0 15px 0;">
                    <div id="showproject" class="container-fluid form-horizontal"
                         style="width:100%;">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">工程名称<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="divviewname">
                                        <label id="labname" class="control-label"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">事件描述<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="divvieweventdesc">
                                        <textarea id="vieweventdesc" class="form-control" readonly
                                                  style="background-color: transparent"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">发生时间<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divviewstarttime">
                                        <label id="labstarttime" class="control-label"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">管理单位<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divviewmandept">
                                        <label id="labmandept" class="control-label"></label>
                                    </div>
                                </div>


                            </div>
                        </div>


                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">所在航道<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divviewhdaoid">
                                        <label id="labhdaoid" class="control-label"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">所在航段<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divviewhduanid">
                                        <label id="labhduanid" class="control-label"></label>
                                    </div>
                                </div>


                            </div>
                        </div>


                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">具体位置<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="divviewaddress">
                                        <label id="labaddress" class="control-label"></label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">损失情况</label>

                                    <div class="col-xs-10" id="divviewlosecase">
                                        <textarea id="viewlosecase" class="form-control" readonly
                                                  style="background-color: transparent"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">主要原因</label>

                                    <div class="col-xs-10" id="divviewmainreason">
                                        <textarea id="viewmainreason" class="form-control" readonly
                                                  style="background-color: transparent"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">处理情况<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="divviewrealcase">
                                        <textarea id="viewrealcase" class="form-control" readonly
                                                  style="background-color: transparent"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">参加人员</label>

                                    <div class="col-xs-10" id="divviewparticipants">
                                        <label id="labparticipants" class="control-label"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">费用(万元)<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divviewcost">
                                        <label id="labcost" class="control-label"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">恢复时间</label>

                                    <div class="col-xs-8" id="divviewrestoretime">
                                        <label id="labrestoretime" class="control-label"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">备注</label>

                                    <div class="col-xs-10" id="divviewremark">
                                        <textarea id="viewremark" class="form-control" readonly
                                                  style="background-color: transparent"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12"
                                 style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                                附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                <i class="icon-paper-clip" style="color:rgb(35, 82, 124);"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 查看图片 -->
    <div class="modal fade" id="modalyjqtgcpic" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelpicview">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div id="yjqtgcpic" class="modal-body" style="padding:0 0 0 0;">
                </div>
            </div>
        </div>
    </div>
</body>
</html>
