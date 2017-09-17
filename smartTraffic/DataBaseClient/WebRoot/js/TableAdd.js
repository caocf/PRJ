var valnum=0;
var ColumnsArray=new Array();
$(document).ready(function() {
	queryColumnByTableName();
});

function queryColumnByTableName(){
	$.ajax( {
		url : "queryColumnByTableName",
		type : "post",
		dataType : "json",
		data : {
			'tableName' : $("#tablename").val()
		},
		
		success : function(data) {

			if (data.columns == null) {
				alert("获取失败数据");
			} else {
				var columns = data.columns;
				for ( var i = 0; i < columns.length; i++) {

					if (columns[i] != null){
						var newTr=$("<tr></tr>");
						if (columns[i] != null){
							if (columns[i][1].columnComment != null)
								newTr.append($("<td>" + columns[i][1].columnComment+ "</td>"));
							else
								newTr.append($("<td>" + columns[i][0].columnName+ "</td>"));
						}
						
						ColumnsArray[valnum]=columns[i][0];
						
						if (columns[i][0].columnType=="DATE"){
							newTr.append($("<td><input type='text' id='val"+valnum+"' class='input_time'  readonly='readonly' "+ 
							"onFocus='HS_setDate(this,75,268)'  name='paramsMap["+valnum+"].value' /></td>"));
						}else{
							var InputLength=0;
							if(columns[i][0].columnType=="VARCHAR2"){
								InputLength=Math.ceil(columns[i][0].columnLength/2);
								
							}else {
								InputLength=columns[i][0].columnLength;
							}
							if(InputLength<=40){
								newTr.append($("<td><input type='text' id='val"+valnum+"' class='input_text' name='paramsMap["+valnum+"].value'"+
										" onfocus=CheckValue("+valnum+") onblur=CheckValue("+valnum+") maxlength="+InputLength+" /></td>"));	
							}else{
								var InputStyleHeight=20*(Math.ceil(InputLength/40)+1)+"px";
								newTr.append($("<td><textarea id='val"+valnum+"' class='textarea' name='paramsMap["+valnum+"].value' " +
										"style='width:600px;height:"+InputStyleHeight+";' onfocus=CheckValue("+valnum+") onblur=CheckValue("+valnum+")  maxlength="+InputLength+" ></textarea></td>"));
							}
						
							
						}
						var nullable="";
						if (columns[i][0].nullable=="N"){
							nullable="<font color=red >*</font>";
						}
						newTr.append($("<td>"+nullable+"<input type='hidden' name='paramsMap["+valnum+"].key' value='"+columns[i][1].columnName+"' /><label id='val"+valnum+"err' class='errLabel'></label></td>"));
						$("#table_data").append(newTr);
						valnum++;
					}
						
				}
				
			
			}

		},
		error : function(a, b, c) {
			alert("抱歉，无法获取数据");
		}
	});
}
function addContent(){
	if(!validSubmitother()){
		return;
	}
	$.ajax( {
		url : "addContent",
		type : "post",
		dataType : "json",
		data : $("#contentform").serialize(),
		
		success : function(data) {
			alert("保存成功！");
			//window.location.reload();
			back();
		},
		error : function(a, b, c) {
			alert("保存失败");
		}
	});
}
function add_submit(){
	var filename = $("#excelfile").val();
	if(filename==""){
		alert("请添加附件");
		return false;
	}
	var mime = filename.toLowerCase().substr(filename.lastIndexOf("."));
	if (mime != ".xls") {
		alert("请选择xls格式的文件上传");
		return false;
		
	}
	return true;
}
function back(){
	window.location.href=$("#basePath").val()+"page/TableInfo.jsp?pNum="+$("#pNum").val()+"&tNum="+$("#tNum").val();
}