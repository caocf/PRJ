//
//  User.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class User
{
    var userid:Int
    var phone:String
    var userpassword:String
    var username:String
    var email:String
    
    init(userid:Int,phone:String,userpassword:String,username:String,email:String)
    {
        self.userid = userid
        self.phone = phone
        self.userpassword = userpassword
        self.username = username
        self.email = email
    }

    class func formatUserJson(userJson:JSON) -> User
    {
        let userid:Int = userJson["userid"].intValue
        let phone:String = userJson["phone"].stringValue
        let userpassword:String = userJson["userpassword"].stringValue
        let username:String = userJson["username"].stringValue
        let email:String = userJson["email"].stringValue
        let user = User(userid: userid, phone: phone, userpassword: userpassword, username: username, email: email)
        return user
    }
    
    static func readLoginUser()->(String,String)
    {
        
        var paths = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)
        let docDir:String? = paths[0]
        if let newPathStr = docDir //pathStr可能为nil
        {
            let fileStr = (newPathStr as NSString).stringByAppendingPathComponent("CommonConfig.plist")
            
            let dict = NSDictionary(contentsOfFile: fileStr)
            if let dictNew = dict
            {
                let dictUser = dictNew.valueForKey("LoginUser") as? NSDictionary
                if let dictUserNew = dictUser
                {
                    let phone:String? = dictUserNew.valueForKey("phone") as? String
                    let password:String? = dictUserNew.valueForKey("password") as? String
                    if let phoneNew = phone , let passwordNew = password
                    {
                        return (phoneNew,passwordNew)
                    }
                    else
                    {
                        return ("","")
                    }
                }
                else
                {
                    print("没有找到LoginUser节点")
                    return ("","")
                }
                
            }
            else
            {
                print("没有找到文件")
                return ("","")
            }
        }
        else
        {
            print("没有找到目录")
            return ("","")
        }
    }
    
    static func writeLoginUser(phone:String,password:String) -> Bool
    {
        var paths = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)
        let docDir:String? = paths[0]
        if let newPathStr = docDir //pathStr可能为nil
        {
            let fileStr = (newPathStr as NSString).stringByAppendingPathComponent("CommonConfig.plist")
            let dict = NSMutableDictionary(contentsOfFile: fileStr)
            if let dictNew = dict
            {
                
                dictNew["LoginUser"] = ["phone":phone,"password":password]
                return dictNew.writeToFile(fileStr, atomically: true)
            }
            else
            {
                return false
            }
        }
        return false
    }
    
    class func appPhoneIsExisted(updateVc:UserDelegate,phone:String)
    {
        let actionName = "appPhoneIsExisted"
        
        var params = [String]()
        params.append("user.phone=\(phone)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        
                        _ = [Parking]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAppPhoneIsExisted(status, msg: msg)
                        })
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateUserError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
        
    }
    
    class func appSendMessage(updateVc:UserDelegate,phone:String)
    {
        let actionName = "appSendMessage"
        
        var params = [String]()
        params.append("user.phone=\(phone)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        
                        _ = [Parking]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        let result = jsonMessage["result"].stringValue
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAppSendMessage(status, msg: msg, result: result)
                        })
                        
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateUserError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }

    
    class func appAddUser(updateVc:UserDelegate,phone:String,username:String,userpassword:String,email:String)
    {
        let actionName = "appAddUser"
        
        var params = [String]()
        params.append("user.phone=\(phone)")
        params.append("user.username=\(username)")
        params.append("user.userpassword=\(userpassword)")
        params.append("user.email=\(email)")

        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        
                        _ = [Parking]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
    
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAppAddUser(status, msg: msg)
                        })
                        
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateUserError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    class func appLogin(updateVc:UserDelegate,phone:String,userpassword:String)
    {
        let actionName = "appLogin"
        
        var params = [String]()
        params.append("user.phone=\(phone)")
        params.append("user.userpassword=\(userpassword)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        let user = self.formatUserJson(jsonRet["result"])
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAppLogin(status, msg: msg, user: user)
                        })
                        
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateUserError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    class func appEditPassword(updateVc:UserDelegate,phone:String,userpassword:String,newPass:String)
    {
        let actionName = "appEditPassword"
        
        var params = [String]()
        params.append("user.phone=\(phone)")
        params.append("newPass=\(newPass)")
        params.append("user.userpassword=\(userpassword)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        
                        _ = [Parking]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAppEditPassword(status, msg: msg)
                        })
                        
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateUserError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
    class func appResetPass(updateVc:UserDelegate,phone:String,userpassword:String)
    {
        let actionName = "appResetPass"
        
        var params = [String]()
        params.append("user.phone=\(phone)")
        params.append("user.userpassword=\(userpassword)")
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            if let retActionUrl = "\(sServerUrl)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet),let updateParkingRequest = HttpTools.getHttpRequest(retActionUrl,paramValue:Tools.formatParamValue(params))
            {
                NSURLConnection.sendAsynchronousRequest(updateParkingRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                    if(error == nil)
                    {
                        
                        _ = [Parking]()
                        var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                        var jsonMessage = jsonRet["message"]
                        let status = jsonMessage["status"].intValue
                        let msg = jsonMessage["msg"].stringValue
                        
                        
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateAppResetPass(status, msg: msg)
                        })
                        
                    }
                    else
                    {
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateUserError(error!.localizedDescription)
                        })
                        print(error!.localizedDescription)
                    }
                })
            }
        }
    }
    
}

protocol UserDelegate
{
    func updateAppPhoneIsExisted(status:Int,msg:String)
    func updateAppSendMessage(status:Int,msg:String,result:String)
    func updateAppAddUser(status:Int,msg:String)
    func updateAppLogin(status:Int,msg:String,user:User)
    func updateAppEditPassword(status:Int,msg:String)
    func updateAppResetPass(status:Int,msg:String)

    
    func updateUserError(error:String)
}