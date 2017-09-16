//分级隐藏/显示
function labeltoggle(thislabel){
var nextlv= getnextlv(thislabel);
    if($(thislabel).next().css('display')!='none'){
        nextlv.hide();
    }else{
        nextlv.show();
    }
    $(thislabel).parent().children('label').removeClass('ischecked');
    $(thislabel).parent().children('label').hover({'background-color':'#0066cc','color':'white'});
    $(thislabel).addClass('ischecked');
}
//获取选中下一层对象
function getnextlv(thislabel){
    var cno=parseInt($(thislabel).attr('class').split(' ')[0].split('_')[1]);
    var cname="";
    for(var i=1;i<=cno;i++){
        cname=".treelv_"+i;
        if(checklv(thislabel,cname,cno)){
            break
        }
    }
    return $(thislabel).nextUntil(cname);
}
//分析层级关系
function checklv(thislabel,cname,cno){
    for(var i=1;i<=cno;i++){
        if($(thislabel).nextUntil(cname).filter(".treelv_"+i).size()!=0){
            return false
        }
    }
    return true
}
//
//function allowDrop(ev)
//{
//    ev.preventDefault();
//}
//
//function drag(ev)
//{
//    ev.dataTransfer.setData("Text",ev.target.id);
//}
//
//function drop(ev)
//{
//    ev.preventDefault();
//    var data=ev.dataTransfer.getData("Text");
//    ev.target.appendChild(document.getElementById(data));
//}