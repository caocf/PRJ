var ListOfUser = new Array();//左侧选择人员列表Id
var RightUserList = new Array();//右侧侧选择人员列表Id
var Gloval_DID;//当前选中的部门id
function showDialog(){
	$("#cresteTree").show();
	$("#cresteTree").dialog( {
		title : '选择通知对象',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});

}
function creatTree(){
	$.ajax( {
		url : 'showDepartmentList',
		type : "post",
		dataType : "json",
		success : function(data) {
		CreatDepartment(data.list,-1);	
	}
	});
}
function CreatDepartment(list,parentId){
	newTree = $("#tree");
	for ( var i = 0; i < list.length; i++) {
	if(parentId==list[i].partOfDepartmentId){
		if(checkChile(list,list[i].departmentId)==0)
		{
			newTr = $("<li class='li" + list[i].departmentId + "'>" +
					"<img src='image/common/minus.png' style='cursor:default'/>"
					+ "<a onclick=showUserList(this,'" + list[i].departmentId+ "')>"
					+ list[i].departmentName + "</a></li>");
			if (list[i].partOfDepartmentId == -1) {
				newTree.append(newTr);
			} else {
				$('#opt' + list[i].partOfDepartmentId).append(newTr);
			}
		}
		else{
		newTr = $("<li class='li" + list[i].departmentId + "'>" +
				"<img src='image/common/plus.png' onclick='openNextDepartment("+list[i].departmentId+")' " +
						"id='img"+list[i].departmentId+"' />"
				+ "<a onclick=showUserList(this,'" + list[i].departmentId+ "')>"
				+ list[i].departmentName + "</a></li>");
		newTu = $("<ul id='opt" + list[i].departmentId + "' style='display:none'></ul>");

		$(newTr).append(newTu);
		if (list[i].partOfDepartmentId == -1) {
			newTree.append(newTr);
		} else {
			$('#opt' + list[i].partOfDepartmentId).append(newTr);

		}
		CreatDepartment(list,list[i].departmentId);
		}
		
	}
	}

}
//检查子部门
function checkChile(list,did){
	var cd=0;
	for ( var i = 0; i < list.length; i++) {
		if(list[i].partOfDepartmentId==did)
			cd+=1;		
	}	
	if(cd==0) return cd;
	else return 1;
	
}
//打开或关闭下级树
function openNextDepartment(id){
	eval("var   ul=opt"+id);   
    eval("var   img=img"+id);    
    ul.style.display=ul.style.display!="none"?"none":"block";   
    img.src=ul.style.display!="none"?"image/common/minus.png":"image/common/plus.png";   
}
function hidenDepartmentTree(){
	$("#cresteTree").dialog("close");
}
function changeUserOfDepartment(){
	 var linkStr="";
	 var UserNameLIst="";
	 for ( var j = 0; j < userList.length; j++) {
           	if(linkStr==""){          		
           		linkStr=userIdList[j];
           		UserNameLIst=userList[j];
           		
           	}else{
           		linkStr+=","+userIdList[j];
           		UserNameLIst+=","+userList[j];
           	}
               
           
		}
	if(linkStr==""){
		$("#listOfUserName").text("港航内部全部用户");
		
	}
	else{
		$("#listOfUserName").text(UserNameLIst);
	}
		$("#listOfUser").attr("value",linkStr);
		$("#cresteTree").dialog("close");
}

