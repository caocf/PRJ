//
//  Taxi.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/6/25.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class Taxi
{
    var sbid:String
    var jd:Int
    var wd:Int
    var distance:Double
    
    init(sbid:String,jd:Int,wd:Int,distance:Double)
    {
        self.sbid = sbid
        self.jd = jd
        self.wd = wd
        self.distance = distance
    }
    
    class func formatTaxiJson(taxiJson:JSON) -> Taxi
    {
        let sbid = taxiJson["sbid"].stringValue
        let jd = taxiJson["jd"].intValue
        let wd = taxiJson["wd"].intValue
        let distance = taxiJson["distance"].doubleValue
        
        let taxi = Taxi(sbid: sbid, jd: jd, wd: wd, distance: distance)
        return taxi
    }
    
    class func initTaxi(updateVc:TaxiDelegate,radius:Int,lan:Double,lon:Double,num:Int)
    {
        let actionName = "searchTaxtByLocationCircle"
        
        var params = [String]()
        params.append("radius=\(radius)")
        params.append("lan=\(lan)")
        params.append("lon=\(lon)")
        params.append("num=\(num)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var taxiList = [Taxi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        let status = jsonRet["message"]["status"].intValue
                        let msg = jsonRet["message"]["msg"].stringValue
                        if (status == 1)
                        {
                            let jsonList = jsonRet["results"]
                            
                            for listJson in jsonList
                            {
                                let taxiTemp = Taxi.formatTaxiJson(listJson.1)
                                taxiList.append(taxiTemp)
                            }
                            
                            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                                updateVc.updateTaxi(status, msg: msg, taxi: taxiList)
                            })
                            
                        }
                        else
                        {
                            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                                updateVc.updateTaxi(status, msg: msg, taxi: taxiList)
                            })
                            
                            
                        }
                        
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }

}

class TaxiPhone
{
    var identify:String
    var id:Int
    var company:String
    var phone:String
    
    init(identify:String,id:Int,company:String,phone:String)
    {
        self.id = id
        self.identify = identify
        self.company = company
        self.phone = phone
    }
    
    class func formatTaxiJson(taxiJson:JSON) -> TaxiPhone
    {
        let id = taxiJson["id"].intValue
        let identify = taxiJson["identify"].stringValue
        let company = taxiJson["company"].stringValue
        let phone = taxiJson["phone"].stringValue
        
        let taxiPhone = TaxiPhone(identify: identify, id: id, company: company, phone: phone)
        return taxiPhone
    }
    
    class func initTaxi(updateVc:TaxiDelegate,taxiName:String)
    {
        let actionName = "queryTaxiPhoneById"
        
        var params = [String]()
        params.append("taxiName=\(taxiName)")
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(Tools.formatParamValue(params))")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getPostHttpRequest(retActionUrl, httpBody: httpBody)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var taxiPhoneList = [TaxiPhone]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        let status = jsonRet["message"]["status"].intValue
                        let msg = jsonRet["message"]["msg"].stringValue
                        if (status == 1)
                        {
                            let jsonList = jsonRet["results"]
                            
                            for listJson in jsonList
                            {
                                let taxiTemp = TaxiPhone.formatTaxiJson(listJson.1)
                                taxiPhoneList.append(taxiTemp)
                            }
                            
                            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                                updateVc.updateTaxiPhone(status, msg: msg, taxiPhone: taxiPhoneList)
                            })
                            
                        }
                        else
                        {
                            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                                updateVc.updateTaxiPhone(status, msg: msg, taxiPhone: taxiPhoneList)
                            })
                            
                            
                        }
                        
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
}

class TaxiDetail
{
    var cphm:String
    var cp:String
    var yhmc:String
    var rllx:String
    
    init(cphm:String,cp:String,yhmc:String,rllx:String)
    {
        self.cphm = cphm
        self.cp = cp
        self.yhmc = yhmc
        self.rllx = rllx
    }
    
    class func formatTaxiJson(taxiJson:JSON) -> TaxiDetail
    {
        let cphm = taxiJson["cphm"].stringValue
        let cp = taxiJson["cp"].stringValue
        let yhmc = taxiJson["yhmc"].stringValue
        let rllx = taxiJson["rllx"].stringValue
        
        let taxiDetail = TaxiDetail(cphm: cphm, cp: cp, yhmc: yhmc, rllx: rllx)
        return taxiDetail
    }
    
    class func initTaxi(updateVc:TaxiDelegate,taxiName:String)
    {
        let actionName = "searchTaxiDetailByName"
        
        var params = [String]()
        params.append("taxiName=\(taxiName)")
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(Tools.formatParamValue(params))")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getPostHttpRequest(retActionUrl, httpBody: httpBody)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var taxiDetailList = [TaxiDetail]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        let status = jsonRet["message"]["status"].intValue
                        let msg = jsonRet["message"]["msg"].stringValue
                        if (status == 1)
                        {
                            let jsonList = jsonRet["results"]
                            
                            for listJson in jsonList
                            {
                                let taxiTemp = TaxiDetail.formatTaxiJson(listJson.1)
                                taxiDetailList.append(taxiTemp)
                            }
                            
                            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                                updateVc.updateTaxiDetail(status, msg: msg, taxtDetail: taxiDetailList)
                            })
                            
                        }
                        else
                        {
                            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                                updateVc.updateTaxiDetail(status, msg: msg, taxtDetail: taxiDetailList)
                            })
                        }
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
}

protocol TaxiDelegate
{
    func updateTaxiDetail(status:Int,msg:String,taxtDetail:[TaxiDetail])
    func updateTaxiPhone(status:Int,msg:String,taxiPhone:[TaxiPhone])
    func updateTaxi(status:Int,msg:String,taxi:[Taxi])
    func updateError(error:String)
}
