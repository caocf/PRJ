package com.channel.dic.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.channel.model.user.CZdXzqhRela;
import com.channel.model.user.CZdXzqhdm;
import com.common.utils.tree.impl.TreeDaoImpl;

@Repository("xzqhdmdao")
public class XzqhdmDaoImpl extends TreeDaoImpl<CZdXzqhdm, CZdXzqhRela> {

	@Override
	public Class<?> getEntryClass() {
		// TODO Auto-generated method stub
		return CZdXzqhdm.class;
	}

	@Override
	public Class<?> getEntryRelationClass() {
		// TODO Auto-generated method stub
		return CZdXzqhRela.class;
	}

	public CZdXzqhdm queryXzqhdmById(int id) {
		// TODO Auto-generated method stub
		CZdXzqhdm xzqh = (CZdXzqhdm) super
				.findUnique(new CZdXzqhdm(), "id", id);
		return xzqh;
	}

	public List<CZdXzqhRela> queryLeafXzqhs(int rootid) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String sql = "select * from c_zd_xzqh_rela where pid in(select sid from c_zd_xzqh_rela where pid=?)";
		Query query = session.createSQLQuery(sql).addEntity(CZdXzqhRela.class)
				.setInteger(0, rootid);
		List<CZdXzqhRela> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<CZdXzqhRela> queryPidLeafXzqh(int pid) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String sql = "select * from c_zd_xzqh_rela where pid =?";
		Query query = session.createSQLQuery(sql).addEntity(CZdXzqhRela.class)
				.setInteger(0, pid);
		List<CZdXzqhRela> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public CZdXzqhdm queryRootXzqh() {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String sql = "select a.* from  c_zd_xzqhdm a where a.id not in (select r.sid from  c_zd_xzqh_rela r) and a.id>0";
		Query query = session.createSQLQuery(sql).addEntity(CZdXzqhdm.class);
		List<CZdXzqhdm> list = query.list();
		session.flush();
		session.close();
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	public String queryXzqhName(Integer xzqh) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String sql = "select * from  c_zd_xzqhdm  where id=?";
		Query query = session.createSQLQuery(sql).addEntity(CZdXzqhdm.class)
				.setInteger(0, xzqh);
		List<CZdXzqhdm> list = query.list();
		session.flush();
		session.close();
		return (list != null && list.size() > 0) ? list.get(0).getName() : "";
	}

	public void addXzqhRela(CZdXzqhRela xzqhRela) {
		// TODO Auto-generated method stub
		super.save(xzqhRela);
	}

	public void addXzqhdm(CZdXzqhdm xzqhdm) {
		// TODO Auto-generated method stub
		// Session session = getSessionFactory().openSession();
		// session.save(xzqhdm);
		// session.flush();
		// session.close();
		super.save(xzqhdm);
	}

	public CZdXzqhRela queryXzqhdmBySid(int sid) {
		// TODO Auto-generated method stub
		return (CZdXzqhRela) super.findUnique(new CZdXzqhRela(), "sid", sid);
	}

	public CZdXzqhdm queryXzqhdmByName(String name) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String sql = "select * from  c_zd_xzqhdm  where name=?";
		Query query = session.createSQLQuery(sql).addEntity(CZdXzqhdm.class)
				.setString(0, name);
		List<CZdXzqhdm> list = query.list();
		session.flush();
		session.close();
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	public CZdXzqhdm queryXzqhdmByCode(String code) {
		Session session = getSessionFactory().openSession();
		String sql = "select * from  c_zd_xzqhdm  where code=?";
		Query query = session.createSQLQuery(sql).addEntity(CZdXzqhdm.class)
				.setString(0, code);
		List<CZdXzqhdm> list = query.list();
		session.flush();
		session.close();
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
}
