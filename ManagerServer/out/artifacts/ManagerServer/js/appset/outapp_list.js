$(document).ready(function(){
    $("#th-large_li").addClass("active");
    $("#outerApp_li").addClass("active");
    showTabledata('AppList')
})

function showTabledata(actionname) {
    $.ajax({
        url: actionname,
        type: "post",
        dataType: "json",
        //data: {
        //    'page': page,
        //    'key': $("#listkey").val(),
        //    'status': usertype,
        //    'type': listtype
        //},
        success: function (data) {
            //console.log(JSON.stringify(data));
            //pagingmake(actionname, 'showTabledata', page, data.pages);
            $("#rolelist-info").empty();
            $("#rolelist-info").append(
                "<tr style='background-color: rgb(240,245,248);'>" +
                "<th style='width:50px;'>编号</th>"+
                "<th>版本号</th>"+
                "<th>版本名</th>"+
                "<th>更新时间</th>"+
                "<th>更新日志</th>"+
                "<th>操作</th>"+
                "</tr>"
            );
            $(data.s1).each(function (index, item) {
                $("#rolelist-info").append(
                    "<tr>" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + item.versionNum + "</td>" +
                    "<td>" + item.verstionDec + "</td>" +
                    "<td>" + item.uptime + "</td>" +
                    "<td>" + item.log + "</td>" +
                    "<td>" +
                    "<span class='clickword' onclick=\"window.location.href='" + item.address + "'\">下载</span>" +
                    "</td>" +
                    "</tr>"
                );
            })

        }
    })
}