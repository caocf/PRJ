package com.channel.appurtenance.service.impl;

import javax.annotation.Resource;

import com.channel.appurtenance.dao.AppurtenanceDao;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.model.hd.CZdAppattribute;
import com.channel.model.user.CZdXzqhdm;
import org.springframework.stereotype.Service;

import com.channel.appurtenance.dao.impl.AppurtenanceTypeDaoImpl;
import com.channel.appurtenance.service.AppurtenanceTypeService;
import com.channel.bean.Constants;
import com.channel.model.hd.CZdAppurtenance;
import com.channel.model.hd.CZdAppurtenanceRela;
import com.channel.model.user.CXtUser;
import com.channel.user.dao.UserDao;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.utils.tree.TreeDao;
import com.common.utils.tree.impl.TreeServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service("appurtenancetypeservice")
public class AppurtenanceTypeServiceImpl extends TreeServiceImpl<CZdAppurtenance, CZdAppurtenanceRela> implements AppurtenanceTypeService {
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "appurtenancetypedao")
    private AppurtenanceTypeDaoImpl appurtenancetypedao;
    @Resource(name = "appurtenancedao")
    private AppurtenanceDao appurtenanceDao;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;

    @Override
    public TreeDao<CZdAppurtenance, CZdAppurtenanceRela> getTreeDao() {
        return appurtenancetypedao;
    }

    @Override
    public BaseResult queryAllParent() {
        BaseQueryRecords<CZdAppurtenance> records = this.findRootNodes();
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult queryAllHduanParent(Integer hduanid) {
        BaseQueryRecords<CZdAppurtenance> records = this.findRootNodes();

        List<Long> bigclassfswcnt = new ArrayList<>();

        //查询该航段该分类下有多少附属物
        for (CZdAppurtenance bigclass : records.getData()) {
            BaseQueryRecords<CZdAppurtenance> secondclasses = this.findChildrenNodes(bigclass);

            long cnttotal = 0;
            for (CZdAppurtenance secondclass : secondclasses.getData()) {
                long cnt = this.appurtenanceDao.countEachApp(secondclass.getId(), hduanid);
                if (cnt >= 0)
                    cnttotal += cnt;
            }
            bigclassfswcnt.add(cnttotal);
        }

        BaseResultOK baseResultOK = new BaseResultOK(records);
        baseResultOK.addToMap("fswcnt", bigclassfswcnt);
        return baseResultOK;
    }

    @Override
    public BaseResult querySubClass(int parentid, int sshduanid) {
        CZdAppurtenance parentnode = new CZdAppurtenance();
        parentnode.setId(parentid);
        BaseQueryRecords<CZdAppurtenance> records = this
                .findChildrenNodes(parentnode);
        if (sshduanid > 0) {
            for (CZdAppurtenance secondclass : records.getData()) {
                secondclass.setFswcnt((int) this.appurtenanceDao.countEachApp(secondclass.getId(), sshduanid));
            }
        }

        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult queryAllFswClass() {
        BaseQueryRecords<CZdAppurtenance> records = super.findLeafNodes();
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult searchAllHduanParent(int hduanid, int fswlx, String content) {
        BaseQueryRecords<CZdAppurtenance> records = this.findRootNodes();

        List<Long> bigclassfswcnt = new ArrayList<>();

        //查询该航段该分类下有多少附属物
        for (CZdAppurtenance bigclass : records.getData()) {
            BaseQueryRecords<CZdAppurtenance> secondclasses = this.findChildrenNodes(bigclass);
            long cnttotal = 0;
            for (CZdAppurtenance secondclass : secondclasses.getData()) {
                int tempfswlx = secondclass.getId();
                long cnt = 0;
                if (fswlx != -1) {
                    if (fswlx == tempfswlx) {
                        cnt = this.appurtenanceDao.queryAppCount(tempfswlx, hduanid, content);
                    }
                } else {
                    cnt = this.appurtenanceDao.queryAppCount(tempfswlx, hduanid, content);
                }
                if (cnt >= 0)
                    cnttotal += cnt;
            }
            bigclassfswcnt.add(cnttotal);
        }

        BaseResultOK baseResultOK = new BaseResultOK(records);
        baseResultOK.addToMap("fswcnt", bigclassfswcnt);
        return baseResultOK;
    }

    @Override
    public BaseResult queryAppByType(int type) {
        BaseQueryRecords records = this.appurtenanceDao.queryAppByType(type);
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult searchAllHduanXzqh(int xzqhid, Integer hduanid, int fswlx, String content) {
        BaseQueryRecords<CZdAppurtenance> records = this.findRootNodes();

        List<Long> bigclassfswcnt = new ArrayList<>();
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        List xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
        xzqhs.add(xzqh);
        //查询该航段该分类下有多少附属物
        for (CZdAppurtenance bigclass : records.getData()) {
            BaseQueryRecords<CZdAppurtenance> secondclasses = this.findChildrenNodes(bigclass);
            long cnttotal = 0;
            for (CZdAppurtenance secondclass : secondclasses.getData()) {
                int tempfswlx = secondclass.getId();
                long cnt = 0;
                if (fswlx != -1) {
                    if (fswlx == tempfswlx) {
                        cnt = this.appurtenanceDao.queryAppCountXzqh(xzqhs,tempfswlx, hduanid, content);
                    }
                } else {
                    cnt = this.appurtenanceDao.queryAppCountXzqh(xzqhs,tempfswlx, hduanid, content);
                }
                if (cnt >= 0)
                    cnttotal += cnt;
            }
            bigclassfswcnt.add(cnttotal);
        }

        BaseResultOK baseResultOK = new BaseResultOK(records);
        baseResultOK.addToMap("fswcnt", bigclassfswcnt);
        return baseResultOK;
    }

}
