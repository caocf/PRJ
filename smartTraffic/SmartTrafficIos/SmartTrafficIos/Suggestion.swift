//
//  Suggestion.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/27.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class Suggestion
{
    class func addAppSuggestion(updateVc:SuggestionDelegate,userid:Int,title:String,content:String)
    {
        let actionName = "addAppSuggestion"
        
        var params = [String]()
        params.append("userid=\(userid)")
        params.append("title=\(title)")
        params.append("content=\(content)")
        let httpBody = NSMutableData()
        let dataTemp = NSString(string: "\(Tools.formatParamValue(params))")
        httpBody.appendData(dataTemp.dataUsingEncoding(NSUTF8StringEncoding)!)
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateRequest = HttpTools.getPostHttpRequest(retActionUrl, httpBody: httpBody)
            {
                NSURLConnection.sendAsynchronousRequest(updateRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAddAppSuggestion(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAddAppSuggestion(-99, msg: error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        

    }
}

protocol SuggestionDelegate
{
    func updateAddAppSuggestion(status:Int,msg:String)
}
