$.fn.delsj = function (no, rowno) {
    var tbodyid = $(this).attr('id');
    var pre = tbodyid + no;
    var firsttrid = pre + "0";
    var $firsttr = $("#" + firsttrid);
    var sjcnt = parseInt($firsttr.attr('sjcnt'));
    var deltrid = pre + rowno;
    var $deltr = $("#" + deltrid);


    var tdnoid = firsttrid + "no";
    var tdquestionid = firsttrid + "question";
    var tdadviseid = firsttrid + 'advise';
    var tdresultid = firsttrid + 'result';

    //如果只有一行了，则直接删除整行
    if (sjcnt == 1) {
        $firsttr.remove();
    } else {
        //如果删除的不是第一行，则直接删除deltrid
        if (rowno != 0) {//?
            $deltr.remove();
        }
        //否则需要从后面的行中获取一行顶上来
        else {
            var firsttroperid = firsttrid + 'oper';
            var firsttrposid = firsttrid + 'pos';
            var firsttrzyaid = firsttrid + 'zya';
            var firsttrdescid = firsttrid + 'desc';

            //删除第一行中的这些
            $("#" + firsttroperid).remove();
            $("#" + firsttrposid).remove();
            $("#" + firsttrzyaid).remove();
            $("#" + firsttrdescid).remove();

            $(this).find('[forii*=\'' + firsttrid + '\']').each(function () {
                //将第一行填上来
                $nexttr = $(this);

                $nexttr.children().each(function () {
                    var item = parseInt($(this).attr('item'));
                    $(this).attr('rowno', '0');
                    if (item == 1) {
                        $(this).attr('id', firsttroperid);
                        $('button', this).attr('rowno', '0');
                    }
                    if (item == 2) {
                        $(this).attr('id', firsttrposid);
                    }
                    if (item == 3) {
                        $(this).attr('id', firsttrzyaid);
                    }
                    if (item == 4) {
                        $(this).attr('id', firsttrdescid);
                    }
                });

                $("#" + tdquestionid).after($nexttr.children());
                $nexttr.remove();
                return false;
            });
        }
    }

    var rowspan = parseInt($("#" + tdnoid).attr('rowspan')) - 1;
    $("#" + tdnoid).attr('rowspan', rowspan);
    $("#" + tdquestionid).attr('rowspan', rowspan);
    $("#" + tdadviseid).attr('rowspan', rowspan);
    $("#" + tdresultid).attr('rowspan', rowspan);
    $firsttr.attr('sjcnt', sjcnt - 1);
}

