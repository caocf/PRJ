$(document).ready(function() {
    //alert("main");
    //console.log("main");
    //$('#pp').textContent="main";
	screenResize();
	$('.default_popup').popup();
	$("#mainview").css("height",myHeight+"px");
	$("#maindown").css("height",(myHeight-110)+"px");
	//$("#content").css("height","850px");

});

function kbutton()
{
    alert("dfdf");
}
var myHeight = 0;
function showpic(path){
	$(".default_popup").attr('href',$("#basePath").val()+'File/'+path);
	$(".default_popup").click();
}
function screenResize()
{
    myHeight = $(window).innerHeight();
    //myHeight = document.documentElement.clientHeight;

    /*if(typeof( $(window).innerWidth()) == 'number' ) {
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
   
    /*if(myHeight<740){
    	myHeight=700;
    }else{
    	 myHeight=Number(myHeight-105);
    }*/


}
