//
//  trafficViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/7.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class trafficViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CLLocationManagerDelegate {
    
    
    var bIsAddView = false
    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0

    var currLocation : CLLocation!
    
    let locationManager : CLLocationManager = CLLocationManager()
    var startPoint:CLLocationCoordinate2D = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(Tools.defLat, Tools.defLon))

    var mvText:MarqueeView!
    
    @IBOutlet weak var vScrollText:UIView!
    
    var MergeList = [MergedConRtic]()           //拥堵路段列表

    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.edgesForExtendedLayout = UIRectEdge.None
        
         mvText = MarqueeView(frame: CGRectMake(16, 4, vScrollText.frame.width - 16, vScrollText.frame.height - 4))
//        mvText.text = "--"
//        mvText.startScroll()

        GScreenWidth = UIScreen.mainScreen().bounds.size.width  //...屏幕的宽
        GScreenHeight = UIScreen.mainScreen().bounds.size.height
        mapView = CNMKMapView(frame: CGRect(x: 0, y: 64, width: GScreenWidth, height: GScreenHeight))
        print(mapView.setMapLangue(0))
        mapView.mapType = UInt(CNMKMapTypeTraffic)
        mapView.delegate = self
        //        mapView.gNoteCBDelegate = self
        self.view.addSubview(mapView)
        self.view.sendSubviewToBack(mapView)
        
        mapView.setZoomLevel(14)
        mapView.showsUserLocation = true
        
        
        mapView.setCenterCoordinate(CNMKGeoPointMake(startPoint.longitude, startPoint.latitude), animated: true)

    }
    
    override func viewDidAppear(animated: Bool) {
//        UIDevice.currentDevice().setValue(UIDeviceOrientation.LandscapeLeft.rawValue, forKey: "orientation")
//        initMergedConRtic()
        
        initMergedConRtic()
        UIDevice.currentDevice().setValue(UIDeviceOrientation.Portrait.rawValue, forKey: "orientation")
       
    }
    
    override func viewDidDisappear(animated: Bool) {
        if bIsAddView
        {
            mvText.removeFromSuperview()
            bIsAddView = false
        }
    }
    
    override func viewWillAppear(animated: Bool) {
            }

    @IBAction func btnLocation(sender: UIButton) {
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
            print("定位服务不可用。")
        }
    }
    
    @IBAction func btnZoomLarge(sender: UIButton) {
        mapView.zoomIn()
    }
    
    @IBAction func btnZoomSmall(sender: UIButton) {
        mapView.zoomOut()
    }

    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        
        mapView.setCenterCoordinate(CNMKGeoPointMake(currLocation.coordinate.longitude, currLocation.coordinate.latitude), animated: true)
        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        print(error)
    }
    
//    func mapView(mapView: CNMKMapView!, viewForOverlay overlay: CNMKOverlay!) -> CNMKOverlayView! {
//            var polyView = CNMKPolyscaleView(overlay: overlay)
//        return polyView
//    }
    
    override func viewWillDisappear(animated: Bool) {
       
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

    func initMergedConRtic()
    {
        if let MergeUrl = HttpTools.getHttpRequest(MergedConRtic.getActionUrl())
        {
//            var bIsSuccess = false
//            var sErrorStr = ""
            NSURLConnection.sendAsynchronousRequest(MergeUrl, queue: NSOperationQueue(), completionHandler: { (response:NSURLResponse?, data:NSData?, error:NSError?) -> Void in
                if (error != nil)
                {
//                    sErrorStr = error!.localizedDescription
                    self.mvText.text = " "
                }
                else
                {
                    let Merge = MergedConRtic.initMergedConRticData(data!)
                    if Merge.0      //返回成功
                    {
//                        bIsSuccess = true
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            self.MergeList = Merge.2!
                            var strMerge = ""
                            for merge in self.MergeList
                            {
                                if strMerge != ""
                                {
                                    strMerge += "\n"
                                }
                                strMerge += "\(merge.roadName):\(merge.rsStart)—\(merge.rsEnd) \(merge.desc)"
                            }
                            if (self.mvText != nil)
                            {
                                self.mvText.text = " "
                                self.vScrollText.addSubview(self.mvText)
                                self.bIsAddView = true
                                self.mvText.text = strMerge
                                self.mvText.startScroll()
                            }
                            
                        })

                    }
                    else
                    {
//                        sErrorStr = Merge.1!
                        self.mvText.text = " "
                    }
                }
                
            })
            
        }
        else
        {
            print("getRequest error")
            let alertInfo = UIAlertView(title: "获取失败", message: "获取接口地址错误!", delegate: nil, cancelButtonTitle: "关闭")
            alertInfo.show()
        }
    }
    
    
    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Portrait
    }
    
    override func preferredInterfaceOrientationForPresentation() -> UIInterfaceOrientation {
        return UIInterfaceOrientation.Portrait
    }
    

}
