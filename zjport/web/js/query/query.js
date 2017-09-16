$(document).ready(function () {
    $("#query").addClass("active");
    $("#query_li").addClass("active");
    showStructure();
    //turnStructure(1,"法律法规");
})
//目录树创建
function treemake() {
    var treeObj = $("#konwledgeTree");
    $.fn.zTree.init(treeObj, konwledgesetting, listtreeNodes);
    //treeObj.hover(function () {
    //  if (!treeObj.hasClass("showIcon")) {
    //    treeObj.addClass("showIcon");
    //  }
    //}
    //    , function() {
    //  treeObj.removeClass("showIcon");
    //}
    //);
    $(".fa-folder:eq(0)").parent().click();

}

var listtreeNodes = [];//目录树nodes
//查询目录树信息
function showStructure() {

    $.ajax({
        url: "../dataquery/sorts",
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.resultcode == -1) {
                BackToLoginPage();
            } else if (data.resultcode == 0) {
                var list = data.records.data;
                if (list == "") {
                    alert('目录为空');
                    return
                } else {
                    listtreeNodes = [];
                    for (var int = 0; int < list.length; int++) {
                        var node;
                        var istr = "<i class='fa fa-folder'></i>";
                        if (list[int].pid != -1) {
                            istr = "<i class='fa fa-file-o'></i>";
                        }
                        node = {
                            "id": list[int].id,
                            "nodename": list[int].lxmc,
                            "name": "<div onclick=selectlistnode(" + list[int].id + ",'" + list[int].lxmc + "'," + list[int].pid + ")><span>" + istr + "&nbsp;" + list[int].lxmc + "</span></div>",
                            "pId": list[int].pid
                        };
                        listtreeNodes.push(node);
                    }
                    treemake();
                }
            } else {
                alert(data.result.resultdesc);
                return
            }

        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
}

/**
 * 目录树点击企业查询
 * @param id :节点id
 * @param name：节点名称
 * @param pid：根节点id
 */
function selectlistnode(id, name, pid) {
    $("#search").val('');
    if (pid != -1) {
        $("#queryname").text(name);
        queryid = id;
    } else {
        var treeObj = $.fn.zTree.getZTreeObj("konwledgeTree");
        var nodes = treeObj.getNodesByParam("pId", id, null);//查找子节点
        if (nodes.length == 0) {
            alert('该节点下无子节点可查询');
            return
        }
        queryid = nodes[0].id;
        $("#queryname").text(nodes[0].nodename);
    }
    querylistshow('../dataquery/single', 1);
}
var queryid = '';//查询id
var cxtjsz = [];
/**
 * 查询列表展示
 * @param actionname：接口名
 * @param page：页码
 */
function querylistshow(actionname, page) {
    var datastr = {};
    datastr['page'] = page;
    datastr['rows'] = 10;
    datastr['id'] = queryid;
    if (cxtjsz == '' || cxtjsz == null) {
    }
    else {
            //var condition={};
            //condition.name=cxtjsz[0].code;
            //condition.value=$("#search").val();
        datastr['code']=cxtjsz[0].code;
        datastr['value']=$("#search").val();
        //datastr['condition.name'] = cxtjsz[0].code;
        //datastr['condition.value'] = $("#search").val();
    }
    console.log(datastr);
    $.ajax({
        url: actionname,
        type: 'post',
        dataType: 'json',
        data: datastr,
        success: function (data) {
            cxtjsz = [];
            cxtjsz = data.map.cxtj;
            $("#search").attr('placeholder', "请输入" + cxtjsz[0].name);
            $(".addTr").empty();
            $(".addTr").remove();
            if (data.resultcode == -1) {
                BackToLoginPage();
            } else if (data.resultcode == 0) {
                var datalist = data.records.data;
                var btlist = data.map.cxzs;
                $("#pagedetal").empty();
                $("#nulltablediv").hide();
                $("#txltable").empty();
                $("#pagedetal").text(
                    "当前页" + datalist.length + "条记录 共" + data.records.total + "条记录，每页" + data.records.rows + "条"
                );
                pagingmake(actionname, 'querylistshow', page, data.records.pages);
                if (datalist == "") {
                    TableIsNull();
                } else {
                    appendToTable(datalist, btlist);
                }
            } else {
                alert(data.result.resultdesc);
            }

        }/*,
         complete : function() {
         CloseLoadingDiv();
         }*/
    });
}
/**
 * 创建列表
 * @param datalist：数据集合
 * @param btlist：表头集合
 */
function appendToTable(datalist, btlist) {
    var bttr = $("<tr></tr>");
    for (var i = 0; i < btlist.length; i++) {
        bttr.append(
            "<th class='center'>" + isnull(btlist[i].name, '--', 1) + "</th>"
        );
    }
    bttr.append(
        "<th class='center'>操作</th>"
    );
    $("#txltable").append(bttr);
    for (var i = 0; i < datalist.length; i++) {
        var datatr = $("<tr></tr>");
        for (var j = 0; j < datalist[i].length; j++) {

            if (j == (datalist[i].length - 1)) {
                datatr.append("<td class='center'><span style='color: #0063dc;cursor: pointer;' onclick=\"seexq('" + datalist[i][j] + "')\">详情</span></td>");
            } else {
                datatr.append("<td class='center'>" + isnull(datalist[i][j], '--', 1) + "</td>");
            }
        }
        $("#txltable").append(datatr);
    }
}

/**
 * 综合查询详情弹窗
 * @param key:主键值
 */
function seexq(key) {
    var datastr = {};
    datastr['key'] = key;
    datastr['id'] = queryid;
    $.ajax({
        url: '../dataquery/detail',
        type: 'post',
        dataType: 'json',
        data: datastr,
        success: function (data) {
            $('#zhcxxq').modal('show');
            $('.zhcx_table').empty();
            var namelist = data.map.column;
            var valuelist = data.map.data;
            for (var i = 0; i < namelist.length; i++) {
                if (i % 3 == 0) {
                    $('.zhcx_table').append(
                        "<tr></tr>"
                    );
                }
                $('.zhcx_table').find('tr:last-child').append(
                    "<td class='zhcx_left_label'>" + isnull(namelist[i].name, '--', 0) + "</td>" +
                    "<td>" + isnull(valuelist[i], '--', 0) + "</td>"
                );
            }
        }
    })
}
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

function TableIsNull() {
    $(".bootpagediv").hide();
    $("#nulltablediv").show();
}

function giveId(id) {
    $("#baseId").val(id);
}

function deleteIt() {
    var id = $("#baseId").val();
    window.location.href = "deleteBase?id=" + id;
}
/*var listtreeNodes =[
 { id:1, pId:0, name: "<span><i class='fa fa-folder'></i>&nbsp;法律法规</span><div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash'></i> </div> <div class='qzidiv' > <i onclick='addmltreemake()' data-toggle='modal' data-target='#addmlModal'class='fa fa-pencil-square-o'></i> </div>" , open:true},
 { id:2, pId:0, name: "<span><i class='fa fa-folder'></i>&nbsp;各级文件</span><div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash'></i> </div> <div class='qzidiv' > <i onclick='addmltreemake()' data-toggle='modal' data-target='#addmlModal'class='fa fa-pencil-square-o'></i> </div>" , open:true},
 { id:3, pId:0, name: "<span><i class='fa fa-folder'></i>&nbsp;标准规范</span><div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash'></i> </div> <div class='qzidiv' > <i onclick='addmltreemake()' data-toggle='modal' data-target='#addmlModal'class='fa fa-pencil-square-o'></i> </div>" , open:true},
 { id:4, pId:1, name: "<span><i class='fa fa-folder'></i>&nbsp;海事安全法规</span><div class='qzidiv' style='margin-right: 10px;'> <i class='fa fa-trash'></i> </div> <div class='qzidiv' > <i onclick='addmltreemake()' data-toggle='modal' data-target='#addmlModal'class='fa fa-pencil-square-o'></i> </div>" , open:true},
 ];*/
/*var addmltreeNodes =[
 { id:0, pId:0, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;知识库</span>" , open:true},
 { id:1, pId:0, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;法律法规</span>" , open:true},
 { id:2, pId:0, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;各级文件</span>" , open:true},
 { id:3, pId:0, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;标准规范</span>" , open:true},
 { id:4, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
 { id:5, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
 { id:6, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
 { id:7, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
 { id:8, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true},
 { id:9, pId:1, name: "<span onclick='addsjml(this)'><i class='fa fa-folder'></i>&nbsp;海事安全法规</span>" , open:true}
 ];*/
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
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
        switchObj.before(spaceStr);
    }
    $(".ztree").find('a').removeAttr('title');
}


