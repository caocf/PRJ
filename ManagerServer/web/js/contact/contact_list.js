$(document).ready(function () {
    $(".txlli").css({'background-color': '#0186ed', 'color': 'white'});
    $("#clearbtn").bind("click", function () {
        $("#clearbtn").hide();
        $("#iptusername").val('');
    })
    $("#iptusername").bind('input propertychange', function () {
        if ($('#iptusername').val() != '' && $('#iptusername').val() != null) {
            $("#clearbtn").show();
        }
    });
    getbm();
})

var addmltreeNodes=[];
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
    },
    callback: {
        onClick: zTreeOnClick
    }
};
//创建部门树
function bmtreemake(){
    var treeObj = $("#depttree");
    $.fn.zTree.init(treeObj, konwledgesetting, addmltreeNodes);
}
//部门树样式方法
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
//获取部门
function getbm(){
    $.ajax({
        url: 'ItemsByPid',
        type: "post",
        dataType: "json",
        data: {
            'pid': 'B4EB412FAFF44223A53456711CA3D5EA'
        },
        success: function (data) {
            //console.log(JSON.stringify(data));
            $(data.obj).each(function(index,item){
                var imgstr="<i class='fa fa-group'></i>";
                var isparent=false;
                if(item.zzjglb==1){
                    imgstr="<i class='fa fa-home'></i>";
                    isparent=true;
                }
                var node={
                    "id":item.id,
                    "describe":item.zzjgmc,
                    "nodename":item.zzjgmc,
                    "name":"<div class='turnStructurediv' style='margin-right: 75px;' ><span >"+imgstr+"&nbsp;<span onclick=\"\">"+item.zzjgmc+"</span></span></div>",
                    "pId":0,
                    "isfristclick":true,
                    "isparent":isparent
                };
                addmltreeNodes.push(node);
            })
            bmtreemake();
        }
    })
}

//节点点击方法
function zTreeOnClick(event, treeId, treeNode) {
    if(treeNode.isparent){
        if(treeNode.isfristclick) {
            treeNode.isfristclick=false;
            $.ajax({
                url: "ItemsByPid",
                dataType: "json",
                data: {
                    'pid': treeNode.id
                },
                type: "post",
                success: function (data) {
                    console.log(JSON.stringify(data));
                    var treeObj = $.fn.zTree.getZTreeObj("depttree");
                    $(data.obj).each(function (index, item) {
                        var imgstr="<i class='fa fa-group'></i>";
                        var isparent=false;
                        var zzlb=1;
                        if(item.zzjglb==1){
                            imgstr="<i class='fa fa-home'></i>";
                            isparent=true;
                            zzlb=2;
                        }
                        var node = {
                            "id": item.id,
                            "describe": item.zzjgmc,
                            "nodename": item.zzjgmc,
                            "name": "<div class='turnStructurediv' style='margin-right: 75px;' ><span>"+imgstr+"&nbsp;<span onclick=\"\">" + item.zzjgmc + "</span></span></div>",
                            "isfristclick":true,
                            "isparent":isparent,
                           //"lb":zzlb
                        };
                        treeObj.addNodes(treeNode, node);
                    })


                },
                error: function (xOption, status) {
                }
            });

        }
    };

    //请求user
    var zzlb=1;
    if(treeNode.isparent) zzlb=2;
    showdeptuser(treeNode.id,zzlb);
}

function isnull(str) {
    var isnull = '';
    if (str == null || str == '' || str == 'null' || str == undefined) {
        return isnull;
    } else {
        return str;
    }
}

function TableIsNull() {
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}

function inittree() {
    var node;
    var url = "alldept";
    $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                nodes.length = 0;
                var tb = data.records.data;
                for (var i in tb) {
                    var obj = tb[i];
                    node = {
                        "id": obj.id,
                        "name": obj.zzjgmc,
                        "pid": obj.sjzzjg,
                        "type": obj.zzjglb,
                        "title": obj.zzjgmc,
                        "click": "showdeptuser('" + obj.id + "'," + obj.isleaf + ")"
                    };
                    nodes.push(node);
                }
                treemake();
            }
        }
    );
}

function treemake() {
    zTreeMember = $.fn.zTree.init($("#depttree"), treesetting, nodes);
}

function showdeptuser(id,isleaf) {
    $("#deptid").val(id);
    $("#isleaf").val(isleaf);
    $('#iptusername').val("");
    Search(1);
}

function Search(page) {
    page = parseInt(page);
    var deptid = $('#deptid').val();
    var isleaf = $('#isleaf').val();
    var username = $('#iptusername').val();
    $.ajax({
        url: 'deptuser',
        type: "post",
        dataType: "json",
        data: {
            "username": username,
            "deptid": deptid,
            "isleaf": isleaf,
            "page": page
        },
        success: function (data) {
            $("#bootpagediv").show();
            $("#nulltablediv").hide();
            var obj = data.obj;
            $(".tr").remove();
            if (obj != null && obj.length > 0) {
                pagingmake("", 'Search', page, data.pages);
                $(obj).each(function (index, item) {
                    $('#txltable').append
                    (
                        "<tr class='tr'>" +
                        "<td class='td'>" + (index + 1) + "</td>" +
                        "<td class='td'>" + isnull(item.xm) + "</td>" +
                        "<td class='td'>" + isnull(item.bmmc) + "</td>" +
                        "<td class='td'>" + ""/*isnull(item.zw)*/ + "</td>" +
                        "<td class='td'>" + isnull(item.sjhm) + "</td>" +
                        //"<td class='td'><span style='cursor:pointer;' onclick='GoShow(\"" + item.id + "\")'>详情</span><span style='cursor:pointer;padding-left: 10px' onclick='GoUpdate(\"" + item.id + "\")'>编辑</span><span style='cursor:pointer;padding-left: 10px' onclick='GoDelete(\"" + item.id + "\")'>删除</span></td>" +
                        "<td class='td'><span style='cursor:pointer;color: #0067ac' onclick='GoShow(\"" + item.id + "\")'>详情</span></td>" +
                        "</tr>"
                    )
                });
            } else {
                TableIsNull();
            }
        }
    });
}

function GoShow(id) {
    window.location.href = $('#basePath').val() + 'contact_detail?id=' + id;
}

function GoUpdate(id) {
    window.location.href = $('#basePath').val() + 'contact_update?id=' + id;
}

