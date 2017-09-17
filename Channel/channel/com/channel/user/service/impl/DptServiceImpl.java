package com.channel.user.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.perm.Permission;
import com.channel.model.user.CXtUser;
import com.channel.permission.PermissionService;
import com.channel.permission.impl.PermissionServiceImpl;
import org.springframework.stereotype.Service;

import com.channel.bean.Constants;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtDptRela;
import com.channel.model.user.CZdXzqhdm;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.channel.user.service.DptService;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.utils.tree.TreeDao;
import com.common.utils.tree.impl.TreeServiceImpl;
import com.common.utils.tree.model.Tree;

@Service("dptservice")
public class DptServiceImpl extends TreeServiceImpl<CXtDpt, CXtDptRela>
        implements DptService {
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;
    @Resource
    private LogService logService;
    @Resource(name = "permissionService")
    private PermissionService permissionService;

    @Override
    public TreeDao<CXtDpt, CXtDptRela> getTreeDao() {
        // TODO Auto-generated method stub
        return dptdao;
    }

    @Override
    public BaseResult queryRoot() {
        List<CXtDpt> dptlist = new ArrayList<>();
        BaseQueryRecords<CXtDpt> dpt = this
                .findRootNodes();
        for (CXtDpt dpti : dpt.getData()) {
            if (dpti.getType() > 0) {
                dptlist.add(dpti);
            }
        }
        BaseQueryRecords<CZdXzqhdm> xzqhdm = this.xzqhdmService
                .findRootNodes(Constants.XZQH_KINDS);
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("dpt", new BaseQueryRecords<CXtDpt>(dptlist));
        baseResultOK.addToMap("xzqhdm", xzqhdm);
        return baseResultOK;
    }

    @Override
    public BaseResult queryAllDpt(int id, boolean isroot) {
        if (isroot) {//如果加载的是根节点,即要显示该节点
            if (id == -1) { //如果ID为-1表示全部节点，则根节点应该为rootnode
                List<CXtDpt> dptlist = new ArrayList<>();
                BaseQueryRecords<CXtDpt> rootnodes = this.findRootNodes();
                for (CXtDpt edpt : rootnodes.getData()) {
                    edpt.setSubcnt(super.countChildrenNode(edpt));
                }
                for (CXtDpt dpti : rootnodes.getData()) {
                    if (dpti.getType() == Constants.DPT_SHENJU) {
                        dptlist.add(dpti);
                    }
                }
                BaseQueryRecords<CXtDpt> records = new BaseQueryRecords<CXtDpt>(dptlist);
                BaseResultOK baseResultOK = new BaseResultOK(records);
                Collections.sort(records.getData(), new Comparator<CXtDpt>() {
                    @Override
                    public int compare(CXtDpt o1, CXtDpt o2) {
                        return (int) (o1.getCreatetime().getTime() - o2.getCreatetime().getTime());
                    }
                });
                return baseResultOK;
            } else {
                CXtDpt dpt = this.dptdao.queryDptById(id);
                if (dpt == null) {
                    return new BaseResultFailed(Constants.RESULT_USER_NOTDPT,
                            "用户管理机构不存在");
                } else {
                    dpt.setSubcnt(super.countChildrenNode(dpt));
                    List<CXtDpt> dpts = new ArrayList<>();
                    dpts.add(dpt);
                    return new BaseResultOK(new BaseQueryRecords<>(dpts));
                }
            }
        } else {
            if (id == -1) {
                List<CXtDpt> dptlist = new ArrayList<>();
                BaseQueryRecords<CXtDpt> rootnodes = this.findRootNodes();
                for (CXtDpt edpt : rootnodes.getData()) {
                    edpt.setSubcnt(super.countChildrenNode(edpt));
                }
                for (CXtDpt dpti : rootnodes.getData()) {
                    if (dpti.getType() == Constants.DPT_SHENJU) {
                        dptlist.add(dpti);
                    }
                }
                BaseQueryRecords<CXtDpt> records = new BaseQueryRecords<CXtDpt>(dptlist);
                BaseResultOK baseResultOK = new BaseResultOK(records);
                Collections.sort(records.getData(), new Comparator<CXtDpt>() {
                    @Override
                    public int compare(CXtDpt o1, CXtDpt o2) {
                        return (int) (o1.getCreatetime().getTime() - o2.getCreatetime().getTime());
                    }
                });
                return baseResultOK;
            } else {
                CXtDpt dpt = this.dptdao.queryDptById(id);
                if (dpt == null) {
                    return new BaseResultFailed(Constants.RESULT_USER_NOTDPT,
                            "用户管理机构不存在");
                } else {
                    if (!super.ifNodeLeaf(dpt)) {
                        BaseQueryRecords<CXtDpt> childnodes = this
                                .findChildrenNodes(dpt);
                        for (CXtDpt edpt : childnodes.getData()) {
                            edpt.setSubcnt(super.countChildrenNode(edpt));
                        }
                        Collections.sort(childnodes.getData(), new Comparator<CXtDpt>() {
                            @Override
                            public int compare(CXtDpt o1, CXtDpt o2) {
                                return (int) (o1.getCreatetime().getTime() - o2.getCreatetime().getTime());
                            }
                        });
                        BaseResultOK baseResultOK = new BaseResultOK(childnodes);
                        return baseResultOK;
                    } else {
                        return new BaseResultFailed();
                    }
                }
            }
        }
    }

    @Override
    public BaseResult queryTreeDpt(int id, boolean isroot) {
        if (id == -1) {
            List<CXtDpt> dptlist = new ArrayList<>();
            BaseQueryRecords<CXtDpt> rootnodes = this.findRootNodes();
            for (CXtDpt edpt : rootnodes.getData()) {
                edpt.setSubcnt(super.countChildrenNode(edpt));
            }
            for (CXtDpt dpti : rootnodes.getData()) {
                if (dpti.getType() == Constants.DPT_SHENJU) {
                    dptlist.add(dpti);
                }
            }
            BaseQueryRecords<CXtDpt> records = new BaseQueryRecords<CXtDpt>(dptlist);
            BaseResultOK baseResultOK = new BaseResultOK(records);
            Collections.sort(records.getData(), new Comparator<CXtDpt>() {
                @Override
                public int compare(CXtDpt o1, CXtDpt o2) {
                    return (int) (o1.getCreatetime().getTime() - o2.getCreatetime().getTime());
                }
            });
            return baseResultOK;
        } else {
            if (isroot) {
                List<CXtDpt> dptlist = new ArrayList<>();
                CXtDpt dpt = this.dptdao.queryDptById(id);
                if (dpt == null) {
                    return new BaseResultFailed(Constants.RESULT_USER_NOTDPT,
                            "用户管理机构不存在");
                } else {
                    dpt.setSubcnt(super.countChildrenNode(dpt));
                    dptlist.add(dpt);
                }
                BaseQueryRecords<CXtDpt> records = new BaseQueryRecords<CXtDpt>(dptlist);
                BaseResultOK baseResultOK = new BaseResultOK(records);
                return baseResultOK;
            } else {
                CXtDpt dpt = this.dptdao.queryDptById(id);
                if (dpt == null) {
                    return new BaseResultFailed(Constants.RESULT_USER_NOTDPT,
                            "用户管理机构不存在");
                } else {
                    if (!super.ifNodeLeaf(dpt)) {
                        BaseQueryRecords<CXtDpt> childnodes = this
                                .findChildrenNodes(dpt);
                        for (CXtDpt edpt : childnodes.getData()) {
                            edpt.setSubcnt(super.countChildrenNode(edpt));
                        }
                        Collections.sort(childnodes.getData(), new Comparator<CXtDpt>() {
                            @Override
                            public int compare(CXtDpt o1, CXtDpt o2) {
                                return (int) (o1.getCreatetime().getTime() - o2.getCreatetime().getTime());
                            }
                        });
                        BaseResultOK baseResultOK = new BaseResultOK(childnodes);
                        return baseResultOK;
                    } else {
                        return new BaseResultFailed();
                    }
                }
            }
        }

    }

    @Override
    public BaseResult queryXzqhDpt(int id, int xzqh) {
        if (id == -1) {
            List<CXtDpt> dptlist = new ArrayList<>();
            CXtDpt dpt = new CXtDpt();
            CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
            boolean isroot = this.xzqhdmService.ifNodeRoot(xzqhdm);
            if (isroot) {
                dpt = this.dptdao.queryDptByXzqh(xzqh, Constants.DPT_SHENJU);
            } else {
                boolean isleaf = this.xzqhdmService.ifNodeLeaf(xzqhdm);
                if (isleaf) {
                    dpt = this.dptdao.queryDptByXzqh(xzqh, Constants.DPT_CHU);
                } else {
                    dpt = this.dptdao.queryDptByXzqh(xzqh, Constants.DPT_SHIJU);
                }
            }
          /*  CXtUser user = (CXtUser) getSession().getAttribute("user");
            dpt = this.dptdao.queryDptById(user.getDepartment());*/
            dpt.setSubcnt(super.countChildrenNode(dpt));
            dptlist.add(dpt);
            BaseQueryRecords<CXtDpt> records = new BaseQueryRecords<CXtDpt>(dptlist);
            BaseResultOK baseResultOK = new BaseResultOK(records);
            return baseResultOK;
        } else {
            CXtDpt dpt = this.dptdao.queryDptById(id);
            if (dpt == null) {
                return new BaseResultFailed(Constants.RESULT_USER_NOTDPT,
                        "用户管理机构不存在");
            } else {
                if (!super.ifNodeLeaf(dpt)) {
                    BaseQueryRecords<CXtDpt> childnodes = this
                            .findChildrenNodes(dpt);
                    for (CXtDpt edpt : childnodes.getData()) {
                        edpt.setSubcnt(super.countChildrenNode(edpt));
                    }
                    Collections.sort(childnodes.getData(), new Comparator<CXtDpt>() {
                        @Override
                        public int compare(CXtDpt o1, CXtDpt o2) {
                            return (int) (o1.getCreatetime().getTime() - o2.getCreatetime().getTime());
                        }
                    });
                    BaseResultOK baseResultOK = new BaseResultOK(childnodes);
                    return baseResultOK;
                } else {
                    return new BaseResultFailed();
                }
            }
        }

    }

    @Override
    public BaseResult queryDptInfo(int id) {
        CXtDpt dpt = this.dptdao.queryDptById(id);
        if (dpt == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTDPT,
                    "用户管理机构不存在");
        } else {
            Tree<CXtDpt> dpttree = this.findOnlyParentPath(dpt);// 所属机构
            StringBuffer dpts = new StringBuffer();
            Tree<CXtDpt> currDptTree = dpttree;
            while (true) {
                dpts.append(currDptTree.getNode().getName() + "/");
                if (currDptTree.getChildren().size() > 0) {
                    currDptTree = currDptTree.getChildren().get(0);
                } else {
                    break;
                }

            }
            dpts.deleteCharAt(dpts.length() - 1);// 去除最后的/
            int charindex = 0;
            charindex = dpts.lastIndexOf("/");
            String s = "";
            int pid = 0;
            if (charindex != -1) {
                s = dpts.substring(0, charindex);
                pid = this.findParentNodes(dpt).getData().get(0).getId();
            }
            CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(dpt.getXzqh());// 获得行政区划
            Tree<CZdXzqhdm> xzqhdmtree = this.xzqhdmService
                    .findOnlyParentPath(xzqhdm);// 行政区划
            StringBuffer xzqhdms = new StringBuffer();
            Tree<CZdXzqhdm> currXzqhdmTree = xzqhdmtree;
            while (true) {
                xzqhdms.append(currXzqhdmTree.getNode().getName() + "/");
                if (currXzqhdmTree.getChildren().size() > 0) {
                    currXzqhdmTree = currXzqhdmTree.getChildren().get(0);
                } else {
                    break;
                }

            }

            xzqhdms.deleteCharAt(xzqhdms.length() - 1);// 去除最后的/
            BaseResultOK baseResultOK = new BaseResultOK();
            baseResultOK.addToMap("dpt", dpt);
            baseResultOK.addToMap("dpts", s);
            baseResultOK.addToMap("pid", pid);
            baseResultOK.addToMap("xzqhs", xzqhdms.toString());
            baseResultOK.addToMap("dpttree", dpttree);
            baseResultOK.addToMap("xzqhdmtree", xzqhdmtree);

            List<String> defaultxzqhs = new ArrayList<>();
            //将角色信息写入
            String defaultxzqhsstr = dpt.getManagexzqh();
            if (defaultxzqhsstr != null && !defaultxzqhsstr.equals("")) {
                try {
                    String[] defaultxzqhsp = defaultxzqhsstr.split(",");

                    for (String defaultxzqh : defaultxzqhsp) {
                        if (defaultxzqh != null && !defaultxzqh.equals("")) {
                            CZdXzqhdm cZdXzqhdm = this.xzqhdmdao.queryXzqhdmById(Integer.parseInt(defaultxzqh));
                            if (cZdXzqhdm != null) {
                                defaultxzqhs.add(cZdXzqhdm.getName());
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
            baseResultOK.addToMap("defaultxzqhs", defaultxzqhs);

            List<String> defaultroles = new ArrayList<>();
            //将角色信息写入
            String defaultrolesstr = dpt.getDefaultroles();
            if (defaultrolesstr != null && !defaultrolesstr.equals("")) {
                try {
                    String[] defaultrolesp = defaultrolesstr.split(",");

                    for (String defaultrole : defaultrolesp) {
                        if (defaultrole != null && !defaultrole.equals("")) {
                            Permission role = this.permissionService.findNode(new Permission(Integer.parseInt(defaultrole)), PermissionServiceImpl.PERM_ROLE_TYPE);
                            if (role != null) {
                                defaultroles.add(role.getName());
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
            baseResultOK.addToMap("defaultroles", defaultroles);

            return baseResultOK;

        }
    }

    @Override
    public BaseResult delDpt(int id) {
        CXtDpt dpt = this.dptdao.queryDptById(id);
        if (dpt == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTDPT,
                    "用户管理机构不存在");
        } else {
            // 如果不是叶子节点，判断下面还有没有子组织机构
            if (!ifNodeLeaf(dpt)) {
                return new BaseResultFailed(Constants.RESULT_DPT_OTHERDPT,
                        "该管理机构下存在未删除机构");// 机构下还有其他机构不能删除
            } else {
                long countuser = this.userDao.countUserByDpt(id);
                if (countuser != 0) {
                    return new BaseResultFailed(Constants.RESULT_DPT_OTHERUSER,
                            "该管理机构下存在未删除用户");// 该管理机构下存在未删除用户
                } else {
                    logService.addLog(ModuleName.XXWH_ZZJG, OpName.DEL, dpt.getName());
                    super.delNode(dpt);
                }
            }
            BaseResultOK baseResultOK = new BaseResultOK();
            return baseResultOK;

        }
    }

    @Override
    public BaseResult addDpt(String name, int xzqh,
                             String dptdesc, int pid, List<Integer> manxzqhs, List<Integer> defaultroles) {
        CXtDpt pdpt = this.dptdao.queryDptById(pid);

        // 添加机构
        CXtDpt dpt = new CXtDpt();
        if (pdpt == null)
            dpt.setType(Constants.DPT_SHENJU);
        else {
            if (pdpt.getType() == Constants.DPT_SHENJU)
                dpt.setType(Constants.DPT_SHIJU);

            if (pdpt.getType() == Constants.DPT_SHIJU)
                dpt.setType(Constants.DPT_CHU);

            if (pdpt.getType() == Constants.DPT_CHU)
                dpt.setType(Constants.DPT_ZHAN);

            if (pdpt.getType() == Constants.DPT_ZHAN)
                return new BaseResultFailed("无法在站下添加");
        }
        dpt.setName(name);
        dpt.setXzqh(xzqh);
        dpt.setDptdesc(dptdesc);

        //配置在该部门下新增用户时的默认行政区划列表
        if (manxzqhs != null && manxzqhs.size() > 0) {
            String xzqhstr = "";
            for (int i = 0; i < manxzqhs.size(); i++) {
                xzqhstr += manxzqhs.get(i);
                if (i != manxzqhs.size() - 1)
                    xzqhstr += ",";
            }
            dpt.setManagexzqh(xzqhstr);
        }

        //配置在该部门下新增用户时的默认角色选择列表
        if (defaultroles != null && defaultroles.size() > 0) {
            String rolestr = "";
            for (int i = 0; i < defaultroles.size(); i++) {
                rolestr += defaultroles.get(i);
                if (i != defaultroles.size() - 1)
                    rolestr += ",";
            }
            dpt.setDefaultroles(rolestr);
        }

        dpt.setCreatetime(new Date());
        dpt.setUpdatetime(new Date());
        this.dptdao.addDpt(dpt);

        logService.addLog(ModuleName.XXWH_ZZJG, OpName.ADD, dpt.getName());


        if (pdpt != null) {
            // 添加机构关联
            CXtDptRela dptRela = new CXtDptRela();
            dptRela.setPid(pid);
            dptRela.setSid(dpt.getId());
            this.dptdao.addDptRela(dptRela);
        }
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("dpt", dpt);
        return baseResultOK;
    }

    @Override
    public BaseResult updateDpt(int id, String name,
                                int xzqh, String dptdesc, List<Integer> manxzqhs, List<Integer> defaultroles) {
        CXtDpt dpt = this.dptdao.queryDptById(id);
        if (dpt == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTDPT, "机构不存在");
        } else {
            // 更新机构
            dpt.setName(name);
            dpt.setXzqh(xzqh);
            dpt.setDptdesc(dptdesc);
            dpt.setUpdatetime(new Date());

            //配置在该部门下新增用户时的默认行政区划列表
            if (manxzqhs != null && manxzqhs.size() > 0) {
                String xzqhstr = "";
                for (int i = 0; i < manxzqhs.size(); i++) {
                    xzqhstr += manxzqhs.get(i);
                    if (i != manxzqhs.size() - 1)
                        xzqhstr += ",";
                }
                dpt.setManagexzqh(xzqhstr);
            } else {
                dpt.setManagexzqh(null);
            }

            //配置在该部门下新增用户时的默认角色选择列表
            if (defaultroles != null && defaultroles.size() > 0) {
                String rolestr = "";
                for (int i = 0; i < defaultroles.size(); i++) {
                    rolestr += defaultroles.get(i);
                    if (i != defaultroles.size() - 1)
                        rolestr += ",";
                }
                dpt.setDefaultroles(rolestr);
            } else {
                dpt.setDefaultroles(null);
            }

            logService.addLog(ModuleName.XXWH_ZZJG, OpName.UPDATE, dpt.getName());
            BaseResultOK baseResultOK = new BaseResultOK();
            return baseResultOK;
        }
    }

    @Override
    public String queryDptName(int id) {
        // TODO Auto-generated method stub
        CXtDpt dpt = this.dptdao.queryDptById(id);
        String name = "";
        if (dpt != null) {
            Tree<CXtDpt> dpttree = this.findOnlyParentPath(dpt);// 所属机构
            StringBuffer dpts = new StringBuffer();
            Tree<CXtDpt> currDptTree = dpttree;
            while (true) {
                dpts.append(currDptTree.getNode().getName() + "/");
                if (currDptTree.getChildren().size() > 0) {
                    currDptTree = currDptTree.getChildren().get(0);
                } else {
                    break;
                }

            }
            dpts.deleteCharAt(dpts.length() - 1);// 去除最后的/
            name = dpts.toString();
        }
        return name;
    }

    @Override
    public List<CZdXzqhdm> queryDptManXzqh(int id) {
        List<CZdXzqhdm> ret = new ArrayList<>();
        CXtDpt dpt = this.dptdao.queryDptById(id);
        if (dpt != null && dpt.getManagexzqh() != null &&
                !dpt.getManagexzqh().equals("") &&
                !dpt.getManagexzqh().toLowerCase().equals("null")) {
            String manxzqhstr = dpt.getManagexzqh();
            String[] xzqhids = manxzqhstr.split(",");
            for (String xzqhid : xzqhids) {
                if (xzqhid != null && !xzqhid.equals("")) {
                    CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(Integer.parseInt(xzqhid));
                    if (xzqhdm != null)
                        ret.add(xzqhdm);
                }
            }
        }
        return ret;
    }

    @Override
    public CXtDpt queryShiDpt(int id) {
        CXtDpt dpt = this.dptdao.queryDptById(id);
        if (dpt == null)
            return null;
        if (dpt.getType() == Constants.DPT_SHIJU)
            return dpt;
        List<CXtDpt> shidpts = this.findParentNodes_r(dpt, Constants.DPT_SHIJU);
        if (shidpts == null || shidpts.size() == 0)
            return null;
        return shidpts.get(0);
    }

    @Override
    public CXtDpt queryChuDpt(int id) {
        CXtDpt dpt = this.dptdao.queryDptById(id);
        if (dpt == null)
            return null;
        if (dpt.getType() == Constants.DPT_CHU)
            return dpt;
        List<CXtDpt> shidpts = this.findParentNodes_r(dpt, Constants.DPT_CHU);
        if (shidpts == null || shidpts.size() == 0)
            return null;
        return shidpts.get(0);
    }

    @Override
    public BaseResult queryDptDefaultRoles(int dptid) {
        CXtDpt dpt = this.dptdao.queryDptById(dptid);
        if (dpt == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTDPT,
                    "用户管理机构不存在");
        } else {
            List<Permission> defaultroles = new ArrayList<>();
            //将角色信息写入
            String defaultrolesstr = dpt.getDefaultroles();
            if (defaultrolesstr != null && !defaultrolesstr.equals("")) {
                try {
                    String[] defaultrolesp = defaultrolesstr.split(",");

                    for (String defaultrole : defaultrolesp) {
                        if (defaultrole != null && !defaultrole.equals("")) {
                            Permission role = this.permissionService.findNode(new Permission(Integer.parseInt(defaultrole)), PermissionServiceImpl.PERM_ROLE_TYPE);
                            if (role != null) {
                                defaultroles.add(role);
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
            return new BaseResultOK(defaultroles);
        }
    }
}
