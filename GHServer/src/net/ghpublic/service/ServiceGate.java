package net.ghpublic.service;

import framework.service.BaseService;
import net.ghpublic.modol.GateBaseEN;
import net.ghpublic.modol.WaterInfoEN;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 2016/4/19.
 */
@Service("serviceGate")
public class ServiceGate extends BaseService
{
    public GateBaseEN getPassByShip(String shipname)
    {
        Session session=sessionFactory.openSession();
        GateBaseEN gateBaseEN=(GateBaseEN) session.createCriteria(GateBaseEN.class)
                                    .add(Restrictions.eq("shipname",shipname))
                                    .uniqueResult();
        session.close();

        return gateBaseEN;
    }

}
