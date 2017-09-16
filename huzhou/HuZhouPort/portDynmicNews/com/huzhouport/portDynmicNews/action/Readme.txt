/addIndustryInfo
【描述】添加行业资讯
【输入】
	industryinfo.title // 标题
	industryinfo.content // 内容
	industryinfo.oriobj // 面向对象, 0--外部  1--内部  2--全部
【输出】
	result // -1表示有错， 0表示成功
	msg //错误信息




/updateIndustryInfo
【描述】更新行业资讯
【输入】
	industryinfo.id //待更新的ID
	industryinfo.title // 标题
	industryinfo.content // 内容
	industryinfo.oriobj // 面向对象, 0--外部  1--内部  2--全部
【输出】
	result // -1表示有错，0表示成功 
	msg //错误信息





/deleteIndustryInfo
【描述】删除行业资讯
【输入】
	id //待删除的ID
	




/findIndustryInfoList
【描述】查找所有的行业资讯
【输入】
	page //页码，从1开始编码
	rows //每页行数
【可选输入】
	searchstring //搜索标题或内容
【输出】
	list //数据
	pages //总页数
	total //总条数
	



/findInnerIndustryInfoList
【描述】查找面向内部和面向全部的行业资讯
【输入】
	page //页码，从1开始编码
	rows //每页行数
【输出】
	list //数据
	pages //总页数
	total //总条数




/findPublicIndustryInfoList
【描述】查找面向公众和面向全部的行业资讯
【输入】
	page //页码，从1开始编码
	rows //每页行数
【输出】
	list //数据
	pages //总页数
	total //总条数


/findIndustryInfoDetail
【描述】查找行业资讯详情
【输入】
	id //待查找的行业资讯ID
【输出】
	industryinfo //详情