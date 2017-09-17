//
//  busTransferResultViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/11.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busTransferResultViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,TransferProtocol,TransferFavorDelegate {

    let sTitleStr = ["换乘少","时间短","少步行","价格少","路程短"]
    var startPoint = addrInput(type: TextType.TextInput)
    var endPoint = addrInput(type: TextType.TextInput)
    var currentOrder = Transfer.defaultOrder
    var transfer = [Transfer]()
        {
        didSet{
            tvTransferResult.reloadData()
        }
    }
    
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    @IBOutlet weak var labStart:UILabel!
    @IBOutlet weak var labEnd:UILabel!
    @IBOutlet weak var tvTransferResult:UITableView!
    var avErrorInfo:UIAlertView!
    
  
    @IBAction func ubbFavor(sender: UIBarButtonItem) {
        if let user = LoginUser.user
        {
            let start = startPoint.point!
            let end = endPoint.point!
            TransferFavor.APPSaveTransferFavor(self, userid: user.userid, startlan: start.latitude, startlon: start.longitude, endlan: end.latitude, endlon: end.longitude, startname: startPoint.text!, endname: endPoint.text!)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "收藏换乘错误", message: "您尚未登录，请登陆后再收藏站点", delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }

    }
    
    @IBAction func btnChange(sender: UIButton) {
        let pointTemp = startPoint
        startPoint = endPoint
        endPoint = pointTemp
        initTransfer(currentOrder)
    }
    
    @IBAction func btnRefresh(sender: UIButton) {
        initTransfer(currentOrder)
    }
    
    @IBAction func uscType(sender: UISegmentedControl) {
        currentOrder = sender.selectedSegmentIndex + 1
        initTransfer(currentOrder)
    }
    
    func initTransfer(order:Int)
    {
        aivLoading.startAnimating()
        transfer.removeAll()
        labStart.text = startPoint.text
        labEnd.text = endPoint.text
        Transfer.initTransfer(self, startLontitude: startPoint.point!.longitude, startLantitude: startPoint.point!.latitude, endLontitude: endPoint.point!.longitude, endLantitude: endPoint.point!.latitude, order: order, count: Transfer.defaultCount)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tvTransferResult.dataSource = self
        tvTransferResult.delegate = self
        initTransfer(currentOrder)
//        println("start:\(startPoint.point?.longitude),\(startPoint.point?.latitude)")
//        println("end:\(endPoint.point?.longitude),\(endPoint.point?.latitude)")


        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return transfer.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("transferResultCell", forIndexPath: indexPath) 
        let transferItem = transfer[indexPath.row]
        cell.textLabel?.text = "\(transferItem.formatLineListStr())"
        cell.detailTextLabel?.text = "全程约\(transferItem.Time)分钟/\(transferItem.Distance)米/\(transferItem.Price)元"
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        self.performSegueWithIdentifier("segueShowBusTransferResultDetail", sender: transfer[indexPath.row])
    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return sTitleStr[currentOrder - 1]
    }
    
    func updateTransfer(transfer: [Transfer]) {
        aivLoading.stopAnimating()
        self.transfer = transfer
        if self.transfer.isEmpty
        {
            avErrorInfo = UIAlertView(title: "获取方案失败", message: "没有找到符合您要求的换乘方案。", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    func updateTransferError(error: String) {
        aivLoading.stopAnimating()
        avErrorInfo = UIAlertView(title: "获取方案失败", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowBusTransferResultDetail":
                let vc = segue.destinationViewController as! busTransferResultDetailViewController
                vc.transfer = (sender as! Transfer)
                vc.startPoint = startPoint
                vc.endPoint = endPoint
                break
            default:
                break
                
            }
        }
        
    }
    
    func updateAPPDeleteForTransfer(status: Int, msg: String) {
        
    }
    
    func updateAPPQueryAllTransferFavor(status: Int, msg: String, transferFavor: [TransferFavor]) {
        
    }
    
    func updateAPPSaveTransferFavor(status: Int, msg: String) {
        if (status == 1)
        {
            avErrorInfo = UIAlertView(title: "收藏成功", message: "收藏换乘成功", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
        else
        {
            avErrorInfo = UIAlertView(title: "收藏错误", message: msg, delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    func updateTransferFavorError(error: String) {
        avErrorInfo = UIAlertView(title: "收藏换乘错误", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
    }
    
    
    @IBAction func closeToTransferResult(segue: UIStoryboardSegue)
    {
        
        print("closeToTransferResult")
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
