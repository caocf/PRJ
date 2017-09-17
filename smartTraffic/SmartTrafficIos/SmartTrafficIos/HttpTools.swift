//
//  HttpTools.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation

class HttpTools
{
    static let defaultServerName = "mainServer"
    static let jxtpiServerName = "jxtpiServer"
    
    class func getHttpRequest(url:String) -> NSURLRequest?
    {
        var retRequest:NSURLRequest?
        if let nsurl = NSURL(string: url)
        {
            retRequest = NSURLRequest(URL: nsurl, cachePolicy: NSURLRequestCachePolicy.ReloadIgnoringLocalCacheData, timeoutInterval: 5.0)
        }
        return retRequest
    }
    
    class func getHttpRequest(url:String,paramValue:String) -> NSURLRequest?
    {
        var retRequest:NSURLRequest?
        if let nsurl = NSURL(string: "\(url)?\(paramValue)")
        {
            retRequest = NSURLRequest(URL: nsurl, cachePolicy: NSURLRequestCachePolicy.ReloadIgnoringLocalCacheData, timeoutInterval: 5.0)
        }
        return retRequest
    }
    
    class func getPostHttpRequest(url:String,httpBody:NSData) -> NSURLRequest?
    {
        var retRequest:NSMutableURLRequest?
        if let nsurl = NSURL(string: url)
        {
            retRequest = NSMutableURLRequest(URL: nsurl)
            retRequest?.addValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
            retRequest?.HTTPMethod = "POST"
            retRequest?.HTTPBody = httpBody
        }
        return retRequest
    }
    
    class func getUrlPath(serverName:String)->String?
    {
        var sUrl:String?
        let pathStr = NSBundle.mainBundle().pathForResource("CommonConfig", ofType:"plist")
        if let newPathStr = pathStr //pathStr可能为nil
        {
            let dict = NSDictionary(contentsOfFile: newPathStr)?.valueForKey(serverName) as! NSDictionary
            let serverAddress = dict.valueForKey("serverAddress") as! String
            let serverPort = dict.valueForKey("serverPort") as! Int
            let serverProjectName = dict.valueForKey("serverProjectName") as! String
            if serverPort == 80
            {
                sUrl = "http://\(serverAddress)/\(serverProjectName)"
            }
            else
            {
                sUrl = "http://\(serverAddress):\(serverPort)/\(serverProjectName)"
            }
        }
        return sUrl
    }
    
    class func getUrlPath(serverName:String,actionName:String)->String?
    {
        var sUrl = getUrlPath(serverName)
        if (sUrl != nil)
        {
            sUrl = "\(sUrl!)/\(actionName)"
        }
        return sUrl
    }
    
    class func getUrlPath(serverName:String,actionName:String,paramValue:String)->String?
    {
        var sUrl = getUrlPath(serverName, actionName: actionName)
        if (sUrl != nil)
        {
            sUrl = "\(sUrl)?\(paramValue)"
        }
        return sUrl
    }


}
