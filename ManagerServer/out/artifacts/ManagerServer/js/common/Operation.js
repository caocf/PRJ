function textOperationOver(id){
	id.style.color="#76ba18";
}
function textOperationOut(id){
	id.style.color="#9a9a9a";
}
function AOperationOver(id){
	id.style.color="#497310";
}
function AOperationOut(id){
	id.style.color="#639d12";
}
//查询
function  QueryOver(id){
	id.src="image/operation/query_hover.png";
}
function  QueryOut(id){
	id.src="image/operation/query_normal.png";
}
//搜索
function  SearchOver(id){
	id.src="image/operation/search_hover.png";
}
function  SearchOut(id){
	id.src="image/operation/search_normal.png";
}
//返回-big
function  BackBigOver(id){
	id.src="image/operation/back_big_hover.png";
}
function  BackBigOut(id){
	id.src="image/operation/back_big_normal.png";
}
//返回-small
function  BackSmallOver(id){
	id.src="image/operation/back_small_hover.png";
}
function  BackSmallOut(id){
	id.src="image/operation/back_small_normal.png";
}
//编辑-big
function  UpdateOver(id){
	id.src="image/operation/update_hover.png";
}
function  UpdateOut(id){
	id.src="image/operation/update_normal.png";
}

//提交-button
function  SubmitOver(id){
	id.style.background="url(image/operation/submit_hover.png)";
}
function  SubmitOut(id){
	id.style.background="url(image/operation/submit_normal.png)";
}
//提交-img
function  SubmitOver_img(id){
	id.src="image/operation/submit_hover.png";
}
function  SubmitOut_img(id){
	id.src="image/operation/submit_normal.png";
}
//添加船舶-img
function  SubmitaddInfo_img(id){
	id.src="image/operation/btn_add-ship_pressed.png";
}
function  SubmitaddInfoOut_img(id){
	id.src="image/operation/btn_add-ship_normal.png";
}
//添加码头-img
function  SubmitaddInfo_wharf(id){
	id.src="image/operation/btn_add-wharf_pressed.png";
}
function  SubmitaddInfoOut_wharf(id){
	id.src="image/operation/btn_add-wharf_normal.png";
}
//删除-big
function  DeleteBigOver(id){
	id.src="image/operation/delete_big_hover.png";
}
function  DeleteBigOut(id){
	id.src="image/operation/delete_big_normal.png";
}
//删除-small
function  DeleteSmallOver(id){
	id.src="image/operation/delete_small_hover.png";
}
function  DeleteSmallOut(id){
	id.src="image/operation/delete_small_normal.png";
}
//取消-button-big
function  ResetBigOver_button(id){
	id.style.background="url(image/operation/reset_big_hover.png)";
}
function  ResetBigOut_button(id){
	id.style.background="url(image/operation/reset_big_normal.png)";
}
//取消-img-big
function  ResetBigOver(id){
	id.src="image/operation/reset_big_hover.png";
}
function  ResetBigOut(id){
	id.src="image/operation/reset_big_normal.png";
}
//重置-img-big
function  ResetBigOver2(id){
	id.src="image/operation/reset_big_hover2.png";
}
function  ResetBigOut2(id){
	id.src="image/operation/reset_big_normal2.png";
}
//重置-img-button
function  ResetBigOver2_BT(id){
	id.style.background="url(image/operation/reset_big_hover2.png)";
}
function  ResetBigOut2_BT(id){
	id.style.background="url(image/operation/reset_big_normal2.png)";
}
//取消-button-small
function  ResetSmallOver(id){
	id.src="image/operation/reset_small_hover.png";
}
function  ResetSmallOut(id){
	id.src="image/operation/reset_small_normal.png";
}
//确定
function  SureOver(id){
	id.src="image/operation/sure_hover.png";
}
function  SureOut(id){
	id.src="image/operation/sure_normal.png";
}
//选择
function  ChooseOver(id){
	id.src="image/operation/choose_hover.png";
}
function  ChooseOut(id){
	id.src="image/operation/choose_normal.png";
} 
//添加附件
function  AttachmentsOver(){
	$(".file_img").attr("src","image/operation/add_attachments_hover.png");
}
function  AttachmentsOut(id){
	$(".file_img").attr("src","image/operation/add_attachments_normal.png");
}


//删除日志
function  deletelogOver(id){
	id.src="image/operation/delete_log_hover.png";
}
function  deletelogOut(id){
	id.src="image/operation/delete_log_normal.png";
}

//日志高级查询
function  selectlogOver(id){
	id.src="image/operation/log_select_hover.png";
}
function  selectlogOut(id){
	id.src="image/operation/log_select_normal.png";
}

//管理通讯录
function  addressuserOver(id){
	id.src="image/operation/address_user_hover.png";
}
function  addressuserOut(id){
	id.src="image/operation/address_user_normal.png";
}

function GetShortTime(time){
	return time.substr(0,19);
}
function GetShortTime1(time){
	return time.substr(0,16);
}
//管理船员信息
function manageboatmanOver(id){
	id.src="image/sailing/manageboatmaninfo_hover.png";
}
function manageboatmanOut(id){
	id.src="image/sailing/manageboatmaninfo_normal.png";
}
//新增
function  addkonwOver(id){
	id.src="image/operation/add_know_hover.png";
}
function  addkonwOut(id){
	id.src="image/operation/add_know_normal.png";
}
//导出
function  ExpOver(id){
	id.src="image/statistic/exp_hover.png";
}
function  ExpOut(id){
	id.src="image/statistic/exp_normal.png";
}
//导出
function  PrintOver(id){
	id.src="image/operation/print_pressed.png";
}
function  PrintOut(id){
	id.src="image/operation/print_normal.png";
}