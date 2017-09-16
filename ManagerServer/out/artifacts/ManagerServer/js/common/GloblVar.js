var VOL = "大客户";
var FACTOR = "代理商";
var RETAILER = "零售";
var CHECKSTATE = [ "未送审", "送审", "审核通过", "审核未通过" ];
var DELIVERYSTATE = [ "作废", "正常", "销退" ];
var FUZZY = "YES";// 是模糊查询，用于方法：如，CustomerDaoImpl 的findCustomerInfo 方法
var ISNOTFUZZY = "NO";
var PASS = "已审批";
var FAIL = "已拒绝";
var DONTUSE = "作废";
var LODOP; // 声明为全局变量，打印时会用
var WEIGHTISUSERED = "true";// 榜单已处理
var PRITEDBILL = '已打印';
var HASDELIVERY = '已提货';
var HASPARTDELIVERY = '已部分提货';
var HASFACTORY = '已出厂';
// 查询条件
var PAIRCONTAIN = "%%";// 包含
var PAIREQULE = "=";// 等于
var NOTPAIREQULE = "!=";// 不等于
var MORETHAN = ">";// 大于
var LISTPAIRTEXT = [ "包含", "不包含", "等于", "不等于", "大于", "小于", "大于等于", "小于等于",
		"以..开头", "以..结尾" ];

var LISTPAIRVALUE = [ "%%", "!%%", "=", "!=", ">", "<", ">=", "<=", "%_", "_%" ];

var LISTFPCTEXT = [ "过磷酸钙", "活性磷", "硫酸", "生料", "熟料", "燃料", "鲜肥","水泥成品","原料" ]
var LISTFPCVALUE = [ "superphosphate", "activePhosphorus", "sulfuricAcid",
		"rawMeal", "clinker", "theFuel", "freshFat", "semiFinishedProducts",
		"cementProducts" ];
var SHOWROWS = 10;
/*"半成品", 
 * var SHOWROWSINFO=["5","10","20"];
 * 
 */

