//
//  testViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/26.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class testViewController: UIViewController,mapChooseDelegate{

    
//    
//    var search = CNMKSearch()
//    var mapView = CNMKMapView()
//
//    func getSDKDrintMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKDrintMsgCB")
//    }
//
//    func getSDKMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKMsgCB")
//
//    }
//
//    func getSDKAllProvinceListMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKAllProvinceListMsgCB")
//
//    }
//    
//    func getSDKAddressMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKAddressMsgCB")
//
//    }
//    
//    func getSDKAdminbypointMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKAdminbypointMsgCB")
//
//    }
//    
//    func getSDKAReverSegeocodMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKAReverSegeocodMsgCB")
//
//    
//    }
//    
//    func getSDKPoiaroundMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKPoiaroundMsgCB")
//
//    }

//    func POIRectangleSearchCB(FunctionName: String!, id2 DicGetString: CNMKPoiResult!, id3 Error: Int) {
//        println("POIRectangleSearchCB")
//        if (FunctionName == "getPOIList")
//        {
//            println("总条数:\(DicGetString.totalpage)")
//            for poiTemp in DicGetString.pois as! [CNMKPoiInfo]
//            {
//                println("name=\(poiTemp.name)")
//                println("pid=\(poiTemp.pid)")
//                println("longitude=\(poiTemp.coordinate.longitude)")
//                println("latitude=\(poiTemp.coordinate.latitude)")
//                println("addr=\(poiTemp.addr)")
//                println("adcode=\(poiTemp.adcode)")
//                println("dataType=\(poiTemp.dataType)")
//                println("resultType=\(poiTemp.resultType)")
//                println("dist=\(poiTemp.dist)")
//                println("---------------------------------")
//            }
//        }
//    
//    }
//    
//    func POISearchZoomLevelCB(FunctionName: String!, id2 MaxPpint: CNMKGeoPoint, id3 MinPpint: CNMKGeoPoint, id4 Error: Int) {
//        println("POISearchZoomLevelCB")
//
//    }
//
//    func getSDKProvinceListMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKProvinceListMsgCB")
//
//
//    }
//    
//
//    func getSDKCityListMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKCityListMsgCB")
//
//    }
//    
//    
//
//    func getSDKAreaListMsgCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKAreaListMsgCB")
//
//
//    }
//
//    func handleMapViewLongPressedCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("handleMapViewLongPressedCB")
//
//
//    }
//
//    func pinIconClickedCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("pinIconClickedCB")
//
//        
//    }
//    
//    func directionIconClickedCB(FunctionName: String!, id2 DicGetString: String!, id3 Error: Int) {
//        println("directionIconClickedCB")
//
//        
//    }
//
//
//    func getSDKPoiMessageCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKPoiMessageCB")
//
//        
//    }
//
//    func getSDKTrackMessageCB(FunctionName: String!, id2 DicGetString: NSMutableDictionary!, id3 Error: Int) {
//        println("getSDKTrackMessageCB")
//
//        
//    }
//    
//    func getSDKDrawPointsCB(FunctionName: String!, id2 PointsGPS: UnsafeMutablePointer<CNMKGeoPoint>, id3 PointsCount: Int) {
//        println("getSDKDrawPointsCB")
//
//        
//    }
    
