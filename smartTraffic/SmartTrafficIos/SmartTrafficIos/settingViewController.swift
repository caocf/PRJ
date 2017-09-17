//
//  settingViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/21.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class settingViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {

    var settingName = ["个人中心","意见建议","关于我们"]
    
    var avErrorInfo:UIAlertView!
    @IBOutlet weak var tvSetting:UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tvSetting.dataSource = self
        tvSetting.delegate = self
        
        tvSetting.reloadData()
        
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
        return settingName.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("settingCell", forIndexPath: indexPath) 
        cell.textLabel?.text = settingName[indexPath.row]
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        
        switch (indexPath.row)
        {
        case 0:
            if (LoginUser.user != nil)
            {
                self.performSegueWithIdentifier("segueUserInfo", sender: nil)
            }
            else
            {
                self.performSegueWithIdentifier("segueLogin", sender: nil)
            }
        case 1:
            if let _ = LoginUser.user
            {
                self.performSegueWithIdentifier("segueShowSuggestion", sender: nil)
            }
            else
            {
                avErrorInfo = UIAlertView(title: "打开收藏错误", message: "您尚未登录，请先登录", delegate: nil, cancelButtonTitle:"关闭")
                avErrorInfo.show()
            }
        case 2:
            self.performSegueWithIdentifier("segueAbout", sender: nil)
            
//            segueShowSuggestion
        default:
            break
        }
    }

    @IBAction func closeToSetting(segue: UIStoryboardSegue)
    {
        
        print("closeToSetting")
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
