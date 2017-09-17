//
//  mainViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/23.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class mainViewController: UIViewController,AreaTpiProtocol,WeatherProtocol,UserDelegate,StatementDelegate {

    @IBOutlet weak var labWeather: UILabel!
    @IBOutlet weak var labWeatherTime: UILabel!
    @IBOutlet weak var labTpi: UILabel!
    @IBOutlet weak var labTpiSpeed: UILabel!
    @IBOutlet weak var labTpiLevel: UILabel!
    @IBOutlet weak var ivTpiLevelImage:UIImageView!
    
    var currentWeather = Weather.initNullWeather()
    var currentAreaTpi = AreaTpi.initNullAreaTpi()
    
    var avErrorInfo:UIAlertView!
    var tUpdate:NSTimer!
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        let saveLoginUser = User.readLoginUser()
        User.appLogin(self, phone: saveLoginUser.0, userpassword: saveLoginUser.1)
        
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(animated: Bool) {
        if let bIsReadStatement = Statement.checkStatementStatus() where !bIsReadStatement
        {
            Statement.getStatementInfo(self)
        }

    }
    
    func updateGetStatementInfo(status: Bool, info: String) {
        if status
        {
            self.performSegueWithIdentifier("segueShowStatement", sender: info)
        }
        else
        {
//            avErrorInfo = UIAlertView(title: "网络连接错误", message: info, delegate: nil, cancelButtonTitle: "关闭")
//            avErrorInfo.show()
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewDidAppear(animated: Bool) {
        print("viewDidAppear")
        updateInfo()
        tUpdate = NSTimer.scheduledTimerWithTimeInterval(120, target: self, selector: "updateInfo", userInfo: nil, repeats: true)
    }
    
    override func viewWillDisappear(animated: Bool) {
        if (tUpdate != nil)
        {
            tUpdate.invalidate()
        }
    }
    
    func updateInfo()
    {
        currentWeather.initWeather(self)
        currentAreaTpi.initAreaTpi(self,areaName: AreaTpi.TPIAreaName.All)
        print("updateInfo")
    }
    
    func updateWeather(updateWeather: Weather) {
        labWeather.text = "\(updateWeather.weatherName) \(updateWeather.l_tmp)-\(updateWeather.h_tmp)℃"
        let timeFormet = "MM月dd日 HH:mm"
        labWeatherTime.text = "\(Tools.nsdateConvertToString(updateWeather.updateDatetime, formatString: timeFormet))更新"
        
    }

    func updateAreaTpi(updateAreaTpi: AreaTpi) {
        labTpi.text = "\(updateAreaTpi.tpi)"
        labTpiLevel.text = "\(updateAreaTpi.tpiLevel)"
        let timeFormet = "YYYY/MM/dd HH:mm"
        labTpiSpeed.text = "路网平均速度：\(updateAreaTpi.avgSpeed)km/h  \(Tools.nsdateConvertToString(updateAreaTpi.tpiTime, formatString: timeFormet))"
        switch (updateAreaTpi.tpiLevel)
        {
            case "畅通":
                ivTpiLevelImage.image = UIImage(named: "bg_路网状况")
            case "基本畅通":
                ivTpiLevelImage.image = UIImage(named: "bg_路网状况_深绿")
            case "轻度拥堵":
                ivTpiLevelImage.image = UIImage(named: "bg_路网状况-_黄")
            case "中度拥堵":
                ivTpiLevelImage.image = UIImage(named: "bg_路网状况-_橙")
            case "严重拥堵":
                ivTpiLevelImage.image = UIImage(named: "bg_路网状况_红")
        default:
            ivTpiLevelImage.image = UIImage(named: "bg_路网状况_灰")
        }
    }
    
    func getAreaTpiError(error: String) {
//        avErrorInfo = UIAlertView(title: "获取交通指数失败", message: error, delegate: nil, cancelButtonTitle: "关闭")
//        avErrorInfo.show()
        print(error)

        
    }
    
    func getWeatherError(error: String) {
//        avErrorInfo = UIAlertView(title: "获取天气失败", message: error, delegate: nil, cancelButtonTitle: "关闭")
//        avErrorInfo.show()
        print(error)
        
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowStatement":
                let vc = segue.destinationViewController as! statementViewController
                vc.statementStr = (sender as! String)
            default:
                break
                
            }
        }
    }

    
    @IBAction func closeToMain(segue: UIStoryboardSegue)
    {
        if segue.sourceViewController is trafficViewController
        {
            let vc = segue.sourceViewController as! trafficViewController
          
            vc.mapView.delegate = nil
            vc.mapView.gNoteCBDelegate = nil
            vc.mapView.removeMapObjects()
        }
        else if segue.sourceViewController is busStationMapViewController
        {
            let vc = segue.sourceViewController as! busStationMapViewController
            vc.mapView.delegate = nil
            vc.mapView.gNoteCBDelegate = nil
            vc.mapView.removeAnnotations(vc.busStationAnno)
            vc.mapView.removeMapObjects()
        }
        else if segue.sourceViewController is busLineMapViewController
        {
            let vc = segue.sourceViewController as! busLineMapViewController
            vc.mapView.delegate = nil
            vc.mapView.gNoteCBDelegate = nil
            vc.gSearch.delegate = nil
            vc.mapView.removeAnnotations(vc.busStationAnno)
            vc.mapView.removeRoutingResult()
            vc.mapView.removeMapObjects()
        }
        else if segue.sourceViewController is parkingMapViewController
        {
            let vc = segue.sourceViewController as! parkingMapViewController
            vc.mapView.delegate = nil
            vc.mapView.gNoteCBDelegate = nil
            vc.mapView.removeAnnotations(vc.parkingAnno)
            vc.mapView.removeMapObjects()
            
        }

        print("closeToMain")
    }
    
    
    func updateAppLogin(status: Int, msg: String, user: User) {
        if (status == 1)
        {
            LoginUser.user = user
        }
        else
        {
            LoginUser.user = nil
            User.writeLoginUser("", password: "")
        }
    }
    
    func updateAppAddUser(status: Int, msg: String) {
        
    }
    
    func updateAppPhoneIsExisted(status: Int, msg: String) {
    }
    
    func updateAppEditPassword(status: Int, msg: String) {
        
    }
    
    func updateAppResetPass(status: Int, msg: String) {
        
    }
    
    func updateAppSendMessage(status: Int, msg: String, result: String) {
       
    }
    
    func updateUserError(error: String) {
  
        print(error)
    }

    
    
    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Portrait
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
