//
//  driveTextViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/18.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class driveTextViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {

    var driveText:NSDictionary!
    var driveTextDateSource:[String] = []
    @IBOutlet weak var tvText:UITableView!
    @IBAction func ubbReturn(sender: UIBarButtonItem) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        for var i = 0 ; i < driveText.count ; i++
        {
            let idx = "\(i)"
            if let text = driveText[idx] as? String
            {
                driveTextDateSource.append(text)
            }

        }
        
        tvText.dataSource = self
        tvText.delegate = self
        
        tvText.reloadData()
        // Do any additional setup after loading the view.
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return driveTextDateSource.count
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("driveTextCell", forIndexPath: indexPath) 
        cell.textLabel?.text = "\(indexPath.row + 1).\(driveTextDateSource[indexPath.row])"
        return cell
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
