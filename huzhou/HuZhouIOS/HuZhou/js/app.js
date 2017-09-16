/**
 * 演示程序当前的 “注册/登录” 等操作，是基于 “本地存储” 完成的
 * 当您要参考这个演示程序进行相关 app 的开发时，
 * 请注意将相关方法调整成 “基于服务端Service” 的实现。
 **/
(function($, owner) {
	owner.serverURL = "http://120.55.100.184:90/HuZhouPort/";
//	owner.serverURL = "http://192.168.1.105:18080/HuZhouPort/";
	/**
	 * 用户登录
	 **/
	owner.login = function(loginInfo, callback) {
		callback = callback || $.noop;
		loginInfo = loginInfo || {};
		loginInfo.account = loginInfo.account || ''; //朱维平
		loginInfo.password = loginInfo.password || ''; //111111

		var authed = false;
		$.ajax({
			type: "post",
			url: owner.serverURL + "login_mobi",
			async: true,
			data: {
				"user.userName": loginInfo.account,
				"user.password": loginInfo.password
			},
			dateType: "json",
			success: function(data) {
				if(data.loginRes == 1) {
					authed = true;
//					console.log(JSON.stringify(data.model));
					var state = owner.getState() || {};
					state.user = data.model;
					state.loginInfo = loginInfo;
					owner.setState(state);
					localStorage.setItem("isSign", true);
//					console.log(authed);
					return callback();
				} else {
					return callback('用户名或密码错误');
				}
			},
			error: function(xOption, status) {
				console.error(xOption);
				console.error(status);
				return callback("网络异常");
			}
		});
	};

	/**
	 * 查询港航动态 
	 */
	owner.queryPortDynmicNews = function(cpage,callback,type){
		$.ajax({
			type: "post",
			url: owner.serverURL + "captureHtml",
			async: true,
			data: {
				"cpage":cpage
			},
			dateType: "json",
			success: function(data) {
				return callback(data,type);
			},
			error: function(xOption, status) {
				console.error(xOption);
				console.error(status);
				var data = {};
				data.errorInfo = "网络异常" 
				return callback(data,type);
			}
		});
	};
	/**
	 * 查询港航动态详情
	 * @param {Object} url
	 * @param {Object} calback
	 */
	owner.queryPortDynmicNewsDetail = function(url,calback){
		$.ajax({
			type: "post",
			url: owner.serverURL + "findDetailInfo",
			async: true,
			data: {
				"url":url
			},
			dateType: "json",
			success: function(data) {
				return callback(data);
			},
			error: function(xOption, status) {
				console.error(xOption);
				console.error(status);
				var data = {};
				data.errorInfo = "网络异常" 
				return callback(data);
			}
		});
		
	}
	
	/**
	 * 根据日期查询日程安排
	 * @param {Object} date
	 * @param {Object} callback
	 */
	owner.showEventListByTime = function(date,callback){
		var state = owner.getState();
		var userId = state.user.userId || 219;
		var month = date.month > 10 ? date.month +"":"0"+date.month;
		var day = date.day > 10 ? date.day +"":"0"+date.day;
		$.ajax({
			type:"post",
			url:owner.serverURL + "EventListByUserIdAndTime",
			async:true,
			dataType:"json",
			data:{
				"user.userId":userId,
				"scheduleEvent.eventTime":date.year+"-"+month+"-"+day
			},
			success:function(data){
				var list = data.list;
				for(var i = 0;i<list.length;i++){
					var eventId = list[i][0].eventId;
					var name = owner.findFirstUser(eventId);
					list[i].push({"name":name});
				}
				callback(data);
			},
			error: function(xOption, status) {
				console.error(JSON.stringify(xOption));
				console.error(status);
				var data = {};
				data.errorInfo = "网络异常" 
				callback(data);
			}
		});	
	};
	/**
	 * 
	 * @param {Object} data
	 */
	owner.findFirstUser = function(data,callback){
		var result = "";
		$.ajax({
			type:"post",
			url:owner.serverURL + "FindFirstUser",
			async:false,
			data:{
				"scheduleEvent.eventId":data
			},
			success:function(data){
				if(data.list != null && data.list.length > 0){
					result = data.list[0];
				}
			}
		});
		return result;
	}
	/**
	 * 
	 * @param {Object} eventId
	 */
	owner.eventListByEventId = function(eventId){
		var result = [];
		$.ajax({
			type:"post",
			url:owner.serverURL + "EventListByEventId",
			async:false,
			data:{
				"scheduleEvent.eventId":eventId
			},
			success:function(data){
				var list = data.list;
				for(var i = 0;i<list.length;i++){
					var name = list[i][2].name;
					result.push(name);
				}
			}
		});
		return result;
	}
	
	/**
	 * 
	 * @param {Object} data
	 * @param {Object} callback
	 */
	owner.updatePushMsgStatusByInfor = function(data,callback){
		$.ajax({
			type:"post",
			url:owner.serverURL + "UpdatePushmsgstatusByInfor",
			async:true,
			data:{
				"pushMsg.messageid":data.messageId,
				"pushMsg.userid":data.userId
			},
			success:function(data){
				owner.log(data);
				$.toast("提交成功");
			},
			error:function(xOption){
				owner.optionError(xOption);
			}
		});
	}
	/**
	 * 查询行业资讯
	 * @param {Object} page
	 * @param {Object} callback
	 */
	owner.findIndustryInfoList = function(page,callback,type){
		$.ajax({
			type:"post",
			url:owner.serverURL+"findIndustryInfoList",
			async:true,
			data:{
				page:page,
				rows:10
			},
			success:function(data){
//			localStorage.removeItem("noRead");
				var ids = [];
				var list = data.list;
				for(var i = 0;i<list.length;i++){
					ids.push(list[i].id);
				}
				localStorage.industryIds = JSON.stringify(ids);
				callback(data,type);
			},
			error:function(xOption,status){
				var data = {"msg":"网络错误"};
				callback(data,type);
			}
		});
	}
	
	/**
	 * 行业资讯详情
	 * @param {Object} id
	 * @param {Object} callback
	 */
	owner.findIndustryInfoDetail = function(id,callback){
		$.ajax({
			type:"post",
			url:owner.serverURL+"findIndustryInfoDetail",
			async:true,
			data:{
				id:id
			},
			success:function(data){
				callback(data);
			},
			error:function(xOption,status){
				var data = {"msg":"网络错误"};
				callback(data);
			}
		});
		
	}
	
	owner.addReaded = function (list){
		var noReadList = [];
		if(localStorage.noRead != null){
			noReadList = JSON.parse(localStorage.noRead);
		}
		owner.log(noReadList);
		var newList = noReadList;
		for(var i = 0;i<list.length;i++){
			if(!owner.contains(noReadList,list[i])){
				newList.push(list[i]);
			}
		}
		localStorage.noRead = JSON.stringify(newList);
		owner.log(localStorage.noRead);
	};
	/**
	 * 判断obj 是否在list内
	 * @param {Object} list
	 * @param {Object} obj
	 */
	owner.contains = function(list,obj){
		for(var i = 0;i<list.length;i++){
			if(list[i] == obj){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查询事件类型
	 * @param {Object} callback
	 */
	owner.queryEventKindList = function(callback){
		$.ajax({
			type:"post",
			url:owner.serverURL + "EventKindList",
			async:true,
			dataType:"json",
			success:function(data){
				var dataList = [];
				for(var i = 0;i<data.list.length;i++){
					var option = {"value":data.list[i].scheduleKindId,"text":data.list[i].scheduleKindName};
					dataList.push(option);
				}
				callback(dataList);
			},
			error:function(xOption,status){
				console.error(xOption);
				console.error(status);
				callback([]);
			}
		});
	}
	/**
	 * 获取部门列表
	 * @param {Object} callback
	 */
	owner.showDepartmentList = function(callback){
		$.ajax({
			type:"post",
			url:owner.serverURL+"showDepartmentList",
			async:true,
			success:function(data){
				if(data.list.length >0 ){
					callback(data.list,-1);
				}else{
					$.toast("未找到");
				}
			},
			error:function(xOption,status){
				console.error(xOption);
				console.error(status);
				$.toast("网络异常");
			}
		});
	}
	/**
	 * 更具部门查询用户列表
	 * @param {Object} departmentId
	 * @param {Object} callback
	 */
	owner.showUserList = function(departmentId,callback){
		$.ajax({
			type:"post",
			url:owner.serverURL+"UserListByDepartment",
			async:true,
			data:{
				"user.partOfDepartment":departmentId
			},
			success:function(data){
				callback(data);
			}
		});
	}
	/**
	 * 添加日程
	 * @param {Object} scheduleEvent
	 * @param {Object} callback
	 */
	owner.addEvent = function(scheduleEvent,callback){
		scheduleEvent = scheduleEvent || {};
		owner.log(scheduleEvent);
		callback = callback || $.noop;
		var state = owner.getState();
		var userId = state.user.userId;
		$.ajax({
			type:"post",
			url:owner.serverURL+"addEvent_mobile",
			async:true,
			dataType:"json",
			data:{
				"scheduleEvent.eventName":scheduleEvent.eName,
				"scheduleEvent.eventKind":scheduleEvent.kind,
				"scheduleEvent.eventTime":scheduleEvent.dateTime + ":00",
				"scheduleEventUser.eventRemind":scheduleEvent.time,
				"scheduleEventUser.eventRemindType":scheduleEvent.remindType,
				"scheduleEvent.eventLocation":scheduleEvent.address,
				"scheduleEventUser.eventUserList":userId+","+scheduleEvent.uList,
				"scheduleEvent.eventContent":scheduleEvent.content,
				"scheduleEventUser.userId":userId
				
			},
			success:function(data){
				callback(1);
			},
			error:function(data){
				callback(0);
			}
		});
	}
	/**
	 * 根据状态 potName分页查询
	 * @param {Object} page 查询页
	 * @param {Object} rows 行
	 * @param {Object} callback 回调函数
	 * @param {Object} type 刷新1 还是加载 0
	 * @param {Object} status 状态 1已处理 0 未处理
	 */
	owner.abNormalListByStatusAndPot = function(page,rows,callback,type,status){
		page = page || 1;
		rows = rows || 10;
		var potname = localStorage.pot || "";
		if(potname == ""){
			$.toast("请选择执勤点");
			return;
		}
		$.ajax({
			type:"post",
			url:owner.serverURL + "AbNormalListByStatusAndPot",
			async:true,
			data:{
				potname:potname,
				state:status,
				totalPage:page,
				allTotal:rows
			},
			success:function(data){
//				owner.log(data);
				callback(data,type);
			},
			error:function(xOption,status){
				console.error(xOption);
				console.error(status);
			}
			
		});
	}
	/**
	 * 提交巡航
	 * @param {Object} formData 表单数据
	 * @param {Object} callback 回调函数
	 */
	owner.submitAbnormalProcess = function(formData,callback){
//		owner.log(formData);
		$.ajax({
			type:"post",
			url:owner.serverURL + "SubmitAbNormalProcess",
			async:false,
			cache:false,
			contentType:false,
			processData:false,
			data:formData,
			success:function(data){
				callback(data);
			},
			error:function(xOption,status){
				console.error(xOption.status + " " + xOption.statusText + "\n"+ xOption.responseURL);
				console.error(status);
			}
		})
	}
	/**
	 * 查询通知公告
	 * @param {Object} callback
	 */
	owner.queryNoticeKindList = function(callback){
		callback = callback || $.noop();
		$.ajax({
			type:"post",
			url:owner.serverURL + "showKnowLedge",
			async:true,
			success:function(data){
				var list = data.pagemodel.recordsDate;
				callback(list);
			},
			error:function(xOption){
				console.error(xOption.status + " " + xOption.statusText + "\n"+ xOption.responseURL);
			}
		});
	}
	/**
	 * 
	 * @param {Object} callback
	 * @param {Object} page
	 * @param {Object} type
	 */
	owner.queryNoticeList = function(callback,page,type){
		var param = localStorage.noticeKind;
		var data = JSON.parse(param);
//		owner.log(data);
		var state = owner.getState();
		var userId = state.user.userId;
		$.ajax({
			type:"post",
			url:owner.serverURL+"showKnowLedgedian",
			async:true,
			data:{
				kindID:data.kindID,
				userid:userId,
				page:page
			},
			success:function(data){
//				owner.log(data);
				callback(data.pagemodel,type);
			},
			error:function(xOption){
				console.error(xOption.status + " " + xOption.statusText + "\n"+ xOption.responseURL);
			}
		});
	}
	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem('$state') || "{}";
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem('$state', JSON.stringify(state));
		//var settings = owner.getSettings();
		//settings.gestures = '';
		//owner.setSettings(settings);
	};

	/**
	 * 获取应用本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('$settings', JSON.stringify(settings));
	}

	/**
	 * 设置应用本地配置
	 **/
	owner.getSettings = function() {
		var settingsText = localStorage.getItem('$settings') || "{}";
		return JSON.parse(settingsText);
	}
	owner.log = function(obj){
		console.log("app.js:"+JSON.stringify(obj));
	}
	owner.optionError = function(xOption){
		console.error(xOption.status + " " + xOption.statusText + "\n"+ xOption.responseURL);
	}

}(mui, window.app = {}));