//    func updatePoi(poi: Poi) {
//        println("1总条数:\(poi.result?.totalpage)")
//        for poiTemp in poi.result?.pois as! [CNMKPoiInfo]
//        {
//            println("name=\(poiTemp.name)")
//            println("pid=\(poiTemp.pid)")
//            println("longitude=\(poiTemp.coordinate.longitude)")
//            println("latitude=\(poiTemp.coordinate.latitude)")
//            println("addr=\(poiTemp.addr)")
//            println("adcode=\(poiTemp.adcode)")
//            println("dataType=\(poiTemp.dataType)")
//            println("resultType=\(poiTemp.resultType)")
//            println("dist=\(poiTemp.dist)")
//            println("1---------------------------------")
//        }
//
//    }
//    
//    func updateError(error: String) {
//        println(error)
//    }
//    
//    func updateRegisterPhone(success: Bool, userIsExist: Bool) {
//        println("success:\(success),userIsExist:\(userIsExist)")
//    }
//    
//    func updateReSendVerifyCode(success: Bool) {
//        
//    }
//    
//    func updateSaveUserInfo(success: Bool, saveUser: User) {
//        
//    }
//    
//    func updateRegisterPhoneVerify(success: Bool) {
//        
//    }
//    
//    func updateEditPassword(success: Bool) {
//        
//    }
//    
//    func updateLogin(success: Bool, loginUser: User) {
//        
//    }
    
    func updateChooseInfo(selectPoint: CNMKGeoPoint) {
        print("selectPoint lat:\(selectPoint.latitude) lon:\(selectPoint.longitude)")
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowTest":
                let vc = segue.destinationViewController as! mapChooseViewController
                vc.vcMapChoose = (sender as! mapChooseDelegate)
                break
            default:
                break
                
            }
        }
    }
    
    @IBAction func btnStart(sender: UIButton) {
    
    
//        self.performSegueWithIdentifier("segueShowTest", sender: self)
        
//        var poi = Poi(poiProtocol: self, searchText: "交通大楼")
//        println("send poi Search \(poi.poiSearch())")
//        var aa = search.poiTypeList() as NSArray
//        for a in aa
//        {
//            println("\(a)")
//        }
//        
//        //search.poiCityList()
//        var bStatus = search.poiSearchInCity("330400", types: "交通大楼", searchType: "all", dataType: "", language: 0, pageCount: 99999, pageNumber: 0)
//        println("\(bStatus)")
        
//        BusLocation.initBusLocation(self, lineID: 42, direct: 2, count: BusLocation.defaultCount, stationID: 658)
        //BusCoordinate.initBusCoordinate(self, lineID: 2, direct: 1)
        
//        BusStation.initBusStation(self, stationID: 659)
        //BusLine.initBusLine(self, lineID: 2, direct: 1)
        //BusStation.initBusStation(self, longtitude: 120.749, lantitude: 30.758, distance: 500)
        //BusStation.initBusStation(self, stationName:"三水湾")
        //BusLine.initBusLine(self, lineName: "9")
//        var bike = PublicBike.initNullPublicBike()
//        //var ac = bike.getActionUrl("南湖")
//        //var ac = bike.getActionUrl(120, lantitude: 30, distance: 999999999, count: 999)
//        var ac = bike.getActionUrl(1003)
//        bike.initPublicBike(self, actionUrl: ac.0, httpBody: ac.1)
        
        
        
//        ifView.setParameter("iat", forKey:IFlySpeechConstant.IFLY_DOMAIN())
//       
//        ifView.setParameter("plain", forKey: IFlySpeechConstant.RESULT_TYPE())
//
//        ifView.start()
//        
//        println("start listenning...")
//        [_iflyRecognizerView setParameter: @"iat" forKey:[IFlySpeechConstant IFLY_DOMAIN]];
//        
//        //设置结果数据格式，可设置为json，xml，plain，默认为json。
//        [_iflyRecognizerView setParameter:@"plain" forKey:[IFlySpeechConstant RESULT_TYPE]];
//        
//        [_iflyRecognizerView start];
//        
//        NSLog(@"start listenning...");

    }
    @IBOutlet weak var tvValue:UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
//        popView = [[PopupView alloc] initWithFrame:CGRectMake(100, 300, 0, 0)];
//        _popView.ParentView = self.view;
//        
//        //创建语音听写的对象
//        self.iflyRecognizerView= [[IFlyRecognizerView alloc] initWithCenter:self.view.center];
//        
//        //delegate需要设置，确保delegate回调可以正常返回
//        _iflyRecognizerView.delegate = self;

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewDidDisappear(animated: Bool) {
       
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
