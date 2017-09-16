$(document).ready(function() {
	updatewharfname();
});

function updatewharfname(){
	var wharfnameID=$("#wharfnameID").val();
	var wharfNorm=$("#wharfNorm").val();
	$("#wharfID").attr("value",wharfnameID);
	if(wharfNorm==null||wharfNorm=="null"){
	$("#wharfWorkNorm").attr("value","未设定");
	}else{
	$("#wharfWorkNorm").attr("value",wharfNorm);	
	}
}
function submitwharfname(){
	var wharfnameID=$("#wharfnameID").val();
	var wharfWorkNorm=$("#wharfWorkNorm").val();
	if(wharfWorkNorm==""){
		alert("码头定量不能为空");
	}else{
		if(isNaN(wharfWorkNorm)){
			alert("请输入数字");
		}else{
			if(parseInt(wharfWorkNorm)!=wharfWorkNorm)
			   {
			  alert("请输入整数");
			   }else{
					$.ajax( {
						url : 'updateWharfName',
						type : "post",
						dataType : "json",
						data : {
							'id': wharfnameID,
							'content':wharfWorkNorm
						},
						success : function(data) {
							
							window.location.href = $("#basePath").val()+"page/wharfwork/wharfNameList.jsp";
						}
					});
			   }
		}	
		
	}
}