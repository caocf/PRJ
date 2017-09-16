$(document).ready(function(){
    $(".yjxli").css({'background-color':'#0186ed','color':'white'});
    getmessagedata();
})
function getmessagedata(){
    $.ajax({
        url: 'AdviceByID',
        type: "post",
        dataType: "json",
        data: {
            'id': $("#kqid").val()
        },
        success: function (data) {
            $("#cs").text(data.city);
            $("#lxdh").text(data.contact);
            $("#fkyh").text(data.name);
            $("#fksj").text(data.time);
            $("#nr").text(data.cotent);
        }
    })
}