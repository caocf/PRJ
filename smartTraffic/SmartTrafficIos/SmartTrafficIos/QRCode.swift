//
//  QRCode.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/28.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class QRCode
{
    class func getBusIDForQRCode(updateVc:QRCodeDelegate,QRCodeStr:String)
    {
        let actionName = "QueryStationIDByQRCodeFromDB"
        
        var params = [String]()
        params.append("QRCode=\(QRCodeStr)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
//                        var parkingList = [Parking]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        let id = jsonRet["id"].intValue
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateGetBusIDForQRCode(id, msg: "")
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateGetBusIDForQRCode(-1, msg: error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
}

protocol QRCodeDelegate
{
    func updateGetBusIDForQRCode(id:Int,msg:String)
}