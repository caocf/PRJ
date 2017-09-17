//
//  busModel.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/11.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation

enum TextType:Int
{
    case TextInput = 0
    case VoiceInput = 1
    case MyPosition = 2
    case MapChoose = 3
    case Station = 4
}

enum OpenMore:Int
{
    case None = 0
    case Start = 1
    case End = 2
}

class addrInput {
    var type:TextType
    var text:String?
    var point:CLLocationCoordinate2D?
    var busStation:BusStation?
    
    init(type:TextType)
    {
        self.type = type
    }
}