package com.zjport.uniformInterface;

import com.nci.data.DataDriver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by TWQ on 2016/11/24.
 */
@Controller
@RequestMapping("/ZJGH_JK/SPI")
public class PerceptionService {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DataDriver getDriver() throws Exception{
        DataDriver driver = null;
        driver = DataDriver.getInstance();
        driver.setHost("10.100.70.101");
        driver.setPort(8090);
        driver.setUser("csp");
        driver.setPwd("password");
        driver.connect();

        return driver;
    }

    //根据船舶名称、AIS9位码或者GPS设备号查询AIS最后位置
    /**
     * 根据船舶名称、ais9位码或者gps设备号进行查询
     * @param cond
     * @return 返回经纬度
     * @throws Exception
     */
    @RequestMapping(value = "/getAisLastPosition")
    @ResponseBody
    public List<Map<String, Object>> getAisLastPosition(String cond) throws Exception {
        DataDriver driver = this.getDriver();
        List<Map<String,Object>> list = driver.getAisLastPosition(cond);
        System.out.println(list);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //根据船舶名称、AIS9位码或者GPS设备号查询GPS最后位置
    /**
     * 根据船舶名称、ais9位码或者gps设备号进行查询
     * @param cond
     * @return 返回经纬度
     * @throws Exception
     */
    @RequestMapping(value = "/getGpsLastPosition")
    @ResponseBody
    public List<Map<String, Object>> getGpsLastPosition(String cond) throws Exception {
        DataDriver driver = this.getDriver();
        List<Map<String,Object>> list = driver.getGpsLastPosition(cond);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //根据AIS编号、开始时间、结束之间查询AIS轨迹
    /**
     * 根据ais编号查询船舶轨迹
     * @param ais
     * @param start
     * @param end
     * @return 返回轨迹列表
     * @throws Exception
     */
    @RequestMapping(value = "/getShipAisTrack")
    @ResponseBody
    public List<Map<String, Object>> getShipAisTrack(String ais,String start,String end) throws Exception {
        Date beginTime = sdf.parse(start);
        Date endTime = sdf.parse(end);

        DataDriver driver = this.getDriver();
        List<Map<String,Object>> list = driver.getShipAisTrack(ais,beginTime,endTime);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //根据GPS设备号和开始、将诶书时间查询GPS轨迹
    /**
     * 根据gps设备号查询船舶轨迹
     * @param gps
     * @param start
     * @param end
     * @return 返回轨迹列表
     * @throws Exception
     */
    @RequestMapping(value = "/getShipGpsTrack")
    @ResponseBody
    public List<Map<String, Object>> getShipGpsTrack(String gps,String start,String end) throws Exception {
        Date beginTime = sdf.parse(start);
        Date endTime = sdf.parse(end);
        DataDriver driver = this.getDriver();
        List<Map<String,Object>> list = driver.getShipGpsTrack(gps,beginTime,endTime);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //根号有船舶名称查询AIS9位码
    /**
     * 根据船舶名称查询船舶ais编号
     * @param cbmc
     * @return ais9位码
     * @throws Exception
     */
    @RequestMapping(value = "/getAissbh")
    @ResponseBody
    public List<Map> getAissbh(String cbmc) throws Exception {
        DataDriver driver = this.getDriver();
        List<Map> list = driver.getAissbh(cbmc);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //根据船舶名称查询GPS编号
    /**
     * 根据船舶名称查询船舶的gps编号
     * @param cbmc
     * @return gps编号
     * @throws Exception
     */
    @RequestMapping(value = "/getGpsbh")
    @ResponseBody
    public String getGpsbh(String cbmc) throws Exception {
        DataDriver driver = this.getDriver();
        String list = driver.getGpsbh(cbmc);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //根据AIS9位码查询船舶名称
    /**
     * 根据ais9位码查询船舶名称
     * @param ais
     * @return 船舶名称
     * @throws Exception
     */
    @RequestMapping(value = "/getAis2cbmc")
    @ResponseBody
    public List<Map> getAis2cbmc(String ais) throws Exception {
        DataDriver driver = this.getDriver();
        List<Map> list = driver.getAis2cbmc(ais);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //根据gos设备号查询船舶名称
    /**
     * 根据gps设备号查询船舶名称
     * @param gps
     * @return 船舶名称
     * @throws Exception
     */
    @RequestMapping(value = "/getGpsbh2cmc")
    @ResponseBody
    public String getGpsbh2cmc(String gps) throws Exception {
        DataDriver driver = this.getDriver();
        String list = driver.getGpsbh2cmc(gps);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //更新船舶的ais9位码
    /**
     * 更新船舶的ais9位码
     * @param ais
     * @param cbmc
     * @return true/false
     * @throws Exception
     */
    @RequestMapping(value = "/setAissbh")
    @ResponseBody
    public boolean setAissbh(String ais,String cbmc) throws Exception {
        DataDriver driver = this.getDriver();
        boolean list = driver.setAissbh(ais,cbmc);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }


    /**
     * 根据举行框查询该区域内的船舶
     * @param minLon
     * @param maxLon
     * @param minLat
     * @param maxLat
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getShips")
    @ResponseBody
    public List<String> getShips(String minLon,String maxLon,String minLat,String maxLat) throws Exception {

        double DminLon = Double.parseDouble(minLon);
        double DmaxLon = Double.parseDouble(maxLon);
        double DminLat = Double.parseDouble(minLat);
        double DmaxLat = Double.parseDouble(maxLat);
        DataDriver driver = this.getDriver();
        List<String> list = driver.getShips(DminLon,DmaxLon,DminLat,DmaxLat);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 根据不规则的区域查询该区域内的船舶数量
     * @param envelope
     * @return
     * @throws Exception
     */
    /*@RequestMapping(value = "/getShipsByArray")
    @ResponseBody
    public List<String> getShips(String[][] envelope) throws Exception {
        double[][]

        DataDriver driver = this.getDriver();
        List<String> list = driver.getShips(envelope);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }*/

    /**
     * 根据河道分段的编号，查询该河道内部的船舶数量
     * @param num
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getShipCountOnRiver")
    @ResponseBody
    public int  getShipCountOnRiver(String num) throws Exception {
        int Inum = Integer.parseInt(num);
        DataDriver driver = this.getDriver();
        int list = driver.getShipCountOnRiver(Inum);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 根据河道分段的编号，查询该河道内部的船舶密度
     * @param num
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getShipDensityOnRiver")
    @ResponseBody
    public double  getShipDensityOnRiver(String num) throws Exception {
        int Inum = Integer.parseInt(num);
        DataDriver driver = this.getDriver();
        double list = driver.getShipDensityOnRiver(Inum);
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 根据城市代码获取该城市内的ais信号的船舶数量
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAisCountOnCity")
    @ResponseBody
    public List<Map<String,Object>>  getAisCountOnCity() throws Exception {
        DataDriver driver = this.getDriver();
        List<Map<String,Object>> list = driver.getAisCountOnCity();
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 根据城市代码获取该城市内的gps信号的船舶数量
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getGpsCountOnCity")
    @ResponseBody
    public List<Map<String,Object>>  getGpsCountOnCity() throws Exception {
        DataDriver driver = this.getDriver();
        List<Map<String,Object>> list = driver.getGpsCountOnCity();
        driver.close();
        return list; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }
}
