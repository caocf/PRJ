package com.zjport.video;

import com.common.base.BaseResult;
import com.sun.javafx.image.impl.IntArgb;
import com.zjport.video.model.ChannelCamera;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("/video")
@Controller
public class VideoAction {

    @Resource(name = "videoService")
    VideoService videoService;

    /**
     * 航道航段树结构加载
     */
    @RequestMapping("/hdaotree")
    @ResponseBody
    public BaseResult queryHdaotree(String xzqh) {
        return this.videoService.loadHdaotree(xzqh);
    }

    /**
     * 航道航段树结构加载
     */
    @RequestMapping("/addhdaotree")
    @ResponseBody
    public BaseResult addHdaotree(String xzqh, String id, Integer ctype) {
        return this.videoService.addHdaotree(xzqh, id, ctype);
    }

    /**
     * 搜索名称热键
     */
    @RequestMapping("/queryhit")
    @ResponseBody
    public BaseResult queryHit(Integer ctype, String name,String xzqh) {
        return this.videoService.queryHit(ctype, name,xzqh);
    }

    /**
     * 视频定位
     */
    @RequestMapping("/locationhit")
    @ResponseBody
    public BaseResult locationHit(Integer ctype, Integer id) {
        return this.videoService.locationHit(ctype, id);
    }

    /**
     * 根据id查摄像头
     */
    @RequestMapping("/querycamerabyid")
    @ResponseBody
    public BaseResult queryCameraById(Integer id) {
        return this.videoService.queryCameraById(id);
    }

    /**
     * 根据行政区划查所有摄像头
     */
    @RequestMapping("/querycameras")
    @ResponseBody
    public BaseResult queryCameras(String xzqh) {
        return this.videoService.queryCameras(xzqh);
    }

    //根据行政区划查航道
    @RequestMapping("/queryhdaoxzqh")
    @ResponseBody
    public BaseResult queryHdaoXzqh(String xzqh) {
        return this.videoService.queryHdaoXzqh(xzqh);
    }

    //根据行政区划查航段
    @RequestMapping("/queryhduanxzqh")
    @ResponseBody
    public BaseResult queryHduanXzqh(String xzqh) {
        return this.videoService.queryHduanXzqh(xzqh);
    }

    //根据航道id和行政区划查航段
    @RequestMapping("/queryhduansshdid")
    @ResponseBody
    public BaseResult queryHduanSshdid(Integer sshdid, String xzqh) {
        return this.videoService.queryHduanSshdid(sshdid, xzqh);
    }

    //根据行政区划查航道摄像头
    @RequestMapping("/queryhdaocamera")
    @ResponseBody
    public BaseResult queryHdaoCamera(String xzqh) {
        return this.videoService.queryHdaoCamera(xzqh);
    }

    //根据行政区划查航段摄像头
    @RequestMapping("/queryhduancamera")
    @ResponseBody
    public BaseResult queryHduanCamera(Integer sshdid, String xzqh) {
        return this.videoService.queryHduanCamera(sshdid, xzqh);
    }

    //查询航段上所有摄像头
    @RequestMapping("/queryhdcamera")
    @ResponseBody
    public BaseResult queryHdCamera(Integer hdid, Integer ctype) {
        return this.videoService.queryHdCamera(hdid, ctype);
    }

    //添加摄像头经纬度信息
    @RequestMapping("/addchannelcamera")
    @ResponseBody
    public BaseResult addChannelCamera(@RequestBody ChannelCamera c) {
        return this.videoService.addChannelCamera(c);
    }

    //更新摄像头经纬度信息
    @RequestMapping("/updatechannelcamera")
    @ResponseBody
    public BaseResult updateChannelCamera(@RequestBody ChannelCamera c) {
        return this.videoService.updateChannelCamera(c);
    }
}
