package com.zjport.information.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.model.*;
import com.zjport.util.CommonConst;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TWQ on 2016/7/28.
 */
@Repository("inforDao")
public class InformationDao extends BaseDaoDB {

    //private Connection con = null;

    public BaseRecords selectAll(String type, String state, String search, int userId, int rows, int page) {
        String hql = "from TInformation where stIsValid='1'";
        //state优先级最高，判断state不为空先查，如果state为空则判断search是否为空，若为空则正常查询，不为空则模糊搜索
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(type)){
            hql += " and stType =:type";
            paramMap.put("type", type);
        }
        if(!StringUtils.isEmpty(userId)){
            hql += " and stInformFromId =:id";
            paramMap.put("id", userId);
        }
        if(!StringUtils.isEmpty(state)) {
            hql += " and stState =:state";
            paramMap.put("state", state);
        }
        if(!StringUtils.isEmpty(search)) {
            hql += " and (stInformTitle like:search";
            paramMap.put("search", "%"+search+"%");
            hql += " or stInformContent like:search)";
            paramMap.put("search", "%"+search+"%");
        }
        hql +=" order by stState,dtCreate desc";
        HQL Hql = new HQL(hql, paramMap);
        Hql.setPage(page);
        Hql.setRows(rows);
        return super.find(Hql);
    }

    public BaseRecords selectApprovalAll(String type, String state, String search, int userId, int rows, int page) {
        String hql = "from TInformation where stIsValid='1' and stState != '5'";
        //state优先级最高，判断state不为空先查，如果state为空则判断search是否为空，若为空则正常查询，不为空则模糊搜索
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(type)){
            hql += " and stType =:type";
            paramMap.put("type", type);
        }
        if(!StringUtils.isEmpty(userId)){
            hql += " and stApprovalUserId =:id";
            paramMap.put("id", userId);
        }
        if(!StringUtils.isEmpty(state)) {
            hql += " and stState =:state";
            paramMap.put("state", state);
        }
        if(!StringUtils.isEmpty(search)) {
            hql += " and (stInformTitle like:search";
            paramMap.put("search", "%"+search+"%");
            hql += " or stInformContent like:search)";
            paramMap.put("search", "%"+search+"%");
        }
        hql +=" order by stState,dtCreate desc";
        HQL Hql = new HQL(hql, paramMap);
        Hql.setPage(page);
        Hql.setRows(rows);
        return super.find(Hql);
    }

    public BaseRecords selectPersonalAll(String type, String state, String search, int userId, String deptId, int rows, int page) {
        String sql = "select DISTINCT a.* from T_Information a left join T_Scan_Department b on a.ST_SCAN_DEPART_MIDDLE_ID = b.ST_SCAN_DEPART_MIDDLE_ID " +
                     "left join T_Department c on b.ST_DEPARTMENT_ID = c.ST_DEPARTMENT_ID " +
                     "where a.ST_IS_VALID = '1' and a.ST_STATE != '5'";
        Map<String,Object> paramMap = new HashMap<>();
        //一个人的用户ID以及部门ID不会为空，顾不做第二次判断了
        if(!StringUtils.isEmpty(deptId) && !StringUtils.isEmpty(userId)){
            sql += " and (c.ST_DEPARTMENT_ID =:deptId";
            paramMap.put("deptId", deptId);
            sql += " or a.ST_APPROVAL_USER_ID =:userId";
            paramMap.put("userId", userId);
            sql += " or a.ST_INFORM_FROM_ID =:userId)";
            paramMap.put("userId", userId);

            /*sql += " or (a.ST_STATE = '5' && a.ST_INFORM_FROM_ID =:userId)";
            paramMap.put("userId", userId);*/
        }
        if(!StringUtils.isEmpty(type)){
            sql += " and a.ST_TYPE =:type";
            paramMap.put("type", type);
        }
        if(!StringUtils.isEmpty(state)) {
            sql += " and a.ST_STATE =:state";
            paramMap.put("state", state);
        }
        if(!StringUtils.isEmpty(search)) {
            sql += " and (a.ST_Inform_Title like:search";
            paramMap.put("search", "%"+search+"%");
            sql += " or ST_Inform_Content like:search)";
            paramMap.put("search", "%"+search+"%");
        }
        sql +=" order by a.ST_STATE,a.DT_CREATE DESC";
        //原sql注入的方式
        /*if(!"".equals(state) && state != null) {
            sql = "select DISTINCT a.* from T_Information a left join T_Scan_Department b on a.ST_SCAN_DEPART_MIDDLE_ID = b.ST_SCAN_DEPART_MIDDLE_ID " +
                    "left join T_Department c on b.ST_DEPARTMENT_ID = c.ST_DEPARTMENT_ID " +
                    "where a.ST_IS_VALID = '1' and (c.ST_DEPARTMENT_ID = '"+deptId+"' or a.ST_APPROVAL_USER_ID = "+userId+" or a.ST_INFORM_FROM_ID = "+userId+") " +
                    "and a.ST_TYPE = "+type+" and a.ST_STATE = "+state+" order by a.ST_STATE,a.DT_CREATE DESC";
        } else if("".equals(search) || search == null) {
            sql = "select DISTINCT a.* from T_Information a left join T_Scan_Department b on a.ST_SCAN_DEPART_MIDDLE_ID = b.ST_SCAN_DEPART_MIDDLE_ID " +
                    "left join T_Department c on b.ST_DEPARTMENT_ID = c.ST_DEPARTMENT_ID " +
                    "where a.ST_IS_VALID = '1' and (c.ST_DEPARTMENT_ID = '"+deptId+"' or a.ST_APPROVAL_USER_ID = "+userId+" or a.ST_INFORM_FROM_ID = "+userId+") " +
                    "and a.ST_TYPE = "+type+" order by a.ST_STATE,a.DT_CREATE DESC";
        } else {
            sql = "select DISTINCT a.* from T_Information a left join T_Scan_Department b on a.ST_SCAN_DEPART_MIDDLE_ID = b.ST_SCAN_DEPART_MIDDLE_ID " +
                    "left join T_Department c on b.ST_DEPARTMENT_ID = c.ST_DEPARTMENT_ID " +
                    "where a.ST_IS_VALID = '1' and (c.ST_DEPARTMENT_ID = '"+deptId+"' or a.ST_APPROVAL_USER_ID = "+userId+" or a.ST_INFORM_FROM_ID = "+userId+") " +
                    "and a.ST_TYPE = "+type+" and (a.ST_Inform_Title like '%"+search+"%' or ST_Inform_Content like '%"+search+"%') order by a.ST_STATE,a.DT_CREATE DESC";
        }*/
        SQL Sql = new SQL(sql,paramMap);
        Sql.setPage(page);
        Sql.setRows(rows);
        return super.find(Sql);
    }

    public BaseRecords getInfoById(int id) {
        String hql = "from TInformation where stIsValid='1'";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(id)) {
            hql += " and stInformId =:id";
            paramMap.put("id", id);
        }
        return super.find(new HQL(hql,paramMap));
    }

    public BaseRecords selectWebList() {
        String hql = "from TWebName where stIsValid='1'";
        return super.find(new HQL(hql));
    }

    public BaseRecords selectWeb(int webId) {
        String hql = "from TWebName where stIsValid='1'";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(webId)) {
            hql += " and stWebNameId =:webId";
            paramMap.put("webId", webId);
        }
        return super.find(new HQL(hql,paramMap));
    }

    public BaseRecords selectWebColumn(int webColumnId) {
        String hql = "from TWebColumn where stIsValid='1'";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(webColumnId)) {
            hql += " and stWebColumnId =:webColumnId";
            paramMap.put("webColumnId", webColumnId);
        }
        return super.find(new HQL(hql,paramMap));
    }

    public BaseRecords selectWebColumnList(int webId) {
        String hql = "from TWebColumn where stIsValid='1'";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(webId)) {
            hql += " and stWebNameId =:webId";
            paramMap.put("webId", webId);
        }
        return super.find(new HQL(hql,paramMap));
    }

    public void saveInformation(TInformation information) {
        super.saveOrUpdate(information);
    }

    public void changeState(int informId) {
        String sql =  "update T_INFORMATION";
        Map<String,Object> paramMap = new HashMap<>();
        sql += " set ST_STATE =:state";
        paramMap.put("state", CommonConst.InfoState_Approvaling);
        sql += " WHERE ST_INFORM_ID=:id";
        paramMap.put("id", informId);
        super.update(new SQL(sql,paramMap));
    }

    public TUser selectUserById (int userId) {
        return (TUser) super.findUnique(new ObjectQuery(TUser.class, "stUserId", userId));
    }

    public void saveAttachment(TAttachment attachment) {
        super.save(attachment);
    }

    public BaseRecords selectAttachment(String middleId) {
        return super.find(new ObjectQuery(TAttachment.class,"stAttachmentMiddleId",middleId));
    }

    //查询所有用户
    public BaseRecords selectApprovalUserList(int userId) {
        /*return super.find(new ObjectQuery(TUser.class));*/
        String sql =  "select a.ST_USER_ID,a.ST_USER_NAME from t_user a LEFT JOIN t_jc_jsqx b on a.ST_JS=b.jsbh LEFT JOIN t_jc_qx c on b.qxbh=c.qxbh where c.qxbh=9 and a.ST_USER_ID!=?";

        return super.find(new SQL(sql,userId));
    }

    public void approvalInform(TInformation information) {
        super.update(information);
    }

    public BaseRecords selectAllBoard() {
        return super.find(new ObjectQuery(TBoard.class));
    }

    public BaseRecords selectDepartmentList(String orgId) {
        return super.find(new ObjectQuery(TDepartment.class,"stOrgId",orgId));
    }

    //假删除，将信息有效位置0即可
    public void deleteInform(int informId) {
        String sql =  "update T_INFORMATION";
        Map<String,Object> paramMap = new HashMap<>();
        sql += " set ST_IS_VALID =:isvalid";
        paramMap.put("isvalid", CommonConst.INVALID);
        sql += " WHERE ST_INFORM_ID=:id";
        paramMap.put("id", informId);
        super.update(new SQL(sql,paramMap));
    }

    public void saveScanDepart(TScanDepartment scanDepart) {
        super.save(scanDepart);
    }

    public void recallInform(int informId) {
        String sql =  "update T_INFORMATION";
        Map<String,Object> paramMap = new HashMap<>();
        sql += " set ST_STATE =:state";
        paramMap.put("state", CommonConst.InfoState_Recall);
        sql += " WHERE ST_INFORM_ID=:id";
        paramMap.put("id", informId);
        super.update(new SQL(sql,paramMap));
    }

    public TWebName getWeb(int webId) {
        return (TWebName)super.findUnique(new ObjectQuery(TWebName.class,"stWebNameId",webId));
    }

    public TWebColumn getWebColumn(int webColumnId) {
        return (TWebColumn)super.findUnique(new ObjectQuery(TWebColumn.class,"stWebColumnId",webColumnId));
    }

    public BaseRecords selectDepartmentByMiddleId(String deptMiddleId) {
        return super.find(new ObjectQuery(TScanDepartment.class,"stScanDepartMiddleId",deptMiddleId));
    }
}
