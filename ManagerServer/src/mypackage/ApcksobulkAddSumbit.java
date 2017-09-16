
package mypackage;

import org.apache.xml.serialize.Serializer;
import org.hibernate.type.SerializableType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apcksobulk" type="{http://tempuri.org/}sec_apcksobulk" minOccurs="0"/>
 *         &lt;element name="list" type="{http://tempuri.org/}ArrayOfSec_apcksobulk_dtl" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "apcksobulk",
    "list"
})
@XmlRootElement(name = "ApcksobulkAddSumbit")
public class ApcksobulkAddSumbit implements Serializable
{

    protected SecApcksobulk apcksobulk;
    protected ArrayOfSecApcksobulkDtl list;

    /**
     * Gets the value of the apcksobulk property.
     * 
     * @return
     *     possible object is
     *     {@link SecApcksobulk }
     *     
     */
    public SecApcksobulk getApcksobulk() {
        return apcksobulk;
    }

    /**
     * Sets the value of the apcksobulk property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecApcksobulk }
     *     
     */
    public void setApcksobulk(SecApcksobulk value) {
        this.apcksobulk = value;
    }

    /**
     * Gets the value of the list property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSecApcksobulkDtl }
     *     
     */
    public ArrayOfSecApcksobulkDtl getList() {
        return list;
    }

    /**
     * Sets the value of the list property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSecApcksobulkDtl }
     *     
     */
    public void setList(ArrayOfSecApcksobulkDtl value) {
        this.list = value;
    }

}
