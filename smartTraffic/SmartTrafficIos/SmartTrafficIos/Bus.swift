//
//  Bus.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/29.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON


class BusStation {
    static let defaultDistance = 500           //默认搜索范围
    static let extendDistance = 2000            //扩展搜索范围
    static let defaultCount = 20                //默认搜索个数
    
    enum BusStationIcon:String
    {
        case normal = "红色.png"
        case select = "红色_big.png"
    }
    
    
    class StationLine {
        var Id = -1
        var Direct = -1
        var EndStationId = -1
        var EndStationName = ""
        var EndTime = ""
        var IcCard = -1
        var IsRing = false
        var LineType = ""
        var Name = ""
        var NormalPrice = -1
        var Price = -1
        var Remark = ""
        var SeasonPrice = -1
        var StartDistance = -1
        var StartStationId = -1
        var StartStationName = ""
        var StartTime = ""
        var TicketType = -1
        var Time = ""
        var busInfo:[BusLocation.BusInfo] = []
        
        init(Id:Int,Name:String,Direct:Int,EndStationId:Int,EndStationName:String,EndTime:String,IcCard:Int,IsRing:Bool,LineType:String,NormalPrice:Int,Price:Int,Remark:String,SeasonPrice:Int,StartDistance:Int,StartStationId:Int,StartStationName:String,StartTime:String,TicketType:Int,Time:String,busInfo:[BusLocation.BusInfo])
        {
            self.Id = Id
            self.Name = Name
            self.Direct = Direct
            self.EndStationId = EndStationId
            self.EndStationName = EndStationName
            self.EndTime = EndTime
            self.IcCard = IcCard
            self.IsRing = IsRing
            self.LineType = LineType
            self.NormalPrice = NormalPrice
            self.Price = Price
            self.Remark = Remark
            self.SeasonPrice = SeasonPrice
            self.StartDistance = StartDistance
            self.StartStationId = StartStationId
            self.StartStationName = StartStationName
            self.StartTime = StartTime
            self.TicketType = TicketType
            self.Time = Time
            self.busInfo = busInfo
        }
        
        class func formatJsonStationLine(stationLine:JSON) -> StationLine
        {
            let Id = stationLine["Id"].intValue
            let Direct = stationLine["Direct"].intValue
            let EndStationId = stationLine["EndStationId"].intValue
            let EndStationName = stationLine["EndStationName"].stringValue
            let EndTime = stationLine["EndTime"].stringValue
            let IcCard = stationLine["IcCard"].intValue
            let IsRing = stationLine["IsRing"].boolValue
            let LineType = stationLine["LineType"].stringValue
            let Name = stationLine["Name"].stringValue
            let NormalPrice = stationLine["NormalPrice"].intValue
            let Price = stationLine["Price"].intValue
            let Remark = stationLine["Remark"].stringValue
            let SeasonPrice = stationLine["SeasonPrice"].intValue
            let StartDistance = stationLine["StartDistance"].intValue
            let StartStationId = stationLine["StartStationId"].intValue
            let StartStationName = stationLine["StartStationName"].stringValue
            let StartTime = stationLine["StartTime"].stringValue
            let TicketType = stationLine["TicketType"].intValue
            let Time = stationLine["Time"].stringValue
            var busInfoList:[BusLocation.BusInfo] = []
            
            let jsonBusInfoList = stationLine["BusLocationList"]
            for busInfoTemp in jsonBusInfoList
            {
                let busInfo = BusLocation.BusInfo.formatJsonBusInfo(busInfoTemp.1)
                busInfoList.append(busInfo)
            }
            
            return StationLine(Id: Id, Name: Name, Direct: Direct, EndStationId: EndStationId, EndStationName: EndStationName, EndTime: EndTime, IcCard: IcCard, IsRing: IsRing, LineType: LineType, NormalPrice: NormalPrice, Price: Price, Remark: Remark, SeasonPrice: SeasonPrice, StartDistance: StartDistance, StartStationId: StartStationId, StartStationName: StartStationName, StartTime: StartTime, TicketType: TicketType, Time: Time,busInfo: busInfoList)

        }
    }
    
    var Id = -1
    var Latitude = 0.0
    var Longitude = 0.0
    var Name = ""
    var QrCode = ""
    var Rename = ""
    var StationType = -1
    var lines:[StationLine]
    
    
    init(Id:Int,Latitude:Double,Longitude:Double,Name:String,QrCode:String,Rename:String,StationType:Int,lines:[StationLine])
    {
        self.Id = Id
        self.Latitude = Latitude
        self.Longitude = Longitude
        self.Name = Name
        self.QrCode = QrCode
        self.Rename = Rename
        self.StationType = StationType
        self.lines = lines
    }
    
    
    class func initBusStation(updateVc:BusProtocol,stationID:Int)
    {
        
