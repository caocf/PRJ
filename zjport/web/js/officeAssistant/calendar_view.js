/**
 * Created by Administrator on 2016/9/18.
 */
$(document).ready(function(){
    $("#officeAssistant").addClass("active");
    $("#calendar_li").addClass("active");
})


function deleteIt(id) {
    window.location.href="deleteCalendar?id="+id;
}