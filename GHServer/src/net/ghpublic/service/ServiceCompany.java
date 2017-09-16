package net.ghpublic.service;

import framework.service.BaseService;
import net.ghpublic.modol.*;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 2016/4/19.
 */
@Service("serviceCompany")
public class ServiceCompany extends BaseService
{
    //按用户名获取公司列表
    public List<?> MyCompany(String username)
    {
        Session session=sessionFactory.openSession();
        List<?> names=session.createCriteria(CompanyBaseEN.class)
                .createAlias("userEN","user")
                .add(Restrictions.eq("user.username",username))
                .list();
        session.close();
        return names;
    }
    //按公司ID获取公司证书列表
    public List<CompanyCertEN> getComCertByID(int id)
    {
        Session session=sessionFactory.openSession();
        List<CompanyCertEN> certs=session.createCriteria(CompanyCertEN.class)
                .createCriteria("companyBaseEN")
                .add(Restrictions.eq("id",id))
                .list();
        session.close();
        return certs;
    }
    //按用户名获取已审核公司
    public List<?> MyCompanyPass(String username)
    {
        Session session=sessionFactory.openSession();
        List<String> names=session.createCriteria(CompanyBaseEN.class)
                .createAlias("statusEN","status")
                .add(Restrictions.eq("status.id",2))//审核通过
                .createCriteria("userEN")
                .add(Restrictions.eq("username",username))
                .list();
        session.close();
        return names;
    }
    //按提示获取公司名列表(审核状态)
    public List<String> CompanyNamesByTip(String tip)
    {
        Session session=sessionFactory.openSession();
        List<String> names=session.createCriteria(CompanyBaseEN.class)
                                    .setProjection(Projections.projectionList().add(Property.forName("name")))
                                    .add(Restrictions.like("name","%"+tip+"%"))
                .createAlias("statusEN","status").add(Restrictions.eq("status.id",2))
                                    .list();
        session.close();
        return names;
    }
    //按公司名获取公司信息
    public CompanyBaseEN CompanyByName(String name)
    {
        Session session=sessionFactory.openSession();
        CompanyBaseEN names=(CompanyBaseEN)session.createCriteria(CompanyBaseEN.class)
                .add(Restrictions.eq("name",name))
                .uniqueResult();
        session.close();
        return names;
    }
}
