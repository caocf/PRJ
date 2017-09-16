package com.zjport.util.charts;

import com.common.base.service.BaseService;
import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.LabelLine;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.axis.*;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.data.WordCloudData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.WordCloud;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Emphasis;
import com.github.abel533.echarts.style.itemstyle.Normal;
import com.zjport.util.ChartsProduceInterface;
import com.zjport.util.DisplayData;
import org.springframework.stereotype.Service;

/**
 * Created by TWQ on 2016/11/30.
 */
@Service("chartService")
public class EchartsProduceImpl extends BaseService implements ChartsProduceInterface{
    public String CreateRainbowBarChart(DisplayData barData, boolean isNeedPie, DisplayData pieData) {

        GsonOption option = new GsonOption();
        option.title().text(barData.getTitle()).link("#").x(X.center).show(false);
        option.tooltip().trigger(Trigger.axis);
        option.calculable(true);
        option.grid().borderWidth(0).y(80).y2(60).x("5%").x2("5%");
        option.toolbox().show(true).feature(new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);

        CategoryAxis category = new CategoryAxis();
        for(String name : barData.getOneByOnename()) {
            category.data(name);
        }
        category.splitLine(new SplitLine().show(false)).axisLabel(new AxisLabel().rotate(45));

        option.xAxis(category);
        option.yAxis(new ValueAxis().show(false));

        Bar bar = new Bar(barData.getTitle());
        bar.itemStyle().normal().color("function(params){var colorList=['#C1232B','#B5C334','#FCCE10','#E87C25','#27727B','#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD','#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0', '#ffa500', '#40e0d0','#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700','#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0'];return colorList[params.dataIndex]}").label().show(true).position(Position.top).formatter("{c}");
        for(String name : barData.getOneByOnename()) {
            bar.data(barData.getOneByOnedata().get(name));
        }
        option.series(bar);

        if(isNeedPie) {
            Pie pie = new Pie(pieData.getTitle());
            pie.radius(0,30).center("75%", "20%").itemStyle().normal().labelLine(new LabelLine().length(20)).label().show(true).position(Position.outer).formatter("{b}\n{c}");
            pie.tooltip().formatter("{b}<br/>{a}:{c}({d}%)").trigger(Trigger.item);
            for(String name : pieData.getOneByOnename()) {
                pie.data(new PieData(name, pieData.getOneByOnedata().get(name)));
            }
            option.series(pie);
        }


        return option.toPrettyString();
    }

    public String CreateBilateralBarChart(DisplayData barData) {
        GsonOption option = new GsonOption();
        option.title().text(barData.getTitle()).link("#").x(X.center).show(false);
        option.tooltip().trigger(Trigger.axis).axisPointer(new AxisPointer().type(PointerType.shadow)).formatter("function(params,ticket,callback){var res ='<strong>'+params[0][1]+'</strong><br/>';for(var i=0;i<params.length;i++){res+=params[i][0]+':'+Math.abs(parseInt(params[i][2]))+'<br/>';}return res;}");

        Legend legend = new Legend();
        for(String name : barData.getOneByMorename()) {
            legend.data(name);
        }
        option.legend(legend);
        option.calculable(true);
        option.grid().borderWidth(0);
        option.toolbox().show(true).feature(new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);

        CategoryAxis category = new CategoryAxis();
        category.splitLine(new SplitLine().show(false)).type(AxisType.category).axisTick(new AxisTick().show(false));
        for(String name : barData.getyName()) {
            category.data(name);
        }

        option.xAxis(new ValueAxis().type(AxisType.value).splitLine(new SplitLine().show(false)).axisLabel( new AxisLabel().show(false).formatter("function(params){return Math.abs(params);}")));
        option.yAxis(category);

        for(String name : barData.getOneByMorename()) {
            Bar bar = new Bar(barData.getTitle());
            for(int i=0;i<barData.getyName().size();i++) {
                bar.data(barData.getOneByMoredata().get(name).get(i));
            }
            if(barData.getOneByMoredata().get(name).get(0) < 0 ) {
                bar.name(name).itemStyle().normal().label(new Label().show(true).position(Position.left).formatter("function(params){return Math.abs(parseInt(params.data));}"));
            } else {
                bar.name(name).itemStyle().normal().label(new Label().show(true).position(Position.right).formatter("function(params){return Math.abs(parseInt(params.data));}"));
            }
            option.series(bar);
        }

        return option.toPrettyString();
    }

    public String CreateRosePie(DisplayData barData) {
        GsonOption option = new GsonOption();
        option.title().text(barData.getTitle()).link("#").show(false);
        option.tooltip().trigger(Trigger.item).formatter("{a}<br/>{b}:{c}({d}%)");

        Legend legend = new Legend();
        legend.x(X.center).y(Y.bottom);
        for(String name : barData.getOneByOnename()) {
            legend.data(name);
        }
        option.legend(legend);
        option.calculable(true);
        option.toolbox().show(true).feature(new MagicType(Magic.pie, Magic.funnel).show(true), Tool.restore, Tool.saveAsImage);

        Pie pie = new Pie(barData.getTitle());
        pie.radius(20,110).name(barData.getTitle()).center("50%", 200).roseType(RoseType.radius).itemStyle().emphasis(new Emphasis().label(new Label().show(false)).labelLine(new LabelLine().show(false))).normal().labelLine(new LabelLine().show(true)).label(new Label().show(true).position(Position.outer).formatter("{b}\n{c}"));
        for(String name : barData.getOneByOnename()) {
            pie.data(new PieData(name, barData.getOneByOnedata().get(name)));
        }
        option.series(pie);
        return option.toPrettyString();
    }

