package com.highwaycenter.glz.model;

/**
 * HGlzXcQljcxjcmx entity. @author MyEclipse Persistence Tools
 */

public class HGlzXcQljcxjcmx implements java.io.Serializable {

	private static final long serialVersionUID = 4517737147522093650L;
		private String id;
		private String checkId;
		private String componentId;
		private String type;
		private String area;
		private String opinion;
		private String judgeType;

		// Constructors

		/** default constructor */
		public HGlzXcQljcxjcmx() {
		}

		/** minimal constructor */
		public HGlzXcQljcxjcmx(String id, String checkId, String componentId) {
			this.id = id;
			this.checkId = checkId;
			this.componentId = componentId;
		}

		/** full constructor */
		public HGlzXcQljcxjcmx(String id, String checkId, String componentId,
				String type, String area, String opinion, String judgeType) {
			this.id = id;
			this.checkId = checkId;
			this.componentId = componentId;
			this.type = type;
			this.area = area;
			this.opinion = opinion;
			this.judgeType = judgeType;
		}

		// Property accessors

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCheckId() {
			return this.checkId;
		}

		public void setCheckId(String checkId) {
			this.checkId = checkId;
		}

		public String getComponentId() {
			return this.componentId;
		}

		public void setComponentId(String componentId) {
			this.componentId = componentId;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getArea() {
			return this.area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getOpinion() {
			return this.opinion;
		}

		public void setOpinion(String opinion) {
			this.opinion = opinion;
		}

		public String getJudgeType() {
			return this.judgeType;
		}

		public void setJudgeType(String judgeType) {
			this.judgeType = judgeType;
		}


}