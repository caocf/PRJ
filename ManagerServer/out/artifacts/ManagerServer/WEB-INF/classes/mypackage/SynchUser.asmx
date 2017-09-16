<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://tempuri.org/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://tempuri.org/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://tempuri.org/">
      <s:element name="DgoodsportopAddSumbit">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="dgoodsportop" type="tns:Se_DgoodSportop" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="Se_DgoodSportop">
        <s:complexContent mixed="false">
          <s:extension base="tns:EntityBase">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="id" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="entid" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="contact" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="contel" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="appjoncon" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="jobprincipal" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="pritel" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="appjobadd" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="icoptime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="esoptime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="namecarrier" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Voyage" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="vessel" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="shipdwt" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="lunport" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="adtime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="cname" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="unid" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="quantity" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="pcpro" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="package" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="appstatus" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="appcon" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="apptime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="appemp" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="endstatus" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="encon" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="endtime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="endemp" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="spreins" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="sapre" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="repofficer" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="reppath" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="filltime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="fillemp" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="portsig" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="dtype" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def1" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def2" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def3" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def4" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def5" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="tdef6" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="tdef7" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="tdef8" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="ndef9" nillable="true" type="s:decimal" />
              <s:element minOccurs="1" maxOccurs="1" name="ndef10" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="DeclNO" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Workers" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="DecTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="WorkAmount" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="IsOutTime" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="EntityBase" abstract="true">
        <s:complexContent mixed="false">
          <s:extension base="tns:QueryEntityBase" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="QueryEntityBase" abstract="true" />
      <s:element name="DgoodsportopAddSumbitResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="DgoodsportopAddSumbitResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ApcksobulkAddSumbit">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="apcksobulk" type="tns:sec_apcksobulk" />
            <s:element minOccurs="0" maxOccurs="1" name="list" type="tns:ArrayOfSec_apcksobulk_dtl" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="sec_apcksobulk">
        <s:complexContent mixed="false">
          <s:extension base="tns:EntityBase">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="id" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="sname" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="voyage" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="sinout" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="depport" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="arrtime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="nationality" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="operators" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="bert" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="ltime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="repofficer" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="recordno" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="agentimg" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="dtime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="autvisa" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="empremark" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="EmPhone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="EmFax" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="EmEmail" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="fillemp" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="filltime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="appemp" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="apptime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="appstatus" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="appcon" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="enstatus" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="endemp" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="endcon" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="dtype" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def1" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def2" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def3" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def4" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="tdef5" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="tdef6" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="ndef7" nillable="true" type="s:decimal" />
              <s:element minOccurs="1" maxOccurs="1" name="ndef8" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="DeclNO" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="entid" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="IsOutTime" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfSec_apcksobulk_dtl">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="sec_apcksobulk_dtl" nillable="true" type="tns:sec_apcksobulk_dtl" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="sec_apcksobulk_dtl">
        <s:complexContent mixed="false">
          <s:extension base="tns:EntityBase">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="id" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="mid" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="gname" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="gtype" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="imdgcode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="shipment" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="nno" nillable="true" type="s:decimal" />
              <s:element minOccurs="1" maxOccurs="1" name="tweight" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="disport" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="loadpos" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="remark" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def1" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def2" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="def3" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="tdef4" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="tdef5" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="ndef6" nillable="true" type="s:decimal" />
              <s:element minOccurs="1" maxOccurs="1" name="ndef7" nillable="true" type="s:decimal" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="ApcksobulkAddSumbitResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ApcksobulkAddSumbitResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="DgoodsportopAddSumbitSoapIn">
    <wsdl:part name="parameters" element="tns:DgoodsportopAddSumbit" />
  </wsdl:message>
  <wsdl:message name="DgoodsportopAddSumbitSoapOut">
    <wsdl:part name="parameters" element="tns:DgoodsportopAddSumbitResponse" />
  </wsdl:message>
  <wsdl:message name="ApcksobulkAddSumbitSoapIn">
    <wsdl:part name="parameters" element="tns:ApcksobulkAddSumbit" />
  </wsdl:message>
  <wsdl:message name="ApcksobulkAddSumbitSoapOut">
    <wsdl:part name="parameters" element="tns:ApcksobulkAddSumbitResponse" />
  </wsdl:message>
  <wsdl:portType name="SynchUserSoap">
    <wsdl:operation name="DgoodsportopAddSumbit">
      <wsdl:input message="tns:DgoodsportopAddSumbitSoapIn" />
      <wsdl:output message="tns:DgoodsportopAddSumbitSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ApcksobulkAddSumbit">
      <wsdl:input message="tns:ApcksobulkAddSumbitSoapIn" />
      <wsdl:output message="tns:ApcksobulkAddSumbitSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:portType name="SynchUserHttpGet" />
  <wsdl:portType name="SynchUserHttpPost" />
  <wsdl:binding name="SynchUserSoap" type="tns:SynchUserSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="DgoodsportopAddSumbit">
      <soap:operation soapAction="http://tempuri.org/DgoodsportopAddSumbit" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ApcksobulkAddSumbit">
      <soap:operation soapAction="http://tempuri.org/ApcksobulkAddSumbit" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="SynchUserSoap12" type="tns:SynchUserSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="DgoodsportopAddSumbit">
      <soap12:operation soapAction="http://tempuri.org/DgoodsportopAddSumbit" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ApcksobulkAddSumbit">
      <soap12:operation soapAction="http://tempuri.org/ApcksobulkAddSumbit" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="SynchUserHttpGet" type="tns:SynchUserHttpGet">
    <http:binding verb="GET" />
  </wsdl:binding>
  <wsdl:binding name="SynchUserHttpPost" type="tns:SynchUserHttpPost">
    <http:binding verb="POST" />
  </wsdl:binding>
  <wsdl:service name="SynchUser">
    <wsdl:port name="SynchUserSoap" binding="tns:SynchUserSoap">
      <soap:address location="http://101.68.92.118:88/services/SynchUser.asmx" />
    </wsdl:port>
    <wsdl:port name="SynchUserSoap12" binding="tns:SynchUserSoap12">
      <soap12:address location="http://101.68.92.118:88/services/SynchUser.asmx" />
    </wsdl:port>
    <wsdl:port name="SynchUserHttpGet" binding="tns:SynchUserHttpGet">
      <http:address location="http://101.68.92.118:88/services/SynchUser.asmx" />
    </wsdl:port>
    <wsdl:port name="SynchUserHttpPost" binding="tns:SynchUserHttpPost">
      <http:address location="http://101.68.92.118:88/services/SynchUser.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>