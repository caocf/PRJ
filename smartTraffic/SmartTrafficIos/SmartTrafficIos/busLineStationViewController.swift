//
//  busLineStationViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/6/3.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busLineStationViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,BusProtocol {
    
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    @IBOutlet weak var labStationName:UILabel!
    @IBOutlet weak var tvStationInfo:UITableView!
    var avErrorInfo:UIAlertView!
    var selectBusStation:BusLine.LineStation!
    var busStation:BusStation!
    //        {
    //        didSet{
    //            initBusStation()
    //        }
    //    }
    var tRefresh:NSTimer?
    
    @IBAction func btnRefresh(sender: UIButton) {
        initBusStation()
        
    }
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        tvStationInfo.dataSource = self
        tvStationInfo.delegate = self
        tRefresh = NSTimer.scheduledTimerWithTimeInterval(60, target: self, selector: "initBusStation", userInfo: nil, repeats: true)
        initBusStation()
        
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func initBusStation()
    {
        BusStation.initBusStation(self, stationID: selectBusStation.StationId)
        aivLoading.startAnimating()
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
        
    }
    
    func updateBusLocation(busLocation: BusLocation) {
        
    }
    
    func updateBusStationList(busStationList: [BusStation]) {
        aivLoading.stopAnimating()
        if busStationList.count > 0
        {
            busStation = busStationList[0]
            labStationName.text = "\(busStation.Name)"
            tvStationInfo.reloadData()
        }
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if (busStation != nil)
        {
            return busStation.lines.count
        }
        else
        {
            return 0
        }
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        let busLineSelect = busStation.lines[indexPath.row]
        self.performSegueWithIdentifier("segueShowBusStop", sender: busLineSelect)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowBusStop":
                let vc = segue.destinationViewController as! busStopViewController
                let busLineSelect = (sender as! BusStation.StationLine)
                vc.showBusLine = ShowBusLine(Id: busLineSelect.Id, Name: busLineSelect.Name, StartName: busLineSelect.StartStationName, EndName: busLineSelect.EndStationName, Direct: busLineSelect.Direct, IsRing: busLineSelect.IsRing)
                vc.busStation = self.busStation
                break
            default:
                break
                
            }
        }
        
        
    }
    
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("stationCell", forIndexPath: indexPath) as! busStationTableViewCell
        let line = busStation.lines[indexPath.row]
        cell.labStationName.text = "\(line.Name)"
        cell.labStartToEnd.text = "\(line.StartStationName) → \(line.EndStationName)"
        if line.busInfo.count > 0
        {
            if line.busInfo[0].Distance == 0
            {
                cell.labBusInfo.text = "下趟公交车即将到站"
            }
            else
            {
                cell.labBusInfo.text = "下趟公交车还有\(line.busInfo[0].Distance)米到站"
            }
            let driverStatus = BusLocation.BusInfo.getDriverStatus(line.busInfo[0].DriveState)
            cell.labDriverStatus.text = "\(driverStatus.0)"
            cell.labDriverStatus.textColor = driverStatus.1
            
        }
        else
        {
            if line.Time == "已停运"
            {
                cell.labBusInfo.text = "\(line.Time)"
            }
            else
            {
                cell.labBusInfo.text = "\(line.Time)发车"
            }
            cell.labDriverStatus.text = ""
        }
        
        return cell
    }
    
    override func viewWillDisappear(animated: Bool) {
        tRefresh?.invalidate()
    }
    
    
    @IBAction func closeToLineStation(segue: UIStoryboardSegue)
    {
        
        print("closeToLineStation")
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
