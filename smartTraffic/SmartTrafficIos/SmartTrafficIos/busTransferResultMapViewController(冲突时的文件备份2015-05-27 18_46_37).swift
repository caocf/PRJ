//
//  busTransferResultMapViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/27.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busTransferResultMapViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CNMKSearchDelegate,CLLocationManagerDelegate {

    var transfer:Transfer!
    var startPoint = addrInput(type: TextType.TextInput)
    var endPoint = addrInput(type: TextType.TextInput)
    
    
    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0
    var gSearch:CNMKSearch!
    var currLocation : CLLocation!
    let locationManager : CLLocationManager = CLLocationManager()
    
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
            println("定位服务不可用。")
        }
        
        GScreenWidth = UIScreen.mainScreen().bounds.size.width  //...屏幕的宽
        GScreenHeight = UIScreen.mainScreen().bounds.size.height
        mapView = CNMKMapView(frame: CGRect(x: 0, y: 0, width: GScreenWidth, height: GScreenHeight))
        println(mapView.setMapLangue(0))
        mapView.mapType = UInt(CNMKMapTypeStandard)
        mapView.delegate = self
        mapView.gNoteCBDelegate = self
        gSearch = CNMKSearch()
        gSearch.delegate = self
        self.view.addSubview(mapView)
        self.view.sendSubviewToBack(mapView)
        
        mapView.setZoomLevel(14)
        mapView.showsUserLocation = true
        
        drawTransfer()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func ubbTransferList(sender: UIBarButtonItem) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    func locationManager(manager: CLLocationManager!, didUpdateLocations locations: [AnyObject]!){
        currLocation = locations.last as! CLLocation
        
        var location02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(currLocation.coordinate.latitude, currLocation.coordinate.longitude))
        mapView.setCenterCoordinate(CNMKGeoPointMake(location02.longitude, location02.latitude), animated: true)
        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(manager: CLLocationManager!, didFailWithError error: NSError!){
        println(error)
    }
    
    override func viewWillDisappear(animated: Bool) {
        mapView.delegate = nil
        mapView.gNoteCBDelegate = nil
        gSearch.delegate = nil
    }

    func mapView(mapView: CNMKMapView!, viewForAnnotation annotation: CNMKAnnotationOverlay!) -> CNMKAnnotationView! {
        if (annotation is CNMKAnnotation)
        {
            var pinView = CNMKPinView(overlay: annotation)
            pinView.pinColor = CNMKPinColorGreen
            return pinView
        }
        return nil
    }
    
//    func mapView(mapView: CNMKMapView!, viewForOverlay overlay: CNMKOverlay!) -> CNMKOverlayView! {
//        var polylineView = CNMKPolylineView(overlay: overlay)
//        polylineView.strokeColor = UIColor(red: 44.0/255.0, green: 102.0/255.0, blue: 233.0/255.0, alpha: 1.0)
//        polylineView.lineWidth = 6
//        return polylineView
//    }
    
    func drawTransfer()
    {
        for line in transfer.NewList
        {
            var lineList = [CNMKGeoPoint]()
            for LineTrails in line.LineTrailsList
            {
                var point02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(LineTrails.Latitude, LineTrails.Longitude))
                var point = CNMKGeoPointMake(point02.longitude, point02.latitude)
                lineList.append(point)
            }
            var polyLine = CNMKPolyline.polylineWithGeoPoints(&lineList, count: UInt(lineList.count)) as! CNMKPolyline
//            mapView.addOverlay(polyLine)
            mapView.driveLineDraw(polyLine, lineWidth: 6, colorR: 44.0/255.0, colorG: 102.0/255.0, colorB: 233.0/255.0, alpha: 1.0)

//            var polyLine = CNMKPolyline.polylineWithGeoPoints(PointsGPS, count: UInt(PointsCount)) as! CNMKPolyline
            
//            mapView.driveLineDraw(polyLine, lineWidth: 6, colorR: 44.0/255.0, colorG: 102.0/255.0, colorB: 233.0/255.0, alpha: 1.0)
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
