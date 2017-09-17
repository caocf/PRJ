//
//  busStopTableViewCell.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/5.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busStopTableViewCell: UITableViewCell {

    @IBOutlet weak var labNo:UILabel!
    @IBOutlet weak var labCarNumber:UILabel!
    @IBOutlet weak var labStopLength:UILabel!
    @IBOutlet weak var labArrive:UILabel!
    @IBOutlet weak var labDriverStatus:UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
