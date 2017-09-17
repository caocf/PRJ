//
//  regSendVerViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/21.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class regSendVerViewController: UIViewController,UserDelegate {

    var verNumber:String?
    var avAlertInfo:UIAlertView!
    var phoneNumber:String!
    @IBOutlet weak var txtVer:UITextField!
    
    @IBAction func btnNext(sender: UIButton) {
        if txtVer.text!.isEmpty
        {
            avAlertInfo = UIAlertView(title: "验证错误", message: "验证码不能为空", delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
        else
        {
            //verNumber = "123456"
            if let verNo = verNumber where verNo == txtVer.text
            {
                self.performSegueWithIdentifier("segueAddUser", sender: phoneNumber)
            }
            else
            {
                avAlertInfo = UIAlertView(title: "验证错误", message: "验证码不正确，请重新输入。", delegate: nil, cancelButtonTitle: "关闭")
                avAlertInfo.show()
            }
            
        }

    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        User.appSendMessage(self, phone: phoneNumber)

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
        
    }
    
    func updateAppResetPass(status: Int, msg: String) {
        
    }
    
    func updateAppSendMessage(status: Int, msg: String, result: String) {
        if (status == 1)
        {
            verNumber = result
            avAlertInfo = UIAlertView(title: "验证手机", message: "验证码成功发送到手机。", delegate: nil, cancelButtonTitle: "关闭")
        }
        else
        {
            avAlertInfo = UIAlertView(title: "验证错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
        }
        avAlertInfo.show()
    }
    
    func updateUserError(error: String) {
        avAlertInfo = UIAlertView(title: "发送错误", message: error, delegate: nil, cancelButtonTitle: "确定")
        avAlertInfo.show()
        print(error)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueAddUser":
                let vc = segue.destinationViewController as! regAddUserViewController
                vc.phoneNumber = (sender as! String)
            default:
                break
                
            }
        }
    }
    
    @IBAction func closeToRegSendVer(segue: UIStoryboardSegue)
    {
        
        print("closeToRegSendVer")
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
