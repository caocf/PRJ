//
//  busStationTableViewCell.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/6.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busStationTableViewCell: UITableViewCell {

    @IBOutlet weak var labStationName:UILabel!
    @IBOutlet weak var labStartToEnd:UILabel!
    @IBOutlet weak var labBusInfo:UILabel!
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
