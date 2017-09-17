//
//  Weather.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/4/23.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation
import SwiftyJSON

class Weather
{
    let apiUrl = "http://apistore.baidu.com/microservice/weather?citypinyin=jiaxing"
    
    var weatherName:String = ""         //天气情况
    var temp:Double = 0.0               //当前气温
    var h_tmp:Double = 0.0              //最高气温
    var l_tmp:Double = 0.0              //最低气温
    var wd:String = ""                  //风向
    var ws:String = ""                  //风力
    var city:String = ""                //城市名称
    var updateDatetime:NSDate           //更新时间
    
    init(weatherName:String,temp:Double,h_tmp:Double,l_tmp:Double,wd:String,ws:String,city:String,date:String,time:String)
    {
        self.weatherName = weatherName
        self.temp = temp
        self.h_tmp = h_tmp
        self.l_tmp = l_tmp
        self.wd = wd
        self.ws = ws
        self.city = city
        let timeString = "\(date) \(time)"
        let timeFormat = "YYYY-MM-dd HH:mm"
        if let updateTime = Tools.stringConvertToNSDate(timeString, formatString: timeFormat)
        {
            self.updateDatetime = updateTime
        }
        else
        {
            self.updateDatetime = NSDate()
        }
    }
    
    class func initNullWeather() -> Weather
    {
        let nullWeather=Weather(weatherName: "", temp: 0, h_tmp: 0, l_tmp: 0, wd: "", ws: "", city: "", date: "1970-01-01", time: "00:01")
        return nullWeather
    }
    
    
    func initWeather(updateVc:WeatherProtocol)
    {
        if let weatherRequest = HttpTools.getHttpRequest(apiUrl)
        {
            NSURLConnection.sendAsynchronousRequest(weatherRequest, queue: NSOperationQueue(), completionHandler: { (_, data:NSData?, error:NSError?) -> Void in
                if(error == nil)
                {
                    var jsonRet = JSON(data: data!, options: NSJSONReadingOptions.MutableContainers, error: nil)
                    if jsonRet["errNum"].intValue == 0
                    {
                        var jsonWeather = jsonRet["retData"]
                        let weatherName = jsonWeather["weather"].stringValue
                        let temp = jsonWeather["temp"].doubleValue
                        let h_tmp = jsonWeather["h_tmp"].doubleValue
                        let l_tmp = jsonWeather["l_tmp"].doubleValue
                        let wd = jsonWeather["WD"].stringValue
                        let ws = jsonWeather["WS"].stringValue
                        let city = jsonWeather["city"].stringValue
                        let date = jsonWeather["date"].stringValue
                        let time = jsonWeather["time"].stringValue
                        
                        let weather = Weather(weatherName: weatherName, temp: temp, h_tmp: h_tmp, l_tmp: l_tmp, wd: wd, ws: ws, city: city, date: date, time: time)
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                            updateVc.updateWeather(weather)
                        })
                        
                    }
                }
                else
                {
                    dispatch_async(dispatch_get_main_queue(), { () -> Void in
                        updateVc.getWeatherError(error!.localizedDescription)//updateVc.updateWeather(nil)
                    })
                    print(error!.localizedDescription)
                }
            })
        }
    }

    
    
}

protocol WeatherProtocol
{
    func getWeatherError(error:String)
    func updateWeather(updateWeather:Weather)
}