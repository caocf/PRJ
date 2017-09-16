package com.zjport.officeAssistant.service;

import com.common.base.BaseRecords;
import com.common.base.service.BaseService;
import com.zjport.common.dao.CommonDao;
import com.zjport.model.*;
import com.zjport.officeAssistant.dao.OfficeAssistantDao;
import com.zjport.util.CommonConst;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWQ on 2016/9/6.
 */
@Service("officeAssistantService")
public class OfficeAssistantService extends BaseService{

    @Resource(name = "officeAssistantDao")
    private OfficeAssistantDao officeAssistantDao;
    @Resource(name = "commonDao")
    private CommonDao commonDao;


    public BaseRecords selectStructure() {
        return this.officeAssistantDao.selectStructure();
    }

    public BaseRecords selectKnowledgeBaseList(int structureId, String search, int rows, int page) {
        return this.officeAssistantDao.selectKnowledgeBaseList(structureId,search,rows,page);
    }

    public void saveKnowledgeBase(TKnowledgeBase base) { this.officeAssistantDao.saveOrUpdate(base);};

    public TKnowledgeBase getKnowledgeBase(String baseId) { return this.officeAssistantDao.getKnowledgeBaseById(Integer.valueOf(baseId));}

    public String getStructureName(int id) {
        TKnowledgeBaseStructure structure = this.officeAssistantDao.getStructureNameById(id);
        return structure.getStStructureName();
    }

    public void saveKnowledgeBaseStructure(String structureId, String parentStructure, String structureName, String structureDescribe) {

        TKnowledgeBaseStructure structure = new TKnowledgeBaseStructure();
        if(!StringUtils.isEmpty(structureId)) {
            structure = this.officeAssistantDao.getStructureById(Integer.valueOf(structureId));
        }
        structure.setStParentStructureId(Integer.valueOf(parentStructure));
        structure.setStStructureName(structureName);
        structure.setStStructureDescribe(structureDescribe);

        this.officeAssistantDao.saveOrUpdate(structure);
    }

    //针对知识库结构做到真删除
    public void deleteStructure(String structureId) {
        TKnowledgeBaseStructure structure = this.officeAssistantDao.getStructureById(Integer.valueOf(structureId));
        this.officeAssistantDao.delete(structure);
    }

    //具体文件都做到假删除，以防万一
    public void deleteFile(String baseId) {
        TKnowledgeBase base = this.officeAssistantDao.getKnowledgeBaseById(Integer.valueOf(baseId));
        base.setStIsValid(CommonConst.INVALID);
        this.officeAssistantDao.update(base);
    }

    public void moveFile(String baseId, String parentStructure) {
        TKnowledgeBase base = this.officeAssistantDao.getKnowledgeBaseById(Integer.valueOf(baseId));
        base.setStStructureId(Integer.valueOf(parentStructure));
        this.officeAssistantDao.update(base);
    }

    public BaseRecords selectUserIn(String type, String structureId, String search, int page, int rows) {
        //TYPE为1则搜索单位关联用户，TYPE为2则搜索部门关联用户
        return this.officeAssistantDao.getUserListByStructureId(type,structureId,search,page,rows);

    }

    public BaseRecords selectUserById(String userId) {
        return this.officeAssistantDao.getUserById(Integer.valueOf(userId));
    }

    public BaseRecords selectAddressGroup() {
        return this.officeAssistantDao.selectAddressGroup();
    }

    public BaseRecords selectAddressGroupList(String groupId, int page, int rows) {
        return this.officeAssistantDao.selectAddressGroupList(Integer.valueOf(groupId),page,rows);
    }

    public void saveUserInfo(String userId, String lawcode, String order) {
        this.officeAssistantDao.saveUserInfo(Integer.valueOf(userId),lawcode,Integer.valueOf(order));
    }


    public List<TCalendar> selectCalendar(int userId) {
        List<TCalendar> calendarList = new ArrayList<TCalendar>();
        BaseRecords records = this.officeAssistantDao.selectCalendarList(userId);
        for(int i = 0; i<records.getData().size(); i++) {
            Object[] calendarArray = (Object[])records.getData().get(i);
            TCalendar calendar = new TCalendar();
            calendar.setStCalendarId(Integer.valueOf(calendarArray[0].toString()));
            calendar.setStCalendarTitle(calendarArray[1].toString());
            calendar.setStUserId(Integer.valueOf(calendarArray[2].toString()));
            calendar.setDtStart(Timestamp.valueOf(calendarArray[3].toString()));
            calendar.setDtEnd(Timestamp.valueOf(calendarArray[4].toString()));
            calendar.setStContent(calendarArray[5].toString());
            calendar.setStType(calendarArray[6].toString());
            calendar.setStCalendarMiddleId(calendarArray[7].toString());
            calendar.setStUrgentState(calendarArray[8].toString());
            calendar.setStAttachmentMiddleId(calendarArray[9].toString());
            calendar.setStIsAlert(Boolean.valueOf(calendarArray[10].toString()));
            calendarList.add(calendar);
        }

        return calendarList;
    }

