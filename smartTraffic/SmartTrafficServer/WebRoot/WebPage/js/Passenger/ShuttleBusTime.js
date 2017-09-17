var oldpagenum = 1;
function TableChange(num,type) {
	$$("#tabel" + oldpagenum).css("display", "none");
	if (type=="first") {
		$$("#tabel" + num).css("display", "");
		oldpagenum=num;
	} else if (type=="before"){
		oldpagenum=oldpagenum-num;
		$$("#tabel" + oldpagenum).css("display", "");
	} else if (type=="after"){
		oldpagenum=oldpagenum+num;
		$$("#tabel" + oldpagenum).css("display", "");
	} else if (type=="last"){
		$$("#tabel" + num).css("display", "");
		oldpagenum=num;
	}
	if(oldpagenum==1){
		$$(".thisfirst").css("display", "none");
		$$(".thislast").css("display", "");
	}else if(oldpagenum==Number($$("#allnum").val())){
		$$(".thisfirst").css("display", "");
		$$(".thislast").css("display", "none");
	}else{
		$$(".thisfirst").css("display", "");
		$$(".thislast").css("display", "");
	}
	$$(".thispage").text(oldpagenum);
}