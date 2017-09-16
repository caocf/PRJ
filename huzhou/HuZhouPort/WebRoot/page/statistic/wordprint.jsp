<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:w="urn:schemas-microsoft-com:office:word"
	xmlns="http://www.w3.org/TR/REC-html40">

	<head>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/statistic/word.css" />
		<script  type="text/javascript"  src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/statistic/print.js"></script>

		<meta http-equiv=Content-Type content="text/html; charset=gb2312">
		<meta name=ProgId content=Word.Document>
		<meta name=Generator content="Microsoft Word 11">
		<meta name=Originator content="Microsoft Word 11">
		<link rel=File-List href="请假加班统计表.files/filelist.xml">
		<title>个人考勤统计表</title>
		<o:SmartTagType
			namespaceuri="urn:schemas-microsoft-com:office:smarttags"
			name="chsdate" />
		

	</head>
	<body lang=ZH-CN style='tab-interval: 21.0pt; text-justify-trim: punctuation'>
		<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId" />
		<object id="WebBrowser" classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height="0" width="0"> </object> 
		<input id="btnPrint" type="button" value="打印" onclick="preview()" /> 
		
		<div class=Section1>
			<p class=MsoNormal align=center style='text-align: center; '>
				<span class="texttitle"><%=session.getAttribute("name")%>考勤统计表<span lang=EN-US><o:p></o:p> </span> </span>
			</p>

			<div align=center>

				<p class=MsoNormal>
					<span class="textcont">报表生成时间：</span>
					
						<span lang=EN-US class="texttime" id="today"></span>
					
					<span lang=EN-US class="texttime"><o:p></o:p>
					</span>
				</p>

				<p class=MsoNormal>
					<span
						class="textcont">统计时间段：</span>
					
						<span lang=EN-US class="texttime" id="beginTime"><%=request.getParameter("beginTime")%></span>
					
					<span
						class="textcont">至</span>
					
						<span lang=EN-US class="texttime" id="endTime"><%=request.getParameter("endTime")%></span>
					
					<span lang=EN-US class="texttime"><o:p></o:p>
					</span>
				</p>

				<p class=MsoNormal>
					<span
						class="textcont">统计对象：</span><span
						lang=EN-US class="texttime"><%=session.getAttribute("name")%><o:p></o:p>
					</span>
				</p>

				<p class=MsoNormal>
					<span
						class="textcont"><b>请假加班总时长：</b></span><span
						lang=EN-US class="texttime" id="sumcount"><o:p></o:p>
					</span>
				</p>
				<p class=MsoNormal>
					<span
						class="textcont"><b>请假加班总次数：</b></span><span
						lang=EN-US class="texttime" id="sumtimecount"><o:p></o:p>
					</span>
				</p>
				
				
				<p class=MsoNormal>
					<span
						class="textcont"><b>请假总时长：</b></span><span
						lang=EN-US class="texttime" id="qjcount">0<o:p></o:p>
					</span>
				</p>
				<div id="QJ_div">
				
				
				</div>
				<p class=MsoNormal>
					<span class="textcont"><b>加班总时长：</b></span><span
						lang=EN-US class="texttime" id="jbcount"><o:p></o:p>
					</span>
				</p>
				<div id="JB_div">	
				</div>
				
				<p class=MsoNormal>
					<span class="textcont"><b>出差总时长：</b></span><span
						lang=EN-US class="texttime" id="czcount"><o:p></o:p>
					</span>
				</p>
				<div id="CZ_div">
				</div>
				<p class=MsoNormal>
					<span class="textcont"><b>&nbsp;</b></span><span
						lang=EN-US class="texttime" id="czcount"><o:p></o:p>
					</span>
				</p>
				<p class=MsoNormal>
					<span class="textcont">本人签字：</span><span
						lang=EN-US class="texttime"><span
						style='mso-spacerun: yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</span>
					<o:p></o:p>
					</span>
				</p>

				<p class=MsoNormal>
					<span class="textcont">领导签字：</span><span
						lang=EN-US class="texttime"><o:p></o:p>
					</span>
				</p>

				<p class=MsoNormal>
					<span lang=EN-US class="texttime"><o:p>&nbsp;</o:p>
					</span>
				</p>

				<p class=MsoNormal>
					<span lang=EN-US class="texttime"><o:p>&nbsp;</o:p>
					</span>
				</p>

				<p class=MsoNormal align=center style='text-align: center'>
					<span lang=EN-US><o:p>&nbsp;</o:p>
					</span>
				</p>
</div>
			</div>
	</body>

</html>