    public String CreateStandardBar(DisplayData barData) {
        GsonOption option = new GsonOption();
        option.title().text(barData.getTitle()).link("#").show(false);
        option.tooltip().trigger(Trigger.axis);
        option.grid().borderWidth(0);
        Legend legend = new Legend();
        legend.x(X.left);
        for(String name : barData.getOneByMorename()) {
            legend.data(name);
        }
        option.legend(legend);
        option.calculable(true);
        option.toolbox().show(true).feature(new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);

        CategoryAxis category = new CategoryAxis();
        for(String name : barData.getyName()) {
            category.data(name);
        }
        category.splitLine(new SplitLine().show(false)).axisLabel(new AxisLabel().rotate(45));

        option.xAxis(category);
        option.yAxis(new ValueAxis().show(false).splitLine(new SplitLine().show(false)));

        for(String name : barData.getOneByMorename()) {
            Bar bar = new Bar(barData.getTitle());
            for(int i=0;i<barData.getyName().size();i++) {
                bar.data(barData.getOneByMoredata().get(name).get(i));
            }
            bar.name(name).itemStyle().normal().label(new Label().show(true).position(Position.top).formatter("{c}"));
            option.series(bar);
        }

        return option.toPrettyString();
    }

    public String CreateCrosswiseBar(DisplayData barData) {
        GsonOption option = new GsonOption();
        option.title().text(barData.getTitle()).link("#").show(false);
        option.tooltip().trigger(Trigger.axis);
        option.grid().borderWidth(0);
        option.calculable(true);
        option.toolbox().show(true).feature(new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);

        CategoryAxis category = new CategoryAxis();
        for(String name : barData.getOneByOnename()) {
            category.data(name);
        }
        category.splitLine(new SplitLine().show(false)).axisLabel(new AxisLabel().rotate(45));

        option.yAxis(category);
        option.xAxis(new ValueAxis().splitLine(new SplitLine().show(false)).boundaryGap(0.0, 0.01));

        Bar bar = new Bar(barData.getTitle());
        bar.itemStyle().normal().color("function(params){var colorList=['#C1232B','#B5C334','#FCCE10','#E87C25','#27727B','#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD','#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'];return colorList[params.dataIndex]}").label().show(true).position(Position.right).formatter("{c}");
        for(String name : barData.getOneByOnename()) {
            bar.data(barData.getOneByOnedata().get(name));
        }
        option.series(bar);

        return option.toPrettyString();
    }

    public String CreateStandardLine(DisplayData barData) {
        GsonOption option = new GsonOption();
        option.title().text(barData.getTitle()).link("#").show(false);
        option.tooltip().trigger(Trigger.axis);
        option.grid().borderWidth(0);
        option.calculable(true);
        option.toolbox().show(true).feature(new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        CategoryAxis category = new CategoryAxis();
        for(String name : barData.getOneByOnename()) {
            category.data(name);
        }
        category.splitLine(new SplitLine().show(false)).boundaryGap(false).axisLabel(new AxisLabel().rotate(45));
        option.xAxis(category);
        option.yAxis(new ValueAxis().splitLine(new SplitLine().show(false)).max(150).axisLabel(new AxisLabel().formatter("{value}")));

        Line line = new Line();
        line.itemStyle().normal().label(new Label().show(true).position(Position.right).formatter("{c}")).areaStyle().typeDefault();
        for(String name : barData.getOneByOnename()) {
            line.data(barData.getOneByOnedata().get(name));
        }
        option.series(line);

        return option.toPrettyString();
    }

    /**
     * 配合字符云显示的方法
     * @return
     */
    private Normal createRandomItemStyle() {
        Normal normal = new Normal();
        normal.color("rgb(" + Math.round(Math.random() * 160) + ","
                + Math.round(Math.random() * 160) + ","
                + Math.round(Math.random() * 160) + ")");
        return normal;
    }

    public String CreateWordCloud(DisplayData barData) {
        GsonOption option = new GsonOption();
        option.title().text(barData.getTitle()).link("#").show(false);
        option.tooltip().show(true);
        WordCloud wordCloud = new WordCloud(barData.getTitle());
        wordCloud.size("100%", "100%");
        wordCloud.textRotation(0, 45, 90, -45);
        wordCloud.textPadding(0);
        wordCloud.autoSize().enable(true).minSize(12);

        int SIZE = barData.getOneByOnename().size();
        for(int i=0; i<SIZE; i++) {
            String name = barData.getOneByOnename().get(i);
            if(i == 0) {
                wordCloud.data(new WordCloudData(name, barData.getOneByOnedata().get(name).intValue()).itemStyle(new ItemStyle().normal(new Normal().color("black"))));
            } else {
                wordCloud.data(new WordCloudData(name, barData.getOneByOnedata().get(name).intValue()).itemStyle(new ItemStyle().normal(createRandomItemStyle())));
            }
        }

        option.series(wordCloud);

        return option.toPrettyString();
    }
}
