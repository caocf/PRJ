//
//  parkingTableViewCell.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class parkingTableViewCell: UITableViewCell {

    @IBOutlet weak var labParkingName:UILabel!
    @IBOutlet weak var labParkingInfo:UILabel!
    var cellParking:Parking!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    

    
    
    @IBAction func btnGoHere(sender: UIButton) {
        
        let mainView = Tools.findViewController(self)
        mainView.performSegueWithIdentifier("segueParkingToDrive", sender: cellParking)
        
        
        
//        for (UIView* next = [self superview]; next; next = next.superview) {
//            UIResponder* nextResponder = [next nextResponder];
//            if ([nextResponder isKindOfClass:[UIViewController class]]) {
//                return (UIViewController*)nextResponder;
//            }
//        }
        
        
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    
}
