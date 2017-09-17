package com.channel.appurtenance.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.channel.model.hd.*;
import com.common.action.result.BaseResult;

public interface AppurtenanceService {

    public BaseResult searchAppurtenances(int xzqh, int sshdid, int sshduanid, int fswsecondclassid, String content);

    public BaseResult queryAppurtenances(int secondclassid, Integer sshduanid, int page, int rows);

    public BaseResult queryAppurtenanceInfo(int id, int fswlx);

    public String queryStrXzqh(int id);

    public BaseResult delAppurtenance(int id, int fswlx);

    public BaseResult delAppurtenances(int hduanid);

    public BaseResult addNavigationmark(CHdHb chdhb,
                                        List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addBollard(CHdXlz chdxlz,
                                 List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addVideoobservation(CHdSpgcd chdspgcd,
                                          List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addLaserobservation(CHdJgllgcd chdjgllgcd,
                                          List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addManualobservation(CHdRggcd cHdRggcd,
                                           List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addRegulationrevement(CHdZzha chdzzha,
                                            List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addHub(CHdSn chdsn, List<File> filelist,
                             List<String> filelistFileName) throws Exception;

    public BaseResult addDam(CHdB chdb, List<File> filelist,
                             List<String> filelistFileName) throws Exception;

    public BaseResult addMooringarea(CHdMbq chdmbq,
                                     List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addManagementstation(CHdGlz chdglz,
                                           List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addHydrologicalstation(CHdSwz chdswz,
                                             List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addShipyard(CHdCc chdcc,
                                  List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addTakeoutfall(CHdQpsk chdqpsk,
                                     List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addKyDock(CHdKymt chdmt, List<File> filelist,
                                List<String> filelistFileName) throws Exception;

    public BaseResult addHyDock(CHdHymt chdmt, List<File> filelist,
                                List<String> filelistFileName) throws Exception;

    public BaseResult addGwDock(CHdGwmt chdmt, List<File> filelist,
                                List<String> filelistFileName) throws Exception;

    public BaseResult addTunnel(CHdSd chdsd, List<File> filelist,
                                List<String> filelistFileName) throws Exception;

    public BaseResult addPipeline(CHdGd chdgd,
                                  List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult addCable(CHdLx chdlx, List<File> filelist,
                               List<String> filelistFileName) throws Exception;

    public BaseResult addBridge(CHdQl chdql, List<File> filelist,
                                List<String> filelistFileName) throws Exception;

    public BaseResult addServicearea(CHdFwq chdfwq,
                                     List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult updateNavigationmark(CHdHb chdhb,
                                           List<Integer> delfileids, List<File> filelist,
                                           List<String> filelistFileName) throws Exception;

    public BaseResult updateBollard(CHdXlz chdxlz,
                                    List<Integer> delfileids, List<File> filelist,
                                    List<String> filelistFileName) throws Exception;

    public BaseResult updateVideoobservation(CHdSpgcd chdspgcd,
                                             List<Integer> delfileids, List<File> filelist,
                                             List<String> filelistFileName) throws Exception;

    public BaseResult updateManualobservation(
            CHdRggcd cHdRggcd, List<Integer> delfileids,
            List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult updateLaserobservation(
            CHdJgllgcd chdjgllgcd, List<Integer> delfileids,
            List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult updateRegulationrevement(CHdZzha chdzzha,
                                               List<Integer> delfileids, List<File> filelist,
                                               List<String> filelistFileName) throws Exception;

    public BaseResult updateHub(CHdSn chdsn,
                                List<Integer> delfileids, List<File> filelist,
                                List<String> filelistFileName) throws Exception;

    public BaseResult updateDam(CHdB chdb,
                                List<Integer> delfileids, List<File> filelist,
                                List<String> filelistFileName) throws Exception;

    public BaseResult updateMooringarea(CHdMbq chdmbq,
                                        List<Integer> delfileids, List<File> filelist,
                                        List<String> filelistFileName) throws Exception;

    public BaseResult updateManagementstation(CHdGlz chdglz,
                                              List<Integer> delfileids, List<File> filelist,
                                              List<String> filelistFileName) throws Exception;

    public BaseResult updateHydrologicalstation(CHdSwz chdswz,
                                                List<Integer> delfileids, List<File> filelist,
                                                List<String> filelistFileName) throws Exception;

    public BaseResult updateShipyard(CHdCc chdcc,
                                     List<Integer> delfileids, List<File> filelist,
                                     List<String> filelistFileName) throws Exception;

    public BaseResult updateTakeoutfall(CHdQpsk chdqpsk,
                                        List<Integer> delfileids, List<File> filelist,
                                        List<String> filelistFileName) throws Exception;

    public BaseResult updateKyDock(CHdKymt chdmt,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception;

    public BaseResult updateHyDock(CHdHymt chdmt,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception;

    public BaseResult updateGwDock(CHdGwmt chdmt,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception;

    public BaseResult updateTunnel(CHdSd chdsd,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception;

    public BaseResult updatePipeline(CHdGd chdgd,
                                     List<Integer> delfileids, List<File> filelist,
                                     List<String> filelistFileName) throws Exception;

    public BaseResult updateCable(CHdLx chdlx,
                                  List<Integer> delfileids, List<File> filelist,
                                  List<String> filelistFileName) throws Exception;

    public BaseResult updateAqueduct(CHdDc chddc,
                                     List<Integer> delfileids, List<File> filelist,
                                     List<String> filelistFileName) throws Exception;

    public BaseResult updateBridge(CHdQl chdql,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception;

    public BaseResult updateServicearea(CHdFwq cHdFwq,
                                        List<Integer> delfileids, List<File> filelist,
                                        List<String> filelistFileName) throws Exception;

    public BaseResult addAqueduct(CHdDc chddc,
                                  List<File> filelist, List<String> filelistFileName)
            throws Exception;

    public BaseResult downloadCHdFj(int id);

    public BaseResult queryAppBh(int fswlx);

    public BaseResult queryMaxAppbh(int fswlx);

    public BaseResult queryAppbhExisted(String bh, int fswlx);

    public BaseResult queryAppPopup(int id, int fswlx);

    public BaseResult queryFsw();

    public BaseResult searchAppByContent(int pid, Integer sshduanid, String content);

    public BaseResult batchImport(int fswlx, List<File> filelist, List<String> filelistFileName) throws FileNotFoundException, Exception;

    public BaseResult downloadTemplate(int fswlx);

    public BaseResult zhcxApp(int xzqh);

    public BaseResult exportApp(int xzqh);

    public BaseResult zhcxApps(int fswlx, int xzqh, int sshdid, Integer sshduanid, String content, int page, int rows);

    public BaseResult importQl();

    public BaseResult importLx();

    public BaseResult importKymt();

    public BaseResult importSn();

    public BaseResult searchAppByXzqh(int xzqh, int pid, Integer sshduanid, String content);

    public BaseResult exportFswInfo(int id, int fswlx);
}
