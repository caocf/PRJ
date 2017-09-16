$(document).ready(function() {
		showInfoByIllegalId();// 显示信息
		showLocation();//显示位置
		
});
function showLocation(){

	$.ajax( {
		url : 'showLocationByIllegalId',
		type : "post",
		dataType : "json",
		data:{'illegal.illegalId':$("#illegalId").val()},
		success : function(data) {
			$("#locationName").text(data.list[0].locationName);
			var map = new BMap.Map("allmap");
			var point = new BMap.Point(120.085213,30.87123);
			if(data.list[0].longitude!=null&&data.list[0].latitude!=null){
				 point = new BMap.Point(data.list[0].longitude/1e6,data.list[0].latitude/1e6);
				 map.centerAndZoom(point, 20);
					var marker = new BMap.Marker(point);  // 创建标注
					map.addOverlay(marker);              // 将标注添加到地图中
					marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
					var label = new BMap.Label("执法地点",{offset:new BMap.Size(20,-10)});
					marker.setLabel(label);		
			}else{
				 map.centerAndZoom(point, 20);
			}
			
			map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, type: BMAP_NAVIGATION_CONTROL_ZOOM}));  //右下角，仅包含缩放按钮
			
		}
	});

}
function showInfoByIllegalId(){
	$.ajax( {
		url : 'showInfoByIllegalId',
		type : "post",
		dataType : "json",
		data:{'illegal.illegalId':$("#illegalId").val()},
		success : function(data) {
			$("#illegalObject").text(data.list[0][0].illegalObject);
			$("#enforecers1").text(data.list[0][1].name);
			$("#enforecers2").text(data.list[0][2].name);
			$("#illegalTime").text(GetShortTime(data.list[0][0].illegalTime));
			$("#illegalReason").text(data.list[0][3].reasonName);
			$("#illegalContent").text(DateIsNull(data.list[0][0].illegalContent));
			$("#reviewUser").text(data.list[0][4].name);
			if(data.list[0][0].reviewWether==0)
			$("#reviewWether").text("待审核");
			if(data.list[0][0].reviewWether==1)
			{	
				
				$("#reviewWether").text("已审核");
				showCheckInfoByIllegalId();
			}
			showUpdateUser();//补充信息
		}
	});
}
function showCheckInfoByIllegalId(){
	$.ajax( {
		url : 'showCheckInfoByIllegalId',
		type : "post",
		dataType : "json",
		data:{'illegal.illegalId':$("#illegalId").val()},
		success : function(data) {
			//$("#reviewUser").text(data.list[0][1].name);
			$("#reviewTime").text(GetShortTime(data.list[0][0].reviewTime));
			if(data.list[0][0].reviewResult==1){
				
				$("#print_img").show();
				
				if(data.list[0][0].isPost==1)
				$("#reviewResult").text("通过(已交换至综合系统)");
				else 
					$("#reviewResult").text("通过（未能成功交换至综合系统)");
			}
			
			if(data.list[0][0].reviewResult==2)	
			{
				$("#reviewResult").text("驳回");
			}
			$("#reviewComment").text(data.list[0][0].reviewComment);
		}
	});	
}
//补充信息
function showUpdateUser(){
	$.ajax( {
		url : 'showSupplementByIllegalId',
		type : "post",
		dataType : "json",
		data:{'illegal.illegalId':$("#illegalId").val()},
		success : function(data) {
			if(data.list.length==0)
			$("#Supplement_title").css("display","none");
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
		url : 'showEvidenceByIllegalId',
		type : "post",
		dataType : "json",
		data:{'illegal.illegalId':$("#illegalId").val()},
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
