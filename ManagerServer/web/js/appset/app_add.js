$(document).ready(function(){
    $("#th-large_li").addClass("active");
    if($("#apptype").val()==1){
        $("#outerApp_li").addClass("active");
    }else{
        $("#innerApp_li").addClass("active");
    }
    $("#appfile").change(function(){
        var appname=$("#appfile").val();
        if(appname!=''&&appname!=null){
            appname=appname.split('\\')[appname.split('\\').length-1];
        }
        $("#filenamespan").text(appname);
    })
})
function sendapp(){
    $.ajaxFileUpload({
        url : 'UpdateAPP',
        secureuri : false,
        fileElementId: ['appfile'],
        type : 'post',
        dataType : 'json',
        data : {
            'versioncode' : $("#bbh").val(),
            'versionname' : $("#bbm").val(),
            'updatelog' : $("#syword").val()
        },
        success : function(data) {
            //console.log(JSON.stringify(data));
            if(data.resultcode==1){
                alert('发布版本成功！');
                if($("#apptype").val()==1){
                    window.location.href=$("#basePath").val()+"outerApp_list";
                }else{
                    window.location.href=$("#basePath").val()+"outerApp_list";
                }
            }else{
                alert(data.resultdesc);
                return
            }

        }
    });
}