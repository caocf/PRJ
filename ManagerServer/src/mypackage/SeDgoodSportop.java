
package mypackage;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Se_DgoodSportop complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Se_DgoodSportop">
 *   &lt;complexContent>
 *     &lt;extension base="{http://tempuri.org/}EntityBase">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appjoncon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jobprincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pritel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appjobadd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="icoptime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="esoptime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="namecarrier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Voyage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vessel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipdwt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lunport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adtime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="cname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="pcpro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="package" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apptime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="appemp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="encon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endtime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="endemp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spreins" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sapre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="repofficer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reppath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filltime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fillemp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="portsig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tdef6" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tdef7" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tdef8" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ndef9" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ndef10" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DeclNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Workers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DecTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="WorkAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsOutTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Se_DgoodSportop", propOrder = {
    "id",
    "entid",
    "contact",
    "contel",
    "appjoncon",
    "jobprincipal",
    "pritel",
    "appjobadd",
    "icoptime",
    "esoptime",
    "namecarrier",
    "voyage",
    "vessel",
    "shipdwt",
    "lunport",
    "adtime",
    "cname",
    "unid",
    "quantity",
    "pcpro",
    "_package",
    "appstatus",
    "appcon",
    "apptime",
    "appemp",
    "endstatus",
    "encon",
    "endtime",
    "endemp",
    "spreins",
    "sapre",
    "repofficer",
    "reppath",
    "filltime",
    "fillemp",
    "portsig",
    "dtype",
    "def1",
    "def2",
    "def3",
    "def4",
    "def5",
    "tdef6",
    "tdef7",
    "tdef8",
    "ndef9",
    "ndef10",
    "declNO",
    "workers",
    "decTime",
    "workAmount",
    "isOutTime"
})
public class SeDgoodSportop
    extends EntityBase
{

    protected String id;
    protected String entid;
    protected String contact;
    protected String contel;
    protected String appjoncon;
    protected String jobprincipal;
    protected String pritel;
    protected String appjobadd;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar icoptime;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar esoptime;
    protected String namecarrier;
    @XmlElement(name = "Voyage")
    protected String voyage;
    protected String vessel;
    protected String shipdwt;
    protected String lunport;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar adtime;
    protected String cname;
    protected String unid;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal quantity;
    protected String pcpro;
    @XmlElement(name = "package")
    protected String _package;
    protected String appstatus;
    protected String appcon;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar apptime;
    protected String appemp;
    protected String endstatus;
    protected String encon;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endtime;
    protected String endemp;
    protected String spreins;
    protected String sapre;
    protected String repofficer;
    protected String reppath;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar filltime;
    protected String fillemp;
    protected String portsig;
    protected String dtype;
    protected String def1;
    protected String def2;
    protected String def3;
    protected String def4;
    protected String def5;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tdef6;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tdef7;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tdef8;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal ndef9;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal ndef10;
    @XmlElement(name = "DeclNO")
    protected String declNO;
    @XmlElement(name = "Workers")
    protected String workers;
    @XmlElement(name = "DecTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar decTime;
    @XmlElement(name = "WorkAmount")
    protected String workAmount;
    @XmlElement(name = "IsOutTime")
    protected String isOutTime;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the entid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntid() {
        return entid;
    }

    /**
     * Sets the value of the entid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntid(String value) {
        this.entid = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * Gets the value of the contel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContel() {
        return contel;
    }

    /**
     * Sets the value of the contel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContel(String value) {
        this.contel = value;
    }

    /**
     * Gets the value of the appjoncon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppjoncon() {
        return appjoncon;
    }

    /**
     * Sets the value of the appjoncon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppjoncon(String value) {
        this.appjoncon = value;
    }

    /**
     * Gets the value of the jobprincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobprincipal() {
        return jobprincipal;
    }

    /**
     * Sets the value of the jobprincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobprincipal(String value) {
        this.jobprincipal = value;
    }

    /**
     * Gets the value of the pritel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPritel() {
        return pritel;
    }

    /**
     * Sets the value of the pritel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPritel(String value) {
        this.pritel = value;
    }

    /**
     * Gets the value of the appjobadd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppjobadd() {
        return appjobadd;
    }

    /**
     * Sets the value of the appjobadd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppjobadd(String value) {
        this.appjobadd = value;
    }

    /**
     * Gets the value of the icoptime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIcoptime() {
        return icoptime;
    }

    /**
     * Sets the value of the icoptime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIcoptime(XMLGregorianCalendar value) {
        this.icoptime = value;
    }

    /**
     * Gets the value of the esoptime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsoptime() {
        return esoptime;
    }

    /**
     * Sets the value of the esoptime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsoptime(XMLGregorianCalendar value) {
        this.esoptime = value;
    }

    /**
     * Gets the value of the namecarrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamecarrier() {
        return namecarrier;
    }

    /**
     * Sets the value of the namecarrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamecarrier(String value) {
        this.namecarrier = value;
    }

    /**
     * Gets the value of the voyage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoyage() {
        return voyage;
    }

    /**
     * Sets the value of the voyage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoyage(String value) {
        this.voyage = value;
    }

    /**
     * Gets the value of the vessel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVessel() {
        return vessel;
    }

    /**
     * Sets the value of the vessel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVessel(String value) {
        this.vessel = value;
    }

    /**
     * Gets the value of the shipdwt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipdwt() {
        return shipdwt;
    }

    /**
     * Sets the value of the shipdwt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipdwt(String value) {
        this.shipdwt = value;
    }

    /**
     * Gets the value of the lunport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLunport() {
        return lunport;
    }

    /**
     * Sets the value of the lunport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLunport(String value) {
        this.lunport = value;
    }

    /**
     * Gets the value of the adtime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAdtime() {
        return adtime;
    }

    /**
     * Sets the value of the adtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAdtime(XMLGregorianCalendar value) {
        this.adtime = value;
    }

    /**
     * Gets the value of the cname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCname() {
        return cname;
    }

    /**
     * Sets the value of the cname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCname(String value) {
        this.cname = value;
    }

    /**
     * Gets the value of the unid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnid() {
        return unid;
    }

    /**
     * Sets the value of the unid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnid(String value) {
        this.unid = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantity(BigDecimal value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the pcpro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPcpro() {
        return pcpro;
    }

    /**
     * Sets the value of the pcpro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPcpro(String value) {
        this.pcpro = value;
    }

    /**
     * Gets the value of the package property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackage() {
        return _package;
    }

    /**
     * Sets the value of the package property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackage(String value) {
        this._package = value;
    }

    /**
     * Gets the value of the appstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppstatus() {
        return appstatus;
    }

    /**
     * Sets the value of the appstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppstatus(String value) {
        this.appstatus = value;
    }

    /**
     * Gets the value of the appcon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppcon() {
        return appcon;
    }

    /**
     * Sets the value of the appcon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppcon(String value) {
        this.appcon = value;
    }

    /**
     * Gets the value of the apptime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApptime() {
        return apptime;
    }

    /**
     * Sets the value of the apptime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApptime(XMLGregorianCalendar value) {
        this.apptime = value;
    }

    /**
     * Gets the value of the appemp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppemp() {
        return appemp;
    }

    /**
     * Sets the value of the appemp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppemp(String value) {
        this.appemp = value;
    }

    /**
     * Gets the value of the endstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndstatus() {
        return endstatus;
    }

    /**
     * Sets the value of the endstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndstatus(String value) {
        this.endstatus = value;
    }

    /**
     * Gets the value of the encon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncon() {
        return encon;
    }

    /**
     * Sets the value of the encon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncon(String value) {
        this.encon = value;
    }

    /**
     * Gets the value of the endtime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndtime() {
        return endtime;
    }

    /**
     * Sets the value of the endtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndtime(XMLGregorianCalendar value) {
        this.endtime = value;
    }

    /**
     * Gets the value of the endemp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndemp() {
        return endemp;
    }

    /**
     * Sets the value of the endemp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndemp(String value) {
        this.endemp = value;
    }

    /**
     * Gets the value of the spreins property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpreins() {
        return spreins;
    }

    /**
     * Sets the value of the spreins property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpreins(String value) {
        this.spreins = value;
    }

    /**
     * Gets the value of the sapre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSapre() {
        return sapre;
    }

    /**
     * Sets the value of the sapre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSapre(String value) {
        this.sapre = value;
    }

    /**
     * Gets the value of the repofficer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepofficer() {
        return repofficer;
    }

    /**
     * Sets the value of the repofficer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepofficer(String value) {
        this.repofficer = value;
    }

    /**
     * Gets the value of the reppath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReppath() {
        return reppath;
    }

    /**
     * Sets the value of the reppath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReppath(String value) {
        this.reppath = value;
    }

    /**
     * Gets the value of the filltime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFilltime() {
        return filltime;
    }

    /**
     * Sets the value of the filltime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFilltime(XMLGregorianCalendar value) {
        this.filltime = value;
    }

    /**
     * Gets the value of the fillemp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFillemp() {
        return fillemp;
    }

    /**
     * Sets the value of the fillemp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFillemp(String value) {
        this.fillemp = value;
    }

    /**
     * Gets the value of the portsig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortsig() {
        return portsig;
    }

    /**
     * Sets the value of the portsig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortsig(String value) {
        this.portsig = value;
    }

    /**
     * Gets the value of the dtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtype() {
        return dtype;
    }

    /**
     * Sets the value of the dtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtype(String value) {
        this.dtype = value;
    }

    /**
     * Gets the value of the def1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDef1() {
        return def1;
    }

    /**
     * Sets the value of the def1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDef1(String value) {
        this.def1 = value;
    }

    /**
     * Gets the value of the def2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDef2() {
        return def2;
    }

    /**
     * Sets the value of the def2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDef2(String value) {
        this.def2 = value;
    }

    /**
     * Gets the value of the def3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDef3() {
        return def3;
    }

    /**
     * Sets the value of the def3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDef3(String value) {
        this.def3 = value;
    }

    /**
     * Gets the value of the def4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDef4() {
        return def4;
    }

    /**
     * Sets the value of the def4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDef4(String value) {
        this.def4 = value;
    }

    /**
     * Gets the value of the def5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDef5() {
        return def5;
    }

    /**
     * Sets the value of the def5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDef5(String value) {
        this.def5 = value;
    }

    /**
     * Gets the value of the tdef6 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTdef6() {
        return tdef6;
    }

    /**
     * Sets the value of the tdef6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTdef6(XMLGregorianCalendar value) {
        this.tdef6 = value;
    }

    /**
     * Gets the value of the tdef7 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTdef7() {
        return tdef7;
    }

    /**
     * Sets the value of the tdef7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTdef7(XMLGregorianCalendar value) {
        this.tdef7 = value;
    }

    /**
     * Gets the value of the tdef8 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTdef8() {
        return tdef8;
    }

    /**
     * Sets the value of the tdef8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTdef8(XMLGregorianCalendar value) {
        this.tdef8 = value;
    }

    /**
     * Gets the value of the ndef9 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNdef9() {
        return ndef9;
    }

    /**
     * Sets the value of the ndef9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNdef9(BigDecimal value) {
        this.ndef9 = value;
    }

    /**
     * Gets the value of the ndef10 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNdef10() {
        return ndef10;
    }

    /**
     * Sets the value of the ndef10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNdef10(BigDecimal value) {
        this.ndef10 = value;
    }

    /**
     * Gets the value of the declNO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeclNO() {
        return declNO;
    }

    /**
     * Sets the value of the declNO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeclNO(String value) {
        this.declNO = value;
    }

    /**
     * Gets the value of the workers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkers() {
        return workers;
    }

    /**
     * Sets the value of the workers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkers(String value) {
        this.workers = value;
    }

    /**
     * Gets the value of the decTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDecTime() {
        return decTime;
    }

    /**
     * Sets the value of the decTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDecTime(XMLGregorianCalendar value) {
        this.decTime = value;
    }

    /**
     * Gets the value of the workAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkAmount() {
        return workAmount;
    }

    /**
     * Sets the value of the workAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkAmount(String value) {
        this.workAmount = value;
    }

    /**
     * Gets the value of the isOutTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsOutTime() {
        return isOutTime;
    }

    /**
     * Sets the value of the isOutTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsOutTime(String value) {
        this.isOutTime = value;
    }

}
