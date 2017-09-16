package net.ghpublic.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/5/17.
 */
public class TradeGoodssourceEN
{
    //通用
    private int id;
    String status;
    PublicUserEN userEN;

    public PublicUserEN getUserEN() {
        return userEN;
    }

    public void setUserEN(PublicUserEN userEN) {
        this.userEN = userEN;
    }

    private String titile;
    private Date postdate;
    private CommonPortEN startport;
    private CommonPortEN unloadport;
    private String price;
    private String linkman;
    private String tel;
    private String remark;
    TradeTypeEN tradeTypeEN;
    //货源
    private CommonGoodsunitEN type;
    CommonUnitEN unitEN;

    public CommonUnitEN getUnitEN() {
        return unitEN;
    }

    public void setUnitEN(CommonUnitEN unitEN) {
        this.unitEN = unitEN;
    }

    private String name;
    private String tons;
    private String packaging;
    //船
    TradeShiptypeEN tradeShiptypeEN;
    String shipname;
    String load;
    String route;

    String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public TradeShiptypeEN getTradeShiptypeEN() {
        return tradeShiptypeEN;
    }

    public void setTradeShiptypeEN(TradeShiptypeEN tradeShiptypeEN) {
        this.tradeShiptypeEN = tradeShiptypeEN;
    }

    public TradeTypeEN getTradeTypeEN() {
        return tradeTypeEN;
    }

    public void setTradeTypeEN(TradeTypeEN tradeTypeEN) {
        this.tradeTypeEN = tradeTypeEN;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPostdate() {
        return postdate;
    }

    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public CommonGoodsunitEN getType() {
        return type;
    }

    public void setType(CommonGoodsunitEN type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTons() {
        return tons;
    }

    public void setTons(String tons) {
        this.tons = tons;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public CommonPortEN getStartport() {
        return startport;
    }

    public void setStartport(CommonPortEN startport) {
        this.startport = startport;
    }

    public CommonPortEN getUnloadport() {
        return unloadport;
    }

    public void setUnloadport(CommonPortEN unloadport) {
        this.unloadport = unloadport;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeGoodssourceEN that = (TradeGoodssourceEN) o;

        if (id != that.id) return false;
        if (titile != null ? !titile.equals(that.titile) : that.titile != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tons != null ? !tons.equals(that.tons) : that.tons != null) return false;
        if (packaging != null ? !packaging.equals(that.packaging) : that.packaging != null) return false;
        if (startport != null ? !startport.equals(that.startport) : that.startport != null) return false;
        if (unloadport != null ? !unloadport.equals(that.unloadport) : that.unloadport != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (linkman != null ? !linkman.equals(that.linkman) : that.linkman != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (titile != null ? titile.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tons != null ? tons.hashCode() : 0);
        result = 31 * result + (packaging != null ? packaging.hashCode() : 0);
        result = 31 * result + (startport != null ? startport.hashCode() : 0);
        result = 31 * result + (unloadport != null ? unloadport.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (linkman != null ? linkman.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
