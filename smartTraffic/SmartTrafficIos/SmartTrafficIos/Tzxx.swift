//
//  Tzxx.swift
//  SmartTrafficIos
//  通阻信息model类
//  Created by EastMac on 15/4/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class Tzxx          //通阻信息
{
    static let actionName = "QueryTzxx"
    
    
    var BT:String      //标题
    var CJSJ:NSDate    //创建时间
    var FBR:String      //发布人
    var FBSJ:NSDate     //发布时间
    var GXSJ:NSDate     //更新时间
    var LBBH:String     //类别编码
    var LBMC:String     //类别名称
    var NR:String       //内容
    var XXBR:String     //信息编号
    

    init(bt:String,cjsj:NSDate,fbr:String,fbsj:NSDate,gxsj:NSDate,lbbh:String,lbmc:String,nr:String,xxbr:String)
    {
        self.BT = bt
        self.CJSJ = cjsj
        self.FBR = fbr
        self.FBSJ = fbsj
        self.GXSJ = gxsj
        self.LBBH = lbbh
        self.LBMC = lbmc
        self.NR = nr
        self.XXBR = xxbr
    }
    
    class func getActionUrl() -> String
    {
        var retActionUrl = ""
        
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            retActionUrl = sServerUrl
        }
        return retActionUrl
    }
    
    class func getActionUrl(startTime:NSDate) -> String
    {
        var retActionUrl = ""
        let customAllowedSet =  NSCharacterSet(charactersInString:" ").invertedSet
        if let sServerUrl = HttpTools.getUrlPath(HttpTools.defaultServerName,actionName: actionName)
        {
            let timeString = Tools.nsdateConvertToString(startTime, formatString: "YYYY-MM-dd HH:mm:ss")
            retActionUrl = "\(sServerUrl)?startTime=\(timeString)".stringByAddingPercentEncodingWithAllowedCharacters(customAllowedSet)!
        }
        return retActionUrl
    }
    
    class func initTzxxData(data:NSData) -> [Tzxx]
    {
        var retTzxx = [Tzxx]()
        let jsonRet = JSON(data: data, options: NSJSONReadingOptions.MutableContainers, error: nil)
        
        for listItem in jsonRet
        {
            let BT = listItem.1["BT"].stringValue
            let CJSJ = Tools.stringConvertToNSDate(listItem.1["CJSJ"].stringValue, formatString: "YYYY-MM-dd HH:mm:ss.S")!
            let FBR = listItem.1["FBR"].stringValue
            let FBSJ = Tools.stringConvertToNSDate(listItem.1["FBSJ"].stringValue, formatString: "YYYY-MM-dd HH:mm:ss.S")!
            let GXSJ = Tools.stringConvertToNSDate(listItem.1["GXSJ"].stringValue, formatString: "YYYY-MM-dd HH:mm:ss.S")!
            let LBBH = listItem.1["LBBH"].stringValue
            let LBMC = listItem.1["LBMC"].stringValue
            let NR = listItem.1["NR"].stringValue
            let XXBH = listItem.1["XXBH"].stringValue
            
            let tzxx = Tzxx(bt: BT, cjsj: CJSJ, fbr: FBR, fbsj: FBSJ, gxsj: GXSJ, lbbh: LBBH, lbmc: LBMC, nr: NR, xxbr: XXBH)
            retTzxx.append(tzxx)
        }

        
        return retTzxx
    }
    
}
