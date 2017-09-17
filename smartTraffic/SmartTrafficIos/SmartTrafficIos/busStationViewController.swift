//
//  busStationViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/3.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busStationViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,BusProtocol,UISearchBarDelegate,IFlyRecognizerViewDelegate,CLLocationManagerDelegate,PoiProtocol {

    var poView:PopupView!
    var ifView:IFlyRecognizerView!
    @IBOutlet weak var ubbChangeMap:UIBarButtonItem!
    @IBOutlet weak var usbStation:UISearchBar!
    @IBOutlet weak var tvStation:UITableView!
    @IBOutlet weak var vMoreButton:UIView!
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    var avErrorInfo:UIAlertView!
    var bSearchPoi:Bool = true
    var bHistory:Bool = true
    var bIsDefaultDistance = false

    var startPoint:CLLocationCoordinate2D = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(Tools.defLat, Tools.defLon))
    var currLocation : CLLocation!
    
    let locationManager : CLLocationManager = CLLocationManager()
    
    var busStationTitle = "历史记录"
    var busStation:[BusStation] = []
        {
        didSet{
            tvStation.reloadData()
        }
    }
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

    
    @IBAction func btnSearch(sender: UIButton) {
        vMoreButton.hidden = true
        searchBarSearchButtonClicked(usbStation)
    }
    
    
    @IBAction func ubbChangeMap(sender: UIBarButtonItem) {
        if (busStation.count > 0)
        {
            self.performSegueWithIdentifier("segueBusStationMap", sender: busStation)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "切换错误", message: "没有可以显示的公交车站点", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
  
    
    @IBAction func btnFavorite(sender: UIButton) {
        vMoreButton.hidden = true
        if let _ = LoginUser.user
        {
            self.performSegueWithIdentifier("segueStationToFavor", sender: nil)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "打开收藏错误", message: "您尚未登录，请登陆后再打开收藏夹", delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }
    }
    
    
    @IBAction func btnNearStation(sender: UIButton) {
        vMoreButton.hidden = true
        if CLLocationManager.locationServicesEnabled()
        {
            locationManager.startUpdatingLocation()
            bSearchPoi = false
            bHistory = false
            bIsDefaultDistance = true
            busStationTitle = "已为您找到周围\(BusStation.defaultDistance)米范围内最近的站点"
        }
        else
        {
            avErrorInfo = UIAlertView(title: "定位错误", message: "定位服务不可用，请打开定位服务。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            print("定位服务不可用。")
        }

    }
    
    
    @IBAction func btnVoice(sender: UIButton) {
        vMoreButton.hidden = true
        usbStation.text = ""
        ifView.setParameter("iat", forKey:IFlySpeechConstant.IFLY_DOMAIN())
        ifView.setParameter("plain", forKey: IFlySpeechConstant.RESULT_TYPE())
        ifView.start()
    }
    
    
    @IBAction func btnZBar(sender: UIButton) {
        self.performSegueWithIdentifier("segueStationToZbar", sender: nil)
    }
    
    func updatePoi(poi: Poi) {
        aivLoading.stopAnimating()
        self.poiSearch = poi
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        poView = PopupView(frame: CGRectMake(100, 300, 0, 0))
        poView.ParentView = self.view
        
        self.ifView = IFlyRecognizerView(center: self.view.center)
        ifView.delegate = self
        
        tvStation.dataSource = self
        tvStation.delegate = self
        usbStation.delegate = self

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
        
        ubbChangeMap.enabled = false
        
        bSearchPoi = true

        bHistory = true
        if let his = History.readHistory("\(History.HistoryType.BusStation.rawValue)")
        {
            history = his
        }
        // Do any additional setup after loading the view.
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
        startPoint = JZLocationConverter.wgs84ToGcj02(currLocation.coordinate)
        bSearchPoi = false
        BusStation.initBusStation(self, longtitude: currLocation.coordinate.longitude, lantitude: currLocation.coordinate.latitude, distance: BusStation.defaultDistance)
        aivLoading.startAnimating()
        locationManager.stopUpdatingLocation()

    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        print(error)
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
                return busStation.count
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
                let cell = tableView.dequeueReusableCellWithIdentifier("stationCell", forIndexPath: indexPath) 
                let busStationSelect = busStation[indexPath.row]
                cell.detailTextLabel?.text = "\(busStationSelect.getAllLines())"
                let stationLocation = CLLocation(latitude: busStationSelect.Latitude, longitude: busStationSelect.Longitude)
//                _ = stationLocation.distanceFromLocation(currLocation)
//                cell.textLabel?.text = "\(busStationSelect.Name)(\(Int(distanceToStation))米)"
                cell.textLabel?.text = "\(busStationSelect.Name)"

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
                usbStation.text = history[indexPath.row]
                bHistory = false
                searchBar(usbStation, textDidChange: usbStation.text!)
            }
            else
            {
                history.removeAll()
                History.writeHistory("\(History.HistoryType.BusLine.rawValue)", history: history)
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
                        let pois = res.pois as! [CNMKPoiInfo]
                        if(indexPath.row < pois.count)
                        {
                            let poiInfo = pois[indexPath.row]
                            if (!Tools.checkIsExist(history, item: poiInfo.name))
                            {
                                history.insert(poiInfo.name, atIndex: 0)
                                History.writeHistory("\(History.HistoryType.BusStation.rawValue)", history: history)
                            }
                            let poiCoordinate = CLLocationCoordinate2D(latitude: poiInfo.coordinate.latitude, longitude: poiInfo.coordinate.longitude)
                            let poi84 = JZLocationConverter.gcj02ToWgs84(poiCoordinate)
                            BusStation.initBusStation(self, longtitude: poi84.longitude, lantitude: poi84.latitude, distance: BusStation.defaultDistance)
                            
                            aivLoading.startAnimating()
                            busStationTitle = "已为您找到\"\(poiInfo.name)\"周边的站点"
                        }
                    }
                }
            }
            else
            {
                if (indexPath.row < busStation.count)
                {
                    self.performSegueWithIdentifier("segueShowStationDetail", sender: busStation[indexPath.row])
                }
            }
        }
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        if bHistory
        {
            return 50
        }
        else
        {
            return 60
        }
    }
    
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowStationDetail":
                let vc = segue.destinationViewController as! busStationDetailViewController
                vc.busStation = (sender as! BusStation)
                break
            case "segueStationToFavor":
                let vc = segue.destinationViewController as! busFavorViewController
                vc.iFavorType = 2
            case "segueBusStationMap":
                let vc = segue.destinationViewController as! busStationMapViewController
                vc.busStation = (sender as! [BusStation])
                vc.startPoint = startPoint
            default:
                break
                
            }
        }
        
    }

    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return busStationTitle
    }
    
    func updateBusCoordinate(busCoordinate: BusCoordinate) {
        
    }
    
    func updateBusInfoError(error: String) {
        if bIsDefaultDistance
        {
            bIsDefaultDistance = false
            BusStation.initBusStation(self, longtitude: currLocation.coordinate.longitude, lantitude: currLocation.coordinate.latitude, distance: BusStation.extendDistance)
            bSearchPoi = false
            bHistory = false
            busStationTitle = "已为您找到周围\(BusStation.extendDistance)米范围内最近的站点"
            
        }
        else
        {
            aivLoading.stopAnimating()
            
            self.busStation.removeAll()
            avErrorInfo = UIAlertView(title: "获取错误", message: "没有找到符合要求的公交站点信息。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            ubbChangeMap.enabled = false
        }
    }
    
    func updateBusLineList(busLineList: [BusLine]) {
        
    }
    
    func updateBusLocation(busLocation: BusLocation) {
        
    }
    
    func updateBusStationList(busStationList: [BusStation]) {
        aivLoading.stopAnimating()
        busStation = busStationList
        if bIsDefaultDistance
        {
            if (busStationList.count == 0)
            {
                bIsDefaultDistance = false
                BusStation.initBusStation(self, longtitude: currLocation.coordinate.longitude, lantitude: currLocation.coordinate.latitude, distance: BusStation.extendDistance)
                bSearchPoi = false
                bHistory = false
                busStationTitle = "已为您找到周围\(BusStation.extendDistance)米范围内最近的站点"
                ubbChangeMap.enabled = false
            }
            else
            {
                aivLoading.stopAnimating()
                busStation = busStationList
                ubbChangeMap.enabled = true
            }
        }
        else
        {
            aivLoading.stopAnimating()
            busStation = busStationList
            if (busStation.count == 0)
            {
                ubbChangeMap.enabled = false
                self.busStation.removeAll()
                avErrorInfo = UIAlertView(title: "获取错误", message: "没有找到符合要求的公交站点信息。", delegate: nil, cancelButtonTitle: "关闭")
                avErrorInfo.show()
            }
            else
            {
                ubbChangeMap.enabled = true
            }
        }


    }
    
    func searchBarResultsListButtonClicked(searchBar: UISearchBar) {
        vMoreButton.hidden = !vMoreButton.hidden
    }
    
    func searchBarShouldBeginEditing(searchBar: UISearchBar) -> Bool {
        vMoreButton.hidden = true
        return true
    }
    
    func searchBar(searchBar: UISearchBar, textDidChange searchText: String) {
        vMoreButton.hidden = true
        if searchText == ""
        {
            bHistory = true
            if let his = History.readHistory("\(History.HistoryType.BusStation.rawValue)")
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
            busStationTitle = "已为您找到关键字为\"\(usbStation.text!)\"的地点"        }
        //        BusLine.initBusLine(self, lineName: searchBar.text)
    }
    
    func searchBarSearchButtonClicked(searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        vMoreButton.hidden = true
        ubbChangeMap.enabled = false
        bSearchPoi = true
        poiSearch = Poi(poiProtocol: self, searchText: usbStation.text!)
        poiSearch?.poiSearch()
        aivLoading.startAnimating()
        busStationTitle = "已为您找到关键字为\"\(usbStation.text!)\"的地点"


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
        if (usbStation.text != "" && error.errorCode() == 0)
        {
            searchBarSearchButtonClicked(usbStation)
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
            usbStation.text = "\(usbStation.text)\(retStr)"
        }
        
    }
    
    @IBAction func closeToStation(segue: UIStoryboardSegue)
    {
        
        print("closeToStation")
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
