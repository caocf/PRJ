//
//  DrewLine.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/9/23.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
class DrewLine {
    var drewType:Int
    var startPoint:CNMKGeoPoint?
    var endPoint:CNMKGeoPoint?
    var lines:[CNMKGeoPoint]?
    var transfer:Transfer?
    
    init(startPoint:CNMKGeoPoint,endPoint:CNMKGeoPoint)
    {
        self.drewType = 0
        self.startPoint = startPoint
        self.endPoint = endPoint
    }
    
    init(lines:[CNMKGeoPoint])
    {
        self.drewType = 1
        self.lines = lines
    }
    
    init(transfer:Transfer,startPoint:CNMKGeoPoint,endPoint:CNMKGeoPoint)
    {
        self.drewType = 2
        self.transfer = transfer
        self.startPoint = startPoint
        self.endPoint = endPoint
    }

}