package com.pc.Controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.Page;
import com.pc.Domain.Movie;
import com.pc.Service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    private Logger log=LoggerFactory.getLogger(VideoController.class);
    private static final String Prefix="/video";

    @RequestMapping(value = Prefix+"/listshow")
    public String videoshow(){
        return "vlist";
    }

    /**
     * 分页查询-带模糊检索
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = Prefix+"/list")
    @ResponseBody
    public Map videolist(@RequestParam("page")Integer page, @RequestParam("pageSize")Integer pageSize,@RequestParam("keyword")String keyword){
        //System.out.println("关键字为======"+keyword);
        Page<Movie>moviePage=videoService.findByPage(page,pageSize,keyword);
       int count=videoService.coutAll(keyword);
       Map<String,Object>map=new HashMap<>();
       map.put("count",count);
       map.put("data",moviePage);
       return map;
    }

    /**
     * 详情查看，并查找相近的五部分影片
     * @param resourceId
     * @param model
     * @return
     */
    @RequestMapping(value = Prefix+"/detail")
    public String videoDetails(@RequestParam("ResourceID")Integer resourceId,Model model){
        Movie movie= videoService.movieDetails(resourceId);
        List<Movie>commendMovies=videoService.currentMovies(resourceId);
        model.addAttribute("commend",commendMovies);
        //System.out.println(commendMovies);
        model.addAttribute("movie",movie);
        return "detail";
    }

}
