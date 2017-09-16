package net.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.annotation.Resource;

/**
 * Created by Admin on 2016/7/7.
 */
public class PublicBaseService
{
    @Resource(name = "factory2")
    protected SessionFactory sessionFactory;

    public int saveEN(Object entity)
    {
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        int result=-2;
        try
        {
            transaction= session.beginTransaction();
            result=(Integer) session.save(entity);
            transaction.commit();
        }
        catch (Exception e)
        {
            transaction.rollback();
            e.printStackTrace();
            result=-1;
        }
        finally
        {
            session.close();
        }

        return result;
    }

    public int updateEN(Object entity)
    {
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        int result=-2;
        try
        {
            transaction= session.beginTransaction();
            session.update(entity);
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

        return result;
    }

    public int deleteEN(Object entity)
    {
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        int result=-2;
        try
        {
            transaction= session.beginTransaction();
            session.delete(entity);
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

        return result;
    }
}
