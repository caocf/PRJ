//
//  trafficTabViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/7/8.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class trafficTabViewController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
//    override func shouldAutorotateToInterfaceOrientation(toInterfaceOrientation: UIInterfaceOrientation) -> Bool {
//        return true
//    }
    
    override func shouldAutorotate() -> Bool {
//        println("\(self.selectedIndex)")
//        if self.selectedIndex == 1
//        {
//            return true
//        }
//        else
//        {
//            return false
//        }
        return true
    }
    
    override func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        if self.selectedIndex == 1
        {
            return UIInterfaceOrientationMask.Landscape
        }
        else
        {
            return UIInterfaceOrientationMask.Portrait
        }
    }
    
    override func preferredInterfaceOrientationForPresentation() -> UIInterfaceOrientation {
        if self.selectedIndex == 1
        {
            return UIInterfaceOrientation.LandscapeLeft
        }
        else
        {
            return UIInterfaceOrientation.Portrait
        }
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