//新增一个事件
$.fn.addsj = function (no) {
    var tbodyid = $(this).attr('id');
    var pre = tbodyid + no;
    var firsttrid = pre + "0";
    var $firsttr = $("#" + firsttrid);

    var tdnoid = firsttrid + "no";
    var tdquestionid = firsttrid + "question";
    var tdadviseid = firsttrid + 'advise';
    var tdresultid = firsttrid + 'result';

    var sjcnt = parseInt($firsttr.attr('sjcnt'));

    var rowno = 0;
    var addedtrid = firsttrid;
    if (sjcnt == 0) {
        rowno = 0;
        addedtrid = firsttrid;
    }
    else {
        rowno = rand(99999, 999999999);
        addedtrid = pre + rowno;
    }
    var tdoperid = addedtrid + 'oper';
    $tdoper = $('<td id="' + tdoperid + '" item=1 rowno="' + rowno + '" style="height:80px;vertical-align:middle;"><button rowno="' + rowno + '" class="btn btn-link" ' +
        'onclick="$(\'#' + tbodyid + '\').delsj(\'' + no + '\',$(this).attr(\'rowno\'));">删除</button></td>');
    var tdposid = addedtrid + 'pos';
    $tdpos = $('<td id="' + tdposid + '" item=2 rowno="' + rowno + '" ><textarea class="form-control noborder" style="" placeholder="请输入具体位置"></textarea></td>');
    var tdzyaid = addedtrid + 'zya';
    $tdzya = $('<td id="' + tdzyaid + '" item=3 rowno="' + rowno + '"  style="vertical-align:middle;"><input type="radio" name="' + tdzyaid + '" checked value="1">左岸<br><input type="radio" name="' + tdzyaid + '" value="2">右岸</td>');
    var tddescid = addedtrid + 'desc';
    $tddesc = $('<td id="' + tddescid + '" item=4 rowno="' + rowno + '" ><textarea class="form-control noborder" style="" placeholder="请输入描述"></textarea></td>');


    //如果一个事件都没,则添加到tdquestion后
    if (sjcnt == 0) {
        $("#" + tdquestionid).after($tdoper);
        $tdoper.after($tdpos);
        $tdpos.after($tdzya);
        $tdzya.after($tddesc);

    } else {//如果有事件 ，则新起一行
        $newtr = $('<tr id="' + addedtrid + '" for="' + firsttrid + '"  forii="' + firsttrid + '"/>');
        $newtr.append($tdoper);
        $newtr.append($tdpos);
        $newtr.append($tdzya);
        $newtr.append($tddesc);


        $("#" + tbodyid).find('[for*=\'' + firsttrid + '\']:last').after($newtr);

        var rowspan = parseInt($("#" + tdnoid).attr('rowspan')) + 1;
        $("#" + tdnoid).attr('rowspan', rowspan);
        $("#" + tdquestionid).attr('rowspan', rowspan);
        $("#" + tdadviseid).attr('rowspan', rowspan);
        $("#" + tdresultid).attr('rowspan', rowspan);
    }

    $firsttr.attr('sjcnt', sjcnt + 1);
}

$.fn.newtr = function (no, question) {
    var tbodyid = $(this).attr('id');
    var pre = tbodyid + no;
    var trid = pre + "0";
    var $tr = $("#" + trid);

    //如果该行不存在，则新增一行，并添加序号巡查问题，处理意见与处理结果
    if ($tr.length <= 0) {
        $tr = $('<tr id="' + trid + '" for="' + trid + '" sjcnt="0"/>');

        var tdnoid = trid + "no";
        $tdno = $('<td rowspan=1 id="' + tdnoid + '" style="vertical-align:middle;">' + no + '</td>');


        var tdquestionid = trid + "question";
        if (question == null)
            $tdquestion = $('<td rowspan=1 id="' + tdquestionid + '" style="vertical-align:middle;"><input type="text" class="form-control noborder" placeholder="请输入巡查问题">' +
                '<button class="btn btn-link" onclick="$(\'#' + tbodyid + '\').addsj(\'' + no + '\')"><span class="icon-plus"></span>&nbsp;新增</button></td>');
        else
            $tdquestion = $('<td rowspan=1 id="' + tdquestionid + '" style="vertical-align:middle;"><span>' + question + '</span><br>' +
                '<button class="btn btn-link" onclick="$(\'#' + tbodyid + '\').addsj(\'' + no + '\')"><span class="icon-plus"></span>&nbsp;新增</button></td>');

        var tdadviseid = trid + 'advise';
        $tdadvise = $('<td rowspan=1 id="' + tdadviseid + '"><textarea class="form-control noborder" style="" placeholder="请输入处理意见"></textarea></td>');
        var tdresultid = trid + 'result';
        $tdresult = $('<td rowspan=1 id="' + tdresultid + '"><textarea class="form-control noborder" style="" placeholder="请输入处理结果"></textarea></td>');

        $tr.append($tdno);
        $tr.append($tdquestion);
        $tr.append($tdadvise);
        $tr.append($tdresult);
        $("#" + tbodyid).append($tr);
    }
    $(this).addsj(no);
}

