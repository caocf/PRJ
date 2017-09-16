package net.ghpublic.service;

import framework.modol.BaseResult;
import framework.service.BaseService;
import net.ghpublic.modol.*;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2016/4/19.
 */
@Service("serviceShip")
public class ServiceShip extends BaseService
{
    //按用户名查询船舶列表
    public List<ShipEN> getMyShips(String username)
    {
        Session session=sessionFactory.openSession();
        List<ShipEN> ships=session.createCriteria(ShipEN.class)
                                    .createCriteria("publicUserEN")
                                    .add(Restrictions.eq("username",username))
                                    .list();
        session.close();
        return ships;
    }
    //XML2Map1
    public Map AnalysisAttributeOfXML(String XmlString) throws Exception
    {
        JSONObject obj = new JSONObject();
        Reader in = new StringReader(XmlString);
        Document document = new SAXBuilder().build(in);
        Element RootNode = document.getRootElement();

        Map map = new HashMap();
        map.put(RootNode.getName(),iterateElement(RootNode));

        return map;
    }
    //XML2Map2
    private Map  iterateElement(Element element)
    {
        List jiedian = element.getChildren();//List NodeList = RootNode.getChildren("dataList");
        Element et = null;
        Map obj = new HashMap();

        for (int i = 0; i < jiedian.size(); i++)
        {
            et = (Element) jiedian.get(i);
            String name=et.getName();
            if("dataList".equals(name))
            {
                if (et.getChildren().size() == 0)
                    return obj;

                obj.put(name,iterateElement(et));
            }else if("data".equals(name))
            {
                List list = new LinkedList();
                String filter=et.getAttributeValue("filterName");
                String level=et.getAttributeValue("alarmLevel");
                if(filter!=null&&!"".equals(filter))
                {
                    list.add(filter);
                }
                else {
                    list.add("");
                }

                if(level!=null&&!"".equals(level))
                {
                    list.add(level);
                }
                else {
                    list.add("");
                }
                Element child=et.getChild("detail");
                if(child!=null)
                {
                    Element child1=child.getChild("description");
                    if(child1!=null)
                    {
                        list.add(child1.getTextTrim());
                    }
                }

                obj.put("data"+i,list);
            }
        }
        return obj;
    }
    //按用户名查询已审核船舶列表
    public List<ShipEN> myshiplistPass(String username)
    {
        Session session=sessionFactory.openSession();
        List<ShipEN> ships=session.createCriteria(ShipEN.class)
                .createAlias("shipstatus","status")
                .add(Restrictions.eq("status.id",2))//审核通过
                .createCriteria("publicUserEN")
                .add(Restrictions.eq("username",username))
                .list();
        session.close();
        return ships;
    }
}
