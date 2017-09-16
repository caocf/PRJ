package com.zjport.common.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.zjport.model.TApplication;
import com.zjport.model.TDepartment;
import com.zjport.model.TOrg;
import com.zjport.model.TUser;
import org.springframework.stereotype.Repository;

/**
 * Created by TWQ on 2016/9/5.
 */
@Repository("commonDao")
public class CommonDao extends BaseDaoDB{

    public BaseRecords selectAllOrg() {
        return super.find(new ObjectQuery(TOrg.class));
    }

    public BaseRecords selectAllDept() {
        return super.find(new ObjectQuery(TDepartment.class));
    }

    public BaseRecords selectAllUser() {
        return super.find(new ObjectQuery(TUser.class,"stOrder",false));
    }

    public TUser selectUserById (int userId) {
        return (TUser) super.findUnique(new ObjectQuery(TUser.class, "stUserId", userId));
    }

    public String getNameById(int id) {
        TUser user = (TUser)super.findUnique(new ObjectQuery(TUser.class,"stUserId",id));
        return user.getStUserName();
    }

    public TDepartment selectDeptById(String deptId) {
        return (TDepartment) super.findUnique(new ObjectQuery(TDepartment.class, "stDepartmentId", deptId));
    }

    public BaseRecords getOrgDetail(String id) {
        return super.find(new ObjectQuery(TOrg.class,"stOrgId",id));
    }

    public TOrg selectOrgById(String id) {
        return (TOrg)super.findUnique(new ObjectQuery(TOrg.class,"stOrgId",id));
    }

    public TUser selectUserBySyncId(String syncId) {
        return (TUser)super.findUnique(new ObjectQuery(TUser.class,"stUserSyncId",syncId));
    }

    public TApplication selectAppById(int appId) {
        return (TApplication)super.findUnique(new ObjectQuery(TApplication.class,"stApplicationId",appId));
    }
}
