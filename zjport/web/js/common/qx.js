//权限显示
function setqx(){
	if($("#qxstr").val()==''){
		return
	}
	var qxz=$("#qxstr").val().split(',');
	$('.qxz').show();
	var qxstr='';
	for (var int = 0; int < qxz.length; int++) {
		if(int==(qxz.length-1))
			qxstr+='.qx'+qxz[int];
		else
			qxstr+='.qx'+qxz[int]+',';
	}
	$(qxstr).remove();
}