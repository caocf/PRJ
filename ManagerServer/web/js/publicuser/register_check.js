var addtype;
var spr=-1;//审批人id
$(document).ready(function(){
    $("#user_li").addClass("active");
    $("#register_li").addClass("active");
    //getspdetal();
    addtype=$("#type").val();
    if(addtype==1||addtype==3){
        getspdetal('ShipByID');
    }else if(addtype==2||addtype==4){
        getspdetalc1('CompanyByID');
    }
})
/**
 * 获取假单详情
 */
function getspdetal(actionname){
    $.ajax({
        url : actionname,
        type : "post",
        dataType : "json",
        data : {
            'id':$("#kqid").val()
        },
        success : function(data) {
            //console.log(JSON.stringify(data));
            var status;
            if(addtype==1||addtype==3){
                status=data.shipstatus.status;
                $("#cmh").text(data.shipname);
                $("#cbdjh").text(data.regnumber);
                getcbzs();
            }else{
                status=data.statusEN.status;
                $("#gsyyzzh").text(data.regnumber);
                getqyzs();
            }
            $("#spzt").text(status);
            $("#yhm").text(data.publicUserEN.username);
            $("#sjh").text(data.publicUserEN.tel);
            $("#yhlx").text(data.publicUserEN.usertype.typename);
            $("#szcs").text(data.publicUserEN.region.regionname);
        }
    })
}
function getspdetalc1(actionname){
    $.ajax({
        url : actionname,
        type : "post",
        dataType : "json",
        data : {
            'id':$("#kqid").val()
        },
        success : function(data) {
            //console.log(JSON.stringify(data));
            var status;
            if(addtype==1||addtype==3){
                status=data.shipstatus.status;
                $("#cmh").text(data.shipname);
                $("#cbdjh").text(data.regnumber);
                getcbzs();
            }else{
                status=data.statusEN.status;
                $("#gsyyzzh").text(data.regnumber);
                getqyzs();
            }
            $("#spzt").text(status);
            $("#yhm").text(data.userEN.username);
            $("#sjh").text(data.userEN.tel);
            $("#yhlx").text(data.userEN.usertype.typename);
            $("#szcs").text(data.userEN.region.regionname);
        }
    })
}



//获取企业证书
function getqyzs(){
    $.ajax({
        url : 'CompanyCerttByID',
        type : "post",
        dataType : "json",
        data : {
            'id':$("#kqid").val()
        },
        success : function(data) {
            var imgname=data.dir.split('/')[(data.dir.split('/').length-1)];

        }
    })
}
//获取船舶证书
function getcbzs(){
    $.ajax({
        url : 'ShipCerttByID',
        type : "post",
        dataType : "json",
        data : {
            'id':$("#kqid").val()
        },
        success : function(data) {
            console.log(JSON.stringify(data));
            var conimgname=data.con.split('/')[(data.con.split('/').length-1)];
            var gppcimgname=data.gppc.split('/')[(data.gppc.split('/').length-1)];

        }
    })
}

//预览证书
function showzsimg(imgsrc){
    $("#zsylimg").attr('src','http://120.55.193.1:8080/GH/'+imgsrc);
}

//提交审批
function checksp(){
    var actionname='CompanyCheckByID';
    if(addtype==1||addtype==3){
        var actionname='ShipCheckByID';
    }
    $.ajax({
        url : actionname,
        type : "post",
        dataType : "json",
        data : {
            'id':$("#kqid").val(),
            'mark':$("#shenheword").val(),
            'status':$('input:radio[name="radiobutton"]:checked').val()
        },
        success : function(data) {
            alert('审批成功');
            //window.location.href=$("#basePath").val()+'HomePage';
            history.back(-1);
        }
        })

}