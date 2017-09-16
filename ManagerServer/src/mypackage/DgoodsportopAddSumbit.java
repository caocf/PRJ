
package mypackage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="dgoodsportop" type="{http://tempuri.org/}Se_DgoodSportop" minOccurs="0"/>
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
    "dgoodsportop"
})
@XmlRootElement(name = "DgoodsportopAddSumbit")
public class DgoodsportopAddSumbit {

    protected SeDgoodSportop dgoodsportop;

    /**
     * Gets the value of the dgoodsportop property.
     * 
     * @return
     *     possible object is
     *     {@link SeDgoodSportop }
     *     
     */
    public SeDgoodSportop getDgoodsportop() {
        return dgoodsportop;
    }

    /**
     * Sets the value of the dgoodsportop property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeDgoodSportop }
     *     
     */
    public void setDgoodsportop(SeDgoodSportop value) {
        this.dgoodsportop = value;
    }

}
