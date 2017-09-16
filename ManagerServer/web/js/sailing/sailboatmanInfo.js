var certificateName="";
var cardNameID="";
$(document).ready(function(){
	showBoatManInfo();
});
function showBoatManInfo(){
	$.ajax({
		url:'ShowBoatUserInfo',
		type:'post',
		dataType:'json',
		data:{
			'boatman.boatmanShip':$("#shipName").val()
		},
		success:function(data){//alert(JSON.stringify(data.list[0][0]))
			var list=data.list;
			appendToTable(list);
		}
	});
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		var newTr=$("<tr class='addTr' id='removeInfo"+list[i][0].boatmanID+"'></tr>");
		/*newTr.append("<td>"+list[i][1].boatmanKindName+":"+"<input type='text' ></br>" +
				""+list[i][2].cardName+":"+"<input type='text' ></td>");*/
		newTr.append("<td ><label  id='boatmanKindName"+list[i][0].boatmanID+"'>"+list[i][1].boatmanKindName+"</label>" +
				"<input type='hidden' name='boatmanKindName' id='boatmanKindNameID"+list[i][0].boatmanID+"' value='"+list[i][0].boatmanKind+"'></br>" +
				"<label  id='cardName"+list[i][0].boatmanID+"'>"+list[i][2].cardName+"</label>" +
						"<input name='cardName' type='hidden' id='cardNameID"+list[i][0].boatmanID+"' value='"+list[i][2].cardID+"'>&nbsp;" +
								"<button class='buttonStyle' onclick='selectCertificate("+list[i][0].boatmanID+")'>选择</button></td>")
		newTr.append("<td ><input name='input_name' class='modify_input' id='boatmanName"+list[i][0].boatmanID+"' value='"+list[i][0].boatmanName+"'></br>" +
				"<input name='input_id' class='modify_input' id='boatmanCard"+list[i][0].boatmanID+"' value='"+list[i][0].boatCardNum+"'></td>")		
		newTr.append("<td ><a class='Operate' onclick='deleteBoatManInfo("+list[i][0].boatmanID+")'>删除</a></td>");
		$("#boatmanTable").append(newTr);
	}
}
//<button class='buttonStyle' onclick='selectCertificate("+list[i][0].boatmanID+")'>选择</button>
//选择证书类型
var CFID="";
function selectCertificate(id){
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	CFID=id;
	showCertificateInfo();
	$("#selectCFName").show();
	//$("#selectaddCFName").hide();
	$("#certificateDiv").show();
}
function showCertificateInfo(){
	$.ajax({
		url:'ShowBoatcardList',
		type:'post',
		dataType:'json',
		success:function(data){
			$("#certificateUl").empty();
			var list=data.list;
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addLi' id='cfName"+list[i].cardID+"' onclick=setCertificateName(this,'"+list[i].cardName+"','"+list[i].cardID+"')>"+list[i].cardName+"</li>");
				$("#certificateUl").append(newLi);
			}
		}
	});
}
function showAddCertificateInfo(){
	$.ajax({
		url:'ShowBoatcardList',
		type:'post',
		dataType:'json',
		success:function(data){
			/*$(".addLi").empty();
			$(".addLi").remove();
			var list=data.list;
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addLi' id='cfName"+list[i].cardID+"' onclick=setCertificateName(this,'"+list[i].cardName+"','"+list[i].cardID+"')>"+list[i].cardName+"</li>");
				$("#certificateUl").append(newLi);
			}*/
			var list=data.list;
			for(var i=0;i<list.length;i++){
				document.getElementById("certificateID").options.add(new Option(list[i].cardName,list[i].cardID));
			}
		}
	});
}
function showBoatmanName(){
	$.ajax({
		url:'ShowBoatmanKindList',
		type:'post',
		dataType:'json',
		success:function(data){
			var list=data.list;
			for(var i=0;i<list.length;i++){
				document.getElementById("boatmanKind").options.add(new Option(list[i].boatmanKindName,list[i].boatmanKindID));
			}
		}
	});
}
function setCertificateName(thisop,cardName,cardID){
	$(".addLiselected").attr("class","addLi");
	$("#cfName"+cardID).attr("class","addLiselected");
	certificateName=cardName;
	cardNameID=cardID;
}
function selectCFNamecl(){
	$("#cardName"+CFID).text(certificateName);
	$("#cardNameID"+CFID).val(cardNameID);
	closecertificateDiv();
}
function closecertificateDiv(){
	$("#certificateDiv,#fullbg").hide();
}
function closeboatmanInfoDiv(){
	$("#boatmanInfoDiv,#fullbg").hide();
}

