/**
 * Created by TWQ on 2016/8/3.
 */

$(document).ready(function(){
    $("#information").addClass("active");
    $("#inform-send").addClass("active");
    showWeb("showWeblist");
    showApprovalUser();

    $('#attachment').change(function(){
        var fileList = this.files;
        var nametext='';
        for( var i = 0 ; i < fileList.length ; i++ ){
            nametext += fileList[i].name+";";
        }
        $('#filenamespan').text(nametext);
    });
});

function showWeb(actionName) {
    $.ajax({
        url:actionName,
        type:'post',
        dataType:'json',
        /*data:{
         'page':selectedPage,
         'rows':10,
         'type':type
         },*/
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                    //WebIsNull();
                }else{
                    appendToWebList(list);
                }
            }else{
                alert(data.result.resultdesc);
            }

        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
}

function WebIsNull() {
    newLi = $("<tr class='addTr'><td colspan='9' align='center'>暂无相关数据</td></tr>");
    $("#web_list").append(newLi);

}

function appendToWebList(list) {
    var newLi = '';
    newLi = $("<ul class='dropdown-menu col-sm-10' style='left:110px;' role='menu' aria-labelledby='dropdownMenu1'>");
    for(var i=0;i<list.length;i++){
        //信息发布网站名
        newLi.append($("<li role='presentation'><a role='menuitem' tabindex='-1' class='onescheckxl' onclick=addname(this,1) id='web_"+list[i].stWebNameId+"'>"+list[i].stName+"</a></li>"));
    }
    newLi.append($("</ul>"));

    $("#web_list").append(newLi);
}

function appendToColumnList(list) {
    var newLi = '';
    newLi = $("<ul class='dropdown-menu col-sm-10' style='left:110px;' role='menu' aria-labelledby='dropdownMenu2' id='lvl2ul'>");
    for(var i=0;i<list.length;i++){
        //信息发布网站栏目名
        newLi.append($("<li role='presentation'><a role='menuitem' tabindex='-1' class='onescheckxl' onclick=addname(this,2) id='webcolumn_"+list[i].stWebColumnId+"'>"+list[i].stWebColumnName+"</a></li>"));
    }
    newLi.append($("</ul>"));

    $("#web_column").append(newLi);
}


function showApprovalUser() {
    $.ajax({
        url:"showApprovalUser",
        type:'post',
        dataType:'json',
        /*data:{
         'page':selectedPage,
         'rows':10,
         'type':type
         },*/
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                    //WebIsNull();
                }else{
                    appendApprovalUser(list);
                }
            }else{
                alert(data.result.resultdesc);
            }

        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
}

function appendApprovalUser(list) {
    //alert(list[0][0]);
    var newOption = "";
    for(var i=0;i<list.length;i++){
        //信息发布网站栏目名
        newOption+="<option name='choose' value='"+list[i][0]+"'>"+list[i][1]+"</option>";
    }
    $("#disabledSelect").append(newOption);
}

/*  富文本编辑器  */
$(function () {
    // Replace the <textarea id="editor1"> with a CKEditor
    // instance, using default configuration.
    CKEDITOR.replace('editor1');

});

/**
 * 新增标签div
 * @param a ：下拉a标签对象
 * @param type：标签类型，1：对象 2：章节
 * @param id  webNameId或者webColumnId  发布网站ID
 */
function addname(a,type) {
    var menuid;
    var menutsid;
    if(type==1){
        menuid='dropdownMenu1';
        menutsid='dxbt';
        $.ajax({
            url:"showWebColumnlist",
            type:'post',
            dataType:'json',
            data:{
             'webId':$(a).attr('id').split('_')[1]
             },
            /*beforeSend : function() {
             ShowLoadingDiv();// 获取数据之前显示loading图标。
             },*/
            success:function(data){
                if(data.resultcode==-1){
                    BackToLoginPage();
                }else if(data.resultcode==0){
                    var list=data.records.data;
                    if(list==""){
                        //WebIsNull();
                    }else{
                        appendToColumnList(list);
                    }
                }else{
                    alert(data.result.resultdesc);
                }

            }/*,
             complete : function() {
             CloseLoadingDiv();
             }*/
        });
        $("#webName").val($(a).attr('id').split('_')[1]);
    }
    if(type==2){
        menuid='dropdownMenu2';
        menutsid='zjbt';
        $("#webColumn").val($(a).attr('id').split('_')[1]);
    }
    var aid=$(a).attr('id');
    $('#'+menutsid).hide();
    $(a).parent().siblings().children('a').attr('onclick','addname(this,'+type+')');
    $(a).parent().siblings().children('a').removeClass('checkedxl');
    $(a).parent().siblings().children('a').addClass('onescheckxl');
    $(a).removeAttr('onclick');
    $(a).removeClass('onescheckxl');
    $(a).addClass('checkedxl');
    $("#"+menuid).children('div').remove();
    var width = $(a).text().length * 20 + 100;
    $("#"+menuid).append(
        "<div onclick=closexl('"+menuid+"') style='height:27px;float: left;margin-left:10px;width:" + width + "px;color: #333;line-height: 27px;padding:0 5px;background-color: rgb(250,250,250);border: solid 1px #ccc;'>" +
        $(a).text() + "<span class='removebt' style='font-size: 20px;font-weight:700;line-height: 27px;float: right;cursor: pointer;' onclick=removename(this,'"+aid+"','"+menutsid+"',"+type+")>×</span>" +
        "</div>"
    );
}

/**
 * 删除标签div
 * @param a：对应删除按钮对象
 * @param b：对应下拉列表a标签id
 * @param c：对应标题id
 * @param type：类型1：对象 2：章节
 */
function removename(a,b,c,type){
    $('#'+b).attr('onclick','addname(this,'+type+')');
    $('#'+b).removeClass('checkedxl');
    $('#'+b).addClass('onescheckxl');
    if($(a).parent().siblings().size()==1)
        $('#'+c).show();
    $(a).parent().remove();
    if(type==1){
        $("#lvl2ul").remove();
        $("#dropdownMenu2").find('div').remove();
        $("#zjbt").show();
    }
}

function closexl(c){
    $("#"+c).dropdown('toggle');
}

//清空文件
function cleanfile(){
    $('#uploadFile').val('');
    $('#imgname').empty();
}

//点击发布判断是否为空并提交
function send(){
    if($("#webName").val() == "") {
        alert("发布网站不能为空！");
    } else if($("#webColumn").val() == "") {
        alert("网站类目不能为空！");
    } else if($("#title").val() == "") {
        alert("信息标题不能为空！");
    } else if($("#disabledSelect").val() == "0") {
        alert("请选择审批人！");
    } else {
        $("#internetForm").submit();
    }
}

function addWbColumn() {
    $.ajax({
        url:"showWebColumnlist",
        type:'post',
        dataType:'json',
        data:{
            'webId':id
        },
        /*beforeSend : function() {
         ShowLoadingDiv();// 获取数据之前显示loading图标。
         },*/
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.records.data;
                if(list==""){
                    //WebIsNull();
                }else{
                    appendToColumnList(list);
                }
            }else{
                alert(data.result.resultdesc);
            }

        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
}