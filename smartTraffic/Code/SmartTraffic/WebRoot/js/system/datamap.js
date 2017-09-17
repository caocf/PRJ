/**
 * 全局json
 */
var __data = {};
function back(jsons) {
        __data = {}; // 有数据清理需求的需要做这个工作
}

//查询数据
function findJsonByKey(key){
	return __data[key];
}

//刷新数据
function refreshJsonByKey(key,json){
	__data[key] = json;
}
