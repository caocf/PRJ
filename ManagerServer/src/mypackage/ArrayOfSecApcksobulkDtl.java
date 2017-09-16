
package mypackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSec_apcksobulk_dtl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSec_apcksobulk_dtl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sec_apcksobulk_dtl" type="{http://tempuri.org/}sec_apcksobulk_dtl" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSec_apcksobulk_dtl", propOrder = {
    "secApcksobulkDtl"
})
public class ArrayOfSecApcksobulkDtl implements Serializable {

    @XmlElement(name = "sec_apcksobulk_dtl", nillable = true)
    protected List<SecApcksobulkDtl> secApcksobulkDtl;

    /**
     * Gets the value of the secApcksobulkDtl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the secApcksobulkDtl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecApcksobulkDtl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SecApcksobulkDtl }
     * 
     * 
     */
    public List<SecApcksobulkDtl> getSecApcksobulkDtl() {
        if (secApcksobulkDtl == null) {
            secApcksobulkDtl = new ArrayList<SecApcksobulkDtl>();
        }
        return this.secApcksobulkDtl;
    }

}
