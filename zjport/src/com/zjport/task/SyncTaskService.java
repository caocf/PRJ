package com.zjport.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjport.common.service.CommonService;
import com.zjport.model.TDepartment;
import com.zjport.model.TOrg;
import com.zjport.model.TUser;
import com.zjport.util.Value;
import org.apache.axis.client.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by TWQ on 2017/3/3.
 */
@Service
@Transactional
public class SyncTaskService {

    @Autowired
    private CommonService commonService;

    int addOrgNum = 0;
    int addDeptNum = 0;
    int updateOrgNum = 0;
    int updateDeptNum = 0;

    /**
     * 同步用户表
     */
    public String getData(String method) {
        String result="";
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress("http://172.26.24.33/webservice/webService.asmx");
            /*call.setTargetEndpointAddress("http://61.153.219.73/webservice/WebInterface.asmx");*/
            call.setOperationName(method);
            call.setSOAPActionURI("http://tempuri.org/"+method);
            call.setTimeout(Integer.valueOf(60*1000));
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            result+= (String)call.invoke(new Object[]{});

        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void saveUpdateUser(JSONArray array) {
        int addNum = 0;
        int updateNum = 0;
        for(int i=0; i<array.size();i++) {
            JSONObject obj = array.getJSONObject(i);
            TUser user = this.commonService.getUserBySyncId(obj.get("ID").toString());
            if(user == null) {
                user = new TUser();
                user.setStJs(3);
                user.setStUserSyncId(obj.get("ID").toString());
                System.out.println(obj.get("ZH").toString());
                addNum++;
            } else {
                updateNum++;
            }
            user.setStAccount(obj.get("ZH").toString());
            user.setStPassword(obj.get("MM").toString());
            user.setStUserName(obj.get("XM").toString());
            user.setStDepartmentId(obj.get("BM").toString());
            user.setStOrgId(obj.get("DW").toString());
            user.setStPhone(obj.get("SJHM").toString());
            user.setStEmail(obj.get("YX").toString());
            user.setStOfficePhone(obj.get("BGDH").toString());
            user.setStFuctitiousPhone(obj.get("XNWH").toString());
            user.setStAddress(Value.of(obj.get("WZH").toString(),1000));
            user.setStOrder(Value.of(obj.get("WZH").toString(),1000));
            user.setStOldAccount(obj.get("LZH").toString());

            this.commonService.saveSyncUser(user);
        }

        System.out.println("*************本次同步总共新增用户数据："+addNum+"条，更新用户数据："+updateNum+"条***************");
    }

    public void saveUpdateOrgDept(JSONObject obj) {

        if("1".equals(obj.getString("ZZJGLB"))) {
            TOrg org = this.commonService.getOrgById(obj.get("ID").toString());
            if(org == null) {
                org = new TOrg();
                org.setStOrgId(obj.getString("ID"));
                addOrgNum++;
            } else {
                updateOrgNum++;
            }
            org.setStOrgName(obj.getString("ZZJGMC"));
            org.setStOrgCode(obj.getString("ZZJGBM"));
            org.setStOrgDomain(obj.getString("ZZYM"));
            org.setStParentOrgId(obj.getString("SJZZJG"));
            org.setStOrgShortName(obj.getString("ZZJGJC"));

            this.commonService.saveSyncOrg(org);
        } else if("2".equals(obj.getString("ZZJGLB"))) {
            TDepartment dept = this.commonService.getDeptById(obj.get("ID").toString());
            if(dept == null) {
                dept = new TDepartment();
                dept.setStDepartmentId(obj.getString("ID"));
                addDeptNum++;
            } else {
                updateDeptNum++;
            }
            dept.setStDepartmentName(obj.getString("ZZJGMC"));
            dept.setStOrgId(obj.getString("SJZZJG"));

            this.commonService.saveSyncDept(dept);
        }

        String tempdept = obj.getString("dept");
        if (!"".equals(tempdept)) {
            JSONArray arr = obj.getJSONArray("dept");
            int len = arr.size();
            for (int i = 0; i < len; i++) {
                saveUpdateOrgDept(arr.getJSONObject(i));
            }
        }
    }

    public String[] getOrgDeptNum() {
        String[] array = new String[4];
        array[0] = String.valueOf(addOrgNum);
        array[1] = String.valueOf(addDeptNum);
        array[2] = String.valueOf(updateOrgNum);
        array[3] = String.valueOf(updateDeptNum);
        return array;
    }
}
