package com.pc.Controller;

import com.pc.Domain.Movie;
import com.pc.Service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class SortController {
    @Autowired
    private VideoService videoService;

    private Logger log= LoggerFactory.getLogger(VideoController.class);
    private static final String Prefix="/video";

    @RequestMapping(value = Prefix+"/sort")
    public String archShow(Model model){
        //不同类型电影热度前十
        Map<String,List<Movie>>map=videoService.sortByType();
        model.addAttribute("map",map);
        return "arch";
    }
}
