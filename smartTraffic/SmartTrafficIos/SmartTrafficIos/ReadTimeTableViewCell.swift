//
//  ReadTimeTableViewCell.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/6/15.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class ReadTimeTableViewCell: UITableViewCell {

    @IBOutlet weak var labRoadName:UILabel!
    @IBOutlet weak var labRoadIndex:UILabel!
    @IBOutlet weak var labRoadLevel:UILabel!
    @IBOutlet weak var labAvgSpeed:UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
