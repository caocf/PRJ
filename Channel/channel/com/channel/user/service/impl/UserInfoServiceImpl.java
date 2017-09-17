package com.channel.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.permission.PermissionService;
import com.channel.permission.impl.PermissionServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.stereotype.Service;
import com.channel.bean.Constants;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtUser;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.UserInfoDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.channel.user.service.DptService;
import com.channel.user.service.UserInfoService;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.Md5Secure;
import com.common.utils.tree.model.Tree;

@Service("userinfoservice")
public class UserInfoServiceImpl extends BaseService implements UserInfoService {
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "userinfodao")
    private UserInfoDao userInfoDao;
    @Resource(name = "dptservice")
    private DptService dptService;
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;
    @Resource
    private LogService logService;


    @Resource(name = "permissionService")
    private PermissionService permissionService;

    @Override
    public BaseResult queryUserInfo(int id) {
        CXtUser queryuser = this.userDao.queryUserById(id);
        CXtDpt dpts = this.dptdao.queryDptById(queryuser.getDepartment());
        Tree<CXtDpt> dpttree = this.dptService.findOnlyParentPath(dpts);
        StringBuffer s = new StringBuffer();
        Tree<CXtDpt> currDptTree = dpttree;
        while (true) {
            s.append(currDptTree.getNode().getName() + "/");
            if (currDptTree.getChildren().size() > 0) {
                currDptTree = currDptTree.getChildren().get(0);
            } else {
                break;
            }
        }
        s.deleteCharAt(s.length() - 1);//去除最后的/
        BaseQueryRecords<Object> baseQueryRecords = this.userInfoDao
                .queryUserInfo(id);
        BaseResultOK baseResultOK = new BaseResultOK(baseQueryRecords);
        baseResultOK.addToMap("dpts", s.toString());
        baseResultOK.addToMap("roles", this.permissionService.queryUserRoles(id));

        return baseResultOK;
    }

    @Override
    public BaseResult updateUserInfo(int id, String name, String tel,
                                     String email, String lawid) {
        CXtUser user = this.userDao.queryUserById(id);
        if (user != null) {
            user.setName(name);
            user.setTel(tel);
            user.setEmail(email);
            user.setLawid(lawid);
            user.setUpdatetime(new Date());
            this.userDao.update(user);
            logService.addLog(ModuleName.XXWH_YYGL, OpName.UPDATE, user.getName() + "的个人信息");
            BaseResultOK baseResultOK = new BaseResultOK();

            /**
             * 更新session信息
             */
            ActionContext actionContext = ActionContext.getContext();
            Map<String, Object> session = actionContext.getSession();
            session.put("user", user);
            return baseResultOK;
        } else {
            return new BaseResultFailed("用户不存在");
        }
    }

    @Override
    public BaseResult updateUserPassword(int id, String password,
                                         String oldpassword) {
        // TODO Auto-generated method stub
        CXtUser user = this.userDao.queryUserById(id);
        if (user == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTEXIST, "用户不存在");
        } else if (user.getUstatus() == Constants.USTATUS_DISABLE) {
            return new BaseResultFailed(Constants.RESULT_USER_DISABLE, "用户已禁用");
        } else if (user.getUstatus() == Constants.USTATUS_DELETE) {
            return new BaseResultFailed(Constants.RESULT_USER_DELETE, "用户已删除");
        } else if (!Md5Secure.encode(oldpassword).equals(user.getPassword())) {
            return new BaseResultFailed(Constants.RESULT_USERINFO_OLDPASSERROR,
                    "原密码错误");
        } else {
            user.setPassword(Md5Secure.encode(password));
            user.setUpdatetime(new Date());
            logService.addLog(ModuleName.XXWH_YYGL, OpName.UPDATE, user.getName() + "的密码");
            BaseResultOK baseResultOK = new BaseResultOK();
            return baseResultOK;
        }
    }

    @Override
    public BaseResult addUserInfo(CXtUser adduser, String roles) {
        CXtUser saveUser = this.userDao.queryUserByUsername(adduser.getUsername());
        if (saveUser != null) {
            return new BaseResult(Constants.RESULT_USERINFO_USEREXISTED, "用户已存在");
        } else {
            this.userInfoDao.addUserInfo(adduser);
            logService.addLog(ModuleName.XXWH_YYGL, OpName.ADD, adduser.getUsername());

            //添加用户时，添加用户在权限这边的映象
            this.permissionService.addUserRefect(adduser.getId());


            //如果可以，为该用户绑定角色
            if (roles != null && !roles.equals("")) {
                String[] rolesp = roles.split(",");
                for (String rolestr : rolesp) {
                    try {
                        if (rolestr != null && !rolestr.equals(""))
                            this.permissionService.bindRoleUser(Integer.parseInt(rolestr), adduser.getId());
                    } catch (Exception e) {

                    }
                }
            }

            return new BaseResultOK();
        }
    }

    @Override
    public BaseResult queryUserInfoByName(int department, String content, int page, int rows) {
        BaseQueryRecords<CXtUser> users = this.userInfoDao.queryUserInfoByName(department, content, page, rows);
        BaseResultOK baseResultOK = new BaseResultOK(users);
        return baseResultOK;
    }
}
