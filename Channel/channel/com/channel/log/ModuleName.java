package com.channel.log;

/**
 * Created by 25019 on 2015/10/22.
 */
public enum ModuleName {
    /* HDXX("综合查询"),
     HDBJ("航道信息维护"),*/
    ZHCX("综合查询"),
    HDXXWH("航道信息维护"),
    HDXC("航道巡查"),
    HZGL_XZXK("航政管理-行政许可"),
    HZGL_XZCF("航政管理-行政处罚"),
    HZGL_THLZ("航政管理-通航论证"),
    HZGL_CZPC("航政管理-重置赔偿"),
    HDYH_GG("航道养护-骨干航道"),
    HDYH_ZX("航道养护-支线航道"),
    HDYH_ZXGC("航道养护-专项工程"),
    HDYH_YJQTGC("航道养护-应急抢通工程"),
    LLGC("流量观测"),
    TJBB_FD("统计报表-法定报表"),
    TJBB_SD("统计报表-设定报表"),
    XXWH_YYGL("系统维护-用户管理"),
    XXWH_ZZJG("系统维护-组织机构管理"),
    XXWH_SJZD("系统维护-数据字典"),
    XXWH_RZGL("系统维护-日志管理"),
    XXWH_BFHY("系统维护-备份还原");

    private String describe;

    private ModuleName(String desc) {
        this.describe = desc;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
