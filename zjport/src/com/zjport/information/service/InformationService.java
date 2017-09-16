package com.zjport.information.service;

import com.common.base.BaseRecords;
import com.zjport.model.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by TWQ on 2016/7/28.
 */
public interface InformationService {

    public BaseRecords<TInformation> selectSendInfoList(String type, String state, String search, int userId, int rows, int page);

    public BaseRecords<TInformation> selectApprovalInfoList(String type, String state, String search, int userId, int rows, int page);

    public BaseRecords<TInformation> selectpersonalInfoList(String type, String state, String search, int userId,String deptId, int rows, int page);

    public TInformation selectInfoDetail(String id);

    public BaseRecords<TWebName> selectWebList();

    public BaseRecords<TWebColumn> selectWebColumnList(String webId);

    public void saveInformation(TInformation information);

    public String getwebName(int webNameId);

    public String getwebColumn(int webColumnId);

    public void changeState(String informId);

    public String getUser(int userId);

    public List<TAttachment> getAttachmentList(String middleId);

    public BaseRecords selectApprovalUserList(int userId);

    public void approvalInform(String informId, String isPass, String backContent, TUser user);

    public BaseRecords<TDepartment> selectDepartment(String orgId);

    public BaseRecords<TBoard> selectBoardList();

    public void deleteInform(String id);

    public void deleteDeptId(String deptMiddleId);

    public void addScanDepart(String departId, String departMiddleId);

    public void recallInform(String id);

    public TWebName getWeb(int webNameId);

    public TWebColumn getColumn(int webColumnId);

    public List<TScanDepartment> getDepartmentList(String deptMiddleId);

    public String sendMessage(String phone, String code, int infoId) throws IOException;
}
