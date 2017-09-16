/**
 * Created by TWQ on 2016/9/7.
 */
$(document).ready(function(){
    $("#officeAssistant").addClass("active");
    $("#knowledgeBase_li").addClass("active");

    $('#attachment').change(function(){
        $('#filenamespan').text($('#attachment').val());
    });
})

function send(){
    if($("#title").val() == "") {
        alert("文档主题不能为空！");
    } else {
        $("#knowledgeForm").submit();
    }
}