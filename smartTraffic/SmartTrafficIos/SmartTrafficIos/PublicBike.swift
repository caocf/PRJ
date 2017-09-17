//
//  PublicBike.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/27.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class PublicBike
{

    static let defaultDistance = 500
    static let extendDistance = 2000
    static let defaultCount = 20
    static let nervousNum = 20
    
    enum BikeIcon:String
    {
        case normal = "绿色.png"
        case normalSelect = "绿色_big.png"
        case nervous = "橙色.png"
        case nervousSelect = "橙色_big.png"
        case noRent = "红色.png"
        case noRentSelect = "红色_big.png"
        case noRevert = "暗红色.png"
        case noRevertSelect = "暗红色_big.png"
    }
    
    var Id:Int = -1
    var Name:String = ""
    var Address:String = ""
    var AreaName:String = ""
    var Count:Int = 0
    var IsAllDay:Bool = false
    var Latitude:Double = 0.0
    var Longitude:Double = 0.0
    var PersonDuty:Bool = false
    var RentCount:Int = 0
    var ServiceTime:String = ""
    var StationPhone:String = ""
    var Distance:Int = -1
    
    init(Id:Int,Name:String,Address:String,AreaName:String,Count:Int,IsAllDay:Bool,Latitude:Double,Longitude:Double,PersonDuty:Bool,RentCount:Int,ServiceTime:String,StationPhone:String)
    {
        self.Id = Id
        self.Name = Name
        self.Address = Address
        self.AreaName = AreaName
        self.Count = Count
        self.IsAllDay = IsAllDay
        self.Latitude = Latitude
        self.Longitude = Longitude
        self.PersonDuty = PersonDuty
        self.RentCount = RentCount
        self.ServiceTime = ServiceTime
        self.StationPhone = StationPhone
        self.Distance = -1
    }
    
    init(Id:Int,Name:String,Address:String,AreaName:String,Count:Int,IsAllDay:Bool,Latitude:Double,Longitude:Double,PersonDuty:Bool,RentCount:Int,ServiceTime:String,StationPhone:String,Distance:Int)
    {
        self.Id = Id
        self.Name = Name
        self.Address = Address
        self.AreaName = AreaName
        self.Count = Count
        self.IsAllDay = IsAllDay
        self.Latitude = Latitude
        self.Longitude = Longitude
        self.PersonDuty = PersonDuty
        self.RentCount = RentCount
        self.ServiceTime = ServiceTime
        self.StationPhone = StationPhone
        self.Distance = Distance
    }
    
    class func initNullPublicBike() -> PublicBike
    {
        let nullPublicBike=PublicBike(Id: -1, Name: "", Address: "", AreaName: "", Count: 0, IsAllDay: false, Latitude: 0.0, Longitude: 0.0, PersonDuty: false, RentCount: 0, ServiceTime: "", StationPhone: "")
        return nullPublicBike
    }

    
    static func formatJsonPublicBike(bike:JSON) -> [PublicBike]
    {
        var publicBikeList = [PublicBike]()
        for _ in bike
        {
            let Id:Int = bike["Id"].intValue
            let Name:String = bike["Name"].stringValue
            let Address:String = bike["Address"].stringValue
            let AreaName:String = bike["AreaName"].stringValue
            let Count:Int = bike["Count"].intValue
            let IsAllDay:Bool = bike["IsAllDay"].boolValue
            let Latitude:Double = bike["Latitude"].doubleValue
            let Longitude:Double = bike["Longitude"].doubleValue
            let PersonDuty:Bool = bike["PersonDuty"].boolValue
            let RentCount:Int = bike["RentCount"].intValue
            let ServiceTime:String = bike["ServiceTime"].stringValue
            let StationPhone:String = bike["StationPhone"].stringValue
            let Distance:Int = bike["Distance"].intValue
            
            let publicBikeTemp = PublicBike(Id: Id, Name: Name, Address: Address, AreaName: AreaName, Count: Count, IsAllDay: IsAllDay, Latitude: Latitude, Longitude: Longitude, PersonDuty: PersonDuty, RentCount: RentCount, ServiceTime: ServiceTime, StationPhone: StationPhone,Distance: Distance)
            publicBikeList.append(publicBikeTemp)
        }
        return publicBikeList
    }

    
    
    func getActionUrl(stationID:Int) -> (String,NSData)
    {
        let actionName = "QueryOriginBikeStationByID"
        let paramKey = "stationID"

        var retActionUrl = ""
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(paramKey)=\(stationID)")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet)!
        }
        return (retActionUrl,httpBody)
    }
    
    func getActionUrl(stationName:String) -> (String,NSData)
    {
        let actionName = "QueryOriginBikeStation"
        let paramKey = "stationName"
        
        var retActionUrl = ""
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(paramKey)=\(stationName)")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet)!
        }
        return (retActionUrl,httpBody)
    }
    
    func getActionUrl(longtitude:Double,lantitude:Double,distance:Int,count:Int) -> (String,NSData)
    {
        let actionName = "QueryOriginNearByBikeStation"
        var params = [String]()
        params.append("longtitude=\(longtitude)")
        params.append("lantitude=\(lantitude)")
        params.append("distance=\(distance)")
        params.append("count=\(count)")
        
        var retActionUrl = ""
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(Tools.formatParamValue(params))")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet)!
        }
        return (retActionUrl,httpBody)
    }
    
    class func updateRealTimeStationCount(updateVc:PublicBikeProtocol,bikeList:[PublicBike])
    {
        let actionName = "QueryOriginBikeParkingCount"
        var sBikeListStr = ""
        for bike in bikeList
        {
            sBikeListStr += "\(bike.Id),"
        }
        var params = [String]()
        params.append("bikeList=\(sBikeListStr)")
        
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(Tools.formatParamValue(params))")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateBikeRequest = HttpTools.getPostHttpRequest(retActionUrl, httpBody: httpBody)
            {
                NSURLConnection.sendAsynchronousRequest(updateBikeRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var stationCountDict:[Int:Int] = [:]
                        let jsonList = jsonRet["List"]
                        for jsonStation in jsonList
                        {
                            let Id = jsonStation.1["Stationid"].intValue
                            let Count = jsonStation.1["Count"].intValue
                            stationCountDict[Id] = Count
                        }
                        
                        let bikeListCount = bikeList.count
                        for bikeIndex in 0..<bikeListCount
                        {
                            if let rentCount = stationCountDict[bikeList[bikeIndex].Id]
                            {
                                bikeList[bikeIndex].RentCount = rentCount
                            }
                        }
                       
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updatePublicBikeReal(bikeList)
                        })
                        
                    }
                    else
                    {
                        updateVc.getPublicBikeError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    class func initPublicBikeList(updateVc:PublicBikeProtocol,actionUrl:String,httpBody:NSData)
    {
        if let publicBikeRequest = HttpTools.getPostHttpRequest(actionUrl, httpBody: httpBody)
        {
            NSURLConnection.sendAsynchronousRequest(publicBikeRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                if(error == nil)
                {
                    var alPublicBike = [PublicBike]()
                    var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                    let jsonList = jsonRet["StationList"]
                    for jsonStation in jsonList
                    {
                        let Id = jsonStation.1["Id"].intValue
                        let Name = jsonStation.1["Name"].stringValue
                        let Address = jsonStation.1["Address"].stringValue
                        let AreaName = jsonStation.1["AreaName"].stringValue
                        let Count = jsonStation.1["Count"].intValue
                        let IsAllDay = jsonStation.1["IsAllDay"].boolValue
                        let Latitude = jsonStation.1["Latitude"].doubleValue
                        let Longitude = jsonStation.1["Longitude"].doubleValue
                        let PersonDuty = jsonStation.1["PersonDuty"].boolValue
                        let RentCount = jsonStation.1["RentCount"].intValue
                        let ServiceTime = jsonStation.1["ServiceTime"].stringValue
                        let StationPhone = jsonStation.1["StationPhone"].stringValue
                        
                        let publicBikeTemp = PublicBike(Id: Id, Name: Name, Address: Address, AreaName: AreaName, Count: Count, IsAllDay: IsAllDay, Latitude: Latitude, Longitude: Longitude, PersonDuty: PersonDuty, RentCount: RentCount, ServiceTime: ServiceTime, StationPhone: StationPhone)
                        alPublicBike.append(publicBikeTemp)
                    }
                    
                    
                    dispatch_async(dispatch_get_main_queue(), { () -> Void in
                        updateVc.updatePublicBike(alPublicBike)
                    })
                    
                }
                else
                {
                    updateVc.getPublicBikeError(error!.localizedDescription)
                    print(error!.localizedDescription)
                }
            })
        }
    }
    
    class func initPublicBike(updateVc:PublicBikeProtocol,actionUrl:String,httpBody:NSData)
    {
        if let publicBikeRequest = HttpTools.getPostHttpRequest(actionUrl, httpBody: httpBody)
        {
            NSURLConnection.sendAsynchronousRequest(publicBikeRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                if(error == nil)
                {
                    var alPublicBike = [PublicBike]()
                    var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
          
                        let Id = jsonRet["Id"].intValue
                        let Name = jsonRet["Name"].stringValue
                        let Address = jsonRet["Address"].stringValue
                        let AreaName = jsonRet["AreaName"].stringValue
                        let Count = jsonRet["Count"].intValue
                        let IsAllDay = jsonRet["IsAllDay"].boolValue
                        let Latitude = jsonRet["Latitude"].doubleValue
                        let Longitude = jsonRet["Longitude"].doubleValue
                        let PersonDuty = jsonRet["PersonDuty"].boolValue
                        let RentCount = jsonRet["RentCount"].intValue
                        let ServiceTime = jsonRet["ServiceTime"].stringValue
                        let StationPhone = jsonRet["StationPhone"].stringValue
                        
                        let publicBikeTemp = PublicBike(Id: Id, Name: Name, Address: Address, AreaName: AreaName, Count: Count, IsAllDay: IsAllDay, Latitude: Latitude, Longitude: Longitude, PersonDuty: PersonDuty, RentCount: RentCount, ServiceTime: ServiceTime, StationPhone: StationPhone)
                        alPublicBike.append(publicBikeTemp)
                    
                    
                    dispatch_async(dispatch_get_main_queue(), { () -> Void in
                        updateVc.updatePublicBike(alPublicBike)
                    })
                    
                }
                else
                {
                    updateVc.getPublicBikeError(error!.localizedDescription)
                    print(error!.localizedDescription)
                }
            })
        }
    }

    
    func initPublicBike(updateVc:PublicBikeProtocol,actionUrl:String,httpBody:NSData)
    {
        if let publicBikeRequest = HttpTools.getPostHttpRequest(actionUrl, httpBody: httpBody)
        {
            NSURLConnection.sendAsynchronousRequest(publicBikeRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                if(error == nil)
                {
                    var jsonStation = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                    
                    self.Id = jsonStation["Id"].intValue
                    self.Name = jsonStation["Name"].stringValue
                    self.Address = jsonStation["Address"].stringValue
                    self.AreaName = jsonStation["AreaName"].stringValue
                    self.Count = jsonStation["Count"].intValue
                    self.IsAllDay = jsonStation["IsAllDay"].boolValue
                    self.Latitude = jsonStation["Latitude"].doubleValue
                    self.Longitude = jsonStation["Longitude"].doubleValue
                    self.PersonDuty = jsonStation["PersonDuty"].boolValue
                    self.RentCount = jsonStation["RentCount"].intValue
                    self.ServiceTime = jsonStation["ServiceTime"].stringValue
                    self.StationPhone = jsonStation["StationPhone"].stringValue
                    
                    dispatch_async(dispatch_get_main_queue(), { () -> Void in
                        updateVc.updatePublicBike(self)
                    })
                    
                }
                else
                {
                    updateVc.getPublicBikeError(error!.localizedDescription)
                    print(error!.localizedDescription)
                }
            })
        }
    }
    
    class func getIcon(isSelect:Bool,publicBike:PublicBike) -> String
    {
        if publicBike.RentCount == 0
        {
            if isSelect
            {
                return BikeIcon.noRentSelect.rawValue
            }
            else
            {
                return BikeIcon.noRent.rawValue
            }
        }
        else if publicBike.Count == publicBike.RentCount
        {
            if isSelect
            {
                return BikeIcon.noRevertSelect.rawValue
            }
            else
            {
                return BikeIcon.noRevert.rawValue
            }
        }
        else if (publicBike.RentCount * 100 / publicBike.Count <= nervousNum) || (publicBike.RentCount * 100 / publicBike.Count >= (100 - nervousNum))
        {
            if isSelect
            {
                return BikeIcon.nervousSelect.rawValue
            }
            else
            {
                return BikeIcon.nervous.rawValue
            }
        }
        else
        {
            if isSelect
            {
                return BikeIcon.normalSelect.rawValue
            }
            else
            {
                return BikeIcon.normal.rawValue
            }
        }
    }
    
    
    
}


protocol PublicBikeProtocol
{
    func getPublicBikeError(error:String)
    func updatePublicBikeReal(updatePublicBikes:[PublicBike])
    func updatePublicBike(updatePublicBike:PublicBike)
    func updatePublicBike(updatePublicBikes:[PublicBike])
}