    public void saveCalendar(TCalendar calendar) {
        this.officeAssistantDao.saveOrUpdate(calendar);
    }

    public void saveAttendUser(TCalendarAttendUser attendUser) {this.officeAssistantDao.saveOrUpdate(attendUser);}

    public TCalendar getCalendar(String id) {
        return this.officeAssistantDao.getCalendarById(Integer.valueOf(id));
    }

    public void deleteCalendar(TCalendar calendar) {
        this.officeAssistantDao.delete(calendar);
    }

    public String getNamesByMiddleId(String middleId) {
        List<TCalendarAttendUser> attendUserList = this.getAttendUserListByMiddleId(middleId);
        String name = "";
        for(TCalendarAttendUser attendUser:attendUserList) {
            name+=this.commonDao.getNameById(attendUser.getStUserId())+";";
        }
        return name;
    }

    public List<TCalendarAttendUser> getAttendUserListByMiddleId(String middleId) {
        BaseRecords records = this.officeAssistantDao.selectAttendUser(middleId);
        List<TCalendarAttendUser> attendUserList = new ArrayList<TCalendarAttendUser>();
        for(int i = 0; i<records.getData().size(); i++) {
            attendUserList.add((TCalendarAttendUser)records.getData().get(i));
        }
        return attendUserList;
    }

    public String getIdsByMiddleId(String middleId) {
        BaseRecords records = this.officeAssistantDao.selectAttendUser(middleId);
        List<TCalendarAttendUser> attendUserList = new ArrayList<TCalendarAttendUser>();
        for(int i = 0; i<records.getData().size(); i++) {
            attendUserList.add((TCalendarAttendUser)records.getData().get(i));
        }

        String id = "";
        for(TCalendarAttendUser attendUser:attendUserList) {
            id+=attendUser.getStUserId()+",";
        }
        return id;
    }

    public void deleteAttendUser(String middleId) {
        if(!StringUtils.isEmpty(middleId)) {
            List<TCalendarAttendUser> attendUserList = this.getAttendUserListByMiddleId(middleId);
            for(TCalendarAttendUser attendUser:attendUserList) {
                this.officeAssistantDao.delete(attendUser);
            }
        }
    }

    public BaseRecords showGroupList(String id) {
        return this.officeAssistantDao.getGroupList(Integer.valueOf(id));
    }

    public TAddressListGroup getAddressListGroup(String id) {return this.officeAssistantDao.getGroupById(Integer.valueOf(id));}

    public void saveGroup(TAddressListGroup group) {
        this.officeAssistantDao.saveOrUpdate(group);
    }

    public void saveUserMiddle(String userId, int groupId) {
        TAddressListMiddle middle = new TAddressListMiddle();
        middle.setStUserId(Integer.valueOf(userId));
        middle.setStAddressListGroupId(groupId);
        this.officeAssistantDao.save(middle);
    }

    public List<TAddressListMiddle> getAddressListMiddleId(int groupId) {
        BaseRecords records = this.officeAssistantDao.selectAddressListMiddleByGroupId(groupId);
        List<TAddressListMiddle> middleList = new ArrayList<TAddressListMiddle>();
        for(int i = 0; i<records.getData().size(); i++) {
            middleList.add((TAddressListMiddle)records.getData().get(i));
        }
        return middleList;
    }

    public void deleteUserMiddle(int groupId) {
        if(!StringUtils.isEmpty(groupId)) {
            List<TAddressListMiddle> middleList = this.getAddressListMiddleId(groupId);
            for(TAddressListMiddle middle:middleList) {
                this.officeAssistantDao.delete(middle);
            }
        }
    }

    public BaseRecords showGroupDetail(String id) {
        return this.officeAssistantDao.getGroupDetail(Integer.valueOf(id));
    }

    public void deleteAddressListGroup(String id) {
        if(!StringUtils.isEmpty(id)) {
            List<TAddressListMiddle> middleList = this.getAddressListMiddleId(Integer.valueOf(id));
            for(TAddressListMiddle middle:middleList) {
                this.officeAssistantDao.delete(middle);
            }
            TAddressListGroup group = this.getAddressListGroup(id);
            this.officeAssistantDao.delete(group);
        }
    }
}