function FindByName(){
	Gloval_DID=-1;
	$("label").css("color", "");
	$.ajax( {
		url : 'FindByName',
		type : "post",
		dataType : "json",
		data : {
			'user.name' : $("#searchName").val()
		},
		success : function(data) {
			$(".dialog_alluser").empty();
			ListOfUser.splice(0,ListOfUser.length);
			if(data.list.length==0) alert("没有符合的数据");
			for ( var i = 0; i < data.list.length; i++) {
				ListOfUser[i] = new Array();
				var USERID=data.list[i].userId;
				var Already=0;
				for ( var j = 0; j < userIdList.length; j++) {
					if(userIdList[j]==USERID){
						Already=1;
						break;
					}
				}
				if (Already!=1){
					ListOfUser[i][0]=USERID;
					ListOfUser[i][1]=data.list[i].name;
					ListOfUser[i][2]=0;
					var newLi = $("<div><a onclick=chooseUser(this,'"+USERID+"','"+data.list[i].name+"') >"+
							"<img src='image/schedule/person_head.png' class='person_head'/><label  id='u"+ USERID+ "' >"
							+ data.list[i].name+ "</label></a></div>");
					var newUl = $(".dialog_alluser");
					$(newUl).append(newLi);
				}	
			}
		}
	});
}
function chooseUser(s,userId,name) {
for(var i=0;i<ListOfUser.length;i++){
		if(ListOfUser[i][0]==userId&ListOfUser[i][2]==0){
			s.parentNode.style.background="url(image/schedule/selecting_background.png)";
			ListOfUser[i][2]=1;
		}
		else if(ListOfUser[i][0]==userId&ListOfUser[i][2]==1){
			s.parentNode.style.background="#fff";
			ListOfUser[i][2]=0;
		}
	}
}
//全部添加
function AllAddToClick() {
	for ( var i = 0; i < ListOfUser.length; i++) {	
		//左侧页面移除选中
		$("#selectToRemove"+ListOfUser[i][0]).remove();
			if (ListOfUser[i][0]!=undefined) {
				userList[userList.length] = ListOfUser[i][1];
				userIdList[userIdList.length] = ListOfUser[i][0];
				
			}		
	}ListOfUser.splice(0,ListOfUser.length);
	$(".dialog_alluser div").css("background","#fff");
	SelectedUser();
}
//添加
function AddToClick(){
	var addNumber=0;
	for ( var i = ListOfUser.length-1; i >=0 ; i--) {
		if (ListOfUser[i][2] == 1) {
			//左侧页面移除选中
			$("#selectToRemove"+ListOfUser[i][0]).remove();

			userList[userList.length] = ListOfUser[i][1];
			userIdList[userIdList.length] = ListOfUser[i][0];
			ListOfUser.splice(i,1);
			addNumber++;
		
		}
	}
	if(addNumber==0)
		alert("没有要增加的对象！");
	else{
	$(".dialog_alluser div").css("background","#fff");
	SelectedUser();
	}
}
//移除
function MoveToClick(){
	var deleteNumber=0;
	for ( var i = 0; i < RightUserList.length; i++) {
		if (RightUserList[i][1] == 1) {
			
			for ( var j = 0; j < userIdList.length; j++) {				
				if (userIdList[j] == RightUserList[i][0]) {
					userList.splice(j,1);
					userIdList.splice(j,1);
					deleteNumber++;
				}
			}
			
		}
	}
	if(deleteNumber==0)
		alert("没有要移除的对象！");
	else{
	//$(".dialog_userList div").css("background","#fff");
		showUserList(null,Gloval_DID);
	SelectedUser();
	}

	
}
//全部移除
function AllMoveToClick(){
	if(userList.length==0){
		alert("没有要移除的对象！");
	}else{
		userList.splice(0,userList.length);
		userIdList.splice(0,userIdList.length);	
		showUserList(null,Gloval_DID);
		SelectedUser();
	}
}
function chooseDeleteUser(s,userId){
	
     var deleteicon=0;
	for(var i=0;i<RightUserList.length;i++){
			if(RightUserList[i][0]==userId&RightUserList[i][1]==0){
				s.parentNode.style.background="url(image/schedule/selecting_background.png)";
				RightUserList[i][1]=1;
				deleteicon=1;
			}
			else if(RightUserList[i][0]==userId&RightUserList[i][1]==1){
				s.parentNode.style.background="#fff";
				RightUserList[i][1]=0;
				deleteicon=1;
			}
		}
	if(deleteicon==0){
		s.parentNode.style.background="url(image/schedule/selecting_background.png)";
		var Rightlength=RightUserList.length;
		RightUserList[Rightlength]=new Array([3]);
		RightUserList[Rightlength][0]=userId;
		RightUserList[Rightlength][1]=1;
	}
	
}
function SelectedUser(){
	 $(".dialog_userList").empty();
	 RightUserList.splice(0,RightUserList.length);
	for(var j = 0; j < userIdList.length; j++){
		var newLi = $("<div><a onclick=chooseDeleteUser(this,'"+userIdList[j]+"') >"+
				"<img src='image/schedule/person_head.png' class='person_head'/><label>"
				+ userList[j]+ "</label></a></div>");
		var newUl = $(".dialog_userList");
		$(newUl).append(newLi);
	}
}
function showUserList(s, departmentID) {
	if(departmentID==-1){//搜索
		return;
	}
	Gloval_DID=departmentID;
	if(s!=null){
	$('#tree li a').css("color", "black");
	s.style.color = "#3f9cd7";
	}
	$.ajax( {
				url : 'UserListByDepartment',
				type : "post",
				dataType : "json",
				data : {
					'user.partOfDepartment' : departmentID
				},
				success : function(data) {
					$(".dialog_alluser").empty();
					ListOfUser.splice(0,ListOfUser.length);
					for ( var i = 0; i < data.list.length; i++) {
						ListOfUser[i] = new Array();
						var USERID=data.list[i].userId;
						var Already=0;
						for ( var j = 0; j < userIdList.length; j++) {
							if(userIdList[j]==USERID){
								Already=1;
								break;
							}
						}
						if (Already!=1) {
							ListOfUser[i][0]=USERID;
							ListOfUser[i][1]=data.list[i].name;
							ListOfUser[i][2]=0;
							var newLi = $("<div id=selectToRemove"+USERID+" ><a onclick=chooseUser(this,'"+USERID+"','"+data.list[i].name+"') >"+
									"<img src='image/schedule/person_head.png' class='person_head'/>" +
									"<label  id='u"+ USERID+ "' >"
									+ data.list[i].name+ "</label></a></div>");
							var newUl = $(".dialog_alluser");
							$(newUl).append(newLi);
						}
					}
				}
			});
}

var uploadNum=1;
function addComponent(file)
{ var url = file.value;	
	file.style.right="-100px";	
	  var pos = url.lastIndexOf("/");
	   if(pos == -1){
	     pos = url.lastIndexOf("\\")
	   }
	   var filename = url.substr(pos +1)
	$("#EvidenceUpload").append($("<tr id='upload"+uploadNum+"'><td>" +
			"<label>"+filename+"</label></td><td><a onclick='deleteUpload("+uploadNum+")' " +
					"class='operation2'>删除</a></td></tr>"));
	uploadNum++;
	$("#file_div").append($("<input type='file'  onchange='addComponent(this)' class='file_button'" +
			" id='file"+uploadNum+"' name='scheduleAttachment.af'/>"));
}
function deleteUpload(num){
	$("#upload"+num).empty();
	$("#upload"+num).remove();
	$("#file"+num).empty();
	$("#file"+num).remove();
	
}
