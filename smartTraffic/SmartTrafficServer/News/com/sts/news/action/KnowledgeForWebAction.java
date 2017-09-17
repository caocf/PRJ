package com.sts.news.action;

import com.sts.news.model.KnowledgeDetail;

/**
 * web端交通常识
 * @author Administrator shen
 *
 */
public class KnowledgeForWebAction 
{
	private KnowledgeDetail knowledgeDetail=new KnowledgeDetail();

	public KnowledgeDetail getKnowledgeDetail() {
		return knowledgeDetail;
	}

	public void setKnowledgeDetail(KnowledgeDetail knowledgeDetail) {
		this.knowledgeDetail = knowledgeDetail;
	}

	//交通常识详情
	public String SearchDetailKnowledge()
	{
		try {
		int id=knowledgeDetail.getId()-1;
		if(knowledgeDetail.getType()==2){
			knowledgeDetail.setTitle(KnowledgeDetail.Law_title[id]);
			knowledgeDetail.setImageUrl(KnowledgeDetail.Law_imageUrl[id]);
			knowledgeDetail.setContent(KnowledgeDetail.Law_content[id]);
			knowledgeDetail.setSource(KnowledgeDetail.Law_source[id]);
			knowledgeDetail.setDate(KnowledgeDetail.Law_date[id]);
			knowledgeDetail.setAuthor(KnowledgeDetail.Law_author[id]);
		}else{
			knowledgeDetail.setTitle(KnowledgeDetail.Common_title[id]);
			knowledgeDetail.setImageUrl(KnowledgeDetail.Common_imageUrl[id]);
			knowledgeDetail.setContent(KnowledgeDetail.Common_content[id]);
			knowledgeDetail.setSource(KnowledgeDetail.Common_source[id]);
			knowledgeDetail.setDate(KnowledgeDetail.Common_date[id]);
			knowledgeDetail.setAuthor(KnowledgeDetail.Common_author[id]);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
