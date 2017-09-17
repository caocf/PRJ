//
//  busPoiViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/11.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busPoiViewController: UIViewController,UITableViewDataSource,UITableViewDelegate ,PoiProtocol{


    var startPoint = addrInput(type: TextType.TextInput)
    var endPoint = addrInput(type: TextType.TextInput)
    
    var poiType = OpenMore.Start
    
    var poiSearch:Poi?
        {
        didSet{
            tvPoint.reloadData()
        }
    }
    
    @IBOutlet weak var unbTitle:UINavigationItem!
    
    @IBOutlet weak var tvPoint:UITableView!
    
    func searchPoi() -> Bool
    {
        var bIsNeedReload = false
        switch (poiType)
        {
        case .Start:
            unbTitle.title = "请选择起点"
            switch(startPoint.type)
            {
            case .MyPosition,.MapChoose,.Station:
                poiType = OpenMore.End
                bIsNeedReload = true
//                searchPoi()
            case .TextInput,.VoiceInput:
                poiType = OpenMore.Start
                poiSearch = Poi(poiProtocol: self, searchText: startPoint.text!)
                poiSearch?.poiSearch()
            }
        case .End:
            unbTitle.title = "请选择终点"
            switch(endPoint.type)
            {
            case .MyPosition,.MapChoose,.Station:
                poiType = OpenMore.None
                bIsNeedReload = true
//                searchPoi()
            case .TextInput,.VoiceInput:
                poiType = OpenMore.End
                poiSearch = Poi(poiProtocol: self, searchText: endPoint.text!)
                poiSearch?.poiSearch()
            }
            break
        case .None:
            self.performSegueWithIdentifier("segueShowTransferResult", sender: nil)
            break
        }
        return bIsNeedReload
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tvPoint.dataSource = self
        tvPoint.delegate = self
        
        while(searchPoi())
        {
        
        }
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowTransferResult":
                let vc = segue.destinationViewController as! busTransferResultViewController
                vc.startPoint = startPoint
                vc.endPoint = endPoint
                break
            default:
                break
                
            }
        }
        
    }

    
    func updatePoi(poi: Poi) {
        self.poiSearch = poi
    }
    
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let poi = poiSearch, let res = poi.result
        {
            return res.totalpage
        }
        else
        {
            return 0
        }
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("poiSelectCell", forIndexPath: indexPath) 
        if let poi = poiSearch, let res = poi.result
        {
            if res.pois.count > indexPath.row
            {
                cell.textLabel?.text = res.pois[indexPath.row].name
                cell.detailTextLabel?.text = res.pois[indexPath.row].addr
            }
            return cell
        }
        else
        {
            return cell
        }
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        switch (poiType)
        {
        case .Start:
            if let poi = poiSearch, let res = poi.result
            {
                if res.pois.count > indexPath.row
                {
                    let poiItem = res.pois[indexPath.row] as! CNMKPoiInfo
                    startPoint.point = CLLocationCoordinate2DMake(poiItem.coordinate.latitude, poiItem.coordinate.longitude)
                }
            }
            poiType = OpenMore.End
        case .End:
            if let poi = poiSearch, let res = poi.result
            {
                if res.pois.count > indexPath.row
                {
                    let poiItem = res.pois[indexPath.row] as! CNMKPoiInfo
                    endPoint.point = CLLocationCoordinate2DMake(poiItem.coordinate.latitude, poiItem.coordinate.longitude)
                }
            }
            poiType = OpenMore.None
        default:
            break
        }
        while(searchPoi())
        {
            
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
