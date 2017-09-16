package com.zjport.manage.service;

import com.common.base.BaseResult;
import com.common.base.service.BaseService;
import com.zjport.manage.dao.ManageDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("manageService")
public class ManageService extends BaseService
{

    @Resource(name = "manageDao")
    private ManageDao manageDao;

    public BaseResult queryRoles(String jsmc, Integer page, Integer row)
    {
        return this.manageDao.queryRoles(jsmc, page, row);
    }

    public BaseResult saveRole(String jsmc, String qxstr)
    {
        return this.manageDao.saveRole(jsmc, qxstr);
    }

    public BaseResult roleDetail(Integer jsbh)
    {
        return this.manageDao.roleDetail(jsbh);
    }

    public BaseResult updateRole(Integer jsbh, String jsmc, String qxstr)
    {
        return this.manageDao.updateRole(jsbh, jsmc, qxstr);
    }

    public BaseResult deleteRole(Integer jsbh)
    {
        return this.manageDao.deleteRole(jsbh);
    }

    public BaseResult queryQxList()
    {
        return this.manageDao.queryQxList();
    }

    public BaseResult getAreaList() {
        return this.manageDao.queryAreaList();
    }

    public BaseResult getJsList() {
        return this.manageDao.queryJsList();
    }

    public BaseResult getQxList(String js) {
        return this.manageDao.queryQxList(Integer.valueOf(js));
    }

    public BaseResult getOrgDetail(String id) {
        return this.manageDao.getOrgDetail(id);
    }

    public BaseResult getUserDetail(String id) {
        return this.manageDao.getUserDetail(Integer.valueOf(id));
    }

}
