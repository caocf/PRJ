package com.zjport.video;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.zjport.model.CameraBean;
import com.zjport.model.CameraSimpleBean;
import com.zjport.model.Parament;
import com.zjport.video.model.CHdHdaojcxx;
import com.zjport.video.model.CHdHduanjcxx;
import com.zjport.video.model.CZdXzqhdm;
import com.zjport.video.model.ChannelCamera;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("videoDao")
public class VideoDao extends BaseDaoDB {

    public List<CHdHdaojcxx> queryHdaos() {
        return super.find(new ObjectQuery(CHdHdaojcxx.class, "id", false)).getData();
    }

    public List<CHdHduanjcxx> queryHduans() {
        return super.find(new ObjectQuery(CHdHduanjcxx.class, "id", false)).getData();
    }

    public List<CameraBean> queryCameraBean() {
        Session session = getSessionFactory().openSession();
        String sql = "select a.id,a.sshdid,a.HDQDMC,a.HDZDMC,ifnull(b.cid,0) as cid,ifnull(b.ctype,0) as ctype,ifnull(c.camera_name,'') as cname from c_hd_hduanjcxx a LEFT JOIN channelcamera b on a.id=b.hdid LEFT JOIN t_camera_parament c on b.cid=c.camera_id  ORDER BY a.id";
        SQLQuery q = session.createSQLQuery(sql);
        q.addScalar("id", StandardBasicTypes.INTEGER);
        q.addScalar("sshdid", StandardBasicTypes.INTEGER);
        q.addScalar("hdqdmc", StandardBasicTypes.STRING);
        q.addScalar("hdzdmc", StandardBasicTypes.STRING);
        q.addScalar("cid", StandardBasicTypes.INTEGER);
        q.addScalar("ctype", StandardBasicTypes.INTEGER);
        q.addScalar("cname", StandardBasicTypes.STRING);
        q.setResultTransformer(Transformers.aliasToBean(CameraBean.class));
        return q.list();
    }

    public List<CHdHduanjcxx> queryHduanByPid(int hdid, String xzqhs) {
        String hql = "from CHdHduanjcxx where 1=1";
        if (hdid > 0) {
            hql += " and sshdid=" + hdid;
        }
        if (!"".equals(xzqhs)) {
            hql += " and hdszxzqh in(" + xzqhs + ")";
        }
        return (List<CHdHduanjcxx>) super.find(new HQL(hql)).getData();
    }

    public List<CameraSimpleBean> queryCameraBean(int hdid, int ctype) {
        Session session = getSessionFactory().openSession();
        String sql = "select ifnull(b.camera_id,0) as cid,ifnull(b.camera_name,'') as cname from channelcamera a JOIN t_camera_parament b on a.cid=b.camera_id where a.hdid=" + hdid + " and a.ctype=" + ctype;
        SQLQuery q = session.createSQLQuery(sql);
        q.addScalar("cid", StandardBasicTypes.INTEGER);
        q.addScalar("cname", StandardBasicTypes.STRING);
        q.setResultTransformer(Transformers.aliasToBean(CameraSimpleBean.class));
        return q.list();
    }

    public BaseRecords queryHdaoByName(String name, String xzqhs) {
        String hql = "select a from CHdHdaojcxx a,CHdHduanjcxx b where a.id=b.sshdid and a.HDMC like ?";
        if (!"".equals(xzqhs) && xzqhs != null) {
            hql += " and b.hdszxzqh in(" + xzqhs + ")";
        }
        hql += " group by a.id";
        return super.find(new HQL(hql, "%" + name + "%"));
    }

    public BaseRecords queryHduanByName(String name, String xzqhs) {
        String hql = "from CHdHduanjcxx where HDQDMC like ? or HDZDMC like ?";
        if (!"".equals(xzqhs) && xzqhs != null) {
            hql += " and hdszxzqh in(" + xzqhs + ")";
        }
        return super.find(new HQL(hql, "%" + name + "%", "%" + name + "%"));
    }

    public BaseRecords queryCameraByName(String name, String xzqhs) {
        String hql = "select a from Parament a,ChannelCamera b,CHdHduanjcxx c where c.id=b.hdid and b.cid=a.cameraId  and a.cameraName like ?";
        if (!"".equals(xzqhs) && xzqhs != null) {
            hql += " and c.hdszxzqh in(" + xzqhs + ")";
        }
        hql += " group by a.id";
        return super.find(new HQL(hql, "%" + name + "%"));
    }

    public CHdHdaojcxx queryHdaoById(int id) {
        return (CHdHdaojcxx) super.findUnique(new ObjectQuery(CHdHdaojcxx.class, "id", id));
    }

