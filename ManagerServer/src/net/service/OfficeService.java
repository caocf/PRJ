package net.service;

import common.BaseResult;
import common.Time;
import net.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wzd on 2016/7/7.
 */
@Service
public class OfficeService extends BaseService
{
    SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //按审批人获取假条
    public BaseResult LeaveAndOvertimefinish(String  id,int page,String key,int... type)
    {
        Session session=sessionFactory.openSession();
        Criteria criteria=session.createCriteria(LeaveOrOt.class)
                //.add(Restrictions.or(Restrictions.eq("approvalResult",ra),Restrictions.eq("approvalResult",rb)))
                .add(Restrictions.eq("approvalResult3",1))//正式提交的
                .addOrder(Order.desc("leaveOrOtDate"))
                .createAlias("approvalID1","approve") .add(Restrictions.eq("approve.id",id))
                .createAlias("user","u").add(Restrictions.like("u.xm","%"+key+"%"))
                .createAlias("leaveOrOtKind","kind").add(Restrictions.between("kind.kindType",type[0],type[1]));

        long count=(long)criteria .setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;
        List<LeaveOrOt> list=criteria.setProjection(null).setFirstResult((page)*10).setMaxResults(10).list();
        session.close();
        return new BaseResult(pages,String.valueOf(count),list);
    }
    //按申请人获取假条
    public BaseResult LeaveAndOvertimeApply(String  id,int page,String key,int... type)
    {
        Session session=sessionFactory.openSession();
        Criteria criteria=session.createCriteria(LeaveOrOt.class)
                .addOrder(Order.desc("leaveOrOtDate"))
                .createAlias("user","u").add(Restrictions.eq("u.id",id))
                .add(Restrictions.like("leaveOrOtReason","%"+key+"%"))
                .createAlias("leaveOrOtKind","kind").add(Restrictions.between("kind.kindType",type[0],type[1]));

        Object obj=criteria .setProjection(Projections.rowCount()) .uniqueResult();
        long count=(long)obj;
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<LeaveOrOt> list=criteria.setProjection(null).setFirstResult((page)*10).setMaxResults(10).list();

        session.close();
        return new BaseResult(pages,String.valueOf(count),list);
    }
    //按ID获取假条
    public LeaveOrOt showLeaveAndOvertimeApproval(int id)
    {
        Session session=sessionFactory.openSession();
        LeaveOrOt user=(LeaveOrOt)session.createCriteria(LeaveOrOt.class)
                                    .add(Restrictions.eq("id",id))
                                    .uniqueResult();
        session.close();
        return user;
    }
    //按申请人ID和类型和时间获取已审批假条
    public List<LeaveOrOt> ApprovedLeavesByUserID(String userid,int kind,Date d1,Date d2)
    {
        Session session=sessionFactory.openSession();
        List<LeaveOrOt> user=session.createCriteria(LeaveOrOt.class)
                .add(Restrictions.eq("approvalResult","准许"))
                .add(Restrictions.ge("beginDate",d1))
                .add(Restrictions.le("endDate",d2))
                .createAlias("leaveOrOtKind","kind")
                .add(Restrictions.eq("kind.kindType",kind))
                .createCriteria("user")
                .add(Restrictions.eq("id",userid)).list();
        session.close();
        return user;
    }
    //获取假别
    public LeaveOrOtKind getKindByID(int id)
    {
        Session session=sessionFactory.openSession();
        LeaveOrOtKind kindID=(LeaveOrOtKind)session.createCriteria(LeaveOrOtKind.class)
                .add(Restrictions.eq("kindID",id))
                .uniqueResult();
        session.close();
        return kindID;
    }

