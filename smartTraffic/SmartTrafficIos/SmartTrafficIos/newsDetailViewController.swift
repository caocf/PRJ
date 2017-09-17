//
//  newsDetailViewController.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/15.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import UIKit

class newsDetailViewController: UIViewController {
    
    var jtgzDetail:Jtgz?
    var tzxxDetail:Tzxx?
    var iSelectNewsType = newsType.Tzxx
    
    @IBOutlet weak var scrollViewHeight: NSLayoutConstraint!
    @IBOutlet weak var labNewsTitle: UILabel!
    @IBOutlet weak var labAttributeLeft: UILabel!
    @IBOutlet weak var labAttributeRight: UILabel!
    @IBOutlet weak var txtContent: UITextView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        switch iSelectNewsType
        {
        case .Tzxx:
            if let showTzxx = tzxxDetail
            {
                labNewsTitle.text = showTzxx.BT
                labAttributeLeft.text = showTzxx.FBR
                labAttributeRight.text = Tools.nsdateConvertToString(showTzxx.GXSJ, formatString: "YYYY年MM月dd日 HH:mm")
                let convertNR = try? NSAttributedString(data: showTzxx.NR.dataUsingEncoding(NSUnicodeStringEncoding, allowLossyConversion: true)!, options: [NSDocumentTypeDocumentAttribute : NSHTMLTextDocumentType], documentAttributes: nil)
                txtContent.attributedText = convertNR
                _ = txtContent.layoutManager.usedRectForTextContainer(txtContent.textContainer)
            }
            
        case .BusChange:
            break
        case .TrafficControl:
            if let showJtgz = jtgzDetail
            {
                labNewsTitle.text = showJtgz.BT
                labAttributeLeft.text = showJtgz.FBR
                labAttributeRight.text = showJtgz.GXSJ
                let convertNR = try? NSAttributedString(data: showJtgz.NR.dataUsingEncoding(NSUnicodeStringEncoding, allowLossyConversion: true)!, options: [NSDocumentTypeDocumentAttribute : NSHTMLTextDocumentType], documentAttributes: nil)
                txtContent.attributedText = convertNR
                _ = txtContent.layoutManager.usedRectForTextContainer(txtContent.textContainer)
            }

        default:
            break
        }

        // Do any additional setup after loading the view.
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
