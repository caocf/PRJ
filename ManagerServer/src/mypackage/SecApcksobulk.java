
package mypackage;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for sec_apcksobulk complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sec_apcksobulk">
 *   &lt;complexContent>
 *     &lt;extension base="{http://tempuri.org/}EntityBase">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="voyage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sinout" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="depport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arrtime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="nationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operators" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ltime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="repofficer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recordno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentimg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="autvisa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="empremark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fillemp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filltime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="appemp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apptime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="appstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endemp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tdef5" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tdef6" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ndef7" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ndef8" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DeclNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "sec_apcksobulk", propOrder = {
    "id",
    "sname",
    "voyage",
    "sinout",
    "depport",
    "arrtime",
    "nationality",
    "operators",
    "bert",
    "ltime",
    "repofficer",
    "recordno",
    "agentimg",
    "dtime",
    "autvisa",
    "empremark",
    "emPhone",
    "emFax",
    "emEmail",
    "fillemp",
    "filltime",
    "appemp",
    "apptime",
    "appstatus",
    "appcon",
    "enstatus",
    "endemp",
    "endcon",
    "dtype",
    "def1",
    "def2",
    "def3",
    "def4",
    "tdef5",
    "tdef6",
    "ndef7",
    "ndef8",
    "declNO",
    "entid",
    "isOutTime"
})
public class SecApcksobulk
    extends EntityBase implements Serializable
{

    protected String id;
    protected String sname;
    protected String voyage;
    protected String sinout;
    protected String depport;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar arrtime;
    protected String nationality;
    protected String operators;
    protected String bert;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ltime;
    protected String repofficer;
    protected String recordno;
    protected String agentimg;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtime;
    protected String autvisa;
    protected String empremark;
    @XmlElement(name = "EmPhone")
    protected String emPhone;
    @XmlElement(name = "EmFax")
    protected String emFax;
    @XmlElement(name = "EmEmail")
    protected String emEmail;
    protected String fillemp;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar filltime;
    protected String appemp;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar apptime;
    protected String appstatus;
    protected String appcon;
    protected String enstatus;
    protected String endemp;
    protected String endcon;
    protected String dtype;
    protected String def1;
    protected String def2;
    protected String def3;
    protected String def4;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tdef5;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tdef6;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal ndef7;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal ndef8;
    @XmlElement(name = "DeclNO")
    protected String declNO;
    protected String entid;
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
     * Gets the value of the sname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSname() {
        return sname;
    }

    /**
     * Sets the value of the sname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSname(String value) {
        this.sname = value;
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
     * Gets the value of the sinout property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSinout() {
        return sinout;
    }

    /**
     * Sets the value of the sinout property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSinout(String value) {
        this.sinout = value;
    }

    /**
     * Gets the value of the depport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepport() {
        return depport;
    }

    /**
     * Sets the value of the depport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepport(String value) {
        this.depport = value;
    }

    /**
     * Gets the value of the arrtime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArrtime() {
        return arrtime;
    }

    /**
     * Sets the value of the arrtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrtime(XMLGregorianCalendar value) {
        this.arrtime = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the operators property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperators() {
        return operators;
    }

    /**
     * Sets the value of the operators property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperators(String value) {
        this.operators = value;
    }

    /**
     * Gets the value of the bert property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBert() {
        return bert;
    }

    /**
     * Sets the value of the bert property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBert(String value) {
        this.bert = value;
    }

    /**
     * Gets the value of the ltime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLtime() {
        return ltime;
    }

    /**
     * Sets the value of the ltime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLtime(XMLGregorianCalendar value) {
        this.ltime = value;
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
     * Gets the value of the recordno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordno() {
        return recordno;
    }

    /**
     * Sets the value of the recordno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordno(String value) {
        this.recordno = value;
    }

    /**
     * Gets the value of the agentimg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentimg() {
        return agentimg;
    }

    /**
     * Sets the value of the agentimg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentimg(String value) {
        this.agentimg = value;
    }

    /**
     * Gets the value of the dtime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtime() {
        return dtime;
    }

    /**
     * Sets the value of the dtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtime(XMLGregorianCalendar value) {
        this.dtime = value;
    }

    /**
     * Gets the value of the autvisa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutvisa() {
        return autvisa;
    }

    /**
     * Sets the value of the autvisa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutvisa(String value) {
        this.autvisa = value;
    }

    /**
     * Gets the value of the empremark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmpremark() {
        return empremark;
    }

    /**
     * Sets the value of the empremark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmpremark(String value) {
        this.empremark = value;
    }

    /**
     * Gets the value of the emPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmPhone() {
        return emPhone;
    }

    /**
     * Sets the value of the emPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmPhone(String value) {
        this.emPhone = value;
    }

    /**
     * Gets the value of the emFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmFax() {
        return emFax;
    }

    /**
     * Sets the value of the emFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmFax(String value) {
        this.emFax = value;
    }

    /**
     * Gets the value of the emEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmEmail() {
        return emEmail;
    }

    /**
     * Sets the value of the emEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmEmail(String value) {
        this.emEmail = value;
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
     * Gets the value of the enstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnstatus() {
        return enstatus;
    }

    /**
     * Sets the value of the enstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnstatus(String value) {
        this.enstatus = value;
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
     * Gets the value of the endcon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndcon() {
        return endcon;
    }

    /**
     * Sets the value of the endcon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndcon(String value) {
        this.endcon = value;
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
     * Gets the value of the tdef5 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTdef5() {
        return tdef5;
    }

    /**
     * Sets the value of the tdef5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTdef5(XMLGregorianCalendar value) {
        this.tdef5 = value;
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
     * Gets the value of the ndef7 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNdef7() {
        return ndef7;
    }

    /**
     * Sets the value of the ndef7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNdef7(BigDecimal value) {
        this.ndef7 = value;
    }

    /**
     * Gets the value of the ndef8 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNdef8() {
        return ndef8;
    }

    /**
     * Sets the value of the ndef8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNdef8(BigDecimal value) {
        this.ndef8 = value;
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
