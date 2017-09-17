//
//  busTransferResultMapViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/27.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busTransferResultMapViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CNMKSearchDelegate,CLLocationManagerDelegate {

    var drewLine:DrewLine!
    let textColor = UIColor(red: 0x27/0xff, green: 0x46/0xff, blue: 0xa2/0xff, alpha: 1.0)
    
    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0
    var gSearch:CNMKSearch!
    var currLocation : CLLocation!
    let locationManager : CLLocationManager = CLLocationManager()
    
    var tempDoc:NSDictionary!
    var startAnno:CNMKAnnotation!
    var endAnno:CNMKAnnotation!

    
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
        mapView.isShowStartIcon = false
        mapView.isShowEndIcon = false
        
        // Do any additional setup after loading the view.
        switch (drewLine.drewType)
        {
        case 0:
            mapView.isShowStartIcon = true
            mapView.isShowEndIcon = true
            gSearch.stepWalkSearch(drewLine.startPoint!, endPoint: drewLine.endPoint!, language: 1, directionIconStatus: 1)
            break
        case 1:
            startAnno = CNMKAnnotation.annotationWithCoordinate(drewLine.lines![0]) as! CNMKAnnotation
            endAnno = CNMKAnnotation.annotationWithCoordinate(drewLine.lines![drewLine.lines!.count - 1]) as! CNMKAnnotation
            let polyLine = CNMKPolyline.polylineWithGeoPoints(&drewLine.lines!, count: UInt(drewLine.lines!.count)) as! CNMKPolyline
            
            if(mapView != nil)
            {
                mapView.driveLineDraw(polyLine, lineWidth: 6, colorR: 44.0/255.0, colorG: 102.0/255.0, colorB: 233.0/255.0, alpha: 1.0)
                mapView.addAnnotation(startAnno, viewHande: nil, customIcon: "new_icon_nav_start")
                mapView.addAnnotation(endAnno, viewHande: nil, customIcon: "new_icon_nav_end")

            }
            break
        case 2:
            if let transfer = drewLine.transfer
            {
                mapView.isShowStartIcon = false
                mapView.isShowEndIcon = false
                for var i = 0 ; i < transfer.NewList.count ; i++
                {
                    switch i
                    {
                    case 0:
                        let firstPoint = transfer.NewList[i]
                        let Point02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(firstPoint.Latitude, firstPoint.Longitude))
                        gSearch.stepWalkSearch(drewLine.startPoint!, endPoint: CNMKGeoPointMake(Point02.longitude, Point02.latitude), language: 1, directionIconStatus: 1)
                        startAnno = CNMKAnnotation.annotationWithCoordinate(drewLine.startPoint!) as! CNMKAnnotation
                        if(mapView != nil)
                        {
                            mapView.addAnnotation(startAnno, viewHande: nil, customIcon: "new_icon_nav_start")
                        }
                    case transfer.NewList.count - 1:
                        let lastPoint = transfer.NewList[i - 1]
                        let Point02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(lastPoint.Latitude, lastPoint.Longitude))
                        gSearch.stepWalkSearch(CNMKGeoPointMake(Point02.longitude, Point02.latitude), endPoint: drewLine.endPoint!, language: 1, directionIconStatus: 1)
                        endAnno = CNMKAnnotation.annotationWithCoordinate(drewLine.endPoint!) as! CNMKAnnotation
                        if(mapView != nil)
                        {
                            mapView.addAnnotation(endAnno, viewHande: nil, customIcon: "new_icon_nav_end")
                        }
                    default:
                        let transferList = transfer.NewList[i]
                        switch (transferList.Type)
                        {
                        case 0 , 2:
                            let startTransfer = transfer.NewList[i - 1]
                            let startPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(startTransfer.Latitude, startTransfer.Longitude))
                            let endTransfer = transfer.NewList[i]
                            let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(endTransfer.Latitude, endTransfer.Longitude))
                            gSearch.stepWalkSearch(CNMKGeoPointMake(startPoint02.longitude, startPoint02.latitude), endPoint: CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude), language: 1, directionIconStatus: 1)
                        case 1:
                            var lineGeoPointList = [CNMKGeoPoint]()
                            for lineTrails in transferList.LineTrailsList
                            {
                                let lineGeoPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(lineTrails.Latitude, lineTrails.Longitude))
                                let lineGeoPoint = CNMKGeoPointMake(lineGeoPoint02.longitude, lineGeoPoint02.latitude)
                                lineGeoPointList.append(lineGeoPoint)
                            }
                            let polyLine = CNMKPolyline.polylineWithGeoPoints(&lineGeoPointList, count: UInt(lineGeoPointList.count)) as! CNMKPolyline
                            
                            if(mapView != nil)
                            {
                                mapView.driveLineDraw(polyLine, lineWidth: 6, colorR: 44.0/255.0, colorG: 102.0/255.0, colorB: 233.0/255.0, alpha: 1.0)
                            }
                        default:
                            break
                        }
                        break
                    }
                }
            }
        default:
            break
        }
        
        

    }
    
    func mapView(mapView: CNMKMapView!, viewForAnnotation annotation: CNMKAnnotationOverlay!) -> CNMKAnnotationView! {
        if (annotation is CNMKAnnotation)
        {
            let pinView = CNMKPinView(overlay: annotation)
            return pinView
        }
        return nil
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func ubbTransferList(sender: UIBarButtonItem) {
        self.dismissViewControllerAnimated(true, completion: nil)
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
        
    }

    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Portrait
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

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
