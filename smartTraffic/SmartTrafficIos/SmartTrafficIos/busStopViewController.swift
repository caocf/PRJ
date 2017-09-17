//
//  busStopViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/5.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busStopViewController: UIViewController,BusProtocol,UITableViewDataSource,UITableViewDelegate {

    @IBOutlet weak var tvBusStop:UITableView!
    @IBOutlet weak var labStopInfo:UILabel!
    @IBOutlet weak var labStationInfo:UILabel!
    
    @IBAction func ubbRefresh(sender: UIBarButtonItem) {
        initBusLocation()
    }
    
    var tRefresh:NSTimer?
    var showBusLine:ShowBusLine!
//    var busStation:BusLine.LineStation!
    var busStation:BusStation!
    var busLocation = BusLocation(lineID: -1, stationID: -1, direct: 0, busInfo: [], nextTime: "")
    
    var avErrorInfo:UIAlertView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        tvBusStop.dataSource = self
        tvBusStop.delegate = self
        
        initBusLocation()
        tRefresh = NSTimer.scheduledTimerWithTimeInterval(20, target: self, selector: "initBusLocation", userInfo: nil, repeats: true)
        // Do any additional setup after loading the view.
    }
    
    func initBusLocation()
    {
        print("busStopRefresh")
        labStopInfo.text = "\(showBusLine.Name) (\(showBusLine.StartName) → \(showBusLine.EndName))"
        labStationInfo.text = "\(busStation.Name)"
        BusLocation.initBusLocation(self, lineID: showBusLine.Id, direct: showBusLine.Direct, count: BusLocation.defaultCount, stationID: busStation.Id)
    }

    override func viewWillDisappear(animated: Bool) {
        tRefresh?.invalidate()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func updateBusCoordinate(busCoordinate: BusCoordinate) {
        
    }
    
    func updateBusInfoError(error: String) {
        print(error)
    }
    
    func updateBusLineList(busLineList: [BusLine]) {
        
    }
    
    func updateBusLocation(busLocation: BusLocation) {
        self.busLocation = busLocation
        tvBusStop.reloadData()
  
    }
    

    
    func updateBusStationList(busStationList: [BusStation]) {
        
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }

    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {

        return busLocation.busInfo.count + 2
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("busCell", forIndexPath: indexPath) as! busStopTableViewCell
        switch (indexPath.row)
        {
        case 0:
            cell.labNo.text = "编号"
//            cell.labCarNumber.text = "车牌号码"
            cell.labStopLength.text = "离站距离"
            cell.labArrive.text = "最近到达"
            cell.labDriverStatus.text = "行驶状态"
        case (busLocation.busInfo.count + 1):
            cell.labNo.text = "\(indexPath.row)"
//            cell.labCarNumber.text = "-"
            if busLocation.nextTime == "已停运"
            {
                cell.labStopLength.text = "\(busLocation.nextTime)"
            }
            else
            {
                cell.labStopLength.text = "\(busLocation.nextTime)发车"
            }
            cell.labArrive.text = "-"
            cell.labDriverStatus.text = "-"
        default:
            if busLocation.busInfo.count > 0
            {
                let busInfo = busLocation.busInfo[indexPath.row - 1]
                cell.labNo.text = "\(indexPath.row)"
//                cell.labCarNumber.text = "\(busInfo.CarNumber)"
                if busInfo.Distance == 0
                {
                    cell.labStopLength.text = "即将到站"
                }
                else
                {
                    cell.labStopLength.text = "\(busInfo.Distance)米/\(busInfo.StationIndex)站"
                }
                cell.labArrive.text = "\(busInfo.StationName)"
                let driverStatus = BusLocation.BusInfo.getDriverStatus(busInfo.DriveState)
                cell.labDriverStatus.text = "\(driverStatus.0)"
                cell.labDriverStatus.textColor = driverStatus.1
            }
            break
        }

        
        return cell
    }
    
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        if indexPath.row == 0
        {
            return 50
        }
        else
        {
            return 60
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
