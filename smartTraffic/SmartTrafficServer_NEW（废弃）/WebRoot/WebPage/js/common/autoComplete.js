var oldautocomplete="";
var tj_array1=new Array();
function eeeee(){
	
	var tj_tags=$$("#tj_tags").val();
	if(tj_tags.length>1){
		if(tj_tags.indexOf(oldautocomplete)==0||tj_tags.indexOf(oldautocomplete)==-1){
	
    $$.ajax({  
        url: "searchpoibykey",  
        type: "post",  
        data: {   
           'sp.key' : encodeURI(tj_tags),
           'sp.pageNumber':10,
           'sp.startPosition':1,
           'sp.json':0
        },  
        success:function(data){  
        	oldautocomplete=tj_tags;
            var list = $$.parseJSON(data.recodeString);	
            var featureMember = list.FeatureCollection.featureMember;
            tj_array1.splice(0,tj_array1.length); 
            if(featureMember!=null){
			for ( var i = 0; i < featureMember.length; i++) {
				var JXPOI = featureMember[i].JXPOI;
				tj_array1[i]=JXPOI[0].NAME+","+JXPOI[0].ADDRESS; 
				/* var map = new Map();
				 map.put("label", JXPOI[0].NAME);
				 map.put("value",JXPOI[0].ADDRESS);tj_array1[i]=map;*/
			}
            }
			
			$$("#tj_tags").autocomplete({
				source: tj_array1,  
				minLength: 1   
			}); 
			
        }  
    });  
	}
	}
};
/*$$(document).ready(function(){

	  
	$$.ui.autocomplete.prototype.select = function (e) {  alert(JSON.stringify(e)); 
	             
	    };  

});*/
function Auto_select_self(){alert("111:"+tj_array1.length);
	for ( var i = 0; i < tj_array1.length; i++) {alert(tj_array1[i]+":"+$$("#tj_tags").val());
		if(tj_array1[i]==$$("#tj_tags").val()){
			alert(i);
		}
	}	
}


/*
 * 
 * //map start-----------------------------------
 * function Map(){
this.container = new Object();
}


Map.prototype.put = function(key, value){
this.container[key] = value;
}


Map.prototype.get = function(key){
return this.container[key];
}


Map.prototype.keySet = function() {
var keyset = new Array();
var count = 0;
for (var key in this.container) {
// 跳过object的extend函数
if (key == 'extend') {
continue;
}
keyset[count] = key;
count++;
}
return keyset;
}


Map.prototype.size = function() {
var count = 0;
for (var key in this.container) {
// 跳过object的extend函数
if (key == 'extend'){
continue;
}
count++;
}
return count;
}


Map.prototype.remove = function(key) {
delete this.container[key];
}


Map.prototype.toString = function(){
var str = "";
for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
}
return str;
}

var map
function ccccc(){alert($$('#tj_tags').val());
map= new XMap("simpleMap",2);
	var key = $$('#tj_tags').val();
	var keyQuery = map.createKeyQuery(key, 20, 1);

	var query = null;
	query = keyQuery;
	query.setArgs( {
		"callback" : "searchCallback"
	});
	query.executeSearch();
	
	
}
//map end-----------------------------------
*
*/
var source1=new Array();
/*var source1=function(){
	source1=new Array();
	ccccc();
};*/
//搜索回调函数
function searchCallback(data) {
	
	if (data.errorMsg) {
		alert("errorMsg:" + data.errorMsg);
	} else {
		
		
		if (data.dataList && data.dataList.length > 0) {
			var len = data.dataList.length;
			var j=0;
			
			for ( var i = 0; i < len; i++) {
				var obj = data.dataList[i];
				if (obj["type"] != "busline") {
					//label:obj["name"];
					//amount:obj["addr"];
					source1[j]=obj["name"]+" "+ obj["addr"];
					j++;
				}
			}
			
		}

	}alert("source1:"+source1);
	//return source1;
}