package com.pc.Controller;

import com.pc.Domain.Forum;
import com.pc.Service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Random;

@Controller
public class ForumController {

    @Autowired
    private VideoService videoService;

    private Logger log= LoggerFactory.getLogger(ForumController.class);
    private static final String Prefix="/video";

    /**
     * 列表展示
     * @return
     */
    @RequestMapping(value = Prefix+"/forum")
    public String forumlist(Model model){
        List<Forum>forums= videoService.forumlist();
        model.addAttribute("forums",forums);
        return "forum";
    }

    /**
     * 帖子提交
     * @return
     */
    @RequestMapping(value = Prefix+"/foruminput")
    public String forumInput(Forum forum){
        //伪造头像
        int num= new Random().nextInt(1000)+1;
        forum.setAvater("https://picsum.photos/90/90/?image="+num);
        //入库
        videoService.insertForum(forum);
        return "redirect:/video/forum";
    }
}
