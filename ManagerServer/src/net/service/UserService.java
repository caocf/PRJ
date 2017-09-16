package net.service;

import common.BaseResult;
import common.DataList;
import net.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class UserService extends BaseService
{
    //根据用户名获取用户信息
    public JcxxYhEN getUserByName(String name)
    {
        Session session=sessionFactory.openSession();
        JcxxYhEN user=(JcxxYhEN)session.createCriteria(JcxxYhEN.class).add(Restrictions.eq("zh",name)).uniqueResult();
        session.close();
        return user;
    }
    //根据用户ID获取用户信息
    public JcxxYhEN getUserByID(String id)
    {
        Session session=sessionFactory.openSession();
        JcxxYhEN user=(JcxxYhEN)session.createCriteria(JcxxYhEN.class).add(Restrictions.eq("id",id)).uniqueResult();
        session.close();
        return user;
    }
    //根据用户账号获取用户信息
    public JcxxYhEN getUserByAccount(String zh)
    {
        Session session=sessionFactory.openSession();
        JcxxYhEN user=(JcxxYhEN)session.createCriteria(JcxxYhEN.class).add(Restrictions.eq("zh",zh)).uniqueResult();
        session.close();
        return user;
    }
    //获取全部用户列表
    public List<UserBaseEN> AllUsers()
    {
        Session session=sessionFactory.openSession();
        List<UserBaseEN> users=session.createCriteria(UserBaseEN.class).list();
        session.close();
        return users;
    }
    //按层级获取用户列表
    public List<UserBaseEN> UsersByLevel(String s)
    {
        Session session=sessionFactory.openSession();
        List<UserBaseEN> users=session.createCriteria(UserBaseEN.class)
                                            .add(Restrictions.eq("level",s))
                                            .list();
        session.close();
        return users;
    }
    //按pid获取用户列表
    public List<UserBaseEN> UsersByPID(String pid)
    {
        Session session=sessionFactory.openSession();
        List<UserBaseEN> users=session.createCriteria(UserBaseEN.class)
                .add(Restrictions.eq("pid",pid))
                .list();
        session.close();
        return users;
    }
    //按名字搜索用户组织
    public BaseResult DirectoryByName(String name)
    {
        Session session=sessionFactory.openSession();
        List<String> names=new ArrayList<>();
        UserBaseEN users=(UserBaseEN)session.createCriteria(UserBaseEN.class)
                .add(Restrictions.eq("name",name))
                .uniqueResult();
        IteratorNames(session,users.getPid(),names);
        session.close();
        BaseResult result=new BaseResult();
        result.setObj(users);
        result.setS1(names);
        return result;
    }

    private void IteratorNames(Session session,String pid,List<String> names)
    {
        int id=Integer.valueOf(pid);
        UserBaseEN users=(UserBaseEN)session.createCriteria(UserBaseEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        names.add(users.getName());

        if("-1".equals(users.getPid()))
        {
            return;
        }
        IteratorNames(session,users.getPid(),names);
    }

    //按提示搜索用户名字列表
    public List<?> NamesByTip(String tip)
    {
        Session session=sessionFactory.openSession();
        List<UserBaseEN> users=session.createCriteria(UserBaseEN.class)
                .setProjection(Projections.projectionList().add(Property.forName("name")))
                .add(Restrictions.like("name","%"+tip+"%"))
                .list();
        session.close();
        return users;
    }

    //按层级获取部门列表
    public List<PatrolboatBaseEN> DepByLevel(String s)
    {
        Session session=sessionFactory.openSession();
        List<PatrolboatBaseEN> users=session.createCriteria(PatrolboatBaseEN.class)
                .add(Restrictions.eq("level",s))
                .list();
        session.close();
        return users;
    }

    //按层级获取部门列表
    public List<PatrolboatBaseEN> DepByPID(String pid)
    {
        Session session=sessionFactory.openSession();
        List<PatrolboatBaseEN> users=session.createCriteria(PatrolboatBaseEN.class)
                .add(Restrictions.eq("pid",pid))
                .list();
        session.close();
        return users;
    }

    //按提示搜索部门名字列表
    public List<?> DepByTip(String tip)
    {
        Session session=sessionFactory.openSession();
        List<PatrolboatBaseEN> users=session.createCriteria(PatrolboatBaseEN.class)
                .setProjection(Projections.projectionList().add(Property.forName("name")))
                .add(Restrictions.like("name","%"+tip+"%"))
                .list();
        session.close();
        return users;
    }

    //按名字搜索部门组织
    public BaseResult DepsByName(String name)
    {
        Session session=sessionFactory.openSession();
        List<String> names=new ArrayList<>();
        PatrolboatBaseEN users=(PatrolboatBaseEN)session.createCriteria(PatrolboatBaseEN.class)
                .add(Restrictions.eq("name",name))
                .uniqueResult();
        IteratorDeps(session,users.getPid(),names);
        session.close();
        BaseResult result=new BaseResult();
        result.setObj(users);
        result.setS1(names);
        return result;
    }

    private void IteratorDeps(Session session,String pid,List<String> names)
    {
        int id=Integer.valueOf(pid);
        PatrolboatBaseEN users=(PatrolboatBaseEN)session.createCriteria(PatrolboatBaseEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        names.add(users.getName());

        if("-1".equals(users.getPid()))
        {
            return;
        }
        IteratorDeps(session,users.getPid(),names);
    }
    //分页获取角色列表
    public DataList UserRoles(int page, String name)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(UserRoleEN.class).add(Restrictions.like("role","%"+name+"%"));

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();
        session.close();

        return new DataList(pages,list);
    }
    //按ID获取权限
    public UserPermissionEN PermissionByID(int id)
    {
        Session session=sessionFactory.openSession();
        UserPermissionEN userPermissionEN=(UserPermissionEN)session.createCriteria(UserPermissionEN.class).add(Restrictions.eq("id",id)).uniqueResult();
        session.close();
        return userPermissionEN;
    }
    //获取全部权限列表
    public List<?> PermissionList()
    {
        Session session=sessionFactory.openSession();
        List<?> list=session.createCriteria(UserPermissionEN.class).list();
        session.close();
        return list;
    }
    //获取全部权限组别
    public List<?> PermissionGroup()
    {
        Session session=sessionFactory.openSession();
        List<?> list=session.createCriteria(UserGroupEN.class).list();
        session.close();
        return list;
    }

    //按ID获取角色
    public UserRoleEN UserRoleByID(int id)
    {
        Session session=sessionFactory.openSession();
        UserRoleEN userRoleEN=(UserRoleEN)session.createCriteria(UserRoleEN.class).add(Restrictions.eq("id",id)).uniqueResult();
        session.close();
        return userRoleEN;
    }

}
