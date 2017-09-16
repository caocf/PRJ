package com.huzhouport.electric.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.electric.dao.ElectricVisaDao;
import com.huzhouport.electric.model.ElectricVisa;

public class ElectricVisaDaoImpl extends BaseDaoImpl<ElectricVisa> implements
		ElectricVisaDao {

	public String addElectricVisaInfo(ElectricVisa electricVisa)
			throws Exception {
		this.hibernateTemplate.save(electricVisa);
		return null;
	}

	public int countPageElectricVisaInfo(ElectricVisa electricVisa,
			String condition) throws Exception {
		if(null==electricVisa.getCondition()){
			electricVisa.setCondition("");
		}
		String hql = "select count(*) from ElectricVisa ev where (ev.shipName like '%"
				+ electricVisa.getCondition()
				+ "%' "
				+ "or ev.startingPort in(select p1.portID from Port p1 where ev.startingPort=p1.portID and p1.portName like '%"
				+ electricVisa.getCondition()
				+ "%')"
				+ " or ev.arrivalPort in(select p1.portID from Port p1 where ev.arrivalPort=p1.portID and p1.portName like '%"
				+ electricVisa.getCondition()
				+ "%') or ev.time like '%"
				+ electricVisa.getCondition()
				+ "%' or ev.visaUserName like '%"
				+ electricVisa.getCondition()
				+ "%' or ev.cargoTypes like '%"
				+ electricVisa.getCondition()
				+ "%' or ev.mark like '%"
				+ electricVisa.getCondition()
				+ "%'or ev.visaStatus like '%"
				+ electricVisa.getCondition()
				+ "%') ";
		if (null != condition && condition != "") {
			hql += "and (" + condition + ")";
		}
		return this.countEForeignKey(electricVisa, hql);
	}

	public List<?> searchElectricVisaInfo(ElectricVisa electricVisa,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception {
		if(null==electricVisa.getCondition()){
			electricVisa.setCondition("");
		}
		String hql = "select new map((select p1.portName from Port p1 where ev.startingPort=p1.portID) as startingPort,"
				+ "(select p2.portName from Port p2 where ev.arrivalPort=p2.portID) as arrivalPort,ev.visaID as visaID,ev.shipName as shipName,ev.mark as mark,"
				+ "ev.time as time,ev.cargoTypes as cargoTypes,ev.carrying as carrying,ev.visaStatus as visaStatus,ev.visaUserName as visaUserName,ev.visaContent as visaContent "
				+ ") from ElectricVisa ev where (ev.shipName like '%"
				+ electricVisa.getCondition()
				+ "%' "
				+ "or ev.startingPort in(select p1.portID from Port p1 where ev.startingPort=p1.portID and p1.portName like '%"
				+ electricVisa.getCondition()
				+ "%')"
				+ " or ev.arrivalPort in(select p1.portID from Port p1 where ev.arrivalPort=p1.portID and p1.portName like '%"
				+ electricVisa.getCondition()
				+ "%') or ev.time like '%"
				+ electricVisa.getCondition()
				+ "%' or ev.visaUserName like '%"
				+ electricVisa.getCondition()
				+ "%' or ev.cargoTypes like '%"
				+ electricVisa.getCondition()
				+ "%' or ev.mark like '%"
				+ electricVisa.getCondition()
				+ "%'or ev.visaStatus like '%"
				+ electricVisa.getCondition()
				+ "%') ";
		if (null != condition && condition != "") {
			hql += " and (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(electricVisa, hql, startSet,
				maxSet);
		return list;

	}

	public List<?> seeElectricVisaID(ElectricVisa electricVisa)
			throws Exception {
		// String hql = "  from ElectricVisa ev where ev.visaID='"
		// + electricVisa.getVisaID() + "'";
		String hql = "select new map((select p1.portName from Port p1 where ev.startingPort=p1.portID) as startingPort,"
				+ "(select p2.portName from Port p2 where ev.arrivalPort=p2.portID) as arrivalPort,ev.visaID as visaID,ev.shipName as shipName,ev.mark as mark,"
				+ "ev.time as time,ev.cargoTypes as cargoTypes,ev.carrying as carrying,ev.visaStatus as visaStatus,ev.visaContent as visaContent"
				+ ")from ElectricVisa ev where ev.visaID="
				+ electricVisa.getVisaID();
		// String hql =
		// "select new map(d.defaultRole as defaultRole,d.departmentId as departmentId,d.departmentName as departmentName,d.partOfDepartmentId as partOfDepartmentId,(select de.departmentName from Department de where de.departmentId=d.partOfDepartmentId) as partOfDepartmentName,r.roleId as roleId,r.roleName as roleName)from Department d,Role r where d.defaultRole=r.roleId and d.departmentId="+department.getDepartmentId();
		return this.hibernateTemplate.find(hql);
	}

	public int countPageElectricVisaInfoAD(ElectricVisa electricVisa,
			String condition) throws Exception {
		if(null==electricVisa.getCondition()){
			electricVisa.setCondition("");
		}
		String hql = "select count(*) from ElectricVisa ev where (ev.shipName like '%"
				+ electricVisa.getCondition()
				+ "%' "
				+ "or ev.startingPort in(select p1.portID from Port p1 where ev.startingPort=p1.portID and p1.portName like '%"
				+ electricVisa.getCondition()
				+ "%')"
				+ " or ev.arrivalPort in(select p1.portID from Port p1 where ev.arrivalPort=p1.portID and p1.portName like '%"
				+ electricVisa.getCondition()
				+ "%') or ev.time like '%"
				+ electricVisa.getCondition()
				+ "%') ";
		if (null != condition && condition != "") {
			hql += "and (" + condition + ")";
		}
		return this.countEForeignKey(electricVisa, hql);
	}

	public List<?> searchElectricVisaInfoAD(ElectricVisa electricVisa,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception {
		if(null==electricVisa.getCondition()){
			electricVisa.setCondition("");
		}
		String hql = "select new map((select p1.portName from Port p1 where ev.startingPort=p1.portID) as startingPort,"
				+ "(select p2.portName from Port p2 where ev.arrivalPort=p2.portID) as arrivalPort,ev.visaID as visaID,ev.shipName as shipName,ev.mark as mark,"
				+ "ev.time as time,ev.cargoTypes as cargoTypes,ev.carrying as carrying,ev.visaStatus as visaStatus,ev.visaUserName as visaUserName,ev.visaContent as visaContent "
				+ ") from ElectricVisa ev where (ev.shipName like '%"
				+ electricVisa.getCondition()
				+ "%' "
				+ "or ev.startingPort in(select p1.portID from Port p1 where ev.startingPort=p1.portID and p1.portName like '%"
				+ electricVisa.getCondition()
				+ "%')"
				+ " or ev.arrivalPort in(select p1.portID from Port p1 where ev.arrivalPort=p1.portID and p1.portName like '%"
				+ electricVisa.getCondition()
				+ "%') or ev.time like '%"
				+ electricVisa.getCondition()
				+ "%') ";
		if (null != condition && condition != "") {
			hql += " and (" + condition + ")";
		}
		List<?> list = this.queryqueryEForeignKey(electricVisa, hql+" order by ev.time desc", startSet,
				maxSet);
		return list;

	}
}