        let actionName = "QueryOriginBusStationByID"
        
        var params = [String]()
        params.append("stationID=\(stationID)")
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
                        var busStationList = [BusStation]()
                        var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonList = jsonRet["Station"]
                        
                        let Id = jsonList["Id"].intValue
                        let Latitude = jsonList["Latitude"].doubleValue
                        let Longitude = jsonList["Longitude"].doubleValue
                        let Name = jsonList["Name"].stringValue
                        let QrCode = jsonList["QrCode"].stringValue
                        let Rename = jsonList["Rename"].stringValue
                        let StationType = jsonList["StationType"].intValue
                        
                        var lines = [StationLine]()
                        for lineJson in jsonRet["LineList"]
                        {
                            lines.append(StationLine.formatJsonStationLine(lineJson.1))
                        }
                        
                        //获取相关线路
                        let busStation = BusStation(Id: Id, Latitude: Latitude, Longitude: Longitude, Name: Name, QrCode: QrCode, Rename: Rename, StationType: StationType, lines: lines)
                        
                        busStationList.append(busStation)
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateBusStationList(busStationList)
                        })
                        
                    }
                    else
                    {
                        updateVc.updateBusInfoError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    
    
    class func initBusStation(updateVc:BusProtocol,stationName:String)
    {
        
        let actionName = "QueryOriginBusStation"
        
        var params = [String]()
        params.append("stationName=\(stationName)")
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
                        var busStationList = [BusStation]()
                        var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                        let jsonList = jsonRet["StationList"]
                        for jsonStation in jsonList
                        {
                            let Id = jsonStation.1["Id"].intValue
                            let Latitude = jsonStation.1["Latitude"].doubleValue
                            let Longitude = jsonStation.1["Longitude"].doubleValue
                            let Name = jsonStation.1["Name"].stringValue
                            let QrCode = jsonStation.1["QrCode"].stringValue
                            let Rename = jsonStation.1["Rename"].stringValue
                            let StationType = jsonStation.1["StationType"].intValue
                            
                            var lines = [StationLine]()
                            for (_,lineJson) in jsonStation.1["List"]
                            {
                                lines.append(StationLine.formatJsonStationLine(lineJson))
                            }
                            
                            //获取相关线路
                            let busStation = BusStation(Id: Id, Latitude: Latitude, Longitude: Longitude, Name: Name, QrCode: QrCode, Rename: Rename, StationType: StationType, lines: lines)
                            
                            busStationList.append(busStation)
                            
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateBusStationList(busStationList)
                        })
                        
                    }
                    else
                    {
                        updateVc.updateBusInfoError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    class func initBusStation(updateVc:BusProtocol,longtitude:Double,lantitude:Double,distance:Int)
    {
        
        let actionName = "QueryOriginNearByBusStation"
        
        var params = [String]()
        params.append("longtitude=\(longtitude)")
        params.append("lantitude=\(lantitude)")
        params.append("distance=\(distance)")
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
                        var busStationList = [BusStation]()
                        var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                        let jsonList = jsonRet["StationList"]
                        for jsonStation in jsonList
                        {
                            let Id = jsonStation.1["Id"].intValue
                            let Latitude = jsonStation.1["Latitude"].doubleValue
                            let Longitude = jsonStation.1["Longitude"].doubleValue
                            let Name = jsonStation.1["Name"].stringValue
                            let QrCode = jsonStation.1["QrCode"].stringValue
                            let Rename = jsonStation.1["Rename"].stringValue
                            let StationType = jsonStation.1["StationType"].intValue
                            
                            var lines = [StationLine]()
                            for (_,lineJson) in jsonStation.1["List"]
                            {
                                lines.append(StationLine.formatJsonStationLine(lineJson))
                            }
                            
                            //获取相关线路
                            let busStation = BusStation(Id: Id, Latitude: Latitude, Longitude: Longitude, Name: Name, QrCode: QrCode, Rename: Rename, StationType: StationType, lines: lines)
                            
                            busStationList.append(busStation)
                            
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateBusStationList(busStationList)
                        })
                        
                    }
                    else
                    {
                        updateVc.updateBusInfoError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    func getAllLines() -> String
    {
        var sLineStr = ""
        for line in lines
        {
            if sLineStr != ""
            {
                sLineStr += ","
            }
            sLineStr += line.Name
        }
        return sLineStr
    }
    
    
}

