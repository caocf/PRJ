var ConditionsArray=new Array();
var selectConditions=0;
$(document).ready(function() {
	PublicInfoByShip();
	checkinput();
	publicUserById();
	ShowShipBookList()
});
function checkinput(){
	$("#publicUserName").focus(function() {
		var che = userNameCheck();
		$("#userNameerr").text("");
	});
	$("#publicUserName").blur(function() {
		userNameCheck();
	});
	$("#password").focus(function() {
		var che = passwordCheck();

		$("#passworderr").text("");
	});
	$("#password").blur(function() {
		passwordCheck();
	});
	$("#confirmPassword").focus(function() {
		var che = confirmPasswordCheck();

		$("#confirmPassworderr").text("");
	});
	$("#confirmPassword").blur(function() {
		confirmPasswordCheck();
	});
}
function userNameCheck() {
	var ret = true;
	ret = emptyChk("#publicUserName", "#userNameerr", "用户名不能为空");
	if (ret == true) {
		ret = valueLength("#publicUserName", "#userNameerr", "2", "18",
				"用户名不能小于2位大于18位");
	}
	if (ret == true) {
		ret = isCellphone("#publicUserName", "#userNameerr", "请输入手机号码");
	}
	return ret;
}
function passwordCheck() {
	var ret = true;
	ret = emptyChk("#password", "#passworderr", "密码不能为空.");
	if (ret == true) {
		ret = valueLength("#password", "#passworderr", "6", "20", "密码是6-20位");
	}
	if (ret == true) {
		ret = regularChk('#password', "#passworderr", "密码只能包含_ ,英文字母,数字.");
	}
	return ret;
}
function confirmPasswordCheck() {
	var ret = true;
	ret = emptyChk("#confirmPassword", "#confirmPassworderr", "确认密码不能为空");
	if (ret == true) {
		ret = chkConfirmPwd("#password", "#confirmPassword",
				"#confirmPassworderr", "两次密码不一样");
	}
	return ret;
}

