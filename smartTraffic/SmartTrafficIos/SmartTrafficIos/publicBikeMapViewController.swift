//
//  publicBikeMapViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/7.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class publicBikeMapViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CLLocationManagerDelegate {

    var currLocation : CLLocation!
    let locationManager : CLLocationManager = CLLocationManager()
    
    var startPoint:CLLocationCoordinate2D!
    var selectBike:PublicBike?
    var selectAnno:CNMKAnnotation!


    @IBOutlet weak var labSelectBikeName:UILabel!
    @IBOutlet weak var labSelectBikeInfo:UILabel!
    
    @IBAction func btnGoHere(sender: UIButton) {
        self.performSegueWithIdentifier("segueBikeMapToDrive", sender: nil)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueBikeMapToDrive":
                let vc = segue.destinationViewController as! driveViewController
                vc.geoStartPoint = CNMKGeoPointMake(startPoint.longitude, startPoint.latitude)
                
                let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(selectBike!.Latitude, selectBike!.Longitude))
                vc.geoEndPoint = CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude)
                break
            default:
                break
                
            }
        }
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
    
    var bikeAnno:[CNMKAnnotation] = []
    var publicBike:[PublicBike] = []
        {
        didSet{
//            for bike in publicBike
            for var i = 0 ; i < publicBike.count ; i++
            {
                let bikeCoodinate = CLLocationCoordinate2D(latitude: publicBike[i].Latitude, longitude: publicBike[i].Longitude)
                let bike84 = JZLocationConverter.wgs84ToGcj02(bikeCoodinate)
                let bikePoint = CNMKGeoPoint(longitude: bike84.longitude, latitude: bike84.latitude)
                let anno = CNMKAnnotation.annotationWithCoordinate(bikePoint) as! CNMKAnnotation
                anno.tag = i
                anno.title1 = publicBike[i].Name
                bikeAnno.append(anno)
            }
            
        }
    }
    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0
    
    func pinIconClickedCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
        if (FunctionName == "getPinPressed")
        {
            if let tag = DicGetString.objectForKey("2")?.integerValue
            {
                if publicBike.count > tag
                {
                    mapView.removeAnnotation(selectAnno)
                    selectAnno = CNMKAnnotation.annotationWithCoordinate(bikeAnno[tag].coordinate) as! CNMKAnnotation
                    mapView.addAnnotation(selectAnno, viewHande: nil, customIcon: PublicBike.getIcon(true, publicBike: publicBike[tag]))
                    
                }
                if (publicBike.count > tag && tag > 0)
                {
                    selectBike = publicBike[tag]
                    labSelectBikeName.text = "\(selectBike!.Name)"
                    labSelectBikeInfo.text = "可还车位:\(selectBike!.Count - selectBike!.RentCount)/可租车位:\(selectBike!.RentCount)/地点:\(selectBike!.Address)"
                }
                
            }
            
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.edgesForExtendedLayout = UIRectEdge.None
        
        GScreenWidth = UIScreen.mainScreen().bounds.size.width  //...屏幕的宽
        GScreenHeight = UIScreen.mainScreen().bounds.size.height
        mapView = CNMKMapView(frame: CGRect(x: 0, y: 0, width: GScreenWidth, height: GScreenHeight))
        print(mapView.setMapLangue(0))
        mapView.mapType = UInt(CNMKMapTypeStandard)
        mapView.delegate = self
        mapView.gNoteCBDelegate = self
        self.view.addSubview(mapView)
        self.view.sendSubviewToBack(mapView)
        
        mapView.setZoomLevel(14)
        mapView.showsUserLocation = true
        
        if (publicBike.count > 0)
        {
        
            selectBike = publicBike[0]
            
            labSelectBikeName.text = "\(selectBike!.Name)"
            labSelectBikeInfo.text = "可还车位:\(selectBike!.Count - selectBike!.RentCount)/可租车位:\(selectBike!.RentCount)/地点:\(selectBike!.Address)"
            
        }
        
        let bikeStartPoint = CNMKGeoPoint(longitude: startPoint.longitude, latitude: startPoint.latitude)
        let bikeStartAnno = CNMKAnnotation.annotationWithCoordinate(bikeStartPoint) as! CNMKAnnotation
        bikeStartAnno.tag = -1
        bikeStartAnno.title1 = "起点"
        mapView.addAnnotation(bikeStartAnno, viewHande: nil, customIcon: "new_icon_green.png")
//        mapView.addAnnotation(bikeStartAnno, viewHande: nil, customIcon: "暗红色.png")
        mapView.setCenterCoordinate(CNMKGeoPointMake(startPoint.longitude, startPoint.latitude), animated: true)
        
        if (bikeAnno.count > 0)
        {
//            mapView.addAnnotations(bikeAnno, viewHande: nil)
            for anno in bikeAnno
            {
                mapView.addAnnotation(anno, viewHande: nil, customIcon: PublicBike.getIcon(false, publicBike: publicBike[anno.tag]))
            }
        }
        
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillDisappear(animated: Bool) {
//        mapView.delegate = nil
//        mapView.gNoteCBDelegate = nil
    }
    

    
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
//        mapView.removeAnnotations(bikeAnno)
//        mapView.removeMapObjects()
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
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
