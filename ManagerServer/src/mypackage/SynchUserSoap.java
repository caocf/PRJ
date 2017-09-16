
package mypackage;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SynchUserSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SynchUserSoap {


    /**
     * 
     * @param dgoodsportop
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "DgoodsportopAddSumbit", action = "http://tempuri.org/DgoodsportopAddSumbit")
    @WebResult(name = "DgoodsportopAddSumbitResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "DgoodsportopAddSumbit", targetNamespace = "http://tempuri.org/", className = "mypackage.DgoodsportopAddSumbit")
    @ResponseWrapper(localName = "DgoodsportopAddSumbitResponse", targetNamespace = "http://tempuri.org/", className = "mypackage.DgoodsportopAddSumbitResponse")
    public String dgoodsportopAddSumbit(
        @WebParam(name = "dgoodsportop", targetNamespace = "http://tempuri.org/")
        SeDgoodSportop dgoodsportop);

    /**
     * 
     * @param list
     * @param apcksobulk
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "ApcksobulkAddSumbit", action = "http://tempuri.org/ApcksobulkAddSumbit")
    @WebResult(name = "ApcksobulkAddSumbitResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "ApcksobulkAddSumbit", targetNamespace = "http://tempuri.org/", className = "mypackage.ApcksobulkAddSumbit")
    @ResponseWrapper(localName = "ApcksobulkAddSumbitResponse", targetNamespace = "http://tempuri.org/", className = "mypackage.ApcksobulkAddSumbitResponse")
    public String apcksobulkAddSumbit(
        @WebParam(name = "apcksobulk", targetNamespace = "http://tempuri.org/")
        SecApcksobulk apcksobulk,
        @WebParam(name = "list", targetNamespace = "http://tempuri.org/")
        ArrayOfSecApcksobulkDtl list);

}
