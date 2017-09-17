//
//  trafficIndexViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/6/11.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class trafficIndexViewController: UIViewController,AreaTpiProtocol {

    var avErrorInfo:UIAlertView!
    
    let indexUrl = ["qlw.jsp","nhq.jsp","xzq.jsp","zhn.jsp","nhn.jsp"]
    
    let indexAreaName:[AreaTpi.TPIAreaName] = [.All,.Nanhu,.Xiuzhou,.Zhonghuan,.Neihuan]
    
    var currentAreaTpi = AreaTpi.initNullAreaTpi()
    @IBOutlet weak var wvIndex:UIWebView!
    @IBOutlet weak var labInfo:UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
   
//        let loadUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName, actionName: indexUrl[0])
//        wvIndex.loadRequest(NSURLRequest(URL: NSURL(string: loadUrl!)!))
//        currentAreaTpi.initAreaTpi(self,areaName: indexAreaName[0])
        // Do any additional setup after loading the view.
    }
    
    @IBAction func scSelectArea(sender: UISegmentedControl) {
        let loadUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName, actionName: indexUrl[sender.selectedSegmentIndex])
        wvIndex.loadRequest(NSURLRequest(URL: NSURL(string: loadUrl!)!))
//        currentAreaTpi.initAreaTpi(self,areaName: indexAreaName[sender.selectedSegmentIndex])
        
    }
    override func viewDidAppear(animated: Bool) {
        UIDevice.currentDevice().setValue(UIDeviceOrientation.LandscapeLeft.rawValue, forKey: "orientation")
        
        let loadUrl = HttpTools.getUrlPath(HttpTools.jxtpiServerName, actionName: indexUrl[0])
        wvIndex.loadRequest(NSURLRequest(URL: NSURL(string: loadUrl!)!))
        
    }
    
    override func viewWillAppear(animated: Bool) {
//        UIDevice.currentDevice().setValue(UIDeviceOrientation.LandscapeLeft.rawValue, forKey: "orientation")

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Landscape
    }
    
    override func preferredInterfaceOrientationForPresentation() -> UIInterfaceOrientation {
        return UIInterfaceOrientation.LandscapeLeft
    }
    
    func getAreaTpiError(error: String) {
        avErrorInfo = UIAlertView(title: "获取交通指数失败", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
    }
    
    func updateAreaTpi(updateAreaTpi: AreaTpi) {
//        var infoText = NSMutableAttributedString(string: "交通指数  拥堵里程比例 \(updateAreaTpi.hrRate * 100)% 路网平均速度 \(updateAreaTpi.avgSpeed)km/h")
//        
//        var tpiText = NSMutableAttributedString(string: "\(updateAreaTpi.tpi) \(updateAreaTpi.tpiLevel)")
//    
//        var tpiColor = UIColor()
//        switch (updateAreaTpi.tpiLevel)
//        {
//        case "畅通":
//            tpiColor = AreaTpi.畅通
//        case "基本畅通":
//            tpiColor = AreaTpi.基本畅通
//        case "轻度拥堵":
//            tpiColor = AreaTpi.轻度拥堵
//        case "中度拥堵":
//            tpiColor = AreaTpi.中度拥堵
//        case "严重拥堵":
//            tpiColor = AreaTpi.严重拥堵
//        default:
//            tpiColor = AreaTpi.其他
//        }
//        tpiText.addAttribute(NSForegroundColorAttributeName, value: tpiColor, range: NSMakeRange(0, tpiText.length))
//        
//        var sStationInfo = NSMutableAttributedString(string: "站点信息：可借辆 可还辆")
//        infoText.insertAttributedString(tpiText, atIndex: 5)
//        
//        labInfo.attributedText = infoText
        
//        labInfo.text = "交通指数 \(updateAreaTpi.tpi) \(updateAreaTpi.tpiLevel) 拥堵里程比例 \(updateAreaTpi.hrRate * 100)% 路网平均速度 \(updateAreaTpi.avgSpeed)km/h"
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
