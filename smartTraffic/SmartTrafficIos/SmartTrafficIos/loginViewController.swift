//
//  loginViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/21.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class loginViewController: UIViewController,UserDelegate {

    var avAlertInfo:UIAlertView!
    @IBOutlet weak var txtUsername:UITextField!
    @IBOutlet weak var txtPassword:UITextField!
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    
    @IBAction func btnLogin(sender: UIButton) {
        txtUsername.resignFirstResponder()
        txtPassword.resignFirstResponder()
        if (txtUsername.text!.isEmpty || txtPassword.text!.isEmpty)
        {
            avAlertInfo = UIAlertView(title: "输入错误", message: "手机号和密码都不能为空，请重新输入。", delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
        else
        {
            User.appLogin(self, phone: txtUsername.text!, userpassword: txtPassword.text!)
            aivLoading.startAnimating()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func closeToLogin(segue: UIStoryboardSegue)
    {
        
        print("closeToLogin")
    }

    func updateAppLogin(status: Int, msg: String, user: User) {
        aivLoading.stopAnimating()
        if (status == 1)
        {
            LoginUser.user = user
            User.writeLoginUser(user.phone, password: user.userpassword)
            performSegueWithIdentifier("segueLoginSuccess", sender: nil)
        }
        else
        {
            avAlertInfo = UIAlertView(title: "登录错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
    }
    
    func updateAppAddUser(status: Int, msg: String) {
        
    }
    
    func updateAppPhoneIsExisted(status: Int, msg: String) {
        
    }
    
    func updateAppEditPassword(status: Int, msg: String) {
        
    }
    
    func updateAppResetPass(status: Int, msg: String) {
        
    }
    
    func updateAppSendMessage(status: Int, msg: String, result: String) {
        
    }
    
    func updateUserError(error: String) {
        aivLoading.stopAnimating()
        avAlertInfo = UIAlertView(title: "登录错误", message: error, delegate: nil, cancelButtonTitle: "确定")
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
