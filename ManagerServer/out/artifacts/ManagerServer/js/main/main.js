$(document).ready(function() {
    //alert("main");
    //console.log("main");
    //$('#pp').textContent="main";
	/*screenResize();
	$('.default_popup').popup();
	$("#mainview").css("height",myHeight+"px");
	$("#maindown").css("height",(myHeight-110)+"px");*/
	//$("#content").css("height","850px");
	UserRoleByID();
});

function kbutton()
{
    alert("dfdf");
}

function UserRoleByID()
{
	var arrayObj = new Array(19,20,21,22,23,24,25,26,27,28,29,30,31);
	var arrayObjid = new Array("shouye_li","user_li","book_li","envelope_li","commenting_li","calendar_li","check_li","search_li","nine_li","file_li","picture-o_li","th-large_li","cog");
	/*for(x=0;x<arrayObjid.length;x++)
	{
		$("#"+arrayObjid[x]).hide();
	}*/

	$.ajax({
		url: 'UserRoleByID',
		type: "post",
		dataType: "json",
		data: {
			"id": $("#jsid").val(),
		},
		success: function (data)
		{
			console.log(data);
			var list=data.permissionENs;
			for( i=0;i<arrayObj.length;i++)
			{
				$(list).each(function (index, item)
				{
					if(arrayObj[i]==item.id)
					{
						$("#"+arrayObjid[i]).show();
					}
				});
			}

		}
	});
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
