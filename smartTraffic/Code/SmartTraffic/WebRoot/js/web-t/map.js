/**
 * @author xiaoxuan
 * 构建地图js页面
 */

/**
 *在页面上显示地图 (web-t的操作)
 * @param id  地图id
 */
var map;//地图，作为全局变量
function createMap(id){
	map=new WebTMap(id);
	map.setVisibility(true);
	return map;
}
