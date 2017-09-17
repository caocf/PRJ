<%@page import="com.channel.model.user.CXtUser" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    List<CZdXzqhdm> manxzqh = (List<CZdXzqhdm>) session.getAttribute("manxzqh");
    String xqstr = "";
    int defaultmanxzqh = -2;
    if (manxzqh != null) {
        for (int i = 0; i < manxzqh.size(); i++) {
            CZdXzqhdm dm = manxzqh.get(i);

            if (i == 0)
                defaultmanxzqh = dm.getId();
            xqstr += dm.getId() + "," + dm.getName() + "," + dm.getType();
            if (i != manxzqh.size() - 1)
                xqstr += ";";
        }
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>数据初始</title>

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
          href="page/hdaoinfo/datatable.css">
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet" type="text/css"
          href="page/hdaoinfo/hdaoinfomanager.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hdaoinfo/hdaoimport.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>

</head>


<body style="min-width:1200px;overflow-y:hidden;background:#f0f0f0;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">
<input type="hidden" id="fswlx" value="">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2" style="min-width: 200px;">
            <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
                 class="pull-left" style="margin-top:10px;"
                 onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
            <label class="pull-left">信息维护</label>
        </div>
        <div class="col-xs-7">
            <a class="btn btn-primary " id="tabhangdao">信息维护</a>
            <a class="btn btn-primary" id="tabmapinfo">物标编辑</a>
            <a class="btn btn-primary active" id="tabimport">数据初始</a>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
</div>
<div class="row navline"></div>

<div class="row navcontent shadow">
    <div class="col-xs-2">
        <div class="row borderbottom">
            <div class="col-xs-12">
                <p class="nomargin nopadding navcontenttitle">数据列表</p>
            </div>
        </div>
        <div id="divleftrow" class="row" style="overflow: auto;">
            <div class="col-xs-12 nopadding" id="divtablelist">
                <!-- 由js生成字典列表 -->
            </div>
        </div>
    </div>
    <div class="col-xs-10 borderleft" id="divright">
        <div class="row borderbottom">
            <div class="col-xs-12">
                <p class="nomargin nopadding navcontenttitle">操作</p>
            </div>
        </div>
        <div class="row borderbottom">
            <div class="col-xs-12" style="padding: 10px 0 10px 10px;" id="divform">
                <div class="col-xs-2" style="width: 180px">
                    <button class="btn btn-primary" onclick="downloadFile();">下载模板</button>
                    <button class="btn btn-primary" onclick="uploadFile();">上传</button>
                </div>
                <div class="col-xs-10">
                    <div id="divimportdata" class="col-xs-6" style="display: none">

                    </div>
                </div>
            </div>

        </div>
        <div id="divrightrow" class="row borderbottom"
             style="overflow: auto;">

        </div>
    </div>
</div>

</body>
</html>
