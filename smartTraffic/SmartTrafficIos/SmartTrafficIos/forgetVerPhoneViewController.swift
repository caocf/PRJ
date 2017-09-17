//
//  forgetVerPhoneViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/22.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class forgetVerPhoneViewController: UIViewController {

    var avAlertInfo:UIAlertView!
    @IBOutlet weak var txtPhone:UITextField!
    
    
    @IBAction func btnNext(sender: UIButton) {
        if txtPhone.text!.isEmpty
        {
            avAlertInfo = UIAlertView(title: "验证错误", message: "手机号不能为空", delegate: nil, cancelButtonTitle: "关闭")
            avAlertInfo.show()
        }
        else
        {
            self.performSegueWithIdentifier("segueValidateForgetPhone", sender: txtPhone.text)

        }

    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueValidateForgetPhone":
                let vc = segue.destinationViewController as! forgetSendVerViewController
                vc.phoneNumber = (sender as! String)
            default:
                break
                
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
    
    @IBAction func closeToForgetVerPhone(segue: UIStoryboardSegue)
    {
        
        print("closeToForgetVerPhone")
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
