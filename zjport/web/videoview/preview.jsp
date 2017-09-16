<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.util.CommonConst" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>实时预览</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

  <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
  <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
  <link href="../css/common/bluetree.css" rel="stylesheet" type="text/css" />
  <!-- jQuery 2.1.4 -->
  <script src="../js/common/jQuery-2.1.4.min.js"></script>
  <!-- CK Editor -->
  <script src="../js/ckeditor/ckeditor.js"></script>
  <!-- 页面js -->
  <script src="../js/common/bootpaging.js"></script>
  <script src="../js/videoview/preview.js"></script>
  <!--css-->
  <style type="text/css">

    /*.ztree * {font-size: 10pt;font-family:"Microsoft Yahei",Verdana,Simsun,"Segoe UI Web Light","Segoe UI Light","Segoe UI Web Regular","Segoe UI","Segoe UI Symbol","Helvetica Neue",Arial}*/
    /*.ztree li ul{ margin:0; padding:0}*/
    /*.ztree li {line-height:30px;}*/
    /*.ztree li a {width:100%;height:30px;padding-top: 0px;}*/
    /*.ztree.ztreestyle li a:hover {text-decoration:none; background-color: rgb(0,103,172);color:white;}*/
    /*/!*.ztreestyle li a span.button.switch {visibility:hidden}*!/*/
    /*.ztreestyle.showIcon li a span.button.switch {visibility:visible}*/
    /*.ztree li a.curSelectedNode {background-color:rgb(0,103,172);border:0;height:30px;color:white;}*/
    /*.ztree li a.curSelectedNode span.button{background-image:url("../image/left_menuForOutLook_white.png");}*/
    /*.ztree li a.curSelectedNode .blueword{color:white!important;}*/
    /*.ztree li span {line-height:30px;}*/
    /*.ztree .node_name {width: 90%;display: inline-block;margin-left:5px;}*/
    /*.ztree .node_name>span {float: left}*/
    /*.ztreestyle li span.button {margin-top: -7px;}*/
    /*.ztreestyle li span.button.switch {width: 16px;height: 16px;}*/

    /*.ztreestyle li a.level0 span {font-weight: bold;}*/
    /*.ztreestyle li span.button {background-image:url("../image/left_menuForOutLook.png"); *background-image:url("../image/left_menuForOutLook.gif")}*/
    /*.ztreestyle li span.button.switch.level0 {width: 20px; height:20px}*/
    /*.ztreestyle li span.button.switch.level1 {width: 20px; height:20px}*/
    /*.ztreestyle li span.button.noline_open {background-position: 0 0;}*/
    /*.ztreestyle li span.button.noline_close {background-position: -18px 0;}*/
    /*.ztreestyle li span.button.noline_open.level0 {background-position: 0 -18px;}*/
    /*.ztreestyle li span.button.noline_close.level0 {background-position: -18px -18px;}*/
    /*.qzidiv{*/
      /*cursor: pointer;*/
      /*float: right;*/
      /*width: 30px;*/
      /*height:29px;*/
      /*color: white;*/
      /*text-align: center;*/
    /*}*/
    /*.qzidiv>i{*/
      /*line-height: 29px;*/
      /*font-size: 15px;*/
    /*}*/
    /*.qzidiv:hover{*/
      /*background-color: rgb(0,88,146);*/
    /*}*/
    .clickword{
      color:rgb(31,116,180);
      cursor: pointer;
    }
    .fa-4x{
      cursor: pointer;
      color: #cccccc;
      float: left;
      margin-left: 20px;
    }
    .fa-4x:hover{
      color: #0066cc;
    }
    div.checkbox{
      padding:4px 10px;
      width: 150px;
      margin:0;
    }
    #gjdiv tr,#gjdiv,#selecttslan,#qxbjlan,#tjsxlan{
      display: none;
    }
  </style>
</head>
<body>
<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
  <b style="font-size: 20px;">实时预览</b>
  <span style="float: right;font-size: 14px;">视频查看&gt实时预览</span>
