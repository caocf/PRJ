package net.ghpublic.controller;
import framework.modol.BaseRecords;
import framework.modol.BaseResult;
import net.ghpublic.modol.*;
import net.ghpublic.service.ServiceNews;
import net.ghpublic.websocket.PushService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangzedong on 2016/4/18.
 */
@Controller
public class ContrPortNews
{
    @Resource(name = "serviceNews")
    ServiceNews newsService;
    @Resource
    PushService pushService;

    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");

    //地区ID
    final int ZJ=1,HangZ=2, JX=3,HuZ=4,ZS=5;


    //舟山港航动态
    @ResponseBody
    @RequestMapping(value = "zhoushan",method = RequestMethod.GET)
    public void zhoushan(HttpServletRequest request) throws Exception
    {
        String mainurlstring="http://port.zhoushan.gov.cn/DT001.html";//

        Document doc= Jsoup.connect(mainurlstring).get();
        Element body=doc.body();

        Element table    =body.getElementById("GV_news");
        Elements list=table.getElementsByTag("tr");
        for(int i=1;i<list.size()-1;i++)
        {
            Element tr=list.get(i);
            Element a=tr.getElementsByTag("a").get(0);
            String href=a.attr("href");
            String title=a.text().replace("·","");
            Element tdtime=tr.select("td[style=text-align:right;]").get(0);
            String time=tdtime.text();

            System.out.println(tr);
        }
    }
    //知识库
    @ResponseBody
    @RequestMapping(value = "law",method = RequestMethod.GET)
    public void law(HttpServletRequest request) throws Exception
    {
        String mainurlstring="http://zjgh.zjt.gov.cn/col/col6718/index.html";
        Document doc= Jsoup.connect(mainurlstring).get();
        Element body=doc.body();
        String buf=body.toString();
        String[] herf = buf.split("urls\\[i\\]=\\'");
        String[] headers = buf.split("headers\\[i\\]=\\'");
        String[] year = buf.split("year\\[i\\]=\\'");
        String[] month = buf.split("month\\[i\\]=\\'");
        String[] day = buf.split("day\\[i\\]=\\'");
        for (int i = 1; i < herf.length; i++) {
            ModelDynmicNews model = new ModelDynmicNews();
            model.setUrl("http://zjgh.zjt.gov.cn"
                    + herf[i].split("\\';")[0]);
            model.setTitile(headers[i].split("\\';")[0]);
            model.setDate(year[i].split("\\';")[0] + "-"
                    + month[i].split("\\';")[0] + "-"
                    + day[i].split("\\';")[0]);


        }
    }
    //湖州航行通告
    @ResponseBody
    @RequestMapping(value = "hhz",method = RequestMethod.GET)
    public void hhz(HttpServletRequest request) throws Exception
    {
        String mainurlstring="http://www.hzgh.gov.cn/hzgh/zwgk/tzgg/hxtg/index_rs_news_1.page?timestamp="+new Date().getTime();//

        Document doc= Jsoup.connect(mainurlstring).get();
        Element body=doc.body();

        Elements list=body.getElementsByTag("li");
        for(Element element:list)
        {
            Element a=element.child(0);
            String href=a.attr("href");
            String title=a.text();
            String time=element.child(1).text();

            hhzs(href,title,time);
        }
    }
    private void  hhzs(String href,String title,String time) throws IOException
    {
        String mainurlstring="http://www.hzgh.gov.cn/"+href;//"/2017/04/05/38138.shtml";
        Document doc= Jsoup.connect(mainurlstring).get();
        Element body=doc.body();
        Element div= body.getElementById("content");
        String content=div.html();
        System.out.println(content);
    }

