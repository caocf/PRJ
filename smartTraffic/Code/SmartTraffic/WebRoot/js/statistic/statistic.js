$(document).ready(function(){
	$("#trafficStatics_back").click(function(){
		window.location.href=$("#basePath").val()+"page/main/traffic.jsp";
	});
	$("#link_tb").click(function(){
		window.open("http://172.20.44.68/rsbi/Login!login.action?" +
		"userName=jiaotong&password=123456&rurl=frame/NFrame.action");
	});
	setHeight();
	readXml();
	
	//$("#top_text").text("统计分析");
});
var linknum=0;
function setHeight(){
	var height=document.documentElement.clientHeight-50;
	var width=document.documentElement.clientWidth-291;
	$(".left_I1").css({"height":""+height+"px"});
	$("#iframeId").css({"height":""+height+"px","width":""+width+"px"});
	//$(".common_iframe").css({"height":""+height+"px","width":""+width+"px"});
	//$(".common_iframe1").width(width);
}
window.onresize=function(){
	var height=document.documentElement.clientHeight-50;
	var width=document.documentElement.clientWidth-291;
	$(".left_I1").css({"height":""+height+"px"});
	$("#iframeId").css({"height":""+height+"px","width":""+width+"px"});
	//$(".common_iframe").css({"height":""+height+"px","width":""+width+"px"});
	//$(".common_iframe").width(width);
};


function readXml(){
	var pageFirst = "";
	$.ajax({
		url: 'statisticsinfo.xml',
	  	dataType: 'xml',
	  	success: function(data){
	  		$(data).find("statisticsinfo").find("module").each(function(index, domEle) {
	  			var name = $(domEle).attr("name");
	  			//var url = $(domEle).attr("url");
	  			//if(index==0){
	  				//pageFirst = url;
	  			//}
	  			var newDiv=$("<div class='left_link'></div>");
	  			newDiv.append($("<label class='left_link_label'><div class='link_image_div'><img class='label_image' src='image/main/arrow.png' /></div><a id='left_link_label_span"+linknum+"'  class='left_link_label_span'" +
	  					"  >"+name+"</a></label>"));
	  			newDiv.append($("<div class='left_link_div' id='left_link_div"+linknum+"'></div>"));
	  			$(".left_I1").append(newDiv);
		  			$(domEle).find("group").each(function(index2, domEle2){
		  				//alert($(domEle2).find("name").text());
		  				var newA=$("<a class='left_link_a' href='"+$(domEle2).find("url").text()+"' target='statistic' ><span class='left_link_a_span' style='white-space:nowrap;'>"+$(domEle2).find("name").text()+"</span></a>");
		  				$("#left_link_div"+linknum).append(newA);
		  			});
		  		linknum++;
	  			});
	  			$(".left_link_label").click(function(){
	  				if($(this).siblings(".left_link_div").css("display")=="none"){
	  					//$(".left_link_div").css({"display":"none"});
	  					//$(".label_image").attr("src","image/main/arrow.png");
	  					$(this).children(".link_image_div").children(".label_image").attr("src","image/main/arrow_down.png");
	  					$(this).siblings(".left_link_div").css({"display":"block"});
	  				}else{
	  					$(this).children(".link_image_div").children(".label_image").attr("src","image/main/arrow.png");
	  					$(this).siblings(".left_link_div").css({"display":"none"});
	  				}
	  			});
		  		$(".left_link_a").click(function(){
					$(".left_link_a_select").attr("class","left_link_a");
					$(this).attr("class","left_link_a_select");
				});
	  			
	  			if($("#num").val()!=""&&$("#num").val()!="null"&&$("#num").val()==0){
	  				$("#left_link_div0").css({"display":"block"});
	  				$("#left_link_div0").siblings(".left_link_label").children(".link_image_div").children(".label_image").attr("src","image/main/arrow_down.png");
	  				//$("#left_link_div"+$("#num").val()).children(".left_link_a").click();
	  				var src=$("#left_link_div0").children(".left_link_a").last().attr("href");
	  				$("#left_link_div0").children(".left_link_a").last().attr("class","left_link_a_select");
	  				//var src=$("#left_link_label_span"+$("#num").val()).attr("href");
	  				$("#statistic").attr("src",src);
	  				$("#statistic").show();
	  			}else{
	  				//var src = $(data).find("statisticsinfo").find("mainurl").attr("url");
	  				//src="page/main/firstpage1.jsp";
	  				//$("#statistic").attr("src",pageFirst);
	  				//$("#statistic").show();
	  			}
	  		}
	 });
	
}