</div>
<div style="float: left;width:100%;height:700px;padding:0 10px;">
  <div style="float: left;width:30%;height:100%;padding-right:20px;">
    <div style="float: left;width:100%;height:100%;background-color: white;border:solid 1px #ccc;overflow: auto;">
      <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);">
        <div class="phonefz" onclick="phonetab(this,1)" style="border:solid 1px #ccc;border-top:solid 2px rgb(0,103,172);border-bottom:solid 2px white;">设备列表</div>
        <div class="phonefz" onclick="phonetab(this,2)">搜藏夹</div>
      </div>
      <!-- 搜索栏-->
      <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
        <div style="heigth:100%;width:80%;float: left;border-radius:4px;border:solid 1px #cccccc;">
          <input id='videoselectinput'type="text" class="form-control" style="height:100%;padding:5px;width:50%;border: 0;float: left;"placeholder="搜索" />
        <i class="fa fa-angle-down gjzkbtn" style="float: right;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border-left:solid 1px #cccccc;background-color: rgb(250,250,250)"></i>
        <i class="fa fa-angle-up gjzkbtn" style="display:none;float: right;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border-left:solid 1px #cccccc;background-color: rgb(250,250,250)"></i>
          <i class="fa fa-search" style="cursor:pointer;float: right;height:30px;width:28px;line-height: 30px;text-align: center;" onclick="selectbtnclick()"></i>
          <div style="float: right;height:30px;width:28px;">
            <i class="fa fa-close" style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;" id="clearbtn"></i>
          </div>
        </div>
        <button class="btn btn-primary" style="width:18%;padding:6px 0;float: right;display:none;" id="scjeditbtn">编辑</button>
      </div>
      <!--高级搜索烂-->
      <div id='gjdiv' style="float: left;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px 20px;line-height: 30px;">
        <table class="table" style="border: 0;margin-bottom: 0" >
          <tr id="gjtj1check_tr">
            <td style="line-height: 31px;">设备IP地址：</td>
            <td><input type="text" style="height:30px;" class="form-control" placeholder="请输入设备编号"/></td>
          </tr>
          <tr id="gjtj2check_tr">
            <td style="line-height: 31px;">设备分组：</td>
            <td><input type="text" style="height:30px;" class="form-control" placeholder="请输入设备分组"/></td>
          </tr>
          <tr id="gjtj3check_tr">
            <td style="line-height: 31px;">行政区划：</td>
            <td><input type="text" style="height:30px;" class="form-control" placeholder="请输入行政区划"/></td>
          </tr>
          <tr id="gjtj4check_tr">
            <td style="line-height: 31px;">设备类型：</td>
            <td><input type="text" style="height:30px;" class="form-control" placeholder="请输入设备类型"/></td>
          </tr>
          <tr id="gjtj5check_tr">
            <td style="line-height: 31px;">设备名称：</td>
            <td><input type="text" style="height:30px;" class="form-control" placeholder="请输入设备名称"/></td>
          </tr>
        </table>
        <button class="btn btn-primary" style="width: 100%;">搜索</button>
        <div class="dropdown" style="float: left;width:150px;">
        <span class="dropdown-toggle" id="gjtjspan" data-toggle="dropdown" style="float: left;color: #0063dc;cursor: pointer;">高级搜索条件&nbsp;<i class="fa fa-sort-desc"></i></span>
          <ul  class="dropdown-menu" role="menu" aria-labelledby="gjtjspan" id="gjtjul" style="width: auto;height: auto;">
            <div class="checkbox  " data-stopPropagation="true" >
              <label>
                <input type="checkbox" class="gjtjcheckbox" style="margin-top:8px;" id="gjtj1check"> 设备IP地址
              </label>
            </div>
            <div class="checkbox  " data-stopPropagation="true">
              <label>
                <input type="checkbox" class="gjtjcheckbox" style="margin-top:8px;" id="gjtj2check"> 设备分组
              </label>
            </div>
            <div class="checkbox  " data-stopPropagation="true" >
              <label>
                <input type="checkbox" class="gjtjcheckbox" style="margin-top:8px;" id="gjtj3check"> 行政区划
              </label>
            </div>
            <div class="checkbox  " data-stopPropagation="true" >
              <label>
                <input type="checkbox" class="gjtjcheckbox" style="margin-top:8px;" id="gjtj4check"> 设备类型
              </label>
            </div>
            <div class="checkbox  " data-stopPropagation="true" >
              <label>
                <input type="checkbox" class="gjtjcheckbox" style="margin-top:8px;" id="gjtj5check"> 设备名称
              </label>
            </div>
          </ul>
        </div>
      </div>
      <!--搜索结果提示栏-->
      <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px 20px;line-height: 30px;" id="selecttslan">
        <span style="color: #0063dc;float: left;cursor: pointer" id="tsfhbtn">返回</span>
        <span style="color: #c6c6c6;float: right;">2条结果</span>
      </div>
      <!--取消编辑栏-->
      <div style="float: left;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px 20px;" id="qxbjlan">
          <button class="btn btn-danger" style="width: 55%;float: left;" onclick="delsclnode()">删除</button>
          <button class="btn btn-default" style="width: 40%;float: right;" id="qxbjbtn">取消编辑</button>
      </div>
      <!--筛选条件栏-->
      <div style="float: left;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px 20px;" id="tjsxlan">
        <div class="btn-group" style="width: 50%;float: left;">
          <button type="button" class="btn btn-default dropdown-toggle" style="float: left;width: 100%;"
                  data-toggle="dropdown">
            <span id="xzqhname">行政区划</span> <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" role="menu" style="width: 100%;height: 400px;" id="xzqhul">
            <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
            <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
            <ul id="xzqhtree" class="ztree bluetree"></ul>
          </ul>
        </div>
        <div class="btn-group" style="width: 50%;float: left;">
          <button type="button" class="btn btn-default dropdown-toggle" style="float: left;width: 100%;"
                  data-toggle="dropdown">
            设备类型 <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" role="menu" style="width: 100%;height:400px;">
            <ul id="" class="ztree"></ul>
          </ul>
        </div>
      </div>
      <!--搜索信息展示栏-->
      <div style="float: left;width:100%;" class="treediv">
        <ul id="konwledgeTree" class="ztree bluetree"></ul>
        <ul id="sclTree" class="ztree" style="display: none;"></ul>
        <script type="text/javascript">
          $(document).ready(function(){
            var treeObj = $("#konwledgeTree");
            var xzqhtreeObj = $("#xzqhtree");
            var scltreeObj = $("#sclTree");
            $.fn.zTree.init(treeObj, konwledgesetting, listtreeNodes);
            $.fn.zTree.init(xzqhtreeObj, konwledgesetting, xzqhtreeNodes);
            $.fn.zTree.init(scltreeObj, sclsetting, scltreeNodes);
            $('.ztree.ztreestyle li a').bind({
              mouseover:function(){
                $(this).find('.blueword').css('color','white');
              },
              mouseout:function(){
                $(this).find('.blueword').css('color','#0072b1');
              }
            });
            treeObj.hover(function () {
              if (!treeObj.hasClass("showIcon")) {
                treeObj.addClass("showIcon");
              }
            }, function() {
              treeObj.removeClass("showIcon");
            });


//            scltreeObj.hover(function () {
//              if (!scltreeObj.hasClass("showIcon")) {
//                scltreeObj.addClass("showIcon");
//              }
//            }, function() {
//              scltreeObj.removeClass("showIcon");
//            });


            xzqhtreeObj.hover(function () {
              if (!xzqhtreeObj.hasClass("showIcon")) {
                xzqhtreeObj.addClass("showIcon");
              }
            }, function() {
              xzqhtreeObj.removeClass("showIcon");
            });


            $("#xzqhul").find('a').attr('data-stopPropagation',true);
            $("#xzqhul,#gjtjul").on("click", "[data-stopPropagation]", function(e) {
              e.stopPropagation();
            });
          });
        </script>
      </div>
    </div>
  </div>
  <div style="float: left;width:69%;height:100%;background-color: white;border:solid 1px #ccc;">
    <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding-left:10px;line-height: 50px;text-align: left;">
      加油站
      <span style="float: right;color: #0066cc;margin-right:20px;cursor: pointer;">
      <i class="fa fa-scissors"></i>&nbsp;截屏
      </span>
    </div>
    <!-- 对应信息列表-->
    <div style="float: left;width:100%;">
      <div style="width: 100%;">
      <i class="fa fa-square-o fa-4x" ></i>
      <i class="fa fa-plus-square-o fa-4x"></i>
      </div>
    </div>
  </div>
</div>
</body>
</html>
