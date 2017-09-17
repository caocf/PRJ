//
//  publicBikeStationViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/28.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class publicBikeStationViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,PublicBikeProtocol,UISearchBarDelegate,CLLocationManagerDelegate,IFlyRecognizerViewDelegate,PoiProtocol,mapChooseDelegate {

    var avErrorInfo:UIAlertView!
    var poView:PopupView!
    var ifView:IFlyRecognizerView!
    @IBOutlet weak var ubbChangeMap:UIBarButtonItem!
    @IBOutlet weak var tvStation:UITableView!
    @IBOutlet weak var usbStationSearch:UISearchBar!

    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    @IBOutlet weak var vMoreButton:UIView!
    var currLocation : CLLocation!
    
    var startPoint:CLLocationCoordinate2D = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(Tools.defLat, Tools.defLon))
    
    
    let locationManager : CLLocationManager = CLLocationManager()
    
    var publicBike:[PublicBike]!
    var bikeStationTitle = "历史记录"
    var poiSearch:Poi?
        {
        didSet{
            tvStation.reloadData()
        }
    }
    var history:[String] = []
        {
        didSet{
            tvStation.reloadData()
        }
    }
    
    var bSearchPoi:Bool = true
    var bHistory:Bool = true
    var bIsDefaultDistance = false

    var bIsFavor:Bool = false
    
    
    @IBAction func ubbChangeMap(sender: UIBarButtonItem) {
        if (publicBike.count > 0)
        {
            self.performSegueWithIdentifier("segueShowBikeMap", sender: publicBike)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "切换错误", message: "没有可以显示的公共自行车站点", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowBikeMap":
                let vc = segue.destinationViewController as! publicBikeMapViewController
                vc.publicBike = (sender as! [PublicBike])
                vc.startPoint = startPoint
                break
            case "segueBikeMapChoose":
                let vc = segue.destinationViewController as! mapChooseViewController
                vc.vcMapChoose = (sender as! publicBikeStationViewController)
            case "segueBikeToDrive":
                let vc = segue.destinationViewController as! driveViewController
                vc.geoStartPoint = CNMKGeoPointMake(startPoint.longitude, startPoint.latitude)
                let selectBike = (sender as! PublicBike)
                let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(selectBike.Latitude, selectBike.Longitude))
                vc.geoEndPoint = CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude)
            default:
                break
                
            }
        }
    }
    
    func updateChooseInfo(selectPoint: CNMKGeoPoint) {
        bikeStationTitle = "已为您找到您地图选点周围的站点"
        bSearchPoi = false
        bHistory = false
        startPoint = CLLocationCoordinate2DMake(selectPoint.latitude, selectPoint.longitude)
        let poing84 = JZLocationConverter.gcj02ToWgs84(CLLocationCoordinate2DMake(selectPoint.latitude, selectPoint.longitude))
        let ac = PublicBike.initNullPublicBike().getActionUrl(poing84.longitude, lantitude: poing84.latitude, distance: PublicBike.defaultDistance, count: PublicBike.defaultCount)
        PublicBike.initPublicBikeList(self, actionUrl: ac.0, httpBody: ac.1)
        aivLoading.startAnimating()
    }
    
    @IBAction func btnMyPosition(sender: UIButton) {
        vMoreButton.hidden = true
        if CLLocationManager.locationServicesEnabled()
        {
            locationManager.startUpdatingLocation()
            bSearchPoi = false
            bHistory = false
            bIsDefaultDistance = true
            bikeStationTitle = "已为您找到周围\(PublicBike.defaultDistance)米范围内最近的站点"
        }
        else
        {
            avErrorInfo = UIAlertView(title: "定位错误", message: "定位服务不可用，请打开定位服务。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            print("定位服务不可用。")
        }

    }
    
    
    @IBAction func btnFavor(sender: UIButton) {
        vMoreButton.hidden = true
        if let _ = LoginUser.user
        {
            self.performSegueWithIdentifier("segueBikeToFavor", sender: nil)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "打开收藏错误", message: "您尚未登录，请登陆后再打开收藏夹", delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }

    }

    @IBAction func btnVoice(sender: UIButton) {
        vMoreButton.hidden = true
        usbStationSearch.text = ""
        ifView.setParameter("iat", forKey:IFlySpeechConstant.IFLY_DOMAIN())
        ifView.setParameter("plain", forKey: IFlySpeechConstant.RESULT_TYPE())
        ifView.start()
    }
    
    @IBAction func btnMapChoose(sender: UIButton) {
        vMoreButton.hidden = true
        self.performSegueWithIdentifier("segueBikeMapChoose", sender: self)
    }
    
    func updatePoi(poi: Poi) {
        aivLoading.stopAnimating()
        self.poiSearch = poi
    }
   
    override func viewDidLoad() {
        super.viewDidLoad()
        ubbChangeMap.enabled = false
        tvStation.dataSource = self
        tvStation.delegate = self
        usbStationSearch.delegate = self
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
            avErrorInfo = UIAlertView(title: "定位错误", message: "定位服务不可用。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            print("定位服务不可用。")
        }
        
        
        poView = PopupView(frame: CGRectMake(100, 300, 0, 0))
        poView.ParentView = self.view
        
        self.ifView = IFlyRecognizerView(center: self.view.center)
        ifView.delegate = self
       
        if bIsFavor
        {
            bHistory = false
            bSearchPoi = false
            tvStation.reloadData()
            bIsFavor = false
        }
        else
        {
            bHistory = true
            bSearchPoi = true
            publicBike = [PublicBike]()
            if let his = History.readHistory("\(History.HistoryType.PublicBike.rawValue)")
            {
                history = his
            }
            
        }
                // Do any additional setup after loading the view.
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        startPoint = JZLocationConverter.wgs84ToGcj02(currLocation.coordinate)
        bSearchPoi = false
        let ac = PublicBike.initNullPublicBike().getActionUrl(currLocation.coordinate.longitude, lantitude: currLocation.coordinate.latitude, distance: PublicBike.defaultDistance, count: PublicBike.defaultCount)
        PublicBike.initPublicBikeList(self, actionUrl: ac.0, httpBody: ac.1)
        aivLoading.startAnimating()
        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        avErrorInfo = UIAlertView(title: "定位失败", message: error.localizedDescription, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
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
                return publicBike.count
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
                
                let cell = tableView.dequeueReusableCellWithIdentifier("stationCell", forIndexPath: indexPath) as! publicBikeStationTableViewCell
                let atIndexBike = publicBike[indexPath.row]
                cell.labStationID.text = "站点编号：\(atIndexBike.Id)"
                cell.labStationName.text = "站点名称：\(atIndexBike.Name)"
                cell.labAddress.text = "站点地址：\(atIndexBike.Address)"
                cell.labServiceTime.text = "服务时间：\(atIndexBike.ServiceTime)"
                cell.cellPublicBike = atIndexBike
                
                let sRentCount = NSMutableAttributedString(string: "\(atIndexBike.RentCount)")
                sRentCount.addAttribute(NSForegroundColorAttributeName, value: UIColor.redColor(), range: NSMakeRange(0, sRentCount.length))
                let sRecvCount = NSMutableAttributedString(string: "\(atIndexBike.Count-atIndexBike.RentCount)")
                sRecvCount.addAttribute(NSForegroundColorAttributeName, value: UIColor.redColor(), range: NSMakeRange(0, sRecvCount.length))
                let sStationInfo = NSMutableAttributedString(string: "站点信息：可借辆 可还辆")
                sStationInfo.insertAttributedString(sRecvCount, atIndex: sStationInfo.length - 1)
                sStationInfo.insertAttributedString(sRentCount, atIndex: 7)
                
                cell.labStationInfo.attributedText = sStationInfo
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
                usbStationSearch.text = history[indexPath.row]
                bHistory = false
                searchBar(usbStationSearch, textDidChange: usbStationSearch.text!)
            }
            else
            {
                history.removeAll()
                History.writeHistory("\(History.HistoryType.PublicBike.rawValue)", history: history)
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
                            History.writeHistory("\(History.HistoryType.PublicBike.rawValue)", history: history)
                        }
                        let poiCoordinate = CLLocationCoordinate2D(latitude: poiInfo.coordinate.latitude, longitude: poiInfo.coordinate.longitude)
                        startPoint = poiCoordinate
                        let poi84 = JZLocationConverter.gcj02ToWgs84(poiCoordinate)
                        let ac = PublicBike.initNullPublicBike().getActionUrl(poi84.longitude, lantitude: poi84.latitude, distance: PublicBike.defaultDistance, count: PublicBike.defaultCount)
                        PublicBike.initPublicBikeList(self, actionUrl: ac.0, httpBody: ac.1)
                        aivLoading.startAnimating()
                        bikeStationTitle = "已为您找到\"\(poiInfo.name)\"周边的站点"
                    }
                }
            }
        }
        
    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return bikeStationTitle
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
                return 165.0
            }
        }

    }
    
    func updatePublicBike(updatePublicBikes: [PublicBike]) {
        if bIsDefaultDistance
        {
            if (updatePublicBikes.count == 0)
            {
                bIsDefaultDistance = false
                let ac = PublicBike.initNullPublicBike().getActionUrl(currLocation.coordinate.longitude, lantitude: currLocation.coordinate.latitude, distance: PublicBike.extendDistance, count: PublicBike.defaultCount)
                PublicBike.initPublicBikeList(self, actionUrl: ac.0, httpBody: ac.1)
                bSearchPoi = false
                bHistory = false
                bikeStationTitle = "已为您找到周围\(PublicBike.extendDistance)米范围内最近的停车点"
            }
            else
            {
                ubbChangeMap.enabled = true
                aivLoading.stopAnimating()
                publicBike = updatePublicBikes
                PublicBike.updateRealTimeStationCount(self, bikeList: publicBike)

            }
        }
        else
        {
            aivLoading.stopAnimating()
            publicBike = updatePublicBikes
            if (publicBike.count == 0)
            {
                ubbChangeMap.enabled = false
                self.publicBike.removeAll()
                avErrorInfo = UIAlertView(title: "获取错误", message: "没有找到符合要求的公共自行车站点信息。", delegate: nil, cancelButtonTitle: "关闭")
                avErrorInfo.show()
            }
            else
            {
                ubbChangeMap.enabled = true
                PublicBike.updateRealTimeStationCount(self, bikeList: publicBike)
            }
        }
    }
    
    func updatePublicBike(updatePublicBike: PublicBike) {
        
    }
    
    func updatePublicBikeReal(updatePublicBikes: [PublicBike]) {
        publicBike = updatePublicBikes
        aivLoading.stopAnimating()
        tvStation.reloadData()
    }
    
    func getPublicBikeError(error: String) {
        if bIsDefaultDistance
        {
            
            bIsDefaultDistance = false
            let ac = PublicBike.initNullPublicBike().getActionUrl(currLocation.coordinate.longitude, lantitude: currLocation.coordinate.latitude, distance: PublicBike.extendDistance, count: PublicBike.defaultCount)
            PublicBike.initPublicBikeList(self, actionUrl: ac.0, httpBody: ac.1)
            bSearchPoi = false
            bHistory = false
            bikeStationTitle = "已为您找到周围\(PublicBike.extendDistance)米范围内最近的停车点"
            
        }
        else
        {
            ubbChangeMap.enabled = false
            aivLoading.stopAnimating()
            
            self.publicBike.removeAll()
            avErrorInfo = UIAlertView(title: "获取错误", message: "没有找到符合要求的公共自行车站点信息。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    func searchBarResultsListButtonClicked(searchBar: UISearchBar) {
        vMoreButton.hidden = !vMoreButton.hidden
    }
    
    func searchBar(searchBar: UISearchBar, textDidChange searchText: String) {
        vMoreButton.hidden = true
        ubbChangeMap.enabled = false
        if searchText == ""
        {
            bHistory = true
            bSearchPoi = true
            if let his = History.readHistory("\(History.HistoryType.PublicBike.rawValue)")
            {
                history = his
            }
            else
            {
                tvStation.reloadData()
            }
        }
        else
        {
            
            bHistory = false
            bSearchPoi = true
            poiSearch = Poi(poiProtocol: self, searchText: searchText)
            poiSearch?.poiSearch()
            aivLoading.startAnimating()
            bikeStationTitle = "已为您找到关键字为\"\(usbStationSearch.text!)\"的地点"
        }
    }
    
    func searchBarSearchButtonClicked(searchBar: UISearchBar) {
        print("searchBarSearchButtonClicked")
        vMoreButton.hidden = true
        
        bSearchPoi = true
        poiSearch = Poi(poiProtocol: self, searchText: usbStationSearch.text!)
        poiSearch?.poiSearch()
        aivLoading.startAnimating()
        bikeStationTitle = "已为您找到关键字为\"\(usbStationSearch.text!)\"的地点"
        
    }
    
    func searchBarShouldBeginEditing(searchBar: UISearchBar) -> Bool {
        print("searchBarShouldBeginEditing")
        vMoreButton.hidden = true
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
        if (usbStationSearch.text != "" && error.errorCode() == 0)
        {
            searchBarSearchButtonClicked(usbStationSearch)
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
            usbStationSearch.text = "\(usbStationSearch.text)\(retStr)"
        }
        
    }
    
    @IBAction func closeToPublicBike(segue: UIStoryboardSegue)
    {
        
        print("closeToPublicBike")
        
        if segue.sourceViewController is publicBikeMapViewController
        {
            let vc = segue.sourceViewController as! publicBikeMapViewController
            vc.mapView.delegate = nil
            vc.mapView.gNoteCBDelegate = nil
            vc.mapView.removeRoutingResult()
            vc.mapView.removeMapObjects()
        }
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
