$(document).ready(function() {
		showInfoByInspectionId();// 显示信息
		showLocation();//显示位置
		$("#reviewComment").focus(function() {
			var che = confirmContentCheck();

			$("#reviewCommenterr").text("");
		});
		$("#reviewComment").blur(function() {
			confirmContentCheck();
		});
});
function confirmContentCheck() {
	var ret = true;
	ret = valueLength("#reviewComment", "#reviewCommenterr", "0", "2000","内容不能超过2000个字。");
	
	return ret;
}

function showLocation(){

	$.ajax( {
		url : 'showLocationByInspectionId',
		type : "post",
		dataType : "json",
		data:{'inspection.inspectionId':$("#inspectionId").val()},
		success : function(data) {
			$("#locationName").text(data.list[0].locationName);
			var map = new BMap.Map("allmap");
			var point = new BMap.Point(data.list[0].longitude/1e6,data.list[0].latitude/1e6);
			map.centerAndZoom(point, 20);
			var marker = new BMap.Marker(point);  // 创建标注
			map.addOverlay(marker);              // 将标注添加到地图中
			marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

			var label = new BMap.Label("执法地点",{offset:new BMap.Size(20,-10)});
			marker.setLabel(label);
			map.enableScrollWheelZoom(true);//地图缩放
		}
	});

}
function showInfoByInspectionId(){
	$.ajax( {
		url : 'showInspectionInfoByInspectionId',
		type : "post",
		dataType : "json",
		data:{'inspection.inspectionId':$("#inspectionId").val()},
		success : function(data) {
			$("#inspectionObject").text(data.list[0][0].inspectionObject);
			$("#inspectionUser").text(data.list[0][1].name);
			$("#inspectionTime").text(GetShortTime(data.list[0][0].inspectionTime));
			$("#inspectionContent").text(DateIsNull(data.list[0][0].inspectionContent));
			if(data.list[0][0].reviewWether==0)
			$("#reviewWether").text("待审核");
			if(data.list[0][0].reviewResult==1)
			{	
				alert("已审核!");
				window.history.go(-1);
			}if(data.list[0][0].reviewResult==2)
			{	
				$("#reviewWether").text("已审核");
				$("#no_reviewResult").attr("checked","checked");
				$("#reviewComment").attr("value",DateIsNull(data.list[0][0].reviewComment));
			}
			showUpdateUser();//补充信息
		}
	});
}

//补充信息
function showUpdateUser(){
	$.ajax( {
		url : 'showInspectionSupplementByInspectionId',
		type : "post",
		dataType : "json",
		data:{'inspection.inspectionId':$("#inspectionId").val()},
		success : function(data) {
			if(data.list.length==0)
				$("#illegalTable_Supplement").css("display","none");
			else
				$("#illegalTable_Supplement").append($("<tr><td>序号</td><td>补充人</td><td>补充时间</td></tr>"));
			for(var i=0;i<data.list.length;i++){
				$("#illegalTable_Supplement").append($("<tr><td>"+(i+1)+"</td><td>"+data.list[i][1].name+"</td><td>"+GetShortTime(data.list[i][0].supplementTime)+"</td></tr>"));
			}
			showEvidenceList();//附件信息
		}
	});		
}
//附件信息
function showEvidenceList(){
	$.ajax( {
		url : 'showInspectionEvidenceByInspectionId',
		type : "post",
		dataType : "json",
		data:{'inspection.inspectionId':$("#inspectionId").val()},
		success : function(data) {
			var divhtml="";
			if(data.list.length==0)
			{
				$("#evidenceDiv_title").css("display","none");
				$("#evidenceDiv").css("display","none");
			}
			for(var i=0;i<data.list.length;i++){				
				var name=data.list[i].evidenceName;
				var type=name.substring(name.lastIndexOf('.')+ 1);
				if(type=="mp4")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].evidencePath+"'>" +
							"<img src ='image/fileImg/word.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].evidenceName+"</a></div>";
				else if(type=="pdf")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].evidencePath+"'>" +
							"<img src ='image/fileImg/pdf.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].evidenceName+"</a></div>";
				else if(type=="xls")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].evidencePath+"'>" +
							"<img src ='image/fileImg/excel.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].evidenceName+"</a></div>";
				else if(type=="ppt")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].evidencePath+"'>" +
							"<img src ='image/fileImg/ppt.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].evidenceName+"</a></div>";
				else if(type=="png")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].evidencePath+"'>" +
							"<img src ='File/"+data.list[i].evidencePath+"'></a>" +
									"<br /><a class='txt'>"+data.list[i].evidenceName+"</a></div>";
				else if(type=="jpg")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].evidencePath+"'>" +
							"<img src ='File/"+data.list[i].evidencePath+"'></a>" +
									"<br /><a class='txt'>"+data.list[i].evidenceName+"</a></div>";
				else if(type=="gif")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].evidencePath+"'>" +
							"<img src ='File/"+data.list[i].evidencePath+"'></a>" +
									"<br /><a class='txt'>"+data.list[i].evidenceName+"</a></div>";
				else
					
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].evidencePath+"'>" +
							"<img src ='image/fileImg/otherFile.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].evidenceName+"</a></div>";
			}
			$("#evidenceDiv").html(divhtml);
		}
	});			
}
function addCheck(){
	if (!confirmContentCheck()) {
		return false;
	}
	var reviewResult;
	var list = $('input:radio[name="inspection.reviewResult"]:checked').val();
	if (list == null) {
		alert("请选择是否通过审核");
		return;
	} else {
		reviewResult = list;
	}
	$.ajax( {
		url : 'checkInspectionByServer',
		type : "post",
		dataType : "json",
		data:{
		'inspection.inspectionId':$("#inspectionId").val(),
		'inspection.reviewUser':$("#userId").val(),
		'inspection.reviewWether':1,
		'inspection.reviewResult':reviewResult,
		'inspection.reviewComment':$("#reviewComment").val()
		},
		success : function(data) {
			alert("保存成功！");
			window.parent.location.href=$("#basePath").val()+"page/business/permit_see.jsp?inspectionId="+$("#inspectionId").val();
		}
	});			
}