package net.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
public class BaseService
{
    @Resource(name = "factory")
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
    public int updateORSave(Object entity)
    {
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        int result=-2;
        try
        {
            transaction= session.beginTransaction();
            session.saveOrUpdate(entity);
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
    //批量
    public int updateORSaveBatches(List<Object> entitys)
    {
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        int result=-2;
        try
        {
            transaction= session.beginTransaction();
            int i=0;
            for(Object obj:entitys)
            {
                session.saveOrUpdate(obj);
                i++;
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

        return result;
    }
}
