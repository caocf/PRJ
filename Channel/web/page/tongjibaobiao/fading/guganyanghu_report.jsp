<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
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

    <title>骨干</title>

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
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet"
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/unauth.js"></script>
    <script>
        function loadbaobiao() {
            var type = $('#selecttimetype').val();
            var dw = $('#seldw').attr('selitem');
            if (dw == '') {
                dw = 12;
            }
            var starttime = '';
            var endtime = '';
            if (type == STATISTIC_MONTH) {
                var selmonths = $('#selmonthstart').attr('realvalue');
                var selmonthe = $('#selmonthend').attr('realvalue');
                if (selmonths == null || selmonthe == null || selmonths == ''
                        || selmonthe == '') {
                    return;
                }

                var enddate = getTimeFromStr(selmonthe, "yyyy-MM-dd");
                enddate.addMonths(1);
                endtime = getTimeFmt(enddate, "yyyy-MM") + "-01";
                starttime = getTimeFmt(getTimeFromStr(selmonths, "yyyy-MM-dd"),
                                "yyyy-MM")
                        + "-01";
            } else {
                var year = $('#selectyear').val();
                var quarter = '' + $('#selectquarter').val();
                switch (quarter) {
                    case '1':
                        starttime = "" + year + "-01-01";
                        endtime = "" + year + "-04-01";
                        break;
                    case '2':
                        starttime = "" + year + "-04-01";
                        endtime = "" + year + "-07-01";
                        break;
                    case '3':
                        starttime = "" + year + "-07-01";
                        endtime = "" + year + "-10-01";
                        break;
                    case '4':
                        starttime = "" + year + "-10-01";
                        endtime = "" + (parseInt(year) + 1) + "-01-01";
                        break;
                }
            }

            var dptflag = 0;
            if (hasPerm('VIEW_SHENGGYHBB') || hasPerm('VIEW_SHIGGYHBB'))
                dptflag = 1;
            else
                dptflag = 0;

            if (starttime != null && starttime != '' && endtime != null
                    && endtime != '') {
                $('#baobiaoiframe').attr(
                        "src",
                        "statistics/queryggtable?loginid=" + $("#userid").val()
                        + "&flag=" + type + "&starttime=" + starttime
                        + "&endtime=" + endtime + "&dptid=" + dw + "&dptflag=" + dptflag);
            }
        }

        function exportggtable() {
            var type = $('#selecttimetype').val();
            var dw = $('#seldw').attr('selitem');
            if (dw == '') {
                dw = 12;
            }
            var starttime = '';
            var endtime = '';
            if (type == STATISTIC_MONTH) {
                var selmonths = $('#selmonthstart').attr('realvalue');
                var selmonthe = $('#selmonthend').attr('realvalue');
                if (selmonths == null || selmonthe == null || selmonths == ''
                        || selmonthe == '') {
                    return;
                }

                var enddate = getTimeFromStr(selmonthe, "yyyy-MM-dd");
                enddate.addMonths(1);
                endtime = getTimeFmt(enddate, "yyyy-MM") + "-01";
                starttime = getTimeFmt(getTimeFromStr(selmonths, "yyyy-MM-dd"),
                                "yyyy-MM")
                        + "-01";
            } else {
                var year = $('#selectyear').val();
                var quarter = '' + $('#selectquarter').val();
                switch (quarter) {
                    case '1':
                        starttime = "" + year + "-01-01";
                        endtime = "" + year + "-04-01";
                        break;
                    case '2':
                        starttime = "" + year + "-04-01";
                        endtime = "" + year + "-07-01";
                        break;
                    case '3':
                        starttime = "" + year + "-07-01";
                        endtime = "" + year + "-10-01";
                        break;
                    case '4':
                        starttime = "" + year + "-10-01";
                        endtime = "" + (parseInt(year) + 1) + "-01-01";
                        break;
                }
            }

            var dptflag = 0;
            if (hasPerm('VIEW_SHENGGYHBB') || hasPerm('VIEW_SHIGGYHBB'))
                dptflag = 1;
            else
                dptflag = 0;

            if (starttime != null && starttime != '' && endtime != null
                    && endtime != '') {
                window.location.href = $("#basePath").val()
                        + "statistics/exportggtable?loginid=" + $("#userid").val() + "&flag="
                        + type + "&starttime=" + starttime + "&endtime=" + endtime + "&dptid=" + dw + "&dptflag=" + dptflag;
            }
        }
    </script>
</head>

<body style="background:#f0f0f0;padding-bottom: 20px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">


<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">