//更新一个事件
$.fn.updatesj = function (obj) {
    var no = "0"+obj.wt;
    var tbodyid = $(this).attr('id');
    var pre = tbodyid + no;
    var firsttrid = pre + "0";
    var $firsttr = $("#" + firsttrid);

    var tdnoid = firsttrid + "no";
    var tdquestionid = firsttrid + "question";
    var tdadviseid = firsttrid + 'advise';
    var tdresultid = firsttrid + 'result';

    var sjcnt = parseInt($firsttr.attr('sjcnt'));

    var rowno = 0;
    var addedtrid = firsttrid;
    if (sjcnt == 0) {
        rowno = 0;
        addedtrid = firsttrid;
    }
    else {
        rowno = rand(99999, 999999999);
        addedtrid = pre + rowno;
    }
    var tdoperid = addedtrid + 'oper';
    $tdoper = $('<td id="' + tdoperid + '" item=1 rowno="' + rowno + '" style="height:80px;vertical-align:middle;"><button rowno="' + rowno + '" class="btn btn-link" ' +
        'onclick="$(\'#' + tbodyid + '\').delsj(\'' + no + '\',$(this).attr(\'rowno\'));">删除</button></td>');
    var tdposid = addedtrid + 'pos';
    $tdpos = $('<td id="' + tdposid + '" item=2 rowno="' + rowno + '" ><textarea class="form-control noborder" style="" placeholder="请输入具体位置">' + obj.jtwz + '</textarea></td>');
    var tdzyaid = addedtrid + 'zya';
    var ckza = "";
    var ckya = "";
    if (obj.ab == 1) {
        ckza = 'checked';
    } else if (obj.ab == 2) {
        ckya = 'checked';
    }

    $tdzya = $('<td id="' + tdzyaid + '" item=3 rowno="' + rowno + '"  style="vertical-align:middle;"><input type="radio" name="' + tdzyaid + '" value="1" ' + ckza + '>左岸<br><input type="radio" name="' + tdzyaid + '" value="2" ' + ckya + '>右岸</td>');
    var tddescid = addedtrid + 'desc';
    $tddesc = $('<td id="' + tddescid + '" item=4 rowno="' + rowno + '" ><textarea class="form-control noborder" style="" placeholder="请输入描述">' + obj.ms + '</textarea></td>');


    //如果一个事件都没,则添加到tdquestion后
    if (sjcnt == 0) {
        $("#" + tdquestionid).after($tdoper);
        $tdoper.after($tdpos);
        $tdpos.after($tdzya);
        $tdzya.after($tddesc);

    } else {//如果有事件 ，则新起一行
        $newtr = $('<tr id="' + addedtrid + '" for="' + firsttrid + '"  forii="' + firsttrid + '"/>');
        $newtr.append($tdoper);
        $newtr.append($tdpos);
        $newtr.append($tdzya);
        $newtr.append($tddesc);


        $("#" + tbodyid).find('[for*=\'' + firsttrid + '\']:last').after($newtr);

        var rowspan = parseInt($("#" + tdnoid).attr('rowspan')) + 1;
        $("#" + tdnoid).attr('rowspan', rowspan);
        $("#" + tdquestionid).attr('rowspan', rowspan);
        $("#" + tdadviseid).attr('rowspan', rowspan);
        $("#" + tdresultid).attr('rowspan', rowspan);
    }

    $firsttr.attr('sjcnt', sjcnt + 1);
}

