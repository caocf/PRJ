$(document).ready(function()
{
    $("#commenting_li").addClass("active");
    $("#xinxiquery_li").addClass("active");

    Search(1);
})

function option(data,id,obj)
{
    var tx=$(obj).text();
    $("#"+id).text(tx);
    $("#"+id).attr('title',data);
}

function Search(page)
{
    var region=$('#lbnamespan').attr('title');
    var type=$('#type').attr('title');
    var status=$('#status').attr('title');
    var title=$('#listkey').val();
    var date1=$('#beginTime').val();
    var date2=$('#endTime').val();

    $.ajax({
        url:'NoticeListByItems',
        type : "post",
        dataType : "json",
        data:
        {
            "region":region,
            "type":type,
            "title":title,
            "status":status,
            "date1":date1,
            "date2":date2,
            "page":page,
        },
        success : function(data)
        {
            var list=data.s1;
            var types=new Array('港航通知','行政通告','航行警告');
            var regions=new Array('浙江省港航管理局','杭州市港航管理局','嘉兴市港航局','湖州市港航局','舟山市港航局');
            var status=new Array('待审核','不通过','已发布');

            $(".tr").remove();
            pagingmake("", 'Search', page, data.resultcode);
            $(list).each(function (index, item)
            {
                var time=item.updatetime;
                //状态颜色
                var result="";
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


                $('#kqtable').append
                (
                    "<tr class='tr'>"+
                    "<td class='td'><a >"+(index+1)+"</a></td>"+
                    "<td class='td'><a class='uniquenews' href='xinxi_detal?id="+item.id+"'>"+isnull(item.title)+"</a></td>"+
                    "<td class='td'>"+isnull(types[item.newstype-4])+"</td>"+
                    "<td class='td'>"+isnull(regions[item.region-1])+"</td>"+
                    "<td class='td'>"+time.substring(0,10)+"</td>"+
                    result+
                    check +
                    "</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:green;cursor: pointer;'  onclick='ToDetail("+item.id+")'>查看</span></td>" +
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
    window.location.href=$('#basePath').val()+'xinxi_detal?id='+id+"&mode="+1;
}

function ToCheck(id)
{
    window.location.href=$('#basePath').val()+'xinxi_detal?id='+id+"&mode="+2;
}