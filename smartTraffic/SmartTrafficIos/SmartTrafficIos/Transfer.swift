//
//  Transfer.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/11.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class Transfer
{
    static let defaultOrder = 1           //默认方案
    static let defaultCount = 10                //默认个数

    var Distance:Int
    var LineList:[String]
    var NewList:[NewListItem]
    var Price:Int
    var Priority:Int
    var Time:Int
    
    init(Distance:Int,LineList:[String],NewList:[NewListItem],Price:Int,Priority:Int,Time:Int)
    {
        self.Distance = Distance
        self.LineList = LineList
        self.NewList = NewList
        self.Price = Price
        self.Priority = Priority
        self.Time = Time
    }
    
    func formatLineListStr() -> String
    {
        var retStr = ""
        for line in LineList
        {
            if retStr != ""
            {
                retStr += " → "
            }
            retStr += line
        }
        return retStr
    }
    
    class func formatJson(TransferJson:JSON) -> Transfer
    {
        let Distance:Int = TransferJson["Distance"].intValue
        let LineList:[String] = NewListItem.formatString(TransferJson["LineList"].arrayValue)
        let NewList:[NewListItem] = NewListItem.formatJson(TransferJson["NewList"])
        let Price:Int = TransferJson["Price"].intValue
        let Priority:Int = TransferJson["Priority"].intValue
        let Time:Int = TransferJson["Time"].intValue
        
        let TransferTemp = Transfer(Distance: Distance, LineList: LineList, NewList: NewList, Price: Price, Priority: Priority, Time: Time)
        return TransferTemp
    }
    
    class func initTransfer(updateVc:TransferProtocol,startLontitude:Double,startLantitude:Double,endLontitude:Double,endLantitude:Double,order:Int,count:Int)
    {
        let actionName = "QueryOriginTrafficTrasfer"
        
        var params = [String]()
        params.append("startLontitude=\(startLontitude)")
        params.append("startLantitude=\(startLantitude)")
        params.append("endLontitude=\(endLontitude)")
        params.append("endLantitude=\(endLantitude)")
        params.append("order=\(order)")
        params.append("count=\(count)")
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
                        var transferList = [Transfer]()
                        var jsonRet = JSON(data: Tools.formatHangZhouThreeNetJson(data!), options: NSJSONReadingOptions.MutableContainers, error: nil)
                        let jsonList = jsonRet["List"]
                        
                        for listJson in jsonList
                        {
                            let transferTemp = Transfer.formatJson(listJson.1)
                            transferList.append(transferTemp)
                        }
                       
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateTransfer(transferList)
                        })
                        
                    }
                    else
                    {
                        updateVc.updateTransferError(error!.localizedDescription)
                        print(error!.localizedDescription)
                    }
                })
            }
        }

    }

}

class NewListItem
{
    var BikeStationAddress:String
    var BusArrive:[String]
    var Direction:String
    var Distance:Int
    var Longitude:Double
    var Latitude:Double
    var LineDetails:LineDetailsItem
    var LineTrailsList:[LineTrailListItem]
    var StationId:Int
    var StationName:String
    var StationType:Int
    var Time:Int
    var Type:Int
    
    init(BikeStationAddress:String,BusArrive:[String],Direction:String,Distance:Int,Longitude:Double,Latitude:Double,LineDetails:LineDetailsItem,LineTrailsList:[LineTrailListItem],StationId:Int,StationName:String,StationType:Int,Time:Int,Type:Int)
    {
        self.BikeStationAddress = BikeStationAddress
        self.BusArrive = BusArrive
        self.Direction = Direction
        self.Distance = Distance
        self.Longitude = Longitude
        self.Latitude = Latitude
        self.LineDetails = LineDetails
        self.LineTrailsList = LineTrailsList
        self.StationId = StationId
        self.StationName = StationName
        self.StationType = StationType
        self.Time = Time
        self.Type = Type
    }
    
    class func formatString(stringJson:[JSON]) -> [String]
    {
        var strArray = [String]()
        for str in stringJson
        {
            strArray.append(str.stringValue)
        }
        return strArray
    }
    
    class func formatJson(NewListItemJson:JSON) -> NewListItem
    {
//        NewListItemJson["BusArrive"].arrayValue
        let BikeStationAddress:String = NewListItemJson["BikeStationAddress"].stringValue
        let BusArrive:[String] = formatString(NewListItemJson["BusArrive"].arrayValue)
        let Direction:String = NewListItemJson["Direction"].stringValue
        let Distance:Int = NewListItemJson["Distance"].intValue
        let Longitude:Double = NewListItemJson["Longitude"].doubleValue
        let Latitude:Double = NewListItemJson["Latitude"].doubleValue
        let LineDetails:LineDetailsItem = LineDetailsItem.formatJson(NewListItemJson["LineDetails"])
        let LineTrailsList:[LineTrailListItem] = LineTrailListItem.formatJson(NewListItemJson["LineTrailsList"])
        let StationId:Int = NewListItemJson["StationId"].intValue
        let StationName:String = NewListItemJson["StationName"].stringValue
        let StationType:Int = NewListItemJson["StationType"].intValue
        let Time:Int = NewListItemJson["Time"].intValue
        let Type:Int = NewListItemJson["Type"].intValue
        
        let NewListItemTemp = NewListItem(BikeStationAddress: BikeStationAddress, BusArrive: BusArrive, Direction: Direction, Distance: Distance, Longitude: Longitude, Latitude: Latitude, LineDetails: LineDetails, LineTrailsList: LineTrailsList, StationId: StationId, StationName: StationName, StationType: StationType, Time: Time, Type: Type)
        return NewListItemTemp
    }
    
