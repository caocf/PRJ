package com.channel.modelutil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.channel.appurtenance.service.AppurtenanceService;
import com.channel.appurtenance.service.impl.AppurtenanceServiceImpl;
import com.channel.bean.Constants;
import com.channel.hangdao.dao.HangDaoDao;
import com.channel.hangdao.service.HangDaoService;
import com.channel.hangduan.service.HangDuanService;
import com.channel.model.hd.CHdHdaojcxx;
import org.springframework.stereotype.Service;

import com.channel.bean.PropDesc;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CZdXzqhdm;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;
import com.channel.modelutil.anotation.ValidatorDefaultMsg;
import com.channel.modelutil.model.ModelInfo;
import com.channel.modelutil.model.ModelPropertyInfo;
import com.channel.user.dao.impl.DptDaoImpl;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;

@Service("ModelService")
public class ModelService extends BaseService {
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;

    @Resource(name = "hangdaodao")
    private HangDaoDao hangDaoDao;

    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;

    @Resource(name = "hangduanservice")
    private HangDuanService hangDuanService;

    @Resource(name = "appurtenanceservice")
    private AppurtenanceService appurtenanceService;

    public ModelInfo queryModelInfo(String modelname,
                                    Map<String, Object> inputdata, boolean editmode) {
        try {
            Class<?> cls = Class.forName("com.channel.model.hd." + modelname);

            ModelInfo info = new ModelInfo();

            info.setModelName(cls.getSimpleName());
            List<ModelPropertyInfo> propertyInfos = new ArrayList<ModelPropertyInfo>();

            // 获得所有的字段
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                UiInput uiInput = field.getAnnotation(UiInput.class);
                // 设置属性注解描述
                if (uiInput != null) {
                    ModelPropertyInfo propertyInfo = new ModelPropertyInfo();

                    propertyInfo.setName(field.getName());
                    propertyInfo.setType(field.getType().getSimpleName());
                    propertyInfo.setOrder(uiInput.order());
                    propertyInfo.setSuborder(uiInput.suborder());
                    propertyInfo.setGroup(uiInput.group());
                    propertyInfo.setReadonly(uiInput.readonly());

                    propertyInfo.setDefaultvalpre("");

                    if (uiInput.inputtype() != null
                            && !uiInput.inputtype().equals(""))
                        propertyInfo.setInputtype(uiInput.inputtype());

                    propertyInfo.setMust(uiInput.must());
                    propertyInfo.setAutoajax(uiInput.autoajax());
                    propertyInfo.setOneline(uiInput.oneline());

                    /********************* 加载默认值 ************/
                    // 如果指定了defaultvalfromweb，则从该选项中加载数据
                    if (uiInput.defaultvalfromweb() != null
                            && !uiInput.defaultvalfromweb().equals("")) {
                        Object valObject = inputdata.get(uiInput
                                .defaultvalfromweb());
                        if (valObject != null) {
                            propertyInfo.setDefaultval("" + valObject);
                        } else {
                            propertyInfo.setDefaultval("");
                        }
                    } else { // 如果没有指定defaultvalfromweb，则尝试从defaultval中加载数据
                        if (uiInput.defaultval() != null
                                && !uiInput.defaultval().equals("")) {
                            propertyInfo.setDefaultval(uiInput.defaultval());
                        } else {
                            propertyInfo.setDefaultval("");
                        }
                    }

                    //解决航段编号生成问题
                    if (modelname.toLowerCase().equals("CHdHduanjcxx".toLowerCase())) {
                        if (field.getName().toLowerCase().equals("hdbh".toLowerCase())) {
                            if (inputdata != null) {
                                if (editmode) {
                                    Object sshdid = inputdata.get("sshdid");
                                    if (sshdid != null) {
                                        CHdHdaojcxx hdaojcxx = this.hangDaoDao.queryHangDaoByHdid((int) sshdid);
                                        if (hdaojcxx != null)
                                            propertyInfo.setDefaultvalpre(hdaojcxx.getHdbh());
                                    }
                                } else {
                                    Object sshdbh = inputdata.get("sshdbh");
                                    Object sshdid = inputdata.get("sshdid");
                                    if (sshdid != null && sshdbh != null) {
                                        CHdHdaojcxx hdaojcxx = this.hangDaoDao.queryHangDaoByHdid((int) sshdid);
                                        if (hdaojcxx != null) {
                                            propertyInfo.setDefaultvalpre((String) sshdbh);
                                            propertyInfo.setDefaultval((String) hangDuanService.queryMaxHdbh(hdaojcxx.getId()).getObj());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (field.getName().toLowerCase().equals("bh".toLowerCase())) {
                        String bh = AppurtenanceServiceImpl.getAppBHPre(AppurtenanceServiceImpl.getAppFswSecondClass(modelname));
                        if (bh != null && !bh.equals("")) {
                            propertyInfo.setDefaultvalpre(bh);
                            if (!editmode) {
                                int fswlx = AppurtenanceServiceImpl.getAppFswSecondClass(modelname);
                                propertyInfo.setDefaultval((String) appurtenanceService.queryMaxAppbh(fswlx).getObj());
                            }
                        }
                    }

                    // 如果默认值仍为空，则尝试从inputdata中根据模型字段来获得尝试获取数据
                    if (propertyInfo.getDefaultval() == null
                            || propertyInfo.getDefaultval().equals("")) {
                        Object valObject = inputdata.get(field.getName());
                        if (valObject != null) {
                            propertyInfo.setDefaultval("" + valObject);
                        } else {
                            propertyInfo.setDefaultval("");
                        }
                    }

                    /**
                     * 修复行政区划和部门在编辑时无法被初始化bug
                     *
                     * 解决方法： 当加载值时，如果没有名字，则后台自动添加
                     */
                    if (uiInput.inputtype() != null) {
                        String defaultval = propertyInfo.getDefaultval();
                        if (uiInput.inputtype().equals("selectdpt")) {
                            if (defaultval != null && !defaultval.equals("")) {
                                try {
                                    CXtDpt dpt = this.dptdao
                                            .queryDptById(Integer
                                                    .parseInt(defaultval));
                                    if (dpt != null) {
                                        propertyInfo.setDefaultval(""
                                                + Integer.parseInt(defaultval)
                                                + "," + dpt.getName());
                                    } else {
                                        propertyInfo.setDefaultval("");
                                    }
                                } catch (Exception e) {
                                    propertyInfo.setDefaultval("");
                                }
                            }
                        } else if (uiInput.inputtype().equals("selectxzqh")) {
                            if (defaultval != null && !defaultval.equals("")) {
                                try {
                                    CZdXzqhdm xzqh = this.xzqhdmdao
                                            .queryXzqhdmById(Integer
                                                    .parseInt(defaultval));
                                    if (xzqh != null) {
                                        propertyInfo.setDefaultval(""
                                                + Integer.parseInt(defaultval)
                                                + "," + xzqh.getName());
                                    } else {
                                        propertyInfo.setDefaultval("");
                                    }
                                } catch (Exception e) {
                                    propertyInfo.setDefaultval("");
                                }
                            }
                        } else if (uiInput.inputtype().equals("selectdate")) {
                            try {
                                defaultval = defaultval.replaceAll("T", " ");
                                propertyInfo
                                        .setDefaultval(DateTimeUtil.getTimeFmt(
                                                DateTimeUtil
                                                        .getDateByStringFmt(defaultval),
                                                "yyyy-MM-dd"));

                                // 如果为1970年，则前台不显示
                                if (propertyInfo.getDefaultval().equals(
                                        "1970-1-1")
                                        || propertyInfo.getDefaultval().equals(
                                        "1970-01-01")) {
                                    propertyInfo.setDefaultval("");
                                }
                            } catch (Exception e) {
                                propertyInfo.setDefaultval("");
                            }
                        } else if (uiInput.inputtype().equals("selectmonth")) {
                            try {
                                defaultval = defaultval.replaceAll("T", " ");
                                propertyInfo
                                        .setDefaultval(DateTimeUtil.getTimeFmt(
                                                DateTimeUtil
                                                        .getDateByStringFmt(defaultval),
                                                "yyyy-MM"));

                                // 如果为1970年，则前台不显示
                                if (propertyInfo.getDefaultval().equals(
                                        "1970-1")
                                        || propertyInfo.getDefaultval().equals(
                                        "1970-01")) {
                                    propertyInfo.setDefaultval("");
                                }
                            } catch (Exception e) {
                                propertyInfo.setDefaultval("");
                            }
                        }
                    }

                    if (uiInput.groupname() != null
                            && !uiInput.groupname().equals(""))
                        propertyInfo.setGroupname(uiInput.groupname());

                    // 设置描述
                    if (uiInput.desc() != null && !uiInput.desc().equals(""))
                        propertyInfo.setDesc(uiInput.desc());
                    else {
                        PropDesc propDesc = field.getAnnotation(PropDesc.class);
                        if (propDesc != null && !propDesc.value().equals("")) {
                            propertyInfo.setDesc(propDesc.value());
                        }
                    }

                    // 按照约定，设置默认值
                    if (!uiInput.autoajax_defaultval().equals("-999999")) {
                        propertyInfo.setAutoajax_defaultval(uiInput
                                .autoajax_defaultval());
                    } else {
                        if (field.getType().getSimpleName().toLowerCase()
                                .contains("int")
                                || field.getType().getSimpleName()
                                .toLowerCase().contains("long")) {
                            propertyInfo.setAutoajax_defaultval("0");
                        } else if (field.getType().getSimpleName()
                                .toLowerCase().contains("double")
                                || field.getType().getSimpleName()
                                .toLowerCase().contains("float")) {
                            propertyInfo.setAutoajax_defaultval("0");
                        } else if (field.getType().getSimpleName()
                                .toLowerCase().contains("string")) {
                            propertyInfo.setAutoajax_defaultval("");
                        } else if (field.getType().getSimpleName()
                                .toLowerCase().contains("date")) {
                            propertyInfo.setAutoajax_defaultval("1970-01-01");
                        } else {
                            propertyInfo.setAutoajax_defaultval("");
                        }

                    }

                    UiInputValidator uiInputValidator = field
                            .getAnnotation(UiInputValidator.class);
                    if (uiInputValidator != null) {
                        // 根据注解信息生成对应的前台支持的json格式
                        Validator[] validators = uiInputValidator.value();
                        List<String> validatorlist = new ArrayList<String>();
                        for (Validator validator : validators) {
                            String validatorstr = "{\"";
                            validatorstr += validator.filter() + "\":{";

                            // 如果设置了notempty验证器，则自动为必须字段
                            if (validator.filter().equals("notempty")) {
                                propertyInfo.setMust(true);
                            }

                            if (validator.attr() != null
                                    && !validator.attr().equals("")) {
                                validatorstr += "\"attr\":\""
                                        + validator.attr() + "\",";
                            }
                            validatorstr += "\"min\":" + validator.min() + ",";
                            validatorstr += "\"max\":" + validator.max() + ",";

                            if (validator.fn() != null
                                    && !validator.fn().equals("")) {
                                validatorstr += "\"fn\":\"" + validator.fn()
                                        + "\",";
                            }

                            if (validator.target() != null
                                    && validator.target().id() != null
                                    && !validator.target().id().equals("")) {
                                String targetattr = validator.target().attr();
                                if (targetattr != null
                                        && !targetattr.equals("")) {

                                    validatorstr += "\"target\":{"
                                            + "\"id\":\""
                                            + validator.target().id() + "\","
                                            + "\"attr\":\"" + targetattr
                                            + "\"},";
                                } else {
                                    validatorstr += "\"target\":{"
                                            + "\"id\":\""
                                            + validator.target().id() + "\"},";
                                }

                            }
                            if (validator.exp() != null
                                    && !validator.exp().equals("")) {
                                validatorstr += "\"exp\":\"" + validator.exp()
                                        + "\",";
                            }
                            String msg = validator.msg();
                            if (msg == null || msg.equals("")) {
                                msg = ValidatorDefaultMsg.getMsgMap(uiInput,
                                        validator);
                            }
                            validatorstr += "\"msg\":\"" + msg + "\"";

                            validatorstr += "}}";
                            validatorlist.add(validatorstr);
                        }
                        propertyInfo.setValidatorjsons(validatorlist);
                    }

                    if (uiInput.autoajax_name() != null
                            && !uiInput.autoajax_name().equals(""))
                        propertyInfo.setAutoajax_name(uiInput.autoajax_name());

                    if (uiInput.autoajax_attr() != null
                            && !uiInput.autoajax_attr().equals(""))
                        propertyInfo.setAutoajax_attr(uiInput.autoajax_attr());

                    if (uiInput.selectdictname() != null
                            && !uiInput.selectdictname().equals(""))
                        propertyInfo
                                .setSelectdictname(uiInput.selectdictname());

                    propertyInfos.add(propertyInfo);

                    if (editmode && uiInput.editable() == false) {
                        propertyInfo.setEditable(false);
                    } else {
                        propertyInfo.setEditable(true);
                    }
                    if (uiInput.hidden() == true) {
                        propertyInfo.setEdithidden(true);
                    } else {
                        propertyInfo.setEdithidden(false);
                    }
                } else {
                    if (editmode) { // 如果是编辑模式，如果需要
                        ModelPropertyInfo propertyInfo = new ModelPropertyInfo();

                        propertyInfo.setEdithidden(true);
                        // 从前端加载初始化数据
                        Object valObject = inputdata.get(field.getName());
                        if (valObject != null) {
                            propertyInfo.setDefaultval("" + valObject);
                        } else {
                            propertyInfo.setDefaultval("");
                        }
                        propertyInfo.setName(field.getName());
                        propertyInfo.setAutoajax(true);
                        propertyInfos.add(propertyInfo);
                    }
                }
            }

            // 进行排序
            Collections.sort(propertyInfos,
                    new Comparator<ModelPropertyInfo>() {

                        @Override
                        public int compare(ModelPropertyInfo o1,
                                           ModelPropertyInfo o2) {
                            if (o1.getGroup() != o2.getGroup()) {
                                return o1.getGroup() - o2.getGroup();
                            } else {
                                if (o1.getOrder() != o2.getOrder())
                                    return o1.getOrder() - o2.getOrder();
                                else {
                                    return o1.getSuborder() - o2.getSuborder();
                                }
                            }
                        }
                    });

            info.setPropertyInfos(propertyInfos);
            return info;
        } catch (
                ClassNotFoundException e
                )

        {
        }

        return null;
    }
}