    public BaseRecords queryHduanById(Integer id) {
        String hql = "from CHdHdaojcxx a,CHdHduanjcxx b where a.id=b.sshdid and b.id=?";
        return super.find(new HQL(hql, id));
    }

    public BaseRecords queryVideoById(Integer id) {
        String hql = "from CHdHdaojcxx a,CHdHduanjcxx b,ChannelCamera c,Parament d where a.id=b.sshdid and b.id=c.hdid and c.cid=d.cameraId and d.id=?";
        return super.find(new HQL(hql, id));
    }

    public List<Parament> queryParamentNoHduan() {
        String hql = "from Parament where camera_id not in(select DISTINCT cid from ChannelCamera)";
        return (List<Parament>) super.find(new HQL(hql)).getData();
    }

    public Parament queryParamentById(Integer id) {
        return (Parament) super.findUnique(new ObjectQuery(Parament.class, "id", id));
    }

    public ChannelCamera queryChannelCameraById(Integer id) {
        return (ChannelCamera) super.findUnique(new ObjectQuery(ChannelCamera.class, "cid", id));
    }


    public BaseRecords queryCameras(List<CZdXzqhdm> xzqhs) {
        String hql = "from CHdHduanjcxx b,ChannelCamera c,Parament d,CHdHdaojcxx a where b.id=c.hdid and c.cid=d.cameraId and a.id=b.sshdid";
        if (xzqhs != null) {
            hql += " and b.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        return super.find(new HQL(hql));
    }

    public BaseRecords queryHdaoXzqh(List<CZdXzqhdm> xzqhs) {
        String hql = "select a from CHdHdaojcxx a,CHdHduanjcxx b where a.id=b.sshdid";
        if (xzqhs != null) {
            hql += " and b.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        hql += " group by a.id";
        return super.find(new HQL(hql));
    }

    public BaseRecords queryHduanXzqh(List<CZdXzqhdm> xzqhs) {
        String hql = "from CHdHduanjcxx";
        if (xzqhs != null) {
            hql += " where hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        return super.find(new HQL(hql));
    }

    public BaseRecords queryHduanSshdid(Integer sshdid, List<CZdXzqhdm> xzqhs) {
        String hql = "from CHdHduanjcxx where 1=1";
        if (sshdid > 0) {
            hql += " and sshdid=" + sshdid;
        }
        if (xzqhs != null) {
            hql += " and hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        return super.find(new HQL(hql));
    }

    public CZdXzqhdm findXzqhByCode(String xzqh) {
        return (CZdXzqhdm) super.findUnique(new ObjectQuery(CZdXzqhdm.class, "code", xzqh));
    }

    public List<CZdXzqhdm> findAllXzqh() {
        String hql = "from CZdXzqhdm";
        return (List<CZdXzqhdm>) super.find(new HQL(hql)).getData();
    }

    public List<CZdXzqhdm> findChildXzqh(int pid) {
        String hql = "from CZdXzqhdm where id in(select sid from CZdXzqhRela where pid=" + pid + ")";
        return (List<CZdXzqhdm>) super.find(new HQL(hql)).getData();
    }

    public BaseRecords queryHdCamera(Integer hdid, Integer ctype) {
        String hql = "from ChannelCamera a,Parament b where a.cid=b.id";
        if (hdid > 0) {
            hql += " and a.hdid=" + hdid;
        }
        if (ctype > 0) {
            hql += " and a.ctype=" + ctype;
        }
        return super.find(new HQL(hql));
    }

    public BaseRecords queryHdaoStrXzqh(String userxzqh) {
        String hql = "select a from CHdHdaojcxx a,CHdHduanjcxx b where a.id=b.sshdid";
        if (!"".equals(userxzqh) && userxzqh != null) {
            hql += " and b.hdszxzqh in(" + userxzqh + ")";
        }
        hql += " group by a.id";
        return super.find(new HQL(hql));
    }

    public void updateChannelCamera(ChannelCamera c) {
        super.update(c);
    }

    public BaseRecords queryHdaoCamera(List<CZdXzqhdm> xzqhs) {
        String hql = "select a from CHdHdaojcxx a,CHdHduanjcxx b,ChannelCamera c where a.id=b.sshdid and b.id=c.hdid";
        if (xzqhs != null) {
            hql += " and b.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        hql += " group by a.id";
        return super.find(new HQL(hql));
    }

    public BaseRecords queryHduanCamera(Integer sshdid, List<CZdXzqhdm> xzqhs) {
        String hql = "select a from CHdHduanjcxx a,ChannelCamera b where a.id=b.hdid";
        if (sshdid > 0) {
            hql += " and a.sshdid=" + sshdid;
        }
        if (xzqhs != null) {
            hql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        hql += " group by a.id";
        return super.find(new HQL(hql));
    }
}
