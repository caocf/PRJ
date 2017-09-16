/**
 * Created by TWQ on 2016/8/10.
 */
$(document).ready(function(){
    $("#information").addClass("active");
    $("#inform-send").addClass("active");
});

//checkbox全选
function checkedOrNo(obj){
    var isCheck = obj.checked;
    $('.systemcheck').each(function(){
        this.checked=isCheck;
    });
}
