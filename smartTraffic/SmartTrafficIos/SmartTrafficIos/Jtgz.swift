//
//  Jtgz.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/12/2.
//  Copyright © 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class Jtgz
{
    static let actionName = "QueryJTGZList"

    var BH:String     //信息编号
    var BT:String      //标题
    var CJSJ:String    //创建时间
    var FBR:String      //发布人
    var FBSJ:String     //发布时间
    var GXSJ:String     //更新时间
    var LY:String
    var NR:String       //内容
    
    init(BH:String,BT:String,CJSJ:String,FBR:String,FBSJ:String,GXSJ:String,LY:String,NR:String)
    {
        self.BH = BH
        self.BT = BT
        self.CJSJ = CJSJ
        self.FBR = FBR
        self.FBSJ = FBSJ
        self.GXSJ = GXSJ
        self.LY = LY
        self.NR = NR
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
    
    class func initJtgzData(data:NSData) -> [Jtgz]
    {
        var retJtgz = [Jtgz]()
        let jsonRet = JSON(data: data, options: NSJSONReadingOptions.MutableContainers, error: nil)
        
        for listItem in jsonRet["jtgzs"]
        {
            let BH = listItem.1["BH"].stringValue
            let BT = listItem.1["BT"].stringValue
            let CJSJ = listItem.1["CJSJ"].stringValue
            let FBR = listItem.1["FBR"].stringValue
            let FBSJ = listItem.1["FBSJ"].stringValue
            let GXSJ = listItem.1["GXSJ"].stringValue
            let LY = listItem.1["LY"].stringValue
            let NR = listItem.1["NR"].stringValue

            
            let jtgz = Jtgz(BH: BH, BT: BT, CJSJ: CJSJ, FBR: FBR, FBSJ: FBSJ, GXSJ: GXSJ, LY: LY, NR: NR)
            retJtgz.append(jtgz)
        }
        
        
        return retJtgz
    }

    
}
