package com.channel.dic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.user.CZdXzqhdm;
import org.springframework.stereotype.Service;

import com.channel.bean.Constants;
import com.channel.dic.dao.DicDao;
import com.channel.dic.service.DicService;
import com.channel.model.hd.CZdAppattribute;
import com.channel.model.user.CXtUser;
import com.channel.model.user.CZdUserjstatus;
import com.channel.user.dao.UserDao;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;

@Service("dicservice")
public class DicServiceImpl extends BaseService implements DicService {
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dicdao")
    private DicDao dicDao;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;
    @Resource
    private LogService logService;

    @Override
    public BaseResult queryAllTitle() {
        BaseQueryRecords<CZdAppattribute> baseQueryRecords = this.dicDao
                .queryAllTitle();
        BaseResultOK baseResultOK = new BaseResultOK(baseQueryRecords);
        return baseResultOK;
    }

    @Override
    public BaseResult queryAllJStatus() {
        BaseQueryRecords<CZdUserjstatus> baseQueryRecords = this.dicDao
                .queryAllJStatus();
        BaseResultOK baseResultOK = new BaseResultOK(baseQueryRecords);
        return baseResultOK;
    }

    @Override
    public BaseResult queryDicAttr(String name) {
        if (name != null && name.equals("zzzt")) {
            BaseQueryRecords<CZdUserjstatus> baseQueryRecords = this.dicDao
                    .queryAllJStatus();
            BaseResultOK baseResultOK = new BaseResultOK(baseQueryRecords);
            return baseResultOK;
        } else {
            BaseQueryRecords<CZdAppattribute> baseQueryRecords = this.dicDao
                    .queryDicAttr(name);
            BaseResultOK baseResultOK = new BaseResultOK(baseQueryRecords);
            return baseResultOK;
        }
    }

    @Override
    public BaseResult queryAttrDesc(int id) {
        String attrdesc = "";
        attrdesc = this.dicDao.queryAttrDesc(id);
        BaseResultOK baseResultOK = new BaseResultOK(attrdesc);
        return baseResultOK;
    }

    @Override
    public BaseResult queryNameDesc() {
        List<Object[]> records = this.dicDao.queryNameDesc();
        Object[] xzqh = {"行政区划", "xzqh"};
        records.add(0, xzqh);
        Object[] jstatus = {"在职状态", "zzzt"};
        records.add(1, jstatus);
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult addDic(String name, String namedesc,
                             String attrdesc) {

        if (name != null && name.equals("zzzt")) {
            CZdUserjstatus jstatus = new CZdUserjstatus(namedesc, attrdesc,
                    new Date(), new Date(), 1);
            this.dicDao.addUserJstatus(jstatus);
        } else {
            CZdAppattribute dic = new CZdAppattribute(0, name, namedesc,
                    attrdesc, new Date(), new Date(), 1, 1);
            this.dicDao.addDic(dic);
            logService.addLog(ModuleName.XXWH_SJZD, OpName.ADD, dic.getName());
        }
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;

    }

    @Override
    public BaseResult delDic(String name, int id) {
        // TODO Auto-generated method stub

        if (name != null && name.equals("zzzt")) {
            CZdUserjstatus jstatus = this.dicDao.queryJstatusById(id);
            if (jstatus != null) {
                jstatus.setValid(0);
                this.dicDao.updateJstatus(jstatus);
            }
        } else {
            CZdAppattribute dic = this.dicDao.queryDicById(id);
            if (dic != null) {
                dic.setValid(0);
                this.dicDao.updateDic(dic);
                logService.addLog(ModuleName.XXWH_SJZD, OpName.DEL, dic.getName());
            }
        }

        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateDic(int id, String name,
                                String namedesc, String attrdesc) {

        if (name != null && name.equals("zzzt")) {
            CZdUserjstatus jstatus = this.dicDao.queryJstatusById(id);
            if (jstatus != null) {
                jstatus.setType(namedesc);
                jstatus.setTypedesc(attrdesc);
                jstatus.setUpdatetime(new Date());
                this.dicDao.updateJstatus(jstatus);
            }
        } else {
            CZdAppattribute dic = this.dicDao.queryDicById(id);
            if (dic != null) {
                dic.setName(name);
                dic.setNamedesc(namedesc);
                dic.setAttrdesc(attrdesc);
                dic.setUpdatetime(new Date());
                this.dicDao.updateDic(dic);
                logService.addLog(ModuleName.XXWH_SJZD, OpName.UPDATE, dic.getName());
            }
        }
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult queryDic(int id) {
        CZdAppattribute dic = this.dicDao.queryDicById(id);
        BaseResultOK baseResultOK = new BaseResultOK(dic);
        return baseResultOK;
    }

    @Override
    public BaseResult queryCity() {
        List<CZdXzqhdm> xzqhdm = this.xzqhdmService.findRootNodes().getData();
        List<CZdXzqhdm> xzqhs = new ArrayList<CZdXzqhdm>();
        if (xzqhdm != null && xzqhdm.size() > 0) {
            int len = xzqhdm.size();
            for (int i = 0; i < len; i++) {
                CZdXzqhdm temp = xzqhdm.get(i);
                List<CZdXzqhdm> childs = this.xzqhdmService.findChildrenNodes(temp).getData();
                if (childs != null && childs.size() > 0) {
                    for (CZdXzqhdm xzqh : childs) {
                        xzqhs.add(xzqh);
                    }
                }
            }
        }
        BaseResultOK baseResultOK = new BaseResultOK(xzqhs);
        return baseResultOK;
    }

    @Override
    public BaseResult queryCityXzqh(int xzqh) {
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        List<String> strxzqhs = new ArrayList<>();
        CZdXzqhdm cZdXzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
        if (cZdXzqhdm != null) {
            int type = cZdXzqhdm.getType();
            switch (type) {
                case Constants.XZQH_SHEN:
                    xzqhs.add(cZdXzqhdm);
                    break;
                case Constants.XZQH_SHI:
                    xzqhs.add(cZdXzqhdm);
                    List<CZdXzqhdm> quxianlist = this.xzqhdmService.findChildrenNodes(cZdXzqhdm, Constants.XZQH_QUXIAN).getData();
                    for (CZdXzqhdm quxian : quxianlist) {
                        xzqhs.add(quxian);
                    }
                    break;
                case Constants.XZQH_QUXIAN:
                    //将所在部门所处的市行政区划存入
                    CZdXzqhdm cityxzqh = this.xzqhdmService.queryShiXzqh(xzqh);
                    xzqhs = this.xzqhdmService.findChildrenNodes(cityxzqh, Constants.XZQH_QUXIAN).getData();
                    break;
            }
        }
        strxzqhs.add(cZdXzqhdm.getName());
        BaseResultOK baseResultOK = new BaseResultOK(xzqhs);
        baseResultOK.addToMap("defxzqhs", strxzqhs);
        return baseResultOK;
    }
}