    //获取嘉兴新闻
    @ResponseBody
    @RequestMapping(value = "jiaxing",method = RequestMethod.POST)
    public void jiaxing(HttpServletRequest request) throws Exception
    {
        String mainurlstring="http://jxgh.gov.cn/";
        String titleurlstring=mainurlstring+"index_show.php?id="+request.getParameter("id");
        String typeid=request.getParameter("typeid");
        //获取网页XML，解析
        Document titledoc= Jsoup.connect(titleurlstring).get();
        Element titlebody=titledoc.body();
        Elements titles=titlebody.select("a[href*=newsId]");//查询器
        for(Element  title:titles)
        {
            System.out.println(title.toString());
            NewsEN news=new NewsEN();
            news.setTitle(title.text());//标题
            CommonRegionEN regionEN=newsService.RegionByID(JX);
            news.setRegion(regionEN);//地区
            NewsTypeEN newsTypeEN=newsService.NewsTypeByID(Integer.valueOf(typeid));
            news.setNewstype(newsTypeEN);//类型

            String infourl=mainurlstring+title.attr("href");//获取内容地址
            Document infodoc= Jsoup.connect(infourl).get();
            Element infobody=infodoc.body();

            Element time=infobody.getElementsByAttributeValue("height","26").first().child(0);
            String s=time.ownText();
            news.setUpdatetime(sd.parse(time.ownText()));
            Element info=infobody.select("div[style=line-height:2.0em; font-size:12px]").first();//查询器内容
            news.setContent(info.toString());

            newsService.saveEN(news);
        }
    }
    //湖州港航新闻获取
    @ResponseBody
    @RequestMapping(value = "huzhou",method = RequestMethod.POST)
    private void huzhou() throws Exception
    {
        String strURL = "http://www.hzgh.gov.cn/hzgh/xwdt/ghdt/index_rs_news_"+ 1
                + ".page?timestamp=1383549282578";
        URL url = new URL(strURL);
        HttpURLConnection httpConn=(HttpURLConnection) url.openConnection();
        InputStreamReader input=new InputStreamReader(httpConn.getInputStream(), "utf-8");
        BufferedReader bufReader=new BufferedReader(input);
        StringBuilder contentBuf=new StringBuilder();
        String line;
        while ((line = bufReader.readLine()) != null)
        {
            contentBuf.append(line);
        }
        String buf = contentBuf.toString();

        String[] herf = buf.split("href=");
        String[] tiaoshu = buf.split("<li>");
        String[] span = buf.split("<span");

        for (int i = 1; i < tiaoshu.length; i++)
        {
            NewsEN news=new NewsEN();//ModelPortDynmicNews model = new ModelPortDynmicNews();

            int beginIx = tiaoshu[i].indexOf(">");
            int endIx = tiaoshu[i].indexOf("</a>");
            String result = tiaoshu[i].substring(beginIx + 1, endIx);
            int begind = span[i].indexOf(">");
            int endd = span[i].indexOf("</span>");
            String resultd = span[i].substring(begind + 1, endd);
            news.setTitle(result);//标题
            news.setUpdatetime(sd.parse(resultd));//时间
            int beginUrl = herf[i].indexOf("\"");
            int endUrl = herf[i].indexOf("shtml\"");
            String URL="http://www.hzgh.gov.cn"+ herf[i].substring(beginUrl + 1, endUrl) + "shtml";

            detail(news,URL);//内容

            CommonRegionEN regionEN=newsService.RegionByID(HuZ);
            news.setRegion(regionEN);//地区
            NewsTypeEN newsTypeEN=newsService.NewsTypeByID(1);
            news.setNewstype(newsTypeEN);//类别

            newsService.saveEN(news);
        }
    }
    private void detail(NewsEN news,String URL) throws  Exception
    {
        URL url=new URL(URL);
        HttpURLConnection httpConn=(HttpURLConnection) url.openConnection();
        InputStreamReader input=new InputStreamReader(httpConn.getInputStream(),"utf-8");
        BufferedReader bufReader=new BufferedReader(input);
        StringBuilder contentBuf=new StringBuilder();
        String line;
        while((line = bufReader.readLine()) != null)
        {
            contentBuf.append(line);
        }
        String buf = contentBuf.toString();
        String a = (buf.split("<div id=\"content\">")[1])
                .split("<div class=\"clear\">")[0];
        String b = a.replace("href=\"", "href=\"http://www.hzgh.gov.cn");
        b = b.replace("src=\"", "src=\"http://www.hzgh.gov.cn").replaceAll("line-height: 35pt", "line-height: 25pt");
        String noStly=b.replaceFirst("<p.*?>", "<p style=\"line-height: 25pt;text-align: center; \">").replaceAll("&nbsp;", "");

        news.setContent(noStly.replaceAll("font-size: 15pt", "font-size:13pt").replaceAll("mso-line-height-rule: exactly", "").replaceAll("text-indent: 79.5pt",""));
    }
    //获取省港航新闻
    @ResponseBody
    @RequestMapping(value = "zhejiang",method = RequestMethod.POST)
    public void zhejiang(HttpServletRequest request) throws Exception
    {
        String mainurlstring="http://zjgh.zjt.gov.cn/";
        String titleurlstring=request.getParameter("id");
        String typeid=request.getParameter("typeid");

        Document titledoc= Jsoup.connect(mainurlstring).get();
        Element titlebody=titledoc.body();

        Element e1=titlebody.select("a[href*="+titleurlstring+"]").first();
        Element e2=e1.parent().parent().child(1);
        Elements elements=e2.select("a[title]");
        for(Element element:elements)
        {
            NewsEN news=new NewsEN();
            news.setTitle(element.attr("title"));//标题
            CommonRegionEN regionEN=newsService.RegionByID(ZJ);
            news.setRegion(regionEN);//地区
            NewsTypeEN newsTypeEN=newsService.NewsTypeByID(Integer.valueOf(typeid));
            news.setNewstype(newsTypeEN);//类别

            String url=mainurlstring+element.attr("href");
            Document infodoc= Jsoup.connect(url).get();
            Element infobody=infodoc.body();
            Element info=infobody.select("td[style=line-height:22px; padding:10px;]").first();
            news.setContent(info.toString());

            Element head=infodoc.head();
            String time=head.select("meta[name=pubDate]").first().attr("content");
            news.setUpdatetime(sd.parse(time));

            newsService.saveEN(news);
        }
    }
    String types[]={"港航通知","行政通告","航行警告"};
    //接收内网通知
    @ResponseBody
    @RequestMapping(value = "InnerNews",method = RequestMethod.POST)
    public String InnerNews(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("title");
        String s2=request.getParameter("region");
        String s3=request.getParameter("type");
        String s4=request.getParameter("content");

        NewsEN news=new NewsEN();
        news.setTitle(s1);
        news.setUpdatetime(new Date());
        news.setContent(s4);
        CommonRegionEN regionEN=newsService.RegionByID(Integer.valueOf(s2));
        news.setRegion(regionEN);
        NewsTypeEN newsTypeEN=newsService.NewsTypeByID(Integer.valueOf(s3));
        news.setNewstype(newsTypeEN);

        int result=newsService.saveEN(news);

        if(result>0)//保存成功，推送到公众APP
        {
            String ms=types[Integer.valueOf(s3)-4]+","+s1;
            pushService.SendMSAll(ms);
        }

        return String.valueOf(result);
    }

