
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Home</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/plugins/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/css/common.css">

    <script type="text/javascript" language="javascript"
            src="JS/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/plugins/bootstrap/js/bootstrap.min.js"></script>

</head>

<body style="text-align:center;">

<h1 >更新失败<h1/>
<br>
<p>${result}</p>

<script >
    function wharfdanger()
    {
        //alert('002');
        /*var datastr={};
         datastr['tel']="1399569568625";
         datastr['city']="嘉兴";
         datastr['content']="建议改进界面";*/

        $.ajax({
            url : 'wdangerreport',
            type : 'post',
            dataType : 'json',
            data: {"Ship":"浙湖州货","Goods":"杂货","Goodstype":"危化品","From":"杭州","To":"上海","Tons":"250",
                "Wharfname":"m1","Portime":"2015-9-5"},
            success : function (data)
            {
                //alert(data.data);
                /*$(data.data).each(function (index, item){
                 $('showdata').append()
                 });*/
                document.getElementById("showdata").innerHTML= data.resultcode;
                //console.log(data);
                //$(data);
            }
        })
    }
    function postships()
    {
        //alert('002');
        /*var datastr={};
        datastr['tel']="1399569568625";
        datastr['city']="嘉兴";
        datastr['content']="建议改进界面";*/

        $.ajax({
            url : 'postships',
            type : 'post',
            dataType : 'json',
            data: {"Title":"15","Type":"杂货","From":"杭州","To":"上海","Shiptype":"sss","Shipname":"浙"},
            success : function (data)
            {
                //alert(data.data);
                /*$(data.data).each(function (index, item){
                    $('showdata').append()
                });*/
                document.getElementById("showdata").innerHTML= data.data[0].tons;
                //console.log(data);
                //$(data);
            }
        })
    }
</script>
<div id="showdata"/>
</body>
</html>
