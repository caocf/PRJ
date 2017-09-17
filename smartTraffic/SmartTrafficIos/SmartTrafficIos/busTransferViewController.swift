//
//  busTransferViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/3.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busTransferViewController: UIViewController,CLLocationManagerDelegate,IFlyRecognizerViewDelegate,mapChooseDelegate,UITableViewDataSource,UITableViewDelegate {
    
    var avErrorInfo:UIAlertView!
    var poView:PopupView!
    var ifView:IFlyRecognizerView!

    var currLocation : CLLocation!
    
    let locationManager : CLLocationManager = CLLocationManager()
    let MY_POSITION = "我的位置"
    let MAP_CHOOSE = "地图上的点"
    let STATION = "站点"
    
    var bHistory:Bool = true
    var busTransferTitle = "历史记录"
    var history:[String] = []
        {
        didSet{
            tvTransfer.reloadData()
        }
    }


    
    var startPoint = addrInput(type: TextType.TextInput)
    var endPoint = addrInput(type: TextType.TextInput)

    var openMore = OpenMore.None

    @IBOutlet weak var txtStart:UITextField!
    @IBOutlet weak var txtEnd:UITextField!
    @IBOutlet weak var tvTransfer:UITableView!
    @IBOutlet weak var btnStartMore:UIButton!
    @IBOutlet weak var btnEndMore:UIButton!
    @IBOutlet weak var vMore:UIView!
    
    
    @IBAction func txtStartBeginEdit(sender: UITextField) {
        startPoint.text = sender.text
        //println("txtStartBeginEdit \(sender.text)")
    }

    
    @IBAction func txtStartEndEdit(sender: UITextField) {
        //println("txtStartEndEdit \(sender.text)")
        sender.resignFirstResponder()
        if (sender.text != startPoint.text)
        {
            startPoint.type = TextType.TextInput
            startPoint.text = sender.text
        }
    }
    
    @IBAction func txtEndBeginEdit(sender: UITextField) {
        endPoint.text = sender.text
    }
    
    @IBAction func txtEndEndEdit(sender: UITextField) {
        sender.resignFirstResponder()
        if (sender.text != endPoint.text)
        {
            endPoint.type = TextType.TextInput
            endPoint.text = sender.text
        }
    }
    
    @IBAction func btnSearch(sender: UIButton) {
        let searchText = "\(txtStart.text!) → \(txtEnd.text!)"
        if (!Tools.checkIsExist(history, item: searchText))
        {
            history.insert(searchText, atIndex: 0)
            History.writeHistory("\(History.HistoryType.BusTransfer.rawValue)", history: history)
        }
        startSearch()
    }
    
    func startSearch()
    {
        if (txtStart.text!.isEmpty || txtEnd.text!.isEmpty)
        {
            avErrorInfo = UIAlertView(title: "错误", message: "请输入或选择正确的起点和终点！", delegate: nil, cancelButtonTitle: "确定")
            avErrorInfo.show()
        }
        else
        {
            txtStartEndEdit(txtStart)
            txtEndEndEdit(txtEnd)
            if checkPoi()
            {
                self.performSegueWithIdentifier("segueTransferPoi", sender: nil)
            }
            else
            {
                self.performSegueWithIdentifier("segueGotoTransferResult", sender: nil)
            }
        }

    }
    
    func checkPoi() -> Bool
    {
        var bIsNeedCheckPoi = false
        switch(startPoint.type)
        {
        case .TextInput,.VoiceInput:
            bIsNeedCheckPoi = true
        default:
            break
        }
        switch(endPoint.type)
        {
        case .TextInput,.VoiceInput:
            bIsNeedCheckPoi = true
        default:
            break
        }
        return bIsNeedCheckPoi
    }

    
    func updateChooseInfo(selectPoint: CNMKGeoPoint) {
        switch (openMore)
        {
        case .Start:
            startPoint.type = TextType.MapChoose
            startPoint.point = JZLocationConverter.gcj02ToWgs84(CLLocationCoordinate2DMake(selectPoint.latitude, selectPoint.longitude))
            startPoint.text = self.MAP_CHOOSE
            txtStart.text = self.MAP_CHOOSE
        case .End:
            endPoint.type = TextType.MapChoose
            endPoint.point = JZLocationConverter.gcj02ToWgs84(CLLocationCoordinate2DMake(selectPoint.latitude, selectPoint.longitude))
            endPoint.text = self.MAP_CHOOSE
            txtEnd.text = self.MAP_CHOOSE
        case .None:
            break
        }
        
        vMore.hidden = true
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueTransferPoi":
                let vc = segue.destinationViewController as! busPoiViewController
                vc.startPoint = startPoint
                vc.endPoint = endPoint
                break
            case "segueBusTransferMapChoose":
                let vc = segue.destinationViewController as! mapChooseViewController
                vc.vcMapChoose = (sender as! busTransferViewController)
            case "segueGotoTransferResult":
                let vc = segue.destinationViewController as! busTransferResultViewController
                vc.startPoint = startPoint
                vc.endPoint = endPoint
            case "segueTransferToFavor":
                let vc = segue.destinationViewController as! busFavorViewController
                vc.iFavorType = 1
            default:
                break
                
            }
        }
        
    }

    @IBAction func btnChangeStartEnd(sender: UIButton) {
        txtStartEndEdit(txtStart)
        txtEndEndEdit(txtEnd)
        let addrTemp = startPoint
        startPoint = endPoint
        endPoint = addrTemp
        txtStart.text = startPoint.text
        txtEnd.text = endPoint.text
    }
    
    func updateButton()
    {
        if vMore.hidden
        {
            vMore.hidden = false
            switch (openMore)
            {
            case .Start:
                btnStartMore.setImage(UIImage(named: "ic_arrow_up"), forState: UIControlState.Normal)
            case .End:
                btnEndMore.setImage(UIImage(named: "ic_arrow_up"), forState: UIControlState.Normal)
            case .None:
                break
            }
        }
        else
        {
            vMore.hidden = true
            openMore = OpenMore.None
            btnStartMore.setImage(UIImage(named: "ic_arrow_down-拷贝"), forState: UIControlState.Normal)
            btnEndMore.setImage(UIImage(named: "ic_arrow_down-拷贝"), forState: UIControlState.Normal)
        }

    }
    
    @IBAction func btnStartMore(sender: UIButton) {
        btnStartMore.resignFirstResponder()
        btnEndMore.resignFirstResponder()
        openMore = OpenMore.Start
        updateButton()
    }
    
    @IBAction func btnEndMore(sender: UIButton) {
        btnStartMore.resignFirstResponder()
        btnEndMore.resignFirstResponder()
        openMore = OpenMore.End
        updateButton()
    }
    
    @IBAction func btnFavorite(sender: UIButton) {
        vMore.hidden = true
        if let _ = LoginUser.user
        {
            self.performSegueWithIdentifier("segueTransferToFavor", sender: nil)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "打开收藏错误", message: "您尚未登录，请登陆后再打开收藏夹", delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }

    }
    
    
    @IBAction func btnMapChoose(sender: UIButton) {
        self.performSegueWithIdentifier("segueBusTransferMapChoose", sender: self)
        //vMore.hidden = true
    }
    
    @IBAction func btnMyPosition(sender: UIButton) {
        switch (openMore)
        {
        case .Start:
            startPoint.type = TextType.MyPosition
            startPoint.point = currLocation.coordinate
            startPoint.text = self.MY_POSITION
            txtStart.text = self.MY_POSITION
        case .End:
            endPoint.type = TextType.MyPosition
            endPoint.point = currLocation.coordinate
            endPoint.text = self.MY_POSITION
            txtEnd.text = self.MY_POSITION
        case .None:
            break
        }
        
        vMore.hidden = true
    }
    
    @IBAction func btnVoice(sender: UIButton) {
        switch (openMore)
        {
        case .Start:
            startPoint.type = TextType.VoiceInput
        case .End:
            endPoint.type = TextType.VoiceInput
        case .None:
            break
        }
        ifView.setParameter("iat", forKey:IFlySpeechConstant.IFLY_DOMAIN())
        ifView.setParameter("plain", forKey: IFlySpeechConstant.RESULT_TYPE())
        ifView.start()
        vMore.hidden = true
    }
    
    @IBAction func btnZBar(sender: UIButton) {
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        tvTransfer.dataSource = self
        tvTransfer.delegate = self
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

        poView = PopupView(frame: CGRectMake(100, 300, 0, 0))
        poView.ParentView = self.view
        
        self.ifView = IFlyRecognizerView(center: self.view.center)
        ifView.delegate = self

        bHistory = true
        if let his = History.readHistory("\(History.HistoryType.BusTransfer.rawValue)")
        {
            history = his
        }

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currLocation = locations.last! as CLLocation
//        bSearchPoi = false
//        var ac = PublicBike.initNullPublicBike().getActionUrl(currLocation.coordinate.longitude, lantitude: currLocation.coordinate.latitude, distance: PublicBike.defaultDistance, count: PublicBike.defaultCount)
//        PublicBike.initPublicBikeList(self, actionUrl: ac.0, httpBody: ac.1)
//        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError){
        print(error)
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
        vMore.hidden = true
        
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
            switch(openMore)
            {
            case .Start:
                txtStart.text = "\(txtStart.text)\(retStr)"
            case .End:
                txtEnd.text =  "\(txtEnd.text)\(retStr)"
            case .None:
                break
            }
//            usbStationSearch.text = "\(usbStationSearch.text)\(retStr)"
        }
        
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if history.count > 0
        {
            return history.count + 1
        }
        else
        {
            return 0
        }
    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return busTransferTitle
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
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
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        if (indexPath.row < history.count)
        {
            let selectHistory = history[indexPath.row]
            var subStr = selectHistory.characters.split(selectHistory.lengthOfBytesUsingEncoding(NSUTF8StringEncoding), allowEmptySlices: false, isSeparator: { (char:Character) -> Bool in
                return char == "→"
            }).map { String($0) }
            if (subStr.count == 2)
            {
                txtStart.text = subStr[0].stringByTrimmingCharactersInSet(NSCharacterSet(charactersInString: " "))
                txtEnd.text = subStr[1].stringByTrimmingCharactersInSet(NSCharacterSet(charactersInString: " "))
//                startPoint.type = TextType.TextInput
//                startPoint.text = txtStart.text
//                endPoint.type = TextType.TextInput
//                endPoint.text = txtEnd.text
//                self.btnSearch(<#sender: UIButton#>)
                startSearch()
            }
           

        }
        else
        {
            history.removeAll()
            History.writeHistory("\(History.HistoryType.BusLine.rawValue)", history: history)
        }


    }
    
    @IBAction func closeToTransfer(segue: UIStoryboardSegue)
    {
        
        print("closeToTransfer")
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
