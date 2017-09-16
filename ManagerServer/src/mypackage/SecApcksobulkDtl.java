
package mypackage;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for sec_apcksobulk_dtl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sec_apcksobulk_dtl">
 *   &lt;complexContent>
 *     &lt;extension base="{http://tempuri.org/}EntityBase">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imdgcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nno" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="tweight" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="disport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loadpos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="def3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tdef4" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tdef5" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ndef6" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ndef7" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sec_apcksobulk_dtl", propOrder = {
    "id",
    "mid",
    "gname",
    "gtype",
    "imdgcode",
    "shipment",
    "nno",
    "tweight",
    "disport",
    "loadpos",
    "remark",
    "def1",
    "def2",
    "def3",
    "tdef4",
    "tdef5",
    "ndef6",
    "ndef7"
})
public class SecApcksobulkDtl
    extends EntityBase
{

    protected String id;
    protected String mid;
    protected String gname;
    protected String gtype;
    protected String imdgcode;
    protected String shipment;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal nno;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal tweight;
    protected String disport;
    protected String loadpos;
    protected String remark;
    protected String def1;
    protected String def2;
    protected String def3;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tdef4;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tdef5;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal ndef6;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal ndef7;

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
     * Gets the value of the mid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMid() {
        return mid;
    }

    /**
     * Sets the value of the mid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMid(String value) {
        this.mid = value;
    }

    /**
     * Gets the value of the gname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGname() {
        return gname;
    }

    /**
     * Sets the value of the gname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGname(String value) {
        this.gname = value;
    }

    /**
     * Gets the value of the gtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGtype() {
        return gtype;
    }

    /**
     * Sets the value of the gtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGtype(String value) {
        this.gtype = value;
    }

    /**
     * Gets the value of the imdgcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImdgcode() {
        return imdgcode;
    }

    /**
     * Sets the value of the imdgcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImdgcode(String value) {
        this.imdgcode = value;
    }

    /**
     * Gets the value of the shipment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipment() {
        return shipment;
    }

    /**
     * Sets the value of the shipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipment(String value) {
        this.shipment = value;
    }

    /**
     * Gets the value of the nno property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNno() {
        return nno;
    }

    /**
     * Sets the value of the nno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNno(BigDecimal value) {
        this.nno = value;
    }

    /**
     * Gets the value of the tweight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTweight() {
        return tweight;
    }

    /**
     * Sets the value of the tweight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTweight(BigDecimal value) {
        this.tweight = value;
    }

    /**
     * Gets the value of the disport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisport() {
        return disport;
    }

    /**
     * Sets the value of the disport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisport(String value) {
        this.disport = value;
    }

    /**
     * Gets the value of the loadpos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadpos() {
        return loadpos;
    }

    /**
     * Sets the value of the loadpos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadpos(String value) {
        this.loadpos = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
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
     * Gets the value of the tdef4 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTdef4() {
        return tdef4;
    }

    /**
     * Sets the value of the tdef4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTdef4(XMLGregorianCalendar value) {
        this.tdef4 = value;
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
     * Gets the value of the ndef6 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNdef6() {
        return ndef6;
    }

    /**
     * Sets the value of the ndef6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNdef6(BigDecimal value) {
        this.ndef6 = value;
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

}