$.fn.updatetr = function (obj) {
    var no = "0" + obj.wt;
    var question = obj.wtsm;
    var tbodyid = $(this).attr('id');
    var pre = tbodyid + no;
    var trid = pre + "0";
    var $tr = $("#" + trid);

    //如果该行不存在，则新增一行，并添加序号巡查问题，处理意见与处理结果
    if ($tr.length <= 0) {
        $tr = $('<tr id="' + trid + '" for="' + trid + '" sjcnt="0"/>');

        var tdnoid = trid + "no";
        $tdno = $('<td rowspan=1 id="' + tdnoid + '" style="vertical-align:middle;">' + no + '</td>');


        var tdquestionid = trid + "question";
        if (question == null)
            $tdquestion = $('<td rowspan=1 id="' + tdquestionid + '" style="vertical-align:middle;"><input type="text" class="form-control noborder" placeholder="请输入巡查问题">' +
                '<button class="btn btn-link" onclick="$(\'#' + tbodyid + '\').addsj(\'' + no + '\')"><span class="icon-plus"></span>&nbsp;新增</button></td>');
        else
            $tdquestion = $('<td rowspan=1 id="' + tdquestionid + '" style="vertical-align:middle;"><span>' + question + '</span><br>' +
                '<button class="btn btn-link" onclick="$(\'#' + tbodyid + '\').addsj(\'' + no + '\')"><span class="icon-plus"></span>&nbsp;新增</button></td>');

        var tdadviseid = trid + 'advise';
        $tdadvise = $('<td rowspan=1 id="' + tdadviseid + '"><textarea class="form-control noborder" style="" placeholder="请输入处理意见">'+obj.clyj+'</textarea></td>');
        var tdresultid = trid + 'result';
        $tdresult = $('<td rowspan=1 id="' + tdresultid + '"><textarea class="form-control noborder" style="" placeholder="请输入处理结果">'+obj.cljg+'</textarea></td>');

        $tr.append($tdno);
        $tr.append($tdquestion);
        $tr.append($tdadvise);
        $tr.append($tdresult);
        $("#" + tbodyid).append($tr);
    }
    $(this).updatesj(obj);
}

//显示一个事件
$.fn.viewsj = function (obj) {
    var no = "0" + obj.wt;
    var tbodyid = $(this).attr('id');
    var pre = tbodyid + no;
    var firsttrid = pre + "0";
    var $firsttr = $("#" + firsttrid);

    var tdnoid = firsttrid + "no";
    var tdquestionid = firsttrid + "question";
    var tdadviseid = firsttrid + 'advise';
    var tdresultid = firsttrid + 'result';

    var sjcnt = parseInt($firsttr.attr('sjcnt'));

    var rowno = 0;
    var addedtrid = firsttrid;
    if (sjcnt == 0) {
        rowno = 0;
        addedtrid = firsttrid;
    }
    else {
        rowno = rand(99999, 999999999);
        addedtrid = pre + rowno;
    }
    var tdposid = addedtrid + 'pos';
    $tdpos = $('<td id="' + tdposid + '" item=2 rowno="' + rowno + '" ><textarea class="form-control noborder" style="background-color:white;" disabled>' + obj.jtwz + '</textarea></td>');
    var tdzyaid = addedtrid + 'zya';
    var zya = obj.ab == 1 ? "左岸" : "右岸";
    $tdzya = $('<td id="' + tdzyaid + '" item=3 rowno="' + rowno + '"  style="vertical-align:middle;text-align:center;">' + zya + '</td>');
    var tddescid = addedtrid + 'desc';
    $tddesc = $('<td id="' + tddescid + '" item=4 rowno="' + rowno + '" ><textarea class="form-control noborder" style="background-color:white;" disabled>' + obj.ms + '</textarea></td>');

    //如果一个事件都没,则添加到tdquestion后
    if (sjcnt == 0) {
        $("#" + tdquestionid).after($tdpos);
        $tdpos.after($tdzya);
        $tdzya.after($tddesc);

    } else {//如果有事件 ，则新起一行
        $newtr = $('<tr id="' + addedtrid + '" for="' + firsttrid + '"  forii="' + firsttrid + '"/>');
        $newtr.append($tdpos);
        $newtr.append($tdzya);
        $newtr.append($tddesc);


        $("#" + tbodyid).find('[for*=\'' + firsttrid + '\']:last').after($newtr);

        var rowspan = parseInt($("#" + tdnoid).attr('rowspan')) + 1;
        $("#" + tdnoid).attr('rowspan', rowspan);
        $("#" + tdquestionid).attr('rowspan', rowspan);
        $("#" + tdadviseid).attr('rowspan', rowspan);
        $("#" + tdresultid).attr('rowspan', rowspan);
    }

    $firsttr.attr('sjcnt', sjcnt + 1);
}

