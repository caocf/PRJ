<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/10/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.util.CommonConst" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.model.TUser" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人中心</title>

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
<%--    <script src="../js/officeAssistant/addressList.js"></script>--%>
    <script src="../js/system/personCenter.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
    <style type="text/css">
        .clickword{
            color:rgb(31,116,180);
            cursor: pointer;
        }
        .qzdiv{
            float: left;
            width: 100%;
            height:40px;
            line-height: 40px;
            padding-left: 20px;
            cursor: pointer;
        }
        #qzbq{
            float: left;
            width: 100%;
            display: none;
        }
    </style>
</head>
<%
    TUser user = (TUser)request.getAttribute("user");
%>
<body>
<%--<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;"></b>
    <span style="float: right;font-size: 14px;">个人工作台&gt个人中心</span>
</div>--%>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        个人中心
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>
<div style="float: left;width:100%;height:700px;padding:0 10px;">
    <div class="modal-body" style="height:560px;background-color: rgb(241,242,246);">
        <div id='userimgdiv'style="float: left;width:150px;height:200px;background-image: url('../image/img_photo.png');background-repeat: no-repeat;background-size: cover;"></div>
        <input type="file" id="userimg" name="userimg" style="display: none;"/>
        <div style="height:100%;margin-left:165px;">
            <table class="table" style="background-color: white;border:solid 1px #ccc;">
                <tr>
                    <td colspan="2"><b>账户信息</b></td>
                </tr>
                <tr>
                    <td class="col-xs-2">用户账号：</td>
                    <td class="col-xs-10" id="account_info">
                        <%=user.getStAccount()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">用户密码：</td>
                    <td class="col-xs-10" id="password_info">
                        ******
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">所属角色：</td>
                    <td class="col-xs-10">
                        <span id="js_info"></span>
                        <input type="hidden" id="user_js"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <%--<a data-toggle="modal" href="#" data-target="#authorityModal" onclick="selectQx()">查看权限</a>--%>
                    </td>
                </tr>
                <%--<tr>
                  <td class="col-xs-2">在职状态<span style="color: red;">*</span>:</td>
                  <td class="col-xs-10">
                    在职
                  </td>
                </tr>--%>

            </table>
            <table class="table" style="background-color: white;margin-top:20px;border:solid 1px #ccc;">
                <tr>
                    <td colspan="2"><b>基本信息</b> &nbsp;&nbsp;<span style="color: #c9cccf;">这是同步过来的信息，在本系统无法修改</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2">职员姓名：</td>
                    <td class="col-xs-10" id="user_info">
                        <%=user.getStUserName()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">职&nbsp;&nbsp;位：</td>
                    <td class="col-xs-10" id="position_info">
                        <%=user.getStPositionId()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">所在单位：</td>
                    <td class="col-xs-10" id="org_info">
                        <%=user.getStOrgId()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">所属部门：</td>
                    <td class="col-xs-10" id="dept_info">
                        <%=user.getStDepartmentId()%>
                    </td>
                </tr>
                <%--<tr>
                    <td class="col-xs-2">上级机构：</td>
                    <td class="col-xs-10" id="parent_org">
                        123123@qq.com
                    </td>
                </tr>--%>
                <tr>
                    <td class="col-xs-2">办公电话：</td>
                    <td class="col-xs-10" id="office_phone">
                        <%=user.getStOfficePhone()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">手机号码：</td>
                    <td class="col-xs-10" id="phone">
                        <%=user.getStPhone()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">虚拟网号：</td>
                    <td class="col-xs-10" id="virtual">
                        <%=user.getStFuctitiousPhone()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">邮箱：</td>
                    <td class="col-xs-10" id="email">
                        <%=user.getStEmail()%>
                    </td>
                </tr>
            </table>
            <table class="table" style="background-color: white;border:solid 1px #ccc;">
                <tr>
                    <td colspan="2"><b>辅助信息</b> &nbsp;&nbsp;<a data-toggle="modal" href="#" data-target="#editModal">修改辅助信息</a></td>
                </tr>
                <tr>
                    <td class="col-xs-2">执法证编号：</td>
                    <td class="col-xs-10" id="law_code">
                        <%=user.getStLawCode()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">办公地点：</td>
                    <td class="col-xs-10" id="location_info">

                    </td>
                </tr>
                <%--<tr>
                  <td class="col-xs-2">在职状态<span style="color: red;">*</span>:</td>
                  <td class="col-xs-10">
                    在职
                  </td>
                </tr>--%>

            </table>
        </div>
    </div>

</div>

<!-- 权限查看模态框（Modal） -->
<div class="modal fade" id="authorityModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:400px;">
        <div class="modal-content" style="width:100%;height:430px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" >
                    查看权限
                </h4>
            </div>
            <div class="modal-body" style="height:310px;overflow-y: auto;" id="qxmodaldiv">
                <ul id="addmlTree" class="ztree bluetree"></ul>
            </div>
        </div><!-- /.modal-content -->
    </div></div><!-- /.modal -->

<!-- 辅助信息修改模态框（Modal） -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px;height:280px;">
            <form id="userForm" method="post">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" >
                    修改辅助信息
                </h4>
            </div>
            <input type="hidden" id="userId" name="userId" value="<%=user.getStUserId()%>"/>
            <div class="modal-body" style="height:160px;background-color: rgb(241,242,246);">
                <div style="float: left;width:96%;height:100%;margin-left:15px;">
                    <table class="table" style="background-color: white;border:solid 1px #ccc;">
                        <tr>
                            <td class="col-xs-3">执法证编号：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" id="lawCode" name="lawCode" placeholder="请输入执法证编号"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">办公地点：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" id="location" name="location" placeholder="请输入办公地点的详细地址"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="send()">
                    保存
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
            </div>
            </form>
        </div><!-- /.modal-content -->
    </div></div><!-- /.modal -->
<script type="text/javascript">
    selectUser("<%=user.getStUserId()%>");
</script>
</body>
</html>
