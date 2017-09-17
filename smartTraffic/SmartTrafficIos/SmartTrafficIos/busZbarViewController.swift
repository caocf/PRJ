//
//  busZbarViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/28.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit
import Foundation
import AVFoundation
class busZbarViewController: UIViewController,AVCaptureMetadataOutputObjectsDelegate,BusProtocol,QRCodeDelegate,UIAlertViewDelegate {

    @IBOutlet weak var ivZbar:UIImageView!
    var avErrorInfo:UIAlertView!
    let device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
    let session = AVCaptureSession()
    var layer: AVCaptureVideoPreviewLayer?

    override func viewDidLoad() {
        super.viewDidLoad()

        self.title = "二维码扫描"
      

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func ubbReturn(sender: UIBarButtonItem) {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        self.setupCamera()
        self.session.startRunning()
    }
    
    func setupCamera(){
        self.session.sessionPreset = AVCaptureSessionPresetHigh
        var error : NSError?
        let input: AVCaptureDeviceInput!
        do {
            input = try AVCaptureDeviceInput(device: device)
        } catch let error1 as NSError {
            error = error1
            input = nil
        }
        if (error != nil) {
            print(error!.description)
            return
        }
        if session.canAddInput(input) {
            session.addInput(input)
        }
        layer = AVCaptureVideoPreviewLayer(session: session)
        layer!.videoGravity = AVLayerVideoGravityResizeAspectFill
//        layer!.frame = CGRectMake(20,100,280,280)
        layer!.frame = CGRectMake(0,0,280,280);

        self.ivZbar.layer.insertSublayer(self.layer!, atIndex: 0)
//        self.view.layer.insertSublayer(self.layer, atIndex: 0)
        let output = AVCaptureMetadataOutput()
        output.setMetadataObjectsDelegate(self, queue: dispatch_get_main_queue())
        if session.canAddOutput(output) {
            session.addOutput(output)
            output.metadataObjectTypes = [AVMetadataObjectTypeQRCode];
        }
        
        session.startRunning()
    }
    
    func captureOutput(captureOutput: AVCaptureOutput!, didOutputMetadataObjects metadataObjects: [AnyObject]!, fromConnection connection: AVCaptureConnection!){
        var stringValue:String?
        if metadataObjects.count > 0 {
            let metadataObject = metadataObjects[0] as! AVMetadataMachineReadableCodeObject
            stringValue = metadataObject.stringValue
        }
        self.session.stopRunning()
        print("code is \(stringValue)")
        if let scanStr = stringValue
        {
            _ = scanStr.lengthOfBytesUsingEncoding(NSUTF8StringEncoding)
            let index = scanStr.endIndex.advancedBy(-11)
            let QRCodeStr = scanStr.substringFromIndex(index)
            QRCode.getBusIDForQRCode(self, QRCodeStr: QRCodeStr)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "扫描错误", message: "扫描二维码错误", delegate: self, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
        
//        QRCode.getBusIDForQRCode(self, QRCodeStr: st)
        //        let alertController = UIAlertController(title: "二维码", message: "扫到的二维码内容为:\(stringValue)", preferredStyle: UIAlertControllerStyle.Alert)
        //        alertController.addAction(UIAlertAction(title: "确认", style: UIAlertActionStyle.Default, handler: nil))
        //        self.presentViewController(alertController, animated: true, completion: nil)
//        var alertView = UIAlertView()
//        alertView.delegate=self
//        alertView.title = "二维码"
//        alertView.message = "扫到的二维码内容为:\(stringValue)"
//        alertView.addButtonWithTitle("确认")
//        alertView.show()
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        if buttonIndex == 0
        {
            self.session.startRunning()
        }
    }


    func updateBusCoordinate(busCoordinate: BusCoordinate) {
        
    }
    
    func updateBusInfoError(error: String) {
        avErrorInfo = UIAlertView(title: "获取公交站点信息错误", message: error, delegate: self, cancelButtonTitle: "关闭")
        avErrorInfo.show()
    }
    
    func updateBusLineList(busLineList: [BusLine]) {
        
    }
    
    func updateBusLocation(busLocation: BusLocation) {
    
    }
    
    func updateBusStationList(busStationList: [BusStation]) {
        if busStationList.count > 0
        {
            self.performSegueWithIdentifier("segueZBarToStation", sender: busStationList[0])
        }
        else
        {
            avErrorInfo = UIAlertView(title: "扫描失败", message: "没有找到对应的站点。", delegate: self, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    func updateGetBusIDForQRCode(id: Int, msg: String) {
        if id > 0
        {
            BusStation.initBusStation(self, stationID: id)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "扫描错误", message: "扫描二维码错误", delegate: self, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueZBarToStation":
                let vc = segue.destinationViewController as! busStationDetailViewController
                vc.busStation = (sender as! BusStation)
                break
            default:
                break
                
            }
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
