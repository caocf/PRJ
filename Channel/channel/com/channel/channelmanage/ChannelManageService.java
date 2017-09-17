package com.channel.channelmanage;

import com.channel.bean.Constants;
import com.channel.bean.ThlzFj;
import com.channel.hangdao.dao.HangDaoDao;
import com.channel.hangduan.dao.HangDuanDao;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.hd.CHdHdaojcxx;
import com.channel.model.hd.CHdHduanjcxx;
import com.channel.model.hz.*;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtUser;
import com.channel.model.yh.CYhFj;
import com.channel.model.yh.CYhZxgc;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.FileMd5;
import com.common.utils.FileUtils;
import com.common.utils.parser.JSONArrayParser;
import com.common.utils.parser.JSONBeanParser;
import com.common.utils.parser.JSONParser;
import com.common.utils.parser.ParseErrorException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.ElseIf;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
@Service("channelmanageservice")
public class ChannelManageService extends BaseService {
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;
    @Resource(name = "hangduandao")
    private HangDuanDao hangDuanDao;
    @Resource(name = "hangdaodao")
    private HangDaoDao hangDaoDao;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "channelmanagedao")
    private ChannelManageDao channelManageDao;
    @Resource(name = "sjzxdao")
    private SjzxDao sjzxDao;
    @Resource
    private LogService logService;

    public BaseResult addChannelManage(CHzAdminlicense adminlicense) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        adminlicense.setCreater(user.getId());
        adminlicense.setCreatetime(new Date());
        adminlicense.setUpdatetime(new Date());
        this.channelManageDao.addChannelManage(adminlicense);
        logService.addLog(ModuleName.HZGL_XZXK, OpName.ADD, "");
        BaseResult result = new BaseResultOK();
        return result;
    }

    public BaseResult delChannelManage(List<Integer> ids) {
        if (ids != null && ids.size() > 0) {
            for (Integer id : ids) {
                CHzAdminlicense adminlicense = this.channelManageDao.queryEntityById(id);
                if (adminlicense != null) {
                    this.channelManageDao.delChannelManage(adminlicense);
                }
            }
        }
        logService.addLog(ModuleName.HZGL_XZXK, OpName.DEL, "");
        BaseResult result = new BaseResultOK();
        return result;
    }

    public BaseResult updateChannelManage(Integer id, CHzAdminlicense adminlicense) {
        CHzAdminlicense updateadminlicense = this.channelManageDao.queryEntityById(id);
        updateadminlicense.setXmmc(adminlicense.getXmmc());
        updateadminlicense.setXmwz(adminlicense.getXmwz());
        int xmlx = adminlicense.getXmlx();
        updateadminlicense.setXmlx(xmlx);
        switch (xmlx) {
            case Constants.XZXK_QL:
                updateadminlicense.setQljgxs(adminlicense.getQljgxs());
                updateadminlicense.setQlthjk(adminlicense.getQlthjk());
                updateadminlicense.setQlthjg(adminlicense.getQlthjg());
                updateadminlicense.setQlyhdjj(adminlicense.getQlyhdjj());
                break;
            case Constants.XZXK_LX:
                updateadminlicense.setLxlb(adminlicense.getLxlb());
                updateadminlicense.setLxkj(adminlicense.getLxkj());
                updateadminlicense.setLxthjg(adminlicense.getLxthjg());
                updateadminlicense.setLxtjlajl(adminlicense.getLxtjlajl());
                break;
            case Constants.XZXK_GX:
                updateadminlicense.setGxlb(adminlicense.getGxlb());
                break;
            case Constants.XZXK_SD:
                updateadminlicense.setSdgxdbg(adminlicense.getSdgxdbg());
                updateadminlicense.setSdmssd(adminlicense.getSdmssd());
                updateadminlicense.setSdrtklajl(adminlicense.getSdrtklajl());
                break;
            case Constants.XZXK_QPSK:
                updateadminlicense.setQpskscaxjl(adminlicense.getQpskscaxjl());
                updateadminlicense.setQpskzgdbg(adminlicense.getQpskzgdbg());
                updateadminlicense.setQpskhxls(adminlicense.getQpskhxls());
                updateadminlicense.setQpskhlls(adminlicense.getQpskhlls());
                break;
            case Constants.XZXK_ZB:
                updateadminlicense.setZbthdj(adminlicense.getZbthdj());
                updateadminlicense.setZbzscd(adminlicense.getZbzscd());
                updateadminlicense.setZbzmtsjg(adminlicense.getZbzmtsjg());
                break;
            default:
                break;
        }
        updateadminlicense.setGcxt(adminlicense.getGcxt());
        updateadminlicense.setJzwxz(adminlicense.getJzwxz());
        updateadminlicense.setSqdw(adminlicense.getSqdw());
        updateadminlicense.setSqdwjbr(adminlicense.getSqdwjbr());
        updateadminlicense.setDz(adminlicense.getDz());
        updateadminlicense.setLxdh(adminlicense.getLxdh());
        updateadminlicense.setXmsjdw(adminlicense.getXmsjdw());
        updateadminlicense.setXksldw(adminlicense.getXksldw());
        updateadminlicense.setXkbh(adminlicense.getXkbh());
        updateadminlicense.setXkrq(adminlicense.getXkrq());
        updateadminlicense.setUpdatetime(new Date());
        logService.addLog(ModuleName.HZGL_XZXK, OpName.UPDATE, updateadminlicense.getXmmc());
        BaseResult result = new BaseResultOK();
        return result;
    }

    public BaseResult queryChannelManage(Integer xmlx, Integer jzwxz, Date starttime, Date endtime, Integer contenttype, String content, String sEcho, int page, int rows) {
        String strstarttime = "";
        String strendtime = "";
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime);
        }
        if (endtime != null) {
            strendtime = DateTimeUtil.getTimeFmt(endtime);
        }
        BaseQueryRecords records = this.channelManageDao.queryChannelManage(xmlx, jzwxz, strstarttime, strendtime, contenttype, content, page, rows);
        BaseResult result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    public BaseResult queryChannelManageById(Integer id) {
        CHzAdminlicense adminlicense = this.channelManageDao.queryEntityById(id);
        BaseResult result = new BaseResultOK(adminlicense);
        return result;
    }

    public BaseResult queryAdminpenalty(Integer dept, Integer contenttype, String content, String sEcho, int page, int rows) {
        List<CHzAdminpenalty> ret = new ArrayList<>();
        BaseQueryRecords records = this.sjzxDao.queryAdminpenalty(dept, contenttype, content, page, rows);
        if (records != null) {
            List<Object[]> list = records.getData();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            if (list != null && list.size() > 0) {
                for (Object[] objs : list) {
                    CHzAdminpenalty obj = new CHzAdminpenalty();
                    obj.setId(Integer.parseInt(String.valueOf(objs[0])));
                    obj.setSlh(String.valueOf(objs[1]));
                    obj.setZwcm(String.valueOf(objs[3]));
                    obj.setAy(String.valueOf(objs[4]));
                    long sj = Long.parseLong(String.valueOf(objs[2]));
                    Date sjdate = new Date(sj);
                    obj.setSlsj(sdf.format(sjdate));
                    ret.add(obj);
                }
            }
        }
        records.setData(ret);
        BaseResult result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    public BaseResult queryAdminpenaltyById(Integer id) {
        CHzAdminpenalty ret = new CHzAdminpenalty();
        BaseQueryRecords baseQueryRecords = this.sjzxDao.queryAdminpenaltyById(id);

        if (baseQueryRecords != null) {
            List<Object[]> list = baseQueryRecords.getData();
            if (list != null && list.size() > 0) {
                Object[] objs = list.get(0);
                ret.setId(getIntegerNull(objs[0]));
                ret.setSlh(getStringNull(objs[1]));
                ret.setSlsj(getDateNull(objs[2]));
                ret.setZwcm(getStringNull(objs[3]));
                ret.setAy(getStringNull(objs[4]));
                ret.setFasj(getDateNull(objs[5]));
                ret.setFadd(getStringNull(objs[6]));
                ret.setAjlb(getStringNull(objs[7]));
                ret.setZyss(getStringNull(objs[8]));
                ret.setZfscbh(getStringNull(objs[9]));
                ret.setWfnr(getStringNull(objs[10]));
                ret.setWftk(getStringNull(objs[11]));
                ret.setCftk(getStringNull(objs[12]));
                ret.setCfyj(getStringNull(objs[13]));
                ret.setCflb(getStringNull(objs[14]));
                ret.setSfcf(getIntegerNull(objs[15]));
                ret.setSfja(getIntegerNull(objs[16]));
                ret.setJarq(getDateNull(objs[17]));
            }
        }
        BaseResult result = new BaseResultOK(ret);
        return result;
    }

    private String getDateNull(Object obj) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String ret = "";
        if (obj != null) {
            String s = String.valueOf(obj);
            if (s != null && !"".equals(s)) {
                long l = Long.parseLong(s);
                if (l > 0) {
                    ret = sdf.format(new Date(l));
                }
            }
        }
        return ret;
    }

    private String getStringNull(Object obj) {
        String ret = "";
        if (obj != null) {
            ret = String.valueOf(obj);
        }
        return ret;
    }

    private int getIntegerNull(Object obj) {
        int ret = 0;
        if (obj != null) {
            String s = String.valueOf(obj);
            if (s != null && !"".equals(s)) {
                ret = Integer.parseInt(s);
            }

        }
        return ret;
    }

    public BaseResult addArgument(CHzArgument argument, List<File> filelist, List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        argument.setCreater(user.getId());
        argument.setCreatetime(new Date());
        argument.setUpdatetime(new Date());
        this.channelManageDao.addArgument(argument);
        logService.addLog(ModuleName.HZGL_THLZ, OpName.ADD, "");
        if (filelist != null && filelist.size() > 0) {
            this.addCHzFj(argument, filelist, filelistFileName, user.getId());
        }
        BaseResult result = new BaseResultOK();
        return result;
    }

    public void addCHzFj(CHzArgument argument, List<File> filelist,
                         List<String> filelistFileName, int loginid) throws Exception {
        if (filelist != null && filelist.size() > 0) {
            String root = this.getRealPath("upload");// upload存放路径
            File infile = null;
            FileUtils.mkdir(root);
            int pid = argument.getId();
            for (int i = 0; i < filelist.size(); i++) {
                // io流操作
                infile = filelist.get(i);
                String infileMd5 = FileMd5.getMd5ByFile(infile);
                String filename = FileUtils.writeToFile(infile, infileMd5, root
                        + "\\" + filelistFileName.get(i));
                CHzThlzfj md5fj = this.channelManageDao.queryFjByMd5(pid,
                        infileMd5);
                // 数据库资源插入操作
                if (md5fj == null) {
                    CHzThlzfj fj = new CHzThlzfj();
                    fj.setPid(pid);
                    fj.setResname(filename);
                    fj.setRespath("\\" + filename);
                    fj.setRessize(String.valueOf(infile.length() * 1.0 / 1000));
                    fj.setFilemd5(infileMd5);
                    fj.setCreater(loginid);
                    fj.setCreatetime(new Date());
                    fj.setUpdatetime(new Date());
                    this.channelManageDao.addCYhFj(fj);
                }
            }
        }
    }

    public BaseResult delArgument(List<Integer> ids) {
        for (Integer thlzid : ids) {
            CHzArgument argument = this.channelManageDao.queryArgumentById(thlzid);
            this.channelManageDao.delCHzFjByPid(thlzid);
            if (argument != null) {
                this.channelManageDao.delArgument(argument);
            }
        }
        logService.addLog(ModuleName.HZGL_THLZ, OpName.DEL, "");
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    public BaseResult updateArgument(CHzArgument updateargument, List<Integer> delfileids, List<File> filelist, List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int id = updateargument.getId();
        CHzArgument argument = this.channelManageDao.queryArgumentById(id);
        if (argument == null) {
            return new BaseResultFailed(Constants.RESULT_THLZ_NOTEXIST, "通航论证不存在");
        } else {
            if (delfileids != null && delfileids.size() > 0) {
                for (Integer delfileid : delfileids) {
                    if (delfileid != null) {
                        this.channelManageDao.delCHzFjById(delfileid);
                    }
                }
            }
            argument.setName(updateargument.getName());
            argument.setPname(updateargument.getPname());
            argument.setAtime(updateargument.getAtime());
            argument.setHdao(updateargument.getHdao());
            argument.setTitle(updateargument.getTitle());
            argument.setUpdatetime(new Date());
            if (filelist != null && filelist.size() > 0) {
                this.addCHzFj(argument, filelist, filelistFileName, user.getId());
            }
            logService.addLog(ModuleName.HZGL_THLZ, OpName.UPDATE, "");
            BaseResultOK result = new BaseResultOK();
            return result;
        }
    }

    public BaseResult queryArgument(Date starttime, Date endtime, String content, String sEcho, int page, int rows) {
        int xzqh = 0;
        CXtDpt dpt = (CXtDpt) getSession().getAttribute("dpt");
        if (dpt != null) {
            xzqh = dpt.getXzqh();
        }
        BaseQueryRecords records = this.channelManageDao.queryArgument(xzqh,starttime, endtime, content, page, rows);
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    public BaseResult queryArgumentById(Integer id) {
        String username = "";
        CHdHdaojcxx hdao = new CHdHdaojcxx();
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        CHzArgument argument = this.channelManageDao.queryArgumentById(id);
        if (argument == null) {
            return new BaseResultFailed(Constants.RESULT_THLZ_NOTEXIST, "通航论证不存在");
        }
        int pid = argument.getId();
        int hdaoid = argument.getHdao();
        int createrid = argument.getCreater();
        if (hdaoid > 0) {
            hdao = this.hangDaoDao.queryHangDaoById(argument.getHdao());
        }
        if (createrid > 0) {
            CXtUser creater = this.userDao.queryUserById(createrid);
            if (creater != null) {
                username = creater.getName();
            }
        }
        List<CHzThlzfj> fjlist = this.channelManageDao.queryFjByPid(pid).getData();
        List<ThlzFj> lists = new ArrayList<ThlzFj>();
        if (fjlist != null && fjlist.size() > 0) {
            for (CHzThlzfj temp : fjlist) {
                ThlzFj fj = new ThlzFj();
                fj.setFid(temp.getId());
                fj.setFname(temp.getResname());
                fj.setFsize(temp.getRessize());
                fj.setFtype(FileUtils.judgeFileType(FileUtils.getFileExtension(temp.getResname())));
                lists.add(fj);
            }
        }
        BaseResultOK result = new BaseResultOK(argument);
        result.addToMap("fjlist", lists);
        result.addToMap("username", username);
        result.addToMap("hdao", hdao.getHdmc());
        return result;
    }

    public BaseResult queryArgumentByIdJson(Integer id) {
        CHzArgument argument = this.channelManageDao.queryArgumentById(id);
        if (argument == null) {
            return new BaseResultFailed(Constants.RESULT_THLZ_NOTEXIST, "通航论证不存在");
        }
        int pid = argument.getId();
        BaseQueryRecords<CHzThlzfj> fjlist = this.channelManageDao.queryFjByPid(pid);
        BaseResultOK result = new BaseResultOK(argument);
        result.addToMap("fjlist", fjlist.getData());
        return result;
    }

    public BaseResult downloadCHzFj(Integer id) {
        CHzThlzfj fj = this.channelManageDao.queryCHzFjById(id);
        if (fj == null) {
            return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                    "附件不存在");
        } else {
            String filename = fj.getRespath().replace("\\", "/");
            try {
                BaseResultOK baseResultOK = new BaseResultOK();
                baseResultOK.addToMap("filename", filename);
                return baseResultOK;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                        "附件不存在");
            }

        }
    }

    public BaseResult queryReparation(Date starttime, Date endtime, String content, String sEcho, int page, int rows) {
        int xzqh = 0;
        CXtDpt dpt = (CXtDpt) getSession().getAttribute("dpt");
        if (dpt != null) {
            xzqh = dpt.getXzqh();
        }
        BaseQueryRecords records = this.channelManageDao.queryReparation(xzqh,starttime, endtime, content, page, rows);
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    public BaseResult queryReparationById(Integer id) {
        CHzCzpc czpc = this.channelManageDao.queryReparationById(id);
        String strdpt = "";
        String strhdao = "";
        String strhduan = "";
        String strxz = "";
        List<CHzCzpclx> lxs = new ArrayList<>();
        if (czpc != null) {
            CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoById(czpc.getHdaoid());
            if (hdao != null) {
                strhdao = hdao.getHdmc();
            }
            CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanById(czpc.getHduanid());
            if (hduan != null) {
                strhduan = hduan.getHdqdmc() + "---" + hduan.getHdzdmc();
            }
            strxz = czpc.getXz() == 1 ? "占用" : "损坏";
            lxs = this.channelManageDao.queryCzpclxByPid(czpc.getId()).getData();
        }
        BaseResultOK result = new BaseResultOK(czpc);
        result.addToMap("hdao", strhdao);
        result.addToMap("hduan", strhduan);
        result.addToMap("xz", strxz);
        result.addToMap("lx", lxs);
        return result;
    }

    public BaseResult delReparation(List<Integer> ids) {
        for (Integer id : ids) {
            CHzCzpc czpc = this.channelManageDao.queryReparationById(id);
            this.channelManageDao.delCHzCzpclxByPid(id);
            if (czpc != null) {
                this.channelManageDao.delReparation(czpc);
            }
        }
        logService.addLog(ModuleName.HZGL_CZPC, OpName.DEL, "");
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    public BaseResult addReparation(CHzCzpc czpc, String jsondata) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        List<CHzCzpclx> czpclxs = new ArrayList<>();
        JSONParser parser = new JSONArrayParser<CHzCzpclx>().addArrayItemParser(new JSONBeanParser<CHzCzpclx>(CHzCzpclx.class));
        try {
            czpclxs = (List<CHzCzpclx>) parser.parse(jsondata);

            int userid = user.getId();
            Date now = new Date();
            czpc.setCreater(userid);
            czpc.setCreatetime(now);
            czpc.setUpdatetime(now);
            this.channelManageDao.save(czpc);
            if (czpclxs != null && czpclxs.size() > 0) {
                int pid = czpc.getId();
                for (CHzCzpclx czpclx : czpclxs) {
                    czpclx.setPid(pid);
                    czpclx.setCreater(userid);
                    czpclx.setCreatetime(now);
                    czpclx.setUpdatetime(now);
                    this.channelManageDao.save(czpclx);
                }
            }
            logService.addLog(ModuleName.HZGL_CZPC, OpName.ADD, czpc.getName());
        } catch (ParseErrorException e) {
            e.printStackTrace();
        }
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    public BaseResult updateReparation(Integer id, CHzCzpc tempczpc, String jsondata) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int userid = user.getId();
        CHzCzpc czpc = this.channelManageDao.queryReparationById(id);
        Date now = new Date();
        czpc.setDw(tempczpc.getDw());
        czpc.setName(tempczpc.getName());
        czpc.setHdaoid(tempczpc.getHdaoid());
        czpc.setHduanid(tempczpc.getHduanid());
        czpc.setXz(tempczpc.getXz());
        czpc.setPbcje(tempczpc.getPbcje());
        czpc.setSldw(tempczpc.getSldw());
        czpc.setZcpgdw(tempczpc.getZcpgdw());
        czpc.setSlrq(tempczpc.getSlrq());
        czpc.setUpdatetime(now);
        this.channelManageDao.update(czpc);
        int pid = czpc.getId();
        this.channelManageDao.delCHzCzpclxByPid(pid);
        List<CHzCzpclx> czpclxs = new ArrayList<>();
        JSONParser parser = new JSONArrayParser<CHzCzpclx>().addArrayItemParser(new JSONBeanParser<CHzCzpclx>(CHzCzpclx.class));
        try {
            czpclxs = (List<CHzCzpclx>) parser.parse(jsondata);
            if (czpclxs != null && czpclxs.size() > 0) {
                for (CHzCzpclx czpclx : czpclxs) {
                    czpclx.setPid(pid);
                    czpclx.setCreater(userid);
                    czpclx.setCreatetime(now);
                    czpclx.setUpdatetime(now);
                    this.channelManageDao.save(czpclx);
                }
            }
            logService.addLog(ModuleName.HZGL_CZPC, OpName.UPDATE, czpc.getName());
        } catch (ParseErrorException e) {
            e.printStackTrace();
        }
        BaseResultOK result = new BaseResultOK();
        return result;
    }


}
