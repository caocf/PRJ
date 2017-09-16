package com.zjport.smart.service.impl;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.dao.impl.querycondition.SQL;
import com.common.base.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.zjport.model.TCurise;
import com.zjport.model.TCurisePhoto;
import com.zjport.model.TUser;
import com.zjport.smart.dao.ICruiseDao;
import com.zjport.smart.service.ICruiseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Will on 2016/9/28 10:35.
 */
@Repository("cruiseService")
public class CruiseServiceImpl extends BaseService implements ICruiseService {

    public static final Logger log = LoggerFactory.getLogger(CruiseServiceImpl.class);

    @Resource(name = "cruiseDao")
    private ICruiseDao cruiseDao;

    @Override
    public String savePic(String relativePath, String realPath, String picBs64) {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String datePath = year + StringUtils.leftPad(month, 2, '0') + StringUtils.leftPad(day, 2, '0');


        int start = picBs64.indexOf("image") + 6;
        int end = picBs64.indexOf(";");
        String extensionName = picBs64.substring(start, end);
        String fn = UUID.randomUUID().toString();

        log.debug(picBs64);
        String result = null;
        OutputStream fos = null;
        try {

            File dir = new File(realPath + File.separator + datePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!StringUtils.isEmpty(picBs64)) {
                log.info(picBs64.substring(picBs64.indexOf(",") + 1));
                byte[] picByte = new BASE64Decoder().decodeBuffer(picBs64.substring(picBs64.indexOf(",") + 1));
                for (int i = 0; i < picByte.length; i++) {
                    if (picByte[i] < 0) {
                        picByte[i] += 256;
                    }
                }
                String fileName = realPath + File.separator + datePath + File.separator + fn + "." + extensionName;
                File outFile = new File(fileName);
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }
                fos = new FileOutputStream(outFile);
                fos.write(picByte);
                fos.flush();
                result = relativePath + "/" + datePath + "/" + fn + "." + extensionName;
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("save pic error", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    @Override
    public BaseResult saveCruisePhoto(TCurisePhoto tp) {
        BaseResult result = new BaseResult();
        try {
            cruiseDao.save(tp);
            result.setResultcode(BaseResult.RESULT_OK);
            result.setObj(tp);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultcode(BaseResult.RESULT_FAILED);
            result.setResultdesc("提交失败");
        }
        return result;
    }

    @Override
    public void saveCruise(TCurise curise) {
        cruiseDao.save(curise);
    }

    @Override
    public void updateCruise(TCurise curise) {
        cruiseDao.update(curise);
    }

    @Override
    public void updateCruises(List<TCurise> curises) {
        cruiseDao.update(curises);
    }

    @Override
    public void deleteCruise(TCurise curise) {
        curise.setStIsValid(TCurise.INVAILD);
        cruiseDao.update(curise);
    }

    @Override
    public void deleteCruises(List<TCurise> curises) {
        for (TCurise curise : curises) {
            curise.setStIsValid(TCurise.INVAILD);
        }
        cruiseDao.update(curises);
    }

    @Override
    public BaseRecords<TCurise> queryCruiseHistory(String userId, int page, int rows, String startTime, String endTime, String channel) {
        String hql = "from TCurise as tc where tc.stIsValid = 1 and tc.dtCuriseEnd is not null";
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isBlank(userId)) {
            hql += " and tc.stCruiseUser = :userId";
            param.put("userId", Integer.valueOf(userId));
        }
        if (!StringUtils.isBlank(startTime)) {
            hql += " and tc.dtCuriseBegin >= :start";
            Date start = DateTimeUtil.getDateByStringFmt(startTime);
            param.put("start", start);
        }
        if (!StringUtils.isBlank(endTime)) {
            hql += " and tc.dtCuriseEnd <= :end";
            Date end = DateTimeUtil.getDateByStringFmt(endTime);
            param.put("end", end);
        }
        if (!StringUtils.isBlank(channel)) {
            hql += " and tc.stChannel = :channel";
            param.put("channel", channel);
        }
        hql += " order by tc.dtCuriseBegin desc";
        HQL query = new HQL(hql, param);
        query.setPage(page);
        query.setRows(rows);
        return cruiseDao.findByHql(query);
    }

    @Override
    public TCurise queryCruisebyId(Integer stCruiseId) {
        ObjectQuery query = new ObjectQuery(TCurise.class, "stCruiseId", stCruiseId);
        return (TCurise) cruiseDao.findUnique(query);
    }

    @Override
    public TCurise queryCruisebyMiddeId(String stCuriseMiddleId) {
        ObjectQuery query = new ObjectQuery(TCurise.class, "stCuriseMiddleId", stCuriseMiddleId);
        return (TCurise) cruiseDao.findUnique(query);
    }

    @Override
    public BaseResult deleteCruisesById(int[] stCruiseId) {
        String sqlStr = "update t_curise set ST_IS_VALID = 0 where ST_CRUISE_ID in (:ids) ";
        Map<String, Object> param = new HashMap<>();
        List<Integer> ids = new ArrayList<>();
        for (int i : stCruiseId) {
            ids.add(i);
        }
        param.put("ids", ids);
        SQL sql = new SQL(sqlStr, param);
        int i = cruiseDao.updateSQL(sql);
        return BaseResult.newResultOK("成功更新" + i + "行");
    }

    @Override
    public TCurisePhoto queryPhotoByID(Integer stCruisePhotoId) {
        ObjectQuery query = new ObjectQuery(TCurisePhoto.class,"stCruisePhotoId",stCruisePhotoId);
        return (TCurisePhoto) cruiseDao.findUnique(query);

    }

    @Override
    public BaseResult queryCruiseDesc(Integer cruiseId) {
        ObjectQuery queryCurise = new ObjectQuery(TCurise.class,"stCruiseId",cruiseId);
        TCurise curise = (TCurise) cruiseDao.findUnique(queryCurise);

        ObjectQuery queryUser = new ObjectQuery(TUser.class,"stUserId",curise.getStCruiseUser());
        TUser user = (TUser) cruiseDao.findUnique(queryUser);

        ObjectQuery queryPhotos = new ObjectQuery(TCurisePhoto.class,"stCuriseMiddleId",curise.getStCuriseMiddleId());
        List<TCurisePhoto> photos = (List<TCurisePhoto>) cruiseDao.find(queryPhotos).getData();
        Map<String,Object> result = new HashMap<>();
        result.put("cruise",curise);
        result.put("user",user);
        result.put("photos",photos);
        return BaseResult.newResultOK(result);
    }

    @Override
    public void updateCruisePhoto(TCurisePhoto tCurisePhoto) {
        cruiseDao.update(tCurisePhoto);
    }
}
