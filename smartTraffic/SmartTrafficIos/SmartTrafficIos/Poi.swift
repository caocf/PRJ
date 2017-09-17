//
//  Poi.swift
//  SmartTrafficIos
//
//  Created by EastMac on 15/5/6.
//  Copyright (c) 2015年 嘉兴市交通运输局. All rights reserved.
//

import Foundation

class Poi:NSObject,CNMKMapViewDelegate,CNMKSearchDelegate,NoteCBDelegate
{
    var search = CNMKSearch()
    var mapView = CNMKMapView()

    var searchText:String
    var poiProtocol:PoiProtocol
    var result:CNMKPoiResult?
    
    init(poiProtocol:PoiProtocol,searchText:String)
    {
        self.poiProtocol = poiProtocol
        self.searchText = searchText
        
    }
    
    
    func POIRectangleSearchCB(FunctionName: String!, id2 DicGetString: CNMKPoiResult!, id3 Error: Int) {
        print("POIRectangleSearchCB")
        if (FunctionName == "getPOIList")
        {
            result = DicGetString
            poiProtocol.updatePoi(self)
        }
    }
    
    func POISearchZoomLevelCB(FunctionName: String!, id2 MaxPpint: CNMKGeoPoint, id3 MinPpint: CNMKGeoPoint, id4 Error: Int) {
        print("POISearchZoomLevelCB")
        
    }
    
    func poiSearch() -> Bool
    {
        search.delegate = self
        mapView.gNoteCBDelegate = self
        let bStatus = search.poiSearchInCity("330400", types: self.searchText, searchType: "all", dataType: "", language: 0, pageCount: 20, pageNumber: 0)
        return bStatus
    }

}

protocol PoiProtocol
{
    func updatePoi(poi:Poi)
}
