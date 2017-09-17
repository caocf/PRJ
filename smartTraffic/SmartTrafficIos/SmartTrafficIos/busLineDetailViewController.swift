//
//  busLineDetailViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/4.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busLineDetailViewController: UIViewController,BusProtocol,UICollectionViewDataSource,UICollectionViewDelegate,UICollectionViewDelegateFlowLayout,LineFavorDelegate {

    @IBOutlet weak var unbTitle:UINavigationBar!
    @IBOutlet weak var labStartStation:UILabel!
    @IBOutlet weak var labEndStation:UILabel!
    @IBOutlet weak var labTimeInfo:UILabel!
    @IBOutlet weak var labPrice:UILabel!
    @IBOutlet weak var labLength:UILabel!
    @IBOutlet weak var labTimeInterval:UILabel!
    @IBOutlet weak var ucvStations:UICollectionView!
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    
    var avErrorInfo:UIAlertView!
    
    var showBusLine:ShowBusLine!
//        {
//        didSet{
//            initBusLineInfo()
//        }
//    }
    
    var tRefresh:NSTimer?
    var busLine:BusLine!
    var busLocation:BusLocation?
    
    var busInStation = [Int:Bool]()
    

    @IBAction func ubbChangeMap(sender: UIBarButtonItem) {
        self.performSegueWithIdentifier("segueLineToMap", sender: busLine)
    }
    
    @IBAction func btnFavor(sender: UIButton) {
        if let user = LoginUser.user
        {
            LineFavor.APPSaveLineFavor(self, userid: user.userid, lineID: showBusLine.Id, direct: showBusLine.Direct, name: showBusLine.Name, startName: showBusLine.StartName, endName: showBusLine.EndName)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "收藏线路错误", message: "您尚未登录，请登陆后再收藏线路", delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }
    }
    
    @IBAction func btnChangeDirect(sender: UIButton) {
        if showBusLine.IsRing
        {
            let alertInfo = UIAlertView(title: "切换失败", message:"本线路是环线，无法切换。", delegate: nil, cancelButtonTitle: "取消")
            alertInfo.show()
        }
        else
        {
            switch (showBusLine.Direct)
            {
            case 1:
                showBusLine.Direct = 2
            case 2:
                showBusLine.Direct = 1
            default:
                break
            }
            initBusLineInfo()
        }
    }
    
    @IBAction func btnRefresh(sender: UIButton) {
        initBusLineInfo()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        ucvStations.dataSource = self
        ucvStations.delegate = self

        tRefresh = NSTimer.scheduledTimerWithTimeInterval(60, target: self, selector: "initBusLineInfo", userInfo: nil, repeats: true)
        initBusLineInfo()
        
        // Do any additional setup after loading the view.
    }
    
    func initBusLineInfo()
    {
        print("busLineRefresh")
        BusLine.initBusLine(self, lineID: showBusLine.Id, direct: showBusLine.Direct)
        aivLoading.startAnimating()

    }
    
    override func viewWillDisappear(animated: Bool) {
        tRefresh?.invalidate()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        var iCount = 0
        if busLine != nil
        {
            switch (showBusLine.Direct)
            {
            case 1:
                iCount = busLine.UpList.count
            case 2:
                iCount = busLine.DownList.count
            default:
                break
            }
        }
        return iCount
    }
    
    func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
        return 1
    }
    
  
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        return CGSizeMake(60, collectionView.frame.height)
    }
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAtIndex section: Int) -> UIEdgeInsets {
        return UIEdgeInsetsMake(0, 0, 0, 0)
    }
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAtIndex section: Int) -> CGFloat {
        return 0
    }
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAtIndex section: Int) -> CGFloat {
        return 0
    }
    
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        var busStationSelect:BusLine.LineStation!
        switch (showBusLine.Direct)
        {
        case 1:
            busStationSelect = busLine.UpList[indexPath.row]
        case 2:
            busStationSelect = busLine.DownList[indexPath.row]
        default:
            break
        }

//        self.performSegueWithIdentifier("segueShowBusStop", sender: busStationSelect)
        self.performSegueWithIdentifier("segueLineDetailToLineStation", sender: busStationSelect)
//        segueLineDetailToLineStation
        print(indexPath.row)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