//新增船员信息
function addBoaManInfo(){
	$("#input_boatmanKind").val("");
	$("#input_certificateID").val("");
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		});
	$("#boatmanInfoTitle").text("新增船员");
	showBoatmanName();
	showAddCertificateInfo();
	$("#boatmanInfoDiv").show();
}
var addID=0
function addboatmanInfo(){
	var positionName=$("#boatmanKind option:selected").text();
	var positionNameID=$("#boatmanKind option:selected").val();
	var positionBoatmanName=$("#input_boatmanKind").val();
	var CFName=$("#certificateID option:selected").text();
	var CFNameID=$("#certificateID option:selected").val();
	var CFNameNum=$("#input_certificateID").val();
	if(CFNameNum.length>20){
		alert("证书长度不能大于20位！");
		return;
	}
	if(positionBoatmanName.length>20){
		alert("船员名字不能大于20位！");
		return;
	}
	var newTr=$("<tr class='addTr' id='addremoveInfo"+addID+"'></tr>");
	/*newTr.append("<td>"+list[i][1].boatmanKindName+":"+"<input type='text' ></br>" +
			""+list[i][2].cardName+":"+"<input type='text' ></td>");*/
	newTr.append("<td ><label  id='addboatmanKindName"+addID+"'>"+positionName+"</label>" +
			"<input type='hidden' name='boatmanKindName' id='addboatmanKindNameID"+addID+"' value='"+positionNameID+"'></br>" +
			"<label  id='addcardName"+addID+"'>"+CFName+"</label>" +
					"<input name='cardName' type='hidden' id='addcardNameID"+addID+"' value='"+CFNameID+"'>&nbsp;" +
							"<button class='buttonStyle' onclick='addselectCertificate("+addID+")'>选择</button></td>")
	newTr.append("<td ><input name='input_name' class='modify_input' id='addboatmanName"+addID+"' value='"+positionBoatmanName+"'></br>" +
			"<input name='input_id' class='modify_input' id='addboatmanCard"+addID+"' value='"+CFNameNum+"'></td>")		
	newTr.append("<td ><a class='Operate' onclick='adddeleteBoatManInfo("+addID+")'>删除</a></td>");
	addID++;
	$("#boatmanTable").append(newTr);
	closeboatmanInfoDiv();
	
}
//新增的船员证书信息修改
var addCFID="";
function addselectCertificate(id){
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	addCFID=id;
	showaddCertificateInfo();
	//$("#selectCFName").hide();
	$("#selectaddCFName").show();
	$("#certificateDiv").show();
}
function showaddCertificateInfo(){
	$.ajax({
		url:'ShowBoatcardList',
		type:'post',
		dataType:'json',
		success:function(data){
			$("#certificateUl").empty();
			var list=data.list;
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='addLi' id='cfName"+list[i].cardID+"' onclick=setaddCertificateName(this,'"+list[i].cardName+"','"+list[i].cardID+"')>"+list[i].cardName+"</li>");
				$("#certificateUl").append(newLi);
			}
		}
	});
}
function setaddCertificateName(thisop,cardName,cardID){
	$(".addLiselected").attr("class","addLi");
	$("#cfName"+cardID).attr("class","addLiselected");
	certificateName=cardName;
	cardNameID=cardID;
}
function selectaddCFNamecl(){
	$("#addcardName"+addCFID).text(certificateName);
	$("#addcardNameID"+addCFID).val(cardNameID);
	closecertificateDiv();
}
function adddeleteBoatManInfo(addboatmanID){
	if(confirm("你确定要删除该船员吗？")){
		$("#addremoveInfo"+addboatmanID).remove();
	}
}
function gotoBack(){
	window.history.go("-1");
}
function deleteBoatManInfo(boatmanID){
	if(confirm("你确定要删除该船员吗？")){
		$("#removeInfo"+boatmanID).remove();
	}
}
//确定修改
function determinemanInfo(){
	var boatmanname=document.getElementsByName("input_name");
	for(var i=0;i<boatmanname.length;i++){
		if(boatmanname[i].value.length>20){
			alert("船员名字不能大于20位！");
			return;
		}
		if(boatmanname[i].value.indexOf(",")>=0){
			alert("船员名字不能包含逗号！");
			return;
		}
		
	}
	var certificateID=document.getElementsByName("input_id");
	for(var i=0;i<certificateID.length;i++){
		if(certificateID[i].value.length>20){
			alert("证书长度不能大于20位！");
			return;
		}
		if(certificateID[i].value.indexOf(",")>=0){
			alert("证书不能包含逗号！");
			return;
		}
		
	}
	var Linkboatmanname="";
	for(var i=0;i<boatmanname.length;i++){
		if(Linkboatmanname==""){
			Linkboatmanname=boatmanname[i].value;
		}else{
			Linkboatmanname+=","+boatmanname[i].value;
		}
	}
	
	var LinkcertificateNum="";
	for(var i=0;i<certificateID.length;i++){
		if(LinkcertificateNum==""){
			LinkcertificateNum=certificateID[i].value;
		}else{
			LinkcertificateNum+=","+certificateID[i].value;
		}
	}
	var certificateNameID=document.getElementsByName("cardName");
	var LinkcertificateNameID="";
	for(var i=0;i<certificateNameID.length;i++){
		if(LinkcertificateNameID==""){
			LinkcertificateNameID=certificateNameID[i].value;
		}else{
			LinkcertificateNameID+=","+certificateNameID[i].value;
		}
	}
	var boatmanKindID=document.getElementsByName("boatmanKindName");
	var LinkboatmanKindID="";
	for(var i=0;i<boatmanKindID.length;i++){
		if(LinkboatmanKindID==""){
			LinkboatmanKindID=boatmanKindID[i].value;
		}else{
			LinkboatmanKindID+=","+boatmanKindID[i].value;
		}
	}
	$.ajax({
		url:'SetBoatUserInfo',
		type:'post',
		dataType:'json',
		data:{
		'boatman.boatmanName':Linkboatmanname,
		'boatman.boatmanShip':$("#shipName").val(),
		'boatman.boatCardNum':LinkcertificateNum,
		'boatman.kindList':LinkboatmanKindID,
		'boatman.boatCardID':LinkcertificateNameID
		},
		success:function(data){
			alert("设置成功！");
			window.history.go("-1");
		},
		error:function(XMLHttpRequest){
			alert("设置错误！");
		}
	});
	
}