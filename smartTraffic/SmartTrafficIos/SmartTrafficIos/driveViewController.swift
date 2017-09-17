//
//  driveViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/18.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class driveViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CNMKSearchDelegate,CLLocationManagerDelegate {

    var geoStartPoint:CNMKGeoPoint!
    var geoEndPoint:CNMKGeoPoint!

    var tempDoc:NSDictionary!

    
    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0
    var gSearch:CNMKSearch!
    
    var currLocation : CLLocation!
    let locationManager : CLLocationManager = CLLocationManager()

    @IBAction func ubbReturn(sender: UIBarButtonItem) {
        mapView.delegate = nil
        mapView.gNoteCBDelegate = nil
        gSearch.delegate = nil
        mapView.removeRoutingResult()
        mapView.removeMapObjects()
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    @IBAction func ubbText(sender: UIBarButtonItem) {
        self.performSegueWithIdentifier("segueDriveText", sender: tempDoc)
    }
    
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueDriveText":
                let vc = segue.destinationViewController as! driveTextViewController
                vc.driveText = (sender as! NSDictionary)
            default:
                break
                
            }
        }
    }

    
    @IBAction func btnLocation(sender: UIButton) {
        locationManager.startUpdatingLocation()
    }
    
    @IBAction func btnZoomLarge(sender: UIButton) {
        mapView.zoomIn()
    }
    
    @IBAction func btnZoomSmall(sender: UIButton) {
        mapView.zoomOut()
    }

    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.edgesForExtendedLayout = UIRectEdge.None

        GScreenWidth = UIScreen.mainScreen().bounds.size.width  //...屏幕的宽
        GScreenHeight = UIScreen.mainScreen().bounds.size.height
        mapView = CNMKMapView(frame: CGRect(x: 0, y: 64, width: GScreenWidth, height: GScreenHeight))
        print(mapView.setMapLangue(0))
        mapView.mapType = UInt(CNMKMapTypeStandard)
        mapView.delegate = self
        mapView.gNoteCBDelegate = self
        gSearch = CNMKSearch()
        gSearch.delegate = self
        self.view.addSubview(mapView)
        self.view.sendSubviewToBack(mapView)
        
        mapView.setZoomLevel(14)
        mapView.showsUserLocation = true
        
        let bRet = gSearch.stepWalkSearch(geoStartPoint, endPoint: geoEndPoint, language: 1, directionIconStatus: 1)
        
        print("bRet = \(bRet)")
        
//        gSearch.drivingSearch(geoStartPoint, endPoint: geoEndPoint, iCostModel: 0, iCriterial: 0, language: 1, bear: -1, vehiclespeed: -1, directionIconStatus: 1)
        

        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(animated: Bool) {
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
    
    func mapView(mapView: CNMKMapView!, viewForAnnotation annotation: CNMKAnnotationOverlay!) -> CNMKAnnotationView! {
        if (annotation is CNMKAnnotation)
        {
            let pinView = CNMKPinView(overlay: annotation)
            pinView.pinColor = CNMKPinColorGreen
            return pinView
        }
        return nil
    }

    
    func getSDKDrintMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
        if (FunctionName == "getDringList")
        {
            tempDoc = DicGetString.copy() as! NSDictionary
            for var i = 0 ; i < tempDoc.count ; i++
            {
                let idx = NSString(format: "%d", i)
                print("\(i+1):\(tempDoc.valueForKey(idx as String))")
            }
        }
    }
    
    func getSDKDrawPointsCB(FunctionName: String!, id2 PointsGPS: UnsafeMutablePointer<CNMKGeoPoint>, id3 PointsCount: Int) {
        if (FunctionName == "getDrawPointList")
        {
//            for var i = 0 ; i < PointsCount ; i++
//            {
//                println("lat=\(PointsGPS[i].latitude),lon=\(PointsGPS[i].longitude)")
//            }
            
            let polyLine = CNMKPolyline.polylineWithGeoPoints(PointsGPS, count: UInt(PointsCount)) as! CNMKPolyline
            
            mapView.driveLineDraw(polyLine, lineWidth: 6, colorR: 44.0/255.0, colorG: 102.0/255.0, colorB: 233.0/255.0, alpha: 1.0)
            
            mapView.setZoomLevel(14)

        }

    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        
        let location02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(currLocation.coordinate.latitude, currLocation.coordinate.longitude))
        mapView.setCenterCoordinate(CNMKGeoPointMake(location02.longitude, location02.latitude), animated: true)
        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        print(error)
    }

    override func viewWillDisappear(animated: Bool) {
        super.viewWillDisappear(animated)
        locationManager.stopUpdatingLocation()
        
    }
    
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
       
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
