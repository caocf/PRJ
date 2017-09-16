var spr=-1;//审批人id
$(document).ready(function(){
    $("#nine_li").addClass("active");
    $("#jcjj_li").addClass("active");
    //getycbxx();
    getspdetal();
})
/**
 * 获取详情
 */
function getspdetal(){
    $.ajax({
        url : 'AISByShipNameEdit',
        type : "post",
        dataType : "json",
        data : {
            'shipname':$("#shipid").val()
        },
        success : function(data) {
            $("#tjsj").text(isnull(data.committime,'--',0));
            var statuscolor;
            switch (parseInt(data.status.id)){
                case 1:
                    statuscolor='#f15c08';
                    break;
                case 2:
                    statuscolor='green';
                    break;
                case 3:
                    statuscolor='red';
                    break;
            }
            $("#spzt").text(data.status.status);
            $("#xcmh").text(data.shipnameid);
            $("#xjwm").text(data.bh);
            $("#spzt").css('color',statuscolor);
            //console.log(data.dir);
            //$("#zsylimg").attr('src',encodeURI("http://localhost:8080/"+data.dir));
            getycbxx();
            if(data.dir==''||data.dir==null){
                $("#cbaiszs").text('暂无证书');
            }else{
                var imgname="<span style='float: left;'>"+data.dir.split('/')[(data.dir.split('/').length-1)]+"</span>";
                $("#cbaiszs").html(
                    imgname+"<form action='AISCeretByShipName'style='float: left;margin-left:10px;' method='post' id='ylAISform'>" +
                    "<input type='hidden' name='shipname' value='"+$("#shipid").val()+"' id='shipname'/>" +
                    "<span class='clickword' data-toggle='modal' data-target='#myModal' onclick=\"iml()\">预览</span>&nbsp;" +
                    "<span class='clickword' onclick=\"showzsimg()\">下载</span></form>"
                );
            }

        }
    })
}
//获取原信息
function getycbxx(){
    $.ajax({
        url : 'AISByShipName',
        type : "post",
        dataType : "json",
        data : {
            'shipname':$("#shipid").val()
        },
        success : function(data) {
            //console.log(JSON.stringify(data));
            $("#ycmh").text(data.cbmc);
            $("#yjwm").text(data.aissbh);
        }
    })
}

//下载证书
function showzsimg(type){
        $("#ylAISform").submit();
}

//提交审批
function checksp(){
    if($('input:radio[name="radiobutton"]:checked').val()==null){
        alert('请选择审批结果');
        return
    }
    $.ajax({
        url : 'AISCheck',
        type : "post",
        dataType : "json",
        data : {
            'shipname':$("#shipid").val(),
            'opinion':$("#shenheword").val(),
            'status':$('input:radio[name="radiobutton"]:checked').val()
        },
        beforeSend : function(){

        },
        success : function(data) {
            alert('审批成功');
            window.location.href=$("#basePath").val()+'jcjj_list';
        },
        error :function() {

        }
    })

}

//预览证书
function iml(evt){
    var xhr = new XMLHttpRequest() ,
        val = encodeURI('/AISFile/浙长兴货3955/Picture_11_Bamboo.jpg');
    content = "shipname="+encodeURIComponent($("#shipid").val());
    xhr.onload = function() {
        var objectURL = URL.createObjectURL(this.response),
            img = document.getElementById('zsylimg');
        img.src = objectURL;
        img.onload = function(e) {
            window.URL.revokeObjectURL(this.src);
        };
    } ;
    xhr.open("POST", "AISCeretByShipName", true);
    xhr.responseType = "blob";
    xhr.setRequestHeader("Content-Length",content.length);
    xhr.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");
    xhr.send(content);
    //xhr.open("post", val);
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