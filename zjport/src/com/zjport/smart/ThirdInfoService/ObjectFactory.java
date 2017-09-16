
package com.zjport.smart.ThirdInfoService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.visionagent.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAllCameraResponseReturn_QNAME = new QName("http://webservice.cms.hikvision.com", "return");
    private final static QName _GetDeviceStatusRequest_QNAME = new QName("http://webservice.cms.hikvision.com", "request");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.visionagent.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllRegionInfoForList }
     * 
     */
    public GetAllRegionInfoForList createGetAllRegionInfoForList() {
        return new GetAllRegionInfoForList();
    }

    /**
     * Create an instance of {@link UserOnlineHeartbeatResponse }
     * 
     */
    public UserOnlineHeartbeatResponse createUserOnlineHeartbeatResponse() {
        return new UserOnlineHeartbeatResponse();
    }

    /**
     * Create an instance of {@link GetAllControlCenterForList }
     * 
     */
    public GetAllControlCenterForList createGetAllControlCenterForList() {
        return new GetAllControlCenterForList();
    }

    /**
     * Create an instance of {@link GetPreviewParam }
     * 
     */
    public GetPreviewParam createGetPreviewParam() {
        return new GetPreviewParam();
    }

    /**
     * Create an instance of {@link GetPlaybackParamResponse }
     * 
     */
    public GetPlaybackParamResponse createGetPlaybackParamResponse() {
        return new GetPlaybackParamResponse();
    }

    /**
     * Create an instance of {@link GetPlaybackParam }
     * 
     */
    public GetPlaybackParam createGetPlaybackParam() {
        return new GetPlaybackParam();
    }

    /**
     * Create an instance of {@link GetDeviceStatus }
     * 
     */
    public GetDeviceStatus createGetDeviceStatus() {
        return new GetDeviceStatus();
    }

    /**
     * Create an instance of {@link GetDeviceStatusResponse }
     * 
     */
    public GetDeviceStatusResponse createGetDeviceStatusResponse() {
        return new GetDeviceStatusResponse();
    }

    /**
     * Create an instance of {@link GetCameraStatus }
     * 
     */
    public GetCameraStatus createGetCameraStatus() {
        return new GetCameraStatus();
    }

    /**
     * Create an instance of {@link GetAllDevice }
     * 
     */
    public GetAllDevice createGetAllDevice() {
        return new GetAllDevice();
    }

    /**
     * Create an instance of {@link GetAllCamera }
     * 
     */
    public GetAllCamera createGetAllCamera() {
        return new GetAllCamera();
    }

    /**
     * Create an instance of {@link GetCameraStatusResponse }
     * 
     */
    public GetCameraStatusResponse createGetCameraStatusResponse() {
        return new GetCameraStatusResponse();
    }

    /**
     * Create an instance of {@link GetAllDeviceResponse }
     * 
     */
    public GetAllDeviceResponse createGetAllDeviceResponse() {
        return new GetAllDeviceResponse();
    }

    /**
     * Create an instance of {@link UserOnlineHeartbeat }
     * 
     */
    public UserOnlineHeartbeat createUserOnlineHeartbeat() {
        return new UserOnlineHeartbeat();
    }

    /**
     * Create an instance of {@link GetPreviewParamResponse }
     * 
     */
    public GetPreviewParamResponse createGetPreviewParamResponse() {
        return new GetPreviewParamResponse();
    }

    /**
     * Create an instance of {@link UserLogin }
     * 
     */
    public UserLogin createUserLogin() {
        return new UserLogin();
    }

    /**
     * Create an instance of {@link GetAllControlCenterForListResponse }
     * 
     */
    public GetAllControlCenterForListResponse createGetAllControlCenterForListResponse() {
        return new GetAllControlCenterForListResponse();
    }

    /**
     * Create an instance of {@link UserLoginResponse }
     * 
     */
    public UserLoginResponse createUserLoginResponse() {
        return new UserLoginResponse();
    }

    /**
     * Create an instance of {@link GetAllRegionInfoForListResponse }
     * 
     */
    public GetAllRegionInfoForListResponse createGetAllRegionInfoForListResponse() {
        return new GetAllRegionInfoForListResponse();
    }

    /**
     * Create an instance of {@link UserLogoutResponse }
     * 
     */
    public UserLogoutResponse createUserLogoutResponse() {
        return new UserLogoutResponse();
    }

    /**
     * Create an instance of {@link GetCameraInfoByDeviceInfo }
     * 
     */
    public GetCameraInfoByDeviceInfo createGetCameraInfoByDeviceInfo() {
        return new GetCameraInfoByDeviceInfo();
    }

    /**
     * Create an instance of {@link UserLogout }
     * 
     */
    public UserLogout createUserLogout() {
        return new UserLogout();
    }

    /**
     * Create an instance of {@link GetCameraInfoByDeviceInfoResponse }
     * 
     */
    public GetCameraInfoByDeviceInfoResponse createGetCameraInfoByDeviceInfoResponse() {
        return new GetCameraInfoByDeviceInfoResponse();
    }

    /**
     * Create an instance of {@link GetAllCameraResponse }
     * 
     */
    public GetAllCameraResponse createGetAllCameraResponse() {
        return new GetAllCameraResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetAllCameraResponse.class)
    public JAXBElement<String> createGetAllCameraResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetAllCameraResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = UserLoginResponse.class)
    public JAXBElement<String> createUserLoginResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, UserLoginResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetCameraInfoByDeviceInfoResponse.class)
    public JAXBElement<String> createGetCameraInfoByDeviceInfoResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetCameraInfoByDeviceInfoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetDeviceStatus.class)
    public JAXBElement<String> createGetDeviceStatusRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetDeviceStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetPreviewParamResponse.class)
    public JAXBElement<String> createGetPreviewParamResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetPreviewParamResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetPlaybackParamResponse.class)
    public JAXBElement<String> createGetPlaybackParamResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetPlaybackParamResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetPlaybackParam.class)
    public JAXBElement<String> createGetPlaybackParamRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetPlaybackParam.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetAllControlCenterForListResponse.class)
    public JAXBElement<String> createGetAllControlCenterForListResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetAllControlCenterForListResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetAllRegionInfoForListResponse.class)
    public JAXBElement<String> createGetAllRegionInfoForListResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetAllRegionInfoForListResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetCameraInfoByDeviceInfo.class)
    public JAXBElement<String> createGetCameraInfoByDeviceInfoRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetCameraInfoByDeviceInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetAllControlCenterForList.class)
    public JAXBElement<String> createGetAllControlCenterForListRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetAllControlCenterForList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetAllCamera.class)
    public JAXBElement<String> createGetAllCameraRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetAllCamera.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = UserLogin.class)
    public JAXBElement<String> createUserLoginRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, UserLogin.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetAllDevice.class)
    public JAXBElement<String> createGetAllDeviceRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetAllDevice.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = UserOnlineHeartbeatResponse.class)
    public JAXBElement<String> createUserOnlineHeartbeatResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, UserOnlineHeartbeatResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetPreviewParam.class)
    public JAXBElement<String> createGetPreviewParamRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetPreviewParam.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetDeviceStatusResponse.class)
    public JAXBElement<String> createGetDeviceStatusResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetDeviceStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = UserLogout.class)
    public JAXBElement<String> createUserLogoutRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, UserLogout.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetAllRegionInfoForList.class)
    public JAXBElement<String> createGetAllRegionInfoForListRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetAllRegionInfoForList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = UserLogoutResponse.class)
    public JAXBElement<String> createUserLogoutResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, UserLogoutResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetAllDeviceResponse.class)
    public JAXBElement<String> createGetAllDeviceResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetAllDeviceResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "return", scope = GetCameraStatusResponse.class)
    public JAXBElement<String> createGetCameraStatusResponseReturn(String value) {
        return new JAXBElement<String>(_GetAllCameraResponseReturn_QNAME, String.class, GetCameraStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = UserOnlineHeartbeat.class)
    public JAXBElement<String> createUserOnlineHeartbeatRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, UserOnlineHeartbeat.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cms.hikvision.com", name = "request", scope = GetCameraStatus.class)
    public JAXBElement<String> createGetCameraStatusRequest(String value) {
        return new JAXBElement<String>(_GetDeviceStatusRequest_QNAME, String.class, GetCameraStatus.class, value);
    }

}
