//
//  AreaTpi.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/22.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class AreaTpi
{
    enum TPIAreaName:String
    {
        case All = "全路网"
        case Nanhu = "南湖区"
        case Xiuzhou = "秀洲区"
        case Zhonghuan = "中环内"
        case Neihuan = "内环内"
    }
    
    static let 畅通 = UIColor(red: 0x01/0xff, green: 0x9b/0xff, blue: 0x1e/0xff, alpha: 1)
    static let 基本畅通 = UIColor(red: 0x7f/0xff, green: 0xbe/0xff, blue: 0x05/0xff, alpha: 1)
    static let 轻度拥堵 = UIColor(red: 0xff/0xff, green: 0xae/0xff, blue: 0x00/0xff, alpha: 1)
    static let 中度拥堵 = UIColor(red: 0xff/0xff, green: 0x6c/0xff, blue: 0x00/0xff, alpha: 1)
    static let 严重拥堵 = UIColor(red: 0xff/0xff, green: 0x00/0xff, blue: 0x00/0xff, alpha: 1)
    static let 其他 = UIColor(red: 0xaa/0xff, green: 0xaa/0xff, blue: 0xaa/0xff, alpha: 1)

    let actionName = "cservice/queryAreaTPIByAreaName"
    let paramValue = "plan=3&areaName="
//    let paramValue = "plan=3&areaName=%E5%85%A8%E8%B7%AF%E7%BD%91"

    
    var tpi:Double
    var tpiTime:NSDate
    var avgSpeed:Double
    var tpiLevel:String
    var hrRate:Double
    
    init(tpi:Double,tpiTime:NSDate,avgSpeed:Double,tpiLevel:String)
    {
        self.tpi = tpi
        self.tpiTime = tpiTime
        self.avgSpeed = avgSpeed
        self.tpiLevel = tpiLevel
        self.hrRate = 0.0
    }
    
    init(tpi:Double,tpiTime:NSDate,avgSpeed:Double,tpiLevel:String,hrRate:Double)
    {
        self.tpi = tpi
        self.tpiTime = tpiTime
        self.avgSpeed = avgSpeed
        self.tpiLevel = tpiLevel
        self.hrRate = hrRate
    }
    
    func getActionUrl(areaName:TPIAreaName) -> String
    {
        var retActionUrl = ""
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            retActionUrl = "\(sServerUrl)?\(paramValue)\(areaName.rawValue)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet)!
        }
        return retActionUrl
    }

    class func initNullAreaTpi() -> AreaTpi
    {
       let nullAreaTpi=AreaTpi(tpi: 0.0, tpiTime: NSDate(timeIntervalSince1970: 0.0), avgSpeed: 0.0, tpiLevel: "")
        return nullAreaTpi
    }
    
    func initAreaTpi(updateVc:AreaTpiProtocol,areaName:TPIAreaName)
    {
        if let areaTpiRequest = HttpTools.getHttpRequest(getActionUrl(areaName))
        {
            NSURLConnection.sendAsynchronousRequest(areaTpiRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                if(error == nil)
                {
                    var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                    if jsonRet["status"].intValue == 2000
                    {
                        var jsonAreaTpi = jsonRet["areaTpi"]
                        let tpiTemp = jsonAreaTpi["tpi"].doubleValue
                        let avgSpeedTemp = jsonAreaTpi["avgSpeed"].doubleValue
                        let tpiLevelTemp = jsonAreaTpi["tpiLevel"].stringValue
                        let tpiTimeTemp = NSDate(timeIntervalSince1970: jsonAreaTpi["time"].doubleValue + 28800.0)
                        let hrRateTemp = jsonAreaTpi["hrRate"].doubleValue
                        
                        let areatpiTemp=AreaTpi(tpi: tpiTemp, tpiTime: tpiTimeTemp, avgSpeed: avgSpeedTemp, tpiLevel: tpiLevelTemp ,hrRate: hrRateTemp)
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAreaTpi(areatpiTemp)
                        })
                        
                    }
                }
                else
                {
                    dispatch_async(dispatch_get_main_queue(), { () -> Void in
                        updateVc.getAreaTpiError(error!.localizedDescription)
                    })
                    print(error!.localizedDescription)
                }
            })
        }
    }
    
}

protocol AreaTpiProtocol
{
    func getAreaTpiError(error:String)
    func updateAreaTpi(updateAreaTpi:AreaTpi)
}
