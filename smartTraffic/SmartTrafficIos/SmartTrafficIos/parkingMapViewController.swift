//
//  parkingMapViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class parkingMapViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CLLocationManagerDelegate {

    var currLocation : CLLocation!
    let locationManager : CLLocationManager = CLLocationManager()

    var startPoint:CLLocationCoordinate2D!
    var selectParking:Parking?
    var selectAnno:CNMKAnnotation!

    
    @IBOutlet weak var labSelectParkingName:UILabel!
    @IBOutlet weak var labSelectParkingInfo:UILabel!
    @IBOutlet weak var vInfo:UIView!
    
    @IBAction func btnGoHere(sender: UIButton) {
        self.performSegueWithIdentifier("segueDrive", sender: nil)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueDrive":
                let vc = segue.destinationViewController as! driveViewController
                vc.geoStartPoint = CNMKGeoPointMake(startPoint.longitude, startPoint.latitude)
                
                let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(selectParking!.gpsla, selectParking!.gpslo))
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
    
    var parkingAnno:[CNMKAnnotation] = []
    var parking = [Parking]()
        {
        didSet{
//            for pTemp in parking
            for var i = 0 ; i < parking.count ; i++
            {
                let parkingCoodinate = CLLocationCoordinate2D(latitude: parking[i].gpsla, longitude: parking[i].gpslo)
                let parking84 = JZLocationConverter.wgs84ToGcj02(parkingCoodinate)
                let parkingPoint = CNMKGeoPoint(longitude: parking84.longitude, latitude: parking84.latitude)
                let anno = CNMKAnnotation.annotationWithCoordinate(parkingPoint) as! CNMKAnnotation
                anno.tag = i
                anno.title1 = parking[i].pointname
                parkingAnno.append(anno)
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
                if parkingAnno.count > tag
                {
                    mapView.removeAnnotation(selectAnno)
                    selectAnno = CNMKAnnotation.annotationWithCoordinate(parkingAnno[tag].coordinate) as! CNMKAnnotation
                    mapView.addAnnotation(selectAnno, viewHande: nil, customIcon: Parking.getIcon(true, parking: parking[tag]))
                    
                }

                if (parking.count > tag && tag > 0)
                {
                    selectParking = parking[tag]
                    labSelectParkingName.text = "\(selectParking!.pointname)"
                    labSelectParkingInfo.text = "总车位:\(selectParking!.parklotcount)/空闲车位:\(selectParking!.freecount)/地点:\(selectParking!.roadname)"
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
        
        if (parking.count > 0)
        {
            selectParking = parking[0]
            labSelectParkingName.text = "\(selectParking!.pointname)"
            labSelectParkingInfo.text = "总车位:\(selectParking!.parklotcount)/空闲车位:\(selectParking!.freecount)/地点:\(selectParking!.roadname)"
        }
        
        let parkingStartPoint = CNMKGeoPoint(longitude: startPoint.longitude, latitude: startPoint.latitude)
        let parkingStartAnno = CNMKAnnotation.annotationWithCoordinate(parkingStartPoint) as! CNMKAnnotation
        parkingStartAnno.tag = -1
        parkingStartAnno.title1 = "起点"
        mapView.addAnnotation(parkingStartAnno, viewHande: nil, customIcon: "new_icon_green.png")
        mapView.setCenterCoordinate(CNMKGeoPointMake(startPoint.longitude, startPoint.latitude), animated: true)

        
        if (parkingAnno.count > 0)
        {
            for anno in parkingAnno
            {
                mapView.addAnnotation(anno, viewHande: nil, customIcon: Parking.getIcon(false, parking: parking[anno.tag]))
            }
//            mapView.addAnnotations(parkingAnno, viewHande: nil)
        }
        
        
        
        // Do any additional setup after loading the view.
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
