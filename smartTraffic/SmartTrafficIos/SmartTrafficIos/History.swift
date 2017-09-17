//
//  History.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/20.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
class History
{
    enum HistoryType:String
    {
        case Parking = "Parking"
        case PublicBike = "PublicBike"
        case BusLine = "BusLine"
        case BusStation = "BusStation"
        case BusTransfer = "BusTransfer"
    }
    static func readHistory(historyName:String)->[String]?
    {
        var paths = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)
        let docDir:String? = paths[0]

        if let newPathStr = docDir //docDir可能为nil
        {
            let fileStr = (newPathStr as NSString).stringByAppendingPathComponent("History.plist")
            let dict = NSDictionary(contentsOfFile: fileStr)
            if (dict != nil)
            {
                let history = dict?.valueForKey(historyName) as? [String]
                return history
            }
            else
            {
                print("没有找到文件")
                return nil
            }
        }
        else
        {
            print("没有找到目录")
        }
        return nil
    }
    
    static func writeHistory(historyName:String,history:[String]) -> Bool
    {
        var paths = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)
        let docDir:String? = paths[0]
        if let newPathStr = docDir //pathStr可能为nil
        {
            let fileStr = (newPathStr as NSString).stringByAppendingPathComponent("History.plist")
            var dict = NSDictionary(contentsOfFile: fileStr)
            if (dict != nil)
            {
                dict!.setValue(history, forKey: historyName)
            }
            else
            {
                dict = [historyName:history]
            }
            return dict!.writeToFile(fileStr, atomically: true)
        }
        else
        {
            print("没有找到目录")
        }
        
        return false
    }
    
    
    
}