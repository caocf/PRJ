//
//  taxiDetailViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/7/10.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class taxiDetailViewController: UIViewController,TaxiDelegate {

    @IBOutlet weak var labCp:UILabel!
    @IBOutlet weak var labCx:UILabel!
    @IBOutlet weak var labGs:UILabel!
    @IBOutlet weak var labRllx:UILabel!

    var avErrorInfo:UIAlertView!
    
    var selectTaxi:Taxi!
    override func viewDidLoad() {
        super.viewDidLoad()

        TaxiDetail.initTaxi(self, taxiName: selectTaxi.sbid)
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnCall(sender: UIButton) {
        TaxiPhone.initTaxi(self, taxiName: selectTaxi.sbid)

    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    func updateTaxi(status: Int, msg: String, taxi: [Taxi]) {
        
    }
    
    func updateError(error: String) {
        avErrorInfo = UIAlertView(title: "获取错误", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
    }
    
    func updateTaxiDetail(status: Int, msg: String, taxtDetail: [TaxiDetail]) {
        switch status
        {
        case 1:
            if taxtDetail.count > 0
            {
                labCp.text = taxtDetail[0].cphm
                labCx.text = taxtDetail[0].cp
                labGs.text = taxtDetail[0].yhmc
                labRllx.text = taxtDetail[0].rllx
            }
        case 0:
            avErrorInfo = UIAlertView(title: "获取失败", message: "没有找到该出租车的信息。", delegate: nil, cancelButtonTitle: "确定")
            avErrorInfo.show()
        default:
            avErrorInfo = UIAlertView(title: "获取失败", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

    }
    
    func updateTaxiPhone(status: Int, msg: String, taxiPhone: [TaxiPhone]) {
        switch status
        {
        case 1:
            if taxiPhone.count > 0
            {
                let url = NSURL(string: "tel://\(taxiPhone[0].phone)")
                UIApplication.sharedApplication().openURL(url!)
            }
        case 0:
            avErrorInfo = UIAlertView(title: "获取失败", message: "没有找到该出租车的号码。", delegate: nil, cancelButtonTitle: "确定")
            avErrorInfo.show()
        default:
            avErrorInfo = UIAlertView(title: "获取失败", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

    }
    
    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Portrait
    }

}
