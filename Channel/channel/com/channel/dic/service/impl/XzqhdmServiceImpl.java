package com.channel.dic.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import org.springframework.stereotype.Service;

import com.channel.bean.Constants;
import com.channel.bean.XzqhDT;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.model.user.CZdXzqhRela;
import com.channel.model.user.CZdXzqhdm;
import com.channel.user.dao.UserDao;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.utils.tree.TreeDao;
import com.common.utils.tree.impl.TreeServiceImpl;
import com.common.utils.tree.model.Tree;

@Service("xzqhdmservice")
public class XzqhdmServiceImpl extends TreeServiceImpl<CZdXzqhdm, CZdXzqhRela>
        implements XzqhdmService {
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;
    @Resource
    private LogService logService;

    @Override
    public TreeDao<CZdXzqhdm, CZdXzqhRela> getTreeDao() {
        return xzqhdmdao;
    }

    @Override
    public BaseResult queryAllXzqh(int id, boolean isroot) {
        if (isroot) {//如果加载的是根节点,即要显示该节点
            List<XzqhDT> list = new ArrayList<XzqhDT>();
            if (id == -1) { //如果ID为-1表示全部节点，则根节点应该为rootnode
                List<CZdXzqhdm> rootnodes = this.findRootNodes(Constants.XZQH_KINDS).getData();
                for (CZdXzqhdm xzqhdm : rootnodes) {
                    int i = this.findChildrenNodes(xzqhdm).getData().size();
                    XzqhDT xzqhdt = new XzqhDT(xzqhdm, i);
                    list.add(xzqhdt);
                }
                Collections.sort(list, new Comparator<XzqhDT>() {
                    @Override
                    public int compare(XzqhDT o1, XzqhDT o2) {
                        return (int) (o1.getXzqhdm().getCreatetime().getTime() - o2.getXzqhdm().getCreatetime().getTime());
                    }
                });
                BaseResultOK baseResultOK = new BaseResultOK(list);
                return baseResultOK;
            } else {
                CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(id);
                if (xzqhdm == null) {
                    return new BaseResultFailed(Constants.RESULT_USER_NOTXZQH,
                            "用户行政区划不存在");
                } else {
                    XzqhDT xzqhDT = new XzqhDT();
                    xzqhDT.setXzqhdm(xzqhdm);
                    xzqhDT.setCountchild(this.findChildrenNodes(xzqhdm).getData().size());
                    list.add(xzqhDT);
                    BaseResultOK baseResultOK = new BaseResultOK(list);
                    return baseResultOK;
                }
            }
        } else {
            List<XzqhDT> list = new ArrayList<XzqhDT>();
            if (id == -1) {
                List<CZdXzqhdm> rootnodes = this.findRootNodes(Constants.XZQH_KINDS).getData();
                for (CZdXzqhdm xzqhdm : rootnodes) {
                    int i = this.findChildrenNodes(xzqhdm).getData().size();
                    XzqhDT xzqhdt = new XzqhDT(xzqhdm, i);
                    list.add(xzqhdt);
                }
                Collections.sort(list, new Comparator<XzqhDT>() {
                    @Override
                    public int compare(XzqhDT o1, XzqhDT o2) {
                        return (int) (o1.getXzqhdm().getCreatetime().getTime() - o2.getXzqhdm().getCreatetime().getTime());
                    }
                });
                BaseResultOK baseResultOK = new BaseResultOK(list);
                return baseResultOK;
            } else {
                CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(id);
                if (xzqhdm == null) {
                    return new BaseResultFailed(Constants.RESULT_USER_NOTXZQH,
                            "用户行政区划不存在");
                } else {
                    //查询下级子节点
                    List<CZdXzqhdm> childnodes = this
                            .findChildrenNodes(xzqhdm).getData();
                    if (childnodes != null && childnodes.size() > 0) {
                        for (CZdXzqhdm cZdXzqhdm : childnodes) {
                            XzqhDT xzqhDT = new XzqhDT();
                            xzqhDT.setXzqhdm(cZdXzqhdm);
                            xzqhDT.setCountchild(this.findChildrenNodes(cZdXzqhdm).getData().size());
                            list.add(xzqhDT);
                        }
                    }
                    Collections.sort(list, new Comparator<XzqhDT>() {
                        @Override
                        public int compare(XzqhDT o1, XzqhDT o2) {
                            return (int) (o1.getXzqhdm().getCreatetime().getTime() - o2.getXzqhdm().getCreatetime().getTime());
                        }
                    });
                    BaseResultOK baseResultOK = new BaseResultOK(list);
                    return baseResultOK;
                }
            }
        }
    }

    @Override
    public BaseResult queryTreeXzqh(int xzqh, boolean isroot) {
        List<XzqhDT> list = new ArrayList<XzqhDT>();
        if (xzqh == -1) {
            List<CZdXzqhdm> rootnodes = this.findRootNodes(Constants.XZQH_KINDS).getData();
            for (CZdXzqhdm xzqhdm : rootnodes) {
                int i = this.findChildrenNodes(xzqhdm).getData().size();
                XzqhDT xzqhdt = new XzqhDT(xzqhdm, i);
                list.add(xzqhdt);
            }
            Collections.sort(list, new Comparator<XzqhDT>() {
                @Override
                public int compare(XzqhDT o1, XzqhDT o2) {
                    return (int) (o1.getXzqhdm().getCreatetime().getTime() - o2.getXzqhdm().getCreatetime().getTime());
                }
            });
            BaseResultOK baseResultOK = new BaseResultOK(list);
            return baseResultOK;
        } else {
            if (isroot) {
                CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
                if (xzqhdm == null) {
                    return new BaseResultFailed(Constants.RESULT_USER_NOTXZQH,
                            "用户行政区划不存在");
                } else {
                    XzqhDT xzqhDT = new XzqhDT();
                    xzqhDT.setXzqhdm(xzqhdm);
                    xzqhDT.setCountchild(this.findChildrenNodes(xzqhdm).getData().size());
                    list.add(xzqhDT);
                    BaseResultOK baseResultOK = new BaseResultOK(list);
                    return baseResultOK;
                }
            } else {
                CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
                if (xzqhdm == null) {
                    return new BaseResultFailed(Constants.RESULT_USER_NOTXZQH,
                            "用户行政区划不存在");
                } else {
                    //查询下级子节点
                    List<CZdXzqhdm> childnodes = this
                            .findChildrenNodes(xzqhdm).getData();
                    if (childnodes != null && childnodes.size() > 0) {
                        for (CZdXzqhdm cZdXzqhdm : childnodes) {
                            XzqhDT xzqhDT = new XzqhDT();
                            xzqhDT.setXzqhdm(cZdXzqhdm);
                            xzqhDT.setCountchild(this.findChildrenNodes(cZdXzqhdm).getData().size());
                            list.add(xzqhDT);
                        }
                    }
                    Collections.sort(list, new Comparator<XzqhDT>() {
                        @Override
                        public int compare(XzqhDT o1, XzqhDT o2) {
                            return (int) (o1.getXzqhdm().getCreatetime().getTime() - o2.getXzqhdm().getCreatetime().getTime());
                        }
                    });
                    BaseResultOK baseResultOK = new BaseResultOK(list);
                    return baseResultOK;
                }
            }
        }

    }

    @Override
    public BaseResult queryXzqhInfo(int id) {
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(id);
        if (xzqhdm == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTXZQH,
                    "用户行政区划不存在");
        } else {
            BaseResultOK baseResultOK = new BaseResultOK();
            baseResultOK.addToMap("xzqh", xzqhdm);
            return baseResultOK;
        }
    }

    public String queryStrXzqh(CZdXzqhdm xzqhdm) {
        Tree<CZdXzqhdm> xzqhtree = this.findOnlyParentPath(xzqhdm);
        StringBuffer xzqhs = new StringBuffer();
        Tree<CZdXzqhdm> currXzqhTree = xzqhtree;
        while (true) {
            xzqhs.append(currXzqhTree.getNode().getName() + "/");
            if (currXzqhTree.getChildren().size() > 0) {
                currXzqhTree = currXzqhTree.getChildren().get(0);
            } else {
                break;
            }
        }
        xzqhs.deleteCharAt(xzqhs.length() - 1);// 去除最后的/
        return xzqhs.toString();
    }

    @Override
    public BaseResult delXzqh(int id) {
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(id);
        if (xzqhdm == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTXZQH,
                    "用户行政区划不存在");
        } else {
            int num = this.xzqhdmdao._findChildrenNodes(xzqhdm).getData()
                    .size();
            if (num > 0) {
                return new BaseResultFailed(Constants.RESULT_USER_CHILDXZQH,
                        "存在子行政区划,请先删除子行政区划");
            } else {
                this.xzqhdmdao._delNode(xzqhdm);
                logService.addLog(ModuleName.XXWH_ZZJG, OpName.DEL, xzqhdm.getName());
                BaseResultOK baseResultOK = new BaseResultOK();
                return baseResultOK;
            }
        }
    }

    @Override
    public BaseResult updateXzqh(int id, int pid, String name, String code) {
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(id);
        if (xzqhdm == null) {
            return new BaseResultFailed(Constants.RESULT_USER_NOTXZQH,
                    "用户行政区划不存在");
        } else {
            CZdXzqhdm temp = this.xzqhdmdao.queryXzqhdmByCode(code);
            if (temp != null) {
                return new BaseResultFailed(Constants.RESULT_USER_NOTXZQH,
                        "行政区划代码已经存在");
            }
            xzqhdm.setName(name);
            xzqhdm.setCode(code);
            xzqhdm.setUpdatetime(new Date());
            if (pid != 0) {
                CZdXzqhRela xzqhRela = this.xzqhdmdao.queryXzqhdmBySid(id);
                if (xzqhRela != null) {
                    xzqhRela.setPid(pid);
                }
            }
            logService.addLog(ModuleName.XXWH_ZZJG, OpName.UPDATE, xzqhdm.getName());
            BaseResultOK baseResultOK = new BaseResultOK();
            return baseResultOK;
        }
    }

    @Override
    public BaseResult addXzqh(String code, String name, int pid) {
        CZdXzqhdm pxzqh = this.xzqhdmdao.queryXzqhdmById(pid);

        CZdXzqhdm xzqhdm = new CZdXzqhdm(code, 1, name, new Date(), new Date());
        if (pxzqh == null) {
            xzqhdm.setType(Constants.XZQH_SHEN);
        } else {
            if (pxzqh.getType() == Constants.XZQH_SHEN)
                xzqhdm.setType(Constants.XZQH_SHI);
            if (pxzqh.getType() == Constants.XZQH_SHI)
                xzqhdm.setType(Constants.XZQH_QUXIAN);
            if (pxzqh.getType() == Constants.XZQH_QUXIAN)
                return new BaseResultFailed("无法在下一级行政区划");
        }
        this.xzqhdmdao._addNode(xzqhdm);
        if (pxzqh != null) {
            CZdXzqhRela xzqhRela = new CZdXzqhRela();
            xzqhRela.setPid(pid);
            xzqhRela.setSid(xzqhdm.getId());
            this.xzqhdmdao.addXzqhRela(xzqhRela);
        }
        logService.addLog(ModuleName.XXWH_ZZJG, OpName.ADD, xzqhdm.getName());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public CZdXzqhdm queryShiXzqh(int id) {
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(id);
        if (xzqh == null)
            return null;
        if (xzqh.getType() == Constants.XZQH_SHI)
            return xzqh;
        List<CZdXzqhdm> shixzqhs = this.findParentNodes_r(xzqh, Constants.XZQH_SHI);
        if (shixzqhs == null || shixzqhs.size() == 0)
            return null;
        return shixzqhs.get(0);
    }

}
