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
    
    @IBAction func ubbChangeMap(sender: UIBarButtonItem) {
        self.performSegueWithIdentifier("segueTransferResultMap", sender: transfer)
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
            var transferList = transfer.NewList[indexPath.row - 1]
            switch (transferList.Type)
            {
            case 0:
                cell.uiImage.image = UIImage(named: "icon_walk")
                var sListStr =  NSMutableAttributedString(string: "\(transferList.Direction)步行\(transferList.Distance)米到达")
                if transferList.StationName != ""
                {
                    var sStationStr = NSMutableAttributedString(string: "\(transferList.StationName)站")
                    sStationStr.addAttribute(NSForegroundColorAttributeName, value: textColor, range: NSMakeRange(0,sStationStr.length))
                    sListStr.insertAttributedString(sStationStr, atIndex: sListStr.length)
                }
                cell.labTransfer.attributedText = sListStr
            case 1:
                cell.uiImage.image = UIImage(named: "icon_bus")
                var sListStr = NSMutableAttributedString(string: "乘坐经过\(transferList.LineDetails.StationCount)站到达")
                var sLineStr = NSMutableAttributedString(string: "\(transferList.LineDetails.Name)")
                sLineStr.addAttribute(NSForegroundColorAttributeName, value: textColor, range: NSMakeRange(0,sLineStr.length))
                var sStationStr = NSMutableAttributedString(string: "\(transferList.StationName)站")
                sStationStr.addAttribute(NSForegroundColorAttributeName, value: textColor, range: NSMakeRange(0,sStationStr.length))
                sListStr.insertAttributedString(sStationStr, atIndex: sListStr.length)
                sListStr.insertAttributedString(sLineStr, atIndex: 2)
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
    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return "换乘方案详情"
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueTransferResultMap":
                var vc = segue.destinationViewController as! busTransferResultMapViewController
                vc.transfer = (sender as! Transfer)
                vc.startPoint = startPoint
                vc.endPoint = endPoint
                break
            default:
                break
                
            }
        }
        
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
