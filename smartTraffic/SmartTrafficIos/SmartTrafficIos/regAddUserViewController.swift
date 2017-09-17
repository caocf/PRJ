//
//  regAddUserViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/21.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class regAddUserViewController: UIViewController,UserDelegate {

    var phoneNumber:String!
    var avAlertInfo:UIAlertView!
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!


    @IBOutlet weak var txtNickName:UITextField!
    @IBOutlet weak var txtPassword:UITextField!
    @IBOutlet weak var txtConfirmPassword:UITextField!
    @IBOutlet weak var txtEmail:UITextField!

    @IBAction func btnRegAddUser(sender: UIButton) {
        if (txtNickName.text!.isEmpty || txtPassword.text!.isEmpty || txtConfirmPassword.text!.isEmpty || txtEmail.text!.isEmpty)
        {
            avAlertInfo = UIAlertView(title: "注册错误", message: "所有输入均不能为空，请重新输入", delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
        else
        {
            if (txtPassword.text != txtConfirmPassword.text)
            {
                avAlertInfo = UIAlertView(title: "注册错误", message: "输入的两次密码不相同，请重新输入。", delegate: nil, cancelButtonTitle: "关闭")
                avAlertInfo.show()
            }
            else
            {
                aivLoading.startAnimating()
            	User.appAddUser(self, phone: phoneNumber, username: txtNickName.text!, userpassword: txtPassword.text!, email: txtEmail.text!)
            }
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
    
    
    func updateAppLogin(status: Int, msg: String, user: User) {
        
    }
    
    func updateAppAddUser(status: Int, msg: String) {
        aivLoading.stopAnimating()
        if (status == 1)
        {
            self.performSegueWithIdentifier("segueRegSuccess", sender: nil)
        }
        else
        {
            avAlertInfo = UIAlertView(title: "注册失败", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
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
        avAlertInfo = UIAlertView(title: "发送错误", message: error, delegate: nil, cancelButtonTitle: "确定")
        avAlertInfo.show()
        print(error)
    }
    
    override func shouldAutorotate() -> Bool {
        return false
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.Portrait
    }
//    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
//        if let segueIdentifier  = segue.identifier
//        {
//            switch segueIdentifier
//            {
//            case "segueAddUser":
//                var vc = segue.destinationViewController as! regAddUserViewController
//                vc.phoneNumber = (sender as! String)
//            default:
//                break
//                
//            }
//        }
//    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
