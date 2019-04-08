package com.pc.Controller;

import com.pc.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlayController {

    private static final String Prefix="/video";

    @Autowired
    private VideoService videoService;

    /**
     * s视频播放观看记录加一
     * @param urlid
     * @param model
     * @return
     */
    @RequestMapping(value = Prefix+"/play")
    public String play(@RequestParam("ResourceID")String urlid, Model model){
        String movieId=urlid.substring(urlid.indexOf("89yu")+4).replaceAll(",","");
        System.out.println("电影ID "+movieId);
        int res= videoService.playcountplus(Integer.parseInt(movieId));
        if (res==1){
            System.out.println("观看记录已经加一");
        }
        String videourl=urlid.substring(0,urlid.indexOf("89yu"));
        int index=videourl.indexOf(".m3u8")+5;
        String url=videourl.substring(0,index);
        System.out.println("播放地址为 "+url);
        model.addAttribute("playurl",url);
        return "play";
    }
}
