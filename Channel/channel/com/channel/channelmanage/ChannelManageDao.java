package com.channel.channelmanage;

import com.channel.model.hz.*;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.common.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/29.
 */
@Repository("channelmanagedao")
public class ChannelManageDao extends BaseDaoDB {
    public void addChannelManage(CHzAdminlicense adminlicense) {
        super.save(adminlicense);
    }

    public CHzAdminlicense queryEntityById(Integer id) {
        return (CHzAdminlicense) super.findUnique(new CHzAdminlicense(), "id", id);
    }

    public void delChannelManage(CHzAdminlicense adminlicense) {
        super.delete(adminlicense);
    }

    public BaseQueryRecords queryChannelManage(Integer xmlx, Integer jzwxz, String strstarttime, String strendtime, Integer contenttype, String content, int page, int rows) {
        String hql = "from CHzAdminlicense where 1=1";
        if (xmlx != 0) {
            hql += " and xmlx =" + xmlx;
        }
        if (jzwxz != 0) {
            hql += " and jzwxz=" + jzwxz;
        }
        if (strstarttime != null && !"".equals(strstarttime)) {
            hql += " and xkrq>='" + strstarttime + "'";
        }
        if (strendtime != null && !"".equals(strendtime)) {
            hql += " and xkrq<='" + strendtime + "'";
        }
        if (content != null && !"".equals(content)) {
            if (contenttype == 1) {
                hql += " and sqdw like '%" + content + "%'";
            } else if (contenttype == 2) {
                hql += " and xkbh like '%" + content + "%'";
            } else {
                hql += " and (sqdw like '%" + content + "%' or xkbh like '%" + content + "%')";
            }
        }
        hql += " order by xkrq desc";
        return super.find(new HQL(hql), page, rows);
    }

    public void addArgument(CHzArgument argument) {
        super.save(argument);
    }

    public void addCYhFj(CHzThlzfj fj) {
        super.save(fj);
    }

    public CHzThlzfj queryFjByMd5(int pid, String infileMd5) {
        String hql = "from CHzThlzfj where pid=? and filemd5='?'";
        return (CHzThlzfj) super.findUnique(new HQL(hql, pid, infileMd5));
    }

    public CHzArgument queryArgumentById(Integer thlzid) {
        String hql = "from CHzArgument where id=?";
        return (CHzArgument) super.findUnique(new HQL(hql, thlzid));
    }

    public void delArgument(CHzArgument argument) {
        super.delete(argument);
    }

    public BaseQueryRecords queryArgument(Date starttime, Date endtime, String content, int page, int rows) {
        String hql = "from CHzArgument where 1=1";
        if (starttime != null) {
            String strstarttime = DateTimeUtil.getTimeFmt(starttime,
                    "yyyy-MM-dd");
            hql += " and atime>='" + strstarttime + " 00:00:00'";
        }
        if (endtime != null) {
            String strendtime = DateTimeUtil.getTimeFmt(endtime,
                    "yyyy-MM-dd");
            hql += " and atime<='" + strendtime + " 23:59:59'";
        }
        if (content != null && !"".equals(content)) {
            hql += " and name like'%" + content + "%'";
        }
        return super.find(new HQL(hql), page, rows);
    }

    public BaseQueryRecords<CHzThlzfj> queryFjByPid(int pid) {
        return (BaseQueryRecords<CHzThlzfj>) super.find(new CHzThlzfj(), "pid", pid);
    }

    public void delCHzFjByPid(Integer pid) {
        String hql = "delete from CHzThlzfj where pid=?";
        super.delete(new HQL(hql, pid));
    }

    public void delCHzFjById(Integer id) {
        String hql = "delete from CHzThlzfj where id=?";
        super.delete(new HQL(hql, id));
    }

    public CHzThlzfj queryCHzFjById(Integer id) {
        return (CHzThlzfj) super.findUnique(new CHzThlzfj(), "id", id);
    }

    public BaseQueryRecords queryReparation(Date starttime, Date endtime, String content, int page, int rows) {
        String hql = "from CHzCzpc  where 1=1";
        if (starttime != null) {
            String strstarttime = DateTimeUtil.getTimeFmt(starttime,
                    "yyyy-MM-dd");
            hql += " and slrq>='" + strstarttime + " 00:00:00'";
        }
        if (endtime != null) {
            String strendtime = DateTimeUtil.getTimeFmt(endtime,
                    "yyyy-MM-dd");
            hql += " and slrq<='" + strendtime + " 23:59:59'";
        }
        if (content != null && !"".equals(content)) {
            hql += " and name like'%" + content + "%'";
        }
        return super.find(new HQL(hql), page, rows);
    }

    public CHzCzpc queryReparationById(Integer id) {
        return (CHzCzpc) super.findUnique(new CHzCzpc(), "id", id);
    }

    public BaseQueryRecords queryReparationRecordById(Integer id) {
        String hql = "from CHzCzpc a,CHzCzpclx b where a.id=b.pid and a.id=?";
        return super.find(new HQL(hql, id));
    }

    public void delCHzCzpclxByPid(Integer id) {
        super.delete(new HQL("delete from CHzCzpclx where pid=?", id));
    }

    public void delReparation(CHzCzpc czpc) {
        super.delete(czpc);
    }

    public BaseQueryRecords queryCzpclxByPid(Integer pid) {
        String hql = "from CHzCzpclx where pid=? order by dfl,xfl";
        return super.find(new HQL(hql, pid));
    }

    public BaseQueryRecords queryArgument(int xzqh, Date starttime, Date endtime, String content, int page, int rows) {
        String hql = "select a from CHzArgument a,CXtUser b,CXtDpt c where a.creater=b.id and b.department=c.id";
        if (xzqh > 0) {
            hql += " and c.xzqh=" + xzqh;
        }
        if (starttime != null) {
            String strstarttime = DateTimeUtil.getTimeFmt(starttime,
                    "yyyy-MM-dd");
            hql += " and a.atime>='" + strstarttime + " 00:00:00'";
        }
        if (endtime != null) {
            String strendtime = DateTimeUtil.getTimeFmt(endtime,
                    "yyyy-MM-dd");
            hql += " and a.atime<='" + strendtime + " 23:59:59'";
        }
        if (content != null && !"".equals(content)) {
            hql += " and a.name like'%" + content + "%'";
        }
        return super.find(new HQL(hql), page, rows);
    }

    public BaseQueryRecords queryReparation(int xzqh, Date starttime, Date endtime, String content, int page, int rows) {
        String hql = "select a from CHzCzpc a,CXtUser b,CXtDpt c where a.creater=b.id and b.department=c.id";
        if (xzqh > 0) {
            hql += " and c.xzqh=" + xzqh;
        }
        if (starttime != null) {
            String strstarttime = DateTimeUtil.getTimeFmt(starttime,
                    "yyyy-MM-dd");
            hql += " and a.slrq>='" + strstarttime + " 00:00:00'";
        }
        if (endtime != null) {
            String strendtime = DateTimeUtil.getTimeFmt(endtime,
                    "yyyy-MM-dd");
            hql += " and a.slrq<='" + strendtime + " 23:59:59'";
        }
        if (content != null && !"".equals(content)) {
            hql += " and a.name like'%" + content + "%'";
        }
        return super.find(new HQL(hql), page, rows);
    }
}
