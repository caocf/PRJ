package com.zjport.smart.dao.impl;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.smart.dao.ICameraDao;
import com.zjport.video.model.ChannelCamera;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Will on 2016/10/13 13:42.
 */
@Repository("cameraDao")
public class CameraDaoImpl extends BaseDaoDB implements ICameraDao {
    private final static Logger log = LoggerFactory.getLogger(CameraDaoImpl.class);
    @Override
    public List<ChannelCamera> queryParamentLocationByIds(Integer[] cameraIds) {
        String hql = "from ChannelCamera where cid in (?) order by cid";
        List<Integer> param = new ArrayList<>();
        for (int i : cameraIds) {
            param.add(i);
        }
        return (List<ChannelCamera>) this.find(new HQL(hql,param)).getData();
    }

    @Override
    public List<Integer> queryCameraIdsByHduanId(Integer hduanId) {
        String sql = "select cid from channelcamera where hdid = ?";
        return (List<Integer>) find(new SQL(sql,hduanId)).getData();
    }

    @Override
    public BaseRecords queryCamerasByHdaoId(Integer hdaoId) {
        String sql = "SELECT cp.camera_name,cc.lat,cc.lon,cc.nickname from channelcamera cc LEFT JOIN t_camera_parament cp on cc.cid = cp.camera_id where cc.hdid = ?";
        return find(new SQL(sql,hdaoId));
    }

    @Override
    public List<Map<String, Object>> queryCameraInfosByHdaoId(Integer hduanId, Integer order) {
        List<Map<String, Object>> result = Collections.EMPTY_LIST;
        String sql = "SELECT " +
                "  cp.camera_id, " +
                "  cp.camera_index_code, " +
                "  cp.camera_name, " +
                "  cp.cascad, " +
                "  cp.cascade_camera_index_code, " +
                "  cp.cascade_ias_ip, " +
                "  cp.cascade_ias_port, " +
                "  cp.cascade_stream_rtsp_path, " +
                "  cp.cascad_device_type, " +
                "  cp.channal_num, " +
                "  cp.control_unit_id, " +
                "  cp.device_id, " +
                "  cp.device_index_code, " +
                "  cp.device_ip, " +
                "  cp.device_port, " +
                "  cp.device_type, " +
                "  cp.digital_zoom, " +
                "  cp.dtream_cu, " +
                "  cp.is_ehome_device, " +
                "  cp.is_use_stream_server, " +
                "  cp.linked_mode, " +
                "  cp.matrix_code, " +
                "  cp.monitor_id, " +
                "  cp.pag_ip, " +
                "  cp.pag_port, " +
                "  cp.`password`, " +
                "  cp.protocol_type, " +
                "  cp.ptz_config_privilege, " +
                "  cp.ptz_control, " +
                "  cp.record_video, " +
                "  cp.region_id, " +
                "  cp.snap_pricture, " +
                "  cp.stream_pu, " +
                "  cp.stream_server_ip, " +
                "  cp.stream_server_port, " +
                "  cp.stream_type, " +
                "  cp.three_d_position, " +
                "  cp.USECISCO, " +
                "  cp.`user`, " +
                "  cp.user_id, " +
                "  cp.user_right_level, " +
                "  cp.user_session, " +
                "  cp.use_stream_server_cascade, " +
                "  cp.vag_ip, " +
                "  cp.vag_port, " +
                "  cp.video_config_privilege, " +
                "  cc.lat, " +
                "  cc.lon " +
                "FROM " +
                "  t_camera_parament cp " +
                "RIGHT JOIN channelcamera cc ON cp.camera_id = cc.cid " +
                "WHERE " +
                "  cc.hdid = ?";
        if(Integer.valueOf(1).equals(order)){
//            sql += " order by ";//当order==1是反序，字段为定义
        }
        Session session = null;
        SQL SQL = new SQL(sql,hduanId);
        try {
            session = getSessionHandler(SQL).openSession(getSessionFactory());
            SQLQuery query = session.createSQLQuery(SQL.getSQLString());
            Map<String,Object> paramMap = SQL.getParamMap();
            Set<Map.Entry<String,Object>> set = paramMap.entrySet();
            for (Map.Entry<String,Object> entry: set) {
                query.setParameter(entry.getKey(),entry.getValue());
            }
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            result = query.list();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            getSessionHandler(SQL).closeSession(session);
        }
        return result;
    }
}