class BusLine
{
    static let upLine = 1
    static let downLine = 2
    
    class LineStation
    {
        var StationId = -1
        var StationName = ""
        var StationIndex = -1
        var StationType = -1
        var StartTime = ""
        var EndTime = ""
        var Longitude = 0.0
        var Latitude = 0.0
        var BikeList = [PublicBike]()
        
        init(StationId:Int,StationName:String,StationIndex:Int,StationType:Int,StartTime:String,EndTime:String,Longitude:Double,Latitude:Double,BikeList:[PublicBike])
        {
            self.StationId = StationId
            self.StationName = StationName
            self.StationIndex = StationIndex
            self.StationType = StationType
            self.StartTime = StartTime
            self.EndTime = EndTime
            self.Longitude = Longitude
            self.Latitude = Latitude
            self.BikeList = BikeList
        }
        
        class func formatJsonLineStation(lineStation:JSON) -> [LineStation]
        {
            var lineStationList = [LineStation]()
            for lineStationTemp in lineStation
            {
                let StationId = lineStationTemp.1["StationId"].intValue
                let StationName = lineStationTemp.1["StationName"].stringValue
                let StationIndex = lineStationTemp.1["StationIndex"].intValue
                let StationType = lineStationTemp.1["StationType"].intValue
                let StartTime = lineStationTemp.1["StartTime"].stringValue
                let EndTime = lineStationTemp.1["EndTime"].stringValue
                let Longitude = lineStationTemp.1["Longitude"].doubleValue
                let Latitude = lineStationTemp.1["Latitude"].doubleValue
                let BikeList = PublicBike.formatJsonPublicBike(lineStationTemp.1["List"])
                
                let lineStation = LineStation(StationId: StationId, StationName: StationName, StationIndex: StationIndex, StationType: StationType, StartTime: StartTime, EndTime: EndTime, Longitude: Longitude, Latitude: Latitude, BikeList: BikeList)
                
                lineStationList.append(lineStation)
                
            }
            return lineStationList
        }
        
    }
    
    var Id = 0
    var Name = ""
    var ShortName = ""
    var UpEndStationId = 0
    var UpEndStationName = ""
    var UpStartStationId = 0
    var UpStartStationName = ""
    var DownEndStationId = 0
    var DownEndStationName = ""
    var DownStartStationId = 0
    var DownStartStationName = ""
    var LineDownEndTime = ""
    var LineDownStartTime = ""
    var LineType = ""
    var LineUpEndTime = ""
    var LineUpStartTime = ""
    var LinedownIntervalTime = 0
    var LinedownLength = 0
    var LineupIntervalTime = 0
    var LineupLength = 0
    var Price = 0
    var Remark = ""
    var TicketType = 0
    var IcCard = ""
    var IsRing = false
    var NormalPrice = 0
    var SeasonPrice = 0
    var DownList:[LineStation]
    var UpList:[LineStation]

    
    init(Id:Int,Name:String,ShortName:String,UpEndStationId:Int,UpEndStationName:String,UpStartStationId:Int,UpStartStationName:String,DownEndStationId:Int,DownEndStationName :String,DownStartStationId :Int,DownStartStationName :String,LineDownEndTime:String,LineDownStartTime:String,LineType:String,LineUpEndTime:String,LineUpStartTime:String,LinedownIntervalTime:Int,LinedownLength:Int,LineupIntervalTime:Int,LineupLength:Int,Price:Int,Remark:String,TicketType:Int,IcCard:String,IsRing:Bool,NormalPrice:Int,SeasonPrice:Int)
    {
        self.Id = Id
        self.Name = Name
        self.ShortName = ShortName
        self.UpEndStationId = UpEndStationId
        self.UpEndStationName = UpEndStationName
        self.UpStartStationId = UpStartStationId
        self.UpStartStationName = UpStartStationName
        self.DownEndStationId = DownEndStationId
        self.DownEndStationName = DownEndStationName
        self.DownStartStationId = DownStartStationId
        self.DownStartStationName = DownStartStationName
        self.LineDownEndTime = LineDownEndTime
        self.LineDownStartTime = LineDownStartTime
        self.LineType = LineType
        self.LineUpEndTime = LineUpEndTime
        self.LineUpStartTime = LineUpStartTime
        self.LinedownIntervalTime = LinedownIntervalTime
        self.LinedownLength = LinedownLength
        self.LineupIntervalTime = LineupIntervalTime
        self.LineupLength = LineupLength
        self.Price = Price
        self.Remark = Remark
        self.TicketType = TicketType
        self.IcCard = IcCard
        self.IsRing = IsRing
        self.NormalPrice = NormalPrice
        self.SeasonPrice = SeasonPrice
        self.DownList = [LineStation]()
        self.UpList = [LineStation]()
    }
    
    
    class func initBusLine(updateVc:BusProtocol,lineName:String)
    {
        
