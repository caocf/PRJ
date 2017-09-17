//
//  taxiMapViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/7/9.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class taxiMapViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CLLocationManagerDelegate,TaxiDelegate {

    var avErrorInfo:UIAlertView!
    var currLocation : CLLocation!
    let locationManager : CLLocationManager = CLLocationManager()
    
    var startPoint:CLLocationCoordinate2D = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(Tools.defLat, Tools.defLon))
    var selectTaxi:Taxi?
    
    @IBOutlet weak var labSelectTaxiName:UILabel!
    @IBOutlet weak var labSelectTaxiInfo:UILabel!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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

    @IBAction func btnCall(sender: UIButton) {
        TaxiPhone.initTaxi(self, taxiName: labSelectTaxiName.text!)
    }
    
    @IBAction func tbbList(sender: UIBarButtonItem) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    var taxiAnno:[CNMKAnnotation] = []
    var taxi = [Taxi]()
        {
        didSet{
            for var i = 0 ; i < taxi.count ; i++
            {
                
                let taxiCoodinate = CLLocationCoordinate2D(latitude: Double(taxi[i].wd) / 1000000, longitude: Double(taxi[i].jd) / 1000000)
                let taxi02 = JZLocationConverter.wgs84ToGcj02(taxiCoodinate)
                let taxiPoint = CNMKGeoPoint(longitude: taxi02.longitude, latitude: taxi02.latitude)
                let anno = CNMKAnnotation.annotationWithCoordinate(taxiPoint) as! CNMKAnnotation
                anno.tag = i
                anno.title1 = taxi[i].sbid
                taxiAnno.append(anno)
            }
            
        }
    }
    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    override func viewDidAppear(animated: Bool) {
        self.edgesForExtendedLayout = UIRectEdge.None
        
        GScreenWidth = UIScreen.mainScreen().bounds.size.width  //...屏幕的宽
        GScreenHeight = UIScreen.mainScreen().bounds.size.height
        mapView = CNMKMapView(frame: CGRect(x: 0, y: 64, width: GScreenWidth, height: GScreenHeight))
        print(mapView.setMapLangue(0))
        mapView.mapType = UInt(CNMKMapTypeStandard)
        mapView.delegate = self
        mapView.gNoteCBDelegate = self
        self.view.addSubview(mapView)
        self.view.sendSubviewToBack(mapView)
        
        mapView.setZoomLevel(17)
        mapView.showsUserLocation = true
        mapView.setCenterCoordinate(CNMKGeoPointMake(startPoint.longitude, startPoint.latitude), animated: true)

        if (taxi.count > 0)
        {
            selectTaxi = taxi[0]
            labSelectTaxiName.text = "\(selectTaxi!.sbid)"
            TaxiDetail.initTaxi(self, taxiName: selectTaxi!.sbid)
        }
        
        if (taxiAnno.count > 0)
        {
            mapView.addAnnotations(taxiAnno, viewHande: nil)
        }
        
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

    func pinIconClickedCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
        if (FunctionName == "getPinPressed")
        {
            if let tag = DicGetString.objectForKey("2")?.integerValue
            {
                if (taxi.count > tag && tag > 0)
                {
                    selectTaxi = taxi[tag]
                    labSelectTaxiName.text = "\(selectTaxi!.sbid)"
                    TaxiDetail.initTaxi(self, taxiName: selectTaxi!.sbid)
//                    labSelectParkingInfo.text = "总车位:\(selectParking!.parklotcount)/空闲车位:\(selectParking!.freecount)/地点:\(selectParking!.roadname)"
                }
                
            }
            
        }
    }

    override func viewWillDisappear(animated: Bool) {
        mapView.delegate = nil
        mapView.gNoteCBDelegate = nil
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
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        
        let location02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(currLocation.coordinate.latitude, currLocation.coordinate.longitude))
        mapView.setCenterCoordinate(CNMKGeoPointMake(location02.longitude, location02.latitude), animated: true)
        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        print(error)
    }
    
    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Portrait
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
    
    func updateError(error: String) {
        avErrorInfo = UIAlertView(title: "出错", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
    }
    
    func updateTaxi(status: Int, msg: String, taxi: [Taxi]) {
        
    }
    
    func updateTaxiDetail(status: Int, msg: String, taxtDetail: [TaxiDetail]) {
        switch status
        {
        case 1:
            if taxtDetail.count > 0
            {
                labSelectTaxiInfo.text = taxtDetail[0].yhmc
            }
        case 0:
            avErrorInfo = UIAlertView(title: "获取失败", message: "没有找到该出租车的信息。", delegate: nil, cancelButtonTitle: "确定")
            avErrorInfo.show()
        default:
            avErrorInfo = UIAlertView(title: "获取失败", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
}
