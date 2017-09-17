//
//  MarqueeView.h
//  MarqueeView
//
//  Created by WenDong Zhang on 5/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MarqueeView : UIView
{
    UILabel *_topLabel;
    UILabel *_bottomLabel;
    
    NSString *_text;
}

@property (nonatomic, strong) NSString *text;

- (void)start;
- (void)startScroll;

@end
