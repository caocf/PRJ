package com.channel.searchpro.service.impl;

import com.channel.bean.Constants;
import com.channel.bean.FieldValue;
import com.channel.bean.PropDescFields;
import com.channel.dic.dao.DicDao;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.model.hd.CZdAppattribute;
import com.channel.model.user.CXtUser;
import com.channel.model.user.CZdXzqhdm;
import com.channel.modelutil.anotation.UiInput;
import com.channel.searchpro.dao.SearchProDao;
import com.channel.searchpro.service.SearchProService;
import com.channel.user.dao.UserDao;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
@Service("searchproservice")
public class SearchProServiceImpl extends BaseService implements SearchProService {
    @Resource(name = "searchprodao")
    private SearchProDao searchProDao;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dicdao")
    private DicDao dicDao;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;

    @Override
    public BaseResult loadField(int fswlx) throws ClassNotFoundException {
        String content = jgFswlx(fswlx);
        if (content == null || "".equals(content)) {
            BaseResultFailed baseResultFailed = new BaseResultFailed();
            return baseResultFailed;
        } else {
            Class c = Class.forName("com.channel.model.hd." + content);
            //获取所有的属性?
            Field[] fs = c.getDeclaredFields();
            //定义可变长的字符串，用来存储属性
            //通过追加的方法，将每个属性拼接到此字符串中
            //最外边的public定义
            List<FieldValue> list = new ArrayList<>();
            for (Field field : fs) {
                boolean flag = false;
                List dics = new ArrayList();
                FieldValue e = new FieldValue();
                e.setTname(field.getType().getSimpleName());
                e.setPname(field.getName());
                String vname = new PropDescFields(c).getPropDesc(field.getName().toString());
                e.setVname(vname);
                if (vname.indexOf("创建人") != -1) {
                    flag = true;
                    List<CXtUser> users = (List<CXtUser>) this.userDao.find(new CXtUser()).getData();
                    for (CXtUser user : users) {
                        dics.add(user.getId() + "," + user.getName());
                    }
                } else if (vname.indexOf("行政区划") != -1) {
                    flag = true;
                    List<CZdXzqhdm> xzqhs = (List<CZdXzqhdm>) this.xzqhdmdao.find(new CZdXzqhdm()).getData();
                    for (CZdXzqhdm xzqh : xzqhs) {
                        dics.add(xzqh.getId() + "," + xzqh.getName());
                    }
                } else {
                    UiInput uia = field.getAnnotation(UiInput.class);//处理字典
                    if (uia != null && !"".equals(uia.selectdictname())) {
                        flag = true;
                        List<CZdAppattribute> temp = this.dicDao.queryDicAttr(uia.selectdictname()).getData();
                        for (CZdAppattribute dic : temp) {
                            dics.add(dic.getId() + "," + dic.getAttrdesc());
                        }
                    }
                }
                e.setFlag(flag);
                e.setDics(dics);
                list.add(e);
            }
            BaseResultOK baseResultOK = new BaseResultOK(list);
            return baseResultOK;
        }
    }

    @Override
    public BaseResult searchPro(int fswlx, List<String> contents, int page, int rows, String secho) {
        String tname = "";
        tname = jgFswlx(fswlx);
        if (tname == null || "".equals(tname)) {
            BaseResultFailed baseResultFailed = new BaseResultFailed();
            return baseResultFailed;
        } else {
            String cdt = "";
            if (contents != null && contents.size() > 0) {
                for (String content : contents) {
                    cdt += " and " + content;
                }
            }
            BaseQueryRecords records = this.searchProDao.searchPro(tname, cdt, page, rows);
            BaseResultOK baseResultOK = new BaseResultOK(records);
            baseResultOK.addToMap("sEcho", secho);
            return baseResultOK;
        }
    }

    private String jgFswlx(int fswlx) {
        String tablename = "";
        switch (fswlx) {
            case 1:
                tablename = "CHdHdaojcxx";
                break;
            case 2:
                tablename = "CHdHduanjcxx";
                break;
            case Constants.APP_NAVIGATIONMARK:
                tablename = "CHdHb";
                break;
            case Constants.APP_BRIDGE:
                tablename = "CHdQl";
                break;
            case Constants.APP_AQUEDUCT:
                tablename = "CHdDc";
                break;
            case Constants.APP_CABLE:
                tablename = "CHdLx";
                break;
            case Constants.APP_PIPELINE:
                tablename = "CHdGd";
                break;
            case Constants.APP_TUNNEL:
                tablename = "CHdSd";
                break;
            case Constants.APP_KYDOCK:
                tablename = "CHdKymt";
                break;
            case Constants.APP_HYDOCK:
                tablename = "CHdHymt";
                break;
            case Constants.APP_GWDOCK:
                tablename = "CHdGwmt";
                break;
            case Constants.APP_SHIPYARD:
                tablename = "CHdCc";
                break;
            case Constants.APP_TAKEOUTFALL:
                tablename = "CHdQpsk";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                tablename = "CHdSwz";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                tablename = "CHdGlz";
                break;
            case Constants.APP_SERVICEAREA:
                tablename = "CHdFwq";
                break;
            case Constants.APP_MOORINGAREA:
                tablename = "CHdMbq";
                break;
            case Constants.APP_HUB:
                tablename = "CHdSn";
                break;
            case Constants.APP_DAM:
                tablename = "CHdB";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                tablename = "CHdZzha";
                break;
            case Constants.APP_LASEROBSERVATION:
                tablename = "CHdJgllgcd";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                tablename = "CHdSpgcd";
                break;
            case Constants.APP_MANUALOBSERVATION:
                tablename = "CHdRggcd";
                break;
            case Constants.APP_BOLLARD:
                tablename = "CHdXlz";
                break;
            default:
                break;
        }
        return tablename;
    }
}
