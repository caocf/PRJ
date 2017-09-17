//
//  userInfoViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/21.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class userInfoViewController: UIViewController {

    @IBOutlet weak var txtUserNo:UITextField!
    @IBOutlet weak var txtNickName:UITextField!
    @IBOutlet weak var txtPhone:UITextField!
    @IBOutlet weak var txtEmail:UITextField!

    
    @IBAction func btnLogout(sender: UIButton) {
        self.dismissViewControllerAnimated(true, completion: { () -> Void in
            LoginUser.user = nil
        })
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if let user = LoginUser.user
        {
            txtUserNo.text = "用户编号：\(user.userid)"
            txtPhone.text = "手机号码：\(user.phone)"
            txtNickName.text = "用户昵称：\(user.username)"
            txtEmail.text = "电子邮箱：\(user.email)"
        }
        else
        {
            UIAlertView(title: "错误", message: "获取已登录用户信息错误。", delegate: nil, cancelButtonTitle: "关闭").show()
        }
    
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueUserInfoToChangePwd":
                let vc = segue.destinationViewController as! editPwdViewController
                vc.phoneNumber = LoginUser.user!.phone            default:
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
//    segueUserInfoToChangePwd

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
