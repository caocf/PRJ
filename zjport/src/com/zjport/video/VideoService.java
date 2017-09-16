package com.zjport.video;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.service.BaseService;
import com.zjport.model.CameraBean;
import com.zjport.model.CameraSimpleBean;
import com.zjport.model.Parament;
import com.zjport.video.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("videoService")
public class VideoService extends BaseService {

    @Resource(name = "videoDao")
    VideoDao videoDao;

    public BaseResult loadHdaotree(String xzqh) {
        List<VideoTree> treeList = new ArrayList<>();
        List<CHdHdaojcxx> hdaoList = new ArrayList<>();
        // 根节点
        treeList.add(new VideoTree("a1", "骨干航道", "0", 1));
        treeList.add(new VideoTree("a2", "支线航道", "0", 1));
        treeList.add(new VideoTree("a3", "未分类", "0", 1));
        // 航道
        String userxzqh = this.queryUserXzqh(xzqh);
        hdaoList = this.videoDao.queryHdaoStrXzqh(userxzqh).getData();
        for (CHdHdaojcxx cHdHdaojcxx : hdaoList) {
            // 骨干航道
            if (cHdHdaojcxx.getSfgg() != null && cHdHdaojcxx.getSfgg().intValue() == 1) {
                treeList.add(new VideoTree("b" + cHdHdaojcxx.getId(), cHdHdaojcxx.getHdmc(), "a1", 2));
            } else {
                treeList.add(new VideoTree("b" + cHdHdaojcxx.getId(), cHdHdaojcxx.getHdmc(), "a2", 2));
            }
        }
        List<Parament> pList = this.videoDao.queryParamentNoHduan();
        for (Parament p : pList) {
            treeList.add(new VideoTree("nohdc" + p.getCameraId(), p.getCameraName(), "a3", 7));
        }
        return BaseResult.newResultOK(treeList);
    }

    public BaseResult addHdaotree(String xzqh, String id, Integer ptype) {
        List<VideoTree> treeList = new ArrayList<>();
        List<CameraSimpleBean> cb = new ArrayList<>();
        String temps = "";
        String xzqhs = this.queryUserXzqh(xzqh);
        switch (ptype) {
            //航段
            case 2:
                temps = id.replace("b", "");
                List<CHdHduanjcxx> list = this.videoDao.queryHduanByPid(Integer.parseInt(temps), xzqhs);
                for (CHdHduanjcxx hd : list) {
                    treeList.add(new VideoTree("c" + hd.getId(), hd.getHdqdmc() + "-" + hd.getHdzdmc(), id, 3));
                }
                break;
            case 3:
                temps = id.replace("c", "");
                treeList.add(new VideoTree("hd" + temps, "航段视频", id, 4));
                treeList.add(new VideoTree("mt" + temps, "码头视频", id, 5));
                treeList.add(new VideoTree("gcd" + temps, "观测点视频", id, 6));
                break;
            case 4:
                temps = id.replace("hd", "");
                cb = this.videoDao.queryCameraBean(Integer.parseInt(temps), 4);
                for (CameraSimpleBean b : cb) {
                    treeList.add(new VideoTree("hdc" + b.getCid(), b.getCname(), id, 7));
                }
                break;
            case 5:
                temps = id.replace("mt", "");
                cb = this.videoDao.queryCameraBean(Integer.parseInt(temps), 5);
                for (CameraSimpleBean b : cb) {
                    treeList.add(new VideoTree("mtc" + b.getCid(), b.getCname(), id, 7));
                }
                break;
            case 6:
                temps = id.replace("gcd", "");
                cb = this.videoDao.queryCameraBean(Integer.parseInt(temps), 6);
                for (CameraSimpleBean b : cb) {
                    treeList.add(new VideoTree("gcdc" + b.getCid(), b.getCname(), id, 7));
                }
                break;

        }
        return BaseResult.newResultOK(treeList);
    }

