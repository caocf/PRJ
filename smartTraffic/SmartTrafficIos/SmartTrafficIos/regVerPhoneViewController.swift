//
//  regVerPhoneViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/21.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class regVerPhoneViewController: UIViewController,UserDelegate {

    var avAlertInfo:UIAlertView!
    @IBOutlet weak var txtPhone:UITextField!
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    @IBAction func btnNext(sender: UIButton) {
        if txtPhone.text!.isEmpty
        {
            avAlertInfo = UIAlertView(title: "验证错误", message: "手机号不能为空", delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
        else
        {
            aivLoading.startAnimating()
            User.appPhoneIsExisted(self, phone: txtPhone.text!)
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
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueValidatePhone":
                let vc = segue.destinationViewController as! regSendVerViewController
                vc.phoneNumber = (sender as! String)
            default:
                break
                
            }
        }
    }

    func updateAppLogin(status: Int, msg: String, user: User) {
        
    }
    
    func updateAppAddUser(status: Int, msg: String) {
        
    }
    
    func updateAppPhoneIsExisted(status: Int, msg: String) {
        aivLoading.stopAnimating()
        if (status == 1)
        {
            self.performSegueWithIdentifier("segueValidatePhone", sender: txtPhone.text)
        }
        else
        {
            avAlertInfo = UIAlertView(title: "验证错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
    }
    
    func updateAppEditPassword(status: Int, msg: String) {
        
    }
    
    func updateAppResetPass(status: Int, msg: String) {
        
    }
    
    func updateAppSendMessage(status: Int, msg: String, result: String) {
        
    }
    
    func updateUserError(error: String) {
        aivLoading.stopAnimating()
        avAlertInfo = UIAlertView(title: "验证错误", message: error, delegate: nil, cancelButtonTitle: "确定")
        avAlertInfo.show()
        print(error)
    }

    @IBAction func closeToRegVerPhone(segue: UIStoryboardSegue)
    {
        
        print("closeToRegVerPhone")
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
