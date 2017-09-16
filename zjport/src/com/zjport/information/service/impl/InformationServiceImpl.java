package com.zjport.information.service.impl;

import com.common.base.BaseRecords;
import com.common.base.service.BaseService;
import com.zjport.file.util.FileOperation;
import com.zjport.information.dao.InformationDao;
import com.zjport.information.service.InformationService;
import com.zjport.model.*;
import com.zjport.util.CommonConst;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWQ on 2016/7/28.
 */
@Service("inforService")
public class InformationServiceImpl extends BaseService implements InformationService {

    @Resource(name = "inforDao")
    private InformationDao inforDao;

    @Override
    public BaseRecords<TInformation> selectSendInfoList(String type, String state, String search, int userId, int rows, int page) {
        BaseRecords records = this.inforDao.selectAll(type, state, search, userId, rows, page);
        return records;
    }

    @Override
    public BaseRecords<TInformation> selectApprovalInfoList(String type, String state, String search, int userId, int rows, int page) {
        BaseRecords records = this.inforDao.selectApprovalAll(type, state, search, userId, rows, page);
        return records;
    }

    @Override
    public BaseRecords<TInformation> selectpersonalInfoList(String type, String state, String search, int userId, String deptId, int rows, int page) {
        BaseRecords records = this.inforDao.selectPersonalAll(type, state, search, userId, deptId, rows, page);
        return records;
    }

    @Override
    public TInformation selectInfoDetail(String id) {
        BaseRecords records = this.inforDao.getInfoById(Integer.valueOf(id));
        return (TInformation)records.getData().get(0);
    }

    @Override
    public BaseRecords<TWebName> selectWebList() {
        BaseRecords records = this.inforDao.selectWebList();
        return records;
    }

    @Override
    public BaseRecords<TWebColumn> selectWebColumnList(String webId) {
        BaseRecords records = this.inforDao.selectWebColumnList(Integer.valueOf(webId));
        return records;
    }

    @Override
    public void saveInformation(TInformation information) {
        this.inforDao.saveInformation(information);
    }

    @Override
    public String getwebName(int webNameId) {
        BaseRecords records = this.inforDao.selectWeb(webNameId);
        TWebName webName = (TWebName)records.getData().get(0);
        return webName.getStName();
    }

    @Override
    public String getwebColumn(int webColumnId) {
        BaseRecords records = this.inforDao.selectWebColumn(webColumnId);
        TWebColumn webColumn = (TWebColumn)records.getData().get(0);
        return webColumn.getStWebColumnName();
    }

    @Override
    public void changeState(String informId) {
        this.inforDao.changeState(Integer.valueOf(informId));
    }

    @Override
    public String getUser(int userId) {
        TUser user = this.inforDao.selectUserById(userId);
        return user.getStUserName();
    }

    @Override
    public List<TAttachment> getAttachmentList(String middleId) {
        List<TAttachment> attachmentList = new ArrayList<TAttachment>();
        if("".equals(middleId) || middleId == null) {
            return attachmentList;
        } else {
            BaseRecords records = this.inforDao.selectAttachment(middleId);
            int size = records.getData().size();

            for(int i = 0; i<size; i++) {
                attachmentList.add((TAttachment)records.getData().get(i));
            }
            return attachmentList;
        }
    }

    @Override
    public BaseRecords selectApprovalUserList(int userId) {
        BaseRecords records = this.inforDao.selectApprovalUserList(userId);
        return records;
    }

    @Override
    public void approvalInform(String informId, String isPass, String backContent, TUser user) {
        BaseRecords records = this.inforDao.getInfoById(Integer.valueOf(informId));
        if(records != null) {
            TInformation info = (TInformation)records.getData().get(0);

            String state = "";
            //判断审批是否通过，若通过且没有审批意见默认同意，若不通过且没有审批意见默认不同意
            if("pass".equals(isPass)) {
                state = CommonConst.InfoState_Send;
                info.setDtSend(new Timestamp(System.currentTimeMillis()));
                if(backContent==null || "".equals(backContent)) {
                    backContent = "同意";
                }
                FileOperation.contentToTxt("E:\\board\\"+info.getStInformId()+".txt",info.getStInformObject()+"："+info.getStInformContent());
            } else {
                state = CommonConst.InfoState_Rejected;
                if(backContent==null || "".equals(backContent)) {
                    backContent = "不同意";
                }
            }
            info.setStState(state);
            info.setStBackContent(backContent);
            info.setStApprovalUserId(user.getStUserId());
            info.setDtApproval(new Timestamp(System.currentTimeMillis()));

            this.inforDao.approvalInform(info);
        }
    }

    @Override
    public BaseRecords<TBoard> selectBoardList() {
        return this.inforDao.selectAllBoard();
    }

