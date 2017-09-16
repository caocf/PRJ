/**
 * Created by TWQ on 2016/8/10.
 */
$(document).ready(function(){
    $("#information").addClass("active");
    $("#inform-send").addClass("active");
});

function deleteIt(id) {
    window.location.href = "deleteInform?id="+id;
}

function sendAgain(id) {
    window.location.href = "sendAgain?id="+id;
}