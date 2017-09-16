package com.zjport.common.service;

import com.common.base.BaseRecords;
import com.common.base.service.BaseService;
import com.zjport.common.dao.CommonDao;
import com.zjport.model.TApplication;
import com.zjport.model.TDepartment;
import com.zjport.model.TOrg;
import com.zjport.model.TUser;
import com.zjport.util.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by TWQ on 2016/9/5.
 */
@Service("commonService")
public class CommonService extends BaseService{

    @Resource(name="commonDao")
    private CommonDao commonDao;

    public BaseRecords<TOrg> selectAllOrg() {
        return this.commonDao.selectAllOrg();
    }

    public BaseRecords<TDepartment> selectAllDept() {
        return this.commonDao.selectAllDept();
    }

    public BaseRecords<TUser> selectAllUser() {
        return this.commonDao.selectAllUser();
    }

    public String getUserName(int userId) {
        TUser user = this.commonDao.selectUserById(userId);
        return user.getStUserName();
    }

    public String getDeptName(String deptId) {
        TDepartment dept = this.commonDao.selectDeptById(deptId);
        return dept.getStDepartmentName();
    }

    public BaseRecords getOrgDetail(String id) {
        return this.commonDao.getOrgDetail(id);
    }

    public void updateOrgDetail(HttpServletRequest req) {
        String id = req.getParameter("orgId");
        String area = req.getParameter("orgArea");
        String phone = req.getParameter("orgPhone");
        String fox = req.getParameter("orgFox");
        String post = req.getParameter("orgPost");
        String address = req.getParameter("orgAddress");

        TOrg org = this.commonDao.selectOrgById(id);
        org.setStOrgAddress(address);
        org.setStOrgArea(area);
        org.setStOrgPhone(phone);
        org.setStOrgFox(fox);
        org.setStOrgPost(post);

        this.commonDao.saveOrUpdate(org);
    }

    public void updateUserDetail(HttpServletRequest req) {
        String id = req.getParameter("userId");
        String lawCode = req.getParameter("userLaw");
        String js = req.getParameter("userJs");
        String order = req.getParameter("userOrder");
        //String location = req.getParameter("location");

        TUser user = this.commonDao.selectUserById(Integer.valueOf(id));
        user.setStLawCode(lawCode);
        user.setStJs(Value.of(js,Value.INTEGER_NULL));
        user.setStOrder(Value.of(order,Value.INTEGER_NULL));
        this.commonDao.saveOrUpdate(user);
    }

    public void updateUserInfo(HttpServletRequest req) {
        String id = req.getParameter("userId");
        String lawCode = req.getParameter("lawCode");
        String location = req.getParameter("location");

        TUser user = this.commonDao.selectUserById(Integer.valueOf(id));
        user.setStLawCode(lawCode);
        user.setStLocation(location);
        this.commonDao.saveOrUpdate(user);
    }

    public TUser selectUserById(String id) {
        return this.commonDao.selectUserById(Integer.valueOf(id));
    }

    public void saveSyncUser(TUser user) {
        this.commonDao.saveOrUpdate(user);
    }

    public void saveSyncOrg(TOrg org) {
        this.commonDao.saveOrUpdate(org);
    }

    public void saveSyncDept(TDepartment dept) {
        this.commonDao.saveOrUpdate(dept);
    }

    public TUser getUserBySyncId(String syncId) {return this.commonDao.selectUserBySyncId(syncId);}

    public TOrg getOrgById(String orgId) {return this.commonDao.selectOrgById(orgId);}

    public TDepartment getDeptById(String deptId) {
        return this.commonDao.selectDeptById(deptId);
    }

    public TApplication getAppById(String appId) {return this.commonDao.selectAppById(Integer.valueOf(appId));}
}
