<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/25
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>新建文档</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

  <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
  <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
  <!-- jQuery 2.1.4 -->
  <script src="../js/common/jQuery-2.1.4.min.js"></script>
  <!-- CK Editor -->
  <script src="../js/ckeditor/ckeditor.js"></script>
  <!-- 页面js -->
  <script src="../js/officeAssistant/Office_add_file.js"></script>
</head>
<%
  String structureId = (String)request.getAttribute("structureId");
%>
<body>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    <img src="../image/information/ic_released.png">
    新建文档
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>
<section class="content" style="padding-top:0; margin-top: 5px;" >
  <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;">
    <form class="form-horizontal" enctype="multipart/form-data" id="knowledgeForm" action="fileSubmit" method="post">
      <div class="box-body">
        <div style="background-color: white;width:100%;padding-top: 10px;box-shadow: 0 0 3px #ccc;">
          <div class="form-group" >
            <label for="title" class="col-sm-1 control-label">主题<span style="color: red">*</span>：</label>
            <div class="col-sm-11">
              <input type="input" class="form-control" style="border: 0;" id="title" name="title" maxlength="60" placeholder="请输入文章标题，标题文字不能超过60个中文字符">
            </div>
          </div>
          <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
            <label for="keyword" class="col-sm-1 control-label">关键字&nbsp;&nbsp;&nbsp;&nbsp;：</label>
            <div class="col-sm-11">
              <input type="input" style="border: 0;" class="form-control" id="keyword" name="keyword" maxlength="30" placeholder="请输入关键字">
            </div>
          </div>
          <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
            <label class="col-sm-1 control-label">添加附件&nbsp;：</label>
            <div class="btn btn-default btn-file" style="margin-left: 15px;">
              <i class="fa fa-paperclip"></i> 添加附件
              <input type="file" name="attachment" id="attach   ment" multiple="multiple"/>
            </div>&nbsp;&nbsp;<span id="filenamespan"></span>
          </div>
          <div class="form-group"style="margin-bottom: 0;">
            <textarea id="editor1" name="editor1" rows="10" cols="80" placeholder="请输入文章内容"></textarea>
            <script>
              /*  富文本编辑器  */
              $(function () {
                // Replace the <textarea id="editor1"> with a CKEditor
                // instance, using default configuration.
                CKEDITOR.replace('editor1');

              });
            </script>
          </div>
        </div>
        <%--<span style="line-height: 30px;">创建人：材料</span>--%>
      </div><!-- /.box-body -->
      <input type="hidden" name="type" id="type" value=""/>
      <div class="box-footer" style="background-color: rgba(0,0,0,0);">
        <div class="pull-left" >
          <button type="button" class="btn btn-primary" onclick="send()"><i class="fa fa-envelope-o"></i>发布</button>
          <%--<button class="btn btn-default">预览</button>--%>
          <%--<button class="btn btn-default">取消</button>--%>
        </div>
        <%--<button class="btn btn-default"><i class="fa fa-times"></i> 发布</button>--%>
      </div><!-- /.box-footer -->
      <input type="hidden" name="structureId" id="structureId" value="<%=structureId%>"/>
    </form>
  </div><!-- /. box -->
</section>
</body>
</html>
