/*$$(document).ready(function() {
	screenResize();
	
});*/
/**开关地图左侧搜索栏**/
function toggleFooter (class_left,class_right) {
    var left_area = $$('.layout1_left');
    var right_area = $$('.layout1_right');
    if(class_left!=undefined){
    	left_area = $$('.'+class_left);
    }
    if(class_right!=undefined){
    	right_area = $$('.'+class_right);
    }
  //  var toggleImg = $$('#toggle-img');
   /* if(left_area[0].offsetWidth <=0){//如果已经关闭，则打开
        left_area.animate({ 
            width: 350
        }, 200);
    right_area.animate({
    	width: myWidth - 785
    },200);
    }else{
        left_area.animate({ 
             width: 0
        }, 200);
    right_area.animate({
    	width: myWidth - 14
    },200);
    }
    setTimeout(function(){codeChange()},200);*/
    if(left_area[0].offsetWidth <=0){//如果已经关闭，则打开
    	left_area.show();
    	right_area.css("margin-left","350px");
    	 $$("#div_left_open").hide();
         $$("#div_left_close").show();
    }else{
    	right_area.css("margin-left","0");
    	left_area.hide();
    	 $$("#div_left_close").hide();
         $$("#div_left_open").show();
    }
    
}



var myWidth = 0;
var myHeight = 0;
function screenResize(){
    if(typeof( $$(window).innerWidth()) == 'number' ) {
        //Non-IE
        myWidth = $$(window).innerWidth();
        myHeight = $$(window).innerHeight();
    } 
    else if(document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight)){
        //IE 6+ in 'standards compliant mode'
        myWidth = document.documentElement.clientWidth;
        myHeight = document.documentElement.clientHeight;  
    }
    window.onresize = function () 
    { 
        if( typeof( $$(window).innerWidth() ) == 'number' ) {
            //Non-IE
            myWidth = window.innerWidth;
           myHeight = window.innerHeight;
        } 
        else if(document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight)){
            //IE 6+ in 'standards compliant mode'
            myWidth = document.documentElement.clientWidth;
            myHeight = document.documentElement.clientHeight;  
        }
      //  mapheight();
    } 
}
function setMapDivHeigth(divip){
	screenResize();
	var divHeight=Number(myHeight)-141;
	if(divHeight>700){
		$$(divip).css("height","700px");
	}else{
		$$(divip).css("height",divHeight+"px");
	}
}


function codeChange(){
    if($$(".layout1_left")[0].offsetWidth >0){
        $$("#div_left_open").hide();
        $$("#div_left_close").show();
    }else{  
        $$("#div_left_close").hide();
        $$("#div_left_open").show();
    }
}