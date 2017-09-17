package com.channel.appurtenance.dao;

import java.util.List;

import com.channel.model.hd.*;
import com.channel.model.user.CZdXzqhdm;
import com.common.dao.BaseQueryRecords;

public interface AppurtenanceDao {

    public BaseQueryRecords searchAppurtenances(List<CZdXzqhdm> xzqhs, int sshdid, int sshduanid, int fswsecondclassid, String content);

    public long countEachApp(int sid, Integer sshduanid);

    public long queryAppCount(int tempfswlx, int hduanid, String content);

    public BaseQueryRecords<Object> queryEachApp(
            int sid, Integer sshduanid, int page, int rows);

    public List<Object[]> queryAppurtenanceInfo(int id, int fswlx);

    public void delAppurtenance(Object object);

    public void delAppurtenances(int hduanid);

    public void addNavigationmark(CHdHb chdhb);

    public void addServicearea(CHdFwq chdfwq);

    public void addBridge(CHdQl chdql);

    public void addAqueduct(CHdDc chddc);

    public void addCable(CHdLx chdlx);

    public void addPipeline(CHdGd chdgd);

    public void addTunnel(CHdSd chdsd);

    public void addKyDock(CHdKymt chdkymt);

    public void addHyDock(CHdHymt chdhymt);

    public void addGwDock(CHdGwmt chdgwmt);

    public void addTakeoutfall(CHdQpsk chdqpsk);

    public void addShipyard(CHdCc chdcc);

    public void addHydrologicalstation(CHdSwz chdswz);

    public void addBollard(CHdXlz chdxlz);

    public void addVideoobservation(CHdSpgcd chdspgcd);

    public void addManualobservation(CHdRggcd chdrggcd);

    public void addLaserobservation(CHdJgllgcd chdjgllgcd);

    public void addRegulationrevement(CHdZzha chdzzha);

    public void addHub(CHdSn chdsn);

    public void addDam(CHdB chdb);

    public void addMooringarea(CHdMbq chdmbq);


    public void addManagementstation(CHdGlz chdglz);

    public void updateNavigationmark(CHdHb chdhb);

    public void updateServicearea(CHdFwq cHdFwq);

    public void updateBridge(CHdQl chdql);

    public void updateAqueduct(CHdDc chddc);

    public void updateCable(CHdLx chdlx);

    public void updatePipeline(CHdGd chdgd);

    public void updateTunnel(CHdSd chdsd);

    public void updateKyDock(CHdKymt chdmt);

    public void updateHyDock(CHdHymt chdmt);

    public void updateGwDock(CHdGwmt chdmt);

    public void updateTakeoutfall(CHdQpsk chdqpsk);

    public void updateShipyard(CHdCc chdcc);

    public void updateHydrologicalstation(CHdSwz chdswz);

    public void updateBollard(CHdXlz chdxlz);

    public void updateVideoobservation(CHdSpgcd chdspgcd);

    public void updateLaserobservation(CHdJgllgcd chdjgllgcd);

    public void updateManualobservation(CHdRggcd chdrggcd);

    public void updateRegulationrevement(CHdZzha chdzzha);

    public void updateHub(CHdSn chdsn);

    public void updateDam(CHdB chdb);

    public void updateMooringarea(CHdMbq chdmbq);


    public void updateManagementstation(CHdGlz chdglz);

    public void addCHdFj(CHdFj fj);

    public List<CHdFj> queryChdfjByApptype(int appid, int apptype);

    public CHdFj queryCHdFjById(int id);

    public void delCHdFj(int id, int fswlx);

    public void delCHdFj(Integer id, int appid, int apptype);

    public Object queryAppurtenanceById(int id, int fswlx);

    public CHdSn querySnByBh(String hb);

    public List queryAppurtenance(int fswlx);

    public List<?> querySnByThlx();

    public List<?> querySnByThlx(String xzqhs);

    public Boolean queryAppbhExisted(String bh, int fswlx);

    public Long queryMaxAppbh(int fswlx);

    public BaseQueryRecords<Object> queryAppPopup(int id, int fswlx);

    public BaseQueryRecords<Object> queryEachApp(int secondclassid, Integer sshduanid, String content);

    public BaseQueryRecords queryAppByType(int type);

    public List zhcxApp(String xzqh);

    public BaseQueryRecords<Object> zhcxApps(int fswlx, String sqlhdid, String content, int page, int rows);

    public List<Object[]> importQl();

    public List<Object[]> importLx();

    public List<Object[]> importKymt();

    public List<Object[]> importSn();

    public long queryAppCountXzqh(List<CZdXzqhdm> xzqhs, int tempfswlx, Integer hduanid, String content);

    public BaseQueryRecords<Object> queryEachAppXzqh(List<CZdXzqhdm> xzqhs, int secondclassid, Integer sshduanid, String content);
}
