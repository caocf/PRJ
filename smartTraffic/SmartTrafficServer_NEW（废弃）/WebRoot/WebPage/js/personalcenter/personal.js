
function OnLoad(){
	LogAndExitRefresh=true;//登录退出时是否刷新页面
	if ($$("#LoginUser").val() == "null") {
		LoginWeb();	
	} else {
		GetUserInfoForWeb();//返回个人信息
	}
}


function GetUserInfoForWeb(){  
	$$.ajax( {
		url : 'GetUserInfoForWeb',
		type : 'post',
		dataType : 'json',
		success : function(data) {
		if(data.user!=null){
			GetPersonInforByWEB(data);
		}else{
			alert("获取用户信息失败");
		}
		
	},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	});
}
function GetPersonInforByWEB(data){
	$$("#nametext").text(data.user.username);
	if(data.user.sex==1){
	$$("#sextext1").attr("checked","checked");
	}else if(data.user.sex==2){
		$$("#sextext2").attr("checked","checked");	
	}else{
		$$("#sextext3").attr("checked","checked");
	}

	var tt = data.user.address;
	var add=[] ;
	if(tt!=null){
		 add = tt.split("-");
	}
	if (add.length == 3) {
		getAreaIDbyName(add[0],add[1],add[2]);
	} else {
		initComplexArea('seachprov', 'seachcity', 'seachdistrict',
				area_array, sub_array, '0', '0', '0');
	}
	
	tt=data.user.birthday;
	if(tt!=null){
		add=[] ; add = tt.split("-");
	}else{
		add=[] ;
	}
	
	if (add.length == 3) {
		 document.getElementById("seachyear").value=add[0];
		 document.getElementById("seachmonth").value=add[1];
		 document.getElementById("seachday").value=add[2];
	} 
	
	$$("#phonetext").text(DateIsNull(data.user.phone,"无"));
	$$("#emailtext").text(DateIsNull(data.user.email,"无"));

}
//根据地区名查询地区码
function getAreaIDbyName(province,city,area) {
	var provinceId;
	var cityId;
	var areaId=0;
	for(var i=0;i<area_array.length;i++){
		if(province==area_array[i]){
			provinceId=i;
			
		}
	}
	if(provinceId == 11 || provinceId == 12 || provinceId == 31 || provinceId == 71 || provinceId == 50 || provinceId == 81 || provinceId == 82){
		//直辖市
		if (sub_arr[provinceId] != undefined) {
			for ( var i = 0; i < sub_arr[provinceId].length; i++) {
				if (sub_arr[provinceId][i] != "") {
					if (city == sub_arr[cityId][i]) {
						cityId = i;
					}
				}
			}
		}	
	}else{
		for(var i=0;i<l_arr.length;i++){
			if(l_arr[i]!=""){
				if(city==l_arr[i]){
					cityId=i;
					
				}
			}
		}
		if (sub_arr[cityId] != undefined) {
			for ( var i = 0; i < sub_arr[cityId].length; i++) {
				if (sub_arr[cityId][i] != "") {
					if (area == sub_arr[cityId][i]) {
						areaId = i;

					}
				}
			}
		}
	}
	
	initComplexArea('seachprov', 'seachcity', 'seachdistrict',
			area_array,sub_array, provinceId, cityId, areaId);
}


function ModifyUserInfoForWeb(){  //更新资料
	 if(confirm("是否更新资料？")){
		var address=ModifyUserInfoCheck();
		var birthday=$$("#seachyear").val()+"-"+$$("#seachmonth").val()+"-"+$$("#seachday").val();
		
		 $$.ajax( {
				url : 'ModifyUserInfoForWeb',
				type : 'post',
				dataType : 'json',
				data : {
					'address':address,
					'birthday':birthday,
					'sex':$$('input[name="sex"]:checked').val()
				},
				success : function(data) {
					if(data.success){
						alert("资料已更新！");
					GetPersonInforByWEB(data);
					}else{
						alert("资料更新失败！");
					}
				},
				error : function(a, b, c) {
					alert("出现错误，请重新提交");
				}

			}); 
	 }
	
}
//提交验证
function ModifyUserInfoCheck() {
	var address = "";
	if ($$("#seachprov").val() != "0") {
		address = $$("#seachprov").find("option:selected").text() + "-";
		if ($$("#seachcity").val() == "0") {
			address += "-";
		} else {
			address += $$("#seachcity").find("option:selected").text() + "-";
		}
		if ($$("#seachdistrict").val() != "0") {
			address += $$("#seachdistrict").find("option:selected").text();
		}

	}
	return address;
}
function bandphone(){ //绑定手机
	window.location.href=$$("#basePath").val()+"WebPage/page/register/ChangPhone1.jsp";
}

function bandemail(){  //绑定邮箱
	alert("该功能正在开发中");
}
function recognizenow(){  //实名认证
	alert("该功能正在开发中");
}