        let actionName = "QueryOriginBusLine"
        
        var params = [String]()
        params.append("lineName=\(lineName)")
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
                        var busLineList = [BusLine]()
                        var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                        let jsonList = jsonRet["LineList"]
                        
                        for jsonLine in jsonList
                        {
                            let Id = jsonLine.1["Id"].intValue
                            let Name = jsonLine.1["Name"].stringValue
                            let ShortName = jsonLine.1["ShortName"].stringValue
                            let UpEndStationId = jsonLine.1["UpEndStationId"].intValue
                            let UpEndStationName = jsonLine.1["UpEndStationName"].stringValue
                            let UpStartStationId = jsonLine.1["UpStartStationId"].intValue
                            let UpStartStationName = jsonLine.1["UpStartStationName"].stringValue
                            let DownEndStationId = jsonLine.1["DownEndStationId"].intValue
                            let DownEndStationName = jsonLine.1["DownEndStationName"].stringValue
                            let DownStartStationId = jsonLine.1["DownStartStationId"].intValue
                            let DownStartStationName = jsonLine.1["DownStartStationName"].stringValue
                            let LineDownEndTime = jsonLine.1["LineDownEndTime"].stringValue
                            let LineDownStartTime = jsonLine.1["LineDownStartTime"].stringValue
                            let LineType = jsonLine.1["LineType"].stringValue
                            let LineUpEndTime = jsonLine.1["LineUpEndTime"].stringValue
                            let LineUpStartTime = jsonLine.1["LineUpStartTime"].stringValue
                            let LinedownIntervalTime = jsonLine.1["LinedownIntervalTime"].intValue
                            let LinedownLength = jsonLine.1["LinedownLength"].intValue
                            let LineupIntervalTime = jsonLine.1["LineupIntervalTime"].intValue
                            let LineupLength = jsonLine.1["LineupLength"].intValue
                            let Price = jsonLine.1["Price"].intValue
                            let Remark = jsonLine.1["Remark"].stringValue
                            let TicketType = jsonLine.1["TicketType"].intValue
                            let IcCard = jsonLine.1["IcCard"].stringValue
                            let IsRing = jsonLine.1["IsRing"].boolValue
                            let NormalPrice = jsonLine.1["NormalPrice"].intValue
                            let SeasonPrice = jsonLine.1["SeasonPrice"].intValue
                            
                            let busLine = BusLine(Id: Id, Name: Name, ShortName: ShortName, UpEndStationId: UpEndStationId, UpEndStationName: UpEndStationName, UpStartStationId: UpStartStationId, UpStartStationName: UpStartStationName, DownEndStationId: DownEndStationId, DownEndStationName: DownEndStationName, DownStartStationId: DownStartStationId, DownStartStationName: DownStartStationName, LineDownEndTime: LineDownEndTime, LineDownStartTime: LineDownStartTime, LineType: LineType, LineUpEndTime: LineUpEndTime, LineUpStartTime: LineUpStartTime, LinedownIntervalTime: LinedownIntervalTime, LinedownLength: LinedownLength, LineupIntervalTime: LineupIntervalTime, LineupLength: LineupLength, Price: Price, Remark: Remark, TicketType: TicketType, IcCard: IcCard, IsRing: IsRing, NormalPrice: NormalPrice, SeasonPrice: SeasonPrice)
                            
                            busLineList.append(busLine)
                        }
                        
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateBusLineList(busLineList)
                        })
                        
                    }
                    else
                    {
                        updateVc.updateBusInfoError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    class func initBusLine(updateVc:BusProtocol,lineID:Int,direct:Int)
    {
        
        let actionName = "QueryOriginBusLineByID"
        
        var params = [String]()
        params.append("lineID=\(lineID)")
        params.append("direct=\(direct)")
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
                        var busLineList = [BusLine]()
                        var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonLine = jsonRet["Line"]
                        
                        
                        let Id = jsonLine["Id"].intValue
                        let Name = jsonLine["Name"].stringValue
                        let ShortName = jsonLine["ShortName"].stringValue
                        let UpEndStationId = jsonLine["UpEndStationId"].intValue
                        let UpEndStationName = jsonLine["UpEndStationName"].stringValue
                        let UpStartStationId = jsonLine["UpStartStationId"].intValue
                        let UpStartStationName = jsonLine["UpStartStationName"].stringValue
                        let DownEndStationId = jsonLine["DownEndStationId"].intValue
                        let DownEndStationName = jsonLine["DownEndStationName"].stringValue
                        let DownStartStationId = jsonLine["DownStartStationId"].intValue
                        let DownStartStationName = jsonLine["DownStartStationName"].stringValue
                        let LineDownEndTime = jsonLine["LineDownEndTime"].stringValue
                        let LineDownStartTime = jsonLine["LineDownStartTime"].stringValue
                        let LineType = jsonLine["LineType"].stringValue
                        let LineUpEndTime = jsonLine["LineUpEndTime"].stringValue
                        let LineUpStartTime = jsonLine["LineUpStartTime"].stringValue
                        let LinedownIntervalTime = jsonLine["LinedownIntervalTime"].intValue
                        let LinedownLength = jsonLine["LinedownLength"].intValue
                        let LineupIntervalTime = jsonLine["LineupIntervalTime"].intValue
                        let LineupLength = jsonLine["LineupLength"].intValue
                        let Price = jsonLine["Price"].intValue
                        let Remark = jsonLine["Remark"].stringValue
                        let TicketType = jsonLine["TicketType"].intValue
                        let IcCard = jsonLine["IcCard"].stringValue
                        let IsRing = jsonLine["IsRing"].boolValue
                        let NormalPrice = jsonLine["NormalPrice"].intValue
                        let SeasonPrice = jsonLine["SeasonPrice"].intValue
                        
                        let busLine = BusLine(Id: Id, Name: Name, ShortName: ShortName, UpEndStationId: UpEndStationId, UpEndStationName: UpEndStationName, UpStartStationId: UpStartStationId, UpStartStationName: UpStartStationName, DownEndStationId: DownEndStationId, DownEndStationName: DownEndStationName, DownStartStationId: DownStartStationId, DownStartStationName: DownStartStationName, LineDownEndTime: LineDownEndTime, LineDownStartTime: LineDownStartTime, LineType: LineType, LineUpEndTime: LineUpEndTime, LineUpStartTime: LineUpStartTime, LinedownIntervalTime: LinedownIntervalTime, LinedownLength: LinedownLength, LineupIntervalTime: LineupIntervalTime, LineupLength: LineupLength, Price: Price, Remark: Remark, TicketType: TicketType, IcCard: IcCard, IsRing: IsRing, NormalPrice: NormalPrice, SeasonPrice: SeasonPrice)
                        
                        busLine.UpList = LineStation.formatJsonLineStation(jsonRet["UpList"])
                        busLine.DownList = LineStation.formatJsonLineStation(jsonRet["DownList"])
                        busLineList.append(busLine)
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateBusLineList(busLineList)
                        })
                        
                    }
                    else
                    {
                        updateVc.updateBusInfoError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    
}


