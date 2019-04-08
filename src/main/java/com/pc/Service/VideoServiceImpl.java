package com.pc.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pc.Dao.VideoMapper;
import com.pc.Domain.Forum;
import com.pc.Domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Movie> findAll() {
        return null;
    }

    @Override
    public Page<Movie> findByPage(int pageNo, int pageSize,String keyword) {
        PageHelper.startPage(pageNo,pageSize);
        return videoMapper.findByPage(keyword);
    }

    @Override
    public int coutAll(String keyword) {
        return videoMapper.countAll(keyword);
    }

    @Override
    public Map<String, List<Movie>> sortByType() {
        //初始化查询分类
        List<Integer>channels=videoMapper.findByType();
        Map<String,List<Movie>>map=new HashMap<>();
        for (Integer channelId:channels) {
            map.put(channelId.toString(),videoMapper.findByChannel(channelId));
        }
        return map;
    }

    @Override
    public int playcountplus(Integer movieId) {
        int res= videoMapper.coutplus(movieId);
        return res;
    }

    @Override
    public Movie movieDetails(Integer movieId) {
        Movie movie=videoMapper.movieDetails(movieId);
        return movie;
    }

    @Override
    public List<Movie> currentMovies(Integer movieId) {
        int channelId=videoMapper.searchType(movieId);
        List<Movie>movies=videoMapper.searchMovies(channelId);
        return movies;
    }

    @Override
    public int insertForum(Forum forum) {
        int res=videoMapper.insertForum(forum);
        return res;
    }

    @Override
    public List<Forum> forumlist() {
        List<Forum>forums= videoMapper.forumlist();
        return forums;
    }


}
