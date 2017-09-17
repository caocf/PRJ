<%@ page import="com.channel.model.user.CXtUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
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

    <title>专项工程</title>

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
    <link rel="stylesheet" type="text/css"
          href="common/common/css/datatable.css">
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hdaoyanghu/hdaoyanghu.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hdaoyanghu/zhuanxianggongcheng_detail.js"></script>

    <style type="text/css">
        label {
            font-weight: normal;
        }
    </style>

</head>

<body style="height:800px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>


<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="defaultpage" value="<%=defaultpage%>">
<input type="hidden" id="zxgcid" value="<%=request.getParameter("zxgcid")%>">


<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">

<input type="hidden" id="zxgcmc" value="">

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
                <a class="btn btn-primary active" id="tabzhuanxianggongcheng">专项工程</a>
            </c:if>
            <c:if test="${ui.groupHasPerms('应急抢通工程')}">
                <a class="btn btn-primary" id="tabyingjiqiangtonggongcheng">应急抢通</a>
            </c:if>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>

    <ol class="breadcrumb" style="margin-bottom:0px;background:#f0f0f0;">
        <li><a id='backtozxgc'
               href="page/hdaoyanghu/zhuanxianggongcheng.jsp?defaultpage=<%=defaultpage%>">专项工程</a></li>
        <li class="active">工程详情</li>
    </ol>

    <div class="row navcontent shadow border" style="margin-top:0;">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle" id="title">专项养护工程</p>
                </div>
            </div>
            <div class="row" style="margin: 24px 10px 24px 10px;">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle pull-left"
                       id="title2"
                       style="font-size:24px; font-weight:bold;font-color:#333333">专项养护工程</p>
                    <c:if test="${ui.hasPerm('MAN_SHIZXGC') || ui.hasPerm('MAN_DPTZXGC')}">
                        <a class="btn btn-link pull-left" style="margin-top:10px;"
                           id="btneditzxgcload">修改工程基本信息</a> <a
                            class="btn btn-link pull-left"
                            style="margin-top:10px;color:#d9534c;" id="btndelzxgcload">删除工程</a>
                    </c:if>
                </div>
            </div>
            <div class="row" style="margin: 0 10px 0 10px;">
                <div class="col-xs-12">
                    <div class="row hide">
                        <div class="col-xs-12" id="xmztcode"></div>
                    </div>
                    <table class="table border">
                        <tr>
                            <td width=25% style="background:#f0f0f0">实际开工日期</td>
                            <td width=25% id="tdstarttime"></td>
                            <td width=25% style="background:#f0f0f0">实际竣工日期</td>
                            <td width=25% id="tdendtime"></td>
                        </tr>
                        <tr>
                            <td style="background:#f0f0f0">工程状态</td>
                            <td id="tdstatus"></td>
                            <td style="background:#f0f0f0">投资总额(万元)</td>
                            <td id="tdtz"></td>
                        </tr>
                        <tr>
                            <td style="background:#f0f0f0">管理单位</td>
                            <td id="tdgldw"></td>
                            <td style="background:#f0f0f0">建设单位</td>
                            <td id="tdjsdw"></td>
                        </tr>
                        <tr>
                            <td style="background:#f0f0f0">施工单位</td>
                            <td id="tdsgdw"></td>
                            <td style="background:#f0f0f0">监理单位</td>
                            <td id="tdjldw"></td>
                        </tr>
                        <tr>
                            <td style="background:#f0f0f0">设计单位</td>
                            <td id="tdsjdw" colspan="3"></td>
                        </tr>
                        <tr>
                            <td style="background:#f0f0f0">工程概况</td>
                            <td id="tdbz" colspan="3"></td>
                        </tr>
                    </table>
                </div>
            </div>


            <div class="row" style="margin: 0px 10px 24px 10px;">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle pull-left"
                       style="font-size:24px; font-weight:bold;font-color:#333333">附件</p>
                    <c:if test="${ui.hasPerm('MAN_SHIZXGC') || ui.hasPerm('MAN_DPTZXGC')}">
                        <a class="btn btn-primary" style="margin: 5px 0 0 12px;"
                           onclick="uploadfj();">上传附件</a> <a class="btn btn-default"
                                                             style="margin: 5px 0 0 12px;" onclick="delfjmulti()">删除</a>
                    </c:if>
                </div>
            </div>

            <div class="row" style="margin: 0 10px 0 10px;">
                <div class="col-xs-12">
                    <table id="tablefj" cellspacing="0" width="100%" class="table">
                        <thead>
                        <tr>
                            <th width="10%"><input id="cbselfjall" type="checkbox">&nbsp;序号</th>
                            <th width="40%">附件名称</th>
                            <th width="10%">附件大小</th>
                            <th width="20%">最后修改时间</th>
                            <c:if test="${ui.hasPerm('MAN_SHIZXGC') || ui.hasPerm('MAN_DPTZXGC')}">
                                <th width="18%">操作</th>
                            </c:if>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>


            <div class="row" style="margin: 0px 10px 24px 10px;">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle pull-left"
                       style="font-size:24px; font-weight:bold;font-color:#333333">专项工程月度进度情况</p>
                    <c:if test="${ui.hasPerm('MAN_SHIZXGC') || ui.hasPerm('MAN_DPTZXGC')}">
                        <a class="btn btn-primary" style="margin: 5px 0 0 12px;"
                           onclick="addydjdqk()">新增月度进度情况</a> <a class="btn btn-default"
                                                                 style="margin: 5px 0 0 12px;"
                                                                 onclick="delybbmulti();">删除</a>
                    </c:if>
                </div>
            </div>

            <div class="row" style="margin: 0 10px 0 10px;">
                <div class="col-xs-12">
                    <table id="tableybb" cellspacing="0" width="100%" class="table">
                        <thead>
                        <tr>
                            <th width="10%"><input id="cbselybball" type="checkbox">&nbsp;序号</th>
                            <th width="40%">工程名称</th>
                            <th width="30%">月份</th>
                            <th width="18%">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${ui.hasPerm('MAN_SHIZXGC') || ui.hasPerm('MAN_DPTZXGC')}">
    <!-- 删除弹窗 -->
    <div class="modal fade" id="modaldelzxgc" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除专项工程</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            你确定要删除该专项工程&nbsp;<label id="lbmodaldelnames"></label>&nbsp;吗？
                        </p>

                        <p id="pErrMsg" class="text-danger"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btndelzxgc" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="modaleditproject" tabindex="-1"
         role="dialog">
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
                         style="width:90%;">
                        <div class="row hide">
                            <div class="col-xs-12" id="diveditgcid"></div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-2 text-right control-label">工程名称<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-10" id="diveditgcmc">
                                        <script>
                                            $("#diveditgcmc").addinputtext({
                                                id: 'editgcmc',
                                                autoajax: {
                                                    name: 'zxgc.gcmc'
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
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">实际开工日期</label>

                                    <div class="col-xs-8" id="diveditstartday">
                                        <script>
                                            $("#diveditstartday").addday({
                                                id: 'editstarttime',
                                                autoajax: {
                                                    name: 'zxgc.starttime',
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">实际竣工日期</label>

                                    <div class="col-xs-8" id="diveditendday">
                                        <script>
                                            $("#diveditendday").addday({
                                                id: 'editendtime',
                                                autoajax: {
                                                    name: 'zxgc.endtime',
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
                                    <label class="col-xs-4 text-right control-label">投资总额(万元)<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="divedittzze">
                                        <script>
                                            $("#divedittzze").addinputtext({
                                                id: 'edittz',
                                                autoajax: {
                                                    name: 'zxgc.tz'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入工程投资总额!'
                                                    }
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

                                    <div class="col-xs-8" id="diveditgldw">
                                        <script>
                                            <c:if test="${ui.hasPerm('MAN_SHIZXGC')}">
                                            $("#diveditgldw").addseldpt({
                                                id: 'editgldw',
                                                autoajax: {
                                                    name: 'zxgc.gldw'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入管理单位!'
                                                    }
                                                }, defaultval: <%=szshiju%>
                                            }, <%=szshiju%>);
                                            </c:if>
                                            <c:if test="${ui.hasPerm('MAN_DPTZXGC')}">
                                            $("#diveditgldw").addseldpt({
                                                id: 'editgldw',
                                                autoajax: {
                                                    name: 'zxgc.gldw'
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
                                    <label class="col-xs-4 text-right control-label">建设单位<sup
                                            style="color:red;">*</sup></label>

                                    <div class="col-xs-8" id="diveditjsdw">
                                        <script>
                                            $("#diveditjsdw").addinputtext({
                                                id: 'editjsdw',
                                                autoajax: {
                                                    name: 'zxgc.jsdw'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入建设单位!'
                                                    }
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">施工单位</label>

                                    <div class="col-xs-8" id="diveditsgdw">
                                        <script>
                                            $("#diveditsgdw").addinputtext({
                                                id: 'editsgdw',
                                                autoajax: {
                                                    name: 'zxgc.sgdw'
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
                                    <label class="col-xs-4 text-right control-label">监理单位</label>

                                    <div class="col-xs-8" id="diveditjldw">
                                        <script>
                                            $("#diveditjldw").addinputtext({
                                                id: 'editjldw',
                                                autoajax: {
                                                    name: 'zxgc.jldw'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">设计单位</label>

                                    <div class="col-xs-8" id="diveditsjdw">
                                        <script>
                                            $("#diveditsjdw").addinputtext({
                                                id: 'editsjdw',
                                                autoajax: {
                                                    name: 'zxgc.sjdw'
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
                                    <label class="col-xs-2 text-right control-label">工程概况</label>

                                    <div class="col-xs-10" id="diveditxmgk">
                                        <script>
                                            $("#diveditxmgk").addtextarea({
                                                id: 'editbz',
                                                autoajax: {
                                                    name: 'zxgc.bz'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
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


    <!-- 新增月报表 -->
    <div class="modal fade" id="modaladdybb" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增月度进度情况</h4>
                </div>
                <div class="modal-body">
                    <div id="formaddybb" class="container-fluid form-horizontal">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">工程名称<sup
                                            style="color:red">*</sup></label>

                                    <div class="col-xs-4" id="divdwmc">
                                        <script>
                                            $("#divdwmc").addinputtext({
                                                id: 'ybbdwmc',
                                                disabled: true,
                                                autoajax: {
                                                    name: 'cyhybb.dwmc'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入单位名称'
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
                                    <label class="col-xs-4 text-right control-label">月份</label>

                                    <div class="col-xs-4" id="divyf">
                                        <script>
                                            var nowday = getTimeFmt(new Date(),
                                                    'yyyy-MM');
                                            $("#divyf").addmonth({
                                                id: 'ybbyf',
                                                defaultval: nowday,
                                                autoajax: {
                                                    name: 'cyhybb.ny'
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
                                    <label class="col-xs-4 text-right control-label">本月完成金额(万元)</label>

                                    <div class="col-xs-4" id="divbywcje">
                                        <script>
                                            $("#divbywcje").addinputtext({
                                                id: 'ybbbywcje',
                                                autoajax: {
                                                    name: 'cyhybb.bywcje',
                                                    defaultval: 0.0
                                                },
                                                validators: {
                                                    number: {}
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
                                    <label class="col-xs-4 text-right control-label">工程状态</label>

                                    <div class="col-xs-4" id="divxmzt"></div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">项目进度情况</label>

                                    <div class="col-xs-8" id="divjdqk">
                                        <script>
                                            $("#divjdqk").addtextarea({
                                                id: 'ybbjdqk',
                                                autoajax: {
                                                    name: 'cyhybb.xmjdqk'
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
                                    <label class="col-xs-4 text-right control-label">备注</label>

                                    <div class="col-xs-8" id="divbz">
                                        <script>
                                            $("#divbz").addinputtext({
                                                id: 'ybbbz',
                                                autoajax: {
                                                    name: 'cyhybb.bz'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <h4>
                                <small>注:&nbsp;1. 凡列入年度预算的专项养护工程，均需填写本表，从前期工作开始填写。</small>
                            </h4>
                            <h4>
                                <small>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.
                                    工程进度情况应按主要工程量(如护岸古砌筑、陆上土方开挖、水下土方疏浚、管理码头桩基、上部结构等)详细说明，工程完工后其主要工程量均应填写完整。
                                </small>
                            </h4>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnaddybb" type="button" class="btn btn-primary"
                            onclick="addybb();">保存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- 删除弹窗 -->
    <div class="modal fade" id="modaldelfj" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除附件</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <p style="width:100%;">
                            你确定要删除附件&nbsp;<label id="lbmodaldelfjnames"></label>&nbsp;吗？
                        </p>

                        <p id="pErrMsg" class="text-danger"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btndelfj" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 删除弹窗 -->
    <div class="modal fade" id="modaldelybb" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除月度进度情况</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <p style="width:100%;">
                            你确定要删除月度进度情况&nbsp;<label id="lbmodaldelybbnames"></label>&nbsp;吗？
                        </p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btndelybb" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 编辑月报表 -->
    <div class="modal fade" id="modaleditybb" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">修改月度进度情况</h4>
                </div>
                <div class="modal-body">
                    <div id="formeditybb" class="container-fluid form-horizontal">
                        <div class="row hide">
                            <div class="col-xs-12" id="diveditybbid"></div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label class="col-xs-4 text-right control-label">工程名称<sup
                                            style="color:red">*</sup></label>

                                    <div class="col-xs-4" id="diveditdwmc">
                                        <script>
                                            $("#diveditdwmc").addinputtext({
                                                id: 'editybbdwmc',
                                                autoajax: {
                                                    name: 'cyhybb.dwmc'
                                                },
                                                validators: {
                                                    notempty: {
                                                        msg: '请输入工程名称'
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
                                    <label class="col-xs-4 text-right control-label">月份</label>

                                    <div class="col-xs-4" id="divedityf">
                                        <script>
                                            var nowday = getTimeFmt(new Date(),
                                                    'yyyy-MM');
                                            $("#divedityf").addmonth({
                                                id: 'editybbyf',
                                                defaultval: nowday,
                                                autoajax: {
                                                    name: 'cyhybb.ny'
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
                                    <label class="col-xs-4 text-right control-label">本月完成金额(万元)</label>

                                    <div class="col-xs-4" id="diveditbywcje">
                                        <script>
                                            $("#diveditbywcje").addinputtext({
                                                id: 'editybbbywcje',
                                                autoajax: {
                                                    name: 'cyhybb.bywcje',
                                                    defaultval: 0.0
                                                },
                                                validators: {
                                                    number: {}
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
                                    <label class="col-xs-4 text-right control-label">工程状态</label>

                                    <div class="col-xs-4" id="diveditxmzt">
                                        <script>
                                            ajax('dic/querydicattr',
                                                    {
                                                        loginid: $("#userid").val(),
                                                        name: 'zxgczt'
                                                    },
                                                    function (data) {
                                                        if (ifResultOK(data)) {
                                                            var records = getResultRecords(data);
                                                            if (records) {
                                                                var data = {};
                                                                for (var i = 0; i < records.data.length; i++) {
                                                                    var dict = records.data[i];
                                                                    var key = dict.id;
                                                                    var value = dict.attrdesc;
                                                                    data[key] = value;
                                                                }
                                                                $("#diveditxmzt").addselect(
                                                                        {
                                                                            data: data,
                                                                            validators: {
                                                                                notempty: {
                                                                                    msg: '请选择工程状态!'
                                                                                }
                                                                            },
                                                                            id: 'editybbxmzt',
                                                                            autoajax: {
                                                                                name: 'cyhybb.xmzt'
                                                                            }

                                                                        });
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
                                    <label class="col-xs-4 text-right control-label">项目进度情况</label>

                                    <div class="col-xs-8" id="diveditjdqk">
                                        <script>
                                            $("#diveditjdqk").addtextarea({
                                                id: 'editybbjdqk',
                                                autoajax: {
                                                    name: 'cyhybb.xmjdqk'
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
                                    <label class="col-xs-4 text-right control-label">备注</label>

                                    <div class="col-xs-8" id="diveditbz">
                                        <script>
                                            $("#diveditbz").addinputtext({
                                                id: 'editybbbz',
                                                autoajax: {
                                                    name: 'cyhybb.bz'
                                                }
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <h4>
                                <small>注:&nbsp;1. 凡列入年度预算的专项养护工程，均需填写本表，从前期工作开始填写。</small>
                            </h4>
                            <h4>
                                <small>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.
                                    工程进度情况应按主要工程量(如护岸古砌筑、陆上土方开挖、水下土方疏浚、管理码头桩基、上部结构等)详细说明，工程完工后其主要工程量均应填写完整。
                                </small>
                            </h4>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btneditybb" type="button" class="btn btn-primary"
                            onclick="editybb();">保存
                    </button>
                </div>
            </div>
        </div>
    </div>

</c:if>
<!-- 查看月报表 -->
<div class="modal fade" id="modalviewybb" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">查看月报表</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid form-horizontal">
                    <table class="table table-bordered">
                        <tr>
                            <td style="width:30%">工程名称</td>
                            <td id="tdviewdwmc"></td>
                        </tr>
                        <tr>
                            <td>月份</td>
                            <td id="tdviewyf"></td>
                        </tr>
                        <tr>
                            <td>本月完成金额(万元)</td>
                            <td id="tdviewbywcje"></td>
                        </tr>
                        <tr>
                            <td>工程状态</td>
                            <td id="tdviewxmzt"></td>
                        </tr>
                        <tr>
                            <td>项目进度情况</td>
                            <td id="tdviewjdqk"></td>
                        </tr>
                        <tr>
                            <td>备注</td>
                            <td id="tdviewbz"></td>
                        </tr>

                        <tr>
                            <td colspan="2">
                                <h4>
                                    <small>注:&nbsp;1. 凡列入年度预算的专项养护工程，均需填写本表，从前期工作开始填写。</small>
                                </h4>
                                <h4>
                                    <small>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.
                                        工程进度情况应按主要工程量(如护岸古砌筑、陆上土方开挖、水下土方疏浚、管理码头桩基、上部结构等)详细说明，工程完工后其主要工程量均应填写完整。
                                    </small>
                                </h4>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnaddybb" type="button" class="btn btn-primary"
                        onclick="addybb();">保存
                </button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
