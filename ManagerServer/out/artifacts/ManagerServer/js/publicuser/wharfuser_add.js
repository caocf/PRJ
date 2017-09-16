var ConditionsArray=new Array();
var selectConditions=0;
$(document).ready(function() {
	ShowWharfBookList();
	checkinput();

});
function checkinput() {
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

function wharfNameCheck() {
	var ret = true;
	ret = emptyChk("#wharfName", "#wharfNameerr", "绑定码头不能为空");
	if (ret == true) {
		ret = valueLength("#wharfName", "#wharfNameerr", "1", "20",
				"绑定船名不能大于20位");
	}
	if (ret == true) {
		ret = specialcharacter("#wharfName", "#wharfNameerr", "不能出现特殊字符");
	}
	return ret;
}
function wharfNumberCheck() {
	var ret = true;
	ret = emptyChk("#wharfNumber", "#wharfNumbererr", "码头编号不能为空");
	if (ret == true) {
		ret = valueLength("#wharfNumber", "#wharfNumbererr", "1", "50",
				"码头编号不能大于50位");
	}
	if (ret == true) {
		ret = specialcharacter("#wharfNumber", "#wharfNumbererr", "不能出现特殊字符");
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
}function validSubmit2() {

	if (!booklistCheck()) {
		return false;
	}
	return true;
}

function ShowWharfBookList(){
	$.ajax( {
		url : "WharfCheckList",
		type : "post",
		dataType : "json",
		success : function(data) {
		var newDiv=$(".wharfbooklist");
        for(var i=0;i<data.list.length;i++){
        	newDiv.append($("<div><input type='checkbox' name='booklist' onclick='booklistCheck()'/>"+data.list[i].wharfBook+"</div>"));
        }
		
	}
	});

}
function AddPublicByWharf(){
	if (!validSubmit()) {
		return;
	}
	if($("#usertable tr").size()<2){
		 $("#infoerr").text("请绑定码头");
		 return;
	}
	
var arr=$("#usertable tr td:first-child input");
            for(var i=0;i<arr.length;i++){
            	var value=$(arr[i]).val();
              if(value==""){
            	  $("#infoerr").text("绑定码头编号不能为空");
            	  return;
              } 
              if (/[~#^$@%&!*\s*]/.test(value)) {
            	$("#infoerr").text("输入的内容不能包含特殊字符！");
            	return false;
            	}
               if (!/^[\+\-]?\d*$/.test(value)) {
            	$("#infoerr").text("只能输入数字！");
            	return;
            	}
              if(value.length>20){
            	  $("#infoerr").text("绑定码头编号不能大于20位");
            	  return;
              }
                for(var j=i+1;j<arr.length;j++){
                if($(arr[j]).val()==value){
                	$("#infoerr").text("绑定码头编号有重复");
                	return;
                }
            }
            }
            
       var arr1=$("#usertable tr td:nth-child(2) input");
            for(var i=0;i<arr1.length;i++){
            	var value1=$(arr1[i]).val();
              if(value1==""){
            	  $("#infoerr").text("绑定码头名称不能为空");
            	  return;
              } 
              if (/[~#^$@%&!*\s*]/.test(value1)) {
            	$("#infoerr").text("输入的内容不能包含特殊字符！");
            	return;
            	}
              if(value1.length>20){
            	  $("#infoerr").text("绑定码头名称不能大于20位");
            	  return;
              }
                for(var j=i+1;j<arr1.length;j++){
                if($(arr1[j]).val()==value1){
                	$("#infoerr").text("绑定码头名称有重复");
                	return;
                }
            }
            }
            var a=true;
            $("#usertable tr").each(function(){          
            	 if($(this).find("td").eq(2).find("label").text()=='材料不足'){
            	  $("#infoerr").text("证据材料不足，请完善");
	              a=false;
            	 }
            	 });
            if(a){
            	$("#add_form").submit();
            }

}
function AddPublicByWharf1(){
	if (!validSubmit2()) {
		return false;
	}
	ConditionsArray[selectConditions]=1;
	$("#con"+selectConditions).text("满足条件");
	$("#cresteData").dialog("close");
}
function AddWharf(){
$("#cresteData").dialog("close");

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
		title : '添加证书',
		collapsible : true,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
	}
function closeShopDialog(){
	$("#cresteData").dialog("close");
}

function deleteUpload(no,j) {
	$("#uploadFile" + no+"_"+j).empty();
	$("#uploadFile" + no+"_"+j).remove();
	$("#showFileName_" + no+"_"+j).empty();
	$("#showFileName_" + no+"_"+j).remove();
	$("#fileName"+no+"_"+j).empty();
    $("#fileName"+no+"_"+j).remove();
}
function removeFile(no){
	$("#" + no).empty();
	$("#" + no).remove();
}

function addShipFIle(){
	var no=$("#usertable tr").length-1;
	if(no>0){	
		no=parseInt($('#usertable tr:last').attr("id").split("_")[1])+1;
	}
	var tr=$("<tr class='addTr' id='shopFile_"+no+"'></tr>");
	tr.append("<td ><input name='listw["+no+"].wharfNumber'size='10'></td>");
	tr.append("<td><input name='listw["+no+"].wharfNum' ></td>");
	var cl=ConditionsArray.length;
	ConditionsArray[cl]=0;
	tr.append("<td><label onclick='showShopDialog("+cl+")' id='con"+cl+"' class='con' >材料不足</label></td>");
	tr.append("<td id='file"+no+"'><div class='showFileName' id='showFileName"+no+"'></div></td>");
	tr.append($("<td><div class='file_class' id='file_class"+no+"'>" +
	"<img src='image/operation/add_attachments_click.png' class='file_img' title='添加附件' />" +"&nbsp;&nbsp;&nbsp;&nbsp;"+
	"<input type='file' onchange='appendFile("+no+",0)' class='file1_button' id='uploadFile"+no+"_0' name='listw["+no+"].listf[0].file'/>" +
	"<input type='hidden' id='fileName"+no+"_0' name='listw["+no+"].listf[0].fileName'></div>" +
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
	$("#file_class"+no).append("<input type='file' class='file1_button' id='uploadFile"+no+ "_"+j+"' name='listw["+no+"].listf["+j+"].file' onchange=appendFile('"+no+"','"+j+"') />" +
	"<input type='hidden' id='fileName"+no+"_"+j+"' name='listw["+no+"].listf["+j+"].fileName'>");
}