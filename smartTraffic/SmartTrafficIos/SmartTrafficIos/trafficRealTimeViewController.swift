//
//  trafficRealTimeViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/6/12.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class trafficRealTimeViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,tpiDelegate,UISearchBarDelegate,UIGestureRecognizerDelegate {

    @IBOutlet weak var tvInfo:UITableView!
    @IBOutlet weak var sbSearch:UISearchBar!
    @IBOutlet weak var sbSearchHeight:NSLayoutConstraint!
    @IBOutlet weak var scRoadTypeHeight:NSLayoutConstraint!
    @IBOutlet weak var vRoadType:UIView!
    
    var selectType = 0
    var selectRoadType = 0
    var roadSelectLevel = 0
    var sTableTitle = ""
    var avErrorInfo:UIAlertView!
    var singleTap:UITapGestureRecognizer!
    var selectRoadName:String?
    var selectRoad:RoadTpi?
    
    var roadTpi = [RoadTpi]()
        {
            didSet{
                if selectType == 2 && roadTpi.count > 0
                {
                    let showTime = NSDate(timeIntervalSince1970: NSTimeInterval(roadTpi[0].time + 28800))
                    sTableTitle = "更新时间:" + Tools.nsdateConvertToString(showTime, formatString: "yyyy-MM-dd HH:mm")
                }
                tvInfo.reloadData()
        }
    }
    
    var passTpi = [PassTpi]()
        {
        didSet{
            if selectType == 3 && passTpi.count > 0
            {
                let showTime = NSDate(timeIntervalSince1970: NSTimeInterval(passTpi[0].time + 28800))
                sTableTitle = "更新时间:" + Tools.nsdateConvertToString(showTime, formatString: "yyyy-MM-dd HH:mm")
            }
            tvInfo.reloadData()
        }
    }
    
    var cbdTpi = [CBDTpi]()
        {
        didSet{
            if selectType == 1 && cbdTpi.count > 0
            {
                let showTime = NSDate(timeIntervalSince1970: NSTimeInterval(cbdTpi[0].time + 28800))
                sTableTitle = "更新时间:" + Tools.nsdateConvertToString(showTime, formatString: "yyyy-MM-dd HH:mm")
            }
            tvInfo.reloadData()
        }
    }
    
    var areaTpi = [NewAreaTpi]()
        {
        didSet{
            if selectType == 0 && areaTpi.count > 0
            {
                let showTime = NSDate(timeIntervalSince1970: NSTimeInterval(areaTpi[0].time + 28800))
                sTableTitle = "更新时间:" + Tools.nsdateConvertToString(showTime, formatString: "yyyy-MM-dd HH:mm")
            }
            tvInfo.reloadData()
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.userInteractionEnabled = true
        tvInfo.dataSource = self
        tvInfo.delegate = self
//        singleTap.delegate = self
        singleTap = UITapGestureRecognizer(target: self, action: Selector("fingerTapped:"))
        singleTap.delegate = self
        singleTap.numberOfTouchesRequired = 1
        self.view.addGestureRecognizer(singleTap)
        sbSearch.delegate = self
        sbSearchHeight.constant = 0.0
        // Do any additional setup after loading the view.
    }
    
    func gestureRecognizer(gestureRecognizer: UIGestureRecognizer, shouldReceiveTouch touch: UITouch) -> Bool {
        let selectViewName = NSStringFromClass(touch.view!.classForCoder)
        print(selectViewName)
        if (selectViewName == "UITableViewCellContentView" || selectViewName == "UITableViewLabel")
        {
            return false
        }
        return true
    }
    
    func fingerTapped(gestureRecognizer:UITapGestureRecognizer)
    {
        sbSearch.resignFirstResponder()
    }
    
    @IBAction func scRoadType(sender: UISegmentedControl) {
        selectRoadType = sender.selectedSegmentIndex
        roadSelectLevel = 0
        switch(selectRoadType)
        {
        case 0:
            RoadTpi.initRoadTpi(self)
        case 1:
            RoadTpi.initRoadTpiFast(self)
        case 2:
            RoadTpi.initRoadTpiMain(self)
        case 3:
            RoadTpi.initRoadTpiOther(self)
        default:
            break
        }
    }
    
    @IBAction func scSelectIndex(sender: UISegmentedControl) {
        selectType = sender.selectedSegmentIndex
        roadSelectLevel = 0
        print("select index\(selectType)")
        switch(selectType)
        {
        case 0:
//            sTableTitle = "区域指数"
            vRoadType.hidden = true
            scRoadTypeHeight.constant = 0.0
            sbSearchHeight.constant = 0.0
            NewAreaTpi.initAreaTpi(self)
        case 1:
//            sTableTitle = "热点指数"
            vRoadType.hidden = true
            scRoadTypeHeight.constant = 0.0
            sbSearchHeight.constant = 0.0
            CBDTpi.initCBDTpi(self)
        case 2:
//            sTableTitle = "主要道路指数"
            vRoadType.hidden = false
            scRoadTypeHeight.constant = 36.0
            sbSearchHeight.constant = 44.0
            RoadTpi.initRoadTpi(self)
        case 3:
//            sTableTitle = "通道走廊指数"
            vRoadType.hidden = true
            scRoadTypeHeight.constant = 0.0
            sbSearchHeight.constant = 0.0
            PassTpi.initPassTpi(self)
        default:
            break
        }
//        tvInfo.reloadData()
    }
    
    override func viewDidAppear(animated: Bool) {
//        UIDevice.currentDevice().setValue(UIDeviceOrientation.LandscapeLeft.rawValue, forKey: "orientation")
        UIDevice.currentDevice().setValue(UIDeviceOrientation.Portrait.rawValue, forKey: "orientation")
        switch(selectType)
        {
        case 0:
            //            sTableTitle = "区域指数"
            vRoadType.hidden = true
            scRoadTypeHeight.constant = 0.0
            sbSearchHeight.constant = 0.0
            NewAreaTpi.initAreaTpi(self)
        case 1:
            //            sTableTitle = "热点指数"
            vRoadType.hidden = true
            scRoadTypeHeight.constant = 0.0
            sbSearchHeight.constant = 0.0
            CBDTpi.initCBDTpi(self)
        case 2:
            //            sTableTitle = "主要道路指数"
            vRoadType.hidden = false
            scRoadTypeHeight.constant = 36.0
            sbSearchHeight.constant = 44.0
            RoadTpi.initRoadTpi(self)
        case 3:
            //            sTableTitle = "通道走廊指数"
            vRoadType.hidden = true
            scRoadTypeHeight.constant = 0.0
            sbSearchHeight.constant = 0.0
            PassTpi.initPassTpi(self)
        default:
            break
        }


    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Portrait
    }
    
    override func preferredInterfaceOrientationForPresentation() -> UIInterfaceOrientation {
        return UIInterfaceOrientation.Portrait
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch(selectType)
        {
        case 0:
            return areaTpi.count + 1
        case 1:
            return cbdTpi.count + 1
        case 2:
            if (roadSelectLevel > 0)
            {
                return roadTpi.count + 2
            }
            else
            {
                return roadTpi.count + 1
            }
        case 3:
            return passTpi.count + 1
        default:
            return 0
        }
    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return sTableTitle
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        if (indexPath.row == 0)
        {
            return 35
        }
        else
        {
            return 45
        }
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        switch(selectType)
        {
        case 0:
            let cell = tableView.dequeueReusableCellWithIdentifier("areaNumberCell", forIndexPath: indexPath) as! ReadTimeTableViewCell
            if (indexPath.row == 0)
            {
                cell.backgroundColor = UIColor(red: 0xaa/0xff, green: 0xaa/0xff, blue: 0xaa/0xff, alpha: 1)
                cell.labRoadIndex.text = "指数"
                cell.labRoadName.text = "区域名称"
                cell.labRoadLevel.text = "拥堵等级"
                cell.labAvgSpeed.text = "平均速度"
                cell.labRoadLevel.textColor = UIColor.blackColor()
                cell.labRoadIndex.textColor = UIColor.blackColor()
            }
            else
            {
                if (areaTpi.count > indexPath.row - 1)
                {
                    let selectAreaTpi = areaTpi[indexPath.row - 1]
                    let tpiColor = TpiTools.getTpiLevelColor(selectAreaTpi.tpiLevel)
                    cell.backgroundColor = UIColor.whiteColor()
                    cell.labRoadName.text = "\(selectAreaTpi.areaName)"
                    cell.labRoadIndex.text = "\(selectAreaTpi.tpi)"
                    cell.labRoadLevel.text = "\(selectAreaTpi.tpiLevel)"
                    cell.labAvgSpeed.text = "\(selectAreaTpi.avgSpeed)"
                    cell.labRoadLevel.textColor = tpiColor
                    cell.labRoadIndex.textColor = tpiColor
                }
            }
            return cell
        case 1:
            let cell = tableView.dequeueReusableCellWithIdentifier("areaNumberCell", forIndexPath: indexPath) as! ReadTimeTableViewCell
            if (indexPath.row == 0)
            {
                cell.backgroundColor = UIColor(red: 0xaa/0xff, green: 0xaa/0xff, blue: 0xaa/0xff, alpha: 1)
                cell.labRoadIndex.text = "指数"
                cell.labRoadName.text = "热点名称"
                cell.labRoadLevel.text = "拥堵等级"
                cell.labAvgSpeed.text = "平均速度"
                cell.labRoadLevel.textColor = UIColor.blackColor()
                cell.labRoadIndex.textColor = UIColor.blackColor()
            }
            else
            {
                if (cbdTpi.count > indexPath.row - 1)
                {
                    let selectCBDTpi = cbdTpi[indexPath.row - 1]
                    let tpiColor = TpiTools.getTpiLevelColor(selectCBDTpi.tpiLevel)
                    cell.backgroundColor = UIColor.whiteColor()
                    cell.labRoadName.text = "\(selectCBDTpi.cbdName)"
                    cell.labRoadIndex.text = "\(selectCBDTpi.tpi)"
                    cell.labRoadLevel.text = "\(selectCBDTpi.tpiLevel)"
                    cell.labAvgSpeed.text = "\(selectCBDTpi.avgSpeed)"
                    cell.labRoadLevel.textColor = tpiColor
                    cell.labRoadIndex.textColor = tpiColor
                }

            }
            return cell
        case 2:
            switch indexPath.row
            {
            case 0:
                let cell = tableView.dequeueReusableCellWithIdentifier("areaNumberCell", forIndexPath: indexPath) as! ReadTimeTableViewCell
                cell.backgroundColor = UIColor(red: 0xaa/0xff, green: 0xaa/0xff, blue: 0xaa/0xff, alpha: 1)
                cell.labRoadIndex.text = "指数"
                cell.labRoadName.text = "道路名称"
                cell.labRoadLevel.text = "拥堵等级"
                cell.labAvgSpeed.text = "平均速度"
                cell.labRoadLevel.textColor = UIColor.blackColor()
                cell.labRoadIndex.textColor = UIColor.blackColor()
                return cell
            case 1:
                if (roadSelectLevel > 0)
                {
                    let returnCell = tableView.dequeueReusableCellWithIdentifier("returnCell", forIndexPath: indexPath) 
                    returnCell.textLabel?.text = "  返回上层"
                    return returnCell
                }
                else
                {
                    let cell = tableView.dequeueReusableCellWithIdentifier("areaNumberCell", forIndexPath: indexPath) as! ReadTimeTableViewCell
                    if (roadTpi.count > indexPath.row - 1)
                    {
                        let selectRoadTpi = roadTpi[indexPath.row - 1]
                        let tpiColor = TpiTools.getTpiLevelColor(selectRoadTpi.tpiLevel)
                        cell.backgroundColor = UIColor.whiteColor()
                        cell.labRoadName.text = "\(selectRoadTpi.roadName)"
                        cell.labRoadIndex.text = "\(selectRoadTpi.tpi)"
                        cell.labRoadLevel.text = "\(selectRoadTpi.tpiLevel)"
                        cell.labAvgSpeed.text = "\(selectRoadTpi.avgSpeed)"
                        cell.labRoadLevel.textColor = tpiColor
                        cell.labRoadIndex.textColor = tpiColor
                    }
                    return cell
                }
            default:
                let cell = tableView.dequeueReusableCellWithIdentifier("areaNumberCell", forIndexPath: indexPath) as! ReadTimeTableViewCell
                if (roadSelectLevel > 0)
                {
                    if (roadTpi.count > indexPath.row - 2)
                    {
                        let selectRoadTpi = roadTpi[indexPath.row - 2]
                        let tpiColor = TpiTools.getTpiLevelColor(selectRoadTpi.tpiLevel)
                        cell.backgroundColor = UIColor.whiteColor()
                        cell.labRoadName.text = "\(selectRoadTpi.startName)→\(selectRoadTpi.endName)"
                        cell.labRoadIndex.text = "\(selectRoadTpi.tpi)"
                        cell.labRoadLevel.text = "\(selectRoadTpi.tpiLevel)"
                        cell.labAvgSpeed.text = "\(selectRoadTpi.avgSpeed)"
                        cell.labRoadLevel.textColor = tpiColor
                        cell.labRoadIndex.textColor = tpiColor
                    }
                }
                else
                {
                    if (roadTpi.count > indexPath.row - 1)
                    {
                        let selectRoadTpi = roadTpi[indexPath.row - 1]
                        let tpiColor = TpiTools.getTpiLevelColor(selectRoadTpi.tpiLevel)
                        cell.backgroundColor = UIColor.whiteColor()
                        cell.labRoadName.text = "\(selectRoadTpi.roadName)"
                        cell.labRoadIndex.text = "\(selectRoadTpi.tpi)"
                        cell.labRoadLevel.text = "\(selectRoadTpi.tpiLevel)"
                        cell.labAvgSpeed.text = "\(selectRoadTpi.avgSpeed)"
                        cell.labRoadLevel.textColor = tpiColor
                        cell.labRoadIndex.textColor = tpiColor
                    }
                }
                return cell
            }
            
//            let cell = tableView.dequeueReusableCellWithIdentifier("areaNumberCell", forIndexPath: indexPath) as! ReadTimeTableViewCell
//            if (indexPath.row == 0)
//            {
//                cell.backgroundColor = UIColor(red: 0xaa/0xff, green: 0xaa/0xff, blue: 0xaa/0xff, alpha: 1)
//                cell.labRoadIndex.text = "指数"
//                cell.labRoadName.text = "道路名称"
//                cell.labRoadLevel.text = "拥堵等级"
//                cell.labAvgSpeed.text = "平均速度"
//                cell.labRoadLevel.textColor = UIColor.blackColor()
//                cell.labRoadIndex.textColor = UIColor.blackColor()
//            }
//            else
//            {
//                if (roadSelectLevel > 0)
//                {
//                    if (indexPath.row == 1)
//                    {
//                        let returnCell = tableView.dequeueReusableCellWithIdentifier("returnCell", forIndexPath: indexPath) as! UITableViewCell
//                        returnCell.textLabel?.text = "返回上级"
//                        return returnCell
//                    }
//                    else
//                    {
//                        if (roadTpi.count > indexPath.row - 2)
//                        {
//                            let selectRoadTpi = roadTpi[indexPath.row - 2]
//                            let tpiColor = TpiTools.getTpiLevelColor(selectRoadTpi.tpiLevel)
//                            cell.backgroundColor = UIColor.whiteColor()
//                            cell.labRoadName.text = "\(selectRoadTpi.roadName):\(selectRoadTpi.startName)→\(selectRoadTpi.endName)"
//                            cell.labRoadIndex.text = "\(selectRoadTpi.tpi)"
//                            cell.labRoadLevel.text = "\(selectRoadTpi.tpiLevel)"
//                            cell.labAvgSpeed.text = "\(selectRoadTpi.avgSpeed)"
//                            cell.labRoadLevel.textColor = tpiColor
//                            cell.labRoadIndex.textColor = tpiColor
//                        }
//                    }
//                }
//                else
//                {
//                    if (roadTpi.count > indexPath.row - 1)
//                    {
//                        let selectRoadTpi = roadTpi[indexPath.row - 1]
//                        let tpiColor = TpiTools.getTpiLevelColor(selectRoadTpi.tpiLevel)
//                        cell.backgroundColor = UIColor.whiteColor()
//                        cell.labRoadName.text = "\(selectRoadTpi.roadName)"
//                        cell.labRoadIndex.text = "\(selectRoadTpi.tpi)"
//                        cell.labRoadLevel.text = "\(selectRoadTpi.tpiLevel)"
//                        cell.labAvgSpeed.text = "\(selectRoadTpi.avgSpeed)"
//                        cell.labRoadLevel.textColor = tpiColor
//                        cell.labRoadIndex.textColor = tpiColor
//                    }
//                }
//            }
//            return cell
        case 3:
            let cell = tableView.dequeueReusableCellWithIdentifier("areaNumberCell", forIndexPath: indexPath) as! ReadTimeTableViewCell
            if (indexPath.row == 0)
            {
                cell.backgroundColor = UIColor(red: 0xaa/0xff, green: 0xaa/0xff, blue: 0xaa/0xff, alpha: 1)
                cell.labRoadIndex.text = "指数"
                cell.labRoadName.text = "通道名称"
                cell.labRoadLevel.text = "拥堵等级"
                cell.labAvgSpeed.text = "平均速度"
                cell.labRoadLevel.textColor = UIColor.blackColor()
                cell.labRoadIndex.textColor = UIColor.blackColor()
            }
            else
            {
                if (passTpi.count > indexPath.row - 1)
                {
                    print("passTpi row:\(indexPath.row)")
                    let selectPassTpi = passTpi[indexPath.row - 1]
                    let tpiColor = TpiTools.getTpiLevelColor(selectPassTpi.tpiLevel)
                    cell.backgroundColor = UIColor.whiteColor()
                    cell.labRoadName.text = "\(selectPassTpi.passName)"
                    cell.labRoadIndex.text = "\(selectPassTpi.tpi)"
                    cell.labRoadLevel.text = "\(selectPassTpi.tpiLevel)"
                    cell.labAvgSpeed.text = "\(selectPassTpi.avgSpeed)"
                    cell.labRoadLevel.textColor = tpiColor
                    cell.labRoadIndex.textColor = tpiColor
                }
            }
            return cell
        default:
            let cell = tableView.dequeueReusableCellWithIdentifier("areaNumberCell", forIndexPath: indexPath) as! ReadTimeTableViewCell
            return cell
        }
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        sbSearch.resignFirstResponder()
        if selectType == 2
        {
            if indexPath.row > 0
            {
                switch roadSelectLevel
                {
                case 0:
                    roadSelectLevel = 1
                    if (roadTpi.count > indexPath.row - 1)
                    {
                        selectRoad = roadTpi[indexPath.row - 1]
                        RoadTpi.initTwoRoadTpi(self, roadName: roadTpi[indexPath.row - 1].roadName)
                    }
                case 1:
                    if indexPath.row == 1
                    {
                        roadSelectLevel = 0
                        selectRoad = nil
                        if let selectRoadNameNew = selectRoadName
                        {
                            RoadTpi.initRoadTpi(self, roadName: selectRoadNameNew)
                        }
                        else
                        {
                            switch(selectRoadType)
                            {
                            case 0:
                                RoadTpi.initRoadTpi(self)
                            case 1:
                                RoadTpi.initRoadTpiFast(self)
                            case 2:
                                RoadTpi.initRoadTpiMain(self)
                            case 3:
                                RoadTpi.initRoadTpiOther(self)
                            default:
                                break
                            }
                        }
                    }
                    else
                    {
                        roadSelectLevel = 2
                        selectRoad = roadTpi[indexPath.row - 2]
                        let selectRoadSection = roadTpi[indexPath.row - 2]
                        RoadTpi.initRoadSectionTpi(self, roadName: selectRoadSection.roadName, roadStart: selectRoadSection.startName, roadEnd: selectRoadSection.endName)
                    }
                case 2:
                    if indexPath.row == 1
                    {
                        roadSelectLevel = 1
                        RoadTpi.initTwoRoadTpi(self, roadName: roadTpi[0].roadName)
                    }
                default:
                    break
                }
            }
        }

    }
    
    func updateError(error: String) {
        avErrorInfo = UIAlertView(title: "获取指数失败", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
    }
    
    func updateRoadTpi(updateRoadTpi: [RoadTpi], status: Int) {
        if status == 2000
        {
            if updateRoadTpi.isEmpty
            {
                self.roadTpi.removeAll()
                avErrorInfo = UIAlertView(title: "获取指数失败", message: "没有获取到指数", delegate: nil, cancelButtonTitle: "关闭")
                avErrorInfo.show()
            }
            else
            {
                self.roadTpi = updateRoadTpi
            }
        }
        else
        {
            self.roadTpi.removeAll()
            avErrorInfo = UIAlertView(title: "获取指数失败", message: "status:\(status)", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    func updatePassTpi(updatePassTpi: [PassTpi], status: Int) {
        if status == 2000
        {
            if updatePassTpi.isEmpty
            {
                self.passTpi.removeAll()
                avErrorInfo = UIAlertView(title: "获取指数失败", message: "没有获取到指数", delegate: nil, cancelButtonTitle: "关闭")
                avErrorInfo.show()
            }
            else
            {
                self.passTpi = updatePassTpi
            }
        }
        else
        {
            self.passTpi.removeAll()
            avErrorInfo = UIAlertView(title: "获取指数失败", message: "status:\(status)", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    func updateCBDTpi(updateCBDTpi: [CBDTpi], status: Int) {
        if status == 2000
        {
            if updateCBDTpi.isEmpty
            {
                self.cbdTpi.removeAll()
                avErrorInfo = UIAlertView(title: "获取指数失败", message: "没有获取到指数", delegate: nil, cancelButtonTitle: "关闭")
                avErrorInfo.show()
            }
            else
            {
                self.cbdTpi = updateCBDTpi
            }
        }
        else
        {
            self.cbdTpi.removeAll()
            
            print("status:\(status)")
            avErrorInfo = UIAlertView(title: "获取指数失败", message: "没有获取到指数", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    
    func searchBarSearchButtonClicked(searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        if searchBar.text!.isEmpty
        {
            selectRoadName = nil
            RoadTpi.initRoadTpi(self)
        }
        else
        {
            selectRoadName = searchBar.text
            RoadTpi.initRoadTpi(self, roadName: searchBar.text!)
        }
    }
    
    func updateAreaTpi(updateAreaTpi: [NewAreaTpi], status: Int) {
        print("area tpi:\(updateAreaTpi)\nstatus:\(status)")
        if status == 2000
        {
            if updateAreaTpi.isEmpty
            {
                self.areaTpi.removeAll()
                avErrorInfo = UIAlertView(title: "获取指数失败", message: "没有获取到指数", delegate: nil, cancelButtonTitle: "关闭")
                avErrorInfo.show()
            }
            else
            {
                self.areaTpi = updateAreaTpi
            }
        }
        else
        {
            self.areaTpi.removeAll()
            self.areaTpi = []
            print("status:\(status)")
            avErrorInfo = UIAlertView(title: "获取指数失败", message: "没有获取到指数", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
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
