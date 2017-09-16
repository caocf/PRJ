//checkbox全选
function checkedOrNo(obj){
	var isCheck = obj.checked;
	$('.systemcheck').each(function(){
		this.checked=isCheck;
	});
}