//全选
function choose() {
    var objs = document.getElementsByName("file");

    if (document.getElementById('allSelect').checked) {
        for (var i = 0; i < objs.length; i++) {
            if (objs[i].disabled == false) {
                objs[i].checked = true;
            }
        }
    }
    else {
        for (var i = 0; i < objs.length; i++) {
            if (objs[i].disabled == false) {
                objs[i].checked = false;
            }
        }
    }
}

//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度
function isnull(str, isnullstr, islong) {
    var isnull = isnullstr;
    if (str == null || str == '' || str == 'null' || str == undefined) {
        return isnull;
    } else {
        if (islong == 1) {
            if (str.length >= 20) {
                return "<abbr title='" + str + "'>" + str.substr(0, 20) + "</abbr>";
            }
        }
        //将数据库时间long转为时间格式
        if (!isNaN(str)) {
            if (str > 65536) {
                str = getSmpFormatDateByLong(parseInt(str), true);
            }
        }
        return str;
    }
}


Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
/**
 *转换日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDate(date, isFull) {
    var pattern = "";
    if (isFull == true || isFull == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    } else {
        pattern = "yyyy-MM-dd";
    }
    return getFormatDate(date, pattern);
}
/**
 *转换当前日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */

function getSmpFormatNowDate(isFull) {
    return getSmpFormatDate(new Date(), isFull);
}
/**
 *转换long值为日期字符串
 * @param l long值
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */

function getSmpFormatDateByLong(l, isFull) {
    return getSmpFormatDate(new Date(l), isFull);
}
/**
 *转换long值为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */

function getFormatDateByLong(l, pattern) {
    return getFormatDate(new Date(l), pattern);
}
/**
 *转换日期对象为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDate(date, pattern) {
    if (date == undefined) {
        date = new Date();
    }
    if (pattern == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    }
    return date.format(pattern);
}