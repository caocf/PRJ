function selectUser(id) {
    $.ajax({
        url:"showUserCenterDetail",
        type:'post',
        dataType:'json',
        data : {
            'id':id
        },
        success:function(data){
            if(data.resultcode==-1){
                BackToLoginPage();
            }else if(data.resultcode==0){
                var list=data.obj.records.data;
                if(list==""){
                }else{
                    showUserDetail(list);
                }
            }else{
                alert(data.result.resultdesc);
            }
        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
    $('#userimgdiv').click(function(){
        $('#userimg').click();
    })
    $('#userimg').change(function(){
        if($('#userimg').val()==''||$('#userimg').val()==null){
            ('#userimgdiv').css('background-image',"url('../image/img_photo.png')");
        }else{
            var file = $("#userimg")[0].files[0];
            var read = new FileReader();
            read.readAsDataURL(file);
            read.onload = function (e) {
                $('#userimgdiv').css('background-image',"url('"+this.result+"')");
            }
        }
    })
}

function showUserDetail(list) {
    var array = list[0];
    $("#account_info").text(array[12]);
    /*$("#password_info").text(array[13]);*/
    $("#js_info").text(array[9]);
    $("#user_js").val(array[8]);

    $("#name_info").text(array[0]);
    $("#position_info").text(array[1]);
    $("#org_info").text(array[2]);
    $("#dept_info").text(array[3]);
    $("#office_phone").text(array[4]);
    $("#phone").text(array[5]);
    $("#virtual").text(array[6]);
    $("#email").text(array[7]);

    $("#law_code").text(array[10]);
    $("#location_info").text(array[14]);

    $("#lawCode").val(array[10]);
    $("#location").val(array[14]);
}

function treemake(){
    var treeObj = $("#addmlTree");
    $.fn.zTree.init(treeObj, konwledgesetting, listtreeNodes);

}
function addDiyDom(treeId, treeNode) {
    var spaceWidth = 7;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 1) {
        var spaceStr = "<span style='display: inline-block;'></span>";
        switchObj.before(spaceStr);
    }
    if (treeNode.level > 0) {
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
        switchObj.before(spaceStr);
    }
    $(".ztree").find('a').removeAttr('title');
}
var listtreeNodes = [];
var konwledgesetting = {
    view: {
        nameIsHTML: true,
        showLine: false,
        showIcon: false,
        selectedMulti: false,
        dblClickExpand: false,
        addDiyDom: addDiyDom
    },
    data: {
        simpleData: {
            enable: true
        }
    }
};
function selectQx() {
    var js = $("#user_js").val();
    $.ajax({
        type : "POST",
        url : "selectQx",
        datatype : "json",
        data : {
            'js':js
        },
        success : function(data) {
            listtreeNodes=[];
            var list=data.obj.records.data;
            for(i=0;i<list.length;i++){
                var node={
                    "id":list[i][1],
                    "describe":list[i][0],
                    "nodename":list[i][0],
                    "name":"<div class='turnStructurediv' style='margin-right: 75px;' ><span ><i class='fa fa-key'></i>&nbsp;"+list[i][0]+"</span></div>",
                    "pId":list[i][2]
                };
                listtreeNodes.push(node);
            }
            treemake();
        },
        error : function() {
            alert("出错！！");
        }
    });
}

function send() {
    var id= $("#userId").val();

    $.ajax({
        type : "POST",
        url : "userInfoSubmit",
        datatype : "json",
        data : $("#userForm").serializeArray(),
        success : function(data) {

            $('#editModal').modal('hide');
            selectUser(id);
            alert("成功！");
        },
        error : function() {
            alert("保存出错！！");
        }
    });
}