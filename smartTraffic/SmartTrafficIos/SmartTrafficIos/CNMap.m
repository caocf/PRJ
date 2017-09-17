//
//  CNMap.m
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/24.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

#import "CNMap.h"

@implementation CNMap

- (void)setChiMapUrl:(NSString*)UrlString
{
    Sysfmappmg_cn = UrlString;
}

- (NSString*)getChiMapUrl
{
    return Sysfmappmg_cn;
}

- (void)setEngMapUrl:(NSString*)UrlString
{
    Sysfmappmg_en = UrlString;
}

- (NSString*)getEngMapUrl
{
    return Sysfmappmg_en;
}

@end