<div class="container-fluid shadow border" style="background:white;">
    <div class="row navcontenttitle borderbottom">
        <div class="col-xs-12">内河骨干航道例行养护工作统计表</div>
    </div>
    <div class="row borderbottom" style="padding:10px 0 10px 0;">
        <div class="col-xs-1 text-right" style="margin-top:8px;">单位</div>
        <div class="col-xs-2 text-center" id="divseldw">
            <script>
                <c:if test="${ui.hasPerm('VIEW_SHENGGYHBB')}">
                $("#divseldw").addseldpt({
                    id: 'seldw',
                    defaultval: 1,
                    selectfn: function () {
                        loadbaobiao();
                    },
                    autoajax: {
                        name: 'dptid'
                    }
                });
                </c:if>
                <c:if test="${ui.hasPerm('VIEW_SHIGGYHBB')}">
                $("#divseldw").addseldpt({
                    id: 'seldw',
                    defaultval: <%=szshiju%>,
                    selectfn: function () {
                        loadbaobiao();
                    },
                    autoajax: {
                        name: 'dptid'
                    }
                }, <%=szshiju%>);
                </c:if>
                <c:if test="${ui.hasPerm('VIEW_DPTGGYHBB')}">
                $("#divseldw").addseldpt({
                    id: 'seldw',
                    defaultval: <%=szju%>,
                    selectfn: function () {
                        loadbaobiao();
                    },
                    autoajax: {
                        name: 'dptid'
                    }
                }, <%=szju%>, true);
                </c:if>


            </script>
        </div>
        <div class="col-xs-2" style="width:auto;">
            <label>时段</label> <select id="selecttimetype" class="form-control"
                                      style="width:auto;display:inline;margin-left:5px;">
            <option value='1' selected="selected">月度</option>
            <option value='2'>季度</option>
        </select>
            <script>
                $('#selecttimetype').change(function () {
                    var type = $('#selecttimetype').val();
                    if (type == STATISTIC_MONTH) {
                        $('#selmonthregiondiv').removeClass('hide');
                        $('#selquarterdiv').addClass('hide');
                    } else {
                        $('#selmonthregiondiv').addClass('hide');
                        $('#selquarterdiv').removeClass('hide');
                    }

                    loadbaobiao();
                });
            </script>
        </div>
        <div class="col-xs-4 text-right" id="selmonthregiondiv">
            <script type="text/javascript">
                //默认为最近一个月
                var now = new Date();
                now.addMonths(-1);
                var defaultvalstart = getTimeFmt(now, 'yyyy-MM');
                var defaultvalend = getTimeFmt(new Date(), 'yyyy-MM');
                $('#selmonthregiondiv').addmonthregion({
                    idstart: 'selmonthstart',
                    idend: 'selmonthend',
                    defaultvalstart: defaultvalstart,
                    defaultvalend: defaultvalend,
                    hintstart: '请选择开始月份',
                    hintend: '请选择开始月份',
                    onpickedstart: "loadbaobiao",
                    onpickedend: "loadbaobiao"
                });
            </script>
        </div>
        <div class="col-xs-4 hide" id="selquarterdiv">
            <select class="form-control"
                    style="width:auto;display:inline;margin-left:5px;" id="selectyear">
                <script>
                    $('#selectyear').change(function () {
                        loadbaobiao();
                    });
                    ajax(
                            'maintenance/queryggstarttime',
                            {
                                'loginid': $("#userid").val()
                            },
                            function (result) {
                                if (ifResultOK(result)) {
                                    var map = getResultMap(result);
                                    if (map != null) {
                                        var startyear = -1;
                                        var endyear = -1;
                                        try {
                                            startyear = parseInt(map.starttime);
                                            endyear = parseInt(map.endtime);
                                        } catch (e) {
                                            startyear = -1;
                                            endyear = -1;
                                        }
                                        if (startyear != -1
                                                && endyear != -1
                                                && startyear <= endyear) {
                                            for (var year = startyear; year <= endyear; year++) {
                                                $('#selectyear')
                                                        .append(
                                                        $('<option value=\'' + year + '\'>'
                                                                + year
                                                                + '年</option>'));
                                            }
                                        }
                                    }
                                }
                            });
                </script>
            </select> <select id="selectquarter" class="form-control"
                              style="width:auto;display:inline;margin-left:5px;">
            <option value='1' selected="selected">第一季度</option>
            <option value='2'>第二季度</option>
            <option value='3'>第三季度</option>
            <option value='4'>第四季度</option>
        </select>
            <script>
                $('#selectquarter').change(function () {
                    loadbaobiao();
                });
            </script>
        </div>
        <c:if test="${ui.hasPerm('EXPORT_BB') || ui.hasPerm('PRINT_BB')}">
            <div class="col-xs-2">
                <c:if test="${ui.hasPerm('EXPORT_BB')}">
                    <button class="btn btn-default" onclick="exportggtable()">导出</button>
                </c:if>
                <c:if test="${ui.hasPerm('PRINT_BB')}">
                    <button class="btn btn-default"
                            onclick="document.getElementById('baobiaoiframe').contentWindow.print();">打印
                    </button>
                </c:if>
            </div>
        </c:if>
    </div>
    <!-- startprint1 -->
    <div class="row text-center">
        <iframe id="baobiaoiframe" class="noborder"
                style="width:98%;overflow:hidden;"
                onload="reinitIframeHeight('baobiaoiframe')"></iframe>
    </div>
    <!-- endprint1 -->
</div>
</body>
</html>
