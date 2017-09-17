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
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">
    <link rel="stylesheet" type="text/css"
          href="common/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/common/css/datatable.css">
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet" type="text/css"
          href="page/hdaoyanghu/zhuanxianggongcheng.css">

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
            src="page/hdaoyanghu/zhuanxianggongcheng.js"></script>
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

    <div class="row navcontent shadow border">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">专项工程</p>
                </div>
            </div>
            <div class="row borderbottom">
                <c:choose>
                <c:when test="${ui.hasPerm('MAN_SHIZXGC') || ui.hasPerm('MAN_DPTZXGC')}">
                <div class="col-xs-2">
                    <a class="btn btn-primary" style="margin: 15px 0 15px 0"
                       data-toggle="modal" data-target="#modalnewproject">新建工程</a> <a
                        class="btn btn-default" style="margin: 15px 0 15px 0"
                        onclick="delmulti();">删除</a>
                </div>
                <div class="col-xs-3">
                    </c:when>
                    <c:otherwise>
                    <div class="col-xs-3 col-xs-offset-2">
                        </c:otherwise>
                        </c:choose>
                        <div class="row">
                            <div class="col-xs-5 text-right">
                                <h4 style="margin-top:22px;font-size:16px;">管理单位:</h4>
                            </div>
                            <div class="col-xs-7 text-left" style="margin: 15px 0 15px 0"
                                 id="divselgldw">
                                <script>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_SHENZXGC')}">
                                    $("#divselgldw").addseldpt({
                                        id: 'selgldw',
                                        hint: '请选择管理单位',
                                        selectfn: function () {
                                            loadzxgclist();
                                        },
                                        defaultval: 1
                                    });
                                    </c:when>
                                    <c:otherwise>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_SHIZXGC')||ui.hasPerm('MAN_SHIZXGC')}">
                                    $("#divselgldw").addseldpt({
                                        id: 'selgldw',
                                        hint: '请选择管理单位',
                                        selectfn: function () {
                                            loadzxgclist();
                                        },
                                        defaultval: <%=szshiju%>
                                    }, <%=szshiju%>, false);
                                    </c:when>
                                    <c:otherwise>
                                    <c:choose>
                                    <c:when test="${ui.hasPerm('VIEW_DPTZXGC')||ui.hasPerm('MAN_DPTZXGC')}">
                                    $("#divselgldw").addseldpt({
                                        id: 'selgldw',
                                        hint: '请选择管理单位',
                                        selectfn: function () {
                                            loadzxgclist();
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
                    <div class="col-xs-4">
                        <div class="row">
                            <div class="col-xs-3 text-right">
                                <h4 style="margin-top:22px;font-size:16px;min-width:100px;">修改时间:</h4>
                            </div>
                            <div class="col-xs-9 text-left" id="divtimeregion"
                                 style="margin: 15px 0 0 0">
                                <script>
                                    $("#divtimeregion").adddayregion({
                                        idstart: 'seldaystart',
                                        hintstart: '请选择开始时间',
                                        hintend: '请选择结束时间',
                                        onpickedstart: "loadzxgclist",
                                        onpickedend: "loadzxgclist",
                                        idend: 'seldayend'
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="input-group" style="margin: 15px 0 0 0">
                            <!-- /btn-group -->
                            <input type="text" class="form-control" aria-label="..." placeholder="请输入工程名称"
                                   id="searchContent">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="btnsearch" onclick="loadzxgclist();">搜索
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
                                <th width="10%">管理单位</th>
                                <th width="10%">工程状态</th>
                                <th width="10%">投资（万元）</th>
                                <th width="15%">最后修改时间</th>
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
                         style="width:90%;">
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

                                    <div class="col-xs-8" id="divstartday">
                                        <script>
                                            $("#divstartday").addday({
                                                id: 'starttime',
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

                                    <div class="col-xs-8" id="divendday">
                                        <script>
                                            $("#divendday").addday({
                                                id: 'endtime',
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

                                    <div class="col-xs-8" id="divtzze">
                                        <script>
                                            $("#divtzze").addinputtext({
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

                                    <div class="col-xs-8" id="divgldw">
                                        <script>
                                            <c:if test="${ui.hasPerm('MAN_SHIZXGC')}">
                                            $("#divgldw").addseldpt({
                                                id: 'addgldw',
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
                                            $("#divgldw").addseldpt({
                                                id: 'addgldw',
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

                                    <div class="col-xs-8" id="divjsdw">
                                        <script>
                                            $("#divjsdw").addinputtext({
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

                                    <div class="col-xs-8" id="divsgdw">
                                        <script>
                                            $("#divsgdw").addinputtext({
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

                                    <div class="col-xs-8" id="divjldw">
                                        <script>
                                            $("#divjldw").addinputtext({
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

                                    <div class="col-xs-8" id="divsjdw">
                                        <script>
                                            $("#divsjdw").addinputtext({
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

                                    <div class="col-xs-10" id="divxmgk">
                                        <script>
                                            $("#divxmgk").addtextarea({
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
                    <button id="btnaddproject" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

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

    </c:if>
</body>
</html>
