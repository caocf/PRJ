//
//  suggestionViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/27.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class suggestionViewController: UIViewController,SuggestionDelegate,UIAlertViewDelegate {

    var avErrorInfo:UIAlertView!
    var singleTap:UITapGestureRecognizer!
    
    @IBOutlet weak var tvTitle:UITextField!
    @IBOutlet weak var tvContent:UITextView!
    
    @IBAction func btnSubmit(sender: UIButton) {
        tvTitle.resignFirstResponder()
        tvContent.resignFirstResponder()
        if let user = LoginUser.user
        {
            Suggestion.addAppSuggestion(self, userid: user.userid, title: tvTitle.text!, content: tvContent.text)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "提交意见建议错误", message: "您尚未登录，请先登录", delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }

    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.userInteractionEnabled = true
        singleTap = UITapGestureRecognizer(target: self, action: Selector("fingerTapped:"))
        self.view.addGestureRecognizer(singleTap)
        // Do any additional setup after loading the view.
    }
    
    func fingerTapped(gestureRecognizer:UITapGestureRecognizer)
    {
        tvTitle.resignFirstResponder()
        tvContent.resignFirstResponder()

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func updateAddAppSuggestion(status: Int, msg: String) {
        if (status == 1)
        {
            avErrorInfo = UIAlertView(title: "提交成功", message: "提交成功", delegate: self, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }
        else
        {
            avErrorInfo = UIAlertView(title: "提交意见建议错误", message: msg, delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        self.dismissViewControllerAnimated(true, completion: nil)
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
