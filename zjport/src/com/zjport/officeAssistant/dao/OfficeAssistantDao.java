package com.zjport.officeAssistant.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TWQ on 2016/9/6.
 */
@Repository("officeAssistantDao")
public class OfficeAssistantDao extends BaseDaoDB{

    public BaseRecords selectStructure() {return super.find(new ObjectQuery(TKnowledgeBaseStructure.class));}

    public BaseRecords selectKnowledgeBaseList(int structureId, String search, int rows, int page) {
        String hql = "from TKnowledgeBase where stIsValid='1'";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(structureId)) {
            hql += " and stStructureId =:structureId";
            paramMap.put("structureId", structureId);
        }
        if(!StringUtils.isEmpty(search)) {
            hql += " and stBaseTitle like:search";
            paramMap.put("search", "%"+search+"%");
        }
        hql +=" order by dtSend desc";
        HQL Hql = new HQL(hql, paramMap);
        Hql.setPage(page);
        Hql.setRows(rows);
        return super.find(Hql);
    }

    public TKnowledgeBase getKnowledgeBaseById(int baseId) {
        return (TKnowledgeBase)super.findUnique(new ObjectQuery(TKnowledgeBase.class, "stBaseId", baseId));
    }

    public TKnowledgeBaseStructure getStructureById(int structureId) {
        return (TKnowledgeBaseStructure)super.findUnique(new ObjectQuery(TKnowledgeBaseStructure.class,"stStructureId",structureId));
    }

    public TKnowledgeBaseStructure getStructureNameById(int id) {
        return (TKnowledgeBaseStructure)super.findUnique(new ObjectQuery(TKnowledgeBaseStructure.class,"stStructureId", id));
    }

    public BaseRecords getUserListByStructureId(String type, String structureId, String search,int page,int rows) {
        /*String hql = "from TUser";
        Map<String,Object> paramMap = new HashMap<>();

        if(!StringUtils.isEmpty(structureId)) {
            if("1".equals(type)) {
                hql += " where stOrgId =:structureId";
            } else {
                hql += " where stDepartmentId =:structureId";
            }
            paramMap.put("structureId", structureId);
        }
        hql +=" order by stOrder";
        HQL Hql = new HQL(hql, paramMap);
        Hql.setPage(page);
        Hql.setRows(rows);
        return super.find(Hql);*/

        String sql = "select a.ST_USER_NAME, a.ST_PHONE, b.ST_DEPARTMENT_NAME, c.ST_POSITION_NAME, a.ST_USER_ID from " +
                "t_user a LEFT JOIN t_department b on a.ST_DEPARTMENT_ID = b.ST_DEPARTMENT_ID " +
                "LEFT JOIN t_position c on a.ST_POSITION_ID=c.ST_POSITION_ID";

        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(search)) {
            sql += " where (a.ST_USER_NAME like:search or a.ST_PHONE like:search)";
            paramMap.put("search", "%"+search+"%");
        } else {
            if(!StringUtils.isEmpty(structureId)) {
                if("1".equals(type)) {
                    sql += " where (a.ST_ORG_ID =:structureId or b.ST_ORG_ID=:structureId)";
                } else {
                    sql += " where a.ST_DEPARTMENT_ID =:structureId";
                }
                paramMap.put("structureId", structureId);
            }
        }
        sql +=" order by  ST_ORDER is null,ST_ORDER";
        SQL Sql = new SQL(sql, paramMap);
        Sql.setPage(page);
        Sql.setRows(rows);
        return super.find(Sql);
    }

    public BaseRecords getUserById(int userId) {
        String sql = "select a.ST_USER_ID, a.ST_USER_NAME, b.ST_DEPARTMENT_NAME, c.ST_POSITION_NAME, a.ST_LAW_CODE, a.ST_ORDER, a.ST_PHONE, a.ST_OFFICE_PHONE, a.ST_FUCTITIOUS_PHONE, a.ST_EMAIL  from " +
                "t_user a LEFT JOIN t_department b on a.ST_DEPARTMENT_ID = b.ST_DEPARTMENT_ID " +
                "LEFT JOIN t_position c on a.ST_POSITION_ID=c.ST_POSITION_ID";

        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(userId)) {
            sql += " where a.ST_USER_ID =:userId";
            paramMap.put("userId", userId);
        }
        sql +=" order by ST_ORDER";
        SQL Sql = new SQL(sql, paramMap);
        return super.find(Sql);
    }

    public BaseRecords selectAddressGroup() {
        return super.find(new ObjectQuery(TAddressListGroup.class));
    }

    public BaseRecords selectAddressGroupList(int groupId, int page, int rows) {
        String sql = "select a.ST_USER_NAME, a.ST_PHONE, b.ST_DEPARTMENT_NAME, c.ST_POSITION_NAME, a.ST_USER_ID, a.ST_LAW_CODE, a.ST_ORDER, a.ST_OFFICE_PHONE, a.ST_FUCTITIOUS_PHONE, a.ST_EMAIL  from " +
                "t_user a LEFT JOIN t_department b on a.ST_DEPARTMENT_ID = b.ST_DEPARTMENT_ID " +
                "LEFT JOIN t_position c on a.ST_POSITION_ID=c.ST_POSITION_ID " +
                "LEFT JOIN t_address_list_middle d on a.ST_USER_ID = d.ST_USER_ID " +
                "LEFT JOIN t_address_list_group e on d.ST_ADDRESS_LIST_GROUP_ID = e.ST_ADDRESS_LIST_GROUP_ID";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(groupId)) {
            sql += " where e.ST_ADDRESS_LIST_GROUP_ID =:groupId";
            paramMap.put("groupId", groupId);
        }
        sql +=" order by ST_ORDER";
        SQL Sql = new SQL(sql, paramMap);
        Sql.setPage(page);
        Sql.setRows(rows);
        return super.find(Sql);
    }

    public void saveUserInfo(int userId, String lawcode, int order) {
        TUser user = (TUser)super.findUnique(new ObjectQuery(TUser.class,"stUserId",userId));
        user.setStLawCode(lawcode);
        user.setStOrder(order);
        super.update(user);
    }



    public BaseRecords selectCalendarList(int userId) {

        String sql = "select distinct a.* from t_calendar a LEFT JOIN t_calendar_attend_user b on a.ST_CALENDAR_MIDDLE_ID = b.ST_CALENDAR_MIDDLE_ID";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(userId)) {
            sql += " where a.ST_USER_ID =:userId";
            paramMap.put("userId", userId);
            sql += " or b.ST_USER_ID =:userId";
            paramMap.put("userId", userId);
        }
        SQL Sql = new SQL(sql, paramMap);
        return super.find(Sql);
    }

    public TCalendar getCalendarById(int id) {
        return (TCalendar)super.findUnique(new ObjectQuery(TCalendar.class,"stCalendarId",id));
    }

    public BaseRecords selectAttendUser(String middleId) {
        return super.find(new ObjectQuery(TCalendarAttendUser.class,"stCalendarMiddleId",middleId));
    }

    public TAddressListGroup getGroupById(int id) {
        return (TAddressListGroup)super.findUnique(new ObjectQuery(TAddressListGroup.class,"stAddressListGroupId",id));
    }

    public BaseRecords getGroupList(int id) {
        return super.find(new ObjectQuery(TAddressListGroup.class,"stAddressListGroupId",id));
    }

    public BaseRecords selectAddressListMiddleByGroupId(int groupId) {
        return super.find(new ObjectQuery(TAddressListMiddle.class,"stAddressListGroupId",groupId));
    }

    public BaseRecords getGroupDetail(int id) {
        String sql = "select distinct a.ST_USER_ID,a.ST_USER_NAME,c.ST_ADDRESS_LIST_GROUP_ID,c.ST_GROUP_NAME from t_user a LEFT JOIN t_address_list_middle b on a.ST_USER_ID = b.ST_USER_ID " +
                "LEFT JOIN t_address_list_group c on b.ST_ADDRESS_LIST_GROUP_ID = c.ST_ADDRESS_LIST_GROUP_ID";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(id)) {
            sql += " where c.ST_ADDRESS_LIST_GROUP_ID =:groupId";
            paramMap.put("groupId", id);
        }
        SQL Sql = new SQL(sql, paramMap);
        return super.find(Sql);
    }
}