    class func formatJson(NewListItemJson:JSON) -> [NewListItem]
    {
        var NewListItemList = [NewListItem]()
        for index in NewListItemJson
        {
            let BikeStationAddress:String = index.1["BikeStationAddress"].stringValue
            let BusArrive:[String] = formatString(index.1["BusArrive"].arrayValue)
            let Direction:String = index.1["Direction"].stringValue
            let Distance:Int = index.1["Distance"].intValue
            let Longitude:Double = index.1["Longitude"].doubleValue
            let Latitude:Double = index.1["Latitude"].doubleValue
            let LineDetails:LineDetailsItem = LineDetailsItem.formatJson(index.1["LineDetails"])
            let LineTrailsList:[LineTrailListItem] = LineTrailListItem.formatJson(index.1["LineTrailsList"])
            let StationId:Int = index.1["StationId"].intValue
            let StationName:String = index.1["StationName"].stringValue
            let StationType:Int = index.1["StationType"].intValue
            let Time:Int = index.1["Time"].intValue
            let Type:Int = index.1["Type"].intValue
            
            let NewListItemTemp = NewListItem(BikeStationAddress: BikeStationAddress, BusArrive: BusArrive, Direction: Direction, Distance: Distance, Longitude: Longitude, Latitude: Latitude, LineDetails: LineDetails, LineTrailsList: LineTrailsList, StationId: StationId, StationName: StationName, StationType: StationType, Time: Time, Type: Type)
            NewListItemList.append(NewListItemTemp)
        }
       
        return NewListItemList
    }


    
}

class LineDetailsItem
{
    var Direct:Int
    var Distance:Int
    var EndTime:String
    var Id:Int
    var LineType:Int
    var Name:String
    var Price:Int
    var StartTime:String
    var StationCount:Int
    
    init(Direct:Int,Distance:Int,EndTime:String,Id:Int,LineType:Int,Name:String,Price:Int,StartTime:String,StationCount:Int)
    {
        self.Direct = Direct
        self.Distance = Distance
        self.EndTime = EndTime
        self.Id = Id
        self.LineType = LineType
        self.Name = Name
        self.Price = Price
        self.StartTime = StartTime
        self.StationCount = StationCount
    }
    
    class func formatJson(LineDetailsItemJson:JSON) -> LineDetailsItem
    {
        let Direct:Int = LineDetailsItemJson["Direct"].intValue
        let Distance:Int = LineDetailsItemJson["Distance"].intValue
        let EndTime:String = LineDetailsItemJson["EndTime"].stringValue
        let Id:Int = LineDetailsItemJson["Id"].intValue
        let LineType:Int = LineDetailsItemJson["LineType"].intValue
        let Name:String = LineDetailsItemJson["Name"].stringValue
        let Price:Int = LineDetailsItemJson["Price"].intValue
        let StartTime:String = LineDetailsItemJson["StartTime"].stringValue
        let StationCount:Int = LineDetailsItemJson["StationCount"].intValue
        
        let LineDetailsItemTemp = LineDetailsItem(Direct: Direct, Distance: Distance, EndTime: EndTime, Id: Id, LineType: LineType, Name: Name, Price: Price, StartTime: StartTime, StationCount: StationCount)
        return LineDetailsItemTemp
    }

}

class LineTrailListItem
{
    var Direct:Int
    var Latitude:Double
    var LineId:Int
    var Longitude:Double
    var TrailIndex:Int
    
    init(Direct:Int,Latitude:Double,LineId:Int,Longitude:Double,TrailIndex:Int)
    {
        self.Direct = Direct
        self.Latitude = Latitude
        self.LineId = LineId
        self.Longitude = Longitude
        self.TrailIndex = TrailIndex
    }
    
    class func formatJson(lineTrailListItemJson:JSON) -> LineTrailListItem
    {
        let Direct:Int = lineTrailListItemJson["Direct"].intValue
        let Latitude:Double = lineTrailListItemJson["Latitude"].doubleValue
        let LineId:Int = lineTrailListItemJson["LineId"].intValue
        let Longitude:Double = lineTrailListItemJson["Longitude"].doubleValue
        let TrailIndex:Int = lineTrailListItemJson["TrailIndex"].intValue
        
        let lineTrailListItemTemp = LineTrailListItem(Direct: Direct, Latitude: Latitude, LineId: LineId, Longitude: Longitude, TrailIndex: TrailIndex)
        return lineTrailListItemTemp
    }
    
    class func formatJson(lineTrailListItemJson:JSON) -> [LineTrailListItem]
    {
        var lineTrailListItemList = [LineTrailListItem]()
        for index in lineTrailListItemJson
        {
            let Direct:Int = index.1["Direct"].intValue
            let Latitude:Double = index.1["Latitude"].doubleValue
            let LineId:Int = index.1["LineId"].intValue
            let Longitude:Double = index.1["Longitude"].doubleValue
            let TrailIndex:Int = index.1["TrailIndex"].intValue
            
            let lineTrailListItemTemp = LineTrailListItem(Direct: Direct, Latitude: Latitude, LineId: LineId, Longitude: Longitude, TrailIndex: TrailIndex)
            lineTrailListItemList.append(lineTrailListItemTemp)
        }
        
        return lineTrailListItemList
    }
}

protocol TransferProtocol
{
    func updateTransfer(transfer:[Transfer])
    func updateTransferError(error:String)
}


