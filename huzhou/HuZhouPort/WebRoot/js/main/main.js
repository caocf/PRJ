$(document).ready(function() {
	screenResize();
	$('.default_popup').popup();
	$("#content").css("height",myHeight+"px");
});
var myHeight = 0;
function showpic(path){
	$(".default_popup").attr('href',$("#basePath").val()+'File/'+path);
	$(".default_popup").click();
}
function screenResize(){
    if(typeof( $(window).innerWidth()) == 'number' ) {
        //Non-IE
        //myWidth = $(window).innerWidth();
        myHeight = $(window).innerHeight();
    } 
    else if(document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight)){
        //IE 6+ in 'standards compliant mode'
       // myWidth = document.documentElement.clientWidth;
        myHeight = document.documentElement.clientHeight;  
    }
    window.onresize = function () 
    { 
        if( typeof( $(window).innerWidth() ) == 'number' ) {
            //Non-IE
           // myWidth = window.innerWidth;
            myHeight = window.innerHeight;
        } 
        else if(document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight)){
            //IE 6+ in 'standards compliant mode'
          //  myWidth = document.documentElement.clientWidth;
            myHeight = document.documentElement.clientHeight;  
        }
      //  mapheight();
    } 
   
    if(myHeight<740){
    	myHeight=635;
    }else{
    	 myHeight=Number(myHeight-105);
    }
}
