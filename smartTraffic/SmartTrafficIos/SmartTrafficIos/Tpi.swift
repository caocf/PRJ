
//
//  Tpi.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/6/15.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class TpiTools
{
    class func getTpiLevelColor(tpiLevel:String) -> UIColor
    {
        var retColor:UIColor!
        switch (tpiLevel)
        {
        case "畅通":
            retColor = AreaTpi.畅通
        case "基本畅通":
            retColor = AreaTpi.基本畅通
        case "轻度拥堵":
            retColor = AreaTpi.轻度拥堵
        case "中度拥堵":
            retColor = AreaTpi.中度拥堵
        case "严重拥堵":
            retColor = AreaTpi.严重拥堵
        default:
            retColor = AreaTpi.其他
        }

        return retColor
    }
}

class NewAreaTpi
{
    
    var avgSpeed:Double
    var coverage:Double
    var hrRate:Double
    var length:Double
    var areaName:String
    var areaTpiNum:String
    var time:Int
    var tpi:Double
    var tpiLevel:String
    
    init(avgSpeed:Double,coverage:Double,hrRate:Double,length:Double,areaName:String,areaTpiNum:String,time:Int,tpi:Double,tpiLevel:String)
    {
        self.avgSpeed = avgSpeed
        self.coverage = coverage
        self.hrRate = hrRate
        self.length = length
        self.areaName = areaName
        self.areaTpiNum = areaTpiNum
        self.time = time
        self.tpi = tpi
        self.tpiLevel = tpiLevel
    }
    
    
    class func formatAreaTpiJson(areaTpiJson:JSON) -> NewAreaTpi
    {
        let avgSpeed:Double = areaTpiJson["avgSpeed"].doubleValue
        let coverage:Double = areaTpiJson["coverage"].doubleValue
        let hrRate:Double = areaTpiJson["hrRate"].doubleValue
        let length:Double = areaTpiJson["length"].doubleValue
        let areaName:String = areaTpiJson["areaName"].stringValue
        let areaTpiNum:String = areaTpiJson["areaTpiNum"].stringValue
        let time:Int = areaTpiJson["time"].intValue
        let tpi:Double = areaTpiJson["tpi"].doubleValue
        let tpiLevel:String = areaTpiJson["tpiLevel"].stringValue
        
        let areaTpiTemp = NewAreaTpi(avgSpeed: avgSpeed, coverage: coverage, hrRate: hrRate, length: length, areaName: areaName, areaTpiNum: areaTpiNum, time: time, tpi: tpi, tpiLevel: tpiLevel)
        return areaTpiTemp
    }
    
