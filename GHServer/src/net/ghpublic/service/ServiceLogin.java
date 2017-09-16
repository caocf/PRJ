package net.ghpublic.service;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import framework.modol.BaseResult;
import framework.modol.DataList;
import framework.service.BaseService;
import net.ghpublic.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by wangzedong on 2016/4/22.
 */
@Service("serviceLogin")
public class ServiceLogin extends BaseService
{
    //按名字获取用户
    public PublicUserEN getPublicUserByName(String username)
    {
        Session session=sessionFactory.openSession();
        PublicUserEN user= (PublicUserEN) session.createCriteria(PublicUserEN.class)
                .add(Restrictions.eq("username",username))
                .uniqueResult();
        session.close();
        return user;
    }
    //获取用户类型列表
    public List<?> UserTypes()
    {
        Session session=sessionFactory.openSession();
        List<?> list=  session.createCriteria(PublicUserTypeEN.class).list();
        session.close();
        return list;
    }

    //获取地区列表
    public List<?> Regions()
    {
        Session session=sessionFactory.openSession();
        List<?> list=  session.createCriteria(PublicuserRegionEN.class).list();
        session.close();
        return list;
    }
    //生成验证码
    public String generateCode()
    {
        Random random = new Random();
        int code= random.nextInt(8999)+1000;
        return String.valueOf(code);
    }
    //发送验证码
    public String sendMSGAL(String tel,String code) throws Exception
    {
        //发送短信
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23418011", "9610d7dd1cb4c01a9d1a18895f3fa226");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        //req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("身份验证");
        JSONObject jb=new JSONObject();
        jb.put("code",code);//变量
        //jb.put("product","注册");
        req.setSmsParamString(jb.toString());
        req.setRecNum(tel);
        req.setSmsTemplateCode("SMS_12740170");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        /*//System.out.println(rsp.getBody());
        JSONObject jsonObject=new JSONObject(rsp.getBody());
        JSONObject jsonObject1=jsonObject.getJSONObject("alibaba_aliqin_fc_sms_num_send_response");
        //EncodeTool.convertStreamToString(new String(rsp.getBody()));
        */
        return rsp.getBody();
    }
    //按手机号码查找已配对验证码
    public PublicUserValidationEN getValidationByTel(String tel)
    {
        Session session=sessionFactory.openSession();
        PublicUserValidationEN publicUserValidationEN= (PublicUserValidationEN)session.createCriteria(PublicUserValidationEN.class)
                .add(Restrictions.eq("tel",tel))
                .uniqueResult();
        session.close();

        return publicUserValidationEN;

    }
    //按ID获取用户类型
    public PublicUserTypeEN getUserTypeById(int id)
    {
        Session session=sessionFactory.openSession();
        PublicUserTypeEN publicUserTypeEN= (PublicUserTypeEN)session.createCriteria(PublicUserTypeEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return publicUserTypeEN;

    }
    //按ID获取城市
    public PublicuserRegionEN getRegionById(int id)
    {
        Session session=sessionFactory.openSession();
        PublicuserRegionEN p= (PublicuserRegionEN)session.createCriteria(PublicuserRegionEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return p;

    }
    //生成用户实例
    public PublicUserEN newPublicUser(HttpServletRequest request)
    {
        PublicUserTypeEN usertypeEN=getUserTypeById(Integer.valueOf(request.getParameter("usertype")));
        PublicuserRegionEN regionEN=getRegionById(Integer.valueOf(request.getParameter("region")));
        PublicUserEN publicUserEN=new PublicUserEN();
        publicUserEN.setUsername(request.getParameter("username"));
        publicUserEN.setPassword(request.getParameter("password"));
        publicUserEN.setTel(request.getParameter("tel"));
        publicUserEN.setRegion(regionEN);
        publicUserEN.setUsertype(usertypeEN);
        publicUserEN.setRegistertime(new Date());
        publicUserEN.setStatus(1);//默认启用

        return publicUserEN;
    }
    //按ID获取用户绑定信息状态
    public PublicuserStatusEN PublicStatusByID(int id)
    {
        Session session=sessionFactory.openSession();
        PublicuserStatusEN publicuserStatusEN= (PublicuserStatusEN)session.createCriteria(PublicuserStatusEN.class)
                .add(Restrictions.eq("id",id)).uniqueResult();
        session.close();
        return publicuserStatusEN;
    }
    //按手机号获取用户
    public PublicUserEN getPublicUserByTel(String tel)
    {
        Session session=sessionFactory.openSession();
        PublicUserEN user= (PublicUserEN) session.createCriteria(PublicUserEN.class)
                .add(Restrictions.eq("tel",tel))
                .uniqueResult();
        session.close();
        return user;
    }
    //分页获取绑定信息列表
    public DataList UsersToCheckByShip(String tip,int page,int type)
    {
        Session session=sessionFactory.openSession();
        Criteria ci=null;
        if(type==1)//船舶
        {
            ci= session.createCriteria(ShipEN.class)
                    .addOrder(Order.desc("binddate"))
                    .createAlias("publicUserEN","user")
                    .add(Restrictions.or(Restrictions.like("user.username","%"+tip+"%"),Restrictions.like("user.tel","%"+tip+"%")));

        }
        else
        {
            ci= session.createCriteria(CompanyBaseEN.class)
                    .addOrder(Order.desc("binddate"))
                    .createAlias("userEN","user")
                    .add(Restrictions.or(Restrictions.like("user.username","%"+tip+"%"),Restrictions.like("user.tel","%"+tip+"%")));
        }


        long count=(long)ci.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=ci.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();

        session.close();
        return new DataList(pages,list);
    }
    //按id获取船舶
    public ShipEN ShipByID(int id)
    {
        Session session=sessionFactory.openSession();
        ShipEN shipEN=(ShipEN)session.createCriteria(ShipEN.class)
                .add(Restrictions.eq("shipid",id)).uniqueResult();
        session.close();
        return shipEN;

    }
    //按id获取船舶证书
    public ShipcertEN ShipCerttByID(int id)
    {
        Session session=sessionFactory.openSession();
        ShipcertEN shipcertEN= (ShipcertEN)session.createCriteria(ShipcertEN.class)
                .createCriteria("shipEN")
                .add(Restrictions.eq("shipid",id)).uniqueResult();
        session.close();
        return shipcertEN;
    }
    //按id获用户绑定信息状态
    public PublicuserStatusEN StatusByID(int id)
    {
        Session session=sessionFactory.openSession();
        PublicuserStatusEN statusEN=(PublicuserStatusEN)session.createCriteria(PublicuserStatusEN.class)
                .add(Restrictions.eq("id",id)).uniqueResult();
        session.close();
        return statusEN;
    }
    //按id获取企业
    public CompanyBaseEN CompanyByID(int id)
    {
        Session session=sessionFactory.openSession();
        CompanyBaseEN companyBaseEN=(CompanyBaseEN)session.createCriteria(CompanyBaseEN.class)
                .add(Restrictions.eq("id",id)).uniqueResult();

        session.close();
        return companyBaseEN;
    }
    //按id获取企业证书
    public CompanyCertEN CompanyCerttByID(int id)
    {
        Session session=sessionFactory.openSession();
        CompanyCertEN companyCertEN= (CompanyCertEN)session.createCriteria(CompanyCertEN.class)
                .createCriteria("companyBaseEN","com")
                .add(Restrictions.eq("com.id",id)).uniqueResult();
        session.close();

        return companyCertEN;
    }
    //按类型和提示获取用户列表
    public DataList UsersByKey(String tip,int typeid,int page,int status)
    {
        Criterion cn=status==-1?Restrictions.le("status",10):Restrictions.eq("status",status);
        Criterion typ=typeid==1?Restrictions.eq("type.id",typeid):Restrictions.ge("type.id",2);
        Session session=sessionFactory.openSession();
        Criteria cr= session.createCriteria(PublicUserEN.class)
                .add(Restrictions.or(Restrictions.like("username","%"+tip+"%"),Restrictions.like("tel","%"+tip+"%")))
                .add(cn)
                .createAlias("usertype","type")
                .add(typ);

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();

        session.close();

        return new DataList(pages,list);

    }
    //按ID获取用户
    public PublicUserEN getPublicUserByID(int  id)
    {
        Session session=sessionFactory.openSession();
        PublicUserEN user= (PublicUserEN) session.createCriteria(PublicUserEN.class)
                .add(Restrictions.eq("userid",id))
                .uniqueResult();
        session.close();
        return user;
    }
    //统计待审核船舶
    public int ShipCheckCount()
    {
        Session session=sessionFactory.openSession();
        long count= (long) session.createCriteria(ShipEN.class).createCriteria("shipstatus")
                .add(Restrictions.eq("id",1))//待审核
                .setProjection(Projections.rowCount()).uniqueResult();
        session.close();
        return (int)count;
    }
    //统计待审核公司
    public int CompanyCheckCount()
    {
        Session session=sessionFactory.openSession();
        long count= (long) session.createCriteria(CompanyBaseEN.class).createCriteria("statusEN")
                .add(Restrictions.eq("id",1))//待审核
                .setProjection(Projections.rowCount()).uniqueResult();
        session.close();
        return (int)count;
    }
}
