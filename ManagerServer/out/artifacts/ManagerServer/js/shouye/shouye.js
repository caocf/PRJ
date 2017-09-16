$(document).ready(function(){
    $(".gztli").css({'background-color': '#0186ed', 'color': 'white'});
    for(i=0;i<$("#dz").val().length;i++){
        if($("#dz").val().charAt(i)==0){
            $(".fadiv:eq("+i+")").hide();
            $(".mui-switch:eq("+i+")").removeClass('mui-active');
        }
    }
    geteveryNO();
})
//获取各项条数
function geteveryNO(){
    //alert('1110111'.charAt(3));
    $.ajax({
        url: 'UserCheckCount',
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#no_zcsp').text(data.resultcode);
        }
    })
    $.ajax({
        url: 'LawCheckCount',
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#no_wzqz').text(data.resultcode);
        }
    })
    $.ajax({
        url: 'DangerShipCount',
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#no_whjg').text(data.resultcode);
        }
    })
    $.ajax({
        url: 'DangerComCount',
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#no_whzy').text(data.resultcode);
        }
    })
}

//保存面板开关
function savembbtnstatus(){
    var statusstr='';
    $(".mui-switch").each(function(i){
        if($(this).hasClass('mui-active')){
            $(".fadiv:eq("+i+")").show();
            statusstr+='1';
        }else{
            statusstr+='0';
            $(".fadiv:eq("+i+")").hide();
        }
    });
    $.ajax({
        url: 'CommitSet',
        type: "post",
        dataType: "json",
        data : {
            'id' :  $('#userid').val(),
            'setting' : statusstr
        },
        success: function () {
            alert('保存成功');
            $('#setModal').modal('hide');
        }
    })
}