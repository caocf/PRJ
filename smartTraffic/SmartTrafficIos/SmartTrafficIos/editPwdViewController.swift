//
//  forgetPwdViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/22.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class editPwdViewController: UIViewController,UserDelegate,UIAlertViewDelegate {

    var phoneNumber:String!
    var avAlertInfo:UIAlertView!
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    @IBOutlet weak var txtOldPassword:UITextField!
    @IBOutlet weak var txtNewPassword:UITextField!
    @IBOutlet weak var txtConfirmPassword:UITextField!
    
    
    @IBAction func btnChange(sender: UIButton) {
        if (txtOldPassword.text!.isEmpty || txtNewPassword.text!.isEmpty || txtConfirmPassword.text!.isEmpty)
        {
            avAlertInfo = UIAlertView(title: "修改错误", message: "所有输入均不能为空，请重新输入", delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
        else
        {
            if (txtNewPassword.text != txtConfirmPassword.text)
            {
                avAlertInfo = UIAlertView(title: "修改错误", message: "输入的两次密码不相同，请重新输入。", delegate: nil, cancelButtonTitle: "关闭")
                avAlertInfo.show()
            }
            else
            {
                aivLoading.startAnimating()
                User.appEditPassword(self, phone: phoneNumber, userpassword: txtOldPassword.text!, newPass: txtNewPassword.text!)
            }
        }
        
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        LoginUser.user = nil
        self.performSegueWithIdentifier("segueChangeToLogin", sender: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        print(phoneNumber)
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func updateAppLogin(status: Int, msg: String, user: User) {
        
    }
    
    func updateAppAddUser(status: Int, msg: String) {
        
    }
    
    func updateAppPhoneIsExisted(status: Int, msg: String) {
        
    }
    
    func updateAppEditPassword(status: Int, msg: String) {
        aivLoading.stopAnimating()
        if (status == 1)
        {
            avAlertInfo = UIAlertView(title: "修改成功", message: "请重新登录。", delegate: self, cancelButtonTitle: "确定")
            avAlertInfo.show()
        }
        else
        {
            avAlertInfo = UIAlertView(title: "修改失败", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }

    }
    
    func updateAppResetPass(status: Int, msg: String) {
        
    }
    
    func updateAppSendMessage(status: Int, msg: String, result: String) {
        
    }
    
    func updateUserError(error: String) {
        aivLoading.stopAnimating()
        avAlertInfo = UIAlertView(title:
            "修改错误", message: error, delegate: nil, cancelButtonTitle: "确定")
        avAlertInfo.show()
        print(error)
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
