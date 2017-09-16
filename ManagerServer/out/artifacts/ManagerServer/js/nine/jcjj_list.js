var listtype = 1;//列表类型

$(document).ready(function () {
    $("#nine_li").addClass("active");
    $("#jcjj_li").addClass("active");
    showTabledata('AISList',1);
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
            $("#userlist_table").empty();
            $("#userlist_table").append(
                "<tr style='background-color: rgb(240,245,248);'>" +
                "<th style='width:50px;'>编号</th>" +
                "<th>船名号</th>" +
                "<th>九位码</th>" +
                "<th>AIS证书</th>" +
                "<th>修改时间</th>" +
                "<th>状态</th>" +
                "<th>操作</th>" +
                "</tr>"
            );
            $(data.list).each(function(index,item){
                var czword='查看';
                var sendtype=2
                var statuscolor;
                switch (parseInt(item.status.id)){
                    case 1:
                        statuscolor='#f15c08';
                        czword='审批';
                        sendtype=1;
                        break;
                    case 2:
                        statuscolor='green';
                        break;
                    case 3:
                        statuscolor='red';
                        break;
                }
                $("#userlist_table").append(
                    "<tr>" +
                    "<td>"+((index+1)+parseInt(page-1)*10)+"</td>"+
                    "<td>"+item.shipnameid+"</td>"+
                    "<td>"+item.bh+"</td>"+
                    "<td>"+isnull(item.dir,'--',0)+"</td>"+
                    "<td>"+isnull(item.committime,'--',0)+"</td>"+
                    "<td><span style='color:"+statuscolor+"'>"+item.status.status+"</span></td>"+
                    "<td><span class='clickword' onclick=\"gotojjcjcheck("+sendtype+",'"+item.shipnameid+"')\">"+czword+"</span></td>"+
                    "</tr>"
                );
            })
        }
    })
}

//跳转接口
function gotojjcjcheck(type,id){
    $.ajax({
        url: 'jcjjcheck_list',
        type: "post",
        dataType: "json",
        data: {
            'id': id
        },
        success : function (){
            window.location.href=$("#basePath").val()+'jcjjcheck_list1?type='+type;
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