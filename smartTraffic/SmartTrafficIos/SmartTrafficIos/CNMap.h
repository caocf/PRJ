//
//  CNMap.h
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/24.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CNMapKit.h"
#import "CNMKSysConfigure.h"
#import "iflyMSC/IFlyRecognizerViewDelegate.h"
#import "iflyMSC/IFlyRecognizerView.h"
#import "iflyMSC/IFlySpeechConstant.h"
#import "iflyMSC/IFlySpeechUtility.h"
#import "PopupView.h"
#import "iflyMSC/iflySetting.h"
#import "iflyMSC/IFlySpeechUtility.h"
#import "ZBarSDK.h"
#import "JZLocationConverter/JZLocationConverter.h"
#import "MarqueeView.h"



@interface CNMap : NSObject

- (void)setChiMapUrl:(NSString*)UrlString;

- (NSString*)getChiMapUrl;

- (void)setEngMapUrl:(NSString*)UrlString;

- (NSString*)getEngMapUrl;


@end
