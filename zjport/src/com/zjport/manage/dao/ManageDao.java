package com.zjport.manage.dao;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.manage.model.TJcJs;
import com.zjport.manage.model.TJcJsqx;
import com.zjport.manage.model.TJcQx;
import com.zjport.model.TArea;
import com.zjport.model.TUser;
import com.zjport.util.CommonConst;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("manageDao")
public class ManageDao extends BaseDaoDB
{

    private Map<Integer, String> qxmap = new HashMap<>();

    public BaseResult queryRoles(String jsmc, int page, int row)
    {
        ObjectQuery objectQuery = new ObjectQuery(TJcJs.class, "jsbh", false, page, row);
        if (!StringUtils.isEmpty(jsmc))
        {
            objectQuery.setLikeKeyVal("jsmc", jsmc);
        }

        BaseRecords records = super.find(objectQuery);
        if (records.getTotal() > 0)
        {
            if (CollectionUtils.isEmpty(qxmap))
            {
                loadQx();
            }

            List<TJcJs> jsList = records.getData();
            for (TJcJs js : jsList)
            {
                js.setQx(queryJsQx(js.getJsbh()));
            }

            records.setData(jsList);
        }

        return new BaseResult(BaseResult.RESULT_OK, "", records);
    }

    private void loadQx()
    {
        BaseRecords records = super.find(new ObjectQuery(TJcQx.class));
        if (records.getTotal() > 0)
        {
            List<TJcQx> qxs = records.getData();
            for (TJcQx qx : qxs)
            {
                qxmap.put(qx.getQxbh(), qx.getQxmc());
            }
        }
    }

    private String queryJsQx(Integer jsbh)
    {
        BaseRecords records = super.find(new ObjectQuery(TJcJsqx.class, "jsbh", jsbh, "bh", false));
        if(records.getTotal() == 0)
        {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        List<TJcJsqx> jsqxList = records.getData();
        for(TJcJsqx jsqx : jsqxList)
        {
            sb.append(qxmap.get(jsqx.getQxbh())).append(",");
        }

        String qxmcStr = sb.toString();
        if(!StringUtils.isEmpty(qxmcStr))
        {
            qxmcStr = qxmcStr.substring(0, qxmcStr.length()-1);
        }

        return qxmcStr;
    }

    public BaseResult saveRole(String jsmc, String qxstr)
    {
        long count = super.count(new ObjectQuery(TJcJs.class, "jsmc", jsmc));
        if(count != 0)
        {
            return new BaseResult(CommonConst.JSMC_REPEAT_CODE, CommonConst.JSMC_REPEAT_INFO);
        }

        // 保存角色信息并返回角色编号
        int jsbh = (int) super.saveAndReturn(new TJcJs(jsmc));

        // 保存角色与权限关系
        String[] qxArr = qxstr.split(",");
        for(String qxbh : qxArr)
        {
            super.save(new TJcJsqx(jsbh, Integer.parseInt(qxbh)));
        }

        return new BaseResult(BaseResult.RESULT_OK, "新增角色成功");
    }

    public BaseResult roleDetail(Integer jsbh)
    {
        if(jsbh == null)
        {
            return new BaseResult(CommonConst.JSBH_ERROR_CODE, CommonConst.JSBH_ERROR_INFO);
        }

        TJcJs js = (TJcJs) super.findUnique(new ObjectQuery(TJcJs.class, "jsbh", jsbh));
        if(js == null)
        {
            return new BaseResult(CommonConst.JSBH_ERROR_CODE, CommonConst.JSBH_ERROR_INFO);
        }

        // 查询所有权限
        BaseRecords records = super.find(new ObjectQuery(TJcQx.class, "qxbh", false));
        List<TJcQx> qxlist = records.getData();

        // 角色关联的权限
        List<TJcJsqx> jsqxlist = super.find(new ObjectQuery(TJcJsqx.class, "jsbh", jsbh)).getData();
        for(TJcJsqx jsqx : jsqxlist)
        {
            for(TJcQx qx : qxlist)
            {
                if(jsqx.getQxbh() == qx.getQxbh())
                {
                    qx.setCheck("1");
                    continue;
                }
            }
        }

        records.setData(qxlist);
        return new BaseResult(BaseResult.RESULT_OK, "", records);
    }

    public BaseResult updateRole(Integer jsbh, String jsmc, String qxstr)
    {
        if(jsbh == null)
        {
            return new BaseResult(CommonConst.JSBH_ERROR_CODE, CommonConst.JSBH_ERROR_INFO);
        }

        TJcJs js = (TJcJs) super.findUnique(new ObjectQuery(TJcJs.class, "jsbh", jsbh));
        if(js == null)
        {
            return new BaseResult(CommonConst.JSBH_ERROR_CODE, CommonConst.JSBH_ERROR_INFO);
        }

        if(!js.getJsmc().equals(jsmc))
        {
            long count = super.count(new ObjectQuery(TJcJs.class, "jsmc", jsmc));
            if(count != 0)
            {
                return new BaseResult(CommonConst.JSMC_REPEAT_CODE, CommonConst.JSMC_REPEAT_INFO);
            }

            // 角色更新
            js.setJsmc(jsmc);
            super.update(js);
        }

        // 先删除原来该角色与权限的关系，再保存新的关联关系
        super.delete(new SQL("delete from t_jc_jsqx where jsbh=?", jsbh));

        String[] qxArr = qxstr.split(",");
        for(String qxbh : qxArr)
        {
            super.save(new TJcJsqx(jsbh, Integer.parseInt(qxbh)));
        }

        return new BaseResult(BaseResult.RESULT_OK, "更新角色成功");
    }

    public BaseResult deleteRole(Integer jsbh)
    {
        if(jsbh == null)
        {
            return new BaseResult(CommonConst.JSBH_ERROR_CODE, CommonConst.JSBH_ERROR_INFO);
        }

        TJcJs js = (TJcJs) super.findUnique(new ObjectQuery(TJcJs.class, "jsbh", jsbh));
        if(js == null)
        {
            return new BaseResult(CommonConst.JSBH_ERROR_CODE, CommonConst.JSBH_ERROR_INFO);
        }

        long count = super.count(new ObjectQuery(TUser.class, "stJs", jsbh));
        if(count != 0)
        {
            return new BaseResult(CommonConst.JS_INUSE_CODE, CommonConst.JS_INUSE_INFO);
        }

        // 删除角色权限关联关系
        super.delete(new SQL("delete from h_jc_jsqx where jsbh=?", jsbh));

        // 删除角色
        super.delete(js);

        return new BaseResult(BaseResult.RESULT_OK, "删除角色成功");
    }

    public BaseResult queryQxList()
    {
        return new BaseResult(BaseResult.RESULT_OK, "", super.find(new ObjectQuery(TJcQx.class, "qxbh", false)));
    }

    public BaseResult queryQxList(int js)
    {
        String sql = "select a.qxmc,a.qxbh,a.fbh from t_jc_qx a LEFT JOIN t_jc_jsqx b on a.qxbh = b.qxbh LEFT JOIN t_jc_js c on b.jsbh = c.jsbh ";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(js)) {
            sql += " where c.jsbh =:jsbh";
            paramMap.put("jsbh", js);
        }
        SQL Sql = new SQL(sql, paramMap);
        return new BaseResult(BaseResult.RESULT_OK, "", super.find(Sql));
    }

