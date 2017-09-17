//
//  bikeViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/24.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit
import Foundation
import AVFoundation
class bikeViewController: UIViewController,AVCaptureMetadataOutputObjectsDelegate {

//    @IBOutlet weak var vMapView:UIView!
    
//    var mapView:CNMKMapView!
//    var GScreenWidth:CGFloat = 0.0
//    var GScreenHeight:CGFloat = 0.0
//    var gSearch:CNMKSearch!
//    var startPoint:CNMKAnnotation!
//    var endPoint:CNMKAnnotation!

    let device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
    let session = AVCaptureSession()
    var layer: AVCaptureVideoPreviewLayer?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "二维码扫描"
        self.view.backgroundColor = UIColor.grayColor()
        let labIntroudction = UILabel(frame:CGRectMake(15, 80, 290, 50))
        labIntroudction.backgroundColor = UIColor.clearColor()
        labIntroudction.numberOfLines = 2
        labIntroudction.textColor = UIColor.whiteColor()
        labIntroudction.text = "将二维码图像置于矩形方框内，离手机摄像头10CM左右，系统会自动识别。"
        self.view.addSubview(labIntroudction)
        let imageView = UIImageView(frame:CGRectMake(10, 140, 300, 300))
        imageView.image = UIImage(named:"pick_bg")
        self.view.addSubview(imageView)

        //        mapView.delegate = self
//        mapView.gNoteCBDelegate = self
//        gSearch = CNMKSearch()
//        gSearch.delegate = self
//        self.view.addSubview(mapView)
//        self.view.sendSubviewToBack(mapView)
//        
//        mapView.showsUserLocation = true
//        mapView.displayZoomControls(true, scaleX: 140, scaleY: 740, fontSize: 14, scaleLineWidth: 2)
//        
//        mapView.setCenterCoordinate(CNMKGeoPointMake(120.749, 30.758), animated: true)
//        
//        var geoStartPoint = CNMKGeoPoint(longitude: 120.749, latitude: 30.758)
//        var geoEndPoint = CNMKGeoPoint(longitude: 120.743617444662, latitude: 30.7721872489838)
//        startPoint = CNMKAnnotation.annotationWithCoordinate(geoStartPoint) as! CNMKAnnotation
//        startPoint.title1 = "起始点"
//        startPoint.tag = 111
//        mapView.addAnnotation(startPoint, viewHande: nil, customIcon: nil)
//        
//        endPoint = CNMKAnnotation.annotationWithCoordinate(geoEndPoint) as! CNMKAnnotation
//        endPoint.title1 = "终止点"
//        endPoint.tag = 222
//        mapView.addAnnotation(endPoint, viewHande: nil, customIcon: nil)
//
//        
//        if let his = History.readHistory("Parking")
//        {
//            for h in his
//            {
//                println(h)
//            }
//        }
//
        // Do any additional setup after loading the view.
    }
    
//    func mapView(mapView: CNMKMapView!, viewForAnnotation annotation: CNMKAnnotationOverlay!) -> CNMKAnnotationView! {
//        if (annotation is CNMKAnnotation)
//        {
//            var pinView = CNMKPinView(overlay: annotation)
//            pinView.pinColor = CNMKPinColorGreen
//            return pinView
//        }
//        return nil
//    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
        layer!.frame = CGRectMake(20,150,280,280);
        self.view.layer.insertSublayer(self.layer!, atIndex: 0)
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
        
        //        let alertController = UIAlertController(title: "二维码", message: "扫到的二维码内容为:\(stringValue)", preferredStyle: UIAlertControllerStyle.Alert)
        //        alertController.addAction(UIAlertAction(title: "确认", style: UIAlertActionStyle.Default, handler: nil))
        //        self.presentViewController(alertController, animated: true, completion: nil)
        let alertView = UIAlertView()
        alertView.delegate=self
        alertView.title = "二维码"
        alertView.message = "扫到的二维码内容为:\(stringValue)"
        alertView.addButtonWithTitle("确认")
        alertView.show()
    }

    
    @IBAction func ubbSearch(sender: UIBarButtonItem) {
//        gSearch.drivingSearch(startPoint.coordinate, endPoint: endPoint.coordinate, iCostModel: 0, iCriterial: 0, language: 1, bear: -1, vehiclespeed: -1, directionIconStatus: 1)
//        mapView.removeAnnotation(startPoint)
//        mapView.removeAnnotation(endPoint)
//        if (History.writeSaveUser("Parking", history: ["123","321","13123"]))
//        {
//            println("writeSaveUser true")
//        }
//        else
//        {
//            println("writeSaveUser false")
//
//        }
//        
//        StationFavor.APPSaveStationFavor(self, userid: 11, stationID: 234)
//        StationFavor.APPQueryAllStationFavor(self, userid: 11)
    }
    
//    func updateAPPDeleteForStation(status: Int, msg: String) {
//        
//    }
//
//    func getSDKDrintMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        var tempDoc:NSMutableDictionary
//        if (FunctionName == "getDringList")
//        {
//            tempDoc = DicGetString
//            for var i = 0 ; i < tempDoc.count ; i++
//            {
//                var idx = NSString(format: "%d", i)
//                println("\(i+1):\(tempDoc.valueForKey(idx as String))")
//            }
//        }
//    }
//    
//    func getSDKDrawPointsCB(FunctionName: String!, id2 PointsGPS: UnsafeMutablePointer<CNMKGeoPoint>, id3 PointsCount: Int) {
//        if (FunctionName == "getDrawPointList")
//        {
//            for var i = 0 ; i < PointsCount ; i++
//            {
//                println("lat=\(PointsGPS[i].latitude),lon=\(PointsGPS[i].longitude)")
//            }
//            
//            var polyLine = CNMKPolyline.polylineWithGeoPoints(PointsGPS, count: UInt(PointsCount)) as! CNMKPolyline
//            
//            mapView.driveLineDraw(polyLine, lineWidth: 6, colorR: 44.0/255.0, colorG: 102.0/255.0, colorB: 233.0/255.0, alpha: 1.0)
//        
//        }
//    }

    override func viewWillDisappear(animated: Bool) {
//        mapView.delegate = nil
//        mapView.gNoteCBDelegate = nil
//        gSearch.delegate = nil
    }
    
    
//    func updateAPPSaveStationFavor(status: Int, msg: String) {
//        println("status:\(status),msg:\(msg)")
//    }
//    
//    func updateStationError(error: String) {
//        println(error)
//    }
//    
//    func updateAPPQueryAllStationFavor(status: Int, msg: String, stationFavor: [StationFavor]) {
//        for i in stationFavor
//        {
//            println("id:\(i.id),stationId:\(i.stationID)")
//        }
//    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
