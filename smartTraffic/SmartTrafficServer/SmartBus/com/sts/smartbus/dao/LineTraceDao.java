package com.sts.smartbus.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.smartbus.model.QRCode;
import com.sts.smartbus.model.Trace;

public class LineTraceDao 
{
	HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 查找轨迹
	 * @param id
	 * @param direct
	 * @return
	 */
	public List<Trace> queryTrace(int id,int direct)
	{
		String hql = "from Trace t where t.lineID=" + id + " and t.direct="+direct;
		
		return (List<Trace>) hibernateTemplate.find(hql);
	}
	
	/**
	 * 查找QR码
	 * @param qr
	 * @return
	 */
	public List<QRCode> queryQR(String qr)
	{
		String hql="from QRCode q where q.qr='"+qr+"'";
		return (List<QRCode>) hibernateTemplate.find(hql);
	}
}