    public String queryUserQx(Integer jsbh)
    {
        BaseRecords records = super.find(new ObjectQuery(TJcJsqx.class, "jsbh", jsbh));
        if(records.getTotal() == 0)
        {
            return "";
        }

        StringBuffer sb = new StringBuffer();

        // 角色关联的权限
        List<TJcJsqx> jsqxList = records.getData();

        // 查询所有权限
        List<TJcQx> qxlist = super.find(new ObjectQuery(TJcQx.class)).getData();
        for(TJcQx qx : qxlist)
        {
            boolean flag = false;
            for(TJcJsqx jsqx : jsqxList)
            {
                if(qx.getQxbh() == jsqx.getQxbh())
                {
                    flag = true;
                    break;
                }
            }

            // 角色没有该权限
            if(!flag)
            {
                sb.append(qx.getQxbh()).append(",");
            }
        }

        if(sb == null || StringUtils.isEmpty(sb.toString()))
        {
            return "";
        }

        return sb.toString().substring(0, sb.toString().length()-1);
    }

    public BaseResult queryAreaList() {
        return new BaseResult(BaseResult.RESULT_OK, "", super.find(new ObjectQuery(TArea.class)));
    }

    public BaseResult queryJsList() {
        return new BaseResult(BaseResult.RESULT_OK, "", super.find(new ObjectQuery(TJcJs.class)));
    }

    public BaseResult getOrgDetail(String id) {
        String sql = "select a.ST_ORG_NAME,a.ST_ORG_CODE,a.ST_ORG_SHORT_NAME,a.ST_ORG_DOMAIN," +
                "b.ST_ORG_NAME parentName,a.ST_ORG_ADDRESS,a.ST_ORG_PHONE,a.ST_ORG_AREA,c.ST_AREA_NAME," +
                "a.ST_ORG_FOX,a.ST_ORG_POST from t_org a " +
                "LEFT JOIN t_org b on a.ST_PARENT_ORG_ID = b.ST_ORG_ID " +
                "LEFT JOIN t_area c on a.ST_ORG_AREA=c.ST_AREA_CODE";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(id)) {
            sql += " where a.st_org_id =:orgId";
            paramMap.put("orgId", id);
        }
        SQL Sql = new SQL(sql, paramMap);
        return new BaseResult(BaseResult.RESULT_OK, "", super.find(Sql));
    }

    public BaseResult getUserDetail(int id) {
        String sql = "select a.ST_USER_NAME,d.ST_POSITION_NAME,c.ST_ORG_NAME,b.ST_DEPARTMENT_NAME,a.ST_OFFICE_PHONE," +
                "a.ST_PHONE,a.ST_FUCTITIOUS_PHONE,a.ST_EMAIL,e.jsbh,e.jsmc,a.ST_LAW_CODE,a.ST_ORDER,a.ST_ACCOUNT,a.ST_USER_SYNC_ID,a.ST_LOCATION " +
                "from t_user a LEFT JOIN t_department b on a.ST_DEPARTMENT_ID=b.ST_DEPARTMENT_ID " +
                "LEFT JOIN t_org c on a.ST_ORG_ID=c.ST_ORG_ID " +
                "LEFT JOIN t_position d on a.ST_POSITION_ID=d.ST_POSITION_ID " +
                "LEFT JOIN t_jc_js e on a.ST_JS=e.jsbh ";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(id)) {
            sql += " where a.st_user_id =:userId";
            paramMap.put("userId", id);
        }
        SQL Sql = new SQL(sql, paramMap);
        return new BaseResult(BaseResult.RESULT_OK, "", super.find(Sql));
    }
}
