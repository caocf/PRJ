<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>船舶查询</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../css/ship/cbcxlist.css" rel="stylesheet" type="text/css"/>

    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/ship/ship.js"></script>
</head>
<body>
<section class="content-header">
    <h1>
        船舶查询
    </h1>
</section>

<section class="content">
    <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter"
                     style="text-align:left;">
                    <div style="heigth:100%;width:400px;float: left;border-radius:4px;border:solid 1px #cccccc;margin: 15px 0 15px 20px;">
                        <input type="text" class="form-control"
                               style="height:100%;padding-left:15px;width:330px;border: 0;float: left;"
                               placeholder="请输入船舶名称"
                               id="iptshipname" onblur="showhintname()" onfocus="hidehintname()"/>
                        <i class="fa fa-search"
                           style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                           onclick="searchgoto()"></i>
                        <ul id="search-sort-list" class="search-sort-list list-unstyled">

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</section>
</body>
</html>
