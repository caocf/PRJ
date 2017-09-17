package com.channel.user.service.impl;

import com.channel.bean.Constants;
import com.channel.bean.UserDT;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtUser;
import com.channel.model.user.CZdXzqhdm;
import com.channel.permission.PermissionService;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.channel.user.service.DptService;
import com.channel.user.service.UserService;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.Md5Secure;
import com.common.utils.PropertyLoader;
import com.common.utils.tree.model.Tree;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service("userservice")
public class UserServiceImpl extends BaseService implements UserService {
    @Resource
    private LogService logService;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;
    @Resource(name = "dptservice")
    private DptService dptService;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;

    @Resource(name = "permissionService")
    private PermissionService permissionService;

    @Override
    public BaseResult queryVersion() {
        // TODO Auto-generated method stub
        Properties properties = PropertyLoader.getPropertiesFromClassPath(
                "version.properties", "utf8");
        String versionname = properties.getProperty("version.name");
        String versioncode = properties.getProperty("version.code");
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("versionname", versionname);
        baseResultOK.addToMap("versioncode", versioncode);
        return baseResultOK;
    }

    @Override
    public BaseResult login(String username, String password) {
        // TODO Auto-generated method stub
        CXtUser user = this.userDao.queryUserByUsername(username);
        if (user == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTEXIST, "用户不存在");
        } else if (user.getUstatus() == Constants.USTATUS_DISABLE) {
            return new BaseResultFailed(Constants.RESULT_USER_DISABLE, "用户已禁用");
        } else if (user.getUstatus() == Constants.USTATUS_DELETE) {
            return new BaseResultFailed(Constants.RESULT_USER_DELETE, "用户已删除");
        } else if (!Md5Secure.encode(password).equals(user.getPassword())) {
            return new BaseResultFailed(Constants.RESULT_USER_PASSERROR, "密码错误");
        } else {
            HttpSession session = getNewSession();
            //ActionContext actionContext = ActionContext.getContext();
            //Map<String, Object> session = actionContext.getSession();
            //将当前用户信息存入session
            session.setAttribute("user", user);

            //将用户所在部门信息存入session
            CXtDpt dpt = this.dptdao.queryDptById(user.getDepartment());
            session.setAttribute("dpt", dpt);

            CZdXzqhdm dptxzqh = this.xzqhdmdao.queryXzqhdmById(dpt.getXzqh());
            session.setAttribute("dptxzqh", dptxzqh);

            //将所在部门所处的市行政区划存入
            CZdXzqhdm dptshixzqh = this.xzqhdmService.queryShiXzqh(dpt.getXzqh());
            session.setAttribute("dptshixzqh", dptshixzqh);

            //将用户所在部门所在的市局存入session,如果存在的话，省局用户不存在
            CXtDpt shiju = this.dptService.queryShiDpt(dpt.getId());
            session.setAttribute("shiju", shiju);

            //将用户所在的处（地方局）存入session，如果存在的话，省市局用户不存在
            CXtDpt chuju = this.dptService.queryChuDpt(dpt.getId());
            session.setAttribute("chuju", chuju);

            //将用户所在部门管辖行政区划信息存入session
            List<CZdXzqhdm> manxzqh = this.dptService.queryDptManXzqh(dpt.getId());
            session.setAttribute("manxzqh", manxzqh);

            BaseResultOK baseResultOK = new BaseResultOK();
            //baseResultOK.addToMap("user", user);
            user.setUpdatetime(new Date());

            logService.addLog(ModuleName.XXWH_YYGL, OpName.LOGIN, "");

            return baseResultOK;
        }
    }

    @Override
    public BaseResult initPerm() throws UnsupportedEncodingException {
        ActionContext actionContext = ActionContext.getContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
        request.setCharacterEncoding("utf-8");
        HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("utf-8");
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        String account = principal.getName();
        String utfaccount = new String(account.getBytes("gbk"), "utf-8");
        Map<String, Object> session = actionContext.getSession();
        CXtUser user = new CXtUser();
//        if ("guest".equals(account)) {
//            user = this.userDao.queryUserByUsername("汤伟强");
//        }
        user = this.userDao.queryUserByUsername(utfaccount);
        BaseResultOK baseResultOK = new BaseResultOK();
        if (user == null) {
            session.put("user", null);
        } else {
            //将当前用户信息存入session
            session.put("user", user);

            //将用户所在部门信息存入session
            CXtDpt dpt = this.dptdao.queryDptById(user.getDepartment());
            session.put("dpt", dpt);

            CZdXzqhdm dptxzqh = this.xzqhdmdao.queryXzqhdmById(dpt.getXzqh());
            session.put("dptxzqh", dptxzqh);

            //将所在部门所处的市行政区划存入
            CZdXzqhdm dptshixzqh = this.xzqhdmService.queryShiXzqh(dpt.getXzqh());
            session.put("dptshixzqh", dptshixzqh);

            //将用户所在部门所在的市局存入session,如果存在的话，省局用户不存在
            CXtDpt shiju = this.dptService.queryShiDpt(dpt.getId());
            session.put("shiju", shiju);

            //将用户所在的处（地方局）存入session，如果存在的话，省市局用户不存在
            CXtDpt chuju = this.dptService.queryChuDpt(dpt.getId());
            session.put("chuju", chuju);

            //将用户所在部门管辖行政区划信息存入session
            List<CZdXzqhdm> manxzqh = this.dptService.queryDptManXzqh(dpt.getId());
            session.put("manxzqh", manxzqh);
            baseResultOK.addToMap("user", user);
            user.setUpdatetime(new Date());
        }
        return baseResultOK;
    }

    @Override
    public BaseResult logout() {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        user.setUpdatetime(new Date());
        logService.addLog(ModuleName.XXWH_YYGL, OpName.LOGOUT, "");
        //登出时，清空所有临时数据
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        session.clear();
//        HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
//        HttpSession hs = request.getSession();
//        hs.invalidate();
        return new BaseResultOK();
    }

    @Override
    public BaseResult disableUser(int id) {
        CXtUser queryuser = this.userDao.queryUserById(id);
        queryuser.setUstatus(Constants.USTATUS_DISABLE);
        queryuser.setUpdatetime(new Date());
        logService.addLog(ModuleName.XXWH_YYGL, OpName.UPDATE, queryuser.getName() + ":禁用");

        /**
         * 更新session信息
         */
        CXtUser loginuser = (CXtUser) getSession().getAttribute("user");
        if (loginuser.getId() == id) {
            getSession().setAttribute("user", this.userDao.queryUserById(loginuser.getId()));
        }

        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult enableUser(int id) {
        CXtUser queryuser = this.userDao.queryUserById(id);
        queryuser.setUstatus(Constants.USTATUS_ENABLE);
        queryuser.setUpdatetime(new Date());
        logService.addLog(ModuleName.XXWH_YYGL, OpName.UPDATE, queryuser.getName() + ":激活");
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult delUser(String strids) {
        String[] ids = strids.split(",");
        CXtUser loginuser = (CXtUser) getSession().getAttribute("user");
        boolean flag = false;
        for (String id : ids) {
            if (loginuser.getId() == Integer.parseInt(id))
                flag = true;
            CXtUser queryuser = this.userDao.queryUserById(Integer
                    .parseInt(id));
            if (queryuser != null) {
                queryuser.setUstatus(Constants.RESULT_USER_DELETE);
                queryuser.setUpdatetime(new Date());

                //删除用户时，删除用户在权限系统中的映象
                this.permissionService.delUserRefect(queryuser.getId());
            }
        }
        logService.addLog(ModuleName.XXWH_YYGL, OpName.DEL, "");


        /**
         * 更新session信息
         */
        if (flag) {
            getSession().setAttribute("user", this.userDao.queryUserById(loginuser.getId()));
        }

        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateUser(int id, String username,
                                 String password, String name, String tel, String email, int title,
                                 int jstatus, String lawid, String roles) {
        CXtUser updateuser = this.userDao.queryUserById(id);
        if (!username.equals(updateuser.getUsername())) {
            CXtUser existeduser = this.userDao
                    .queryUserByUsername(username);
            if (existeduser != null) {
                return new BaseResultFailed(
                        Constants.RESULT_USERINFO_USEREXISTED, "添加用户已存在");
            }
        }
        updateuser.setUsername(username);
        if (password != null && !"".equals(password))
            updateuser.setPassword(Md5Secure.encode(password));

        updateuser.setName(name);
        updateuser.setTel(tel);
        updateuser.setEmail(email);
        updateuser.setTitle(title);
        updateuser.setJstatus(jstatus);
        updateuser.setLawid(lawid);
        updateuser.setUpdatetime(new Date());
        this.userDao.save(updateuser);
        logService.addLog(ModuleName.XXWH_YYGL, OpName.UPDATE, updateuser.getName());

        /**
         * 更新session信息
         */
        CXtUser loginuser = (CXtUser) getSession().getAttribute("user");
        if (loginuser.getId() == id) {
            getSession().setAttribute("user", this.userDao.queryUserById(loginuser.getId()));
        }

        //更新角色信息
        //如果可以，为该用户绑定角色
        if (roles != null && !roles.equals("")) {
            this.permissionService.delBindUserRoles(id);
            String[] rolesp = roles.split(",");
            for (String rolestr : rolesp) {
                try {
                    if (rolestr != null && !rolestr.equals(""))
                        this.permissionService.bindRoleUser(Integer.parseInt(rolestr), id);
                } catch (Exception e) {
                }
            }
        }

        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult queryUser(int department, int dptflag, String content,
                                int page, int rows, String Echo) {
        CXtDpt dpt = this.dptdao.queryDptById(department);
        if (dpt == null) {
            BaseResultOK baseResultOK = new BaseResultOK(new ArrayList<UserDT>());
            baseResultOK.addToMap("sEcho", Echo);
            baseResultOK.addToMap("total", 0);
            return baseResultOK;
        } else {
            int type = dpt.getType();
            List<Object[]> users = null;
            List<UserDT> userDTs = new ArrayList<UserDT>();
            int total = 0;
            String dpts = String.valueOf(department);
            if (dptflag == 1) {
                List<CXtDpt> dptlist = this.dptService.findChildrenNodes_r(dpt);
                if (dptlist != null && dptlist.size() > 0) {
                    StringBuffer sb = new StringBuffer("");
                    for (CXtDpt tempdpt : dptlist) {
                        sb.append(tempdpt.getId() + ",");
                    }
                    if (!"".equals(sb.toString())) {
                        sb.append(dpt.getId());
                    }
                    dpts = sb.toString();
                }
            }
            BaseQueryRecords<Object[]> records = this.userDao.queryUser(
                    dpts, content, page, rows);
            users = (List<Object[]>) records.getData();
            total = (int) records.getTotal();
            if (users.size() != 0) {
                for (Object[] objects : users) {
                    UserDT userDT = new UserDT();
                    userDT.setArruser(objects);
                    userDT.setStrdpt(queryStrDpt((CXtDpt) objects[1]));
                    userDTs.add(userDT);
                }
            }

            BaseResultOK baseResultOK = new BaseResultOK(userDTs);
            baseResultOK.addToMap("sEcho", Echo);
            baseResultOK.addToMap("total", total);
            return baseResultOK;
        }
    }

    public String queryStrDpt(CXtDpt dpt) {
        if (dpt.getType() == Constants.DPT_SHENJU)
            return dpt.getName();

        Tree<CXtDpt> dpttree = this.dptService.findOnlyParentPath(dpt);// 所属机构
        StringBuffer dpts = new StringBuffer();
        Tree<CXtDpt> currDptTree = dpttree;
        while (true) {
            if (currDptTree.getNode().getType() != Constants.DPT_SHENJU)
                dpts.append(currDptTree.getNode().getName() + "/");
            if (currDptTree.getChildren().size() > 0) {
                currDptTree = currDptTree.getChildren().get(0);
            } else {
                break;
            }
        }
        dpts.deleteCharAt(dpts.length() - 1);// 去除最后的/
        return dpts.toString();
    }

}
