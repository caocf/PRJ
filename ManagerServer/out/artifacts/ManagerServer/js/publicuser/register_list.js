var listtype = 1;//列表类型

$(document).ready(function () {
    $("#user_li").addClass("active");
    $("#register_li").addClass("active");
    showTabledata('UsersToCheck',1);
})
function showTabledata(actionname, page) {
    $.ajax({
        url: actionname,
        type: "post",
        dataType: "json",
        data: {
            'page': page,
            'tip': $("#listkey").val(),
            'type': listtype
        },
        success: function (data) {
            pagingmake(actionname, 'showTabledata', page, data.pages);
            $("#userlist_table").empty();
            $("#userlist_table").append(
                "<tr style='background-color: rgb(240,245,248);'>" +
                "<th>编号</th>" +
                "<th>用户名</th>" +
                "<th>手机号</th>" +
                "<th>用户类型</th>" +
                "<th>审批内容</th>" +
                "<th>时间</th>" +
                "<th>状态</th>" +
                "<th>操作</th>" +
                "</tr>"
            );
            $(data.list).each(function(index,item){
                var statuscolor;
                var switchid;
                var switchstr;
                var spname;
                var id;
                if(listtype==1){
                    switchid=item[1].shipstatus.id;
                    switchstr=item[1].shipstatus.status;
                    spname=item[1].shipname;
                    id=item[1].shipid;
                }else{
                    switchid=item[1].statusEN.id;
                    switchstr=item[1].statusEN.status;
                    spname=item[1].name;
                    id=item[1].id;
                }
                var czword='查看';
                var sendtype=listtype;
                switch (parseInt(switchid)){
                    case 1:
                        statuscolor='#333';
                        czword='审批';
                        break;
                    case 2:
                        sendtype+=2;
                        statuscolor='#008000';
                        break;
                    case 3:
                        sendtype+=2;
                        statuscolor='red';
                        break;
                }
                $("#userlist_table").append(
                    "<tr>" +
                    "<td>"+((index+1)+parseInt(page-1)*10)+"</td>"+
                    "<td>"+item[0].username+"</td>"+
                    "<td>"+item[0].tel+"</td>"+
                    "<td>"+item[0].usertype.typename+"</td>"+
                    "<td>"+spname+"</td>"+
                    "<td>"+item[1].binddate+"</td>"+
                    "<td><span style='color:"+statuscolor+"'>"+switchstr+"</span></td>"+
                    "<td><span class='clickword' onclick=\"window.location.href='"+$("#basePath").val()+"register_check?type="+sendtype+"&idoftype="+id+"'\">"+czword+"</span></td>"+
                    "</tr>"
                );
            })
        }
    })
}
/**
 * 列表类型切换
 * @param type 类型
 */
function setlbtype(type) {
    listtype = type;
    if (type == 1) {
        $("#typespan").text('船户');
    } else {
        $("#typespan").text('企业');
    }
    showTabledata('UsersToCheck',1);
}