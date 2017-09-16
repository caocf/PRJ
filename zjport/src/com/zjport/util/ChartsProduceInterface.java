package com.zjport.util;

/**
 * Created by TWQ on 2016/11/30.
 */
public interface ChartsProduceInterface {
    /**
     * 生成彩虹条形图
     * @param barData
     * @return
     */
    public String CreateRainbowBarChart(DisplayData barData, boolean isNeedPie, DisplayData pieData);

    /**
     * 生成双边条形图
     * @param barData
     * @return
     */
    public String CreateBilateralBarChart(DisplayData barData);

    /**
     * 生成南丁格尔玫瑰图
     * @param barData
     * @return
     */
    public String CreateRosePie(DisplayData barData);

    /**
     * 生成标准柱状图
     * @param barData
     * @return
     */
    public String CreateStandardBar(DisplayData barData);

    /**
     * 生成横向柱状图
     * @param barData
     * @return
     */
    public String CreateCrosswiseBar(DisplayData barData);

    /**
     * 生成标准折线图
     * @param barData
     * @return
     */
    public String CreateStandardLine(DisplayData barData);

    /**
     * 生成字符云图
     * @param barData
     * @return
     */
    public String CreateWordCloud(DisplayData barData);
}
