$(document).ready(function()
{
  $("#commenting_li").addClass("active");
  Load();
})

function Load()
{
  var ID=$('#itemid').val();
  $.ajax({
    url:"NoticeByID",
    type : "post",
    dataType : "json",
    data :
    {
      "id":ID
    },
    success : function(data)
    {
        var status=new Array("待审核","不通过","已发布");
        $('#status').text(status[data.status-1]);

        var opinon=data.opinon;
        $('#opinon').text(opinon);

        var title=data.title;
        $('#title').text(title);

        var time=data.updatetime;
        $('#ptime').text(time.substring(0,10));

        var regions=new Array('浙江省港航管理局','杭州市港航管理局','嘉兴市港航管理局','湖州市港航管理局','舟山市港航管理局');
        $('#region').text(regions[data.region-1]);
        $('#content').html(data.content);

        var tocheck=$('#mode').val();
        if(tocheck==2)//审核模式
        {
            $('#check').css('display',"block");
            $('#c1').css('display',"inline");
            $('#c2').css('display',"inline");
        }
        else//查看模式
        {
            $('#check').css('display',"none");
            $('#c1').css('display',"none");
            $('#c2').css('display',"none");
        }
    }

    });
}
    function Check()
    {
        var status=  $("input[name='radiobutton']:checked").val();
        var op=$('#shenheword').val();

        $.ajax({
            url: "CheckNotice",
            type: "post",
            dataType: "json",
            data: {
                "ID": $('#itemid').val(),
                "status": status,
                "opinion": op,
            },
            success: function (data)
            {
                history.back(-1);
            }
        });
    }