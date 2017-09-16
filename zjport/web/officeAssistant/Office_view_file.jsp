<%@ page import="com.zjport.model.TKnowledgeBase" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zjport.model.TAttachment" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/25
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>查看文档</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>


  <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
  <!-- jQuery 2.1.4 -->
  <script src="../js/common/jQuery-2.1.4.min.js"></script>
  <!-- CK Editor -->
  <script src="../js/ckeditor/ckeditor.js"></script>
  <!-- 页面js -->
  <script src="../js/officeAssistant/Office_view_file.js"></script>
</head>
<%
  TKnowledgeBase knowledgeBase = (TKnowledgeBase)request.getAttribute("knowledgeBase");
  String structureName = (String)request.getAttribute("structureName");
  String fromUser = (String)request.getAttribute("fromUser");
  List<TAttachment> attachmentList = (List<TAttachment>)request.getAttribute("attachmentList");
%>
<body>
<section class="content-header" style="background-color: white;padding: 15px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    <img src="../image/information/ic_details.png">
    文档详情
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>
<section class="content" style="padding-top:0; margin-top: 5px;" >
  <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;float:left;box-shadow: 0 0 0 white;">.
    <div style="float: left;width: 100%;padding:0 10px;">
      <button type="button" class="btn btn-primary" onclick=window.location.href='baseEdit?id=<%=knowledgeBase.getStBaseId()%>'>编辑
      </button>
      <button type="button" class="btn btn-default" data-toggle="modal" data-target="#deleteModal">删除
      </button>
    </div>
    <div class="box-body" style="float: left;width: 100%;">
      <div style="float: left;margin-top:20px;background-color: white;width:100%;padding: 10px 0px;height:570px;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
        <div style="float: left;width:100%;height:50px;border-bottom: solid 1px rgb(239,239,239);">
          <b style="margin-left:10px;float: left;font-size: 18px;color: #333;line-height: 50px;"><%=knowledgeBase.getStBaseTitle()%></b>

          <%--<img style="margin-top:10px;margin-right:15px;float: right;" src="../image/information/information_state_4.png">--%>

          <%--<div style="margin-top:10px;margin-right:15px;float: right;width: 80px;height:30px;background-color: rgb(243,178,0);color:white;line-height: 30px;text-align: center;">待审批</div>--%>
        </div>
        <div style="float: left;width:100%;height:100px;padding:5px 10px;border-bottom: solid 1px rgb(239,239,239);">
          <div style="float: left;width:50%;height:100%;border-right:solid 1px rgb(239,239,239)">
            <div style="float: left;width:100%;height:45px;">
              <span style="float: left;color:#666;font-size: 15px;line-height:45px;">发布人员：</span>
              <div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;margin-top:10px;margin-left:15px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=fromUser%></div>
            </div>
            <div style="float: left;width:100%;height:45px;">
              <span style="float: left;color:#666;font-size: 15px;line-height:45px;">关键词：</span>
              <div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;margin-top:10px;margin-left:30px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=knowledgeBase.getStBaseKeyword()%></div>
            </div>
          </div>
          <div style="float: left;width:49%;height:100%;padding:0 10px;">
            <div style="float: left;width:100%;height:45px;">
              <span style="float: left;color:#666;font-size: 15px;line-height:45px;">所属类别：</span>
              <div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;margin-top:10px;margin-left:30px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=structureName%></div>
            </div>
          </div>
        </div>
        <div style="float: left;width:100%;height:300px;padding:5px 10px;border-bottom: solid 1px rgb(239,239,239);overflow-y: auto;word-break: break-all;">
          <%=knowledgeBase.getStBaseContent()%>
        </div>
        <%
          if(attachmentList != null) {
            for(int i = 0; i<attachmentList.size(); i++) {
              TAttachment attachment = attachmentList.get(i);
        %>
        <div style="float: left;margin-left:10px;margin-top:10px;height:40px;padding:10px 10px;background-color: rgb(250,250,250);border:solid 1px rgb(239,239,239);border-radius:4px;">
          <span style="float: left;line-height: 20px;color:#666;"><%=attachment.getStFileName()%></span>
          <div style="float: left;width:40px;height:20px;margin-left:10px;line-height: 20px;text-align: center;background-color: rgb(1,134,237);color: #ffffff;cursor:pointer;">
            <a href="../common/downloadFile?id=<%=attachment.getStAttachmentId()%>" style="color: #fff" >下载</a>
          </div>
        </div>
        <%
            }
          }
        %>
        <%--<div style="float: left;width:100%;">
          <div style="float: left;margin-left:10px;margin-top:10px;height:40px;padding:10px 10px;background-color: rgb(250,250,250);border:solid 1px rgb(239,239,239);border-radius:4px;">
            <span style="float: left;line-height: 20px;color:#666;">1.jpg</span>
            &lt;%&ndash;<div style="float: left;width:40px;height:20px;margin-left:10px;line-height: 20px;text-align: center;background-color: rgb(1,134,237);color: #ffffff;cursor:pointer;">预览</div>&ndash;%&gt;
            <div style="float: left;width:40px;height:20px;margin-left:10px;line-height: 20px;text-align: center;background-color: rgb(1,134,237);color: #ffffff;cursor:pointer;">下载</div>
          </div>
        </div>--%>
      </div>
    </div><!-- /.box-body -->
  </div><!-- /. box -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="margin-top:300px;">
    <div class="modal-content" style="width: 400px;">
      <div class="modal-header">
        <h4 class="modal-title" id="myModalLabel">
          提示
        </h4>
      </div>
      <div class="modal-body" style="line-height: 20px;">
        <div style="background-color: rgb(255,168,0);height:20px;width:20px;border-radius:10px;text-align: center; color:white;float:left;">!</div>
        &nbsp;您确定要删除么？
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="">
          确定
        </button>
        <button type="button" class="btn btn-default"
                data-dismiss="modal">取消
        </button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal -->
</div><!-- /.modal -->
</body>
</html>
