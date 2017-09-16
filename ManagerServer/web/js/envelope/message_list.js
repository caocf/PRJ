$(document).ready(function () {
    $(".yjxli").css({'background-color': '#0186ed', 'color': 'white'});
    showTabledata('AdviceByTip', 1);
})
function showTabledata(actionname, page) {
    $.ajax({
        url: actionname,
        type: "post",
        dataType: "json",
        data: {
            'page': page,
            'tip': $("#listkey").val()
        },
        success: function (data) {
            console.log(JSON.stringify(data));
            pagingmake(actionname, 'showTabledata', page, data.pages);
            $("#rolelist-info").empty();
            $("#rolelist-info").append(
                "<tr style='background-color: rgb(240,245,248);'>" +
                "<th style='width:50px;'>编号</th>" +
                "<th>城市</th>" +
                "<th>联系电话</th>" +
                "<th>反馈用户</th>" +
                "<th>内容</th>" +
                "<th>反馈时间</th>" +
                "<th>操作</th>" +
                "</tr>"
            );
            $(data.list).each(function (index, item) {
                $("#rolelist-info").append(
                    "<tr>" +
                    "<td>" + ((index + 1) + parseInt(page - 1) * 10) + "</td>" +
                    "<td>" + isnull(item.city,'--',0) + "</td>" +
                    "<td>" + isnull(item.contact,'--',0) + "</td>" +
                    "<td>" + isnull(item.name,'--',0) + "</td>" +
                    "<td>" + isnull(item.cotent,'--',0) + "</td>" +
                    "<td>" + isnull(item.time,'--',0) + "</td>" +
                    "<td><a href='"+$("#basePath").val()+"message_check?id="+item.id+"'>查看</a></td>" +
                    "</tr>"
                );
            })

        }
    })
}

//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度
function isnull(str,isnullstr,islong) {
    var isnull = isnullstr;
    if (str == null || str == '' || str == 'null'||str == undefined) {
        return isnull;
    } else {
        if(islong==1){
            if(str.length>=20){
                return "<abbr title='"+str+"'>"+str.substr(0,20)+"</abbr>";
            }
        }
        return str;
    }
}