//
//  publicBikeFavorViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/27.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class publicBikeFavorViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate,BikeFavorDelegate, PublicBikeProtocol{

    @IBOutlet weak var tvFavor:UITableView!
    @IBAction func ubbReturn(sender: UIBarButtonItem) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    @IBAction func ubbDelete(sender: UIBarButtonItem) {
        tvFavor.setEditing(!tvFavor.editing, animated: true)
    }

    var avErrorInfo:UIAlertView!
    var publicBike:[PublicBike] = []

    var bikeFavor = [BikeFavor]()
        {
        didSet{
            tvFavor.reloadData()

        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tvFavor.dataSource = self
        tvFavor.delegate = self
        if let user = LoginUser.user
        {
            BikeFavor.APPQueryAllBikeStationFavor(self, userid: user.userid)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "获取收藏错误", message: "您尚未登录，请先登录。", delegate: self, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

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
        return bikeFavor.count
    }
    
    func tableView(tableView: UITableView, editingStyleForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCellEditingStyle {
        return UITableViewCellEditingStyle.Delete
    }
    
    func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        tableView.beginUpdates()
        BikeFavor.APPDeleteBikeForStation(self, userid: LoginUser.user!.userid, stationIds: "\(bikeFavor[indexPath.row].id)")
        bikeFavor.removeAtIndex(indexPath.row)
        tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
        tableView.endUpdates()
    }
    
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:UITableViewCell
        
        cell = tableView.dequeueReusableCellWithIdentifier("bikeFavorCell", forIndexPath: indexPath) 
        let selectBike = bikeFavor[indexPath.row]
        cell.textLabel?.text = "\(selectBike.stationName)"
        cell.detailTextLabel?.text = "\(selectBike.address)"
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        
        let ac = PublicBike.initNullPublicBike().getActionUrl(bikeFavor[indexPath.row].stationID)
        PublicBike.initPublicBike(self, actionUrl: ac.0, httpBody: ac.1)
    }
    
    func updateAPPQueryAllBikeStationFavor(status: Int, msg: String, bikeFavor: [BikeFavor]) {
        if (status == 1)
        {
            self.bikeFavor = bikeFavor
        }
        else
        {
            avErrorInfo = UIAlertView(title: "获取信息错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }

    }
    
    func updateAPPDeleteBikeForStation(status: Int, msg: String) {
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
    
    func updateAPPSaveBikeStationFavor(status: Int, msg: String) {
        
    }

    func updatePublicBike(updatePublicBike: PublicBike) {
        
    }
    
    func updatePublicBike(updatePublicBikes: [PublicBike]) {
        publicBike = updatePublicBikes
        PublicBike.updateRealTimeStationCount(self, bikeList: publicBike)

    }
    
    func updatePublicBikeReal(updatePublicBikes: [PublicBike]) {
        publicBike = updatePublicBikes
        self.performSegueWithIdentifier("segueFavorToBike", sender: publicBike)
    }
    
    func getPublicBikeError(error: String) {
        avErrorInfo = UIAlertView(title: "获取公共自行车站点数据", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueFavorToBike":
                let vc = segue.destinationViewController as! publicBikeStationViewController
                vc.publicBike = (sender as! [PublicBike])
                vc.bIsFavor = true
                break
            default:
                break
                
            }
        }
    }

    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        
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