    private String queryUserXzqh(String xzqh) {
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        CZdXzqhdm xzqhnode = this.videoDao.findXzqhByCode(xzqh);
        if ("".equals(xzqh)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer("");
            int xzqhtype = xzqhnode.getType();
            switch (xzqhtype) {
                case 1:
                    xzqhs = this.videoDao.findAllXzqh();
                    break;
                case 2:
                    xzqhs = this.videoDao.findChildXzqh(xzqhnode.getId());
                    xzqhs.add(xzqhnode);
                    break;
                default:
                    xzqhs.add(xzqhnode);
                    break;
            }
            int len = xzqhs.size();
            for (int i = 0; i < len; i++) {
                CZdXzqhdm x = xzqhs.get(i);
                if (i == len - 1) {
                    sb.append(x.getId());
                } else {
                    sb.append(x.getId() + ",");
                }
            }
            return sb.toString();
        }
    }

    public BaseResult queryHit(Integer ctype, String name, String xzqh) {
        BaseRecords baseRecords = new BaseRecords();
        List<CameraSimpleBean> list = new ArrayList<>();
        String xzqhs = this.queryUserXzqh(xzqh);
        switch (ctype) {
            case 2:
                baseRecords = this.videoDao.queryHdaoByName(name,xzqhs);
                List<CHdHdaojcxx> hdao = baseRecords.getData();
                for (CHdHdaojcxx hdaojcxx : hdao) {
                    CameraSimpleBean csb = new CameraSimpleBean();
                    csb.setCid(hdaojcxx.getId());
                    csb.setCname(hdaojcxx.getHdmc());
                    list.add(csb);
                }
                break;
            case 3:
                baseRecords = this.videoDao.queryHduanByName(name,xzqhs);
                List<CHdHduanjcxx> hduan = baseRecords.getData();
                for (CHdHduanjcxx hduanjcxx : hduan) {
                    CameraSimpleBean csb = new CameraSimpleBean();
                    csb.setCid(hduanjcxx.getId());
                    csb.setCname(hduanjcxx.getHdqdmc() + "---" + hduanjcxx.getHdzdmc());
                    list.add(csb);
                }
                break;
            case 7:
                baseRecords = this.videoDao.queryCameraByName(name,xzqhs);
                List<Parament> plist = baseRecords.getData();
                for (Parament p : plist) {
                    CameraSimpleBean csb = new CameraSimpleBean();
                    csb.setCid(p.getCameraId());
                    csb.setCname(p.getCameraName());
                    list.add(csb);
                }
                break;
        }
        BaseResult result = new BaseResult();
        baseRecords.setData(list);
        result.setRecords(baseRecords);
        return result;
    }