    class func initAreaTpi(updateVc:tpiDelegate)
    {
        let actionName = "cservice/queryAllAreaTPI"
        let paramValue = "plan=3"
        
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let updateRequest = HttpTools.getHttpRequest(sServerUrl, paramValue: paramValue)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var areaTpiList = [NewAreaTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["areaTpis"]
                        
                        for listJson in jsonList
                        {
                            let areaTpiTemp = NewAreaTpi.formatAreaTpiJson(listJson.1)
                            areaTpiList.append(areaTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAreaTpi(areaTpiList, status: status)
                        })
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



class RoadTpi
{
    
    var avgSpeed:Double
    var coverage:Double
    var endName:String
    var hrRate:Double
    var length:Double
    var roadClass:String
    var roadName:String
    var roadTpiNum:String
    var startName:String
    var time:Int
    var tpi:Double
    var tpiLevel:String
    
    init(avgSpeed:Double,coverage:Double,endName:String,hrRate:Double,length:Double,roadClass:String,roadName:String,roadTpiNum:String,startName:String,time:Int,tpi:Double,tpiLevel:String)
    {
        self.avgSpeed = avgSpeed
        self.coverage = coverage
        self.endName = endName
        self.hrRate = hrRate
        self.length = length
        self.roadClass = roadClass
        self.roadName = roadName
        self.roadTpiNum = roadTpiNum
        self.startName = startName
        self.time = time
        self.tpi = tpi
        self.tpiLevel = tpiLevel
    }
    
    class func formatRoadTpiJson(roadTpiJson:JSON) -> RoadTpi
    {
        let avgSpeed:Double = roadTpiJson["avgSpeed"].doubleValue
        let coverage:Double = roadTpiJson["coverage"].doubleValue
        let endName:String = roadTpiJson["endName"].stringValue
        let hrRate:Double = roadTpiJson["hrRate"].doubleValue
        let length:Double = roadTpiJson["length"].doubleValue
        let roadClass:String = roadTpiJson["roadClass"].stringValue
        let roadName:String = roadTpiJson["roadName"].stringValue
        let roadTpiNum:String = roadTpiJson["roadTpiNum"].stringValue
        let startName:String = roadTpiJson["startName"].stringValue
        let time:Int = roadTpiJson["time"].intValue
        let tpi:Double = roadTpiJson["tpi"].doubleValue
        let tpiLevel:String = roadTpiJson["tpiLevel"].stringValue
        
        let roadTpiTemp = RoadTpi(avgSpeed: avgSpeed, coverage: coverage, endName: endName, hrRate: hrRate, length: length, roadClass: roadClass, roadName: roadName, roadTpiNum: roadTpiNum, startName: startName, time: time, tpi: tpi, tpiLevel: tpiLevel)
        return roadTpiTemp
    }
    
    class func initRoadTpi(updateVc:tpiDelegate)
    {
        let actionName = "cservice/queryRoadTPIByTPIDescPager"
        let paramValue = "plan=1&direction=1&pageNum=1&pageSize=100"

        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let updateRequest = HttpTools.getHttpRequest(sServerUrl, paramValue: paramValue)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var roadTpiList = [RoadTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["roadTpis"][0]
                        
                        for listJson in jsonList
                        {
                            let roadTpiTemp = RoadTpi.formatRoadTpiJson(listJson.1)
                            roadTpiList.append(roadTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateRoadTpi(roadTpiList, status: status)
                        })
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
    
    class func initRoadTpiFast(updateVc:tpiDelegate)
    {
        let actionName = "cservice/queryUnifyRoadTPIByRoadClassTPIDescPager"
        let paramValue = "plan=1&roadClass=%E5%BF%AB%E9%80%9F%E8%B7%AF&pageNum=1&pageSize=100"
        
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let updateRequest = HttpTools.getHttpRequest(sServerUrl, paramValue: paramValue)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var roadTpiList = [RoadTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["roadTpis"]
                        
                        for listJson in jsonList
                        {
                            let roadTpiTemp = RoadTpi.formatRoadTpiJson(listJson.1)
                            roadTpiList.append(roadTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateRoadTpi(roadTpiList, status: status)
                        })
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
    
    class func initRoadTpiMain(updateVc:tpiDelegate)
    {
        let actionName = "cservice/queryUnifyRoadTPIByRoadClassTPIDescPager"
        let paramValue = "plan=1&roadClass=%E4%B8%BB%E5%B9%B2%E9%81%93&pageNum=1&pageSize=100"
        
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let updateRequest = HttpTools.getHttpRequest(sServerUrl, paramValue: paramValue)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var roadTpiList = [RoadTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["roadTpis"]
                        
                        for listJson in jsonList
                        {
                            let roadTpiTemp = RoadTpi.formatRoadTpiJson(listJson.1)
                            roadTpiList.append(roadTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateRoadTpi(roadTpiList, status: status)
                        })
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

    class func initRoadTpiOther(updateVc:tpiDelegate)
    {
        let actionName = "cservice/queryUnifyRoadTPIByLowRoadClassTPIDescPager"
        let paramValue = "plan=1&pageSize=999&pageNum=1"
        
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let updateRequest = HttpTools.getHttpRequest(sServerUrl, paramValue: paramValue)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var roadTpiList = [RoadTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["roadTpis"]
                        
                        for listJson in jsonList
                        {
                            let roadTpiTemp = RoadTpi.formatRoadTpiJson(listJson.1)
                            roadTpiList.append(roadTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateRoadTpi(roadTpiList, status: status)
                        })
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

    
    class func initRoadTpi(updateVc:tpiDelegate,roadName:String)
    {
        let actionName = "cservice/queryUnifyRoadTPIByRoadName"
        
        var params = [String]()
        params.append("plan=1")
        params.append("roadName=\(roadName)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params).stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!)
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var roadTpiList = [RoadTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["roadTpi"]
                        
                   
                        let roadTpiTemp = RoadTpi.formatRoadTpiJson(jsonList)
                        roadTpiList.append(roadTpiTemp)
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateRoadTpi(roadTpiList, status: status)
                        })
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

    class func initTwoRoadTpi(updateVc:tpiDelegate,roadName:String)
    {
        let actionName = "cservice/queryTwoRoadTPIByRoadNameTPIDesc"
        
        var params = [String]()
        params.append("plan=1")
        params.append("roadName=\(roadName)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params).stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!)
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var roadTpiList = [RoadTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["roadTpis"]
                        
                        for listJson in jsonList
                        {
                            let roadTpiTemp = RoadTpi.formatRoadTpiJson(listJson.1)
                            roadTpiList.append(roadTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateRoadTpi(roadTpiList, status: status)
                        })
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
    
    class func initRoadSectionTpi(updateVc:tpiDelegate,roadName:String,roadStart:String,roadEnd:String)
    {
        let actionName = "cservice/queryRoadSectionTPIByRoadInfoTPIDesc"
        
        var params = [String]()
        params.append("plan=1")
        params.append("roadName=\(roadName)")
        params.append("roadStart=\(roadStart)")
        params.append("roadEnd=\(roadEnd)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params).stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!)
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var roadTpiList = [RoadTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["roadTpis"]
                        
                        for listJson in jsonList
                        {
                            let roadTpiTemp = RoadTpi.formatRoadTpiJson(listJson.1)
                            roadTpiList.append(roadTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateRoadTpi(roadTpiList, status: status)
                        })
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

class PassTpi
{
    
    var avgSpeed:Double
    var coverage:Double
    var endName:String
    var hrRate:Double
    var length:Double
    var passClass:String
    var passName:String
    var passTpiNum:String
    var startName:String
    var time:Int
    var tpi:Double
    var tpiLevel:String
    
    init(avgSpeed:Double,coverage:Double,endName:String,hrRate:Double,length:Double,passClass:String,passName:String,passTpiNum:String,startName:String,time:Int,tpi:Double,tpiLevel:String)
    {
        self.avgSpeed = avgSpeed
        self.coverage = coverage
        self.endName = endName
        self.hrRate = hrRate
        self.length = length
        self.passClass = passClass
        self.passName = passName
        self.passTpiNum = passTpiNum
        self.startName = startName
        self.time = time
        self.tpi = tpi
        self.tpiLevel = tpiLevel
    }
    
    class func formatPassTpiJson(passTpiJson:JSON) -> PassTpi
    {
        let avgSpeed:Double = passTpiJson["avgSpeed"].doubleValue
        let coverage:Double = passTpiJson["coverage"].doubleValue
        let endName:String = passTpiJson["endName"].stringValue
        let hrRate:Double = passTpiJson["hrRate"].doubleValue
        let length:Double = passTpiJson["length"].doubleValue
        let passClass:String = passTpiJson["passClass"].stringValue
        let passName:String = passTpiJson["passName"].stringValue
        let passTpiNum:String = passTpiJson["passTpiNum"].stringValue
        let startName:String = passTpiJson["startName"].stringValue
        let time:Int = passTpiJson["time"].intValue
        let tpi:Double = passTpiJson["tpi"].doubleValue
        let tpiLevel:String = passTpiJson["tpiLevel"].stringValue
        
        let PassTpiTemp = PassTpi(avgSpeed: avgSpeed, coverage: coverage, endName: endName, hrRate: hrRate, length: length, passClass: passClass, passName: passName, passTpiNum: passTpiNum, startName: startName, time: time, tpi: tpi, tpiLevel: tpiLevel)
        return PassTpiTemp
    }
    
    class func initPassTpi(updateVc:tpiDelegate)
    {
        let actionName = "cservice/queryPassTPIByTPIDescPager"
        let paramValue = "plan=1&direction=1&pageNum=1&pageSize=20"
        
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let updateRequest = HttpTools.getHttpRequest(sServerUrl, paramValue: paramValue)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var passTpiList = [PassTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["passTpis"][0]
                        
                        for listJson in jsonList
                        {
                            let passTpiTemp = PassTpi.formatPassTpiJson(listJson.1)
                            passTpiList.append(passTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updatePassTpi(passTpiList, status: status)
                        })
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

class CBDTpi
{
    
    var avgSpeed:Double
    var coverage:Double
    var hrRate:Double
    var length:Double
    var cbdName:String
    var cbdTpiNum:String
    var time:Int
    var tpi:Double
    var tpiLevel:String
    
    init(avgSpeed:Double,coverage:Double,hrRate:Double,length:Double,cbdName:String,cbdTpiNum:String,time:Int,tpi:Double,tpiLevel:String)
    {
        self.avgSpeed = avgSpeed
        self.coverage = coverage
        self.hrRate = hrRate
        self.length = length
        self.time = time
        self.tpi = tpi
        self.tpiLevel = tpiLevel
        self.cbdName = cbdName
        self.cbdTpiNum = cbdTpiNum
    }
    
    class func formatCBDTpiJson(cbdTpiJson:JSON) -> CBDTpi
    {
        let avgSpeed:Double = cbdTpiJson["avgSpeed"].doubleValue
        let coverage:Double = cbdTpiJson["coverage"].doubleValue
        let hrRate:Double = cbdTpiJson["hrRate"].doubleValue
        let length:Double = cbdTpiJson["length"].doubleValue
        let cbdName:String = cbdTpiJson["cbdName"].stringValue
        let cbdTpiNum:String = cbdTpiJson["cbdTpiNum"].stringValue
        let time:Int = cbdTpiJson["time"].intValue
        let tpi:Double = cbdTpiJson["tpi"].doubleValue
        let tpiLevel:String = cbdTpiJson["tpiLevel"].stringValue
        
        let cbdTpiTemp = CBDTpi(avgSpeed: avgSpeed, coverage: coverage, hrRate: hrRate, length: length, cbdName: cbdName, cbdTpiNum: cbdTpiNum, time: time, tpi: tpi, tpiLevel: tpiLevel)
        return cbdTpiTemp
    }
    
    class func initCBDTpi(updateVc:tpiDelegate)
    {
        let actionName = "cservice/queryCBDTPIByTPIDescPager"
        let paramValue = "plan=1&pageNum=1&pageSize=9"
        
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            if let updateRequest = HttpTools.getHttpRequest(sServerUrl, paramValue: paramValue)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var cbdTpiList = [CBDTpi]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let status = jsonRet["status"].intValue
                        let jsonList = jsonRet["cbdTpis"]
                        
                        for listJson in jsonList
                        {
                            let cbdTpiTemp = CBDTpi.formatCBDTpiJson(listJson.1)
                            cbdTpiList.append(cbdTpiTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateCBDTpi(cbdTpiList, status: status)
                        })
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


protocol tpiDelegate
{
    func updateAreaTpi(updateAreaTpi:[NewAreaTpi],status:Int)
    func updateRoadTpi(updateRoadTpi:[RoadTpi],status:Int)
    func updatePassTpi(updatePassTpi:[PassTpi],status:Int)
    func updateCBDTpi(updateCBDTpi:[CBDTpi],status:Int)
    func updateError(error:String)
}

