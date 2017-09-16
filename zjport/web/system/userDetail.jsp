<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/10/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.model.TUser" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>

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
    <script src="../js/system/userDetail.js"></script>
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
        .table tr{
            height:50px;
        }
        .table td{
            line-height: 34px!important;
        }
        #qxmodaldiv label{
            width: 100%;
        }
    </style>
</head>
<%
    TUser user = (TUser)request.getAttribute("user");
%>
<body>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        用户管理
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>

</section>
<div style="float: left;width:100%;height:700px;padding:0 10px;">
    <div class="modal-body" style="height:560px;background-color: rgb(241,242,246);">
        <div style="float: left;width:98%;height:100%;margin-left:15px;">
            <table class="table" style="background-color: white;margin-top:20px;border:solid 1px #ccc;">
                <tr>
                    <td colspan="2"><b>基本信息</b> &nbsp;&nbsp;<span style="color: #c9cccf;">这是同步过来的信息，在本系统无法修改</span></td>
                </tr>
                <tr>
                    <td class="col-xs-2">职员姓名：</td>
                    <td class="col-xs-10" id="user_name">
                        <%=user.getStUserName()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">职&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
                    <td class="col-xs-10" id="user_position">
                        <%=user.getStPositionId()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">所在单位：</td>
                    <td class="col-xs-10" id="user_org">
                        <%=user.getStOrgId()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">所属部门：</td>
                    <td class="col-xs-10" id="user_dept">
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
                    <td class="col-xs-10" id="user_office_phone">
                        <%=user.getStOfficePhone()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">手机号码：</td>
                    <td class="col-xs-10" id="user_phone">
                        <%=user.getStPhone()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">虚拟网号：</td>
                    <td class="col-xs-10" id="user_virtual">
                        <%=user.getStFuctitiousPhone()%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
                    <td class="col-xs-10" id="user_email">
                        <%=user.getStEmail()%>
                    </td>
                </tr>
            </table>
            <table class="table" style="background-color: white;border:solid 1px #ccc;">
                <tr>
                    <td colspan="2"><b>辅助信息</b> &nbsp;&nbsp;<a data-toggle="modal" data-target="#editModal" style="cursor: pointer;" onclick="showJs()">修改辅助信息</a></td>
                </tr>
                <tr>
                    <td class="col-xs-2">所属角色<span style="color: red;">*</span>：</td>
                    <td class="col-xs-10">
                        <span id="user_jsword"></span>
                        <input type="hidden" id="user_js"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <%--<a data-toggle="modal" data-target="#authorityModal" style="cursor: pointer;" onclick="selectQx()">查看权限</a>--%>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-2">执法证编号：</td>
                    <td class="col-xs-10" id="user_law">
                        <%=user.getStLawCode()%>
                    </td>
                </tr>
                <%--<tr>
                    <td class="col-xs-2">办公地点<span style="color: red;">*</span>：</td>
                    <td class="col-xs-10" id="user_address">

                    </td>
                </tr>--%>
                <tr>
                    <td class="col-xs-2">序&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
                    <td class="col-xs-10" id="user_order">
                        <%=user.getStOrder()%>
                    </td>
                </tr>
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
        <div class="modal-content" style="width:800px;height:300px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" >
                    修改辅助信息
                </h4>
            </div>
            <form id="userForm" action="userDetailSubmit" method="post">
            <div class="modal-body" style="height:180px;background-color: rgb(241,242,246);">
                <div style="float: left;width:96%;height:100%;margin-left:15px;">
                    <table class="table" style="background-color: white;border:solid 1px #ccc;">
                        <tr>
                            <td class="col-xs-3">执法证编号：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;"placeholder="请输入执法证编号" id="userLaw" name="userLaw"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">角色<span style="color: red;">*</span>：</td>
                            <td class="col-xs-9">
                                <select class="from-control" name="userJs" id="userJs" >

                                </select>
                            </td>
                        </tr>
                        <%--<tr>
                            <td class="col-xs-3">办公地点：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;"placeholder="请输入办公地点" id="userAddress" name="userAddress"/>
                            </td>
                        </tr>--%>
                        <tr>
                            <td class="col-xs-3">序号：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;"placeholder="请输入序号" id="userOrder" name="userOrder"/>
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
            <input type="hidden" name="userId" id="userId" value="<%=user.getStUserId()%>"/>
            </form>
        </div><!-- /.modal-content -->
    </div></div><!-- /.modal -->
<script type="text/javascript">
    selectUser("<%=user.getStUserId()%>");
</script>
</body>
</html>
