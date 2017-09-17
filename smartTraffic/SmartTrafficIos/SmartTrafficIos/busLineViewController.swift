//
//  busLineViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/3.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class busLineViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,BusProtocol,UISearchBarDelegate,IFlyRecognizerViewDelegate {

    var avErrorInfo:UIAlertView!
    var poView:PopupView!
    var ifView:IFlyRecognizerView!
    var bHistory:Bool = true
    @IBOutlet weak var tvLine:UITableView!
    @IBOutlet weak var usbLineSearch:UISearchBar!
    @IBOutlet weak var aivLoading:UIActivityIndicatorView!
    @IBOutlet weak var vMoreButton:UIView!
    
    var busLine:[ShowBusLine] = []
        {
        didSet{
            tvLine.reloadData()
        }
    }
    var busLineTitle = "历史记录"
    
    var history:[String] = []
        {
        didSet{
            tvLine.reloadData()
        }
    }

    
    @IBAction func btnFavorite(sender: UIButton) {
        vMoreButton.hidden = true
        if let _ = LoginUser.user
        {
            self.performSegueWithIdentifier("segueLineToFavor", sender: nil)
        }
        else
        {
            avErrorInfo = UIAlertView(title: "打开收藏错误", message: "您尚未登录，请登陆后再打开收藏夹", delegate: nil, cancelButtonTitle:"关闭")
            avErrorInfo.show()
        }
    }
    
    
    @IBAction func btnVoice(sender: UIButton) {
        vMoreButton.hidden = true
        usbLineSearch.text = ""
        ifView.setParameter("iat", forKey:IFlySpeechConstant.IFLY_DOMAIN())
        ifView.setParameter("plain", forKey: IFlySpeechConstant.RESULT_TYPE())
        ifView.start()

    }
    
    @IBAction func btnSearch(sender: UIButton) {
        vMoreButton.hidden = true

        searchBarSearchButtonClicked(usbLineSearch)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        poView = PopupView(frame: CGRectMake(100, 300, 0, 0))
        poView.ParentView = self.view
        
        self.ifView = IFlyRecognizerView(center: self.view.center)
        ifView.delegate = self
        
        tvLine.dataSource = self
        tvLine.delegate = self
        usbLineSearch.delegate = self
        // Do any additional setup after loading the view.
        bHistory = true
        if let his = History.readHistory("\(History.HistoryType.BusLine.rawValue)")
        {
            history = his
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if bHistory
        {
            if history.count > 0
            {
                return history.count + 1
            }
            else
            {
                return 0
            }
        }
        else
        {
            return busLine.count
        }
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        if bHistory
        {
            let cell = tableView.dequeueReusableCellWithIdentifier("historyCell", forIndexPath: indexPath) 
            if history.count > 0
            {
                if (indexPath.row == history.count)
                {
                    cell.textLabel?.text = "清除历史记录"
                    cell.textLabel?.textAlignment = NSTextAlignment.Center
                    cell.imageView?.image = nil
                }
                else
                {
                    cell.textLabel?.textAlignment = NSTextAlignment.Left
                    cell.textLabel?.text = history[indexPath.row]
                    cell.imageView?.image = UIImage(named: "ic_history")
                }
            }
            return cell
        }
        else
        {
            let cell = tableView.dequeueReusableCellWithIdentifier("lineCell", forIndexPath: indexPath) 
            
            let busLineIndex = busLine[indexPath.row]
            //        cell.imageView?.image = UIImage(named: "")
            cell.textLabel?.text = "\(busLineIndex.Name)"
            cell.detailTextLabel?.text = "\(busLineIndex.StartName)→\(busLineIndex.EndName)"
            
            return cell
        }
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: false)
        if bHistory
        {
            if (indexPath.row < history.count)
            {
                usbLineSearch.text = history[indexPath.row]
                bHistory = false
                searchBar(usbLineSearch, textDidChange: usbLineSearch.text!)
            }
            else
            {
                history.removeAll()
                History.writeHistory("\(History.HistoryType.BusLine.rawValue)", history: history)
            }
        }
        else
        {
            if (indexPath.row < busLine.count)
            {
                if (!Tools.checkIsExist(history, item: busLine[indexPath.row].Name))
                {
                    history.insert(busLine[indexPath.row].Name, atIndex: 0)
                    History.writeHistory("\(History.HistoryType.BusLine.rawValue)", history: history)
                }
                self.performSegueWithIdentifier("segueShowLineDetail", sender: busLine[indexPath.row])
            }
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let segueIdentifier  = segue.identifier
        {
            switch segueIdentifier
            {
            case "segueShowLineDetail":
                let vc = segue.destinationViewController as! busLineDetailViewController
                vc.showBusLine = (sender as! ShowBusLine)
                break
            case "segueLineToFavor":
                let vc = segue.destinationViewController as! busFavorViewController
                vc.iFavorType = 0
            default:
                break
                
            }
        }
        
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        if bHistory
        {
            return 50
        }
        else
        {
            return 60
        }

    }

    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return busLineTitle
    }
    
    func updateBusLocation(busLocation: BusLocation) {
        
    }
    
    func updateBusCoordinate(busCoordinate: BusCoordinate) {
        
    }
    
    func updateBusLineList(busLineList: [BusLine]) {
        aivLoading.stopAnimating()
        self.busLine.removeAll()
        if busLineList.count > 0
        {
            for busLineTemp in busLineList
            {
                if busLineTemp.IsRing
                {
                    let showBusLine = ShowBusLine(Id: busLineTemp.Id, Name: busLineTemp.Name, StartName: busLineTemp.UpStartStationName, EndName: busLineTemp.UpEndStationName, Direct: BusLine.upLine,IsRing: busLineTemp.IsRing)
                    self.busLine.append(showBusLine)
                }
                else
                {
                    let showBusLineUp = ShowBusLine(Id: busLineTemp.Id, Name: busLineTemp.Name, StartName: busLineTemp.UpStartStationName, EndName: busLineTemp.UpEndStationName, Direct: BusLine.upLine,IsRing: busLineTemp.IsRing)
                    self.busLine.append(showBusLineUp)
                    let showBusLineDown = ShowBusLine(Id: busLineTemp.Id, Name: busLineTemp.Name, StartName: busLineTemp.DownStartStationName, EndName: busLineTemp.DownEndStationName , Direct: BusLine.downLine,IsRing: busLineTemp.IsRing)
                    self.busLine.append(showBusLineDown)
                }
            }
        }
        else
        {
            avErrorInfo = UIAlertView(title: "获取公交线路失败", message: "没有找到符合要求的停车点信息", delegate: nil, cancelButtonTitle: "关闭")
            avErrorInfo.show()
        }
        //self.busLine = busLineList
        //tvLine.reloadData()
    }
    
    func updateBusStationList(busStationList: [BusStation]) {
        
    }
    
    func updateBusInfoError(error: String) {
        aivLoading.stopAnimating()
        avErrorInfo = UIAlertView(title: "获取公交线路失败", message: error, delegate: nil, cancelButtonTitle: "关闭")
        avErrorInfo.show()
        print(error)
    }
    
    func searchBarResultsListButtonClicked(searchBar: UISearchBar) {
        vMoreButton.hidden = !vMoreButton.hidden
    }
    
    func searchBarSearchButtonClicked(searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        vMoreButton.hidden = true
        BusLine.initBusLine(self, lineName: searchBar.text!)
        aivLoading.startAnimating()
    }
    
    func searchBarShouldBeginEditing(searchBar: UISearchBar) -> Bool {
        vMoreButton.hidden = true
        return true
    }
    
    func searchBar(searchBar: UISearchBar, textDidChange searchText: String) {
        vMoreButton.hidden = true
        if searchText == ""
        {
            bHistory = true
            if let his = History.readHistory("\(History.HistoryType.BusLine.rawValue)")
            {
                history = his
            }
            else
            {
                tvLine.reloadData()
            }
        }
        else
        {
            bHistory = false
            aivLoading.startAnimating()
            BusLine.initBusLine(self, lineName: searchText)
            busLineTitle = "已为您找到关键字为\"\(searchText)\"的线路"
        }
    }
    
    override func viewWillDisappear(animated: Bool) {
        //终止识别
        ifView.cancel()
        ifView.delegate = nil
        super.viewDidDisappear(animated)
    }
    
    func onError(error: IFlySpeechError!) {
        
        self.view.addSubview(poView)
        poView.setText("识别结束")
        print("errorCode:\(error.errorCode())")
        if (usbLineSearch.text != "" && error.errorCode() == 0)
        {
            searchBarSearchButtonClicked(usbLineSearch)
        }
        
    }
    
    func onResult(resultArray: [AnyObject]!, isLast: Bool) {
        var retStr = ""
        let result = resultArray as NSArray
        if let dic = result[0] as? NSDictionary
        {
            for (key,_) in dic
            {
                retStr += "\(key)"
            }
            usbLineSearch.text = "\(usbLineSearch.text)\(retStr)"
        }
        
    }

    
    @IBAction func closeToLine(segue: UIStoryboardSegue)
    {
        
        print("closeToLine")
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