$.fn.viewtr = function (obj,indexval) {
    var no = "0" + obj.wt;
    var question = obj.wtsm;

    var tbodyid = $(this).attr('id');
    var pre = tbodyid + no;
    var trid = pre + "0";
    var $tr = $("#" + trid);

    //如果该行不存在，则新增一行，并添加序号巡查问题，处理意见与处理结果
    if ($tr.length <= 0) {
        $tr = $('<tr id="' + trid + '" for="' + trid + '" sjcnt="0"/>');

        var tdnoid = trid + "no";
        $tdno = $('<td rowspan=1 id="' + tdnoid + '" style="vertical-align:middle;text-align:center;">' + "0"+indexval + '</td>');

        var tdquestionid = trid + "question";
        if (question == null)
            $tdquestion = $('<td rowspan=1 id="' + tdquestionid + '" style="vertical-align:middle;text-align:center;"><input type="text" class="form-control noborder"></td>');
        else
            $tdquestion = $('<td rowspan=1 id="' + tdquestionid + '" style="vertical-align:middle;text-align:center;"><span>' + question + '</span><br></td>');

        var tdadviseid = trid + 'advise';
        $tdadvise = $('<td rowspan=1 id="' + tdadviseid + '"><textarea class="form-control noborder" style="background-color:white;" disabled>' + obj.clyj + '</textarea></td>');
        var tdresultid = trid + 'result';
        $tdresult = $('<td rowspan=1 id="' + tdresultid + '"><textarea class="form-control noborder" style="background-color:white;" disabled>' + obj.cljg + '</textarea></td>');

        $tr.append($tdno);
        $tr.append($tdquestion);
        $tr.append($tdadvise);
        $tr.append($tdresult);
        $("#" + tbodyid).append($tr);
    }
    $(this).viewsj(obj);
}

function getXunChaTableData(tabdivid) {
    var $tabdiv = $("#" + tabdivid);
    var $table = $("table", $tabdiv);
    var $tbody = $("tbody", $table);
    var data = new Array();
    var wts = null;
    $("tr", $tbody).each(function () {
        $tr = $(this);
        var trid = $tr.attr('id');
        var posstr = $('textarea', $("#" + trid + 'pos')).val();//没有具体位置不添加
        if (typeof($tr.attr("forii")) == "undefined") {//判断是否第一行
            var advisestr = $('textarea', $("#" + trid + 'advise')).val();
            if (advisestr != null && advisestr != "") {//没有意见就不添加了
                wts = new Object();
                var nostr = $("#" + trid + 'no').text();

                var quesstr = $('span', $("#" + trid + 'question')).text();
                if(quesstr==""){
                    quesstr =$('input', $("#" + trid + 'question')).val();
                }
                var retstr = $('textarea', $("#" + trid + 'result')).val();
                wts.wt = parseInt(nostr);
                wts.wtsm = quesstr;
                wts.clyj = advisestr;
                wts.cljg = retstr;
                wts.sjs = new Array();
                data.push(wts);
            }
        }

        if (posstr != null && posstr != "") {//没有具体地点就不添加了
            var zyastr = $('input:checked', $("#" + trid + 'zya')).val();
            var descstr = $('textarea', $("#" + trid + 'desc')).val();
            var sjs = new Object();
            sjs.jtwz = posstr;
            sjs.ab = parseInt(zyastr);
            sjs.ms = descstr
            wts.sjs.push(sjs);
        }
    });
    return data;
}

function newqt() {
    var cnt = parseInt($('#qt').attr('cnt')) + 1;
    $('#qt').attr('cnt', cnt);
    $('#qttbody').newtr('' + cnt, null);
}

function updateqt() {
    var cnt = parseInt($('#qtu').attr('cnt')) + 1;
    $('#qtu').attr('cnt', cnt);
    $('#qtubody').newtr('' + cnt, null);
}