    public BaseResult locationHit(Integer ctype, Integer id) {
        List<VideoTree> treeList = new ArrayList<>();
        BaseRecords records = new BaseRecords();
        List<Object[]> list = new ArrayList<>();
        CHdHdaojcxx hdao = null;
        CHdHduanjcxx hduan = null;
        ChannelCamera cc = null;
        Parament p = null;
        switch (ctype) {
            case 2:
                hdao = this.videoDao.queryHdaoById(id);
                if (hdao.getSfgg() != null && hdao.getSfgg().intValue() == 1) {
                    treeList.add(new VideoTree("b" + hdao.getId(), hdao.getHdmc(), "a1", 2));
                } else {
                    treeList.add(new VideoTree("b" + hdao.getId(), hdao.getHdmc(), "a2", 2));
                }
                break;
            case 3:
                records = this.videoDao.queryHduanById(id);
                if (records != null) {
                    list = records.getData();
                    if (list != null && list.size() > 0) {
                        Object[] arr = list.get(0);
                        hdao = (CHdHdaojcxx) arr[0];
                        hduan = (CHdHduanjcxx) arr[1];
                        if (hdao.getSfgg() != null && hdao.getSfgg().intValue() == 1) {
                            treeList.add(new VideoTree("b" + hdao.getId(), hdao.getHdmc(), "a1", 2));
                        } else {
                            treeList.add(new VideoTree("b" + hdao.getId(), hdao.getHdmc(), "a2", 2));
                        }
                        if (hduan != null) {
                            treeList.add(new VideoTree("c" + hduan.getId(), hduan.getHdqdmc() + "-" + hduan.getHdzdmc(), "b" + hdao.getId(), 3));
                        }
                    }
                }
                break;
            case 7:
                records = this.videoDao.queryVideoById(id);
                if (records != null) {
                    list = records.getData();
                    if (list != null && list.size() > 0) {
                        Object[] arr = list.get(0);
                        hdao = (CHdHdaojcxx) arr[0];
                        hduan = (CHdHduanjcxx) arr[1];
                        cc = (ChannelCamera) arr[2];
                        p = (Parament) arr[3];
                        if (hdao != null && hdao.getSfgg() != null && hdao.getSfgg().intValue() == 1) {
                            treeList.add(new VideoTree("b" + hdao.getId(), hdao.getHdmc(), "a1", 2));
                        } else {
                            treeList.add(new VideoTree("b" + hdao.getId(), hdao.getHdmc(), "a2", 2));
                        }
                        if (hduan != null) {
                            int hduanid = hduan.getId();
                            treeList.add(new VideoTree("c" + hduanid, hduan.getHdqdmc() + "-" + hduan.getHdzdmc(), "b" + hdao.getId(), 3));
                            if (cc != null) {
                                switch (cc.getCtype()) {
                                    case 4:
                                        treeList.add(new VideoTree("hd" + hduanid, "航段视频", "c" + hduanid, 4));
                                        if (p != null) {
                                            treeList.add(new VideoTree("hdc" + p.getCameraId(), p.getCameraName(), "hd" + hduanid, 7));
                                        }
                                        break;
                                    case 5:
                                        treeList.add(new VideoTree("mt" + hduanid, "码头视频", "c" + hduanid, 5));
                                        if (p != null) {
                                            treeList.add(new VideoTree("mtc" + p.getCameraId(), p.getCameraName(), "mt" + hduanid, 7));
                                        }
                                        break;
                                    case 6:
                                        treeList.add(new VideoTree("gcd" + hduanid, "观测点视频", "c" + hduanid, 6));
                                        if (p != null) {
                                            treeList.add(new VideoTree("gcdc" + p.getCameraId(), p.getCameraName(), "gcd" + hduanid, 7));
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
                break;
        }
        return BaseResult.newResultOK(treeList);
    }

    public BaseResult queryCameraById(Integer id) {
        CHdHdaojcxx hdao = new CHdHdaojcxx();
        CHdHduanjcxx hduan = new CHdHduanjcxx();
        Parament p = this.videoDao.queryParamentById(id);
        ChannelCamera c = this.videoDao.queryChannelCameraById(id);
        if (c!=null){
            List hd = this.videoDao.queryHduanById(c.getHdid()).getData();
            if (hd != null && hd.size() > 0) {
                Object[] objs = (Object[]) hd.get(0);
                hdao = (CHdHdaojcxx) objs[0];
                hduan = (CHdHduanjcxx) objs[1];
            }
        }
        BaseResult ok = new BaseResult();
        ok.addToMap("baseinfo", p);
        ok.addToMap("locainfo", c);
        ok.addToMap("hdaoinfo", hdao);
        ok.addToMap("hduaninfo", hduan);
        return ok;
    }

    public BaseResult queryCameras(String xzqh) {
        BaseRecords records = null;
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        CZdXzqhdm xzqhnode = this.videoDao.findXzqhByCode(xzqh);
        if ("330000".equals(xzqh) || "".equals(xzqh)) {
            records = this.videoDao.queryCameras(null);
        } else {
            int xzqhtype = xzqhnode.getType();
            if (xzqhtype == 2) {
                xzqhs = this.videoDao.findChildXzqh(xzqhnode.getId());
            }
            xzqhs.add(xzqhnode);
            records = this.videoDao.queryCameras(xzqhs);
        }
        BaseResult ok = new BaseResult();
        ok.setRecords(records);
        return ok;
    }

    public BaseResult queryHdaoXzqh(String xzqh) {
        BaseRecords records = null;
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        CZdXzqhdm xzqhnode = this.videoDao.findXzqhByCode(xzqh);
        if ("330000".equals(xzqh) || "".equals(xzqh)) {
            records = this.videoDao.queryHdaoXzqh(null);
        } else {
            int xzqhtype = xzqhnode.getType();
            if (xzqhtype == 2) {
                xzqhs = this.videoDao.findChildXzqh(xzqhnode.getId());
            }
            xzqhs.add(xzqhnode);
            records = this.videoDao.queryHdaoXzqh(xzqhs);
        }
        BaseResult ok = new BaseResult();
        ok.setRecords(records);
        return ok;
    }

    private BaseRecords queryHduanInXzqh(String xzqh) {
        BaseRecords records = new BaseRecords();
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        CZdXzqhdm xzqhnode = this.videoDao.findXzqhByCode(xzqh);
        if ("330000".equals(xzqh) || "".equals(xzqh)) {
            records = this.videoDao.queryHduanXzqh(null);
        } else {
            int xzqhtype = xzqhnode.getType();
            if (xzqhtype == 2) {
                xzqhs = this.videoDao.findChildXzqh(xzqhnode.getId());
            }
            xzqhs.add(xzqhnode);
            records = this.videoDao.queryHduanXzqh(xzqhs);
        }
        return records;
    }

    public BaseResult queryHduanXzqh(String xzqh) {
        BaseRecords records = this.queryHduanInXzqh(xzqh);
        BaseResult ok = new BaseResult();
        ok.setRecords(records);
        return ok;
    }

    public BaseResult queryHduanSshdid(Integer sshdid, String xzqh) {
        BaseRecords records = null;
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        CZdXzqhdm xzqhnode = this.videoDao.findXzqhByCode(xzqh);
        if ("330000".equals(xzqh) || "".equals(xzqh)) {
            records = this.videoDao.queryHduanSshdid(sshdid, null);
        } else {
            int xzqhtype = xzqhnode.getType();
            if (xzqhtype == 2) {
                xzqhs = this.videoDao.findChildXzqh(xzqhnode.getId());
            }
            xzqhs.add(xzqhnode);
            records = this.videoDao.queryHduanSshdid(sshdid, xzqhs);
        }
        BaseResult ok = new BaseResult();
        ok.setRecords(records);
        return ok;
    }

    public BaseResult queryHdCamera(Integer hdid, Integer ctype) {
        BaseRecords records = this.videoDao.queryHdCamera(hdid, ctype);
        BaseResult ok = new BaseResult();
        ok.setRecords(records);
        return ok;
    }


    public BaseResult addChannelCamera(ChannelCamera c) {
        this.videoDao.save(c);
        BaseResult ok = new BaseResult();
        return ok;
    }

    public BaseResult updateChannelCamera(ChannelCamera c) {
        int id =c.getId();
        if (id>0){
        ChannelCamera uptobj = this.videoDao.queryChannelCameraById(id);
        uptobj.setHdid(c.getHdid());
        uptobj.setCtype(c.getCtype());
        uptobj.setNickname(c.getNickname());
        uptobj.setLon(c.getLon());
        uptobj.setLat(c.getLat());
        this.videoDao.updateChannelCamera(uptobj);
        }
        BaseResult ok = new BaseResult();
        return ok;
    }

    public BaseResult queryHdaoCamera(String xzqh) {
        BaseRecords records = null;
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        CZdXzqhdm xzqhnode = this.videoDao.findXzqhByCode(xzqh);
        if ("330000".equals(xzqh) || "".equals(xzqh)) {
            records = this.videoDao.queryHdaoCamera(null);
        } else {
            int xzqhtype = xzqhnode.getType();
            if (xzqhtype == 2) {
                xzqhs = this.videoDao.findChildXzqh(xzqhnode.getId());
            }
            xzqhs.add(xzqhnode);
            records = this.videoDao.queryHdaoCamera(xzqhs);
        }
        BaseResult ok = new BaseResult();
        ok.setRecords(records);
        return ok;
    }

    public BaseResult queryHduanCamera(Integer sshdid, String xzqh) {
        BaseRecords records = null;
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        CZdXzqhdm xzqhnode = this.videoDao.findXzqhByCode(xzqh);
        if ("330000".equals(xzqh) || "".equals(xzqh)) {
            records = this.videoDao.queryHduanCamera(sshdid, null);
        } else {
            int xzqhtype = xzqhnode.getType();
            if (xzqhtype == 2) {
                xzqhs = this.videoDao.findChildXzqh(xzqhnode.getId());
            }
            xzqhs.add(xzqhnode);
            records = this.videoDao.queryHduanCamera(sshdid, xzqhs);
        }
        BaseResult ok = new BaseResult();
        ok.setRecords(records);
        return ok;
    }
}