    //获取地区列表
    @ResponseBody
    @RequestMapping(value = "NewsRegions",method = RequestMethod.POST)
    public List<?> NewsRegions(HttpServletRequest request)
    {
        return newsService.NewsRegions();

    }
    //获取新闻类型列表
    @ResponseBody
    @RequestMapping(value = "NewsTypes",method = RequestMethod.POST)
    public List<?> NewsTypes(HttpServletRequest request)
    {
        return newsService.NewsTypes();

    }
    //按地区和类别和页码获取新闻列表
    @ResponseBody
    @RequestMapping(value = "newslist",method = RequestMethod.POST)
    public BaseResult getIndustoryList(HttpServletRequest request)
    {
        String region=request.getParameter("Region");
        String type=request.getParameter("Type");
        int page=Integer.valueOf(request.getParameter("Page"));
        return newsService.getNewsListByCatelog(Integer.valueOf(region),Integer.valueOf(type ),page);

    }
    //获取知识库
    @ResponseBody
    @RequestMapping(value = "Knowlage",method = RequestMethod.POST)
    public BaseResult Knowlage(HttpServletRequest request)
    {
        int page=Integer.valueOf(request.getParameter("Page"));
        return newsService.Knowlage(page);

    }
    //按ID获取新闻
    @ResponseBody
    @RequestMapping(value = "NewsByID",method = RequestMethod.POST)
    public NewsEN NewsByID(HttpServletRequest request)
    {
        String s1=request.getParameter("Newsid");
        NewsEN list=newsService.NewsByID(Integer.valueOf(s1));

        return list;
    }
    //按新闻ID提交评论
    @ResponseBody
    @RequestMapping(value = "comment",method = RequestMethod.POST)
    public BaseResult sumbComment(HttpServletRequest request)
    {
        int n=Integer.valueOf(request.getParameter("Newsid"));
        NewsCommentEN commentEN=new NewsCommentEN();
        commentEN.setContent(request.getParameter("Content"));
        commentEN.setNewsid(n);
        commentEN.setUsername(request.getParameter("Username"));
        commentEN.setSumbtime(new Date());

        int result=newsService.saveEN(commentEN);

        return new BaseResult(result,"");
    }
    //按新闻ID和页码获取评论
    @ResponseBody
    @RequestMapping(value = "commentlist",method = RequestMethod.POST)
    public BaseRecords<NewsCommentEN> getCommentList(HttpServletRequest request)
    {
        String s1=request.getParameter("Newsid");
        String s2=request.getParameter("Page");
        List<NewsCommentEN> list=newsService.getCommentListByCategory(Integer.valueOf(s1),Integer.valueOf(s2));
        return new BaseRecords(list);
    }
    //按地区和类别获取新闻条数
    @ResponseBody
    @RequestMapping(value = "NewsCountByRegionType",method = RequestMethod.POST)
    public BaseResult NewsCountByRegionType(HttpServletRequest request)
    {
        String s1=request.getParameter("type");
        String s2=request.getParameter("region");
        int count=(int)newsService.NewsCountByRegionType(Integer.valueOf(s2),Integer.valueOf(s1));

        return new BaseResult(count,s1);
    }
    //按ID修改通知标题和内容(网页)
    @ResponseBody
    @RequestMapping(value = "UpdateNews",method = RequestMethod.POST)
    public BaseResult UpdateNewsByID(HttpServletRequest request)
    {
        String s1=request.getParameter("ID");
        String s2=request.getParameter("title");
        String s3=request.getParameter("content");
        NewsEN list=newsService.NewsByID(Integer.valueOf(s1));

        list.setTitle(s2);
        list.setContent(s3);
        list.setUpdatetime(new Date());

        return new BaseResult().setObj(list);
    }
}
