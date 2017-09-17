//
//  Statement.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/27.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
class Statement
{
    static func checkStatementStatus()->Bool?
    {
        var paths = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)
        let docDir:String? = paths[0]

//        var pathStr = NSBundle.mainBundle().pathForResource("CommonConfig", ofType:"plist")
        if let newPathStr = docDir //pathStr可能为nil
        {
            let fileStr = (newPathStr as NSString).stringByAppendingPathComponent("CommonConfig.plist")

            let dict = NSDictionary(contentsOfFile: fileStr)
            if (dict != nil)
            {
                let ReadStatement = dict?.valueForKey("ReadStatement") as? Bool
                return ReadStatement
            }
            else
            {
                print("没有找到文件")
                return false
            }
        }
        return false
    }
    
    static func writeStatementStatus()
    {
        var paths = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)
        let docDir:String? = paths[0]
        if let newPathStr = docDir //pathStr可能为nil
        {
            let fileStr = (newPathStr as NSString).stringByAppendingPathComponent("CommonConfig.plist")
            var dict = NSMutableDictionary(contentsOfFile: fileStr)
            if (dict != nil)
            {
                dict!.setValue(true, forKey: "ReadStatement")
            }
            else
            {
                dict = ["ReadStatement":true]
            }
            dict!.writeToFile(fileStr, atomically: true)

        }
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
    


    class func getStatementInfo(updateVc:StatementDelegate)
    {
        let actionName = "QueryStatement"
        
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:"")
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        let statementString:String = NSString(data: data!, encoding: NSUTF8StringEncoding)! as String
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateGetStatementInfo(true, info: statementString)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateGetStatementInfo(false, info: error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
}

protocol StatementDelegate
{
    func updateGetStatementInfo(status:Bool,info:String)
}