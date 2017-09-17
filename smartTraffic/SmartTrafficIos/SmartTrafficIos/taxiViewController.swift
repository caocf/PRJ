//
//  taxiViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/6/25.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class taxiViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,TaxiDelegate,CLLocationManagerDelegate {

    @IBOutlet weak var tvInfo:UITableView!
    var currLocation : CLLocation!
    var startPoint:CLLocationCoordinate2D = CLLocationCoordinate2DMake(Tools.defLat, Tools.defLon)
    let locationManager : CLLocationManager = CLLocationManager()
    var tUpdate:NSTimer!

    var taxiList = [Taxi]()
        {
        didSet{
            tvInfo.reloadData()
        }
    }
    var avErrorInfo:UIAlertView!
    var taxiTitle = ""

    override func viewDidLoad() {
        super.viewDidLoad()

        tvInfo.dataSource = self
        tvInfo.delegate = self
        locationManager.requestAlwaysAuthorization()
        if CLLocationManager.locationServicesEnabled()
        {
            locationManager.delegate = self
            //设备使用电池供电时最高的精度
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            //精确到10米,距离过滤器，定义了设备移动后获得位置信息的最小距离
            locationManager.distanceFilter = kCLLocationAccuracyNearestTenMeters
            locationManager.startUpdatingLocation()
            
        }
        else
        {
            avErrorInfo = UIAlertView(title: "定位错误", message: "定位服务不可用，请打开定位服务。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            print("定位服务不可用。")
        }

        // Do any additional setup after loading the view.
    }
    @IBAction func ubbMap(sender: UIBarButtonItem) {
        if (taxiList.count > 0)
        {
            self.performSegueWithIdentifier("segueTaxiMap", sender: taxiList)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "切换错误", message: "没有可以显示的出租车", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    override func viewDidAppear(animated: Bool) {
//        Taxi.initTaxi(self, radius: Int, lan: Double, lon: Double, num: 5)

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func updateError(error: String) {
        avErrorInfo = UIAlertView(title: "获取错误", message: "获取出租车信息错误。", delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
    }
    
    func updataTaxiPhone(status: Int, msg: String, taxiPhone: [TaxiPhone]) {
        
    }
    
    func updateTaxi(status: Int, msg: String, taxi: [Taxi]) {
        switch status
        {
        case 1:
            taxiTitle = "已为您找到周边最近的5辆出租车"
            self.taxiList = taxi
        case 0:
            taxiTitle = "没有找到附近的出租车"
            self.taxiList.removeAll()
            avErrorInfo = UIAlertView(title: "获取失败", message: "没有找到附近的出租车。", delegate: nil, cancelButtonTitle: "确定")
            avErrorInfo.show()
        default:
            taxiTitle = "没有找到附近的出租车"
            self.taxiList.removeAll()
            avErrorInfo = UIAlertView(title: "获取失败", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return taxiList.count
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: true)
        self.performSegueWithIdentifier("segueTaxiDetail", sender: taxiList[indexPath.row])
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("taxiCell", forIndexPath: indexPath) 
        let item = taxiList[indexPath.row]
        cell.textLabel?.text = item.sbid
        cell.detailTextLabel?.text = "距离：\(item.distance)米"
        return cell
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        startPoint = currLocation.coordinate
      
        Taxi.initTaxi(self, radius: 50000, lan: startPoint.longitude, lon: startPoint.latitude, num: 5)
        
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        avErrorInfo = UIAlertView(title: "定位失败", message: error.localizedDescription, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }

    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return taxiTitle
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueTaxiMap":
                let vc = segue.destinationViewController as! taxiMapViewController
                vc.taxi = (sender as! [Taxi])
            case "segueTaxiDetail":
                let vc = segue.destinationViewController as! taxiDetailViewController
                vc.selectTaxi = (sender as! Taxi)
            default:
                break
                
            }
        }
    }
    
    func updateTaxiDetail(status: Int, msg: String, taxtDetail: [TaxiDetail]) {
        
    }
    
    func updateTaxiPhone(status: Int, msg: String, taxiPhone: [TaxiPhone]) {
        
    }
    
    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Portrait
    }
    
    @IBAction func closeToTaxi(segue: UIStoryboardSegue)
    {
        
        print("closeToTaxi")
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