//            case "segueShowBusStop":
//                var vc = segue.destinationViewController as! busStopViewController
//                vc.showBusLine = self.showBusLine
//                vc.busStation = (sender as! BusLine.LineStation)
//                break
            case "segueLineDetailToLineStation":
                let vc = segue.destinationViewController as! busLineStationViewController
                vc.selectBusStation = (sender as! BusLine.LineStation)
            case "segueLineToMap":
                let vc = segue.destinationViewController as! busLineMapViewController
                vc.showBusLine = self.showBusLine
                vc.busLine = (sender as! BusLine)
            default:
                break
                
            }
        }

        
    }
    
    func collectionView(collectionView: UICollectionView, shouldSelectItemAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier("stationCollection", forIndexPath: indexPath) as! busLineStationCollectionViewCell
        cell.center.y = collectionView.frame.height / 2
        //cell.backgroundColor = UIColor.whiteColor()
        let index = indexPath.row
        if let busIn = busInStation[indexPath.row]
        {
            cell.ivBusLocation.hidden = busIn
        }
        else
        {
            cell.ivBusLocation.hidden = true
        }
        cell.labStationNo.text = "\(index + 1)"
        switch (showBusLine.Direct)
        {
        case 1:
            cell.labStationName.text = "\(busLine.UpList[index].StationName)"
        case 2:
            cell.labStationName.text = "\(busLine.DownList[index].StationName)"

        default:
            break
        }
        print("\(indexPath.row):\(cell.labStationName.frame.height)")

        return cell

    }
    
    

    func updateBusCoordinate(busCoordinate: BusCoordinate) {
        
    }
    
    func updateBusInfoError(error: String) {
        aivLoading.stopAnimating()
        avErrorInfo = UIAlertView(title: "获取公交信息失败", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    func updateBusLineList(busLineList: [BusLine]) {
        aivLoading.stopAnimating()
        if (busLineList.count > 0)
        {
            busLine = busLineList[0]
            switch (showBusLine.Direct)
            {
            case 1:
                BusLocation.initBusLocation(self, lineID: showBusLine.Id, direct: showBusLine.Direct, count: BusLocation.defaultCount, stationID: busLine.UpEndStationId)
                labStartStation.text = busLine.UpStartStationName
                labEndStation.text = busLine.UpEndStationName
                labTimeInfo.text = "首末班：\(busLine.LineUpStartTime)-\(busLine.LineUpEndTime)"
                labPrice.text = "票价：\(busLine.Price)元"
                labLength.text = "线路长度：\(busLine.LineupLength)米"
                labTimeInterval.text = "发班间隔：-"
            case 2:
                BusLocation.initBusLocation(self, lineID: showBusLine.Id, direct: showBusLine.Direct, count: BusLocation.defaultCount, stationID: busLine.DownEndStationId)
                labStartStation.text = busLine.DownStartStationName
                labEndStation.text = busLine.DownEndStationName
                labTimeInfo.text = "首末班：\(busLine.LineDownStartTime)-\(busLine.LineDownEndTime)"
                labPrice.text = "票价：\(busLine.Price)元"
                labLength.text = "线路长度：\(busLine.LinedownLength)米"
                labTimeInterval.text = "发班间隔：-"
            default:
                break
            }
            ucvStations.reloadData()
            
        }
    }
    
    func updateBusLocation(busLocation: BusLocation) {
        aivLoading.stopAnimating()
        busInStation.removeAll()
        var busCount = 0
        switch (showBusLine.Direct)
        {
        case 1:
            busCount = busLine.UpList.count
        case 2:
            busCount = busLine.DownList.count
        default:
            busCount = 0
        }
        if busCount > 0
        {
            for busLocationInfo in busLocation.busInfo
            {
                busInStation[busCount - busLocationInfo.StationIndex] = false
            }
        }
        ucvStations.reloadData()

        
    }
    
    func updateBusStationList(busStationList: [BusStation]) {
        
    }
    
    
    func updateAPPSaveLineFavor(status: Int, msg: String) {
        if (status == 1)
        {
            avErrorInfo = UIAlertView(title: "收藏线路成功", message: "收藏线路成功", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
        else
        {
            avErrorInfo = UIAlertView(title: "收藏线路错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

    }
    
    func updateAPPQueryAllLineFavor(status: Int, msg: String, lineFavor: [LineFavor]) {
        
    }
    
    func updateAPPDeleteForLine(status: Int, msg: String) {
        
    }
    
    func updateLineError(error: String) {
        avErrorInfo = UIAlertView(title: "收藏线路错误", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    @IBAction func closeToLineDetail(segue: UIStoryboardSegue)
    {
        print("closeToLineDetail")
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
