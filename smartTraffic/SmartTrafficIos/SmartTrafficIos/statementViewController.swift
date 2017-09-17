//
//  statementViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/27.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class statementViewController: UIViewController {

    @IBOutlet weak var tvStatement:UITextView!
    var statementStr:String!
    override func viewDidLoad() {
        super.viewDidLoad()
        statementStr = statementStr.stringByTrimmingCharactersInSet(NSCharacterSet(charactersInString: "\""))
        statementStr = statementStr.stringByReplacingOccurrencesOfString("\\r\\n", withString: "\n")
        tvStatement.text = statementStr
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func btnOk(sender: UIButton) {
        
//        println(tvStatement.text)
        Statement.writeStatementStatus()
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
