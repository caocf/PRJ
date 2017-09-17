//
//  publicBikeStationTableViewCell.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/28.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class publicBikeStationTableViewCell: UITableViewCell,BikeFavorDelegate {

    @IBOutlet weak var labStationName:UILabel!
    @IBOutlet weak var labStationID:UILabel!
    @IBOutlet weak var labServiceTime:UILabel!
    @IBOutlet weak var labStationInfo:UILabel!
    @IBOutlet weak var labAddress:UILabel!
    var avErrorInfo:UIAlertView!
    var cellPublicBike:PublicBike!
    

    @IBAction func btnGoHere(sender: UIButton) {
        let mainView = Tools.findViewController(self)
    mainView.performSegueWithIdentifier("segueBikeToDrive", sender: cellPublicBike)    }
    
    @IBAction func btnFavor(sender: UIButton) {
//        var mainView = Tools.findViewController(self)
        if let user = LoginUser.user
        {
            BikeFavor.APPSaveBikeStationFavor(self, userid: user.userid, stationID: cellPublicBike.Id, stationName: cellPublicBike.Name, address: cellPublicBike.Address)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "收藏站点错误", message: "您尚未登录，请登陆后再收藏站点", delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    func updateAPPSaveBikeStationFavor(status: Int, msg: String) {
        if (status == 1)
        {
            avErrorInfo = UIAlertView(title: "收藏站点成功", message: "收藏站点成功", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
        else
        {
            avErrorInfo = UIAlertView(title: "收藏站点错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

    }
    
    func updateAPPDeleteBikeForStation(status: Int, msg: String) {
        
    }
    
    func updateAPPQueryAllBikeStationFavor(status: Int, msg: String, bikeFavor: [BikeFavor]) {
        
    }
    
    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
