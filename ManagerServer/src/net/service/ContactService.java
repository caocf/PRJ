package net.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.BaseRecords;
import common.BaseResult;
import net.bean.DeptBean;
import net.modol.JcxxYhEN;
import net.modol.JcxxZzjgEN;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ContactService extends BaseService
{
    public BaseResult allDept() {
        String data = this.getWs("GetZZJG");
        List<DeptBean> list = new ArrayList<>();
        JSONObject root = JSON.parseObject(data);
        getZzjgList(root, list);
        BaseResult result = new BaseResult();
        BaseRecords records = new BaseRecords();
        records.setData(list);
        result.setRecords(records);
        return result;
    }

    public String getWs(String opname) {
        String result = "";
        try {
            Service service = new Service();
            Call call = (org.apache.axis.client.Call) service.createCall();
            call.setTargetEndpointAddress("http://172.26.24.33/webservice/webService.asmx");
            call.setOperationName(opname);
            call.setSOAPActionURI("http://tempuri.org/" + opname);
            call.setTimeout(Integer.valueOf(60 * 1000));
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            result = (String) call.invoke(new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void getZzjgList(JSONObject obj, List<DeptBean> list) {
        DeptBean db = new DeptBean();
        db.setID(obj.getString("ID"));
        db.setZZJGMC(obj.getString("ZZJGMC"));
        db.setSJZZJG(obj.getString("SJZZJG"));
        db.setZZJGLB(obj.getIntValue("ZZJGLB"));
        db.setWZH(obj.getIntValue("WZH"));
        String tempdept = obj.getString("dept");
        if (!"".equals(tempdept)) {
            db.setIsleaf(0);
        } else {
            db.setIsleaf(1);
        }
        list.add(db);
        if (!"".equals(tempdept)) {
            JSONArray arr = obj.getJSONArray("dept");
            int len = arr.size();
            for (int i = 0; i < len; i++) {
                getZzjgList(arr.getJSONObject(i), list);
            }
        }
    }

    public BaseResult deptUser(String deptid, String username, int page) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(JcxxYhEN.class);
        if (username != null && !"".equals(username)) {
            criteria.add(Restrictions.like("xm", "%" + username + "%"));
        }
        if (deptid != null && !"".equals(deptid)) {
            criteria.add(Restrictions.eq("dw", deptid));
        }
        //页码
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        int pages = (int) count / 10;
        pages = count % 10 > 0 ? pages + 1 : pages;
        if (pages == 0)
            pages = 1;
        List<?> datas = criteria.setProjection(null).setFirstResult((page - 1) * 10)
                .setMaxResults(10).list();
        session.close();
        return new BaseResult(pages, (int) count, datas);
    }

    public BaseResult deptUser(String username, String deptid, int isleaf, int page) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(JcxxYhEN.class);
        if (username != null && !"".equals(username)) {
            criteria.add(Restrictions.like("xm", "%" + username + "%"));
        }
        if (deptid != null && !"".equals(deptid)) {
            if (isleaf == 1) {
                criteria.add(Restrictions.eq("bm", deptid));
            } else {
                criteria.add(Restrictions.eq("dw", deptid));
            }
        }
        //页码
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        int pages = (int) count / 10;
        pages = count % 10 > 0 ? pages + 1 : pages;
        if (pages == 0)
            pages = 1;
        List<?> datas = criteria.setProjection(null).setFirstResult((page - 1) * 10)
                .setMaxResults(10).list();
        session.close();
        return new BaseResult(pages, (int) count, datas);
    }

    public BaseResult queryUserById(String id) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(JcxxYhEN.class);
        criteria.add(Restrictions.eq("id", id));
        JcxxYhEN en = (JcxxYhEN) criteria.uniqueResult();
        BaseResult ret = new BaseResult();
        if (en != null && !"".equals(en.getDw())) {
            Criteria criteria2 = session.createCriteria(JcxxZzjgEN.class);
            criteria2.add(Restrictions.eq("id", en.getDw()));
            JcxxZzjgEN zzjg = (JcxxZzjgEN) criteria2.uniqueResult();
            ret.addToMap("zzjg", zzjg);
        }
        ret.setObj(en);
        session.close();
        return ret;
    }

    public BaseResult updateUserById(String id, String yx, String bgdh, String sjhm, String xnwh) {
        Session session = sessionFactory.openSession();
        BaseResult ret = new BaseResult();
        Criteria criteria = session.createCriteria(JcxxYhEN.class);
        criteria.add(Restrictions.eq("id", id));
        JcxxYhEN en = (JcxxYhEN) criteria.uniqueResult();
        if (en != null) {
            en.setYx(yx);
            en.setBgdh(bgdh);
            en.setSjhm(sjhm);
            en.setXnwh(xnwh);
            super.updateEN(en);
        }
        session.close();
        return ret;
    }

    //用户同步
    public void UserById(org.json.JSONArray array)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction=null;
        int result=-2;
        try
        {
            transaction= session.beginTransaction();

            for(int i=0;i<array.length();i++)
            {
                org.json.JSONObject userobj = array.getJSONObject(i);
                String id=userobj.getString("ID");
                String ZH=userobj.getString("ZH");
                String XM=userobj.getString("XM");
                String MM=userobj.getString("MM");
                String ZW=userobj.getString("ZW");
                String DW=userobj.getString("DW");
                String BM=userobj.getString("BM");
                String JS=userobj.getString("JS");
                String SJZG=userobj.getString("SJZG");
                String YX=userobj.getString("YX");
                String BGDH=userobj.getString("BGDH");
                String SJHM=userobj.getString("SJHM");
                String XNWH=userobj.getString("XNWH");
                String ZT=userobj.getString("ZT");
                String WZH=userobj.getString("WZH");
                String SJZZJGIDLB=userobj.getString("SJZZJGIDLB");
                String JDID=userobj.getString("JDID");
                String BMMC=userobj.getString("BMMC");
                String zwmc=userobj.getString("zwmc");
                String zfzhm=userobj.getString("zfzhm");
                String zfzmc=userobj.getString("zfzmc");

                int flag=1;
                JcxxYhEN user=(JcxxYhEN)session.createCriteria(JcxxYhEN.class)
                        .add(Restrictions.eq("id", id)).uniqueResult();
                if(user==null)
                {
                    user=new JcxxYhEN();
                    user.setId(id);
                    flag=0;
                }

                user.setZh(ZH);
                user.setXm(XM);
                user.setMm(MM);
                user.setZw(ZW);
                user.setDw(DW);
                user.setBm(BM);
                user.setJs("10");
                user.setSjzg(SJZG);
                user.setYx(YX);
                user.setBgdh(BGDH);
                user.setSjhm(SJHM);
                user.setXnwh(XNWH);
                user.setZt(ZT);
                user.setWzh(Integer.valueOf(WZH));
                user.setSjzzjgidlb(SJZZJGIDLB);
                user.setJdid(JDID);
                user.setBmmc(BMMC);

                if(flag==1)
                {
                    session.update(user);
                }else
                {
                    session.save(user);
                }

                if(i>=50)
                {
                    session.flush();
                    session.clear();
                }
            }

            transaction.commit();
            result=1;
        }
        catch (Exception e)
        {
            transaction.rollback();
            result=-1;
        }
        finally
        {
            session.close();
        }
    }
    //组织机构同步
    public void Orgnize(org.json.JSONObject object)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction=null;
        int result=-2;
        try
        {
            transaction= session.beginTransaction();

            DepIterator(object,session);


            transaction.commit();
            result=1;
        }
        catch (Exception e)
        {
            transaction.rollback();
            result=-1;
        }
        finally
        {
            session.close();
        }
    }
    public void DepIterator(org.json.JSONObject object,Session session) throws Exception
    {
        String ID=object.getString("ID");
        String ZZJGMC=object.getString("ZZJGMC");
        String ZZJGBM=object.getString("ZZJGBM");
        String ZZJGDM=object.getString("ZZJGDM");
        String ZZJGLB=object.getString("ZZJGLB");
        String ZZYM=object.getString("ZZYM");
        String SJZZJG=object.getString("SJZZJG");
        String SJZZJGIDLB=object.getString("SJZZJGIDLB");
        String WZH=object.getString("WZH");
        String SFCK=object.getString("SFCK");
        String SFJD=object.getString("SFJD");
        String ZZJGJC=object.getString("ZZJGJC");
        String SSQY=object.getString("SSQY");
        String WSDWBH=object.getString("WSDWBH");
        String adcode=object.getString("adcode");
        Object dept=object.get("dept");

        JcxxZzjgEN jcxxZzjgEN=new JcxxZzjgEN();
        jcxxZzjgEN.setId(ID);
        jcxxZzjgEN.setZzjgmc(ZZJGMC);System.out.println(ZZJGMC);
        jcxxZzjgEN.setZzjgbm(ZZJGBM);
        jcxxZzjgEN.setZzjgdm(ZZJGDM);
        jcxxZzjgEN.setZzjglb(ZZJGLB);
        jcxxZzjgEN.setZzym(ZZYM);
        jcxxZzjgEN.setSjzzjg(SJZZJG);
        jcxxZzjgEN.setSjzzjgidlb(SJZZJGIDLB);
        jcxxZzjgEN.setWzh(Integer.valueOf(WZH));
        jcxxZzjgEN.setSfck(SFCK);
        jcxxZzjgEN.setSfjd(SFJD);
        jcxxZzjgEN.setZzjgjc(ZZJGJC);
        jcxxZzjgEN.setSsqy(SSQY);
        jcxxZzjgEN.setWsdwbh(WSDWBH);
        jcxxZzjgEN.setAdcode(adcode);

        session.save(jcxxZzjgEN);

        if(dept==null||"".equals(dept))
        {
            return;
        }
        else
        {
            org.json.JSONArray array=(org.json.JSONArray) dept;
            for(int i=0;i<array.length();i++)
            {
                org.json.JSONObject childobj=array.getJSONObject(i);
                if(childobj!=null)
                {
                    DepIterator(childobj,session);
                }
            }
        }
    }

}
