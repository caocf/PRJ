//
//  busStationMapViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/29.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busStationMapViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CLLocationManagerDelegate {

    var currLocation : CLLocation!
    let locationManager : CLLocationManager = CLLocationManager()
    
    var startPoint:CLLocationCoordinate2D!
    var selectBusStation:BusStation?
    var selectAnno:CNMKAnnotation!

    
    var busStationAnno:[CNMKAnnotation] = []
    var busStation:[BusStation] = []
        {
        didSet{
            for var i = 0 ; i < busStation.count ; i++
            {
                let busStationCoodinate = CLLocationCoordinate2D(latitude: busStation[i].Latitude, longitude: busStation[i].Longitude)
                let busStation84 = JZLocationConverter.wgs84ToGcj02(busStationCoodinate)
                let busStationPoint = CNMKGeoPoint(longitude: busStation84.longitude, latitude: busStation84.latitude)
                let anno = CNMKAnnotation.annotationWithCoordinate(busStationPoint) as! CNMKAnnotation
                anno.tag = i
                anno.title1 = busStation[i].Name
                busStationAnno.append(anno)
            }
            
        }
    }

    
    @IBOutlet weak var labSelectBusStationName:UILabel!
    @IBOutlet weak var labSelectBusStationInfo:UILabel!
    
    @IBAction func btnGoHere(sender: UIButton) {
        self.performSegueWithIdentifier("segueBusStationMapToDrive", sender: nil)
    }
    
    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0

    
    @IBAction func ubbReturn(sender: UIBarButtonItem) {
        mapView.delegate = nil
        mapView.gNoteCBDelegate = nil
        mapView.removeAnnotations(busStationAnno)
        mapView.removeMapObjects()
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueBusStationMapToDrive":
                let vc = segue.destinationViewController as! driveViewController
        
                vc.geoStartPoint = CNMKGeoPointMake(startPoint.longitude, startPoint.latitude)
                
                let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(selectBusStation!.Latitude, selectBusStation!.Longitude))
                vc.geoEndPoint = CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude)
                break
            default:
                break
                
            }
        }
    }
    
    func pinIconClickedCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
        if (FunctionName == "getPinPressed")
        {
            if let tag = DicGetString.objectForKey("2")?.integerValue
            {
                if busStationAnno.count > tag
                {
                    mapView.removeAnnotation(selectAnno)
                    selectAnno = CNMKAnnotation.annotationWithCoordinate(busStationAnno[tag].coordinate) as! CNMKAnnotation
                    mapView.addAnnotation(selectAnno, viewHande: nil, customIcon: BusStation.BusStationIcon.select.rawValue as String)
                }
                if (busStation.count > tag && tag > 0)
                {
                    selectBusStation = busStation[tag]
                    labSelectBusStationName.text = "\(selectBusStation!.Name)"
                    labSelectBusStationInfo.text = "\(selectBusStation!.getAllLines())"
                }
                
            }
            
        }
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
        self.view.addSubview(mapView)
        self.view.sendSubviewToBack(mapView)
        
        mapView.setZoomLevel(14)
        mapView.showsUserLocation = true
        
        if (busStation.count > 0)
        {
            selectBusStation = busStation[0]
            labSelectBusStationName.text = "\(selectBusStation!.Name)"
            labSelectBusStationInfo.text = "\(selectBusStation!.getAllLines())"
        }
        
        let busStationStartPoint = CNMKGeoPoint(longitude: startPoint.longitude, latitude: startPoint.latitude)
        let busStationStartAnno = CNMKAnnotation.annotationWithCoordinate(busStationStartPoint) as! CNMKAnnotation
        busStationStartAnno.tag = -1
        busStationStartAnno.title1 = "起点"
        mapView.addAnnotation(busStationStartAnno, viewHande: nil, customIcon: "new_icon_green.png")
        mapView.setCenterCoordinate(CNMKGeoPointMake(startPoint.longitude, startPoint.latitude), animated: true)
        
        
        if (busStationAnno.count > 0)
        {
            for anno in busStationAnno
            {
                mapView.addAnnotation(anno, viewHande: nil, customIcon: BusStation.BusStationIcon.normal.rawValue as String)
            }
            //            mapView.addAnnotations(busStationAnno, viewHande: nil)
        }


        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(animated: Bool) {
        
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
        
        let location84 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(currLocation.coordinate.latitude, currLocation.coordinate.longitude))
        mapView.setCenterCoordinate(CNMKGeoPointMake(location84.longitude, location84.latitude), animated: true)
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
    
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
        locationManager.stopUpdatingLocation()
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
