<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path1 + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
 <base href="<%=basePath1%>">

<link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/user_department_show.css" />
<link rel="stylesheet" type="text/css" href="css/leave/leave.css" />
<link rel="stylesheet" type="text/css" href="css/common/smallDialog.css" />
<script src="js/common/jquery-1.5.2.min.js"></script>
<script src="js/officoa/knowledgelist_add.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>

<script type="text/javascript">  
$('#ddtree').combotree( {  
    //获取数据URL  
    url : 'showKnowLedge',  
    //选择树节点触发事件  
    onSelect : function(data.pagemodel.recordsDate) {  
        //返回树对象  
        var tree = $(this).tree;  
        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
        var isLeaf = tree('isLeaf', data.pagemodel.recordsDate.target);  
        if (!isLeaf) {  
            //清除选中  
            $('#ddtree').combotree('clear');  
        }  
    }  
});  
  
//赋值  
//$('#ddtree').combotree('setValue',20);  
  
function check() {  
    var value = $('#ddtree').combotree('getValue');  
    if (value && value.length != 0) {  
        return true;  
    } else {  
        return false;  
    }  
}  
</script> 
</head>
<body><input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input id="kindID" name="kindID" type="hidden" value="<%=request.getParameter("kindID") %>" />
<input type="hidden" value="<%=session.getAttribute("userId")%>" id="LoginUser" />
<input id="kind" name="kind" type="hidden" value="<%=request.getParameter("kind") %>" />
<div class="u2_top">
				<div id="u2_top_left">
					<img alt="新增" src="image/operation/add_know_normal.png" class="u3_img" 
						onclick="addkonwledge()" onMouseOver="addkonwOver(this)" onMouseOut="addkonwOut(this)"/>
				</div>
				<div id="u2_top_right">
					<input type="text" id="Content"  name="Content" class="search_input_box"/><img alt="搜索" 
					src="image/operation/search_normal.png" class="u3_img" 
					onclick="selectKnowledgelist()" onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
				</div>
			</div>	
	
	
	<table width="100%" border="0" cellspacing="0" class="listTable"
			id="Knowledge">
			<col width="40%"/><col width="40%"/><col width="20%"/>
		<tr>
				<th> 
					通知名称 
				</th>
				<th> 
					通知摘要 
				</th>
				<th>
					操作
				</th>
			</tr>
		
		
   </table>

<div id="fenye">

</div>


<div id="deletelogdiv" style="display:none;height:300px;" >
<div class="selectlog_content">
    <p>知识库类别：</p><input type="text" id="kindname" name="kindname" value="" /><br/>
	<p>知识库类别：</p><input type="text" id="ddtree" name="ddtree" value="" /><br/>
</div>
<div class="selectlog_font">
		<img src="image/operation/sure_normal.png" onClick="addknowledge()"
				onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
			<img src="image/operation/reset_small_normal.png" onClick="hiden()"
				onmouseover="ResetSmallOver(this)" onMouseOut="ResetSmallOut(this)" title="取消" />
		</div>
</div>
		
</body></html>