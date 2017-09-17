//
//  mapChooseViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/14.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class mapChooseViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CLLocationManagerDelegate {

    var vcMapChoose:mapChooseDelegate!

    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0
    
    var currLocation : CLLocation!
    
    let locationManager : CLLocationManager = CLLocationManager()

    var currentLevel:Int32 = 14

    var selectPoint:CNMKGeoPoint!
    
    var anno:CNMKAnnotation!
    
    var startPoint:CLLocationCoordinate2D = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(Tools.defLat, Tools.defLon))
    
    @IBAction func ubbReturn(sender: UIBarButtonItem) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    @IBAction func ubbOk(sender: UIBarButtonItem) {
        if (vcMapChoose != nil)
        {
            vcMapChoose.updateChooseInfo(selectPoint)
        }
        self.dismissViewControllerAnimated(true, completion: nil)
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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.edgesForExtendedLayout = UIRectEdge.None
           }
    
    
    func handleMapViewLongPressedCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("handleMapViewLongPressedCB")

        if (FunctionName == "getClickPressed")
        {
            print("点击了地图")
            mapView.multipleTouchEnabled = false
            mapView.userInteractionEnabled = false
            mapView.scrollEnabled = false
        }

    
    }
    
    override func viewDidAppear(animated: Bool) {
        
        GScreenWidth = UIScreen.mainScreen().bounds.size.width  //...屏幕的宽
        GScreenHeight = UIScreen.mainScreen().bounds.size.height
        mapView = CNMKMapView(frame: CGRect(x: 0, y: 99, width: GScreenWidth, height: GScreenHeight - 35))
        print(mapView.setMapLangue(0))
        mapView.mapType = UInt(CNMKMapTypeStandard)
        mapView.delegate = self
        mapView.gNoteCBDelegate = self
        self.view.addSubview(mapView)
        self.view.sendSubviewToBack(mapView)
        
        mapView.setZoomLevel(currentLevel)
        mapView.showsUserLocation = true
        
        mapView.setCenterCoordinate(CNMKGeoPointMake(startPoint.longitude, startPoint.latitude), animated: true)

    }
    
    func mapView(mapView: CNMKMapView!, viewForAnnotation annotation: CNMKAnnotationOverlay!) -> CNMKAnnotationView! {
        if (annotation is CNMKAnnotation)
        {
            let pinView = CNMKPinView(overlay: annotation)
            //pinView.pinColor(CNMKPinColorGreen)
            return pinView
        }
        return nil
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
//        println("touchesBegan")
        
        let touch = touches.first! as UITouch
        let point = touch.locationInView(self.view)
//        println("x:\(point.x),y:\(point.y)")
        selectPoint = mapView.pixelToLngLat(point, toCoordinateFromView: self.view)
        print("lon:\(selectPoint.longitude),lan:\(selectPoint.latitude)")
        if (anno != nil)
        {
            mapView.removeAnnotation(anno)
        }
        
        anno = CNMKAnnotation.annotationWithCoordinate(selectPoint) as! CNMKAnnotation
        anno.tag = 10
        anno.title1 = "myPosition"
        mapView.addAnnotation(anno, viewHande: nil, customIcon: nil)
    }
    
    override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
//        println("touchesEnded")
        mapView.multipleTouchEnabled = true
        mapView.userInteractionEnabled = true
        mapView.scrollEnabled = true
  
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        
        let location84 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(currLocation.coordinate.latitude, currLocation.coordinate.longitude))
        mapView.setCenterCoordinate(CNMKGeoPointMake(location84.longitude, location84.latitude), animated: true)
        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        print(error)
    }

    override func viewWillDisappear(animated: Bool) {
        super.viewWillDisappear(animated)
        mapView.delegate = nil
        mapView.gNoteCBDelegate = nil
    }
    
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
        mapView.removeAnnotation(anno)
        mapView.removeMapObjects()
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

protocol mapChooseDelegate
{
    func updateChooseInfo(selectPoint:CNMKGeoPoint)
}
