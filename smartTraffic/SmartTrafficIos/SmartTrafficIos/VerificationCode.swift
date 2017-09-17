//
//  VerificationCode.swift
//  HealthyHainingIos
//
//  Created by EastMac on 15/3/26.
//  Copyright (c) 2015年 EastLanCore. All rights reserved.
//

import Foundation

class VerificationCode
{
    var codeString:String?
    
    init()
    {
        codeString = createNumberVerificationCode()
    }
    
    class func createNumberVerificationCode() -> String
    {
        let verNumber:UInt32 = arc4random_uniform(1000000)
        return String(format: "%06d", verNumber)
    }
    
    func createNumberVerificationCode() -> String
    {
        let verNumber:UInt32 = arc4random_uniform(1000000)
        return String(format: "%06d", verNumber)
    }
}