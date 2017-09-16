package net.ghpublic.controller;

import framework.modol.BaseRecords;
import framework.modol.BaseResult;
import net.ghpublic.modol.*;
import net.ghpublic.service.ServiceLogin;
import net.ghpublic.service.ServiceTrade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wzd on 2016/5/17.
 */
@Controller
public class ContrTrade
{
    @Resource
    ServiceTrade tradeService;
    @Resource
    ServiceLogin loginService;

    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm");

    //获取港口列表
    @ResponseBody
    @RequestMapping(value = "PortList",method = RequestMethod.POST)
    public List<?> PortList(HttpServletRequest request) throws Exception
    {
        return tradeService.PortList();
    }
    //获取货物类型列表
    @ResponseBody
    @RequestMapping(value = "GoodsTypeList",method = RequestMethod.POST)
    public List<?> GoodsTypeList(HttpServletRequest request) throws Exception
    {
        return tradeService.GoodsTypeList();
    }
    //获取船舶类型列表
    @ResponseBody
    @RequestMapping(value = "ShipTypeList",method = RequestMethod.POST)
    public List<?> ShipTypeList(HttpServletRequest request) throws Exception
    {
        return tradeService.ShipTypeList();
    }
    //获取包装形式列表
    @ResponseBody
    @RequestMapping(value = "PackegeList",method = RequestMethod.POST)
    public List<?> PackegeList(HttpServletRequest request) throws Exception
    {
        return tradeService.PackegeList();
    }
    //获取货物单位列表
    @ResponseBody
    @RequestMapping(value = "getUnit",method = RequestMethod.POST)
    public List<?> getUnit(HttpServletRequest request) throws Exception
    {
        return tradeService.getUnit();
    }
    //发布交易信息
    @ResponseBody
    @RequestMapping(value = "postTrade",method = RequestMethod.POST)
    public BaseResult postTrade(HttpServletRequest request) throws Exception
    {
        String id=request.getParameter("id");
        String userid=request.getParameter("Userid");
        PublicUserEN userEN=loginService.getPublicUserByID(Integer.valueOf(userid));
        String Title=request.getParameter("Title");
        Date date=new Date();
        String FromID=request.getParameter("FromID");
        CommonPortEN start=null;
        if(FromID!=null&&!"".equals(FromID))
        {
            start=tradeService.PortByID(Integer.valueOf(FromID));
        }
        String ToID=request.getParameter("ToID");
        CommonPortEN end=null;
        if(ToID!=null&&!"".equals(ToID))
        {
            end=tradeService.PortByID(Integer.valueOf(ToID));
        }

        String Price=request.getParameter("Price");
        String Linkman=request.getParameter("Linkman");
        String Tel=request.getParameter("Tel");
        String Remark=request.getParameter("Remark");
        String tradetype=request.getParameter("tradetype");
        TradeTypeEN tradeTypeEN=tradeService.TradeTypeByID(Integer.valueOf(tradetype));
        //货源
        String TypeID=request.getParameter("TypeID");
        CommonGoodsunitEN goodsunitEN=null;
        if(TypeID!=null&&!"".equals(TypeID))
        {
            goodsunitEN=tradeService.GoodsTypeByID(Integer.valueOf(TypeID));
        }
        CommonUnitEN unitEN=null;
        String UnitID=request.getParameter("UnitID");
        if(UnitID!=null&&!"".equals(UnitID))
        {
            unitEN=tradeService.GoodsUnitByID(Integer.valueOf(UnitID));
        }
        String Name=request.getParameter("Name");
        String Tons=request.getParameter("Tons");

        String Package=request.getParameter("Package");
        //船源、租船、售船
        String ShiptypeID=request.getParameter("ShiptypeID");
        TradeShiptypeEN shiptypeEN=null;
        if(ShiptypeID!=null&&!"".equals(ShiptypeID))
        {
            shiptypeEN =tradeService.ShipTypeByID(Integer.valueOf(ShiptypeID));
        }
        String Shipname=request.getParameter("Shipname");
        String Load=request.getParameter("Load");
        String Route=request.getParameter("Route");
        //售船
        String Age=request.getParameter("Age");

        //保存
        TradeGoodssourceEN tradeGoodssourceEN=new TradeGoodssourceEN();
        if(id!=null&&!"".equals(id)&&!"-1".equals(id)&&!"0".equals(id))//更新
        {
            tradeGoodssourceEN=tradeService.getGoodsByid(Integer.valueOf(id));
        }
        tradeGoodssourceEN.setStatus("1");//已发布状态
        tradeGoodssourceEN.setUserEN(userEN);
        tradeGoodssourceEN.setTitile(Title);
        tradeGoodssourceEN.setPostdate(date);
        tradeGoodssourceEN.setStartport(start);
        tradeGoodssourceEN.setUnloadport(end);
        tradeGoodssourceEN.setPrice(Price);
        tradeGoodssourceEN.setLinkman(Linkman);
        tradeGoodssourceEN.setTel(Tel);
        tradeGoodssourceEN.setRemark(Remark);
        tradeGoodssourceEN.setTradeTypeEN(tradeTypeEN);

        tradeGoodssourceEN.setType(goodsunitEN);//货物类型
        tradeGoodssourceEN.setName(Name);
        tradeGoodssourceEN.setTons(Tons);
        tradeGoodssourceEN.setUnitEN(unitEN);
        tradeGoodssourceEN.setPackaging(Package);

        tradeGoodssourceEN.setTradeShiptypeEN(shiptypeEN);
        tradeGoodssourceEN.setShipname(Shipname);
        tradeGoodssourceEN.setLoad(Load);
        tradeGoodssourceEN.setRoute(Route);

        tradeGoodssourceEN.setAge(Age);

        tradeService.saveORupdate(tradeGoodssourceEN);

        return  BaseResult.newResultOK();
    }
    //获取最新发布列表
    @ResponseBody
    @RequestMapping(value = "latest",method = RequestMethod.POST)
    public List<TradeGoodssourceEN> getNewGoods(HttpServletRequest request)
    {
        return  tradeService.getTradeLatest();
    }
    //获取我的发布列表
    @ResponseBody
    @RequestMapping(value = "mypost",method = RequestMethod.POST)
    public List<TradeGoodssourceEN> getMyPost(HttpServletRequest request)
    {
        String userid=request.getParameter("UserID");
        return  tradeService.getMyPost(Integer.valueOf(userid));
    }
    //按条件查找信息列表
    @ResponseBody
    @RequestMapping(value = "TradeListByTerms",method = RequestMethod.POST)
    public List<TradeGoodssourceEN> getGoodsListByParams(HttpServletRequest request)
    {
        String s1=request.getParameter("FromID");
        String s2=request.getParameter("ToID");
        String s3=request.getParameter("TradetypeID");
        String GoodsTypeid=request.getParameter("GoodsTypeid");
        String ShipTypeid=request.getParameter("ShipTypeid");

        return tradeService.getGoodsListByParams(Integer.valueOf(s1),Integer.valueOf(s2),Integer.valueOf(s3),Integer.valueOf(GoodsTypeid),Integer.valueOf(ShipTypeid));
    }
    //按id取消我的发布
    @ResponseBody
    @RequestMapping(value = "CancelMyPost",method = RequestMethod.POST)
    public BaseResult CancelMyPost(HttpServletRequest request) throws Exception
    {
        String id=request.getParameter("id");

        TradeGoodssourceEN mypostEN=tradeService.getGoodsByid(Integer.valueOf(id));
        mypostEN.setStatus("0");//取消发布状态
        tradeService.updateEN(mypostEN);

        return BaseResult.newResultOK();
    }
}
