package com.pc.Service;


import com.pc.Dao.SourceMapper;
import com.pc.Domain.Movie;
import com.pc.Domain.Source;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CrawerServiceImpl implements ICrawerService {

    @Autowired
    private SourceMapper sourceMapper;

    private Logger logger = LoggerFactory.getLogger(CrawerServiceImpl.class);


    /**
     * https://www.okzy.co/?m=vod-type-id-5.html 动作片
     *
     * @return
     */
    @Override
    public boolean getUrlAndNameAndDate(Map<String, List<String>> map) {
        //使用OKHTTP模拟客户端发送请求
        OkHttpClient client = new OkHttpClient();
        Map<String, String> shujuku = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            //拿到该页面中总页数，将页数拼接到url里
            Integer pageAll = Integer.parseInt(entry.getValue().get(1).toString());
//            Document doc;
//            Elements element;
//            try {
//                doc= Jsoup.connect(addr).get();
//                //body > div.xing_vb > ul:nth-child(52) > li > div
//                element=doc.select("body").select("div.xing_vb").select("ul:nth-child(52)").select("li").select("div");
//                String text=element.text();
//                int begin=text.indexOf("/");
//                pageAll=Integer.parseInt(text.substring(begin+1,begin+3));
//                System.out.println("cc");
//            } catch (IOException e) {
//               logger.warn("Jsoup未定位到");
//            }
            for (int p = 1; p <= pageAll; p++) {
                String page = String.valueOf(p);
                String url = entry.getValue().get(0).toString().replace("{&page}", page);
                //构建请求
                Request.Builder builder = new Request.Builder().url(url);
                //增加请求头,模拟Chrome浏览器
                builder.addHeader("User-Agent" , "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
                Request request = builder.build();
                String resText = null;
                for (int i = 0; i < 10; i++) {
                    try {
                        Response response = client.newCall(request).execute();
                        resText = response.body().string();
                        //成功后则跳出循环
                        break;
                    } catch (IOException e) {
                        logger.warn("爬虫链接超时,正在准备第{}次重试，URL:{}", (i + 1), url);
                        continue;
                    }
                }
                if (resText == null) {
                    logger.error("爬虫抓取失败，失败的url:{}", url);
                    return false;
                }
                //拿到了每一个页面上影片名和链接
                //解析这些resText,拿到网页里海报，视频播放链接，影片简介
                logger.info("第 {} 页", p);
                //logger.info("内容是{}..............",resText);
                //System.out.println(resText);

                Document doc;
                Elements element;
                try {
                    for (int i = 2; i <= 51; i++) {
                        doc = Jsoup.connect(url).get();
                        //开始定位页面中链接和片名
                        //body > div.xing_vb > ul:nth-child(2) > li > span.xing_vb4 > a
                        //body > div.xing_vb > ul:nth-child(51) > li > span.xing_vb4 > a
                        element = doc.select("body").select("div.xing_vb").select("ul:nth-child(" + i + ")").select("li").select("span.xing_vb4").select("a");
                        //影片名
                        String movieName = element.text();
                        //影片链接
                        String movieIp = entry.getKey()+"...."+"https://www.okzy.co" + element.attr("href");
//                        System.out.println(movieName);
//                        System.out.println(movieIp);
                        //存入数据库？
                        shujuku.put(movieName, movieIp);
                    }
                } catch (IOException e) {
                    logger.warn("Jsoup未定位到,继续爬去下一个种类");
                    break;
                }
            }
            //跳至下一个map的键值中
        }
        shujuku.remove("");
        Source source=new Source();
        for (Map.Entry<String,String> e:shujuku.entrySet()) {
            source.setSourceName(e.getKey());//插入影片名字
            //source.setSourceSort(e.getValue().substring(0,3));//影片类型
            source.setChannelId(Integer.parseInt(e.getValue().substring(0,1)));
            source.setSourceUrl(e.getValue().substring(5));//影片的url
            sourceMapper.insertSource(source);
            System.out.println(e.getKey()+","+e.getValue());
        }
        return true;
    }

    @Override
    public boolean insertAllData(Map<String, List<String>> map) {
        //new还一个实体类,拿到数据之后进行封装
        Movie movie=new Movie();
        //存放每一个电影的详细信息
        List<List<String>>data=new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            //拿到该页面中总页数，将页数拼接到url里
            Integer pageAll = Integer.parseInt(entry.getValue().get(1).toString());
            for (int p = 1; p <= pageAll; p++) {
                String page = String.valueOf(p);
                String url = entry.getValue().get(0).toString().replace("{&page}", page);
                Document document;
                Elements element;
                try {
                    for (int i = 2; i <= 51; i++) {
                        document = Jsoup.connect(url).get();
                        //开始定位页面中链接和片名
                        element = document.select("body").select("div.xing_vb").select("ul:nth-child(" + i + ")").select("li").select("span.xing_vb4").select("a");
                        //影片名
                        String MovieName = element.text();
                        //影片链接
                        String MovieIp = entry.getKey()+"...."+"https://www.okzy.co" + element.attr("href");
                        //根据对应的电影IP和电影名去搜索，IP网页中的内容信息，封装入数据库

                        Document doc;
                        Elements elementPic,elementContent,elementUrl,elementDirect,elementActor,elementShowTime;
                        //存放内容的集合
                        List<String>list=new ArrayList<>();
                        //获取URL
                        doc = Jsoup.connect(MovieIp.substring(5)).get();
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
                        int beginIndex=elementUrl.text().indexOf('$');
                        int endIndex=elementUrl.text().indexOf(" 全选");
                        String videoUrl=null;
                        try {
                            videoUrl=elementUrl.text().substring(beginIndex+1,endIndex);
                        }catch (Exception e){
                            //throw e;
                            logger.info("错误是什么呢 自己看"+e);
                            continue;
                        }
                        System.out.println("视频链接"+videoUrl);
                        //导演
                        String Direct=elementDirect.text();
                        //演员
                        String Actor=elementActor.text();
                        //上映时间
                        String ShowTime=elementShowTime.text();
                        //影片类型
                        String channelId=MovieIp.substring(0,1);
                        //影片名
                        list= Arrays.asList(MovieName,channelId,Pic,Content,videoUrl,Direct,Actor,ShowTime);
                        data.add(list);
                    }
                } catch (IOException e) {
                    logger.warn("Jsoup未定位到");
                    continue;
                }
            }
            //跳至下一个map的键值中
        }
        System.out.println("正在插入数据库请稍后。。。。。");
        for (List<String> ldata : data) {
            movie.setMovieName(ldata.get(0));
            movie.setChannelId(Integer.parseInt(ldata.get(1)));
            movie.setMoviePicture(ldata.get(2));
            movie.setMovieContent(ldata.get(3));
            movie.setPlayUrl(ldata.get(4));
            movie.setMovieDirector(ldata.get(5));
            movie.setMovieActors(ldata.get(6));
            movie.setMovieTime(ldata.get(7));
            movie.setIsRecommend(0);
            sourceMapper.insertMovieCotent(movie);
        }
//        for (Map.Entry<String,String> e:data.entrySet()) {
//            source.setSourceName(e.getKey());//插入影片名字
//            //source.setSourceSort(e.getValue().substring(0,3));//影片类型
//            source.setChannelId(Integer.parseInt(e.getValue().substring(0,1)));
//            sourceMapper.insertSource(source);
//            System.out.println(e.getKey()+","+e.getValue());
//        }
        return true;
    }
}
