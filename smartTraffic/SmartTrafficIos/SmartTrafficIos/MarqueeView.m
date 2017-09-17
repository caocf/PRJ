//
//  MarqueeView.m
//  MarqueeView
//
//  Created by WenDong Zhang on 5/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MarqueeView.h"

@implementation MarqueeView

@synthesize text;

- (void)startScroll
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    if (_text == NULL)
    {
        return;
    }
    [self performSelector:@selector(start) withObject:nil afterDelay:1.0f];

}

- (id)initWithFrame:(CGRect)frame
{
    NSLog(@"%s", __PRETTY_FUNCTION__);

    
    self = [super initWithFrame:frame];
    if (self) {
        // create label
        _topLabel = [[UILabel alloc] initWithFrame:CGRectZero];
        _bottomLabel = [[UILabel alloc] initWithFrame:CGRectZero];
        _topLabel.numberOfLines = _bottomLabel.numberOfLines = 0;
        
        [self addSubview:_topLabel];
        [self addSubview:_bottomLabel];
        
        self.clipsToBounds = YES;
        
//        self.backgroundColor = [UIColor redColor];
//        _topLabel.backgroundColor = [UIColor grayColor];
//        _bottomLabel.backgroundColor = [UIColor greenColor];
    }
    return self;
}

- (void)setText:(NSString *)text_
{
    NSLog(@"%s", __PRETTY_FUNCTION__);

    if (_text != text_) {
        _topLabel.text = text_;
        _bottomLabel.text = text_;
        [_topLabel sizeToFit];
        [_bottomLabel sizeToFit];
        _text = text_;
        [self setNeedsLayout];
    }
}

- (void)layoutSubviews
{
    NSLog(@"%s", __PRETTY_FUNCTION__);

//    NSLog(@"%f,%f",self.frame.size.width,self.frame.size.height);
//    NSLog(@"%@",_text);
    
    
    if(text == NULL)
    {
        text = @" ";
    }
    
    UIFont *font = _topLabel.font;
    CGSize textSize = CGSizeMake(self.frame.size.width, CGFLOAT_MAX);
    if (_text == NULL)
    {
        return;
    }
    CGSize size = [_text sizeWithFont:font constrainedToSize:textSize lineBreakMode:UILineBreakModeWordWrap];
    CGRect frame = CGRectMake(0.0f, 0.0f, size.width, size.height);
    _topLabel.frame = frame;
    frame.origin.y += frame.size.height;
    _bottomLabel.frame = frame;
//    NSLog(@"layoutSubviews- -");
}

- (void)start
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    
    [UIView animateWithDuration:10.0f delay:0.0f options:UIViewAnimationOptionRepeat|UIViewAnimationOptionCurveLinear animations:^(void) {
        CGRect frame = _topLabel.frame;
        frame.origin.y = -frame.size.height;
        _topLabel.frame = frame;
        frame.origin.y += frame.size.height;
        _bottomLabel.frame = frame;
        
    } completion:NULL];
}

@end
