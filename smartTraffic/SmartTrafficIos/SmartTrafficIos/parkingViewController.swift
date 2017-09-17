//
//  parkingViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class parkingViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,UISearchBarDelegate,CLLocationManagerDelegate,IFlyRecognizerViewDelegate,PoiProtocol,ParkingProtocal,mapChooseDelegate {

    var poView:PopupView!
    var ifView:IFlyRecognizerView!
    @IBOutlet weak var ubbChangeMap:UIBarButtonItem!
    @IBOutlet weak var usbParking:UISearchBar!
    @IBOutlet weak var tvParking:UITableView!
    @IBOutlet weak var vMore:UIView!
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    var currLocation : CLLocation!
    var avErrorInfo:UIAlertView!
    var startPoint:CLLocationCoordinate2D = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(Tools.defLat, Tools.defLon))
    
    let locationManager : CLLocationManager = CLLocationManager()

    var parking:[Parking] = []
        {
        didSet{
            tvParking.reloadData()
            if parking.count > 0
            {
                ubbChangeMap.enabled = true
            }
            else
            {
                ubbChangeMap.enabled = false
            }
        }
    }
    var parkingTitle = "历史记录"
    var poiSearch:Poi?
        {
        didSet{
            tvParking.reloadData()
        }
    }
    var history:[String] = []
        {
        didSet{
            tvParking.reloadData()
        }
    }
    
    
    var bSearchPoi:Bool = true
    var bHistory:Bool = true
    var bIsDefaultDistance = false

    @IBAction func ubbChangeMap(sender: UIBarButtonItem) {
        if (parking.count > 0)
        {
            self.performSegueWithIdentifier("segueShowParkingMap", sender: parking)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "切换错误", message: "没有可以显示的停车点", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
      
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowParkingMap":
                let vc = segue.destinationViewController as! parkingMapViewController
                vc.parking = (sender as! [Parking])
                vc.startPoint = startPoint
                break
            case "segueParkingMapChoose":
                let vc = segue.destinationViewController as! mapChooseViewController
                vc.vcMapChoose = (sender as! parkingViewController)
            case "segueParkingToDrive":
                let vc = segue.destinationViewController as! driveViewController
                vc.geoStartPoint = CNMKGeoPointMake(startPoint.longitude, startPoint.latitude)
                let selectParking = (sender as! Parking)
                let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(selectParking.gpsla, selectParking.gpslo))
                vc.geoEndPoint = CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude)
            default:
                break
                
            }
        }
    }
    
    func updateChooseInfo(selectPoint: CNMKGeoPoint) {
        parkingTitle = "已为您找到您地图选点周围的停车点"
        bHistory = false
        bSearchPoi = false
        startPoint = CLLocationCoordinate2DMake(selectPoint.latitude, selectPoint.longitude)
        let poing84 = JZLocationConverter.gcj02ToWgs84(startPoint)
        Parking.initParking(self, radius: Parking.defaultRadius, lan: poing84.latitude, lon: poing84.longitude, page: 1, num: Parking.defaultNum, isReal: 1)
        aivLoading.startAnimating()

    }

    
    @IBAction func btnMyPosition(sender: UIButton) {
        vMore.hidden = true
        if CLLocationManager.locationServicesEnabled()
        {
            locationManager.startUpdatingLocation()
            bSearchPoi = false
            bHistory = false
            bIsDefaultDistance = true
            parkingTitle = "已为您找到周围\(Parking.defaultRadius)米范围内最近的停车点"
        }
        else
        {
            avErrorInfo = UIAlertView(title: "定位错误", message: "定位服务不可用，请打开定位服务。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            print("定位服务不可用。")
        }
        
    }
    
    @IBAction func btnVoice(sender: UIButton) {
        vMore.hidden = true
        usbParking.text = ""
        ifView.setParameter("iat", forKey:IFlySpeechConstant.IFLY_DOMAIN())
        ifView.setParameter("plain", forKey: IFlySpeechConstant.RESULT_TYPE())
        ifView.start()
    }
    
    @IBAction func btnMapChoose(sender: UIButton) {
        vMore.hidden = true
        self.performSegueWithIdentifier("segueParkingMapChoose", sender: self)
    }
    
    func updatePoi(poi: Poi) {
        aivLoading.stopAnimating()
        self.poiSearch = poi
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        ubbChangeMap.enabled = false
        tvParking.dataSource = self
        tvParking.delegate = self
        usbParking.delegate = self
        locationManager.requestAlwaysAuthorization()
        if CLLocationManager.locationServicesEnabled()
        {
            locationManager.delegate = self
            //设备使用电池供电时最高的精度
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            //精确到10米,距离过滤器，定义了设备移动后获得位置信息的最小距离
            locationManager.distanceFilter = kCLLocationAccuracyNearestTenMeters
//            locationManager.startUpdatingLocation()
            
        }
        else
        {
            avErrorInfo = UIAlertView(title: "定位错误", message: "定位服务不可用，请打开定位服务。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            print("定位服务不可用。")
        }
        
        bSearchPoi = true
        poView = PopupView(frame: CGRectMake(100, 300, 0, 0))
        poView.ParentView = self.view
        
        self.ifView = IFlyRecognizerView(center: self.view.center)
        ifView.delegate = self
        
        bHistory = true
        if let his = History.readHistory("\(History.HistoryType.Parking.rawValue)")
        {
            history = his
        }

        // Do any additional setup after loading the view.
    }

    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        startPoint = JZLocationConverter.wgs84ToGcj02(currLocation.coordinate)
        bHistory = false
        bSearchPoi = false
        parking.removeAll()
        Parking.initParking(self, radius: Parking.defaultRadius, lan: currLocation.coordinate.latitude, lon: currLocation.coordinate.longitude, page: 1, num: Parking.defaultNum, isReal: 1)
        aivLoading.startAnimating()
        locationManager.stopUpdatingLocation()
    }

    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        avErrorInfo = UIAlertView(title: "定位失败", message: error.localizedDescription, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }

    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if bHistory
        {
            if history.count > 0
            {
                return history.count + 1
            }
            else
            {
                return 0
            }
        }
        else
        {
            if bSearchPoi
            {
                if let poi = poiSearch, let res = poi.result
                {
                    return res.totalpage
                }
                else
                {
                    return 0
                }
            }
            else
            {
                return parking.count
            }

        }
        
    }

    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        if bHistory
        {
            let cell = tableView.dequeueReusableCellWithIdentifier("historyCell", forIndexPath: indexPath) 
            if history.count > 0
            {
                if (indexPath.row == history.count)
                {
                    cell.textLabel?.text = "清除历史记录"
                    cell.textLabel?.textAlignment = NSTextAlignment.Center
                    cell.imageView?.image = nil
                }
                else
                {
                    cell.textLabel?.textAlignment = NSTextAlignment.Left
                    cell.textLabel?.text = history[indexPath.row]
                    cell.imageView?.image = UIImage(named: "ic_history")
                }
            }
            return cell
        }
        else
        {
            if bSearchPoi
            {
                let cell = tableView.dequeueReusableCellWithIdentifier("poiCell", forIndexPath: indexPath) 
                if let poi = poiSearch, let res = poi.result
                {
                    if res.pois.count > indexPath.row
                    {
                        cell.textLabel?.text = res.pois[indexPath.row].name
                        cell.detailTextLabel?.text = res.pois[indexPath.row].addr
                    }
                    return cell
                }
                else
                {
                    return cell
                }
            }
            else
            {
                let cell = tableView.dequeueReusableCellWithIdentifier("parkingCell", forIndexPath: indexPath) as! parkingTableViewCell
                let atIndexParking = parking[indexPath.row]
                cell.labParkingName.text = "\(atIndexParking.pointname)"
                cell.labParkingInfo.text = "总车位:\(atIndexParking.parklotcount)/空闲车位:\(atIndexParking.freecount)/地点:\(atIndexParking.roadname)"
                cell.cellParking = atIndexParking
                return cell
            }
        }
    }
    

    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        if bHistory
        {
            if (indexPath.row < history.count)
            {
                usbParking.text = history[indexPath.row]
                bHistory = false
                searchBar(usbParking, textDidChange: usbParking.text!)
            }
            else
            {
                history.removeAll()
                History.writeHistory("\(History.HistoryType.Parking.rawValue)", history: history)
            }
        }
        else
        {
            if bSearchPoi
            {
                bSearchPoi = false
                if let poi = poiSearch, let res = poi.result
                {
                    if res.totalpage > 0
                    {
                        let poiInfo = (res.pois as! [CNMKPoiInfo])[indexPath.row]
                        if (!Tools.checkIsExist(history, item: poiInfo.name))
                        {
                            history.insert(poiInfo.name, atIndex: 0)
                            History.writeHistory("\(History.HistoryType.Parking.rawValue)", history: history)
                        }
                        let poiCoordinate = CLLocationCoordinate2D(latitude: poiInfo.coordinate.latitude, longitude: poiInfo.coordinate.longitude)
                        startPoint = poiCoordinate
                        //                    println("1.\(poiInfo.coordinate.longitude),\(poiInfo.coordinate.latitude)")
                        let poi84 = JZLocationConverter.gcj02ToWgs84(poiCoordinate)
                        //                    println("2.\(poi84.longitude),\(poi84.latitude)")
                        parking.removeAll()
                        Parking.initParking(self, radius: Parking.defaultRadius, lan: poi84.latitude, lon: poi84.longitude, page: 1, num: Parking.defaultNum, isReal: 1)
                        aivLoading.startAnimating()
                        parkingTitle = "已为您找到\"\(poiInfo.name)\"周边的停车点"
                    }
                }
            }
        }
    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return parkingTitle
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        if bHistory
        {
            return 50
        }
        else
        {
            if bSearchPoi
            {
                return 60.0
            }
            else
            {
                return 75.0
            }
        }
    }
    
    func updateParking(parking: [Parking]) {
        if bIsDefaultDistance
        {
            if (parking.count == 0)
            {
                bIsDefaultDistance = false
                Parking.initParking(self, radius: Parking.extendRadius, lan: currLocation.coordinate.latitude, lon: currLocation.coordinate.longitude, page: 1, num: Parking.defaultNum, isReal: 1)
                bSearchPoi = false
                bHistory = false
                parkingTitle = "已为您找到周围\(Parking.extendRadius)米范围内最近的停车点"
            }
            else
            {
                aivLoading.stopAnimating()
                self.parking = parking
            }
        }
        else
        {
            aivLoading.stopAnimating()
            self.parking = parking
            if (parking.count == 0)
            {
                self.parking.removeAll()
                avErrorInfo = UIAlertView(title: "获取错误", message: "没有找到符合要求的停车点信息。", delegate: nil, cancelButtonTitle: "关闭")
                avErrorInfo.show()
            }
        }
    }
    
    func updateParkingError(error: String) {
        if bIsDefaultDistance
        {
            bIsDefaultDistance = false
            Parking.initParking(self, radius: Parking.extendRadius, lan: currLocation.coordinate.latitude, lon: currLocation.coordinate.longitude, page: 1, num: Parking.defaultNum, isReal: 1)
            bSearchPoi = false
            bHistory = false
            parkingTitle = "已为您找到周围\(Parking.extendRadius)米范围内最近的停车点"
            
        }
        else
        {
            aivLoading.stopAnimating()
            self.parking.removeAll()
            avErrorInfo = UIAlertView(title: "获取停车场失败", message: error, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            print(error)
        }
        
        
    }
    
    func searchBarResultsListButtonClicked(searchBar: UISearchBar) {
        vMore.hidden = !vMore.hidden
    }
    
    func searchBarSearchButtonClicked(searchBar: UISearchBar) {
        print("searchBarSearchButtonClicked")
        vMore.hidden = true
        bSearchPoi = true
        poiSearch = Poi(poiProtocol: self, searchText: usbParking.text!)
        poiSearch?.poiSearch()
        aivLoading.startAnimating()
        parkingTitle = "请选择关键字为\"\(usbParking.text)\"的地点"
        
    }
    
    func searchBar(searchBar: UISearchBar, textDidChange searchText: String) {
        vMore.hidden = true
        ubbChangeMap.enabled = false
        if searchText == ""
        {
            bHistory = true
            if let his = History.readHistory("\(History.HistoryType.Parking.rawValue)")
            {
                history = his
            }
            else
            {
                tvParking.reloadData()
            }
        }
        else
        {
            bHistory = false
            bSearchPoi = true
            poiSearch = Poi(poiProtocol: self, searchText: searchText)
            poiSearch?.poiSearch()
            aivLoading.startAnimating()
            parkingTitle = "请选择关键字为\"\(searchText)\"的地点"
        }
    }
    
    func searchBarShouldBeginEditing(searchBar: UISearchBar) -> Bool {
        print("searchBarShouldBeginEditing")
        vMore.hidden = true
        return true
    }
    
    override func viewWillDisappear(animated: Bool) {
        //终止识别
        ifView.cancel()
        ifView.delegate = nil
        super.viewDidDisappear(animated)
    }
    
    func onError(error: IFlySpeechError!) {
        
        self.view.addSubview(poView)
        poView.setText("识别结束")
        print("errorCode:\(error.errorCode())")
        if (usbParking.text != "" && error.errorCode() == 0)
        {
            searchBarSearchButtonClicked(usbParking)
        }
        
    }
    
    func onResult(resultArray: [AnyObject]!, isLast: Bool) {
        var retStr = ""
        let result = resultArray as NSArray
        if let dic = result[0] as? NSDictionary
        {
            for (key,_) in dic
            {
                retStr += "\(key)"
            }
            usbParking.text = "\(usbParking.text)\(retStr)"
        }
        
    }
    
    @IBAction func closeToParking(segue: UIStoryboardSegue)
    {
        if segue.sourceViewController is parkingMapViewController
        {
            let vc = segue.sourceViewController as! parkingMapViewController
            vc.mapView.delegate = nil
            vc.mapView.gNoteCBDelegate = nil
            vc.mapView.removeAnnotations(vc.parkingAnno)
            vc.mapView.removeMapObjects()

        }
        print("closeToParking")
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
