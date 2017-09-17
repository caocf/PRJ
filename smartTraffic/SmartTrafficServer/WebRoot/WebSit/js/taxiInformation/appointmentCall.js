$$(document).ready(function() {
	SelectOption("tixing",100);
	LogAndExitRefresh=true;//��¼�˳�ʱ�Ƿ�ˢ��ҳ��
	if ($$("#LoginUser").val() == "null") {
		LoginWeb();
	} else {
		GetUserInfoForWeb();//���ظ�����Ϣ
		queryOrder();
	}
});
function GetUserInfoForWeb() {
	$$.ajax( {
		url : 'GetUserInfoForWeb',
		type : 'post',
		dataType : 'json',
		success : function(data) {

			$$("#phone").val(DateIsNull(data.user.phone, ""));

		},
		error : function(a, b, c) {
			alert("��ȡ�绰����ʧ��");
		}
	});
}
//getMaxDate���ɿͻ��˱���ʱ��
function getMaxDate(){
	var t = new Date();
	var maxDate = [t.getFullYear(), t.getMonth()+1, t.getDate()+TIMELIMIT-1].join('-');
	return maxDate; 
}
function queryOrder(){
	
	$$.ajax( {
			url : 'queryOrder',
			type : "post",
			dataType : "json",
			success : function(data) {
				$$(".listTable .addTr").empty();
				$$(".listTable .addTr").remove();
				if(data.taxiOrders!=null){
					$$(".loadingData").hide();
					var list = data.taxiOrders;
					for ( var i = 0; i < list.length; i++) {
						var newTr=$$("<tr class='addTr'></tr>");
						newTr.append($$("<td>&nbsp;</td>"));
						newTr.append($$("<td>"+list[i].start+"</td>"));
						newTr.append($$("<td>"+list[i].end+"</td>"));
						newTr.append($$("<td>"+HourTimeFormat(list[i].date)+"</td>"));
						newTr.append($$("<td>"+list[i].phone+"</td>"));
						if(list[i].alert==1)
							newTr.append($$("<td>��</td>"));
						else
							newTr.append($$("<td>��</td>"));
						newTr.append($$("<td>"+DateIsNull(list[i].status,"��")+"</td>"));
						$$(".listTable").append(newTr);
					}
					if(list.length==0) $$(".loadingData").show();
				}else{
					$$(".loadingData").show();
				}
			}
		});



}
function ChangeText(tishitext){
	var startCity=$$("#startCity").val();
	$$("#startCity").val($$("#endCity").val());
	$$("#endCity").val(startCity);
}
function orderTaxi(){

	if(!validSubmit()){
		return;
	}
	$$.ajax( {
			url : 'orderTaxi',
			type : "post",
			dataType : "json",
			data:{
				'start':$$("#startCity").val(),
				'end':$$("#endCity").val(),
				'date':$$("#Time").val(),
				'alert':$$("#tixing .abc").val(),
				'phone':$$("#phone").val()
			},
			success : function(data) {
				if(data.result){
					alert("�ύ�ɹ�");
					window.location.reload();
				}else{
					alert("�ύʧ��");	
				}
			},error : function(a, b, c) {
				alert("�ύʧ��");
			}
		});
}
function ValueCheck(valueId, msg) {
	var check = $$.trim($$(valueId).val());
	if (check == ""&&check == "") {
		alert(msg);
		return false;
	}
	return true;
}


function validSubmit() {
	if (!ValueCheck("#startCity","�����ز���Ϊ��")) {
		return false;
	}
	if (!ValueCheck("#endCity","����ز���Ϊ��")) {

		return false;
	}
	if (!ValueCheck("#Time","ʱ�䲻��Ϊ��")) {

		return false;
	}if (!ValueCheck("#phone","�绰����Ϊ��")) {

		return false;
	}
	return true;
}