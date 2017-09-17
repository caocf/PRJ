function CheckValue(num){
	var ret = true;
	$("#val"+num+"err").text("");
	if(ColumnsArray[num].nullable=="N"){
		ret = emptyChk("#val"+num, "#val"+num+"err", "必填项，不能为空");
	}
	if(ret==true){
		 if(ColumnsArray[num].columnType=="NUMBER"){
				ret = CheckNumberOrDouble("#val"+num, "#val"+num+"err", "只能输入数字");
			}
	}
	return ret;
}
function validSubmitother() {
	var valuelist=$("#table_data td .input_text,#table_data td .input_time,#table_data td .textarea");
	for(var i=0;i<valuelist.length;i++){
		if (!CheckValue(valuelist.get(i).id.replace("val",""))) {
			return false;
		}
		
	}
	return true;
}