//
//  newsViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/13.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

enum newsType:Int
{
    case Merge = 0
    case Tzxx = 1
    case TrafficControl = 2
    case TrafficAccident = 3
    case BusChange = 4

}


class newsViewController: UIViewController,UITableViewDataSource,UITableViewDelegate
{
    var MergeList = [MergedConRtic]()           //拥堵路段列表
    var TzxxList = [Tzxx]()                     //通阻信息列表
    var JtgzList = [Jtgz]()                     //交通管制列表

    var iSelectNewsType = newsType.Merge    //当前选择的新闻类型
    
    @IBOutlet weak var newTableView: UITableView!
    @IBOutlet weak var aivLoading: UIActivityIndicatorView!
    @IBAction func newsSegment(sender: UISegmentedControl)
    {
        iSelectNewsType = newsType(rawValue: sender.selectedSegmentIndex)!
        switch iSelectNewsType
        {
        case .Merge:
            initMergedConRtic()
        case .Tzxx:
            initTzxx()
        case .TrafficAccident:
            newTableView.reloadData()
            break
        case .TrafficControl:
            initJtgz()
            break
        case .BusChange:
            break
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        newTableView.dataSource = self
        newTableView.delegate = self
        initMergedConRtic()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch iSelectNewsType
        {
        case .Merge:
            return MergeList.count
        case .Tzxx:
            return TzxxList.count
        case .TrafficAccident:
            return 0
        case .TrafficControl:
            return JtgzList.count
        case .BusChange:
            return 0
        }
    }
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
       
        return 1
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("newsCell", forIndexPath: indexPath) 
        switch iSelectNewsType
        {
        case .Merge:
           cell.textLabel?.text = "\(MergeList[indexPath.row].roadName):\(MergeList[indexPath.row].rsStart)—\(MergeList[indexPath.row].rsEnd)   \(MergeList[indexPath.row].desc)"
        case .Tzxx:
            cell.textLabel?.text = "\(TzxxList[indexPath.row].BT)"
        case .TrafficAccident:
            break
        case .TrafficControl:
            cell.textLabel?.text = "\(JtgzList[indexPath.row].BT)"
            break
        case .BusChange:
            break
        }

        
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        switch iSelectNewsType
        {
        case .Tzxx:
            self.performSegueWithIdentifier("segueShowNewsDetail", sender: TzxxList[indexPath.row])
        case .TrafficAccident:
            break
        case .TrafficControl:
            self.performSegueWithIdentifier("segueShowNewsDetail", sender: JtgzList[indexPath.row])
            break
        case .BusChange:
            break
        default:
            break
        }

    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowNewsDetail":
                let vc = segue.destinationViewController as! newsDetailViewController
                switch iSelectNewsType
                {
                case .Tzxx:
                    vc.tzxxDetail = (sender as! Tzxx)
                case .TrafficControl:
                    vc.jtgzDetail = (sender as! Jtgz)
                default:
                    break
                }
                vc.iSelectNewsType = self.iSelectNewsType
                break
            default:
                break
                
            }
        }

    }
    
    @IBAction func newsDetailClose(segue: UIStoryboardSegue)
    {
        print("close newsDetailClose")
    }
    
    func initMergedConRtic()
    {
        if let MergeUrl = HttpTools.getHttpRequest(MergedConRtic.getActionUrl())
        {
            aivLoading.startAnimating()
            var bIsSuccess = false
            var sErrorStr = ""
            NSURLConnection.sendAsynchronousRequest(MergeUrl, queue: NSOperationQueue(), completionHandler: { (response:NSURLResponse?, data:NSData?, error:NSError?) -> Void in
                if (error != nil)
                {
                    sErrorStr = error!.localizedDescription
                }
                else
                {
                    let Merge = MergedConRtic.initMergedConRticData(data!)
                    if Merge.0      //返回成功
                    {
                        bIsSuccess = true
                        self.MergeList = Merge.2!
                    }
                    else
                    {
                        sErrorStr = Merge.1!
                    }
                }
                dispatch_async(dispatch_get_main_queue(), { () -> Void in
                    self.newTableView.reloadData()
                    self.aivLoading.stopAnimating()
                    if !bIsSuccess
                    {
                        let alertInfo = UIAlertView(title: "获取失败", message: sErrorStr, delegate: nil, cancelButtonTitle: "关闭")
                        alertInfo.show()
                    }
                })
                
            })
            
        }
        else
        {
            print("getRequest error")
            let alertInfo = UIAlertView(title: "获取失败", message: "获取接口地址错误!", delegate: nil, cancelButtonTitle: "关闭")
            alertInfo.show()
        }
    }

    func initTzxx()
    {
        let lastTime:NSDate = NSDate(timeIntervalSinceNow: -30*24*60*60)         //获取最近30天的信息
        if let tzxxUrl = HttpTools.getHttpRequest(Tzxx.getActionUrl(lastTime))
        {
            aivLoading.startAnimating()
            var bIsSuccess = false
            var sErrorStr = ""
            NSURLConnection.sendAsynchronousRequest(tzxxUrl, queue: NSOperationQueue(), completionHandler: { (response:NSURLResponse?, data:NSData?, error:NSError?) -> Void in
                if (error != nil)
                {
                    sErrorStr = error!.localizedDescription
                }
                else
                {
                    let tzxxlist = Tzxx.initTzxxData(data!)
                    self.TzxxList = tzxxlist
                    bIsSuccess = true
                }
                dispatch_async(dispatch_get_main_queue(), { () -> Void in
                    self.newTableView.reloadData()
                    self.aivLoading.stopAnimating()
                    if !bIsSuccess
                    {
                        let alertInfo = UIAlertView(title: "获取失败", message: sErrorStr, delegate: nil, cancelButtonTitle: "关闭")
                        alertInfo.show()
                    }
                })

            })
        }
        else
        {
            print("getRequest error")
            let alertInfo = UIAlertView(title: "获取失败", message: "获取接口地址错误!", delegate: nil, cancelButtonTitle: "关闭")
            alertInfo.show()
        }
    }

    
    func initJtgz()
    {
        if let jtgzUrl = HttpTools.getHttpRequest(Jtgz.getActionUrl())
        {
            aivLoading.startAnimating()
            var bIsSuccess = false
            var sErrorStr = ""
            NSURLConnection.sendAsynchronousRequest(jtgzUrl, queue: NSOperationQueue(), completionHandler: { (response:NSURLResponse?, data:NSData?, error:NSError?) -> Void in
                if (error != nil)
                {
                    sErrorStr = error!.localizedDescription
                }
                else
                {
                    let jtgzlist = Jtgz.initJtgzData(data!)
                    self.JtgzList = jtgzlist
                    bIsSuccess = true
                }
                dispatch_async(dispatch_get_main_queue(), { () -> Void in
                    self.newTableView.reloadData()
                    self.aivLoading.stopAnimating()
                    if !bIsSuccess
                    {
                        let alertInfo = UIAlertView(title: "获取失败", message: sErrorStr, delegate: nil, cancelButtonTitle: "关闭")
                        alertInfo.show()
                    }
                })
                
            })
        }
        else
        {
            print("getRequest error")
            let alertInfo = UIAlertView(title: "获取失败", message: "获取接口地址错误!", delegate: nil, cancelButtonTitle: "关闭")
            alertInfo.show()
        }
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