class BusCoordinate
{
    var coordinate:String = ""
    var coordinateId:Int = -1
    var coordinateType:Int = -1
    var routeId:Int = -1
    var upDown:Int = -1
    
    init(coordinate:String,coordinateId:Int,coordinateType:Int,routeId:Int,upDown:Int)
    {
        self.coordinate = coordinate
        self.coordinateId = coordinateId
        self.coordinateType = coordinateType
        self.routeId = routeId
        self.upDown = upDown
    }
    
    
    class func initBusCoordinate(updateVc:BusProtocol,lineID:Int,direct:Int)
    {
        
        let actionName = "QueryOriginLineTrack"
        
        var params = [String]()
        params.append("lineID=\(lineID)")
        params.append("direct=\(direct)")
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
                        var jsonCoordinate = jsonRet["busCoordinate"]
                       
                        let coordinate = jsonCoordinate["coordinate"].stringValue
                        let coordinateId = jsonCoordinate["coordinateId"].intValue
                        let coordinateType = jsonCoordinate["coordinateType"].intValue
                        let routeId = jsonCoordinate["routeId"].intValue
                        let upDown = jsonCoordinate["upDown"].intValue
                        
                        let busCoordinate = BusCoordinate(coordinate: coordinate, coordinateId: coordinateId, coordinateType: coordinateType, routeId: routeId, upDown: upDown)
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateBusCoordinate(busCoordinate)
                        })
                        
                    }
                    else
                    {
                        updateVc.updateBusInfoError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
}

