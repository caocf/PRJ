function dtpaging(actionname,functionname,page,pages){
	//总页数pages为1时，隐藏分页
	$("#pagination").html('');
	if(parseInt(pages)<=1){
		$("#pageshow").hide();
		return
	}
	if(parseInt(pages)>1){
		$("#pageshow").show();
	}
	//首页
	var firstpage='';
	var shang=parseInt(page)-1;
	var isfirstonclick="onclick="+functionname+"('"+actionname+"','"+1+"')";//首页点击事件
	var ispreviousonclick="onclick="+functionname+"('"+actionname+"','"+shang+"')";//上一页点击事件
	if(parseInt(page)==1){
		firstpage="class='disabled'";
		shang=1;
		isfirstonclick='';
		ispreviousonclick='';
	}
	
	//末页
	var lastpage='';
	var xia=parseInt(page)+1;
	var islastonclick="onclick="+functionname+"('"+actionname+"','"+parseInt(pages)+"')";
	var isnextonclick="onclick="+functionname+"('"+actionname+"','"+xia+"')";
	if(parseInt(page)==parseInt(pages)){
		lastpage="class='disabled'";
		xia=parseInt(pages);
		islastonclick='';
		isnextonclick='';
	}
	
	//首页，末页，上一页，下一页
	var firstpageli="<li "+firstpage+"><a "+isfirstonclick+"><span aria-hidden='true'><abbr title='首页'><<</abbr></span></a></li>";
	var previous="<li "+firstpage+"><a "+ispreviousonclick+"><span aria-hidden='true'><abbr title='上一页'><</abbr></span></span></a></li>";
	var lastpageli="<li "+lastpage+"><a "+islastonclick+"><span aria-hidden='true'><abbr title='末页'>>></abbr></span></span></a></li>";
	var next="<li "+lastpage+"><a "+isnextonclick+"><span aria-hidden='true'><abbr title='下一页'>></abbr></span></span></a></li>";
	
	//总页数小于5时
	if(parseInt(pages)<=5){
		$("#pagination").append(firstpageli);
		$("#pagination").append(previous);
		for (var int = 1; int <= parseInt(pages); int++) {
			var active='';
			if(int==parseInt(page)){
				active="class='active'";
			}
			var intpage="<li "+active+"><a onclick="+functionname+"('"+actionname+"','"+int+"')>"+int+"</a></li>";
			$("#pagination").append(intpage);
		}
		$("#pagination").append(next);
		$("#pagination").append(lastpageli);
	}
	else{
		if(parseInt(page)<=2){
			$("#pagination").append(firstpageli);
			$("#pagination").append(previous);
			for (var int = 1; int <= 5; int++) {
				var active='';
				if(int==parseInt(page)){
					active="class='active'";
				}
				var intpage="<li "+active+"><a onclick="+functionname+"('"+actionname+"','"+int+"')>"+int+"</a></li>";
				$("#pagination").append(intpage);
			}
			$("#pagination").append(next);
			$("#pagination").append(lastpageli);
		}else if(parseInt(page)>(parseInt(pages)-2)){
			$("#pagination").append(firstpageli);
			$("#pagination").append(previous);
			for (var int = (parseInt(pages)-4); int <= parseInt(pages); int++) {
				var active='';
				if(int==parseInt(page)){
					active="class='active'";
				}
				var intpage="<li "+active+"><a onclick="+functionname+"('"+actionname+"','"+int+"')>"+int+"</a></li>";
				$("#pagination").append(intpage);
			}
			$("#pagination").append(next);
			$("#pagination").append(lastpageli);
		}else{
			$("#pagination").append(firstpageli);
			$("#pagination").append(previous);
			for (var int = (parseInt(page)-2); int <= (parseInt(page)+2); int++) {
				var active='';
				if(int==parseInt(page)){
					active="class='active'";
				}
				var intpage="<li "+active+"><a onclick="+functionname+"('"+actionname+"','"+int+"')>"+int+"</a></li>";
				$("#pagination").append(intpage);
			}
			$("#pagination").append(next);
			$("#pagination").append(lastpageli);
		}
	}
	$('#pagination').find("li").css('cursor','pointer');
	$('#pagination').find("abbr").css({'border-bottom':'0'});
}
