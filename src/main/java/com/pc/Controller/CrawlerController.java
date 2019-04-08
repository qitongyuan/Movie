package com.pc.Controller;


import com.pc.Service.ICrawerService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/crawer/")
public class CrawlerController {

    private Logger logger= LoggerFactory.getLogger(CrawlerController.class);

    @Autowired
    private ICrawerService iCrawerService;

    @RequestMapping("/getdata")
    @ResponseBody
    public String Crawler() throws IOException {
        Map<String,List<String>>map=new HashMap<>();
        List<String>l1= Arrays.asList("https://www.okzy.co/?m=vod-type-id-5-pg-{&page}.html","45");
        List<String>l2= Arrays.asList("https://www.okzy.co/?m=vod-type-id-6-pg-{&page}.html","55");//差10页
        List<String>l3= Arrays.asList("https://www.okzy.co/?m=vod-type-id-19-pg-{&page}.html","5");//差4页
        List<String>l4=Arrays.asList("https://www.okzy.co/?m=vod-type-id-7-pg-{&page}.html","26");//差3页
        List<String>l5=Arrays.asList("https://www.okzy.co/?m=vod-type-id-8-pg-{&page}.html","14");
        List<String>l6=Arrays.asList("https://www.okzy.co/?m=vod-type-id-11-pg-{&page}.html","4");

        map.put("1",l1);//动作片
        map.put("2",l2);//喜剧片
        map.put("3",l3);//记录片
        map.put("4",l4);//爱情片
        map.put("5",l5);//科幻片
        map.put("6",l6);//战争片
        boolean flag=iCrawerService.getUrlAndNameAndDate(map);
        if (flag==true){
            return "爬虫成功";
        } else {
            return "爬虫失败";
        }
    }

    @RequestMapping("/start")
    @ResponseBody
    public String finalCrawler() throws IOException {
        Map<String,List<String>>map=new HashMap<>();
        List<String>l1= Arrays.asList("https://www.okzy.co/?m=vod-type-id-5-pg-{&page}.html","45");
        List<String>l2= Arrays.asList("https://www.okzy.co/?m=vod-type-id-6-pg-{&page}.html","55");
        List<String>l3= Arrays.asList("https://www.okzy.co/?m=vod-type-id-19-pg-{&page}.html","5");
        List<String>l4=Arrays.asList("https://www.okzy.co/?m=vod-type-id-7-pg-{&page}.html","26");
        List<String>l5=Arrays.asList("https://www.okzy.co/?m=vod-type-id-8-pg-{&page}.html","14");
        List<String>l6=Arrays.asList("https://www.okzy.co/?m=vod-type-id-11-pg-{&page}.html","4");

        map.put("1",l1);//动作片
        map.put("2",l2);//喜剧片
        map.put("3",l3);//记录片
        map.put("4",l4);//爱情片
        map.put("5",l5);//科幻片
        map.put("6",l6);//战争片
        boolean flag=iCrawerService.insertAllData(map);
        if (flag==true){
            return "爬虫成功";
        } else {
            return "爬虫失败";
        }
    }

    /**
     * 测试用例 拿一个分类中的所有url
     * @return
     * @throws IOException
     */
    @RequestMapping("/demo")
    @ResponseBody
    public String demo() throws IOException {
        String url ="https://www.okzy.co/?m=vod-type-id-19-pg-5.html";
        //构建请求
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        Request request = builder.build();
        String resText = null;
        Response response = client.newCall(request).execute();
        resText = response.body().string();
        return  resText;
    }

    /**
     * 拿一个url中所有资源
     * pic: body > div.warp > div:nth-child(1) > div > div > div.vodImg > img
     * content: body > div.warp > div:nth-child(3) > div.vodplayinfo > span
     * url: #\31 > ul > li
     * url2: #\32 > ul > li
     * dircter: body > div.warp > div:nth-child(1) > div > div > div.vodInfo > div.vodinfobox > ul > li:nth-child(2)
     * actors: body > div.warp > div:nth-child(1) > div > div > div.vodInfo > div.vodinfobox > ul > li:nth-child(3)
     * 追加字段 今日播放量 （影片类型 片名）已拿到
     *         外接一个评论表
     * @return
     * @throws IOException
     */
    @RequestMapping("/getcontent")
    @ResponseBody
    public String geturlAndPic() throws IOException {
        String url ="https://www.okzy.co/?m=vod-detail-id-23244.html";
        //构建请求
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        Request request = builder.build();
        String resText = null;
        Response response = client.newCall(request).execute();
        resText = response.body().string();
        //----------------------------------------------
        Document doc;
        Elements elementPic,elementContent,elementUrl,elementDirect,elementActor,elementShowTime;
        //存放内容的集合
        List<String>list=new ArrayList<>();
        try {
                doc = Jsoup.connect(url).get();
                //开始定位页面中图片 url
                //body > div.warp > div:nth-child(1) > div > div > div.vodImg > img
                // body > div.warp > div:nth-child(4) > font > div > div
                //content: body > div.warp > div:nth-child(3) > div.vodplayinfo > span
                //dircter: body > div.warp > div:nth-child(1) > div > div > div.vodInfo > div.vodinfobox > ul > li:nth-child(2)
                //actor:body > div.warp > div:nth-child(1) > div > div > div.vodInfo > div.vodinfobox > ul > li:nth-child(3) > span
                //time:body > div.warp > div:nth-child(1) > div > div > div.vodInfo > div.vodinfobox > ul > li:nth-child(7) > span
                elementPic = doc.select("body").select("div.warp").select("div:nth-child(1)").select("div").select("div").select("div.vodImg").select("img");
                elementContent=doc.select("body").select("div.warp").select("div:nth-child(3)").select("div.vodplayinfo").select("span");
                elementUrl=doc.select("body").select("div.warp").select("div:nth-child(4)").select("font").select("div").select("div").eq(2);
                elementDirect=doc.select("body").select("div.warp").select("div:nth-child(1)").select("div").select("div").select("div.vodInfo").select("div.vodinfobox").select("ul").select("li:nth-child(2)").select("span");
                elementActor=doc.select("body").select("div.warp").select("div:nth-child(1)").select("div").select("div").select("div.vodInfo").select("div.vodinfobox").select("ul").select("li:nth-child(3)").select("span");
                elementShowTime=doc.select("body").select("div.warp").select("div:nth-child(1)").select("div").select("div").select("div.vodInfo").select("div.vodinfobox").select("ul").select("li:nth-child(7)").select("span");
                //影片图片
                String Pic =elementPic.attr("src");
                //影片内容
                String Content=elementContent.text();
                //视频链接
            System.out.println(elementUrl.text());
                int beginIndex=elementUrl.text().indexOf("$");
                int endIndex=elementUrl.text().indexOf(" 全选");
                String videoUrl=elementUrl.text().substring(beginIndex+1,endIndex);
                //导演
                String Direct=elementDirect.text();
                //演员
                String Actor=elementActor.text();
                //上映时间
                String ShowTime=elementShowTime.text();
                list=Arrays.asList(Pic,Content,videoUrl,Direct,Actor,ShowTime);
        } catch (IOException e) {
            logger.warn("Jsoup未定位到");
        }
        for (String item:list) {
            System.out.println(item+",");
        }
        return resText;
    }


}
