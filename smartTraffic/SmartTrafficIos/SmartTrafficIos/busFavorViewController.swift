//
//  busFavorViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/24.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busFavorViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,LineFavorDelegate,StationFavorDelegate,TransferFavorDelegate,UIAlertViewDelegate,BusProtocol {

    var startPoint = addrInput(type: TextType.TextInput)
    var endPoint = addrInput(type: TextType.TextInput)
    var avErrorInfo:UIAlertView!
    var iFavorType = 0
    var stationFavor = [StationFavor]()
        {
        didSet{
            tvFavor.reloadData()
        }
    }

    var lineFavor = [LineFavor]()
        {
        didSet{
            tvFavor.reloadData()
        }
    }
 
    var transferFavor = [TransferFavor]()
        {
        didSet{
            tvFavor.reloadData()
        }
    }
    
    @IBOutlet weak var tvFavor:UITableView!
    
    @IBAction func ubbReturn(sender: UIBarButtonItem) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    @IBAction func ubbDelete(sender: UIBarButtonItem) {
        tvFavor.setEditing(!tvFavor.editing, animated: true)
    }
    
    
    @IBOutlet weak var scFavor:UISegmentedControl!
    @IBAction func scFavorType(sender: UISegmentedControl) {
        
        if let user = LoginUser.user
        {
            iFavorType = sender.selectedSegmentIndex
            switch (sender.selectedSegmentIndex)
            {
            case 0:
                LineFavor.APPQueryAllLineFavor(self, userid: user.userid)
            case 1:
                TransferFavor.APPQueryAllTransferFavor(self, userid: user.userid)
            case 2:
                StationFavor.APPQueryAllStationFavor(self, userid: user.userid)
            default:
                break
            }
        }
        else
        {
            avErrorInfo = UIAlertView(title: "获取收藏错误", message: "您尚未登录，请先登录。", delegate: self, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

        
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        tvFavor.dataSource = self
        tvFavor.delegate = self
        
        scFavor.selectedSegmentIndex = iFavorType
        scFavorType(scFavor)
                // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch (iFavorType)
        {
        case 0:
            return lineFavor.count
        case 1:
            return transferFavor.count
        case 2:
            return stationFavor.count
        default:
            return 0
        }
    }
    
    func tableView(tableView: UITableView, editingStyleForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCellEditingStyle {
        return UITableViewCellEditingStyle.Delete
    }
    
    func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        tableView.beginUpdates()
        switch (iFavorType)
        {
        case 0:
            LineFavor.APPDeleteForLine(self, userid: LoginUser.user!.userid, lineIds: "\(lineFavor[indexPath.row].id)")
            lineFavor.removeAtIndex(indexPath.row)
            break
        case 1:
            TransferFavor.APPDeleteForTransfer(self, userid: LoginUser.user!.userid, transferIds: "\(transferFavor[indexPath.row].id)")
            transferFavor.removeAtIndex(indexPath.row)
            break
        case 2:
            StationFavor.APPDeleteForStation(self, userid: LoginUser.user!.userid, stationIds: "\(stationFavor[indexPath.row].id)")
            stationFavor.removeAtIndex(indexPath.row)
            break
        default:
            break
        }
        tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
        tableView.endUpdates()
    }
    
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:UITableViewCell
        switch (iFavorType)
        {
        case 0:
            cell = tableView.dequeueReusableCellWithIdentifier("lineFavorCell", forIndexPath: indexPath) 
            let selectLine = lineFavor[indexPath.row]
            cell.textLabel?.text = "\(selectLine.name)"
            cell.detailTextLabel?.text = "\(selectLine.startName) → \(selectLine.endName)"
        case 1:
            let selectTransfer = transferFavor[indexPath.row]
            cell = tableView.dequeueReusableCellWithIdentifier("transferFavorCell", forIndexPath: indexPath) 
            cell.textLabel?.text = "\(selectTransfer.startname) → \(selectTransfer.endname)"
        case 2:
            cell = tableView.dequeueReusableCellWithIdentifier("stationFavorCell", forIndexPath: indexPath) 
            let selectStation = stationFavor[indexPath.row]
            cell.detailTextLabel?.text = "\(selectStation.lines)"
            cell.textLabel?.text = "\(selectStation.stationName)"

        default:
            cell = tableView.dequeueReusableCellWithIdentifier("lineFavorCell", forIndexPath: indexPath) 
                        break
        }
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        switch (iFavorType)
        {
        case 0:
            let selectLineFavor = lineFavor[indexPath.row]
            BusLine.initBusLine(self, lineID: selectLineFavor.lineID, direct: selectLineFavor.direct)
            //获取线路信息，成功跳转。
        case 1:
            let selectTransferFavor = transferFavor[indexPath.row]
            startPoint.text = selectTransferFavor.startname
            endPoint.text = selectTransferFavor.endname
            
            startPoint.point = CLLocationCoordinate2DMake(selectTransferFavor.startlan, selectTransferFavor.startlon)
            
            endPoint.point = CLLocationCoordinate2DMake(selectTransferFavor.endlan, selectTransferFavor.endlon)
            
            self.performSegueWithIdentifier("segueFavorToTransfer", sender: nil)
            break
            //获取换乘信息，成功跳转。
        case 2:
            //获取站点信息，成功跳转。
            let selectStationFavor = stationFavor[indexPath.row]
            BusStation.initBusStation(self, stationID: selectStationFavor.stationID)
        default:
            break
        }
    }
    
    
    func updateBusCoordinate(busCoordinate: BusCoordinate) {
        
    }
    
    func updateBusInfoError(error: String) {
        avErrorInfo = UIAlertView(title: "获取信息错误", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    func updateAPPDeleteForLine(status: Int, msg: String) {
        if (status == 1)
        {
            avErrorInfo = UIAlertView(title: "删除成功", message: "删除线路收藏成功", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            tvFavor.reloadData()
        }
        else
        {
            avErrorInfo = UIAlertView(title: "删除线路收藏错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            tvFavor.reloadData()
        }
    }
    
    func updateAPPDeleteForStation(status: Int, msg: String) {
        if (status == 1)
        {
            avErrorInfo = UIAlertView(title: "删除成功", message: "删除站点收藏成功", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            tvFavor.reloadData()
        }
        else
        {
            avErrorInfo = UIAlertView(title: "删除站点收藏错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            tvFavor.reloadData()
        }
    
    }
    
    func updateAPPDeleteForTransfer(status: Int, msg: String) {
        if (status == 1)
        {
            avErrorInfo = UIAlertView(title: "删除成功", message: "删除换乘收藏成功", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            tvFavor.reloadData()
        }
        else
        {
            avErrorInfo = UIAlertView(title: "删除换乘收藏错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
            tvFavor.reloadData()
        }
    }
    
    func updateAPPQueryAllLineFavor(status: Int, msg: String, lineFavor: [LineFavor]) {
        if (status == 1)
        {
            self.lineFavor = lineFavor
        }
        else
        {
            avErrorInfo = UIAlertView(title: "获取信息错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

    }
    
    func updateAPPQueryAllStationFavor(status: Int, msg: String, stationFavor: [StationFavor]) {
        if (status == 1)
        {
            self.stationFavor = stationFavor
        }
        else
        {
            avErrorInfo = UIAlertView(title: "获取信息错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    func updateAPPQueryAllTransferFavor(status: Int, msg: String, transferFavor: [TransferFavor]) {
        if (status == 1)
        {
            self.transferFavor = transferFavor
        }
        else
        {
            avErrorInfo = UIAlertView(title: "获取信息错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

    }
    
    func updateAPPSaveLineFavor(status: Int, msg: String) {
        
    }
    
    func updateAPPSaveStationFavor(status: Int, msg: String) {
        
    }
    
    func updateAPPSaveTransferFavor(status: Int, msg: String) {
        
    }
    
    func updateLineError(error: String) {
        avErrorInfo = UIAlertView(title: "获取信息错误", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    func updateTransferFavorError(error: String) {
        avErrorInfo = UIAlertView(title: "获取信息错误", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    func updateStationError(error: String) {
        avErrorInfo = UIAlertView(title: "获取信息错误", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    func updateBusLineList(busLineList: [BusLine]) {
        if (busLineList.count <= 0)
        {
            avErrorInfo = UIAlertView(title: "获取收藏错误", message: "没有找到该收藏", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
        else
        {
            let busLineTemp = busLineList[0]
            var showBusLine:ShowBusLine!
            if busLineTemp.IsRing
            {
                showBusLine = ShowBusLine(Id: busLineTemp.Id, Name: busLineTemp.Name, StartName: busLineTemp.UpStartStationName, EndName: busLineTemp.UpEndStationName, Direct: BusLine.upLine,IsRing: busLineTemp.IsRing)
            }
            else
            {
                if busLineTemp.UpList.count > 0
                {
                    showBusLine = ShowBusLine(Id: busLineTemp.Id, Name: busLineTemp.Name, StartName: busLineTemp.UpStartStationName, EndName: busLineTemp.UpEndStationName, Direct: BusLine.upLine,IsRing: busLineTemp.IsRing)
                }
                else
                {
                    showBusLine = ShowBusLine(Id: busLineTemp.Id, Name: busLineTemp.Name, StartName: busLineTemp.DownStartStationName, EndName: busLineTemp.DownEndStationName , Direct: BusLine.downLine,IsRing: busLineTemp.IsRing)
                }
            }

            self.performSegueWithIdentifier("segueFavorToLine", sender: showBusLine)
        }
    }
    
    func updateBusLocation(busLocation: BusLocation) {
        
    }
    
    func updateBusStationList(busStationList: [BusStation]) {
        if (busStationList.count <= 0)
        {
            avErrorInfo = UIAlertView(title: "获取收藏错误", message: "没有找到该收藏", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
        else
        {
            self.performSegueWithIdentifier("segueFavorToStation", sender: busStationList[0])
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueFavorToLine":
                let vc = segue.destinationViewController as! busLineDetailViewController
                vc.showBusLine = (sender as! ShowBusLine)
                break
            case "segueFavorToStation":
                let vc = segue.destinationViewController as! busStationDetailViewController
                vc.busStation = (sender as! BusStation)
                break
            case "segueFavorToTransfer":
                let vc = segue.destinationViewController as! busTransferResultViewController
                vc.startPoint = startPoint
                vc.endPoint = endPoint
            default:
                break
                
            }
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