function shipNameCheck() {
	var ret = true;
	ret = emptyChk("#shipName", "#shipNameerr", "绑定船名不能为空");
	if (ret == true) {
		ret = valueLength("#shipName", "#shipNameerr", "1", "20",
				"绑定船名不能大于20位");
	}
	if (ret == true) {
		ret =specialcharacter("#shipName", "#shipNameerr", "不能出现特殊字符");
	}
	return ret;
}
function booklistCheck() {
	var opts = document.getElementsByName("booklist");
	var sicon = 0;
	for (i = 0; i < opts.length; i++) {
		if (opts[i].checked == false) {
			sicon = 1;
		}
	}
	if (sicon == 1) {
		$("#totalcheckbox").attr("checked", "");
		AllCheckBox = 0;
		$("#booklisterr").text("需全部签证");
		return false;
	} else {
		$("#totalcheckbox").attr("checked", "checked");
		AllCheckBox = 1;
		$("#booklisterr").text("");
		return true;
	}
}
function ShowShipBookList(){
	$.ajax( {
		url : "ShipCheckList",
		type : "post",
		dataType : "json",
		success : function(data) {
		var newDiv=$(".shipbooklist");
        for(var i=0;i<data.list.length;i++){
        	newDiv.append($("<div><input type='checkbox' name='booklist' onclick='booklistCheck()'/>"+data.list[i].shipBook+"</div>"));
        }
		
	}
	});

}
var AllCheckBox=0;
function SelectAllBox(){
	if(AllCheckBox==0){
		$(":checkbox").attr("checked","checked");
		AllCheckBox=1;
	}
	else{
		$(":checkbox").attr("checked","");
		AllCheckBox=0;
	}
}
function validSubmit() {
	if (!userNameCheck()) {
		return false;
	}
	if (!passwordCheck()) {

		return false;
	}
	if (!confirmPasswordCheck()) {

		return false;
	}	

	return true;
}
function validSubmit1() {
	if (!booklistCheck()) {
		return false;
	}
	return true;
}
function PublicInfoByShip(){
	$.ajax( {
		url : "PublicInfoByShip",
		type : "post",
		dataType : "json",
		data:{
		'publicUser.userId':$("#publicId").val()
	},
		success : function(data) {
		$("#publicUserName").attr("value",data.list[0][0].userName);
		$("#userName1").text(data.list[0][0].userName);
		$("#password").attr("value",data.list[0][0].psd);
		$("#confirmPassword").attr("value",data.list[0][0].psd);
		$("#shipName").attr("value",data.list[0][1].shipNum);
	},
	error : function(XMLHttpRequest) {
		alert("获取信息失败，请刷新重试！");
	}
	});
}
function UpdatePublicByShip(){
	if (!validSubmit()) {
		return false;
	}
	if($("#usertable tr").size()<2){
		 $("#infoerr").text("请绑定船名号");
		 return false;
	}
	  var arr=$("#usertable tr td:first-child input");
            for(var i=0;i<arr.length;i++){
            	var value=$(arr[i]).val();
              if(value==""){
            	  $("#infoerr").text("绑定船名不能为空");
            	  return false;
              } 
            if (/[~#^$@%&!*\s*]/.test(value)) {
            	$("#infoerr").text("输入的内容不能包含特殊字符！");
            	return false;
            	}
              if(value.length>20){
            	  $("#infoerr").text("绑定船名不能大于20位");
            	  return false;
              }
                for(var j=i+1;j<arr.length;j++){
                	
                if($(arr[j]).val()==value){
                	$("#infoerr").text("绑定船名号有重复");
                	return false;
                }
            }
            }

	  var a=true;
            $("#usertable tr").each(function(){          
            	 if($(this).find("td").eq(1).find("label").text()=='材料不足'){
            	  $("#infoerr").text("证据材料不足，请完善");
	              a=false;
            	 }
            	 });
            if(a){
            	$("#updateAction").submit();
            }
}
function AddPublicByShip1(){
	if (!validSubmit1()) {
		return false;
	}
	ConditionsArray[selectConditions]=1;
	$("#con"+selectConditions).text("满足条件");
	$("#cresteData").dialog("close");
}
function AddShip(){
$("#cresteData").dialog("close");

}
function showShopDialog(num){
	if(num<ConditionsArray.length){
		if(ConditionsArray[num]==0){
			$(":checkbox").attr("checked","");
		}else{
			$(":checkbox").attr("checked","chcked");
		}
	}
	selectConditions=num;
	$("#cresteData").show();
	$("#cresteData").dialog( {
		title : '添加船舶',
		collapsible : true,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
	}
function closeShopDialog(){
	$("#cresteData").dialog("close");
}
function addShipFIle(){
	var no=$("#usertable tr").length-1;
	if(no>0){	
		no=parseInt($('#usertable tr:last').attr("id").split("_")[1])+1;
	}
	var tr=$("<tr class='addTr' id='shopFile_"+no+"'></tr>");
	tr.append("<td><input name='lists["+no+"].shipNum'></td>");
	var cl=ConditionsArray.length;
	ConditionsArray[cl]=0;
	tr.append("<td><label onclick='showShopDialog("+cl+")' id='con"+cl+"' class='con' >材料不足</label></td>");
	tr.append("<td id='file"+no+"'><div class='showFileName' id='showFileName"+no+"'></div></td>");
	tr.append($("<td><div class='file_class' id='file_class"+no+"'>" +
	"<img src='image/operation/add_attachments_click.png' class='file_img' title='添加附件' />" +"&nbsp;&nbsp;&nbsp;&nbsp;"+
	"<input type='file' onchange='appendFile("+no+",0)' class='file1_button' id='uploadFile"+no+"_0' name='lists["+no+"].listf[0].file'/>" +
	"<input type='hidden' id='fileName"+no+"_0' name='lists["+no+"].listf[0].fileName'></div>" +
						"<a class='operation' onclick=removeFile('shopFile_"+no+"')>" +
						"删除</a>"
						+ "</td>"));
	$("#usertable").append(tr);
}

function appendFile(no,j){
	var url = $("#uploadFile"+no+"_"+j).val();
	
	$("#uploadFile"+no+"_"+j).css("right","200px");
	if(url==""){
		return;
	}
	var pos = url.lastIndexOf("/");
	if (pos == -1) {
		pos = url.lastIndexOf("\\");
	}	
	var filename = url.substr(pos + 1);
	$("#fileName"+no+"_"+j).attr("value",filename);
	$("#showFileName"+no).append("<div id='showFileName_"+no+"_"+j+"'>"+filename+ "<a onclick='deleteUpload("+no+","+j+")' " + "class='operation'>删除</a></div>");
	j++;
	$("#file_class"+no).append("<input type='file' class='file1_button' id='uploadFile"+no+ "_"+j+"' name='lists["+no+"].listf["+j+"].file' onchange=appendFile('"+no+"','"+j+"') />" +
	"<input type='hidden' id='fileName"+no+"_"+j+"' name='lists["+no+"].listf["+j+"].fileName'>");
}
function deleteUpload(no,j,fileId) {
	$("#uploadFile" + no+"_"+j).empty();
	$("#uploadFile" + no+"_"+j).remove();
	$("#showFileName_" + no+"_"+j).empty();
	$("#showFileName_" + no+"_"+j).remove();
	$("#fileName"+no+"_"+j).empty();
    $("#fileName"+no+"_"+j).remove();
   
    if(fileId){
    	DeleteById("deleteShipFileById","",fileId)
    }
}
function removeFile(no,shipId){
	$("#" + no).empty();
	$("#" + no).remove();
	
	if(shipId){
		DeleteById("deleteShipBindingById",shipId,"");
	}
}
function DeleteById(ationName,shipId,fileId){
	$.ajax( {
		url : ationName,
		type : "post",
		dataType : "json",
		data:{
		'shipBinding.shipId':shipId,
		'shipFile.fileId':fileId
	},
		success : function(data) {
		append(data.list);
	},
	error : function(XMLHttpRequest) {
		alert("获取信息失败，请刷新重试！");
	}
	});

}
function publicUserById(){
	$.ajax( {
		url : "publicUserById",
		type : "post",
		dataType : "json",
		data:{
		'publicUser.userId':$("#publicId").val()
	},
		success : function(data) {
		append(data.list);
	},
	error : function(XMLHttpRequest) {
		alert("获取信息失败，请刷新重试！");
	}
	});

}

function append(list){
	for(var no=0;no<list.length;no++){
		var tr=$("<tr class='addTr' id='shopFile_"+no+"'></tr>");
		tr.append("<td><input type='hidden' name='lists["+no+"].shipId' value='"+list[no][4]+"'><input name='lists["+no+"].shipNum' value='"+list[no][0]+"'></td>");
		var cl=ConditionsArray.length;
		ConditionsArray[cl]=1;
		tr.append("<td><label onclick='showShopDialog("+cl+")' id='con"+cl+"' class='con' >满足条件</label></td>");
       var fileNameVar="";
       var fileIndex=0;
		if(list[no][2]!=null&&list[no][2]!=""){
        	var fileIdList=list[no][2].split(";");
		var fileNameList=list[no][1].split(";");	
		
		for(var j=0;j<fileIdList.length;j++){
			fileNameVar+="<div id='showFileName_"+no+"_"+j+"'><input type='hidden' name='lists["+no+"].listf["+j+"].fileId' value='"+fileIdList[j]+"'>"+fileNameList[j]+ "<a onclick='deleteUpload("+no+","+j+","+fileIdList[j]+")' " + "class='operation'>删除</a></div>";
		
		}
		var fileIndex=list[no][2].length;
        }
		tr.append("<td id='file"+no+"'><div class='showFileName' id='showFileName"+no+"'>"+fileNameVar+"</div></td>");
		tr.append($("<td><div class='file_class' id='file_class"+no+"'>" +
	"<img src='image/operation/add_attachments_click.png' class='file_img' title='添加附件' />" +"&nbsp;&nbsp;&nbsp;&nbsp;"+
	"<input type='file' onchange='appendFile("+no+","+fileIndex+")' class='file1_button' id='uploadFile"+no+"_"+fileIndex+"' name='lists["+no+"].listf["+fileIndex+"].file'/>" +
	"<input type='hidden' id='fileName"+no+"_"+fileIndex+"' name='lists["+no+"].listf["+fileIndex+"].fileName'></div>" +
						"<a class='operation' onclick=removeFile('shopFile_"+no+"','"+list[no][4]+"')>" +
						"删除</a></td>"));
		$("#usertable").append(tr);
	}
}