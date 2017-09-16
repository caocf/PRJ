$(document).ready(function(){
    $("#file_li").addClass("active");
    if($("#filetype").val()==1){
        $("#filecheck_li").addClass("active");
    }else{
        $("#myfile_li").addClass("active");
    }
    $('#summernote').summernote({
        lang: 'zh-CN',
        minHeight:400,
        toolbar: [
            ['style', ['bold', 'italic', 'underline', 'clear']],
            ['font', ['strikethrough']],
            ['fontsize', ['fontsize']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['color', ['color']],
        ]
    });
})