$(document).ready(function()
{
    $("#cog").addClass("active");
    $("#systemlog_li").addClass("active");

    Search("",1);
})

function option(data,id,obj)
{
    var tx=$(obj).text();
    $("#"+id).text(tx);
    $("#"+id).attr('title',data);
}

var staticpage
;
function Search(name,page)
{
    var title=$('#listkey').val();
    var date1=$('#beginTime').val();
    var date2=$('#endTime').val();

    $.ajax({
        url:'SystemLogsByUser',
        type : "post",
        dataType : "json",
        data:
        {
            "name":title,

            "date1":date1,
            "date2":date2,
            "page":page,
        },
        success : function(data)
        {
            var list=data.list;
            staticpage=page;

            $(".tr").remove();
            //alert( data.pages);
            pagingmake("", 'Search', page, data.pages);
            $(list).each(function (index, item)
            {
                var time=item.optime;
                //alert(time);
                //状态颜色
                /*var result="";
                var check="";
                if(item.status==1)
                {
                    result="<td class='td' ><span style='color: #f0ad4e;'>"+isnull(status[item.status-1])+"</span></td>";
                    check= "<td class='td'><span  style='color: green;cursor: pointer'  onclick='ToCheck("+item.id+")'>审核";
                }
                else if(item.status==2)
                {
                    result="<td class='td'><span style='color:red;'>"+isnull(status[item.status-1])+"</span></td>";
                    check= "<td class='td'><span  style='color: gray;' >审核";
                }

                else if(item.status==3)
                {
                    result="<td class='td'><span style='color: #14d1ff;'>"+isnull(status[item.status-1])+"</span></td>";
                    check= "<td class='td'><span  style='color: gray;' >审核";
                }
                 */

                $('#kqtable').append
                (
                    "<tr class='tr'>"+
                    "<td class='td'><a >"+(index+1)+"</a></td>"+
                    "<td class='td'>"+isnull(item.username)+"</td>"+
                    "<td class='td'>"+isnull(item.op)+"</td>"+
                    "<td class='td'>"+time +"</td>"+
                    "<td class='td'> <span style='color:green;cursor: pointer;'  onclick='ToDetail("+item.id+")'>删除</span></td>" +
                    "</tr>"
                )
            });
        }
    });
}

function isnull(str)
{
    var isnull = '';
    if (str == null || str == '' || str == 'null'||str == undefined) {
        return isnull;
    } else {
        return str;
    }
}

function ToDetail(id)
{
    if(window.confirm('你确定要删除吗？'))
    {
        $.ajax({
            url: 'delSystemLogByID',
            type: "post",
            dataType: "json",
            data: {
                "id":  id
            },
            success: function (data)
            {
                alert("删除成功");
                Search("",1);
            }
        });
        return true;
    }else
    {
        //alert("取消");
        return false;
    }


}

function ToCheck(id)
{
    window.location.href=$('#basePath').val()+'xinxi_detal?id='+id+"&mode="+2;
}