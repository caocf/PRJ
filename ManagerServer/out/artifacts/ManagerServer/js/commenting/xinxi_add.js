$(document).ready(function()
{
    $("#commenting_li").addClass("active");
        $("#myxinxi_li").addClass("active");
        $('#summernote').summernote(
        {
        lang: 'zh-CN',
        minHeight:400,
        toolbar: [
            ['style', ['bold', 'italic', 'underline', 'clear']],
            ['font', ['strikethrough']],
            ['fontsize', ['fontsize']],
            //['insert', ['link', 'picture']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['color', ['color']],
        ]
    });
})
//选项菜单
function setspr(id,obj)
{
    var text1=$(obj).text();
    var ID=$(obj).attr("title");//ID值
    $("#"+id).text(text1);
    $("#"+id).attr("title",ID);
    //alert(selected);
}
//提交
function Sumit()
{
    //
    var title=$("#title").val();
    var region=$("#sprword").attr("title");
    var type=$("#sprword1").attr("title");
    var content=$("#summernote").summernote('code')
    //alert(content);
    if(title=="")
    {
        alert("标题不能为空！");
        return;
    }
    if(region=="")
    {
        alert("地区不能为空！");
        return;
    }
    if(type=="")
    {
        alert("类型不能为空！");
        return;
    }

    if(content=="")
    {
        alert("内容不能为空！");
        return;
    }

    $.ajax({
        url:"CommitNews",
        type : "post",
        dataType : "json",
        data :
        {
            "type" :type,
            "title" : title,
            'content': content,
            'region': region
        },
        success : function(data)
        {
            alert("提交成功");
            window.location.href=$("#basePath").val()+"xinxiquery_list";
        }
    });
}