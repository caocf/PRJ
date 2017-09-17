//
//  Parking.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class Parking {
    
    static let defaultRadius = 1000
    static let extendRadius = 3000
    static let defaultNum = 100
    static let nervousNum = 20
    
    enum ParkingIcon:String
    {
        case normal = "绿色.png"
        case normalSelect = "绿色_big.png"
        case nervous = "橙色.png"
        case nervousSelect = "橙色_big.png"
        case noParking = "红色.png"
        case noParkingSelect = "红色_big.png"
    }

    
    var parkpointid:String
    var parktype:Int
    var pointname:String
    var roadname:String
    var parklotcount:Int
    var gpslo:Double
    var gpsla:Double
    var freecount:Int
    var updateTime:NSDate
    var distance:Double
    
    init(parkpointid:String,parktype:Int,pointname:String,roadname:String,parklotcount:Int,gpslo:Double,gpsla:Double,freecount:Int,updateTime:NSDate,distance:Double)
    {
        self.parkpointid = parkpointid
        self.parktype = parktype
        self.pointname = pointname
        self.roadname = roadname
        self.parklotcount = parklotcount
        self.gpslo = gpslo
        self.gpsla = gpsla
        self.freecount = freecount
        self.updateTime = updateTime
        self.distance = distance
    }
    
    class func formatJson(parkingJson:JSON) -> Parking
    {
        let distance:Double = parkingJson["distance"].doubleValue
        let parkpointid:String = parkingJson["parkpointid"].stringValue
        let parktype:Int = parkingJson["parktype"].intValue
        let pointname:String = parkingJson["pointname"].stringValue
        let roadname:String = parkingJson["roadname"].stringValue
        let parklotcount:Int = parkingJson["parklotcount"].intValue
        let gpslo:Double = parkingJson["gpslo"].doubleValue
        let gpsla:Double = parkingJson["gpsla"].doubleValue
        let freecount:Int = parkingJson["freecount"].intValue
        let updateTime:NSDate = Tools.stringConvertToNSDate(parkingJson["updateTime"].stringValue, formatString: "YYYYMMddHHmmss")!
        
        let parking = Parking(parkpointid: parkpointid, parktype: parktype, pointname: pointname, roadname: roadname, parklotcount: parklotcount, gpslo: gpslo, gpsla: gpsla, freecount: freecount, updateTime: updateTime,distance: distance)
        return parking
    }
    
    
    class func initParking(updateVc:ParkingProtocal,radius:Int,lan:Double,lon:Double,page:Int,num:Int,isReal:Int)
    {
        let actionName = "searchParkingByLocationCircle"
        
        var params = [String]()
        params.append("radius=\(radius)")
        params.append("lan=\(lan)")
        params.append("lon=\(lon)")
        params.append("page=\(page)")
        params.append("num=\(num)")
        params.append("isReal=\(isReal)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var parkingList = [Parking]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        if (jsonRet["message"]["status"].intValue == 1)
                        {
                            let jsonList = jsonRet["result"]
                            
                            for listJson in jsonList
                            {
                                let parkingTemp = Parking.formatJson(listJson.1)
                                parkingList.append(parkingTemp)
                            }
                            
                            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                                updateVc.updateParking(parkingList)
                            })

                        }
                        else
                        {
                            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                                updateVc.updateParkingError(jsonRet["message"]["msg"].stringValue)
                            })
                            

                        }
                        
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateParkingError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    class func getIcon(isSelect:Bool,parking:Parking) -> String
    {
        if parking.freecount == 0
        {
            if isSelect
            {
                return ParkingIcon.noParkingSelect.rawValue
            }
            else
            {
                return ParkingIcon.noParking.rawValue
            }
        }
        else if parking.freecount * 100 / parking.parklotcount <= nervousNum
        {
            if isSelect
            {
                return ParkingIcon.nervousSelect.rawValue
            }
            else
            {
                return ParkingIcon.nervous.rawValue
            }
        }
        else
        {
            if isSelect
            {
                return ParkingIcon.normalSelect.rawValue
            }
            else
            {
                return ParkingIcon.normal.rawValue
            }
        }
    }
    
}

protocol ParkingProtocal
{
    func updateParking(parking:[Parking])
    func updateParkingError(error:String)
}


