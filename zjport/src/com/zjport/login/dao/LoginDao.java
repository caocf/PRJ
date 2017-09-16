package com.zjport.login.dao;

import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.zjport.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by TWQ on 2016/8/10.
 */
@Repository("loginDao")
public class LoginDao extends BaseDaoDB {

    public TUser selectUser(String id) {
        return (TUser) super.findUnique(new ObjectQuery(TUser.class, "stUserSyncId", id));
    }

    public TOrg selectOrg(String id) {
        return (TOrg) super.findUnique(new ObjectQuery(TOrg.class, "stOrgId", id));
    }

    public TDepartment selectDept(String id) {
        return (TDepartment) super.findUnique(new ObjectQuery(TDepartment.class, "stDepartmentId", id));
    }

    public TUser selectUserByAccount(String account) {
        return (TUser)super.findUnique(new ObjectQuery(TUser.class,"stAccount",account));
    }

    public List<TUserApplication> selectUserApp(int userId) {
        return (List<TUserApplication>)super.find(new ObjectQuery(TUserApplication.class,"stUserId",userId)).getData();
    }

    public TApplication selectApplication(int appId) {
        return (TApplication)super.findUnique(new ObjectQuery(TApplication.class,"stApplicationId",appId));
    }

    /*public List<SsoSystem> selectSsoSystem(int userId) {
        String sql = "select a.ST_SSO_SYSTEM_ID,a.ST_SYSTEM_NAME,a.ST_SYSTEM_URL,a.ST_SYSTEM_ICON,b.ST_SYSTEM_ACCOUNT from t_sso_system a LEFT JOIN " +
                "t_sso_system_middle b on a.ST_SSO_SYSTEM_ID=b.ST_SSO_SYSTEM_ID LEFT JOIN " +
                "t_sso_user c on b.ST_SYSTEM_MIDDLE_ID=c.ST_SYSTEM_MIDDLE_ID where c.ST_SSO_USER_ID=?";

        SQL Sql = new SQL(sql, userId);

        BaseRecords records = super.find(Sql);

        List<SsoSystem> list = new ArrayList<SsoSystem>();
        for(int i = 0; i<records.getData().size(); i++) {
            SsoSystem sso = new SsoSystem();
            Object[] ob = (Object[])records.getData().get(i);
            sso.setStSSOSystemId(Integer.parseInt(String.valueOf(ob[0])));
            sso.setStSystemName(String.valueOf(ob[1]));
            sso.setStSystemUrl(String.valueOf(ob[2]));
            sso.setStSystemIcon(String.valueOf(ob[3]));
            sso.setStSystemAccount(String.valueOf(ob[4]));
            list.add(sso);
        }

        return list;
    }*/
}
