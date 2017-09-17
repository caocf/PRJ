//
//  busTransferResultDetailViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/12.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busTransferResultDetailViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {

    var transfer:Transfer!
    var startPoint = addrInput(type: TextType.TextInput)
    var endPoint = addrInput(type: TextType.TextInput)
    let textColor = UIColor(red: 0x27/0xff, green: 0x46/0xff, blue: 0xa2/0xff, alpha: 1.0)


    @IBOutlet weak var labStart:UILabel!
    @IBOutlet weak var labEnd:UILabel!
    @IBOutlet weak var labInfo:UILabel!
    @IBOutlet weak var tvTransfer:UITableView!
    
    @IBAction func ubbChangeMap(sender: UIBarButtonItem) {
        let geoStartPoint = CNMKGeoPointMake(startPoint.point!.longitude, startPoint.point!.latitude)
        let geoEndPoint = CNMKGeoPointMake(endPoint.point!.longitude, endPoint.point!.latitude)
        let drewline = DrewLine(transfer: transfer, startPoint: geoStartPoint, endPoint: geoEndPoint)
        self.performSegueWithIdentifier("segueShowTransferMap", sender: drewline)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        labStart.text = "\(startPoint.text!)"
        labEnd.text = "\(endPoint.text!)"
        labInfo.text = "全程约\(transfer.Time)分钟/\(transfer.Distance)米/\(transfer.Price)元"
        tvTransfer.dataSource = self
        tvTransfer.delegate = self
        tvTransfer.reloadData()
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
        return transfer.NewList.count + 2
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("transferResultDetailCell", forIndexPath: indexPath) as! busTransferResultTableViewCell
        
        switch (indexPath.row)
        {
        case 0:
            cell.uiImage.image = UIImage(named: "icon_start")
            cell.labTransfer.text = "从起点\(startPoint.text!)出发"
        case transfer.NewList.count + 1:
            cell.uiImage.image = UIImage(named: "icon_finish")
            cell.labTransfer.text = "到达目的地\(endPoint.text!)"
        default:
            let transferList = transfer.NewList[indexPath.row - 1]
            switch (transferList.Type)
            {
            case 0:
                cell.uiImage.image = UIImage(named: "icon_walk")
                let sListStr =  NSMutableAttributedString(string: "\(transferList.Direction)步行\(transferList.Distance)米")
                if transferList.StationName != ""
                {
                    let sStationStr = NSMutableAttributedString(string: "到达\(transferList.StationName)站")
                    sStationStr.addAttribute(NSForegroundColorAttributeName, value: textColor, range: NSMakeRange(0,sStationStr.length))
                    sListStr.insertAttributedString(sStationStr, atIndex: sListStr.length)
                }
                cell.labTransfer.attributedText = sListStr
            case 1:
                cell.uiImage.image = UIImage(named: "icon_bus")
                let sListStr = NSMutableAttributedString(string: "乘坐经过\(transferList.LineDetails.StationCount)站")
                let sLineStr = NSMutableAttributedString(string: "\(transferList.LineDetails.Name)")
                sLineStr.addAttribute(NSForegroundColorAttributeName, value: textColor, range: NSMakeRange(0,sLineStr.length))
                let sStationStr = NSMutableAttributedString(string: "到达\(transferList.StationName)站")
                sStationStr.addAttribute(NSForegroundColorAttributeName, value: textColor, range: NSMakeRange(0,sStationStr.length))
                sListStr.insertAttributedString(sStationStr, atIndex: sListStr.length)
                sListStr.insertAttributedString(sLineStr, atIndex: 2)
                cell.labTransfer.attributedText = sListStr
                break
            case 2:
                cell.uiImage.image = UIImage(named: "icon_bicycle")
                let sListStr =  NSMutableAttributedString(string: "\(transferList.Direction)骑行\(transferList.Distance)米")
                if transferList.StationName != ""
                {
                    let sStationStr = NSMutableAttributedString(string: "到达\(transferList.StationName)站")
                    sStationStr.addAttribute(NSForegroundColorAttributeName, value: textColor, range: NSMakeRange(0,sStationStr.length))
                    sListStr.insertAttributedString(sStationStr, atIndex: sListStr.length)
                }
                cell.labTransfer.attributedText = sListStr

                break
            default:
                break
            }

        }
        
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
//        self.performSegueWithIdentifier("segueShowBusTransferResultDetail", sender: transfer[indexPath.row])
        var drewline:DrewLine!
       
        if (indexPath.row > 0 && indexPath.row <= transfer.NewList.count)
        {
            let transferList = transfer.NewList[indexPath.row - 1]
            switch (transferList.Type)
            {
            case 0:
                let geoStartPoint:CNMKGeoPoint
                let geoEndPoint:CNMKGeoPoint

                if(indexPath.row <= 1)
                {
                    geoStartPoint = CNMKGeoPointMake(startPoint.point!.longitude, startPoint.point!.latitude)
                }
                else
                {
                    let transferListLast = transfer.NewList[indexPath.row - 2]
                    let startPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(transferListLast.Latitude, transferListLast.Longitude))
                    geoStartPoint = CNMKGeoPointMake(startPoint02.longitude, startPoint02.latitude)

                }
                
                if(indexPath.row >= transfer.NewList.count)
                {
                    geoEndPoint = CNMKGeoPointMake(endPoint.point!.longitude, endPoint.point!.latitude)
                }
                else
                {
                    let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(transferList.Latitude, transferList.Longitude))
                    geoEndPoint = CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude)
                }
                drewline = DrewLine(startPoint: geoStartPoint, endPoint: geoEndPoint)
                self.performSegueWithIdentifier("segueShowTransferMap", sender: drewline)
            case 1:
                var lineGeoPointList = [CNMKGeoPoint]()
                for lineTrails in transferList.LineTrailsList
                {
                    let lineGeoPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(lineTrails.Latitude, lineTrails.Longitude))
                    let lineGeoPoint = CNMKGeoPointMake(lineGeoPoint02.longitude, lineGeoPoint02.latitude)
                    lineGeoPointList.append(lineGeoPoint)
                }
                drewline = DrewLine(lines: lineGeoPointList)
                self.performSegueWithIdentifier("segueShowTransferMap", sender: drewline)
            case 2:
                let geoStartPoint:CNMKGeoPoint
                let geoEndPoint:CNMKGeoPoint
                
                
                let transferListLast = transfer.NewList[indexPath.row - 2]
                let startPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(transferListLast.Latitude, transferListLast.Longitude))
                geoStartPoint = CNMKGeoPointMake(startPoint02.longitude, startPoint02.latitude)
                
                
                let endPoint02 = JZLocationConverter.wgs84ToGcj02(CLLocationCoordinate2DMake(transferList.Latitude, transferList.Longitude))
                geoEndPoint = CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude)
                drewline = DrewLine(startPoint: geoStartPoint, endPoint: geoEndPoint)
                self.performSegueWithIdentifier("segueShowTransferMap", sender: drewline)
                
            default:
                break
            }
        }
        
    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return "换乘方案详情"
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowTransferMap":
                let vc = segue.destinationViewController as! busTransferResultMapViewController
//                vc.transfer = (sender as! Transfer)
                vc.drewLine = (sender as! DrewLine)
//                var startPoint02 = JZLocationConverter.wgs84ToGcj02(startPoint.point!)
//                vc.geoStartPoint = CNMKGeoPointMake(startPoint02.longitude, startPoint02.latitude)
//                var endPoint02 = JZLocationConverter.wgs84ToGcj02(endPoint.point!)
//                vc.geoEndPoint = CNMKGeoPointMake(endPoint02.longitude, endPoint02.latitude)
                break
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
    
    @IBAction func closeToTransferResultDetail(segue: UIStoryboardSegue)
    {
        if segue.sourceViewController is busTransferResultMapViewController
        {
            let vc = segue.sourceViewController as! busTransferResultMapViewController
            vc.mapView.delegate = nil
            vc.mapView.gNoteCBDelegate = nil
            vc.gSearch.delegate = nil
            vc.mapView.removeRoutingResult()
            vc.mapView.removeMapObjects()
        }
        print("closeToTransferResultDetail")
    }
    
//    segueTransferResultMap
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
