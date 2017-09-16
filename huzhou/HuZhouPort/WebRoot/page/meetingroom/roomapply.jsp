<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>会议室申请</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/common/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/meetingroom/roomapply.css" />
		<script src="js/common/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/common/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="js/meetingroom/roomapply.js"></script>

	</head>

	<body>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=session.getAttribute("depid")%>" id="depid" />
		<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId" />
		<div class="panel panel-default" style="background:#f7f7f7">
    		<div class="panel-body">
       			 会议室管理   / 新建申请
	    	</div>
		</div>
		<div name="form" style="float:left;width:55%">
			<form class="form-horizontal" role="form" target='id_iframe' id="appform">
			  <div class="form-group">
			   	<label for="inputEmail3" class="col-sm-1 control-label" style="font-weight:200;width:30%"><font style="color:red">*</font>会议室</label>
			   	 <div class="col-sm-10" style="width:50%">
				    <select class="form-control" id="rooms" onchange="onSelectChange()">				   
				    </select>
			    </div>
			  </div>
			  <div class="form-group">
			   	<label for="inputEmail3" class="col-sm-1 control-label" style="font-weight:200;width:30%"><font style="color:red">*</font>会议主题</label>
			    <div class="col-sm-10" style="width:50%">
			      <input type="text" class="form-control" id="topic" placeholder="请输入会议主题">
			    </div>
			  </div>
			  <div class="form-group">
			   	<label for="inputEmail3" class="col-sm-1 control-label" style="font-weight:200;width:30%"><font style="color:red">*</font>会议日期</label>
			    <div class="col-sm-10" style="width:50%">
			     	<input type="text" class="Wdate form-control" id="date"  readonly
			     	onFocus="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'%y-%M-%d',isShowWeek:true,onpicked:function() {$dp.$('d122_1').value=$dp.cal.getP('W','W');$dp.$('d122_2').value=$dp.cal.getP('W','WW');}})"/>
			    </div>
			  </div>
			  <div class="form-group">
			   	<label for="inputEmail3" class="col-sm-1 control-label" style="font-weight:200;width:30%"><font style="color:red">*</font>会议开始时间</label>
			    <div class="col-sm-10" style="width:50%">
			     	<input type="text" class="Wdate form-control" id="time1"  readonly
			     	onFocus="WdatePicker({'dateFmt':'HH:mm',isShowWeek:true,onpicked:function() {$dp.$('d122_1').value=$dp.cal.getP('W','W');$dp.$('d122_2').value=$dp.cal.getP('W','WW');}})"/>
			    </div>
			  </div>
			  <div class="form-group">
			   	<label for="inputEmail3" class="col-sm-1 control-label" style="font-weight:200;width:30%"><font style="color:red">*</font>会议结束时间</label>
			    <div class="col-sm-10" style="width:50%">
			     	<input type="text" class="Wdate form-control" id="time2" readonly
			     	onFocus="WdatePicker({'dateFmt':'HH:mm',isShowWeek:true,onpicked:function() {$dp.$('d122_1').value=$dp.cal.getP('W','W');$dp.$('d122_2').value=$dp.cal.getP('W','WW');}})"/>
			    </div>
			  </div>
			  <div class="form-group">
			   	<label for="inputEmail3" class="col-sm-1 control-label" style="font-weight:200;width:30%">联系电话</label>
			    <div class="col-sm-10" style="width:50%">
			      <input type="text" class="form-control" id="tel" placeholder="输入联系电话">
			    </div>
			  </div>
			  <br>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" class="btn btn-primary" onclick="commit()">提交</button>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			      <button type="submit" class="btn btn-default">取消</button>
			    </div>
			  </div>
			</form>
			<iframe id="id_iframe" name="id_iframe" style="display:none;"></iframe>
		</div>
		<div style="float:left;width:40%">
			<div name="calender" id="calendar" >			
			</div>			
			<div class="panel panel-default" style="margin-top:10px">
			    <div class="panel-body" id="showTime">
			        	会议室申请记录
			    </div>
			</div>
			<table class="table" id="record">
			 <thead><tr><th>序号</th><th>申请人</th><th>会议时间</th><th>状态</th></tr></thead>
			 <tbody id=data></tbody>						 
			</table>
		</div>
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		           
		            <div class="modal-body" id="tipbox"></div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
		            </div>
		        </div><!-- /.modal-content -->
		    </div><!-- /.modal -->
		</div>
	</body>
</html>