    //按id查审批人列表
    public List<?> Approvers(String id)
    {
        Session session=sessionFactory.openSession();
        List<?> kindID=session.createCriteria(JcxxYhEN.class)
                .add(Restrictions.eq("id",id))
                .list();
        session.close();
        return kindID;
    }
    //获取工作日时长
    public int getRealTime(String time1,String time2) throws Exception
    {
        Date beginDate = sdDateFormat  .parse(time1);
        Date endDate = sdDateFormat  .parse(time2);

        if(beginDate.getTime()>=endDate.getTime())
            return 0;
        Time time=new Time();
        return time.getPerid(time.getBeginTime(beginDate),time.getEndTime(endDate));
    }

    //按用户获取日程
    public List<?> AgendaByUser(String id)
    {
        Session session=sessionFactory.openSession();
        List<?> num=session.createCriteria(ScheduleBaseEN.class)
                .createCriteria("user")
                .add(Restrictions.eq("id",id))
                .list();
        session.close();
        return num;
    }
    //按用户和时间获取日程
    public List<?> AgendaByUserAndTime(String id, Date time1, Date time2)
    {
        Session session=sessionFactory.openSession();
        List<?> num=session.createCriteria(ScheduleBaseEN.class)
                .add(Restrictions.between("scheduletime",time1,time2))
                .createCriteria("user")
                .add(Restrictions.eq("id",id))
                .list();
        session.close();
        return num;
    }

    //按ID获取日程
    public ScheduleBaseEN AgendaByID(int id)
    {
        Session session=sessionFactory.openSession();
        ScheduleBaseEN num=(ScheduleBaseEN)session.createCriteria(ScheduleBaseEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return num;
    }
    @Resource
    OrgnizeService orgnizeService;
    //按ID查询组织下员工考勤数据
    public Duty CountByID(String id,String type,String d1,String d2) throws Exception
    {
        Duty duty=new Duty();
        if(type.equals("1"))
        {
            JcxxZzjgEN zz=orgnizeService.DepartmentByID(id);
            //是单位
            if(zz.getZzjglb().equals("1"))
            {
                List<JcxxZzjgEN> orgs=orgnizeService.ItemsByPid(id);
                for(JcxxZzjgEN zj:orgs)
                {
                    Duty depduty=CountByID(zj.getId(),"1",d1,d2);
                    duty.leave+=depduty.getLeave();
                    duty.jb+=depduty.getJb();
                    duty.cc+=depduty.getCc();

                }
                return duty;
            }
            //是部门,则统计员工数据
            if(zz.getZzjglb().equals("2"))
            {
                    List<JcxxYhEN> list=orgnizeService.CrewsByDepID(id);

                    for(JcxxYhEN yh:list)
                    {
                        Duty userduty=CountByID(yh.getId(),"3",d1,d2);
                        duty.leave+=userduty.getLeave();
                        duty.jb+=userduty.getJb();
                        duty.cc+=userduty.getCc();
                    }
                    return duty;
            }
        }
        //是用户
        if(type.equals("3"))
        {
            Date date1=sdDateFormat.parse(d1);
            Date date2=sdDateFormat.parse(d2);
            Time time=new Time();
            int p=time.getPerid(time.getBeginTime(date1),time.getEndTime(date2));

            //请假
            int userleave=0;
            List<LeaveOrOt> leaves1=ApprovedLeavesByUserID(id,1,date1,date2);
            for(LeaveOrOt leave:leaves1)
            {
                userleave+=leave.getLastDate();
            }
            duty.setLeave(userleave);
            //加班
            int userjb=0;
            List<LeaveOrOt> leaves2=ApprovedLeavesByUserID(id,2,date1,date2);
            for(LeaveOrOt leave:leaves2)
            {
                userjb+=leave.getLastDate();
            }
            duty.setJb(userjb);
            //出差
            int usercc=0;
            List<LeaveOrOt> leaves3=ApprovedLeavesByUserID(id,3,date1,date2);
            for(LeaveOrOt leave:leaves3)
            {
                usercc+=leave.getLastDate();
            }
            duty.setCc(usercc);
            return duty;
        }

        return duty;
    }
}
