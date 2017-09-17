package com.channel.appurtenance.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.channel.appurtenance.service.AppurtenanceTypeService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

@Controller
public class AppurtenanceTypeAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Resource(name = "appurtenancetypeservice")
    private AppurtenanceTypeService appurtenanceTypeService;
    private int parentid;
    private int type;
    private int fswlx;
    private int xzqh;
    private String content;
    private Integer hduanid = 0;
    private BaseResult result;

    /**
     * 查询所有附属物大类型
     *
     * @return
     */
    public String queryAllParent() {
        result = this.appurtenanceTypeService.queryAllParent();
        return "success";
    }

    /**
     * 查询所有附属物大类型
     *
     * @return
     */
    public String queryAllHduanParent() {
        result = this.appurtenanceTypeService.queryAllHduanParent(hduanid);
        return "success";
    }

    /**
     * 搜索所有附属物大类型
     *
     * @return
     */
    public String searchAllHduanXzqh() {
        result = this.appurtenanceTypeService.searchAllHduanXzqh(xzqh,hduanid,fswlx,content);
        return "success";
    }

    /**
     * 搜索所有附属物大类型
     *
     * @return
     */
    public String searchAllHduanParent() {
        result = this.appurtenanceTypeService.searchAllHduanParent(hduanid,fswlx,content);
        return "success";
    }

    /**
     * 查询所有附属物子类型
     *
     * @return
     */
    public String queryAllFswClass() {
        result = this.appurtenanceTypeService.queryAllFswClass();
        return "success";
    }

    /**
     * 查询某一大类的附属物子类型
     *
     * @return
     */
    public String querySubClass() {
        result = this.appurtenanceTypeService.querySubClass(parentid, hduanid);
        return "success";
    }

    /**
     * 查询所有附属物根据类型
     *
     * @return
     */
    public String queryAppByType() {
        result = this.appurtenanceTypeService.queryAppByType(type);
        return "success";
    }

    public int getFswlx() {
        return fswlx;
    }

    public void setFswlx(int fswlx) {
        this.fswlx = fswlx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public Integer getHduanid() {
        return hduanid;
    }

    public void setHduanid(Integer hduanid) {
        this.hduanid = hduanid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getXzqh() {
        return xzqh;
    }

    public void setXzqh(int xzqh) {
        this.xzqh = xzqh;
    }
}