    @Override
    public BaseRecords<TDepartment> selectDepartment(String orgId) {
        return this.inforDao.selectDepartmentList(orgId);
    }

    @Override
    public void deleteInform(String id) {
        this.inforDao.deleteInform(Integer.valueOf(id));
    }

    @Override
    public void deleteDeptId(String deptMiddleId) {
        if(!StringUtils.isEmpty(deptMiddleId)) {
            List<TScanDepartment> departmentList = this.getDepartmentList(deptMiddleId);
            for(TScanDepartment department:departmentList) {
                this.inforDao.delete(department);
            }
        }
    }

    @Override
    public List<TScanDepartment> getDepartmentList(String deptMiddleId) {
        List<TScanDepartment> departmentList = new ArrayList<TScanDepartment>();
        if("".equals(deptMiddleId) || deptMiddleId == null) {
            return departmentList;
        } else {
            BaseRecords records = this.inforDao.selectDepartmentByMiddleId(deptMiddleId);
            int size = records.getData().size();

            for(int i = 0; i<size; i++) {
                departmentList.add((TScanDepartment)records.getData().get(i));
            }
            return departmentList;
        }
    }

    @Override
    public void addScanDepart(String departId, String departMiddleId) {
        //String[] array = departIds.split(",");

        //for(int i = 0 ; i<array.length; i++) {
            TScanDepartment scanDepart = new TScanDepartment();
            //System.out.println(array[i]);
            scanDepart.setStDepartmentId(departId);
            scanDepart.setStScanDepartMiddleId(departMiddleId);
            this.inforDao.saveScanDepart(scanDepart);
        //}
    }

    @Override
    public void recallInform(String id) {
        this.inforDao.recallInform(Integer.valueOf(id));
    }

    @Override
    public TWebName getWeb(int webNameId) {
        return this.inforDao.getWeb(webNameId);
    }

    @Override
    public TWebColumn getColumn(int webColumnId) {
        return this.inforDao.getWebColumn(webColumnId);
    }

    @Override
    public String sendMessage(String phone, String code, int infoId) throws IOException {
        String responseMsg = "";
        //1.构造HttpClient的实例
        HttpClient httpClient=new HttpClient();
        /*httpClient.getParams().setContentCharset("GBK");*/
        /*String url="http://218.75.63.106:3966/MessageTask/task/sendMessage";*/
        String url="http://172.20.24.100:3966/Middle/task/sendMessage";
        //String url="http://218.75.63.106:3966/Middle/task/sendMessage";
        //2.构造PostMethod的实例
        PostMethod postMethod=new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        //3.把参数值放入到PostMethod对象中
        //方式1：
        /*NameValuePair[] data = { new NameValuePair("tel", "15888398873,15990305045"),
        new NameValuePair("code", "SMS_12740170") };
        postMethod.setRequestBody(data);*/

        //方式2：
        /*String phone = "15888398873,15868324190";*/
        postMethod.addParameter("phone", phone);
        postMethod.addParameter("code", code);
        postMethod.addParameter("signature","SMS_27350215");
        try {
            // 4.执行postMethod,调用http接口
            httpClient.executeMethod(postMethod);//200
            //5.读取内容
            responseMsg = postMethod.getResponseBodyAsString().trim();
            //6.处理返回的内容
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //7.释放连接
            postMethod.releaseConnection();
        }

        if(responseMsg != null && !"".equals(responseMsg)) {
            String result = responseMsg.replace("\\","");
            result = result.replace("\"","");
            String[] array = result.split("_");

            if(infoId!=0) {
                TInformation info = this.selectInfoDetail(infoId+"");
                //传回的短信反馈格式为“successNum_sum_failPhoneNum” 即“成功发送数量_总发送数量_失败号码”（逗号隔开），若成功条数等于总条数则没有失败号码
                info.setStResult(array[0]+"/"+array[1]);
                if(!array[0].equals(array[1])) {
                    info.setStFailnum(array[2]);
                }

                this.inforDao.saveOrUpdate(info);
            } else {
                //预警短信只发送一条，若发送成功则array[0]为1
                if("1".equals(array[0])) {
                    return "success";
                } else {
                    return "fail";
                }
            }
        } else {
            if(infoId!=0) {
                TInformation info = this.selectInfoDetail(infoId+"");
                String pNum[] = phone.split(",");
                info.setStResult("0/"+pNum.length);
                info.setStFailnum(phone);

                this.inforDao.saveOrUpdate(info);
            } else {
                //预警短信只发送一条，若发送成功则array[0]为1
            }
        }
        //判断是否传入信息发布主键，若为0则为预警短信发送

        return "success";
    }
}
