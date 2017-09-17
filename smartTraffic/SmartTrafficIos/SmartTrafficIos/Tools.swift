//
//  Tools.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/15.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation

class Tools {
    
    static var defLat:Double = 30.758
    static var defLon:Double = 120.749
    
    class func stringConvertToNSDate(timeString:String,formatString:String) -> NSDate?
    {
        let dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = formatString
        dateFormatter.timeZone = NSTimeZone(forSecondsFromGMT: 0)
        return dateFormatter.dateFromString(timeString)
    }
    
    class func nsdateConvertToString(dateTime:NSDate,formatString:String) -> String
    {
        let dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = formatString
        dateFormatter.timeZone = NSTimeZone(forSecondsFromGMT: 0)
        return dateFormatter.stringFromDate(dateTime)
    }
    
    class func formatParamValue(params:[String]) ->String
    {
        var retParam = ""
        for param in params
        {
            if retParam != ""
            {
                retParam += "&"
            }
            retParam += param
        }
        return retParam
    }
    
    class func formatHangZhouThreeNetJson(data:NSData) -> NSData
    {
        var dataString = NSString(data: data, encoding: NSUTF8StringEncoding)
        let subRange = NSMakeRange(1, dataString!.length-2)
        dataString = dataString?.substringWithRange(subRange)
        dataString = dataString?.stringByReplacingOccurrencesOfString("\\", withString: "")
        return dataString!.dataUsingEncoding(NSUTF8StringEncoding)!
    }

    class func findViewController(subView:UIView) -> UIViewController
    {
        for var next:UIView = (subView.superview! as UIView) ; ; next = next.superview! as UIView
        {
            let nextResponder = next.nextResponder()! as UIResponder
            if (nextResponder is UIViewController)
            {
                return nextResponder as! UIViewController
            }
        }
    }
    
    class func checkIsExist(array:[String],item:String) -> Bool
    {
        var bIsExist = false
        for arrayItem in array
        {
            if arrayItem == item
            {
                bIsExist = true
                break
            }
        }
        return bIsExist
    }
    
    class func versionCheck() -> String
    {
        
        let infoDictionary = NSBundle.mainBundle().infoDictionary
        
//        let appDisplayName: AnyObject? = infoDictionary!["CFBundleDisplayName"]
        
        let majorVersion : AnyObject? = infoDictionary! ["CFBundleShortVersionString"]
        
//        let minorVersion : AnyObject? = infoDictionary! ["CFBundleVersion"]
        
        let appversion = majorVersion as! String
        
        //        let iosversion : NSString = UIDevice.currentDevice().systemVersion   //ios 版本
        //
        //        let identifierNumber = UIDevice.currentDevice().identifierForVendor   //设备 udid
        //
        //        let systemName = UIDevice.currentDevice().systemName   //设备名称
        //
        //        let model = UIDevice.currentDevice().model   //设备型号
        //
        //        let localizedModel = UIDevice.currentDevice().localizedModel   //设备区域化型号 如 A1533
        
        
        
        
        print(appversion)
        return appversion
    }

    
}
