<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-驾校报名</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/training.css" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/menu.css" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/common/CRselectBox.css" />
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/carTraining.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/nav.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/jquery-1.9.1.min.js"></script>
	
  </head>
  <body>
	
	              <div class="xqCont1_top3_cont">报名须知
	                  <p>一、照片：一寸白底彩色证件照2张（注：照片需为本人正面像露出双耳；彩色照片背景必须为纯白色，冲印、数码均可；如考试时需戴眼镜，所交照片要戴眼镜；）。<br/>
                                                               二、证件：如报名人员是外地户口（以身份证签发地为准），须持本人身份证(户口本、市民卡等其他证件不能代替)、一年期有效暂住证或居住证的原件。有摩托车的必须办增驾，外地的需把驾驶本先转到杭州才可增驾。报名后需学校协办查询确认异地无驾照，方可入理论培训班。<br/>
                                                               三、军人学车：现役军人（含武警）申请考领地方驾驶证，须出示本人的军官证、士兵证团级以上单位政治部或政治处出具的证明。（证明信必须为A4纸，注明：杭州市车辆管理所、姓名、性别、出生年月日、证件号、详细现住址）。军人、公安或执法人员须着便装。<br/>
                                                               四、费用：可以先预交1500元，剩下的费用上车前交清。<br/>
                                                               五、体检：到校打印体检单后，由驾校老师带到河坊街体检。<br/>
                                                               六、理论：体检合格后，方可约理论考试时间（期间需上满5次理论培训课，模拟考试满85分以上）。</p><br/><p style="font-weight:bold;margin: -20px auto -10px auto">网上报名</p>
                           <div class="right_bm" style="font-weight: normal;margin: 12px auto auto 0px;font-size: 12px">您所填写的报名信息，将会作为学车身份确认信息，请务必如实填写。
         <div class="right_bm_bot" >
         <div class="clear"></div>
           <div class="input">姓名&nbsp;
           <input class="text" type="text"/></div>
           <div class="input" style="margin-left: 30px;width: 290px">报名驾校												
					<div class="CRselectBox" id="layout1_select01" style="margin: -29px auto auto 65px;height: 27px;">
							<input type="hidden" value="1"  class="abc" />
							<input type="hidden" value="浙江一" class="abc_CRtext" />
							<a class="CRselectValue" >浙江一</a>
								<ul class="CRselectBoxOptions" style="margin-top: -5px">
									<li class="CRselectBoxItem">
										<a  class="selected" rel="1">浙江一</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="2">浙江二</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="3">浙江三</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="4">浙江四</a>
									</li>											
								</ul>
					</div>
							
          </div>
          <div class="input" style="margin-top: 10px" >姓名
          <input type="radio" name="radiobutton" value="radiobutton" class="radiobutton" /><label for="radiobutton">男</label>
          <input type="radio" name="radiobutton" value="radiobutton" class="radiobutton" /><label for="radiobutton">女</label>
          </div>
          <div class="input" style="margin-left: 30px;width: 290px;margin-top: 10px;">课程选择												
					<div class="CRselectBox" id="layout1_select02" style="margin: -28px auto auto 65px;height: 27px;">
							<input type="hidden" value="1" class="abc" />
							<input type="hidden" value="浙江一" class="abc_CRtext" />
							<a class="CRselectValue" >浙江一</a>
								<ul class="CRselectBoxOptions" style="margin-top: -5px">
									<li class="CRselectBoxItem">
										<a  class="selected" rel="1">浙江一</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="2">浙江二</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="3">浙江三</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="4">浙江四</a>
									</li>											
								</ul>
					</div>
							
          </div>
          <div class="input" style="margin-top: 10px">电话&nbsp;
          <input class="text" type="text"/>
          </div>
           <div class="input" style="margin-left: 30px;width: 290px;margin-top: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;地区												
					<div class="CRselectBox" id="layout1_select03" style="margin: -28px auto auto 65px;height: 27px;">
							<input type="hidden" value="1" class="abc" />
							<input type="hidden" value="浙江一" class="abc_CRtext" />
							<a class="CRselectValue" >浙江一</a>
								<ul class="CRselectBoxOptions" style="margin-top: -5px">
									<li class="CRselectBoxItem">
										<a  class="selected" rel="1">浙江一</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="2">浙江二</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="3">浙江三</a>
									</li>
									<li class="CRselectBoxItem">
										<a  rel="4">浙江四</a>
									</li>											
								</ul>
					</div>
							
          </div>
          <div class="input" style="margin-top: 15px;width: 585px">地址&nbsp;
          <input class="text" type="text" style="width: 540px;"/>
          </div>
          <div class="input" style="width: 500px;margin-top: 8px;margin-left: 15px">
             <input type="radio" name="radiobutton" value="radiobutton" class="radiobutton" /><label for="radiobutton">当地户口</label>
             <input type="radio" name="radiobutton" value="radiobutton" class="radiobutton" /><label for="radiobutton">非当地有暂住证</label>
             <input type="radio" name="radiobutton" value="radiobutton" class="radiobutton" /><label for="radiobutton">非当地无暂住证</label>
          </div>
          <div><a class="select_buttom" style="margin: 10px auto auto 35px;"> 提交 </a></div>
         </div>
                                      </div>
	              </div>
	              