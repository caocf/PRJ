//
//  MergedConRtic.swift
//  SmartTrafficIos
//  拥堵路段model类
//  Created by EastMac on 15/4/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON


class MergedConRtic          //通阻信息
{
    static let actionName = "conanalyse/queryMergedConRticByTime"
    
    var roadName:String     //拥堵路段
    var rsStart:String      //拥堵路段开始路名
    var rsEnd:String        //拥堵路段结束路名
    var desc:String         //拥堵状况描述

    init(roadName:String,rsStart:String,rsEnd:String,desc:String)
    {
        self.roadName = roadName
        self.rsStart = rsStart
        self.rsEnd = rsEnd
        self.desc = desc
    }
    

    class func getActionUrl() -> String
    {
        var retActionUrl = ""
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName,actionName: actionName)
        {
            retActionUrl = "\(sServerUrl)?plan=3&timeType=1".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet)!
        }
        return retActionUrl
    }
    
    class func initMergedConRticData(data:NSData) -> (Bool,String?,[MergedConRtic]?)
    {
        var retMergedConRtic = [MergedConRtic]()
        var retStatus:Bool = false
        var jsonRet = JSON(data: data, options: NSJSONReadingOptions.MutableContainers, error: nil)
        if jsonRet["status"].intValue == 2000
        {
            retStatus = true
        }
        let retMsg = jsonRet["msg"].stringValue
        for listItem in jsonRet["mergedConRtics"]
        {
            let roadName = listItem.1["roadName"]
            let desc = listItem.1["roadSections"][0]["desc"]
            let rsStart = listItem.1["roadSections"][0]["rsStart"]
            let rsEnd = listItem.1["roadSections"][0]["rsEnd"]
            let mergedConRtic = MergedConRtic(roadName: roadName.stringValue, rsStart: rsStart.stringValue, rsEnd: rsEnd.stringValue, desc: desc.stringValue)
            retMergedConRtic.append(mergedConRtic)
        }
        return (retStatus,retMsg,retMergedConRtic)
    }
    
}

