//
//  Favor.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/23.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class BikeFavor
{
    var id:Int
    var stationID:Int
    var stationName:String
    var address:String

    init(id:Int,stationID:Int,stationName:String,address:String)
    {
        self.id = id
        self.stationID = stationID
        self.stationName = stationName
        self.address = address
    }
    
    class func formatBikeFavorJson(bikeFavorJson:JSON) -> BikeFavor
    {
        let id:Int = bikeFavorJson["id"].intValue
        let stationID:Int = bikeFavorJson["stationID"].intValue
        let stationName:String = bikeFavorJson["stationName"].stringValue
        let address:String = bikeFavorJson["address"].stringValue
        let bikeFavor = BikeFavor(id: id, stationID: stationID, stationName: stationName, address: address)
        return bikeFavor
    }

    
    class func APPSaveBikeStationFavor(updateVc:BikeFavorDelegate,userid:Int,stationID:Int,stationName:String,address:String)
    {
        let actionName = "APPSaveBikeStationFavor"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("stationID=\(stationID)")
        params.append("name=\(stationName)")
        params.append("address=\(address)")
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
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPSaveBikeStationFavor(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPSaveBikeStationFavor(-99, msg: error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    class func APPDeleteBikeForStation(updateVc:BikeFavorDelegate,userid:Int,stationIds:String)
    {
        let actionName = "APPDeleteBikeForStation"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("stationIds=\(stationIds)")
        
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPDeleteBikeForStation(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPDeleteBikeForStation(-99, msg: error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    
    class func APPQueryAllBikeStationFavor(updateVc:BikeFavorDelegate,userid:Int)
    {
        let actionName = "APPQueryAllBikeStationFavor"
        
        var params = [String]()
        params.append("userid=\(userid)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    var bikeFavorList = [BikeFavor]()
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        for bikeFavorItemJson in jsonRet["result"]
                        {
                            let bikeFavorTemp = self.formatBikeFavorJson(bikeFavorItemJson.1)
                            bikeFavorList.append(bikeFavorTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPQueryAllBikeStationFavor(status, msg: msg, bikeFavor: bikeFavorList)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPQueryAllBikeStationFavor(-99 , msg: error!.localizedDescription, bikeFavor: bikeFavorList)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    

}

class LineFavor
{
    var id:Int
    var lineID:Int
    var direct:Int
    var name:String
    var startName:String
    var endName:String
    
    init(id:Int,lineID:Int,direct:Int,name:String,startName:String,endName:String)
    {
        self.id = id
        self.lineID = lineID
        self.direct = direct
        self.name = name
        self.startName = startName
        self.endName = endName
    }
    
    class func formatLineFavorJson(lineFavorJson:JSON) -> LineFavor
    {
        let id:Int = lineFavorJson["id"].intValue
        let lineID:Int = lineFavorJson["lineID"].intValue
        let direct:Int = lineFavorJson["direct"].intValue
        let name:String = lineFavorJson["linename"].stringValue
        let startName:String = lineFavorJson["startname"].stringValue
        let endName:String = lineFavorJson["endname"].stringValue
        let lineFavor = LineFavor(id: id, lineID: lineID, direct: direct, name: name, startName: startName, endName: endName)
        return lineFavor
    }

    
    class func APPSaveLineFavor(updateVc:LineFavorDelegate,userid:Int,lineID:Int,direct:Int,name:String,startName:String,endName:String)
    {
        let actionName = "APPSaveLineFavor"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("lineID=\(lineID)")
        params.append("direct=\(direct)")
        params.append("name=\(name)")
        params.append("startName=\(startName)")
        params.append("endName=\(endName)")
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
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPSaveLineFavor(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateLineError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    class func APPDeleteForLine(updateVc:LineFavorDelegate,userid:Int,lineIds:String)
    {
        let actionName = "APPDeleteForLine"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("lineIds=\(lineIds)")
  
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPDeleteForLine(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateLineError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    
    class func APPQueryAllLineFavor(updateVc:LineFavorDelegate,userid:Int)
    {
        let actionName = "APPQueryAllLineFavor"
        
        var params = [String]()
        params.append("userid=\(userid)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        var lineFavorList = [LineFavor]()
                        for lineFavorItemJson in jsonRet["result"]
                        {
                            let lineFavorTemp = self.formatLineFavorJson(lineFavorItemJson.1)
                            lineFavorList.append(lineFavorTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPQueryAllLineFavor(status, msg: msg, lineFavor: lineFavorList)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateLineError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    



}

class StationFavor
{
    var id:Int
    var stationID:Int
    var stationName:String
    var lines:String
    
    init(id:Int,stationID:Int,stationName:String,lines:String)
    {
        self.id = id
        self.stationID = stationID
        self.stationName = stationName
        self.lines = lines
    }
    
    class func formatStationFavorJson(stationFavorJson:JSON) -> StationFavor
    {
        let id:Int = stationFavorJson["id"].intValue
        let stationID:Int = stationFavorJson["stationID"].intValue
        let stationName:String = stationFavorJson["stationName"].stringValue
        let lines:String = stationFavorJson["lines"].stringValue
        let stationFavor = StationFavor(id: id, stationID: stationID, stationName: stationName, lines: lines)
        return stationFavor
    }
    
    class func APPSaveStationFavor(updateVc:StationFavorDelegate,userid:Int,stationID:Int,stationName:String,lines:String)
    {
        let actionName = "APPSaveStationFavor"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("stationID=\(stationID)")
        params.append("name=\(stationName)")
        params.append("stationLines=\(lines)")
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(Tools.formatParamValue(params))")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getPostHttpRequest(retActionUrl, httpBody: httpBody)            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPSaveStationFavor(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateStationError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    class func APPDeleteForStation(updateVc:StationFavorDelegate,userid:Int,stationIds:String)
    {
        let actionName = "APPDeleteForStation"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("stationIds=\(stationIds)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPDeleteForStation(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateStationError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }

    
    class func APPQueryAllStationFavor(updateVc:StationFavorDelegate,userid:Int)
    {
        let actionName = "APPQueryAllStationFavor"
        
        var params = [String]()
        params.append("userid=\(userid)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        var stationFavorList = [StationFavor]()
                        for stationFavorItemJson in jsonRet["result"]
                        {
                            let stationFavorTemp = self.formatStationFavorJson(stationFavorItemJson.1)
                            stationFavorList.append(stationFavorTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPQueryAllStationFavor(status, msg: msg, stationFavor: stationFavorList)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateStationError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }


}

class TransferFavor
{
    var id:Int
    var startlan:Double
    var startlon:Double
    var endlan:Double
    var endlon:Double
    var startname:String
    var endname:String
    
    init(id:Int,startlan:Double,startlon:Double,endlan:Double,endlon:Double,startname:String,endname:String)
    {
        self.id = id
        self.startlan = startlan
        self.startlon = startlon
        self.endlan = endlan
        self.endlon = endlon
        self.startname = startname
        self.endname = endname
    }
    
    class func formatTransferFavorJson(transferFavorJson:JSON) -> TransferFavor
    {
        let id:Int = transferFavorJson["id"].intValue
        let startlan:Double = transferFavorJson["startLantitude"].doubleValue
        let startlon:Double = transferFavorJson["startLongtitude"].doubleValue
        let endlan:Double = transferFavorJson["endLantitude"].doubleValue
        let endlon:Double = transferFavorJson["endLongtitude"].doubleValue
        let startname:String = transferFavorJson["startName"].stringValue
        let endname:String = transferFavorJson["endName"].stringValue
        let transferFavor = TransferFavor(id: id, startlan: startlan, startlon: startlon, endlan: endlan, endlon: endlon, startname: startname, endname: endname)
        return transferFavor
    }

    class func APPSaveTransferFavor(updateVc:TransferFavorDelegate,userid:Int,startlan:Double,startlon:Double,endlan:Double,endlon:Double,startname:String,endname:String)
    {
        let actionName = "APPSaveTransferFavor"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("startLantitude=\(startlan)")
        params.append("startLongtitude=\(startlon)")
        params.append("endLantitude=\(endlan)")
        params.append("endLongtitude=\(endlon)")
        params.append("startName=\(startname)")
        params.append("endName=\(endname)")
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(Tools.formatParamValue(params))")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getPostHttpRequest(retActionUrl, httpBody: httpBody)
//            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPSaveTransferFavor(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateTransferFavorError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    class func APPDeleteForTransfer(updateVc:TransferFavorDelegate,userid:Int,transferIds:String)
    {
        let actionName = "APPDeleteForTransfer"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("transferIds=\(transferIds)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPDeleteForTransfer(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateTransferFavorError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }

    class func APPQueryAllTransferFavor(updateVc:TransferFavorDelegate,userid:Int)
    {
        let actionName = "APPQueryAllTransferFavor"
        
        var params = [String]()
        params.append("userid=\(userid)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        var transferFavorList = [TransferFavor]()
                        for transferFavorItemJson in jsonRet["result"]
                        {
                            let transferFavorTemp = self.formatTransferFavorJson(transferFavorItemJson.1)
                            transferFavorList.append(transferFavorTemp)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAPPQueryAllTransferFavor(status, msg: msg, transferFavor: transferFavorList)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateTransferFavorError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }

}

protocol BikeFavorDelegate
{
    func updateAPPSaveBikeStationFavor(status:Int,msg:String)
    func updateAPPDeleteBikeForStation(status:Int,msg:String)
    func updateAPPQueryAllBikeStationFavor(status:Int,msg:String,bikeFavor:[BikeFavor])
}

protocol LineFavorDelegate
{
    func updateAPPSaveLineFavor(status:Int,msg:String)
    func updateAPPDeleteForLine(status:Int,msg:String)
    func updateAPPQueryAllLineFavor(status:Int,msg:String,lineFavor:[LineFavor])
    func updateLineError(error:String)
}

protocol StationFavorDelegate
{
    func updateAPPSaveStationFavor(status:Int,msg:String)
    func updateAPPDeleteForStation(status:Int,msg:String)
    func updateAPPQueryAllStationFavor(status:Int,msg:String,stationFavor:[StationFavor])
    func updateStationError(error:String)
}

protocol TransferFavorDelegate
{
    func updateAPPSaveTransferFavor(status:Int,msg:String)
    func updateAPPDeleteForTransfer(status:Int,msg:String)
    func updateAPPQueryAllTransferFavor(status:Int,msg:String,transferFavor:[TransferFavor])
    func updateTransferFavorError(error:String)
}



