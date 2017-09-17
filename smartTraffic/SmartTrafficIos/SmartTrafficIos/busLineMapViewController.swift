//
//  busLineMapViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/28.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busLineMapViewController: UIViewController,CNMKMapViewDelegate,NoteCBDelegate,CNMKSearchDelegate,BusProtocol,CLLocationManagerDelegate  {

    var startPoint:CLLocationCoordinate2D = CLLocationCoordinate2DMake(30.758, 120.749)
    var selectBusStation:BusLine.LineStation?

    var busLine:BusLine!
    var showBusLine:ShowBusLine!
    var avErrorInfo:UIAlertView!
    
    var mapView:CNMKMapView!
    var GScreenWidth:CGFloat = 0.0
    var GScreenHeight:CGFloat = 0.0
    var gSearch:CNMKSearch!
    var currLocation : CLLocation!
    let locationManager : CLLocationManager = CLLocationManager()

    var busStationAnno:[CNMKAnnotation] = []
    var selectAnno:CNMKAnnotation!
    
    @IBOutlet weak var labSelectBusStationName:UILabel!
    @IBOutlet weak var labSelectBusStationInfo:UILabel!
    
    @IBAction func btnGoHere(sender: UIButton) {
        self.performSegueWithIdentifier("segueBusLineMapToDrive", sender: nil)
    }
    
    

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueBusLineMapToDrive":
                let vc = segue.destinationViewController as! driveViewController
                let startPoint02 = JZLocationConverter.wgs84ToGcj02(startPoint)
                vc.geoStartPoint = CNMKGeoPointMake(startPoint02.longitude, startPoint02.latitude)
                
                let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(selectBusStation!.Latitude, selectBusStation!.Longitude))
                vc.geoEndPoint = CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude)
                break
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

    @IBAction func ubbLineList(sender: UIBarButtonItem) {
        mapView.delegate = nil
        mapView.gNoteCBDelegate = nil
        gSearch.delegate = nil
        mapView.removeAnnotations(busStationAnno)
        mapView.removeRoutingResult()
        mapView.removeMapObjects()
        self.dismissViewControllerAnimated(true, completion: nil)
    }

    func locationCenter()
    {
        let location02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(30.758, 120.749))
        mapView.setCenterCoordinate(CNMKGeoPointMake(location02.longitude, location02.latitude), animated: true)
    }
    
    func pinIconClickedCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
        if (FunctionName == "getPinPressed")
        {
            if let tag = DicGetString.objectForKey("2")?.integerValue
            {
//                showAnnos(tag,normalIcon: "new_pin_red",selectIcon: "new_pin_purple")
                if busStationAnno.count > tag
                {
                    mapView.removeAnnotation(selectAnno)
                    selectAnno = CNMKAnnotation.annotationWithCoordinate(busStationAnno[tag].coordinate) as! CNMKAnnotation
                    mapView.addAnnotation(selectAnno, viewHande: nil, customIcon: BusStation.BusStationIcon.select.rawValue as String)
                    
                }
                switch (showBusLine.Direct)
                {
                case 1:
                    if busLine.UpList.count > tag
                    {
                        selectBusStation = busLine.UpList[tag]
                        labSelectBusStationName.text = "\(selectBusStation!.StationName)"
                        labSelectBusStationInfo.text = "营运时间：\(selectBusStation!.StartTime) - \(selectBusStation!.EndTime)"
                    }
                    break
                case 2:
                    if busLine.DownList.count > tag
                    {
                        selectBusStation = busLine.DownList[tag]
                        labSelectBusStationName.text = "\(selectBusStation!.StationName)"
                        labSelectBusStationInfo.text = "营运时间：\(selectBusStation!.StartTime) - \(selectBusStation!.EndTime)"
                    }
                    break
                default:
                    break
                }
                
                
            }
            
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

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
        locationCenter()
        
        BusCoordinate.initBusCoordinate(self, lineID: showBusLine.Id, direct: showBusLine.Direct)
                // Do any additional setup after loading the view.
        
        switch (showBusLine.Direct)
        {
        case 1:
            for var i = 0 ; i < busLine.UpList.count ; i++
            {
                let busStationCoodinate84 = CLLocationCoordinate2D(latitude: busLine.UpList[i].Latitude, longitude: busLine.UpList[i].Longitude)
                let busStation02 = JZLocationConverter.wgs84ToGcj02(busStationCoodinate84)
                let busShowPoint = CNMKGeoPoint(longitude: busStation02.longitude, latitude: busStation02.latitude)
                let anno = CNMKAnnotation.annotationWithCoordinate(busShowPoint) as! CNMKAnnotation
                anno.tag = i
                anno.title1 = busLine.UpList[i].StationName
                busStationAnno.append(anno)
                
                mapView.setCenterCoordinate(CNMKGeoPointMake(busLine.UpList[0].Longitude, busLine.UpList[0].Latitude), animated: true)
                selectBusStation = busLine.UpList[0]
                
                labSelectBusStationName.text = "\(selectBusStation!.StationName)"
                labSelectBusStationInfo.text = "营运时间：\(selectBusStation!.StartTime) - \(selectBusStation!.EndTime)"
            }
            
            break
        case 2:
            for var i = 0 ; i < busLine.DownList.count ; i++
            {
                let busStationCoodinate84 = CLLocationCoordinate2D(latitude: busLine.DownList[i].Latitude, longitude: busLine.DownList[i].Longitude)
                let busStation02 = JZLocationConverter.wgs84ToGcj02(busStationCoodinate84)
                let busShowPoint = CNMKGeoPoint(longitude: busStation02.longitude, latitude: busStation02.latitude)
                let anno = CNMKAnnotation.annotationWithCoordinate(busShowPoint) as! CNMKAnnotation
                anno.tag = i
                anno.title1 = busLine.DownList[i].StationName
                busStationAnno.append(anno)
                
                mapView.setCenterCoordinate(CNMKGeoPointMake(busLine.DownList[0].Longitude, busLine.DownList[0].Latitude), animated: true)
                selectBusStation = busLine.DownList[0]
                
                labSelectBusStationName.text = "\(selectBusStation!.StationName)"
                labSelectBusStationInfo.text = "营运时间：\(selectBusStation!.StartTime) - \(selectBusStation!.EndTime)"
            }
            break
        default:
            break
        }
        
        //        showAnnos(-1,normalIcon: "new_pin_red",selectIcon: "new_pin_purple")
        if (busStationAnno.count > 0)
        {
            for annoIdx in busStationAnno
            {
                mapView.addAnnotation(annoIdx, viewHande: nil, customIcon: BusStation.BusStationIcon.normal.rawValue as String)
            }
            //            mapView.addAnnotations(busStationAnno, viewHande: nil)
        }
        
        

    }
    
    override func viewDidAppear(animated: Bool) {
//        locationManager.requestAlwaysAuthorization()
//        if CLLocationManager.locationServicesEnabled()
//        {
//            locationManager.delegate = self
//            //设备使用电池供电时最高的精度
//            locationManager.desiredAccuracy = kCLLocationAccuracyBest
//            //精确到10米,距离过滤器，定义了设备移动后获得位置信息的最小距离
//            locationManager.distanceFilter = kCLLocationAccuracyNearestTenMeters
//            locationManager.startUpdatingLocation()
//            
//        }
//        else
//        {
//            println("定位服务不可用。")
//        }
        
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
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        
        let location02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(currLocation.coordinate.latitude, currLocation.coordinate.longitude))
        mapView.setCenterCoordinate(CNMKGeoPointMake(location02.longitude, location02.latitude), animated: true)
        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        print(error)
    }
    

    func updateBusCoordinate(busCoordinate: BusCoordinate) {
//        var busLineCoordinate = busCoordinate.coordinate.characters.split(maxSplit: busCoordinate.coordinate.lengthOfBytesUsingEncoding(NSUTF8StringEncoding), allowEmptySlices: false) { (char:Character) -> Bool in
//            return char == ";"
//        }.map { String($0) }
        
        let busLineCoordinate = busCoordinate.coordinate.characters.split(busCoordinate.coordinate.lengthOfBytesUsingEncoding(NSUTF8StringEncoding), allowEmptySlices: false) { (char:Character) -> Bool in
            return char == ";"
        }.map { String($0)}
        var lineGeoPointList = [CNMKGeoPoint]()
        for busCoor in busLineCoordinate
        {
            var busLinePoint = busCoor.characters.split(busCoor.lengthOfBytesUsingEncoding(NSUTF8StringEncoding), allowEmptySlices: false, isSeparator: { (char:Character) -> Bool in
                return char == ","
            }).map { String($0) }
            let lineGeoPoint84 = CLLocationCoordinate2DMake((busLinePoint[1] as NSString).doubleValue, (busLinePoint[0] as NSString).doubleValue)
//            var lineGeoPoint02 = JZLocationConverter.wgs84ToGcj02(lineGeoPoint84)
            //为什么三网给的轨迹坐标直接是gcj02的？
            let lineGeoPoint = CNMKGeoPointMake(lineGeoPoint84.longitude, lineGeoPoint84.latitude)
            lineGeoPointList.append(lineGeoPoint)
        }
        
        let polyLine = CNMKPolyline.polylineWithGeoPoints(&lineGeoPointList, count: UInt(lineGeoPointList.count)) as! CNMKPolyline
        
        if(mapView != nil)
        {
            mapView.driveLineDraw(polyLine, lineWidth: 6, colorR: 44.0/255.0, colorG: 102.0/255.0, colorB: 233.0/255.0, alpha: 1.0)
        }
        

    }
    
    func updateBusStationList(busStationList: [BusStation]) {
        
    }
    
    func updateBusLocation(busLocation: BusLocation) {
        
    }
    
    func updateBusLineList(busLineList: [BusLine]) {
        
    }
    
    func updateBusInfoError(error: String) {
        avErrorInfo = UIAlertView(title: "获取轨迹错误", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
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