class BusLocation
{
    static let defaultCount = 99
    
    class BusInfo {
    
        
        var CarNumber:String = ""
        var CongestionDegree:Int = 0
        var Distance:Int = 0
        var DriveState:Int = 0
        var StationId:Int = -1
        var StationIndex:Int = -1
        var StationName:String = ""
    
        init(CarNumber:String,CongestionDegree:Int,Distance:Int,DriveState:Int,StationId:Int,StationIndex:Int,StationName:String)
        {
            self.CarNumber = CarNumber
            self.CongestionDegree = CongestionDegree
            self.Distance = Distance
            self.DriveState = DriveState
            self.StationId = StationId
            self.StationIndex = StationIndex
            self.StationName = StationName
        }
        
        class func getDriverStatus(driverStatus:Int) -> (String,UIColor)
        {
            var driverStatusStr = ["未知","畅通","正常","拥堵","非常拥堵"]
            var driverStatusColor = [UIColor.whiteColor(),UIColor.greenColor(),UIColor.orangeColor(),UIColor.redColor()]
            return (driverStatusStr[driverStatus],driverStatusColor[driverStatus])
        }
    
        
        class func formatJsonBusInfo(JsonBusInfo:JSON) -> BusInfo
        {
            let CarNumber:String = JsonBusInfo["CarNumber"].stringValue
            let CongestionDegree:Int = JsonBusInfo["CongestionDegree"].intValue
            let Distance:Int = JsonBusInfo["Distance"].intValue
            let DriveState:Int = JsonBusInfo["DriveState"].intValue
            let StationId:Int = JsonBusInfo["StationId"].intValue
            let StationIndex:Int = JsonBusInfo["StationIndex"].intValue
            let StationName:String = JsonBusInfo["StationName"].stringValue
            
            let busInfo = BusInfo(CarNumber: CarNumber, CongestionDegree: CongestionDegree, Distance: Distance, DriveState: DriveState, StationId: StationId, StationIndex: StationIndex, StationName: StationName)
            return busInfo
        }

    }
    
    var lineID:Int = -1
    var stationID:Int = -1
    var direct:Int = -1
    var busInfo:[BusInfo] = [BusInfo]()
    var nextTime:String = ""
    
    init(lineID:Int,stationID:Int,direct:Int,busInfo:[BusInfo],nextTime:String)
    {
        self.lineID = lineID
        self.stationID = stationID
        self.direct = direct
        self.busInfo = busInfo
        self.nextTime = nextTime
    }
    
    class func initBusLocation(updateVc:BusProtocol,lineID:Int,direct:Int,count:Int,stationID:Int)
    {
        
        let actionName = "QueryOriginBusArrive"
        
        var params = [String]()
        params.append("lineID=\(lineID)")
        params.append("direct=\(direct)")
        params.append("count=\(count)")
        params.append("stationID=\(stationID)")
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
                        var busInfoList = [BusInfo]()
                        var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                        
                        let jsonBusInfoList = jsonRet["BusLocationList"]
                        for busInfoTemp in jsonBusInfoList
                        {
                            let busInfo = BusInfo.formatJsonBusInfo(busInfoTemp.1)
                            busInfoList.append(busInfo)
                        }
                        let busNextTime:String = jsonRet["Time"].stringValue

                        let busLocation = BusLocation(lineID: lineID, stationID: stationID, direct: direct, busInfo: busInfoList, nextTime: busNextTime)
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateBusLocation(busLocation)
                        })
                        
                    }
                    else
                    {
                        updateVc.updateBusInfoError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }

    
}

class ShowBusLine {
    var Id:Int = -1
    var Name:String = ""
    var StartName:String = ""
    var EndName:String = ""
    var Direct:Int = 0
    var IsRing:Bool = false
    
    
    init(Id:Int,Name:String,StartName:String,EndName:String,Direct:Int,IsRing:Bool)
    {
        self.Id = Id
        self.Name = Name
        self.StartName = StartName
        self.EndName = EndName
        self.Direct = Direct
        self.IsRing = IsRing
    }
}



protocol BusProtocol
{
    func updateBusLineList(busLineList:[BusLine])
    func updateBusStationList(busStationList:[BusStation])
    func updateBusCoordinate(busCoordinate:BusCoordinate)
    func updateBusLocation(busLocation:BusLocation)
    func updateBusInfoError(error:String)
}