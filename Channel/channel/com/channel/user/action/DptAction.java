package com.channel.user.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import com.channel.dic.service.XzqhdmService;
import com.channel.user.service.DptService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

import java.util.List;

@Controller
public class DptAction extends BaseAction {
    private int id;// 机构id
    private int isroot = 0;//是否加载根节点
    private int pid;// 所属机构id 父机构
    private String name;// 机构名称
    private int xzqh;// 机构代码
    private String dptdesc;// 机构描述
    private BaseResult result;
    private String code;
    private int page;
    private int rows;
    @Resource(name = "dptservice")
    private DptService dptService;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    private List<Integer> manxzqhs;
    private List<Integer> defaultroles;

    /**
     * 查询根目录下的组织机构和行政区划
     *
     * @return
     */
    public String queryRoot() {
        result = this.dptService.queryRoot();
        return "success";
    }

    /**
     * 查询该组织下的所有组织机构
     *
     * @return
     */
    public String queryAllDpt() {
        result = this.dptService.queryAllDpt(id, isroot == 1 ? true : false);
        return "success";
    }

    /**
     * 查询该组织下的所有组织机构
     *
     * @return
     */
    public String queryTreeDpt() {
        result = this.dptService.queryTreeDpt(id, isroot == 1 ? true : false);
        return "success";
    }

    /**
     * 查询该组织下的所有组织机构
     *
     * @return
     */
    public String queryXzqhDpt() {
        result = this.dptService.queryXzqhDpt(id, xzqh);
        return "success";
    }

    /**
     * 查询该组织下的所有行政机构
     *
     * @return
     */
    public String queryAllXzqh() {
        result = this.xzqhdmService.queryAllXzqh(id, isroot == 1 ? true : false);
        return "success";
    }

    /**
     * 查询该组织下的所有行政机构
     *
     * @return
     */
    public String queryTreeXzqh() {
        result = this.xzqhdmService.queryTreeXzqh(xzqh, isroot == 1 ? true : false);
        return "success";
    }

    /**
     * 查询组织结构信息
     *
     * @return
     */
    public String queryDptInfo() {
        result = this.dptService.queryDptInfo(id);
        return "success";
    }

    public String queryDptDefaultRoles() {
        result = this.dptService.queryDptDefaultRoles(id);
        return "success";
    }

    /**
     * 删除组织结构
     *
     * @return
     */
    public String delDpt() {
        result = this.dptService.delDpt(id);
        return "success";
    }

    /**
     * 添加组织结构
     *
     * @return
     */
    public String addDpt() {
        result = this.dptService.addDpt(name, xzqh, dptdesc,
                pid, manxzqhs,defaultroles);
        return "success";
    }

    /**
     * 更新组织结构
     *
     * @return
     */
    public String updateDpt() {
        result = this.dptService.updateDpt(id, name, xzqh,
                dptdesc, manxzqhs,defaultroles);
        return "success";
    }

    /**
     * 查询行政区划信息
     *
     * @return
     */
    public String queryXzqhInfo() {
        result = this.xzqhdmService.queryXzqhInfo(id);
        return "success";
    }

    /**
     * 删除组织结构
     *
     * @return
     */
    public String delXzqh() {
        result = this.xzqhdmService.delXzqh(id);
        return "success";
    }

    /**
     * 添加组织结构
     *
     * @return
     */
    public String addXzqh() {
        result = this.xzqhdmService.addXzqh(code, name, pid);
        return "success";
    }

    /**
     * 更新组织结构
     *
     * @return
     */
    public String updateXzqh() {
        result = this.xzqhdmService.updateXzqh(id,pid,name,code);
        return "success";
    }


    public List<Integer> getDefaultroles() {
        return defaultroles;
    }

    public void setDefaultroles(List<Integer> defaultroles) {
        this.defaultroles = defaultroles;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXzqh() {
        return xzqh;
    }

    public void setXzqh(int xzqh) {
        this.xzqh = xzqh;
    }

    public String getDptdesc() {
        return dptdesc;
    }

    public void setDptdesc(String dptdesc) {
        this.dptdesc = dptdesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsroot() {
        return isroot;
    }

    public void setIsroot(int isroot) {
        this.isroot = isroot;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Integer> getManxzqhs() {
        return manxzqhs;
    }

    public void setManxzqhs(List<Integer> manxzqhs) {
        this.manxzqhs = manxzqhs;
    }
}
